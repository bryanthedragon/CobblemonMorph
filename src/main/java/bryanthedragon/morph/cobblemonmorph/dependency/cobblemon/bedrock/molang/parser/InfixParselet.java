package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.parser;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.parser.tokenizer.Token;

public interface InfixParselet {
   Expression parse(MoLangParser var1, Token var2, Expression var3);

   default Precedence getPrecedence() {
      return Precedence.ANYTHING;
   }
}
