package co.zpdev.bots.rltrading.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.rltrading.Main;
import net.dv8tion.jda.core.entities.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;

public class Ignoreinvites {

    @Command(aliases = "ignoreinvites")
    public void onCommand(Message message) {
        if (message.getMember().getRoles().stream().noneMatch(r -> r.getName().equals("Owner") || r.getName().equals("Admin"))) return;
        if (message.getMentionedUsers().size() < 1) return;

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
            rd.close();

            JSONObject data = sb.toString().isEmpty() ? new JSONObject() : new JSONObject(sb.toString());
            JSONArray array = data.has("ignore") ? data.getJSONArray("ignore") : new JSONArray();

            String id = message.getMentionedUsers().get(0).getId();
            boolean contains = false;
            for (int i = 0; i < array.length(); i++) {
                if (array.getString(i).equals(id)) contains = true;
            }
            if (contains) {
                message.getChannel().sendMessage("That user is already in the list!").queue();
                return;
            }

            array.put(id);
            data.put("ignore", array);

            FileWriter wr = new FileWriter(file);
            wr.write(data.toString(2));
            wr.close();

            message.getChannel().sendMessage("Added " + message.getMentionedMembers().get(0).getEffectiveName() + " to the ignore list.").queue();
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
    }

}
