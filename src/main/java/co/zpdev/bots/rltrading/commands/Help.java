package co.zpdev.bots.rltrading.commands;

import co.zpdev.bots.core.command.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;

public class Help {

    @Command(aliases = "help")
    public void onCommand(Message message) {
        MessageEmbed embed = new EmbedBuilder().setColor(Color.RED).setAuthor("Command List")
                .setFooter("Developed by ZP4RKER#3333", null)
                .setDescription("**Main Commands**\n!topinvites\n!8ball\n!help\n!credits\n!invite\n" +
                                            "**Staff Commands**\n!ban\n!kick\n!resetinvites").build();

        message.getChannel().sendMessage(embed).queue();
    }

}
