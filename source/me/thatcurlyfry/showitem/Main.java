package me.thatcurlyfry.showitem;

import me.thatcurlyfry.showitem.cmd.Commands;
import me.thatcurlyfry.showitem.cooldown.CooldownManager;
import me.thatcurlyfry.showitem.listeners.AfterChatListener;
import me.thatcurlyfry.showitem.listeners.BeforeChatListener;
import net.amoebaman.util.Reflection;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {
   private Chat chat;
   private final int config_version = 2;

   public void onEnable() {
      getConfig().options().copyDefaults(true);
      saveConfig();
      this.setupConfig();
      this.setupChat();
      if(!Reflection.getVersion().contains("v1_8") && !Reflection.getVersion().contains("v1_9")) {
         this.getServer().getPluginManager().registerEvents(new BeforeChatListener(), this);
         this.getLogger().warning("Using Before v1_8 (" + Reflection.getVersion().substring(0, Reflection.getVersion().length() - 1) + ") Fanciful Library!");
      } else {
         this.getServer().getPluginManager().registerEvents(new AfterChatListener(), this);
         this.getLogger().warning("Using After v1_8 (" + Reflection.getVersion().substring(0, Reflection.getVersion().length() - 1) + ") Fanciful Library!");
      }

      this.getCommand("showitem").setExecutor(new Commands());
   }

   public void onDisable() {
      this.chat = null;
      CooldownManager.getInstance().getMap().clear();
   }

   private boolean setupChat() {
      if(!Bukkit.getPluginManager().isPluginEnabled("Vault")) {
         return false;
      } else {
         RegisteredServiceProvider var1 = this.getServer().getServicesManager().getRegistration(Chat.class);
         if(var1 == null) {
            return false;
         } else {
            this.chat = (Chat)var1.getProvider();
            this.getLogger().warning("Found " + var1.getPlugin().getName() + " v" + var1.getPlugin().getDescription().getVersion() + " for chat hook.");
            return this.chat != null;
         }
      }
   }

   private void setupConfig() {
      if(!(new File(this.getDataFolder(), "config.yml")).exists()) {
         this.saveDefaultConfig();
      } else {
         if(!this.getConfig().isSet("config-version") || this.getConfig().getInt("config-version") < 2) {
            this.getConfig().options().copyDefaults(true);
            this.getConfig().set("config-version", Integer.valueOf(2));
            this.saveConfig();
            this.getLogger().warning("Updating config.yml");
         }

      }
   }

   public Chat getChat() {
      return this.chat;
   }

   public void sendMessage(CommandSender var1, String var2) {
      if(var2.contains("{n}")) {
         String[] var3 = var2.split("\\{n\\}");
         String[] var7 = var3;
         int var6 = var3.length;

         for(int var5 = 0; var5 < var6; ++var5) {
            String var4 = var7[var5];
            var4 = this.format(var4);
            var1.sendMessage(var4);
         }
      } else {
         var1.sendMessage(this.format(var2));
      }

   }

   public String format(String var1) {
      var1 = var1.replace("{prefix}", this.getConfig().getString("message.prefix"));
      var1 = var1.replace("{primary}", this.getConfig().getString("message.primary"));
      var1 = var1.replace("{secondary}", this.getConfig().getString("message.secondary"));
      var1 = ChatColor.translateAlternateColorCodes('&', var1);
      return var1;
   }

   public static Main getInstance() {
      return (Main)JavaPlugin.getPlugin(Main.class);
   }
}
