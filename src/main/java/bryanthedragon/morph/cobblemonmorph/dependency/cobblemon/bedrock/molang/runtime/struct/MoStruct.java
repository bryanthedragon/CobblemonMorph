
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import java.util.Iterator;

public interface MoStruct
extends MoValue {
    public void set(Iterator<String> var1, MoValue var2);

    public MoValue get(Iterator<String> var1, MoParams var2);

    public void clear();

    @Override
    default public MoStruct value() {
        return this;
    }
}

