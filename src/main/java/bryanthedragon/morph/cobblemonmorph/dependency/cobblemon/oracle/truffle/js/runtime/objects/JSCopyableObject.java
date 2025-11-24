
package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public interface JSCopyableObject {
    default public JSObject copy() {
        JSObject thisObj = (JSObject)((Object)this);
        return JSObjectUtil.copyProperties(thisObj.copyWithoutProperties(thisObj.getShape().getRoot()), thisObj);
    }
}

