package me.thatcurlyfry.showitem.plugins.hooks;

import com.google.common.collect.Maps;
import me.thatcurlyfry.showitem.plugins.Hook;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class PlaceholdersHook extends Hook {
   public String getPlugin() {
      return "ShowItem";
   }

   public Map getReplaceMap(Player var1, Player var2) {
      if(var1 == null) {
         return null;
      } else {
         HashMap var3 = Maps.newHashMap();
         var3.put("{name}", var1.getName());
         var3.put("{dis_name}", var1.getDisplayName());
         return var3;
      }
   }
}
