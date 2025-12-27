package org.graalvm.polyglot.io;

import java.io.IOException;
import java.net.URI;

public interface MessageTransport {
   MessageEndpoint open(URI uri, MessageEndpoint peerEndpoint) throws IOException, MessageTransport.VetoException;

   public static final class VetoException extends Exception {
      private static final long serialVersionUID = 3493487569356378902L;

      public VetoException(String message) {
         super(message);
      }
   }
}
