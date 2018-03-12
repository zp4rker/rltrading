package co.zpdev.bots.rltrading.listeners;

import co.zpdev.bots.rltrading.Main;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.SubscribeEvent;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class DevControl {

    @SubscribeEvent
    public void onJarSend(PrivateMessageReceivedEvent event) {
        if (!event.getChannel().getUser().getId().equals("145064570237485056")) return;
        if (event.getMessage().getAttachments().size() != 1) return;

        Message.Attachment attachment = event.getMessage().getAttachments().get(0);
        if (!attachment.getFileName().endsWith(".jar")) return;

        File location = getLocation();
        if (location == null) return;

        location.delete();

        attachment.download(location);

        String token = event.getJDA().getToken();

        event.getJDA().shutdown();

        try {
            List<String> cmd = new ArrayList<>();
            cmd.add("java");
            cmd.add("-jar");
            cmd.add(location.getAbsolutePath());
            cmd.add(token);
            if (!Main.DBLTOKEN.isEmpty()) cmd.add(Main.DBLTOKEN);
            Process process = new ProcessBuilder(cmd).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    @SubscribeEvent
    public void onStopCommand(PrivateMessageReceivedEvent event) {
        if (!event.getChannel().getUser().getId().equals("145064570237485056")) return;
        if (!event.getMessage().getContentRaw().equalsIgnoreCase("stop")) return;

        event.getChannel().sendMessage("Stopping...").complete();
        event.getJDA().shutdown();
        System.exit(0);
    }

    private File getLocation() {
        try {
            return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return null;
        }
    }

}
