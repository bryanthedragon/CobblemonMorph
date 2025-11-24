
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.GetAsyncIteratorNodeGen;
import com.oracle.truffle.js.nodes.access.GetIteratorNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public abstract class GetAsyncIteratorNode
extends GetIteratorNode {
    @Node.Child
    private PropertySetNode setState;
    @Node.Child
    private GetMethodNode getAsyncIteratorMethodNode;
    private final ConditionProfile asyncToSync = ConditionProfile.createBinaryProfile();

    protected GetAsyncIteratorNode(JSContext context, JavaScriptNode objectNode) {
        super(context, objectNode);
        this.setState = PropertySetNode.createSetHidden(JSFunction.ASYNC_FROM_SYNC_ITERATOR_KEY, context);
        this.getAsyncIteratorMethodNode = GetMethodNode.create(context, Symbol.SYMBOL_ASYNC_ITERATOR);
    }

    @Override
    @Specialization
    protected IteratorRecord doGetIterator(Object iteratedObject, @Cached(value="create()") IsCallableNode isCallableNode, @Cached(value="createCall()") JSFunctionCallNode methodCallNode, @Cached(value="create()") IsJSObjectNode isObjectNode) {
        Object method = this.getAsyncIteratorMethodNode.executeWithTarget(iteratedObject);
        if (this.asyncToSync.profile(method == Undefined.instance)) {
            Object syncMethod = this.getIteratorMethodNode().executeWithTarget(iteratedObject);
            IteratorRecord syncIteratorRecord = this.getIterator(iteratedObject, syncMethod, isCallableNode, methodCallNode, isObjectNode);
            JSObject asyncIterator = this.createAsyncFromSyncIterator(syncIteratorRecord);
            return IteratorRecord.create(asyncIterator, this.getNextMethodNode.getValue(asyncIterator), false);
        }
        return this.getIterator(iteratedObject, method, isCallableNode, methodCallNode, isObjectNode);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return GetAsyncIteratorNodeGen.create(this.context, GetAsyncIteratorNode.cloneUninitialized(this.objectNode, materializedTags));
    }

    private JSObject createAsyncFromSyncIterator(IteratorRecord syncIteratorRecord) {
        JSDynamicObject syncIterator = syncIteratorRecord.getIterator();
        if (!JSDynamicObject.isJSDynamicObject(syncIterator)) {
            throw Errors.createTypeErrorNotAnObject(syncIterator, this);
        }
        JSObject obj = JSOrdinary.create(this.context, this.context.getAsyncFromSyncIteratorFactory(), this.getRealm());
        this.setState.setValue(obj, syncIteratorRecord);
        return obj;
    }
}

