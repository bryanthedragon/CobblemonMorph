/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.world.item.BlockItem
 *  net.minecraft.world.level.material.MapColor
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ApricornBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ApricornSaplingBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.ApricornItem;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\r\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\u0006\u0010\u0007J\r\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\t\u0010\nJ\r\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\f\u0010\rJ\r\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u000f\u0010\u0010j\u0002\b\u0013j\u0002\b\u0014j\u0002\b\u0015j\u0002\b\u0016j\u0002\b\u0017j\u0002\b\u0018j\u0002\b\u0019\u00a8\u0006\u001a"}, d2={"Lcom/cobblemon/mod/common/api/apricorn/Apricorn;", "", "Lcom/cobblemon/mod/common/block/ApricornBlock;", "block", "()Lcom/cobblemon/mod/common/block/ApricornBlock;", "Lcom/cobblemon/mod/common/item/ApricornItem;", "item", "()Lcom/cobblemon/mod/common/item/ApricornItem;", "Lnet/minecraft/world/level/material/MapColor;", "mapColor", "()Lnet/minecraft/world/level/material/MapColor;", "Lcom/cobblemon/mod/common/block/ApricornSaplingBlock;", "sapling", "()Lcom/cobblemon/mod/common/block/ApricornSaplingBlock;", "Lnet/minecraft/world/item/BlockItem;", "seed", "()Lnet/minecraft/world/item/BlockItem;", "<init>", "(Ljava/lang/String;I)V", "BLACK", "BLUE", "GREEN", "PINK", "RED", "WHITE", "YELLOW", "common"})
public final class Apricorn
extends Enum<Apricorn> {
    public static final /* enum */ Apricorn BLACK = new Apricorn();
    public static final /* enum */ Apricorn BLUE = new Apricorn();
    public static final /* enum */ Apricorn GREEN = new Apricorn();
    public static final /* enum */ Apricorn PINK = new Apricorn();
    public static final /* enum */ Apricorn RED = new Apricorn();
    public static final /* enum */ Apricorn WHITE = new Apricorn();
    public static final /* enum */ Apricorn YELLOW = new Apricorn();
    private static final /* synthetic */ Apricorn[] $VALUES;

    @NotNull
    public final ApricornItem item() {
        return switch (WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
            case 1 -> CobblemonItems.BLACK_APRICORN;
            case 2 -> CobblemonItems.BLUE_APRICORN;
            case 3 -> CobblemonItems.GREEN_APRICORN;
            case 4 -> CobblemonItems.PINK_APRICORN;
            case 5 -> CobblemonItems.RED_APRICORN;
            case 6 -> CobblemonItems.WHITE_APRICORN;
            case 7 -> CobblemonItems.YELLOW_APRICORN;
            default -> throw new NoWhenBranchMatchedException();
        };
    }

    @NotNull
    public final BlockItem seed() {
        return switch (WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
            case 1 -> (BlockItem)CobblemonItems.BLACK_APRICORN_SEED;
            case 2 -> (BlockItem)CobblemonItems.BLUE_APRICORN_SEED;
            case 3 -> (BlockItem)CobblemonItems.GREEN_APRICORN_SEED;
            case 4 -> (BlockItem)CobblemonItems.PINK_APRICORN_SEED;
            case 5 -> (BlockItem)CobblemonItems.RED_APRICORN_SEED;
            case 6 -> (BlockItem)CobblemonItems.WHITE_APRICORN_SEED;
            case 7 -> (BlockItem)CobblemonItems.YELLOW_APRICORN_SEED;
            default -> throw new NoWhenBranchMatchedException();
        };
    }

    @NotNull
    public final ApricornBlock block() {
        return switch (WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
            case 1 -> CobblemonBlocks.BLACK_APRICORN;
            case 2 -> CobblemonBlocks.BLUE_APRICORN;
            case 3 -> CobblemonBlocks.GREEN_APRICORN;
            case 4 -> CobblemonBlocks.PINK_APRICORN;
            case 5 -> CobblemonBlocks.RED_APRICORN;
            case 6 -> CobblemonBlocks.WHITE_APRICORN;
            case 7 -> CobblemonBlocks.YELLOW_APRICORN;
            default -> throw new NoWhenBranchMatchedException();
        };
    }

    @NotNull
    public final ApricornSaplingBlock sapling() {
        return switch (WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
            case 1 -> CobblemonBlocks.BLACK_APRICORN_SAPLING;
            case 2 -> CobblemonBlocks.BLUE_APRICORN_SAPLING;
            case 3 -> CobblemonBlocks.GREEN_APRICORN_SAPLING;
            case 4 -> CobblemonBlocks.PINK_APRICORN_SAPLING;
            case 5 -> CobblemonBlocks.RED_APRICORN_SAPLING;
            case 6 -> CobblemonBlocks.WHITE_APRICORN_SAPLING;
            case 7 -> CobblemonBlocks.YELLOW_APRICORN_SAPLING;
            default -> throw new NoWhenBranchMatchedException();
        };
    }

    @NotNull
    public final MapColor mapColor() {
        MapColor mapColor;
        switch (WhenMappings.$EnumSwitchMapping$0[this.ordinal()]) {
            case 1: {
                MapColor mapColor2 = MapColor.f_283927_;
                mapColor = mapColor2;
                Intrinsics.checkNotNullExpressionValue((Object)mapColor2, (String)"BLACK");
                break;
            }
            case 2: {
                MapColor mapColor3 = MapColor.f_283743_;
                mapColor = mapColor3;
                Intrinsics.checkNotNullExpressionValue((Object)mapColor3, (String)"BLUE");
                break;
            }
            case 3: {
                MapColor mapColor4 = MapColor.f_283784_;
                mapColor = mapColor4;
                Intrinsics.checkNotNullExpressionValue((Object)mapColor4, (String)"GREEN");
                break;
            }
            case 4: {
                MapColor mapColor5 = MapColor.f_283765_;
                mapColor = mapColor5;
                Intrinsics.checkNotNullExpressionValue((Object)mapColor5, (String)"PINK");
                break;
            }
            case 5: {
                MapColor mapColor6 = MapColor.f_283913_;
                mapColor = mapColor6;
                Intrinsics.checkNotNullExpressionValue((Object)mapColor6, (String)"RED");
                break;
            }
            case 6: {
                MapColor mapColor7 = MapColor.f_283811_;
                mapColor = mapColor7;
                Intrinsics.checkNotNullExpressionValue((Object)mapColor7, (String)"WHITE");
                break;
            }
            case 7: {
                MapColor mapColor8 = MapColor.f_283832_;
                mapColor = mapColor8;
                Intrinsics.checkNotNullExpressionValue((Object)mapColor8, (String)"YELLOW");
                break;
            }
            default: {
                throw new NoWhenBranchMatchedException();
            }
        }
        return mapColor;
    }

    public static Apricorn[] values() {
        return (Apricorn[])$VALUES.clone();
    }

    public static Apricorn valueOf(String value2) {
        return Enum.valueOf(Apricorn.class, value2);
    }

    static {
        $VALUES = apricornArray = new Apricorn[]{Apricorn.BLACK, Apricorn.BLUE, Apricorn.GREEN, Apricorn.PINK, Apricorn.RED, Apricorn.WHITE, Apricorn.YELLOW};
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

