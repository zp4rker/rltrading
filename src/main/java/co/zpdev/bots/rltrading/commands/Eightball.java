package co.zpdev.bots.rltrading.commands;

import co.zpdev.bots.core.command.Command;
import net.dv8tion.jda.core.entities.Message;

import java.util.Random;

public class Eightball {

    @Command(aliases = "8ball")
    public void onCommand(Message message, String[] args) {
        if (args.length < 1) {
            message.getChannel().sendMessage(message.getAuthor().getAsMention() + ", you didn't ask a question!").queue();
            return;
        }

        String[] answers = {
                "it is certain.",
                "it is decidedly so.",
                "without a doubt.",
                "yes definitely.",
                "you may rely on it.",
                "as I see it, yes.",
                "most likley.",
                "outlook good.",
                "yep.",
                "signs point to yes.",

                "reply hazy try again.",
                "ask again later.",
                "better not tell you now.",
                "cannot predict now.",
                "concentrate and ask again.",

                "don't count on it.",
                "my reply is no.",
                "my sources say no.",
                "outlook not so good.",
                "very doubtful."
        };

        int i = new Random().nextInt(20);

        message.getChannel().sendMessage(message.getAuthor().getAsMention() + ", " + answers[i]).queue();
    }

}
