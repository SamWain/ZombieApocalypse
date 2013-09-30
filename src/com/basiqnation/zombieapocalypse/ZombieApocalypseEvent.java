package com.basiqnation.zombieapocalypse;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ZombieApocalypseEvent extends Event
{
  private static final HandlerList handlers = new HandlerList();
  private String message;
  private int zombies;

  public ZombieApocalypseEvent(int z)
  {
    this.zombies = z;
  }

  public int getZombies() {
    return this.zombies;
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