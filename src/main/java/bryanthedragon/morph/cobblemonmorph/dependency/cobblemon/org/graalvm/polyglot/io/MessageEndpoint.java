
package org.graalvm.polyglot.io;

import java.io.IOException;
import java.nio.ByteBuffer;

public interface MessageEndpoint {
    public void sendText(String var1) throws IOException;

    public void sendBinary(ByteBuffer var1) throws IOException;

    public void sendPing(ByteBuffer var1) throws IOException;

    public void sendPong(ByteBuffer var1) throws IOException;

    public void sendClose() throws IOException;
}

