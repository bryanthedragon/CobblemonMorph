package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.ast.BinaryOpExpression;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.DoubleValue;
import com.bedrockk.molang.runtime.value.MoValue;

public class EqualExpression extends BinaryOpExpression {
   public EqualExpression(Expression left, Expression right) {
      super(left, right);
   }

   @Override
   public String getSigil() {
      return "==";
   }

   @Override
   public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
      return new DoubleValue(this.left.evaluate(scope, environment).equals(this.right.evaluate(scope, environment)));
   }
}
