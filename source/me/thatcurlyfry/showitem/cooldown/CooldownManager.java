package me.thatcurlyfry.showitem.cooldown;

import me.thatcurlyfry.showitem.Main;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class CooldownManager {
   private static CooldownManager instance;
   private ConcurrentHashMap cooldownPlayers = new ConcurrentHashMap();

   public static CooldownManager getInstance() {
      if(instance == null) {
         instance = new CooldownManager();
      }

      return instance;
   }

   public void addCooldown(Player var1, String var2, int var3) {
      this.cooldownPlayers.put(var1.getName() + "_" + var2.toLowerCase(), new Cooldown(var3));
   }

   public boolean checkCooldown(Player var1, String var2) {
      if(this.hasCooldown(var1, var2) && this.getTimeLeft(var1, var2) <= 0) {
         this.removeCooldown(var1, var2);
         return true;
      } else {
         return false;
      }
   }

   public void cooldownMsg(Player var1, String var2) {
      Main.getInstance().sendMessage(var1, Main.getInstance().getConfig().getString("message.on-cooldown").replace("{cooldown}", this.time(this.getTimeLeft(var1, var2))));
   }

   public void removeCooldown(Player var1, String var2) {
      this.cooldownPlayers.remove(var1.getName() + "_" + var2.toLowerCase());
   }

   public Cooldown getCooldown(Player var1, String var2) {
      return !this.hasCooldown(var1, var2)?null:(Cooldown)this.cooldownPlayers.get(var1.getName() + "_" + var2.toLowerCase());
   }

   public int getTimeLeft(Player var1, String var2) {
      if(!this.hasCooldown(var1, var2)) {
         return -1;
      } else {
         Cooldown var3 = this.getCooldown(var1, var2);
         long var4 = var3.startTime;
         long var6 = System.currentTimeMillis();
         long var8 = (var6 - var4) / 1000L - (long)var3.seconds;
         if(var8 >= 0L) {
            var8 = 0L;
         }

         return (int)Math.abs(var8);
      }
   }

   public String time(int var1) {
      int var2 = var1 / 86400;
      int var3 = var1 % 86400;
      int var4 = var3 / 3600;
      int var5 = var3 % 3600;
      int var6 = var5 / 60;
      int var7 = var5 % 60;
      return var2 > 0?var2 + " days":(var4 > 0?var4 + " hours " + var6 + " minutes " + var7 + " seconds":(var6 > 0?var6 + " minutes " + var7 + " seconds":(var1 > 0?var7 + " seconds":"now")));
   }

   public boolean hasCooldown(Player var1, String var2) {
      return this.cooldownPlayers.containsKey(var1.getName() + "_" + var2.toLowerCase());
   }

   public ConcurrentHashMap getMap() {
      return this.cooldownPlayers;
   }
}
