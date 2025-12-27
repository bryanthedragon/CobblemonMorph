package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.array.JSGetLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.unary.TypeOfNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSLocaleObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToCanonicalizedLocaleListNode.class)
public final class JSToCanonicalizedLocaleListNodeGen extends JSToCanonicalizedLocaleListNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private TruffleString.EqualNode equalsNode;
   @Node.Child
   private TruffleString.ToJavaStringNode toJavaStringNode;
   @Node.Child
   private JSToObjectNode otherType_toObjectNode_;
   @Node.Child
   private JSGetLengthNode otherType_getLengthNode_;
   @Node.Child
   private JSHasPropertyNode otherType_hasPropertyNode_;
   @Node.Child
   private TypeOfNode otherType_typeOfNode_;
   @Node.Child
   private JSToStringNode otherType_toStringNode_;
   @Node.Child
   private InteropLibrary foreignType_interop_;
   @Node.Child
   private TypeOfNode foreignType_typeOfNode_;
   @Node.Child
   private JSToStringNode foreignType_toStringNode_;

   private JSToCanonicalizedLocaleListNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public String[] executeLanguageTags(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof TruffleString) {
         TruffleString arg0Value_ = (TruffleString)arg0Value;
         return this.doTString(arg0Value_);
      } else if ((state_0 & 2) != 0 && JSGuards.isUndefined(arg0Value)) {
         return this.doUndefined(arg0Value);
      } else if ((state_0 & 4) != 0 && arg0Value instanceof JSLocaleObject) {
         JSLocaleObject arg0Value_ = (JSLocaleObject)arg0Value;
         return this.doLocale(arg0Value_);
      } else {
         if ((state_0 & 24) != 0) {
            if ((state_0 & 8) != 0
               && !JSGuards.isForeignObject(arg0Value)
               && !JSGuards.isString(arg0Value)
               && !JSGuards.isUndefined(arg0Value)
               && !JSGuards.isJSLocale(arg0Value)) {
               return this.doOtherType(
                  arg0Value,
                  this.otherType_toObjectNode_,
                  this.otherType_getLengthNode_,
                  this.otherType_hasPropertyNode_,
                  this.otherType_typeOfNode_,
                  this.otherType_toStringNode_,
                  this.equalsNode,
                  this.toJavaStringNode
               );
            }

            if ((state_0 & 16) != 0 && JSGuards.isForeignObject(arg0Value)) {
               return this.doForeignType(
                  arg0Value, this.foreignType_interop_, this.foreignType_typeOfNode_, this.foreignType_toStringNode_, this.equalsNode, this.toJavaStringNode
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private String[] executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      String[] arg0Value_;
      try {
         int state_0 = this.state_0_;
         if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_x = (TruffleString)arg0Value;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doTString(arg0Value_x);
         }

         if (JSGuards.isUndefined(arg0Value)) {
            int var13;
            this.state_0_ = var13 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doUndefined(arg0Value);
         }

         if (arg0Value instanceof JSLocaleObject) {
            JSLocaleObject arg0Value_x = (JSLocaleObject)arg0Value;
            int var12;
            this.state_0_ = var12 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doLocale(arg0Value_x);
         }

         if (JSGuards.isForeignObject(arg0Value) || JSGuards.isString(arg0Value) || JSGuards.isUndefined(arg0Value) || JSGuards.isJSLocale(arg0Value)) {
            if (JSGuards.isForeignObject(arg0Value)) {
               this.foreignType_interop_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
               this.foreignType_typeOfNode_ = super.insert(TypeOfNode.create());
               this.foreignType_toStringNode_ = super.insert(JSToStringNode.create());
               this.equalsNode = super.insert(this.equalsNode == null ? TruffleString.EqualNode.create() : this.equalsNode);
               this.toJavaStringNode = super.insert(this.toJavaStringNode == null ? TruffleString.ToJavaStringNode.create() : this.toJavaStringNode);
               int var11;
               this.state_0_ = var11 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doForeignType(
                  arg0Value, this.foreignType_interop_, this.foreignType_typeOfNode_, this.foreignType_toStringNode_, this.equalsNode, this.toJavaStringNode
               );
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
         }

         this.otherType_toObjectNode_ = super.insert(JSToObjectNode.createToObject(this.context));
         this.otherType_getLengthNode_ = super.insert(JSGetLengthNode.create(this.context));
         this.otherType_hasPropertyNode_ = super.insert(JSHasPropertyNode.create());
         this.otherType_typeOfNode_ = super.insert(TypeOfNode.create());
         this.otherType_toStringNode_ = super.insert(JSToStringNode.create());
         this.equalsNode = super.insert(this.equalsNode == null ? TruffleString.EqualNode.create() : this.equalsNode);
         this.toJavaStringNode = super.insert(this.toJavaStringNode == null ? TruffleString.ToJavaStringNode.create() : this.toJavaStringNode);
         int var10;
         this.state_0_ = var10 = state_0 | 8;
         lock.unlock();
         hasLock = false;
         arg0Value_ = this.doOtherType(
            arg0Value,
            this.otherType_toObjectNode_,
            this.otherType_getLengthNode_,
            this.otherType_hasPropertyNode_,
            this.otherType_typeOfNode_,
            this.otherType_toStringNode_,
            this.equalsNode,
            this.toJavaStringNode
         );
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return arg0Value_;
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
      Object[] data = new Object[6];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doTString", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doUndefined", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doLocale", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doOtherType", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(
            Arrays.asList(
               this.otherType_toObjectNode_,
               this.otherType_getLengthNode_,
               this.otherType_hasPropertyNode_,
               this.otherType_typeOfNode_,
               this.otherType_toStringNode_,
               this.equalsNode,
               this.toJavaStringNode
            )
         );
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doForeignType", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(
            Arrays.asList(this.foreignType_interop_, this.foreignType_typeOfNode_, this.foreignType_toStringNode_, this.equalsNode, this.toJavaStringNode)
         );
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToCanonicalizedLocaleListNode create(JSContext context) {
      return new JSToCanonicalizedLocaleListNodeGen(context);
   }
}
