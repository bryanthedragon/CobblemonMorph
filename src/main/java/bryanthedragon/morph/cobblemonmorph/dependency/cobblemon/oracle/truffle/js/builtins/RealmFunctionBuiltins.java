package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class RealmFunctionBuiltins extends JSBuiltinsContainer.SwitchEnum<RealmFunctionBuiltins.RealmFunction> {
   public static final JSBuiltinsContainer BUILTINS = new RealmFunctionBuiltins();

   protected RealmFunctionBuiltins() {
      super(JSRealm.REALM_BUILTIN_CLASS_NAME, RealmFunctionBuiltins.RealmFunction.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, RealmFunctionBuiltins.RealmFunction builtinEnum) {
      switch (builtinEnum) {
         case create:
         case createAllowCrossRealmAccess:
            return RealmFunctionBuiltinsFactory.RealmCreateNodeGen.create(context, builtin, args().fixedArgs(0).createArgumentNodes(context));
         case global:
            return RealmFunctionBuiltinsFactory.RealmGlobalNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case dispose:
            return RealmFunctionBuiltinsFactory.RealmDisposeNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case current:
            return RealmFunctionBuiltinsFactory.RealmCurrentNodeGen.create(context, builtin, args().fixedArgs(0).createArgumentNodes(context));
         case eval:
            return RealmFunctionBuiltinsFactory.RealmEvalNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context));
         case owner:
            return RealmFunctionBuiltinsFactory.RealmOwnerNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case detachGlobal:
            return RealmFunctionBuiltinsFactory.RealmDetachGlobalNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case navigate:
            return RealmFunctionBuiltinsFactory.RealmNavigateNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         default:
            return null;
      }
   }

   protected static JSRealm topLevelRealm(Node node) {
      return JSRealm.getMain(node);
   }

   protected static int toRealmIndexOrThrow(JSRealm topLevelRealm, Object index) {
      int realmIdx = JSRuntime.intValue(JSRuntime.toNumber(index));
      if (realmIdx < 0) {
         throw Errors.createTypeError("Invalid realm index");
      } else {
         JSRealm jsrealm = topLevelRealm.getFromRealmList(realmIdx);
         if (jsrealm == null) {
            throw Errors.createTypeError("Invalid realm index");
         } else {
            return realmIdx;
         }
      }
   }

   public abstract static class RealmCreateNode extends JSBuiltinNode {
      public RealmCreateNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @CompilerDirectives.TruffleBoundary
      @Specialization
      protected Object createRealm() {
         JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
         JSRealm newRealm = topLevelRealm.createChildRealm();
         return topLevelRealm.getIndexFromRealmList(newRealm);
      }
   }

   public abstract static class RealmCurrentNode extends JSBuiltinNode {
      public RealmCurrentNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @CompilerDirectives.TruffleBoundary
      @Specialization
      protected Object current() {
         JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
         JSRealm currentRealm = topLevelRealm.getCurrentV8Realm();
         return topLevelRealm.getIndexFromRealmList(currentRealm);
      }
   }

   public abstract static class RealmDetachGlobalNode extends JSBuiltinNode {
      public RealmDetachGlobalNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @CompilerDirectives.TruffleBoundary
      @Specialization
      protected Object detachGlobal(Object index) {
         JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
         int realmIndex = RealmFunctionBuiltins.toRealmIndexOrThrow(topLevelRealm, index);
         JSRealm realm = topLevelRealm.getFromRealmList(realmIndex);
         JSObject.setPrototype(realm.getGlobalObject(), Null.instance);
         realm.setGlobalObject(Undefined.instance);
         return Undefined.instance;
      }
   }

   public abstract static class RealmDisposeNode extends JSBuiltinNode {
      public RealmDisposeNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @CompilerDirectives.TruffleBoundary
      @Specialization
      protected Object dispose(Object index) {
         JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
         int realmIndex = RealmFunctionBuiltins.toRealmIndexOrThrow(topLevelRealm, index);
         topLevelRealm.removeFromRealmList(realmIndex);
         return Undefined.instance;
      }
   }

   public abstract static class RealmEvalNode extends JSBuiltinNode {
      public RealmEvalNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @CompilerDirectives.TruffleBoundary
      @Specialization
      protected Object eval(Object index, Object code) {
         JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
         int realmIndex = RealmFunctionBuiltins.toRealmIndexOrThrow(topLevelRealm, index);
         JSRealm selectedRealm = topLevelRealm.getFromRealmList(realmIndex);
         String sourceText = JSRuntime.toJavaString(code);
         Source source = Source.newBuilder("js", sourceText, "<eval>").build();
         JSRealm currentV8Realm = topLevelRealm.getCurrentV8Realm();
         topLevelRealm.setCurrentV8Realm(selectedRealm);

         Object var10;
         try {
            ScriptNode script = this.getContext().getEvaluator().parseEval(this.getContext(), this, source);
            var10 = script.runEval(IndirectCallNode.getUncached(), selectedRealm);
         } finally {
            topLevelRealm.setCurrentV8Realm(currentV8Realm);
         }

         return var10;
      }
   }

   public static enum RealmFunction implements BuiltinEnum<RealmFunctionBuiltins.RealmFunction> {
      create(0),
      createAllowCrossRealmAccess(0),
      global(1),
      dispose(1),
      current(0),
      eval(2),
      owner(1),
      detachGlobal(1),
      navigate(1);

      private final int length;

      private RealmFunction(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }

   public abstract static class RealmGlobalNode extends JSBuiltinNode {
      public RealmGlobalNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @CompilerDirectives.TruffleBoundary
      @Specialization
      protected Object global(Object index) {
         JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
         int realmIndex = RealmFunctionBuiltins.toRealmIndexOrThrow(topLevelRealm, index);
         JSRealm jsrealm = topLevelRealm.getFromRealmList(realmIndex);
         return jsrealm.getGlobalObject();
      }
   }

   public abstract static class RealmNavigateNode extends JSBuiltinNode {
      public RealmNavigateNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @CompilerDirectives.TruffleBoundary
      @Specialization
      protected Object navigate(Object index) {
         JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
         int realmIndex = RealmFunctionBuiltins.toRealmIndexOrThrow(topLevelRealm, index);
         JSRealm realm = topLevelRealm.getFromRealmList(realmIndex);
         JSObject.setPrototype(realm.getGlobalObject(), Null.instance);
         realm.setGlobalObject(Undefined.instance);
         JSRealm newRealm = topLevelRealm.createChildRealm();
         int tempIdx = topLevelRealm.getIndexFromRealmList(newRealm);
         topLevelRealm.removeFromRealmList(tempIdx);
         topLevelRealm.setInRealmList(tempIdx, newRealm);
         return Undefined.instance;
      }
   }

   public abstract static class RealmOwnerNode extends JSBuiltinNode {
      public RealmOwnerNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @CompilerDirectives.TruffleBoundary
      @Specialization
      protected Object owner(Object object) {
         JSRealm realm = null;
         if (JSObject.isJSObject(object)) {
            realm = creationRealm((JSObject)object);
            JSRealm topLevelRealm = RealmFunctionBuiltins.topLevelRealm(this);
            int index = topLevelRealm.getIndexFromRealmList(realm);
            return index == -1 ? Undefined.instance : index;
         } else {
            throw Errors.createError("Invalid argument");
         }
      }

      private static JSRealm creationRealm(JSObject object) {
         return JSFunction.isJSFunction(object) ? JSFunction.getRealm(object) : creationRealmFromConstructor(object);
      }

      private static JSRealm creationRealmFromConstructor(JSObject object) {
         Object nonProxy = JSProxy.getTargetNonProxy(object);
         if (nonProxy instanceof JSObject) {
            JSDynamicObject prototype = JSObject.getPrototype((JSDynamicObject)nonProxy);
            if (prototype != Null.instance) {
               Object constructor = JSRuntime.getDataProperty(prototype, JSObject.CONSTRUCTOR);
               if (JSFunction.isJSFunction(constructor)) {
                  return JSFunction.getRealm((JSFunctionObject)constructor);
               }
            }
         }

         return null;
      }
   }
}
