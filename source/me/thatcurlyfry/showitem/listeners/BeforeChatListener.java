package me.thatcurlyfry.showitem.listeners;

import me.thatcurlyfry.showitem.Main;
import me.thatcurlyfry.showitem.cooldown.CooldownManager;
import me.thatcurlyfry.showitem.plugins.Hook;
import me.thatcurlyfry.showitem.plugins.Hooks;
import mkremins.fanciful.FancyMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class BeforeChatListener implements Listener {
   @EventHandler
   public void onKick(PlayerKickEvent var1) {
      if(Main.getInstance().getConfig().getInt("use_cooldown") > 0) {
         CooldownManager.getInstance().removeCooldown(var1.getPlayer(), "ITEM_CHAT");
      }

   }

   @EventHandler
   public void onQuit(PlayerQuitEvent var1) {
      if(Main.getInstance().getConfig().getInt("use_cooldown") > 0) {
         CooldownManager.getInstance().removeCooldown(var1.getPlayer(), "ITEM_CHAT");
      }

   }

   @EventHandler
   public void onItemChat(AsyncPlayerChatEvent var1) {
      Player var2 = var1.getPlayer();
      String var3 = var1.getMessage();
      var3 = var1.getPlayer().isOp()?ChatColor.translateAlternateColorCodes('&', var3):var3.replace("&", "");
      if(StringUtils.containsIgnoreCase(ChatColor.stripColor(var3), Main.getInstance().getConfig().getString("format.item-format"))) {
         if(Main.getInstance().getConfig().getInt("use_cooldown") > 0) {
            if(CooldownManager.getInstance().hasCooldown(var2, "ITEM_CHAT") && !CooldownManager.getInstance().checkCooldown(var2, "ITEM_CHAT")) {
               var1.setCancelled(true);
               var1.getRecipients().clear();
               CooldownManager.getInstance().cooldownMsg(var2, "ITEM_CHAT");
               return;
            }

            CooldownManager.getInstance().addCooldown(var2, "ITEM_CHAT", Main.getInstance().getConfig().getInt("use_cooldown"));
         }

         if(!Main.getInstance().getConfig().getString("permission").isEmpty() && !var2.hasPermission(Main.getInstance().getConfig().getString("permission"))) {
            var1.setCancelled(true);
            var1.getRecipients().clear();
            Main.getInstance().sendMessage(var2, Main.getInstance().getConfig().getString("message.no-perms"));
         } else if(var2.getItemInHand() != null && var2.getItemInHand().getType() != Material.AIR) {
            this.sendItemMsg(var2, var3, var1.getRecipients());
            var1.setCancelled(true);
            var1.getRecipients().clear();
         } else {
            var1.setCancelled(true);
            var1.getRecipients().clear();
            Main.getInstance().sendMessage(var2, Main.getInstance().getConfig().getString("message.empty-hand"));
         }
      }
   }

   private ItemStack getItem(ItemStack var1) {
      var1 = var1.clone();
      ItemMeta var2 = var1.getItemMeta();
      List var3 = null;
      if(var1.getAmount() > 1) {
         if(var1.getItemMeta().hasLore()) {
            var3 = var1.getItemMeta().getLore();
            var3.add(Main.getInstance().format(Main.getInstance().getConfig().getString("format.amount-format")).replace("{amount}", Integer.toString(var1.getAmount())));
         } else {
            var3 = Arrays.asList(new String[]{Main.getInstance().format(Main.getInstance().getConfig().getString("format.amount-format")).replace("{amount}", Integer.toString(var1.getAmount()))});
         }

         var2.setLore(var3);
         var1.setItemMeta(var2);
      }

      return var1;
   }

   private void sendItemMsg(Player var1, String var2, Set var3) {
      String[] var4 = var2.split(" ");
      String var5 = "";
      String var6 = "";
      String var7 = "";

      for(int var8 = 0; var8 < var4.length; ++var8) {
         String var9 = var4[var8];
         if(StringUtils.containsIgnoreCase(var9, Main.getInstance().getConfig().getString("format.item-format"))) {
            var5 = var9;
            var6 = StringUtils.substringBefore(var2, var9);
            var7 = StringUtils.substringAfter(var2, var9);
            break;
         }
      }

      var2.replace(var5, "");

      Player var15;
      FancyMessage var17;
      label70:
      for(Iterator var16 = var3.iterator(); var16.hasNext(); var17.send(var15)) {
         var15 = (Player)var16.next();
         String var10 = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("format.chat-format"));
         Iterator var12 = Hooks.getActive().iterator();

         while(true) {
            Hook var11;
            do {
               if(!var12.hasNext()) {
                  var17 = new FancyMessage(var10);
                  if(!var6.isEmpty()) {
                     var6 = ChatColor.getLastColors(var10) + var6;
                     var17.then(ChatColor.translateAlternateColorCodes('&', var6));
                  }

                  String var18 = ChatColor.translateAlternateColorCodes('&', Main.getInstance().getConfig().getString("format.item-chat-format"));
                  if(var1.getItemInHand().hasItemMeta() && var1.getItemInHand().getItemMeta().hasDisplayName()) {
                     var18 = var18.replace("{item}", var1.getItemInHand().getItemMeta().getDisplayName().trim());
                  } else {
                     var18 = var18.replace("{item}", WordUtils.capitalizeFully(var1.getItemInHand().getType().name().replace("_", " ")).trim());
                  }

                  var17.then(var18);
                  var17.itemTooltip(this.getItem(var1.getItemInHand()));
                  if(!var7.isEmpty()) {
                     var17.then(ChatColor.translateAlternateColorCodes('&', !var7.contains("&") && !var7.contains("\u00a7")?this.setMsgColor(var7, ChatColor.getLastColors(var6.isEmpty()?var10:var6)):this.setMsgColor(var7, ChatColor.getLastColors(var7))));
                  }
                  continue label70;
               }

               var11 = (Hook)var12.next();
            } while(var11.getReplaceMap(var1, var15) == null);

            Entry var13;
            for(Iterator var14 = var11.getReplaceMap(var1, var15).entrySet().iterator(); var14.hasNext(); var10 = var10.replace((CharSequence)var13.getKey(), ChatColor.translateAlternateColorCodes('&', var13.getValue().toString()))) {
               var13 = (Entry)var14.next();
            }
         }
      }

   }

   private String setMsgColor(String var1, String var2) {
      if(var1.contains(" ")) {
         String[] var3 = var1.split(" ");
         String var4 = "";
         String[] var8 = var3;
         int var7 = var3.length;

         for(int var6 = 0; var6 < var7; ++var6) {
            String var5 = var8[var6];
            var4 = var4 + var2 + var5 + " ";
         }

         var1 = var4;
      } else {
         char[] var9 = var1.toCharArray();
         StringBuilder var10 = new StringBuilder();

         for(int var11 = 0; var11 < var9.length; ++var11) {
            var10.append(var2 + var9[var11]);
         }

         var1 = var10.toString();
      }

      return var1;
   }
}
