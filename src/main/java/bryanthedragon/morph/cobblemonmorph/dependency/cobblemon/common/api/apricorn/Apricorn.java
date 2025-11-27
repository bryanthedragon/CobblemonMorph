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

import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.material.MapColor;

import org.jetbrains.annotations.NotNull;

public enum Apricorn {
    BLACK,
    BLUE,
    GREEN,
    PINK,
    RED,
    WHITE,
    YELLOW;

    @NotNull
    public ApricornItem item() {
        switch (this) {
            case BLACK:
                return CobblemonItems.BLACK_APRICORN;
            case BLUE:
                return CobblemonItems.BLUE_APRICORN;
            case GREEN:
                return CobblemonItems.GREEN_APRICORN;
            case PINK:
                return CobblemonItems.PINK_APRICORN;
            case RED:
                return CobblemonItems.RED_APRICORN;
            case WHITE:
                return CobblemonItems.WHITE_APRICORN;
            case YELLOW:
                return CobblemonItems.YELLOW_APRICORN;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public BlockItem seed() {
        switch (this) {
            case BLACK:
                return (BlockItem)CobblemonItems.BLACK_APRICORN_SEED;
            case BLUE:
                return (BlockItem)CobblemonItems.BLUE_APRICORN_SEED;
            case GREEN:
                return (BlockItem)CobblemonItems.GREEN_APRICORN_SEED;
            case PINK:
                return (BlockItem)CobblemonItems.PINK_APRICORN_SEED;
            case RED:
                return (BlockItem)CobblemonItems.RED_APRICORN_SEED;
            case WHITE:
                return (BlockItem)CobblemonItems.WHITE_APRICORN_SEED;
            case YELLOW:
                return (BlockItem)CobblemonItems.YELLOW_APRICORN_SEED;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public ApricornBlock block() {
        switch (this) {
            case BLACK:
                return CobblemonBlocks.BLACK_APRICORN;
            case BLUE:
                return CobblemonBlocks.BLUE_APRICORN;
            case GREEN:
                return CobblemonBlocks.GREEN_APRICORN;
            case PINK:
                return CobblemonBlocks.PINK_APRICORN;
            case RED:
                return CobblemonBlocks.RED_APRICORN;
            case WHITE:
                return CobblemonBlocks.WHITE_APRICORN;
            case YELLOW:
                return CobblemonBlocks.YELLOW_APRICORN;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public ApricornSaplingBlock sapling() {
        switch (this) {
            case BLACK:
                return CobblemonBlocks.BLACK_APRICORN_SAPLING;
            case BLUE:
                return CobblemonBlocks.BLUE_APRICORN_SAPLING;
            case GREEN:
                return CobblemonBlocks.GREEN_APRICORN_SAPLING;
            case PINK:
                return CobblemonBlocks.PINK_APRICORN_SAPLING;
            case RED:
                return CobblemonBlocks.RED_APRICORN_SAPLING;
            case WHITE:
                return CobblemonBlocks.WHITE_APRICORN_SAPLING;
            case YELLOW:
                return CobblemonBlocks.YELLOW_APRICORN_SAPLING;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @NotNull
    public MapColor mapColor() {
        switch (this) {
            case BLACK:
                return MapColor.f_283927_;
            case BLUE:
                return MapColor.f_283743_;
            case GREEN:
                return MapColor.f_283784_;
            case PINK:
                return MapColor.f_283765_;
            case RED:
                return MapColor.f_283913_;
            case WHITE:
                return MapColor.f_283811_;
            case YELLOW:
                return MapColor.f_283832_;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }
}

