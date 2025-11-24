
package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.debug.DebugContext;
import com.oracle.truffle.api.nodes.LanguageInfo;

public interface DebugContextsListener {
    public void contextCreated(DebugContext var1);

    public void languageContextCreated(DebugContext var1, LanguageInfo var2);

    public void languageContextInitialized(DebugContext var1, LanguageInfo var2);

    public void languageContextFinalized(DebugContext var1, LanguageInfo var2);

    public void languageContextDisposed(DebugContext var1, LanguageInfo var2);

    public void contextClosed(DebugContext var1);
}

