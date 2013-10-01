package com.basiqnation.zombieapocalypse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;

import net.milkbowl.vault.permission.Permission;

public class ZombieApocalypseCommands implements CommandExecutor {	
	public static ZombieApocalypse plugin;
	public static String pluginName;
	public static String pluginVersion;
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (label.equalsIgnoreCase("za")){
			//  /za
			if (args.length == 0) {
				if (sender.hasPermission("zombieapocalypse.help")){
					sender.sendMessage(ChatColor.GREEN + 
					"----Zombie Apocalypse Commands----\n" + 
					"/za help - displays information about the plugin\n" + 
					"/za start - starts a zombie apocalypse\n" + 
					"/za start <int> - starts a zombie apocalypse with the specified number of zombies per player" + 
					"/za kills - display kills and goal for the current apocalypse"
					);
					return true;
				}
				else {
					sender.sendMessage(ChatColor.RED + "You do not have permission for /za.");
				}
				return true;
			}
			
			//  /za help
			if (args.length == 1 && (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?"))) {
				if (sender.hasPermission("zombieapocalypse.help")) {
					sender.sendMessage(ChatColor.GREEN + 
					"----Zombie Apocalypse Commands----\n" + 
					"/za help - displays information about the plugin\n" + 
					"/za start - starts a zombie apocalypse\n" + 
					"/za start <int> - starts a zombie apocalypse with the specified number of zombies per player" + 
					"/za kills - display kills and goal for the current apocalypse"
					);
					return true;
				}
				else {
					sender.sendMessage(ChatColor.RED + "You do not have permission for /za ? or /za help.");
				}
				return true;
			}

			//  /za start
			if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
				if (sender.hasPermission("zombieapocalypse.start")) {
					StartApocManualDefault e = new StartApocManualDefault();
					Bukkit.getServer().getPluginManager().callEvent(e);
					return true;
				}
				else  {
					sender.sendMessage(ChatColor.RED + "You do not have permission for /za start");
				}
				return true;
			}
			
			//  /za kills
			if (args.length == 1 && args[0].equalsIgnoreCase("kills")){
				if (sender.hasPermission("zombieapocalypse.kills")) {
					CheckKillsEvent e = new CheckKillsEvent(sender);
					Bukkit.getServer().getPluginManager().callEvent(e);
					return true;
				}
				else {
					sender.sendMessage(ChatColor.RED + "You do not have permission for /za kills.");
				}
				return true;
			}
			
			//  /za start <int>
			if (args.length == 2 && args[0].equalsIgnoreCase("start")){
				if(sender.hasPermission("zombieapocalypse.start.amount")) {
					if (isInteger(args[1])) {
						int i = Integer.parseInt(args[1]);
						if (i > 0){
							StartApocManualSpecified e = new StartApocManualSpecified(i);
							Bukkit.getServer().getPluginManager().callEvent(e);
							return true;
						}
					}
				}
				else {
					sender.sendMessage(ChatColor.RED + "Invalid start, the command must be /za start or /za start <integer>.");
				}
				return true;
				
			}
			
			// /za wrong or too many arguments
			if (args.length > 2 || (!args[0].equalsIgnoreCase("help") || !args[0].equalsIgnoreCase("?") ||	!args[0].equalsIgnoreCase("start") || !args[0].equalsIgnoreCase("kills"))) {
				if (sender.hasPermission("zombieapocalypse.help")) {
					sender.sendMessage(ChatColor.RED + "Invalid or too many arguments! See usage: /za ?" );
					return true;
				}
				else {
					sender.sendMessage(ChatColor.RED + "You do not have permission for /za.");
				}
				return true;
			}
		}
		else {
			return false;
		}
		return true;
	}
	
	public static boolean isInteger(String s){
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
}