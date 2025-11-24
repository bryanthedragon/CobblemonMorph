/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnLoader;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.AppendageCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.BasicSpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ+\u0010\t\u001a\u0006\u0012\u0002\b\u00030\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/SpawningConditionAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nSpawningConditionAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpawningConditionAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SpawningConditionAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,64:1\n1855#2,2:65\n*S KotlinDebug\n*F\n+ 1 SpawningConditionAdapter.kt\ncom/cobblemon/mod/common/util/adapters/SpawningConditionAdapter\n*L\n53#1:65,2\n*E\n"})
public final class SpawningConditionAdapter
implements JsonDeserializer<SpawningCondition<?>> {
    @NotNull
    public static final SpawningConditionAdapter INSTANCE = new SpawningConditionAdapter();

    private SpawningConditionAdapter() {
    }

    @NotNull
    public SpawningCondition<?> deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        SpawningCondition spawningCondition;
        String name;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        JsonElement jsonElement = json.getAsJsonObject().get("type");
        String string = name = jsonElement != null ? jsonElement.getAsString() : null;
        if (name == null) {
            SpawningCondition spawningCondition2 = SpawnLoader.INSTANCE.getDeserializingConditionClass() == null ? (SpawningCondition)ctx.deserialize(json, (Type)((Object)BasicSpawningCondition.class)) : (SpawningCondition)ctx.deserialize(json, (Type)SpawnLoader.INSTANCE.getDeserializingConditionClass());
            spawningCondition = spawningCondition2;
            Intrinsics.checkNotNullExpressionValue((Object)spawningCondition2, (String)"{\n            if (deseri\u2026)\n            }\n        }");
        } else {
            Class<? extends SpawningCondition<?>> clazz = SpawningCondition.Companion.getByName(name);
            if (clazz == null) {
                throw new IllegalStateException("Unrecognized spawning condition type: " + name);
            }
            Object object = ctx.deserialize(json, (Type)clazz);
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"{\n                ctx.de\u2026son, clazz)\n            }");
            spawningCondition = (SpawningCondition)((Object)((Void)object));
        }
        SpawningCondition condition2 = spawningCondition;
        List<Class<? extends AppendageCondition>> appendageClasses = AppendageCondition.Companion.getAppendages(condition2);
        Iterable $this$forEach$iv = appendageClasses;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Class it = (Class)element$iv;
            boolean bl = false;
            try {
                List<AppendageCondition> list = condition2.getAppendages();
                Object object = ctx.deserialize(json, (Type)it);
                Intrinsics.checkNotNullExpressionValue((Object)object, (String)"ctx.deserialize(json, it)");
                list.add((AppendageCondition)object);
            }
            catch (Exception e) {
                Cobblemon.INSTANCE.getLOGGER().error("Unable to deserialize appendage condition of type: " + it.getSimpleName());
                throw e;
            }
        }
        return condition2;
    }
}

