
package com.oracle.truffle.js.builtins.foreign;

import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.foreign.ForeignIterablePrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

public final class ForeignIterablePrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<ForeignIterablePrototype> {
    public static final JSBuiltinsContainer BUILTINS = new ForeignIterablePrototypeBuiltins();

    protected ForeignIterablePrototypeBuiltins() {
        super(null, ForeignIterablePrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, ForeignIterablePrototype builtinEnum) {
        switch (builtinEnum) {
            case _iterator: {
                return ForeignIterablePrototypeBuiltinsFactory.IteratorNodeGen.create(context, builtin, ForeignIterablePrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    @ImportStatic(value={JSConfig.class})
    public static abstract class IteratorNode
    extends JSBuiltinNode {
        @Node.Child
        private PropertySetNode setEnumerateIteratorNode;
        private final BranchProfile errorBranch = BranchProfile.create();

        public IteratorNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.setEnumerateIteratorNode = PropertySetNode.createSetHidden(JSRuntime.ENUMERATE_ITERATOR_ID, context);
        }

        @Specialization
        protected JSDynamicObject execute(Object target, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            Object iterator;
            if (!interop.hasIterator(target)) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorNotIterable(target, null);
            }
            try {
                iterator = interop.getIterator(target);
            }
            catch (UnsupportedMessageException e) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorInteropException(target, e, "getIterator", null);
            }
            JSObject iteratorObj = JSOrdinary.create(this.getContext(), this.getContext().getEnumerateIteratorFactory(), this.getRealm());
            this.setEnumerateIteratorNode.setValue(iteratorObj, iterator);
            return iteratorObj;
        }
    }

    public static enum ForeignIterablePrototype implements BuiltinEnum<ForeignIterablePrototype>
    {
        _iterator(0);

        private final int length;

        private ForeignIterablePrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public Object getKey() {
            return this == _iterator ? Symbol.SYMBOL_ITERATOR : BuiltinEnum.super.getKey();
        }
    }
}

