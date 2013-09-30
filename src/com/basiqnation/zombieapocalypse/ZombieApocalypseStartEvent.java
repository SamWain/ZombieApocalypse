package com.basiqnation.zombieapocalypse;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ZombieApocalypseStartEvent extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private String message;

  public String getMessage()
  {
    return this.message;
  }

  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }
}