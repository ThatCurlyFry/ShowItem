package me.thatcurlyfry.showitem.plugins.hooks;

import com.google.common.collect.Maps;
import me.clip.deluxechat.DeluxeChat;
import me.clip.deluxechat.objects.DeluxeFormat;
import me.clip.deluxechat.placeholders.PlaceholderHandler;
import me.thatcurlyfry.showitem.plugins.Hook;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DeluxeChatHook extends Hook {
   public String getPlugin() {
      return "DeluxeChat";
   }

   public Map getReplaceMap(Player var1, Player var2) {
      DeluxeChat var3 = (DeluxeChat)Bukkit.getPluginManager().getPlugin(this.getPlugin());
      if(var3 != null && var1 != null && var3.getPlayerFormat(var1) != null) {
         HashMap var4 = Maps.newHashMap();
         DeluxeFormat var5 = var3.getPlayerFormat(var1);
         if(var5.getPrefix() != null && !var5.getPrefix().isEmpty()) {
            var4.put("{deluxechat_prefix}", PlaceholderHandler.setPlaceholders(var1, var5.getPrefix()));
         } else {
            var4.put("{deluxechat_prefix}", "");
         }

         if(var5.getSuffix() != null && !var5.getSuffix().isEmpty()) {
            var4.put("{deluxechat_suffix}", PlaceholderHandler.setPlaceholders(var1, var5.getSuffix()));
         } else {
            var4.put("{deluxechat_suffix}", "");
         }

         return var4;
      } else {
         return null;
      }
   }
}
