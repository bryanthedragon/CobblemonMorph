package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.utilities.TriState;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.graalvm.polyglot.Value;

@ExportLibrary(InteropLibrary.class)
final class PolyglotBindings implements TruffleObject {
   private final PolyglotContextImpl context;
   private final PolyglotLanguageContext languageContext;
   private volatile Map<String, Value> bindings;

   PolyglotBindings(PolyglotContextImpl context) {
      this(context, null);
   }

   PolyglotBindings(PolyglotLanguageContext languageContext) {
      this(languageContext.context, languageContext);
   }

   private PolyglotBindings(PolyglotContextImpl context, PolyglotLanguageContext languageContext) {
      Objects.requireNonNull(context);
      this.context = context;
      this.languageContext = languageContext;
   }

   public Map<String, Value> getBindings() {
      Map<String, Value> localBindings = this.bindings;
      if (localBindings == null) {
         this.bindings = localBindings = this.context.getPolyglotGuestBindings();
      }

      return localBindings;
   }

   @ExportMessage
   boolean hasMembers() {
      return true;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object readMember(String member) throws UnknownIdentifierException {
      Value value = this.getBindings().get(member);
      if (value == null) {
         throw UnknownIdentifierException.create(member);
      } else {
         return this.languageContext != null ? this.context.toGuestValue(null, value, false) : this.context.getAPIAccess().getReceiver(value);
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   void writeMember(String member, Object value) {
      Value v = this.languageContext != null ? this.languageContext.asValue(value) : this.context.asValue(value);
      this.getBindings().put(member, v);
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   void removeMember(String member) throws UnknownIdentifierException {
      Value ret = this.getBindings().remove(member);
      if (ret == null) {
         throw UnknownIdentifierException.create(member);
      }
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getMembers(boolean includeInternal) {
      return new PolyglotBindings.Members(this.getBindings().keySet());
   }

   @ExportMessage.Repeat({@ExportMessage(name = "isMemberReadable"), @ExportMessage(name = "isMemberModifiable"), @ExportMessage(name = "isMemberRemovable")})
   @CompilerDirectives.TruffleBoundary
   boolean isMemberExisting(String member) {
      return this.getBindings().containsKey(member);
   }

   @ExportMessage
   boolean isMemberInsertable(String member) {
      return !this.isMemberExisting(member);
   }

   @ExportMessage
   TriState isIdenticalOrUndefined(Object other) {
      return this == other ? TriState.TRUE : TriState.UNDEFINED;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   int identityHashCode() {
      return System.identityHashCode(this);
   }

   @ExportLibrary(InteropLibrary.class)
   static final class Members implements TruffleObject {
      final String[] names;

      Members(Set<String> names) {
         this.names = names.toArray(new String[0]);
      }

      @ExportMessage
      boolean hasArrayElements() {
         return true;
      }

      @ExportMessage
      long getArraySize() {
         return this.names.length;
      }

      @ExportMessage
      Object readArrayElement(long index) throws InvalidArrayIndexException {
         if (!this.isArrayElementReadable(index)) {
            throw InvalidArrayIndexException.create(index);
         } else {
            return this.names[(int)index];
         }
      }

      @ExportMessage
      boolean isArrayElementReadable(long index) {
         return index >= 0L && index < this.names.length;
      }
   }
}
