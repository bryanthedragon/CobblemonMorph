
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
    public Process start(ProcessCommand var1) throws IOException;

    public static final class Redirect {
        public static final Redirect PIPE = new Redirect(Type.PIPE, null);
        public static final Redirect INHERIT = new Redirect(Type.INHERIT, null);
        private final Type type;
        private final OutputStream stream;

        Redirect(Type type, OutputStream stream) {
            Objects.requireNonNull(type, "Type must be non null.");
            this.type = type;
            this.stream = stream;
        }

        OutputStream getOutputStream() {
            return this.stream;
        }

        public String toString() {
            return this.type.toString();
        }

        public int hashCode() {
            return this.type.hashCode();
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != Redirect.class) {
                return false;
            }
            return this.type.equals((Object)((Redirect)obj).type);
        }

        static enum Type {
            PIPE,
            INHERIT,
            STREAM;

        }
    }

    public static final class ProcessCommand {
        private List<String> cmd;
        private String cwd;
        private Map<String, String> environment;
        private boolean redirectErrorStream;
        private Redirect inputRedirect;
        private Redirect outputRedirect;
        private Redirect errorRedirect;

        ProcessCommand(List<String> command, String cwd, Map<String, String> environment, boolean redirectErrorStream, Redirect inputRedirect, Redirect outputRedirect, Redirect errorRedirect) {
            Objects.requireNonNull(command, "Command must be non null.");
            Objects.requireNonNull(environment, "Environment must be non null.");
            Objects.requireNonNull(inputRedirect, "InputRedirect must be non null.");
            Objects.requireNonNull(outputRedirect, "OutputRedirect must be non null.");
            Objects.requireNonNull(errorRedirect, "ErrorRedirect must be non null.");
            this.cmd = Collections.unmodifiableList(new ArrayList<String>(command));
            this.cwd = cwd;
            this.environment = Collections.unmodifiableMap(new HashMap<String, String>(environment));
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

        public Redirect getInputRedirect() {
            return this.inputRedirect;
        }

        public Redirect getOutputRedirect() {
            return this.outputRedirect;
        }

        public Redirect getErrorRedirect() {
            return this.errorRedirect;
        }
    }
}

