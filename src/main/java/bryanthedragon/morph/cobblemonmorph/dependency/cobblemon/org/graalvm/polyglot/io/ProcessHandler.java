package org.graalvm.polyglot.io;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public interface ProcessHandler {
   Process start(ProcessHandler.ProcessCommand command) throws IOException;

   public static final class ProcessCommand {
      private List<String> cmd;
      private String cwd;
      private Map<String, String> environment;
      private boolean redirectErrorStream;
      private ProcessHandler.Redirect inputRedirect;
      private ProcessHandler.Redirect outputRedirect;
      private ProcessHandler.Redirect errorRedirect;

      ProcessCommand(
         List<String> command,
         String cwd,
         Map<String, String> environment,
         boolean redirectErrorStream,
         ProcessHandler.Redirect inputRedirect,
         ProcessHandler.Redirect outputRedirect,
         ProcessHandler.Redirect errorRedirect
      ) {
         Objects.requireNonNull(command, "Command must be non null.");
         Objects.requireNonNull(environment, "Environment must be non null.");
         Objects.requireNonNull(inputRedirect, "InputRedirect must be non null.");
         Objects.requireNonNull(outputRedirect, "OutputRedirect must be non null.");
         Objects.requireNonNull(errorRedirect, "ErrorRedirect must be non null.");
         this.cmd = Collections.unmodifiableList(new ArrayList<>(command));
         this.cwd = cwd;
         this.environment = Collections.unmodifiableMap(new HashMap<>(environment));
         this.redirectErrorStream = redirectErrorStream;
         this.inputRedirect = inputRedirect;
         this.outputRedirect = outputRedirect;
         this.errorRedirect = errorRedirect;
      }

      public List<String> getCommand() {
         return this.cmd;
      }

      public String getDirectory() {
         return this.cwd;
      }

      public Map<String, String> getEnvironment() {
         return this.environment;
      }

      public boolean isRedirectErrorStream() {
         return this.redirectErrorStream;
      }

      public ProcessHandler.Redirect getInputRedirect() {
         return this.inputRedirect;
      }

      public ProcessHandler.Redirect getOutputRedirect() {
         return this.outputRedirect;
      }

      public ProcessHandler.Redirect getErrorRedirect() {
         return this.errorRedirect;
      }
   }

   public static final class Redirect {
      public static final ProcessHandler.Redirect PIPE = new ProcessHandler.Redirect(ProcessHandler.Redirect.Type.PIPE, null);
      public static final ProcessHandler.Redirect INHERIT = new ProcessHandler.Redirect(ProcessHandler.Redirect.Type.INHERIT, null);
      private final ProcessHandler.Redirect.Type type;
      private final OutputStream stream;

      Redirect(ProcessHandler.Redirect.Type type, OutputStream stream) {
         Objects.requireNonNull(type, "Type must be non null.");
         this.type = type;
         this.stream = stream;
      }

      OutputStream getOutputStream() {
         return this.stream;
      }

      @Override
      public String toString() {
         return this.type.toString();
      }

      @Override
      public int hashCode() {
         return this.type.hashCode();
      }

      @Override
      public boolean equals(Object obj) {
         if (obj == this) {
            return true;
         } else {
            return obj != null && obj.getClass() == ProcessHandler.Redirect.class ? this.type.equals(((ProcessHandler.Redirect)obj).type) : false;
         }
      }

      static enum Type {
         PIPE,
         INHERIT,
         STREAM;
      }
   }
}
