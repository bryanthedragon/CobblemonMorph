
package com.oracle.truffle.api.io;

import com.oracle.truffle.api.impl.Accessor;
import com.oracle.truffle.api.io.TruffleProcessBuilder;
import java.util.List;
import org.graalvm.polyglot.io.FileSystem;

final class IOAccessor
extends Accessor {
    static final IOAccessor ACCESSOR = new IOAccessor();

    private IOAccessor() {
    }

    static Accessor.EngineSupport engineAccess() {
        return ACCESSOR.engineSupport();
    }

    static Accessor.LanguageSupport languageAccess() {
        return ACCESSOR.languageSupport();
    }

    static final class IOSupportImpl
    extends Accessor.IOSupport {
        IOSupportImpl() {
        }

        @Override
        public TruffleProcessBuilder createProcessBuilder(Object polyglotLanguageContext, FileSystem fileSystem, List<String> command) {
            return new TruffleProcessBuilder(polyglotLanguageContext, fileSystem, command);
        }
    }
}

