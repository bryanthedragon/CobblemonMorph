package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.builtins.OperatorsBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;

public abstract class JSToOperandNode extends JavaScriptBaseNode {
   protected final JSToPrimitiveNode.Hint hint;
   protected final boolean checkOperatorAllowed;

   protected JSToOperandNode(JSToPrimitiveNode.Hint hint, boolean checkOperatorAllowed) {
      this.hint = hint;
      this.checkOperatorAllowed = checkOperatorAllowed;
   }

   public static JSToOperandNode createHintDefault() {
      return create(JSToPrimitiveNode.Hint.Default);
   }

   public static JSToOperandNode createHintString() {
      return create(JSToPrimitiveNode.Hint.String);
   }

   public static JSToOperandNode createHintNumber() {
      return create(JSToPrimitiveNode.Hint.Number);
   }

   public static JSToOperandNode create(JSToPrimitiveNode.Hint hint) {
      return JSToOperandNodeGen.create(hint, true);
   }

   public static JSToOperandNode create(JSToPrimitiveNode.Hint hint, boolean checkOperatorAllowed) {
      return JSToOperandNodeGen.create(hint, checkOperatorAllowed);
   }

   public abstract Object execute(Object value);

   protected JSToPrimitiveNode.Hint getHint() {
      return this.hint;
   }

   @Specialization
   protected Object doOverloaded(JSOverloadedOperatorsObject arg) {
      if (this.checkOperatorAllowed) {
         OperatorsBuiltins.checkOverloadedOperatorsAllowed(arg, this);
      }

      return arg;
   }

   @Specialization(guards = "!hasOverloadedOperators(arg)")
   protected Object doOther(Object arg, @Cached("create(getHint())") JSToPrimitiveNode toPrimitiveNode) {
      return toPrimitiveNode.execute(arg);
   }
}
