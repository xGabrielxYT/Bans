package pl.xgabriel.bans;

import java.io.File;
import java.io.IOException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import pl.xgabriel.bans.commands.CmdBanPerm;
import pl.xgabriel.bans.commands.CmdBanTemp;
import pl.xgabriel.bans.commands.CmdUnBan;
import pl.xgabriel.bans.listeners.PlayerLogin;
import pl.xgabriel.bans.managers.FilesManager;
import pl.xgabriel.bans.managers.Utils;

public class Main extends JavaPlugin {
   public void onEnable() {
      new Utils();
      new FilesManager(this);
      new CmdBanPerm(this);
      new CmdBanTemp(this);
      new CmdUnBan(this);
      new PlayerLogin(this);
   }

   public static void ban(String nick, String powod, String admin) {
      File file = new File("plugins/HikeBans/Bany/" + nick + ".yml");
      YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
      yml.set("Ban", "PERM");
      yml.set("Powod", powod);
      yml.set("Admin", admin);

      try {
         yml.save(file);
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   public static void ban(String nick, String powod, String admin, long czas) {
      File file = new File("plugins/HikeBans/Bany/" + nick + ".yml");
      YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
      yml.set("Ban", "TEMP");
      yml.set("Powod", powod);
      yml.set("Admin", admin);
      yml.set("Czas", czas);

      try {
         yml.save(file);
      } catch (IOException var8) {
         var8.printStackTrace();
      }

   }

   public static void unBan(String zbanowany) {
      File file = new File("plugins/HikeBans/Bany/" + zbanowany + ".yml");
      file.delete();
   }

   public static String getBan(String nick) {
      File file = new File("plugins/HikeBans/Bany/" + nick + ".yml");
      if (file.exists()) {
         YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
         String msg = yml.getString("Ban") + ";" + yml.getString("Powod") + ";" + yml.getString("Admin") + ";" + yml.getLong("Czas");
         return msg;
      } else {
         return null;
      }
   }
}
