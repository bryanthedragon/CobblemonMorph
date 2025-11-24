
package org.graalvm.shadowed.org.jcodings.transcode;

import java.util.Arrays;
import org.graalvm.shadowed.org.jcodings.ObjPtr;
import org.graalvm.shadowed.org.jcodings.exception.TranscoderException;
import org.graalvm.shadowed.org.jcodings.transcode.EConv;
import org.graalvm.shadowed.org.jcodings.transcode.EConvFlags;
import org.graalvm.shadowed.org.jcodings.transcode.Transcoder;
import org.graalvm.shadowed.org.jcodings.transcode.TranscoderList;
import org.graalvm.shadowed.org.jcodings.util.CaseInsensitiveBytesHash;
import org.graalvm.shadowed.org.jcodings.util.Hash;

public class TranscoderDB
implements EConvFlags {
    public static final CaseInsensitiveBytesHash<CaseInsensitiveBytesHash<Entry>> transcoders = new CaseInsensitiveBytesHash();

    static Entry makeEntry(byte[] source, byte[] destination) {
        Entry entry;
        CaseInsensitiveBytesHash<Entry> sHash = transcoders.get(source);
        if (sHash == null) {
            sHash = new CaseInsensitiveBytesHash();
            transcoders.putDirect(source, sHash);
        }
        if ((entry = sHash.get(destination)) != null) {
            throw new TranscoderException("transcoder from <%n> has been already registered", new String(source + " to " + new String(destination)));
        }
        entry = new Entry(source, destination);
        sHash.putDirect(destination, entry);
        return entry;
    }

    public static Entry getEntry(byte[] source, byte[] destination) {
        CaseInsensitiveBytesHash<Entry> sHash = transcoders.get(source);
        return sHash == null ? null : sHash.get(destination);
    }

    static void register(Transcoder transcoder) {
        Entry entry = TranscoderDB.makeEntry(transcoder.source, transcoder.destination);
        if (entry.transcoder != null) {
            throw new TranscoderException("transcoder from <%n> has been already registered", new String(transcoder.source + " to " + new String(transcoder.destination)));
        }
        entry.transcoder = transcoder;
    }

    static void declare(String source, String destination, String transcoderClass) {
        Entry entry = TranscoderDB.makeEntry(source.getBytes(), destination.getBytes());
        entry.transcoderClass = transcoderClass;
    }

    public static int searchPath(byte[] source, byte[] destination, SearchPathCallback callback) {
        if (CaseInsensitiveBytesHash.caseInsensitiveEquals(source, destination)) {
            return -1;
        }
        ObjPtr bfsQueue = new ObjPtr();
        SearchPathQueue queue = new SearchPathQueue();
        queue.encoding = source;
        ObjPtr<SearchPathQueue> bfsLastQueue = queue.next;
        bfsQueue.p = queue;
        CaseInsensitiveBytesHash<byte[]> bfsVisited = new CaseInsensitiveBytesHash<byte[]>();
        bfsVisited.put(source, EConv.NULL_STRING);
        while (bfsQueue.p != null) {
            CaseInsensitiveBytesHash<Entry> table2;
            queue = (SearchPathQueue)bfsQueue.p;
            bfsQueue.p = queue.next.p;
            if (bfsQueue.p == null) {
                bfsLastQueue = bfsQueue;
            }
            if ((table2 = transcoders.get(queue.encoding)) == null) continue;
            Entry entry = table2.get(destination);
            if (entry != null) {
                byte[] tmp;
                bfsVisited.put(destination, queue.encoding);
                byte[] enc = destination;
                int pathLength = 0;
                while ((tmp = (byte[])bfsVisited.get(enc)) != EConv.NULL_STRING) {
                    ++pathLength;
                    enc = tmp;
                }
                int depth = pathLength;
                enc = destination;
                while ((tmp = (byte[])bfsVisited.get(enc)) != EConv.NULL_STRING) {
                    callback.call(tmp, enc, --depth);
                    enc = tmp;
                }
                return pathLength;
            }
            byte[] bfsBaseEnc = queue.encoding;
            for (Hash.HashEntry o : table2.entryIterator()) {
                CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry e = (CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry)o;
                byte[] dname = e.bytes;
                if (bfsVisited.get(dname) != null) continue;
                SearchPathQueue q = new SearchPathQueue();
                q.encoding = dname;
                q.next.p = null;
                bfsLastQueue.p = q;
                bfsLastQueue = q.next;
                bfsVisited.putDirect(dname, bfsBaseEnc);
            }
            bfsBaseEnc = null;
        }
        return -1;
    }

    public static EConv alloc(int n) {
        return new EConv(n);
    }

    private static EConv openByTranscoderEntries(int n, Entry[] entries) {
        EConv econv = new EConv(n);
        for (int i = 0; i < n; ++i) {
            Transcoder transcoder = entries[i].getTranscoder();
            econv.addTranscoderAt(transcoder, econv.numTranscoders);
        }
        return econv;
    }

    private static EConv open0(byte[] source, byte[] destination, int ecflags) {
        Entry[] entries;
        int numTrans;
        if (source.length == 0 && destination.length == 0) {
            numTrans = 0;
            entries = null;
        } else {
            final ObjPtr lentries = new ObjPtr();
            numTrans = TranscoderDB.searchPath(source, destination, new SearchPathCallback(){
                int additional = 0;

                @Override
                public void call(byte[] source, byte[] destination, int depth) {
                    if (lentries.p == null) {
                        lentries.p = new Entry[depth + 1 + this.additional];
                    }
                    ((Entry[])lentries.p)[depth] = TranscoderDB.getEntry(source, destination);
                }
            });
            entries = (Entry[])lentries.p;
            if (numTrans < 0) {
                return null;
            }
        }
        EConv ec = TranscoderDB.openByTranscoderEntries(numTrans, entries);
        if (ec == null) {
            return null;
        }
        ec.flags = ecflags;
        ec.source = source;
        ec.destination = destination;
        return ec;
    }

    public static int decoratorNames(int ecflags, byte[][] decorators) {
        switch (ecflags & 0x3F00) {
            case 0: 
            case 256: 
            case 4096: 
            case 8192: {
                break;
            }
            default: {
                return -1;
            }
        }
        if ((ecflags & 0x4000) != 0 && (ecflags & 0x8000) != 0) {
            return -1;
        }
        int numDecorators = 0;
        if ((ecflags & 0x4000) != 0) {
            decorators[numDecorators++] = "xml_text_escape".getBytes();
        }
        if ((ecflags & 0x8000) != 0) {
            decorators[numDecorators++] = "xml_attr_content_escape".getBytes();
        }
        if ((ecflags & 0x100000) != 0) {
            decorators[numDecorators++] = "xml_attr_quote".getBytes();
        }
        if ((ecflags & 0x1000) != 0) {
            decorators[numDecorators++] = "crlf_newline".getBytes();
        }
        if ((ecflags & 0x2000) != 0) {
            decorators[numDecorators++] = "cr_newline".getBytes();
        }
        if ((ecflags & 0x100) != 0) {
            decorators[numDecorators++] = "universal_newline".getBytes();
        }
        return numDecorators;
    }

    public static EConv open(String source, String destination, int ecflags) {
        return TranscoderDB.open(source.getBytes(), destination.getBytes(), ecflags);
    }

    public static EConv open(byte[] source, byte[] destination, int ecflags) {
        byte[][] decorators = new byte[32][];
        int numDecorators = TranscoderDB.decoratorNames(ecflags, decorators);
        if (numDecorators == -1) {
            return null;
        }
        EConv ec = TranscoderDB.open0(source, destination, ecflags & 0xFF);
        if (ec == null) {
            return null;
        }
        for (int i = 0; i < numDecorators; ++i) {
            if (ec.decorateAtLast(decorators[i])) continue;
            ec.close();
            return null;
        }
        ec.flags |= ecflags & 0xFFFFFF00;
        return ec;
    }

    static byte[] asciiCompatibleEncoding(byte[] asciiCompatName) {
        CaseInsensitiveBytesHash<Entry> dTable = transcoders.get(asciiCompatName);
        if (dTable == null || dTable.size() != 1) {
            return null;
        }
        byte[] asciiCN = null;
        for (Entry e : dTable) {
            Transcoder transcoder;
            if (EConv.decorator(e.source, e.destination) || (transcoder = e.getTranscoder()) == null || !transcoder.compatibility.isDecoder()) continue;
            asciiCN = transcoder.destination;
            break;
        }
        return asciiCN;
    }

    static {
        TranscoderList.load();
    }

    public static final class Entry {
        private String transcoderClass;
        private final byte[] source;
        private final byte[] destination;
        private Transcoder transcoder;

        private Entry(byte[] source, byte[] destination) {
            this.source = source;
            this.destination = destination;
        }

        public byte[] getSource() {
            return this.source;
        }

        public byte[] getDestination() {
            return this.destination;
        }

        public Transcoder getTranscoder() {
            if (this.transcoder == null) {
                if (this.transcoderClass != null) {
                    this.transcoder = TranscoderList.getInstance(this.transcoderClass);
                } else {
                    Transcoder.GenericTranscoderEntry[] list = TranscoderList.GENERIC_LIST;
                    for (int i = 0; i < list.length; ++i) {
                        Transcoder.GenericTranscoderEntry entry = list[i];
                        if (!Arrays.equals(this.source, entry.source) || !Arrays.equals(this.destination, entry.destination)) continue;
                        this.transcoder = entry.createTranscoder();
                        break;
                    }
                }
            }
            return this.transcoder;
        }
    }

    static final class SearchPathQueue {
        ObjPtr<SearchPathQueue> next = new ObjPtr();
        byte[] encoding;

        SearchPathQueue() {
        }
    }

    public static interface SearchPathCallback {
        public void call(byte[] var1, byte[] var2, int var3);
    }
}

