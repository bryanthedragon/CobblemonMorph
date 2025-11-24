
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.OperatorsBuiltinsFactory;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.access.HasPropertyCacheNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertyNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.array.JSGetLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.CreateMethodPropertyNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsConstructorNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.OperatorSet;
import org.graalvm.collections.EconomicMap;
import org.graalvm.collections.EconomicSet;

public final class OperatorsBuiltins
extends JSBuiltinsContainer.Lambda {
    public static final TruffleString OPERATORS = Strings.constant("Operators");
    private static final TruffleString LEFT_ID = Strings.constant("left");
    private static final TruffleString RIGHT_ID = Strings.constant("right");
    private static final TruffleString OPEN_ID = Strings.constant("open");
    public static final JSBuiltinsContainer BUILTINS = new OperatorsBuiltins();
    protected static final HiddenKey OPERATOR_DEFINITIONS_ID = new HiddenKey("OperatorDefinitions");

    protected OperatorsBuiltins() {
        super(null);
        this.defineFunction(OPERATORS, 1, JSAttributes.getDefault(), (context, builtin) -> OperatorsBuiltinsFactory.OperatorsNodeGen.create(context, builtin, OperatorsBuiltins.args().fixedArgs(1).varArgs().createArgumentNodes(context)));
    }

    public static boolean overloadedOperatorsAllowed(JSDynamicObject arg) {
        return true;
    }

    public static void checkOverloadedOperatorsAllowed(JSDynamicObject arg, Node originatingNode) {
        if (!OperatorsBuiltins.overloadedOperatorsAllowed(arg)) {
            throw Errors.createTypeError("use of overloaded operators is not enabled by a `with operators from` clause", originatingNode);
        }
    }

    public static abstract class ConstructOperatorSetNode
    extends JavaScriptBaseNode {
        @Node.Child
        private IsObjectNode tableIsObjectNode;
        @Node.Child
        private JSHasPropertyNode tableHasKeyNode;
        @Node.Child
        private ReadElementNode tableGetNode;
        @Node.Child
        private IsCallableNode isCallableNode;
        @Node.Child
        private PropertyGetNode getOperatorDefinitionsNode;
        @Node.Child
        private JSGetLengthNode getOpenSetLengthNode;
        @Node.Child
        private ReadElementNode readOpenSetElementNode;
        @Node.Child
        private JSToStringNode openOperatorToStringNode;
        @Node.Child
        private IsJSObjectNode typeIsJSObjectNode;
        @Node.Child
        private IsConstructorNode typeIsConstructorNode;
        @Node.Child
        private GetPrototypeNode getSuperclassNode;
        @Node.Child
        private HasPropertyCacheNode hasOperatorDefinitionsNode;
        @Node.Child
        private PropertyGetNode getClassNameNode;
        @Node.Child
        private JSToStringNode classNameToStringNode;
        protected final JSContext context;

        protected ConstructOperatorSetNode(JSContext context) {
            this.context = context;
        }

        public static ConstructOperatorSetNode create(JSContext context) {
            return OperatorsBuiltinsFactory.ConstructOperatorSetNodeGen.create(context);
        }

        public abstract OperatorSet execute(Object var1, Object[] var2);

        @Specialization
        protected OperatorSet construct(Object table, Object[] extraTables) {
            EconomicSet<TruffleString> openOperators;
            int operatorCounter = this.getContext().getOperatorCounter();
            if (!this.tableIsObject(table)) {
                throw Errors.createTypeErrorNotAnObject(table, this);
            }
            EconomicMap<TruffleString, Object> selfOperatorDefinitions = Boundaries.economicMapCreate();
            for (TruffleString operator : OperatorSet.ALL_OPERATORS) {
                if (!this.tableHasKey(table, operator)) continue;
                Object value2 = this.tableGet(table, operator);
                if (!this.isCallable(value2)) {
                    throw Errors.createTypeError(Boundaries.stringFormat("the implementation of the operator [[Class]] %s [[Class]] is not a function", operator), (Node)this);
                }
                Boundaries.economicMapPut(selfOperatorDefinitions, operator, value2);
            }
            if (this.tableHasKey(table, OPEN_ID)) {
                openOperators = Boundaries.economicSetCreate();
                Object openSet = this.tableGet(table, OPEN_ID);
                long openSetLength = this.getOpenSetLength(openSet);
                int i = 0;
                while ((long)i < openSetLength) {
                    Object element = this.readOpenSetElement(openSet, i);
                    if (!Strings.isTString(element) || !Boundaries.economicSetContains(OperatorSet.ALL_OPERATORS, JSRuntime.toStringIsString(element))) {
                        throw Errors.createTypeError(Strings.toJavaString(Strings.concat(Strings.constant("unrecognized operator "), this.openOperatorToString(element))), (Node)this);
                    }
                    Boundaries.economicSetAdd(openOperators, JSRuntime.toStringIsString(element));
                    ++i;
                }
            } else {
                openOperators = OperatorSet.ALL_OPERATORS;
            }
            EconomicMap<TruffleString, Object[]> leftOperatorDefinitions = Boundaries.economicMapCreate();
            EconomicMap<TruffleString, Object[]> rightOperatorDefinitions = Boundaries.economicMapCreate();
            for (Object extraTable : extraTables) {
                Object operatorImplementation;
                if (!this.tableIsObject(extraTable)) {
                    throw Errors.createTypeErrorNotAnObject(extraTable, this);
                }
                if (this.tableHasKey(extraTable, LEFT_ID)) {
                    if (this.tableHasKey(extraTable, RIGHT_ID)) {
                        throw Errors.createTypeError("overload table must not be both left and right", (Node)this);
                    }
                    Object leftType = this.tableGet(extraTable, LEFT_ID);
                    if (!this.isJSConstructor(leftType)) {
                        throw Errors.createTypeError("the left: value must be an ECMAScript constructor", (Node)this);
                    }
                    OperatorSet leftSet = this.getOperatorSetOfClass(this.getRealm(), (JSDynamicObject)leftType);
                    if (leftSet == null) {
                        throw Errors.createTypeError(Boundaries.stringFormat("the left: value %s must be a class with operators overloaded", this.getClassName(leftType)), (Node)this);
                    }
                    for (TruffleString operator : OperatorSet.BINARY_OPERATORS) {
                        if (!this.tableHasKey(extraTable, operator)) continue;
                        operatorImplementation = this.tableGet(extraTable, operator);
                        if (!this.isCallable(operatorImplementation)) {
                            throw Errors.createTypeError(Boundaries.stringFormat("the implementation of the operator %s %s [[Class]] is not a function", this.getClassName(leftType), operator), (Node)this);
                        }
                        if (!leftSet.isOperatorOpen(operator)) {
                            throw Errors.createTypeError(Boundaries.stringFormat("the operator %s may not be overloaded on the provided type %s", operator, this.getClassName(leftType), this));
                        }
                        if (!Boundaries.economicMapContainsKey(rightOperatorDefinitions, operator)) {
                            Boundaries.economicMapPut(rightOperatorDefinitions, operator, new JSDynamicObject[operatorCounter]);
                        }
                        Boundaries.economicMapGet(rightOperatorDefinitions, operator)[leftSet.getOperatorCounter()] = operatorImplementation;
                    }
                    continue;
                }
                if (!this.tableHasKey(extraTable, RIGHT_ID)) {
                    throw Errors.createTypeError("Either left: or right: must be provided", (Node)this);
                }
                Object rightType = this.tableGet(extraTable, RIGHT_ID);
                if (!this.isJSConstructor(rightType)) {
                    throw Errors.createTypeError("the right: value must be an ECMAScript constructor", (Node)this);
                }
                OperatorSet rightSet = this.getOperatorSetOfClass(this.getRealm(), (JSDynamicObject)rightType);
                if (rightSet == null) {
                    throw Errors.createTypeError(Boundaries.stringFormat("the right: value %s must be a class with operators overloaded", this.getClassName(rightType)), (Node)this);
                }
                for (TruffleString operator : OperatorSet.BINARY_OPERATORS) {
                    if (!this.tableHasKey(extraTable, operator)) continue;
                    operatorImplementation = this.tableGet(extraTable, operator);
                    if (!this.isCallable(operatorImplementation)) {
                        throw Errors.createTypeError(Boundaries.stringFormat("the implementation of the operator [[Class]] %s %s is not a function", operator, this.getClassName(rightType)), (Node)this);
                    }
                    if (!rightSet.isOperatorOpen(operator)) {
                        throw Errors.createTypeError(Boundaries.stringFormat("the operator %s may not be overloaded on the provided type %s", operator, this.getClassName(rightType), this));
                    }
                    if (!Boundaries.economicMapContainsKey(leftOperatorDefinitions, operator)) {
                        Boundaries.economicMapPut(leftOperatorDefinitions, operator, new JSDynamicObject[operatorCounter]);
                    }
                    Boundaries.economicMapGet(leftOperatorDefinitions, operator)[rightSet.getOperatorCounter()] = operatorImplementation;
                }
            }
            this.getContext().incOperatorCounter();
            return new OperatorSet(operatorCounter, selfOperatorDefinitions, leftOperatorDefinitions, rightOperatorDefinitions, openOperators);
        }

        protected OperatorSet getOperatorSetOfClass(JSRealm realm, JSDynamicObject constructor) {
            if (constructor == realm.getNumberConstructor()) {
                return OperatorSet.NUMBER_OPERATOR_SET;
            }
            if (constructor == realm.getBigIntConstructor()) {
                return OperatorSet.BIGINT_OPERATOR_SET;
            }
            if (constructor == realm.getStringConstructor()) {
                return OperatorSet.STRING_OPERATOR_SET;
            }
            return this.findOperatorDefinitions(constructor);
        }

        protected JSContext getContext() {
            return this.context;
        }

        protected boolean tableIsObject(Object table) {
            if (this.tableIsObjectNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.tableIsObjectNode = this.insert(IsObjectNode.create());
            }
            return this.tableIsObjectNode.executeBoolean(table);
        }

        protected boolean tableHasKey(Object table, TruffleString key) {
            if (this.tableHasKeyNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.tableHasKeyNode = this.insert(JSHasPropertyNode.create());
            }
            return this.tableHasKeyNode.executeBoolean(table, key);
        }

        protected Object tableGet(Object table, TruffleString key) {
            if (this.tableGetNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.tableGetNode = this.insert(ReadElementNode.create(this.getContext()));
            }
            return this.tableGetNode.executeWithTargetAndIndex(table, key);
        }

        protected boolean isCallable(Object operatorImplementation) {
            if (this.isCallableNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.isCallableNode = this.insert(IsCallableNode.create());
            }
            return this.isCallableNode.executeBoolean(operatorImplementation);
        }

        protected long getOpenSetLength(Object openSet) {
            if (this.getOpenSetLengthNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getOpenSetLengthNode = this.insert(JSGetLengthNode.create(this.getContext()));
            }
            return this.getOpenSetLengthNode.executeLong(openSet);
        }

        protected Object readOpenSetElement(Object openSet, long index) {
            if (this.readOpenSetElementNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readOpenSetElementNode = this.insert(ReadElementNode.create(this.getContext()));
            }
            return this.readOpenSetElementNode.executeWithTargetAndIndex(openSet, index);
        }

        protected TruffleString openOperatorToString(Object openOperator) {
            if (this.openOperatorToStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.openOperatorToStringNode = this.insert(JSToStringNode.create());
            }
            return this.openOperatorToStringNode.executeString(openOperator);
        }

        protected boolean isJSConstructor(Object type) {
            if (this.typeIsJSObjectNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.typeIsJSObjectNode = IsJSObjectNode.create();
            }
            if (this.typeIsConstructorNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.typeIsConstructorNode = IsConstructorNode.create();
            }
            return this.typeIsJSObjectNode.executeBoolean(type) && this.typeIsConstructorNode.executeBoolean(type);
        }

        protected boolean hasOperatorDefinitions(JSDynamicObject constructor) {
            if (this.hasOperatorDefinitionsNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.hasOperatorDefinitionsNode = this.insert(HasPropertyCacheNode.create(OPERATOR_DEFINITIONS_ID, this.getContext(), true));
            }
            return this.hasOperatorDefinitionsNode.hasProperty(constructor);
        }

        protected JSDynamicObject getSuperclass(JSDynamicObject constructor) {
            if (this.getSuperclassNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getSuperclassNode = this.insert(GetPrototypeNode.create());
            }
            return this.getSuperclassNode.execute(constructor);
        }

        protected OperatorSet getOperatorDefinitions(JSDynamicObject constructor) {
            if (this.getOperatorDefinitionsNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getOperatorDefinitionsNode = this.insert(PropertyGetNode.createGetHidden(OPERATOR_DEFINITIONS_ID, this.getContext()));
            }
            return (OperatorSet)this.getOperatorDefinitionsNode.getValue(constructor);
        }

        protected OperatorSet findOperatorDefinitions(JSDynamicObject arg) {
            JSDynamicObject constructor = arg;
            while (constructor != Null.instance && !this.hasOperatorDefinitions(constructor)) {
                constructor = this.getSuperclass(constructor);
            }
            if (constructor == Null.instance) {
                return null;
            }
            return this.getOperatorDefinitions(constructor);
        }

        protected TruffleString getClassName(Object constructor) {
            if (this.getClassNameNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getClassNameNode = this.insert(PropertyGetNode.create(JSFunction.NAME, this.getContext()));
            }
            if (this.classNameToStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.classNameToStringNode = this.insert(JSToStringNode.create());
            }
            return this.classNameToStringNode.executeString(this.getClassNameNode.getValue(constructor));
        }
    }

    public static abstract class CreateOverloadedOperatorsObjectNode
    extends JavaScriptBaseNode {
        protected final JSContext context;
        protected final OperatorSet operatorSet;

        protected CreateOverloadedOperatorsObjectNode(JSContext context, OperatorSet operatorSet) {
            this.context = context;
            this.operatorSet = operatorSet;
        }

        public static CreateOverloadedOperatorsObjectNode create(JSContext context, OperatorSet operatorSet) {
            return OperatorsBuiltinsFactory.CreateOverloadedOperatorsObjectNodeGen.create(context, operatorSet);
        }

        protected JSContext getContext() {
            return this.context;
        }

        protected abstract JSOverloadedOperatorsObject execute(Object var1);

        protected Shape getProtoChildShape(Object prototype) {
            CompilerAsserts.neverPartOfCompilation();
            if (JSGuards.isJSObject(prototype)) {
                JSObject jsproto = (JSObject)prototype;
                return JSObjectUtil.getProtoChildShape(jsproto, JSOrdinary.OVERLOADED_OPERATORS_INSTANCE, this.getContext());
            }
            return null;
        }

        protected Shape getShapeWithoutProto() {
            CompilerAsserts.neverPartOfCompilation();
            return JSObjectUtil.getProtoChildShape(null, JSOrdinary.OVERLOADED_OPERATORS_INSTANCE, this.getContext());
        }

        protected Shape getShapeWithDefaultProto(JSRealm realm) {
            CompilerAsserts.neverPartOfCompilation();
            return JSObjectUtil.getProtoChildShape(realm.getObjectPrototype(), JSOrdinary.OVERLOADED_OPERATORS_INSTANCE, this.getContext());
        }

        @Specialization(guards={"!getContext().isMultiContext()", "prototype == cachedPrototype", "isJSObject(cachedPrototype)"}, limit="getContext().getPropertyCacheLimit()")
        protected JSOverloadedOperatorsObject doCachedProto(Object prototype, @Cached(value="prototype") Object cachedPrototype, @Cached(value="getProtoChildShape(prototype)") Shape cachedShape) {
            return JSOverloadedOperatorsObject.create(this.getContext(), cachedShape, this.operatorSet);
        }

        @Specialization
        protected JSOverloadedOperatorsObject createWithProto(JSObject prototype, @CachedLibrary(limit="3") DynamicObjectLibrary setProtoNode, @Cached(value="getShapeWithoutProto()") Shape cachedShape) {
            JSOverloadedOperatorsObject object = JSOverloadedOperatorsObject.create(this.getContext(), cachedShape, this.operatorSet);
            setProtoNode.put(object, JSObject.HIDDEN_PROTO, prototype);
            return object;
        }

        @Specialization(guards={"!isJSObject(prototype)"})
        public JSOverloadedOperatorsObject createDefaultProto(Object prototype, @Cached(value="getShapeWithDefaultProto(getRealm())") Shape cachedShape) {
            return JSOverloadedOperatorsObject.create(this.getContext(), cachedShape, this.operatorSet);
        }
    }

    public static abstract class OperatorsNode
    extends JSBuiltinNode {
        @Node.Child
        private CreateObjectNode createPrototypeNode;
        @Node.Child
        private ConstructOperatorSetNode constructOperatorSetNode;
        @Node.Child
        private CreateMethodPropertyNode setConstructorNode;
        @Node.Child
        private PropertySetNode setOperatorDefinitionsNode;

        public OperatorsNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.createPrototypeNode = CreateObjectNode.create(context);
            this.constructOperatorSetNode = ConstructOperatorSetNode.create(context);
            this.setConstructorNode = CreateMethodPropertyNode.create(context, JSObject.CONSTRUCTOR);
            this.setOperatorDefinitionsNode = PropertySetNode.createSetHidden(OPERATOR_DEFINITIONS_ID, context);
        }

        @Specialization
        protected JSDynamicObject doOperators(VirtualFrame frame, Object table, Object ... extraTables) {
            JSDynamicObject prototype = this.createPrototypeNode.execute(frame);
            OperatorSet operatorSet = this.constructOperatorSetNode.execute(table, extraTables);
            JSDynamicObject constructor = this.createConstructor(operatorSet);
            JSFunction.setClassPrototype(constructor, prototype);
            this.setConstructorNode.executeVoid(prototype, constructor);
            this.setOperatorDefinitionsNode.setValue(constructor, operatorSet);
            return constructor;
        }

        @CompilerDirectives.TruffleBoundary
        private JSDynamicObject createConstructor(final OperatorSet operatorSet) {
            RootCallTarget callTarget = new JavaScriptRootNode(){
                @Node.Child
                private PropertyNode getPrototypeNode;
                @Node.Child
                private CreateOverloadedOperatorsObjectNode createOverloadedOperatorsObjectNode;
                {
                    this.getPrototypeNode = PropertyNode.createProperty(this.getContext(), null, JSObject.PROTOTYPE);
                    this.createOverloadedOperatorsObjectNode = CreateOverloadedOperatorsObjectNode.create(this.getContext(), operatorSet);
                }

                @Override
                public Object execute(VirtualFrame frame) {
                    Object constructor = JSArguments.getNewTarget(frame.getArguments());
                    Object prototype = this.getPrototypeNode.executeWithTarget(frame, constructor);
                    return this.createOverloadedOperatorsObjectNode.execute(prototype);
                }
            }.getCallTarget();
            JSFunctionData constructorFunctionData = JSFunctionData.create(this.getContext(), callTarget, 0, Strings.EMPTY_STRING);
            return JSFunction.create(this.getRealm(), constructorFunctionData);
        }
    }
}

