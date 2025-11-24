
package com.oracle.truffle.js.nodes.intl;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.array.JSGetLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.intl.JSToCanonicalizedLocaleListNodeGen;
import com.oracle.truffle.js.nodes.unary.TypeOfNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.intl.JSLocale;
import com.oracle.truffle.js.runtime.builtins.intl.JSLocaleObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import java.util.ArrayList;

@ImportStatic(value={JSConfig.class})
public abstract class JSToCanonicalizedLocaleListNode
extends JavaScriptBaseNode {
    final JSContext context;
    private final BranchProfile errorBranch = BranchProfile.create();

    protected JSToCanonicalizedLocaleListNode(JSContext context) {
        this.context = context;
    }

    public static JSToCanonicalizedLocaleListNode create(JSContext context) {
        return JSToCanonicalizedLocaleListNodeGen.create(context);
    }

    public abstract String[] executeLanguageTags(Object var1);

    @Specialization
    protected String[] doTString(TruffleString s) {
        return JSToCanonicalizedLocaleListNode.doJavaString(Strings.toJavaString(s));
    }

    @Specialization(guards={"isUndefined(object)"})
    protected String[] doUndefined(Object object) {
        return new String[0];
    }

    @Specialization
    protected String[] doLocale(JSLocaleObject object) {
        return JSToCanonicalizedLocaleListNode.doJavaString(JSLocale.getInternalState(object).getLocale());
    }

    @Specialization(guards={"!isForeignObject(object)", "!isString(object)", "!isUndefined(object)", "!isJSLocale(object)"})
    protected String[] doOtherType(Object object, @Cached(value="createToObject(context)") JSToObjectNode toObjectNode, @Cached(value="create(context)") JSGetLengthNode getLengthNode, @Cached JSHasPropertyNode hasPropertyNode, @Cached TypeOfNode typeOfNode, @Cached JSToStringNode toStringNode, @Cached @Cached.Shared(value="equalsNode") TruffleString.EqualNode equalsNode, @Cached @Cached.Shared(value="toJavaStringNode") TruffleString.ToJavaStringNode toJavaStringNode) {
        ArrayList result = new ArrayList();
        JSDynamicObject localeObj = (JSDynamicObject)toObjectNode.execute(object);
        long len = getLengthNode.executeLong(localeObj);
        for (long k = 0L; k < len; ++k) {
            if (!hasPropertyNode.executeBoolean((Object)localeObj, k)) continue;
            Object kValue = JSObject.get(localeObj, k);
            TruffleString typeOfKValue = typeOfNode.executeString(kValue);
            if (JSRuntime.isNullOrUndefined(kValue) || !Strings.equals(equalsNode, Strings.STRING, typeOfKValue) && !Strings.equals(equalsNode, Strings.OBJECT, typeOfKValue)) {
                this.errorBranch.enter();
                throw Errors.createTypeError(Boundaries.stringFormat("String or Object expected in locales list, got %s", typeOfKValue));
            }
            String lt = JSLocale.isJSLocale(kValue) ? JSLocale.getInternalState((JSDynamicObject)kValue).getLocale() : Strings.toJavaString(toJavaStringNode, toStringNode.executeString(kValue));
            String canonicalizedLt = IntlUtil.validateAndCanonicalizeLanguageTag(lt);
            if (Boundaries.listContains(result, canonicalizedLt)) continue;
            Boundaries.listAdd(result, canonicalizedLt);
        }
        return result.toArray(new String[0]);
    }

    @Specialization(guards={"isForeignObject(object)"})
    protected String[] doForeignType(Object object, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop, @Cached TypeOfNode typeOfNode, @Cached JSToStringNode toStringNode, @Cached @Cached.Shared(value="equalsNode") TruffleString.EqualNode equalsNode, @Cached @Cached.Shared(value="toJavaStringNode") TruffleString.ToJavaStringNode toJavaStringNode) {
        long len;
        ArrayList result = new ArrayList();
        try {
            len = interop.getArraySize(object);
        }
        catch (UnsupportedMessageException e) {
            this.errorBranch.enter();
            throw Errors.createTypeErrorInteropException(object, e, "getArraySize", this);
        }
        for (long k = 0L; k < len; ++k) {
            Object kValue;
            if (!interop.isArrayElementReadable(object, k)) continue;
            try {
                kValue = interop.readArrayElement(object, k);
            }
            catch (InvalidArrayIndexException | UnsupportedMessageException e) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorInteropException(object, e, "readArrayElement", k, this);
            }
            TruffleString typeOfKValue = typeOfNode.executeString(kValue);
            if (!Strings.equals(equalsNode, Strings.STRING, typeOfKValue) && !Strings.equals(equalsNode, Strings.OBJECT, typeOfKValue)) {
                this.errorBranch.enter();
                throw Errors.createTypeError(Boundaries.stringFormat("String or Object expected in locales list, got %s", typeOfKValue));
            }
            String lt = Strings.toJavaString(toJavaStringNode, toStringNode.executeString(kValue));
            String canonicalizedLt = IntlUtil.validateAndCanonicalizeLanguageTag(lt);
            if (Boundaries.listContains(result, canonicalizedLt)) continue;
            Boundaries.listAdd(result, canonicalizedLt);
        }
        return result.toArray(new String[0]);
    }

    private static String[] doJavaString(String s) {
        return new String[]{IntlUtil.validateAndCanonicalizeLanguageTag(s)};
    }
}

