package com.basiqnation.ZombieApocalypse;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CheckKillsEvent extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private String message;
  private CommandSender pl;

  public CheckKillsEvent(CommandSender p)
  {
    this.pl = p;
  }

  public CommandSender getPlayer() {
    return this.pl;
  }

  public String getMessage() {
    return this.message;
  }

  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}