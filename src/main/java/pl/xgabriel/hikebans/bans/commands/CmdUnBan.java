package pl.xgabriel.bans.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.xgabriel.bans.Main;
import pl.xgabriel.bans.managers.FilesManager;

public class CmdUnBan implements CommandExecutor {
   public CmdUnBan(Main Main) {
      Main.getCommand("unban").setExecutor(this);
   }

   public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {
      if (cs instanceof Player && !cs.hasPermission("hikemc.bans.UNBAN")) {
         cs.sendMessage("§4Nie masz uprawnien.");
         return true;
      } else {
         if (args.length == 1) {
            String nick = args[0];
            String admin = cs.getName();
            if (Main.getBan(nick) != null) {
               Main.unBan(nick);
               String msg = FilesManager.ymlconfig.getString("Broadcast.UnBan");
               msg = msg.replace("&", "§");
               msg = msg.replace("@nick", nick);
               msg = msg.replace("@admin", admin);
               FilesManager.ymlconfig.getBoolean("Broadcast.unbanBroadcast");
               Bukkit.broadcastMessage(msg);
               String toadminPerm = FilesManager.ymlconfig.getString("Messages.unBan");
               toadminPerm = toadminPerm.replace("&", "§");
               toadminPerm = toadminPerm.replace("@nick", nick);
               cs.sendMessage(toadminPerm);
            } else {
               cs.sendMessage("§cPodany gracz nie jest zbanowany.");
            }
         } else {
            cs.sendMessage("§cPoprawne uzycie: /unban <Nick>");
         }

         return false;
      }
   }
}
