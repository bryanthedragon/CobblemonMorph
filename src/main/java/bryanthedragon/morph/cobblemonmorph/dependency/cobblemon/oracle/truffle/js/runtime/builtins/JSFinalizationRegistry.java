
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.FinalizationRegistryPrototypeBuiltins;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.FinalizationRecord;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFinalizationRegistryObject;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class JSFinalizationRegistry
extends JSNonProxy
implements JSConstructorFactory.Default,
PrototypeSupplier {
    public static final JSFinalizationRegistry INSTANCE = new JSFinalizationRegistry();
    public static final TruffleString CLASS_NAME = Strings.constant("FinalizationRegistry");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("FinalizationRegistry.prototype");
    public static final HiddenKey FINALIZATION_REGISTRY_ID = new HiddenKey("FinalizationRegistry");

    private JSFinalizationRegistry() {
    }

    public static JSFinalizationRegistryObject create(JSContext context, JSRealm realm, Object cleanupCallback) {
        JSObjectFactory factory = context.getFinalizationRegistryFactory();
        JSFinalizationRegistryObject obj = factory.initProto(new JSFinalizationRegistryObject(factory.getShape(realm), cleanupCallback, new ArrayList<FinalizationRecord>(), JSFinalizationRegistry.createReferenceQueue()), realm);
        context.registerFinalizationRegistry(obj);
        context.trackAllocation(obj);
        return obj;
    }

    @CompilerDirectives.TruffleBoundary
    private static ReferenceQueue<Object> createReferenceQueue() {
        return new ReferenceQueue<Object>();
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
        JSContext ctx = realm.getContext();
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, prototype, ctor);
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, FinalizationRegistryPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putToStringTag(prototype, CLASS_NAME);
        return prototype;
    }

    @Override
    public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
        return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm);
    }

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return this.getClassName();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
        return Strings.addBrackets(this.getClassName());
    }

    public static boolean isJSFinalizationRegistry(Object obj) {
        return obj instanceof JSFinalizationRegistryObject;
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getFinalizationRegistryPrototype();
    }

    @CompilerDirectives.TruffleBoundary
    public static void appendToCells(JSFinalizationRegistryObject finalizationRegistry, Object target, Object holdings, Object unregisterToken) {
        List<FinalizationRecord> cells = finalizationRegistry.getCells();
        ReferenceQueue<Object> queue = finalizationRegistry.getReferenceQueue();
        WeakReference<Object> weakTarget = new WeakReference<Object>(target, queue);
        cells.add(new FinalizationRecord(weakTarget, holdings, unregisterToken));
    }

    @CompilerDirectives.TruffleBoundary
    public static boolean removeFromCells(JSFinalizationRegistryObject finalizationRegistry, Object unregisterToken) {
        List<FinalizationRecord> cells = finalizationRegistry.getCells();
        boolean removed = false;
        Iterator<FinalizationRecord> iterator = cells.iterator();
        while (iterator.hasNext()) {
            FinalizationRecord record = iterator.next();
            if (!JSRuntime.isSameValue(record.getUnregisterToken().get(), unregisterToken)) continue;
            iterator.remove();
            removed = true;
        }
        return removed;
    }

    @CompilerDirectives.TruffleBoundary
    public static void cleanupFinalizationRegistry(JSFinalizationRegistryObject finalizationRegistry, Object callbackArg) {
        FinalizationRecord cell;
        Object callback;
        Object object = callback = callbackArg == Undefined.instance ? finalizationRegistry.getCleanupCallback() : callbackArg;
        while ((cell = JSFinalizationRegistry.removeCellEmptyTarget(finalizationRegistry)) != null) {
            assert (cell.getWeakRefTarget().get() == null);
            JSRuntime.call(callback, Undefined.instance, new Object[]{cell.getHeldValue()});
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static FinalizationRecord removeCellEmptyTarget(JSFinalizationRegistryObject finalizationRegistry) {
        List<FinalizationRecord> cells = finalizationRegistry.getCells();
        for (int i = 0; i < cells.size(); ++i) {
            FinalizationRecord record = cells.get(i);
            if (record.getWeakRefTarget().get() != null) continue;
            cells.remove(i);
            return record;
        }
        return null;
    }

    public static void hostCleanupFinalizationRegistry(JSFinalizationRegistryObject finalizationRegistry) {
        boolean performCleanup;
        ReferenceQueue<Object> queue = finalizationRegistry.getReferenceQueue();
        boolean queueNotEmpty = queue.poll() != null;
        boolean bl = performCleanup = queueNotEmpty || JSObject.getJSContext(finalizationRegistry).getContextOptions().isTestV8Mode();
        if (performCleanup) {
            Reference<Object> o;
            while ((o = queue.poll()) != null) {
            }
            JSFinalizationRegistry.cleanupFinalizationRegistry(finalizationRegistry, Undefined.instance);
        }
    }
}

