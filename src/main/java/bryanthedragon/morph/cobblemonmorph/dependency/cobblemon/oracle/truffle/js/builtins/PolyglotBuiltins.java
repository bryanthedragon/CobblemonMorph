
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.PolyglotBuiltinsFactory;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.Pair;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.NoSuchFileException;

public final class PolyglotBuiltins
extends JSBuiltinsContainer.SwitchEnum<Polyglot> {
    public static final JSBuiltinsContainer BUILTINS = new PolyglotBuiltins();
    public static final JSBuiltinsContainer INTERNAL_BUILTINS = new PolyglotInternalBuiltins();

    protected PolyglotBuiltins() {
        super(JSRealm.POLYGLOT_CLASS_NAME, Polyglot.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, Polyglot builtinEnum) {
        switch (builtinEnum) {
            case export: {
                return PolyglotBuiltinsFactory.PolyglotExportNodeGen.create(context, builtin, PolyglotBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
            case import_: {
                return PolyglotBuiltinsFactory.PolyglotImportNodeGen.create(context, builtin, PolyglotBuiltins.args().fixedArgs(1).createArgumentNodes(context));
            }
            case eval: {
                return PolyglotBuiltinsFactory.PolyglotEvalNodeGen.create(context, builtin, PolyglotBuiltins.args().fixedArgs(2).createArgumentNodes(context));
            }
        }
        return null;
    }

    static abstract class PolyglotToPolyglotValueNode
    extends JSBuiltinNode {
        @Node.Child
        private ExportValueNode exportValueNode = ExportValueNode.create();

        PolyglotToPolyglotValueNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object execute(Object value2) {
            return this.exportValueNode.execute(value2);
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotToJSValueNode
    extends JSBuiltinNode {
        PolyglotToJSValueNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected final Object toJSValue(TruffleObject obj, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            return JSInteropUtil.toPrimitiveOrDefault(obj, obj, interop, this);
        }

        @Specialization(guards={"!isTruffleObject(obj)"})
        protected static Object toJSValue(Object obj) {
            return obj;
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotIsInstantiableNode
    extends JSBuiltinNode {
        PolyglotIsInstantiableNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected static boolean isInstantiable(TruffleObject obj, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            return interop.isInstantiable(obj);
        }

        @Specialization(guards={"!isTruffleObject(obj)"})
        protected static boolean unsupported(Object obj) {
            return false;
        }
    }

    static abstract class PolyglotKeysNode
    extends JSBuiltinNode {
        PolyglotKeysNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @CompilerDirectives.TruffleBoundary
        @Specialization
        protected Object keys(TruffleObject obj) {
            return JSArray.createConstantObjectArray(this.getContext(), this.getRealm(), JSInteropUtil.keys(obj).toArray());
        }

        @Specialization(guards={"!isTruffleObject(obj)"})
        protected boolean unsupported(Object obj) {
            throw Errors.createTypeErrorNotATruffleObject("keys");
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotHasKeysNode
    extends JSBuiltinNode {
        PolyglotHasKeysNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean hasKeys(TruffleObject obj, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            return interop.hasMembers(obj);
        }

        @Specialization(guards={"!isTruffleObject(obj)"})
        protected boolean unsupported(Object obj) {
            return false;
        }
    }

    static abstract class PolyglotEvalFileNode
    extends PolyglotEvalBaseNode {
        PolyglotEvalFileNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"language.equals(cachedLanguage)"}, limit="1")
        @CompilerDirectives.TruffleBoundary
        protected Object evalFileCachedLanguage(TruffleString language, TruffleString file, @Cached(value="language") TruffleString cachedLanguage, @Cached @Cached.Shared(value="toJavaStringNode") TruffleString.ToJavaStringNode toJavaStringNode, @Cached(value="getLanguageIdAndMimeType(toJavaStringNode, language)") Pair<String, String> languagePair, @Cached @Cached.Shared(value="callNode") IndirectCallNode callNode) {
            return callNode.call(this.evalFileIntl(file, languagePair.getFirst(), languagePair.getSecond()), new Object[0]);
        }

        @Specialization(replaces={"evalFileCachedLanguage"})
        @CompilerDirectives.TruffleBoundary
        protected Object evalFileString(TruffleString language, TruffleString file, @Cached @Cached.Shared(value="toJavaStringNode") TruffleString.ToJavaStringNode toJavaStringNode, @Cached @Cached.Shared(value="callNode") IndirectCallNode callNode) {
            Pair<String, String> pair = this.getLanguageIdAndMimeType(toJavaStringNode, language);
            return callNode.call(this.evalFileIntl(file, pair.getFirst(), pair.getSecond()), new Object[0]);
        }

        private CallTarget evalFileIntl(TruffleString fileName, String languageId, String mimeType) {
            Source source;
            CompilerAsserts.neverPartOfCompilation();
            TruffleLanguage.Env env = this.getRealm().getEnv();
            try {
                source = Source.newBuilder(languageId, env.getPublicTruffleFile(Strings.toJavaString(fileName))).mimeType(mimeType).build();
            }
            catch (AccessDeniedException e) {
                throw Errors.createError("Cannot evaluate file " + fileName + ": permission denied");
            }
            catch (NoSuchFileException e) {
                throw Errors.createError("Cannot evaluate file " + fileName + ": no such file");
            }
            catch (IOException | SecurityException e) {
                throw Errors.createError("Cannot evaluate file: " + e.getMessage());
            }
            try {
                return env.parsePublic(source, new String[0]);
            }
            catch (IllegalStateException ex) {
                throw Errors.createErrorFromException(ex);
            }
        }

        @Specialization(guards={"!isString(languageId) || !isString(fileName)"})
        protected Object eval(Object languageId, Object fileName) {
            throw Errors.createTypeError("Expected arguments: (String languageId, String fileName)");
        }
    }

    static abstract class PolyglotEvalNode
    extends PolyglotEvalBaseNode {
        PolyglotEvalNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"language.equals(cachedLanguage)"}, limit="1")
        @CompilerDirectives.TruffleBoundary
        protected Object evalCachedLanguage(TruffleString language, TruffleString source, @Cached(value="language") TruffleString cachedLanguage, @Cached @Cached.Shared(value="toJavaStringNode") TruffleString.ToJavaStringNode toJavaStringNode, @Cached(value="getLanguageIdAndMimeType(toJavaStringNode, language)") Pair<String, String> languagePair, @Cached @Cached.Shared(value="callNode") IndirectCallNode callNode) {
            return callNode.call(this.evalStringIntl(source, languagePair.getFirst(), languagePair.getSecond()), new Object[0]);
        }

        @Specialization(replaces={"evalCachedLanguage"})
        @CompilerDirectives.TruffleBoundary
        protected Object evalString(TruffleString language, TruffleString source, @Cached @Cached.Shared(value="toJavaStringNode") TruffleString.ToJavaStringNode toJavaStringNode, @Cached @Cached.Shared(value="callNode") IndirectCallNode callNode) {
            Pair<String, String> pair = this.getLanguageIdAndMimeType(toJavaStringNode, language);
            return callNode.call(this.evalStringIntl(source, pair.getFirst(), pair.getSecond()), new Object[0]);
        }

        private CallTarget evalStringIntl(TruffleString sourceText, String languageId, String mimeType) {
            CompilerAsserts.neverPartOfCompilation();
            this.getContext().checkEvalAllowed();
            Source source = Source.newBuilder(languageId, Strings.toJavaString(sourceText), "<eval>").mimeType(mimeType).build();
            TruffleLanguage.Env env = this.getRealm().getEnv();
            try {
                return env.parsePublic(source, new String[0]);
            }
            catch (IllegalStateException ex) {
                throw Errors.createErrorFromException(ex);
            }
        }

        @Specialization(guards={"!isString(languageId) || !isString(source)"})
        protected Object eval(Object languageId, Object source) {
            throw Errors.createTypeError("Expected arguments: (String languageId, String sourceCode)");
        }
    }

    static abstract class PolyglotEvalBaseNode
    extends JSBuiltinNode {
        protected final ConditionProfile isValid = ConditionProfile.createBinaryProfile();

        PolyglotEvalBaseNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        protected Pair<String, String> getLanguageIdAndMimeType(TruffleString.ToJavaStringNode toJavaStringNode, TruffleString languageIdOrMimeTypeTS) {
            String language;
            String languageIdOrMimeType;
            String languageId = languageIdOrMimeType = Strings.toJavaString(toJavaStringNode, languageIdOrMimeTypeTS);
            String mimeType = null;
            if (languageIdOrMimeType.indexOf(47) >= 0 && (language = Source.findLanguage(languageIdOrMimeType)) != null) {
                languageId = language;
                mimeType = languageIdOrMimeType;
            }
            return new Pair<String, Object>(languageId, mimeType);
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotGetSizeNode
    extends JSBuiltinNode {
        PolyglotGetSizeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object getSize(TruffleObject obj, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            try {
                return interop.getArraySize(obj);
            }
            catch (UnsupportedMessageException e) {
                return Null.instance;
            }
        }

        @Specialization(guards={"!isTruffleObject(obj)"})
        protected boolean unsupported(Object obj) {
            throw Errors.createTypeErrorNotATruffleObject("getSize");
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotConstructNode
    extends JSBuiltinNode {
        PolyglotConstructNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object doNew(TruffleObject obj, Object[] arguments, @Cached ExportValueNode exportValue, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            Object target = exportValue.execute(obj);
            Object[] convertedArgs = new Object[arguments.length];
            for (int i = 0; i < arguments.length; ++i) {
                convertedArgs[i] = exportValue.execute(arguments[i]);
            }
            try {
                return interop.instantiate(target, convertedArgs);
            }
            catch (ArityException | UnsupportedMessageException | UnsupportedTypeException e) {
                throw Errors.createTypeErrorInteropException(obj, e, "instantiate", this);
            }
        }

        @Specialization(guards={"!isTruffleObject(obj)"})
        protected boolean unsupported(Object obj, Object[] arguments) {
            throw Errors.createTypeErrorNotATruffleObject("construct");
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotExecuteNode
    extends JSBuiltinNode {
        PolyglotExecuteNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object execute(TruffleObject obj, Object[] arguments, @Cached ExportValueNode exportValue, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            Object target = exportValue.execute(obj);
            Object[] convertedArgs = new Object[arguments.length];
            for (int i = 0; i < arguments.length; ++i) {
                convertedArgs[i] = exportValue.execute(arguments[i]);
            }
            try {
                return interop.execute(target, convertedArgs);
            }
            catch (ArityException | UnsupportedMessageException | UnsupportedTypeException e) {
                throw Errors.createTypeErrorInteropException(obj, e, "execute", this);
            }
        }

        @Specialization(guards={"!isTruffleObject(obj)"})
        protected boolean unsupported(Object obj, Object[] arguments) {
            throw Errors.createTypeErrorNotATruffleObject("execute");
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotUnboxValueNode
    extends JSBuiltinNode {
        PolyglotUnboxValueNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object truffleObject(TruffleObject obj, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            Object unboxed = JSInteropUtil.toPrimitiveOrDefault(obj, obj, interop, this);
            if (unboxed == obj) {
                throw Errors.createTypeErrorNotATruffleObject("unbox");
            }
            return unboxed;
        }

        @Specialization(guards={"isJavaPrimitive(obj)"})
        protected Object primitive(Object obj) {
            return obj;
        }

        @Specialization(guards={"!isTruffleObject(obj)", "!isJavaPrimitive(obj)"})
        protected boolean unsupported(Object obj) {
            throw Errors.createTypeErrorNotATruffleObject("unbox");
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotRemoveNode
    extends JSBuiltinNode {
        PolyglotRemoveNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean member(TruffleObject obj, TruffleString name, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop, @Cached TruffleString.ToJavaStringNode toJavaStringNode) {
            try {
                interop.removeMember(obj, Strings.toJavaString(toJavaStringNode, name));
                return true;
            }
            catch (UnknownIdentifierException e) {
                return false;
            }
            catch (UnsupportedMessageException e) {
                throw Errors.createTypeErrorInteropException(obj, e, "removeMember", name, this);
            }
        }

        @Specialization
        protected boolean arrayElementInt(TruffleObject obj, int index, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            try {
                interop.removeArrayElement(obj, index);
                return true;
            }
            catch (InvalidArrayIndexException e) {
                return false;
            }
            catch (UnsupportedMessageException e) {
                throw Errors.createTypeErrorInteropException(obj, e, "removeArrayElement", index, this);
            }
        }

        @Specialization(guards={"isNumber(index)"}, replaces={"arrayElementInt"})
        protected boolean arrayElement(TruffleObject obj, Number index, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            try {
                interop.removeArrayElement(obj, JSRuntime.longValue(index));
                return true;
            }
            catch (InvalidArrayIndexException e) {
                return false;
            }
            catch (UnsupportedMessageException e) {
                throw Errors.createTypeErrorInteropException(obj, e, "removeArrayElement", index, this);
            }
        }

        @Specialization(guards={"!isString(key)", "!isNumber(key)"})
        protected Object unsupportedKey(TruffleObject obj, Object key, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary keyInterop, @Cached TruffleString.ToJavaStringNode toJavaStringNode) {
            try {
                if (keyInterop.isString(key)) {
                    return this.member(obj, keyInterop.asTruffleString(key), interop, toJavaStringNode);
                }
                if (keyInterop.fitsInInt(key)) {
                    return this.arrayElementInt(obj, keyInterop.asInt(key), interop);
                }
            }
            catch (UnsupportedMessageException e) {
                throw Errors.createTypeErrorUnboxException(obj, e, this);
            }
            return Null.instance;
        }

        @Specialization(guards={"!isTruffleObject(obj)"})
        protected boolean unsupported(Object obj, Object key) {
            throw Errors.createTypeErrorNotATruffleObject("remove");
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotWriteNode
    extends JSBuiltinNode {
        PolyglotWriteNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object member(TruffleObject obj, TruffleString name, Object value2, @Cached.Shared(value="exportValue") @Cached ExportValueNode exportValue, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop, @Cached TruffleString.ToJavaStringNode toJavaStringNode) {
            Object convertedValue = exportValue.execute(value2);
            try {
                interop.writeMember(obj, Strings.toJavaString(toJavaStringNode, name), convertedValue);
                return convertedValue;
            }
            catch (UnknownIdentifierException e) {
                return Null.instance;
            }
            catch (UnsupportedMessageException | UnsupportedTypeException e) {
                throw Errors.createTypeErrorInteropException(obj, e, "writeMember", name, this);
            }
        }

        @Specialization
        protected Object arrayElementInt(TruffleObject obj, int index, Object value2, @Cached.Shared(value="exportValue") @Cached ExportValueNode exportValue, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            Object convertedValue = exportValue.execute(value2);
            try {
                interop.writeArrayElement(obj, index, convertedValue);
                return convertedValue;
            }
            catch (InvalidArrayIndexException e) {
                return Null.instance;
            }
            catch (UnsupportedMessageException | UnsupportedTypeException e) {
                throw Errors.createTypeErrorInteropException(obj, e, "writeArrayElement", index, this);
            }
        }

        @Specialization(guards={"isNumber(index)"}, replaces={"arrayElementInt"})
        protected Object arrayElement(TruffleObject obj, Number index, Object value2, @Cached.Shared(value="exportValue") @Cached ExportValueNode exportValue, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            Object convertedValue = exportValue.execute(value2);
            try {
                interop.writeArrayElement(obj, JSRuntime.longValue(index), convertedValue);
                return convertedValue;
            }
            catch (InvalidArrayIndexException e) {
                return Null.instance;
            }
            catch (UnsupportedMessageException | UnsupportedTypeException e) {
                throw Errors.createTypeErrorInteropException(obj, e, "writeArrayElement", index, this);
            }
        }

        @Specialization(guards={"!isString(key)", "!isNumber(key)"})
        protected Object unsupportedKey(TruffleObject obj, Object key, Object value2, @Cached.Shared(value="exportValue") @Cached ExportValueNode exportValue, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary keyInterop, @Cached TruffleString.ToJavaStringNode toJavaStringNode) {
            try {
                if (keyInterop.isString(key)) {
                    return this.member(obj, keyInterop.asTruffleString(key), value2, exportValue, interop, toJavaStringNode);
                }
                if (keyInterop.fitsInInt(key)) {
                    return this.arrayElement(obj, keyInterop.asInt(key), value2, exportValue, interop);
                }
            }
            catch (UnsupportedMessageException e) {
                throw Errors.createTypeErrorUnboxException(obj, e, this);
            }
            return Null.instance;
        }

        @Specialization(guards={"!isTruffleObject(obj)"})
        protected boolean unsupported(Object obj, Object name, Object value2) {
            throw Errors.createTypeErrorNotATruffleObject("write");
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotReadNode
    extends JSBuiltinNode {
        PolyglotReadNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object member(TruffleObject obj, TruffleString name, @Cached.Shared(value="importValue") @Cached(value="create()") ImportValueNode foreignConvert, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            return JSInteropUtil.readMemberOrDefault(obj, name, Null.instance, interop, foreignConvert, this);
        }

        @Specialization
        protected Object arrayElementInt(TruffleObject obj, int index, @Cached.Shared(value="importValue") @Cached(value="create()") ImportValueNode foreignConvert, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            return JSInteropUtil.readArrayElementOrDefault(obj, index, Null.instance, interop, foreignConvert, this);
        }

        @Specialization(guards={"isNumber(index)"}, replaces={"arrayElementInt"})
        protected Object arrayElement(TruffleObject obj, Number index, @Cached.Shared(value="importValue") @Cached(value="create()") ImportValueNode foreignConvert, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            return JSInteropUtil.readArrayElementOrDefault(obj, JSRuntime.longValue(index), Null.instance, interop, foreignConvert, this);
        }

        @Specialization(guards={"!isString(key)", "!isNumber(key)"})
        protected Object unsupportedKey(TruffleObject obj, Object key, @Cached.Shared(value="importValue") @Cached(value="create()") ImportValueNode foreignConvert, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary keyInterop) {
            try {
                if (keyInterop.isString(key)) {
                    return this.member(obj, keyInterop.asTruffleString(key), foreignConvert, interop);
                }
                if (keyInterop.fitsInInt(key)) {
                    return this.arrayElement(obj, keyInterop.asInt(key), foreignConvert, interop);
                }
            }
            catch (UnsupportedMessageException e) {
                throw Errors.createTypeErrorUnboxException(obj, e, this);
            }
            return Null.instance;
        }

        @Specialization(guards={"!isTruffleObject(obj)"})
        protected boolean unsupported(Object obj, Object name) {
            throw Errors.createTypeErrorNotATruffleObject("read");
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotHasSizeNode
    extends JSBuiltinNode {
        PolyglotHasSizeNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected boolean truffleObject(TruffleObject obj, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            return interop.hasArrayElements(obj);
        }

        @Specialization(guards={"isJavaPrimitive(obj)"})
        protected boolean primitive(Object obj) {
            return false;
        }

        @Specialization(guards={"!isTruffleObject(obj)", "!isJavaPrimitive(obj)"})
        protected boolean unsupported(Object obj) {
            return false;
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotIsNullNode
    extends JSBuiltinNode {
        PolyglotIsNullNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected static boolean truffleObject(TruffleObject obj, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            return interop.isNull(obj);
        }

        @Specialization(guards={"isJavaPrimitive(obj)"})
        protected static boolean primitive(Object obj) {
            return false;
        }

        @Specialization(guards={"!isTruffleObject(obj)", "!isJavaPrimitive(obj)"})
        protected static boolean unsupported(Object obj) {
            return false;
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotIsBoxedPrimitiveNode
    extends JSBuiltinNode {
        PolyglotIsBoxedPrimitiveNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(limit="InteropLibraryLimit")
        protected static boolean truffleObject(TruffleObject obj, @CachedLibrary(value="obj") InteropLibrary interop) {
            return JSInteropUtil.isBoxedPrimitive(obj, interop);
        }

        @Specialization(guards={"isJavaPrimitive(obj)"})
        protected static boolean primitive(Object obj) {
            return false;
        }

        @Specialization(guards={"!isTruffleObject(obj)", "!isJavaPrimitive(obj)"})
        protected static boolean unsupported(Object obj) {
            return false;
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotIsExecutableNode
    extends JSBuiltinNode {
        PolyglotIsExecutableNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected static boolean truffleObject(TruffleObject obj, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            return interop.isExecutable(obj);
        }

        @Specialization(guards={"isJavaPrimitive(obj)"})
        protected static boolean primitive(Object obj) {
            return false;
        }

        @Specialization(guards={"!isTruffleObject(obj)", "!isJavaPrimitive(obj)"})
        protected static boolean unsupported(Object obj) {
            return false;
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotImportNode
    extends JSBuiltinNode {
        PolyglotImportNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object doString(TruffleString identifier, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop, @Cached.Shared(value="importValue") @Cached ImportValueNode importValueNode) {
            Object polyglotBindings;
            try {
                polyglotBindings = this.getRealm().getEnv().getPolyglotBindings();
            }
            catch (SecurityException e) {
                throw Errors.createErrorFromException(e);
            }
            try {
                return importValueNode.executeWithTarget(interop.readMember(polyglotBindings, Strings.toJavaString(identifier)));
            }
            catch (UnknownIdentifierException e) {
                return Undefined.instance;
            }
            catch (UnsupportedMessageException e) {
                throw Errors.createTypeErrorInteropException(polyglotBindings, e, "readMember", identifier, this);
            }
        }

        @Specialization(guards={"!isString(identifier)"})
        protected Object doMaybeUnbox(TruffleObject identifier, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop, @Cached.Shared(value="importValue") @Cached ImportValueNode importValueNode) {
            if (interop.isString(identifier)) {
                TruffleString unboxed;
                try {
                    unboxed = interop.asTruffleString(identifier);
                }
                catch (UnsupportedMessageException e) {
                    throw Errors.createTypeErrorUnboxException(identifier, e, this);
                }
                return this.doString(unboxed, interop, importValueNode);
            }
            return this.doInvalid(identifier);
        }

        @Specialization(guards={"!isString(identifier)", "!isTruffleObject(identifier)"})
        @CompilerDirectives.TruffleBoundary
        protected Object doInvalid(Object identifier) {
            throw Errors.createTypeErrorInvalidIdentifier(identifier);
        }
    }

    @ImportStatic(value={JSConfig.class})
    static abstract class PolyglotExportNode
    extends JSBuiltinNode {
        @Node.Child
        private ExportValueNode exportValue = ExportValueNode.create();

        PolyglotExportNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization
        protected Object doString(TruffleString identifier, Object value2, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            Object polyglotBindings;
            try {
                polyglotBindings = this.getRealm().getEnv().getPolyglotBindings();
            }
            catch (SecurityException e) {
                throw Errors.createErrorFromException(e);
            }
            JSInteropUtil.writeMember(polyglotBindings, identifier, value2, interop, this.exportValue, this);
            return value2;
        }

        @Specialization(guards={"!isString(identifier)"})
        protected Object doMaybeUnbox(TruffleObject identifier, Object value2, @Cached.Shared(value="interop") @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
            if (interop.isString(identifier)) {
                TruffleString unboxed;
                try {
                    unboxed = interop.asTruffleString(identifier);
                }
                catch (UnsupportedMessageException e) {
                    throw Errors.createTypeErrorUnboxException(identifier, e, this);
                }
                return this.doString(unboxed, value2, interop);
            }
            return this.doInvalid(identifier, value2);
        }

        @Specialization(guards={"!isString(identifier)"})
        @CompilerDirectives.TruffleBoundary
        protected Object doInvalid(Object identifier, Object value2) {
            throw Errors.createTypeErrorInvalidIdentifier(identifier);
        }
    }

    public static final class PolyglotInternalBuiltins
    extends JSBuiltinsContainer.SwitchEnum<PolyglotInternal> {
        protected PolyglotInternalBuiltins() {
            super(JSRealm.POLYGLOT_CLASS_NAME, PolyglotInternal.class);
        }

        @Override
        protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, PolyglotInternal builtinEnum) {
            switch (builtinEnum) {
                case isExecutable: {
                    return PolyglotBuiltinsFactory.PolyglotIsExecutableNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case isBoxed: {
                    return PolyglotBuiltinsFactory.PolyglotIsBoxedPrimitiveNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case isNull: {
                    return PolyglotBuiltinsFactory.PolyglotIsNullNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case isInstantiable: {
                    return PolyglotBuiltinsFactory.PolyglotIsInstantiableNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case hasSize: {
                    return PolyglotBuiltinsFactory.PolyglotHasSizeNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case read: {
                    return PolyglotBuiltinsFactory.PolyglotReadNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(2).createArgumentNodes(context));
                }
                case write: {
                    return PolyglotBuiltinsFactory.PolyglotWriteNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(3).createArgumentNodes(context));
                }
                case remove: {
                    return PolyglotBuiltinsFactory.PolyglotRemoveNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(2).createArgumentNodes(context));
                }
                case unbox: {
                    return PolyglotBuiltinsFactory.PolyglotUnboxValueNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case construct: {
                    return PolyglotBuiltinsFactory.PolyglotConstructNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(1).varArgs().createArgumentNodes(context));
                }
                case execute: {
                    return PolyglotBuiltinsFactory.PolyglotExecuteNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(1).varArgs().createArgumentNodes(context));
                }
                case getSize: {
                    return PolyglotBuiltinsFactory.PolyglotGetSizeNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case keys: {
                    return PolyglotBuiltinsFactory.PolyglotKeysNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case toJSValue: {
                    return PolyglotBuiltinsFactory.PolyglotToJSValueNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case toPolyglotValue: {
                    return PolyglotBuiltinsFactory.PolyglotToPolyglotValueNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case hasKeys: {
                    return PolyglotBuiltinsFactory.PolyglotHasKeysNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(1).createArgumentNodes(context));
                }
                case evalFile: {
                    return PolyglotBuiltinsFactory.PolyglotEvalFileNodeGen.create(context, builtin, PolyglotInternalBuiltins.args().fixedArgs(2).createArgumentNodes(context));
                }
            }
            return null;
        }

        public static enum PolyglotInternal implements BuiltinEnum<PolyglotInternal>
        {
            isExecutable(1),
            isBoxed(1),
            isNull(1),
            hasSize(1),
            read(2),
            write(3),
            unbox(1),
            construct(1),
            execute(1),
            getSize(1),
            remove(2),
            toJSValue(1),
            toPolyglotValue(1),
            keys(1),
            hasKeys(1),
            isInstantiable(1),
            evalFile(2);

            private final int length;

            private PolyglotInternal(int length) {
                this.length = length;
            }

            @Override
            public int getLength() {
                return this.length;
            }
        }
    }

    public static enum Polyglot implements BuiltinEnum<Polyglot>
    {
        export(2),
        import_(1),
        eval(2);

        private final int length;

        private Polyglot(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }
    }
}

