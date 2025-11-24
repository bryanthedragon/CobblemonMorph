
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.InitializeInstanceElementsNodeGen;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.access.PrivateFieldAddNode;
import com.oracle.truffle.js.nodes.access.PropertyNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.function.ClassElementDefinitionRecord;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public abstract class InitializeInstanceElementsNode
extends JavaScriptNode {
    private static final JSFunctionObject[] EMPTY_INITIALIZERS = new JSFunctionObject[0];
    @Node.Child
    @Executed
    protected JavaScriptNode targetNode;
    @Node.Child
    @Executed
    protected JavaScriptNode constructorNode;
    @Node.Child
    @Executed(with={"constructorNode"})
    protected JSTargetableNode fieldsNode;
    @Node.Child
    @Executed(with={"constructorNode"})
    protected JSTargetableNode initializersNode;
    @Node.Child
    @Executed(with={"constructorNode"})
    protected JSTargetableNode brandNode;
    protected final JSContext context;

    protected InitializeInstanceElementsNode(JSContext context, JavaScriptNode targetNode, JavaScriptNode constructorNode) {
        this.context = context;
        this.targetNode = targetNode;
        this.constructorNode = constructorNode;
        if (constructorNode != null) {
            this.fieldsNode = PropertyNode.createGetHidden(context, null, JSFunction.CLASS_FIELDS_ID);
            this.brandNode = PropertyNode.createGetHidden(context, null, JSFunction.PRIVATE_BRAND_ID);
            this.initializersNode = PropertyNode.createGetHidden(context, null, JSFunction.CLASS_INITIALIZERS_ID);
        }
    }

    public static JavaScriptNode create(JSContext context, JavaScriptNode targetNode, JavaScriptNode constructorNode) {
        return InitializeInstanceElementsNodeGen.create(context, targetNode, constructorNode);
    }

    public static InitializeInstanceElementsNode create(JSContext context) {
        return InitializeInstanceElementsNodeGen.create(context, null, null);
    }

    public final Object executeStaticElements(Object targetConstructor, ClassElementDefinitionRecord[] staticElements) {
        return this.executeEvaluated(targetConstructor, Undefined.instance, staticElements, EMPTY_INITIALIZERS, Undefined.instance);
    }

    protected abstract Object executeEvaluated(Object var1, Object var2, ClassElementDefinitionRecord[] var3, JSFunctionObject[] var4, Object var5);

    @ExplodeLoop(kind=ExplodeLoop.LoopExplosionKind.FULL_UNROLL)
    @Specialization
    protected static Object withFields(Object target, Object constructor, ClassElementDefinitionRecord[] fields, JSFunctionObject[] initializers, Object brand, @Cached(value="createBrandAddNode(brand, context)") @Cached.Shared(value="privateBrandAdd") PrivateFieldAddNode privateBrandAddNode, @Cached(value="createFieldNodes(fields, context)") DefineFieldNode[] fieldNodes, @Cached(value="createCall()") JSFunctionCallNode callInit) {
        InitializeInstanceElementsNode.privateBrandAdd(target, constructor, fields, initializers, brand, privateBrandAddNode);
        InitializeInstanceElementsNode.executeInitializers(target, initializers, callInit);
        int size = fieldNodes.length;
        assert (size == fields.length);
        for (int i = 0; i < size; ++i) {
            Object[] fieldInitializers;
            ClassElementDefinitionRecord record = fields[i];
            Object initValue = fieldNodes[i].defineField(target, record);
            for (Object initializer : fieldInitializers = record.getInitializers()) {
                initValue = fieldNodes[i].defineFieldWithInitializer(target, record, initializer, initValue);
            }
        }
        return target;
    }

    private static void executeInitializers(Object target, JSFunctionObject[] initializers, JSFunctionCallNode callInit) {
        for (JSFunctionObject initializer : initializers) {
            callInit.executeCall(JSArguments.createZeroArg(target, initializer));
        }
    }

    @Specialization
    protected static Object privateBrandAdd(Object target, Object constructor, Object fields, Object initializers, Object brand, @Cached(value="createBrandAddNode(brand, context)") @Cached.Shared(value="privateBrandAdd") PrivateFieldAddNode privateBrandAddNode) {
        assert (privateBrandAddNode != null == (brand != Undefined.instance));
        if (privateBrandAddNode != null) {
            privateBrandAddNode.execute(target, brand, constructor);
        }
        return target;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return InitializeInstanceElementsNode.create(this.context, InitializeInstanceElementsNode.cloneUninitialized(this.targetNode, materializedTags), InitializeInstanceElementsNode.cloneUninitialized(this.constructorNode, materializedTags));
    }

    static PrivateFieldAddNode createBrandAddNode(Object brand, JSContext context) {
        CompilerAsserts.neverPartOfCompilation();
        if (brand != Undefined.instance) {
            return PrivateFieldAddNode.create(context);
        }
        return null;
    }

    static DefineFieldNode[] createFieldNodes(ClassElementDefinitionRecord[] fields, JSContext context) {
        CompilerAsserts.neverPartOfCompilation();
        int size = fields.length;
        DefineFieldNode[] fieldNodes = new DefineFieldNode[size];
        for (int i = 0; i < size; ++i) {
            ClassElementDefinitionRecord field = fields[i];
            Object key = field.getKey();
            Object initializer = field.getValue();
            boolean isAnonymousFunctionDefinition = (Boolean)field.isAnonymousFunction();
            Node writeNode = null;
            if (field instanceof ClassElementDefinitionRecord.AutoAccessor) {
                writeNode = DynamicObjectLibrary.getFactory().createDispatched(5);
            } else if (key instanceof HiddenKey) {
                writeNode = PrivateFieldAddNode.create(context);
            } else if (key != null) {
                writeNode = WriteElementNode.create(context, true, true);
            }
            JSFunctionCallNode callNode = null;
            if (initializer != Undefined.instance) {
                callNode = JSFunctionCallNode.createCall();
            }
            fieldNodes[i] = new DefineFieldNode(writeNode, callNode, isAnonymousFunctionDefinition);
        }
        return fieldNodes;
    }

    static final class DefineFieldNode
    extends JavaScriptBaseNode {
        @Node.Child
        Node writeNode;
        @Node.Child
        JSFunctionCallNode callNode;
        @Node.Child
        JSFunctionCallNode callInitializersNode;
        private final boolean isAnonymousFunctionDefinition;

        DefineFieldNode(Node writeNode, JSFunctionCallNode callNode, boolean isAnonymousFunctionDefinition) {
            this.writeNode = writeNode;
            this.callNode = callNode;
            this.isAnonymousFunctionDefinition = isAnonymousFunctionDefinition;
        }

        Object defineField(Object target, ClassElementDefinitionRecord record) {
            assert (this.callNode != null == (record.getValue() != Undefined.instance));
            Object value2 = Undefined.instance;
            if (this.callNode != null) {
                Object initializer = record.getValue();
                Object key = record.getKey();
                value2 = this.callNode.executeCall(this.isAnonymousFunctionDefinition ? JSArguments.create(target, initializer, Undefined.instance, key) : JSArguments.createOneArg(target, initializer, Undefined.instance));
            }
            return this.writeValue(target, record, value2);
        }

        Object defineFieldWithInitializer(Object target, ClassElementDefinitionRecord record, Object initializer, Object initValue) {
            assert (record.getInitializers().length > 0);
            Object value2 = this.getInitializersCallNode().executeCall(this.isAnonymousFunctionDefinition ? JSArguments.create(target, initializer, initValue, record.getKey()) : JSArguments.createOneArg(target, initializer, initValue));
            return this.writeValue(target, record, value2);
        }

        private JSFunctionCallNode getInitializersCallNode() {
            if (this.callInitializersNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.callInitializersNode = this.insert(JSFunctionCallNode.createCall());
            }
            return this.callInitializersNode;
        }

        private Object writeValue(Object target, ClassElementDefinitionRecord record, Object value2) {
            if (this.writeNode instanceof PropertySetNode) {
                ((PropertySetNode)this.writeNode).setValue(target, value2);
            } else if (this.writeNode instanceof PrivateFieldAddNode) {
                assert (record.getKey() instanceof HiddenKey) : record.getKey();
                ((PrivateFieldAddNode)this.writeNode).execute(target, record.getKey(), value2);
            } else if (this.writeNode instanceof DynamicObjectLibrary) {
                ClassElementDefinitionRecord.AutoAccessor autoAccessor = (ClassElementDefinitionRecord.AutoAccessor)record;
                ((DynamicObjectLibrary)this.writeNode).put((DynamicObject)target, autoAccessor.getBackingStorageKey(), value2);
            } else if (this.writeNode != null) {
                assert (JSRuntime.isPropertyKey(record.getKey())) : record.getKey();
                ((WriteElementNode)this.writeNode).executeWithTargetAndIndexAndValue(target, record.getKey(), value2);
            }
            return value2;
        }
    }
}

