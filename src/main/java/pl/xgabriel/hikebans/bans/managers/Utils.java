package pl.xgabriel.bans.managers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
   public static SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy - HH:mm:ss");

   public static String stringBuilder(String[] args, int liczOdArgumentu) {
      String msg = "";

      for(int i = liczOdArgumentu; i < args.length; ++i) {
         msg = msg + args[i];
         if (i <= args.length - 2) {
            msg = msg + " ";
         }
      }

      return msg;
   }

   public static long getTimeWithString(String s) {
      Pattern pattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[, \\s]*)?(?:([0-9]+)\\s*mo[a-z]*[, \\s]*)?(?:([0-9]+)\\s*d[a-z]*[, \\s]*)?(?:([0-9]+)\\s*h[a-z]*[, \\s]*)?(?:([0-9]+)\\s*m[a-z]*[, \\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2);
      Matcher matcher = pattern.matcher(s);
      long czas = 0L;
      boolean found = false;

      while(true) {
         do {
            do {
               if (!matcher.find()) {
                  if (!found) {
                     return -1L;
                  }

                  return czas * 1000L;
               }
            } while(matcher.group() == null);
         } while(matcher.group().isEmpty());

         for(int i = 0; i < matcher.groupCount(); ++i) {
            if (matcher.group(i) != null && !matcher.group(i).isEmpty()) {
               found = true;
               break;
            }
         }

         if (matcher.group(1) != null && !matcher.group(1).isEmpty()) {
            czas += (long)(31536000 * Integer.valueOf(matcher.group(1)));
         }

         if (matcher.group(2) != null && !matcher.group(2).isEmpty()) {
            czas += (long)(2592000 * Integer.valueOf(matcher.group(2)));
         }

         if (matcher.group(3) != null && !matcher.group(3).isEmpty()) {
            czas += (long)(86400 * Integer.valueOf(matcher.group(3)));
         }

         if (matcher.group(4) != null && !matcher.group(4).isEmpty()) {
            czas += (long)(3600 * Integer.valueOf(matcher.group(4)));
         }

         if (matcher.group(5) != null && !matcher.group(5).isEmpty()) {
            czas += (long)(60 * Integer.valueOf(matcher.group(5)));
         }

         if (matcher.group(6) != null && !matcher.group(6).isEmpty()) {
            czas += (long)Integer.valueOf(matcher.group(6));
         }
      }
   }

   public static String getDate(long czas) {
      return sdf.format(new Date(czas));
   }

   public static void copy(InputStream source, File file) {
      try {
         OutputStream out = new FileOutputStream(file);
         byte[] buf = new byte[1024];
         boolean len1 = false;

         int len;
         while((len = source.read(buf)) != -1) {
            out.write(buf, 0, len);
         }

         out.close();
         source.close();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   public static long getTimeWithArgs(String czas) {
      long sekundy = (long)(1000 * Integer.valueOf(czas.split(";")[0]));
      long minuty = (long)(1000 * Integer.valueOf(czas.split(";")[1]) * 60);
      long godziny = (long)(1000 * Integer.valueOf(czas.split(";")[2]) * 60 * 60);
      long dni = (long)(1000 * Integer.valueOf(czas.split(";")[3]) * 60 * 60 * 24);
      long miesiace = (long)(1000 * Integer.valueOf(czas.split(";")[4]) * 60 * 60 * 24 * 30);
      long lata = (long)(1000 * Integer.valueOf(czas.split(";")[5]) * 60 * 60 * 24 * 30 * 12);
      long wieki = (long)(1000 * Integer.valueOf(czas.split(";")[6]) * 60 * 60 * 24 * 30 * 12 * 100);
      long czasBana = System.currentTimeMillis() + sekundy + minuty + godziny + dni + miesiace + lata + wieki;
      return czasBana;
   }
}
