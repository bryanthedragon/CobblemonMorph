package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.runtime.util.UnmodifiableArrayList;
import com.oracle.truffle.js.runtime.util.UnmodifiablePropertyKeyList;
import java.util.ArrayList;
import java.util.List;

@GeneratedBy(ListSizeNode.class)
public final class ListSizeNodeGen extends ListSizeNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private ListSizeNodeGen() {
   }

   @Override
   public int execute(List<?> arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof UnmodifiableArrayList) {
         UnmodifiableArrayList<?> arg0Value_ = (UnmodifiableArrayList<?>)arg0Value;
         return ListSizeNode.unmodifiableArrayList(arg0Value_);
      } else if ((state_0 & 2) != 0 && arg0Value instanceof UnmodifiablePropertyKeyList) {
         UnmodifiablePropertyKeyList<?> arg0Value_ = (UnmodifiablePropertyKeyList<?>)arg0Value;
         return ListSizeNode.unmodifiablePropertyKeyList(arg0Value_);
      } else if ((state_0 & 4) != 0 && arg0Value instanceof ArrayList) {
         ArrayList<?> arg0Value_ = (ArrayList<?>)arg0Value;
         return ListSizeNode.arrayList(arg0Value_);
      } else if ((state_0 & 8) != 0 && fallbackGuard_(state_0, arg0Value)) {
         return ListSizeNode.list(arg0Value);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private int executeAndSpecialize(List<?> arg0Value) {
      int state_0 = this.state_0_;
      if (arg0Value instanceof UnmodifiableArrayList) {
         UnmodifiableArrayList<?> arg0Value_ = (UnmodifiableArrayList<?>)arg0Value;
         int var7;
         this.state_0_ = var7 = state_0 | 1;
         return ListSizeNode.unmodifiableArrayList(arg0Value_);
      } else if (arg0Value instanceof UnmodifiablePropertyKeyList) {
         UnmodifiablePropertyKeyList<?> arg0Value_ = (UnmodifiablePropertyKeyList<?>)arg0Value;
         int var6;
         this.state_0_ = var6 = state_0 | 2;
         return ListSizeNode.unmodifiablePropertyKeyList(arg0Value_);
      } else if (arg0Value instanceof ArrayList) {
         ArrayList<?> arg0Value_ = (ArrayList<?>)arg0Value;
         int var5;
         this.state_0_ = var5 = state_0 | 4;
         return ListSizeNode.arrayList(arg0Value_);
      } else {
         int var4;
         this.state_0_ = var4 = state_0 | 8;
         return ListSizeNode.list(arg0Value);
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
      Object[] s = new Object[]{"unmodifiableArrayList", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"unmodifiablePropertyKeyList", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"arrayList", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"list", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      return Introspection.Provider.create(data);
   }

   private static boolean fallbackGuard_(int state_0, List<?> arg0Value) {
      if ((state_0 & 1) == 0 && arg0Value instanceof UnmodifiableArrayList) {
         return false;
      } else {
         return (state_0 & 2) == 0 && arg0Value instanceof UnmodifiablePropertyKeyList ? false : (state_0 & 4) != 0 || !(arg0Value instanceof ArrayList);
      }
   }

   public static ListSizeNode create() {
      return new ListSizeNodeGen();
   }
}
