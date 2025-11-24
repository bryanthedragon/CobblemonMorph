
package org.graalvm.shadowed.org.jcodings;

import org.graalvm.shadowed.org.jcodings.Encoding;
import org.graalvm.shadowed.org.jcodings.EncodingList;
import org.graalvm.shadowed.org.jcodings.exception.InternalException;
import org.graalvm.shadowed.org.jcodings.util.CaseInsensitiveBytesHash;

public final class EncodingDB {
    static Entry ascii;
    static final CaseInsensitiveBytesHash<Entry> encodings;
    static final CaseInsensitiveBytesHash<Entry> aliases;

    public static final CaseInsensitiveBytesHash<Entry> getEncodings() {
        return encodings;
    }

    public static final CaseInsensitiveBytesHash<Entry> getAliases() {
        return aliases;
    }

    public static void declare(String name, String encodingClass) {
        byte[] bytes = name.getBytes();
        if (encodings.get(bytes) != null) {
            throw new InternalException("encoding already registerd <%n>", name);
        }
        encodings.putDirect(bytes, new Entry(encodingClass));
    }

    public static void alias(String alias, String original) {
        byte[] origBytes = original.getBytes();
        Entry originalEntry = encodings.get(origBytes);
        if (originalEntry == null) {
            throw new InternalException("no such encoding <%n>", original);
        }
        byte[] aliasBytes = alias.getBytes();
        if (aliases.get(aliasBytes) != null) {
            throw new InternalException("encoding alias already registerd <%n>", alias);
        }
        aliases.putDirect(aliasBytes, originalEntry);
    }

    public static void replicate(String replica, String original) {
        byte[] origBytes = original.getBytes();
        Entry originalEntry = encodings.get(origBytes);
        if (originalEntry == null) {
            throw new InternalException("no such encoding <%n>", original);
        }
        EncodingDB.finishReplica(replica, originalEntry.isDummy, originalEntry);
    }

    private static void replicate(String replica, String original, boolean dummy2) {
        byte[] origBytes = original.getBytes();
        Entry originalEntry = encodings.get(origBytes);
        if (originalEntry == null) {
            throw new InternalException("no such encoding <%n>", original);
        }
        EncodingDB.finishReplica(replica, dummy2, originalEntry);
    }

    private static void finishReplica(String replica, boolean dummy2, Entry originalEntry) {
        byte[] replicaBytes = replica.getBytes();
        if (encodings.get(replicaBytes) != null) {
            throw new InternalException("encoding replica already registerd <%n>", replica);
        }
        encodings.putDirect(replicaBytes, new Entry(replicaBytes, originalEntry, dummy2));
    }

    public static void set_base(String name, String original) {
    }

    public static Entry dummy(byte[] bytes) {
        if (encodings.get(bytes) != null) {
            throw new InternalException("encoding already registerd <%n>", new String(bytes));
        }
        Entry entry = new Entry(bytes);
        encodings.putDirect(bytes, entry);
        return entry;
    }

    public static void dummy(String name) {
        EncodingDB.dummy(name.getBytes());
    }

    public static void dummy_unicode(String replica) {
        EncodingDB.replicate(replica, replica + "BE", true);
    }

    static {
        encodings = new CaseInsensitiveBytesHash(50);
        aliases = new CaseInsensitiveBytesHash(150);
        EncodingList.load();
    }

    public static final class Entry {
        private static int count;
        private final Entry base;
        private Encoding encoding;
        private final String encodingClass;
        private final int index;
        private final boolean isDummy;
        private final byte[] name;

        private Entry(byte[] name, String encodingClass, Entry base, boolean isDummy) {
            this.name = name;
            this.encodingClass = encodingClass;
            this.base = base;
            this.isDummy = isDummy;
            this.index = count++;
        }

        Entry(String encodingClass) {
            this(null, encodingClass, null, false);
        }

        Entry(byte[] name, Entry base) {
            this(name, base.encodingClass, base, false);
        }

        Entry(byte[] name) {
            this(name, EncodingDB.ascii.encodingClass, ascii, true);
        }

        Entry(byte[] name, Entry base, boolean dummy2) {
            this(name, base.encodingClass, base, dummy2);
        }

        public int hashCode() {
            return this.encodingClass.hashCode();
        }

        public Entry getBase() {
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

