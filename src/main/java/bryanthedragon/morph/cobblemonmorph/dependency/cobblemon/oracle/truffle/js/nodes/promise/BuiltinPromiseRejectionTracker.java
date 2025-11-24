
package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSContextOptions;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.PromiseRejectionTracker;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.io.PrintWriter;
import java.util.Deque;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class BuiltinPromiseRejectionTracker
implements PromiseRejectionTracker {
    private final JSContext context;
    private final JSContextOptions.UnhandledRejectionsTrackingMode mode;
    private final Set<JSDynamicObject> pendingUnhandledRejections = new LinkedHashSet<JSDynamicObject>();
    private final Deque<PromiseChainInfoRecord> asyncHandledRejections = new LinkedList<PromiseChainInfoRecord>();
    private final Map<JSDynamicObject, PromiseChainInfoRecord> maybeUnhandledPromises = new WeakHashMap<JSDynamicObject, PromiseChainInfoRecord>();

    public BuiltinPromiseRejectionTracker(JSContext context, JSContextOptions.UnhandledRejectionsTrackingMode mode) {
        assert (mode != JSContextOptions.UnhandledRejectionsTrackingMode.NONE);
        this.context = context;
        this.mode = mode;
    }

    @Override
    public void promiseRejected(JSDynamicObject promise, Object reason) {
        this.maybeUnhandledPromises.put(promise, new PromiseChainInfoRecord(reason, false));
        this.pendingUnhandledRejections.add(promise);
        this.context.getLanguage().getPromiseJobsQueueEmptyAssumption().invalidate("Potential unhandled rejection");
    }

    @Override
    public void promiseRejectionHandled(JSDynamicObject promise) {
        PromiseChainInfoRecord promiseInfo = this.maybeUnhandledPromises.get(promise);
        if (promiseInfo != null) {
            this.maybeUnhandledPromises.remove(promise);
            this.pendingUnhandledRejections.remove(promise);
            if (promiseInfo.warned) {
                this.asyncHandledRejections.add(promiseInfo);
            }
        }
    }

    @Override
    public void promiseRejectedAfterResolved(JSDynamicObject promise, Object value2) {
    }

    @Override
    public void promiseResolvedAfterResolved(JSDynamicObject promise, Object value2) {
    }

    @Override
    public void promiseReactionJobsProcessed() {
        JSRealm realm = JSRealm.get(null);
        assert (realm.getContext() == this.context);
        while (!this.asyncHandledRejections.isEmpty()) {
            PromiseChainInfoRecord info = this.asyncHandledRejections.removeFirst();
            PrintWriter out = realm.getErrorWriter();
            out.println("[GraalVM JavaScript Warning] Promise rejection was handled asynchronously: " + JSRuntime.safeToString(info.reason));
            out.flush();
        }
        while (!this.pendingUnhandledRejections.isEmpty()) {
            JSDynamicObject unhandled = this.pendingUnhandledRejections.iterator().next();
            this.pendingUnhandledRejections.remove(unhandled);
            PromiseChainInfoRecord info = this.maybeUnhandledPromises.get(unhandled);
            if (info == null) continue;
            info.warned = true;
            if (this.mode == JSContextOptions.UnhandledRejectionsTrackingMode.HANDLER) {
                Object handler = realm.getUnhandledPromiseRejectionHandler();
                if (handler == null) continue;
                JSRuntime.call(handler, Undefined.instance, new Object[]{info.reason, unhandled});
                continue;
            }
            if (this.mode == JSContextOptions.UnhandledRejectionsTrackingMode.WARN) {
                PrintWriter out = realm.getErrorWriter();
                out.println("[GraalVM JavaScript Warning] Unhandled promise rejection: " + JSRuntime.safeToString(info.reason));
                out.flush();
                continue;
            }
            assert (this.mode == JSContextOptions.UnhandledRejectionsTrackingMode.THROW);
            throw Errors.createError("Unhandled promise rejection: " + JSRuntime.safeToString(info.reason));
        }
    }

    private static class PromiseChainInfoRecord {
        private final Object reason;
        private boolean warned;

        PromiseChainInfoRecord(Object reason, boolean warned) {
            this.reason = reason;
            this.warned = warned;
        }
    }
}

