package org.graalvm.shadowed.org.jcodings.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.graalvm.shadowed.org.jcodings.exception.InternalException;

public class ArrayReader {
   public static DataInputStream openStream(String name) {
      String entry = "/org/graalvm/shadowed/org/jcodings/tables/" + name + ".bin";
      InputStream is = ArrayReader.class.getResourceAsStream(entry);
      if (is == null) {
         throw new InternalException("entry: " + entry + " not found");
      } else {
         return new DataInputStream(new BufferedInputStream(is));
      }
   }

   public static byte[] readByteArray(String name) {
      DataInputStream dis = openStream(name);

      try {
         int size = dis.readInt();
         byte[] bytes = new byte[size];

         for (int i = 0; i < size; i++) {
            bytes[i] = dis.readByte();
         }

         checkAvailable(dis, name);
         dis.close();
         return bytes;
      } catch (IOException var5) {
         decorate(var5, name);
         return null;
      }
   }

   public static int[] readIntArray(String name) {
      DataInputStream dis = openStream(name);

      try {
         int size = dis.readInt();
         int[] ints = new int[size];

         for (int i = 0; i < size; i++) {
            ints[i] = dis.readInt();
         }

         checkAvailable(dis, name);
         dis.close();
         return ints;
      } catch (IOException var5) {
         decorate(var5, name);
         return null;
      }
   }

   public static int[][] readNestedIntArray(String name) {
      DataInputStream dis = openStream(name);

      try {
         int size = dis.readInt();
         int[][] ints = new int[size][];

         for (int i = 0; i < size; i++) {
            int iSize = dis.readInt();
            int[] iints = new int[iSize];
            ints[i] = iints;

            for (int k = 0; k < iSize; k++) {
               iints[k] = dis.readInt();
            }
         }

         checkAvailable(dis, name);
         dis.close();
         return ints;
      } catch (IOException var8) {
         decorate(var8, name);
         return null;
      }
   }

   static void checkAvailable(DataInputStream dis, String name) throws IOException {
      if (dis.available() != 0) {
         throw new InternalException("length mismatch for table: " + name + " (" + dis.available() + " left)");
      }
   }

   static void decorate(IOException ioe, String name) {
      throw new InternalException("problem reading table: " + name + ": " + ioe);
   }
}
