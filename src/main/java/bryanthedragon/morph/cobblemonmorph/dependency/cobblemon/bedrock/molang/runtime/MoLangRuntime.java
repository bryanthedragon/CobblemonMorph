
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.ExprTraverser;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.ArrayStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.ContextStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.VariableStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.visitor.ExprConnectingVisitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoLangRuntime {
    private final MoLangEnvironment environment = new MoLangEnvironment();

    public MoLangRuntime() {
        this.environment.getStructs().put("math", MoLangMath.LIBRARY);
        this.environment.getStructs().put("temp", new VariableStruct());
        this.environment.getStructs().put("variable", new VariableStruct());
        this.environment.getStructs().put("array", new ArrayStruct());
    }

    public MoValue execute(Expression expression) {
        return this.execute(Collections.singletonList(expression));
    }

    public MoValue execute(List<Expression> expressions) {
        return this.execute(expressions, new HashMap<String, MoValue>());
    }

    public MoValue execute(List<Expression> expressions, Map<String, MoValue> context) {
        ExprTraverser traverser = new ExprTraverser();
        traverser.getVisitors().add(new ExprConnectingVisitor());
        traverser.traverse(expressions);
        this.environment.getStructs().put("context", new ContextStruct(context));
        MoValue result = DoubleValue.ZERO;
        MoScope scope = new MoScope();
        for (Expression expression : new ArrayList<Expression>(expressions)) {
            if (scope.getReturnValue() != null) break;
            result = expression.evaluate(scope, this.environment);
        }
        this.environment.getStructs().get("temp").clear();
        this.environment.getStructs().remove("context");
        return scope.getReturnValue() != null ? scope.getReturnValue() : result;
    }

    public MoLangEnvironment getEnvironment() {
        return this.environment;
    }
}

