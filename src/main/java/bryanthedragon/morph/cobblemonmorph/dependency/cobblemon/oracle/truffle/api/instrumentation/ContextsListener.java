
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.nodes.LanguageInfo;

public interface ContextsListener {
    public void onContextCreated(TruffleContext var1);

    default public void onLanguageContextCreate(TruffleContext context, LanguageInfo language) {
    }

    public void onLanguageContextCreated(TruffleContext var1, LanguageInfo var2);

    default public void onLanguageContextCreateFailed(TruffleContext context, LanguageInfo language) {
    }

    default public void onLanguageContextInitialize(TruffleContext context, LanguageInfo language) {
    }

    public void onLanguageContextInitialized(TruffleContext var1, LanguageInfo var2);

    default public void onLanguageContextInitializeFailed(TruffleContext context, LanguageInfo language) {
    }

    public void onLanguageContextFinalized(TruffleContext var1, LanguageInfo var2);

    public void onLanguageContextDisposed(TruffleContext var1, LanguageInfo var2);

    public void onContextClosed(TruffleContext var1);

    default public void onContextResetLimits(TruffleContext context) {
    }
}

