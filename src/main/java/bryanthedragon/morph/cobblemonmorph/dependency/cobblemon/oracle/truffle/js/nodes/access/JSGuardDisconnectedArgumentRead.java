package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ReadNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public abstract class JSGuardDisconnectedArgumentRead extends JavaScriptNode implements RepeatableNode, ReadNode {
   private final int argumentIndex;
   @Node.Child
   @Executed
   JavaScriptNode argumentsArrayNode;
   @Node.Child
   private ReadElementNode readElementNode;
   private final TruffleString name;

   JSGuardDisconnectedArgumentRead(int index, ReadElementNode readElementNode, JavaScriptNode argumentsArray, TruffleString name) {
      this.argumentIndex = index;
      this.argumentsArrayNode = argumentsArray;
      this.readElementNode = readElementNode;
      this.name = name;
   }

   public static JSGuardDisconnectedArgumentRead create(int index, ReadElementNode readElementNode, JavaScriptNode argumentsArray, TruffleString name) {
      return JSGuardDisconnectedArgumentReadNodeGen.create(index, readElementNode, argumentsArray, name);
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag != JSTags.ReadVariableTag.class && tag != StandardTags.ReadVariableTag.class ? super.hasTag(tag) : true;
   }

   @Override
   public Object getNodeObject() {
      NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor("name", this.name);
      descriptor.addProperty("readVariableName", this.name);
      return descriptor;
   }

   @Specialization(guards = "!isArgumentsDisconnected(argumentsArray)")
   public Object doObject(JSArgumentsObject argumentsArray, @Cached("createBinaryProfile()") @Cached.Shared("unconnected") ConditionProfile unconnected) {
      assert JSArgumentsArray.isJSArgumentsObject(argumentsArray);

      return unconnected.profile(this.argumentIndex >= JSAbstractArgumentsArray.getConnectedArgumentCount(argumentsArray))
         ? Undefined.instance
         : this.readElementNode.executeWithTargetAndIndex(argumentsArray, this.argumentIndex);
   }

   public final int getIndex() {
      return this.argumentIndex;
   }

   @Specialization(guards = "isArgumentsDisconnected(argumentsArray)")
   public Object doObjectDisconnected(
      JSArgumentsObject argumentsArray,
      @Cached("createBinaryProfile()") ConditionProfile wasDisconnected,
      @Cached("createBinaryProfile()") @Cached.Shared("unconnected") ConditionProfile unconnected
   ) {
      assert JSArgumentsArray.isJSArgumentsObject(argumentsArray);

      if (wasDisconnected.profile(JSAbstractArgumentsArray.wasIndexDisconnected(argumentsArray, this.argumentIndex))) {
         return JSAbstractArgumentsArray.getDisconnectedIndexValue(argumentsArray, this.argumentIndex);
      } else {
         return unconnected.profile(this.argumentIndex >= JSAbstractArgumentsArray.getConnectedArgumentCount(argumentsArray))
            ? Undefined.instance
            : this.readElementNode.executeWithTargetAndIndex(argumentsArray, this.argumentIndex);
      }
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSGuardDisconnectedArgumentReadNodeGen.create(
         this.argumentIndex,
         cloneUninitialized(this.readElementNode, materializedTags),
         cloneUninitialized(this.argumentsArrayNode, materializedTags),
         this.name
      );
   }
}
