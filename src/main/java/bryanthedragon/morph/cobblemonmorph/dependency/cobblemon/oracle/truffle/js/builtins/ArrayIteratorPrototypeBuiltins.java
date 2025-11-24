
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.builtins.ArrayIteratorPrototypeBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.nodes.access.CreateIterResultObjectNode;
import com.oracle.truffle.js.nodes.access.HasHiddenKeyCacheNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.array.JSGetLengthNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class ArrayIteratorPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<ArrayIteratorPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new ArrayIteratorPrototypeBuiltins();

    protected ArrayIteratorPrototypeBuiltins() {
        super(JSArray.ITERATOR_PROTOTYPE_NAME, ArrayIteratorPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ArrayIteratorPrototype builtinEnum) {
        switch (builtinEnum) {
            case next: {
                return ArrayIteratorPrototypeBuiltinsFactory.ArrayIteratorNextNodeGen.create(context, builtin, ArrayIteratorPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class ArrayIteratorNextNode
    extends JSBuiltinNode {
        @Node.Child
        private HasHiddenKeyCacheNode isArrayIteratorNode = HasHiddenKeyCacheNode.create(JSArray.ARRAY_ITERATION_KIND_ID);
        @Node.Child
        private PropertyGetNode getIteratedObjectNode;
        @Node.Child
        private PropertyGetNode getNextIndexNode;
        @Node.Child
        private PropertyGetNode getIterationKindNode;
        @Node.Child
        private PropertySetNode setNextIndexNode;
        @Node.Child
        private PropertySetNode setIteratedObjectNode;
        @Node.Child
        private CreateIterResultObjectNode createIterResultObjectNode;
        @Node.Child
        private JSGetLengthNode getLengthNode;
        @Node.Child
        private ReadElementNode readElementNode;
        private final ConditionProfile intIndexProfile;
        private final BranchProfile errorBranch;
        private final ConditionProfile isTypedArrayProfile;

        public ArrayIteratorNextNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.getIteratedObjectNode = PropertyGetNode.createGetHidden(JSRuntime.ITERATED_OBJECT_ID, context);
            this.getNextIndexNode = PropertyGetNode.createGetHidden(JSRuntime.ITERATOR_NEXT_INDEX, context);
            this.getIterationKindNode = PropertyGetNode.createGetHidden(JSArray.ARRAY_ITERATION_KIND_ID, context);
            this.setIteratedObjectNode = PropertySetNode.createSetHidden(JSRuntime.ITERATED_OBJECT_ID, context);
            this.setNextIndexNode = PropertySetNode.createSetHidden(JSRuntime.ITERATOR_NEXT_INDEX, context);
            this.createIterResultObjectNode = CreateIterResultObjectNode.create(context);
            this.intIndexProfile = ConditionProfile.createBinaryProfile();
            this.isTypedArrayProfile = ConditionProfile.createBinaryProfile();
            this.errorBranch = BranchProfile.create();
        }

        @Specialization(guards={"isArrayIterator(iterator)"})
        protected JSDynamicObject doArrayIterator(VirtualFrame frame, JSDynamicObject iterator) {
            Object result;
            long length;
            Object array = this.getIteratedObjectNode.getValue(iterator);
            if (array == Undefined.instance) {
                return this.createIterResultObjectNode.execute(frame, Undefined.instance, true);
            }
            long index = this.getNextIndex(iterator);
            int itemKind = this.getIterationKind(iterator);
            if (this.isTypedArrayProfile.profile(JSArrayBufferView.isJSArrayBufferView(array))) {
                JSTypedArrayObject typedArray = (JSTypedArrayObject)array;
                if (JSArrayBufferView.hasDetachedBuffer(typedArray, this.getContext())) {
                    this.errorBranch.enter();
                    throw Errors.createTypeError("Cannot perform Array Iterator.prototype.next on a detached ArrayBuffer");
                }
                length = JSArrayBufferView.typedArrayGetLength(typedArray);
            } else {
                length = this.getLength().executeLong(array);
            }
            if (index >= length) {
                this.setIteratedObjectNode.setValue(iterator, Undefined.instance);
                return this.createIterResultObjectNode.execute(frame, Undefined.instance, true);
            }
            this.setNextIndexNode.setValue(iterator, index + 1L);
            if (itemKind == 1) {
                return this.createIterResultObjectNode.execute(frame, this.indexToJS(index), false);
            }
            Object elementValue = this.readElement().executeWithTargetAndIndex(array, index);
            if (itemKind == 2) {
                result = elementValue;
            } else {
                assert (itemKind == 3);
                result = JSArray.createConstantObjectArray(this.getContext(), this.getRealm(), new Object[]{this.indexToJS(index), elementValue});
            }
            return this.createIterResultObjectNode.execute(frame, result, false);
        }

        @Fallback
        protected JSDynamicObject doIncompatibleReceiver(Object iterator) {
            throw Errors.createTypeError("not an Array Iterator");
        }

        protected final boolean isArrayIterator(Object thisObj) {
            return this.isArrayIteratorNode.executeHasHiddenKey(thisObj);
        }

        private long getNextIndex(JSDynamicObject iterator) {
            try {
                return this.getNextIndexNode.getValueLong(iterator);
            }
            catch (UnexpectedResultException e) {
                throw Errors.shouldNotReachHere();
            }
        }

        private int getIterationKind(JSDynamicObject iterator) {
            try {
                return this.getIterationKindNode.getValueInt(iterator);
            }
            catch (UnexpectedResultException e) {
                throw Errors.shouldNotReachHere();
            }
        }

        private Object indexToJS(long index) {
            if (this.intIndexProfile.profile(JSRuntime.longIsRepresentableAsInt(index))) {
                return (int)index;
            }
            return (double)index;
        }

        private ReadElementNode readElement() {
            if (this.readElementNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.readElementNode = this.insert(ReadElementNode.create(this.getContext()));
            }
            return this.readElementNode;
        }

        private JSGetLengthNode getLength() {
            if (this.getLengthNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getLengthNode = this.insert(JSGetLengthNode.create(this.getContext()));
            }
            return this.getLengthNode;
        }
    }

    public static enum ArrayIteratorPrototype implements BuiltinEnum<ArrayIteratorPrototype>
    {
        next(0);

        private final int length;

        private ArrayIteratorPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

