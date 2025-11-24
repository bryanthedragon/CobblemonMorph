
package org.graalvm.polyglot.io;

import java.io.IOException;
import java.net.URI;
import org.graalvm.polyglot.io.MessageEndpoint;

public interface MessageTransport {
    public MessageEndpoint open(URI var1, MessageEndpoint var2) throws IOException, VetoException;

    public static final class VetoException
    extends Exception {
        private static final long serialVersionUID = 3493487569356378902L;

        public VetoException(String message) {
            super(message);
        }
    }
}

