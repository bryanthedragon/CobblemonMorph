package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class ToPropertyDescriptorNode extends JavaScriptBaseNode {
   private final JSContext context;
   @Node.Child
   private JSToBooleanNode toBooleanNode;
   @CompilerDirectives.CompilationFinal
   private boolean wasExecuted;
   @Node.Child
   private PropertyGetNode getEnumerableNode;
   @Node.Child
   private PropertyGetNode getConfigurableNode;
   @Node.Child
   private PropertyGetNode getWritableNode;
   @Node.Child
   private PropertyGetNode getValueNode;
   @Node.Child
   private PropertyGetNode getSetNode;
   @Node.Child
   private PropertyGetNode getGetNode;
   @Node.Child
   private HasPropertyCacheNode hasEnumerableNode;
   @Node.Child
   private HasPropertyCacheNode hasConfigurableNode;
   @Node.Child
   private HasPropertyCacheNode hasWritableNode;
   @Node.Child
   private HasPropertyCacheNode hasValueNode;
   @Node.Child
   private HasPropertyCacheNode hasSetNode;
   @Node.Child
   private HasPropertyCacheNode hasGetNode;
   private final BranchProfile errorBranch = BranchProfile.create();

   public abstract Object execute(Object operand);

   public static ToPropertyDescriptorNode create(JSContext context) {
      return ToPropertyDescriptorNodeGen.create(context);
   }

   protected ToPropertyDescriptorNode(JSContext context) {
      this.context = context;
      this.wasExecuted = context.isMultiContext();
   }

   private void initialize() {
      if (this.toBooleanNode == null
         || this.hasEnumerableNode == null
         || this.hasConfigurableNode == null
         || this.hasWritableNode == null
         || this.hasValueNode == null
         || this.hasGetNode == null
         || this.hasSetNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.toBooleanNode = this.insert(JSToBooleanNode.create());
         this.hasEnumerableNode = this.insert(HasPropertyCacheNode.create(JSAttributes.ENUMERABLE, this.context));
         this.hasConfigurableNode = this.insert(HasPropertyCacheNode.create(JSAttributes.CONFIGURABLE, this.context));
         this.hasWritableNode = this.insert(HasPropertyCacheNode.create(JSAttributes.WRITABLE, this.context));
         this.hasValueNode = this.insert(HasPropertyCacheNode.create(JSAttributes.VALUE, this.context));
         this.hasGetNode = this.insert(HasPropertyCacheNode.create(JSAttributes.GET, this.context));
         this.hasSetNode = this.insert(HasPropertyCacheNode.create(JSAttributes.SET, this.context));
      }
   }

   private boolean toBoolean(Object target) {
      return this.toBooleanNode.executeBoolean(target);
   }

   protected boolean wasExecuted(JSDynamicObject obj) {
      return this.wasExecuted;
   }

   @Specialization(guards = {"!wasExecuted(obj)", "isJSObject(obj)"})
   protected Object nonSpecialized(JSDynamicObject obj) {
      CompilerDirectives.transferToInterpreterAndInvalidate();
      this.wasExecuted = true;
      return JSRuntime.toPropertyDescriptor(obj);
   }

   @Specialization(guards = {"wasExecuted(obj)", "isJSObject(obj)"})
   protected Object doDefault(
      JSDynamicObject obj,
      @Cached("create()") BranchProfile hasGetBranch,
      @Cached("create()") BranchProfile hasSetBranch,
      @Cached("create()") BranchProfile hasEnumerableBranch,
      @Cached("create()") BranchProfile hasConfigurableBranch,
      @Cached("create()") BranchProfile hasValueBranch,
      @Cached("create()") BranchProfile hasWritableBranch,
      @Cached("create()") IsCallableNode isCallable
   ) {
      this.initialize();
      PropertyDescriptor desc = PropertyDescriptor.createEmpty();
      if (this.hasEnumerableNode.hasProperty(obj)) {
         hasEnumerableBranch.enter();
         desc.setEnumerable(this.getEnumerableValue(obj));
      }

      if (this.hasConfigurableNode.hasProperty(obj)) {
         hasConfigurableBranch.enter();
         desc.setConfigurable(this.getConfigurableValue(obj));
      }

      boolean hasValue = this.hasValueNode.hasProperty(obj);
      if (hasValue) {
         hasValueBranch.enter();
         desc.setValue(this.getValue(obj));
      }

      boolean hasWritable = this.hasWritableNode.hasProperty(obj);
      if (hasWritable) {
         hasWritableBranch.enter();
         desc.setWritable(this.getWritableValue(obj));
      }

      boolean hasGet = this.hasGetNode.hasProperty(obj);
      if (hasGet) {
         hasGetBranch.enter();
         Object getter = this.getGet(obj);
         if (!isCallable.executeBoolean(getter) && getter != Undefined.instance) {
            this.errorBranch.enter();
            throw Errors.createTypeError("Getter must be a function");
         }

         desc.setGet(getter);
      }

      boolean hasSet = this.hasSetNode.hasProperty(obj);
      if (hasSet) {
         hasSetBranch.enter();
         Object setter = this.getSet(obj);
         if (!isCallable.executeBoolean(setter) && setter != Undefined.instance) {
            this.errorBranch.enter();
            throw Errors.createTypeError("Setter must be a function");
         }

         desc.setSet(setter);
      }

      if (!hasGet && !hasSet || !hasValue && !hasWritable) {
         return desc;
      } else {
         this.errorBranch.enter();
         throw Errors.createTypeError("Invalid property. A property cannot both have accessors and be writable or have a value");
      }
   }

   private Object getSet(JSDynamicObject obj) {
      if (this.getSetNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getSetNode = this.insert(PropertyGetNode.create(JSAttributes.SET, false, this.context));
      }

      return this.getSetNode.getValue(obj);
   }

   private Object getGet(JSDynamicObject obj) {
      if (this.getGetNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getGetNode = this.insert(PropertyGetNode.create(JSAttributes.GET, false, this.context));
      }

      return this.getGetNode.getValue(obj);
   }

   private Object getValue(JSDynamicObject obj) {
      if (this.getValueNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getValueNode = this.insert(PropertyGetNode.create(JSAttributes.VALUE, false, this.context));
      }

      return this.getValueNode.getValue(obj);
   }

   private boolean getWritableValue(JSDynamicObject obj) {
      if (this.getWritableNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getWritableNode = this.insert(PropertyGetNode.create(JSAttributes.WRITABLE, false, this.context));
      }

      return this.toBoolean(this.getWritableNode.getValue(obj));
   }

   private boolean getConfigurableValue(JSDynamicObject obj) {
      if (this.getConfigurableNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getConfigurableNode = this.insert(PropertyGetNode.create(JSAttributes.CONFIGURABLE, false, this.context));
      }

      return this.toBoolean(this.getConfigurableNode.getValue(obj));
   }

   private boolean getEnumerableValue(JSDynamicObject obj) {
      if (this.getEnumerableNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getEnumerableNode = this.insert(PropertyGetNode.create(JSAttributes.ENUMERABLE, false, this.context));
      }

      return this.toBoolean(this.getEnumerableNode.getValue(obj));
   }

   @Specialization(guards = "!isJSObject(obj)")
   protected Object doNonObject(Object obj, @Cached JSToStringNode toStringNode, @Cached TruffleString.ConcatNode concatNode) {
      String message;
      if (this.context.isOptionV8CompatibilityMode()) {
         message = Strings.toJavaString(Strings.concat(concatNode, Strings.PROPERTY_DESCRIPTION_MUST_BE_AN_OBJECT, toStringNode.executeString(obj)));
      } else {
         message = "must be an object";
      }

      throw Errors.createTypeError(message);
   }
}
