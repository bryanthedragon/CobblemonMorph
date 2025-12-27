package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.visitor;

import com.bedrockk.molang.ExprTraverser;
import com.bedrockk.molang.ExprVisitor;
import com.bedrockk.molang.Expression;
import java.util.function.Predicate;

public class FirstFindingVisitor implements ExprVisitor {
   private final Predicate<Expression> predicate;
   private Expression found;

   @Override
   public Object onVisit(Expression expression) {
      if (this.predicate.test(expression)) {
         this.found = expression;
         return ExprTraverser.ActionType.STOP_TRAVERSAL;
      } else {
         return null;
      }
   }

   public Expression getFound() {
      return this.found;
   }

   public FirstFindingVisitor(Predicate<Expression> predicate) {
      this.predicate = predicate;
   }
}
