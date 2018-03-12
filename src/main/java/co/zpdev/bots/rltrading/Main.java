package co.zpdev.bots.rltrading;

import co.zpdev.bots.core.command.handler.CommandHandler;
import co.zpdev.bots.rltrading.listeners.GuildJoin;
import co.zpdev.bots.rltrading.listeners.DevControl;
import co.zpdev.bots.rltrading.listeners.MemberJoin;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.hooks.AnnotatedEventManager;
import org.discordbots.api.client.DiscordBotListAPI;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static DiscordBotListAPI dblApi = null;
    public static String DBLTOKEN = "";

    private static boolean servers = true;

    public static void main(String[] args) throws Exception {
        CommandHandler handler = new CommandHandler("!", "co.zpdev.bots.rltrading.commands");

        JDA jda = new JDABuilder(AccountType.BOT).setToken(args[0])
                .setEventManager(new AnnotatedEventManager())
                .addEventListener(handler)
                .addEventListener(new MemberJoin())
                .addEventListener(new GuildJoin())
                .addEventListener(new DevControl())
                .buildBlocking();

        if (args.length > 1) {
            dblApi = new DiscordBotListAPI.Builder().token(args[1]).build();
            DBLTOKEN = args[1];
        }

        if (dblApi != null) dblApi.setStats(jda.asBot().getApplicationInfo().complete().getId(), jda.getGuilds().size());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (servers) jda.getPresence().setGame(Game.playing("!help | " + jda.getGuilds().size() + " servers"));
                else jda.getPresence().setGame(Game.playing("!help | " + jda.getUsers().size() + " users"));
                servers = !servers;
            }
        }, 0, 1000*60*5);
    }

}
