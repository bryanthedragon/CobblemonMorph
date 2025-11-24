
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;

public interface ImportMetaInitializer {
    public void initializeImportMeta(JSDynamicObject var1, JSModuleRecord var2);
}

