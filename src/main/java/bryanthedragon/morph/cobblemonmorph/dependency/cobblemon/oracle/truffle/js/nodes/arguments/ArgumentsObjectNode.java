package com.oracle.truffle.js.nodes.arguments;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsObject;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public abstract class ArgumentsObjectNode extends JavaScriptNode {
   protected final boolean strict;
   private final int leadingArgCount;
   private final JSContext context;
   @Node.Child
   private DynamicObjectLibrary putLengthNode;
   @Node.Child
   private DynamicObjectLibrary putSymbolIteratorNode;
   @Node.Child
   private DynamicObjectLibrary putCalleeNode;
   @Node.Child
   private DynamicObjectLibrary putCallerNode;
   private static final int THROWER_ACCESSOR_PROPERTY_FLAGS = JSAttributes.notConfigurableNotEnumerable() | 8;

   protected ArgumentsObjectNode(JSContext context, boolean strict, int leadingArgCount) {
      this.strict = strict;
      this.leadingArgCount = leadingArgCount;
      this.context = context;
      this.putLengthNode = JSObjectUtil.createDispatched(JSArgumentsArray.LENGTH);
      this.putSymbolIteratorNode = JSObjectUtil.createDispatched(Symbol.SYMBOL_ITERATOR);
      this.putCalleeNode = JSObjectUtil.createDispatched(JSArgumentsArray.CALLEE);
      this.putCallerNode = strict && context.getEcmaScriptVersion() < 8 ? JSObjectUtil.createDispatched(JSArgumentsArray.CALLER) : null;
   }

   public static JavaScriptNode create(JSContext context, boolean strict, int leadingArgCount) {
      return ArgumentsObjectNodeGen.create(context, strict, leadingArgCount);
   }

   protected final boolean isStrict() {
      return this.strict;
   }

   @Specialization(guards = "isStrict()")
   protected final JSArgumentsObject doUnmapped(VirtualFrame frame) {
      Object[] arguments = this.getObjectArray(frame);
      JSRealm realm = this.getRealm();

      assert realm == JSFunction.getRealm(getFunctionObject(frame));

      JSObjectFactory factory = this.context.getStrictArgumentsFactory();
      JSArgumentsObject argumentsObject = JSArgumentsArray.createUnmapped(factory.getShape(realm), arguments);
      factory.initProto(argumentsObject, realm);
      Properties.putWithFlags(this.putLengthNode, argumentsObject, JSArgumentsArray.LENGTH, arguments.length, JSAttributes.getDefaultNotEnumerable());
      Properties.putWithFlags(
         this.putSymbolIteratorNode, argumentsObject, Symbol.SYMBOL_ITERATOR, realm.getArrayProtoValuesIterator(), JSAttributes.getDefaultNotEnumerable()
      );
      Properties.putWithFlags(this.putCalleeNode, argumentsObject, JSArgumentsArray.CALLEE, realm.getThrowerAccessor(), THROWER_ACCESSOR_PROPERTY_FLAGS);
      if (this.context.getEcmaScriptVersion() < 8) {
         Properties.putWithFlags(this.putCallerNode, argumentsObject, JSArgumentsArray.CALLER, realm.getThrowerAccessor(), THROWER_ACCESSOR_PROPERTY_FLAGS);
      }

      return this.context.trackAllocation(argumentsObject);
   }

   @Specialization(guards = "!isStrict()")
   protected final JSArgumentsObject doMapped(VirtualFrame frame) {
      Object[] arguments = this.getObjectArray(frame);
      JSFunctionObject callee = getFunctionObject(frame);

      assert !JSFunction.isStrict(callee);

      JSRealm realm = this.getRealm();

      assert realm == JSFunction.getRealm(callee);

      JSObjectFactory factory = this.context.getNonStrictArgumentsFactory();
      JSArgumentsObject argumentsObject = JSArgumentsArray.createMapped(factory.getShape(realm), arguments);
      factory.initProto(argumentsObject, realm);
      Properties.putWithFlags(this.putLengthNode, argumentsObject, JSArgumentsArray.LENGTH, arguments.length, JSAttributes.getDefaultNotEnumerable());
      Properties.putWithFlags(
         this.putSymbolIteratorNode, argumentsObject, Symbol.SYMBOL_ITERATOR, realm.getArrayProtoValuesIterator(), JSAttributes.getDefaultNotEnumerable()
      );
      Properties.putWithFlags(this.putCalleeNode, argumentsObject, JSArgumentsArray.CALLEE, callee, JSAttributes.getDefaultNotEnumerable());
      return this.context.trackAllocation(argumentsObject);
   }

   private static JSFunctionObject getFunctionObject(VirtualFrame frame) {
      return (JSFunctionObject)JSArguments.getFunctionObject(frame.getArguments());
   }

   public Object[] getObjectArray(VirtualFrame frame) {
      return JSArguments.extractUserArguments(frame.getArguments(), this.leadingArgCount);
   }

   static boolean isInitialized(Object argumentsArray) {
      return argumentsArray != Undefined.instance;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return ArgumentsObjectNodeGen.create(this.context, this.strict, this.leadingArgCount);
   }
}
