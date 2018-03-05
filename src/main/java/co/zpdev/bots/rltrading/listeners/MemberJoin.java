package co.zpdev.bots.rltrading.listeners;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

import java.awt.*;

public class MemberJoin {

    @SubscribeEvent
    public void onJoin(GuildMemberJoinEvent event) {
        if (!event.getGuild().getId().equals("351614020907696129")) return;

        MessageEmbed embed = new EmbedBuilder().setColor(Color.RED).setAuthor("Welcome to the RLTrading Discord!")
                .setDescription("Select your platform by clicking a suitable emoji below. " +
                        "You can choose more than 1. You will gain access to trading, Price channels and more.").build();

        event.getUser().openPrivateChannel().complete().sendMessage(embed).queue();
    }

}
