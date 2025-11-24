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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Token)) {
            return false;
        }
        Token other = (Token)o;
        TokenType this$type = this.getType();
        TokenType other$type = other.getType();
        if (this$type == null ? other$type != null : !((Object)((Object)this$type)).equals((Object)other$type)) {
            return false;
        }
        String this$text = this.getText();
        String other$text = other.getText();
        if (this$text == null ? other$text != null : !this$text.equals(other$text)) {
            return false;
        }
        TokenPosition this$position = this.getPosition();
        TokenPosition other$position = other.getPosition();
        return !(this$position == null ? other$position != null : !((Object)this$position).equals(other$position));
    }

    @SuppressWarnings("unused")
    public int hashCode() {
        int PRIME = 59;
        int result = 1;
        TokenType $type = this.getType();
        result = result * 59 + ($type == null ? 43 : ((Object)((Object)$type)).hashCode());
        String $text = this.getText();
        result = result * 59 + ($text == null ? 43 : $text.hashCode());
        TokenPosition $position = this.getPosition();
        result = result * 59 + ($position == null ? 43 : ((Object)$position).hashCode());
        return result;
    }

    public String toString() {
        return "Token(type=" + this.getType() + ", text=" + this.getText() + ", position=" + this.getPosition() + ")";
    }

    public Token(TokenType type, String text, TokenPosition position) {
        this.type = type;
        this.text = text;
        this.position = position;
    }
}

