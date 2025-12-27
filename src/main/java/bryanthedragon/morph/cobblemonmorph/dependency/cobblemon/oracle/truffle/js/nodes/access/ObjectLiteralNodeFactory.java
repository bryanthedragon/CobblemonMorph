package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ObjectLiteralNode.class)
public final class ObjectLiteralNodeFactory {
   private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

   @GeneratedBy(ObjectLiteralNode.ComputedObjectLiteralDataMemberNode.class)
   public static final class ComputedObjectLiteralDataMemberNodeGen
      extends ObjectLiteralNode.ComputedObjectLiteralDataMemberNode
      implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private ObjectLiteralNodeFactory.ComputedObjectLiteralDataMemberNodeGen.NoFieldNoFunctionDef0Data noFieldNoFunctionDef0_cache;

      private ComputedObjectLiteralDataMemberNodeGen(
         JavaScriptNode key, boolean isStatic, int attributes, JavaScriptNode valueNode, boolean isField, boolean isAnonymousFunctionDefinition
      ) {
         super(key, isStatic, attributes, valueNode, isField, isAnonymousFunctionDefinition);
      }

      @ExplodeLoop
      @Override
      public void executeVoid(VirtualFrame frameValue, JSDynamicObject arg0Value, JSDynamicObject arg1Value, JSRealm arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (ObjectLiteralNodeFactory.ComputedObjectLiteralDataMemberNodeGen.NoFieldNoFunctionDef0Data s0_ = this.noFieldNoFunctionDef0_cache;
                  s0_ != null;
                  s0_ = s0_.next_
               ) {
                  if (s0_.dynamicObject_.accepts(arg0Value)) {
                     assert !this.isFieldOrStaticBlock;

                     assert !this.isAnonymousFunctionDefinition;

                     assert this.setFunctionName == null;

                     assert !ObjectLiteralNode.ObjectLiteralMemberNode.isMethodNode(this.valueNode);

                     this.doNoFieldNoFunctionDef(frameValue, arg0Value, arg1Value, arg2Value, s0_.dynamicObject_);
                     return;
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               DynamicObjectLibrary noFieldNoFunctionDef1_dynamicObject__ = ObjectLiteralNodeFactory.DYNAMIC_OBJECT_LIBRARY_.getUncached();

               assert !this.isFieldOrStaticBlock;

               assert !this.isAnonymousFunctionDefinition;

               assert this.setFunctionName == null;

               assert !ObjectLiteralNode.ObjectLiteralMemberNode.isMethodNode(this.valueNode);

               this.doNoFieldNoFunctionDef(frameValue, arg0Value, arg1Value, arg2Value, noFieldNoFunctionDef1_dynamicObject__);
               return;
            }

            if ((state_0 & 4) != 0) {
               this.doGeneric(frameValue, arg0Value, arg1Value, arg2Value);
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(frameValue, arg0Value, arg1Value, arg2Value);
      }

      private void executeAndSpecialize(VirtualFrame frameValue, JSDynamicObject arg0Value, JSDynamicObject arg1Value, JSRealm arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               ObjectLiteralNodeFactory.ComputedObjectLiteralDataMemberNodeGen.NoFieldNoFunctionDef0Data s0_ = this.noFieldNoFunctionDef0_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null) {
                     if (s0_.dynamicObject_.accepts(arg0Value)) {
                        assert !this.isFieldOrStaticBlock;

                        assert !this.isAnonymousFunctionDefinition;

                        assert this.setFunctionName == null;

                        assert !ObjectLiteralNode.ObjectLiteralMemberNode.isMethodNode(this.valueNode);
                        break;
                     }

                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null
                  && !this.isFieldOrStaticBlock
                  && !this.isAnonymousFunctionDefinition
                  && this.setFunctionName == null
                  && !ObjectLiteralNode.ObjectLiteralMemberNode.isMethodNode(this.valueNode)
                  && count0_ < 3) {
                  s0_ = super.insert(
                     new ObjectLiteralNodeFactory.ComputedObjectLiteralDataMemberNodeGen.NoFieldNoFunctionDef0Data(this.noFieldNoFunctionDef0_cache)
                  );
                  s0_.dynamicObject_ = s0_.insertAccessor(ObjectLiteralNodeFactory.DYNAMIC_OBJECT_LIBRARY_.create(arg0Value));
                  VarHandle.storeStoreFence();
                  this.noFieldNoFunctionDef0_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  this.doNoFieldNoFunctionDef(frameValue, arg0Value, arg1Value, arg2Value, s0_.dynamicObject_);
                  return;
               }
            }

            DynamicObjectLibrary noFieldNoFunctionDef1_dynamicObject__ = null;
            if (this.isFieldOrStaticBlock
               || this.isAnonymousFunctionDefinition
               || this.setFunctionName != null
               || ObjectLiteralNode.ObjectLiteralMemberNode.isMethodNode(this.valueNode)) {
               int var16;
               this.state_0_ = var16 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               this.doGeneric(frameValue, arg0Value, arg1Value, arg2Value);
               return;
            }

            noFieldNoFunctionDef1_dynamicObject__ = ObjectLiteralNodeFactory.DYNAMIC_OBJECT_LIBRARY_.getUncached();
            int var17;
            this.exclude_ = var17 = exclude | 1;
            this.noFieldNoFunctionDef0_cache = null;
            state_0 &= -2;
            int var15;
            this.state_0_ = var15 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            this.doNoFieldNoFunctionDef(frameValue, arg0Value, arg1Value, arg2Value, noFieldNoFunctionDef1_dynamicObject__);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               ObjectLiteralNodeFactory.ComputedObjectLiteralDataMemberNodeGen.NoFieldNoFunctionDef0Data s0_ = this.noFieldNoFunctionDef0_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"doNoFieldNoFunctionDef", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ObjectLiteralNodeFactory.ComputedObjectLiteralDataMemberNodeGen.NoFieldNoFunctionDef0Data s0_ = this.noFieldNoFunctionDef0_cache;
               s0_ != null;
               s0_ = s0_.next_
            ) {
               cached.add(Arrays.asList(s0_.dynamicObject_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doNoFieldNoFunctionDef", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doGeneric", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectLiteralNode.ComputedObjectLiteralDataMemberNode create(
         JavaScriptNode key, boolean isStatic, int attributes, JavaScriptNode valueNode, boolean isField, boolean isAnonymousFunctionDefinition
      ) {
         return new ObjectLiteralNodeFactory.ComputedObjectLiteralDataMemberNodeGen(
            key, isStatic, attributes, valueNode, isField, isAnonymousFunctionDefinition
         );
      }

      @GeneratedBy(ObjectLiteralNode.ComputedObjectLiteralDataMemberNode.class)
      private static final class NoFieldNoFunctionDef0Data extends Node {
         @Node.Child
         ObjectLiteralNodeFactory.ComputedObjectLiteralDataMemberNodeGen.NoFieldNoFunctionDef0Data next_;
         @Node.Child
         DynamicObjectLibrary dynamicObject_;

         NoFieldNoFunctionDef0Data(ObjectLiteralNodeFactory.ComputedObjectLiteralDataMemberNodeGen.NoFieldNoFunctionDef0Data next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }
}
