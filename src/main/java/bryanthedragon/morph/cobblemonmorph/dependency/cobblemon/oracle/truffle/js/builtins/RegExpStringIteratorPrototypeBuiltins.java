
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.RegExpPrototypeBuiltins;
import com.oracle.truffle.js.builtins.RegExpStringIteratorPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.access.CreateIterResultObjectNode;
import com.oracle.truffle.js.nodes.access.HasHiddenKeyCacheNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.cast.JSToLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class RegExpStringIteratorPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<RegExpStringIteratorPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new RegExpStringIteratorPrototypeBuiltins();

    protected RegExpStringIteratorPrototypeBuiltins() {
        super(JSString.REGEXP_ITERATOR_PROTOTYPE_NAME, RegExpStringIteratorPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, RegExpStringIteratorPrototype builtinEnum) {
        switch (builtinEnum) {
            case next: {
                return RegExpStringIteratorPrototypeBuiltinsFactory.RegExpStringIteratorNextNodeGen.create(context, builtin, RegExpStringIteratorPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class RegExpStringIteratorNextNode
    extends RegExpPrototypeBuiltins.RegExpPrototypeSymbolOperation {
        @Node.Child
        private HasHiddenKeyCacheNode isRegExpStringIteratorNode;
        @Node.Child
        private PropertyGetNode getIteratingRegExpNode;
        @Node.Child
        private PropertyGetNode getIteratedStringNode;
        @Node.Child
        private PropertyGetNode getGlobalNode;
        @Node.Child
        private PropertyGetNode getUnicodeNode;
        @Node.Child
        private PropertyGetNode getDoneNode;
        @Node.Child
        private PropertySetNode setDoneNode;
        @Node.Child
        private JSToStringNode toStringNode;
        @Node.Child
        private JSToLengthNode toLengthNode;
        @Node.Child
        private CreateIterResultObjectNode createIterResultObjectNode;
        private final ConditionProfile noMatchProfile = ConditionProfile.createCountingProfile();
        private final ConditionProfile globalProfile = ConditionProfile.createBinaryProfile();

        public RegExpStringIteratorNextNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.isRegExpStringIteratorNode = this.insert(HasHiddenKeyCacheNode.create(JSString.REGEXP_ITERATOR_ITERATING_REGEXP_ID));
        }

        @Specialization(guards={"isRegExpStringIterator(iterator)"})
        protected JSDynamicObject doRegExpStringIterator(VirtualFrame frame, JSDynamicObject iterator) {
            boolean fullUnicode;
            boolean global;
            boolean done;
            try {
                done = this.getGetDoneNode().getValueBoolean(iterator);
            }
            catch (UnexpectedResultException e) {
                throw Errors.shouldNotReachHere();
            }
            if (done) {
                return this.getCreateIterResultObjectNode().execute(frame, Undefined.instance, true);
            }
            Object regex = this.getGetIteratingRegExpNode().getValue(iterator);
            TruffleString string = (TruffleString)this.getGetIteratedStringNode().getValue(iterator);
            try {
                global = this.getGetGlobalNode().getValueBoolean(iterator);
                fullUnicode = this.getGetUnicodeNode().getValueBoolean(iterator);
            }
            catch (UnexpectedResultException e) {
                throw Errors.shouldNotReachHere();
            }
            Object match = this.regexExecIntl((JSDynamicObject)regex, string);
            if (this.noMatchProfile.profile(match == Null.instance)) {
                this.getSetDoneNode().setValueBoolean(iterator, true);
                return this.getCreateIterResultObjectNode().execute(frame, Undefined.instance, true);
            }
            if (this.globalProfile.profile(global)) {
                TruffleString matchStr = this.getToStringNode().executeString(this.read(match, 0));
                if (Strings.isEmpty(matchStr)) {
                    int thisIndex = (int)this.getToLengthNode().executeLong(this.getLastIndex(regex));
                    int nextIndex = fullUnicode ? this.advanceStringIndexUnicode(string, thisIndex) : thisIndex + 1;
                    this.setLastIndex(regex, nextIndex);
                }
                return this.getCreateIterResultObjectNode().execute(frame, match, false);
            }
            this.getSetDoneNode().setValueBoolean(iterator, true);
            return this.getCreateIterResultObjectNode().execute(frame, match, false);
        }

        @Fallback
        protected JSDynamicObject doIncompatibleReceiver(Object iterator) {
            throw Errors.createTypeError("not a RegExp String Iterator");
        }

        protected final boolean isRegExpStringIterator(Object thisObj) {
            return this.isRegExpStringIteratorNode.executeHasHiddenKey(thisObj);
        }

        private PropertyGetNode getGetIteratingRegExpNode() {
            if (this.getIteratingRegExpNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getIteratingRegExpNode = this.insert(PropertyGetNode.createGetHidden(JSString.REGEXP_ITERATOR_ITERATING_REGEXP_ID, this.getContext()));
            }
            return this.getIteratingRegExpNode;
        }

        private PropertyGetNode getGetIteratedStringNode() {
            if (this.getIteratedStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getIteratedStringNode = this.insert(PropertyGetNode.createGetHidden(JSString.REGEXP_ITERATOR_ITERATED_STRING_ID, this.getContext()));
            }
            return this.getIteratedStringNode;
        }

        private PropertyGetNode getGetGlobalNode() {
            if (this.getGlobalNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getGlobalNode = this.insert(PropertyGetNode.createGetHidden(JSString.REGEXP_ITERATOR_GLOBAL_ID, this.getContext()));
            }
            return this.getGlobalNode;
        }

        private PropertyGetNode getGetUnicodeNode() {
            if (this.getUnicodeNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getUnicodeNode = this.insert(PropertyGetNode.createGetHidden(JSString.REGEXP_ITERATOR_UNICODE_ID, this.getContext()));
            }
            return this.getUnicodeNode;
        }

        private PropertyGetNode getGetDoneNode() {
            if (this.getDoneNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getDoneNode = this.insert(PropertyGetNode.createGetHidden(JSString.REGEXP_ITERATOR_DONE_ID, this.getContext()));
            }
            return this.getDoneNode;
        }

        private PropertySetNode getSetDoneNode() {
            if (this.setDoneNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.setDoneNode = this.insert(PropertySetNode.createSetHidden(JSString.REGEXP_ITERATOR_DONE_ID, this.getContext()));
            }
            return this.setDoneNode;
        }

        private JSToStringNode getToStringNode() {
            if (this.toStringNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toStringNode = this.insert(JSToStringNode.create());
            }
            return this.toStringNode;
        }

        private JSToLengthNode getToLengthNode() {
            if (this.toLengthNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toLengthNode = this.insert(JSToLengthNode.create());
            }
            return this.toLengthNode;
        }

        private CreateIterResultObjectNode getCreateIterResultObjectNode() {
            if (this.createIterResultObjectNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.createIterResultObjectNode = this.insert(CreateIterResultObjectNode.create(this.getContext()));
            }
            return this.createIterResultObjectNode;
        }
    }

    public static enum RegExpStringIteratorPrototype implements BuiltinEnum<RegExpStringIteratorPrototype>
    {
        next(0);

        private final int length;

        private RegExpStringIteratorPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

