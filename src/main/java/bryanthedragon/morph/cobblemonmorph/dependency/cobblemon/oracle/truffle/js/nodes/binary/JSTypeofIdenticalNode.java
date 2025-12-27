package com.oracle.truffle.js.nodes.binary;

import com.oracle.js.parser.ParserException;
import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.BinaryNode;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.LiteralNode;
import com.oracle.js.parser.ir.UnaryNode;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.nodes.unary.TypeOfNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.JSSymbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

@ImportStatic({JSTypeofIdenticalNode.Type.class, JSConfig.class})
public abstract class JSTypeofIdenticalNode extends JSUnaryNode {
   protected final JSTypeofIdenticalNode.Type type;

   protected JSTypeofIdenticalNode(JavaScriptNode childNode, JSTypeofIdenticalNode.Type type) {
      super(childNode);
      this.type = type;
   }

   public static JSTypeofIdenticalNode create(JavaScriptNode childNode, JSConstantNode.JSConstantStringNode constStringNode) {
      return create(childNode, (TruffleString)constStringNode.execute(null));
   }

   public static JSTypeofIdenticalNode create(JavaScriptNode childNode, TruffleString string) {
      return JSTypeofIdenticalNodeGen.create(childNode, typeStringToEnum(string));
   }

   private static JSTypeofIdenticalNode.Type typeStringToEnum(TruffleString string) {
      if (Strings.equals(JSNumber.TYPE_NAME, string)) {
         return JSTypeofIdenticalNode.Type.Number;
      } else if (Strings.equals(JSBigInt.TYPE_NAME, string)) {
         return JSTypeofIdenticalNode.Type.BigInt;
      } else if (Strings.equals(JSString.TYPE_NAME, string)) {
         return JSTypeofIdenticalNode.Type.String;
      } else if (Strings.equals(JSBoolean.TYPE_NAME, string)) {
         return JSTypeofIdenticalNode.Type.Boolean;
      } else if (Strings.equals(JSOrdinary.TYPE_NAME, string)) {
         return JSTypeofIdenticalNode.Type.Object;
      } else if (Strings.equals(Undefined.TYPE_NAME, string)) {
         return JSTypeofIdenticalNode.Type.Undefined;
      } else if (Strings.equals(JSFunction.TYPE_NAME, string)) {
         return JSTypeofIdenticalNode.Type.Function;
      } else {
         return Strings.equals(JSSymbol.TYPE_NAME, string) ? JSTypeofIdenticalNode.Type.Symbol : JSTypeofIdenticalNode.Type.False;
      }
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag != JSTags.BinaryOperationTag.class && tag != JSTags.UnaryOperationTag.class && tag != JSTags.LiteralTag.class ? super.hasTag(tag) : true;
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (!materializedTags.contains(JSTags.BinaryOperationTag.class)
         && !materializedTags.contains(JSTags.UnaryOperationTag.class)
         && !materializedTags.contains(JSTags.LiteralTag.class)) {
         return this;
      } else {
         Object[] info = this.parseMaterializationInfo();
         if (info == null) {
            info = new Object[]{Strings.fromJavaString(this.type.name().toLowerCase()), true, true};
         }

         JavaScriptNode lhs = JSConstantNode.create(info[0]);
         JavaScriptNode rhs = TypeOfNode.create(cloneUninitialized(this.getOperand(), materializedTags));
         if ((Boolean)info[2]) {
            JavaScriptNode tmp = lhs;
            lhs = rhs;
            rhs = tmp;
         }

         JavaScriptNode materialized = info[1] ? JSIdenticalNode.createUnoptimized(lhs, rhs) : JSEqualNode.createUnoptimized(lhs, rhs);
         transferSourceSectionAddExpressionTag(this, lhs);
         transferSourceSectionAddExpressionTag(this, rhs);
         transferSourceSectionAndTags(this, materialized);
         return materialized;
      }
   }

   private JavaScriptLanguage getLanguageSafe() {
      JavaScriptLanguage language = null;

      try {
         language = this.getRootNode().getLanguage(JavaScriptLanguage.class);
         if (language == null) {
            language = this.getLanguage();
         }

         return language;
      } finally {
         ;
      }
   }

   private Object[] parseMaterializationInfo() {
      JavaScriptLanguage language = this.getLanguageSafe();
      if (language == null) {
         return null;
      } else {
         JSContext context = language.getJSContext();

         TruffleString literal;
         boolean identity;
         boolean typeofAsLeftOperand;
         try {
            Expression expression = context.getEvaluator().parseExpression(context, this.getSourceSection().getCharacters().toString());
            if (!(expression instanceof BinaryNode)) {
               return null;
            }

            BinaryNode binaryNode = (BinaryNode)expression;
            Expression lhs = binaryNode.getLhs();
            Expression rhs = binaryNode.getRhs();
            if (isTypeOf(lhs) && rhs instanceof LiteralNode) {
               typeofAsLeftOperand = true;
               literal = Strings.fromJavaString(((LiteralNode)rhs).getString());
            } else {
               if (!isTypeOf(rhs) || !(lhs instanceof LiteralNode)) {
                  return null;
               }

               typeofAsLeftOperand = false;
               literal = Strings.fromJavaString(((LiteralNode)lhs).getString());
            }

            TokenType tokenType = binaryNode.tokenType();
            if (tokenType == TokenType.EQ) {
               identity = false;
            } else {
               if (tokenType != TokenType.EQ_STRICT) {
                  return null;
               }

               identity = true;
            }
         } catch (ParserException var11) {
            return null;
         }

         return new Object[]{literal, identity, typeofAsLeftOperand};
      }
   }

   private static boolean isTypeOf(Expression expression) {
      return expression instanceof UnaryNode && ((UnaryNode)expression).tokenType() == TokenType.TYPEOF;
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return clazz == boolean.class;
   }

   @Override
   public final Object execute(VirtualFrame frame) {
      return this.executeBoolean(frame);
   }

   @Override
   public abstract boolean executeBoolean(VirtualFrame frame);

   @Specialization
   protected final boolean doBoolean(boolean value) {
      return this.type == JSTypeofIdenticalNode.Type.Boolean;
   }

   @Specialization
   protected final boolean doNumber(int value) {
      return this.type == JSTypeofIdenticalNode.Type.Number;
   }

   @Specialization
   protected final boolean doNumber(SafeInteger value) {
      return this.type == JSTypeofIdenticalNode.Type.Number;
   }

   @Specialization
   protected final boolean doNumber(long value) {
      return this.type == JSTypeofIdenticalNode.Type.Number;
   }

   @Specialization
   protected final boolean doNumber(double value) {
      return this.type == JSTypeofIdenticalNode.Type.Number;
   }

   @Specialization
   protected final boolean doSymbol(Symbol value) {
      return this.type == JSTypeofIdenticalNode.Type.Symbol;
   }

   @Specialization
   protected final boolean doBigInt(BigInt value) {
      return this.type == JSTypeofIdenticalNode.Type.BigInt;
   }

   @Specialization
   protected final boolean doString(TruffleString value) {
      return this.type == JSTypeofIdenticalNode.Type.String;
   }

   @Specialization(guards = {"type == Object || type == Function", "isJSFunction(value)"})
   protected final boolean doTypeObjectOrFunctionJSFunction(Object value) {
      assert this.type == JSTypeofIdenticalNode.Type.Object || this.type == JSTypeofIdenticalNode.Type.Function;

      return this.type == JSTypeofIdenticalNode.Type.Function;
   }

   @Specialization(guards = "type == Object || type == Function")
   protected final boolean doTypeObjectOrFunctionJSProxy(JSProxyObject value, @Cached IsCallableNode isCallableNode) {
      Object proxyTarget = JSProxy.getTargetNonProxy(value);
      boolean callable = isCallableNode.executeBoolean(proxyTarget);
      if (this.type == JSTypeofIdenticalNode.Type.Object) {
         return !callable;
      } else {
         assert this.type == JSTypeofIdenticalNode.Type.Function;

         return callable;
      }
   }

   @Specialization(guards = {"type == Object || type == Function", "!isJSFunction(value)", "!isJSProxy(value)"})
   protected final boolean doTypeObjectOrFunctionOther(JSDynamicObject value) {
      assert !JSGuards.isJSFunction(value) && !JSGuards.isJSProxy(value);

      if (this.type == JSTypeofIdenticalNode.Type.Object) {
         return value != Undefined.instance;
      } else {
         assert this.type == JSTypeofIdenticalNode.Type.Function;

         return false;
      }
   }

   @Specialization(guards = {"type != Object", "type != Function"})
   protected final boolean doTypePrimitive(JSDynamicObject value) {
      if (this.type == JSTypeofIdenticalNode.Type.Undefined) {
         return value == Undefined.instance;
      } else {
         assert this.type == JSTypeofIdenticalNode.Type.Number
            || this.type == JSTypeofIdenticalNode.Type.BigInt
            || this.type == JSTypeofIdenticalNode.Type.String
            || this.type == JSTypeofIdenticalNode.Type.Boolean
            || this.type == JSTypeofIdenticalNode.Type.Symbol
            || this.type == JSTypeofIdenticalNode.Type.False;

         return false;
      }
   }

   @Specialization(guards = "isForeignObject(value)", limit = "InteropLibraryLimit")
   protected final boolean doForeignObject(Object value, @CachedLibrary("value") InteropLibrary interop) {
      if (this.type == JSTypeofIdenticalNode.Type.Undefined || this.type == JSTypeofIdenticalNode.Type.Symbol || this.type == JSTypeofIdenticalNode.Type.False) {
         return false;
      } else if (this.type == JSTypeofIdenticalNode.Type.Boolean) {
         return interop.isBoolean(value);
      } else if (this.type == JSTypeofIdenticalNode.Type.String) {
         return interop.isString(value);
      } else if (this.type == JSTypeofIdenticalNode.Type.Number) {
         return interop.isNumber(value);
      } else if (this.type == JSTypeofIdenticalNode.Type.Function) {
         return interop.isExecutable(value) || interop.isInstantiable(value) || this.isHostSymbolInNashornCompatMode(value);
      } else {
         return this.type != JSTypeofIdenticalNode.Type.Object
            ? false
            : !interop.isExecutable(value)
               && !interop.isInstantiable(value)
               && !interop.isBoolean(value)
               && !interop.isString(value)
               && !interop.isNumber(value)
               && !this.isHostSymbolInNashornCompatMode(value);
      }
   }

   private boolean isHostSymbolInNashornCompatMode(Object value) {
      if (this.getLanguage().getJSContext().isOptionNashornCompatibilityMode()) {
         TruffleLanguage.Env env = this.getRealm().getEnv();
         if (env.isHostSymbol(value)) {
            return true;
         }
      }

      return false;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSTypeofIdenticalNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags), this.type);
   }

   public static enum Type {
      Number,
      BigInt,
      String,
      Boolean,
      Object,
      Undefined,
      Function,
      Symbol,
      False;
   }
}
