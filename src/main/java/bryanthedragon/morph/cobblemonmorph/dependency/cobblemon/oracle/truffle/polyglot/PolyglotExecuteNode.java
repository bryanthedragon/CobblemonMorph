package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import java.lang.reflect.Array;
import java.lang.reflect.Type;

abstract class PolyglotExecuteNode extends Node {
   private static final Object[] EMPTY = new Object[0];
   @Node.Child
   private PolyglotLanguageContext.ToGuestValuesNode toGuests = PolyglotLanguageContext.ToGuestValuesNode.create();

   public final Object execute(PolyglotLanguageContext languageContext, Object function, Object functionArgsObject) {
      return this.execute(languageContext, function, functionArgsObject, Object.class, Object.class, Object.class, null);
   }

   public final Object execute(
      PolyglotLanguageContext languageContext,
      Object function,
      Object functionArgsObject,
      Class<?> resultClass,
      Type resultType,
      Class<?> paramClass,
      Type paramType
   ) {
      Object[] argsArray;
      if (paramType != null && paramClass.isArray()) {
         if (functionArgsObject == null) {
            argsArray = EMPTY;
         } else if (paramClass.getComponentType().isPrimitive() && !(functionArgsObject instanceof Object[])) {
            argsArray = copyToObjectArray(paramClass.cast(functionArgsObject));
         } else {
            argsArray = (Object[])functionArgsObject;
         }
      } else if (paramType == null && functionArgsObject == null) {
         argsArray = EMPTY;
      } else if (paramType == null && functionArgsObject instanceof Object[]) {
         argsArray = (Object[])functionArgsObject;
      } else {
         argsArray = new Object[]{functionArgsObject};
      }

      Object[] functionArgs = this.toGuests.apply(languageContext, argsArray);
      return this.executeImpl(languageContext, function, functionArgs, resultClass, resultType);
   }

   @CompilerDirectives.TruffleBoundary
   private static Object[] copyToObjectArray(Object functionArgs) {
      assert functionArgs.getClass().isArray();

      int length = Array.getLength(functionArgs);
      Object[] copy = new Object[length];

      for (int i = 0; i < length; i++) {
         copy[i] = Array.get(functionArgs, 0);
      }

      return copy;
   }

   protected abstract Object executeImpl(
      PolyglotLanguageContext languageContext, Object function, Object[] functionArgsObject, Class<?> resultClass, Type resultType
   );

   @Specialization(limit = "5")
   Object doCached(
      PolyglotLanguageContext languageContext,
      Object function,
      Object[] functionArgs,
      Class<?> resultClass,
      Type resultType,
      @CachedLibrary("function") InteropLibrary interop,
      @Cached PolyglotToHostNode toHost,
      @Cached ConditionProfile executableCondition,
      @Cached ConditionProfile instantiableCondition,
      @Cached BranchProfile unsupportedError,
      @Cached BranchProfile arityError,
      @Cached BranchProfile unsupportedArgumentError
   ) {
      boolean executable = executableCondition.profile(interop.isExecutable(function));

      Object result;
      try {
         if (executable) {
            result = interop.execute(function, functionArgs);
         } else {
            if (!instantiableCondition.profile(interop.isInstantiable(function))) {
               throw PolyglotInteropErrors.executeUnsupported(languageContext, function);
            }

            result = interop.instantiate(function, functionArgs);
         }
      } catch (UnsupportedTypeException var16) {
         unsupportedArgumentError.enter();
         if (executable) {
            throw PolyglotInteropErrors.invalidExecuteArgumentType(languageContext, function, functionArgs);
         }

         throw PolyglotInteropErrors.invalidInstantiateArgumentType(languageContext, function, functionArgs);
      } catch (ArityException var17) {
         arityError.enter();
         if (executable) {
            throw PolyglotInteropErrors.invalidExecuteArity(
               languageContext, function, functionArgs, var17.getExpectedMinArity(), var17.getExpectedMaxArity(), var17.getActualArity()
            );
         }

         throw PolyglotInteropErrors.invalidInstantiateArity(
            languageContext, function, functionArgs, var17.getExpectedMinArity(), var17.getExpectedMaxArity(), var17.getActualArity()
         );
      } catch (UnsupportedMessageException var18) {
         unsupportedError.enter();
         throw PolyglotInteropErrors.executeUnsupported(languageContext, function);
      }

      return toHost.execute(languageContext, result, resultClass, resultType);
   }
}
