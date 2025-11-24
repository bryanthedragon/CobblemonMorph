
package com.oracle.truffle.polyglot;

import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;
import org.graalvm.polyglot.io.ProcessHandler;

final class ProcessHandlers {
    private ProcessHandlers() {
        throw new IllegalStateException("Instance is not allowed");
    }

    static ProcessHandler newDefaultProcessHandler() {
        return new DefaultProcessHandler();
    }

    static boolean isDefault(ProcessHandler handler) {
        return handler instanceof DefaultProcessHandler;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    static Process decorate(PolyglotLanguageContext owner, List<? extends String> cmd, Process process, OutputStream out, OutputStream err) {
        ProcessDecorator decorator = new ProcessDecorator(owner, cmd.get(0), process, out, err);
        PolyglotContextImpl polyglotContextImpl = owner.context;
        synchronized (polyglotContextImpl) {
            owner.context.subProcesses.add(decorator);
        }
        return decorator;
    }

    private static final class CopierThread
    extends Thread {
        private static final int BUFSIZE = 8192;
        private final AbstractPolyglotImpl polyglot;
        private final InputStream in;
        private final OutputStream out;
        private final byte[] buffer;

        CopierThread(AbstractPolyglotImpl polyglot, String name, InputStream in, OutputStream out) {
            Objects.requireNonNull(polyglot, "Polyglot must be non null.");
            Objects.requireNonNull(name, "Name must be non null.");
            Objects.requireNonNull(in, "In must be non null.");
            Objects.requireNonNull(out, "Out must be non null.");
            this.setName(name);
            this.polyglot = polyglot;
            this.in = in;
            this.out = out;
            this.buffer = new byte[8192];
        }

        @Override
        public void run() {
            try (AbstractPolyglotImpl.ThreadScope scope = this.polyglot.getRootImpl().createThreadScope();){
                while (true) {
                    if (this.isInterrupted()) {
                        return;
                    }
                    int read2 = this.in.read(this.buffer, 0, this.buffer.length);
                    if (read2 == -1) {
                        return;
                    }
                    this.out.write(this.buffer, 0, read2);
                }
            }
            catch (IOException iOException) {
                return;
            }
        }
    }

    static final class ProcessDecorator
    extends Process {
        private final Reference<PolyglotLanguageContext> owner;
        private final String command;
        private final Process delegate;
        private final Thread outCopier;
        private final Thread errCopier;

        private ProcessDecorator(PolyglotLanguageContext owner, String command, Process delegate, OutputStream out, OutputStream err) {
            Objects.requireNonNull(owner, "Owner must be non null");
            Objects.requireNonNull(command, "Command muset be non null");
            Objects.requireNonNull(delegate, "Delegate must be non null.");
            this.owner = new WeakReference<PolyglotLanguageContext>(owner);
            this.command = command;
            this.delegate = delegate;
            this.outCopier = out == null ? null : new CopierThread(owner.getImpl(), ProcessDecorator.createThreadName(owner, command, "stdout"), delegate.getInputStream(), out);
            Thread thread = this.errCopier = err == null ? null : new CopierThread(owner.getImpl(), ProcessDecorator.createThreadName(owner, command, "stderr"), delegate.getErrorStream(), err);
            if (this.outCopier != null) {
                this.outCopier.start();
            }
            if (this.errCopier != null) {
                this.errCopier.start();
            }
        }

        PolyglotLanguageContext getOwner() {
            return this.owner.get();
        }

        String getCommand() {
            return this.command;
        }

        @Override
        public OutputStream getOutputStream() {
            return this.delegate.getOutputStream();
        }

        @Override
        public InputStream getInputStream() {
            if (this.outCopier == null) {
                return this.delegate.getInputStream();
            }
            return null;
        }

        @Override
        public InputStream getErrorStream() {
            if (this.errCopier == null) {
                return this.delegate.getErrorStream();
            }
            return null;
        }

        @Override
        public int waitFor() throws InterruptedException {
            int res = this.delegate.waitFor();
            this.waitForCopiers();
            this.removeFromActiveSubProcesses();
            return res;
        }

        @Override
        public int exitValue() {
            int result = this.delegate.exitValue();
            this.removeFromActiveSubProcesses();
            return result;
        }

        @Override
        public void destroy() {
            this.delegate.destroy();
        }

        @Override
        public Process destroyForcibly() {
            Process result = this.delegate.destroyForcibly();
            assert (result.pid() == this.delegate.pid());
            return this;
        }

        @Override
        public boolean isAlive() {
            boolean result = this.delegate.isAlive();
            if (!result) {
                this.removeFromActiveSubProcesses();
            }
            return result;
        }

        @Override
        public boolean waitFor(long timeout, TimeUnit unit) throws InterruptedException {
            boolean res = this.delegate.waitFor(timeout, unit);
            if (res) {
                this.waitForCopiers();
                this.removeFromActiveSubProcesses();
            }
            return res;
        }

        private void waitForCopiers() throws InterruptedException {
            if (this.outCopier != null) {
                this.outCopier.join();
            }
            if (this.errCopier != null) {
                this.errCopier.join();
            }
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void removeFromActiveSubProcesses() {
            PolyglotLanguageContext languageContext = this.owner.get();
            if (languageContext != null) {
                PolyglotContextImpl polyglotContextImpl = languageContext.context;
                synchronized (polyglotContextImpl) {
                    languageContext.context.subProcesses.remove(this);
                }
            }
        }

        private static String createThreadName(PolyglotLanguageContext creator, String command, String suffix) {
            return "Polyglot-" + creator.language.getId() + "-" + command + "-" + suffix;
        }
    }

    private static final class DefaultProcessHandler
    implements ProcessHandler {
        private DefaultProcessHandler() {
        }

        @Override
        public Process start(ProcessHandler.ProcessCommand command) throws IOException {
            ProcessBuilder builder = new ProcessBuilder(command.getCommand()).redirectErrorStream(command.isRedirectErrorStream()).redirectInput(DefaultProcessHandler.translateRedirect(command.getInputRedirect())).redirectOutput(DefaultProcessHandler.translateRedirect(command.getOutputRedirect())).redirectError(DefaultProcessHandler.translateRedirect(command.getErrorRedirect()));
            Map<String, String> env = builder.environment();
            env.clear();
            env.putAll(command.getEnvironment());
            String cwd = command.getDirectory();
            if (cwd != null) {
                builder.directory(Paths.get(cwd, new String[0]).toFile());
            }
            return builder.start();
        }

        private static ProcessBuilder.Redirect translateRedirect(ProcessHandler.Redirect redirect) {
            if (redirect == ProcessHandler.Redirect.PIPE) {
                return ProcessBuilder.Redirect.PIPE;
            }
            if (redirect == ProcessHandler.Redirect.INHERIT) {
                return ProcessBuilder.Redirect.INHERIT;
            }
            throw new IllegalStateException("Unsupported redirect: " + redirect);
        }
    }
}

