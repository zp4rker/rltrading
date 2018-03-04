package co.zpdev.bots.rltrading.listeners;

import co.zpdev.bots.rltrading.Main;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

public class GuildJoin {

    @SubscribeEvent
    public void onGuildJoin(GuildJoinEvent event) {
        Main.dblApi.setStats(event.getJDA().asBot().getApplicationInfo().complete().getId(), event.getJDA().getGuilds().size());
    }

}
