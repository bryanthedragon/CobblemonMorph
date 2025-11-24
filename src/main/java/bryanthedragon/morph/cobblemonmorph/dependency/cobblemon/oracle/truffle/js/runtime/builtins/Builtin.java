
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.objects.JSAttributes;

public interface Builtin {
    public TruffleString getName();

    public Object getKey();

    public int getLength();

    public int getECMAScriptVersion();

    public boolean isAnnexB();

    public boolean isWritable();

    public boolean isEnumerable();

    public boolean isConfigurable();

    default public int getAttributeFlags() {
        return JSAttributes.fromConfigurableEnumerableWritable(this.isConfigurable(), this.isEnumerable(), this.isWritable());
    }

    public boolean isGetter();

    public boolean isSetter();

    public JSFunctionData createFunctionData(JSContext var1);

    default public boolean isIncluded(JSContext context) {
        if (this.getECMAScriptVersion() > context.getEcmaScriptVersion()) {
            return false;
        }
        return !this.isAnnexB() || context.isOptionAnnexB();
    }
}

