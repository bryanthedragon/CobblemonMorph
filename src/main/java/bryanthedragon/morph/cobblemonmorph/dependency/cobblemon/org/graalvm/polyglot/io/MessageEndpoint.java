package org.graalvm.polyglot.io;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface MessageEndpoint {
   void sendText(String text) throws IOException;

   void sendBinary(ByteBuffer data) throws IOException;

   void sendPing(ByteBuffer data) throws IOException;

   void sendPong(ByteBuffer data) throws IOException;

   void sendClose() throws IOException;
}
