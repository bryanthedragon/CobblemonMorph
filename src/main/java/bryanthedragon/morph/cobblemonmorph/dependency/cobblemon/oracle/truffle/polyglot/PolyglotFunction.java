package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.Node;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.function.Function;

final class PolyglotFunction<T, R> implements Function<T, R>, PolyglotWrapper {
   final Object guestObject;
   final PolyglotLanguageContext languageContext;
   final CallTarget apply;

   PolyglotFunction(PolyglotLanguageContext languageContext, Object function, Class<?> returnClass, Type returnType, Class<?> paramClass, Type paramType) {
      this.guestObject = function;
      this.languageContext = languageContext;
      this.apply = PolyglotFunction.Apply.lookup(languageContext, function.getClass(), returnClass, returnType, paramClass, paramType);
   }

   @Override
   public R apply(T t) {
      return (R)this.apply.call(this.languageContext, this.guestObject, t);
   }

   @Override
   public PolyglotLanguageContext getLanguageContext() {
      return this.languageContext;
   }

   @Override
   public Object getGuestObject() {
      return this.guestObject;
   }

   @Override
   public PolyglotContextImpl getContext() {
      return this.languageContext.context;
   }

   @Override
   public String toString() {
      return PolyglotWrapper.toString(this);
   }

   @Override
   public int hashCode() {
      return PolyglotWrapper.hashCode(this.languageContext, this.guestObject);
   }

   @Override
   public boolean equals(Object o) {
      return o instanceof PolyglotFunction ? PolyglotWrapper.equals(this.languageContext, this.guestObject, ((PolyglotFunction)o).guestObject) : false;
   }

   @CompilerDirectives.TruffleBoundary
   public static <T> PolyglotFunction<?, ?> create(
      PolyglotLanguageContext languageContext, Object function, Class<?> returnClass, Type returnType, Class<?> paramClass, Type paramType
   ) {
      return new PolyglotFunction(languageContext, function, returnClass, returnType, paramClass, paramType);
   }

   static final class Apply extends HostToGuestRootNode {
      final Class<?> receiverClass;
      final Class<?> returnClass;
      final Type returnType;
      final Class<?> paramClass;
      final Type paramType;
      @Node.Child
      private PolyglotExecuteNode apply;

      Apply(PolyglotLanguageInstance language, Class<?> receiverType, Class<?> returnClass, Type returnType, Class<?> paramClass, Type paramType) {
         super(language);
         this.receiverClass = Objects.requireNonNull(receiverType);
         this.returnClass = Objects.requireNonNull(returnClass);
         this.returnType = returnType;
         this.paramClass = Objects.requireNonNull(paramClass);
         this.paramType = paramType;
      }

      @Override
      protected Class<? extends TruffleObject> getReceiverType() {
         return (Class<? extends TruffleObject>)this.receiverClass;
      }

      @Override
      public String getName() {
         return "PolyglotFunction<" + this.receiverClass + ", " + this.returnType + ">.apply";
      }

      @Override
      protected Object executeImpl(PolyglotLanguageContext languageContext, Object function, Object[] args) {
         PolyglotExecuteNode localApply = this.apply;
         if (localApply == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.apply = localApply = this.insert(PolyglotExecuteNodeGen.create());
         }

         return localApply.execute(languageContext, function, args[2], this.returnClass, this.returnType, this.paramClass, this.paramType);
      }

      @Override
      public int hashCode() {
         int result = 1;
         result = 31 * result + Objects.hashCode(this.receiverClass);
         result = 31 * result + Objects.hashCode(this.returnClass);
         result = 31 * result + Objects.hashCode(this.returnType);
         result = 31 * result + Objects.hashCode(this.paramClass);
         return 31 * result + Objects.hashCode(this.paramType);
      }

      @Override
      public boolean equals(Object obj) {
         if (!(obj instanceof PolyglotFunction.Apply)) {
            return false;
         } else {
            PolyglotFunction.Apply other = (PolyglotFunction.Apply)obj;
            return this.receiverClass == other.receiverClass
               && this.returnClass == other.returnClass
               && Objects.equals(this.returnType, other.returnType)
               && this.paramClass == other.paramClass
               && Objects.equals(this.paramType, other.paramType);
         }
      }

      private static CallTarget lookup(
         PolyglotLanguageContext languageContext, Class<?> receiverClass, Class<?> returnClass, Type returnType, Class<?> paramClass, Type paramType
      ) {
         PolyglotFunction.Apply apply = new PolyglotFunction.Apply(
            languageContext.getLanguageInstance(), receiverClass, returnClass, returnType, paramClass, paramType
         );
         CallTarget target = lookupHostCodeCache(languageContext, apply, CallTarget.class);
         if (target == null) {
            target = installHostCodeCache(languageContext, apply, apply.getCallTarget(), CallTarget.class);
         }

         return target;
      }
   }
}
