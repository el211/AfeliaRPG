package me.afelia.afeliarpg;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class AfeliaRPG extends JavaPlugin {

    private FileConfiguration dataConfig;


    @Override
    public void onEnable() {
        // Register the plugin messaging channel listener
        getServer().getMessenger().registerIncomingPluginChannel(this, "afeliarpg:harmonie", new RPGMessageListener(this));

        // Load or create data.yml file
        File dataFile = new File(getDataFolder(), "data.yml");
        if (!dataFile.exists()) {
            saveResource("data.yml", false);
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    // Method to retrieve the player's Harmonie value
    public int getPlayerHarmonie(Player player) {
        FileConfiguration config = getConfig();
        ConfigurationSection playersSection = config.getConfigurationSection("players");

        if (playersSection != null && playersSection.contains(player.getUniqueId().toString())) {
            return playersSection.getInt(player.getUniqueId().toString() + ".harmonie", 0);
        }

        return 0;
    }
    // Method to send Harmonie value to PlaceholderAPI
    public void sendHarmonieValue(Player player) {
        int harmonie = getPlayerHarmonie(player);
        Bukkit.getServer().sendPluginMessage(this, "AfeliaRPG", String.valueOf(harmonie).getBytes());
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("harmogive")) {
            return handleHarmogiveCommand(sender, args);
        } else if (command.getName().equalsIgnoreCase("harmoset")) {
            return handleHarmosetCommand(sender, args);
        } else if (command.getName().equalsIgnoreCase("harmotake")) {
            return handleHarmotakeCommand(sender, args);
        } else if (command.getName().equalsIgnoreCase("harmoclear")) {
            return handleHarmoclearCommand(sender, args);
        } else if (command.getName().equalsIgnoreCase("harmonie")) {
            return handleHarmonieCommand(sender);
        } else if (command.getName().equalsIgnoreCase("harmosee")) {
            return handleHarmoseeCommand(sender, args);
        } else if (command.getName().equalsIgnoreCase("harmosend")) {
            return handleHarmosendCommand(sender, args);
        } else if (command.getName().equalsIgnoreCase("harmoniehelp")) {
            return handleHarmonieHelpCommand(sender);
        } else if (command.getName().equalsIgnoreCase("harconsolegive")) {
            return handleConsoleHarmogive(sender, args);
        } else if (command.getName().equalsIgnoreCase("harconsoleset")) {
            return handleConsoleHarmoset(sender, args);
        } else if (command.getName().equalsIgnoreCase("harconsoletake")) {
            return handleConsoleHarmotake(sender, args);
        } else if (command.getName().equalsIgnoreCase("harconsoleclear")) {
            return handleConsoleHarmoclear(sender, args);
        } else if (command.getName().equalsIgnoreCase("harconsoleharmonie")) {
            return handleConsoleHarmonie(sender, args);
        } else if (command.getName().equalsIgnoreCase("harconsolesee")) {
            return handleConsoleHarmosee(sender, args);
        } else if (command.getName().equalsIgnoreCase("harconsolesend")) {
            return handleConsoleHarmosend(sender, args);
        }
        return false;
    }

    private boolean handleConsoleHarmogive(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("Usage: /harconsolegive <player> <amount>");
            return false;
        }
        Player target = getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found!");
            return true;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid amount!");
            return true;
        }
        giveHarmonie(target, amount);
        sender.sendMessage("Given " + amount + " Harmonie to " + target.getName() + " from console.");
        return true;
    }

    private boolean handleConsoleHarmoset(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("Usage: /harconsoleset <player> <amount>");
            return false;
        }
        Player target = getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found!");
            return true;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid amount!");
            return true;
        }
        setHarmonie(target, amount);
        sender.sendMessage("Set " + target.getName() + "'s Harmonie balance to " + amount + " from console.");
        return true;
    }

    private boolean handleHarmogiveCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }
        if (!sender.hasPermission("afeliarpg.command.harmogive")) {
            sender.sendMessage("You don't have permission to use this command.");
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage("Usage: /harmogive <player> <amount>");
            return false;
        }
        Player target = getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found!");
            return true;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid amount!");
            return true;
        }
        giveHarmonie(target, amount);
        sender.sendMessage("Given " + amount + " Harmonie to " + target.getName());
        return true;
    }

    private boolean handleHarmosetCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }
        if (!sender.hasPermission("afeliarpg.command.harmoset")) {
            sender.sendMessage("You don't have permission to use this command.");
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage("Usage: /harmoset <player> <amount>");
            return false;
        }
        Player target = getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found!");
            return true;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid amount!");
            return true;
        }
        setHarmonie(target, amount);
        sender.sendMessage("Set " + target.getName() + "'s Harmonie balance to " + amount);
        return true;
    }

    private boolean handleHarmotakeCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }
        if (!sender.hasPermission("afeliarpg.command.harmotake")) {
            sender.sendMessage("You don't have permission to use this command.");
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage("Usage: /harmotake <player> <amount>");
            return false;
        }
        Player target = getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found!");
            return true;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid amount!");
            return true;
        }
        if (getHarmonie(target) < amount) {
            sender.sendMessage(target.getName() + " does not have enough Harmonie.");
            return true;
        }
        takeHarmonie(target, amount);
        sender.sendMessage("Taken " + amount + " Harmonie from " + target.getName());
        return true;
    }

    private boolean handleHarmoclearCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }
        if (!sender.hasPermission("afeliarpg.command.harmoclear")) {
            sender.sendMessage("You don't have permission to use this command.");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("Usage: /harmoclear <player>");
            return false;
        }
        Player target = getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found!");
            return true;
        }
        clearHarmonie(target);
        sender.sendMessage("Cleared " + target.getName() + "'s Harmonie balance");
        return true;
    }

    private boolean handleHarmonieCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }
        Player player = (Player) sender;
        int balance = getHarmonie(player);
        sender.sendMessage("Your Harmonie balance: " + balance);
        return true;
    }

    private boolean handleHarmoseeCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }
        if (!sender.hasPermission("afeliarpg.command.harmosee")) {
            sender.sendMessage("You don't have permission to use this command.");
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage("Usage: /harmosee <player>");
            return false;
        }
        Player target = getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found!");
            return true;
        }
        int balance = getHarmonie(target);
        sender.sendMessage(target.getName() + "'s Harmonie balance: " + balance);
        return true;
    }

    private boolean handleHarmosendCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be run by a player.");
            return true;
        }
        if (!sender.hasPermission("afeliarpg.command.harmosend")) {
            sender.sendMessage("You don't have permission to use this command.");
            return true;
        }
        if (args.length != 2) {
            sender.sendMessage("Usage: /harmosend <player> <amount>");
            return false;
        }
        Player player = (Player) sender;
        Player target = getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found!");
            return true;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid amount!");
            return true;
        }
        if (amount < 0) {
            sender.sendMessage("You can't send a negative amount of Harmonie.");
            return true;
        }
        if (getHarmonie(player) < amount) {
            sender.sendMessage("You don't have enough Harmonie.");
            return true;
        }
        giveHarmonie(target, amount);
        takeHarmonie(player, amount);
        sender.sendMessage("Sent " + amount + " Harmonie to " + target.getName());
        target.sendMessage("Received " + amount + " Harmonie from " + player.getName());
        return true;
    }

    private boolean handleHarmonieHelpCommand(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cCette commande ne peut être exécutée que par un joueur.");
            return true;
        }
        Player player = (Player) sender;
        player.sendMessage("§aListe des commandes Harmonie :");
        player.sendMessage("§e/harmogive <joueur> <montant> - §7Donner des Harmonies à un joueur.");
        player.sendMessage("§e/harmoset <joueur> <montant> - §7Définir le solde des Harmonies d'un joueur.");
        player.sendMessage("§e/harmotake <joueur> <montant> - §7Prendre des Harmonies d'un joueur.");
        player.sendMessage("§e/harmoclear <joueur> - §7Effacer le solde des Harmonies d'un joueur.");
        player.sendMessage("§e/harmonie - §7Vérifier votre solde d'Harmonies.");
        player.sendMessage("§e/harmosee <joueur> - §7Voir le solde d'Harmonies d'un joueur.");
        player.sendMessage("§e/harmosend <joueur> <montant> - §7Envoyer des Harmonies à un autre joueur.");
        return true;
    }

    private boolean handleConsoleHarmotake(CommandSender sender, String[] args) {
        if (args.length != 2) {
            sender.sendMessage("Usage: /harconsoletake <player> <amount>");
            return false;
        }
        Player target = getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found!");
            return true;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid amount!");
            return true;
        }
        if (getHarmonie(target) < amount) {
            sender.sendMessage(target.getName() + " does not have enough Harmonie.");
            return true;
        }
        takeHarmonie(target, amount);
        sender.sendMessage("Taken " + amount + " Harmonie from " + target.getName() + " from console.");
        return true;
    }

    private boolean handleConsoleHarmoclear(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /harconsoleclear <player>");
            return false;
        }
        Player target = getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found!");
            return true;
        }
        clearHarmonie(target);
        sender.sendMessage("Cleared " + target.getName() + "'s Harmonie balance from console.");
        return true;
    }

    private boolean handleConsoleHarmonie(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /harconsoleharmonie <player>");
            return false;
        }
        Player target = getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found!");
            return true;
        }
        int balance = getHarmonie(target);
        sender.sendMessage(target.getName() + "'s Harmonie balance: " + balance + " from console.");
        return true;
    }

    private boolean handleConsoleHarmosee(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("Usage: /harconsolesee <player>");
            return false;
        }
        Player target = getServer().getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("Player not found!");
            return true;
        }
        int balance = getHarmonie(target);
        sender.sendMessage(target.getName() + "'s Harmonie balance: " + balance + " from console.");
        return true;
    }

    private boolean handleConsoleHarmosend(CommandSender sender, String[] args) {
        if (args.length != 3) {
            sender.sendMessage("Usage: /harconsolesend <sender> <receiver> <amount>");
            return false;
        }
        Player senderPlayer = getServer().getPlayer(args[0]);
        Player receiverPlayer = getServer().getPlayer(args[1]);
        if (senderPlayer == null || receiverPlayer == null) {
            sender.sendMessage("Sender or receiver player not found!");
            return true;
        }
        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Invalid amount!");
            return true;
        }
        if (amount < 0) {
            sender.sendMessage("You can't send a negative amount of Harmonie.");
            return true;
        }
        if (getHarmonie(senderPlayer) < amount) {
            sender.sendMessage("You don't have enough Harmonie.");
            return true;
        }
        giveHarmonie(receiverPlayer, amount);
        takeHarmonie(senderPlayer, amount);
        sender.sendMessage("Sent " + amount + " Harmonie to " + receiverPlayer.getName() + " from console.");
        receiverPlayer.sendMessage("Received " + amount + " Harmonie from " + senderPlayer.getName() + " from console.");
        return true;
    }

    private int getHarmonie(Player player) {
        return dataConfig.getInt("players." + player.getUniqueId() + ".harmonie", 0);
    }

    private void giveHarmonie(Player player, int amount) {
        int currentAmount = getHarmonie(player);
        dataConfig.set("players." + player.getUniqueId() + ".harmonie", currentAmount + amount);
        saveDataConfig();
    }

    private void setHarmonie(Player player, int amount) {
        dataConfig.set("players." + player.getUniqueId() + ".harmonie", amount);
        saveDataConfig();
    }

    private void takeHarmonie(Player player, int amount) {
        int currentAmount = getHarmonie(player);
        int newAmount = Math.max(currentAmount - amount, 0);
        dataConfig.set("players." + player.getUniqueId() + ".harmonie", newAmount);
        saveDataConfig();
    }

    private void clearHarmonie(Player player) {
        dataConfig.set("players." + player.getUniqueId() + ".harmonie", 0);
        saveDataConfig();
    }

    private void saveDataConfig() {
        try {
            dataConfig.save(new File(getDataFolder(), "data.yml"));
        } catch (IOException e) {
            getLogger().severe("Error saving data configuration file: " + e.getMessage());
        }
    }

}
