
package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeAccessor;

public abstract class ExecutableNode
extends Node {
    @CompilerDirectives.CompilationFinal
    private Object polyglotRef;

    protected ExecutableNode(TruffleLanguage<?> language) {
        CompilerAsserts.neverPartOfCompilation();
        if (language != null) {
            assert (!NodeAccessor.HOST.isHostLanguage(language.getClass())) : "do not create create executable nodes with host language";
            this.polyglotRef = language;
        } else {
            this.polyglotRef = NodeAccessor.ENGINE.getCurrentSharingLayer();
        }
        assert (language == null || this.getLanguageInfo() != null) : "Truffle language instance is not initialized.";
    }

    final TruffleLanguage<?> getLanguage() {
        Object ref = this.polyglotRef;
        if (ref instanceof TruffleLanguage) {
            return (TruffleLanguage)ref;
        }
        return null;
    }

    final void applyEngineRef(ExecutableNode node) {
        this.polyglotRef = node.polyglotRef;
    }

    final Object getSharingLayer() {
        Object ref = this.polyglotRef;
        if (ref instanceof TruffleLanguage) {
            return NodeAccessor.ENGINE.getPolyglotSharingLayer(NodeAccessor.LANGUAGE.getPolyglotLanguageInstance((TruffleLanguage)ref));
        }
        return ref;
    }

    final void setSharingLayer(Object engine) {
        assert (!(this.polyglotRef instanceof TruffleLanguage)) : "not allowed overwrite language";
        this.polyglotRef = engine;
    }

    public abstract Object execute(VirtualFrame var1);

    public final LanguageInfo getLanguageInfo() {
        TruffleLanguage<?> language = this.getLanguage();
        if (language != null) {
            return NodeAccessor.LANGUAGE.getLanguageInfo(language);
        }
        return null;
    }

    public final <C extends TruffleLanguage> C getLanguage(Class<C> languageClass) {
        TruffleLanguage<?> language = this.getLanguage();
        if (language == null) {
            return null;
        }
        if (language.getClass() != languageClass) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new ClassCastException(String.format("Illegal language class specified. Expected '%s'.", language.getClass().getName()));
        }
        return (C)language;
    }
}

