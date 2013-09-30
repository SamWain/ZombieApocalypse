package com.basiqnation.ZombieApocalypse;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class StartApocManualDefault extends Event
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