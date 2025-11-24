
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNodeGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;

@GenerateUncached
@ImportStatic(value={JSConfig.class})
public abstract class JSToBooleanNode
extends JavaScriptBaseNode {
    protected JSToBooleanNode() {
    }

    public abstract boolean executeBoolean(Object var1);

    public static JSToBooleanNode create() {
        return JSToBooleanNodeGen.create();
    }

    @Specialization
    protected static boolean doBoolean(boolean value2) {
        return value2;
    }

    @Specialization(guards={"isJSNull(value)"})
    protected static boolean doNull(Object value2) {
        return false;
    }

    @Specialization(guards={"isUndefined(value)"})
    protected static boolean doUndefined(Object value2) {
        return false;
    }

    @Specialization
    protected static boolean doInt(int value2) {
        return value2 != 0;
    }

    @Specialization
    protected static boolean doLong(long value2) {
        return value2 != 0L;
    }

    @Specialization
    protected static boolean doDouble(double value2) {
        return value2 != 0.0 && !Double.isNaN(value2);
    }

    @Specialization
    protected static boolean doBigInt(BigInt value2) {
        return value2.compareTo(BigInt.ZERO) != 0;
    }

    @Specialization
    protected static boolean doString(TruffleString value2) {
        return Strings.length(value2) != 0;
    }

    @Specialization(guards={"isJSObject(value)"})
    protected static boolean doObject(Object value2) {
        return true;
    }

    @Specialization
    protected static boolean doSymbol(Symbol value2) {
        return true;
    }

    @Specialization(guards={"isForeignObject(value)"}, limit="InteropLibraryLimit")
    protected final boolean doForeignObject(Object value2, @CachedLibrary(value="value") InteropLibrary interop) {
        if (interop.isNull(value2)) {
            return false;
        }
        try {
            if (interop.isBoolean(value2)) {
                return interop.asBoolean(value2);
            }
            if (interop.isString(value2)) {
                return !Strings.isEmpty(interop.asTruffleString(value2));
            }
            if (interop.isNumber(value2)) {
                if (interop.fitsInInt(value2)) {
                    return JSToBooleanNode.doInt(interop.asInt(value2));
                }
                if (interop.fitsInLong(value2)) {
                    return JSToBooleanNode.doLong(interop.asLong(value2));
                }
                if (interop.fitsInDouble(value2)) {
                    return JSToBooleanNode.doDouble(interop.asDouble(value2));
                }
                return true;
            }
        }
        catch (UnsupportedMessageException e) {
            throw Errors.createTypeErrorUnboxException(value2, e, this);
        }
        return true;
    }
}

