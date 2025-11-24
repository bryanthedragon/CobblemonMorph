package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang;

import java.util.List;

public interface ExprVisitor {
    default public void beforeTraverse(List<Expression> expressions) {
    }

    public Object onVisit(Expression var1);

    default public void onLeave(Expression expression) {
    }

    default public void afterTraverse(List<Expression> expressions) {
    }
}

