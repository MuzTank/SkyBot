package ml.duncte123.skybot.commands;

import ml.duncte123.skybot.Command;
import ml.duncte123.skybot.utils.HelpEmbeds;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpCommand extends Command {

    public final static String help = "shows a list of all the commands.";
    /**
     * This is a check to see if the command is save to execute
     * @param args The command agruments
     * @param event a instance of {@link net.dv8tion.jda.core.events.message.MessageReceivedEvent MessageReceivedEvent}
     * @return true if we are the command is safe to run
     */
    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * This is the action of the command, the thing you want the command to to needs to be in here
     * @param args The command agruments
     * @param event a instance of {@link net.dv8tion.jda.core.events.message.MessageReceivedEvent MessageReceivedEvent}
     */
    @Override
    public void action(String[] args, MessageReceivedEvent event) {


        event.getAuthor().openPrivateChannel().queue( (pc) -> {
            pc.sendMessage(HelpEmbeds.mainCommands).queue( msg -> {
                        pc.sendMessage(HelpEmbeds.musicCommands).queue();
                        pc.sendMessage(HelpEmbeds.funCommands).queue();
                        pc.sendMessage(HelpEmbeds.modCommands).queue();
                        event.getTextChannel().sendMessage(event.getMember().getAsMention() +" check your DM's").queue();
                    },
                    //When sending fails, send to the channel
                    err -> {
                        TextChannel currentChann = event.getTextChannel();
                        currentChann.sendMessage(HelpEmbeds.mainCommands).queue();
                        currentChann.sendMessage(HelpEmbeds.musicCommands).queue();
                        currentChann.sendMessage(HelpEmbeds.funCommands).queue();
                        currentChann.sendMessage(HelpEmbeds.modCommands).queue();
                        currentChann.sendMessage("Message could not be delivered to dm's and has been send in this channel.").queue();
                    }
            );
        },
                err -> event.getChannel().sendMessage("ERROR: " + err.getMessage()).queue()
        );
    }

    /**
     * The usage instructions of the command
     * @return a String
     */
    @Override
    public String help() {
        // TODO Auto-generated method stub
        return help;
    }
}