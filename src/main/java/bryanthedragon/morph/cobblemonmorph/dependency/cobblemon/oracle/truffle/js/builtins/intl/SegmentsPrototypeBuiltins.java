
package com.oracle.truffle.js.builtins.intl;

import com.cobblemon.mod.relocations.ibm.icu.text.BreakIterator;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.intl.SegmentsPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerAsIntNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.intl.CreateSegmentDataObjectNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenter;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenterObject;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmentsObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class SegmentsPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<SegmentsPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new SegmentsPrototypeBuiltins();

    protected SegmentsPrototypeBuiltins() {
        super(JSSegmenter.SEGMENTS_PROTOTYPE_NAME, SegmentsPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, SegmentsPrototype builtinEnum) {
        switch (builtinEnum) {
            case containing: {
                return SegmentsPrototypeBuiltinsFactory.SegmentsContainingNodeGen.create(context, builtin, SegmentsPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case _iterator: {
                return SegmentsPrototypeBuiltinsFactory.SegmentsIteratorNodeGen.create(context, builtin, SegmentsPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
        }
        return null;
    }

    public static class CreateSegmentIteratorNode
    extends JavaScriptBaseNode {
        private final JSContext context;

        protected CreateSegmentIteratorNode(JSContext context) {
            this.context = context;
        }

        public static CreateSegmentIteratorNode create(JSContext context) {
            return new CreateSegmentIteratorNode(context);
        }

        public Object execute(JSSegmenterObject segmenter, TruffleString value2) {
            return JSSegmenter.createSegmentIterator(this.context, this.getRealm(), segmenter, value2);
        }
    }

    public static abstract class SegmentsIteratorNode
    extends JSBuiltinNode {
        @Node.Child
        private CreateSegmentIteratorNode createSegmentIteratorNode;

        public SegmentsIteratorNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
            this.createSegmentIteratorNode = CreateSegmentIteratorNode.create(context);
        }

        @Specialization
        public Object doSegments(JSSegmentsObject segments) {
            return this.createSegmentIteratorNode.execute(segments.getSegmentsSegmenter(), segments.getSegmentsString());
        }

        @Specialization(guards={"!isJSSegments(bummer)"})
        public Object doOther(Object bummer) {
            throw Errors.createTypeErrorSegmentsExpected();
        }
    }

    public static abstract class SegmentsContainingNode
    extends JSBuiltinNode {
        public SegmentsContainingNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        public Object doSegments(JSSegmentsObject segments, Object index, @Cached JSToIntegerAsIntNode toIntegerNode, @Cached(value="create(getContext())") CreateSegmentDataObjectNode createResultNode, @Cached TruffleString.ToJavaStringNode toJavaStringNode) {
            int n = toIntegerNode.executeInt(index);
            TruffleString string = segments.getSegmentsString();
            int len = Strings.length(string);
            if (n < 0 || n >= len) {
                return Undefined.instance;
            }
            JSSegmenterObject segmenter = segments.getSegmentsSegmenter();
            BreakIterator breakIterator = SegmentsContainingNode.getBreakIterator(segmenter, toJavaStringNode.execute(string));
            int startIndex = SegmentsContainingNode.findBoundaryBefore(breakIterator, n);
            int endIndex = SegmentsContainingNode.findBoundaryAfter(breakIterator, n);
            return createResultNode.execute(breakIterator, JSSegmenter.getGranularity(segmenter), string, startIndex, endIndex);
        }

        @CompilerDirectives.TruffleBoundary
        private static BreakIterator getBreakIterator(JSSegmenterObject segmenter, String string) {
            BreakIterator breakIterator = segmenter.getBreakIterator();
            breakIterator.setText(string);
            return breakIterator;
        }

        @CompilerDirectives.TruffleBoundary
        private static int findBoundaryBefore(BreakIterator breakIterator, int index) {
            return breakIterator.preceding(index + 1);
        }

        @CompilerDirectives.TruffleBoundary
        private static int findBoundaryAfter(BreakIterator breakIterator, int index) {
            return breakIterator.following(index);
        }

        @Specialization(guards={"!isJSSegments(bummer)"})
        public Object doOther(Object bummer, Object index) {
            throw Errors.createTypeErrorSegmentsExpected();
        }
    }

    public static enum SegmentsPrototype implements BuiltinEnum<SegmentsPrototype>
    {
        containing(1),
        _iterator(0){

            @Override
            public Object getKey() {
                return Symbol.SYMBOL_ITERATOR;
            }
        };

        private final int length;

        private SegmentsPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

