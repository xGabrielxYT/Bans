package pl.xgabriel.bans.commands;

import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.xgabriel.bans.Main;
import pl.xgabriel.bans.managers.FilesManager;
import pl.xgabriel.bans.managers.Utils;

public class CmdBanTemp implements CommandExecutor {
   public CmdBanTemp(Main Main) {
      Main.getCommand("tempban").setExecutor(this);
   }

   public boolean onCommand(CommandSender cs, Command cmd, String s, String[] args) {
      if (cs instanceof Player && !cs.hasPermission("hikemc.bans.TEMP")) {
         cs.sendMessage("§4Nie masz uprawnien.");
         return true;
      } else {
         if (args.length >= 3) {
            String nick = args[0];
            String powod = Utils.stringBuilder(args, 2).replace("&", "§");
            String admin = cs.getName();
            long czas = System.currentTimeMillis() + Utils.getTimeWithString(args[1]);
            Main.ban(nick, powod, admin, czas);
            Player other = Bukkit.getPlayerExact(nick);
            if (other == null) {
               String toadminPerm1 = FilesManager.ymlconfig.getString("Messages.banOffline");
               toadminPerm1 = toadminPerm1.replace("&", "§");
               toadminPerm1 = toadminPerm1.replace("@nick", nick);
               toadminPerm1 = toadminPerm1.replace("@powod", powod);
               toadminPerm1 = toadminPerm1.replace("@admin", admin);
               cs.sendMessage(toadminPerm1);
            } else {
               List list = FilesManager.ymlconfig.getStringList("Login.TEMP");
               String msg = "";

               for(int i = 0; i < list.size(); ++i) {
                  msg = msg + (String)list.get(i);
                  if (i <= list.size() - 2) {
                     msg = msg + "@n";
                  }
               }

               msg = msg.replace("&", "§");
               msg = msg.replace("@n", "\n");
               msg = msg.replace("@powod", powod);
               msg = msg.replace("@admin", admin);
               msg = msg.replace("@czas", Utils.getDate(czas));
               String dc = FilesManager.ymlconfig.getString("Messages.discord");
               other.kickPlayer(msg + "\n §7Jeśli grałeś na paczce anticheat \n §czrób screena tego ekranu (z paskiem zadań) i wyslij na discorda \n §cDiscord: " + dc + "\n§cNick: " + other.getName());
            }

            String msg = FilesManager.ymlconfig.getString("Broadcast.TEMP");
            msg = msg.replace("&", "§");
            msg = msg.replace("@nick", nick);
            msg = msg.replace("@powod", powod);
            msg = msg.replace("@admin", admin);
            msg = msg.replace("@czas", Utils.getDate(czas));
            FilesManager.ymlconfig.getBoolean("Broadcast.tempBroadcast");
            Bukkit.broadcastMessage(msg);
            String toadminPerm = FilesManager.ymlconfig.getString("Messages.tempBan");
            toadminPerm = toadminPerm.replace("&", "§");
            toadminPerm = toadminPerm.replace("@nick", nick);
            toadminPerm = toadminPerm.replace("@powod", powod);
            toadminPerm = toadminPerm.replace("@admin", admin);
            toadminPerm = toadminPerm.replace("@czas", Utils.getDate(czas));
            cs.sendMessage(toadminPerm);
         } else {
            cs.sendMessage("§cPoprawne uzycie: /tempban <Nick> <Czas np: 1s1m1h1d1mo1y> <Powod>");
         }

         return false;
      }
   }
}
