package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast.binaryop;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.ast.BinaryOpExpression;
import com.bedrockk.molang.runtime.MoLangEnvironment;
import com.bedrockk.molang.runtime.MoScope;
import com.bedrockk.molang.runtime.value.MoValue;

public class ArrowExpression extends BinaryOpExpression {
   public ArrowExpression(Expression left, Expression right) {
      super(left, right);
   }

   @Override
   public String getSigil() {
      return "->";
   }

   @Override
   public MoValue evaluate(MoScope scope, MoLangEnvironment environment) {
      Object leftEnv = this.left.evaluate(scope, environment);
      return leftEnv instanceof MoLangEnvironment ? this.right.evaluate(scope, (MoLangEnvironment)leftEnv) : null;
   }
}
