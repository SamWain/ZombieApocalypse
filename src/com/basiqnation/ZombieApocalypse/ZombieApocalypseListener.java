package com.basiqnation.ZombieApocalypse;

import java.util.ArrayList;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;

public class ZombieApocalypseListener implements Listener, Runnable {
  private ZombieApocalypse plugin;
  int zkilled = 0;
  int zgoal;
  boolean zahappening = false;
  boolean morning = false;
  String worldname = "";
  ArrayList<Player> pInvolved = new ArrayList();
  
  // Configurable options
  String notrunnning = "";
  String halfway = "";
  String complete = "";
  String failed = "";
  String nosleep = "";
  String reward_sword = "";
  String reward_chestplate = "";
  String reward_diamonds = "";
  
  int diamonds_amount;
  
  public ZombieApocalypseListener(ZombieApocalypse za) {
    this.plugin = za;
    this.worldname = this.plugin.getConfig().getString("world");
    this.notrunnning = this.plugin.getConfig().getString("notrunnning");
    this.halfway = this.plugin.getConfig().getString("halfway");
    this.complete = this.plugin.getConfig().getString("complete");
    this.failed = this.plugin.getConfig().getString("failed");
    this.nosleep = this.plugin.getConfig().getString("nosleep");
    this.reward_sword = this.plugin.getConfig().getString("reward_sword");
    this.reward_chestplate = this.plugin.getConfig().getString("reward_chestplate");
    this.reward_diamonds = this.plugin.getConfig().getString("reward_diamonds");
    this.diamonds_amount = this.plugin.getConfig().getInt("diamonds_amount");
 }

  @EventHandler
  public void CheckKills(CheckKillsEvent e) {
    if (this.zahappening){
      e.getPlayer().sendMessage(ChatColor.GREEN + "ZA: " + ChatColor.DARK_GREEN + this.zkilled + "/" + this.zgoal + ChatColor.GREEN + " zombies killed.");
    }
    else{
      e.getPlayer().sendMessage(ChatColor.LIGHT_PURPLE + notrunnning);
    }
 }

  @EventHandler
  public void ZombieKilled(EntityDeathEvent e)
  {
    Random rng;
    if ((e.getEntity().toString().equals("CraftZombie")) && (e.getEntity().getWorld().toString().equals("CraftWorld{name=" + this.worldname + "}")))
    {
      this.zkilled += 1;
      if ((this.zahappening) && (!this.morning))
      {
        if (this.zkilled == this.zgoal * 0.5D) {
          for (Player p : this.pInvolved) {
            p.sendMessage(ChatColor.GREEN + halfway);
          }
        }
        if (this.zkilled >= this.zgoal)
        {
          this.zahappening = false;
          for (Player p : this.pInvolved) {
            p.sendMessage(ChatColor.GREEN + complete);
          }

          rng = new Random();
          for (Player p : this.pInvolved){
            int reward = rng.nextInt(100);

            if (reward == 0){
                p.sendMessage(ChatColor.GREEN + reward_sword);
                if (p.getWorld().toString().equals("CraftWorld{name=" + this.worldname + "}")){
                  ItemStack itm = new ItemStack(Material.DIAMOND_SWORD);
                  itm.addEnchantment(Enchantment.DAMAGE_UNDEAD, 5);
                  itm.addEnchantment(Enchantment.DURABILITY, 3);
                  itm.addEnchantment(Enchantment.KNOCKBACK, 2);
                  itm.addEnchantment(Enchantment.FIRE_ASPECT, 2);
                  PlayerInventory p_i = p.getInventory();
                  p_i.addItem(new ItemStack[] { itm });
                }
            }
            else if (reward == 1){
                p.sendMessage(ChatColor.AQUA + reward_chestplate);
                if (p.getWorld().toString().equals("CraftWorld{name=" + this.worldname + "}")){
                  ItemStack itm = new ItemStack(Material.DIAMOND_CHESTPLATE);
                  itm.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
                  itm.addEnchantment(Enchantment.THORNS, 3);
                  itm.addEnchantment(Enchantment.DURABILITY, 3);
                  PlayerInventory p_i = p.getInventory();
                  p_i.addItem(new ItemStack[] { itm });
                }
            }
            else if (reward >=2 && reward <= 25){
                giveDiamond(p);
            }
            else if (reward >=26 && reward <= 100){
                if (p.getWorld().toString().equals("CraftWorld{name=" + this.worldname + "}")){
                    ItemStack itm = new ItemStack(Material.DIAMOND);
                    PlayerInventory p_i = p.getInventory();
                    p_i.addItem(new ItemStack[] { itm });
                }
            }            
          }
        }
      }
    }
  }

  public void giveDiamond(Player p) {
    p.sendMessage(ChatColor.AQUA + reward_diamonds);
    if (p.getWorld().toString().equals("CraftWorld{name=" + this.worldname + "}")){
      ItemStack itm = new ItemStack(Material.DIAMOND);
      PlayerInventory p_i = p.getInventory();
      for (int i = 1; i <= diamonds_amount; i++){
          p_i.addItem(new ItemStack[] { itm });
      }
    }
  }

  @EventHandler
  public void BecomeMorning(ZombieApocalypseMorningEvent e)
  {
    if (this.zahappening)
    {
      this.zahappening = false;
      for (Player p : Bukkit.getOnlinePlayers())
        if (p.getWorld().toString().equals("CraftWorld{name=" + this.worldname + "}"))
          p.sendMessage(ChatColor.RED + failed);
    }
  }

  @EventHandler
  public void Apocalypse(ZombieApocalypseEvent e)
  {
    this.pInvolved.clear();
    this.zkilled = 0;
    for (Player p : Bukkit.getOnlinePlayers()) {
      if (p.getWorld().toString().equals("CraftWorld{name=" + this.worldname + "}")) {
        this.pInvolved.add(p);
      }
    }

    int i = 0;
    for (Player p : this.pInvolved) {
      if (p.getWorld().toString().equals("CraftWorld{name=" + this.worldname + "}")) {
        i++;
      }
    }
    if (e.getZombies() == 1){
    	this.zgoal = 1;
    }
    else {
        this.zgoal = ((int)(i * (0.75D * e.getZombies())));
    }
    
    
    // if in any case someone set the amount of zombies to one, make sure one is listed as being needed a slaying!
    if (this.zgoal == 0){
    	this.zgoal = 1;
    }
    
    for (Player p : Bukkit.getOnlinePlayers()) {
      if (p.getWorld().toString().equals("CraftWorld{name=" + this.worldname + "}")) {
        p.sendMessage(ChatColor.GREEN + "Kill " + this.zgoal + " zombies before dawn!");
      }
    }
    this.zahappening = true;
  }

  public void run()
  {
    if (this.worldname.equals("")) {
      this.worldname = this.plugin.getConfig().getString("world");
    }
    else {
      long time = Bukkit.getServer().getWorld(this.worldname).getTime();
      if (((time >= 23000L) && (time <= 23060L)) || ((time >= 25L) && (time <= 85L)))
      {
        ZombieApocalypseMorningEvent e = new ZombieApocalypseMorningEvent();
        Bukkit.getServer().getPluginManager().callEvent(e);
      }
    }
  }
  
  /*
  @EventHandler
  public void PlayerWelcome(PlayerJoinEvent e){
    e.getPlayer().sendMessage(ChatColor.GREEN + "ZA: This server runs the Zombie Apocalypse plugin by sheodox");
    e.getPlayer().sendMessage(ChatColor.GREEN + "ZA: Type /za help to see information about ZA.");
  }
  */
  
  @EventHandler
  public void kickOutOfBed(PlayerBedEnterEvent e) {
    if (this.zahappening) {
      e.getPlayer().sendMessage(ChatColor.RED + nosleep);
      e.setCancelled(true);
    }
  }
}