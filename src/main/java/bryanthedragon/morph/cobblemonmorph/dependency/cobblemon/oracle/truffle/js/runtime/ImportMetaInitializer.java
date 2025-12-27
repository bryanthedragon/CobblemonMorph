package com.oracle.truffle.js.runtime;

import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;

public interface ImportMetaInitializer {
   void initializeImportMeta(JSDynamicObject importMeta, JSModuleRecord module);
}
