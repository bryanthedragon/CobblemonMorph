package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer;

public class TokenIterator {
    private final String code;
    private int index = 0;
    private int currentLine = 0;
    private int lastStep = 0;
    private int lastStepLine = 0;

    public boolean hasNext() {
        return this.index < this.code.length();
    }

    public Token next() {
        while (this.index < this.code.length()) {
            TokenType token;
            if (this.code.length() > this.index + 1 && (token = TokenType.bySymbol(this.code.substring(this.index, this.index + 2))) != null) {
                this.index += 2;
                return new Token(token, this.getPosition());
            }
            String expr = this.getStringAt(this.index);
            TokenType tokenType = TokenType.bySymbol(expr);
            if (tokenType != null) {
                ++this.index;
                return new Token(tokenType, this.getPosition());
            }
            if (expr.equals("'")) {
                int stringLength;
                int stringStart = this.index;
                String previousCharacter = "";
                for (stringLength = this.index + 1; stringLength < this.code.length() && (!this.getStringAt(stringLength).equals("'") || previousCharacter.equals("\\")); ++stringLength) {
                    previousCharacter = this.getStringAt(stringLength);
                }
                this.index = ++stringLength;
                return new Token(TokenType.STRING, this.code.substring(stringStart + 1, stringLength - 1).replace("\\'", "'"), this.getPosition());
            }
            if (Character.isLetter(expr.charAt(0))) {
                int nameLength;
                for (nameLength = this.index + 1; nameLength < this.code.length() && (Character.isLetterOrDigit(this.getStringAt(nameLength).charAt(0)) || this.getStringAt(nameLength).equals("_") || this.getStringAt(nameLength).equals(".")); ++nameLength) {
                }
                String value2 = this.code.substring(this.index, nameLength).toLowerCase();
                TokenType token2 = TokenType.bySymbol(value2);
                if (token2 == null) {
                    token2 = TokenType.NAME;
                }
                this.index = nameLength;
                return new Token(token2, value2, this.getPosition());
            }
            if (Character.isDigit(expr.charAt(0))) {
                int numLength;
                int numStart = this.index;
                boolean hasDecimal = false;
                for (numLength = this.index + 1; numLength < this.code.length() && (Character.isDigit(this.getStringAt(numLength).charAt(0)) || this.getStringAt(numLength).equals(".") && !hasDecimal); ++numLength) {
                    if (!this.getStringAt(numLength).equals(".")) continue;
                    hasDecimal = true;
                }
                this.index = numLength;
                return new Token(TokenType.NUMBER, this.code.substring(numStart, numLength), this.getPosition());
            }
            if (expr.equals("\n") || expr.equals("\r")) {
                ++this.currentLine;
            }
            ++this.index;
        }
        return new Token(TokenType.EOF, this.getPosition());
    }

    public void step() {
        this.lastStep = this.index;
        this.lastStepLine = this.currentLine;
    }

    public TokenPosition getPosition() {
        return new TokenPosition(this.lastStepLine, this.currentLine, this.lastStep, this.index);
    }

    public String getStringAt(String str, int i) {
        return str.substring(i, i + 1);
    }

    public String getStringAt(int i) {
        return this.code.substring(i, i + 1);
    }

    public String getString() {
        return this.code;
    }

    public TokenIterator(String code) {
        this.code = code;
    }
}

