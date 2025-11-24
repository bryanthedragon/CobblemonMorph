
package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.ReportPolymorphism;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.utilities.AssumedValue;
import com.oracle.truffle.js.nodes.CompileRegexNodeGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.RegexCompilerInterface;
import com.oracle.truffle.js.runtime.Strings;

@ImportStatic(value={JSConfig.class})
public abstract class CompileRegexNode
extends JavaScriptBaseNode {
    private final JSContext context;
    @Node.Child
    private InteropLibrary isCompiledRegexNullNode;

    protected CompileRegexNode(JSContext context) {
        this.context = context;
    }

    public static CompileRegexNode create(JSContext context) {
        return CompileRegexNodeGen.create(context);
    }

    public final Object compile(Object pattern) {
        return this.compile(pattern, Strings.EMPTY_STRING);
    }

    public final Object compile(Object pattern, Object flags) {
        return this.executeCompile(pattern, flags);
    }

    protected abstract Object executeCompile(Object var1, Object var2);

    @Specialization(guards={"stringEquals(equalsNode, pattern, cachedPattern)", "stringEquals(equalsNode2, flags, cachedFlags)"}, limit="MaxCompiledRegexCacheLength")
    protected Object getCached(TruffleString pattern, TruffleString flags, @Cached(value="pattern") TruffleString cachedPattern, @Cached(value="flags") TruffleString cachedFlags, @Cached(value="createAssumedValue()") AssumedValue<Object> cachedCompiledRegex, @Cached TruffleString.EqualNode equalsNode, @Cached TruffleString.EqualNode equalsNode2) {
        Object cached = cachedCompiledRegex.get();
        if (cached == null) {
            cached = this.doCompile(cachedPattern, cachedFlags);
            cachedCompiledRegex.set(cached);
        }
        return cached;
    }

    protected static boolean stringEquals(TruffleString.EqualNode node, TruffleString a, TruffleString b) {
        return Strings.equals(node, a, b);
    }

    @Specialization(guards={"!TrimCompiledRegexCache"})
    protected Object doCompileNoTrimCache(TruffleString pattern, TruffleString flags) {
        return this.doCompile(pattern, flags);
    }

    @Specialization(replaces={"getCached"})
    @ReportPolymorphism.Megamorphic
    protected Object doCompile(TruffleString pattern, TruffleString flags) {
        return RegexCompilerInterface.compile(Strings.toJavaString(pattern), Strings.toJavaString(flags), this.context, this.getRealm(), this.getIsCompiledRegexNullNode());
    }

    private InteropLibrary getIsCompiledRegexNullNode() {
        if (this.isCompiledRegexNullNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.isCompiledRegexNullNode = this.insert(InteropLibrary.getFactory().createDispatched(3));
        }
        return this.isCompiledRegexNullNode;
    }

    AssumedValue<Object> createAssumedValue() {
        return new AssumedValue<Object>("compiledRegex", null);
    }
}

