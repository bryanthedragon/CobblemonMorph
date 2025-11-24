
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.visitor;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ExprVisitor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class FindingVisitor
implements ExprVisitor {
    private final Predicate<Expression> predicate;
    private final List<Expression> foundExpressions = new ArrayList<Expression>();

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

