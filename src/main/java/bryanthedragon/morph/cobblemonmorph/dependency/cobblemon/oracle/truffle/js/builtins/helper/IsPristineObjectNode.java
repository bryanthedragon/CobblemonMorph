package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.RegExpPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public abstract class IsPristineObjectNode extends JavaScriptBaseNode {
   private final JSClass jsClass;
   private final Shape initialPrototypeShape;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final Object[] propertyKeys;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final Assumption[] propertyFinalAssumptions;
   @Node.Child
   private GetPrototypeNode getPrototypeNode = GetPrototypeNode.create();

   IsPristineObjectNode(JSClass jsClass, Shape initialPrototypeShape, Object... propertyKeys) {
      assert jsClass != JSProxy.INSTANCE : "not supported because getting the prototype of proxy objects can have side effects";

      this.jsClass = jsClass;
      this.initialPrototypeShape = initialPrototypeShape;
      this.propertyKeys = propertyKeys;
      this.propertyFinalAssumptions = new Assumption[propertyKeys.length];

      for (int i = 0; i < propertyKeys.length; i++) {
         this.propertyFinalAssumptions[i] = initialPrototypeShape.getProperty(propertyKeys[i]).getLocation().getFinalAssumption();
      }
   }

   public static IsPristineObjectNode create(JSClass jsClass, Shape initialPrototypeShape, Object... propertyKeys) {
      return IsPristineObjectNodeGen.create(jsClass, initialPrototypeShape, propertyKeys);
   }

   public static IsPristineObjectNode createRegExpExecAndMatch(JSContext context) {
      assert context.getEcmaScriptVersion() >= 6;

      assert Strings.equals(Strings.EXEC, (TruffleString)RegExpPrototypeBuiltins.RegExpPrototype.exec.getKey());

      return create(
         JSRegExp.INSTANCE,
         JSRealm.get(null).getInitialRegExpPrototypeShape(),
         Symbol.SYMBOL_MATCH,
         Strings.EXEC,
         JSRegExp.FLAGS,
         JSRegExp.GLOBAL,
         JSRegExp.UNICODE,
         JSRegExp.STICKY
      );
   }

   public abstract boolean execute(JSDynamicObject object);

   @Specialization(guards = "cachedShape.check(object)", assumptions = "getPropertyFinalAssumptions()")
   boolean doCached(
      JSDynamicObject object,
      @Cached("object.getShape()") Shape cachedShape,
      @Cached("isInstanceAndDoesNotOverwriteProps(cachedShape)") boolean isInstanceAndDoesNotOverwriteProps
   ) {
      return isInstanceAndDoesNotOverwriteProps && this.prototypeShapeUnchanged(object);
   }

   @Specialization(assumptions = "getPropertyFinalAssumptions()", replaces = "doCached")
   boolean doDynamic(JSDynamicObject object) {
      return this.isInstanceAndDoesNotOverwriteProps(object.getShape()) && this.prototypeShapeUnchanged(object);
   }

   @Specialization
   boolean doAssumptionsInvalid(JSDynamicObject object) {
      return false;
   }

   Assumption[] getPropertyFinalAssumptions() {
      return this.propertyFinalAssumptions;
   }

   private boolean prototypeShapeUnchanged(JSDynamicObject object) {
      return this.getPrototypeNode.execute(object).getShape() == this.initialPrototypeShape;
   }

   boolean isInstanceAndDoesNotOverwriteProps(Shape objectShape) {
      if (objectShape.getDynamicType() != this.jsClass) {
         return false;
      } else {
         for (Object key : this.propertyKeys) {
            if (objectShape.hasProperty(key)) {
               return false;
            }
         }

         return true;
      }
   }
}
