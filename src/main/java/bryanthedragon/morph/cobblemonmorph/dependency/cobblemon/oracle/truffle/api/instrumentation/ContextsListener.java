package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.nodes.LanguageInfo;

public interface ContextsListener {
   void onContextCreated(TruffleContext context);

   default void onLanguageContextCreate(TruffleContext context, LanguageInfo language) {
   }

   void onLanguageContextCreated(TruffleContext context, LanguageInfo language);

   default void onLanguageContextCreateFailed(TruffleContext context, LanguageInfo language) {
   }

   default void onLanguageContextInitialize(TruffleContext context, LanguageInfo language) {
   }

   void onLanguageContextInitialized(TruffleContext context, LanguageInfo language);

   default void onLanguageContextInitializeFailed(TruffleContext context, LanguageInfo language) {
   }

   void onLanguageContextFinalized(TruffleContext context, LanguageInfo language);

   void onLanguageContextDisposed(TruffleContext context, LanguageInfo language);

   void onContextClosed(TruffleContext context);

   default void onContextResetLimits(TruffleContext context) {
   }
}
