
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.StringIteratorPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.access.CreateIterResultObjectNode;
import com.oracle.truffle.js.nodes.access.HasHiddenKeyCacheNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class StringIteratorPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<StringIteratorPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new StringIteratorPrototypeBuiltins();

    protected StringIteratorPrototypeBuiltins() {
        super(JSString.ITERATOR_PROTOTYPE_NAME, StringIteratorPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, StringIteratorPrototype builtinEnum) {
        switch (builtinEnum) {
            case next: {
                return StringIteratorPrototypeBuiltinsFactory.StringIteratorNextNodeGen.create(context, builtin, StringIteratorPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class StringIteratorNextNode
    extends JSBuiltinNode {
        @Node.Child
        private HasHiddenKeyCacheNode isStringIteratorNode;
        @Node.Child
        private PropertyGetNode getIteratedObjectNode;
        @Node.Child
        private PropertyGetNode getNextIndexNode;
        @Node.Child
        private PropertySetNode setNextIndexNode;
        @Node.Child
        private PropertySetNode setIteratedObjectNode;
        @Node.Child
        private CreateIterResultObjectNode createIterResultObjectNode;
        @Node.Child
        private TruffleString.ReadCharUTF16Node stringReadNode;
        private final ConditionProfile isSurrogatePair = ConditionProfile.createCountingProfile();

        public StringIteratorNextNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.isStringIteratorNode = HasHiddenKeyCacheNode.create(JSString.ITERATED_STRING_ID);
            this.getIteratedObjectNode = PropertyGetNode.createGetHidden(JSString.ITERATED_STRING_ID, context);
            this.getNextIndexNode = PropertyGetNode.createGetHidden(JSString.STRING_ITERATOR_NEXT_INDEX_ID, context);
            this.setIteratedObjectNode = PropertySetNode.createSetHidden(JSString.ITERATED_STRING_ID, context);
            this.setNextIndexNode = PropertySetNode.createSetHidden(JSString.STRING_ITERATOR_NEXT_INDEX_ID, context);
            this.createIterResultObjectNode = CreateIterResultObjectNode.create(context);
            this.stringReadNode = TruffleString.ReadCharUTF16Node.create();
        }

        @Specialization(guards={"isStringIterator(iterator)"})
        protected JSDynamicObject doStringIterator(VirtualFrame frame, JSDynamicObject iterator, @Cached TruffleString.FromCodePointNode fromCodePointNode, @Cached TruffleString.SubstringByteIndexNode substringNode) {
            int length;
            Object iteratedString = this.getIteratedObjectNode.getValue(iterator);
            if (iteratedString == Undefined.instance) {
                return this.createIterResultObjectNode.execute(frame, Undefined.instance, true);
            }
            TruffleString string = (TruffleString)iteratedString;
            int index = this.getNextIndex(iterator);
            if (index >= (length = Strings.length(string))) {
                this.setIteratedObjectNode.setValue(iterator, Undefined.instance);
                return this.createIterResultObjectNode.execute(frame, Undefined.instance, true);
            }
            char first = Strings.charAt(this.stringReadNode, string, index);
            TruffleString result = this.isSurrogatePair.profile(Character.isHighSurrogate(first) && index + 1 < length) && Character.isLowSurrogate(Strings.charAt(this.stringReadNode, string, index + 1)) ? Strings.substring(this.getContext(), substringNode, string, index, 2) : Strings.fromCodePoint(fromCodePointNode, first);
            this.setNextIndexNode.setValue(iterator, index + Strings.length(result));
            return this.createIterResultObjectNode.execute(frame, result, false);
        }

        @Fallback
        protected JSDynamicObject doIncompatibleReceiver(Object iterator) {
            throw Errors.createTypeError("not a String Iterator");
        }

        protected final boolean isStringIterator(Object thisObj) {
            return this.isStringIteratorNode.executeHasHiddenKey(thisObj);
        }

        private int getNextIndex(JSDynamicObject iterator) {
            try {
                return this.getNextIndexNode.getValueInt(iterator);
            }
            catch (UnexpectedResultException e) {
                throw Errors.shouldNotReachHere();
            }
        }
    }

    public static enum StringIteratorPrototype implements BuiltinEnum<StringIteratorPrototype>
    {
        next(0);

        private final int length;

        private StringIteratorPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

