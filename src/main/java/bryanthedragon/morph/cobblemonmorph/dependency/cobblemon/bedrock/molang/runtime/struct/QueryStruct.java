package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;

import java.util.HashMap;
import java.util.Iterator;
import java.util.function.Function;

public class QueryStruct
implements MoStruct {
    public final HashMap<String, Function<MoParams, Object>> functions;

    @SuppressWarnings("unused")
    public MoValue get(Iterator<String> names, MoParams params) {
        MoParams currentParams;
        String key = names.next();
        Function<MoParams, Object> func = this.functions.get(key);
        MoParams moParams = currentParams = names.hasNext() ? MoParams.EMPTY : params;
        if (func != null) {
            Object result = func.apply(currentParams);
            if (result instanceof MoStruct && names.hasNext()) {
                return ((MoStruct)result).get(names, params);
            }
            return MoValue.of(result);
        }
        return null;
    }

    public void set(Iterator<String> names, MoValue value2) {
        throw new RuntimeException("Cannot set a value in query struct");
    }

    public void clear() {
        this.functions.clear();
    }

    public QueryStruct addFunction(String name, Function<MoParams, Object> func) {
        this.functions.put(name, func);
        return this;
    }

    public HashMap<String, Function<MoParams, Object>> getFunctions() {
        return this.functions;
    }

    public QueryStruct(HashMap<String, Function<MoParams, Object>> functions2) {
        this.functions = functions2;
    }
}

