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

package ml.duncte123.skybot.commands.guild.mod;

import ml.duncte123.skybot.SinceSkybot;
import ml.duncte123.skybot.objects.command.Command;
import ml.duncte123.skybot.objects.command.CommandCategory;
import ml.duncte123.skybot.utils.EmbedUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class AnnounceCommand extends Command {
    
    public AnnounceCommand() {
        this.category = CommandCategory.MOD_ADMIN;
    }
    
    @Override
    public void executeCommand(String invoke, String[] args, GuildMessageReceivedEvent event) {
        
        Permission[] perms = {
                Permission.ADMINISTRATOR
        };
        
        if (!event.getMember().hasPermission(perms)) {
            sendMsg(event, "I'm sorry but you don't have permission to run this command.");
            return;
        }
        
        if (event.getMessage().getMentionedChannels().size() < 1) {
            sendMsg(event, "Correct usage is `" + PREFIX + getName() + " [#Channel] [Message]`");
            return;
        }

        try {
            TextChannel targetChannel = event.getMessage().getMentionedChannels().get(0);

            if (!targetChannel.getGuild().getSelfMember().hasPermission(targetChannel, Permission.MESSAGE_WRITE, Permission.MESSAGE_READ)) {
                sendMsg(event, "I can not talk in " + targetChannel.getAsMention());
                sendError(event.getMessage());
                return;
            }

            String msg = event.getMessage().getContentRaw().split("\\s+", 3)[2];
            @SinceSkybot(version = "3.52.3")
            EmbedBuilder embed = EmbedUtils.defaultEmbed().setDescription(msg).setFooter(null, "");

            if (!event.getMessage().getAttachments().isEmpty()) {
                event.getMessage().getAttachments().stream().filter(Message.Attachment::isImage).findFirst().ifPresent(attachment -> {
                    if (invoke.endsWith("2"))
                        embed.setThumbnail(attachment.getUrl());
                    else
                        embed.setImage(attachment.getUrl());
                });
            }

            sendEmbed(targetChannel, embed.build());
            sendSuccess(event.getMessage());
            
        } catch (Exception e) {
            sendMsg(event, "WHOOPS: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public String help() {
        return "Announces a message.\n" +
                "Usage `" + PREFIX + getName() + " <message>`";
    }
    
    @Override
    public String getName() {
        return "announce";
    }

    @Override
    public String[] getAliases() {
        return new String[]{"announce2"};
    }
}
