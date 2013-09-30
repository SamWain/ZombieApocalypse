package com.basiqnation.ZombieApocalypse;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

class ZombieApocalypseTimer
  implements Listener, Runnable
{
  Random rng = new Random();
  private ZombieApocalypse plugin;
  String worldname = "";
  int confZombies = 40;
  int chance;
  boolean warningmsg;
  boolean safemsg;
  int hardlimit;
  ArrayList<Player> pInvolved = new ArrayList();
  
  
  // Configurable options
  String warning = "";
  String started = "";
  String noplayers = "";
  

  ZombieApocalypseTimer(ZombieApocalypse za){
    this.plugin = za;
    this.worldname = this.plugin.getConfig().getString("world");
    this.chance = this.plugin.getConfig().getInt("chance");
    this.warningmsg = this.plugin.getConfig().getBoolean("warningmsg");
    this.safemsg = this.plugin.getConfig().getBoolean("safemsg");
    this.hardlimit = this.plugin.getConfig().getInt("hardlimit");
    this.warning = this.plugin.getConfig().getString("warning");
    this.started = this.plugin.getConfig().getString("started");
    this.noplayers = this.plugin.getConfig().getString("noplayers");
  }

  public void run()
  {
    if (this.worldname.equals("")) {
      this.worldname = this.plugin.getConfig().getString("world");
    }
    else {
      long time = Bukkit.getServer().getWorld(this.worldname.toString()).getTime();
      int num = this.rng.nextInt(this.chance);
      if ((time >= 12000L) && (time <= 12100L) && (this.warningmsg)) {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
          if (p.getWorld().toString().equals("CraftWorld{name=" + this.worldname + "}")) {
            p.sendMessage(ChatColor.GREEN + warning);
          }
        }
        System.out.println(warning);
      }

      if ((time >= 13000L) && (time <= 13100L))
      {
        switch (num) {
        case 0:
          Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + started);
          int i = 0;
          for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            if (p.getWorld().toString().equals("CraftWorld{name=" + this.worldname + "}")) {
              i++;
            }
          }

          if (i == 0) {
            System.out.println(noplayers);
          }
          else {
            this.confZombies = this.plugin.getConfig().getInt("numzombies");
            if ((this.hardlimit > 0) && 
              (this.confZombies * i > this.hardlimit) && (i > 0)) {
              this.confZombies = (this.hardlimit / i);
            }

            startApocalypse(this.confZombies);
            ZombieApocalypseEvent f = new ZombieApocalypseEvent(this.confZombies);
            Bukkit.getServer().getPluginManager().callEvent(f);
          }
          break;
        default:
          if (this.safemsg)
            for (Player p : Bukkit.getServer().getOnlinePlayers())
              if (p.getWorld().toString().equals("CraftWorld{name=" + this.worldname + "}"))
                p.sendMessage(ChatColor.GREEN + "You are safe for another night, the undead still lie in their graves.");
          break;
        }
      }
    }
  }

  @EventHandler
  public void startEvent(StartApocManualDefault e)
  {
    this.pInvolved.clear();
    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
      if (p.getWorld().toString().equals("CraftWorld{name=" + this.worldname + "}")) {
        this.pInvolved.add(p);
      }
    }
    this.confZombies = this.plugin.getConfig().getInt("numzombies");
    if ((this.hardlimit > 0) && 
      (this.confZombies * this.pInvolved.size() > this.hardlimit) && (this.pInvolved.size() > 0)) {
      this.confZombies = (this.hardlimit / this.pInvolved.size());
    }

    for (Player p : this.pInvolved) {
      p.sendMessage(ChatColor.GREEN + "ZA: Starting apocalypse with " + this.confZombies * this.pInvolved.size() + " zombies.");
      p.sendMessage(ChatColor.GREEN + "ZA: Manual Start");
      p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + started);
    }

    if (this.pInvolved.isEmpty()) {
      System.out.println(ChatColor.GREEN + noplayers);
    }
    else {
      Bukkit.getServer().getWorld(this.worldname).setTime(13150L);
      startApocalypse(this.confZombies);
      ZombieApocalypseEvent f = new ZombieApocalypseEvent(this.confZombies);
      Bukkit.getServer().getPluginManager().callEvent(f);
    }
  }

  @EventHandler
  public void startEvent(StartApocManualSpecified e)
  {
    this.pInvolved.clear();
    for (Player p : Bukkit.getServer().getOnlinePlayers()) {
      if (p.getWorld().toString().equals("CraftWorld{name=" + this.worldname + "}")) {
        this.pInvolved.add(p);
      }
    }
    int zombies = e.getZombies();
    if ((this.hardlimit > 0) && 
      (zombies * this.pInvolved.size() > this.hardlimit) && (this.pInvolved.size() > 0)) {
      zombies = this.hardlimit / this.pInvolved.size();
    }

    for (Player p : this.pInvolved) {
      p.sendMessage(ChatColor.GREEN + "ZA: Starting apocalypse with " + zombies * this.pInvolved.size() + " zombies.");
      p.sendMessage(ChatColor.GREEN + "ZA: Manual Start");
      p.sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + started);
    }
    if (this.pInvolved.isEmpty()) {
      System.out.println(ChatColor.GREEN + noplayers);
    }
    else {
      Bukkit.getServer().getWorld(this.worldname).setTime(13150L);
      startApocalypse(zombies);
      ZombieApocalypseEvent f = new ZombieApocalypseEvent(zombies);
      Bukkit.getServer().getPluginManager().callEvent(f);
    }
  }

  public void startApocalypse(int zombies)
  {
    Random rng = new Random();

    for (Player p : Bukkit.getOnlinePlayers())
    {
      if (p.getWorld().toString().equals("CraftWorld{name=" + this.worldname + "}"))
      {
        for (int j = 0; j < zombies; j++) {
          int x = rng.nextInt(15);
          int z = rng.nextInt(15);
          boolean xOrz = rng.nextBoolean();
          boolean xneg = rng.nextBoolean();
          boolean zneg = rng.nextBoolean();
          if (xOrz) x += 10; else
            z += 10;
          if (xneg)
            x *= -1;
          if (zneg)
            z *= -1;
          Location loc = p.getLocation();
          loc.setX(loc.getX() + x);
          loc.setZ(loc.getZ() + z);
          loc.setY(Bukkit.getWorld(this.worldname).getHighestBlockYAt(loc));
          Bukkit.getWorld(this.worldname).spawnEntity(loc, EntityType.ZOMBIE);
        }
      }
    }
  }
}