package com.oracle.truffle.api.source;

abstract class SourceSectionUnloaded extends SourceSection {
   SourceSectionUnloaded(Source source) {
      super(source);
   }

   @Override
   public final boolean isAvailable() {
      return true;
   }

   @Override
   final boolean isValid() {
      return true;
   }

   @Override
   public final CharSequence getCharacters() {
      return "";
   }

   static final class Indexed extends SourceSectionUnloaded {
      final int charIndex;
      final int charLength;

      Indexed(Source source, int charIndex, int charLength) {
         super(source);
         this.charIndex = charIndex;
         this.charLength = charLength;
      }

      @Override
      public boolean hasLines() {
         return false;
      }

      @Override
      public boolean hasColumns() {
         return false;
      }

      @Override
      public boolean hasCharIndex() {
         return true;
      }

      @Override
      public int getStartLine() {
         return 1;
      }

      @Override
      public int getStartColumn() {
         return 1;
      }

      @Override
      public int getEndLine() {
         return 1;
      }

      @Override
      public int getEndColumn() {
         return 1;
      }

      @Override
      public int getCharIndex() {
         return this.charIndex;
      }

      @Override
      public int getCharEndIndex() {
         return this.charIndex + this.charLength;
      }

      @Override
      public int getCharLength() {
         return this.charLength;
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = 1;
         result = 31 * result + this.charIndex;
         result = 31 * result + this.charLength;
         return 31 * result + this.getSource().hashCode();
      }

      @Override
      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (!(obj instanceof SourceSectionUnloaded.Indexed)) {
            return false;
         } else {
            SourceSectionUnloaded.Indexed other = (SourceSectionUnloaded.Indexed)obj;
            if (this.charIndex == other.charIndex && this.charLength == other.charLength) {
               if (this.getSource() == null) {
                  if (other.getSource() != null) {
                     return false;
                  }
               } else if (!this.getSource().equals(other.getSource())) {
                  return false;
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }

   static final class Lines extends SourceSectionUnloaded {
      final int startLine;
      final int endLine;

      Lines(Source source, int startLine, int endLine) {
         super(source);
         this.startLine = startLine;
         this.endLine = endLine;
      }

      @Override
      public boolean hasLines() {
         return true;
      }

      @Override
      public boolean hasColumns() {
         return false;
      }

      @Override
      public boolean hasCharIndex() {
         return false;
      }

      @Override
      public int getStartLine() {
         return this.startLine;
      }

      @Override
      public int getStartColumn() {
         return 1;
      }

      @Override
      public int getEndLine() {
         return this.endLine;
      }

      @Override
      public int getEndColumn() {
         return 1;
      }

      @Override
      public int getCharIndex() {
         return 0;
      }

      @Override
      public int getCharEndIndex() {
         return 0;
      }

      @Override
      public int getCharLength() {
         return 0;
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = 1;
         result = 31 * result + this.startLine;
         result = 31 * result + this.endLine;
         return 31 * result + this.getSource().hashCode();
      }

      @Override
      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (!(obj instanceof SourceSectionUnloaded.Lines)) {
            return false;
         } else {
            SourceSectionUnloaded.Lines other = (SourceSectionUnloaded.Lines)obj;
            if (this.startLine == other.startLine && this.endLine == other.endLine) {
               if (this.getSource() == null) {
                  if (other.getSource() != null) {
                     return false;
                  }
               } else if (!this.getSource().equals(other.getSource())) {
                  return false;
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }

   static final class LinesAndColumns extends SourceSectionUnloaded {
      final int startLine;
      final int startColumn;
      final int endLine;
      final int endColumn;

      LinesAndColumns(Source source, int startLine, int startColumn, int endLine, int endColumn) {
         super(source);
         this.startLine = startLine;
         this.startColumn = startColumn;
         this.endLine = endLine;
         this.endColumn = endColumn;
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
         return false;
      }

      @Override
      public int getStartLine() {
         return this.startLine;
      }

      @Override
      public int getStartColumn() {
         return this.startColumn;
      }

      @Override
      public int getEndLine() {
         return this.endLine;
      }

      @Override
      public int getEndColumn() {
         return this.endColumn;
      }

      @Override
      public int getCharIndex() {
         return 0;
      }

      @Override
      public int getCharEndIndex() {
         return 0;
      }

      @Override
      public int getCharLength() {
         return 0;
      }

      @Override
      public int hashCode() {
         int prime = 31;
         int result = 1;
         result = 31 * result + this.startLine;
         result = 31 * result + this.startColumn;
         result = 31 * result + this.endLine;
         result = 31 * result + this.endColumn;
         return 31 * result + this.getSource().hashCode();
      }

      @Override
      public boolean equals(Object obj) {
         if (this == obj) {
            return true;
         } else if (obj == null) {
            return false;
         } else if (!(obj instanceof SourceSectionUnloaded.LinesAndColumns)) {
            return false;
         } else {
            SourceSectionUnloaded.LinesAndColumns other = (SourceSectionUnloaded.LinesAndColumns)obj;
            if (this.startLine == other.startLine
               && this.startColumn == other.startColumn
               && this.endLine == other.endLine
               && this.endColumn == other.endColumn) {
               if (this.getSource() == null) {
                  if (other.getSource() != null) {
                     return false;
                  }
               } else if (!this.getSource().equals(other.getSource())) {
                  return false;
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }
}
