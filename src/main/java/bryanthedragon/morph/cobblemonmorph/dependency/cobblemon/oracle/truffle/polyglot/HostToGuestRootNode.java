package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.RootNode;
import java.util.Objects;

abstract class HostToGuestRootNode extends RootNode {
   protected static final int ARGUMENT_OFFSET = 2;
   @CompilerDirectives.CompilationFinal
   private boolean seenEnter;
   @CompilerDirectives.CompilationFinal
   private boolean seenNonEnter;
   private final PolyglotLanguage language;
   private final PolyglotSharingLayer layer;
   @CompilerDirectives.CompilationFinal
   private boolean seenError;

   HostToGuestRootNode(PolyglotSharingLayer layer) {
      super(null);

      assert layer != null;

      this.layer = layer;
      this.language = null;
      EngineAccessor.NODES.setSharingLayer(this, layer);
   }

   HostToGuestRootNode(PolyglotLanguageInstance language) {
      super(null);
      EngineAccessor.NODES.setSharingLayer(this, language.sharing);
      this.layer = language.sharing;
      this.language = language.language;
   }

   protected abstract Class<?> getReceiverType();

   @Override
   public final Object execute(VirtualFrame frame) {
      Object[] args = frame.getArguments();
      PolyglotLanguageContext languageContext = this.layer.getSingleConstantLanguageContext(this.language);
      if (languageContext == null) {
         languageContext = (PolyglotLanguageContext)args[0];
      }

      PolyglotContextImpl constantContext = this.layer.getSingleConstantContext();
      if (constantContext == null) {
         constantContext = languageContext.context;
      }

      assert languageContext.context == constantContext;

      PolyglotContextImpl context = constantContext;

      assert Objects.equals(this.layer, languageContext.context.layer) : PolyglotSharingLayer.invalidSharingError(
         this, this.layer, languageContext.context.layer
      );

      Object[] prev;
      boolean needsEnter;
      try {
         needsEnter = this.layer.engine.needsEnter(context);
         if (needsEnter) {
            if (!this.seenEnter) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.seenEnter = true;
            }

            prev = this.layer.engine.enterCached(context, true);
         } else {
            if (!this.seenNonEnter) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.seenNonEnter = true;
            }

            prev = null;
         }
      } catch (Throwable var21) {
         throw (RuntimeException)this.handleException(languageContext, var21, false, RuntimeException.class);
      }

      Object var11;
      try {
         Object[] arguments = frame.getArguments();
         Object receiver = this.getReceiverType().cast(arguments[1]);
         Object result = this.executeImpl(languageContext, receiver, arguments);

         assert !(result instanceof TruffleObject);

         var11 = result;
      } catch (Throwable var22) {
         throw (RuntimeException)this.handleException(languageContext, var22, true, RuntimeException.class);
      } finally {
         if (needsEnter) {
            try {
               this.layer.engine.leaveCached(prev, context);
            } catch (Throwable var20) {
               throw (RuntimeException)this.handleException(languageContext, var20, false, RuntimeException.class);
            }
         }
      }

      return var11;
   }

   private <E extends Throwable> E handleException(PolyglotLanguageContext languageContext, Throwable e, boolean entered, Class<E> exceptionType) throws E {
      if (!this.seenError) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.seenError = true;
      }

      throw PolyglotImpl.guestToHostException(languageContext, e, entered);
   }

   protected abstract Object executeImpl(PolyglotLanguageContext languageContext, Object receiver, Object[] args);

   static <T> T installHostCodeCache(PolyglotLanguageContext languageContext, Object key, T value, Class<T> expectedType) {
      T result = expectedType.cast(languageContext.getLanguageInstance().hostToGuestCodeCache.putIfAbsent(key, value));
      return result != null ? result : value;
   }

   static <T> T lookupHostCodeCache(PolyglotLanguageContext languageContext, Object key, Class<T> expectedType) {
      return expectedType.cast(languageContext.getLanguageInstance().hostToGuestCodeCache.get(key));
   }
}
