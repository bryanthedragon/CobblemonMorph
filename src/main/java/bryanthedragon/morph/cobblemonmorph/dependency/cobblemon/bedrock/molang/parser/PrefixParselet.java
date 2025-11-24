package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.Token;

public interface PrefixParselet {
    public Expression parse(MoLangParser var1, Token var2);
}

