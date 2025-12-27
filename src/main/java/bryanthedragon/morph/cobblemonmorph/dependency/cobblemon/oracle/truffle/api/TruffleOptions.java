package com.oracle.truffle.api;

import com.oracle.truffle.api.nodes.NodeCost;
import java.security.AccessController;

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
      return kind == null ? null : NodeCost.valueOf(kind);
   }

   static {
      Truffle.getRuntime();
      TruffleOptions$1GetOptions options = new TruffleOptions$1GetOptions();
      AccessController.doPrivileged(options);
      TraceRewrites = options.traceRewrites;
      DetailedRewriteReasons = options.detailedRewriteReasons;
      AOT = options.aot;
      TraceRewritesFilterClass = options.traceRewritesFilterClass;
      TraceRewritesFilterFromCost = options.traceRewritesFilterFromCost;
      TraceRewritesFilterToCost = options.traceRewritesFilterToCost;
   }
}
