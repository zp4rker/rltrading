package co.zpdev.bots.rltrading;

import co.zpdev.bots.core.command.handler.CommandHandler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.AnnotatedEventManager;

public class Main {

    public static void main(String[] args) throws Exception {
        CommandHandler handler = new CommandHandler("!", "co.zpdev.bots.rltrading.commands");

        new JDABuilder(AccountType.BOT).setToken(args[0])
                .setEventManager(new AnnotatedEventManager()).buildBlocking();
    }

}
