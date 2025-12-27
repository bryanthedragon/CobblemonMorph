package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer;

public final class Token {
   private final TokenType type;
   private final String text;
   private final TokenPosition position;

   public Token(TokenType tokenType, TokenPosition position) {
      this.type = tokenType;
      this.text = tokenType.getSymbol();
      this.position = position;
   }

   public TokenType getType() {
      return this.type;
   }

   public String getText() {
      return this.text;
   }

   public TokenPosition getPosition() {
      return this.position;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } 
      else if (!(o instanceof Token other)) {
         return false;
      } 
      else {
         Object this$type = this.getType();
         Object other$type = other.getType();
         if (this$type == null ? other$type == null : this$type.equals(other$type)) {
            Object this$text = this.getText();
            Object other$text = other.getText();
            if (this$text == null ? other$text == null : this$text.equals(other$text)) {
               Object this$position = this.getPosition();
               Object other$position = other.getPosition();
               return this$position == null ? other$position == null : this$position.equals(other$position);
            } 
            else {
               return false;
            }
         } 
         else {
            return false;
         }
      }
   }

   @Override
   @SuppressWarnings("unused")
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $type = this.getType();
      result = result * 59 + ($type == null ? 43 : $type.hashCode());
      Object $text = this.getText();
      result = result * 59 + ($text == null ? 43 : $text.hashCode());
      Object $position = this.getPosition();
      return result * 59 + ($position == null ? 43 : $position.hashCode());
   }

   @Override
   public String toString() {
      return "Token(type=" + this.getType() + ", text=" + this.getText() + ", position=" + this.getPosition() + ")";
   }

   public Token(TokenType type, String text, TokenPosition position) {
      this.type = type;
      this.text = text;
      this.position = position;
   }
}
