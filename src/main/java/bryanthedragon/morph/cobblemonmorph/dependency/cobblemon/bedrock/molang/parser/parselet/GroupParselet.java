package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser.parselet;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.parser.MoLangParser;
import com.bedrockk.molang.parser.PrefixParselet;
import com.bedrockk.molang.parser.tokenizer.Token;
import com.bedrockk.molang.parser.tokenizer.TokenType;

public class GroupParselet implements PrefixParselet {
   @Override
   public Expression parse(MoLangParser parser, Token token) {
      Expression expr = parser.parseExpression();
      parser.consumeToken(TokenType.BRACKET_RIGHT);
      return expr;
   }
}
