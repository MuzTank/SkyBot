package ml.duncte123.skybot.utils;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.Arrays;


public class CommandParser {

    public CommandContainer parse(String rw, MessageReceivedEvent e){
        final String[] split = rw.substring(rw.indexOf(Config.prefix) + 1, rw.length()).split(" ");
        final String invoke = split[0].toLowerCase();
        final String[] args = Arrays.copyOfRange(split, 1, split.length);

        return new CommandContainer(invoke, args, e);
    }

     public class CommandContainer {
         public final String invoke;
         public final String[] args;
         public final MessageReceivedEvent event;

         /**
          * Puts the contents of a command in a simple class
          * @param invoke The command that is ran
          * @param args The arguments from the command
          * @param e A instance of the {@link net.dv8tion.jda.core.events.message.MessageReceivedEvent MessageReceivedEvent}
          */
         public CommandContainer(String invoke, String[] args, MessageReceivedEvent e){
             this.invoke = invoke;
             this.args = args;
             this.event = e;
         }
     }

}