/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmField
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntityType
 *  net.minecraft.world.level.block.entity.BlockEntityType$Builder
 *  net.minecraft.world.level.block.state.BlockState
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.CobblemonHangingSignBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.CobblemonSignBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.DisplayCaseBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilAnalyzerBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilMultiblockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.GildedChestBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.HealingMachineBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PCBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.PokemonPastureBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.RestorationTankBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock.FossilMultiblockBuilder;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;
import java.util.Arrays;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\b\u00c6\u0002\u0018\u000022\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0002\u0012\u0014\u0012\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00020\u0004\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0001B\t\b\u0002\u00a2\u0006\u0004\b%\u0010&R8\u0010\u0007\u001a&\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005 \u0006*\u0012\u0012\f\u0012\n \u0006*\u0004\u0018\u00010\u00050\u0005\u0018\u00010\u00030\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\bR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\n\u0010\bR\u001a\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\f\u0010\bR\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\bR\u001a\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\bR\u001a\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\bR\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\bR\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00150\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\bR\u001a\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\bR\u001a\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\bR\u001a\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\bR$\u0010\u001d\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R*\u0010!\u001a\u0012\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u00020\u00048\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/CobblemonBlockEntities;", "Lcom/cobblemon/mod/common/platform/PlatformRegistry;", "Lnet/minecraft/core/Registry;", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", "Lnet/minecraft/resources/ResourceKey;", "Lcom/cobblemon/mod/common/block/entity/BerryBlockEntity;", "kotlin.jvm.PlatformType", "BERRY", "Lnet/minecraft/world/level/block/entity/BlockEntityType;", "Lcom/cobblemon/mod/common/block/entity/DisplayCaseBlockEntity;", "DISPLAY_CASE", "Lcom/cobblemon/mod/common/block/entity/FossilAnalyzerBlockEntity;", "FOSSIL_ANALYZER", "Lcom/cobblemon/mod/common/block/entity/FossilMultiblockEntity;", "FOSSIL_MULTIBLOCK", "Lcom/cobblemon/mod/common/block/entity/GildedChestBlockEntity;", "GILDED_CHEST", "Lcom/cobblemon/mod/common/block/entity/CobblemonHangingSignBlockEntity;", "HANGING_SIGN", "Lcom/cobblemon/mod/common/block/entity/HealingMachineBlockEntity;", "HEALING_MACHINE", "Lcom/cobblemon/mod/common/block/entity/PokemonPastureBlockEntity;", "PASTURE", "Lcom/cobblemon/mod/common/block/entity/PCBlockEntity;", "PC", "Lcom/cobblemon/mod/common/block/entity/RestorationTankBlockEntity;", "RESTORATION_TANK", "Lcom/cobblemon/mod/common/block/entity/CobblemonSignBlockEntity;", "SIGN", "registry", "Lnet/minecraft/core/Registry;", "getRegistry", "()Lnet/minecraft/core/Registry;", "registryKey", "Lnet/minecraft/resources/ResourceKey;", "getRegistryKey", "()Lnet/minecraft/resources/ResourceKey;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonBlockEntities.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonBlockEntities.kt\ncom/cobblemon/mod/common/CobblemonBlockEntities\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,87:1\n37#2,2:88\n*S KotlinDebug\n*F\n+ 1 CobblemonBlockEntities.kt\ncom/cobblemon/mod/common/CobblemonBlockEntities\n*L\n40#1:88,2\n*E\n"})
public final class CobblemonBlockEntities
extends PlatformRegistry<Registry<BlockEntityType<?>>, ResourceKey<Registry<BlockEntityType<?>>>, BlockEntityType<?>> {
    @NotNull
    public static final CobblemonBlockEntities INSTANCE = new CobblemonBlockEntities();
    @NotNull
    private static final Registry<BlockEntityType<?>> registry;
    @NotNull
    private static final ResourceKey<Registry<BlockEntityType<?>>> registryKey;
    @JvmField
    @NotNull
    public static final BlockEntityType<HealingMachineBlockEntity> HEALING_MACHINE;
    @JvmField
    @NotNull
    public static final BlockEntityType<PCBlockEntity> PC;
    @JvmField
    public static final BlockEntityType<BerryBlockEntity> BERRY;
    @JvmField
    @NotNull
    public static final BlockEntityType<PokemonPastureBlockEntity> PASTURE;
    @JvmField
    @NotNull
    public static final BlockEntityType<CobblemonSignBlockEntity> SIGN;
    @JvmField
    @NotNull
    public static final BlockEntityType<CobblemonHangingSignBlockEntity> HANGING_SIGN;
    @JvmField
    @NotNull
    public static final BlockEntityType<GildedChestBlockEntity> GILDED_CHEST;
    @JvmField
    @NotNull
    public static final BlockEntityType<FossilMultiblockEntity> FOSSIL_MULTIBLOCK;
    @JvmField
    @NotNull
    public static final BlockEntityType<RestorationTankBlockEntity> RESTORATION_TANK;
    @JvmField
    @NotNull
    public static final BlockEntityType<FossilAnalyzerBlockEntity> FOSSIL_ANALYZER;
    @JvmField
    @NotNull
    public static final BlockEntityType<DisplayCaseBlockEntity> DISPLAY_CASE;

    private CobblemonBlockEntities() {
    }

    @Override
    @NotNull
    public Registry<BlockEntityType<?>> getRegistry() {
        return registry;
    }

    @Override
    @NotNull
    public ResourceKey<Registry<BlockEntityType<?>>> getRegistryKey() {
        return registryKey;
    }

    private static final FossilMultiblockEntity FOSSIL_MULTIBLOCK$lambda$1(BlockPos pos, BlockState state) {
        Intrinsics.checkNotNullExpressionValue((Object)pos, (String)"pos");
        Intrinsics.checkNotNullExpressionValue((Object)state, (String)"state");
        return new FossilMultiblockEntity(pos, state, new FossilMultiblockBuilder(pos), null, 8, null);
    }

    private static final RestorationTankBlockEntity RESTORATION_TANK$lambda$2(BlockPos pos, BlockState state) {
        Intrinsics.checkNotNullExpressionValue((Object)pos, (String)"pos");
        Intrinsics.checkNotNullExpressionValue((Object)state, (String)"state");
        return new RestorationTankBlockEntity(pos, state, new FossilMultiblockBuilder(pos));
    }

    private static final FossilAnalyzerBlockEntity FOSSIL_ANALYZER$lambda$3(BlockPos pos, BlockState state) {
        Intrinsics.checkNotNullExpressionValue((Object)pos, (String)"pos");
        Intrinsics.checkNotNullExpressionValue((Object)state, (String)"state");
        return new FossilAnalyzerBlockEntity(pos, state, new FossilMultiblockBuilder(pos));
    }

    static {
        Registry registry = BuiltInRegistries.f_257049_;
        Intrinsics.checkNotNullExpressionValue((Object)registry, (String)"BLOCK_ENTITY_TYPE");
        CobblemonBlockEntities.registry = registry;
        ResourceKey resourceKey = Registries.f_256922_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"BLOCK_ENTITY_TYPE");
        registryKey = resourceKey;
        Block[] blockArray = new Block[]{CobblemonBlocks.HEALING_MACHINE};
        BlockEntityType blockEntityType = INSTANCE.create("healing_machine", BlockEntityType.Builder.m_155273_(HealingMachineBlockEntity::new, (Block[])blockArray).m_58966_(null));
        Intrinsics.checkNotNullExpressionValue((Object)blockEntityType, (String)"this.create(\"healing_mac\u2026ING_MACHINE).build(null))");
        HEALING_MACHINE = blockEntityType;
        blockArray = new Block[]{CobblemonBlocks.PC};
        BlockEntityType blockEntityType2 = INSTANCE.create("pc", BlockEntityType.Builder.m_155273_(PCBlockEntity::new, (Block[])blockArray).m_58966_(null));
        Intrinsics.checkNotNullExpressionValue((Object)blockEntityType2, (String)"this.create(\"pc\", BlockE\u2026onBlocks.PC).build(null))");
        PC = blockEntityType2;
        Collection<BerryBlock> $this$toTypedArray$iv = CobblemonBlocks.INSTANCE.berries().values();
        boolean $i$f$toTypedArray = false;
        Collection<BerryBlock> thisCollection$iv = $this$toTypedArray$iv;
        BerryBlock[] berryBlockArray = thisCollection$iv.toArray(new BerryBlock[0]);
        BERRY = INSTANCE.create("berry", BlockEntityType.Builder.m_155273_(BerryBlockEntity::new, (Block[])((Block[])Arrays.copyOf(berryBlockArray, berryBlockArray.length))).m_58966_(null));
        blockArray = new Block[]{CobblemonBlocks.PASTURE};
        BlockEntityType blockEntityType3 = INSTANCE.create("pasture", BlockEntityType.Builder.m_155273_(PokemonPastureBlockEntity::new, (Block[])blockArray).m_58966_(null));
        Intrinsics.checkNotNullExpressionValue((Object)blockEntityType3, (String)"this.create(\"pasture\", B\u2026cks.PASTURE).build(null))");
        PASTURE = blockEntityType3;
        blockArray = new Block[]{CobblemonBlocks.APRICORN_SIGN, CobblemonBlocks.APRICORN_WALL_SIGN};
        BlockEntityType blockEntityType4 = INSTANCE.create("sign", BlockEntityType.Builder.m_155273_(CobblemonSignBlockEntity::new, (Block[])blockArray).m_58966_(null));
        Intrinsics.checkNotNullExpressionValue((Object)blockEntityType4, (String)"this.create(\"sign\", Bloc\u2026N_WALL_SIGN).build(null))");
        SIGN = blockEntityType4;
        blockArray = new Block[]{CobblemonBlocks.APRICORN_HANGING_SIGN, CobblemonBlocks.APRICORN_WALL_HANGING_SIGN};
        BlockEntityType blockEntityType5 = INSTANCE.create("hanging_sign", BlockEntityType.Builder.m_155273_(CobblemonHangingSignBlockEntity::new, (Block[])blockArray).m_58966_(null));
        Intrinsics.checkNotNullExpressionValue((Object)blockEntityType5, (String)"this.create(\"hanging_sig\u2026ANGING_SIGN).build(null))");
        HANGING_SIGN = blockEntityType5;
        blockArray = new Block[]{CobblemonBlocks.GILDED_CHEST, CobblemonBlocks.BLUE_GILDED_CHEST, CobblemonBlocks.YELLOW_GILDED_CHEST, CobblemonBlocks.PINK_GILDED_CHEST, CobblemonBlocks.BLACK_GILDED_CHEST, CobblemonBlocks.WHITE_GILDED_CHEST, CobblemonBlocks.GREEN_GILDED_CHEST, CobblemonBlocks.GIMMIGHOUL_CHEST};
        BlockEntityType blockEntityType6 = INSTANCE.create("chest", BlockEntityType.Builder.m_155273_((p0, p1) -> new GildedChestBlockEntity(p0, p1, null, 4, null), (Block[])blockArray).m_58966_(null));
        Intrinsics.checkNotNullExpressionValue((Object)blockEntityType6, (String)"this.create(\"chest\", Blo\u2026_CHEST\n    ).build(null))");
        GILDED_CHEST = blockEntityType6;
        blockArray = new Block[]{CobblemonBlocks.MONITOR};
        BlockEntityType blockEntityType7 = INSTANCE.create("fossil_multiblock", BlockEntityType.Builder.m_155273_(CobblemonBlockEntities::FOSSIL_MULTIBLOCK$lambda$1, (Block[])blockArray).m_58966_(null));
        Intrinsics.checkNotNullExpressionValue((Object)blockEntityType7, (String)"this.create(\"fossil_mult\u2026      ).build(null)\n    )");
        FOSSIL_MULTIBLOCK = blockEntityType7;
        blockArray = new Block[]{CobblemonBlocks.RESTORATION_TANK};
        BlockEntityType blockEntityType8 = INSTANCE.create("restoration_tank", BlockEntityType.Builder.m_155273_(CobblemonBlockEntities::RESTORATION_TANK$lambda$2, (Block[])blockArray).m_58966_(null));
        Intrinsics.checkNotNullExpressionValue((Object)blockEntityType8, (String)"this.create(\"restoration\u2026      ).build(null)\n    )");
        RESTORATION_TANK = blockEntityType8;
        blockArray = new Block[]{CobblemonBlocks.FOSSIL_ANALYZER};
        BlockEntityType blockEntityType9 = INSTANCE.create("fossil_analyzer", BlockEntityType.Builder.m_155273_(CobblemonBlockEntities::FOSSIL_ANALYZER$lambda$3, (Block[])blockArray).m_58966_(null));
        Intrinsics.checkNotNullExpressionValue((Object)blockEntityType9, (String)"this.create(\"fossil_anal\u2026      ).build(null)\n    )");
        FOSSIL_ANALYZER = blockEntityType9;
        blockArray = new Block[]{CobblemonBlocks.DISPLAY_CASE};
        BlockEntityType blockEntityType10 = INSTANCE.create("display_case", BlockEntityType.Builder.m_155273_(DisplayCaseBlockEntity::new, (Block[])blockArray).m_58966_(null));
        Intrinsics.checkNotNullExpressionValue((Object)blockEntityType10, (String)"this.create(\"display_cas\u2026Y_CASE).build(null)\n    )");
        DISPLAY_CASE = blockEntityType10;
    }
}

