
package com.oracle.truffle.js.runtime.builtins.intl;

import com.cobblemon.mod.relocations.ibm.icu.text.BreakIterator;
import com.cobblemon.mod.relocations.ibm.icu.util.ULocale;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.intl.SegmentIteratorPrototypeBuiltins;
import com.oracle.truffle.js.builtins.intl.SegmenterFunctionBuiltins;
import com.oracle.truffle.js.builtins.intl.SegmenterPrototypeBuiltins;
import com.oracle.truffle.js.builtins.intl.SegmentsPrototypeBuiltins;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmentIteratorObject;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenterObject;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmentsObject;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.Locale;

public final class JSSegmenter
extends JSNonProxy
implements JSConstructorFactory.WithFunctions,
PrototypeSupplier {
    public static final TruffleString CLASS_NAME = Strings.constant("Segmenter");
    public static final TruffleString PROTOTYPE_NAME = Strings.constant("Segmenter.prototype");
    public static final TruffleString SEGMENTS_PROTOTYPE_NAME = Strings.constant("Segments.prototype");
    public static final TruffleString ITERATOR_CLASS_NAME = Strings.constant("Segmenter String Iterator");
    public static final TruffleString ITERATOR_PROTOTYPE_NAME = Strings.constant("Segment Iterator.prototype");
    public static final TruffleString TO_STRING_TAG = Strings.constant("Intl.Segmenter");
    public static final TruffleString GET_BREAK_TYPE = Strings.constant("get breakType");
    public static final TruffleString GET_INDEX = Strings.constant("get index");
    public static final JSSegmenter INSTANCE = new JSSegmenter();

    private JSSegmenter() {
    }

    public static boolean isJSSegmenter(Object obj) {
        return obj instanceof JSSegmenterObject;
    }

    @Override
    public TruffleString getClassName() {
        return CLASS_NAME;
    }

    @Override
    public TruffleString getClassName(JSDynamicObject object) {
        return this.getClassName();
    }

    @Override
    public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
        JSContext ctx = realm.getContext();
        JSObject segmenterPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putConstructorProperty(ctx, segmenterPrototype, ctor);
        JSObjectUtil.putFunctionsFromContainer(realm, segmenterPrototype, SegmenterPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putToStringTag(segmenterPrototype, TO_STRING_TAG);
        return segmenterPrototype;
    }

    @Override
    public Shape makeInitialShape(JSContext ctx, JSDynamicObject prototype) {
        return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, ctx);
    }

    public static JSConstructor createConstructor(JSRealm realm) {
        return INSTANCE.createConstructorAndPrototype(realm, SegmenterFunctionBuiltins.BUILTINS);
    }

    public static JSSegmenterObject create(JSContext context, JSRealm realm) {
        InternalState state = new InternalState();
        JSObjectFactory factory = context.getSegmenterFactory();
        JSSegmenterObject obj = new JSSegmenterObject(factory.getShape(realm), state);
        factory.initProto(obj, realm);
        return context.trackAllocation(obj);
    }

    public static JSSegmentIteratorObject createSegmentIterator(JSContext context, JSRealm realm, JSDynamicObject segmenter, TruffleString value2) {
        BreakIterator icuIterator = JSSegmenter.createBreakIterator(segmenter, Strings.toJavaString(value2));
        Granularity granularity = JSSegmenter.getGranularity(segmenter);
        IteratorState iteratorState = new IteratorState(value2, icuIterator, granularity);
        JSObjectFactory factory = context.getSegmentIteratorFactory();
        JSSegmentIteratorObject segmentIterator = new JSSegmentIteratorObject(factory.getShape(realm), iteratorState);
        factory.initProto(segmentIterator, realm);
        return context.trackAllocation(segmentIterator);
    }

    public static JSSegmentsObject createSegments(JSContext context, JSRealm realm, JSSegmenterObject segmenter, TruffleString string) {
        JSObjectFactory factory = context.getSegmentsFactory();
        JSSegmentsObject segments = new JSSegmentsObject(factory.getShape(realm), segmenter, string);
        factory.initProto(segments, realm);
        return context.trackAllocation(segments);
    }

    @CompilerDirectives.TruffleBoundary
    public static void setLocale(JSContext ctx, InternalState state, String[] locales) {
        Locale selectedLocale = IntlUtil.selectedLocale(ctx, locales);
        Locale strippedLocale = selectedLocale.stripExtensions();
        if (strippedLocale.toLanguageTag().equals("und")) {
            selectedLocale = ctx.getLocale();
            strippedLocale = selectedLocale.stripExtensions();
        }
        state.locale = strippedLocale.toLanguageTag();
        state.javaLocale = strippedLocale;
    }

    @CompilerDirectives.TruffleBoundary
    public static void setupInternalBreakIterator(InternalState state, String granularity) {
        state.javaLocale = Locale.forLanguageTag(state.locale);
        switch (granularity) {
            case "grapheme": {
                state.granularity = Granularity.GRAPHEME;
                break;
            }
            case "word": {
                state.granularity = Granularity.WORD;
                break;
            }
            case "sentence": {
                state.granularity = Granularity.SENTENCE;
                break;
            }
            default: {
                throw Errors.shouldNotReachHere(String.format("Segmenter with granularity, %s, is not supported", granularity));
            }
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static BreakIterator createBreakIterator(JSDynamicObject segmenterObj) {
        InternalState state = JSSegmenter.getInternalState(segmenterObj);
        ULocale ulocale = ULocale.forLocale(state.javaLocale);
        BreakIterator icuIterator = state.granularity.getIterator(ulocale);
        return icuIterator;
    }

    @CompilerDirectives.TruffleBoundary
    public static BreakIterator createBreakIterator(JSDynamicObject segmenterObj, String text) {
        BreakIterator icuIterator = JSSegmenter.createBreakIterator(segmenterObj);
        icuIterator.setText(text);
        return icuIterator;
    }

    public static Granularity getGranularity(JSDynamicObject segmenterObj) {
        InternalState state = JSSegmenter.getInternalState(segmenterObj);
        return state.granularity;
    }

    @CompilerDirectives.TruffleBoundary
    public static JSDynamicObject resolvedOptions(JSContext context, JSRealm realm, JSDynamicObject segmenterObj) {
        InternalState state = JSSegmenter.getInternalState(segmenterObj);
        return state.toResolvedOptionsObject(context, realm);
    }

    public static InternalState getInternalState(JSDynamicObject segmenterObj) {
        assert (JSSegmenter.isJSSegmenter(segmenterObj));
        return ((JSSegmenterObject)segmenterObj).getInternalState();
    }

    @Override
    public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
        return realm.getSegmenterPrototype();
    }

    public static Shape makeInitialSegmentsShape(JSContext ctx, JSDynamicObject prototype) {
        return JSObjectUtil.getProtoChildShape(prototype, JSOrdinary.BARE_INSTANCE, ctx);
    }

    public static boolean isJSSegments(Object obj) {
        return obj instanceof JSSegmentsObject;
    }

    public static JSObject createSegmentsPrototype(JSRealm realm) {
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, SegmentsPrototypeBuiltins.BUILTINS);
        return prototype;
    }

    public static Shape makeInitialSegmentIteratorShape(JSContext ctx, JSDynamicObject prototype) {
        return JSObjectUtil.getProtoChildShape(prototype, JSOrdinary.BARE_INSTANCE, ctx);
    }

    public static boolean isJSSegmentIterator(Object obj) {
        return obj instanceof JSSegmentIteratorObject;
    }

    public static JSObject createSegmentIteratorPrototype(JSRealm realm) {
        JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm, realm.getIteratorPrototype());
        JSObjectUtil.putFunctionsFromContainer(realm, prototype, SegmentIteratorPrototypeBuiltins.BUILTINS);
        JSObjectUtil.putToStringTag(prototype, ITERATOR_CLASS_NAME);
        return prototype;
    }

    public static class InternalState {
        private String locale;
        private Locale javaLocale;
        Granularity granularity = Granularity.GRAPHEME;

        JSDynamicObject toResolvedOptionsObject(JSContext context, JSRealm realm) {
            JSObject result = JSOrdinary.create(context, realm);
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_LOCALE, Strings.fromJavaString(this.locale), JSAttributes.getDefault());
            JSObjectUtil.defineDataProperty(context, result, IntlUtil.KEY_GRANULARITY, Strings.fromJavaString(this.granularity.getName()), JSAttributes.getDefault());
            return result;
        }
    }

    public static enum Granularity implements IcuIteratorHelper
    {
        GRAPHEME("grapheme"){

            @Override
            @CompilerDirectives.TruffleBoundary
            public BreakIterator getIterator(ULocale locale) {
                return BreakIterator.getCharacterInstance(locale);
            }
        }
        ,
        WORD("word"){

            @Override
            @CompilerDirectives.TruffleBoundary
            public BreakIterator getIterator(ULocale locale) {
                return BreakIterator.getWordInstance(locale);
            }
        }
        ,
        SENTENCE("sentence"){

            @Override
            @CompilerDirectives.TruffleBoundary
            public BreakIterator getIterator(ULocale locale) {
                return BreakIterator.getSentenceInstance(locale);
            }
        };

        private String name;

        private Granularity(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    static interface IcuIteratorHelper {
        public BreakIterator getIterator(ULocale var1);
    }

    public static class IteratorState {
        private final TruffleString iteratedString;
        private final BreakIterator breakIterator;
        private final Granularity granularity;

        public IteratorState(TruffleString iteratedObject, BreakIterator breakIterator, Granularity granularity) {
            this.iteratedString = iteratedObject;
            this.breakIterator = breakIterator;
            this.granularity = granularity;
        }

        public TruffleString getIteratedString() {
            return this.iteratedString;
        }

        public Granularity getSegmenterGranularity() {
            return this.granularity;
        }

        public BreakIterator getBreakIterator() {
            return this.breakIterator;
        }
    }
}

