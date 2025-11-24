/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.annotations.SerializedName
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.ArraysKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  kotlin.ranges.IntRange
 *  kotlin.ranges.RangesKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.tags.TagKey
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.LevelReader
 *  net.minecraft.world.level.biome.Biome
 *  net.minecraft.world.level.block.Block
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.phys.shapes.Shapes
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonBlocks;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Berries;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.Flavor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthFactor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.GrowthPoint;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.berry.spawncondition.BerrySpawnCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.berry.BerryYieldCalculationEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.mulch.MulchVariant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BerryBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.BerryItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import com.google.gson.annotations.SerializedName;
import io.netty.buffer.ByteBuf;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00de\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\b\u0018\u0000 \u008e\u00012\u00020\u0001:\u0002\u008e\u0001B\u0084\u0002\u0012\u0006\u0010`\u001a\u00020F\u0012\u0006\u00109\u001a\u000208\u0012\u0012\u0010o\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020n0m0l\u0012\u0006\u0010]\u001a\u000208\u0012\u0006\u0010w\u001a\u000208\u0012\f\u0010?\u001a\b\u0012\u0004\u0012\u00020>0=\u0012\f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u000e0\r\u0012\f\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020~0l\u0012\f\u0010W\u001a\b\u0012\u0004\u0012\u00020V0U\u0012\b\b\u0002\u0010s\u001a\u00020\u0016\u0012\u0012\u0010i\u001a\u000e\u0012\u0004\u0012\u00020F\u0012\u0004\u0012\u00020F0C\u0012\r\u0010\u0083\u0001\u001a\b\u0012\u0004\u0012\u00020\u00190\r\u0012\f\u0010h\u001a\b\u0012\u0004\u0012\u00020\u00190\r\u0012\u0012\u0010D\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\f0C\u0012\u0014\u0010\u0085\u0001\u001a\u000f\u0012\u0004\u0012\u00020\f\u0012\u0005\u0012\u00030\u0084\u00010C\u0012\u0006\u0010G\u001a\u00020F\u0012\u0006\u0010K\u001a\u00020F\u0012\u0006\u0010M\u001a\u00020F\u0012\u0006\u0010O\u001a\u00020F\u0012\b\u0010\u0088\u0001\u001a\u00030\u0087\u0001\u00a2\u0006\u0006\b\u008c\u0001\u0010\u008d\u0001J\u000f\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J9\u0010\u000f\u001a\u0014\u0012\u0004\u0012\u00020\f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\u000b2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J1\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\t2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0015\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0015\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001d\u0010\u001c\u001a\u00020\u001b2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\rH\u0002\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0015\u0010 \u001a\u00020\u00162\u0006\u0010\u001f\u001a\u00020\u001e\u00a2\u0006\u0004\b \u0010!J\u0017\u0010'\u001a\u00020$2\u0006\u0010#\u001a\u00020\"H\u0000\u00a2\u0006\u0004\b%\u0010&J\u0015\u0010)\u001a\u00020\f2\u0006\u0010)\u001a\u00020(\u00a2\u0006\u0004\b)\u0010*J\u000f\u0010,\u001a\u0004\u0018\u00010+\u00a2\u0006\u0004\b,\u0010-J\r\u0010.\u001a\u00020\f\u00a2\u0006\u0004\b.\u0010/J\r\u00100\u001a\u00020\f\u00a2\u0006\u0004\b0\u0010/J\u0017\u00101\u001a\u0004\u0018\u00010\u00002\u0006\u0010\u0015\u001a\u00020\u0000\u00a2\u0006\u0004\b1\u00102J\u0017\u00104\u001a\u0004\u0018\u00010\u00002\u0006\u00103\u001a\u00020\u0000\u00a2\u0006\u0004\b4\u00102J\u000f\u00107\u001a\u00020$H\u0000\u00a2\u0006\u0004\b5\u00106R\u0017\u00109\u001a\u0002088\u0006\u00a2\u0006\f\n\u0004\b9\u0010:\u001a\u0004\b;\u0010<R\u001d\u0010?\u001a\b\u0012\u0004\u0012\u00020>0=8\u0006\u00a2\u0006\f\n\u0004\b?\u0010@\u001a\u0004\bA\u0010BR \u0010D\u001a\u000e\u0012\u0004\u0012\u00020(\u0012\u0004\u0012\u00020\f0C8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bD\u0010ER\u001a\u0010G\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\f\n\u0004\bG\u0010H\u001a\u0004\bI\u0010JR\u0017\u0010K\u001a\u00020F8\u0006\u00a2\u0006\f\n\u0004\bK\u0010H\u001a\u0004\bL\u0010JR\u001a\u0010M\u001a\u00020F8\u0006X\u0087\u0004\u00a2\u0006\f\n\u0004\bM\u0010H\u001a\u0004\bN\u0010JR\u0017\u0010O\u001a\u00020F8\u0006\u00a2\u0006\f\n\u0004\bO\u0010H\u001a\u0004\bP\u0010JR\u001d\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u000e0\r8\u0006\u00a2\u0006\f\n\u0004\bQ\u0010R\u001a\u0004\bS\u0010TR(\u0010W\u001a\b\u0012\u0004\u0012\u00020V0U8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bW\u0010X\u001a\u0004\bY\u0010Z\"\u0004\b[\u0010\\R\u0017\u0010]\u001a\u0002088\u0006\u00a2\u0006\f\n\u0004\b]\u0010:\u001a\u0004\b^\u0010<R*\u0010`\u001a\u00020F2\u0006\u0010_\u001a\u00020F8\u0006@@X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b`\u0010H\u001a\u0004\ba\u0010J\"\u0004\bb\u0010cR$\u0010d\u001a\u00020\u001b2\u0006\u0010_\u001a\u00020\u001b8\u0006@BX\u0086.\u00a2\u0006\f\n\u0004\bd\u0010e\u001a\u0004\bf\u0010gR\u001a\u0010h\u001a\b\u0012\u0004\u0012\u00020\u00190\r8\u0002X\u0083\u0004\u00a2\u0006\u0006\n\u0004\bh\u0010RR#\u0010i\u001a\u000e\u0012\u0004\u0012\u00020F\u0012\u0004\u0012\u00020F0C8\u0006\u00a2\u0006\f\n\u0004\bi\u0010E\u001a\u0004\bj\u0010kR#\u0010o\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020n0m0l8\u0006\u00a2\u0006\f\n\u0004\bo\u0010p\u001a\u0004\bq\u0010rR\u0017\u0010s\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\bs\u0010t\u001a\u0004\bu\u0010vR\u0017\u0010w\u001a\u0002088\u0006\u00a2\u0006\f\n\u0004\bw\u0010:\u001a\u0004\bx\u0010<R2\u0010{\u001a\u001e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u001b0yj\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u001b`z8\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\b{\u0010|R2\u0010}\u001a\u001e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u001b0yj\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u001b`z8\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\b}\u0010|R\u001e\u0010\u007f\u001a\b\u0012\u0004\u0012\u00020~0l8\u0006\u00a2\u0006\r\n\u0004\b\u007f\u0010p\u001a\u0005\b\u0080\u0001\u0010rR'\u0010\u0081\u0001\u001a\u00020\u001b2\u0006\u0010_\u001a\u00020\u001b8\u0006@BX\u0086.\u00a2\u0006\u000e\n\u0005\b\u0081\u0001\u0010e\u001a\u0005\b\u0082\u0001\u0010gR\u001c\u0010\u0083\u0001\u001a\b\u0012\u0004\u0012\u00020\u00190\r8\u0002X\u0083\u0004\u00a2\u0006\u0007\n\u0005\b\u0083\u0001\u0010RR'\u0010\u0085\u0001\u001a\u000f\u0012\u0004\u0012\u00020\f\u0012\u0005\u0012\u00030\u0084\u00010C8\u0006\u00a2\u0006\u000e\n\u0005\b\u0085\u0001\u0010E\u001a\u0005\b\u0086\u0001\u0010kR\u001d\u0010\u0088\u0001\u001a\u00030\u0087\u00018\u0006\u00a2\u0006\u0010\n\u0006\b\u0088\u0001\u0010\u0089\u0001\u001a\u0006\b\u008a\u0001\u0010\u008b\u0001\u00a8\u0006\u008f\u0001"}, d2={"Lcom/cobblemon/mod/common/api/berry/Berry;", "", "Lcom/cobblemon/mod/common/block/BerryBlock;", "block", "()Lcom/cobblemon/mod/common/block/BerryBlock;", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lnet/minecraft/core/BlockPos;", "pos", "Lkotlin/Pair;", "", "", "Lcom/cobblemon/mod/common/api/berry/GrowthFactor;", "bonusYield", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)Lkotlin/Pair;", "Lnet/minecraft/world/entity/LivingEntity;", "placer", "calculateYield", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/LivingEntity;)I", "partner", "", "canMutateWith", "(Lcom/cobblemon/mod/common/api/berry/Berry;)Z", "Lnet/minecraft/world/phys/AABB;", "boxes", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "createAndUniteShapes", "(Ljava/util/Collection;)Lnet/minecraft/world/phys/shapes/VoxelShape;", "Lcom/cobblemon/mod/common/pokemon/Nature;", "nature", "dislikedBy", "(Lcom/cobblemon/mod/common/pokemon/Nature;)Z", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode$common", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "Lcom/cobblemon/mod/common/api/berry/Flavor;", "flavor", "(Lcom/cobblemon/mod/common/api/berry/Flavor;)I", "Lcom/cobblemon/mod/common/item/BerryItem;", "item", "()Lcom/cobblemon/mod/common/item/BerryItem;", "maxYield", "()I", "minYield", "mutationWith", "(Lcom/cobblemon/mod/common/api/berry/Berry;)Lcom/cobblemon/mod/common/api/berry/Berry;", "resulting", "partnerForMutation", "validate$common", "()V", "validate", "Lkotlin/ranges/IntRange;", "baseYield", "Lkotlin/ranges/IntRange;", "getBaseYield", "()Lkotlin/ranges/IntRange;", "Ljava/util/EnumSet;", "Lcom/cobblemon/mod/common/api/mulch/MulchVariant;", "favoriteMulches", "Ljava/util/EnumSet;", "getFavoriteMulches", "()Ljava/util/EnumSet;", "", "flavors", "Ljava/util/Map;", "Lnet/minecraft/resources/ResourceLocation;", "flowerModelIdentifier", "Lnet/minecraft/resources/ResourceLocation;", "getFlowerModelIdentifier", "()Lnet/minecraft/resources/ResourceLocation;", "flowerTexture", "getFlowerTexture", "fruitModelIdentifier", "getFruitModelIdentifier", "fruitTexture", "getFruitTexture", "growthFactors", "Ljava/util/Collection;", "getGrowthFactors", "()Ljava/util/Collection;", "", "Lcom/cobblemon/mod/common/api/berry/GrowthPoint;", "growthPoints", "[Lcom/cobblemon/mod/common/api/berry/GrowthPoint;", "getGrowthPoints", "()[Lcom/cobblemon/mod/common/api/berry/GrowthPoint;", "setGrowthPoints", "([Lcom/cobblemon/mod/common/api/berry/GrowthPoint;)V", "growthTime", "getGrowthTime", "<set-?>", "identifier", "getIdentifier", "setIdentifier$common", "(Lnet/minecraft/resources/ResourceLocation;)V", "matureShape", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "getMatureShape", "()Lnet/minecraft/world/phys/shapes/VoxelShape;", "matureShapeBoxes", "mutations", "getMutations", "()Ljava/util/Map;", "", "Lnet/minecraft/tags/TagKey;", "Lnet/minecraft/world/level/biome/Biome;", "preferredBiomeTags", "Ljava/util/List;", "getPreferredBiomeTags", "()Ljava/util/List;", "randomizedGrowthPoints", "Z", "getRandomizedGrowthPoints", "()Z", "refreshRate", "getRefreshRate", "Ljava/util/HashMap;", "Lkotlin/collections/HashMap;", "shapedFlower", "Ljava/util/HashMap;", "shapedFruit", "Lcom/cobblemon/mod/common/api/berry/spawncondition/BerrySpawnCondition;", "spawnConditions", "getSpawnConditions", "sproutShape", "getSproutShape", "sproutShapeBoxes", "Ljava/awt/Color;", "tintIndexes", "getTintIndexes", "", "weight", "F", "getWeight", "()F", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lkotlin/ranges/IntRange;Ljava/util/List;Lkotlin/ranges/IntRange;Lkotlin/ranges/IntRange;Ljava/util/EnumSet;Ljava/util/Collection;Ljava/util/List;[Lcom/cobblemon/mod/common/api/berry/GrowthPoint;ZLjava/util/Map;Ljava/util/Collection;Ljava/util/Collection;Ljava/util/Map;Ljava/util/Map;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;F)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nBerry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Berry.kt\ncom/cobblemon/mod/common/api/berry/Berry\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,348:1\n17#2,2:349\n19#2:353\n13579#3,2:351\n1#4:354\n1855#5,2:355\n1855#5,2:357\n1855#5,2:359\n*S KotlinDebug\n*F\n+ 1 Berry.kt\ncom/cobblemon/mod/common/api/berry/Berry\n*L\n169#1:349,2\n169#1:353\n169#1:351,2\n238#1:355,2\n297#1:357,2\n310#1:359,2\n*E\n"})
public final class Berry {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final IntRange baseYield;
    @NotNull
    private final List<TagKey<Biome>> preferredBiomeTags;
    @NotNull
    private final IntRange growthTime;
    @NotNull
    private final IntRange refreshRate;
    @NotNull
    private final EnumSet<MulchVariant> favoriteMulches;
    @NotNull
    private final Collection<GrowthFactor> growthFactors;
    @NotNull
    private final List<BerrySpawnCondition> spawnConditions;
    @NotNull
    private GrowthPoint[] growthPoints;
    private final boolean randomizedGrowthPoints;
    @NotNull
    private final Map<ResourceLocation, ResourceLocation> mutations;
    @SerializedName(value="sproutShape")
    @NotNull
    private final Collection<AABB> sproutShapeBoxes;
    @SerializedName(value="matureShape")
    @NotNull
    private final Collection<AABB> matureShapeBoxes;
    @NotNull
    private final Map<Flavor, Integer> flavors;
    @NotNull
    private final Map<Integer, Color> tintIndexes;
    @SerializedName(value="flowerModel")
    @NotNull
    private final ResourceLocation flowerModelIdentifier;
    @NotNull
    private final ResourceLocation flowerTexture;
    @SerializedName(value="fruitModel")
    @NotNull
    private final ResourceLocation fruitModelIdentifier;
    @NotNull
    private final ResourceLocation fruitTexture;
    private final float weight;
    @NotNull
    private transient ResourceLocation identifier;
    private transient HashMap<Integer, VoxelShape> shapedFlower;
    private transient HashMap<Integer, VoxelShape> shapedFruit;
    private transient VoxelShape sproutShape;
    private transient VoxelShape matureShape;

    public Berry(@NotNull ResourceLocation identifier, @NotNull IntRange baseYield, @NotNull List<TagKey<Biome>> preferredBiomeTags, @NotNull IntRange growthTime, @NotNull IntRange refreshRate, @NotNull EnumSet<MulchVariant> favoriteMulches, @NotNull Collection<? extends GrowthFactor> growthFactors, @NotNull List<? extends BerrySpawnCondition> spawnConditions, @NotNull GrowthPoint[] growthPoints, boolean randomizedGrowthPoints, @NotNull Map<ResourceLocation, ? extends ResourceLocation> mutations, @NotNull Collection<? extends AABB> sproutShapeBoxes, @NotNull Collection<? extends AABB> matureShapeBoxes, @NotNull Map<Flavor, Integer> flavors, @NotNull Map<Integer, ? extends Color> tintIndexes, @NotNull ResourceLocation flowerModelIdentifier, @NotNull ResourceLocation flowerTexture, @NotNull ResourceLocation fruitModelIdentifier, @NotNull ResourceLocation fruitTexture, float weight) {
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Intrinsics.checkNotNullParameter((Object)baseYield, (String)"baseYield");
        Intrinsics.checkNotNullParameter(preferredBiomeTags, (String)"preferredBiomeTags");
        Intrinsics.checkNotNullParameter((Object)growthTime, (String)"growthTime");
        Intrinsics.checkNotNullParameter((Object)refreshRate, (String)"refreshRate");
        Intrinsics.checkNotNullParameter(favoriteMulches, (String)"favoriteMulches");
        Intrinsics.checkNotNullParameter(growthFactors, (String)"growthFactors");
        Intrinsics.checkNotNullParameter(spawnConditions, (String)"spawnConditions");
        Intrinsics.checkNotNullParameter((Object)growthPoints, (String)"growthPoints");
        Intrinsics.checkNotNullParameter(mutations, (String)"mutations");
        Intrinsics.checkNotNullParameter(sproutShapeBoxes, (String)"sproutShapeBoxes");
        Intrinsics.checkNotNullParameter(matureShapeBoxes, (String)"matureShapeBoxes");
        Intrinsics.checkNotNullParameter(flavors, (String)"flavors");
        Intrinsics.checkNotNullParameter(tintIndexes, (String)"tintIndexes");
        Intrinsics.checkNotNullParameter((Object)flowerModelIdentifier, (String)"flowerModelIdentifier");
        Intrinsics.checkNotNullParameter((Object)flowerTexture, (String)"flowerTexture");
        Intrinsics.checkNotNullParameter((Object)fruitModelIdentifier, (String)"fruitModelIdentifier");
        Intrinsics.checkNotNullParameter((Object)fruitTexture, (String)"fruitTexture");
        this.baseYield = baseYield;
        this.preferredBiomeTags = preferredBiomeTags;
        this.growthTime = growthTime;
        this.refreshRate = refreshRate;
        this.favoriteMulches = favoriteMulches;
        this.growthFactors = growthFactors;
        this.spawnConditions = spawnConditions;
        this.growthPoints = growthPoints;
        this.randomizedGrowthPoints = randomizedGrowthPoints;
        this.mutations = mutations;
        this.sproutShapeBoxes = sproutShapeBoxes;
        this.matureShapeBoxes = matureShapeBoxes;
        this.flavors = flavors;
        this.tintIndexes = tintIndexes;
        this.flowerModelIdentifier = flowerModelIdentifier;
        this.flowerTexture = flowerTexture;
        this.fruitModelIdentifier = fruitModelIdentifier;
        this.fruitTexture = fruitTexture;
        this.weight = weight;
        this.identifier = identifier;
        this.validate$common();
    }

    public /* synthetic */ Berry(ResourceLocation resourceLocation, IntRange intRange, List list, IntRange intRange2, IntRange intRange3, EnumSet enumSet, Collection collection, List list2, GrowthPoint[] growthPointArray, boolean bl, Map map, Collection collection2, Collection collection3, Map map2, Map map3, ResourceLocation resourceLocation2, ResourceLocation resourceLocation3, ResourceLocation resourceLocation4, ResourceLocation resourceLocation5, float f, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 0x200) != 0) {
            bl = true;
        }
        this(resourceLocation, intRange, list, intRange2, intRange3, enumSet, collection, list2, growthPointArray, bl, map, collection2, collection3, map2, map3, resourceLocation2, resourceLocation3, resourceLocation4, resourceLocation5, f);
    }

    @NotNull
    public final IntRange getBaseYield() {
        return this.baseYield;
    }

    @NotNull
    public final List<TagKey<Biome>> getPreferredBiomeTags() {
        return this.preferredBiomeTags;
    }

    @NotNull
    public final IntRange getGrowthTime() {
        return this.growthTime;
    }

    @NotNull
    public final IntRange getRefreshRate() {
        return this.refreshRate;
    }

    @NotNull
    public final EnumSet<MulchVariant> getFavoriteMulches() {
        return this.favoriteMulches;
    }

    @NotNull
    public final Collection<GrowthFactor> getGrowthFactors() {
        return this.growthFactors;
    }

    @NotNull
    public final List<BerrySpawnCondition> getSpawnConditions() {
        return this.spawnConditions;
    }

    @NotNull
    public final GrowthPoint[] getGrowthPoints() {
        return this.growthPoints;
    }

    public final void setGrowthPoints(@NotNull GrowthPoint[] growthPointArray) {
        Intrinsics.checkNotNullParameter((Object)growthPointArray, (String)"<set-?>");
        this.growthPoints = growthPointArray;
    }

    public final boolean getRandomizedGrowthPoints() {
        return this.randomizedGrowthPoints;
    }

    @NotNull
    public final Map<ResourceLocation, ResourceLocation> getMutations() {
        return this.mutations;
    }

    @NotNull
    public final Map<Integer, Color> getTintIndexes() {
        return this.tintIndexes;
    }

    @NotNull
    public final ResourceLocation getFlowerModelIdentifier() {
        return this.flowerModelIdentifier;
    }

    @NotNull
    public final ResourceLocation getFlowerTexture() {
        return this.flowerTexture;
    }

    @NotNull
    public final ResourceLocation getFruitModelIdentifier() {
        return this.fruitModelIdentifier;
    }

    @NotNull
    public final ResourceLocation getFruitTexture() {
        return this.fruitTexture;
    }

    public final float getWeight() {
        return this.weight;
    }

    @NotNull
    public final ResourceLocation getIdentifier() {
        return this.identifier;
    }

    public final void setIdentifier$common(@NotNull ResourceLocation resourceLocation) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"<set-?>");
        this.identifier = resourceLocation;
    }

    @NotNull
    public final VoxelShape getSproutShape() {
        VoxelShape voxelShape = this.sproutShape;
        if (voxelShape != null) {
            return voxelShape;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"sproutShape");
        return null;
    }

    @NotNull
    public final VoxelShape getMatureShape() {
        VoxelShape voxelShape = this.matureShape;
        if (voxelShape != null) {
            return voxelShape;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"matureShape");
        return null;
    }

    @Nullable
    public final BerryItem item() {
        return CobblemonItems.INSTANCE.berries().get(this.identifier);
    }

    @Nullable
    public final BerryBlock block() {
        return CobblemonBlocks.INSTANCE.berries().get(this.identifier);
    }

    public final int flavor(@NotNull Flavor flavor) {
        Intrinsics.checkNotNullParameter((Object)((Object)flavor), (String)"flavor");
        Integer n = this.flavors.get((Object)flavor);
        return n != null ? n : 0;
    }

    public final boolean dislikedBy(@NotNull Nature nature) {
        Intrinsics.checkNotNullParameter((Object)nature, (String)"nature");
        Flavor flavor = nature.getDislikedFlavor();
        if (flavor == null) {
            return false;
        }
        Flavor dislikedFlavor = flavor;
        return this.flavor(dislikedFlavor) > 0;
    }

    /*
     * WARNING - void declaration
     */
    public final int calculateYield(@NotNull Level world, @NotNull BlockState state, @NotNull BlockPos pos, @Nullable LivingEntity placer) {
        void this_$iv;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        int base = RangesKt.random((IntRange)this.baseYield, (Random)((Random)Random.Default));
        Pair<Integer, Collection<GrowthFactor>> bonus = this.bonusYield(world, state, pos);
        int yield = 0;
        yield = base + ((Number)bonus.getFirst()).intValue();
        BlockEntity blockEntity = world.m_7702_(pos);
        Intrinsics.checkNotNull((Object)blockEntity, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity");
        BerryBlockEntity treeEntity = (BerryBlockEntity)blockEntity;
        if (BerryBlock.Companion.getMulch(state) == MulchVariant.RICH) {
            yield = Math.min(yield + 1, this.maxYield());
            treeEntity.decrementMulchDuration(world, pos, state);
        }
        BerryYieldCalculationEvent event = new BerryYieldCalculationEvent(this, world, state, pos, placer, yield, (Collection)bonus.getSecond());
        EventObservable<BerryYieldCalculationEvent> eventObservable = CobblemonEvents.BERRY_YIELD;
        BerryYieldCalculationEvent[] berryYieldCalculationEventArray = new BerryYieldCalculationEvent[]{event};
        BerryYieldCalculationEvent[] events$iv = berryYieldCalculationEventArray;
        boolean $i$f$post = false;
        this_$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
        BerryYieldCalculationEvent[] $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            BerryYieldCalculationEvent element$iv$iv;
            BerryYieldCalculationEvent it = element$iv$iv = $this$forEach$iv$iv[i];
            boolean bl = false;
            yield = it.getYield();
        }
        return yield;
    }

    public static /* synthetic */ int calculateYield$default(Berry berry, Level level, BlockState blockState, BlockPos blockPos2, LivingEntity livingEntity, int n, Object object) {
        if ((n & 8) != 0) {
            livingEntity = null;
        }
        return berry.calculateYield(level, blockState, blockPos2, livingEntity);
    }

    /*
     * WARNING - void declaration
     */
    public final int minYield() {
        int n;
        Iterable iterable = this.growthFactors;
        int n2 = this.baseYield.getFirst();
        int n3 = 0;
        for (Object t : iterable) {
            void it;
            GrowthFactor growthFactor = (GrowthFactor)t;
            n = n3;
            boolean bl = false;
            int n4 = it.minYield();
            n3 = n + n4;
        }
        n = n3;
        return n2 + n;
    }

    /*
     * WARNING - void declaration
     */
    public final int maxYield() {
        int n;
        Iterable iterable = this.growthFactors;
        int n2 = this.baseYield.getLast();
        int n3 = 0;
        for (Object t : iterable) {
            void it;
            GrowthFactor growthFactor = (GrowthFactor)t;
            n = n3;
            boolean bl = false;
            int n4 = it.maxYield();
            n3 = n + n4;
        }
        n = n3;
        return n2 + n;
    }

    public final boolean canMutateWith(@NotNull Berry partner) {
        Intrinsics.checkNotNullParameter((Object)partner, (String)"partner");
        return this.mutationWith(partner) != null;
    }

    @Nullable
    public final Berry mutationWith(@NotNull Berry partner) {
        Intrinsics.checkNotNullParameter((Object)partner, (String)"partner");
        ResourceLocation resourceLocation = this.mutations.get(partner.identifier);
        if (resourceLocation == null) {
            return null;
        }
        ResourceLocation berryIdentifier = resourceLocation;
        return Berries.INSTANCE.getByIdentifier(berryIdentifier);
    }

    @Nullable
    public final Berry partnerForMutation(@NotNull Berry resulting) {
        Berry berry;
        block1: {
            Intrinsics.checkNotNullParameter((Object)resulting, (String)"resulting");
            for (Map.Entry<ResourceLocation, ResourceLocation> entry : this.mutations.entrySet()) {
                boolean bl = false;
                ResourceLocation partner = entry.getKey();
                ResourceLocation result = entry.getValue();
                Berry object2 = Intrinsics.areEqual((Object)result, (Object)resulting.identifier) ? Berries.INSTANCE.getByIdentifier(partner) : null;
                if (object2 == null) continue;
                berry = object2;
                break block1;
            }
            berry = null;
        }
        return berry;
    }

    public final void validate$common() {
        if (this.baseYield.getFirst() < 0 || this.baseYield.getLast() < 0) {
            throw new IllegalArgumentException("A berry base yield must be a positive range");
        }
        if (this.growthTime.getFirst() < 0 || this.growthTime.getLast() < 0) {
            throw new IllegalArgumentException("The growth time must be a positive range");
        }
        if (this.refreshRate.getFirst() < 0 || this.refreshRate.getLast() < 0) {
            throw new IllegalArgumentException("The refresh rate must be a positive range");
        }
        Iterable $this$forEach$iv = this.growthFactors;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            GrowthFactor it = (GrowthFactor)element$iv;
            boolean bl = false;
            it.validateArguments();
        }
        int maxYield = this.maxYield();
        if (this.growthPoints.length < maxYield) {
            throw new IllegalArgumentException("Anchor points must have enough elements for the max possible yield of " + maxYield + " you've provided " + this.growthPoints.length + " points");
        }
        this.shapedFlower = new HashMap();
        this.shapedFruit = new HashMap();
        this.sproutShape = this.createAndUniteShapes(this.sproutShapeBoxes);
        this.matureShape = this.createAndUniteShapes(this.matureShapeBoxes);
    }

    public final void encode$common(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130085_(this.identifier);
        buffer.writeInt(this.baseYield.getFirst());
        buffer.writeInt(this.baseYield.getLast());
        buffer.m_245616_(this.favoriteMulches, MulchVariant.class);
        buffer.writeInt(this.growthTime.getFirst());
        buffer.writeInt(this.growthTime.getLast());
        buffer.writeInt(this.refreshRate.getFirst());
        buffer.writeInt(this.refreshRate.getLast());
        buffer.m_236828_((Collection)ArraysKt.toList((Object[])this.growthPoints), Berry::encode$lambda$5);
        buffer.writeBoolean(this.randomizedGrowthPoints);
        buffer.m_236831_(this.mutations, Berry::encode$lambda$6, Berry::encode$lambda$7);
        buffer.m_236828_(this.sproutShapeBoxes, Berry::encode$lambda$8);
        buffer.m_236828_(this.matureShapeBoxes, Berry::encode$lambda$9);
        buffer.m_236831_(this.flavors, Berry::encode$lambda$10, Berry::encode$lambda$11);
        buffer.m_236831_(this.tintIndexes, Berry::encode$lambda$12, Berry::encode$lambda$13);
        buffer.m_130085_(this.flowerModelIdentifier);
        buffer.m_130085_(this.flowerTexture);
        buffer.m_130085_(this.fruitModelIdentifier);
        buffer.m_130085_(this.fruitTexture);
    }

    private final Pair<Integer, Collection<GrowthFactor>> bonusYield(Level world, BlockState state, BlockPos pos) {
        int bonus = 0;
        ArrayList passed = new ArrayList();
        MulchVariant mulchVariant = BerryBlock.Companion.getMulch(state);
        boolean hasBiomeMulch = this.favoriteMulches.contains((Object)mulchVariant);
        Iterable $this$forEach$iv = this.growthFactors;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            GrowthFactor factor = (GrowthFactor)element$iv;
            boolean bl = false;
            if (factor.isValid((LevelReader)world, state, pos)) {
                bonus += factor.yield();
                ((Collection)passed).add(factor);
                continue;
            }
            if (!hasBiomeMulch) continue;
            bonus += factor.yield();
        }
        return TuplesKt.to((Object)bonus, passed);
    }

    private final VoxelShape createAndUniteShapes(Collection<? extends AABB> boxes) {
        VoxelShape shape = null;
        Iterable $this$forEach$iv = boxes;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            AABB box = (AABB)element$iv;
            boolean bl = false;
            shape = shape == null ? Block.m_49796_((double)box.f_82288_, (double)box.f_82289_, (double)box.f_82290_, (double)box.f_82291_, (double)box.f_82292_, (double)box.f_82293_) : Shapes.m_83110_((VoxelShape)shape, (VoxelShape)Block.m_49796_((double)box.f_82288_, (double)box.f_82289_, (double)box.f_82290_, (double)box.f_82291_, (double)box.f_82292_, (double)box.f_82293_));
        }
        VoxelShape voxelShape = shape;
        if (voxelShape == null) {
            VoxelShape voxelShape2 = Shapes.m_83144_();
            voxelShape = voxelShape2;
            Intrinsics.checkNotNullExpressionValue((Object)voxelShape2, (String)"fullCube()");
        }
        return voxelShape;
    }

    private static final void encode$lambda$5(FriendlyByteBuf writer, GrowthPoint value2) {
        writer.writeDouble(value2.getPosition().f_82479_);
        writer.writeDouble(value2.getPosition().f_82480_);
        writer.writeDouble(value2.getPosition().f_82481_);
        writer.writeDouble(value2.getRotation().f_82479_);
        writer.writeDouble(value2.getRotation().f_82480_);
        writer.writeDouble(value2.getRotation().f_82481_);
    }

    private static final void encode$lambda$6(FriendlyByteBuf writer, ResourceLocation key) {
        writer.m_130085_(key);
    }

    private static final void encode$lambda$7(FriendlyByteBuf writer, ResourceLocation value2) {
        writer.m_130085_(value2);
    }

    private static final void encode$lambda$8(FriendlyByteBuf writer, AABB value2) {
        Intrinsics.checkNotNullExpressionValue((Object)writer, (String)"writer");
        ByteBuf byteBuf = (ByteBuf)writer;
        Intrinsics.checkNotNullExpressionValue((Object)value2, (String)"value");
        NetExtensionsKt.writeBox(byteBuf, value2);
    }

    private static final void encode$lambda$9(FriendlyByteBuf writer, AABB value2) {
        Intrinsics.checkNotNullExpressionValue((Object)writer, (String)"writer");
        ByteBuf byteBuf = (ByteBuf)writer;
        Intrinsics.checkNotNullExpressionValue((Object)value2, (String)"value");
        NetExtensionsKt.writeBox(byteBuf, value2);
    }

    private static final void encode$lambda$10(FriendlyByteBuf writer, Flavor key) {
        writer.m_130068_((Enum)key);
    }

    private static final void encode$lambda$11(FriendlyByteBuf writer, Integer value2) {
        Intrinsics.checkNotNullExpressionValue((Object)value2, (String)"value");
        writer.writeInt(value2.intValue());
    }

    private static final void encode$lambda$12(FriendlyByteBuf writer, Integer key) {
        Intrinsics.checkNotNullExpressionValue((Object)key, (String)"key");
        writer.writeInt(key.intValue());
    }

    private static final void encode$lambda$13(FriendlyByteBuf writer, Color value2) {
        writer.writeInt(value2.getRGB());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0000\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/berry/Berry$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/api/berry/Berry;", "decode$common", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/api/berry/Berry;", "decode", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nBerry.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Berry.kt\ncom/cobblemon/mod/common/api/berry/Berry$Companion\n+ 2 ArraysJVM.kt\nkotlin/collections/ArraysKt__ArraysJVMKt\n*L\n1#1,348:1\n37#2,2:349\n*S KotlinDebug\n*F\n+ 1 Berry.kt\ncom/cobblemon/mod/common/api/berry/Berry$Companion\n*L\n331#1:349,2\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final Berry decode$common(@NotNull FriendlyByteBuf buffer) {
            void $this$toTypedArray$iv;
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            ResourceLocation identifier = buffer.m_130281_();
            IntRange baseYield = new IntRange(buffer.readInt(), buffer.readInt());
            EnumSet favMulchs = buffer.m_247336_(MulchVariant.class);
            IntRange growthTime = new IntRange(buffer.readInt(), buffer.readInt());
            IntRange refreshRate = new IntRange(buffer.readInt(), buffer.readInt());
            List list = buffer.m_236845_(Companion::decode$lambda$0);
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { reader\u2026dDouble()))\n            }");
            Collection collection = list;
            boolean $i$f$toTypedArray = false;
            void thisCollection$iv = $this$toTypedArray$iv;
            GrowthPoint[] growthPoints = thisCollection$iv.toArray(new GrowthPoint[0]);
            boolean randomizedGrowthPoints = buffer.readBoolean();
            Map mutations = buffer.m_236847_(Companion::decode$lambda$1, Companion::decode$lambda$2);
            List sproutShapeBoxes = buffer.m_236845_(Companion::decode$lambda$3);
            List matureShapeBoxes = buffer.m_236845_(Companion::decode$lambda$4);
            Map flavors = buffer.m_236847_(Companion::decode$lambda$5, Companion::decode$lambda$6);
            Map tintIndexes = buffer.m_236847_(Companion::decode$lambda$7, Companion::decode$lambda$8);
            ResourceLocation flowerModelIdentifier = buffer.m_130281_();
            ResourceLocation flowerTexture = buffer.m_130281_();
            ResourceLocation fruitModelIdentifier = buffer.m_130281_();
            ResourceLocation fruitTexture = buffer.m_130281_();
            Intrinsics.checkNotNullExpressionValue((Object)identifier, (String)"identifier");
            List list2 = CollectionsKt.emptyList();
            Intrinsics.checkNotNullExpressionValue((Object)favMulchs, (String)"favMulchs");
            Collection collection2 = SetsKt.emptySet();
            List list3 = CollectionsKt.emptyList();
            Intrinsics.checkNotNullExpressionValue((Object)mutations, (String)"mutations");
            Intrinsics.checkNotNullExpressionValue((Object)sproutShapeBoxes, (String)"sproutShapeBoxes");
            Collection collection3 = sproutShapeBoxes;
            Intrinsics.checkNotNullExpressionValue((Object)matureShapeBoxes, (String)"matureShapeBoxes");
            Collection collection4 = matureShapeBoxes;
            Intrinsics.checkNotNullExpressionValue((Object)flavors, (String)"flavors");
            Intrinsics.checkNotNullExpressionValue((Object)tintIndexes, (String)"tintIndexes");
            Intrinsics.checkNotNullExpressionValue((Object)flowerModelIdentifier, (String)"flowerModelIdentifier");
            Intrinsics.checkNotNullExpressionValue((Object)flowerTexture, (String)"flowerTexture");
            Intrinsics.checkNotNullExpressionValue((Object)fruitModelIdentifier, (String)"fruitModelIdentifier");
            Intrinsics.checkNotNullExpressionValue((Object)fruitTexture, (String)"fruitTexture");
            return new Berry(identifier, baseYield, list2, growthTime, refreshRate, favMulchs, collection2, list3, growthPoints, randomizedGrowthPoints, mutations, collection3, collection4, flavors, tintIndexes, flowerModelIdentifier, flowerTexture, fruitModelIdentifier, fruitTexture, 0.0f);
        }

        private static final GrowthPoint decode$lambda$0(FriendlyByteBuf reader) {
            return new GrowthPoint(new Vec3(reader.readDouble(), reader.readDouble(), reader.readDouble()), new Vec3(reader.readDouble(), reader.readDouble(), reader.readDouble()));
        }

        private static final ResourceLocation decode$lambda$1(FriendlyByteBuf reader) {
            return reader.m_130281_();
        }

        private static final ResourceLocation decode$lambda$2(FriendlyByteBuf reader) {
            return reader.m_130281_();
        }

        private static final AABB decode$lambda$3(FriendlyByteBuf it) {
            Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
            return NetExtensionsKt.readBox((ByteBuf)it);
        }

        private static final AABB decode$lambda$4(FriendlyByteBuf it) {
            Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
            return NetExtensionsKt.readBox((ByteBuf)it);
        }

        private static final Flavor decode$lambda$5(FriendlyByteBuf reader) {
            return (Flavor)reader.m_130066_(Flavor.class);
        }

        private static final Integer decode$lambda$6(FriendlyByteBuf reader) {
            return reader.readInt();
        }

        private static final Integer decode$lambda$7(FriendlyByteBuf reader) {
            return reader.readInt();
        }

        private static final Color decode$lambda$8(FriendlyByteBuf reader) {
            return new Color(reader.readInt());
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

