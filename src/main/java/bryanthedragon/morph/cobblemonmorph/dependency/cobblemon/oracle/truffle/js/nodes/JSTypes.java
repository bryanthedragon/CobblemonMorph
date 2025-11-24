
package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.dsl.ImplicitCast;
import com.oracle.truffle.api.dsl.TypeCheck;
import com.oracle.truffle.api.dsl.TypeSystem;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@TypeSystem(value={boolean.class, int.class, double.class, long.class, SafeInteger.class, BigInt.class})
public class JSTypes {
    protected JSTypes() {
    }

    @ImplicitCast
    public static double intToDouble(int value2) {
        return value2;
    }

    @ImplicitCast
    public static double safeIntegerToDouble(SafeInteger value2) {
        return value2.doubleValue();
    }

    @ImplicitCast
    public static double longToDouble(long value2) {
        return value2;
    }

    @Deprecated
    @TypeCheck(value=DynamicObject.class)
    public static boolean isDynamicObject(Object value2) {
        return JSDynamicObject.isJSDynamicObject(value2);
    }
}

