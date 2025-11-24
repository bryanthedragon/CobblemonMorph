
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.function.BuiltinArgumentBuilder;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;

public interface BuiltinEnum<E extends Enum<? extends BuiltinEnum<E>>> {
    default public E asEnum() {
        return (E)((Enum)((Object)this));
    }

    default public TruffleString getName() {
        return this.prependAccessorPrefix(BuiltinEnum.stripName(Strings.fromJavaString(((Enum)this.asEnum()).name())));
    }

    default public Object getKey() {
        return BuiltinEnum.stripName(Strings.fromJavaString(((Enum)this.asEnum()).name()));
    }

    default public boolean isConstructor() {
        return false;
    }

    default public boolean isNewTargetConstructor() {
        return false;
    }

    public int getLength();

    default public boolean isEnabled() {
        return true;
    }

    default public boolean isAOTSupported() {
        return true;
    }

    default public int getECMAScriptVersion() {
        return 5;
    }

    default public boolean isAnnexB() {
        return false;
    }

    default public boolean isWritable() {
        return true;
    }

    default public boolean isConfigurable() {
        return true;
    }

    default public boolean isEnumerable() {
        return false;
    }

    default public boolean isGetter() {
        return false;
    }

    default public boolean isSetter() {
        return false;
    }

    default public Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget) {
        throw new UnsupportedOperationException();
    }

    default public BuiltinArgumentBuilder args() {
        return BuiltinArgumentBuilder.builder();
    }

    public static TruffleString stripName(TruffleString name) {
        if (Strings.endsWith(name, Strings.UNDERSCORE) && !Strings.endsWith(name, Strings.UNDERSCORE_2)) {
            return Strings.lazySubstring(name, 0, Strings.length(name) - 1);
        }
        return name;
    }

    default public TruffleString prependAccessorPrefix(TruffleString name) {
        if (this.isGetter()) {
            return Strings.concat(Strings.GET_SPC, name);
        }
        if (this.isSetter()) {
            return Strings.concat(Strings.SET_SPC, name);
        }
        return name;
    }
}

