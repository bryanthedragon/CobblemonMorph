
package com.oracle.truffle.api;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.nodes.NodeCost;
import java.security.AccessController;
import java.security.PrivilegedAction;

public final class TruffleOptions {
    public static final boolean TraceRewrites;
    public static final boolean DetailedRewriteReasons;
    public static final String TraceRewritesFilterClass;
    public static final NodeCost TraceRewritesFilterFromCost;
    public static final NodeCost TraceRewritesFilterToCost;
    public static final boolean AOT;

    private TruffleOptions() {
    }

    private static NodeCost parseNodeInfoKind(String kind) {
        if (kind == null) {
            return null;
        }
        return NodeCost.valueOf(kind);
    }

    static {
        Truffle.getRuntime();
        class GetOptions
        implements PrivilegedAction<Void> {
            boolean aot;
            boolean traceRewrites;
            boolean detailedRewriteReasons;
            String traceRewritesFilterClass;
            NodeCost traceRewritesFilterFromCost;
            NodeCost traceRewritesFilterToCost;

            GetOptions() {
            }

            @Override
            public Void run() {
                this.aot = Boolean.getBoolean("com.oracle.graalvm.isaot");
                this.traceRewrites = Boolean.getBoolean("truffle.TraceRewrites");
                this.detailedRewriteReasons = Boolean.getBoolean("truffle.DetailedRewriteReasons");
                this.traceRewritesFilterClass = System.getProperty("truffle.TraceRewritesFilterClass");
                this.traceRewritesFilterFromCost = TruffleOptions.parseNodeInfoKind(System.getProperty("truffle.TraceRewritesFilterFromCost"));
                this.traceRewritesFilterToCost = TruffleOptions.parseNodeInfoKind(System.getProperty("truffle.TraceRewritesFilterToCost"));
                return null;
            }
        }
        GetOptions options = new GetOptions();
        AccessController.doPrivileged(options);
        TraceRewrites = options.traceRewrites;
        DetailedRewriteReasons = options.detailedRewriteReasons;
        AOT = options.aot;
        TraceRewritesFilterClass = options.traceRewritesFilterClass;
        TraceRewritesFilterFromCost = options.traceRewritesFilterFromCost;
        TraceRewritesFilterToCost = options.traceRewritesFilterToCost;
    }
}

