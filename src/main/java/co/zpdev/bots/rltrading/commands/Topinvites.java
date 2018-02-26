package co.zpdev.bots.rltrading.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.rltrading.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Topinvites {

    @Command(aliases = "topinvites")
    public void onCommand(Message message) {
        JSONObject data = getData();
        JSONArray array =  data.has("ignore") ? data.getJSONArray("ignore") : null;

        List<Invite> invites = message.getGuild().getInvites().complete().stream()
                .filter(i ->
                    array == null ||
                    !Arrays.asList(array.join(",").split(",")).contains(i.getInviter().getId()) && !i.getInviter().isBot()
                )
                .sorted(Comparator.comparingInt(Invite::getUses))
                .limit(10).collect(Collectors.toList());

        EmbedBuilder embed = new EmbedBuilder().setAuthor("RLTrading Discord", "https://discord.gg/KrFCGta", message.getGuild().getIconUrl())
                .setFooter("Invite Leaderboard", null);

        for (int i = 0; i < invites.size(); i++) {
            User user = invites.get(i).getInviter();
            String name = user.getName() + "#" + user.getDiscriminator();
            String uses = invites.get(i).getUses() + "";
            embed.addField((i+1) + ". " + name, uses, false);
        }

        message.getChannel().sendMessage(embed.build()).queue();
    }

    private JSONObject getData() {
        try {
            File dir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            File file = new File(dir, "config.json");

            FileReader rd = new FileReader(file);
            StringBuilder sb = new StringBuilder();
            int c;
            while ((c = rd.read()) != -1) {
                sb.append((char) c);
            }

            return sb.toString().isEmpty() ? new JSONObject() : new JSONObject(sb.toString());
        } catch (URISyntaxException | IOException e) {
            return new JSONObject();
        }
    }

}
