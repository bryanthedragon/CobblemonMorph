/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Triple
 *  kotlin.TuplesKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.JvmField
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.DefaultedRegistry
 *  net.minecraft.core.Registry
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.core.registries.Registries
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.valueproviders.IntProvider
 *  net.minecraft.util.valueproviders.UniformInt
 *  net.minecraft.world.effect.MobEffects
 *  net.minecraft.world.entity.EntityType
 *  net.minecraft.world.flag.FeatureFlag
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.ButtonBlock
 *  net.minecraft.world.level.block.DoorBlock
 *  net.minecraft.world.level.block.DropExperienceBlock
 *  net.minecraft.world.level.block.FenceBlock
 *  net.minecraft.world.level.block.FenceGateBlock
 *  net.minecraft.world.level.block.FireBlock
 *  net.minecraft.world.level.block.FlowerBlock
 *  net.minecraft.world.level.block.FlowerPotBlock
 *  net.minecraft.world.level.block.LeavesBlock
 *  net.minecraft.world.level.block.PressurePlateBlock
 *  net.minecraft.world.level.block.PressurePlateBlock$Sensitivity
 *  net.minecraft.world.level.block.RotatedPillarBlock
 *  net.minecraft.world.level.block.SlabBlock
 *  net.minecraft.world.level.block.SoundType
 *  net.minecraft.world.level.block.StairBlock
 *  net.minecraft.world.level.block.TrapDoorBlock
 *  net.minecraft.world.level.block.state.BlockBehaviour
 *  net.minecraft.world.level.block.state.BlockBehaviour$OffsetType
 *  net.minecraft.world.level.block.state.BlockBehaviour$Properties
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.BlockSetType
 *  net.minecraft.world.level.block.state.properties.NoteBlockInstrument
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.level.block.state.properties.WoodType
 *  net.minecraft.world.level.material.MapColor
 *  net.minecraft.world.level.material.PushReaction
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.apricorn.Apricorn;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ApricornBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.ApricornSaplingBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BigRootBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.CoinPouchBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.DisplayCaseBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.EnergyRootBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.FossilAnalyzerBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.HealingMachineBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MedicinalLeekBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MintBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MonitorBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.PCBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.PastureBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.RestorationTankBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.RevivalHerbBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.TumblestoneBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.VivichokeBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.chest.GildedChestBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.sign.CobblemonHangingSignBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.sign.CobblemonSignBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.sign.CobblemonWallHangingSignBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.sign.CobblemonWallSignBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.BlocksInvoker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.DoorBlockInvoker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.FireBlockInvoker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.PressurePlateBlockInvoker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.StairsBlockInvoker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.mixin.invoker.TrapdoorBlockInvoker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.PlatformRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Triple;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00f0\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b#\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b%\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b%\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010%\n\u0002\b\f\b\u00c6\u0002\u0018\u00002&\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0004\u0012\u0004\u0012\u00020\u00030\u0001B\u000b\b\u0002\u00a2\u0006\u0006\b\u00f9\u0002\u0010\u00fa\u0002J\u001f\u0010\n\u001a\u00020\t2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0019\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\f\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0017\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0016\u001a\u00020\u00132\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0015J\u0017\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0006\u001a\u00020\u0005H\u0002\u00a2\u0006\u0004\b\u0018\u0010\u0019J+\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u001b\u001a\u00020\u001a2\b\b\u0002\u0010\u001c\u001a\u00020\u001aH\u0002\u00a2\u0006\u0004\b\u001e\u0010\u001fJ-\u0010%\u001a\u00028\u0000\"\u0004\b\u0000\u0010 2\u0006\u0010!\u001a\u00028\u00002\u0006\u0010#\u001a\u00020\"2\u0006\u0010$\u001a\u00020\"H\u0002\u00a2\u0006\u0004\b%\u0010&J\u0019\u0010'\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\f\u00a2\u0006\u0004\b'\u0010\u0010J9\u0010,\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010(\u001a\u00020\"2\u0006\u0010)\u001a\u00020\"2\u0006\u0010*\u001a\u00020\"2\b\u0010+\u001a\u0004\u0018\u00010\u0003H\u0002\u00a2\u0006\u0004\b,\u0010-R\u0017\u0010.\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b.\u0010/\u001a\u0004\b0\u00101R\u0017\u00102\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b2\u0010/\u001a\u0004\b3\u00101R\u0017\u00105\u001a\u0002048\u0006\u00a2\u0006\f\n\u0004\b5\u00106\u001a\u0004\b7\u00108R\u001c\u0010;\u001a\n :*\u0004\u0018\u000109098\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b;\u0010<R\u001c\u0010>\u001a\n :*\u0004\u0018\u00010=0=8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b>\u0010?R\u0014\u0010A\u001a\u00020@8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bA\u0010BR\u0014\u0010D\u001a\u00020C8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bD\u0010ER\u0014\u0010G\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bG\u0010HR\u0014\u0010I\u001a\u00020\u00178\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bI\u0010JR\u0014\u0010K\u001a\u00020\u001d8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bK\u0010LR\u0014\u0010M\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bM\u0010NR\u001c\u0010P\u001a\n :*\u0004\u0018\u00010O0O8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bP\u0010QR\u0014\u0010S\u001a\u00020R8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bS\u0010TR\u0014\u0010V\u001a\u00020U8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bV\u0010WR\u001c\u0010Y\u001a\n :*\u0004\u0018\u00010X0X8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bY\u0010ZR\u001c\u0010\\\u001a\n :*\u0004\u0018\u00010[0[8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\\\u0010]R\u0014\u0010_\u001a\u00020^8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b_\u0010`R\u0014\u0010b\u001a\u00020a8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bb\u0010cR\u0014\u0010d\u001a\u00020\u001d8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bd\u0010LR\u001f\u0010f\u001a\n :*\u0004\u0018\u00010e0e8\u0006\u00a2\u0006\f\n\u0004\bf\u0010g\u001a\u0004\bh\u0010iR\u0017\u0010j\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\bj\u0010/\u001a\u0004\bk\u00101R\u0017\u0010l\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\bl\u0010/\u001a\u0004\bm\u00101R\u0017\u0010n\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\bn\u0010/\u001a\u0004\bo\u00101R\u0014\u0010q\u001a\u00020p8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bq\u0010rR\u0014\u0010s\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bs\u0010tR\u0014\u0010v\u001a\u00020u8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\bv\u0010wR\u0014\u0010y\u001a\u00020x8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\by\u0010zR\u0014\u0010{\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b{\u0010NR\u0014\u0010|\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b|\u0010NR\u0014\u0010}\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b}\u0010tR\u0014\u0010~\u001a\u00020u8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b~\u0010wR\u0014\u0010\u007f\u001a\u00020x8\u0006X\u0087\u0004\u00a2\u0006\u0006\n\u0004\b\u007f\u0010zR\u0018\u0010\u0081\u0001\u001a\u00030\u0080\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u0081\u0001\u0010\u0082\u0001R\u001a\u0010\u0083\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u0083\u0001\u0010/\u001a\u0005\b\u0084\u0001\u00101R\u001a\u0010\u0085\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u0085\u0001\u0010/\u001a\u0005\b\u0086\u0001\u00101R\u001a\u0010\u0087\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u0087\u0001\u0010/\u001a\u0005\b\u0088\u0001\u00101R\u001a\u0010\u0089\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u0089\u0001\u0010/\u001a\u0005\b\u008a\u0001\u00101R\u001a\u0010\u008b\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u008b\u0001\u0010/\u001a\u0005\b\u008c\u0001\u00101R\u001a\u0010\u008d\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u008d\u0001\u0010/\u001a\u0005\b\u008e\u0001\u00101R\u001a\u0010\u008f\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u008f\u0001\u0010/\u001a\u0005\b\u0090\u0001\u00101R\u001a\u0010\u0091\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u0091\u0001\u0010/\u001a\u0005\b\u0092\u0001\u00101R\u001a\u0010\u0093\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u0093\u0001\u0010/\u001a\u0005\b\u0094\u0001\u00101R\u001a\u0010\u0095\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u0095\u0001\u0010/\u001a\u0005\b\u0096\u0001\u00101R\u0018\u0010\u0097\u0001\u001a\u00030\u0080\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u0097\u0001\u0010\u0082\u0001R\u0017\u0010\u0098\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u0098\u0001\u0010\u0099\u0001R\u0017\u0010\u009a\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u009a\u0001\u0010\u0099\u0001R\u0017\u0010\u009b\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u009b\u0001\u0010\u0099\u0001R\u0017\u0010\u009c\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u009c\u0001\u0010\u0099\u0001R\u0017\u0010\u009d\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u009d\u0001\u0010\u0099\u0001R\u0017\u0010\u009e\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u009e\u0001\u0010\u0099\u0001R\u0017\u0010\u009f\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u009f\u0001\u0010\u0099\u0001R\u0017\u0010\u00a0\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00a0\u0001\u0010\u0099\u0001R\u0017\u0010\u00a1\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00a1\u0001\u0010\u0099\u0001R\u0017\u0010\u00a2\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00a2\u0001\u0010\u0099\u0001R\u0017\u0010\u00a3\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00a3\u0001\u0010\u0099\u0001R\u0018\u0010\u00a5\u0001\u001a\u00030\u00a4\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00a5\u0001\u0010\u00a6\u0001R\u0017\u0010\u00a7\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00a7\u0001\u0010\u0099\u0001R\u001a\u0010\u00a8\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00a8\u0001\u0010/\u001a\u0005\b\u00a9\u0001\u00101R\u0017\u0010\u00aa\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00aa\u0001\u0010\u0099\u0001R\u0018\u0010\u00ac\u0001\u001a\u00030\u00ab\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00ac\u0001\u0010\u00ad\u0001R\u001a\u0010\u00ae\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00ae\u0001\u0010/\u001a\u0005\b\u00af\u0001\u00101R\u001a\u0010\u00b0\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00b0\u0001\u0010/\u001a\u0005\b\u00b1\u0001\u00101R\u0017\u0010\u00b2\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00b2\u0001\u0010\u0099\u0001R\u0018\u0010\u00b4\u0001\u001a\u00030\u00b3\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00b4\u0001\u0010\u00b5\u0001R\u001a\u0010\u00b6\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00b6\u0001\u0010/\u001a\u0005\b\u00b7\u0001\u00101R\u0016\u0010\u00b8\u0001\u001a\u00020x8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b8\u0001\u0010zR\u0016\u0010\u00b9\u0001\u001a\u00020x8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00b9\u0001\u0010zR\u0016\u0010\u00ba\u0001\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ba\u0001\u0010tR\u0016\u0010\u00bb\u0001\u001a\u00020u8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00bb\u0001\u0010wR\u0016\u0010\u00bc\u0001\u001a\u00020x8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00bc\u0001\u0010zR\u0018\u0010\u00bd\u0001\u001a\u00030\u0080\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00bd\u0001\u0010\u0082\u0001R\u001a\u0010\u00be\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00be\u0001\u0010/\u001a\u0005\b\u00bf\u0001\u00101R\u001a\u0010\u00c0\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00c0\u0001\u0010/\u001a\u0005\b\u00c1\u0001\u00101R\u0018\u0010\u00c3\u0001\u001a\u00030\u00c2\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00c3\u0001\u0010\u00c4\u0001R\u001a\u0010\u00c5\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00c5\u0001\u0010/\u001a\u0005\b\u00c6\u0001\u00101R\u001a\u0010\u00c7\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00c7\u0001\u0010/\u001a\u0005\b\u00c8\u0001\u00101R\u001a\u0010\u00c9\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00c9\u0001\u0010/\u001a\u0005\b\u00ca\u0001\u00101R\u0017\u0010\u00cb\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00cb\u0001\u0010\u0099\u0001R\u001a\u0010\u00cc\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00cc\u0001\u0010/\u001a\u0005\b\u00cd\u0001\u00101R\u001a\u0010\u00ce\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00ce\u0001\u0010/\u001a\u0005\b\u00cf\u0001\u00101R\u001a\u0010\u00d0\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00d0\u0001\u0010/\u001a\u0005\b\u00d1\u0001\u00101R\u001a\u0010\u00d2\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00d2\u0001\u0010/\u001a\u0005\b\u00d3\u0001\u00101R\u001a\u0010\u00d4\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00d4\u0001\u0010/\u001a\u0005\b\u00d5\u0001\u00101R\u001a\u0010\u00d6\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00d6\u0001\u0010/\u001a\u0005\b\u00d7\u0001\u00101R\u0016\u0010\u00d8\u0001\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d8\u0001\u0010NR\u0016\u0010\u00d9\u0001\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d9\u0001\u0010NR\u0016\u0010\u00da\u0001\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00da\u0001\u0010NR\u0017\u0010\u00db\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00db\u0001\u0010\u0099\u0001R\u001a\u0010\u00dc\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00dc\u0001\u0010/\u001a\u0005\b\u00dd\u0001\u00101R\u001a\u0010\u00de\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00de\u0001\u0010/\u001a\u0005\b\u00df\u0001\u00101R\u001a\u0010\u00e0\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00e0\u0001\u0010/\u001a\u0005\b\u00e1\u0001\u00101R\u001a\u0010\u00e2\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00e2\u0001\u0010/\u001a\u0005\b\u00e3\u0001\u00101R\u001a\u0010\u00e4\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00e4\u0001\u0010/\u001a\u0005\b\u00e5\u0001\u00101R\u001a\u0010\u00e6\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00e6\u0001\u0010/\u001a\u0005\b\u00e7\u0001\u00101R\u0018\u0010\u00e9\u0001\u001a\u00030\u00e8\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00e9\u0001\u0010\u00ea\u0001R\u0016\u0010\u00eb\u0001\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00eb\u0001\u0010NR\u0016\u0010\u00ec\u0001\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ec\u0001\u0010NR\u0016\u0010\u00ed\u0001\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ed\u0001\u0010NR\u001a\u0010\u00ee\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00ee\u0001\u0010/\u001a\u0005\b\u00ef\u0001\u00101R\u0018\u0010\u00f1\u0001\u001a\u00030\u00f0\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00f1\u0001\u0010\u00f2\u0001R\u0017\u0010\u00f3\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00f3\u0001\u0010\u0099\u0001R\u001a\u0010\u00f4\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00f4\u0001\u0010/\u001a\u0005\b\u00f5\u0001\u00101R\u0017\u0010\u00f6\u0001\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00f6\u0001\u0010\u0099\u0001R\u001a\u0010\u00f7\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00f7\u0001\u0010/\u001a\u0005\b\u00f8\u0001\u00101R\u001a\u0010\u00f9\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00f9\u0001\u0010/\u001a\u0005\b\u00fa\u0001\u00101R\u001a\u0010\u00fb\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00fb\u0001\u0010/\u001a\u0005\b\u00fc\u0001\u00101R\u001a\u0010\u00fd\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00fd\u0001\u0010/\u001a\u0005\b\u00fe\u0001\u00101R\u001a\u0010\u00ff\u0001\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00ff\u0001\u0010/\u001a\u0005\b\u0080\u0002\u00101R\u0018\u0010\u0082\u0002\u001a\u00030\u0081\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u0082\u0002\u0010\u0083\u0002R\u001a\u0010\u0084\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u0084\u0002\u0010/\u001a\u0005\b\u0085\u0002\u00101R\u0018\u0010\u0087\u0002\u001a\u00030\u0086\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u0087\u0002\u0010\u0088\u0002R\u001a\u0010\u0089\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u0089\u0002\u0010/\u001a\u0005\b\u008a\u0002\u00101R\u0018\u0010\u008c\u0002\u001a\u00030\u008b\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u008c\u0002\u0010\u008d\u0002R\u001a\u0010\u008e\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u008e\u0002\u0010/\u001a\u0005\b\u008f\u0002\u00101R\u001a\u0010\u0090\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u0090\u0002\u0010/\u001a\u0005\b\u0091\u0002\u00101R\u001a\u0010\u0092\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u0092\u0002\u0010/\u001a\u0005\b\u0093\u0002\u00101R\u0016\u0010\u0094\u0002\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0094\u0002\u0010tR\u0016\u0010\u0095\u0002\u001a\u00020u8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0095\u0002\u0010wR\u0016\u0010\u0096\u0002\u001a\u00020x8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u0096\u0002\u0010zR\u0018\u0010\u0097\u0002\u001a\u00030\u0080\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u0097\u0002\u0010\u0082\u0001R!\u0010\u0099\u0002\u001a\f :*\u0005\u0018\u00010\u0098\u00020\u0098\u00028\u0002X\u0082\u0004\u00a2\u0006\b\n\u0006\b\u0099\u0002\u0010\u009a\u0002R\u001a\u0010\u009b\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u009b\u0002\u0010/\u001a\u0005\b\u009c\u0002\u00101R!\u0010\u009e\u0002\u001a\f :*\u0005\u0018\u00010\u009d\u00020\u009d\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u009e\u0002\u0010\u009f\u0002R\u001a\u0010\u00a0\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00a0\u0002\u0010/\u001a\u0005\b\u00a1\u0002\u00101R\u001a\u0010\u00a2\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00a2\u0002\u0010/\u001a\u0005\b\u00a3\u0002\u00101R\u001a\u0010\u00a4\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00a4\u0002\u0010/\u001a\u0005\b\u00a5\u0002\u00101R\u001a\u0010\u00a6\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00a6\u0002\u0010/\u001a\u0005\b\u00a7\u0002\u00101R\u0016\u0010\u00a8\u0002\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a8\u0002\u0010tR\u0016\u0010\u00a9\u0002\u001a\u00020u8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00a9\u0002\u0010wR\u001d\u0010\u00aa\u0002\u001a\u00030\u0080\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u00aa\u0002\u0010\u0082\u0001\u001a\u0006\b\u00ab\u0002\u0010\u00ac\u0002R\u0018\u0010\u00ae\u0002\u001a\u00030\u00ad\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00ae\u0002\u0010\u00af\u0002R\u0018\u0010\u00b0\u0002\u001a\u00030\u00ad\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00b0\u0002\u0010\u00af\u0002R\u0018\u0010\u00b2\u0002\u001a\u00030\u00b1\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00b2\u0002\u0010\u00b3\u0002R\u0018\u0010\u00b5\u0002\u001a\u00030\u00b4\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00b5\u0002\u0010\u00b6\u0002R\u001a\u0010\u00b7\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00b7\u0002\u0010/\u001a\u0005\b\u00b8\u0002\u00101R\u001a\u0010\u00b9\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00b9\u0002\u0010/\u001a\u0005\b\u00ba\u0002\u00101R\u001a\u0010\u00bb\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00bb\u0002\u0010/\u001a\u0005\b\u00bc\u0002\u00101R\u001a\u0010\u00bd\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00bd\u0002\u0010/\u001a\u0005\b\u00be\u0002\u00101R\u0017\u0010\u00bf\u0002\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00bf\u0002\u0010\u0099\u0001R\u001a\u0010\u00c0\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00c0\u0002\u0010/\u001a\u0005\b\u00c1\u0002\u00101R\u001a\u0010\u00c2\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00c2\u0002\u0010/\u001a\u0005\b\u00c3\u0002\u00101R\u0016\u0010\u00c4\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c4\u0002\u0010NR\u0016\u0010\u00c5\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c5\u0002\u0010NR\u0016\u0010\u00c6\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c6\u0002\u0010NR\u0016\u0010\u00c7\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c7\u0002\u0010NR\u0016\u0010\u00c8\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00c8\u0002\u0010NR\u001a\u0010\u00c9\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00c9\u0002\u0010/\u001a\u0005\b\u00ca\u0002\u00101R\u001a\u0010\u00cb\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00cb\u0002\u0010/\u001a\u0005\b\u00cc\u0002\u00101R\u0016\u0010\u00cd\u0002\u001a\u00020\u001d8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00cd\u0002\u0010LR\u0016\u0010\u00ce\u0002\u001a\u00020\u001d8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ce\u0002\u0010LR\u0017\u0010\u00cf\u0002\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00cf\u0002\u0010\u0099\u0001R\u001a\u0010\u00d0\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00d0\u0002\u0010/\u001a\u0005\b\u00d1\u0002\u00101R\u001a\u0010\u00d2\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00d2\u0002\u0010/\u001a\u0005\b\u00d3\u0002\u00101R\u0017\u0010\u00d4\u0002\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00d4\u0002\u0010\u0099\u0001R\u0017\u0010\u00d5\u0002\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00d5\u0002\u0010\u0099\u0001R\u001a\u0010\u00d6\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00d6\u0002\u0010/\u001a\u0005\b\u00d7\u0002\u00101R\u0016\u0010\u00d8\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d8\u0002\u0010NR\u0016\u0010\u00d9\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00d9\u0002\u0010NR\u0018\u0010\u00db\u0002\u001a\u00030\u00da\u00028\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00db\u0002\u0010\u00dc\u0002R\u001a\u0010\u00dd\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00dd\u0002\u0010/\u001a\u0005\b\u00de\u0002\u00101R\u0017\u0010\u00df\u0002\u001a\u00020\u00138\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00df\u0002\u0010\u0099\u0001R\u001a\u0010\u00e0\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00e0\u0002\u0010/\u001a\u0005\b\u00e1\u0002\u00101R\u001a\u0010\u00e2\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00e2\u0002\u0010/\u001a\u0005\b\u00e3\u0002\u00101R\u0016\u0010\u00e4\u0002\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e4\u0002\u0010tR\u0016\u0010\u00e5\u0002\u001a\u00020u8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e5\u0002\u0010wR\u0016\u0010\u00e6\u0002\u001a\u00020x8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00e6\u0002\u0010zR\u0018\u0010\u00e7\u0002\u001a\u00030\u0080\u00018\u0006X\u0087\u0004\u00a2\u0006\b\n\u0006\b\u00e7\u0002\u0010\u0082\u0001R\u001a\u0010\u00e8\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00e8\u0002\u0010/\u001a\u0005\b\u00e9\u0002\u00101R\u001a\u0010\u00ea\u0002\u001a\u00020\u000e8\u0006\u00a2\u0006\u000e\n\u0005\b\u00ea\u0002\u0010/\u001a\u0005\b\u00eb\u0002\u00101R\u0016\u0010\u00ec\u0002\u001a\u00020\t8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ec\u0002\u0010tR\u0016\u0010\u00ed\u0002\u001a\u00020u8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ed\u0002\u0010wR\u0016\u0010\u00ee\u0002\u001a\u00020x8\u0006X\u0087\u0004\u00a2\u0006\u0007\n\u0005\b\u00ee\u0002\u0010zR\"\u0010\u000f\u001a\u000f\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e0\u00ef\u00028\u0002X\u0082\u0004\u00a2\u0006\u0007\n\u0005\b\u000f\u0010\u00f0\u0002R%\u0010\u00f1\u0002\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0016X\u0096\u0004\u00a2\u0006\u0010\n\u0006\b\u00f1\u0002\u0010\u00f2\u0002\u001a\u0006\b\u00f3\u0002\u0010\u00f4\u0002R+\u0010\u00f5\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u00048\u0016X\u0096\u0004\u00a2\u0006\u0010\n\u0006\b\u00f5\u0002\u0010\u00f6\u0002\u001a\u0006\b\u00f7\u0002\u0010\u00f8\u0002\u00a8\u0006\u00fb\u0002"}, d2={"Lcom/cobblemon/mod/common/CobblemonBlocks;", "Lcom/cobblemon/mod/common/platform/PlatformRegistry;", "Lnet/minecraft/core/Registry;", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/resources/ResourceKey;", "", "name", "Lcom/cobblemon/mod/common/api/apricorn/Apricorn;", "apricorn", "Lcom/cobblemon/mod/common/block/ApricornBlock;", "apricornBlock", "(Ljava/lang/String;Lcom/cobblemon/mod/common/api/apricorn/Apricorn;)Lcom/cobblemon/mod/common/block/ApricornBlock;", "", "Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/block/BerryBlock;", "berries", "()Ljava/util/Map;", "berryBlock", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/block/BerryBlock;", "Lnet/minecraft/world/level/block/DropExperienceBlock;", "deepslateEvolutionStoneOre", "(Ljava/lang/String;)Lnet/minecraft/world/level/block/DropExperienceBlock;", "evolutionStoneOre", "Lnet/minecraft/world/level/block/LeavesBlock;", "leaves", "(Ljava/lang/String;)Lnet/minecraft/world/level/block/LeavesBlock;", "Lnet/minecraft/world/level/material/MapColor;", "arg", "arg2", "Lnet/minecraft/world/level/block/RotatedPillarBlock;", "log", "(Ljava/lang/String;Lnet/minecraft/world/level/material/MapColor;Lnet/minecraft/world/level/material/MapColor;)Lnet/minecraft/world/level/block/RotatedPillarBlock;", "E", "block", "", "burnChance", "spreadChance", "setFlammable", "(Ljava/lang/Object;II)Ljava/lang/Object;", "strippedBlocks", "stage", "height", "xzOffset", "nextStage", "tumblestoneBlock", "(Ljava/lang/String;IIILnet/minecraft/world/level/block/Block;)Lnet/minecraft/world/level/block/Block;", "AGUAV_BERRY", "Lcom/cobblemon/mod/common/block/BerryBlock;", "getAGUAV_BERRY", "()Lcom/cobblemon/mod/common/block/BerryBlock;", "APICOT_BERRY", "getAPICOT_BERRY", "Lnet/minecraft/world/level/block/state/properties/BlockSetType;", "APRICORN_BLOCK_SET_TYPE", "Lnet/minecraft/world/level/block/state/properties/BlockSetType;", "getAPRICORN_BLOCK_SET_TYPE", "()Lnet/minecraft/world/level/block/state/properties/BlockSetType;", "Lnet/minecraft/world/level/block/ButtonBlock;", "kotlin.jvm.PlatformType", "APRICORN_BUTTON", "Lnet/minecraft/world/level/block/ButtonBlock;", "Lnet/minecraft/world/level/block/DoorBlock;", "APRICORN_DOOR", "Lnet/minecraft/world/level/block/DoorBlock;", "Lnet/minecraft/world/level/block/FenceBlock;", "APRICORN_FENCE", "Lnet/minecraft/world/level/block/FenceBlock;", "Lnet/minecraft/world/level/block/FenceGateBlock;", "APRICORN_FENCE_GATE", "Lnet/minecraft/world/level/block/FenceGateBlock;", "Lcom/cobblemon/mod/common/block/sign/CobblemonHangingSignBlock;", "APRICORN_HANGING_SIGN", "Lcom/cobblemon/mod/common/block/sign/CobblemonHangingSignBlock;", "APRICORN_LEAVES", "Lnet/minecraft/world/level/block/LeavesBlock;", "APRICORN_LOG", "Lnet/minecraft/world/level/block/RotatedPillarBlock;", "APRICORN_PLANKS", "Lnet/minecraft/world/level/block/Block;", "Lnet/minecraft/world/level/block/PressurePlateBlock;", "APRICORN_PRESSURE_PLATE", "Lnet/minecraft/world/level/block/PressurePlateBlock;", "Lcom/cobblemon/mod/common/block/sign/CobblemonSignBlock;", "APRICORN_SIGN", "Lcom/cobblemon/mod/common/block/sign/CobblemonSignBlock;", "Lnet/minecraft/world/level/block/SlabBlock;", "APRICORN_SLAB", "Lnet/minecraft/world/level/block/SlabBlock;", "Lnet/minecraft/world/level/block/StairBlock;", "APRICORN_STAIRS", "Lnet/minecraft/world/level/block/StairBlock;", "Lnet/minecraft/world/level/block/TrapDoorBlock;", "APRICORN_TRAPDOOR", "Lnet/minecraft/world/level/block/TrapDoorBlock;", "Lcom/cobblemon/mod/common/block/sign/CobblemonWallHangingSignBlock;", "APRICORN_WALL_HANGING_SIGN", "Lcom/cobblemon/mod/common/block/sign/CobblemonWallHangingSignBlock;", "Lcom/cobblemon/mod/common/block/sign/CobblemonWallSignBlock;", "APRICORN_WALL_SIGN", "Lcom/cobblemon/mod/common/block/sign/CobblemonWallSignBlock;", "APRICORN_WOOD", "Lnet/minecraft/world/level/block/state/properties/WoodType;", "APRICORN_WOOD_TYPE", "Lnet/minecraft/world/level/block/state/properties/WoodType;", "getAPRICORN_WOOD_TYPE", "()Lnet/minecraft/world/level/block/state/properties/WoodType;", "ASPEAR_BERRY", "getASPEAR_BERRY", "BABIRI_BERRY", "getBABIRI_BERRY", "BELUE_BERRY", "getBELUE_BERRY", "Lcom/cobblemon/mod/common/block/BigRootBlock;", "BIG_ROOT", "Lcom/cobblemon/mod/common/block/BigRootBlock;", "BLACK_APRICORN", "Lcom/cobblemon/mod/common/block/ApricornBlock;", "Lcom/cobblemon/mod/common/block/ApricornSaplingBlock;", "BLACK_APRICORN_SAPLING", "Lcom/cobblemon/mod/common/block/ApricornSaplingBlock;", "Lcom/cobblemon/mod/common/block/chest/GildedChestBlock;", "BLACK_GILDED_CHEST", "Lcom/cobblemon/mod/common/block/chest/GildedChestBlock;", "BLACK_TUMBLESTONE_BLOCK", "BLACK_TUMBLESTONE_CLUSTER", "BLUE_APRICORN", "BLUE_APRICORN_SAPLING", "BLUE_GILDED_CHEST", "Lcom/cobblemon/mod/common/block/MintBlock;", "BLUE_MINT", "Lcom/cobblemon/mod/common/block/MintBlock;", "BLUK_BERRY", "getBLUK_BERRY", "CHARTI_BERRY", "getCHARTI_BERRY", "CHERI_BERRY", "getCHERI_BERRY", "CHESTO_BERRY", "getCHESTO_BERRY", "CHILAN_BERRY", "getCHILAN_BERRY", "CHOPLE_BERRY", "getCHOPLE_BERRY", "COBA_BERRY", "getCOBA_BERRY", "COLBUR_BERRY", "getCOLBUR_BERRY", "CORNN_BERRY", "getCORNN_BERRY", "CUSTAP_BERRY", "getCUSTAP_BERRY", "CYAN_MINT", "DAWN_STONE_ORE", "Lnet/minecraft/world/level/block/DropExperienceBlock;", "DEEPSLATE_DAWN_STONE_ORE", "DEEPSLATE_DUSK_STONE_ORE", "DEEPSLATE_FIRE_STONE_ORE", "DEEPSLATE_ICE_STONE_ORE", "DEEPSLATE_LEAF_STONE_ORE", "DEEPSLATE_MOON_STONE_ORE", "DEEPSLATE_SHINY_STONE_ORE", "DEEPSLATE_SUN_STONE_ORE", "DEEPSLATE_THUNDER_STONE_ORE", "DEEPSLATE_WATER_STONE_ORE", "Lcom/cobblemon/mod/common/block/DisplayCaseBlock;", "DISPLAY_CASE", "Lcom/cobblemon/mod/common/block/DisplayCaseBlock;", "DRIPSTONE_MOON_STONE_ORE", "DURIN_BERRY", "getDURIN_BERRY", "DUSK_STONE_ORE", "Lcom/cobblemon/mod/common/block/EnergyRootBlock;", "ENERGY_ROOT", "Lcom/cobblemon/mod/common/block/EnergyRootBlock;", "ENIGMA_BERRY", "getENIGMA_BERRY", "FIGY_BERRY", "getFIGY_BERRY", "FIRE_STONE_ORE", "Lcom/cobblemon/mod/common/block/FossilAnalyzerBlock;", "FOSSIL_ANALYZER", "Lcom/cobblemon/mod/common/block/FossilAnalyzerBlock;", "GANLON_BERRY", "getGANLON_BERRY", "GILDED_CHEST", "GIMMIGHOUL_CHEST", "GREEN_APRICORN", "GREEN_APRICORN_SAPLING", "GREEN_GILDED_CHEST", "GREEN_MINT", "GREPA_BERRY", "getGREPA_BERRY", "HABAN_BERRY", "getHABAN_BERRY", "Lcom/cobblemon/mod/common/block/HealingMachineBlock;", "HEALING_MACHINE", "Lcom/cobblemon/mod/common/block/HealingMachineBlock;", "HONDEW_BERRY", "getHONDEW_BERRY", "HOPO_BERRY", "getHOPO_BERRY", "IAPAPA_BERRY", "getIAPAPA_BERRY", "ICE_STONE_ORE", "JABOCA_BERRY", "getJABOCA_BERRY", "KASIB_BERRY", "getKASIB_BERRY", "KEBIA_BERRY", "getKEBIA_BERRY", "KEE_BERRY", "getKEE_BERRY", "KELPSY_BERRY", "getKELPSY_BERRY", "LANSAT_BERRY", "getLANSAT_BERRY", "LARGE_BUDDING_BLACK_TUMBLESTONE", "LARGE_BUDDING_SKY_TUMBLESTONE", "LARGE_BUDDING_TUMBLESTONE", "LEAF_STONE_ORE", "LEPPA_BERRY", "getLEPPA_BERRY", "LIECHI_BERRY", "getLIECHI_BERRY", "LUM_BERRY", "getLUM_BERRY", "MAGOST_BERRY", "getMAGOST_BERRY", "MAGO_BERRY", "getMAGO_BERRY", "MARANGA_BERRY", "getMARANGA_BERRY", "Lcom/cobblemon/mod/common/block/MedicinalLeekBlock;", "MEDICINAL_LEEK", "Lcom/cobblemon/mod/common/block/MedicinalLeekBlock;", "MEDIUM_BUDDING_BLACK_TUMBLESTONE", "MEDIUM_BUDDING_SKY_TUMBLESTONE", "MEDIUM_BUDDING_TUMBLESTONE", "MICLE_BERRY", "getMICLE_BERRY", "Lcom/cobblemon/mod/common/block/MonitorBlock;", "MONITOR", "Lcom/cobblemon/mod/common/block/MonitorBlock;", "MOON_STONE_ORE", "NANAB_BERRY", "getNANAB_BERRY", "NETHER_FIRE_STONE_ORE", "NOMEL_BERRY", "getNOMEL_BERRY", "OCCA_BERRY", "getOCCA_BERRY", "ORAN_BERRY", "getORAN_BERRY", "PAMTRE_BERRY", "getPAMTRE_BERRY", "PASSHO_BERRY", "getPASSHO_BERRY", "Lcom/cobblemon/mod/common/block/PastureBlock;", "PASTURE", "Lcom/cobblemon/mod/common/block/PastureBlock;", "PAYAPA_BERRY", "getPAYAPA_BERRY", "Lcom/cobblemon/mod/common/block/PCBlock;", "PC", "Lcom/cobblemon/mod/common/block/PCBlock;", "PECHA_BERRY", "getPECHA_BERRY", "Lnet/minecraft/world/level/block/FlowerBlock;", "PEP_UP_FLOWER", "Lnet/minecraft/world/level/block/FlowerBlock;", "PERSIM_BERRY", "getPERSIM_BERRY", "PETAYA_BERRY", "getPETAYA_BERRY", "PINAP_BERRY", "getPINAP_BERRY", "PINK_APRICORN", "PINK_APRICORN_SAPLING", "PINK_GILDED_CHEST", "PINK_MINT", "Lnet/minecraft/block/AbstractBlock$Settings;", "PLANT_PROPERTIES", "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;", "POMEG_BERRY", "getPOMEG_BERRY", "Lnet/minecraft/world/level/block/FlowerPotBlock;", "POTTED_PEP_UP_FLOWER", "Lnet/minecraft/world/level/block/FlowerPotBlock;", "QUALOT_BERRY", "getQUALOT_BERRY", "RABUTA_BERRY", "getRABUTA_BERRY", "RAWST_BERRY", "getRAWST_BERRY", "RAZZ_BERRY", "getRAZZ_BERRY", "RED_APRICORN", "RED_APRICORN_SAPLING", "RED_MINT", "getRED_MINT", "()Lcom/cobblemon/mod/common/block/MintBlock;", "Lcom/cobblemon/mod/common/block/CoinPouchBlock;", "RELIC_COIN_POUCH", "Lcom/cobblemon/mod/common/block/CoinPouchBlock;", "RELIC_COIN_SACK", "Lcom/cobblemon/mod/common/block/RestorationTankBlock;", "RESTORATION_TANK", "Lcom/cobblemon/mod/common/block/RestorationTankBlock;", "Lcom/cobblemon/mod/common/block/RevivalHerbBlock;", "REVIVAL_HERB", "Lcom/cobblemon/mod/common/block/RevivalHerbBlock;", "RINDO_BERRY", "getRINDO_BERRY", "ROSELI_BERRY", "getROSELI_BERRY", "ROWAP_BERRY", "getROWAP_BERRY", "SALAC_BERRY", "getSALAC_BERRY", "SHINY_STONE_ORE", "SHUCA_BERRY", "getSHUCA_BERRY", "SITRUS_BERRY", "getSITRUS_BERRY", "SKY_TUMBLESTONE_BLOCK", "SKY_TUMBLESTONE_CLUSTER", "SMALL_BUDDING_BLACK_TUMBLESTONE", "SMALL_BUDDING_SKY_TUMBLESTONE", "SMALL_BUDDING_TUMBLESTONE", "SPELON_BERRY", "getSPELON_BERRY", "STARF_BERRY", "getSTARF_BERRY", "STRIPPED_APRICORN_LOG", "STRIPPED_APRICORN_WOOD", "SUN_STONE_ORE", "TAMATO_BERRY", "getTAMATO_BERRY", "TANGA_BERRY", "getTANGA_BERRY", "TERRACOTTA_SUN_STONE_ORE", "THUNDER_STONE_ORE", "TOUGA_BERRY", "getTOUGA_BERRY", "TUMBLESTONE_BLOCK", "TUMBLESTONE_CLUSTER", "Lcom/cobblemon/mod/common/block/VivichokeBlock;", "VIVICHOKE_SEEDS", "Lcom/cobblemon/mod/common/block/VivichokeBlock;", "WACAN_BERRY", "getWACAN_BERRY", "WATER_STONE_ORE", "WATMEL_BERRY", "getWATMEL_BERRY", "WEPEAR_BERRY", "getWEPEAR_BERRY", "WHITE_APRICORN", "WHITE_APRICORN_SAPLING", "WHITE_GILDED_CHEST", "WHITE_MINT", "WIKI_BERRY", "getWIKI_BERRY", "YACHE_BERRY", "getYACHE_BERRY", "YELLOW_APRICORN", "YELLOW_APRICORN_SAPLING", "YELLOW_GILDED_CHEST", "", "Ljava/util/Map;", "registry", "Lnet/minecraft/core/Registry;", "getRegistry", "()Lnet/minecraft/core/Registry;", "registryKey", "Lnet/minecraft/resources/ResourceKey;", "getRegistryKey", "()Lnet/minecraft/resources/ResourceKey;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nCobblemonBlocks.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonBlocks.kt\ncom/cobblemon/mod/common/CobblemonBlocks\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,596:1\n1#2:597\n*E\n"})
public final class CobblemonBlocks
extends PlatformRegistry<Registry<Block>, ResourceKey<Registry<Block>>, Block> {
    @NotNull
    public static final CobblemonBlocks INSTANCE;
    @NotNull
    private static final Registry<Block> registry;
    @NotNull
    private static final ResourceKey<Registry<Block>> registryKey;
    @NotNull
    private static final BlockSetType APRICORN_BLOCK_SET_TYPE;
    private static final WoodType APRICORN_WOOD_TYPE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock DAWN_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock DUSK_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock FIRE_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock NETHER_FIRE_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock ICE_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock LEAF_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock MOON_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock DRIPSTONE_MOON_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock SHINY_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock SUN_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock TERRACOTTA_SUN_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock THUNDER_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock WATER_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock DEEPSLATE_DAWN_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock DEEPSLATE_DUSK_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock DEEPSLATE_FIRE_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock DEEPSLATE_ICE_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock DEEPSLATE_LEAF_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock DEEPSLATE_MOON_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock DEEPSLATE_SHINY_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock DEEPSLATE_SUN_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock DEEPSLATE_THUNDER_STONE_ORE;
    @JvmField
    @NotNull
    public static final DropExperienceBlock DEEPSLATE_WATER_STONE_ORE;
    @JvmField
    @NotNull
    public static final RotatedPillarBlock APRICORN_LOG;
    @JvmField
    @NotNull
    public static final RotatedPillarBlock STRIPPED_APRICORN_LOG;
    @JvmField
    @NotNull
    public static final RotatedPillarBlock APRICORN_WOOD;
    @JvmField
    @NotNull
    public static final RotatedPillarBlock STRIPPED_APRICORN_WOOD;
    @JvmField
    @NotNull
    public static final Block APRICORN_PLANKS;
    @JvmField
    @NotNull
    public static final LeavesBlock APRICORN_LEAVES;
    @JvmField
    @NotNull
    public static final FenceBlock APRICORN_FENCE;
    @JvmField
    @NotNull
    public static final FenceGateBlock APRICORN_FENCE_GATE;
    @JvmField
    public static final ButtonBlock APRICORN_BUTTON;
    @JvmField
    public static final PressurePlateBlock APRICORN_PRESSURE_PLATE;
    @JvmField
    @NotNull
    public static final CobblemonSignBlock APRICORN_SIGN;
    @JvmField
    @NotNull
    public static final CobblemonWallSignBlock APRICORN_WALL_SIGN;
    @JvmField
    @NotNull
    public static final CobblemonHangingSignBlock APRICORN_HANGING_SIGN;
    @JvmField
    @NotNull
    public static final CobblemonWallHangingSignBlock APRICORN_WALL_HANGING_SIGN;
    @JvmField
    @NotNull
    public static final SlabBlock APRICORN_SLAB;
    @JvmField
    public static final StairBlock APRICORN_STAIRS;
    @JvmField
    public static final DoorBlock APRICORN_DOOR;
    @JvmField
    public static final TrapDoorBlock APRICORN_TRAPDOOR;
    private static final BlockBehaviour.Properties PLANT_PROPERTIES;
    @JvmField
    @NotNull
    public static final ApricornSaplingBlock BLACK_APRICORN_SAPLING;
    @JvmField
    @NotNull
    public static final ApricornSaplingBlock BLUE_APRICORN_SAPLING;
    @JvmField
    @NotNull
    public static final ApricornSaplingBlock GREEN_APRICORN_SAPLING;
    @JvmField
    @NotNull
    public static final ApricornSaplingBlock PINK_APRICORN_SAPLING;
    @JvmField
    @NotNull
    public static final ApricornSaplingBlock RED_APRICORN_SAPLING;
    @JvmField
    @NotNull
    public static final ApricornSaplingBlock WHITE_APRICORN_SAPLING;
    @JvmField
    @NotNull
    public static final ApricornSaplingBlock YELLOW_APRICORN_SAPLING;
    @JvmField
    @NotNull
    public static final MedicinalLeekBlock MEDICINAL_LEEK;
    @JvmField
    @NotNull
    public static final EnergyRootBlock ENERGY_ROOT;
    @JvmField
    @NotNull
    public static final BigRootBlock BIG_ROOT;
    @JvmField
    @NotNull
    public static final RevivalHerbBlock REVIVAL_HERB;
    @JvmField
    @NotNull
    public static final Block TUMBLESTONE_CLUSTER;
    @JvmField
    @NotNull
    public static final Block LARGE_BUDDING_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final Block MEDIUM_BUDDING_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final Block SMALL_BUDDING_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final Block SKY_TUMBLESTONE_CLUSTER;
    @JvmField
    @NotNull
    public static final Block LARGE_BUDDING_SKY_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final Block MEDIUM_BUDDING_SKY_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final Block SMALL_BUDDING_SKY_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final Block BLACK_TUMBLESTONE_CLUSTER;
    @JvmField
    @NotNull
    public static final Block LARGE_BUDDING_BLACK_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final Block MEDIUM_BUDDING_BLACK_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final Block SMALL_BUDDING_BLACK_TUMBLESTONE;
    @JvmField
    @NotNull
    public static final Block TUMBLESTONE_BLOCK;
    @JvmField
    @NotNull
    public static final Block SKY_TUMBLESTONE_BLOCK;
    @JvmField
    @NotNull
    public static final Block BLACK_TUMBLESTONE_BLOCK;
    @JvmField
    @NotNull
    public static final ApricornBlock BLACK_APRICORN;
    @JvmField
    @NotNull
    public static final ApricornBlock BLUE_APRICORN;
    @JvmField
    @NotNull
    public static final ApricornBlock GREEN_APRICORN;
    @JvmField
    @NotNull
    public static final ApricornBlock PINK_APRICORN;
    @JvmField
    @NotNull
    public static final ApricornBlock RED_APRICORN;
    @JvmField
    @NotNull
    public static final ApricornBlock WHITE_APRICORN;
    @JvmField
    @NotNull
    public static final ApricornBlock YELLOW_APRICORN;
    @JvmField
    @NotNull
    public static final CoinPouchBlock RELIC_COIN_POUCH;
    @JvmField
    @NotNull
    public static final CoinPouchBlock RELIC_COIN_SACK;
    @JvmField
    @NotNull
    public static final GildedChestBlock GILDED_CHEST;
    @JvmField
    @NotNull
    public static final GildedChestBlock BLUE_GILDED_CHEST;
    @JvmField
    @NotNull
    public static final GildedChestBlock BLACK_GILDED_CHEST;
    @JvmField
    @NotNull
    public static final GildedChestBlock YELLOW_GILDED_CHEST;
    @JvmField
    @NotNull
    public static final GildedChestBlock WHITE_GILDED_CHEST;
    @JvmField
    @NotNull
    public static final GildedChestBlock GREEN_GILDED_CHEST;
    @JvmField
    @NotNull
    public static final GildedChestBlock PINK_GILDED_CHEST;
    @JvmField
    @NotNull
    public static final GildedChestBlock GIMMIGHOUL_CHEST;
    @JvmField
    @NotNull
    public static final MonitorBlock MONITOR;
    @JvmField
    @NotNull
    public static final FossilAnalyzerBlock FOSSIL_ANALYZER;
    @JvmField
    @NotNull
    public static final RestorationTankBlock RESTORATION_TANK;
    @JvmField
    @NotNull
    public static final HealingMachineBlock HEALING_MACHINE;
    @JvmField
    @NotNull
    public static final PCBlock PC;
    @JvmField
    @NotNull
    public static final DisplayCaseBlock DISPLAY_CASE;
    @NotNull
    private static final MintBlock RED_MINT;
    @JvmField
    @NotNull
    public static final MintBlock BLUE_MINT;
    @JvmField
    @NotNull
    public static final MintBlock CYAN_MINT;
    @JvmField
    @NotNull
    public static final MintBlock PINK_MINT;
    @JvmField
    @NotNull
    public static final MintBlock GREEN_MINT;
    @JvmField
    @NotNull
    public static final MintBlock WHITE_MINT;
    @JvmField
    @NotNull
    public static final PastureBlock PASTURE;
    @JvmField
    @NotNull
    public static final VivichokeBlock VIVICHOKE_SEEDS;
    @JvmField
    @NotNull
    public static final FlowerBlock PEP_UP_FLOWER;
    @JvmField
    public static final FlowerPotBlock POTTED_PEP_UP_FLOWER;
    @NotNull
    private static final Map<ResourceLocation, BerryBlock> berries;
    @NotNull
    private static final BerryBlock AGUAV_BERRY;
    @NotNull
    private static final BerryBlock APICOT_BERRY;
    @NotNull
    private static final BerryBlock ASPEAR_BERRY;
    @NotNull
    private static final BerryBlock BABIRI_BERRY;
    @NotNull
    private static final BerryBlock BELUE_BERRY;
    @NotNull
    private static final BerryBlock BLUK_BERRY;
    @NotNull
    private static final BerryBlock CHARTI_BERRY;
    @NotNull
    private static final BerryBlock CHERI_BERRY;
    @NotNull
    private static final BerryBlock CHESTO_BERRY;
    @NotNull
    private static final BerryBlock CHILAN_BERRY;
    @NotNull
    private static final BerryBlock CHOPLE_BERRY;
    @NotNull
    private static final BerryBlock COBA_BERRY;
    @NotNull
    private static final BerryBlock COLBUR_BERRY;
    @NotNull
    private static final BerryBlock CORNN_BERRY;
    @NotNull
    private static final BerryBlock CUSTAP_BERRY;
    @NotNull
    private static final BerryBlock DURIN_BERRY;
    @NotNull
    private static final BerryBlock ENIGMA_BERRY;
    @NotNull
    private static final BerryBlock FIGY_BERRY;
    @NotNull
    private static final BerryBlock GANLON_BERRY;
    @NotNull
    private static final BerryBlock GREPA_BERRY;
    @NotNull
    private static final BerryBlock HABAN_BERRY;
    @NotNull
    private static final BerryBlock HONDEW_BERRY;
    @NotNull
    private static final BerryBlock HOPO_BERRY;
    @NotNull
    private static final BerryBlock IAPAPA_BERRY;
    @NotNull
    private static final BerryBlock JABOCA_BERRY;
    @NotNull
    private static final BerryBlock KASIB_BERRY;
    @NotNull
    private static final BerryBlock KEBIA_BERRY;
    @NotNull
    private static final BerryBlock KEE_BERRY;
    @NotNull
    private static final BerryBlock KELPSY_BERRY;
    @NotNull
    private static final BerryBlock LANSAT_BERRY;
    @NotNull
    private static final BerryBlock LEPPA_BERRY;
    @NotNull
    private static final BerryBlock LIECHI_BERRY;
    @NotNull
    private static final BerryBlock LUM_BERRY;
    @NotNull
    private static final BerryBlock MAGO_BERRY;
    @NotNull
    private static final BerryBlock MAGOST_BERRY;
    @NotNull
    private static final BerryBlock MARANGA_BERRY;
    @NotNull
    private static final BerryBlock MICLE_BERRY;
    @NotNull
    private static final BerryBlock NANAB_BERRY;
    @NotNull
    private static final BerryBlock NOMEL_BERRY;
    @NotNull
    private static final BerryBlock OCCA_BERRY;
    @NotNull
    private static final BerryBlock ORAN_BERRY;
    @NotNull
    private static final BerryBlock PAMTRE_BERRY;
    @NotNull
    private static final BerryBlock PASSHO_BERRY;
    @NotNull
    private static final BerryBlock PAYAPA_BERRY;
    @NotNull
    private static final BerryBlock PECHA_BERRY;
    @NotNull
    private static final BerryBlock PERSIM_BERRY;
    @NotNull
    private static final BerryBlock PETAYA_BERRY;
    @NotNull
    private static final BerryBlock PINAP_BERRY;
    @NotNull
    private static final BerryBlock POMEG_BERRY;
    @NotNull
    private static final BerryBlock QUALOT_BERRY;
    @NotNull
    private static final BerryBlock RABUTA_BERRY;
    @NotNull
    private static final BerryBlock RAWST_BERRY;
    @NotNull
    private static final BerryBlock RAZZ_BERRY;
    @NotNull
    private static final BerryBlock RINDO_BERRY;
    @NotNull
    private static final BerryBlock ROSELI_BERRY;
    @NotNull
    private static final BerryBlock ROWAP_BERRY;
    @NotNull
    private static final BerryBlock SALAC_BERRY;
    @NotNull
    private static final BerryBlock SHUCA_BERRY;
    @NotNull
    private static final BerryBlock SITRUS_BERRY;
    @NotNull
    private static final BerryBlock SPELON_BERRY;
    @NotNull
    private static final BerryBlock STARF_BERRY;
    @NotNull
    private static final BerryBlock TAMATO_BERRY;
    @NotNull
    private static final BerryBlock TANGA_BERRY;
    @NotNull
    private static final BerryBlock TOUGA_BERRY;
    @NotNull
    private static final BerryBlock WACAN_BERRY;
    @NotNull
    private static final BerryBlock WATMEL_BERRY;
    @NotNull
    private static final BerryBlock WEPEAR_BERRY;
    @NotNull
    private static final BerryBlock WIKI_BERRY;
    @NotNull
    private static final BerryBlock YACHE_BERRY;

    private CobblemonBlocks() {
    }

    @Override
    @NotNull
    public Registry<Block> getRegistry() {
        return registry;
    }

    @Override
    @NotNull
    public ResourceKey<Registry<Block>> getRegistryKey() {
        return registryKey;
    }

    @NotNull
    public final BlockSetType getAPRICORN_BLOCK_SET_TYPE() {
        return APRICORN_BLOCK_SET_TYPE;
    }

    public final WoodType getAPRICORN_WOOD_TYPE() {
        return APRICORN_WOOD_TYPE;
    }

    @NotNull
    public final MintBlock getRED_MINT() {
        return RED_MINT;
    }

    @NotNull
    public final Map<Block, Block> strippedBlocks() {
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)APRICORN_WOOD, (Object)STRIPPED_APRICORN_WOOD), TuplesKt.to((Object)APRICORN_LOG, (Object)STRIPPED_APRICORN_LOG)};
        return MapsKt.mapOf((Pair[])pairArray);
    }

    private final ApricornBlock apricornBlock(String name, Apricorn apricorn) {
        BlockBehaviour.Properties properties2 = BlockBehaviour.Properties.m_284310_().m_284180_(apricorn.mapColor()).m_60977_().m_60913_(Blocks.f_49999_.m_155943_(), Blocks.f_49999_.m_7325_()).m_60918_(SoundType.f_56736_).m_60955_();
        Intrinsics.checkNotNullExpressionValue((Object)properties2, (String)"create().mapColor(aprico\u2026ndGroup.WOOD).nonOpaque()");
        return this.create(name, new ApricornBlock(properties2, apricorn));
    }

    private final Block tumblestoneBlock(String name, int stage, int height, int xzOffset, Block nextStage) {
        BlockBehaviour.Properties properties2 = BlockBehaviour.Properties.m_284310_().m_278166_(PushReaction.DESTROY).m_60955_().m_60978_(1.5f).m_60918_(CobblemonSounds.TUMBLESTONE_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties2, (String)"create()\n            .pi\u2026ounds.TUMBLESTONE_SOUNDS)");
        return (Block)this.create(name, new TumblestoneBlock(properties2, stage, height, xzOffset, nextStage));
    }

    @NotNull
    public final BerryBlock getAGUAV_BERRY() {
        return AGUAV_BERRY;
    }

    @NotNull
    public final BerryBlock getAPICOT_BERRY() {
        return APICOT_BERRY;
    }

    @NotNull
    public final BerryBlock getASPEAR_BERRY() {
        return ASPEAR_BERRY;
    }

    @NotNull
    public final BerryBlock getBABIRI_BERRY() {
        return BABIRI_BERRY;
    }

    @NotNull
    public final BerryBlock getBELUE_BERRY() {
        return BELUE_BERRY;
    }

    @NotNull
    public final BerryBlock getBLUK_BERRY() {
        return BLUK_BERRY;
    }

    @NotNull
    public final BerryBlock getCHARTI_BERRY() {
        return CHARTI_BERRY;
    }

    @NotNull
    public final BerryBlock getCHERI_BERRY() {
        return CHERI_BERRY;
    }

    @NotNull
    public final BerryBlock getCHESTO_BERRY() {
        return CHESTO_BERRY;
    }

    @NotNull
    public final BerryBlock getCHILAN_BERRY() {
        return CHILAN_BERRY;
    }

    @NotNull
    public final BerryBlock getCHOPLE_BERRY() {
        return CHOPLE_BERRY;
    }

    @NotNull
    public final BerryBlock getCOBA_BERRY() {
        return COBA_BERRY;
    }

    @NotNull
    public final BerryBlock getCOLBUR_BERRY() {
        return COLBUR_BERRY;
    }

    @NotNull
    public final BerryBlock getCORNN_BERRY() {
        return CORNN_BERRY;
    }

    @NotNull
    public final BerryBlock getCUSTAP_BERRY() {
        return CUSTAP_BERRY;
    }

    @NotNull
    public final BerryBlock getDURIN_BERRY() {
        return DURIN_BERRY;
    }

    @NotNull
    public final BerryBlock getENIGMA_BERRY() {
        return ENIGMA_BERRY;
    }

    @NotNull
    public final BerryBlock getFIGY_BERRY() {
        return FIGY_BERRY;
    }

    @NotNull
    public final BerryBlock getGANLON_BERRY() {
        return GANLON_BERRY;
    }

    @NotNull
    public final BerryBlock getGREPA_BERRY() {
        return GREPA_BERRY;
    }

    @NotNull
    public final BerryBlock getHABAN_BERRY() {
        return HABAN_BERRY;
    }

    @NotNull
    public final BerryBlock getHONDEW_BERRY() {
        return HONDEW_BERRY;
    }

    @NotNull
    public final BerryBlock getHOPO_BERRY() {
        return HOPO_BERRY;
    }

    @NotNull
    public final BerryBlock getIAPAPA_BERRY() {
        return IAPAPA_BERRY;
    }

    @NotNull
    public final BerryBlock getJABOCA_BERRY() {
        return JABOCA_BERRY;
    }

    @NotNull
    public final BerryBlock getKASIB_BERRY() {
        return KASIB_BERRY;
    }

    @NotNull
    public final BerryBlock getKEBIA_BERRY() {
        return KEBIA_BERRY;
    }

    @NotNull
    public final BerryBlock getKEE_BERRY() {
        return KEE_BERRY;
    }

    @NotNull
    public final BerryBlock getKELPSY_BERRY() {
        return KELPSY_BERRY;
    }

    @NotNull
    public final BerryBlock getLANSAT_BERRY() {
        return LANSAT_BERRY;
    }

    @NotNull
    public final BerryBlock getLEPPA_BERRY() {
        return LEPPA_BERRY;
    }

    @NotNull
    public final BerryBlock getLIECHI_BERRY() {
        return LIECHI_BERRY;
    }

    @NotNull
    public final BerryBlock getLUM_BERRY() {
        return LUM_BERRY;
    }

    @NotNull
    public final BerryBlock getMAGO_BERRY() {
        return MAGO_BERRY;
    }

    @NotNull
    public final BerryBlock getMAGOST_BERRY() {
        return MAGOST_BERRY;
    }

    @NotNull
    public final BerryBlock getMARANGA_BERRY() {
        return MARANGA_BERRY;
    }

    @NotNull
    public final BerryBlock getMICLE_BERRY() {
        return MICLE_BERRY;
    }

    @NotNull
    public final BerryBlock getNANAB_BERRY() {
        return NANAB_BERRY;
    }

    @NotNull
    public final BerryBlock getNOMEL_BERRY() {
        return NOMEL_BERRY;
    }

    @NotNull
    public final BerryBlock getOCCA_BERRY() {
        return OCCA_BERRY;
    }

    @NotNull
    public final BerryBlock getORAN_BERRY() {
        return ORAN_BERRY;
    }

    @NotNull
    public final BerryBlock getPAMTRE_BERRY() {
        return PAMTRE_BERRY;
    }

    @NotNull
    public final BerryBlock getPASSHO_BERRY() {
        return PASSHO_BERRY;
    }

    @NotNull
    public final BerryBlock getPAYAPA_BERRY() {
        return PAYAPA_BERRY;
    }

    @NotNull
    public final BerryBlock getPECHA_BERRY() {
        return PECHA_BERRY;
    }

    @NotNull
    public final BerryBlock getPERSIM_BERRY() {
        return PERSIM_BERRY;
    }

    @NotNull
    public final BerryBlock getPETAYA_BERRY() {
        return PETAYA_BERRY;
    }

    @NotNull
    public final BerryBlock getPINAP_BERRY() {
        return PINAP_BERRY;
    }

    @NotNull
    public final BerryBlock getPOMEG_BERRY() {
        return POMEG_BERRY;
    }

    @NotNull
    public final BerryBlock getQUALOT_BERRY() {
        return QUALOT_BERRY;
    }

    @NotNull
    public final BerryBlock getRABUTA_BERRY() {
        return RABUTA_BERRY;
    }

    @NotNull
    public final BerryBlock getRAWST_BERRY() {
        return RAWST_BERRY;
    }

    @NotNull
    public final BerryBlock getRAZZ_BERRY() {
        return RAZZ_BERRY;
    }

    @NotNull
    public final BerryBlock getRINDO_BERRY() {
        return RINDO_BERRY;
    }

    @NotNull
    public final BerryBlock getROSELI_BERRY() {
        return ROSELI_BERRY;
    }

    @NotNull
    public final BerryBlock getROWAP_BERRY() {
        return ROWAP_BERRY;
    }

    @NotNull
    public final BerryBlock getSALAC_BERRY() {
        return SALAC_BERRY;
    }

    @NotNull
    public final BerryBlock getSHUCA_BERRY() {
        return SHUCA_BERRY;
    }

    @NotNull
    public final BerryBlock getSITRUS_BERRY() {
        return SITRUS_BERRY;
    }

    @NotNull
    public final BerryBlock getSPELON_BERRY() {
        return SPELON_BERRY;
    }

    @NotNull
    public final BerryBlock getSTARF_BERRY() {
        return STARF_BERRY;
    }

    @NotNull
    public final BerryBlock getTAMATO_BERRY() {
        return TAMATO_BERRY;
    }

    @NotNull
    public final BerryBlock getTANGA_BERRY() {
        return TANGA_BERRY;
    }

    @NotNull
    public final BerryBlock getTOUGA_BERRY() {
        return TOUGA_BERRY;
    }

    @NotNull
    public final BerryBlock getWACAN_BERRY() {
        return WACAN_BERRY;
    }

    @NotNull
    public final BerryBlock getWATMEL_BERRY() {
        return WATMEL_BERRY;
    }

    @NotNull
    public final BerryBlock getWEPEAR_BERRY() {
        return WEPEAR_BERRY;
    }

    @NotNull
    public final BerryBlock getWIKI_BERRY() {
        return WIKI_BERRY;
    }

    @NotNull
    public final BerryBlock getYACHE_BERRY() {
        return YACHE_BERRY;
    }

    @NotNull
    public final Map<ResourceLocation, BerryBlock> berries() {
        return MapsKt.toMap(berries);
    }

    private final BerryBlock berryBlock(String name) {
        ResourceLocation identifier = MiscUtilsKt.cobblemonResource(name + "_berry");
        String string = identifier.m_135815_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"identifier.path");
        BlockBehaviour.Properties properties2 = BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)Blocks.f_50092_)).m_60988_().m_60918_(CobblemonSounds.BERRY_BUSH_SOUNDS).m_60978_(0.2f);
        Intrinsics.checkNotNullExpressionValue((Object)properties2, (String)"copy(Blocks.WHEAT).dynam\u2026SH_SOUNDS).strength(0.2F)");
        BerryBlock block = this.create(string, new BerryBlock(identifier, properties2));
        berries.put(identifier, block);
        return block;
    }

    private final RotatedPillarBlock log(String name, MapColor arg, MapColor arg2) {
        RotatedPillarBlock block = BlocksInvoker.createLogBlock(arg, arg2);
        RotatedPillarBlock rotatedPillarBlock = this.create(name, block);
        Intrinsics.checkNotNullExpressionValue((Object)rotatedPillarBlock, (String)"this.create(name, block)");
        return rotatedPillarBlock;
    }

    static /* synthetic */ RotatedPillarBlock log$default(CobblemonBlocks cobblemonBlocks, String string, MapColor mapColor, MapColor mapColor2, int n, Object object) {
        if ((n & 2) != 0) {
            MapColor mapColor3 = MapColor.f_283762_;
            Intrinsics.checkNotNullExpressionValue((Object)mapColor3, (String)"DIRT_BROWN");
            mapColor = mapColor3;
        }
        if ((n & 4) != 0) {
            MapColor mapColor4 = MapColor.f_283762_;
            Intrinsics.checkNotNullExpressionValue((Object)mapColor4, (String)"DIRT_BROWN");
            mapColor2 = mapColor4;
        }
        return cobblemonBlocks.log(string, mapColor, mapColor2);
    }

    private final <E> E setFlammable(E block, int burnChance, int spreadChance) {
        if (!(block instanceof Block)) {
            return block;
        }
        Block block2 = Blocks.f_50083_;
        Intrinsics.checkNotNull((Object)block2, (String)"null cannot be cast to non-null type net.minecraft.block.FireBlock");
        FireBlock fireBlock = (FireBlock)block2;
        ((FireBlockInvoker)fireBlock).registerNewFlammableBlock((Block)block, burnChance, spreadChance);
        return block;
    }

    private final DropExperienceBlock evolutionStoneOre(String name) {
        return this.create(name, new DropExperienceBlock(BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)Blocks.f_49996_)), (IntProvider)UniformInt.m_146622_((int)1, (int)2)));
    }

    private final DropExperienceBlock deepslateEvolutionStoneOre(String name) {
        return this.create(name, new DropExperienceBlock(BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)Blocks.f_152468_)), (IntProvider)UniformInt.m_146622_((int)1, (int)2)));
    }

    private final LeavesBlock leaves(String name) {
        LeavesBlock block = BlocksInvoker.createLeavesBlock(SoundType.f_56740_);
        LeavesBlock leavesBlock = this.create(name, block);
        Intrinsics.checkNotNullExpressionValue((Object)leavesBlock, (String)"this.create(name, block)");
        return leavesBlock;
    }

    private static final boolean APRICORN_TRAPDOOR$lambda$0(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos2, EntityType entityType) {
        return false;
    }

    private static final int MONITOR$lambda$1(BlockState it) {
        return it.m_61143_((Property)MonitorBlock.Companion.getSCREEN()) != MonitorBlock.MonitorScreen.OFF ? 15 : 0;
    }

    private static final int HEALING_MACHINE$lambda$2(BlockState it) {
        Comparable comparable = it.m_61143_((Property)HealingMachineBlock.Companion.getCHARGE_LEVEL());
        Intrinsics.checkNotNullExpressionValue((Object)comparable, (String)"it.get(HealingMachineBlock.CHARGE_LEVEL)");
        return ((Number)((Object)comparable)).intValue() >= 5 ? 7 : 2;
    }

    private static final int PC$lambda$3(BlockState it) {
        Comparable comparable = it.m_61143_((Property)PCBlock.Companion.getON());
        Intrinsics.checkNotNull((Object)comparable, (String)"null cannot be cast to non-null type kotlin.Boolean");
        return (Boolean)comparable != false && it.m_61143_((Property)PCBlock.Companion.getPART()) == PCBlock.PCPart.TOP ? 10 : 0;
    }

    private static final int PASTURE$lambda$4(BlockState it) {
        Comparable comparable = it.m_61143_((Property)PastureBlock.Companion.getON());
        Intrinsics.checkNotNull((Object)comparable, (String)"null cannot be cast to non-null type kotlin.Boolean");
        return (Boolean)comparable != false && it.m_61143_((Property)PastureBlock.Companion.getPART()) == PastureBlock.PasturePart.TOP ? 10 : 0;
    }

    static {
        Triple[] tripleArray;
        INSTANCE = new CobblemonBlocks();
        DefaultedRegistry defaultedRegistry = BuiltInRegistries.f_256975_;
        Intrinsics.checkNotNullExpressionValue((Object)defaultedRegistry, (String)"BLOCK");
        registry = (Registry)defaultedRegistry;
        ResourceKey resourceKey = Registries.f_256747_;
        Intrinsics.checkNotNullExpressionValue((Object)resourceKey, (String)"BLOCK");
        registryKey = resourceKey;
        APRICORN_BLOCK_SET_TYPE = new BlockSetType("apricorn");
        APRICORN_WOOD_TYPE = WoodType.m_61844_((WoodType)new WoodType("apricorn", APRICORN_BLOCK_SET_TYPE));
        DAWN_STONE_ORE = INSTANCE.evolutionStoneOre("dawn_stone_ore");
        DUSK_STONE_ORE = INSTANCE.evolutionStoneOre("dusk_stone_ore");
        FIRE_STONE_ORE = INSTANCE.evolutionStoneOre("fire_stone_ore");
        NETHER_FIRE_STONE_ORE = INSTANCE.evolutionStoneOre("nether_fire_stone_ore");
        ICE_STONE_ORE = INSTANCE.evolutionStoneOre("ice_stone_ore");
        LEAF_STONE_ORE = INSTANCE.evolutionStoneOre("leaf_stone_ore");
        MOON_STONE_ORE = INSTANCE.evolutionStoneOre("moon_stone_ore");
        DRIPSTONE_MOON_STONE_ORE = INSTANCE.evolutionStoneOre("dripstone_moon_stone_ore");
        SHINY_STONE_ORE = INSTANCE.evolutionStoneOre("shiny_stone_ore");
        SUN_STONE_ORE = INSTANCE.evolutionStoneOre("sun_stone_ore");
        TERRACOTTA_SUN_STONE_ORE = INSTANCE.evolutionStoneOre("terracotta_sun_stone_ore");
        THUNDER_STONE_ORE = INSTANCE.evolutionStoneOre("thunder_stone_ore");
        WATER_STONE_ORE = INSTANCE.evolutionStoneOre("water_stone_ore");
        DEEPSLATE_DAWN_STONE_ORE = INSTANCE.deepslateEvolutionStoneOre("deepslate_dawn_stone_ore");
        DEEPSLATE_DUSK_STONE_ORE = INSTANCE.deepslateEvolutionStoneOre("deepslate_dusk_stone_ore");
        DEEPSLATE_FIRE_STONE_ORE = INSTANCE.deepslateEvolutionStoneOre("deepslate_fire_stone_ore");
        DEEPSLATE_ICE_STONE_ORE = INSTANCE.deepslateEvolutionStoneOre("deepslate_ice_stone_ore");
        DEEPSLATE_LEAF_STONE_ORE = INSTANCE.deepslateEvolutionStoneOre("deepslate_leaf_stone_ore");
        DEEPSLATE_MOON_STONE_ORE = INSTANCE.deepslateEvolutionStoneOre("deepslate_moon_stone_ore");
        DEEPSLATE_SHINY_STONE_ORE = INSTANCE.deepslateEvolutionStoneOre("deepslate_shiny_stone_ore");
        DEEPSLATE_SUN_STONE_ORE = INSTANCE.deepslateEvolutionStoneOre("deepslate_sun_stone_ore");
        DEEPSLATE_THUNDER_STONE_ORE = INSTANCE.deepslateEvolutionStoneOre("deepslate_thunder_stone_ore");
        DEEPSLATE_WATER_STONE_ORE = INSTANCE.deepslateEvolutionStoneOre("deepslate_water_stone_ore");
        MapColor mapColor = MapColor.f_283748_;
        Intrinsics.checkNotNullExpressionValue((Object)mapColor, (String)"BROWN");
        APRICORN_LOG = CobblemonBlocks.log$default(INSTANCE, "apricorn_log", null, mapColor, 2, null);
        STRIPPED_APRICORN_LOG = CobblemonBlocks.log$default(INSTANCE, "stripped_apricorn_log", null, null, 6, null);
        APRICORN_WOOD = CobblemonBlocks.log$default(INSTANCE, "apricorn_wood", null, null, 6, null);
        STRIPPED_APRICORN_WOOD = CobblemonBlocks.log$default(INSTANCE, "stripped_apricorn_wood", null, null, 6, null);
        APRICORN_PLANKS = INSTANCE.create("apricorn_planks", new Block(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283762_).m_280658_(NoteBlockInstrument.BASS).m_60913_(2.0f, 3.0f).m_60918_(SoundType.f_56736_)));
        APRICORN_LEAVES = INSTANCE.leaves("apricorn_leaves");
        APRICORN_FENCE = INSTANCE.create("apricorn_fence", new FenceBlock(BlockBehaviour.Properties.m_284310_().m_284180_(APRICORN_PLANKS.m_284356_()).m_280658_(NoteBlockInstrument.BASS).m_60913_(2.0f, 3.0f).m_60918_(SoundType.f_56736_)));
        APRICORN_FENCE_GATE = INSTANCE.create("apricorn_fence_gate", new FenceGateBlock(BlockBehaviour.Properties.m_284310_().m_284180_(APRICORN_PLANKS.m_284356_()).m_280658_(NoteBlockInstrument.BASS).m_60913_(2.0f, 3.0f).m_60918_(SoundType.f_56736_), APRICORN_WOOD_TYPE));
        APRICORN_BUTTON = INSTANCE.create("apricorn_button", BlocksInvoker.createWoodenButtonBlock(BlockSetType.f_271198_, new FeatureFlag[0]));
        APRICORN_PRESSURE_PLATE = INSTANCE.create("apricorn_pressure_plate", PressurePlateBlockInvoker.cobblemon$create(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.m_284310_().m_284180_(APRICORN_PLANKS.m_284356_()).m_280658_(NoteBlockInstrument.BASS).m_60910_().m_60978_(0.5f).m_60918_(SoundType.f_56736_), APRICORN_BLOCK_SET_TYPE));
        BlockBehaviour.Properties properties2 = BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)Blocks.f_50095_));
        Intrinsics.checkNotNullExpressionValue((Object)properties2, (String)"copy(Blocks.OAK_SIGN)");
        WoodType woodType = APRICORN_WOOD_TYPE;
        Intrinsics.checkNotNullExpressionValue((Object)woodType, (String)"APRICORN_WOOD_TYPE");
        APRICORN_SIGN = INSTANCE.create("apricorn_sign", new CobblemonSignBlock(properties2, woodType));
        BlockBehaviour.Properties properties3 = BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)Blocks.f_50158_));
        Intrinsics.checkNotNullExpressionValue((Object)properties3, (String)"copy(Blocks.OAK_WALL_SIGN)");
        WoodType woodType2 = APRICORN_WOOD_TYPE;
        Intrinsics.checkNotNullExpressionValue((Object)woodType2, (String)"APRICORN_WOOD_TYPE");
        APRICORN_WALL_SIGN = INSTANCE.create("apricorn_wall_sign", new CobblemonWallSignBlock(properties3, woodType2));
        BlockBehaviour.Properties properties4 = BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)Blocks.f_244093_));
        Intrinsics.checkNotNullExpressionValue((Object)properties4, (String)"copy(Blocks.OAK_WALL_HANGING_SIGN)");
        WoodType woodType3 = APRICORN_WOOD_TYPE;
        Intrinsics.checkNotNullExpressionValue((Object)woodType3, (String)"APRICORN_WOOD_TYPE");
        APRICORN_HANGING_SIGN = INSTANCE.create("apricorn_hanging_sign", new CobblemonHangingSignBlock(properties4, woodType3));
        BlockBehaviour.Properties properties5 = BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)Blocks.f_244319_));
        Intrinsics.checkNotNullExpressionValue((Object)properties5, (String)"copy(Blocks.OAK_HANGING_SIGN)");
        WoodType woodType4 = APRICORN_WOOD_TYPE;
        Intrinsics.checkNotNullExpressionValue((Object)woodType4, (String)"APRICORN_WOOD_TYPE");
        APRICORN_WALL_HANGING_SIGN = INSTANCE.create("apricorn_wall_hanging_sign", new CobblemonWallHangingSignBlock(properties5, woodType4));
        APRICORN_SLAB = INSTANCE.create("apricorn_slab", new SlabBlock(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283825_).m_280658_(NoteBlockInstrument.BASS).m_60913_(2.0f, 3.0f).m_60918_(SoundType.f_56736_)));
        APRICORN_STAIRS = INSTANCE.create("apricorn_stairs", StairsBlockInvoker.cobblemon$create(APRICORN_PLANKS.m_49966_(), BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)APRICORN_PLANKS))));
        APRICORN_DOOR = INSTANCE.create("apricorn_door", DoorBlockInvoker.cobblemon$create(BlockBehaviour.Properties.m_284310_().m_284180_(APRICORN_PLANKS.m_284356_()).m_280658_(NoteBlockInstrument.BASS).m_60978_(3.0f).m_60918_(SoundType.f_56736_).m_60955_(), APRICORN_BLOCK_SET_TYPE));
        APRICORN_TRAPDOOR = INSTANCE.create("apricorn_trapdoor", TrapdoorBlockInvoker.cobblemon$create(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283825_).m_280658_(NoteBlockInstrument.BASS).m_60978_(3.0f).m_60918_(SoundType.f_56736_).m_60955_().m_60922_(CobblemonBlocks::APRICORN_TRAPDOOR$lambda$0), APRICORN_BLOCK_SET_TYPE));
        BlockBehaviour.Properties properties6 = PLANT_PROPERTIES = BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283915_).m_60910_().m_60977_().m_60966_().m_60918_(SoundType.f_56740_).m_278166_(PushReaction.DESTROY);
        Intrinsics.checkNotNullExpressionValue((Object)properties6, (String)"PLANT_PROPERTIES");
        BLACK_APRICORN_SAPLING = INSTANCE.create("black_apricorn_sapling", new ApricornSaplingBlock(properties6, Apricorn.BLACK));
        BlockBehaviour.Properties properties7 = PLANT_PROPERTIES;
        Intrinsics.checkNotNullExpressionValue((Object)properties7, (String)"PLANT_PROPERTIES");
        BLUE_APRICORN_SAPLING = INSTANCE.create("blue_apricorn_sapling", new ApricornSaplingBlock(properties7, Apricorn.BLUE));
        BlockBehaviour.Properties properties8 = PLANT_PROPERTIES;
        Intrinsics.checkNotNullExpressionValue((Object)properties8, (String)"PLANT_PROPERTIES");
        GREEN_APRICORN_SAPLING = INSTANCE.create("green_apricorn_sapling", new ApricornSaplingBlock(properties8, Apricorn.GREEN));
        BlockBehaviour.Properties properties9 = PLANT_PROPERTIES;
        Intrinsics.checkNotNullExpressionValue((Object)properties9, (String)"PLANT_PROPERTIES");
        PINK_APRICORN_SAPLING = INSTANCE.create("pink_apricorn_sapling", new ApricornSaplingBlock(properties9, Apricorn.PINK));
        BlockBehaviour.Properties properties10 = PLANT_PROPERTIES;
        Intrinsics.checkNotNullExpressionValue((Object)properties10, (String)"PLANT_PROPERTIES");
        RED_APRICORN_SAPLING = INSTANCE.create("red_apricorn_sapling", new ApricornSaplingBlock(properties10, Apricorn.RED));
        BlockBehaviour.Properties properties11 = PLANT_PROPERTIES;
        Intrinsics.checkNotNullExpressionValue((Object)properties11, (String)"PLANT_PROPERTIES");
        WHITE_APRICORN_SAPLING = INSTANCE.create("white_apricorn_sapling", new ApricornSaplingBlock(properties11, Apricorn.WHITE));
        BlockBehaviour.Properties properties12 = PLANT_PROPERTIES;
        Intrinsics.checkNotNullExpressionValue((Object)properties12, (String)"PLANT_PROPERTIES");
        YELLOW_APRICORN_SAPLING = INSTANCE.create("yellow_apricorn_sapling", new ApricornSaplingBlock(properties12, Apricorn.YELLOW));
        BlockBehaviour.Properties properties13 = BlockBehaviour.Properties.m_284310_().m_278166_(PushReaction.DESTROY).m_278183_().m_284180_(MapColor.f_283909_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.MEDICINAL_LEEK_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties13, (String)"create().pistonBehavior(\u2026ds.MEDICINAL_LEEK_SOUNDS)");
        MEDICINAL_LEEK = INSTANCE.create("medicinal_leek", new MedicinalLeekBlock(properties13));
        BlockBehaviour.Properties properties14 = BlockBehaviour.Properties.m_284310_().m_278166_(PushReaction.DESTROY).m_278183_().m_284180_(MapColor.f_283762_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.ENERGY_ROOT_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties14, (String)"create().pistonBehavior(\u2026ounds.ENERGY_ROOT_SOUNDS)");
        ENERGY_ROOT = INSTANCE.create("energy_root", new EnergyRootBlock(properties14));
        BlockBehaviour.Properties properties15 = BlockBehaviour.Properties.m_284310_().m_278166_(PushReaction.DESTROY).m_278183_().m_284180_(MapColor.f_283915_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.BIG_ROOT_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties15, (String)"create().pistonBehavior(\u2026onSounds.BIG_ROOT_SOUNDS)");
        BIG_ROOT = INSTANCE.create("big_root", new BigRootBlock(properties15));
        BlockBehaviour.Properties properties16 = BlockBehaviour.Properties.m_284310_().m_278166_(PushReaction.DESTROY).m_284180_(MapColor.f_283915_).m_278183_().m_60910_().m_60966_().m_60918_(CobblemonSounds.REVIVAL_HERB_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties16, (String)"create().pistonBehavior(\u2026unds.REVIVAL_HERB_SOUNDS)");
        REVIVAL_HERB = INSTANCE.create("revival_herb", new RevivalHerbBlock(properties16));
        TUMBLESTONE_CLUSTER = INSTANCE.tumblestoneBlock("tumblestone_cluster", 3, 7, 3, null);
        LARGE_BUDDING_TUMBLESTONE = INSTANCE.tumblestoneBlock("large_budding_tumblestone", 2, 5, 3, TUMBLESTONE_CLUSTER);
        MEDIUM_BUDDING_TUMBLESTONE = INSTANCE.tumblestoneBlock("medium_budding_tumblestone", 1, 4, 3, LARGE_BUDDING_TUMBLESTONE);
        SMALL_BUDDING_TUMBLESTONE = INSTANCE.tumblestoneBlock("small_budding_tumblestone", 0, 3, 4, MEDIUM_BUDDING_TUMBLESTONE);
        SKY_TUMBLESTONE_CLUSTER = INSTANCE.tumblestoneBlock("sky_tumblestone_cluster", 3, 7, 3, null);
        LARGE_BUDDING_SKY_TUMBLESTONE = INSTANCE.tumblestoneBlock("large_budding_sky_tumblestone", 2, 5, 3, SKY_TUMBLESTONE_CLUSTER);
        MEDIUM_BUDDING_SKY_TUMBLESTONE = INSTANCE.tumblestoneBlock("medium_budding_sky_tumblestone", 1, 4, 3, LARGE_BUDDING_SKY_TUMBLESTONE);
        SMALL_BUDDING_SKY_TUMBLESTONE = INSTANCE.tumblestoneBlock("small_budding_sky_tumblestone", 0, 3, 4, MEDIUM_BUDDING_SKY_TUMBLESTONE);
        BLACK_TUMBLESTONE_CLUSTER = INSTANCE.tumblestoneBlock("black_tumblestone_cluster", 3, 7, 3, null);
        LARGE_BUDDING_BLACK_TUMBLESTONE = INSTANCE.tumblestoneBlock("large_budding_black_tumblestone", 2, 5, 3, BLACK_TUMBLESTONE_CLUSTER);
        MEDIUM_BUDDING_BLACK_TUMBLESTONE = INSTANCE.tumblestoneBlock("medium_budding_black_tumblestone", 1, 4, 3, LARGE_BUDDING_BLACK_TUMBLESTONE);
        SMALL_BUDDING_BLACK_TUMBLESTONE = INSTANCE.tumblestoneBlock("small_budding_black_tumblestone", 0, 3, 4, MEDIUM_BUDDING_BLACK_TUMBLESTONE);
        TUMBLESTONE_BLOCK = INSTANCE.create("tumblestone_block", new Block(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283895_).m_60978_(1.0f).m_60918_(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).m_60999_().m_280658_(NoteBlockInstrument.BASEDRUM)));
        SKY_TUMBLESTONE_BLOCK = INSTANCE.create("sky_tumblestone_block", new Block(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283869_).m_60978_(1.0f).m_60918_(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).m_60999_().m_280658_(NoteBlockInstrument.BASEDRUM)));
        BLACK_TUMBLESTONE_BLOCK = INSTANCE.create("black_tumblestone_block", new Block(BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283771_).m_60978_(1.0f).m_60918_(CobblemonSounds.TUMBLESTONE_BLOCK_SOUNDS).m_60999_().m_280658_(NoteBlockInstrument.BASEDRUM)));
        BLACK_APRICORN = INSTANCE.apricornBlock("black_apricorn", Apricorn.BLACK);
        BLUE_APRICORN = INSTANCE.apricornBlock("blue_apricorn", Apricorn.BLUE);
        GREEN_APRICORN = INSTANCE.apricornBlock("green_apricorn", Apricorn.GREEN);
        PINK_APRICORN = INSTANCE.apricornBlock("pink_apricorn", Apricorn.PINK);
        RED_APRICORN = INSTANCE.apricornBlock("red_apricorn", Apricorn.RED);
        WHITE_APRICORN = INSTANCE.apricornBlock("white_apricorn", Apricorn.WHITE);
        YELLOW_APRICORN = INSTANCE.apricornBlock("yellow_apricorn", Apricorn.YELLOW);
        BlockBehaviour.Properties properties17 = BlockBehaviour.Properties.m_284310_().m_60918_(CobblemonSounds.RELIC_COIN_POUCH_SOUNDS).m_278166_(PushReaction.DESTROY).m_60978_(0.4f).m_60955_();
        Intrinsics.checkNotNullExpressionValue((Object)properties17, (String)"create()\n               \u2026             .nonOpaque()");
        RELIC_COIN_POUCH = INSTANCE.create("relic_coin_pouch", new CoinPouchBlock(properties17, true));
        BlockBehaviour.Properties properties18 = BlockBehaviour.Properties.m_284310_().m_60918_(CobblemonSounds.RELIC_COIN_SACK_SOUNDS).m_278166_(PushReaction.DESTROY).m_60978_(0.4f);
        Intrinsics.checkNotNullExpressionValue((Object)properties18, (String)"create()\n               \u2026          .strength(0.4f)");
        RELIC_COIN_SACK = INSTANCE.create("relic_coin_sack", new CoinPouchBlock(properties18, false));
        BlockBehaviour.Properties properties19 = BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)Blocks.f_50087_)).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties19, (String)"copy(Blocks.CHEST).nonOp\u2026unds.GILDED_CHEST_SOUNDS)");
        GILDED_CHEST = INSTANCE.create("gilded_chest", new GildedChestBlock(properties19, GildedChestBlock.Type.RED));
        BlockBehaviour.Properties properties20 = BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)Blocks.f_50087_)).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties20, (String)"copy(Blocks.CHEST).nonOp\u2026unds.GILDED_CHEST_SOUNDS)");
        BLUE_GILDED_CHEST = INSTANCE.create("blue_gilded_chest", new GildedChestBlock(properties20, GildedChestBlock.Type.BLUE));
        BlockBehaviour.Properties properties21 = BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)Blocks.f_50087_)).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties21, (String)"copy(Blocks.CHEST).nonOp\u2026unds.GILDED_CHEST_SOUNDS)");
        BLACK_GILDED_CHEST = INSTANCE.create("black_gilded_chest", new GildedChestBlock(properties21, GildedChestBlock.Type.BLACK));
        BlockBehaviour.Properties properties22 = BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)Blocks.f_50087_)).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties22, (String)"copy(Blocks.CHEST).nonOp\u2026unds.GILDED_CHEST_SOUNDS)");
        YELLOW_GILDED_CHEST = INSTANCE.create("yellow_gilded_chest", new GildedChestBlock(properties22, GildedChestBlock.Type.YELLOW));
        BlockBehaviour.Properties properties23 = BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)Blocks.f_50087_)).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties23, (String)"copy(Blocks.CHEST).nonOp\u2026unds.GILDED_CHEST_SOUNDS)");
        WHITE_GILDED_CHEST = INSTANCE.create("white_gilded_chest", new GildedChestBlock(properties23, GildedChestBlock.Type.WHITE));
        BlockBehaviour.Properties properties24 = BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)Blocks.f_50087_)).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties24, (String)"copy(Blocks.CHEST).nonOp\u2026unds.GILDED_CHEST_SOUNDS)");
        GREEN_GILDED_CHEST = INSTANCE.create("green_gilded_chest", new GildedChestBlock(properties24, GildedChestBlock.Type.GREEN));
        BlockBehaviour.Properties properties25 = BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)Blocks.f_50087_)).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties25, (String)"copy(Blocks.CHEST).nonOp\u2026unds.GILDED_CHEST_SOUNDS)");
        PINK_GILDED_CHEST = INSTANCE.create("pink_gilded_chest", new GildedChestBlock(properties25, GildedChestBlock.Type.PINK));
        BlockBehaviour.Properties properties26 = BlockBehaviour.Properties.m_60926_((BlockBehaviour)((BlockBehaviour)Blocks.f_50087_)).m_60955_().m_60918_(CobblemonSounds.GILDED_CHEST_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties26, (String)"copy(Blocks.CHEST).nonOp\u2026unds.GILDED_CHEST_SOUNDS)");
        GIMMIGHOUL_CHEST = INSTANCE.create("gimmighoul_chest", new GildedChestBlock(properties26, GildedChestBlock.Type.FAKE));
        BlockBehaviour.Properties properties27 = BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283906_).m_60918_(SoundType.f_56743_).m_278166_(PushReaction.BLOCK).m_60999_().m_60913_(5.0f, 6.0f).m_60953_(CobblemonBlocks::MONITOR$lambda$1);
        Intrinsics.checkNotNullExpressionValue((Object)properties27, (String)"create()\n               \u2026orScreen.OFF) 15 else 0 }");
        MONITOR = INSTANCE.create("monitor", new MonitorBlock(properties27));
        BlockBehaviour.Properties properties28 = BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283906_).m_60918_(SoundType.f_56743_).m_278166_(PushReaction.BLOCK).m_60999_().m_60913_(5.0f, 6.0f).m_60955_();
        Intrinsics.checkNotNullExpressionValue((Object)properties28, (String)"create()\n               \u2026             .nonOpaque()");
        FOSSIL_ANALYZER = INSTANCE.create("fossil_analyzer", new FossilAnalyzerBlock(properties28));
        BlockBehaviour.Properties properties29 = BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283906_).m_60918_(SoundType.f_56744_).m_278166_(PushReaction.BLOCK).m_60999_().m_60913_(5.0f, 6.0f).m_60955_();
        Intrinsics.checkNotNullExpressionValue((Object)properties29, (String)"create()\n               \u2026             .nonOpaque()");
        RESTORATION_TANK = INSTANCE.create("restoration_tank", new RestorationTankBlock(properties29));
        BlockBehaviour.Properties properties30 = BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283906_).m_60918_(SoundType.f_56743_).m_278166_(PushReaction.BLOCK).m_60978_(2.0f).m_60955_().m_60953_(CobblemonBlocks::HEALING_MACHINE$lambda$2);
        Intrinsics.checkNotNullExpressionValue((Object)properties30, (String)"create()\n               \u2026_CHARGE_LEVEL) 7 else 2 }");
        HEALING_MACHINE = INSTANCE.create("healing_machine", new HealingMachineBlock(properties30));
        BlockBehaviour.Properties properties31 = BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283906_).m_60918_(SoundType.f_56743_).m_278166_(PushReaction.BLOCK).m_60978_(2.0f).m_60955_().m_60953_(CobblemonBlocks::PC$lambda$3);
        Intrinsics.checkNotNullExpressionValue((Object)properties31, (String)"create()\n               \u2026.PCPart.TOP)) 10 else 0 }");
        PC = INSTANCE.create("pc", new PCBlock(properties31));
        BlockBehaviour.Properties properties32 = BlockBehaviour.Properties.m_284310_().m_60918_(CobblemonSounds.DISPLAY_CASE_SOUNDS).m_60955_().m_278166_(PushReaction.BLOCK).m_284180_(MapColor.f_283947_).m_60978_(0.3f);
        Intrinsics.checkNotNullExpressionValue((Object)properties32, (String)"create()\n               \u2026          .strength(0.3f)");
        DISPLAY_CASE = INSTANCE.create("display_case", new DisplayCaseBlock(properties32));
        BlockBehaviour.Properties properties33 = BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283913_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.MINT_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties33, (String)"create().mapColor(MapCol\u2026blemonSounds.MINT_SOUNDS)");
        RED_MINT = INSTANCE.create("red_mint", new MintBlock(MintBlock.MintType.RED, properties33));
        BlockBehaviour.Properties properties34 = BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283743_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.MINT_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties34, (String)"create().mapColor(MapCol\u2026blemonSounds.MINT_SOUNDS)");
        BLUE_MINT = INSTANCE.create("blue_mint", new MintBlock(MintBlock.MintType.BLUE, properties34));
        BlockBehaviour.Properties properties35 = BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283772_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.MINT_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties35, (String)"create().mapColor(MapCol\u2026blemonSounds.MINT_SOUNDS)");
        CYAN_MINT = INSTANCE.create("cyan_mint", new MintBlock(MintBlock.MintType.CYAN, properties35));
        BlockBehaviour.Properties properties36 = BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283765_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.MINT_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties36, (String)"create().mapColor(MapCol\u2026blemonSounds.MINT_SOUNDS)");
        PINK_MINT = INSTANCE.create("pink_mint", new MintBlock(MintBlock.MintType.PINK, properties36));
        BlockBehaviour.Properties properties37 = BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283784_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.MINT_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties37, (String)"create().mapColor(MapCol\u2026blemonSounds.MINT_SOUNDS)");
        GREEN_MINT = INSTANCE.create("green_mint", new MintBlock(MintBlock.MintType.GREEN, properties37));
        BlockBehaviour.Properties properties38 = BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283811_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.MINT_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties38, (String)"create().mapColor(MapCol\u2026blemonSounds.MINT_SOUNDS)");
        WHITE_MINT = INSTANCE.create("white_mint", new MintBlock(MintBlock.MintType.WHITE, properties38));
        BlockBehaviour.Properties properties39 = BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283748_).m_60918_(SoundType.f_56736_).m_60978_(2.0f).m_60955_().m_278166_(PushReaction.BLOCK).m_60953_(CobblemonBlocks::PASTURE$lambda$4);
        Intrinsics.checkNotNullExpressionValue((Object)properties39, (String)"create()\n               \u2026urePart.TOP)) 10 else 0 }");
        PASTURE = INSTANCE.create("pasture", new PastureBlock(properties39));
        BlockBehaviour.Properties properties40 = BlockBehaviour.Properties.m_284310_().m_278166_(PushReaction.DESTROY).m_278183_().m_284180_(MapColor.f_283915_).m_60910_().m_60977_().m_60966_().m_60918_(CobblemonSounds.VIVICHOKE_SOUNDS);
        Intrinsics.checkNotNullExpressionValue((Object)properties40, (String)"create().pistonBehavior(\u2026nSounds.VIVICHOKE_SOUNDS)");
        VIVICHOKE_SEEDS = INSTANCE.create("vivichoke_seeds", new VivichokeBlock(properties40));
        PEP_UP_FLOWER = INSTANCE.create("pep_up_flower", new FlowerBlock(MobEffects.f_19620_, 10, BlockBehaviour.Properties.m_284310_().m_284180_(MapColor.f_283915_).m_60910_().m_60966_().m_60918_(SoundType.f_56740_).m_222979_(BlockBehaviour.OffsetType.XZ).m_278166_(PushReaction.DESTROY)));
        POTTED_PEP_UP_FLOWER = INSTANCE.create("potted_pep_up_flower", BlocksInvoker.createFlowerPotBlock((Block)PEP_UP_FLOWER, new FeatureFlag[0]));
        berries = new LinkedHashMap();
        AGUAV_BERRY = INSTANCE.berryBlock("aguav");
        APICOT_BERRY = INSTANCE.berryBlock("apicot");
        ASPEAR_BERRY = INSTANCE.berryBlock("aspear");
        BABIRI_BERRY = INSTANCE.berryBlock("babiri");
        BELUE_BERRY = INSTANCE.berryBlock("belue");
        BLUK_BERRY = INSTANCE.berryBlock("bluk");
        CHARTI_BERRY = INSTANCE.berryBlock("charti");
        CHERI_BERRY = INSTANCE.berryBlock("cheri");
        CHESTO_BERRY = INSTANCE.berryBlock("chesto");
        CHILAN_BERRY = INSTANCE.berryBlock("chilan");
        CHOPLE_BERRY = INSTANCE.berryBlock("chople");
        COBA_BERRY = INSTANCE.berryBlock("coba");
        COLBUR_BERRY = INSTANCE.berryBlock("colbur");
        CORNN_BERRY = INSTANCE.berryBlock("cornn");
        CUSTAP_BERRY = INSTANCE.berryBlock("custap");
        DURIN_BERRY = INSTANCE.berryBlock("durin");
        ENIGMA_BERRY = INSTANCE.berryBlock("enigma");
        FIGY_BERRY = INSTANCE.berryBlock("figy");
        GANLON_BERRY = INSTANCE.berryBlock("ganlon");
        GREPA_BERRY = INSTANCE.berryBlock("grepa");
        HABAN_BERRY = INSTANCE.berryBlock("haban");
        HONDEW_BERRY = INSTANCE.berryBlock("hondew");
        HOPO_BERRY = INSTANCE.berryBlock("hopo");
        IAPAPA_BERRY = INSTANCE.berryBlock("iapapa");
        JABOCA_BERRY = INSTANCE.berryBlock("jaboca");
        KASIB_BERRY = INSTANCE.berryBlock("kasib");
        KEBIA_BERRY = INSTANCE.berryBlock("kebia");
        KEE_BERRY = INSTANCE.berryBlock("kee");
        KELPSY_BERRY = INSTANCE.berryBlock("kelpsy");
        LANSAT_BERRY = INSTANCE.berryBlock("lansat");
        LEPPA_BERRY = INSTANCE.berryBlock("leppa");
        LIECHI_BERRY = INSTANCE.berryBlock("liechi");
        LUM_BERRY = INSTANCE.berryBlock("lum");
        MAGO_BERRY = INSTANCE.berryBlock("mago");
        MAGOST_BERRY = INSTANCE.berryBlock("magost");
        MARANGA_BERRY = INSTANCE.berryBlock("maranga");
        MICLE_BERRY = INSTANCE.berryBlock("micle");
        NANAB_BERRY = INSTANCE.berryBlock("nanab");
        NOMEL_BERRY = INSTANCE.berryBlock("nomel");
        OCCA_BERRY = INSTANCE.berryBlock("occa");
        ORAN_BERRY = INSTANCE.berryBlock("oran");
        PAMTRE_BERRY = INSTANCE.berryBlock("pamtre");
        PASSHO_BERRY = INSTANCE.berryBlock("passho");
        PAYAPA_BERRY = INSTANCE.berryBlock("payapa");
        PECHA_BERRY = INSTANCE.berryBlock("pecha");
        PERSIM_BERRY = INSTANCE.berryBlock("persim");
        PETAYA_BERRY = INSTANCE.berryBlock("petaya");
        PINAP_BERRY = INSTANCE.berryBlock("pinap");
        POMEG_BERRY = INSTANCE.berryBlock("pomeg");
        QUALOT_BERRY = INSTANCE.berryBlock("qualot");
        RABUTA_BERRY = INSTANCE.berryBlock("rabuta");
        RAWST_BERRY = INSTANCE.berryBlock("rawst");
        RAZZ_BERRY = INSTANCE.berryBlock("razz");
        RINDO_BERRY = INSTANCE.berryBlock("rindo");
        ROSELI_BERRY = INSTANCE.berryBlock("roseli");
        ROWAP_BERRY = INSTANCE.berryBlock("rowap");
        SALAC_BERRY = INSTANCE.berryBlock("salac");
        SHUCA_BERRY = INSTANCE.berryBlock("shuca");
        SITRUS_BERRY = INSTANCE.berryBlock("sitrus");
        SPELON_BERRY = INSTANCE.berryBlock("spelon");
        STARF_BERRY = INSTANCE.berryBlock("starf");
        TAMATO_BERRY = INSTANCE.berryBlock("tamato");
        TANGA_BERRY = INSTANCE.berryBlock("tanga");
        TOUGA_BERRY = INSTANCE.berryBlock("touga");
        WACAN_BERRY = INSTANCE.berryBlock("wacan");
        WATMEL_BERRY = INSTANCE.berryBlock("watmel");
        WEPEAR_BERRY = INSTANCE.berryBlock("wepear");
        WIKI_BERRY = INSTANCE.berryBlock("wiki");
        YACHE_BERRY = INSTANCE.berryBlock("yache");
        Triple[] tripleArray2 = new Triple[]{new Triple((Object)APRICORN_LOG, (Object)5, (Object)5), new Triple((Object)STRIPPED_APRICORN_LOG, (Object)5, (Object)5), new Triple((Object)APRICORN_WOOD, (Object)5, (Object)5), new Triple((Object)STRIPPED_APRICORN_WOOD, (Object)5, (Object)5), new Triple((Object)APRICORN_PLANKS, (Object)5, (Object)20), new Triple((Object)APRICORN_LEAVES, (Object)30, (Object)60), new Triple((Object)APRICORN_FENCE, (Object)5, (Object)20), new Triple((Object)APRICORN_FENCE_GATE, (Object)5, (Object)20), new Triple((Object)APRICORN_SLAB, (Object)5, (Object)20), new Triple((Object)APRICORN_STAIRS, (Object)5, (Object)20)};
        for (Triple data : tripleArray = tripleArray2) {
            boolean bl = false;
            INSTANCE.setFlammable(data.getFirst(), ((Number)data.getSecond()).intValue(), ((Number)data.getThird()).intValue());
        }
    }
}

