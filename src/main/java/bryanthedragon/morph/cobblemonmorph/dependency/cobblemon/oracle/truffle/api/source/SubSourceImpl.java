package com.oracle.truffle.api.source;

import java.net.URI;
import java.net.URL;
import org.graalvm.polyglot.io.ByteSequence;

final class SubSourceImpl extends Source {
   private final SubSourceImpl.Key key;

   static Source create(Source base, int baseIndex, int length) {
      if (baseIndex >= 0 && length >= 0 && baseIndex + length <= base.getLength()) {
         return new SubSourceImpl(new SubSourceImpl.Key(base, baseIndex, length));
      } else {
         throw new IllegalArgumentException("text positions out of range");
      }
   }

   private SubSourceImpl(SubSourceImpl.Key key) {
      this.key = key;
   }

   @Override
   Source copy() {
      return new SubSourceImpl(this.key);
   }

   @Override
   public boolean hasBytes() {
      return this.key.base.hasBytes();
   }

   @Override
   public boolean hasCharacters() {
      return this.key.base.hasCharacters();
   }

   @Override
   public boolean isCached() {
      return this.key.base.isCached();
   }

   @Override
   protected Object getSourceId() {
      return this.key;
   }

   @Override
   Object getSourceKey() {
      return this.key;
   }

   @Override
   public String getName() {
      return this.key.base.getName();
   }

   @Override
   public String getPath() {
      return this.key.base.getPath();
   }

   @Override
   public URL getURL() {
      return this.key.base.getURL();
   }

   @Override
   public URI getOriginalURI() {
      return this.key.base.getURI();
   }

   @Override
   public ByteSequence getBytes() {
      return this.key.base.getBytes().subSequence(this.key.baseIndex, this.key.baseIndex + this.key.subLength);
   }

   @Override
   public CharSequence getCharacters() {
      return this.key.base.getCharacters().subSequence(this.key.baseIndex, this.key.baseIndex + this.key.subLength);
   }

   @Override
   public boolean isInternal() {
      return this.key.base.isInternal();
   }

   @Override
   public boolean isInteractive() {
      return this.key.base.isInteractive();
   }

   @Override
   public String getMimeType() {
      return this.key.base.getMimeType();
   }

   @Override
   public String getLanguage() {
      return this.key.base.getLanguage();
   }

   private static final class Key {
      final Source base;
      final int baseIndex;
      final int subLength;

      Key(Source base, int baseIndex, int length) {
         this.base = base;
         this.baseIndex = baseIndex;
         this.subLength = length;
      }

      @Override
      public boolean equals(Object obj) {
         if (!(obj instanceof SubSourceImpl.Key)) {
            return false;
         } else {
            SubSourceImpl.Key other = (SubSourceImpl.Key)obj;
            return this.base.equals(other.base) && this.baseIndex == other.baseIndex && this.subLength == other.subLength;
         }
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = 1;
         result = 31 * result + this.base.hashCode();
         result = 31 * result + this.baseIndex;
         return 31 * result + this.subLength;
      }
   }
}
