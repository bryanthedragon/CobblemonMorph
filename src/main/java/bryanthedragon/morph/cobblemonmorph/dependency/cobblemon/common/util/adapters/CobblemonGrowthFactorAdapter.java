/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  kotlin.Metadata
 *  kotlin.jvm.JvmClassMappingKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.reflect.KClass
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthFactor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.adapter.GrowthFactorAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.berry.BiomeDownfallGrowthFactor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.berry.BiomeTemperatureGrowthFactor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.berry.PreferredBiomeGrowthFactor;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ'\u0010\u000f\u001a\u00020\u000e2\u000e\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u000b2\u0006\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J'\u0010\u0013\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0012H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0016\u001a\u00020\u00158\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0017R@\u0010\u001a\u001a.\u0012\u0004\u0012\u00020\u0015\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\b0\u000b0\u0018j\u0016\u0012\u0004\u0012\u00020\u0015\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\b0\u000b`\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/util/adapters/CobblemonGrowthFactorAdapter;", "Lcom/cobblemon/mod/common/api/berry/adapter/GrowthFactorAdapter;", "Lcom/google/gson/JsonElement;", "jElement", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "context", "Lcom/cobblemon/mod/common/api/berry/GrowthFactor;", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/berry/GrowthFactor;", "Lkotlin/reflect/KClass;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "", "register", "(Lkotlin/reflect/KClass;Lnet/minecraft/resources/ResourceLocation;)V", "factor", "Lcom/google/gson/JsonSerializationContext;", "serialize", "(Lcom/cobblemon/mod/common/api/berry/GrowthFactor;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "", "VARIANT", "Ljava/lang/String;", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "types", "Ljava/util/HashMap;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonGrowthFactorAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonGrowthFactorAdapter.kt\ncom/cobblemon/mod/common/util/adapters/CobblemonGrowthFactorAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,62:1\n1#2:63\n*E\n"})
public final class CobblemonGrowthFactorAdapter
implements GrowthFactorAdapter {
    @NotNull
    public static final CobblemonGrowthFactorAdapter INSTANCE = new CobblemonGrowthFactorAdapter();
    @NotNull
    private static final String VARIANT = "variant";
    @NotNull
    private static final HashMap<String, KClass<? extends GrowthFactor>> types = new HashMap();

    private CobblemonGrowthFactorAdapter() {
    }

    @Override
    public void register(@NotNull KClass<? extends GrowthFactor> type, @NotNull ResourceLocation identifier) {
        Intrinsics.checkNotNullParameter(type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        KClass<? extends GrowthFactor> existing = types.put(identifier.toString(), type);
        if (existing != null) {
            Cobblemon.INSTANCE.getLOGGER().debug("Replaced {} under ID {} with {} in the {}", (Object)Reflection.getOrCreateKotlinClass(existing.getClass()).getQualifiedName(), (Object)identifier.toString(), (Object)type.getQualifiedName(), (Object)Reflection.getOrCreateKotlinClass(this.getClass()).getQualifiedName());
        }
    }

    @NotNull
    public GrowthFactor deserialize(@NotNull JsonElement jElement, @NotNull Type type, @NotNull JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter((Object)jElement, (String)"jElement");
        Intrinsics.checkNotNullParameter((Object)type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        JsonObject json = jElement.getAsJsonObject();
        String string = json.get(VARIANT).getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.get(VARIANT).asString");
        String string2 = string.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        String variant = string2;
        KClass<? extends GrowthFactor> kClass = types.get(variant);
        if (kClass == null) {
            throw new IllegalArgumentException("Cannot resolve type for variant " + variant);
        }
        KClass<? extends GrowthFactor> registeredType = kClass;
        Object object = context.deserialize((JsonElement)json, (Type)JvmClassMappingKt.getJavaClass(registeredType));
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.deserialize(json, registeredType.java)");
        return (GrowthFactor)object;
    }

    @NotNull
    public JsonElement serialize(@NotNull GrowthFactor factor, @NotNull Type type, @NotNull JsonSerializationContext context) {
        Object object;
        Object v1;
        JsonObject json;
        block2: {
            Intrinsics.checkNotNullParameter((Object)factor, (String)"factor");
            Intrinsics.checkNotNullParameter((Object)type, (String)"type");
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            json = context.serialize((Object)factor).getAsJsonObject();
            Set<Map.Entry<String, KClass<? extends GrowthFactor>>> set2 = types.entrySet();
            Intrinsics.checkNotNullExpressionValue(set2, (String)"types.entries");
            Iterable iterable = set2;
            for (Object t : iterable) {
                Map.Entry it = (Map.Entry)t;
                boolean bl = false;
                if (!Intrinsics.areEqual(it.getValue(), (Object)Reflection.getOrCreateKotlinClass(factor.getClass()))) continue;
                v1 = t;
                break block2;
            }
            v1 = null;
        }
        if ((object = (Map.Entry)v1) == null || (object = (String)object.getKey()) == null) {
            throw new IllegalArgumentException("Cannot resolve variant for type " + Reflection.getOrCreateKotlinClass(factor.getClass()).getQualifiedName());
        }
        Object variant = object;
        json.addProperty(VARIANT, (String)variant);
        Intrinsics.checkNotNullExpressionValue((Object)json, (String)"json");
        return (JsonElement)json;
    }

    static {
        INSTANCE.register((KClass<? extends GrowthFactor>)Reflection.getOrCreateKotlinClass(BiomeDownfallGrowthFactor.class), BiomeDownfallGrowthFactor.Companion.getID());
        INSTANCE.register((KClass<? extends GrowthFactor>)Reflection.getOrCreateKotlinClass(BiomeTemperatureGrowthFactor.class), BiomeTemperatureGrowthFactor.Companion.getID());
        INSTANCE.register((KClass<? extends GrowthFactor>)Reflection.getOrCreateKotlinClass(PreferredBiomeGrowthFactor.class), PreferredBiomeGrowthFactor.Companion.getID());
    }
}

