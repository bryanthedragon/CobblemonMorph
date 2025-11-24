
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.AbstractJSClass;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Nullish;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class Null {
    public static final TruffleString TYPE_NAME = Strings.OBJECT;
    public static final TruffleString NAME = Strings.NULL;
    public static final TruffleString CLASS_NAME = Strings.NULL_UNDEFINED;
    public static final TruffleString DISPLAY_STRING_UNDEFINED = Strings.constant("[object Undefined]");
    public static final TruffleString DISPLAY_STRING_NULL = Strings.constant("[object Null]");
    public static final JSClass NULL_CLASS = NullClass.INSTANCE;
    static final Shape SHAPE = JSShape.makeStaticRoot(NULL_CLASS);
    public static final JSDynamicObject instance = new Nullish();

    private Null() {
    }

    static final class NullClass
    extends AbstractJSClass {
        static final NullClass INSTANCE = new NullClass();

        private NullClass() {
        }

        @Override
        public TruffleString getClassName(JSDynamicObject object) {
            return object == Undefined.instance ? Undefined.NAME : NAME;
        }

        @Override
        public String toString() {
            return Strings.toJavaString(CLASS_NAME);
        }

        @Override
        public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
            return this.delete(thisObj, Strings.fromLong(index), isStrict);
        }

        @Override
        public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
            throw Errors.createTypeErrorCannotDeletePropertyOf(key, thisObj);
        }

        @Override
        public TruffleString toDisplayStringImpl(JSDynamicObject object, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
            return object == Undefined.instance ? DISPLAY_STRING_UNDEFINED : DISPLAY_STRING_NULL;
        }

        @Override
        public TruffleString defaultToString(JSDynamicObject thisObj) {
            return thisObj == Undefined.instance ? Undefined.NAME : NAME;
        }
    }
}

