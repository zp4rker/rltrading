package co.zpdev.bots.rltrading.commands;

import co.zpdev.bots.core.command.Command;
import co.zpdev.bots.rltrading.Main;
import net.dv8tion.jda.core.entities.Message;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

public class Resetinvites {

    @Command(aliases = "resetinvites")
    public void onCommand(Message message) {
        if (message.getMember().getRoles().stream().noneMatch(r -> r.getName().equals("Owner") || r.getName().equals("Admin"))) return;

        JSONObject data = getData();
        JSONArray array = data.has("ignore") ? data.getJSONArray("ignore") : null;

        message.getGuild().getInvites().complete().stream()
                .filter(i ->
                        array == null ||
                        !Arrays.asList(array.join(",").split(",")).contains(i.getInviter().getId()) && !i.getInviter().isBot()
                )
                .forEach(i -> i.delete().complete());

        message.getChannel().sendMessage("Invites have been reset!").queue();
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
