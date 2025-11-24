
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoScope;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import java.util.HashMap;
import java.util.Map;

public interface Expression {
    public static final Map<String, Object> attributes = new HashMap<String, Object>();

    public String getOriginalString();

    public void setOriginalString(String var1);

    default public Map<String, Object> getAttributes() {
        return attributes;
    }

    public MoValue evaluate(MoScope var1, MoLangEnvironment var2);

    default public void assign(MoScope scope, MoLangEnvironment environment, MoValue value2) {
        throw new RuntimeException("Cannot assign a value to " + this.getClass());
    }
}

