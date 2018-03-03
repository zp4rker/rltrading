package co.zpdev.bots.rltrading.commands;

import co.zpdev.bots.core.command.Command;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

import java.util.Arrays;

public class Ban {

    @Command(aliases = "ban")
    public void onCommand(Message message, String[] args) {
        if (!message.getMember().hasPermission(Permission.ADMINISTRATOR) && !message.getMember().hasPermission(Permission.BAN_MEMBERS)) return;

        if (message.getMentionedMembers().size() != 1 && args.length < 2) {
            message.getChannel().sendMessage("Invalid arguments! Correct usage: `!ban @user <reason>`.").queue();
            return;
        }

        Member banned = message.getMentionedMembers().get(0);
        Member banner = message.getMember();
        String reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        message.getGuild().getController().ban(banned, 7, reason).complete();
        message.getChannel().sendMessage(banner.getEffectiveName() + " just banned " + banned.getEffectiveName() + " for " + reason).queue();
    }

}
