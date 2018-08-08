/*
 * Skybot, a multipurpose discord bot
 *      Copyright (C) 2017 - 2018  Duncan "duncte123" Sterken & Ramid "ramidzkh" Khan & Maurice R S "Sanduhr32"
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

@file:Author(nickname = "Sanduhr32", author = "Maurice R S")

package ml.duncte123.skybot.commands.music

import ml.duncte123.skybot.Author
import ml.duncte123.skybot.objects.ConsoleUser
import ml.duncte123.skybot.objects.TrackUserData
import ml.duncte123.skybot.objects.command.CommandContext
import ml.duncte123.skybot.objects.command.MusicCommand
import ml.duncte123.skybot.utils.MessageUtils

@Author(nickname = "Sanduhr32", author = "Maurice R S")
class SkipCommand : MusicCommand() {
    override fun executeCommand(ctx: CommandContext) {

        val event = ctx.event
        val args = ctx.args

        if (!channelChecks(event))
            return

        val mng = getMusicManager(event.guild)
        val scheduler = mng.scheduler
        mng.latestChannel = -1

        if (mng.player.playingTrack == null) {
            MessageUtils.sendMsg(event, "The player is not playing.")
            return
        }
        val count = if (args.isNotEmpty()) {
            if (!args[0].matches("\\d{1,10}".toRegex())) {
                1
            } else {
                args[0].toInt().coerceIn(1, scheduler.queue.size.coerceAtLeast(1))
            }
        } else {
            1
        }

        repeat(count) {
            scheduler.nextTrack()
        }
        val userData = mng.player.playingTrack.userData as TrackUserData
        val user = ctx.jda.getUserById(userData.userId)
        MessageUtils.sendMsg(event, "Successfully skipped $count tracks." +
                if (mng.player.playingTrack != null) {
                    "\nNow playing: ${mng.player.playingTrack.info.title}\n" +
                            "Requester: ${String.format("%#s", user)}"
                } else "")
        mng.latestChannel = event.channel.idLong
    }

    override fun help(): String = "Skips the current track."

    override fun getName(): String = "skip"

    override fun getAliases(): Array<String> = arrayOf("next", "nexttrack", "skiptrack")
}