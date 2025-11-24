/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.text.StringsKt
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.effect.MobEffect
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.adapter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffect;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.effect.ShoulderEffectRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.effects.PotionBaseEffect;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/effect/adapter/ShoulderEffectAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/api/pokemon/effect/ShoulderEffect;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "typeOfT", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/pokemon/effect/ShoulderEffect;", "<init>", "()V", "common"})
public final class ShoulderEffectAdapter
implements JsonDeserializer<ShoulderEffect> {
    @NotNull
    public static final ShoulderEffectAdapter INSTANCE = new ShoulderEffectAdapter();

    private ShoulderEffectAdapter() {
    }

    @NotNull
    public ShoulderEffect deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @NotNull JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)typeOfT, (String)"typeOfT");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Pair pair = json.isJsonPrimitive() ? TuplesKt.to((Object)json.getAsString(), (Object)new JsonObject()) : TuplesKt.to((Object)json.getAsJsonObject().get("type").getAsString(), (Object)json.getAsJsonObject());
        String typeId = (String)pair.component1();
        JsonObject obj = (JsonObject)pair.component2();
        Intrinsics.checkNotNullExpressionValue((Object)typeId, (String)"typeId");
        Class<? extends ShoulderEffect> clazz = ShoulderEffectRegistry.INSTANCE.get(typeId);
        if (clazz == null) {
            ShoulderEffectAdapter $this$deserialize_u24lambda_u240 = this;
            boolean bl = false;
            try {
                ResourceLocation effectId = new ResourceLocation(StringsKt.replace$default((String)StringsKt.replace$default((String)typeId, (String)"-", (String)"_", (boolean)false, (int)4, null), (String)"slow_fall", (String)"slow_falling", (boolean)false, (int)4, null));
                Registry registry = BuiltInRegistries.f_256974_;
                MobEffect effect = (MobEffect)registry.m_7745_(effectId);
                if (effect != null) {
                    return new PotionBaseEffect(effect, 0, true, false, false);
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            throw new IllegalArgumentException("Cannot find shoulder effect with type '" + typeId + "'");
        }
        Class<? extends ShoulderEffect> effect = clazz;
        Object object = context.deserialize((JsonElement)obj, (Type)effect);
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.deserialize(obj, effect)");
        return (ShoulderEffect)object;
    }
}

