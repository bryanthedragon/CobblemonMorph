package com.cobblemon.mod.relocations.ibm.icu.impl;

import com.cobblemon.mod.relocations.ibm.icu.util.ICUUncheckedIOException;
import com.cobblemon.mod.relocations.ibm.icu.util.VersionInfo;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public final class ICUBinary {
   private static final List<ICUBinary.DataFile> icuDataFiles = new ArrayList<>();
   private static final byte MAGIC1 = -38;
   private static final byte MAGIC2 = 39;
   private static final byte CHAR_SET_ = 0;
   private static final byte CHAR_SIZE_ = 2;
   private static final String MAGIC_NUMBER_AUTHENTICATION_FAILED_ = "ICU data file error: Not an ICU data file";
   private static final String HEADER_AUTHENTICATION_FAILED_ = "ICU data file error: Header authentication failed, please check if you have a valid ICU data file";

   private static void addDataFilesFromPath(String dataPath, List<ICUBinary.DataFile> files) {
      int pathStart = 0;

      while (pathStart < dataPath.length()) {
         int sepIndex = dataPath.indexOf(File.pathSeparatorChar, pathStart);
         int pathLimit;
         if (sepIndex >= 0) {
            pathLimit = sepIndex;
         } else {
            pathLimit = dataPath.length();
         }

         String path = dataPath.substring(pathStart, pathLimit).trim();
         if (path.endsWith(File.separator)) {
            path = path.substring(0, path.length() - 1);
         }

         if (path.length() != 0) {
            addDataFilesFromFolder(new File(path), new StringBuilder(), icuDataFiles);
         }

         if (sepIndex < 0) {
            break;
         }

         pathStart = sepIndex + 1;
      }
   }

   private static void addDataFilesFromFolder(File folder, StringBuilder itemPath, List<ICUBinary.DataFile> dataFiles) {
      File[] files = folder.listFiles();
      if (files != null && files.length != 0) {
         int folderPathLength = itemPath.length();
         if (folderPathLength > 0) {
            itemPath.append('/');
            folderPathLength++;
         }

         for (File file : files) {
            String fileName = file.getName();
            if (!fileName.endsWith(".txt")) {
               itemPath.append(fileName);
               if (file.isDirectory()) {
                  addDataFilesFromFolder(file, itemPath, dataFiles);
               } else if (fileName.endsWith(".dat")) {
                  ByteBuffer pkgBytes = mapFile(file);
                  if (pkgBytes != null && ICUBinary.DatPackageReader.validate(pkgBytes)) {
                     dataFiles.add(new ICUBinary.PackageDataFile(itemPath.toString(), pkgBytes));
                  }
               } else {
                  dataFiles.add(new ICUBinary.SingleDataFile(itemPath.toString(), file));
               }

               itemPath.setLength(folderPathLength);
            }
         }
      }
   }

   static int compareKeys(CharSequence key, ByteBuffer bytes, int offset) {
      int i = 0;

      while (true) {
         int c2 = bytes.get(offset);
         if (c2 == 0) {
            if (i == key.length()) {
               return 0;
            }

            return 1;
         }

         if (i == key.length()) {
            return -1;
         }

         int diff = key.charAt(i) - c2;
         if (diff != 0) {
            return diff;
         }

         i++;
         offset++;
      }
   }

   static int compareKeys(CharSequence key, byte[] bytes, int offset) {
      int i = 0;

      while (true) {
         int c2 = bytes[offset];
         if (c2 == 0) {
            if (i == key.length()) {
               return 0;
            }

            return 1;
         }

         if (i == key.length()) {
            return -1;
         }

         int diff = key.charAt(i) - c2;
         if (diff != 0) {
            return diff;
         }

         i++;
         offset++;
      }
   }

   public static ByteBuffer getData(String itemPath) {
      return getData(null, null, itemPath, false);
   }

   public static ByteBuffer getData(ClassLoader loader, String resourceName, String itemPath) {
      return getData(loader, resourceName, itemPath, false);
   }

   public static ByteBuffer getRequiredData(String itemPath) {
      return getData(null, null, itemPath, true);
   }

   private static ByteBuffer getData(ClassLoader loader, String resourceName, String itemPath, boolean required) {
      ByteBuffer bytes = getDataFromFile(itemPath);
      if (bytes != null) {
         return bytes;
      } else {
         if (loader == null) {
            loader = ClassLoaderUtil.getClassLoader(ICUData.class);
         }

         if (resourceName == null) {
            resourceName = "com/cobblemon/mod/relocations/ibm/icu/impl/data/icudt71b/" + itemPath;
         }

         ByteBuffer buffer = null;

         try {
            InputStream is = ICUData.getStream(loader, resourceName, required);
            return is == null ? null : getByteBufferFromInputStreamAndCloseStream(is);
         } catch (IOException var7) {
            throw new ICUUncheckedIOException(var7);
         }
      }
   }

   private static ByteBuffer getDataFromFile(String itemPath) {
      for (ICUBinary.DataFile dataFile : icuDataFiles) {
         ByteBuffer data = dataFile.getData(itemPath);
         if (data != null) {
            return data;
         }
      }

      return null;
   }

   private static ByteBuffer mapFile(File path) {
      try {
         FileInputStream file = new FileInputStream(path);
         FileChannel channel = file.getChannel();
         ByteBuffer bytes = null;

         try {
            bytes = channel.map(MapMode.READ_ONLY, 0L, channel.size());
         } finally {
            file.close();
         }

         return bytes;
      } catch (FileNotFoundException var9) {
         System.err.println(var9);
      } catch (IOException var10) {
         System.err.println(var10);
      }

      return null;
   }

   public static void addBaseNamesInFileFolder(String folder, String suffix, Set<String> names) {
      for (ICUBinary.DataFile dataFile : icuDataFiles) {
         dataFile.addBaseNamesInFolder(folder, suffix, names);
      }
   }

   public static VersionInfo readHeaderAndDataVersion(ByteBuffer bytes, int dataFormat, ICUBinary.Authenticate authenticate) throws IOException {
      return getVersionInfoFromCompactInt(readHeader(bytes, dataFormat, authenticate));
   }

   public static int readHeader(ByteBuffer bytes, int dataFormat, ICUBinary.Authenticate authenticate) throws IOException {
      assert bytes != null && bytes.position() == 0;

      byte magic1 = bytes.get(2);
      byte magic2 = bytes.get(3);
      if (magic1 == -38 && magic2 == 39) {
         byte isBigEndian = bytes.get(8);
         byte charsetFamily = bytes.get(9);
         byte sizeofUChar = bytes.get(10);
         if (isBigEndian >= 0 && 1 >= isBigEndian && charsetFamily == 0 && sizeofUChar == 2) {
            bytes.order(isBigEndian != 0 ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN);
            int headerSize = bytes.getChar(0);
            int sizeofUDataInfo = bytes.getChar(4);
            if (sizeofUDataInfo >= 20 && headerSize >= sizeofUDataInfo + 4) {
               byte[] formatVersion = new byte[]{bytes.get(16), bytes.get(17), bytes.get(18), bytes.get(19)};
               if (bytes.get(12) == (byte)(dataFormat >> 24)
                  && bytes.get(13) == (byte)(dataFormat >> 16)
                  && bytes.get(14) == (byte)(dataFormat >> 8)
                  && bytes.get(15) == (byte)dataFormat
                  && (authenticate == null || authenticate.isDataVersionAcceptable(formatVersion))) {
                  ((Buffer)bytes).position(headerSize);
                  return bytes.get(20) << 24 | (bytes.get(21) & 0xFF) << 16 | (bytes.get(22) & 0xFF) << 8 | bytes.get(23) & 0xFF;
               } else {
                  throw new IOException(
                     "ICU data file error: Header authentication failed, please check if you have a valid ICU data file"
                        + String.format(
                           "; data format %02x%02x%02x%02x, format version %d.%d.%d.%d",
                           bytes.get(12),
                           bytes.get(13),
                           bytes.get(14),
                           bytes.get(15),
                           formatVersion[0] & 0xFF,
                           formatVersion[1] & 0xFF,
                           formatVersion[2] & 0xFF,
                           formatVersion[3] & 0xFF
                        )
                  );
               }
            } else {
               throw new IOException("Internal Error: Header size error");
            }
         } else {
            throw new IOException("ICU data file error: Header authentication failed, please check if you have a valid ICU data file");
         }
      } else {
         throw new IOException("ICU data file error: Not an ICU data file");
      }
   }

   public static int writeHeader(int dataFormat, int formatVersion, int dataVersion, DataOutputStream dos) throws IOException {
      dos.writeChar(32);
      dos.writeByte(-38);
      dos.writeByte(39);
      dos.writeChar(20);
      dos.writeChar(0);
      dos.writeByte(1);
      dos.writeByte(0);
      dos.writeByte(2);
      dos.writeByte(0);
      dos.writeInt(dataFormat);
      dos.writeInt(formatVersion);
      dos.writeInt(dataVersion);
      dos.writeLong(0L);

      assert dos.size() == 32;

      return 32;
   }

   public static void skipBytes(ByteBuffer bytes, int skipLength) {
      if (skipLength > 0) {
         ((Buffer)bytes).position(bytes.position() + skipLength);
      }
   }

   public static byte[] getBytes(ByteBuffer bytes, int length, int additionalSkipLength) {
      byte[] dest = new byte[length];
      bytes.get(dest);
      if (additionalSkipLength > 0) {
         skipBytes(bytes, additionalSkipLength);
      }

      return dest;
   }

   public static String getString(ByteBuffer bytes, int length, int additionalSkipLength) {
      CharSequence cs = bytes.asCharBuffer();
      String s = cs.subSequence(0, length).toString();
      skipBytes(bytes, length * 2 + additionalSkipLength);
      return s;
   }

   public static char[] getChars(ByteBuffer bytes, int length, int additionalSkipLength) {
      char[] dest = new char[length];
      bytes.asCharBuffer().get(dest);
      skipBytes(bytes, length * 2 + additionalSkipLength);
      return dest;
   }

   public static short[] getShorts(ByteBuffer bytes, int length, int additionalSkipLength) {
      short[] dest = new short[length];
      bytes.asShortBuffer().get(dest);
      skipBytes(bytes, length * 2 + additionalSkipLength);
      return dest;
   }

   public static int[] getInts(ByteBuffer bytes, int length, int additionalSkipLength) {
      int[] dest = new int[length];
      bytes.asIntBuffer().get(dest);
      skipBytes(bytes, length * 4 + additionalSkipLength);
      return dest;
   }

   public static long[] getLongs(ByteBuffer bytes, int length, int additionalSkipLength) {
      long[] dest = new long[length];
      bytes.asLongBuffer().get(dest);
      skipBytes(bytes, length * 8 + additionalSkipLength);
      return dest;
   }

   public static ByteBuffer sliceWithOrder(ByteBuffer bytes) {
      ByteBuffer b = bytes.slice();
      return b.order(bytes.order());
   }

   public static ByteBuffer getByteBufferFromInputStreamAndCloseStream(InputStream is) throws IOException {
      ByteBuffer var10;
      try {
         int avail = is.available();
         byte[] bytes;
         if (avail > 32) {
            bytes = new byte[avail];
         } else {
            bytes = new byte[128];
         }

         int length = 0;

         while (true) {
            if (length < bytes.length) {
               int numRead = is.read(bytes, length, bytes.length - length);
               if (numRead < 0) {
                  break;
               }

               length += numRead;
            } else {
               int nextByte = is.read();
               if (nextByte < 0) {
                  break;
               }

               int capacity = 2 * bytes.length;
               if (capacity < 128) {
                  capacity = 128;
               } else if (capacity < 16384) {
                  capacity *= 2;
               }

               bytes = Arrays.copyOf(bytes, capacity);
               bytes[length++] = (byte)nextByte;
            }
         }

         var10 = ByteBuffer.wrap(bytes, 0, length);
      } finally {
         is.close();
      }

      return var10;
   }

   public static VersionInfo getVersionInfoFromCompactInt(int version) {
      return VersionInfo.getInstance(version >>> 24, version >> 16 & 0xFF, version >> 8 & 0xFF, version & 0xFF);
   }

   public static byte[] getVersionByteArrayFromCompactInt(int version) {
      return new byte[]{(byte)(version >> 24), (byte)(version >> 16), (byte)(version >> 8), (byte)version};
   }

   static {
      String dataPath = ICUConfig.get(ICUBinary.class.getName() + ".dataPath");
      if (dataPath != null) {
         addDataFilesFromPath(dataPath, icuDataFiles);
      }
   }

   public interface Authenticate {
      boolean isDataVersionAcceptable(byte[] var1);
   }

   private static final class DatPackageReader {
      private static final int DATA_FORMAT = 1131245124;
      private static final ICUBinary.DatPackageReader.IsAcceptable IS_ACCEPTABLE = new ICUBinary.DatPackageReader.IsAcceptable();

      static boolean validate(ByteBuffer bytes) {
         try {
            ICUBinary.readHeader(bytes, 1131245124, IS_ACCEPTABLE);
         } catch (IOException var2) {
            return false;
         }

         int count = bytes.getInt(bytes.position());
         if (count <= 0) {
            return false;
         } else {
            return bytes.position() + 4 + count * 24 > bytes.capacity()
               ? false
               : startsWithPackageName(bytes, getNameOffset(bytes, 0)) && startsWithPackageName(bytes, getNameOffset(bytes, count - 1));
         }
      }

      private static boolean startsWithPackageName(ByteBuffer bytes, int start) {
         int length = "icudt71b".length() - 1;

         for (int i = 0; i < length; i++) {
            if (bytes.get(start + i) != "icudt71b".charAt(i)) {
               return false;
            }
         }

         byte c = bytes.get(start + length++);
         return (c == 98 || c == 108) && bytes.get(start + length) == 47;
      }

      static ByteBuffer getData(ByteBuffer bytes, CharSequence key) {
         int index = binarySearch(bytes, key);
         if (index >= 0) {
            ByteBuffer data = bytes.duplicate();
            ((Buffer)data).position(getDataOffset(bytes, index));
            ((Buffer)data).limit(getDataOffset(bytes, index + 1));
            return ICUBinary.sliceWithOrder(data);
         } else {
            return null;
         }
      }

      static void addBaseNamesInFolder(ByteBuffer bytes, String folder, String suffix, Set<String> names) {
         int index = binarySearch(bytes, folder);
         if (index < 0) {
            index = ~index;
         }

         int base = bytes.position();
         int count = bytes.getInt(base);
         StringBuilder sb = new StringBuilder();

         while (index < count && addBaseName(bytes, index, folder, suffix, sb, names)) {
            index++;
         }
      }

      private static int binarySearch(ByteBuffer bytes, CharSequence key) {
         int base = bytes.position();
         int count = bytes.getInt(base);
         int start = 0;
         int limit = count;

         while (start < limit) {
            int mid = start + limit >>> 1;
            int nameOffset = getNameOffset(bytes, mid);
            nameOffset += "icudt71b".length() + 1;
            int result = ICUBinary.compareKeys(key, bytes, nameOffset);
            if (result < 0) {
               limit = mid;
            } else {
               if (result <= 0) {
                  return mid;
               }

               start = mid + 1;
            }
         }

         return ~start;
      }

      private static int getNameOffset(ByteBuffer bytes, int index) {
         int base = bytes.position();

         assert 0 <= index && index < bytes.getInt(base);

         return base + bytes.getInt(base + 4 + index * 8);
      }

      private static int getDataOffset(ByteBuffer bytes, int index) {
         int base = bytes.position();
         int count = bytes.getInt(base);
         if (index == count) {
            return bytes.capacity();
         } else {
            assert 0 <= index && index < count;

            return base + bytes.getInt(base + 4 + 4 + index * 8);
         }
      }

      static boolean addBaseName(ByteBuffer bytes, int index, String folder, String suffix, StringBuilder sb, Set<String> names) {
         int offset = getNameOffset(bytes, index);
         offset += "icudt71b".length() + 1;
         if (folder.length() != 0) {
            for (int i = 0; i < folder.length(); offset++) {
               if (bytes.get(offset) != folder.charAt(i)) {
                  return false;
               }

               i++;
            }

            if (bytes.get(offset++) != 47) {
               return false;
            }
         }

         sb.setLength(0);

         byte b;
         while ((b = bytes.get(offset++)) != 0) {
            char c = (char)b;
            if (c == '/') {
               return true;
            }

            sb.append(c);
         }

         int nameLimit = sb.length() - suffix.length();
         if (sb.lastIndexOf(suffix, nameLimit) >= 0) {
            names.add(sb.substring(0, nameLimit));
         }

         return true;
      }

      private static final class IsAcceptable implements ICUBinary.Authenticate {
         private IsAcceptable() {
         }

         @Override
         public boolean isDataVersionAcceptable(byte[] version) {
            return version[0] == 1;
         }
      }
   }

   private abstract static class DataFile {
      protected final String itemPath;

      DataFile(String item) {
         this.itemPath = item;
      }

      @Override
      public String toString() {
         return this.itemPath;
      }

      abstract ByteBuffer getData(String var1);

      abstract void addBaseNamesInFolder(String var1, String var2, Set<String> var3);
   }

   private static final class PackageDataFile extends ICUBinary.DataFile {
      private final ByteBuffer pkgBytes;

      PackageDataFile(String item, ByteBuffer bytes) {
         super(item);
         this.pkgBytes = bytes;
      }

      @Override
      ByteBuffer getData(String requestedPath) {
         return ICUBinary.DatPackageReader.getData(this.pkgBytes, requestedPath);
      }

      @Override
      void addBaseNamesInFolder(String folder, String suffix, Set<String> names) {
         ICUBinary.DatPackageReader.addBaseNamesInFolder(this.pkgBytes, folder, suffix, names);
      }
   }

   private static final class SingleDataFile extends ICUBinary.DataFile {
      private final File path;

      SingleDataFile(String item, File path) {
         super(item);
         this.path = path;
      }

      @Override
      public String toString() {
         return this.path.toString();
      }

      @Override
      ByteBuffer getData(String requestedPath) {
         return requestedPath.equals(this.itemPath) ? ICUBinary.mapFile(this.path) : null;
      }

      @Override
      void addBaseNamesInFolder(String folder, String suffix, Set<String> names) {
         if (this.itemPath.length() > folder.length() + suffix.length()
            && this.itemPath.startsWith(folder)
            && this.itemPath.endsWith(suffix)
            && this.itemPath.charAt(folder.length()) == '/'
            && this.itemPath.indexOf(47, folder.length() + 1) < 0) {
            names.add(this.itemPath.substring(folder.length() + 1, this.itemPath.length() - suffix.length()));
         }
      }
   }
}
