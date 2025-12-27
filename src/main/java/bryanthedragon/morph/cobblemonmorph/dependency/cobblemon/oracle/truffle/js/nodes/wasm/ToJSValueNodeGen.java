package com.oracle.truffle.js.nodes.wasm;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;

@GeneratedBy(ToJSValueNode.class)
public final class ToJSValueNodeGen extends ToJSValueNode implements Introspection.Provider {
   private static final ToJSValueNodeGen.Uncached UNCACHED = new ToJSValueNodeGen.Uncached();

   private ToJSValueNodeGen() {
   }

   @Override
   public Object execute(Object arg0Value) {
      return this.convert(arg0Value);
   }

   @Override
   public NodeCost getCost() {
      return NodeCost.MONOMORPHIC;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      Object[] s = new Object[]{"convert", (byte)1, null};
      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static ToJSValueNode create() {
      return new ToJSValueNodeGen();
   }

   public static ToJSValueNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(ToJSValueNode.class)
   @DenyReplace
   private static final class Uncached extends ToJSValueNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public Object execute(Object arg0Value) {
         return this.convert(arg0Value);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @Override
      public boolean isAdoptable() {
         return false;
      }
   }
}
