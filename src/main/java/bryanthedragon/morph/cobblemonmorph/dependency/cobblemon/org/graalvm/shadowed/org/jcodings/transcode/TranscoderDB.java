package org.graalvm.shadowed.org.jcodings.transcode;

import java.util.Arrays;
import org.graalvm.shadowed.org.jcodings.ObjPtr;
import org.graalvm.shadowed.org.jcodings.exception.TranscoderException;
import org.graalvm.shadowed.org.jcodings.util.CaseInsensitiveBytesHash;
import org.graalvm.shadowed.org.jcodings.util.Hash;

public class TranscoderDB implements EConvFlags {
   public static final CaseInsensitiveBytesHash<CaseInsensitiveBytesHash<TranscoderDB.Entry>> transcoders = new CaseInsensitiveBytesHash<>();

   static TranscoderDB.Entry makeEntry(byte[] source, byte[] destination) {
      CaseInsensitiveBytesHash<TranscoderDB.Entry> sHash = transcoders.get(source);
      if (sHash == null) {
         sHash = new CaseInsensitiveBytesHash<>();
         transcoders.putDirect(source, sHash);
      }

      TranscoderDB.Entry entry = sHash.get(destination);
      if (entry == null) {
         entry = new TranscoderDB.Entry(source, destination);
         sHash.putDirect(destination, entry);
         return entry;
      } else {
         throw new TranscoderException("transcoder from <%n> has been already registered", new String(source + " to " + new String(destination)));
      }
   }

   public static TranscoderDB.Entry getEntry(byte[] source, byte[] destination) {
      CaseInsensitiveBytesHash<TranscoderDB.Entry> sHash = transcoders.get(source);
      return sHash == null ? null : sHash.get(destination);
   }

   static void register(Transcoder transcoder) {
      TranscoderDB.Entry entry = makeEntry(transcoder.source, transcoder.destination);
      if (entry.transcoder != null) {
         throw new TranscoderException(
            "transcoder from <%n> has been already registered", new String(transcoder.source + " to " + new String(transcoder.destination))
         );
      } else {
         entry.transcoder = transcoder;
      }
   }

   static void declare(String source, String destination, String transcoderClass) {
      TranscoderDB.Entry entry = makeEntry(source.getBytes(), destination.getBytes());
      entry.transcoderClass = transcoderClass;
   }

   public static int searchPath(byte[] source, byte[] destination, TranscoderDB.SearchPathCallback callback) {
      if (CaseInsensitiveBytesHash.caseInsensitiveEquals(source, destination)) {
         return -1;
      } else {
         ObjPtr<TranscoderDB.SearchPathQueue> bfsQueue = new ObjPtr<>();
         TranscoderDB.SearchPathQueue queue = new TranscoderDB.SearchPathQueue();
         queue.encoding = source;
         ObjPtr<TranscoderDB.SearchPathQueue> bfsLastQueue = queue.next;
         bfsQueue.p = queue;
         CaseInsensitiveBytesHash<byte[]> bfsVisited = new CaseInsensitiveBytesHash<>();
         bfsVisited.put(source, EConv.NULL_STRING);

         while (bfsQueue.p != null) {
            queue = bfsQueue.p;
            bfsQueue.p = queue.next.p;
            if (bfsQueue.p == null) {
               bfsLastQueue = bfsQueue;
            }

            CaseInsensitiveBytesHash<TranscoderDB.Entry> table2 = transcoders.get(queue.encoding);
            if (table2 != null) {
               TranscoderDB.Entry entry = table2.get(destination);
               if (entry != null) {
                  bfsVisited.put(destination, queue.encoding);
                  byte[] enc = destination;
                  int pathLength = 0;

                  while (true) {
                     byte[] tmp = bfsVisited.get(enc);
                     if (tmp == EConv.NULL_STRING) {
                        int depth = pathLength;
                        enc = destination;

                        while (true) {
                           tmp = bfsVisited.get(enc);
                           if (tmp == EConv.NULL_STRING) {
                              return pathLength;
                           }

                           callback.call(tmp, enc, --depth);
                           enc = tmp;
                        }
                     }

                     pathLength++;
                     enc = tmp;
                  }
               }

               byte[] bfsBaseEnc = queue.encoding;

               for (Hash.HashEntry<TranscoderDB.Entry> o : table2.entryIterator()) {
                  CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<TranscoderDB.Entry> e = (CaseInsensitiveBytesHash.CaseInsensitiveBytesHashEntry<TranscoderDB.Entry>)o;
                  byte[] dname = e.bytes;
                  if (bfsVisited.get(dname) == null) {
                     TranscoderDB.SearchPathQueue q = new TranscoderDB.SearchPathQueue();
                     q.encoding = dname;
                     q.next.p = null;
                     bfsLastQueue.p = q;
                     bfsLastQueue = q.next;
                     bfsVisited.putDirect(dname, bfsBaseEnc);
                  }
               }

               bfsBaseEnc = null;
            }
         }

         return -1;
      }
   }

   public static EConv alloc(int n) {
      return new EConv(n);
   }

   private static EConv openByTranscoderEntries(int n, TranscoderDB.Entry[] entries) {
      EConv econv = new EConv(n);

      for (int i = 0; i < n; i++) {
         Transcoder transcoder = entries[i].getTranscoder();
         econv.addTranscoderAt(transcoder, econv.numTranscoders);
      }

      return econv;
   }

   private static EConv open0(byte[] source, byte[] destination, int ecflags) {
      int numTrans;
      TranscoderDB.Entry[] entries;
      if (source.length == 0 && destination.length == 0) {
         numTrans = 0;
         entries = null;
      } else {
         final ObjPtr<TranscoderDB.Entry[]> lentries = new ObjPtr<>();
         numTrans = searchPath(source, destination, new TranscoderDB.SearchPathCallback() {
            int additional = 0;

            @Override
            public void call(byte[] source, byte[] destinationx, int depth) {
               if (lentries.p == null) {
                  lentries.p = new TranscoderDB.Entry[depth + 1 + this.additional];
               }

               lentries.p[depth] = TranscoderDB.getEntry(source, destinationx);
            }
         });
         entries = lentries.p;
         if (numTrans < 0) {
            return null;
         }
      }

      EConv ec = openByTranscoderEntries(numTrans, entries);
      if (ec == null) {
         return null;
      } else {
         ec.flags = ecflags;
         ec.source = source;
         ec.destination = destination;
         return ec;
      }
   }

   public static int decoratorNames(int ecflags, byte[][] decorators) {
      switch (ecflags & 16128) {
         case 0:
         case 256:
         case 4096:
         case 8192:
            if ((ecflags & 16384) != 0 && (ecflags & 32768) != 0) {
               return -1;
            }

            int numDecorators = 0;
            if ((ecflags & 16384) != 0) {
               decorators[numDecorators++] = "xml_text_escape".getBytes();
            }

            if ((ecflags & 32768) != 0) {
               decorators[numDecorators++] = "xml_attr_content_escape".getBytes();
            }

            if ((ecflags & 1048576) != 0) {
               decorators[numDecorators++] = "xml_attr_quote".getBytes();
            }

            if ((ecflags & 4096) != 0) {
               decorators[numDecorators++] = "crlf_newline".getBytes();
            }

            if ((ecflags & 8192) != 0) {
               decorators[numDecorators++] = "cr_newline".getBytes();
            }

            if ((ecflags & 256) != 0) {
               decorators[numDecorators++] = "universal_newline".getBytes();
            }

            return numDecorators;
         default:
            return -1;
      }
   }

   public static EConv open(String source, String destination, int ecflags) {
      return open(source.getBytes(), destination.getBytes(), ecflags);
   }

   public static EConv open(byte[] source, byte[] destination, int ecflags) {
      byte[][] decorators = new byte[32][];
      int numDecorators = decoratorNames(ecflags, decorators);
      if (numDecorators == -1) {
         return null;
      } else {
         EConv ec = open0(source, destination, ecflags & 0xFF);
         if (ec == null) {
            return null;
         } else {
            for (int i = 0; i < numDecorators; i++) {
               if (!ec.decorateAtLast(decorators[i])) {
                  ec.close();
                  return null;
               }
            }

            ec.flags |= ecflags & -256;
            return ec;
         }
      }
   }

   static byte[] asciiCompatibleEncoding(byte[] asciiCompatName) {
      CaseInsensitiveBytesHash<TranscoderDB.Entry> dTable = transcoders.get(asciiCompatName);
      if (dTable != null && dTable.size() == 1) {
         byte[] asciiCN = null;

         for (TranscoderDB.Entry e : dTable) {
            if (!EConv.decorator(e.source, e.destination)) {
               Transcoder transcoder = e.getTranscoder();
               if (transcoder != null && transcoder.compatibility.isDecoder()) {
                  asciiCN = transcoder.destination;
                  break;
               }
            }
         }

         return asciiCN;
      } else {
         return null;
      }
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

               for (int i = 0; i < list.length; i++) {
                  Transcoder.GenericTranscoderEntry entry = list[i];
                  if (Arrays.equals(this.source, entry.source) && Arrays.equals(this.destination, entry.destination)) {
                     this.transcoder = entry.createTranscoder();
                     break;
                  }
               }
            }
         }

         return this.transcoder;
      }
   }

   public interface SearchPathCallback {
      void call(byte[] var1, byte[] var2, int var3);
   }

   static final class SearchPathQueue {
      ObjPtr<TranscoderDB.SearchPathQueue> next = new ObjPtr<>();
      byte[] encoding;
   }
}
