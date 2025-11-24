/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.level.block.grower.AbstractTreeGrower
 *  net.minecraft.world.level.levelgen.feature.ConfiguredFeature
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.grower;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn.Apricorn;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.world.feature.CobblemonConfiguredFeatures;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\r\u0010\u000eJ-\u0010\b\u001a\u0010\u0012\f\u0012\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00070\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0014\u00a2\u0006\u0004\b\b\u0010\tR\u0014\u0010\u000b\u001a\u00020\n8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/block/grower/ApricornTreeGrower;", "Lnet/minecraft/world/level/block/grower/AbstractTreeGrower;", "Lnet/minecraft/util/RandomSource;", "random", "", "bl", "Lnet/minecraft/resources/ResourceKey;", "Lnet/minecraft/world/level/levelgen/feature/ConfiguredFeature;", "getTreeFeature", "(Lnet/minecraft/util/RandomSource;Z)Lnet/minecraft/resources/ResourceKey;", "Lcom/cobblemon/mod/common/api/apricorn/Apricorn;", "apricorn", "Lcom/cobblemon/mod/common/api/apricorn/Apricorn;", "<init>", "(Lcom/cobblemon/mod/common/api/apricorn/Apricorn;)V", "common"})
public final class ApricornTreeGrower
extends AbstractTreeGrower {
    @NotNull
    private final Apricorn apricorn;

    public ApricornTreeGrower(@NotNull Apricorn apricorn) {
        Intrinsics.checkNotNullParameter((Object)((Object)apricorn), (String)"apricorn");
        this.apricorn = apricorn;
    }

    @NotNull
    protected ResourceKey<ConfiguredFeature<?, ?>> m_213888_(@NotNull RandomSource random, boolean bl) {
        Intrinsics.checkNotNullParameter((Object)random, (String)"random");
        return switch (WhenMappings.$EnumSwitchMapping$0[this.apricorn.ordinal()]) {
            case 1 -> CobblemonConfiguredFeatures.BLACK_APRICORN_TREE_KEY;
            case 2 -> CobblemonConfiguredFeatures.BLUE_APRICORN_TREE_KEY;
            case 3 -> CobblemonConfiguredFeatures.GREEN_APRICORN_TREE_KEY;
            case 4 -> CobblemonConfiguredFeatures.PINK_APRICORN_TREE_KEY;
            case 5 -> CobblemonConfiguredFeatures.RED_APRICORN_TREE_KEY;
            case 6 -> CobblemonConfiguredFeatures.WHITE_APRICORN_TREE_KEY;
            case 7 -> CobblemonConfiguredFeatures.YELLOW_APRICORN_TREE_KEY;
            default -> throw new NoWhenBranchMatchedException();
        };
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Apricorn.values().length];
            try {
                nArray[Apricorn.BLACK.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Apricorn.BLUE.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Apricorn.GREEN.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Apricorn.PINK.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Apricorn.RED.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Apricorn.WHITE.ordinal()] = 6;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Apricorn.YELLOW.ordinal()] = 7;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

