
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.HasOnlyShapePropertiesNodeGen;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSObjectPrototype;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

@ImportStatic(value={JSObject.class, JSConfig.class})
public abstract class HasOnlyShapePropertiesNode
extends JavaScriptBaseNode {
    protected HasOnlyShapePropertiesNode() {
    }

    public static HasOnlyShapePropertiesNode create() {
        return HasOnlyShapePropertiesNodeGen.create();
    }

    public final boolean execute(JSDynamicObject object) {
        return this.execute(object, JSObject.getJSClass(object));
    }

    public abstract boolean execute(JSDynamicObject var1, JSClass var2);

    @Specialization(guards={"jsclass == cachedJSClass", "!isJSObjectPrototype(cachedJSClass)"}, limit="InteropLibraryLimit")
    static boolean doCached(JSDynamicObject object, JSClass jsclass, @Cached(value="jsclass") JSClass cachedJSClass) {
        return cachedJSClass.hasOnlyShapeProperties(object);
    }

    @Specialization(guards={"isJSObjectPrototype(jsclass)"})
    static boolean doObjectPrototype(JSDynamicObject object, JSClass jsclass, @Cached(value="getJSContext(object)") JSContext context) {
        if (context.getArrayPrototypeNoElementsAssumption().isValid()) {
            assert (jsclass.hasOnlyShapeProperties(object));
            return true;
        }
        return JSObjectPrototype.INSTANCE.hasOnlyShapeProperties(object);
    }

    @Specialization(replaces={"doCached", "doObjectPrototype"})
    static boolean doUncached(JSDynamicObject object, JSClass jsclass) {
        return jsclass.hasOnlyShapeProperties(object);
    }

    static boolean isJSObjectPrototype(JSClass jsclass) {
        return jsclass == JSObjectPrototype.INSTANCE;
    }
}

