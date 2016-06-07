package me.thatcurlyfry.showitem.plugins.hooks;

import com.google.common.collect.Maps;
import me.thatcurlyfry.showitem.Main;
import me.thatcurlyfry.showitem.plugins.Hook;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class VaultHook extends Hook {
   public String getPlugin() {
      return "Vault";
   }

   public Map getReplaceMap(Player var1, Player var2) {
      if(var1 == null) {
         return null;
      } else {
         HashMap var3 = Maps.newHashMap();
         if(Main.getInstance().getChat().getPlayerPrefix(var1) != null && !Main.getInstance().getChat().getPlayerPrefix(var1).isEmpty()) {
            var3.put("{prefix}", Main.getInstance().getChat().getPlayerPrefix(var1));
         } else {
            var3.put("{prefix}", "");
         }

         if(Main.getInstance().getChat().getPlayerSuffix(var1) != null && !Main.getInstance().getChat().getPlayerSuffix(var1).isEmpty()) {
            var3.put("{suffix}", Main.getInstance().getChat().getPlayerSuffix(var1));
         } else {
            var3.put("{suffix}", "");
         }

         return var3;
      }
   }
}
