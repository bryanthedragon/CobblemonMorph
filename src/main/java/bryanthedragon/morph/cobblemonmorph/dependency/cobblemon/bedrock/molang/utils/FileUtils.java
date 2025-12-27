package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class FileUtils {
   public static String readFile(InputStream inputStream) throws IOException {
      return readFile(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
   }

   private static String readFile(Reader reader) throws IOException {
      String var4;
      try (BufferedReader br = new BufferedReader(reader)) {
         String temp = br.readLine();

         StringBuilder stringBuilder;
         for (stringBuilder = new StringBuilder(); temp != null; temp = br.readLine()) {
            if (stringBuilder.length() != 0) {
               stringBuilder.append("\n");
            }

            stringBuilder.append(temp);
         }

         var4 = stringBuilder.toString();
      }

      return var4;
   }
}
