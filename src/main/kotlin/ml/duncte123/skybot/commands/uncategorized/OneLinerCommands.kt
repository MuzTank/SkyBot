/*
 * Skybot, a multipurpose discord bot
 *      Copyright (C) 2017  Duncan "duncte123" Sterken
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

package ml.duncte123.skybot.commands.uncategorized

import ml.duncte123.skybot.objects.command.Command
import ml.duncte123.skybot.utils.AirUtils
import ml.duncte123.skybot.utils.EmbedUtils
import net.dv8tion.jda.core.MessageBuilder
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent
import java.lang.management.ManagementFactory

class OneLinerCommands : Command() {

    override fun executeCommand(invoke: String?, args: Array<out String>?, event: GuildMessageReceivedEvent) {
        when (invoke) {
            "ping" -> {
                val time = System.currentTimeMillis()

                event.channel.sendMessage("PONG!").queue { message ->
                    message.editMessageFormat("PONG!" +
                            "\nping is: %dms " +
                            "\nWebsocket ping: %sms\n" +
                            "Average shard ping: %sms",
                            System.currentTimeMillis() - time,
                            event.jda.ping,
                            event.jda.asBot().shardManager.averagePing).queue()
                }
            }

            "cookie" -> sendMsg(event, "<:blobnomcookie_secret:317636549342789632>")

            "trigger" -> sendEmbed(event, EmbedUtils.embedImage("https://cdn.discordapp.com/attachments/94831883505905664/176181155467493377/triggered.gif"))

            "wam" -> sendEmbed(event, EmbedUtils.embedField("GET YOUR WAM NOW!!!!", "[http://downloadmorewam.com/](http://downloadmorewam.com/)"))

            "mineh" -> event.channel.sendMessage(MessageBuilder().setTTS(true).append("Insert creepy music here").build())
                    .queue {sendEmbed(event, EmbedUtils.embedImage("https://cdn.discordapp.com/attachments/204540634478936064/213983832087592960/20160813133415_1.jpg")) }

            "invite" -> sendMsg(event, "Invite me with this link:\n" + "<https://discordapp.com/oauth2/authorize?client_id=${event.jda.selfUser.id}&scope=bot&permissions=8>")

            "uptime" -> sendMsg(event, AirUtils.getUptime(ManagementFactory.getRuntimeMXBean().uptime, true))
        }
    }

    override fun help() =
        "`" + this.PREFIX + "ping` => Shows the delay from the bot to the discord servers.\n" +
                "`" + this.PREFIX + "cookie` => blobnomcookie.\n" +
                "`" + this.PREFIX + "trigger` => use when you are triggered.\n" +
                "`" + this.PREFIX + "wam` => you need more WAM!.\n" +
                "`" + this.PREFIX + "mineh` => HERE COMES MINEH!\n" +
                "`" + this.PREFIX + "invite` => gives you the bot invite\n" +
                "`" + this.PREFIX + "uptime` => shows the bot uptime"

    override fun getName() = "ping"

    override fun getAliases() = arrayOf("cookie", "trigger", "wam", "mineh", "invite", "uptime")
}