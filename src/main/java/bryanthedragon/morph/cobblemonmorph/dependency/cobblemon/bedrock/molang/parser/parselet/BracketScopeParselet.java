
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.StatementExpression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.MoLangParser;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.Precedence;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.PrefixParselet;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.Token;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.tokenizer.TokenType;
import java.util.ArrayList;

public class BracketScopeParselet
implements PrefixParselet {
    @Override
    public Expression parse(MoLangParser parser, Token token) {
        ArrayList<Expression> exprs = new ArrayList<Expression>();
        if (!parser.matchToken(TokenType.CURLY_BRACKET_RIGHT)) {
            while (!parser.matchToken(TokenType.CURLY_BRACKET_RIGHT, false)) {
                exprs.add(parser.parseExpression(Precedence.SCOPE));
                if (parser.matchToken(TokenType.SEMICOLON)) continue;
            }
            parser.consumeToken(TokenType.CURLY_BRACKET_RIGHT);
        }
        return new StatementExpression(exprs.toArray(new Expression[0]));
    }
}

