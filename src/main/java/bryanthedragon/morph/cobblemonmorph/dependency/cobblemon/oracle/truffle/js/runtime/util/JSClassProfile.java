
package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.NodeCloneable;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSShape;

public abstract class JSClassProfile
extends NodeCloneable {
    private static final JSClassProfile UNCACHED = new JSClassProfile(){

        public String toString() {
            return "JSClass(uncached)";
        }
    };

    JSClassProfile() {
    }

    public static JSClassProfile create() {
        return new Cached();
    }

    public static JSClassProfile getUncached() {
        return UNCACHED;
    }

    public JSClass getJSClass(JSDynamicObject jsobject) {
        return (JSClass)JSShape.getJSClassNoCast(jsobject.getShape());
    }

    public JSClass profile(JSClass jsobjectClass) {
        return jsobjectClass;
    }

    private static final class Cached
    extends JSClassProfile {
        @CompilerDirectives.CompilationFinal
        private JSClass expectedJSClass;
        @CompilerDirectives.CompilationFinal
        private boolean polymorphicJSClass;

        private Cached() {
        }

        @Override
        public JSClass getJSClass(JSDynamicObject jsobject) {
            Object jsobjectClass = JSShape.getJSClassNoCast(jsobject.getShape());
            if (!this.polymorphicJSClass) {
                if (jsobjectClass == this.expectedJSClass) {
                    return this.expectedJSClass;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                if (this.expectedJSClass == null) {
                    this.expectedJSClass = (JSClass)jsobjectClass;
                } else {
                    this.polymorphicJSClass = true;
                }
            }
            return (JSClass)jsobjectClass;
        }

        @Override
        public JSClass profile(JSClass jsobjectClass) {
            if (!this.polymorphicJSClass) {
                if (jsobjectClass == this.expectedJSClass) {
                    return this.expectedJSClass;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                if (this.expectedJSClass == null) {
                    this.expectedJSClass = jsobjectClass;
                } else {
                    this.polymorphicJSClass = true;
                }
            }
            return jsobjectClass;
        }

        public String toString() {
            return "JSClass(" + (this.polymorphicJSClass ? "polymorphic" : Boundaries.stringValueOf(this.expectedJSClass)) + ")";
        }
    }
}

