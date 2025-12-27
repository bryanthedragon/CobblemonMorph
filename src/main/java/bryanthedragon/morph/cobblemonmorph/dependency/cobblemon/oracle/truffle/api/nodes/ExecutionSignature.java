package com.oracle.truffle.api.nodes;

public final class ExecutionSignature {
   public static final ExecutionSignature GENERIC = create(null, null);
   private final Class<?>[] argumentTypes;
   private final Class<?> returnType;

   ExecutionSignature(Class<?> returnType, Class<?>[] argumentTypes) {
      this.argumentTypes = argumentTypes;
      this.returnType = returnType;
   }

   public Class<?>[] getArgumentTypes() {
      return this.argumentTypes;
   }

   public Class<?> getReturnType() {
      return this.returnType;
   }

   public static ExecutionSignature create(Class<?> returnType, Class<?>[] argumentTypes) {
      return new ExecutionSignature(returnType, argumentTypes);
   }
}
