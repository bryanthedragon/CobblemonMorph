package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.Token;

public interface InfixParselet {
    public Expression parse(MoLangParser var1, Token var2, Expression var3);

    default public Precedence getPrecedence() {
        return Precedence.ANYTHING;
    }
}

