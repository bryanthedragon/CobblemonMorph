package com.oracle.truffle.js.nodes.wasm;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.builtins.JSDataViewObject;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;

@GeneratedBy(ExportByteSourceNode.class)
public final class ExportByteSourceNodeGen extends ExportByteSourceNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private ExportByteSourceNodeGen(JSContext context, String nonByteSourceMessage, String emptyByteSourceMessage) {
      super(context, nonByteSourceMessage, emptyByteSourceMessage);
   }

   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof JSArrayBufferObject) {
         JSArrayBufferObject arg0Value_ = (JSArrayBufferObject)arg0Value;
         return this.exportBuffer(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof JSTypedArrayObject) {
         JSTypedArrayObject arg0Value_ = (JSTypedArrayObject)arg0Value;
         return this.exportTypedArray(arg0Value_);
      } else if ((state_0 & 4) != 0 && arg0Value instanceof JSDataViewObject) {
         JSDataViewObject arg0Value_ = (JSDataViewObject)arg0Value;
         return this.exportDataView(arg0Value_);
      } else if ((state_0 & 8) != 0 && fallbackGuard_(state_0, arg0Value)) {
         return this.exportOther(arg0Value);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private Object executeAndSpecialize(Object arg0Value) {
      int state_0 = this.state_0_;
      if (arg0Value instanceof JSArrayBufferObject) {
         JSArrayBufferObject arg0Value_ = (JSArrayBufferObject)arg0Value;
         int var7;
         this.state_0_ = var7 = state_0 | 1;
         return this.exportBuffer(arg0Value_);
      } else if (arg0Value instanceof JSTypedArrayObject) {
         JSTypedArrayObject arg0Value_ = (JSTypedArrayObject)arg0Value;
         int var6;
         this.state_0_ = var6 = state_0 | 2;
         return this.exportTypedArray(arg0Value_);
      } else if (arg0Value instanceof JSDataViewObject) {
         JSDataViewObject arg0Value_ = (JSDataViewObject)arg0Value;
         int var5;
         this.state_0_ = var5 = state_0 | 4;
         return this.exportDataView(arg0Value_);
      } else {
         int var4;
         this.state_0_ = var4 = state_0 | 8;
         return this.exportOther(arg0Value);
      }
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[5];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"exportBuffer", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"exportTypedArray", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"exportDataView", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"exportOther", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      return Introspection.Provider.create(data);
   }

   private static boolean fallbackGuard_(int state_0, Object arg0Value) {
      if ((state_0 & 1) == 0 && arg0Value instanceof JSArrayBufferObject) {
         return false;
      } else {
         return (state_0 & 2) == 0 && arg0Value instanceof JSTypedArrayObject ? false : (state_0 & 4) != 0 || !(arg0Value instanceof JSDataViewObject);
      }
   }

   public static ExportByteSourceNode create(JSContext context, String nonByteSourceMessage, String emptyByteSourceMessage) {
      return new ExportByteSourceNodeGen(context, nonByteSourceMessage, emptyByteSourceMessage);
   }
}
