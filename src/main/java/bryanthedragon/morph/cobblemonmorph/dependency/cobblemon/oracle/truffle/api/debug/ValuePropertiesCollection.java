package com.oracle.truffle.api.debug;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.LanguageInfo;
import java.util.AbstractCollection;
import java.util.Iterator;
import java.util.NoSuchElementException;

final class ValuePropertiesCollection extends AbstractCollection<DebugValue> {
   static final InteropLibrary INTEROP = InteropLibrary.getFactory().getUncached();
   private final DebuggerSession session;
   private final LanguageInfo language;
   private final Object object;
   private final DebugScope scope;
   private final Object keys;
   private final String receiverName;

   ValuePropertiesCollection(DebuggerSession session, LanguageInfo language, Object object, Object keys, String receiverName, DebugScope scope) {
      this.session = session;
      this.language = language;
      this.object = object;
      this.keys = keys;
      this.scope = scope;
      this.receiverName = receiverName;
   }

   @Override
   public Iterator<DebugValue> iterator() {
      return new ValuePropertiesCollection.PropertiesIterator(this.receiverName);
   }

   @Override
   public int size() {
      try {
         int size = (int)INTEROP.getArraySize(this.keys);
         if (this.receiverName != null) {
            size--;
         }

         return size;
      } catch (UnsupportedMessageException var2) {
         return 0;
      }
   }

   DebugValue get(String name) {
      if (name.equals(this.receiverName)) {
         return null;
      } else {
         return INTEROP.isMemberExisting(this.object, name)
            ? new DebugValue.ObjectMemberValue(this.session, this.language, this.scope, this.object, name)
            : null;
      }
   }

   private final class PropertiesIterator implements Iterator<DebugValue> {
      private final String ignoredName;
      private long currentIndex = 0L;
      private String nextMember;

      PropertiesIterator(String ignoredName) {
         this.ignoredName = ignoredName;
      }

      @Override
      public boolean hasNext() {
         if (this.ignoredName == null) {
            return ValuePropertiesCollection.INTEROP.isArrayElementExisting(ValuePropertiesCollection.this.keys, this.currentIndex);
         } else if (this.nextMember != null) {
            return true;
         } else {
            while (ValuePropertiesCollection.INTEROP.isArrayElementExisting(ValuePropertiesCollection.this.keys, this.currentIndex)) {
               this.nextMember = this.readNext();
               if (!this.ignoredName.equals(this.nextMember)) {
                  return true;
               }
            }

            return false;
         }
      }

      private String readNext() {
         try {
            Object key = ValuePropertiesCollection.INTEROP.readArrayElement(ValuePropertiesCollection.this.keys, this.currentIndex);
            String member = ValuePropertiesCollection.INTEROP.asString(key);
            this.currentIndex++;
            return member;
         } catch (ThreadDeath var3) {
            throw var3;
         } catch (Throwable var4) {
            throw DebugException.create(ValuePropertiesCollection.this.session, var4, ValuePropertiesCollection.this.language);
         }
      }

      public DebugValue next() {
         if (!this.hasNext()) {
            throw new NoSuchElementException();
         } else {
            String member;
            if (this.nextMember != null) {
               member = this.nextMember;
               this.nextMember = null;
            } else {
               member = this.readNext();
            }

            return new DebugValue.ObjectMemberValue(
               ValuePropertiesCollection.this.session,
               ValuePropertiesCollection.this.language,
               ValuePropertiesCollection.this.scope,
               ValuePropertiesCollection.this.object,
               member
            );
         }
      }

      @Override
      public void remove() {
         throw new UnsupportedOperationException("remove not supported.");
      }
   }
}
