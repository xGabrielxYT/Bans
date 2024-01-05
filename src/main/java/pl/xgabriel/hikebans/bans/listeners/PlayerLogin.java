package pl.xgabriel.bans.listeners;

import java.io.File;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import pl.xgabriel.bans.Main;
import pl.xgabriel.bans.managers.FilesManager;
import pl.xgabriel.bans.managers.Utils;

public class PlayerLogin implements Listener {
   public PlayerLogin(Main Main) {
      Bukkit.getPluginManager().registerEvents(this, Main);
   }

   @EventHandler
   public void onLogin(PlayerLoginEvent e) {
      Player p = e.getPlayer();
      File file = new File("plugins/HikeBans/Bany/" + p.getName() + ".yml");
      if (file.exists()) {
         YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
         List list;
         String msg;
         int i;
         if (yml.getString("Ban").equals("PERM")) {
            list = FilesManager.ymlconfig.getStringList("Login.PERM");
            msg = "";

            for(i = 0; i < list.size(); ++i) {
               msg = msg + (String)list.get(i);
               if (i <= list.size() - 2) {
                  msg = msg + "@n";
               }
            }

            msg = msg.replace("&", "§");
            msg = msg.replace("@n", "\n");
            msg = msg.replace("@powod", Main.getBan(p.getName()).split(";")[1]);
            msg = msg.replace("@admin", Main.getBan(p.getName()).split(";")[2]);
            e.disallow(Result.KICK_OTHER, msg);
         } else if (yml.getString("Ban").equals("TEMP")) {
            if (System.currentTimeMillis() > Long.valueOf(Main.getBan(p.getName()).split(";")[3])) {
               Main.unBan(p.getName());
            } else {
               list = FilesManager.ymlconfig.getStringList("Login.TEMP");
               msg = "";

               for(i = 0; i < list.size(); ++i) {
                  msg = msg + (String)list.get(i);
                  if (i <= list.size() - 2) {
                     msg = msg + "@n";
                  }
               }

               msg = msg.replace("&", "§");
               msg = msg.replace("@n", "\n");
               msg = msg.replace("@powod", Main.getBan(p.getName()).split(";")[1]);
               msg = msg.replace("@admin", Main.getBan(p.getName()).split(";")[2]);
               msg = msg.replace("@czas", Utils.getDate(Long.valueOf(Main.getBan(p.getName()).split(";")[3])));
               e.disallow(Result.KICK_OTHER, msg);
            }
         }
      }

   }
}
