package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.SourceSection;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.AbstractList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class DebugValue {
   static final InteropLibrary INTEROP = InteropLibrary.getFactory().getUncached();
   final LanguageInfo preferredLanguage;

   abstract Object get() throws DebugException;

   DebugValue(LanguageInfo preferredLanguage) {
      this.preferredLanguage = preferredLanguage;
   }

   public abstract void set(DebugValue value) throws DebugException;

   @Deprecated(since = "21.2")
   public abstract void set(Object primitiveValue) throws DebugException;

   @Deprecated(since = "20.1")
   public abstract <T> T as(Class<T> clazz) throws DebugException;

   public abstract String getName();

   public abstract boolean isReadable();

   public abstract boolean hasReadSideEffects();

   public abstract boolean hasWriteSideEffects();

   public abstract boolean isWritable();

   public abstract boolean isInternal();

   public DebugScope getScope() {
      return null;
   }

   public final boolean isNull() {
      if (!this.isReadable()) {
         return false;
      } else {
         try {
            Object value = this.get();
            return INTEROP.isNull(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage());
         }
      }
   }

   public boolean isString() {
      if (!this.isReadable()) {
         return false;
      } else {
         try {
            Object value = this.get();
            return INTEROP.isString(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage());
         }
      }
   }

   public final String asString() throws DebugException {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object val = this.get();
            return INTEROP.isString(val) ? INTEROP.asString(val) : null;
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage());
         }
      }
   }

   public boolean fitsInInt() {
      if (!this.isReadable()) {
         return false;
      } else {
         try {
            Object value = this.get();
            return INTEROP.fitsInInt(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage());
         }
      }
   }

   public int asInt() {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object value = this.get();
            return INTEROP.asInt(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (UnsupportedMessageException var3) {
            throw new UnsupportedOperationException("Not an int", var3);
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage());
         }
      }
   }

   public boolean isBoolean() {
      if (!this.isReadable()) {
         return false;
      } else {
         try {
            Object value = this.get();
            return INTEROP.isBoolean(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage());
         }
      }
   }

   public boolean asBoolean() {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object value = this.get();
            return INTEROP.asBoolean(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (UnsupportedMessageException var3) {
            throw new UnsupportedOperationException("Not a boolean", var3);
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage());
         }
      }
   }

   public boolean isNumber() {
      if (!this.isReadable()) {
         return false;
      } else {
         try {
            Object value = this.get();
            return INTEROP.isNumber(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage());
         }
      }
   }

   public boolean fitsInLong() {
      if (!this.isReadable()) {
         return false;
      } else {
         try {
            Object value = this.get();
            return INTEROP.fitsInLong(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage());
         }
      }
   }

   public long asLong() {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object value = this.get();
            return INTEROP.asLong(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (UnsupportedMessageException var3) {
            throw new UnsupportedOperationException("Not a long", var3);
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage());
         }
      }
   }

   public boolean fitsInDouble() {
      if (!this.isReadable()) {
         return false;
      } else {
         try {
            Object value = this.get();
            return INTEROP.fitsInDouble(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage());
         }
      }
   }

   public double asDouble() {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object value = this.get();
            return INTEROP.asDouble(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (UnsupportedMessageException var3) {
            throw new UnsupportedOperationException("Not a double", var3);
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage());
         }
      }
   }

   public boolean fitsInFloat() {
      if (!this.isReadable()) {
         return false;
      } else {
         try {
            Object value = this.get();
            return INTEROP.fitsInFloat(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage());
         }
      }
   }

   public float asFloat() {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object value = this.get();
            return INTEROP.asFloat(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (UnsupportedMessageException var3) {
            throw new UnsupportedOperationException("Not a float", var3);
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage());
         }
      }
   }

   public boolean fitsInByte() {
      if (!this.isReadable()) {
         return false;
      } else {
         try {
            Object value = this.get();
            return INTEROP.fitsInByte(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage());
         }
      }
   }

   public byte asByte() {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object value = this.get();
            return INTEROP.asByte(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (UnsupportedMessageException var3) {
            throw new UnsupportedOperationException("Not a byte", var3);
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage());
         }
      }
   }

   public boolean fitsInShort() {
      if (!this.isReadable()) {
         return false;
      } else {
         try {
            Object value = this.get();
            return INTEROP.fitsInShort(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage());
         }
      }
   }

   public short asShort() {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object value = this.get();
            return INTEROP.asShort(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (UnsupportedMessageException var3) {
            throw new UnsupportedOperationException("Not a short", var3);
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage());
         }
      }
   }

   public boolean isDate() {
      if (!this.isReadable()) {
         return false;
      } else {
         try {
            Object value = this.get();
            return INTEROP.isDate(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public LocalDate asDate() {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object value = this.get();
            return INTEROP.asDate(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (UnsupportedMessageException var3) {
            throw new UnsupportedOperationException("Not a date", var3);
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public boolean isTime() {
      if (!this.isReadable()) {
         return false;
      } else {
         try {
            Object value = this.get();
            return INTEROP.isTime(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public LocalTime asTime() {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object value = this.get();
            return INTEROP.asTime(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (UnsupportedMessageException var3) {
            throw new UnsupportedOperationException("Not a time", var3);
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public boolean isInstant() {
      return this.isDate() && this.isTime() && this.isTimeZone();
   }

   public Instant asInstant() {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object value = this.get();
            return INTEROP.asInstant(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (UnsupportedMessageException var3) {
            throw new UnsupportedOperationException("Not an instant", var3);
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public boolean isTimeZone() {
      if (!this.isReadable()) {
         return false;
      } else {
         try {
            Object value = this.get();
            return INTEROP.isTimeZone(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public ZoneId asTimeZone() {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object value = this.get();
            return INTEROP.asTimeZone(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (UnsupportedMessageException var3) {
            throw new UnsupportedOperationException("Not a time", var3);
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public boolean isDuration() {
      if (!this.isReadable()) {
         return false;
      } else {
         try {
            Object value = this.get();
            return INTEROP.isDuration(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public Duration asDuration() {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object value = this.get();
            return INTEROP.asDuration(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (UnsupportedMessageException var3) {
            throw new UnsupportedOperationException("Not a time", var3);
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public boolean isMetaObject() {
      if (!this.isReadable()) {
         return false;
      } else {
         try {
            Object value = this.get();
            return INTEROP.isMetaObject(value);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public String getMetaQualifiedName() {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object value = this.get();
            return INTEROP.asString(INTEROP.getMetaQualifiedName(value));
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (UnsupportedMessageException var3) {
            throw new UnsupportedOperationException("Not a metaobject", var3);
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public String getMetaSimpleName() {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object value = this.get();
            return INTEROP.asString(INTEROP.getMetaSimpleName(value));
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (UnsupportedMessageException var3) {
            throw new UnsupportedOperationException("Not a metaobject", var3);
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public boolean isMetaInstance(DebugValue instance) {
      if (!this.isReadable()) {
         throw new UnsupportedOperationException("Value is not readable");
      } else {
         try {
            Object value = this.get();
            return INTEROP.isMetaInstance(value, instance.get());
         } catch (ThreadDeath var3) {
            throw var3;
         } catch (UnsupportedMessageException var4) {
            throw new UnsupportedOperationException("Not a metaobject", var4);
         } catch (Throwable var5) {
            throw DebugException.create(this.getSession(), var5, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public final List<Breakpoint> getRootInstanceBreakpoints() {
      final Object value = this.get();
      final List<Breakpoint>[] breakpoints = new List[]{null};
      this.getSession().visitBreakpoints(new Consumer<Breakpoint>() {
         public void accept(Breakpoint b) {
            if (b.getRootInstance() == value) {
               if (breakpoints[0] == null) {
                  breakpoints[0] = new LinkedList<>();
               }

               breakpoints[0].add(b);
            }
         }
      });
      return breakpoints[0] != null ? breakpoints[0] : Collections.emptyList();
   }

   public final Collection<DebugValue> getProperties() throws DebugException {
      if (!this.isReadable()) {
         return null;
      } else {
         Object value = this.get();

         try {
            return getProperties(value, null, this.getSession(), this.resolveLanguage(), null);
         } catch (ThreadDeath var3) {
            throw var3;
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }
   }

   static ValuePropertiesCollection getProperties(Object value, String receiverName, DebuggerSession session, LanguageInfo language, DebugScope scope) {
      if (INTEROP.hasMembers(value)) {
         Object keys;
         try {
            keys = INTEROP.getMembers(value, true);
         } catch (UnsupportedMessageException var7) {
            return null;
         }

         return new ValuePropertiesCollection(session, language, value, keys, receiverName, scope);
      } else {
         return null;
      }
   }

   public final DebugValue getProperty(String name) throws DebugException {
      if (!this.isReadable()) {
         return null;
      } else {
         Object value = this.get();
         if (value != null) {
            try {
               return !INTEROP.isMemberExisting(value, name)
                  ? null
                  : new DebugValue.ObjectMemberValue(this.getSession(), this.resolveLanguage(), null, value, name);
            } catch (ThreadDeath var4) {
               throw var4;
            } catch (Throwable var5) {
               throw DebugException.create(this.getSession(), var5, this.resolveLanguage(), null, true, null);
            }
         } else {
            return null;
         }
      }
   }

   public final boolean isArray() throws DebugException {
      return !this.isReadable() ? false : INTEROP.hasArrayElements(this.get());
   }

   public List<DebugValue> getArray() throws DebugException {
      if (!this.isReadable()) {
         return null;
      } else {
         Object value = this.get();
         return INTEROP.hasArrayElements(value) ? new ValueInteropList(this.getSession(), this.resolveLanguage(), value) : null;
      }
   }

   public Object getRawValue(Class<? extends TruffleLanguage<?>> languageClass) {
      Objects.requireNonNull(languageClass);
      RootNode rootNode = this.getScope().getRoot();
      if (rootNode == null) {
         return null;
      } else {
         TruffleLanguage<?> language = Debugger.ACCESSOR.nodeSupport().getLanguage(rootNode);
         return language != null && language.getClass() == languageClass ? this.get() : null;
      }
   }

   public final String toDisplayString() {
      return this.toDisplayString(true);
   }

   public final String toDisplayString(boolean allowSideEffects) throws DebugException {
      if (!this.isReadable()) {
         return "<not readable>";
      } else {
         try {
            Object stringValue = INTEROP.toDisplayString(this.getLanguageView(), allowSideEffects);
            return INTEROP.asString(stringValue);
         } catch (ThreadDeath var3) {
            throw var3;
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }
   }

   final Object getLanguageView() {
      LanguageInfo language = this.resolveLanguage();
      Object value = this.get();
      return language == null ? value : this.getDebugger().getEnv().getLanguageView(language, value);
   }

   final LanguageInfo resolveLanguage() {
      LanguageInfo languageInfo;
      if (this.preferredLanguage != null) {
         languageInfo = this.preferredLanguage;
      } else if (this.getScope() != null && this.getScope().getLanguage() != null) {
         languageInfo = this.getScope().getLanguage();
      } else {
         languageInfo = this.getOriginalLanguage();
      }

      return languageInfo;
   }

   public final DebugValue getMetaObject() throws DebugException {
      if (!this.isReadable()) {
         return null;
      } else {
         Object view = this.getLanguageView();

         try {
            return INTEROP.hasMetaObject(view) ? new DebugValue.HeapValue(this.getSession(), this.resolveLanguage(), null, INTEROP.getMetaObject(view)) : null;
         } catch (ThreadDeath var3) {
            throw var3;
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public final SourceSection getSourceLocation() throws DebugException {
      if (!this.isReadable()) {
         return null;
      } else {
         try {
            Object obj = this.getLanguageView();
            return INTEROP.hasSourceLocation(obj) ? this.getSession().resolveSection(INTEROP.getSourceLocation(obj)) : null;
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public final boolean canExecute() throws DebugException {
      if (!this.isReadable()) {
         return false;
      } else {
         Object value = this.get();

         try {
            return INTEROP.isExecutable(value);
         } catch (ThreadDeath var3) {
            throw var3;
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public final DebugValue execute(DebugValue... arguments) throws DebugException {
      Object value = this.get();
      Object[] args = new Object[arguments.length];

      for (int i = 0; i < arguments.length; i++) {
         args[i] = arguments[i].get();
      }

      try {
         Object retValue = INTEROP.execute(value, args);
         return new DebugValue.HeapValue(this.getSession(), this.resolveLanguage(), null, retValue);
      } catch (ThreadDeath var5) {
         throw var5;
      } catch (Throwable var6) {
         throw DebugException.create(this.getSession(), var6, this.resolveLanguage(), null, true, null);
      }
   }

   public boolean hasIterator() throws DebugException {
      if (!this.isReadable()) {
         return false;
      } else {
         Object value = this.get();

         try {
            return INTEROP.hasIterator(value);
         } catch (ThreadDeath var3) {
            throw var3;
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public DebugValue getIterator() throws DebugException {
      Object value = this.get();

      Object iterator;
      try {
         iterator = INTEROP.getIterator(value);
      } catch (ThreadDeath var4) {
         throw var4;
      } catch (Throwable var5) {
         throw DebugException.create(this.getSession(), var5, this.resolveLanguage(), null, true, null);
      }

      return new DebugValue.HeapValue(this.getSession(), this.preferredLanguage, null, iterator);
   }

   public boolean isIterator() throws DebugException {
      if (!this.isReadable()) {
         return false;
      } else {
         Object value = this.get();

         try {
            return INTEROP.isIterator(value);
         } catch (ThreadDeath var3) {
            throw var3;
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public boolean hasIteratorNextElement() throws DebugException {
      if (!this.isReadable()) {
         return false;
      } else {
         Object value = this.get();

         try {
            return INTEROP.hasIteratorNextElement(value);
         } catch (ThreadDeath var3) {
            throw var3;
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public DebugValue getIteratorNextElement() throws NoSuchElementException, DebugException {
      Object value = this.get();

      Object next;
      try {
         next = INTEROP.getIteratorNextElement(value);
      } catch (ThreadDeath var4) {
         throw var4;
      } catch (StopIterationException var5) {
         throw new NoSuchElementException(var5.getLocalizedMessage());
      } catch (Throwable var6) {
         throw DebugException.create(this.getSession(), var6, this.resolveLanguage(), null, true, null);
      }

      return new DebugValue.HeapValue(this.getSession(), this.preferredLanguage, null, next);
   }

   public boolean hasHashEntries() throws DebugException {
      if (!this.isReadable()) {
         return false;
      } else {
         Object hash = this.get();

         try {
            return INTEROP.hasHashEntries(hash);
         } catch (ThreadDeath var3) {
            throw var3;
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }
   }

   public long getHashSize() throws DebugException {
      Object hash = this.get();

      try {
         return INTEROP.getHashSize(hash);
      } catch (ThreadDeath var3) {
         throw var3;
      } catch (Throwable var4) {
         throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
      }
   }

   public boolean isHashEntryReadable(DebugValue key) throws DebugException {
      Object hash = this.get();
      Object keyObject = key.get();

      try {
         return INTEROP.isHashEntryReadable(hash, keyObject);
      } catch (ThreadDeath var5) {
         throw var5;
      } catch (Throwable var6) {
         throw DebugException.create(this.getSession(), var6, this.resolveLanguage(), null, true, null);
      }
   }

   public DebugValue getHashValue(DebugValue key) throws DebugException {
      Object hash = this.get();
      Object keyObject = key.get();
      DebugValue value = DebugValue.HashEntryValue.getValueOrNull(this.getSession(), this.preferredLanguage, hash, keyObject);
      return value;
   }

   public DebugValue getHashValueOrDefault(DebugValue key, DebugValue defaultValue) throws DebugException {
      Object hash = this.get();
      Object keyObject = key.get();
      Object defaultObject = defaultValue.get();

      Object v;
      try {
         v = INTEROP.readHashValueOrDefault(hash, keyObject, defaultObject);
      } catch (ThreadDeath var8) {
         throw var8;
      } catch (Throwable var9) {
         throw DebugException.create(this.getSession(), var9, this.resolveLanguage(), null, true, null);
      }

      if (v == defaultObject) {
         return defaultValue;
      } else {
         DebugValue.HashEntryValue value = new DebugValue.HashEntryValue(
            this.getSession(), this.preferredLanguage, hash, keyObject, DebugValue.HashEntryValue.EntryKind.VALUE
         );
         value.setCachedValue(v);
         return value;
      }
   }

   public boolean isHashEntryModifiable(DebugValue key) throws DebugException {
      Object hash = this.get();
      Object keyObject = key.get();

      try {
         return INTEROP.isHashEntryModifiable(hash, keyObject);
      } catch (ThreadDeath var5) {
         throw var5;
      } catch (Throwable var6) {
         throw DebugException.create(this.getSession(), var6, this.resolveLanguage(), null, true, null);
      }
   }

   public boolean isHashEntryInsertable(DebugValue key) throws DebugException {
      Object hash = this.get();
      Object keyObject = key.get();

      try {
         return INTEROP.isHashEntryInsertable(hash, keyObject);
      } catch (ThreadDeath var5) {
         throw var5;
      } catch (Throwable var6) {
         throw DebugException.create(this.getSession(), var6, this.resolveLanguage(), null, true, null);
      }
   }

   public boolean isHashEntryWritable(DebugValue key) throws DebugException {
      Object hash = this.get();
      Object keyObject = key.get();

      try {
         return INTEROP.isHashEntryWritable(hash, keyObject);
      } catch (ThreadDeath var5) {
         throw var5;
      } catch (Throwable var6) {
         throw DebugException.create(this.getSession(), var6, this.resolveLanguage(), null, true, null);
      }
   }

   public void putHashEntry(DebugValue key, DebugValue value) throws DebugException {
      Object hash = this.get();
      Object keyObject = key.get();
      Object valueObject = value.get();

      try {
         INTEROP.writeHashEntry(hash, keyObject, valueObject);
      } catch (ThreadDeath var7) {
         throw var7;
      } catch (Throwable var8) {
         throw DebugException.create(this.getSession(), var8, this.resolveLanguage(), null, true, null);
      }
   }

   public boolean isHashEntryRemovable(DebugValue key) throws DebugException {
      Object hash = this.get();
      Object keyObject = key.get();

      try {
         return INTEROP.isHashEntryRemovable(hash, keyObject);
      } catch (ThreadDeath var5) {
         throw var5;
      } catch (Throwable var6) {
         throw DebugException.create(this.getSession(), var6, this.resolveLanguage(), null, true, null);
      }
   }

   public boolean removeHashEntry(DebugValue key) throws DebugException {
      Object hash = this.get();
      Object keyObject = key.get();

      try {
         INTEROP.removeHashEntry(hash, keyObject);
         return true;
      } catch (UnknownKeyException var5) {
         return false;
      } catch (ThreadDeath var6) {
         throw var6;
      } catch (Throwable var7) {
         throw DebugException.create(this.getSession(), var7, this.resolveLanguage(), null, true, null);
      }
   }

   public boolean isHashEntryExisting(DebugValue key) throws DebugException {
      Object hash = this.get();
      Object keyObject = key.get();

      try {
         return INTEROP.isHashEntryExisting(hash, keyObject);
      } catch (ThreadDeath var5) {
         throw var5;
      } catch (Throwable var6) {
         throw DebugException.create(this.getSession(), var6, this.resolveLanguage(), null, true, null);
      }
   }

   public DebugValue getHashEntriesIterator() throws DebugException {
      Object hash = this.get();

      Object entriesIterator;
      try {
         entriesIterator = INTEROP.getHashEntriesIterator(hash);
      } catch (ThreadDeath var4) {
         throw var4;
      } catch (Throwable var5) {
         throw DebugException.create(this.getSession(), var5, this.resolveLanguage(), null, true, null);
      }

      return new DebugValue.HashEntriesIteratorValue(this.getSession(), this.preferredLanguage, null, hash, entriesIterator, null);
   }

   public DebugValue getHashKeysIterator() throws DebugException {
      Object hash = this.get();

      Object keysIterator;
      try {
         keysIterator = INTEROP.getHashKeysIterator(hash);
      } catch (ThreadDeath var4) {
         throw var4;
      } catch (Throwable var5) {
         throw DebugException.create(this.getSession(), var5, this.resolveLanguage(), null, true, null);
      }

      return new DebugValue.HeapValue(this.getSession(), this.preferredLanguage, null, keysIterator);
   }

   public DebugValue getHashValuesIterator() throws DebugException {
      Object hash = this.get();

      Object valuesIterator;
      try {
         valuesIterator = INTEROP.getHashValuesIterator(hash);
      } catch (ThreadDeath var4) {
         throw var4;
      } catch (Throwable var5) {
         throw DebugException.create(this.getSession(), var5, this.resolveLanguage(), null, true, null);
      }

      return new DebugValue.HeapValue(this.getSession(), this.preferredLanguage, null, valuesIterator);
   }

   @Override
   public int hashCode() throws DebugException {
      if (this.isReadable()) {
         Object value = this.get();
         return this.valueHashCode(value);
      } else {
         return this.unreadableHashCode();
      }
   }

   @Override
   public boolean equals(Object obj) throws DebugException {
      if (!(obj instanceof DebugValue)) {
         return false;
      } else if (obj == this) {
         return true;
      } else {
         DebugValue other = (DebugValue)obj;
         boolean thisReadable = this.isReadable();
         boolean otherReadable = other.isReadable();
         if (thisReadable && otherReadable) {
            Object value1 = this.get();
            Object value2 = other.get();
            return this.valueEquals(value1, value2);
         } else {
            return thisReadable != otherReadable ? false : this.unreadableEquals(other);
         }
      }
   }

   int valueHashCode(Object value) throws DebugException {
      try {
         if (INTEROP.hasIdentity(value)) {
            return INTEROP.identityHashCode(value);
         }
      } catch (ThreadDeath var3) {
         throw var3;
      } catch (Throwable var4) {
         throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
      }

      return System.identityHashCode(value);
   }

   boolean valueEquals(Object value1, Object value2) throws DebugException {
      if (value1 == value2) {
         return true;
      } else {
         try {
            return INTEROP.isIdentical(value1, value2, INTEROP);
         } catch (ThreadDeath var4) {
            throw var4;
         } catch (Throwable var5) {
            throw DebugException.create(this.getSession(), var5, this.resolveLanguage(), null, true, null);
         }
      }
   }

   abstract int unreadableHashCode();

   abstract boolean unreadableEquals(DebugValue var);

   public final LanguageInfo getOriginalLanguage() throws DebugException {
      if (!this.isReadable()) {
         return null;
      } else {
         Object obj = this.get();
         if (obj == null) {
            return null;
         } else {
            InteropLibrary lib = InteropLibrary.getFactory().getUncached(obj);
            if (lib.hasLanguage(obj)) {
               try {
                  return this.getSession().getDebugger().getEnv().getLanguageInfo(lib.getLanguage(obj));
               } catch (UnsupportedMessageException var4) {
                  CompilerDirectives.transferToInterpreter();
                  throw new AssertionError(var4);
               }
            } else {
               return null;
            }
         }
      }
   }

   public final DebugValue asInLanguage(LanguageInfo language) {
      return this.preferredLanguage == language ? this : this.createAsInLanguage(language);
   }

   abstract DebugValue createAsInLanguage(LanguageInfo language);

   public abstract DebuggerSession getSession();

   final Debugger getDebugger() {
      return this.getSession().getDebugger();
   }

   @Override
   public String toString() {
      return "DebugValue(name=" + this.getName() + ", value = " + (this.isReadable() ? this.toDisplayString() : "<not readable>") + ")";
   }

   static void checkPrimitive(Object value) {
      Class<?> clazz;
      if (value == null
         || (clazz = value.getClass()) != Byte.class
            && clazz != Short.class
            && clazz != Integer.class
            && clazz != Long.class
            && clazz != Float.class
            && clazz != Double.class
            && clazz != Character.class
            && clazz != Boolean.class
            && clazz != String.class) {
         throw new IllegalArgumentException(value + " is not primitive.");
      }
   }

   abstract static class AbstractDebugCachedValue extends DebugValue.AbstractDebugValue {
      private volatile Object cachedValue;

      AbstractDebugCachedValue(DebuggerSession session, LanguageInfo preferredLanguage) {
         super(session, preferredLanguage);
      }

      @Override
      final Object get() {
         Object value = this.cachedValue;
         if (value == null) {
            synchronized (this) {
               value = this.cachedValue;
               if (value == null) {
                  value = this.readValue();
                  this.cachedValue = value;
               }
            }
         }

         return value;
      }

      abstract Object readValue();

      final void setCachedValue(Object newCachedValue) {
         this.cachedValue = newCachedValue;
      }

      final void resetCachedValue() {
         this.cachedValue = null;
      }
   }

   abstract static class AbstractDebugValue extends DebugValue {
      final DebuggerSession session;

      AbstractDebugValue(DebuggerSession session, LanguageInfo preferredLanguage) {
         super(preferredLanguage);
         this.session = session;
      }

      @Override
      public final <T> T as(Class<T> clazz) throws DebugException {
         if (!this.isReadable()) {
            throw new IllegalStateException("Value is not readable");
         } else {
            try {
               if (clazz == String.class) {
                  Object val = this.get();
                  Object stringValue;
                  if (INTEROP.isMetaObject(val)) {
                     stringValue = INTEROP.getMetaQualifiedName(val);
                  } else {
                     stringValue = INTEROP.toDisplayString(this.getLanguageView());
                  }

                  return clazz.cast(INTEROP.asString(stringValue));
               }

               if (clazz == Number.class || clazz == Boolean.class) {
                  return this.convertToPrimitive(clazz);
               }
            } catch (ThreadDeath var4) {
               throw var4;
            } catch (Throwable var5) {
               throw DebugException.create(this.getSession(), var5, this.resolveLanguage(), null, true, null);
            }

            throw new UnsupportedOperationException();
         }
      }

      private <T> T convertToPrimitive(Class<T> clazz) {
         Object val = this.get();
         return clazz.isInstance(val) ? clazz.cast(val) : clazz.cast(Debugger.ACCESSOR.hostSupport().convertPrimitiveLossLess(val, clazz));
      }

      @Override
      public final DebuggerSession getSession() {
         return this.session;
      }
   }

   static final class ArrayElementValue extends DebugValue.AbstractDebugCachedValue {
      private final Object array;
      private final long index;
      private final DebugScope scope;

      ArrayElementValue(DebuggerSession session, LanguageInfo preferredLanguage, DebugScope scope, Object array, long index) {
         super(session, preferredLanguage);
         this.array = array;
         this.index = index;
         this.scope = scope;
      }

      @Override
      Object readValue() {
         this.checkValid();

         try {
            return INTEROP.readArrayElement(this.array, this.index);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage(), null, true, null);
         }
      }

      @Override
      public String getName() {
         return String.valueOf(this.index);
      }

      @Override
      public boolean isReadable() {
         this.checkValid();
         return INTEROP.isArrayElementReadable(this.array, this.index);
      }

      @Override
      public boolean isWritable() {
         this.checkValid();
         return INTEROP.isArrayElementWritable(this.array, this.index);
      }

      @Override
      public boolean hasReadSideEffects() {
         this.checkValid();
         return false;
      }

      @Override
      public boolean hasWriteSideEffects() {
         this.checkValid();
         return false;
      }

      @Override
      public boolean isInternal() {
         this.checkValid();
         return false;
      }

      @Override
      public DebugScope getScope() {
         this.checkValid();
         return this.scope;
      }

      @Override
      public void set(DebugValue value) {
         this.checkValid();

         try {
            Object newValue = value.get();
            INTEROP.writeArrayElement(this.array, this.index, newValue);
            this.resetCachedValue();
         } catch (ThreadDeath var3) {
            throw var3;
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }

      @Override
      public void set(Object primitiveValue) {
         this.checkValid();
         checkPrimitive(primitiveValue);

         try {
            INTEROP.writeArrayElement(this.array, this.index, primitiveValue);
            this.resetCachedValue();
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage(), null, true, null);
         }
      }

      @Override
      DebugValue createAsInLanguage(LanguageInfo language) {
         return new DebugValue.ArrayElementValue(this.session, language, this.scope, this.array, this.index);
      }

      @Override
      int unreadableHashCode() {
         int hash = 7;
         hash = 29 * hash + this.valueHashCode(this.array);
         return 29 * hash + Long.hashCode(this.index);
      }

      @Override
      boolean unreadableEquals(DebugValue var) {
         if (!(var instanceof DebugValue.ArrayElementValue)) {
            return false;
         } else {
            DebugValue.ArrayElementValue other = (DebugValue.ArrayElementValue)var;
            return this.valueEquals(this.array, other.array) && this.index == other.index;
         }
      }

      private void checkValid() {
         if (this.scope != null) {
            this.scope.verifyValidState();
         }
      }
   }

   private static final class HashEntriesIteratorValue extends DebugValue.HeapValue {
      private final Object hashMap;
      private final DebugValue.HashEntryValue.EntryKind kind;

      HashEntriesIteratorValue(
         DebuggerSession session, LanguageInfo preferredLanguage, String name, Object hashMap, Object value, DebugValue.HashEntryValue.EntryKind kind
      ) {
         super(session, preferredLanguage, name, value);
         this.hashMap = hashMap;

         assert kind == null || kind == DebugValue.HashEntryValue.EntryKind.KEY;

         this.kind = kind;
      }

      @Override
      public DebugValue getIteratorNextElement() {
         Object value = this.get();

         try {
            Object next = INTEROP.getIteratorNextElement(value);
            if (DebugValue.HashEntryValue.EntryKind.KEY == this.kind) {
               return new DebugValue.HashEntryValue(this.getSession(), this.resolveLanguage(), this.hashMap, next, this.kind);
            } else {
               assert this.kind == null;

               return new DebugValue.HashEntryArrayValue(this.getSession(), this.resolveLanguage(), null, this.hashMap, next);
            }
         } catch (ThreadDeath var3) {
            throw var3;
         } catch (StopIterationException var4) {
            throw new NoSuchElementException(var4.getLocalizedMessage());
         } catch (Throwable var5) {
            throw DebugException.create(this.getSession(), var5, this.resolveLanguage(), null, true, null);
         }
      }
   }

   private static final class HashEntryArrayValue extends DebugValue.HeapValue {
      private final Object hashMap;

      HashEntryArrayValue(DebuggerSession session, LanguageInfo preferredLanguage, String name, Object hashMap, Object value) {
         super(session, preferredLanguage, name, value);
         this.hashMap = hashMap;
      }

      @Override
      public List<DebugValue> getArray() throws DebugException {
         return new DebugValue.HashEntryArrayValue.HashEntriesList(this.get());
      }

      final class HashEntriesList extends AbstractList<DebugValue> {
         private final Object list;

         HashEntriesList(Object list) {
            this.list = list;
         }

         public DebugValue get(int index) {
            DebugValue.HashEntryValue.EntryKind kind;
            switch (index) {
               case 0:
                  kind = DebugValue.HashEntryValue.EntryKind.KEY;
                  break;
               case 1:
                  kind = DebugValue.HashEntryValue.EntryKind.VALUE;
                  break;
               default:
                  throw DebugException.create(
                     HashEntryArrayValue.this.getSession(),
                     InvalidArrayIndexException.create(index),
                     HashEntryArrayValue.this.resolveLanguage(),
                     null,
                     true,
                     null
                  );
            }

            Object key;
            try {
               key = DebugValue.INTEROP.readArrayElement(this.list, 0L);
            } catch (ThreadDeath var5) {
               throw var5;
            } catch (Throwable var6) {
               throw DebugException.create(HashEntryArrayValue.this.getSession(), var6, HashEntryArrayValue.this.resolveLanguage(), null, true, null);
            }

            return new DebugValue.HashEntryValue(
               HashEntryArrayValue.this.session, HashEntryArrayValue.this.preferredLanguage, HashEntryArrayValue.this.hashMap, key, kind
            );
         }

         public DebugValue set(int index, DebugValue newValue) {
            throw new UnsupportedOperationException();
         }

         @Override
         public int size() {
            try {
               return (int)DebugValue.INTEROP.getArraySize(this.list);
            } catch (UnsupportedMessageException var2) {
               return 0;
            }
         }
      }
   }

   private static final class HashEntryValue extends DebugValue.AbstractDebugCachedValue {
      private final Object hashMap;
      private final Object key;
      private final DebugValue.HashEntryValue.EntryKind kind;

      HashEntryValue(DebuggerSession session, LanguageInfo preferredLanguage, Object map, Object key, DebugValue.HashEntryValue.EntryKind kind) {
         super(session, preferredLanguage);
         this.hashMap = map;
         this.key = key;
         this.kind = kind;
      }

      static DebugValue.HashEntryValue getValueOrNull(DebuggerSession session, LanguageInfo preferredLanguage, Object map, Object key) throws DebugException {
         Object valueObject;
         try {
            valueObject = INTEROP.readHashValue(map, key);
         } catch (ThreadDeath var6) {
            throw var6;
         } catch (UnknownKeyException var7) {
            return null;
         } catch (Throwable var8) {
            throw DebugException.create(session, var8, preferredLanguage, null, true, null);
         }

         DebugValue.HashEntryValue value = new DebugValue.HashEntryValue(session, preferredLanguage, map, key, DebugValue.HashEntryValue.EntryKind.VALUE);
         value.setCachedValue(valueObject);
         return value;
      }

      @Override
      public String getName() {
         return null;
      }

      @Override
      public boolean isReadable() {
         switch (this.kind) {
            case KEY:
               return true;
            case VALUE:
               try {
                  return INTEROP.isHashEntryReadable(this.hashMap, this.key);
               } catch (ThreadDeath var2) {
                  throw var2;
               } catch (Throwable var3) {
                  throw DebugException.create(this.getSession(), var3, this.resolveLanguage(), null, true, null);
               }
            default:
               throw CompilerDirectives.shouldNotReachHere();
         }
      }

      @Override
      public boolean isWritable() {
         switch (this.kind) {
            case KEY:
               try {
                  return INTEROP.isHashEntryRemovable(this.hashMap, this.key);
               } catch (ThreadDeath var4) {
                  throw var4;
               } catch (Throwable var5) {
                  throw DebugException.create(this.getSession(), var5, this.resolveLanguage(), null, true, null);
               }
            case VALUE:
               try {
                  return INTEROP.isHashEntryWritable(this.hashMap, this.key);
               } catch (ThreadDeath var2) {
                  throw var2;
               } catch (Throwable var3) {
                  throw DebugException.create(this.getSession(), var3, this.resolveLanguage(), null, true, null);
               }
            default:
               throw CompilerDirectives.shouldNotReachHere();
         }
      }

      @Override
      Object readValue() {
         switch (this.kind) {
            case KEY:
               return this.key;
            case VALUE:
               try {
                  return INTEROP.readHashValue(this.hashMap, this.key);
               } catch (ThreadDeath var2) {
                  throw var2;
               } catch (Throwable var3) {
                  throw DebugException.create(this.getSession(), var3, this.resolveLanguage(), null, true, null);
               }
            default:
               throw CompilerDirectives.shouldNotReachHere();
         }
      }

      @Override
      public boolean hasReadSideEffects() {
         return true;
      }

      @Override
      public boolean hasWriteSideEffects() {
         return true;
      }

      @Override
      public boolean isInternal() {
         return false;
      }

      @Override
      public DebugScope getScope() {
         return null;
      }

      @Override
      public void set(DebugValue value) {
         Object newValue = value.get();
         this.setNewValue(newValue);
      }

      @Override
      public void set(Object primitiveValue) {
         checkPrimitive(primitiveValue);
         this.setNewValue(primitiveValue);
      }

      private void setNewValue(Object newValue) {
         switch (this.kind) {
            case KEY:
               try {
                  Object value = INTEROP.readHashValue(this.hashMap, this.key);
                  INTEROP.removeHashEntry(this.hashMap, this.key);
                  INTEROP.writeHashEntry(this.hashMap, newValue, value);
                  return;
               } catch (ThreadDeath var5) {
                  throw var5;
               } catch (Throwable var6) {
                  throw DebugException.create(this.getSession(), var6, this.resolveLanguage(), null, true, null);
               }
            case VALUE:
               try {
                  INTEROP.writeHashEntry(this.hashMap, this.key, newValue);
                  return;
               } catch (ThreadDeath var3) {
                  throw var3;
               } catch (Throwable var4) {
                  throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
               }
            default:
               throw CompilerDirectives.shouldNotReachHere();
         }
      }

      @Override
      DebugValue createAsInLanguage(LanguageInfo language) {
         return new DebugValue.HashEntryValue(this.session, language, this.hashMap, this.key, this.kind);
      }

      @Override
      int unreadableHashCode() {
         int hash = 7;
         hash = 29 * hash + this.valueHashCode(this.hashMap);
         hash = 29 * hash + this.valueHashCode(this.key);
         return 29 * hash + this.kind.hashCode();
      }

      @Override
      boolean unreadableEquals(DebugValue var) {
         if (!(var instanceof DebugValue.HashEntryValue)) {
            return false;
         } else {
            DebugValue.HashEntryValue other = (DebugValue.HashEntryValue)var;
            return this.valueEquals(this.hashMap, other.hashMap) && this.valueEquals(this.key, other.key) && this.kind == other.kind;
         }
      }

      static enum EntryKind {
         KEY,
         VALUE;
      }
   }

   static class HeapValue extends DebugValue.AbstractDebugValue {
      private final String name;
      private final Object value;

      HeapValue(DebuggerSession session, String name, Object value) {
         this(session, null, name, value);
      }

      HeapValue(DebuggerSession session, LanguageInfo preferredLanguage, String name, Object value) {
         super(session, preferredLanguage);
         this.name = name;
         this.value = value;

         assert value != null;
      }

      @Override
      Object get() {
         return this.value;
      }

      @Override
      public void set(DebugValue expression) {
         throw DebugException.create(this.getSession(), "Can not modify read-only value.");
      }

      @Override
      public void set(Object primitiveValue) {
         throw DebugException.create(this.getSession(), "Can not modify read-only value.");
      }

      @Override
      public String getName() {
         return this.name;
      }

      @Override
      public boolean isReadable() {
         return true;
      }

      @Override
      public boolean isWritable() {
         return false;
      }

      @Override
      public boolean hasReadSideEffects() {
         return false;
      }

      @Override
      public boolean hasWriteSideEffects() {
         return false;
      }

      @Override
      public boolean isInternal() {
         return false;
      }

      @Override
      DebugValue createAsInLanguage(LanguageInfo language) {
         return new DebugValue.HeapValue(this.session, language, this.name, this.value);
      }

      @Override
      int unreadableHashCode() {
         throw new UnsupportedOperationException("HeapValue is always readable.");
      }

      @Override
      boolean unreadableEquals(DebugValue var) {
         throw new UnsupportedOperationException("HeapValue is always readable.");
      }
   }

   static final class ObjectMemberValue extends DebugValue.AbstractDebugCachedValue {
      private final Object object;
      private final String member;
      private final DebugScope scope;

      ObjectMemberValue(DebuggerSession session, LanguageInfo preferredLanguage, DebugScope scope, Object object, String member) {
         super(session, preferredLanguage);
         this.object = object;
         this.member = member;
         this.scope = scope;
      }

      @Override
      Object readValue() {
         this.checkValid();

         try {
            return INTEROP.readMember(this.object, this.member);
         } catch (ThreadDeath var2) {
            throw var2;
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage(), null, true, null);
         }
      }

      @Override
      public String getName() {
         return String.valueOf(this.member);
      }

      @Override
      public boolean isReadable() {
         this.checkValid();
         return INTEROP.isMemberReadable(this.object, this.member);
      }

      @Override
      public boolean isWritable() {
         this.checkValid();
         return INTEROP.isMemberWritable(this.object, this.member);
      }

      @Override
      public boolean hasReadSideEffects() {
         this.checkValid();
         return INTEROP.hasMemberReadSideEffects(this.object, this.member);
      }

      @Override
      public boolean hasWriteSideEffects() {
         this.checkValid();
         return INTEROP.hasMemberWriteSideEffects(this.object, this.member);
      }

      @Override
      public boolean isInternal() {
         this.checkValid();
         return INTEROP.isMemberInternal(this.object, this.member);
      }

      @Override
      public DebugScope getScope() {
         this.checkValid();
         return this.scope;
      }

      @Override
      public void set(DebugValue value) {
         this.checkValid();

         try {
            Object newValue = value.get();
            INTEROP.writeMember(this.object, this.member, newValue);
            this.resetCachedValue();
         } catch (ThreadDeath var3) {
            throw var3;
         } catch (Throwable var4) {
            throw DebugException.create(this.getSession(), var4, this.resolveLanguage(), null, true, null);
         }
      }

      @Override
      public void set(Object primitiveValue) {
         this.checkValid();
         checkPrimitive(primitiveValue);

         try {
            INTEROP.writeMember(this.object, this.member, primitiveValue);
            this.resetCachedValue();
         } catch (Throwable var3) {
            throw DebugException.create(this.getSession(), var3, this.resolveLanguage(), null, true, null);
         }
      }

      @Override
      DebugValue createAsInLanguage(LanguageInfo language) {
         return new DebugValue.ObjectMemberValue(this.session, language, this.scope, this.object, this.member);
      }

      @Override
      int unreadableHashCode() {
         int hash = 7;
         hash = 29 * hash + this.valueHashCode(this.object);
         return 29 * hash + this.member.hashCode();
      }

      @Override
      boolean unreadableEquals(DebugValue var) {
         if (!(var instanceof DebugValue.ObjectMemberValue)) {
            return false;
         } else {
            DebugValue.ObjectMemberValue other = (DebugValue.ObjectMemberValue)var;
            return this.valueEquals(this.object, other.object) && this.member.equals(other.member);
         }
      }

      private void checkValid() {
         if (this.scope != null) {
            this.scope.verifyValidState();
         }
      }
   }
}
