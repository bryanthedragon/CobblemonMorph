package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.control.DeletePropertyNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Objects;

public abstract class JSArrayDeleteRangeNode extends JavaScriptBaseNode {
   protected final JSContext context;
   protected final boolean orThrow;

   protected JSArrayDeleteRangeNode(JSContext context, boolean orThrow) {
      this.context = Objects.requireNonNull(context);
      this.orThrow = orThrow;
   }

   public static JSArrayDeleteRangeNode create(JSContext context, boolean orThrow) {
      return JSArrayDeleteRangeNodeGen.create(context, orThrow);
   }

   public abstract void execute(JSDynamicObject array, ScriptArray arrayType, long start, long end);

   @Specialization(guards = {"cachedArrayType.isInstance(arrayType)", "!cachedArrayType.isHolesType()"}, limit = "5")
   protected void denseArray(
      JSDynamicObject array,
      ScriptArray arrayType,
      long start,
      long end,
      @Cached("arrayType") ScriptArray cachedArrayType,
      @Cached("create(orThrow, context)") DeletePropertyNode deletePropertyNode
   ) {
      for (long i = start; i < end; i++) {
         deletePropertyNode.executeEvaluated(array, i);
      }
   }

   @Specialization(guards = {"cachedArrayType.isInstance(arrayType)", "cachedArrayType.isHolesType()"}, limit = "5")
   protected void sparseArray(
      JSDynamicObject array,
      ScriptArray arrayType,
      long start,
      long end,
      @Cached("arrayType") ScriptArray cachedArrayType,
      @Cached("create(orThrow, context)") DeletePropertyNode deletePropertyNode,
      @Cached("create(context)") JSArrayNextElementIndexNode nextElementIndexNode
   ) {
      long pos = start;

      while (pos < end) {
         deletePropertyNode.executeEvaluated(array, pos);
         pos = nextElementIndexNode.executeLong(array, pos, end);
      }
   }

   @Specialization(replaces = {"denseArray", "sparseArray"})
   protected void doUncached(
      JSDynamicObject array,
      ScriptArray arrayType,
      long start,
      long end,
      @Cached("create(orThrow, context)") DeletePropertyNode deletePropertyNode,
      @Cached("create(context)") JSArrayNextElementIndexNode nextElementIndexNode
   ) {
      if (arrayType.isHolesType()) {
         this.sparseArray(array, arrayType, start, end, arrayType, deletePropertyNode, nextElementIndexNode);
      } else {
         this.denseArray(array, arrayType, start, end, arrayType, deletePropertyNode);
      }
   }
}
