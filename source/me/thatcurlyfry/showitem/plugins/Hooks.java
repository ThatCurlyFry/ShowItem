package me.thatcurlyfry.showitem.plugins;

import com.google.common.collect.Lists;
import me.thatcurlyfry.showitem.Main;
import me.thatcurlyfry.showitem.plugins.hooks.*;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public enum Hooks {
   FACTIONS_UUID(!Bukkit.getPluginManager().isPluginEnabled("MassiveCore") && Bukkit.getPluginManager().isPluginEnabled("Factions")?new FactionsUUIDHook():null, "factionsuuid"),
   FACTIONS(Bukkit.getPluginManager().isPluginEnabled("MassiveCore") && Bukkit.getPluginManager().isPluginEnabled("Factions")?new FactionsHook():null, "factions"),
   DELUXE_TAGS(new DeluxeTagsHook(), "deluxetags"),
   DELUXE_CHAT(new DeluxeChatHook(), "deluxechat"),
   PLACEHOLDERS(new PlaceholdersHook(), "placeholders"),
   VAULT(new VaultHook(), "vault");

   private Hook hook;
   private String id;

   private Hooks(Hook var3, String var4) {
      this.hook = var3;
      this.id = var4;
   }

   public Hook getHook() {
      return this.hook;
   }

   public String getId() {
      return this.id;
   }

   public boolean shouldUse() {
      return Main.getInstance().getConfig().getBoolean("hooks." + this.getId());
   }

   public static List getActive() {
      ArrayList var0 = Lists.newArrayList();
      Hooks[] var4;
      int var3 = (var4 = values()).length;

      for(int var2 = 0; var2 < var3; ++var2) {
         Hooks var1 = var4[var2];
         if(var1.getHook() != null && var1.shouldUse() && var1.getHook().isEnabled()) {
            var0.add(var1.getHook());
         }
      }

      return var0;
   }
}
