package me.thatcurlyfry.showitem.plugins.hooks;

import com.google.common.collect.Maps;
import com.massivecraft.factions.entity.MPlayer;
import com.massivecraft.factions.entity.MPlayerColl;
import me.thatcurlyfry.showitem.Main;
import me.thatcurlyfry.showitem.plugins.Hook;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class FactionsHook extends Hook {
   public String getPlugin() {
      return "Factions";
   }

   public Map getReplaceMap(Player var1, Player var2) {
      if(var1 != null && var2 != null) {
         HashMap var3 = Maps.newHashMap();
         MPlayer var4 = (MPlayer)MPlayerColl.get().get(var1.getUniqueId());
         MPlayer var5 = (MPlayer)MPlayerColl.get().get(var2.getUniqueId());
         if(var4.getFaction().isNone()) {
            var3.put("{faction}", "");
            return var3;
         } else {
            var3.put("{faction}", Main.getInstance().format(Main.getInstance().getConfig().getString("format.factions-format").replace("{faction}", var4.getFaction().getColorTo(var5.getFaction()) + var4.getFaction().getName(var4.getRole().getPrefix()))));
            return var3;
         }
      } else {
         return null;
      }
   }
}
