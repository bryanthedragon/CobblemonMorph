
package com.oracle.truffle.api.staticobject;

import com.oracle.truffle.api.impl.Accessor;

final class SomAccessor
extends Accessor {
    static final SomAccessor ACCESSOR = new SomAccessor();
    static final Accessor.RuntimeSupport RUNTIME = ACCESSOR.runtimeSupport();
    static final Accessor.LanguageSupport LANGUAGE = ACCESSOR.languageSupport();
    static final Accessor.EngineSupport ENGINE = ACCESSOR.engineSupport();

    SomAccessor() {
    }

    static final class SomImpl
    extends Accessor.SomSupport {
        SomImpl() {
        }
    }
}

