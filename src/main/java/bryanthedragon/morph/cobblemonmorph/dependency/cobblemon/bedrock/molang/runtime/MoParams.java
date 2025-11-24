package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.MoStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.DoubleValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;

import java.util.ArrayList;
import java.util.List;

public class MoParams {
    public static final MoParams EMPTY = new MoParams(new ArrayList<MoValue>());
    private final List<MoValue> params;

    public MoParams(List<MoValue> params) {
        this.params = params;
    }

    @SuppressWarnings("unchecked")
    public <T extends MoValue> T get(int index) {
        return (T)this.params.get(index);
    }

    public boolean contains(int index) {
        return this.params.size() >= index + 1;
    }

    public int getInt(int index) {
        return (int)this.getDouble(index);
    }

    public double getDouble(int index) {
        return ((DoubleValue)this.get(index)).asDouble();
    }

    public MoStruct getStruct(int index) {
        return (MoStruct)this.get(index);
    }

    public String getString(int index) {
        return ((StringValue)this.get(index)).asString();
    }

    public MoLangEnvironment getEnv(int index) {
        return (MoLangEnvironment)this.get(index);
    }

    public List<MoValue> getParams() {
        return this.params;
    }
}

