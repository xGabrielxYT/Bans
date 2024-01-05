package pl.xgabriel.bans.managers;

import java.io.File;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.xgabriel.bans.Main;

public class FilesManager {
   Main Main;
   public static File fileconfig = new File("plugins/HikeBans/config.yml");
   public static YamlConfiguration ymlconfig;

   public FilesManager(Main Main) {
      this.Main = Main;
      this.checkFiles();
   }

   public void checkFiles() {
      File dir = new File("plugins/HikeBans");
      if (!dir.exists()) {
         dir.mkdir();
      }

      File dirGracze = new File("plugins/HikeBans/Bany");
      if (!dirGracze.exists()) {
         dirGracze.mkdir();
      }

      if (!fileconfig.exists()) {
         Utils.copy(this.Main.getResource("config.yml"), fileconfig);
      }

      ymlconfig = YamlConfiguration.loadConfiguration(fileconfig);
   }
}
