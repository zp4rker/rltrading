package co.zpdev.bots.rltrading.commands;

import co.zpdev.bots.core.command.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

public class Kick {

    @Command(aliases = "kick")
    public void onCommand(Message message) {
        if (!message.getMember().hasPermission(Permission.KICK_MEMBERS) && !message.getMember().hasPermission(Permission.ADMINISTRATOR)) return;

        if (message.getMentionedMembers().size() != 1) {
            message.getChannel().sendMessage("Invalid arguments! You didn't mention a user. Example: `!kick @user#1234`").queue();
            return;
        }

        Member kicked = message.getMentionedMembers().get(0);
        Member kicker = message.getMember();

        message.getGuild().getController().kick(kicked).complete();
        message.getChannel().sendMessage(kicker.getEffectiveName() + " just kicked " + kicked.getEffectiveName() + ".").queue();
    }

}
