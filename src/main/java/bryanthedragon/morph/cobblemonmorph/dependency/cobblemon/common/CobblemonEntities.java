/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.JvmField
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.DefaultedRegistry
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.entity.EntityType$Builder
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.entity.MobCategory
 *  net.minecraft.world.entity.ai.attributes.AttributeSupplier$Builder
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokeball.PokeBalls;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonBoatEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.boat.CobblemonChestBoatEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokeball.EmptyPokeBallEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.JvmField;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\r\b\u00c6\u0002\u0018\u000022\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002\u0012\u0014\u0012\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b&\u0010'J/\u0010\n\u001a\u00020\b2 \u0010\t\u001a\u001c\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0003\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\u0004\b\n\u0010\u000bR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0014\u0010\u0010\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u000eR\u0014\u0010\u0014\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0011R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u000eR\u0014\u0010\u0017\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0017\u0010\u0011R\u001a\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0019\u0010\u000eR\u0014\u0010\u001a\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u0011R\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u000eR\u0014\u0010\u001d\u001a\u00020\u000f8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\u0011R$\u0010\u001e\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!R*\u0010\"\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00020\u00048\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\u00a8\u0006("}, d2={"Lcom/cobblemon/mod/common/CobblemonEntities;", "Lcom/cobblemon/mod/common/platform/PlatformRegistry;", "Lnet/minecraft/core/Registry;", "Lnet/minecraft/world/entity/EntityType;", "Lnet/minecraft/resources/ResourceKey;", "Lkotlin/Function2;", "Lnet/minecraft/world/entity/LivingEntity;", "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", "", "consumer", "registerAttributes", "(Lkotlin/jvm/functions/Function2;)V", "Lcom/cobblemon/mod/common/entity/boat/CobblemonBoatEntity;", "BOAT", "Lnet/minecraft/world/entity/EntityType;", "Lnet/minecraft/resources/ResourceLocation;", "BOAT_KEY", "Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/entity/boat/CobblemonChestBoatEntity;", "CHEST_BOAT", "CHEST_BOAT_KEY", "Lcom/cobblemon/mod/common/entity/pokeball/EmptyPokeBallEntity;", "EMPTY_POKEBALL", "EMPTY_POKEBALL_KEY", "Lcom/cobblemon/mod/common/entity/generic/GenericBedrockEntity;", "GENERIC_BEDROCK_ENTITY", "GENERIC_BEDROCK_ENTITY_KEY", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "POKEMON", "POKEMON_KEY", "registry", "Lnet/minecraft/core/Registry;", "getRegistry", "()Lnet/minecraft/core/Registry;", "registryKey", "Lnet/minecraft/resources/ResourceKey;", "getRegistryKey", "()Lnet/minecraft/resources/ResourceKey;", "<init>", "()V", "common"})
public final class CobblemonEntities
extends PlatformRegistry<Registry<EntityType<?>>, ResourceKey<Registry<EntityType<?>>>, EntityType<?>> {
    @NotNull
    public static final CobblemonEntities INSTANCE = new CobblemonEntities();
    @NotNull
    private static final Registry<EntityType<?>> registry;
    @NotNull
    private static final ResourceKey<Registry<EntityType<?>>> registryKey;
    @JvmField
    @NotNull
    public static final ResourceLocation POKEMON_KEY;
    @JvmField
    @NotNull
    public static final EntityType<PokemonEntity> POKEMON;
    @JvmField
    @NotNull
    public static final ResourceLocation EMPTY_POKEBALL_KEY;
    @JvmField
    @NotNull
    public static final EntityType<EmptyPokeBallEntity> EMPTY_POKEBALL;
    @JvmField
    @NotNull
    public static final ResourceLocation BOAT_KEY;
    @JvmField
    @NotNull
    public static final EntityType<CobblemonBoatEntity> BOAT;
    @JvmField
    @NotNull
    public static final ResourceLocation CHEST_BOAT_KEY;
    @JvmField
    @NotNull
    public static final EntityType<CobblemonChestBoatEntity> CHEST_BOAT;
    @JvmField
    @NotNull
    public static final ResourceLocation GENERIC_BEDROCK_ENTITY_KEY;
    @JvmField
    @NotNull
    public static final EntityType<GenericBedrockEntity> GENERIC_BEDROCK_ENTITY;

    private CobblemonEntities() {
    }

    @Override
    @NotNull
    public Registry<EntityType<?>> getRegistry() {
        return registry;
    }

    @Override
    @NotNull
    public ResourceKey<Registry<EntityType<?>>> getRegistryKey() {
        return registryKey;
    }

    public final void registerAttributes(@NotNull Function2<? super EntityType<? extends LivingEntity>, ? super AttributeSupplier.Builder, Unit> consumer) {
        Intrinsics.checkNotNullParameter(consumer, (String)"consumer");
        consumer.invoke(POKEMON, (Object)PokemonEntity.Companion.createAttributes());
    }

    private static final PokemonEntity POKEMON$lambda$0(EntityType entityType, Level world) {
        Intrinsics.checkNotNullExpressionValue((Object)world, (String)"world");
        return new PokemonEntity(world, null, null, 6, null);
    }

    private static final EmptyPokeBallEntity EMPTY_POKEBALL$lambda$1(EntityType entityType, Level world) {
        PokeBall pokeBall = PokeBalls.INSTANCE.getPOKE_BALL();
        Intrinsics.checkNotNullExpressionValue((Object)world, (String)"world");
        return new EmptyPokeBallEntity(pokeBall, world, null, 4, null);
    }

    private static final GenericBedrockEntity GENERIC_BEDROCK_ENTITY$lambda$2(EntityType entityType, Level world) {
        Intrinsics.checkNotNullExpressionValue((Object)world, (String)"world");
        return new GenericBedrockEntity(world);
    }

    static {
        DefaultedRegistry defaultedRegistry = BuiltInRegistries.f_256780_;
        Intrinsics.checkNotNullExpressionValue((Object)defaultedRegistry, (String)"ENTITY_TYPE");
        registry = (Registry)defaultedRegistry;
        ResourceKey resourceKey = Registries.f_256939_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"ENTITY_TYPE");
        registryKey = resourceKey;
        POKEMON_KEY = MiscUtils.cobblemonResource("pokemon");
        String string = POKEMON_KEY.m_135815_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"POKEMON_KEY.path");
        EntityType entityType = INSTANCE.create(string, EntityType.Builder.m_20704_(CobblemonEntities::POKEMON$lambda$0, (MobCategory)MobCategory.CREATURE).m_20712_(POKEMON_KEY.toString()));
        Intrinsics.checkNotNullExpressionValue((Object)entityType, (String)"this.create(\n        POK\u2026MON_KEY.toString())\n    )");
        POKEMON = entityType;
        EMPTY_POKEBALL_KEY = MiscUtils.cobblemonResource("empty_pokeball");
        String string2 = EMPTY_POKEBALL_KEY.m_135815_();
        Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"EMPTY_POKEBALL_KEY.path");
        entityType = INSTANCE.create(string2, EntityType.Builder.m_20704_(CobblemonEntities::EMPTY_POKEBALL$lambda$1, (MobCategory)MobCategory.MISC).m_20712_(EMPTY_POKEBALL_KEY.toString()));
        Intrinsics.checkNotNullExpressionValue((Object)entityType, (String)"this.create(\n        EMP\u2026ALL_KEY.toString())\n    )");
        EMPTY_POKEBALL = entityType;
        BOAT_KEY = MiscUtils.cobblemonResource("boat");
        String string3 = BOAT_KEY.m_135815_();
        Intrinsics.checkNotNullExpressionValue((Object)string3, (String)"BOAT_KEY.path");
        entityType = INSTANCE.create(string3, EntityType.Builder.m_20704_(CobblemonBoatEntity::new, (MobCategory)MobCategory.MISC).m_20699_(1.375f, 0.5625f).m_20702_(10).m_20712_(BOAT_KEY.toString()));
        Intrinsics.checkNotNullExpressionValue((Object)entityType, (String)"this.create(\n        BOA\u2026OAT_KEY.toString())\n    )");
        BOAT = entityType;
        CHEST_BOAT_KEY = MiscUtils.cobblemonResource("chest_boat");
        String string4 = CHEST_BOAT_KEY.m_135815_();
        Intrinsics.checkNotNullExpressionValue((Object)string4, (String)"CHEST_BOAT_KEY.path");
        entityType = INSTANCE.create(string4, EntityType.Builder.m_20704_(CobblemonChestBoatEntity::new, (MobCategory)MobCategory.MISC).m_20699_(1.375f, 0.5625f).m_20702_(10).m_20712_(CHEST_BOAT_KEY.toString()));
        Intrinsics.checkNotNullExpressionValue((Object)entityType, (String)"this.create(\n        CHE\u2026OAT_KEY.toString())\n    )");
        CHEST_BOAT = entityType;
        GENERIC_BEDROCK_ENTITY_KEY = MiscUtils.cobblemonResource("generic_bedrock");
        String string5 = GENERIC_BEDROCK_ENTITY_KEY.m_135815_();
        Intrinsics.checkNotNullExpressionValue((Object)string5, (String)"GENERIC_BEDROCK_ENTITY_KEY.path");
        entityType = INSTANCE.create(string5, EntityType.Builder.m_20704_(CobblemonEntities::GENERIC_BEDROCK_ENTITY$lambda$2, (MobCategory)MobCategory.MISC).m_20712_(GENERIC_BEDROCK_ENTITY_KEY.toString()));
        Intrinsics.checkNotNullExpressionValue((Object)entityType, (String)"this.create(\n        GEN\u2026ITY_KEY.toString())\n    )");
        GENERIC_BEDROCK_ENTITY = entityType;
    }
}

