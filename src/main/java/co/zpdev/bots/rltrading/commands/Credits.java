package co.zpdev.bots.rltrading.commands;

import co.zpdev.bots.core.command.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageEmbed;

import java.awt.*;

public class Credits {

    @Command(aliases = "credits")
    public void onCommand(Message message) {
        MessageEmbed embed = new EmbedBuilder().setColor(Color.RED)
                .setAuthor("Bot Credits", null, message.getJDA().getSelfUser().getEffectiveAvatarUrl())
                .setDescription("Developed by ZP4RKER#3333.\nIdea by Flase#1236\nOriginally made for (RLTrading)[https://discord.gg/KrFCGta].")
                .build();

        message.getChannel().sendMessage(embed).queue();
    }

}
