package me.afelia.afeliarpg;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import me.clip.placeholderapi.PlaceholderAPI;

public class RPGMessageListener implements PluginMessageListener {

    private final Plugin plugin;

    public RPGMessageListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("AfeliaRPG")) {
            return;
        }

        // Convert the received message to Harmonie value
        String harmonieString = new String(message);
        int harmonie;
        try {
            harmonie = Integer.parseInt(harmonieString);
        } catch (NumberFormatException e) {
            plugin.getLogger().warning("Received invalid Harmonie value: " + harmonieString);
            return;
        }

        // Update PlaceholderAPI with the received Harmonie value
        String replacedText = PlaceholderAPI.setPlaceholders(player, "%customharmonie_player_harmonie%");
        // Use the replacedText if needed
        // For example: player.sendMessage(replacedText);
    }
}
