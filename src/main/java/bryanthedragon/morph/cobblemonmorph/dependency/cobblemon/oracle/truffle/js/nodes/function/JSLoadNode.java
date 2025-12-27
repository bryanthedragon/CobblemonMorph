package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;

public abstract class JSLoadNode extends JavaScriptBaseNode {
   protected final JSContext context;

   public static JSLoadNode create(JSContext context) {
      return JSLoadNodeGen.create(context);
   }

   protected JSLoadNode(JSContext context) {
      this.context = context;
   }

   public abstract Object executeLoad(Source source, JSRealm realm);

   protected static CallTarget loadScript(Source source, JSRealm realm) {
      return realm.getEnv().parsePublic(source);
   }

   @CompilerDirectives.TruffleBoundary(allowInlining = true)
   static boolean equals(Source source, Source cachedSource) {
      return source.equals(cachedSource);
   }

   @Specialization(guards = {"cachedSource.isCached()", "equals(source, cachedSource)"}, limit = "1")
   static Object cachedLoad(
      Source source,
      JSRealm realm,
      @Cached @Cached.Shared("importValue") ImportValueNode importValue,
      @Cached("source") Source cachedSource,
      @Cached("create(loadScript(source, realm))") DirectCallNode callNode
   ) {
      return importValue.executeWithTarget(callNode.call(JSArguments.EMPTY_ARGUMENTS_ARRAY));
   }

   @Specialization(replaces = "cachedLoad")
   static Object uncachedLoad(
      Source source, JSRealm realm, @Cached @Cached.Shared("importValue") ImportValueNode importValue, @Cached IndirectCallNode callNode
   ) {
      return importValue.executeWithTarget(callNode.call(loadScript(source, realm), JSArguments.EMPTY_ARGUMENTS_ARRAY));
   }
}
