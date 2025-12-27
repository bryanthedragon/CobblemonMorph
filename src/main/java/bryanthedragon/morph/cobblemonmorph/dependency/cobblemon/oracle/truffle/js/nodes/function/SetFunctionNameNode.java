package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;

public class SetFunctionNameNode extends JavaScriptBaseNode {
   private final ConditionProfile isSymbolProfile = ConditionProfile.createBinaryProfile();

   protected SetFunctionNameNode() {
   }

   public static SetFunctionNameNode create() {
      return new SetFunctionNameNode();
   }

   public Object execute(Object functionValue, Object propertyKey) {
      return this.execute(functionValue, propertyKey, null);
   }

   public Object execute(Object functionValue, Object propertyKey, TruffleString prefix) {
      assert JSFunction.isJSFunction(functionValue);

      assert JSRuntime.isPropertyKey(propertyKey);

      TruffleString name = this.isSymbolProfile.profile(propertyKey instanceof Symbol)
         ? ((Symbol)propertyKey).toFunctionNameString()
         : (TruffleString)propertyKey;
      if (prefix != null && !Strings.isEmpty(prefix)) {
         name = concatenate(prefix, name);
      }

      return setFunctionName((JSFunctionObject)functionValue, name);
   }

   @CompilerDirectives.TruffleBoundary
   private static TruffleString concatenate(TruffleString prefix, TruffleString name) {
      return Strings.concatAll(prefix, Strings.SPACE, name);
   }

   private static Object setFunctionName(JSFunctionObject functionValue, TruffleString name) {
      PropertyDescriptor propDesc = PropertyDescriptor.createData(name, false, false, true);
      JSRuntime.definePropertyOrThrow(functionValue, JSFunction.NAME, propDesc);
      return functionValue;
   }
}
