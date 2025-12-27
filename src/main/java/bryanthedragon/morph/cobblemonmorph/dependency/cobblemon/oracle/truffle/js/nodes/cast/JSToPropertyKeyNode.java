package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.Symbol;
import java.util.Set;

public abstract class JSToPropertyKeyNode extends JavaScriptBaseNode {
   public static JSToPropertyKeyNode create() {
      return JSToPropertyKeyNodeGen.create();
   }

   public abstract Object execute(Object operand);

   @Specialization
   protected TruffleString doTString(TruffleString value) {
      return value;
   }

   @Specialization
   protected Symbol doSymbol(Symbol value) {
      return value;
   }

   @Specialization(guards = "!isSymbol(value)")
   protected Object doOther(
      Object value,
      @Cached("createHintString()") JSToPrimitiveNode toPrimitiveNode,
      @Cached("create()") JSToStringNode toStringNode,
      @Cached("createBinaryProfile()") ConditionProfile isSymbol
   ) {
      Object key = toPrimitiveNode.execute(value);
      return isSymbol.profile(key instanceof Symbol) ? key : toStringNode.executeString(key);
   }

   public abstract static class JSToPropertyKeyWrapperNode extends JSUnaryNode {
      @Node.Child
      private JSToPropertyKeyNode toPropertyKeyNode;

      protected JSToPropertyKeyWrapperNode(JavaScriptNode operand) {
         super(operand);
      }

      public static JavaScriptNode create(JavaScriptNode key) {
         return (JavaScriptNode)(!key.isResultAlwaysOfType(TruffleString.class) && !key.isResultAlwaysOfType(Symbol.class)
            ? JSToPropertyKeyNodeGen.JSToPropertyKeyWrapperNodeGen.create(key)
            : key);
      }

      @Specialization
      protected Object doDefault(Object value) {
         if (this.toPropertyKeyNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toPropertyKeyNode = this.insert(JSToPropertyKeyNode.create());
         }

         return this.toPropertyKeyNode.execute(value);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return JSToPropertyKeyNodeGen.JSToPropertyKeyWrapperNodeGen.create(cloneUninitialized(this.getOperand(), materializedTags));
      }
   }
}
