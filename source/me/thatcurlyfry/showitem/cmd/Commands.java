package me.thatcurlyfry.showitem.cmd;

import me.thatcurlyfry.showitem.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {
   public boolean onCommand(CommandSender var1, Command var2, String var3, String[] var4) {
      if(var2.getName().equalsIgnoreCase("showitem")) {
         if(!var1.hasPermission("showitem.cmd")) {
            var1.sendMessage("\u00a7cYou need to have permission \'showitem.cmd\' to use this command.");
            return true;
         } else if(var4.length == 0) {
            var1.sendMessage(Main.getInstance().format("{prefix}/" + var3 + " reload"));
            return true;
         } else if(var4.length == 1) {
            if(var4[0].equalsIgnoreCase("reload")) {
               Main.getInstance().reloadConfig();
               Main.getInstance().saveConfig();
               Main.getInstance().sendMessage(var1, Main.getInstance().getConfig().getString("message.reload"));
               return true;
            } else {
               var1.sendMessage(Main.getInstance().format("{prefix}/" + var3 + " reload"));
               return true;
            }
         } else {
            var1.sendMessage(Main.getInstance().format("{prefix}/" + var3 + " reload"));
            return true;
         }
      } else {
         return true;
      }
   }
}
