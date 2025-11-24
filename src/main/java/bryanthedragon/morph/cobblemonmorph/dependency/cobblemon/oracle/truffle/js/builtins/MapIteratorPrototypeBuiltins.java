
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.MapIteratorPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.access.CreateIterResultObjectNode;
import com.oracle.truffle.js.nodes.access.HasHiddenKeyCacheNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSMap;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSHashMap;

public final class MapIteratorPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<MapIteratorPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new MapIteratorPrototypeBuiltins();

    protected MapIteratorPrototypeBuiltins() {
        super(JSMap.ITERATOR_PROTOTYPE_NAME, MapIteratorPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, MapIteratorPrototype builtinEnum) {
        switch (builtinEnum) {
            case next: {
                return MapIteratorPrototypeBuiltinsFactory.MapIteratorNextNodeGen.create(context, builtin, MapIteratorPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class MapIteratorNextNode
    extends JSBuiltinNode {
        @Node.Child
        private HasHiddenKeyCacheNode isMapIteratorNode;
        @Node.Child
        private PropertyGetNode getIteratedObjectNode;
        @Node.Child
        private PropertyGetNode getNextIndexNode;
        @Node.Child
        private PropertyGetNode getIterationKindNode;
        @Node.Child
        private PropertySetNode setIteratedObjectNode;
        @Node.Child
        private CreateIterResultObjectNode createIterResultObjectNode;
        private final ConditionProfile detachedProf = ConditionProfile.createBinaryProfile();
        private final ConditionProfile doneProf = ConditionProfile.createBinaryProfile();
        private final ConditionProfile iterKindKey = ConditionProfile.createBinaryProfile();
        private final ConditionProfile iterKindValue = ConditionProfile.createBinaryProfile();

        public MapIteratorNextNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.isMapIteratorNode = HasHiddenKeyCacheNode.create(JSMap.MAP_ITERATION_KIND_ID);
            this.getIteratedObjectNode = PropertyGetNode.createGetHidden(JSRuntime.ITERATED_OBJECT_ID, context);
            this.getNextIndexNode = PropertyGetNode.createGetHidden(JSRuntime.ITERATOR_NEXT_INDEX, context);
            this.getIterationKindNode = PropertyGetNode.createGetHidden(JSMap.MAP_ITERATION_KIND_ID, context);
            this.setIteratedObjectNode = PropertySetNode.createSetHidden(JSRuntime.ITERATED_OBJECT_ID, context);
            this.createIterResultObjectNode = CreateIterResultObjectNode.create(context);
        }

        @Specialization(guards={"isMapIterator(iterator)"})
        protected JSDynamicObject doMapIterator(VirtualFrame frame, JSDynamicObject iterator) {
            Object result;
            Object map = this.getIteratedObjectNode.getValue(iterator);
            if (this.detachedProf.profile(map == Undefined.instance)) {
                return this.createIterResultObjectNode.execute(frame, Undefined.instance, true);
            }
            JSHashMap.Cursor mapCursor = (JSHashMap.Cursor)this.getNextIndexNode.getValue(iterator);
            int itemKind = this.getIterationKind(iterator);
            if (this.doneProf.profile(!mapCursor.advance())) {
                this.setIteratedObjectNode.setValue(iterator, Undefined.instance);
                return this.createIterResultObjectNode.execute(frame, Undefined.instance, true);
            }
            Object elementKey = mapCursor.getKey();
            Object elementValue = mapCursor.getValue();
            if (this.iterKindKey.profile(itemKind == 1)) {
                result = elementKey;
            } else if (this.iterKindValue.profile(itemKind == 2)) {
                result = elementValue;
            } else {
                assert (itemKind == 3);
                result = JSArray.createConstantObjectArray(this.getContext(), this.getRealm(), new Object[]{elementKey, elementValue});
            }
            return this.createIterResultObjectNode.execute(frame, result, false);
        }

        @Fallback
        protected JSDynamicObject doIncompatibleReceiver(Object iterator) {
            throw Errors.createTypeError("not a Map Iterator");
        }

        protected final boolean isMapIterator(Object thisObj) {
            return this.isMapIteratorNode.executeHasHiddenKey(thisObj);
        }

        private int getIterationKind(JSDynamicObject iterator) {
            try {
                return this.getIterationKindNode.getValueInt(iterator);
            }
            catch (UnexpectedResultException e) {
                throw Errors.shouldNotReachHere();
            }
        }
    }

    public static enum MapIteratorPrototype implements BuiltinEnum<MapIteratorPrototype>
    {
        next(0);

        private final int length;

        private MapIteratorPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

