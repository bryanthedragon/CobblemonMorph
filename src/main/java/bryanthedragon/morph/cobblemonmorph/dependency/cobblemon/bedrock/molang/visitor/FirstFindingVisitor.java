
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.visitor;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ExprTraverser;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ExprVisitor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import java.util.function.Predicate;

public class FirstFindingVisitor
implements ExprVisitor {
    private final Predicate<Expression> predicate;
    private Expression found;

    @Override
    public Object onVisit(Expression expression) {
        if (this.predicate.test(expression)) {
            this.found = expression;
            return ExprTraverser.ActionType.STOP_TRAVERSAL;
        }
        return null;
    }

    public Expression getFound() {
        return this.found;
    }

    public FirstFindingVisitor(Predicate<Expression> predicate) {
        this.predicate = predicate;
    }
}

