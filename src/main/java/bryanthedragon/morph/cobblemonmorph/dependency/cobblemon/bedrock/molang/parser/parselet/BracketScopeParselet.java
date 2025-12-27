package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.ast.StatementExpression;
import com.bedrockk.molang.parser.MoLangParser;
import com.bedrockk.molang.parser.Precedence;
import com.bedrockk.molang.parser.PrefixParselet;
import com.bedrockk.molang.parser.tokenizer.Token;
import com.bedrockk.molang.parser.tokenizer.TokenType;
import java.util.ArrayList;
import java.util.List;

public class BracketScopeParselet implements PrefixParselet {
   @Override
   public Expression parse(MoLangParser parser, Token token) {
      List<Expression> exprs = new ArrayList<>();
      if (!parser.matchToken(TokenType.CURLY_BRACKET_RIGHT)) {
         while (true) {
            if (!parser.matchToken(TokenType.CURLY_BRACKET_RIGHT, false)) {
               exprs.add(parser.parseExpression(Precedence.SCOPE));
               if (parser.matchToken(TokenType.SEMICOLON)) {
                  continue;
               }
            }

            parser.consumeToken(TokenType.CURLY_BRACKET_RIGHT);
            break;
         }
      }

      return new StatementExpression(exprs.toArray(new Expression[0]));
   }
}
