/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.HashBiMap
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonSerializationContext
 *  kotlin.Metadata
 *  kotlin.jvm.JvmClassMappingKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Reflection
 *  kotlin.reflect.KClass
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.adapters.RequirementAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.requirement.EvolutionRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.AnyRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.AreaRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.AttackDefenceRatioRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.BattleCriticalHitsRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.BiomeRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.BlocksTraveledRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.DamageTakenRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.DefeatRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.FriendshipRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.HeldItemRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.LevelRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.MoonPhaseRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.MoveSetRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.MoveTypeRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.PartyMemberRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.PlayerHasAdvancementRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.PokemonPropertiesRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.PropertyRangeRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.RecoilRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.StatCompareRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.StatEqualRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.StructureRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.TimeRangeRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.UseMoveRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.WeatherRequirement;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.requirements.WorldRequirement;
import com.google.common.collect.HashBiMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import java.lang.reflect.Type;
import java.util.Locale;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001e\u0010\u001fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ/\u0010\u0011\u001a\u00020\u0010\"\b\b\u0000\u0010\u000b*\u00020\b2\u0006\u0010\r\u001a\u00020\f2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00000\u000eH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J'\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0015H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\f8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0019Rt\u0010\u001c\u001ab\u0012\f\u0012\n \u001b*\u0004\u0018\u00010\f0\f\u0012\u001c\u0012\u001a\u0012\u0006\b\u0001\u0012\u00020\b \u001b*\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\u000e0\u000e \u001b*0\u0012\f\u0012\n \u001b*\u0004\u0018\u00010\f0\f\u0012\u001c\u0012\u001a\u0012\u0006\b\u0001\u0012\u00020\b \u001b*\f\u0012\u0006\b\u0001\u0012\u00020\b\u0018\u00010\u000e0\u000e\u0018\u00010\u001a0\u001a8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u001d\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/pokemon/evolution/adapters/CobblemonRequirementAdapter;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/adapters/RequirementAdapter;", "Lcom/google/gson/JsonElement;", "json", "Ljava/lang/reflect/Type;", "typeOfT", "Lcom/google/gson/JsonDeserializationContext;", "context", "Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;", "T", "", "id", "Lkotlin/reflect/KClass;", "type", "", "registerType", "(Ljava/lang/String;Lkotlin/reflect/KClass;)V", "src", "typeOfSrc", "Lcom/google/gson/JsonSerializationContext;", "serialize", "(Lcom/cobblemon/mod/common/api/pokemon/evolution/requirement/EvolutionRequirement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonSerializationContext;)Lcom/google/gson/JsonElement;", "VARIANT", "Ljava/lang/String;", "Lcom/google/common/collect/HashBiMap;", "kotlin.jvm.PlatformType", "types", "Lcom/google/common/collect/HashBiMap;", "<init>", "()V", "common"})
public final class CobblemonRequirementAdapter
implements RequirementAdapter {
    @NotNull
    public static final CobblemonRequirementAdapter INSTANCE = new CobblemonRequirementAdapter();
    @NotNull
    private static final String VARIANT = "variant";
    private static final HashBiMap<String, KClass<? extends EvolutionRequirement>> types = HashBiMap.create();

    private CobblemonRequirementAdapter() {
    }

    @Override
    public <T extends EvolutionRequirement> void registerType(@NotNull String id, @NotNull KClass<T> type) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter(type, (String)"type");
        HashBiMap<String, KClass<? extends EvolutionRequirement>> hashBiMap = types;
        Intrinsics.checkNotNullExpressionValue(hashBiMap, (String)"types");
        Map map = (Map)hashBiMap;
        String string = id.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        String string2 = string;
        map.put(string2, type);
    }

    @NotNull
    public EvolutionRequirement deserialize(@NotNull JsonElement json, @NotNull Type typeOfT, @NotNull JsonDeserializationContext context) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Intrinsics.checkNotNullParameter((Object)typeOfT, (String)"typeOfT");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        String string = json.getAsJsonObject().get(VARIANT).getAsString();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"json.asJsonObject.get(VARIANT).asString");
        String string2 = string.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        String variant = string2;
        KClass kClass = (KClass)types.get((Object)variant);
        if (kClass == null) {
            throw new IllegalArgumentException("Cannot resolve evolution requirement type for variant " + variant);
        }
        KClass type = kClass;
        Object object = context.deserialize(json, (Type)JvmClassMappingKt.getJavaClass((KClass)type));
        Intrinsics.checkNotNullExpressionValue((Object)object, (String)"context.deserialize(json, type.java)");
        return (EvolutionRequirement)object;
    }

    @NotNull
    public JsonElement serialize(@NotNull EvolutionRequirement src, @NotNull Type typeOfSrc, @NotNull JsonSerializationContext context) {
        Intrinsics.checkNotNullParameter((Object)src, (String)"src");
        Intrinsics.checkNotNullParameter((Object)typeOfSrc, (String)"typeOfSrc");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        JsonObject json = context.serialize((Object)src, (Type)src.getClass()).getAsJsonObject();
        String string = (String)types.inverse().get((Object)Reflection.getOrCreateKotlinClass(src.getClass()));
        if (string == null) {
            throw new IllegalArgumentException("Cannot resolve evolution requirement for type " + Reflection.getOrCreateKotlinClass(src.getClass()).getQualifiedName());
        }
        String variant = string;
        json.addProperty(VARIANT, variant);
        Intrinsics.checkNotNullExpressionValue((Object)json, (String)"json");
        return (JsonElement)json;
    }

    static {
        INSTANCE.registerType("area", Reflection.getOrCreateKotlinClass(AreaRequirement.class));
        INSTANCE.registerType("biome", Reflection.getOrCreateKotlinClass(BiomeRequirement.class));
        INSTANCE.registerType("friendship", Reflection.getOrCreateKotlinClass(FriendshipRequirement.class));
        INSTANCE.registerType("held_item", Reflection.getOrCreateKotlinClass(HeldItemRequirement.class));
        INSTANCE.registerType("world", Reflection.getOrCreateKotlinClass(WorldRequirement.class));
        INSTANCE.registerType("has_move", Reflection.getOrCreateKotlinClass(MoveSetRequirement.class));
        INSTANCE.registerType("has_move_type", Reflection.getOrCreateKotlinClass(MoveTypeRequirement.class));
        INSTANCE.registerType("party_member", Reflection.getOrCreateKotlinClass(PartyMemberRequirement.class));
        INSTANCE.registerType("properties", Reflection.getOrCreateKotlinClass(PokemonPropertiesRequirement.class));
        INSTANCE.registerType("time_range", Reflection.getOrCreateKotlinClass(TimeRangeRequirement.class));
        INSTANCE.registerType("level", Reflection.getOrCreateKotlinClass(LevelRequirement.class));
        INSTANCE.registerType("weather", Reflection.getOrCreateKotlinClass(WeatherRequirement.class));
        INSTANCE.registerType("stat_compare", Reflection.getOrCreateKotlinClass(StatCompareRequirement.class));
        INSTANCE.registerType("stat_equal", Reflection.getOrCreateKotlinClass(StatEqualRequirement.class));
        INSTANCE.registerType("attack_defence_ratio", Reflection.getOrCreateKotlinClass(AttackDefenceRatioRequirement.class));
        INSTANCE.registerType("battle_critical_hits", Reflection.getOrCreateKotlinClass(BattleCriticalHitsRequirement.class));
        INSTANCE.registerType("damage_taken", Reflection.getOrCreateKotlinClass(DamageTakenRequirement.class));
        INSTANCE.registerType("use_move", Reflection.getOrCreateKotlinClass(UseMoveRequirement.class));
        INSTANCE.registerType("moon_phase", Reflection.getOrCreateKotlinClass(MoonPhaseRequirement.class));
        INSTANCE.registerType("recoil", Reflection.getOrCreateKotlinClass(RecoilRequirement.class));
        INSTANCE.registerType("defeat", Reflection.getOrCreateKotlinClass(DefeatRequirement.class));
        INSTANCE.registerType("blocks_traveled", Reflection.getOrCreateKotlinClass(BlocksTraveledRequirement.class));
        INSTANCE.registerType("structure", Reflection.getOrCreateKotlinClass(StructureRequirement.class));
        INSTANCE.registerType("any", Reflection.getOrCreateKotlinClass(AnyRequirement.class));
        INSTANCE.registerType("property_range", Reflection.getOrCreateKotlinClass(PropertyRangeRequirement.class));
        INSTANCE.registerType(PlayerHasAdvancementRequirement.Companion.getADAPTER_VARIANT(), Reflection.getOrCreateKotlinClass(PlayerHasAdvancementRequirement.class));
    }
}

