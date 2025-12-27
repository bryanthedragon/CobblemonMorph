package com.oracle.truffle.api.source;

final class SourceSectionLoaded extends SourceSection {
   final int charIndex;
   final int charLength;

   SourceSectionLoaded(Source source, int charIndex, int charLength) {
      super(source);
      this.charIndex = charIndex;
      this.charLength = charLength;
   }

   @Override
   public boolean isAvailable() {
      return true;
   }

   @Override
   public boolean hasLines() {
      return true;
   }

   @Override
   public boolean hasColumns() {
      return true;
   }

   @Override
   public boolean hasCharIndex() {
      return true;
   }

   @Override
   boolean isValid() {
      return this.charIndex + this.charLength <= this.getSource().getCharacters().length();
   }

   @Override
   public int getStartLine() {
      return !this.isValid() ? 1 : this.source.getLineNumber(this.getCharIndex());
   }

   @Override
   public int getStartColumn() {
      return !this.isValid() ? 1 : this.source.getColumnNumber(this.getCharIndex());
   }

   @Override
   public int getEndLine() {
      return !this.isValid() ? 1 : this.source.getLineNumber(this.getCharIndex() + Math.max(0, this.getCharLength() - 1));
   }

   @Override
   public int getEndColumn() {
      return !this.isValid() ? 1 : this.source.getColumnNumber(this.getCharIndex() + Math.max(0, this.getCharLength() - 1));
   }

   @Override
   public int getCharIndex() {
      return this.charIndex;
   }

   @Override
   public int getCharLength() {
      return this.charLength;
   }

   @Override
   public int getCharEndIndex() {
      return this.getCharIndex() + this.getCharLength();
   }

   @Override
   public CharSequence getCharacters() {
      return (CharSequence)(!this.isValid() ? "" : this.source.getCharacters().subSequence(this.getCharIndex(), this.getCharEndIndex()));
   }

   @Override
   public int hashCode() {
      if (!this.isAvailable()) {
         return System.identityHashCode(this);
      } else {
         int prime = 31;
         int result = 1;
         result = 31 * result + this.charIndex;
         result = 31 * result + this.charLength;
         return 31 * result + this.source.hashCode();
      }
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (obj.getClass() != SourceSectionLoaded.class) {
         return false;
      } else {
         SourceSectionLoaded other = (SourceSectionLoaded)obj;
         if (this.charIndex != other.charIndex) {
            return false;
         } else if (this.charLength != other.charLength) {
            return false;
         } else {
            if (this.source == null) {
               if (other.source != null) {
                  return false;
               }
            } else if (!this.source.equals(other.source)) {
               return false;
            }

            return true;
         }
      }
   }
}
