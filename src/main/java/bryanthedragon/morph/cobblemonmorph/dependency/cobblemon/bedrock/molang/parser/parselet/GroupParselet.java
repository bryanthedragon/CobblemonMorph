
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.MoLangParser;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.PrefixParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.Token;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.TokenType;

public class GroupParselet
implements PrefixParselet {
    @Override
    public Expression parse(MoLangParser parser, Token token) {
        Expression expr = parser.parseExpression();
        parser.consumeToken(TokenType.BRACKET_RIGHT);
        return expr;
    }
}

