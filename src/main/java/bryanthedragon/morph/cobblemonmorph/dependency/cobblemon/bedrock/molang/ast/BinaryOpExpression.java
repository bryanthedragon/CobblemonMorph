package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ast;

import com.bedrockk.molang.Expression;
import com.bedrockk.molang.StringHolder;

public abstract class BinaryOpExpression extends StringHolder implements Expression {
   protected final Expression left;
   protected final Expression right;

   public abstract String getSigil();

   public Expression getLeft() {
      return this.left;
   }

   public Expression getRight() {
      return this.right;
   }

   public BinaryOpExpression(Expression left, Expression right) {
      this.left = left;
      this.right = right;
   }
}
