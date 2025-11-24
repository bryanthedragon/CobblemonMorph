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
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.adapters.EvolutionAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.BlockClickEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.ItemInteractionEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.LevelUpEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.TradeEvolution;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010%\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001d\u0010\u001eJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ/\u0010\u0011\u001a\u00020\u0010\"\b\b\u0000\u0010\u000b*\u00020\b2\u0006\u0010\r\u001a\u00020\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J'\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0019R(\u0010\u001b\u001a\u0016\u0012\u0004\u0012\u00020\f\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\b0\u000e0\u001a8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\u001c\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/adapters/CobblemonEvolutionAdapter;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/adapters/EvolutionAdapter;", "Lcom/google/gson/JsonElement;", "jsonIn", "Ljava/lang/reflect/Type;", "typeOfT", "Lcom/google/gson/JsonDeserializationContext;", "context", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "T", "", "id", "Lkotlin/reflect/KClass;", "type", "", "registerType", "(Ljava/lang/String;Lkotlin/reflect/KClass;)V", "src", "typeOfSrc", "Lcom/google/gson/JsonSerializationContext;", "serialize", "(Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "VARIANT", "Ljava/lang/String;", "", "types", "Ljava/util/Map;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nCobbledEvolutionAdapter.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobbledEvolutionAdapter.kt\ncom/cobblemon/mod/common/pokemon/evolution/adapters/CobblemonEvolutionAdapter\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,61:1\n1#2:62\n*E\n"})
public final class CobblemonEvolutionAdapter
implements EvolutionAdapter {
    @NotNull
    public static final CobblemonEvolutionAdapter INSTANCE = new CobblemonEvolutionAdapter();
    @NotNull
    private static final String VARIANT = "variant";
    @NotNull
    private static final Map<String, KClass<? extends Evolution>> types = new LinkedHashMap();

    private CobblemonEvolutionAdapter() {
    }

    @Override
    public <T extends Evolution> void registerType(@NotNull String id, @NotNull KClass<T> type) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter(type, (String)"type");
        String string = id.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        types.put(string, type);
    }

    @NotNull
    public Evolution deserialize(@NotNull JsonElement jsonIn, @NotNull Type typeOfT, @NotNull JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter((Object)jsonIn, (String)"jsonIn");
        Intrinsics.checkNotNullParameter((Object)typeOfT, (String)"typeOfT");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        JsonObject json = jsonIn.getAsJsonObject();
        String string = json.get(VARIANT).getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.get(VARIANT).asString");
        String string2 = string.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        String variant = string2;
        KClass<? extends Evolution> kClass = types.get(variant);
        if (kClass == null) {
            throw new IllegalArgumentException("Cannot resolve type for variant " + variant);
        }
        KClass<? extends Evolution> type = kClass;
        Object object = context.deserialize((JsonElement)json, (Type)JvmClassMappingKt.getJavaClass(type));
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.deserialize(json, type.java)");
        return (Evolution)object;
    }

    @NotNull
    public JsonElement serialize(@NotNull Evolution src, @NotNull Type typeOfSrc, @NotNull JsonSerializationContext context) {
        Object object;
        Object v0;
        JsonObject json;
        block2: {
            Intrinsics.checkNotNullParameter((Object)src, (String)"src");
            Intrinsics.checkNotNullParameter((Object)typeOfSrc, (String)"typeOfSrc");
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            json = context.serialize((Object)src, (Type)src.getClass()).getAsJsonObject();
            Iterable iterable = types.entrySet();
            for (Object t : iterable) {
                Map.Entry it = (Map.Entry)t;
                boolean bl = false;
                if (!Intrinsics.areEqual(it.getValue(), (Object)Reflection.getOrCreateKotlinClass(src.getClass()))) continue;
                v0 = t;
                break block2;
            }
            v0 = null;
        }
        if ((object = (Map.Entry)v0) == null || (object = (String)object.getKey()) == null) {
            throw new IllegalArgumentException("Cannot resolve variant for type " + Reflection.getOrCreateKotlinClass(src.getClass()).getQualifiedName());
        }
        Object variant = object;
        json.addProperty(VARIANT, (String)variant);
        Intrinsics.checkNotNullExpressionValue((Object)json, (String)"json");
        return (JsonElement)json;
    }

    static {
        INSTANCE.registerType("level_up", Reflection.getOrCreateKotlinClass(LevelUpEvolution.class));
        INSTANCE.registerType("trade", Reflection.getOrCreateKotlinClass(TradeEvolution.class));
        INSTANCE.registerType("item_interact", Reflection.getOrCreateKotlinClass(ItemInteractionEvolution.class));
        INSTANCE.registerType("passive", Reflection.getOrCreateKotlinClass(LevelUpEvolution.class));
        INSTANCE.registerType("block_click", Reflection.getOrCreateKotlinClass(BlockClickEvolution.class));
    }
}

