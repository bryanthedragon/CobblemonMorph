package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang;

import java.util.List;

public interface ExprVisitor {
   default void beforeTraverse(List<Expression> expressions) {
   }

   Object onVisit(Expression var1);

   default void onLeave(Expression expression) {
   }

   default void afterTraverse(List<Expression> expressions) {
   }
}
