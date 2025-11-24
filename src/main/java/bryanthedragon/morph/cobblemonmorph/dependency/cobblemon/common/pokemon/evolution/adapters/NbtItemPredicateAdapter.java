/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  com.google.gson.JsonSerializer
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.advancements.critereon.NbtPredicate
 *  net.minecraft.world.item.Item
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.conditional.RegistryLikeCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.predicate.NbtItemPredicate;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.advancements.critereon.NbtPredicate;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u00012\b\u0012\u0004\u0012\u00020\u00020\u0003B\t\b\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018J'\u0010\n\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\u000e\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\rH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fR\u001c\u0010\u0011\u001a\n \u0010*\u0004\u0018\u00010\u00060\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0014\u001a\u00020\u00138\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00138\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0015\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/adapters/NbtItemPredicateAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/pokemon/evolution/predicate/NbtItemPredicate;", "Lcom/google/gson/JsonSerializer;", "Lcom/google/gson/JsonElement;", "jElement", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/pokemon/evolution/predicate/NbtItemPredicate;", "predicate", "Lcom/google/gson/JsonSerializationContext;", "serialize", "(Lcom/cobblemon/mod/common/pokemon/evolution/predicate/NbtItemPredicate;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "kotlin.jvm.PlatformType", "CONDITION_TYPE", "Ljava/lang/reflect/Type;", "", "ITEM", "Ljava/lang/String;", "NBT", "<init>", "()V", "common"})
public final class NbtItemPredicateAdapter
implements JsonDeserializer<NbtItemPredicate>,
JsonSerializer<NbtItemPredicate> {
    @NotNull
    public static final NbtItemPredicateAdapter INSTANCE = new NbtItemPredicateAdapter();
    @NotNull
    private static final String ITEM = "item";
    @NotNull
    private static final String NBT = "nbt";
    private static final Type CONDITION_TYPE;

    private NbtItemPredicateAdapter() {
    }

    @NotNull
    public NbtItemPredicate deserialize(@NotNull JsonElement jElement, @NotNull Type type, @NotNull JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter((Object)jElement, (String)"jElement");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (jElement.isJsonPrimitive()) {
            Object object = context.deserialize(jElement, CONDITION_TYPE);
            Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.deserialize(jElement, CONDITION_TYPE)");
            RegistryLikeCondition registryLikeCondition = (RegistryLikeCondition)object;
            NbtPredicate nbtPredicate = NbtPredicate.f_57471_;
            Intrinsics.checkNotNullExpressionValue((Object)nbtPredicate, (String)"ANY");
            return new NbtItemPredicate(registryLikeCondition, nbtPredicate);
        }
        JsonObject jObject = jElement.getAsJsonObject();
        RegistryLikeCondition itemCondition = (RegistryLikeCondition)context.deserialize(jObject.get(ITEM), CONDITION_TYPE);
        NbtPredicate nbtPredicate = NbtPredicate.m_57481_((JsonElement)jObject.get(NBT));
        Intrinsics.checkNotNullExpressionValue((Object)itemCondition, (String)"itemCondition");
        Intrinsics.checkNotNullExpressionValue((Object)nbtPredicate, (String)"nbtPredicate");
        return new NbtItemPredicate(itemCondition, nbtPredicate);
    }

    @NotNull
    public JsonElement serialize(@NotNull NbtItemPredicate predicate, @NotNull Type type, @NotNull JsonSerializationContext context) {
        JsonObject jsonObject;
        Intrinsics.checkNotNullParameter((Object)predicate, (String)"predicate");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        JsonElement serializedItemCondition = context.serialize(predicate.getItem(), CONDITION_TYPE);
        if (Intrinsics.areEqual((Object)predicate.getNbt(), (Object)NbtPredicate.f_57471_)) {
            Intrinsics.checkNotNullExpressionValue((Object)serializedItemCondition, (String)"serializedItemCondition");
            return serializedItemCondition;
        }
        JsonObject $this$serialize_u24lambda_u240 = jsonObject = new JsonObject();
        boolean bl = false;
        $this$serialize_u24lambda_u240.add(ITEM, serializedItemCondition);
        $this$serialize_u24lambda_u240.add(NBT, predicate.getNbt().m_57476_());
        return (JsonElement)jsonObject;
    }

    static {
        Type[] typeArray = new Type[]{Item.class};
        CONDITION_TYPE = TypeToken.getParameterized((Type)((Type)((Object)RegistryLikeCondition.class)), (Type[])typeArray).getType();
    }
}

