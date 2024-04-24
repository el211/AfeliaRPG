package me.afelia.afeliarpg;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class PlayerDataManager {
    private static final Map<Player, PlayerData> playerDataMap = new HashMap<>();

    public static void setPlayerData(Player player, PlayerData playerData) {
        playerDataMap.put(player, playerData);
    }

    public static PlayerData getPlayerData(Player player) {
        return playerDataMap.get(player);
    }
}

