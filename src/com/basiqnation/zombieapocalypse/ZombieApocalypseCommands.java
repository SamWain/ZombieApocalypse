package com.basiqnation.zombieapocalypse;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;

public class ZombieApocalypseCommands implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
	  
    if ((commandLabel.equalsIgnoreCase("za")) && (args.length <= 0)){
        sender.sendMessage(ChatColor.GREEN + 
      		  "----Zombie Apocalypse Commands----\n" + 
      		  "/za help - displays information about the plugin\n" + 
      		  "/za start - starts a zombie apocalypse\n" + 
      		  "/za start <int> - starts a zombie apocalypse with the specified number of zombies per player" + 
      		  "/za kills - display kills and goal for the current apocalypse"
      		  );
        return true;
    }
    
    if ((commandLabel.equalsIgnoreCase("za")) && (args[0].equals("help"))){
        sender.sendMessage(ChatColor.GREEN + 
      		  "----Zombie Apocalypse Commands----\n" + 
      		  "/za help - displays information about the plugin\n" + 
      		  "/za start - starts a zombie apocalypse\n" + 
      		  "/za start <int> - starts a zombie apocalypse with the specified number of zombies per player" + 
      		  "/za kills - display kills and goal for the current apocalypse"
      		  );
        return true;
    }

    if ((commandLabel.equalsIgnoreCase("za")) && (args[0].equals("start")) && (sender.isOp())){
      if (args.length == 1) {
        StartApocManualDefault e = new StartApocManualDefault();
        Bukkit.getServer().getPluginManager().callEvent(e);
        return true;
      }
      if (args.length == 2) {
        if (isInteger(args[1])) {
          int i = Integer.parseInt(args[1]);
          if (i > 0)
          {
            StartApocManualSpecified e = new StartApocManualSpecified(i);
            Bukkit.getServer().getPluginManager().callEvent(e);
          }
        }
        else {
          sender.sendMessage(ChatColor.RED + "ZA: Invalid start, the command must be /za start or /za start <integer>.");
        }
        return true;
      }

    }

    if ((commandLabel.equalsIgnoreCase("za")) && (args[0].equals("start")) && (!sender.isOp())){
      sender.sendMessage(ChatColor.RED + "ZA: You need to be an OP to start the apocalypse!");
      return true;
    }

    if ((commandLabel.equalsIgnoreCase("za")) && (args[0].equals("commands"))){
      sender.sendMessage(ChatColor.GREEN + 
    		  "----Zombie Apocalypse Commands----\n" + 
    		  "/za help - displays information about the plugin\n" + 
    		  "/za start - starts a zombie apocalypse\n" + 
    		  "/za start <int> - starts a zombie apocalypse with the specified number of zombies per player" + 
    		  "/za kills - display kills and goal for the current apocalypse"
    		  );
      return true;
    }

    if ((commandLabel.equalsIgnoreCase("za")) && (args[0].equals("kills"))){
      CheckKillsEvent e = new CheckKillsEvent(sender);
      Bukkit.getServer().getPluginManager().callEvent(e);
      return true;
    }
    return false;
  }

  public static boolean isInteger(String s){
    try
    {
      Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return false;
    }
    return true;
  }
}