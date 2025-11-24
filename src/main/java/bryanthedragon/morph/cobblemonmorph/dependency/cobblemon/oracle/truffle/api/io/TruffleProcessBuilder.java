
package com.oracle.truffle.api.io;

import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.io.IOAccessor;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.graalvm.polyglot.io.FileSystem;
import org.graalvm.polyglot.io.ProcessHandler;

public final class TruffleProcessBuilder {
    private final Object polyglotLanguageContext;
    private final FileSystem fileSystem;
    private List<String> cmd;
    private TruffleFile cwd;
    private boolean inheritIO;
    private boolean clearEnvironment;
    private Map<String, String> env;
    private boolean redirectErrorStream;
    private ProcessHandler.Redirect inputRedirect;
    private ProcessHandler.Redirect outputRedirect;
    private ProcessHandler.Redirect errorRedirect;

    TruffleProcessBuilder(Object polyglotLanguageContext, FileSystem fileSystem, List<String> command) {
        Objects.requireNonNull(polyglotLanguageContext, "PolylgotLanguageContext must be non null.");
        Objects.requireNonNull(fileSystem, "FileSystem must be non null.");
        Objects.requireNonNull(command, "Command must be non null.");
        this.polyglotLanguageContext = polyglotLanguageContext;
        this.fileSystem = fileSystem;
        this.cmd = command;
        this.inputRedirect = ProcessHandler.Redirect.PIPE;
        this.outputRedirect = ProcessHandler.Redirect.PIPE;
        this.errorRedirect = ProcessHandler.Redirect.PIPE;
    }

    public TruffleProcessBuilder command(List<String> command) {
        Objects.requireNonNull(command, "Command must be non null.");
        this.cmd = new ArrayList<String>(command);
        return this;
    }

    public TruffleProcessBuilder command(String ... command) {
        Objects.requireNonNull(command, "Command must be non null.");
        this.cmd = new ArrayList<String>(command.length);
        Collections.addAll(this.cmd, command);
        return this;
    }

    public TruffleProcessBuilder directory(TruffleFile currentWorkingDirectory) {
        this.cwd = currentWorkingDirectory;
        return this;
    }

    public TruffleProcessBuilder redirectErrorStream(boolean enabled) {
        this.redirectErrorStream = enabled;
        return this;
    }

    public TruffleProcessBuilder redirectInput(ProcessHandler.Redirect source) {
        Objects.requireNonNull(source, "Source must be non null.");
        this.inputRedirect = source;
        return this;
    }

    public TruffleProcessBuilder redirectOutput(ProcessHandler.Redirect destination) {
        Objects.requireNonNull(destination, "Destination must be non null.");
        this.outputRedirect = destination;
        return this;
    }

    public TruffleProcessBuilder redirectError(ProcessHandler.Redirect destination) {
        Objects.requireNonNull(destination, "Destination must be non null.");
        this.errorRedirect = destination;
        return this;
    }

    public TruffleProcessBuilder inheritIO(boolean enabled) {
        this.inheritIO = enabled;
        return this;
    }

    public TruffleProcessBuilder clearEnvironment(boolean clear2) {
        this.clearEnvironment = clear2;
        return this;
    }

    public TruffleProcessBuilder environment(String name, String value2) {
        Objects.requireNonNull(name, "Name must be non null.");
        Objects.requireNonNull(value2, "Value must be non null.");
        if (this.env == null) {
            this.env = new HashMap<String, String>();
        }
        this.env.put(name, value2);
        return this;
    }

    public TruffleProcessBuilder environment(Map<String, String> environment) {
        for (Map.Entry<String, String> e : environment.entrySet()) {
            this.environment(e.getKey(), e.getValue());
        }
        return this;
    }

    public ProcessHandler.Redirect createRedirectToStream(OutputStream stream) {
        return IOAccessor.engineAccess().createRedirectToOutputStream(this.polyglotLanguageContext, stream);
    }

    public Process start() throws IOException {
        Map<String, String> useEnv;
        ArrayList<String> useCmd = new ArrayList<String>();
        for (String item : this.cmd) {
            if (item == null) {
                throw new NullPointerException("Command contains null.");
            }
            useCmd.add(item);
        }
        if (useCmd.isEmpty()) {
            throw new IndexOutOfBoundsException("Command is empty");
        }
        useCmd = Collections.unmodifiableList(this.cmd);
        if (this.inheritIO) {
            this.inputRedirect = ProcessHandler.Redirect.INHERIT;
            this.outputRedirect = ProcessHandler.Redirect.INHERIT;
            this.errorRedirect = ProcessHandler.Redirect.INHERIT;
        }
        if (this.clearEnvironment) {
            useEnv = this.env == null ? Collections.emptyMap() : Collections.unmodifiableMap(this.env);
        } else {
            useEnv = IOAccessor.engineAccess().getProcessEnvironment(this.polyglotLanguageContext);
            if (this.env != null) {
                useEnv = new HashMap<String, String>(useEnv);
                useEnv.putAll(this.env);
                useEnv = Collections.unmodifiableMap(useEnv);
            }
        }
        try {
            String useCwd = this.cwd != null ? this.cwd.getPath() : this.fileSystem.toAbsolutePath(this.fileSystem.parsePath("")).toString();
            return IOAccessor.engineAccess().createSubProcess(this.polyglotLanguageContext, useCmd, useCwd, useEnv, this.redirectErrorStream, this.inputRedirect, this.outputRedirect, this.errorRedirect);
        }
        catch (IOException | SecurityException ioe) {
            throw ioe;
        }
        catch (Throwable t) {
            throw this.wrapHostException(t);
        }
    }

    private <T extends Throwable> RuntimeException wrapHostException(T t) {
        if (IOAccessor.engineAccess().hasDefaultProcessHandler(this.polyglotLanguageContext)) {
            throw TruffleProcessBuilder.sthrow(RuntimeException.class, t);
        }
        throw IOAccessor.engineAccess().wrapHostException(null, this.polyglotLanguageContext, t);
    }

    private static <T extends Throwable> T sthrow(Class<T> type, Throwable t) throws T {
        throw t;
    }
}

