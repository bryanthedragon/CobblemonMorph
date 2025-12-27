package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer;

public final class TokenPosition {
   private final int startLineNumber;
   private final int endLineNumber;
   private final int startColumn;
   private final int endColumn;

   public TokenPosition(int startLineNumber, int endLineNumber, int startColumn, int endColumn) {
      this.startLineNumber = startLineNumber;
      this.endLineNumber = endLineNumber;
      this.startColumn = startColumn;
      this.endColumn = endColumn;
   }

   public int getStartLineNumber() {
      return this.startLineNumber;
   }

   public int getEndLineNumber() {
      return this.endLineNumber;
   }

   public int getStartColumn() {
      return this.startColumn;
   }

   public int getEndColumn() {
      return this.endColumn;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } 
      else if (!(o instanceof TokenPosition other)) {
         return false;
      } 
      else if (this.getStartLineNumber() != other.getStartLineNumber()) {
         return false;
      } 
      else if (this.getEndLineNumber() != other.getEndLineNumber()) {
         return false;
      } 
      else {
         return this.getStartColumn() != other.getStartColumn() ? false : this.getEndColumn() == other.getEndColumn();
      }
   }

   @Override
   @SuppressWarnings("unused")
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + this.getStartLineNumber();
      result = result * 59 + this.getEndLineNumber();
      result = result * 59 + this.getStartColumn();
      return result * 59 + this.getEndColumn();
   }

   @Override
   public String toString() {
      return "TokenPosition(startLineNumber="
         + this.getStartLineNumber()
         + ", endLineNumber="
         + this.getEndLineNumber()
         + ", startColumn="
         + this.getStartColumn()
         + ", endColumn="
         + this.getEndColumn()
         + ")";
   }
}
