package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.nodes.LanguageInfo;

public interface DebugContextsListener {
   void contextCreated(DebugContext context);

   void languageContextCreated(DebugContext context, LanguageInfo language);

   void languageContextInitialized(DebugContext context, LanguageInfo language);

   void languageContextFinalized(DebugContext context, LanguageInfo language);

   void languageContextDisposed(DebugContext context, LanguageInfo language);

   void contextClosed(DebugContext context);
}
