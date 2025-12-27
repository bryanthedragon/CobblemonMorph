package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.decorators.ApplyDecoratorsToClassDefinitionNode;
import com.oracle.truffle.js.decorators.ApplyDecoratorsToElementDefinition;
import com.oracle.truffle.js.decorators.DecoratorListEvaluationNode;
import com.oracle.truffle.js.decorators.DefineMethodPropertyNode;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.access.InitializeInstanceElementsNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.ObjectLiteralNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import com.oracle.truffle.js.nodes.unary.IsConstructorNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class ClassDefinitionNode extends NamedEvaluationTargetNode implements FunctionNameHolder, ResumableNode.WithObjectState {
   private static final JSFunctionObject[] EMPTY = new JSFunctionObject[0];
   @Node.Children
   private JavaScriptNode[] classDecorators;
   @Node.Children
   private ObjectLiteralNode.ObjectLiteralMemberNode[] memberNodes;
   @Node.Children
   private DecoratorListEvaluationNode[] memberDecorators;
   @Node.Children
   private ApplyDecoratorsToElementDefinition[] defineStaticMethodDecorators;
   @Node.Children
   private ApplyDecoratorsToElementDefinition[] defineInstanceMethodDecorators;
   @Node.Children
   private ApplyDecoratorsToElementDefinition[] defineStaticElementDecorators;
   @Node.Children
   private ApplyDecoratorsToElementDefinition[] defineInstanceElementDecorators;
   @Node.Child
   private JavaScriptNode constructorFunctionNode;
   @Node.Child
   private JavaScriptNode classHeritageNode;
   @Node.Child
   private ApplyDecoratorsToClassDefinitionNode decorateClassDefinition;
   @Node.Child
   private DefineMethodPropertyNode defineMethodProperty;
   @Node.Child
   private JSWriteFrameSlotNode writeClassBindingNode;
   @Node.Child
   private JSWriteFrameSlotNode writeInternalConstructorBrand;
   @Node.Child
   private PropertyGetNode getPrototypeNode;
   @Node.Child
   private CreateMethodPropertyNode setConstructorNode;
   @Node.Child
   private CreateObjectNode.CreateObjectWithPrototypeNode createPrototypeNode;
   @Node.Child
   private DefineMethodNode defineConstructorMethodNode;
   @Node.Child
   private PropertySetNode setElementsNode;
   @Node.Child
   private PropertySetNode setInitializersNode;
   @Node.Child
   private InitializeInstanceElementsNode staticElementsNode;
   @Node.Child
   private PropertySetNode setPrivateBrandNode;
   @Node.Child
   private SetFunctionNameNode setFunctionName;
   @Node.Child
   private IsConstructorNode isConstructorNode;
   @Node.Child
   private JSFunctionCallNode staticExtraInitializersCallNode;
   private final JSContext context;
   private final TruffleString className;
   private final boolean hasName;
   private final int instanceElementCount;
   private final int staticElementCount;
   private final int instanceMethodsCount;
   private final int staticMethodsCount;
   private final BranchProfile errorBranch = BranchProfile.create();

   protected ClassDefinitionNode(
      JSContext context,
      JSFunctionExpressionNode constructorFunctionNode,
      JavaScriptNode classHeritageNode,
      ObjectLiteralNode.ObjectLiteralMemberNode[] memberNodes,
      JSWriteFrameSlotNode writeClassBindingNode,
      JSWriteFrameSlotNode writeInternalConstructorBrand,
      JavaScriptNode[] classDecorators,
      DecoratorListEvaluationNode[] memberDecorators,
      TruffleString className,
      int instanceElementsCount,
      int staticElementCount,
      boolean hasPrivateInstanceMethods,
      int blockScopeSlot
   ) {
      this.context = context;
      this.constructorFunctionNode = constructorFunctionNode;
      this.classHeritageNode = classHeritageNode;
      this.memberNodes = memberNodes;
      this.className = className;
      this.hasName = className != null;
      this.instanceElementCount = instanceElementsCount;
      this.staticElementCount = staticElementCount;
      this.instanceMethodsCount = countMethods(memberNodes, false);
      this.staticMethodsCount = countMethods(memberNodes, true);
      this.writeClassBindingNode = writeClassBindingNode;
      this.writeInternalConstructorBrand = writeInternalConstructorBrand;
      this.getPrototypeNode = PropertyGetNode.create(JSObject.PROTOTYPE, false, context);
      this.setConstructorNode = CreateMethodPropertyNode.create(context, JSObject.CONSTRUCTOR);
      this.createPrototypeNode = CreateObjectNode.createOrdinaryWithPrototype(context);
      this.defineConstructorMethodNode = DefineMethodNode.create(context, constructorFunctionNode, blockScopeSlot);
      this.setElementsNode = instanceElementsCount != 0 ? PropertySetNode.createSetHidden(JSFunction.CLASS_FIELDS_ID, context) : null;
      this.setPrivateBrandNode = hasPrivateInstanceMethods ? PropertySetNode.createSetHidden(JSFunction.PRIVATE_BRAND_ID, context) : null;
      this.setFunctionName = this.hasName ? null : SetFunctionNameNode.create();
      this.isConstructorNode = IsConstructorNode.create();
      this.classDecorators = classDecorators;
      this.memberDecorators = memberDecorators;
      this.setInitializersNode = PropertySetNode.createSetHidden(JSFunction.CLASS_INITIALIZERS_ID, context);
      this.defineStaticMethodDecorators = initDecoratorsElementDefinitionNodes(context, this.staticMethodsCount, true);
      this.defineInstanceMethodDecorators = initDecoratorsElementDefinitionNodes(context, this.instanceMethodsCount, false);
      this.defineStaticElementDecorators = initDecoratorsElementDefinitionNodes(context, this.staticElementCount, true);
      this.defineInstanceElementDecorators = initDecoratorsElementDefinitionNodes(context, this.instanceElementCount, false);
      this.staticExtraInitializersCallNode = JSFunctionCallNode.createCall();
   }

   private static int countMethods(ObjectLiteralNode.ObjectLiteralMemberNode[] memberNodes, boolean countStatic) {
      int total = 0;

      for (ObjectLiteralNode.ObjectLiteralMemberNode member : memberNodes) {
         if (countStatic == member.isStatic()) {
            if (ObjectLiteralNode.isMethod(member)) {
               total++;
            } else if (ObjectLiteralNode.isAccessor(member)) {
               ObjectLiteralNode.AccessorMemberNode accessor = (ObjectLiteralNode.AccessorMemberNode)member;

               assert accessor.hasGetter() || accessor.hasSetter();

               if (accessor.hasGetter()) {
                  total++;
               }

               if (accessor.hasSetter()) {
                  total++;
               }
            }
         }
      }

      return total;
   }

   public static ClassDefinitionNode create(
      JSContext context,
      JSFunctionExpressionNode constructorFunction,
      JavaScriptNode classHeritage,
      ObjectLiteralNode.ObjectLiteralMemberNode[] members,
      JSWriteFrameSlotNode writeClassBinding,
      JSWriteFrameSlotNode writeInternalConstructorBrand,
      TruffleString className,
      JavaScriptNode[] classDecorators,
      DecoratorListEvaluationNode[] memberDecorators,
      int instanceFieldCount,
      int staticFieldCount,
      boolean hasPrivateInstanceMethods,
      JSFrameSlot blockScopeSlot
   ) {
      return new ClassDefinitionNode(
         context,
         constructorFunction,
         classHeritage,
         members,
         writeClassBinding,
         writeInternalConstructorBrand,
         classDecorators,
         memberDecorators,
         className,
         instanceFieldCount,
         staticFieldCount,
         hasPrivateInstanceMethods,
         blockScopeSlot != null ? blockScopeSlot.getIndex() : -1
      );
   }

   @Override
   public Object execute(VirtualFrame frame) {
      return this.executeWithName(frame, this.className);
   }

   @Override
   public Object resume(VirtualFrame frame, int stateSlot) {
      Object maybeState = this.getState(frame, stateSlot);
      ClassDefinitionNode.ClassDefinitionResumptionRecord resumptionRecord = null;
      if (maybeState instanceof ClassDefinitionNode.ClassDefinitionResumptionRecord) {
         this.resetState(frame, stateSlot);
         resumptionRecord = (ClassDefinitionNode.ClassDefinitionResumptionRecord)maybeState;
      }

      return this.executeWithName(frame, this.className, resumptionRecord, stateSlot);
   }

   @Override
   public Object executeWithName(VirtualFrame frame, Object name) {
      return this.executeWithName(frame, name, null, -1);
   }

   private Object executeWithName(VirtualFrame frame, Object name, ClassDefinitionNode.ClassDefinitionResumptionRecord resumptionRecord, int stateSlot) {
      JSRealm realm = this.getRealm();
      JSDynamicObject proto;
      JSObject constructor;
      Object[] decorators;
      ClassElementDefinitionRecord[] instanceElements;
      ClassElementDefinitionRecord[] instanceMethods;
      ClassElementDefinitionRecord[] staticElements;
      ClassElementDefinitionRecord[] staticMethods;
      int instanceElementIndex;
      int staticElementIndex;
      int instanceMethodIndex;
      int staticMethodIndex;
      int startIndex;
      if (resumptionRecord == null) {
         Object protoParent = realm.getObjectPrototype();
         Object constructorParent = realm.getFunctionPrototype();
         if (this.classHeritageNode != null) {
            Object superclass = this.classHeritageNode.execute(frame);
            if (superclass == Null.instance) {
               protoParent = Null.instance;
            } else {
               if (!this.isConstructorNode.executeBoolean(superclass)) {
                  this.errorBranch.enter();
                  throw Errors.createTypeError("not a constructor", this);
               }

               if (JSRuntime.isGenerator(superclass)) {
                  this.errorBranch.enter();
                  throw Errors.createTypeError("class cannot extend a generator function", this);
               }

               protoParent = this.getPrototypeNode.getValue(superclass);
               if (protoParent != Null.instance && !JSRuntime.isObject(protoParent)) {
                  this.errorBranch.enter();
                  throw Errors.createTypeError("protoParent is neither Object nor Null", this);
               }

               constructorParent = superclass;
            }
         }

         decorators = this.classDecoratorListEvaluation(frame);

         assert protoParent == Null.instance || JSRuntime.isObject(protoParent);

         proto = this.createPrototypeNode.execute((JSDynamicObject)protoParent);
         constructor = this.defineConstructorMethodNode.execute(frame, proto, (JSDynamicObject)constructorParent);
         JSFunction.setClassPrototype(constructor, proto);
         if (this.setFunctionName != null && name != null) {
            this.setFunctionName.execute(constructor, name);
         }

         this.setConstructorNode.executeVoid(proto, constructor);
         instanceElements = this.instanceElementCount == 0 ? null : new ClassElementDefinitionRecord[this.instanceElementCount];
         staticElements = this.staticElementCount == 0 ? null : new ClassElementDefinitionRecord[this.staticElementCount];
         instanceMethods = this.instanceMethodsCount == 0 ? null : new ClassElementDefinitionRecord[this.instanceMethodsCount];
         staticMethods = this.staticMethodsCount == 0 ? null : new ClassElementDefinitionRecord[this.staticMethodsCount];
         instanceElementIndex = 0;
         staticElementIndex = 0;
         instanceMethodIndex = 0;
         staticMethodIndex = 0;
         startIndex = 0;
      } else {
         proto = resumptionRecord.proto;
         constructor = resumptionRecord.constructor;
         instanceElements = resumptionRecord.instanceElements;
         staticElements = resumptionRecord.staticElements;
         instanceMethods = resumptionRecord.instanceMethods;
         staticMethods = resumptionRecord.staticMethods;
         instanceElementIndex = resumptionRecord.instanceElementIndex;
         staticElementIndex = resumptionRecord.staticElementIndex;
         instanceMethodIndex = resumptionRecord.instanceMethodIndex;
         staticMethodIndex = resumptionRecord.staticMethodIndex;
         startIndex = resumptionRecord.startIndex;
         decorators = resumptionRecord.decorators;
      }

      this.initializeMembers(
         frame,
         proto,
         constructor,
         instanceElements,
         instanceMethods,
         staticElements,
         staticMethods,
         startIndex,
         instanceElementIndex,
         instanceMethodIndex,
         staticElementIndex,
         staticMethodIndex,
         stateSlot,
         realm
      );
      if (this.writeClassBindingNode != null) {
         this.writeClassBindingNode.executeWrite(frame, constructor);
      }

      List<Object> staticExtraInitializers = new ArrayList<>();
      List<Object> instanceExtraInitializers = new ArrayList<>();
      this.applyDecorators(
         frame, instanceElements, instanceMethods, instanceExtraInitializers, staticExtraInitializers, staticElements, staticMethods, constructor, proto
      );
      if (this.setElementsNode != null) {
         this.setElementsNode.setValue(constructor, instanceElements);
      }

      this.setInitializersNode.setValue(constructor, instanceExtraInitializers.toArray(EMPTY));
      if (this.setPrivateBrandNode != null) {
         HiddenKey privateBrand = new HiddenKey("Brand");
         this.setPrivateBrandNode.setValue(constructor, privateBrand);
      }

      if (this.writeInternalConstructorBrand != null) {
         this.writeInternalConstructorBrand.executeWrite(frame, constructor);
      }

      this.executeStaticExtraInitializers(constructor, staticExtraInitializers.toArray(EMPTY));
      if (this.staticElementCount != 0) {
         InitializeInstanceElementsNode initializeStaticElements = this.staticElementsNode;
         if (initializeStaticElements == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.staticElementsNode = initializeStaticElements = this.insert(InitializeInstanceElementsNode.create(this.context));
         }

         initializeStaticElements.executeStaticElements(constructor, staticElements);
      }

      List<Object> classExtraInitializers = new ArrayList<>();
      return this.applyDecoratorsClassDefinition(frame, this.getClassName(), constructor, decorators, classExtraInitializers);
   }

   private void applyDecorators(
      VirtualFrame frame,
      ClassElementDefinitionRecord[] instanceElements,
      ClassElementDefinitionRecord[] instanceMethods,
      List<Object> instanceExtraInitializers,
      List<Object> staticExtraInitializers,
      ClassElementDefinitionRecord[] staticElements,
      ClassElementDefinitionRecord[] staticMethods,
      JSDynamicObject constructor,
      JSDynamicObject proto
   ) {
      this.applyDecoratorsStaticMethods(frame, staticMethods, staticExtraInitializers, constructor);
      this.applyDecoratorsInstanceMethods(frame, instanceMethods, instanceExtraInitializers, proto);
      this.applyDecoratorsStaticElements(frame, staticElements, staticExtraInitializers, constructor);
      this.applyDecoratorsInstanceElements(frame, instanceElements, instanceExtraInitializers, proto);
   }

   private void executeStaticExtraInitializers(Object target, Object[] initializers) {
      for (Object initializer : initializers) {
         this.staticExtraInitializersCallNode.executeCall(JSArguments.createZeroArg(target, initializer));
      }
   }

   @ExplodeLoop
   private void applyDecoratorsStaticMethods(
      VirtualFrame frame, ClassElementDefinitionRecord[] staticMethods, List<Object> instanceExtraInitializers, JSDynamicObject proto
   ) {
      if (staticMethods != null) {
         int i = 0;

         for (ClassElementDefinitionRecord m : staticMethods) {
            assert m.isMethod() || m.isSetter() || m.isGetter();

            this.defineStaticMethodDecorators[i++].executeDecorator(frame, proto, m, instanceExtraInitializers);
            this.getDefineMethodProperty().executeDefine(proto, m, false);
         }
      }
   }

   @ExplodeLoop
   private void applyDecoratorsInstanceMethods(
      VirtualFrame frame, ClassElementDefinitionRecord[] instanceMethods, List<Object> extraInitializers, JSDynamicObject homeObject
   ) {
      if (instanceMethods != null) {
         int i = 0;

         for (ClassElementDefinitionRecord m : instanceMethods) {
            assert instanceMethods.length == this.instanceMethodsCount;

            this.defineInstanceMethodDecorators[i++].executeDecorator(frame, homeObject, m, extraInitializers);
            this.getDefineMethodProperty().executeDefine(homeObject, m, false);
         }
      }
   }

   @ExplodeLoop
   private void applyDecoratorsStaticElements(
      VirtualFrame frame, ClassElementDefinitionRecord[] staticElements, List<Object> instanceExtraInitializers, JSDynamicObject proto
   ) {
      if (staticElements != null) {
         int i = 0;

         for (ClassElementDefinitionRecord f : staticElements) {
            if (!f.isMethod() && !f.isSetter() && !f.isGetter()) {
               this.defineStaticElementDecorators[i++].executeDecorator(frame, proto, f, instanceExtraInitializers);
            }
         }
      }
   }

   @ExplodeLoop
   private void applyDecoratorsInstanceElements(
      VirtualFrame frame, ClassElementDefinitionRecord[] instanceFields, List<Object> instanceExtraInitializers, JSDynamicObject proto
   ) {
      if (instanceFields != null) {
         int i = 0;

         for (ClassElementDefinitionRecord f : instanceFields) {
            this.defineInstanceElementDecorators[i++].executeDecorator(frame, proto, f, instanceExtraInitializers);
         }
      }
   }

   private static ApplyDecoratorsToElementDefinition[] initDecoratorsElementDefinitionNodes(JSContext context, int size, boolean isStatic) {
      CompilerAsserts.neverPartOfCompilation();
      if (size == 0) {
         return null;
      } else {
         ApplyDecoratorsToElementDefinition[] nodes = new ApplyDecoratorsToElementDefinition[size];

         for (int i = 0; i < size; i++) {
            nodes[i] = ApplyDecoratorsToElementDefinition.create(context, isStatic);
         }

         return nodes;
      }
   }

   private DefineMethodPropertyNode getDefineMethodProperty() {
      if (this.defineMethodProperty == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.defineMethodProperty = this.insert(DefineMethodPropertyNode.create());
      }

      return this.defineMethodProperty;
   }

   private Object[] classDecoratorListEvaluation(VirtualFrame frame) {
      Object[] decorators = new Object[this.classDecorators.length];

      for (int i = 0; i < decorators.length; i++) {
         Object maybeDecorator = this.classDecorators[i].execute(frame);
         decorators[decorators.length - i - 1] = maybeDecorator;
      }

      return decorators;
   }

   private Object applyDecoratorsClassDefinition(
      VirtualFrame frame, Object name, JSObject constructor, Object[] decorators, List<Object> classExtraInitializers
   ) {
      if (this.classDecorators.length == 0) {
         return constructor;
      } else {
         if (this.decorateClassDefinition == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.decorateClassDefinition = this.insert(ApplyDecoratorsToClassDefinitionNode.create(this.context));
         }

         return this.decorateClassDefinition.executeDecorators(frame, name, constructor, decorators, classExtraInitializers);
      }
   }

   private static void storeElement(
      ClassDefinitionNode.ClassElementDefinitionRecordIndexes indexes,
      ClassElementDefinitionRecord[] staticStorage,
      ClassElementDefinitionRecord[] instanceStorage,
      ClassElementDefinitionRecord element,
      boolean isStatic
   ) {
      if (isStatic) {
         staticStorage[indexes.staticElementIndex++] = element;
      } else {
         instanceStorage[indexes.instanceElementIndex++] = element;
      }
   }

   private static void storeMethod(
      ClassDefinitionNode.ClassElementDefinitionRecordIndexes indexes,
      ClassElementDefinitionRecord[] staticStorage,
      ClassElementDefinitionRecord[] instanceStorage,
      ClassElementDefinitionRecord element,
      boolean isStatic
   ) {
      if (isStatic) {
         staticStorage[indexes.staticMethodIndex++] = element;
      } else {
         instanceStorage[indexes.instanceMethodIndex++] = element;
      }
   }

   @ExplodeLoop
   private void initializeMembers(
      VirtualFrame frame,
      JSDynamicObject proto,
      JSObject constructor,
      ClassElementDefinitionRecord[] instanceElements,
      ClassElementDefinitionRecord[] instanceMethods,
      ClassElementDefinitionRecord[] staticElements,
      ClassElementDefinitionRecord[] staticMethods,
      int startIndex,
      int instanceElementsIdx,
      int instanceMethodsIdx,
      int staticElementIdx,
      int staticMethodIdx,
      int stateSlot,
      JSRealm realm
   ) {
      ClassDefinitionNode.ClassElementDefinitionRecordIndexes indexes = new ClassDefinitionNode.ClassElementDefinitionRecordIndexes(
         instanceElementsIdx, instanceMethodsIdx, staticElementIdx, staticMethodIdx
      );
      Object[] decorators = null;
      int i = 0;

      try {
         for (; i < this.memberNodes.length; i++) {
            if (i >= startIndex) {
               ObjectLiteralNode.ObjectLiteralMemberNode memberNode = this.memberNodes[i];
               boolean isStatic = memberNode.isStatic();
               JSDynamicObject homeObject = (JSDynamicObject)(isStatic ? constructor : proto);
               decorators = this.memberDecorators[i] != null ? this.memberDecorators[i].execute(frame) : null;
               if (memberNode.isFieldOrStaticBlock()) {
                  ClassElementDefinitionRecord field = initField(frame, realm, decorators, memberNode, homeObject);
                  storeElement(indexes, staticElements, instanceElements, field, isStatic);
               } else {
                  Object key = memberNode.evaluateKey(frame);
                  if (ObjectLiteralNode.isAutoAccessor(memberNode)) {
                     ClassElementDefinitionRecord autoAccessor = initAutoAccessor(frame, realm, decorators, memberNode, homeObject, key);
                     storeElement(indexes, staticElements, instanceElements, autoAccessor, isStatic);
                  } else if (ObjectLiteralNode.isMethod(memberNode)) {
                     Object value = memberNode.evaluateValue(frame, homeObject, key, realm);
                     memberNode.evaluateWithKeyAndValue(frame, homeObject, key, value, realm);
                     ClassElementDefinitionRecord method;
                     if (memberNode instanceof ObjectLiteralNode.PrivateMethodMemberNode) {
                        ObjectLiteralNode.PrivateMethodMemberNode privateMember = (ObjectLiteralNode.PrivateMethodMemberNode)memberNode;
                        int slot = privateMember.getWritePrivateNode().getSlotIndex();
                        int brandSlot = privateMember.getPrivateBrandSlotIndex();
                        int blockSlot = this.defineConstructorMethodNode.getBlockScopeSlot();
                        method = ClassElementDefinitionRecord.createPrivateMethod(
                           this.context, key, slot, brandSlot, blockSlot, value, memberNode.isAnonymousFunctionDefinition(), decorators
                        );
                     } else {
                        method = ClassElementDefinitionRecord.createPublicMethod(
                           this.context, key, value, memberNode.isAnonymousFunctionDefinition(), decorators
                        );
                     }

                     storeMethod(indexes, staticMethods, instanceMethods, method, isStatic);
                  } else if (ObjectLiteralNode.isAccessor(memberNode)) {
                     memberNode.evaluateWithKeyAndValue(frame, homeObject, key, null, realm);
                     ObjectLiteralNode.AccessorMemberNode accessorMember = (ObjectLiteralNode.AccessorMemberNode)memberNode;

                     assert accessorMember.hasGetter() || accessorMember.hasSetter();

                     if (accessorMember.hasGetter()) {
                        Object getter = accessorMember.evaluateGetter(frame, homeObject, key, realm);
                        ClassElementDefinitionRecord element;
                        if (memberNode instanceof ObjectLiteralNode.PrivateAccessorMemberNode) {
                           ObjectLiteralNode.PrivateAccessorMemberNode privateMember = (ObjectLiteralNode.PrivateAccessorMemberNode)memberNode;
                           int slot = privateMember.getWritePrivateNode().getSlotIndex();
                           int brandSlot = privateMember.getPrivateBrandSlotIndex();
                           int blockSlot = this.defineConstructorMethodNode.getBlockScopeSlot();
                           element = ClassElementDefinitionRecord.createPrivateGetter(
                              this.context, key, slot, brandSlot, blockSlot, getter, memberNode.isAnonymousFunctionDefinition(), decorators
                           );
                        } else {
                           element = ClassElementDefinitionRecord.createPublicGetter(
                              this.context, key, getter, memberNode.isAnonymousFunctionDefinition(), decorators
                           );
                        }

                        storeMethod(indexes, staticMethods, instanceMethods, element, isStatic);
                     }

                     if (accessorMember.hasSetter()) {
                        Object setter = accessorMember.evaluateSetter(frame, homeObject, key, realm);
                        ClassElementDefinitionRecord element;
                        if (memberNode instanceof ObjectLiteralNode.PrivateAccessorMemberNode) {
                           ObjectLiteralNode.PrivateAccessorMemberNode privateMember = (ObjectLiteralNode.PrivateAccessorMemberNode)memberNode;
                           int slot = privateMember.getWritePrivateNode().getSlotIndex();
                           int brandSlot = privateMember.getPrivateBrandSlotIndex();
                           int blockSlot = this.defineConstructorMethodNode.getBlockScopeSlot();
                           element = ClassElementDefinitionRecord.createPrivateSetter(
                              this.context, key, slot, brandSlot, blockSlot, setter, memberNode.isAnonymousFunctionDefinition(), decorators
                           );
                        } else {
                           element = ClassElementDefinitionRecord.createPublicSetter(
                              this.context, key, setter, memberNode.isAnonymousFunctionDefinition(), decorators
                           );
                        }

                        storeMethod(indexes, staticMethods, instanceMethods, element, isStatic);
                     }
                  }
               }
            }
         }
      } catch (YieldException var29) {
         this.setState(
            frame,
            stateSlot,
            new ClassDefinitionNode.ClassDefinitionResumptionRecord(
               proto,
               constructor,
               instanceElements,
               staticElements,
               instanceMethods,
               staticMethods,
               indexes.instanceElementIndex,
               indexes.staticElementIndex,
               indexes.instanceMethodIndex,
               indexes.staticMethodIndex,
               decorators,
               i
            )
         );
         throw var29;
      }

      assert indexes.instanceElementIndex == this.instanceElementCount && indexes.staticElementIndex == this.staticElementCount;
   }

   private static ClassElementDefinitionRecord initField(
      VirtualFrame frame, JSRealm realm, Object[] decorators, ObjectLiteralNode.ObjectLiteralMemberNode memberNode, JSDynamicObject homeObject
   ) {
      memberNode.executeVoid(frame, homeObject, realm);
      Object key = memberNode.evaluateKey(frame);
      Object value = memberNode.evaluateValue(frame, homeObject, key, realm);
      return ClassElementDefinitionRecord.createField(
         realm.getContext(), key, value, memberNode.isPrivate(), memberNode.isAnonymousFunctionDefinition(), decorators
      );
   }

   private static ClassElementDefinitionRecord initAutoAccessor(
      VirtualFrame frame, JSRealm realm, Object[] decorators, ObjectLiteralNode.ObjectLiteralMemberNode memberNode, JSDynamicObject homeObject, Object key
   ) {
      ObjectLiteralNode.AutoAccessorDataMemberNode autoAccessorDataMemberNode = (ObjectLiteralNode.AutoAccessorDataMemberNode)memberNode;
      HiddenKey backingStorageKey = autoAccessorDataMemberNode.createBackingStorageKey(key);
      JSFunctionObject setter = autoAccessorDataMemberNode.createAutoAccessorSetter(backingStorageKey);
      JSFunctionObject getter = autoAccessorDataMemberNode.createAutoAccessorGetter(backingStorageKey);
      autoAccessorDataMemberNode.executeWithGetterSetter(homeObject, key, getter, setter);
      Object value = memberNode.evaluateValue(frame, homeObject, key, realm);
      ClassElementDefinitionRecord field = ClassElementDefinitionRecord.createAutoAccessor(
         realm.getContext(), key, backingStorageKey, value, memberNode.isPrivate(), memberNode.isAnonymousFunctionDefinition(), decorators
      );
      field.setSetter(setter);
      field.setGetter(getter);
      return field;
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return clazz == JSDynamicObject.class;
   }

   @Override
   public TruffleString getFunctionName() {
      return this.hasName ? ((FunctionNameHolder)this.constructorFunctionNode).getFunctionName() : Strings.EMPTY_STRING;
   }

   public TruffleString getClassName() {
      return this.hasName ? this.className : Strings.EMPTY_STRING;
   }

   @Override
   public void setFunctionName(TruffleString name) {
      ((FunctionNameHolder)this.constructorFunctionNode).setFunctionName(name);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new ClassDefinitionNode(
         this.context,
         cloneUninitialized(this.constructorFunctionNode, materializedTags),
         cloneUninitialized(this.classHeritageNode, materializedTags),
         ObjectLiteralNode.ObjectLiteralMemberNode.cloneUninitialized(this.memberNodes, materializedTags),
         cloneUninitialized(this.writeClassBindingNode, materializedTags),
         cloneUninitialized(this.writeInternalConstructorBrand, materializedTags),
         cloneUninitialized(this.classDecorators, materializedTags),
         cloneUninitialized(this.memberDecorators, materializedTags),
         this.className,
         this.instanceElementCount,
         this.staticElementCount,
         this.setPrivateBrandNode != null,
         this.defineConstructorMethodNode.getBlockScopeSlot()
      );
   }

   static class ClassDefinitionResumptionRecord {
      final JSDynamicObject proto;
      final JSObject constructor;
      final ClassElementDefinitionRecord[] instanceElements;
      final ClassElementDefinitionRecord[] staticElements;
      final ClassElementDefinitionRecord[] instanceMethods;
      final ClassElementDefinitionRecord[] staticMethods;
      final int instanceElementIndex;
      final int instanceMethodIndex;
      final int staticElementIndex;
      final int staticMethodIndex;
      final int startIndex;
      final Object[] decorators;

      ClassDefinitionResumptionRecord(
         JSDynamicObject proto,
         JSObject constructor,
         ClassElementDefinitionRecord[] instanceFields,
         ClassElementDefinitionRecord[] staticElements,
         ClassElementDefinitionRecord[] instanceMethods,
         ClassElementDefinitionRecord[] staticMethods,
         int instanceElementIndex,
         int staticElementIndex,
         int instanceMethodIndex,
         int staticMethodIndex,
         Object[] decorators,
         int startIndex
      ) {
         this.proto = proto;
         this.constructor = constructor;
         this.instanceElements = instanceFields;
         this.staticElements = staticElements;
         this.instanceMethods = instanceMethods;
         this.staticMethods = staticMethods;
         this.instanceElementIndex = instanceElementIndex;
         this.staticElementIndex = staticElementIndex;
         this.instanceMethodIndex = instanceMethodIndex;
         this.staticMethodIndex = staticMethodIndex;
         this.startIndex = startIndex;
         this.decorators = decorators;
      }
   }

   static class ClassElementDefinitionRecordIndexes {
      int instanceElementIndex;
      int instanceMethodIndex;
      int staticElementIndex;
      int staticMethodIndex;

      ClassElementDefinitionRecordIndexes(int instanceElementsIdx, int instanceMethodsIdx, int staticElementIdx, int staticMethodIdx) {
         this.instanceElementIndex = instanceElementsIdx;
         this.instanceMethodIndex = instanceMethodsIdx;
         this.staticElementIndex = staticElementIdx;
         this.staticMethodIndex = staticMethodIdx;
      }
   }
}
