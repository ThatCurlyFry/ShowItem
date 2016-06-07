package me.thatcurlyfry.showitem.cooldown;

public class Cooldown {
   public long startTime = System.currentTimeMillis();
   public int seconds;

   public Cooldown(int var1) {
      this.seconds = var1;
   }
}
