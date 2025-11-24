
package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNextNode;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@ExportLibrary(value=InteropLibrary.class, delegateTo="iterator")
public final class JSIteratorWrapper
implements TruffleObject {
    final JSDynamicObject iterator;
    private final IteratorRecord iteratorRecord;
    private Object next;
    private static final Object STOP = StopIterationException.create();

    private JSIteratorWrapper(IteratorRecord iterator) {
        this.iterator = iterator.getIterator();
        this.iteratorRecord = iterator;
    }

    public static JSIteratorWrapper create(IteratorRecord iterator) {
        return new JSIteratorWrapper(iterator);
    }

    @ExportMessage
    boolean isIterator() {
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object next(JavaScriptLanguage language, JSRealm realm, JSInteropGetIteratorNextNode iteratorNextNode) {
        language.interopBoundaryEnter(realm);
        try {
            Object object = iteratorNextNode.getIteratorNextElement(this.iteratorRecord, language, STOP);
            return object;
        }
        finally {
            language.interopBoundaryExit(realm);
        }
    }

    @ExportMessage
    boolean hasIteratorNextElement(@CachedLibrary(value="this") InteropLibrary self, @Cached @Cached.Shared(value="getIteratorNext") JSInteropGetIteratorNextNode iteratorNextNode) {
        JavaScriptLanguage language = JavaScriptLanguage.get(self);
        JSRealm realm = JSRealm.get(self);
        if (this.next == null) {
            this.next = this.next(language, realm, iteratorNextNode);
        }
        return this.next != STOP;
    }

    @ExportMessage
    Object getIteratorNextElement(@CachedLibrary(value="this") InteropLibrary self, @Cached @Cached.Shared(value="getIteratorNext") JSInteropGetIteratorNextNode iteratorNextNode) throws StopIterationException {
        if (this.hasIteratorNextElement(self, iteratorNextNode)) {
            Object result = this.next;
            this.next = null;
            return result;
        }
        throw StopIterationException.create();
    }
}

