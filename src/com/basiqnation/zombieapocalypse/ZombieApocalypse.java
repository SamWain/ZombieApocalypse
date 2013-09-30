package com.basiqnation.zombieapocalypse;

import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class ZombieApocalypse extends JavaPlugin
{
  public void onEnable()
  {
    if (!new File(getDataFolder(), "config.yml").exists()) {
      saveDefaultConfig();
    }
    if (getConfig().getBoolean("configured"))
    {
      getServer().getPluginManager().registerEvents(new ZombieApocalypseListener(this), this);
      getServer().getPluginManager().registerEvents(new ZombieApocalypseTimer(this), this);

      getServer().getScheduler().scheduleSyncRepeatingTask(this, new ZombieApocalypseTimer(this), 0L, 100L);
      getServer().getScheduler().scheduleSyncRepeatingTask(this, new ZombieApocalypseListener(this), 0L, 60L);
      getCommand("za").setExecutor(new ZombieApocalypseCommands());
    }
    else
    {
      Bukkit.getServer().broadcastMessage(ChatColor.RED + "ZA: Please configure Zombie Apocalypse's config.yml");
      getServer().getPluginManager().registerEvents(new AlertConfig(), this);
    }
  }
}