
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.SetPrototypeBuiltinsFactory;
import com.oracle.truffle.js.builtins.helper.JSCollectionsNormalizeNode;
import com.oracle.truffle.js.builtins.helper.JSCollectionsNormalizeNodeGen;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.access.GetIteratorBaseNode;
import com.oracle.truffle.js.nodes.access.IteratorCloseNode;
import com.oracle.truffle.js.nodes.access.IteratorStepNode;
import com.oracle.truffle.js.nodes.access.IteratorValueNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSSet;
import com.oracle.truffle.js.runtime.builtins.JSSetObject;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSHashMap;

public final class SetPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<SetPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new SetPrototypeBuiltins();
    public static final JSBuiltinsContainer NEW_SET_BUILTINS = new NewSetPrototypeBuiltins();

    protected SetPrototypeBuiltins() {
        super(JSSet.PROTOTYPE_NAME, SetPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, SetPrototype builtinEnum) {
        switch (builtinEnum) {
            case clear: {
                return SetPrototypeBuiltinsFactory.JSSetClearNodeGen.create(context, builtin, SetPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case delete: {
                return SetPrototypeBuiltinsFactory.JSSetDeleteNodeGen.create(context, builtin, SetPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case add: {
                return SetPrototypeBuiltinsFactory.JSSetAddNodeGen.create(context, builtin, SetPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case has: {
                return SetPrototypeBuiltinsFactory.JSSetHasNodeGen.create(context, builtin, SetPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case forEach: {
                return SetPrototypeBuiltinsFactory.JSSetForEachNodeGen.create(context, builtin, SetPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case values: {
                return SetPrototypeBuiltinsFactory.CreateSetIteratorNodeGen.create(context, builtin, 2, SetPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case entries: {
                return SetPrototypeBuiltinsFactory.CreateSetIteratorNodeGen.create(context, builtin, 3, SetPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class CreateSetIteratorNode
    extends JSBuiltinNode {
        private final int iterationKind;
        @Node.Child
        private CreateObjectNode.CreateObjectWithPrototypeNode createObjectNode;
        @Node.Child
        private PropertySetNode setNextIndexNode;
        @Node.Child
        private PropertySetNode setIteratedObjectNode;
        @Node.Child
        private PropertySetNode setIterationKindNode;

        public CreateSetIteratorNode(JSContext context, JSBuiltin builtin, int iterationKind) {
            super(context, builtin);
            this.iterationKind = iterationKind;
            this.createObjectNode = CreateObjectNode.createOrdinaryWithPrototype(context);
            this.setIteratedObjectNode = PropertySetNode.createSetHidden(JSRuntime.ITERATED_OBJECT_ID, context);
            this.setNextIndexNode = PropertySetNode.createSetHidden(JSRuntime.ITERATOR_NEXT_INDEX, context);
            this.setIterationKindNode = PropertySetNode.createSetHidden(JSSet.SET_ITERATION_KIND_ID, context);
        }

        @Specialization
        protected JSDynamicObject doSet(JSSetObject set2) {
            JSDynamicObject iterator = this.createObjectNode.execute(this.getRealm().getSetIteratorPrototype());
            this.setIteratedObjectNode.setValue(iterator, set2);
            this.setNextIndexNode.setValue(iterator, JSSet.getInternalSet(set2).getEntries());
            this.setIterationKindNode.setValueInt(iterator, this.iterationKind);
            return iterator;
        }

        @Specialization(guards={"!isJSSet(thisObj)"})
        protected JSDynamicObject doIncompatibleReceiver(Object thisObj) {
            throw Errors.createTypeError("not a Set");
        }
    }

    public static abstract class JSSetForEachNode
    extends JSBuiltinNode {
        public JSSetForEachNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isCallable.executeBoolean(callback)"}, limit="1")
        protected Object forEachFunction(JSSetObject thisObj, JSDynamicObject callback, Object thisArg, @Cached @Cached.Shared(value="isCallable") IsCallableNode isCallable, @Cached(value="createCall()") JSFunctionCallNode callNode) {
            JSHashMap map = JSSet.getInternalSet(thisObj);
            JSHashMap.Cursor cursor = map.getEntries();
            while (cursor.advance()) {
                Object key = cursor.getKey();
                callNode.executeCall(JSArguments.create(thisArg, callback, key, key, thisObj));
            }
            return Undefined.instance;
        }

        @Specialization(guards={"!isCallable.executeBoolean(callback)"}, limit="1")
        protected static Object forEachFunctionNoFunction(JSSetObject thisObj, Object callback, Object thisArg, @Cached @Cached.Shared(value="isCallable") IsCallableNode isCallable) {
            throw Errors.createTypeErrorCallableExpected();
        }

        @Specialization(guards={"!isJSSet(thisObj)"})
        protected static Object forEachFunctionNoSet(Object thisObj, Object callback, Object thisArg) {
            throw Errors.createTypeErrorSetExpected();
        }
    }

    public static abstract class JSSetIsDisjointFromNode
    extends JSSetNewOperation {
        private final BranchProfile hasError = BranchProfile.create();

        public JSSetIsDisjointFromNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Boolean isDisjointFrom(JSSetObject set2, Object iterable) {
            Object hasCheck = this.getHasFunction(set2);
            if (!this.isCallable(hasCheck)) {
                this.hasError.enter();
                throw Errors.createTypeErrorCallableExpected();
            }
            IteratorRecord iteratorRecord = this.getIteratorNode.execute(iterable);
            try {
                Object nextValue;
                Object has;
                do {
                    Object next;
                    if ((next = this.iteratorStepNode.execute(iteratorRecord)) == Boolean.FALSE) {
                        return Boolean.TRUE;
                    }
                    nextValue = this.iteratorValueNode.execute(next);
                } while ((has = this.call(hasCheck, set2, nextValue)) != Boolean.TRUE);
                return Boolean.FALSE;
            }
            catch (AbstractTruffleException ex) {
                this.iteratorError.enter();
                this.iteratorCloseAbrupt(iteratorRecord.getIterator());
                throw ex;
            }
        }

        @Specialization(guards={"!isJSSet(thisObj)"})
        protected boolean notSet(Object thisObj, Object key) {
            throw Errors.createTypeErrorSetExpected();
        }
    }

    public static abstract class JSSetIsSupersetOfNode
    extends JSSetNewOperation {
        private final BranchProfile hasError = BranchProfile.create();

        public JSSetIsSupersetOfNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Boolean isSupersetOf(JSSetObject set2, Object iterable) {
            Object hasCheck = this.getHasFunction(set2);
            if (!this.isCallable(hasCheck)) {
                this.hasError.enter();
                throw Errors.createTypeErrorCallableExpected();
            }
            IteratorRecord iteratorRecord = this.getIteratorNode.execute(iterable);
            try {
                Object nextValue;
                Object has;
                do {
                    Object next;
                    if ((next = this.iteratorStepNode.execute(iteratorRecord)) == Boolean.FALSE) {
                        return Boolean.TRUE;
                    }
                    nextValue = this.iteratorValueNode.execute(next);
                } while ((has = this.call(hasCheck, set2, nextValue)) != Boolean.FALSE);
                return Boolean.FALSE;
            }
            catch (AbstractTruffleException ex) {
                this.iteratorError.enter();
                this.iteratorCloseAbrupt(iteratorRecord.getIterator());
                throw ex;
            }
        }

        @Specialization(guards={"!isJSSet(thisObj)"})
        protected boolean notSet(Object thisObj, Object key) {
            throw Errors.createTypeErrorSetExpected();
        }
    }

    public static abstract class JSSetIsSubsetOfNode
    extends JSSetNewOperation {
        private final BranchProfile needCreateNewBranch = BranchProfile.create();
        private final BranchProfile isObjectError = BranchProfile.create();

        public JSSetIsSubsetOfNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Boolean isSubsetOf(JSSetObject set2, Object iterable) {
            IteratorRecord iteratorRecord = this.getIteratorNode.execute(set2);
            if (!JSRuntime.isObject(iterable)) {
                this.isObjectError.enter();
                throw Errors.createTypeErrorNotIterable(iterable, this);
            }
            JSDynamicObject otherSet = (JSDynamicObject)iterable;
            Object hasCheck = this.getHasFunction(otherSet);
            if (!this.isCallable(hasCheck)) {
                this.needCreateNewBranch.enter();
                otherSet = (JSDynamicObject)this.constructSet(new Object[0]);
                this.addEntryFromIterable(otherSet, iterable, this.getAddFunction(otherSet));
                hasCheck = this.getHasFunction(otherSet);
            }
            try {
                Object nextValue;
                Object has;
                do {
                    Object next;
                    if ((next = this.iteratorStepNode.execute(iteratorRecord)) == Boolean.FALSE) {
                        return Boolean.TRUE;
                    }
                    nextValue = this.iteratorValueNode.execute(next);
                } while ((has = this.call(hasCheck, otherSet, nextValue)) != Boolean.FALSE);
                return Boolean.FALSE;
            }
            catch (AbstractTruffleException ex) {
                this.iteratorError.enter();
                this.iteratorCloseAbrupt(iteratorRecord.getIterator());
                throw ex;
            }
        }

        @Specialization(guards={"!isJSSet(thisObj)"})
        protected boolean notSet(Object thisObj, Object key) {
            throw Errors.createTypeErrorSetExpected();
        }
    }

    public static abstract class JSSetSymmetricDifferenceNode
    extends JSSetNewOperation {
        private final BranchProfile removerError = BranchProfile.create();

        public JSSetSymmetricDifferenceNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject symmetricDifference(JSSetObject set2, Object iterable) {
            JSDynamicObject newSet = (JSDynamicObject)this.constructSet(set2);
            Object remover = this.getRemoveFunction(newSet);
            if (!this.isCallable(remover)) {
                this.removerError.enter();
                throw Errors.createTypeErrorCallableExpected();
            }
            Object adder = this.getAddFunction(newSet);
            if (!this.isCallable(adder)) {
                this.adderError.enter();
                throw Errors.createTypeErrorCallableExpected();
            }
            IteratorRecord iteratorRecord = this.getIteratorNode.execute(iterable);
            try {
                while (true) {
                    Object next;
                    if ((next = this.iteratorStepNode.execute(iteratorRecord)) == Boolean.FALSE) {
                        return newSet;
                    }
                    Object nextValue = this.iteratorValueNode.execute(next);
                    Object removed = this.call(remover, newSet, nextValue);
                    if (removed != Boolean.FALSE) continue;
                    this.call(adder, newSet, nextValue);
                }
            }
            catch (AbstractTruffleException ex) {
                this.iteratorError.enter();
                this.iteratorCloseAbrupt(iteratorRecord.getIterator());
                throw ex;
            }
        }

        @Specialization(guards={"!isJSSet(thisObj)"})
        protected boolean notSet(Object thisObj, Object key) {
            throw Errors.createTypeErrorSetExpected();
        }
    }

    public static abstract class JSSetDifferenceNode
    extends JSSetNewOperation {
        private final BranchProfile removerError = BranchProfile.create();

        public JSSetDifferenceNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject difference(JSSetObject set2, Object iterable) {
            JSDynamicObject newSet = (JSDynamicObject)this.constructSet(set2);
            Object remover = this.getRemoveFunction(newSet);
            if (!this.isCallable(remover)) {
                this.removerError.enter();
                throw Errors.createTypeErrorCallableExpected();
            }
            IteratorRecord iteratorRecord = this.getIteratorNode.execute(iterable);
            try {
                while (true) {
                    Object next;
                    if ((next = this.iteratorStepNode.execute(iteratorRecord)) == Boolean.FALSE) {
                        return newSet;
                    }
                    Object nextValue = this.iteratorValueNode.execute(next);
                    this.call(remover, newSet, nextValue);
                }
            }
            catch (AbstractTruffleException ex) {
                this.iteratorError.enter();
                this.iteratorCloseAbrupt(iteratorRecord.getIterator());
                throw ex;
            }
        }

        @Specialization(guards={"!isJSSet(thisObj)"})
        protected boolean notSet(Object thisObj, Object key) {
            throw Errors.createTypeErrorSetExpected();
        }
    }

    public static abstract class JSSetIntersectionNode
    extends JSSetNewOperation {
        private final BranchProfile hasError = BranchProfile.create();

        public JSSetIntersectionNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject intersection(JSSetObject set2, Object iterable) {
            JSDynamicObject newSet = (JSDynamicObject)this.constructSet(new Object[0]);
            Object hasCheck = this.getHasFunction(set2);
            if (!this.isCallable(hasCheck)) {
                this.hasError.enter();
                throw Errors.createTypeErrorCallableExpected();
            }
            Object adder = this.getAddFunction(newSet);
            if (!this.isCallable(adder)) {
                this.adderError.enter();
                throw Errors.createTypeErrorCallableExpected();
            }
            IteratorRecord iteratorRecord = this.getIteratorNode.execute(iterable);
            try {
                while (true) {
                    Object next;
                    if ((next = this.iteratorStepNode.execute(iteratorRecord)) == Boolean.FALSE) {
                        return newSet;
                    }
                    Object nextValue = this.iteratorValueNode.execute(next);
                    Object has = this.call(hasCheck, set2, nextValue);
                    if (has != Boolean.TRUE) continue;
                    this.call(adder, newSet, nextValue);
                }
            }
            catch (AbstractTruffleException ex) {
                this.iteratorError.enter();
                this.iteratorCloseAbrupt(iteratorRecord.getIterator());
                throw ex;
            }
        }

        @Specialization(guards={"!isJSSet(thisObj)"})
        protected boolean notSet(Object thisObj, Object key) {
            throw Errors.createTypeErrorSetExpected();
        }
    }

    public static abstract class JSSetUnionNode
    extends JSSetNewOperation {
        public JSSetUnionNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject union(JSSetObject set2, Object iterable) {
            JSDynamicObject newSet = (JSDynamicObject)this.constructSet(set2);
            Object adder = this.getAddFunction(newSet);
            this.addEntryFromIterable(newSet, iterable, adder);
            return newSet;
        }

        @Specialization(guards={"!isJSSet(thisObj)"})
        protected boolean notSet(Object thisObj, Object key) {
            throw Errors.createTypeErrorSetExpected();
        }
    }

    protected static abstract class JSSetNewOperation
    extends JSSetOperation {
        @Node.Child
        protected GetIteratorBaseNode getIteratorNode;
        @Node.Child
        protected IteratorStepNode iteratorStepNode;
        @Node.Child
        protected IteratorValueNode iteratorValueNode;
        @Node.Child
        protected IteratorCloseNode iteratorCloseNode;
        @Node.Child
        protected JSFunctionCallNode callFunctionNode;
        @Node.Child
        protected PropertyGetNode getAddNode;
        @Node.Child
        protected PropertyGetNode getRemoveNode;
        @Node.Child
        protected PropertyGetNode getHasNode;
        @Node.Child
        protected IsCallableNode isCallableNode;
        protected final BranchProfile iteratorError = BranchProfile.create();
        protected final BranchProfile adderError = BranchProfile.create();

        protected JSSetNewOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.getIteratorNode = GetIteratorBaseNode.create();
            this.iteratorStepNode = IteratorStepNode.create();
            this.iteratorValueNode = IteratorValueNode.create();
            this.iteratorCloseNode = IteratorCloseNode.create(context);
        }

        protected Object addEntryFromIterable(Object target, Object iterable, Object adder) {
            if (!this.isCallable(adder)) {
                this.adderError.enter();
                throw Errors.createTypeErrorCallableExpected();
            }
            IteratorRecord iteratorRecord = this.getIteratorNode.execute(iterable);
            try {
                while (true) {
                    Object next;
                    if ((next = this.iteratorStepNode.execute(iteratorRecord)) == Boolean.FALSE) {
                        return target;
                    }
                    Object nextValue = this.iteratorValueNode.execute(next);
                    this.call(adder, target, nextValue);
                }
            }
            catch (AbstractTruffleException ex) {
                this.iteratorError.enter();
                this.iteratorCloseAbrupt(iteratorRecord.getIterator());
                throw ex;
            }
        }

        protected final void iteratorCloseAbrupt(JSDynamicObject iterator) {
            if (this.iteratorCloseNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.iteratorCloseNode = this.insert(IteratorCloseNode.create(this.getContext()));
            }
            this.iteratorCloseNode.executeAbrupt(iterator);
        }

        protected Object call(Object function, Object target, Object ... userArguments) {
            if (this.callFunctionNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.callFunctionNode = this.insert(JSFunctionCallNode.createCall());
            }
            return this.callFunctionNode.executeCall(JSArguments.create(target, function, userArguments));
        }

        protected final Object getAddFunction(Object object) {
            if (this.getAddNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getAddNode = this.insert(PropertyGetNode.create(Strings.ADD, false, this.getContext()));
            }
            return this.getAddNode.getValue(object);
        }

        protected final Object getRemoveFunction(Object object) {
            if (this.getRemoveNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getRemoveNode = this.insert(PropertyGetNode.create(Strings.DELETE, false, this.getContext()));
            }
            return this.getRemoveNode.getValue(object);
        }

        protected final Object getHasFunction(Object object) {
            if (this.getHasNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getHasNode = this.insert(PropertyGetNode.create(Strings.HAS, false, this.getContext()));
            }
            return this.getHasNode.getValue(object);
        }

        protected final Object constructSet(Object ... arguments) {
            JSFunctionObject ctr = this.getRealm().getSetConstructor();
            return JSRuntime.construct(ctr, arguments);
        }

        protected final boolean isCallable(Object object) {
            if (this.isCallableNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.isCallableNode = this.insert(IsCallableNode.create());
            }
            return this.isCallableNode.executeBoolean(object);
        }
    }

    public static abstract class JSSetHasNode
    extends JSSetOperation {
        public JSSetHasNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean has(JSSetObject thisObj, Object key) {
            Object normalizedKey = this.normalize(key);
            return JSSet.getInternalSet(thisObj).has(normalizedKey);
        }

        @Specialization(guards={"!isJSSet(thisObj)"})
        protected boolean hasNoObject(Object thisObj, Object key) {
            throw Errors.createTypeErrorSetExpected();
        }
    }

    public static abstract class JSSetAddNode
    extends JSSetOperation {
        public JSSetAddNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected JSDynamicObject add(JSSetObject thisObj, Object key) {
            Object normalizedKey = this.normalize(key);
            JSSet.getInternalSet(thisObj).put(normalizedKey, PRESENT);
            return thisObj;
        }

        @Specialization(guards={"!isJSSet(thisObj)"})
        protected static JSDynamicObject notSet(Object thisObj, Object key) {
            throw Errors.createTypeErrorSetExpected();
        }
    }

    public static abstract class JSSetDeleteNode
    extends JSSetOperation {
        public JSSetDeleteNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean delete(JSSetObject thisObj, Object key) {
            Object normalizedKey = this.normalize(key);
            return JSSet.getInternalSet(thisObj).remove(normalizedKey);
        }

        @Specialization(guards={"!isJSSet(thisObj)"})
        protected static boolean notSet(Object thisObj, Object key) {
            throw Errors.createTypeErrorSetExpected();
        }
    }

    public static abstract class JSSetClearNode
    extends JSBuiltinNode {
        public JSSetClearNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected static JSDynamicObject clear(JSSetObject thisObj) {
            JSSet.getInternalSet(thisObj).clear();
            return Undefined.instance;
        }

        @Specialization(guards={"!isJSSet(thisObj)"})
        protected static JSDynamicObject notSet(Object thisObj) {
            throw Errors.createTypeErrorSetExpected();
        }
    }

    public static abstract class JSSetOperation
    extends JSBuiltinNode {
        protected static final Object PRESENT = new Object();
        @Node.Child
        private JSCollectionsNormalizeNode normalizeNode;

        public JSSetOperation(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected Object normalize(Object value2) {
            if (this.normalizeNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.normalizeNode = this.insert(JSCollectionsNormalizeNodeGen.create());
            }
            return this.normalizeNode.execute(value2);
        }
    }

    public static final class NewSetPrototypeBuiltins
    extends JSBuiltinsContainer.SwitchEnum<NewSetPrototype> {
        protected NewSetPrototypeBuiltins() {
            super(NewSetPrototype.class);
        }

        @Override
        protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, NewSetPrototype builtinEnum) {
            switch (builtinEnum) {
                case union: {
                    return SetPrototypeBuiltinsFactory.JSSetUnionNodeGen.create(context, builtin, NewSetPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
                }
                case intersection: {
                    return SetPrototypeBuiltinsFactory.JSSetIntersectionNodeGen.create(context, builtin, NewSetPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
                }
                case difference: {
                    return SetPrototypeBuiltinsFactory.JSSetDifferenceNodeGen.create(context, builtin, NewSetPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
                }
                case symmetricDifference: {
                    return SetPrototypeBuiltinsFactory.JSSetSymmetricDifferenceNodeGen.create(context, builtin, NewSetPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
                }
                case isSubsetOf: {
                    return SetPrototypeBuiltinsFactory.JSSetIsSubsetOfNodeGen.create(context, builtin, NewSetPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
                }
                case isSupersetOf: {
                    return SetPrototypeBuiltinsFactory.JSSetIsSupersetOfNodeGen.create(context, builtin, NewSetPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
                }
                case isDisjointFrom: {
                    return SetPrototypeBuiltinsFactory.JSSetIsDisjointFromNodeGen.create(context, builtin, NewSetPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
                }
            }
            return null;
        }

        public static enum NewSetPrototype implements BuiltinEnum<NewSetPrototype>
        {
            union(1),
            intersection(1),
            difference(1),
            symmetricDifference(1),
            isSubsetOf(1),
            isSupersetOf(1),
            isDisjointFrom(1);

            private final int length;

            private NewSetPrototype(int length) {
                this.length = length;
            }

            @Override
            public int getLength() {
                return this.length;
            }
        }
    }

    public static enum SetPrototype implements BuiltinEnum<SetPrototype>
    {
        clear(0),
        delete(1),
        add(1),
        has(1),
        forEach(1),
        values(0),
        entries(0);

        private final int length;

        private SetPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

