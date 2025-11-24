/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.AbilityPool;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbility;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.abilities.PotentialAbilityType;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/util/adapters/AbilityPoolAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/abilities/AbilityPool;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "ctx", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/abilities/AbilityPool;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nAbilityPoolAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AbilityPoolAdapter.kt\ncom/cobblemon/mod/common/util/adapters/AbilityPoolAdapter\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,36:1\n1855#2:37\n1856#2:39\n1#3:38\n*S KotlinDebug\n*F\n+ 1 AbilityPoolAdapter.kt\ncom/cobblemon/mod/common/util/adapters/AbilityPoolAdapter\n*L\n29#1:37\n29#1:39\n*E\n"})
public final class AbilityPoolAdapter
implements JsonDeserializer<AbilityPool> {
    @NotNull
    public static final AbilityPoolAdapter INSTANCE = new AbilityPoolAdapter();

    private AbilityPoolAdapter() {
    }

    @NotNull
    public AbilityPool deserialize(@NotNull JsonElement json, @NotNull Type type, @NotNull JsonDeserializationContext ctx) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)ctx, (String)"ctx");
        AbilityPool pool = new AbilityPool();
        JsonArray jsonArray = json.getAsJsonArray();
        Intrinsics.checkNotNullExpressionValue((Object)jsonArray, (String)"json.asJsonArray");
        Iterable $this$forEach$iv = (Iterable)jsonArray;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            PotentialAbility potentialAbility;
            block3: {
                JsonElement element = (JsonElement)element$iv;
                boolean bl = false;
                for (PotentialAbilityType it : (Iterable)PotentialAbility.Companion.getTypes()) {
                    boolean bl2 = false;
                    Intrinsics.checkNotNullExpressionValue((Object)element, (String)"element");
                    Object t = it.parseFromJSON(element);
                    if (t == null) continue;
                    potentialAbility = t;
                    break block3;
                }
                potentialAbility = null;
            }
            if (potentialAbility == null) {
                throw new IllegalStateException("Failed to interpret ability: " + json);
            }
            PotentialAbility potentialAbility2 = potentialAbility;
            pool.add(potentialAbility2.getPriority(), potentialAbility2);
        }
        return pool;
    }
}

