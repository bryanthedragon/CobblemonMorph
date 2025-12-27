package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.visitor;

import com.bedrockk.molang.ExprVisitor;
import com.bedrockk.molang.Expression;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FindingVisitor implements ExprVisitor {
   private final Predicate<Expression> predicate;
   private final List<Expression> foundExpressions = new ArrayList<>();

   @Override
   public Object onVisit(Expression expression) {
      if (this.predicate.test(expression)) {
         this.foundExpressions.add(expression);
      }

      return null;
   }

   public List<Expression> getFoundExpressions() {
      return this.foundExpressions;
   }

   public FindingVisitor(Predicate<Expression> predicate) {
      this.predicate = predicate;
   }
}
