package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.utils;

import com.bedrockk.molang.Expression;

public final class ExprUtils {
   public static Expression getExprAttribute(Expression expression, String attributeName) {
      Object parent = expression.getAttributes().get(attributeName);
      return parent instanceof Expression ? (Expression)parent : null;
   }

   public static Expression parent(Expression expression) {
      return getExprAttribute(expression, "parent");
   }

   public static Expression next(Expression expression) {
      return getExprAttribute(expression, "next");
   }

   public static Expression previous(Expression expression) {
      return getExprAttribute(expression, "previous");
   }
}
