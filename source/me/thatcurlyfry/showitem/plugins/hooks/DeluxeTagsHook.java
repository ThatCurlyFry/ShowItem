package me.thatcurlyfry.showitem.plugins.hooks;

import com.google.common.collect.Maps;
import me.clip.deluxetags.DeluxeTag;
import me.thatcurlyfry.showitem.plugins.Hook;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class DeluxeTagsHook extends Hook {
   public String getPlugin() {
      return "DeluxeTags";
   }

   public Map getReplaceMap(Player var1, Player var2) {
      if(var1 != null && DeluxeTag.hasTagLoaded(var1)) {
         HashMap var3 = Maps.newHashMap();
         var3.put("{deluxe_tag}", DeluxeTag.getPlayerDisplayTag(var1));
         var3.put("{deluxetag_tag}", DeluxeTag.getPlayerDisplayTag(var1));
         return var3;
      } else {
         return null;
      }
   }
}
