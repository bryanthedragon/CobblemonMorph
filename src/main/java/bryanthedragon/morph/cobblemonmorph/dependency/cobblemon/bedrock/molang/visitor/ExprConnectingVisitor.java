package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.visitor;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ExprVisitor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;

import java.util.LinkedList;
import java.util.List;

public class ExprConnectingVisitor
implements ExprVisitor {
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private final LinkedList<Expression> stack = new LinkedList();
    private Expression previous;

    @Override
    public void beforeTraverse(List<Expression> expressions) {
        this.stack.clear();
        this.previous = null;
    }

    @Override
    public Object onVisit(Expression expression) {
        if (!this.stack.isEmpty()) {
            expression.getAttributes().put("parent", this.stack.getLast());
        }
        if (this.previous != null && expression.getAttributes().get("parent") == this.previous.getAttributes().get("parent")) {
            expression.getAttributes().put("previous", this.previous);
            this.previous.getAttributes().put("next", expression);
        }
        this.stack.add(expression);
        return null;
    }

    @Override
    public void onLeave(Expression expression) {
        this.previous = expression;
        this.stack.pollLast();
    }
}

