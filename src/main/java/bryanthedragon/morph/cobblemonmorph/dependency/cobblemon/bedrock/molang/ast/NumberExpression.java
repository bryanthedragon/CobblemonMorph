package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;

public class NumberExpression extends StringHolder implements Expression {
   double number;

   public NumberExpression(double number) {
      this.number = number;
      this.setOriginalString(String.valueOf(number));
   }

   @Override
   public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
      return new DoubleValue(this.number);
   }
}
