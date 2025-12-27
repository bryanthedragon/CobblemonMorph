package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.ast.TernaryExpression;
import com.bedrockk.molang.parser.InfixParselet;
import com.bedrockk.molang.parser.MoLangParser;
import com.bedrockk.molang.parser.Precedence;
import com.bedrockk.molang.parser.tokenizer.Token;
import com.bedrockk.molang.parser.tokenizer.TokenType;

public class TernaryParselet implements InfixParselet {
   @Override
   public Expression parse(MoLangParser parser, Token token, Expression leftExpr) {
      if (parser.matchToken(TokenType.COLON)) {
         return new TernaryExpression(leftExpr, null, parser.parseExpression(this.getPrecedence()));
      } else {
         Expression thenExpr = parser.parseExpression(this.getPrecedence());
         return !parser.matchToken(TokenType.COLON)
            ? new TernaryExpression(leftExpr, thenExpr, null)
            : new TernaryExpression(leftExpr, thenExpr, parser.parseExpression(this.getPrecedence()));
      }
   }

   @Override
   public Precedence getPrecedence() {
      return Precedence.CONDITIONAL;
   }
}
