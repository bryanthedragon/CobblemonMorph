
package org.graalvm.nativeimage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.graalvm.nativeimage.ImageSingletons;
import org.graalvm.nativeimage.Isolate;
import org.graalvm.nativeimage.IsolateThread;
import org.graalvm.nativeimage.impl.IsolateSupport;
import org.graalvm.word.UnsignedWord;

public final class Isolates {
    private Isolates() {
    }

    public static IsolateThread createIsolate(CreateIsolateParameters parameters) throws IsolateException {
        Objects.requireNonNull(parameters);
        return ImageSingletons.lookup(IsolateSupport.class).createIsolate(parameters);
    }

    public static IsolateThread attachCurrentThread(Isolate isolate) throws IsolateException {
        return ImageSingletons.lookup(IsolateSupport.class).attachCurrentThread(isolate);
    }

    public static IsolateThread getCurrentThread(Isolate isolate) throws IsolateException {
        return ImageSingletons.lookup(IsolateSupport.class).getCurrentThread(isolate);
    }

    public static Isolate getIsolate(IsolateThread thread) throws IsolateException {
        return ImageSingletons.lookup(IsolateSupport.class).getIsolate(thread);
    }

    public static void detachThread(IsolateThread thread) throws IsolateException {
        ImageSingletons.lookup(IsolateSupport.class).detachThread(thread);
    }

    public static void tearDownIsolate(IsolateThread thread) throws IsolateException {
        ImageSingletons.lookup(IsolateSupport.class).tearDownIsolate(thread);
    }

    public static interface ProtectionDomain {
        public static final ProtectionDomain NO_DOMAIN = new ProtectionDomain(){};
        public static final ProtectionDomain NEW_DOMAIN = new ProtectionDomain(){};
    }

    public static final class CreateIsolateParameters {
        private static final CreateIsolateParameters DEFAULT = new Builder().build();
        private final UnsignedWord reservedAddressSpaceSize;
        private final String auxiliaryImagePath;
        private final UnsignedWord auxiliaryImageReservedSpaceSize;
        private final List<String> arguments;
        private final ProtectionDomain protectionDomain;

        public static CreateIsolateParameters getDefault() {
            return DEFAULT;
        }

        private CreateIsolateParameters(UnsignedWord reservedAddressSpaceSize, String auxiliaryImagePath, UnsignedWord auxiliaryImageReservedSpaceSize, List<String> arguments, ProtectionDomain protectionDomain) {
            this.reservedAddressSpaceSize = reservedAddressSpaceSize;
            this.auxiliaryImagePath = auxiliaryImagePath;
            this.auxiliaryImageReservedSpaceSize = auxiliaryImageReservedSpaceSize;
            this.arguments = arguments;
            this.protectionDomain = protectionDomain;
        }

        public UnsignedWord getReservedAddressSpaceSize() {
            return this.reservedAddressSpaceSize;
        }

        public String getAuxiliaryImagePath() {
            return this.auxiliaryImagePath;
        }

        public UnsignedWord getAuxiliaryImageReservedSpaceSize() {
            return this.auxiliaryImageReservedSpaceSize;
        }

        public List<String> getArguments() {
            return Collections.unmodifiableList(this.arguments);
        }

        public ProtectionDomain getProtectionDomain() {
            return this.protectionDomain;
        }

        public static final class Builder {
            private UnsignedWord reservedAddressSpaceSize;
            private String auxiliaryImagePath;
            private UnsignedWord auxiliaryImageReservedSpaceSize;
            private final List<String> arguments;
            private ProtectionDomain protectionDomain = ProtectionDomain.NO_DOMAIN;

            public Builder() {
                this.arguments = new ArrayList<String>();
            }

            public Builder reservedAddressSpaceSize(UnsignedWord size) {
                this.reservedAddressSpaceSize = size;
                return this;
            }

            public Builder auxiliaryImagePath(String filePath) {
                this.auxiliaryImagePath = filePath;
                return this;
            }

            public Builder auxiliaryImageReservedSpaceSize(UnsignedWord size) {
                this.auxiliaryImageReservedSpaceSize = size;
                return this;
            }

            public Builder appendArgument(String argument) {
                this.arguments.add(argument);
                return this;
            }

            public Builder setProtectionDomain(ProtectionDomain domain) {
                this.protectionDomain = domain;
                return this;
            }

            public CreateIsolateParameters build() {
                return new CreateIsolateParameters(this.reservedAddressSpaceSize, this.auxiliaryImagePath, this.auxiliaryImageReservedSpaceSize, this.arguments, this.protectionDomain);
            }
        }
    }

    public static final class IsolateException
    extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public IsolateException(String message) {
            super(message);
        }
    }
}

