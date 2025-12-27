package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import java.util.Objects;
import org.graalvm.collections.EconomicSet;
import org.graalvm.collections.Pair;

@ExportLibrary(InteropLibrary.class)
final class HostAdapterSuperMembers implements TruffleObject {
   final HostObject adapter;

   HostAdapterSuperMembers(HostObject adapter) {
      this.adapter = Objects.requireNonNull(adapter);
   }

   public Object getAdapter() {
      return this.adapter;
   }

   @ExportMessage
   boolean hasMembers() {
      return true;
   }

   @ExportMessage
   Object readMember(
      String name, @Cached.Shared("cache") @Cached HostAdapterSuperMembers.NameCache cache, @CachedLibrary("this.adapter") InteropLibrary interop
   ) throws UnsupportedMessageException, UnknownIdentifierException {
      String superMethodName = cache.getSuperMethodName(name);
      return interop.readMember(this.getAdapter(), superMethodName);
   }

   @ExportMessage
   Object invokeMember(
      String name,
      Object[] args,
      @Cached.Shared("cache") @Cached HostAdapterSuperMembers.NameCache cache,
      @CachedLibrary("this.adapter") InteropLibrary interop
   ) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
      String superMethodName = cache.getSuperMethodName(name);
      return interop.invokeMember(this.getAdapter(), superMethodName, args);
   }

   @ExportMessage
   boolean isMemberReadable(
      String name, @Cached.Shared("cache") @Cached HostAdapterSuperMembers.NameCache cache, @CachedLibrary("this.adapter") InteropLibrary interop
   ) {
      String superMethodName = cache.getSuperMethodName(name);
      return interop.isMemberReadable(this.getAdapter(), superMethodName);
   }

   @ExportMessage
   boolean isMemberInvocable(
      String name, @Cached.Shared("cache") @Cached HostAdapterSuperMembers.NameCache cache, @CachedLibrary("this.adapter") InteropLibrary interop
   ) {
      String superMethodName = cache.getSuperMethodName(name);
      return interop.isMemberInvocable(this.getAdapter(), superMethodName);
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getMembers(boolean includeInternal) {
      return new HostObject.KeysArray(includeInternal ? this.collectSuperMembers() : new String[0]);
   }

   @CompilerDirectives.TruffleBoundary
   private String[] collectSuperMembers() {
      HostClassDesc classDesc = HostClassDesc.forClass(this.adapter.context, this.adapter.getLookupClass());
      EconomicSet<String> names = EconomicSet.create();

      for (String name : classDesc.getMethodNames(false, true)) {
         if (name.startsWith("super$")) {
            names.add(name.substring("super$".length()));
         }
      }

      return names.toArray(new String[names.size()]);
   }

   static final class NameCache {
      @CompilerDirectives.CompilationFinal
      private Pair<String, String> cachedNameToSuper;
      private static final HostAdapterSuperMembers.NameCache UNCACHED = new HostAdapterSuperMembers.NameCache(true);

      NameCache() {
      }

      NameCache(boolean uncached) {
         if (uncached) {
            this.cachedNameToSuper = Pair.empty();
         }
      }

      String getSuperMethodName(String name) {
         if (this.cachedNameToSuper == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.cachedNameToSuper = Pair.create(name, HostAdapterFactory.getSuperMethodName(name));
         }

         String cachedName = this.cachedNameToSuper.getLeft();
         if (cachedName != null) {
            if (cachedName.equals(name)) {
               return this.cachedNameToSuper.getRight();
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.cachedNameToSuper = Pair.empty();
               return HostAdapterFactory.getSuperMethodName(name);
            }
         } else {
            return HostAdapterFactory.getSuperMethodName(name);
         }
      }

      static HostAdapterSuperMembers.NameCache create() {
         return new HostAdapterSuperMembers.NameCache();
      }

      static HostAdapterSuperMembers.NameCache getUncached() {
         return UNCACHED;
      }
   }
}
