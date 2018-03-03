package co.zpdev.bots.rltrading.commands;

import co.zpdev.bots.core.command.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;

public class Invite {

    @Command(aliases = "invite")
    public void onCommand(Message message) {
        MessageEmbed embed = new EmbedBuilder().setColor(Color.RED)
                .setAuthor("Bot Credits", null, message.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setDescription("To invite this bot to your server, click (here)[https://discordapp.com/oauth2/authorize?client_id=418337753072402443&scope=bot&permissions=8].")
                .build();

        message.getChannel().sendMessage(embed).queue();
    }

}
