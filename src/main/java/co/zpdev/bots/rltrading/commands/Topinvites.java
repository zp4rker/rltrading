package co.zpdev.bots.rltrading.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.rltrading.Main;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Invite;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Topinvites {

    @Command(aliases = "topinvites")
    public void onCommand(Message message) {
        JSONObject data = getData();
        JSONArray array =  data.has("ignore") ? data.getJSONArray("ignore") : null;

        List<Invite> list = message.getGuild().getInvites().complete().stream()
                .filter(inv -> {
                    if (array ==  null) return true;
                    if (inv.getInviter() == null) return false;
                    List<String> arrList = new ArrayList<>();
                    for (int i = 0; i < array.length(); i++) {
                        arrList.add(array.getString(i));
                    }
                    return !arrList.contains(inv.getInviter().getId());
                }).collect(Collectors.toList());

        LinkedHashMap<User, Integer> allInv = new LinkedHashMap<>();

        for (Invite i : list) {
            if (allInv.containsKey(i.getInviter())) allInv.replace(i.getInviter(), allInv.get(i.getInviter()) + i.getUses());
            else if (allInv.size() < 10) allInv.put(i.getInviter(), i.getUses());
        }

        LinkedHashMap<User, Integer> invites = new LinkedHashMap<>();

        allInv.entrySet().stream()
                .sorted((o1, o2) -> o1 == o2 ? 0 : o1.getValue() > o2.getValue() ? -1 : 1)
                .forEach(entry -> invites.put(entry.getKey(), entry.getValue()));

        EmbedBuilder embed = new EmbedBuilder().setAuthor("RLTrading Discord", "https://discord.gg/KrFCGta", message.getGuild().getIconUrl())
                .setTitle("Invite Leaderboard").setColor(Color.RED);

        for (int i = 0; i < invites.size(); i++) {
            User user = invites.keySet().toArray(new User[0])[i];
            if (user == null) continue;
            String name = user.getName() + "#" + user.getDiscriminator();
            String uses = allInv.get(user) + " invited";
            embed.addField((i+1) + ". " + name, uses, false);
        }

        message.getChannel().sendMessage(embed.build()).queue();
    }

    private JSONObject getData() {
        try {
            File dir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
            File file = new File(dir, "config.json");
            if (!file.exists()) file.createNewFile();

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
