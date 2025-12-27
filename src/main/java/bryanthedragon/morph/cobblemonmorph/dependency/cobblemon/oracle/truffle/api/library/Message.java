package com.oracle.truffle.api.library;

import com.oracle.truffle.api.CompilerDirectives;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Message {
   private final String simpleName;
   private final String qualifiedName;
   private final int id;
   private final int hash;
   private final Class<?> returnType;
   private final Class<? extends Library> libraryClass;
   private final List<Class<?>> parameterTypes;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final Class<?>[] parameterTypesArray;
   private final int parameterCount;
   @CompilerDirectives.CompilationFinal
   LibraryFactory<Library> library;

   protected Message(Class<? extends Library> libraryClass, String messageName, int id, Class<?> returnType, Class<?>... parameterTypes) {
      this(libraryClass, id, messageName, returnType, parameterTypes);
      if (id < 0) {
         throw new IllegalArgumentException("Id must be non-negative.");
      }
   }

   private Message(Class<? extends Library> libraryClass, int id, String messageName, Class<?> returnType, Class<?>... parameterTypes) {
      Objects.requireNonNull(libraryClass);
      Objects.requireNonNull(messageName);
      Objects.requireNonNull(returnType);
      this.libraryClass = libraryClass;
      this.simpleName = messageName.intern();
      this.returnType = returnType;
      this.parameterTypesArray = parameterTypes;
      this.parameterTypes = Collections.unmodifiableList(Arrays.asList(parameterTypes));
      this.qualifiedName = (this.getLibraryName() + "." + this.simpleName).intern();
      this.id = id;
      this.parameterCount = parameterTypes.length;
      this.hash = this.qualifiedName.hashCode();
   }

   public final int getId() {
      return this.id;
   }

   public final String getQualifiedName() {
      return this.qualifiedName;
   }

   public final String getSimpleName() {
      return this.simpleName;
   }

   public final String getLibraryName() {
      return this.getLibraryClass().getName();
   }

   public final Class<?> getReturnType() {
      return this.returnType;
   }

   public final Class<?> getReceiverType() {
      return this.parameterTypesArray[0];
   }

   public final List<Class<?>> getParameterTypes() {
      return this.parameterTypes;
   }

   public final Class<?> getParameterType(int index) {
      return this.parameterTypesArray[index];
   }

   public final int getParameterCount() {
      return this.parameterCount;
   }

   public final Class<? extends Library> getLibraryClass() {
      return this.libraryClass;
   }

   public final LibraryFactory<?> getFactory() {
      return this.library;
   }

   @Override
   public final boolean equals(Object obj) {
      return this == obj;
   }

   @Override
   public final int hashCode() {
      return this.hash;
   }

   @Override
   protected final Object clone() throws CloneNotSupportedException {
      throw new CloneNotSupportedException();
   }

   @Override
   public final String toString() {
      StringBuilder b = new StringBuilder();
      b.append("Message[");
      b.append(this.getReturnType().getSimpleName());
      b.append(" ").append(this.getQualifiedName());
      b.append("(");
      String sep = "";

      for (Class<?> param : this.getParameterTypes()) {
         b.append(sep);
         b.append(param.getSimpleName());
         sep = ", ";
      }

      b.append(")");
      return b.toString();
   }

   @CompilerDirectives.TruffleBoundary
   public static Message resolve(Class<? extends Library> libraryClass, String messageName) {
      return LibraryFactory.resolveMessage(libraryClass, messageName, true);
   }

   @CompilerDirectives.TruffleBoundary
   public static Message resolve(Class<? extends Library> libraryClass, String messageName, boolean fail) {
      return LibraryFactory.resolveMessage(libraryClass, messageName, fail);
   }
}
