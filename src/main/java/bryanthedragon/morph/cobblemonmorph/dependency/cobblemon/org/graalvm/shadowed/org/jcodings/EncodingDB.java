package org.graalvm.shadowed.org.jcodings;

import org.graalvm.shadowed.org.jcodings.exception.InternalException;
import org.graalvm.shadowed.org.jcodings.util.CaseInsensitiveBytesHash;

public final class EncodingDB {
   static EncodingDB.Entry ascii;
   static final CaseInsensitiveBytesHash<EncodingDB.Entry> encodings = new CaseInsensitiveBytesHash<>(50);
   static final CaseInsensitiveBytesHash<EncodingDB.Entry> aliases = new CaseInsensitiveBytesHash<>(150);

   public static final CaseInsensitiveBytesHash<EncodingDB.Entry> getEncodings() {
      return encodings;
   }

   public static final CaseInsensitiveBytesHash<EncodingDB.Entry> getAliases() {
      return aliases;
   }

   public static void declare(String name, String encodingClass) {
      byte[] bytes = name.getBytes();
      if (encodings.get(bytes) != null) {
         throw new InternalException("encoding already registerd <%n>", name);
      } else {
         encodings.putDirect(bytes, new EncodingDB.Entry(encodingClass));
      }
   }

   public static void alias(String alias, String original) {
      byte[] origBytes = original.getBytes();
      EncodingDB.Entry originalEntry = encodings.get(origBytes);
      if (originalEntry == null) {
         throw new InternalException("no such encoding <%n>", original);
      } else {
         byte[] aliasBytes = alias.getBytes();
         if (aliases.get(aliasBytes) != null) {
            throw new InternalException("encoding alias already registerd <%n>", alias);
         } else {
            aliases.putDirect(aliasBytes, originalEntry);
         }
      }
   }

   public static void replicate(String replica, String original) {
      byte[] origBytes = original.getBytes();
      EncodingDB.Entry originalEntry = encodings.get(origBytes);
      if (originalEntry == null) {
         throw new InternalException("no such encoding <%n>", original);
      } else {
         finishReplica(replica, originalEntry.isDummy, originalEntry);
      }
   }

   private static void replicate(String replica, String original, boolean dummy) {
      byte[] origBytes = original.getBytes();
      EncodingDB.Entry originalEntry = encodings.get(origBytes);
      if (originalEntry == null) {
         throw new InternalException("no such encoding <%n>", original);
      } else {
         finishReplica(replica, dummy, originalEntry);
      }
   }

   private static void finishReplica(String replica, boolean dummy, EncodingDB.Entry originalEntry) {
      byte[] replicaBytes = replica.getBytes();
      if (encodings.get(replicaBytes) != null) {
         throw new InternalException("encoding replica already registerd <%n>", replica);
      } else {
         encodings.putDirect(replicaBytes, new EncodingDB.Entry(replicaBytes, originalEntry, dummy));
      }
   }

   public static void set_base(String name, String original) {
   }

   public static EncodingDB.Entry dummy(byte[] bytes) {
      if (encodings.get(bytes) != null) {
         throw new InternalException("encoding already registerd <%n>", new String(bytes));
      } else {
         EncodingDB.Entry entry = new EncodingDB.Entry(bytes);
         encodings.putDirect(bytes, entry);
         return entry;
      }
   }

   public static void dummy(String name) {
      dummy(name.getBytes());
   }

   public static void dummy_unicode(String replica) {
      replicate(replica, replica + "BE", true);
   }

   static {
      EncodingList.load();
   }

   public static final class Entry {
      private static int count;
      private final EncodingDB.Entry base;
      private Encoding encoding;
      private final String encodingClass;
      private final int index;
      private final boolean isDummy;
      private final byte[] name;

      private Entry(byte[] name, String encodingClass, EncodingDB.Entry base, boolean isDummy) {
         this.name = name;
         this.encodingClass = encodingClass;
         this.base = base;
         this.isDummy = isDummy;
         this.index = count++;
      }

      Entry(String encodingClass) {
         this(null, encodingClass, null, false);
      }

      Entry(byte[] name, EncodingDB.Entry base) {
         this(name, base.encodingClass, base, false);
      }

      Entry(byte[] name) {
         this(name, EncodingDB.ascii.encodingClass, EncodingDB.ascii, true);
      }

      Entry(byte[] name, EncodingDB.Entry base, boolean dummy) {
         this(name, base.encodingClass, base, dummy);
      }

      @Override
      public int hashCode() {
         return this.encodingClass.hashCode();
      }

      public EncodingDB.Entry getBase() {
         return this.base;
      }

      public Encoding getEncoding() {
         if (this.encoding == null) {
            if (this.name == null) {
               this.encoding = EncodingList.getInstance(this.encodingClass);
            } else {
               this.encoding = EncodingList.getInstance(this.encodingClass).replicate(this.name);
               if (this.isDummy) {
                  this.encoding.setDummy();
               }
            }
         }

         return this.encoding;
      }

      public String getEncodingClass() {
         return this.encodingClass;
      }

      public int getIndex() {
         return this.index;
      }

      public boolean isDummy() {
         return this.isDummy;
      }
   }
}
