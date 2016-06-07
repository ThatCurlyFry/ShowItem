package me.thatcurlyfry.showitem.plugins.hooks;

import com.google.common.collect.Maps;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import me.thatcurlyfry.showitem.Main;
import me.thatcurlyfry.showitem.plugins.Hook;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class FactionsUUIDHook extends Hook {
   public String getPlugin() {
      return "Factions";
   }

   public Map getReplaceMap(Player var1, Player var2) {
      if(var1 != null && var2 != null) {
         HashMap var3 = Maps.newHashMap();
         FPlayer var4 = FPlayers.getInstance().getByPlayer(var1);
         FPlayer var5 = FPlayers.getInstance().getByPlayer(var2);
         if(var4.getFaction().isNone()) {
            var3.put("{faction}", "");
            return var3;
         } else {
            var3.put("{faction}", Main.getInstance().format(Main.getInstance().getConfig().getString("format.factions-format").replace("{faction}", var4.getChatTag(var5))));
            return var3;
         }
      } else {
         return null;
      }
   }
}
