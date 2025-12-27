package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.util.UnmodifiableArrayList;
import com.oracle.truffle.js.runtime.util.UnmodifiablePropertyKeyList;
import java.util.ArrayList;
import java.util.List;

public abstract class ListSizeNode extends JavaScriptBaseNode {
   protected ListSizeNode() {
   }

   public static ListSizeNode create() {
      return ListSizeNodeGen.create();
   }

   public abstract int execute(List<?> list);

   @Specialization
   static int unmodifiableArrayList(UnmodifiableArrayList<?> list) {
      return list.size();
   }

   @Specialization
   static int unmodifiablePropertyKeyList(UnmodifiablePropertyKeyList<?> list) {
      return list.size();
   }

   @CompilerDirectives.TruffleBoundary(allowInlining = true)
   @Specialization
   static int arrayList(ArrayList<?> list) {
      return list.size();
   }

   @Fallback
   static int list(List<?> list) {
      return Boundaries.listSize(list);
   }
}
