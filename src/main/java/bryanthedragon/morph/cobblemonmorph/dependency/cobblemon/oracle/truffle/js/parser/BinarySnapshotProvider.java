package com.oracle.truffle.js.parser;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.js.codec.BinaryDecoder;
import com.oracle.truffle.js.codec.NodeDecoder;
import com.oracle.truffle.js.nodes.JSNodeDecoder;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.runtime.JSContext;
import java.nio.ByteBuffer;

public class BinarySnapshotProvider implements SnapshotProvider {
   public static final int MAGIC = 827214146;
   private final ByteBuffer buffer;

   public BinarySnapshotProvider(ByteBuffer buffer) {
      this.buffer = buffer;

      assert checkFormat(new BinaryDecoder(buffer));
   }

   private static boolean checkFormat(BinaryDecoder decoder) {
      int magic = decoder.getInt32();
      if (magic != 827214146) {
         throw new IllegalArgumentException("Unknown format");
      } else {
         int checksum = decoder.getInt32();
         if (checksum != JSNodeDecoder.getChecksum()) {
            throw new IllegalArgumentException("Snapshot verification failed");
         } else {
            return true;
         }
      }
   }

   public BinarySnapshotProvider(byte[] bytes) {
      this(ByteBuffer.wrap(bytes));
   }

   @Override
   public Object apply(NodeFactory nodeFactory, JSContext context, Source source) {
      BinaryDecoder decoder = new BinaryDecoder(this.buffer);
      checkFormat(decoder);
      int sourceLength = decoder.getInt32();
      int sourceHash = decoder.getInt32();
      CharSequence code = source.getCharacters();
      if (code.length() == sourceLength && code.hashCode() == sourceHash) {
         return new JSNodeDecoder().decodeNode(new NodeDecoder.DecoderState(decoder), nodeFactory, context, source);
      } else {
         throw new IllegalArgumentException("Snapshot verification failed");
      }
   }
}
