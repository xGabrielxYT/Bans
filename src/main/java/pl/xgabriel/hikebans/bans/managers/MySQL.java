package pl.xgabriel.bans.managers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {
   public static String url;
   public static String database;
   public static String user;
   public static String password;

   static {
      url = "jdbc:mysql://" + FilesManager.ymlconfig.getString("MySQL.Host") + ":" + FilesManager.ymlconfig.getInt("MySQL.Port") + "/";
      database = FilesManager.ymlconfig.getString("MySQL.Database");
      user = FilesManager.ymlconfig.getString("MySQL.User");
      password = FilesManager.ymlconfig.getString("MySQL.Password");
   }

   public MySQL() {
      if (FilesManager.ymlconfig.getBoolean("MySQL.Enable")) {
         createTable();
      }

   }

   public static void createTable() {
      try {
         Connection con = DriverManager.getConnection(url + database, user, password);
         Statement st = con.createStatement();

         try {
            st.execute("CREATE TABLE IF NOT EXISTS hikeBans(nick CHAR(16), powod VARCHAR(100), czas CHAR(23), admin CHAR(16))");
         } catch (SQLException var3) {
            System.out.print(" > Tabela nie zostala stworzona");
         }
      } catch (SQLException var4) {
         System.out.print(" > Nie udalo sie polaczyc z baza danych");
      }

   }

   public static void addRecord(String nick, String powod, String czas, String admin) {
      try {
         Connection con = DriverManager.getConnection(url + database, user, password);
         Statement st = con.createStatement();

         try {
            ResultSet rs = st.executeQuery("SELECT * FROM hikeBans WHERE nick='" + nick + "'");
            if (rs.next()) {
               st.execute("UPDATE hikeBans SET powod='" + powod + "', czas='" + czas + "', admin='" + admin + "' WHERE nick='" + nick + "'");
            } else {
               st.execute("INSERT INTO hikeBans VALUES('" + nick + "', '" + powod + "', '" + czas + "', '" + admin + "')");
            }
         } catch (SQLException var7) {
            var7.printStackTrace();
            System.out.print(" > Nie mozna dodac rekordu");
         }
      } catch (SQLException var8) {
         System.out.print(" > Nie udalo sie polaczyc z baza danych");
      }

   }

   public static void removeRecord(String nick) {
      try {
         Connection con = DriverManager.getConnection(url + database, user, password);
         Statement st = con.createStatement();

         try {
            st.execute("DELETE FROM hikeBans WHERE nick='" + nick + "'");
         } catch (SQLException var4) {
            var4.printStackTrace();
            System.out.print(" > Nie mozna usunac rekordu");
         }
      } catch (SQLException var5) {
         System.out.print(" > Nie udalo sie polaczyc z baza danych");
      }

   }
}
