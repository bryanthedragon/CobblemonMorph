package com.oracle.truffle.api.io;

import com.oracle.truffle.api.TruffleFile;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
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
      this.cmd = new ArrayList<>(command);
      return this;
   }

   public TruffleProcessBuilder command(String... command) {
      Objects.requireNonNull(command, "Command must be non null.");
      this.cmd = new ArrayList<>(command.length);
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

   public TruffleProcessBuilder clearEnvironment(boolean clear) {
      this.clearEnvironment = clear;
      return this;
   }

   public TruffleProcessBuilder environment(String name, String value) {
      Objects.requireNonNull(name, "Name must be non null.");
      Objects.requireNonNull(value, "Value must be non null.");
      if (this.env == null) {
         this.env = new HashMap<>();
      }

      this.env.put(name, value);
      return this;
   }

   public TruffleProcessBuilder environment(Map<String, String> environment) {
      for (Entry<String, String> e : environment.entrySet()) {
         this.environment(e.getKey(), e.getValue());
      }

      return this;
   }

   public ProcessHandler.Redirect createRedirectToStream(OutputStream stream) {
      return IOAccessor.engineAccess().createRedirectToOutputStream(this.polyglotLanguageContext, stream);
   }

   public Process start() throws IOException {
      List<String> useCmd = new ArrayList<>();

      for (String item : this.cmd) {
         if (item == null) {
            throw new NullPointerException("Command contains null.");
         }

         useCmd.add(item);
      }

      if (useCmd.isEmpty()) {
         throw new IndexOutOfBoundsException("Command is empty");
      } else {
         useCmd = Collections.unmodifiableList(this.cmd);
         if (this.inheritIO) {
            this.inputRedirect = ProcessHandler.Redirect.INHERIT;
            this.outputRedirect = ProcessHandler.Redirect.INHERIT;
            this.errorRedirect = ProcessHandler.Redirect.INHERIT;
         }

         Map<String, String> useEnv;
         if (this.clearEnvironment) {
            useEnv = this.env == null ? Collections.emptyMap() : Collections.unmodifiableMap(this.env);
         } else {
            useEnv = IOAccessor.engineAccess().getProcessEnvironment(this.polyglotLanguageContext);
            if (this.env != null) {
               Map<String, String> var8 = new HashMap<>(useEnv);
               var8.putAll(this.env);
               useEnv = Collections.unmodifiableMap(var8);
            }
         }

         try {
            String useCwd;
            if (this.cwd != null) {
               useCwd = this.cwd.getPath();
            } else {
               useCwd = this.fileSystem.toAbsolutePath(this.fileSystem.parsePath("")).toString();
            }

            return IOAccessor.engineAccess()
               .createSubProcess(
                  this.polyglotLanguageContext, useCmd, useCwd, useEnv, this.redirectErrorStream, this.inputRedirect, this.outputRedirect, this.errorRedirect
               );
         } catch (SecurityException | IOException var4) {
            throw var4;
         } catch (Throwable var5) {
            throw this.wrapHostException(var5);
         }
      }
   }

   private <T extends Throwable> RuntimeException wrapHostException(T t) {
      if (IOAccessor.engineAccess().hasDefaultProcessHandler(this.polyglotLanguageContext)) {
         throw (RuntimeException)sthrow(RuntimeException.class, t);
      } else {
         throw IOAccessor.engineAccess().wrapHostException(null, this.polyglotLanguageContext, t);
      }
   }

   private static <T extends Throwable> T sthrow(Class<T> type, Throwable t) throws T {
      throw t;
   }
}
