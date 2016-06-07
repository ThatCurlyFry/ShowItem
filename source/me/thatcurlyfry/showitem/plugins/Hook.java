package me.thatcurlyfry.showitem.plugins;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public abstract class Hook {
   public boolean isEnabled() {
      return this.getPlugin() != null && !this.getPlugin().isEmpty()?Bukkit.getPluginManager().isPluginEnabled(this.getPlugin()):false;
   }

   public abstract String getPlugin();

   public abstract Map getReplaceMap(Player var1, Player var2);
}
