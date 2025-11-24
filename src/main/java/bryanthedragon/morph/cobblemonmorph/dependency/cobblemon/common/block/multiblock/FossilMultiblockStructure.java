/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.authlib.GameProfile
 *  kotlin.Deprecated
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.NbtUtils
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.tags.FluidTags
 *  net.minecraft.util.RandomSource
 *  net.minecraft.world.Containers
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.Pose
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.ItemLike
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.HorizontalDirectionalBlock
 *  net.minecraft.world.level.block.entity.BlockEntity
 *  net.minecraft.world.level.block.entity.BlockEntityTicker
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.level.block.state.properties.Property
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.multiblock;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.FossilRevivedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossil;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.Fossils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.fossil.NaturalMaterials;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.multiblock.MultiblockStructure;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.tags.CobblemonItemTags;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.FossilAnalyzerBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.MonitorBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.RestorationTankBlock;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.FossilMultiblockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.RestorationTankBlockEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.fossil.FossilState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.CancellableSoundController;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.CancellableSoundInstance;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokeball.PokeBall;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u00be\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010!\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0007\n\u0002\b\u0005\u0018\u0000 \u0081\u00012\u00020\u0001:\u0002\u0081\u0001B4\u0012\u0006\u0010h\u001a\u00020\u0006\u0012\u0006\u0010E\u001a\u00020\u0006\u0012\u0006\u0010r\u001a\u00020\u0006\u0012\b\b\u0002\u0010|\u001a\u00020\b\u0012\b\b\u0002\u0010~\u001a\u00020}\u00a2\u0006\u0005\b\u007f\u0010\u0080\u0001J+\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u00042\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0017\u00a2\u0006\u0004\b\t\u0010\nJ\u0015\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\b\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001d\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001d\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0014\u0010\u0013J\r\u0010\u0015\u001a\u00020\u0011\u00a2\u0006\u0004\b\u0015\u0010\u0016J%\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\u0004\b\u0019\u0010\u001aJ/\u0010\u001d\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u001c\u001a\u00020\u001b\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u0017\u0010 \u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b \u0010!J\u0017\u0010\"\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\"\u0010!J1\u0010%\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\b\u0010$\u001a\u0004\u0018\u00010#H\u0016\u00a2\u0006\u0004\b%\u0010&J7\u0010*\u001a\u00020\u001f2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010'2\b\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\u0010)\u001a\u0004\u0018\u00010(H\u0016\u00a2\u0006\u0004\b*\u0010+J?\u00103\u001a\u0002022\u0006\u0010,\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010-\u001a\u00020\u00062\u0006\u0010$\u001a\u00020#2\u0006\u0010/\u001a\u00020.2\u0006\u00101\u001a\u000200H\u0016\u00a2\u0006\u0004\b3\u00104J-\u00109\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u00106\u001a\u0002052\u0006\u00108\u001a\u000207\u00a2\u0006\u0004\b9\u0010:J\u0015\u0010;\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b;\u0010!J\u0015\u0010<\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b<\u0010!J\u0017\u0010=\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b=\u0010!J\u0017\u0010>\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b>\u0010!J\u0015\u0010?\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b?\u0010!J\u0015\u0010@\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b@\u0010!J\u0015\u0010A\u001a\u00020\u001f2\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\bA\u0010!J\u000f\u0010C\u001a\u00020BH\u0016\u00a2\u0006\u0004\bC\u0010DR\u0017\u0010E\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\bE\u0010F\u001a\u0004\bG\u0010HR\u001a\u0010I\u001a\u00020\u00068\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\bI\u0010F\u001a\u0004\bJ\u0010HR\"\u0010K\u001a\u00020\b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bK\u0010L\u001a\u0004\bM\u0010N\"\u0004\bO\u0010PR(\u0010R\u001a\b\u0012\u0004\u0012\u00020\u000f0Q8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bR\u0010S\u001a\u0004\bT\u0010U\"\u0004\bV\u0010WR\u0018\u0010Y\u001a\u0004\u0018\u00010X8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\bY\u0010ZR\u0017\u0010\\\u001a\u00020[8\u0006\u00a2\u0006\f\n\u0004\b\\\u0010]\u001a\u0004\b^\u0010_R$\u0010a\u001a\u00020\u00112\u0006\u0010`\u001a\u00020\u00118\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\ba\u0010b\u001a\u0004\bc\u0010\u0016R\u0016\u0010e\u001a\u00020d8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\be\u0010fR\u0016\u0010g\u001a\u00020d8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\bg\u0010fR\u0017\u0010h\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\bh\u0010F\u001a\u0004\bi\u0010HR$\u0010j\u001a\u00020\b2\u0006\u0010`\u001a\u00020\b8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\bj\u0010L\u001a\u0004\bk\u0010NR\u0016\u0010l\u001a\u00020\b8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\bl\u0010LR(\u0010n\u001a\u0004\u0018\u00010m2\b\u0010`\u001a\u0004\u0018\u00010m8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\bn\u0010o\u001a\u0004\bp\u0010qR\u0017\u0010r\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\br\u0010F\u001a\u0004\bs\u0010HR$\u0010t\u001a\u0004\u0018\u0001058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bt\u0010u\u001a\u0004\bv\u0010w\"\u0004\bx\u0010yR$\u0010z\u001a\u00020\b2\u0006\u0010`\u001a\u00020\b8\u0006@BX\u0086\u000e\u00a2\u0006\f\n\u0004\bz\u0010L\u001a\u0004\b{\u0010N\u00a8\u0006\u0082\u0001"}, d2={"Lcom/cobblemon/mod/common/block/multiblock/FossilMultiblockStructure;", "Lcom/cobblemon/mod/common/api/multiblock/MultiblockStructure;", "Lnet/minecraft/world/level/block/state/BlockState;", "state", "Lnet/minecraft/world/level/Level;", "world", "Lnet/minecraft/core/BlockPos;", "pos", "", "getComparatorOutput", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)I", "progress", "Lcom/cobblemon/mod/common/block/MonitorBlock$MonitorScreen;", "getProgressScreen", "(I)Lcom/cobblemon/mod/common/block/MonitorBlock$MonitorScreen;", "Lnet/minecraft/world/item/ItemStack;", "stack", "", "insertFossil", "(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/level/Level;)Z", "insertOrganicMaterial", "isRunning", "()Z", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "isSafeFloor", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Z", "Lnet/minecraft/world/phys/AABB;", "box", "makeSuitableY", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;Lnet/minecraft/world/phys/AABB;)Lnet/minecraft/core/BlockPos;", "", "markDirty", "(Lnet/minecraft/world/level/Level;)V", "markRemoved", "Lnet/minecraft/world/entity/player/Player;", "player", "onBreak", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/entity/player/Player;)V", "Lnet/minecraft/server/level/ServerLevel;", "Lnet/minecraft/util/RandomSource;", "random", "onTriggerEvent", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/util/RandomSource;)V", "blockState", "blockPos", "Lnet/minecraft/world/InteractionHand;", "interactionHand", "Lnet/minecraft/world/phys/BlockHitResult;", "blockHitResult", "Lnet/minecraft/world/InteractionResult;", "onUse", "(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;", "Lnet/minecraft/core/Direction;", "directionToBehind", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "spawn", "(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Direction;Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "startMachine", "stopMachine", "syncToClient", "tick", "updateFossilType", "updateOnStatus", "updateProgress", "Lnet/minecraft/nbt/CompoundTag;", "writeToNbt", "()Lnet/minecraft/nbt/CompoundTag;", "analyzerPos", "Lnet/minecraft/core/BlockPos;", "getAnalyzerPos", "()Lnet/minecraft/core/BlockPos;", "controllerBlockPos", "getControllerBlockPos", "fillLevel", "I", "getFillLevel", "()I", "setFillLevel", "(I)V", "", "fossilInventory", "Ljava/util/List;", "getFossilInventory", "()Ljava/util/List;", "setFossilInventory", "(Ljava/util/List;)V", "Ljava/util/UUID;", "fossilOwnerUUID", "Ljava/util/UUID;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/fossil/FossilState;", "fossilState", "Lcom/cobblemon/mod/common/client/render/models/blockbench/fossil/FossilState;", "getFossilState", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/fossil/FossilState;", "<set-?>", "hasCreatedPokemon", "Z", "getHasCreatedPokemon", "", "lastInteraction", "J", "machineStartTime", "monitorPos", "getMonitorPos", "organicMaterialInside", "getOrganicMaterialInside", "protectionTime", "Lcom/cobblemon/mod/common/api/fossil/Fossil;", "resultingFossil", "Lcom/cobblemon/mod/common/api/fossil/Fossil;", "getResultingFossil", "()Lcom/cobblemon/mod/common/api/fossil/Fossil;", "tankBasePos", "getTankBasePos", "tankConnectorDirection", "Lnet/minecraft/core/Direction;", "getTankConnectorDirection", "()Lnet/minecraft/core/Direction;", "setTankConnectorDirection", "(Lnet/minecraft/core/Direction;)V", "timeRemaining", "getTimeRemaining", "animAge", "", "animPartialTicks", "<init>", "(Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;IF)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nFossilMultiblockStructure.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilMultiblockStructure.kt\ncom/cobblemon/mod/common/block/multiblock/FossilMultiblockStructure\n+ 2 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 3 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n+ 5 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,673:1\n14#2,5:674\n19#2:682\n14#2,5:683\n19#2:691\n13579#3:679\n13580#3:681\n13579#3:688\n13580#3:690\n14#4:680\n14#4:689\n1855#5,2:692\n1855#5,2:694\n1855#5,2:696\n1855#5,2:698\n*S KotlinDebug\n*F\n+ 1 FossilMultiblockStructure.kt\ncom/cobblemon/mod/common/block/multiblock/FossilMultiblockStructure\n*L\n129#1:674,5\n129#1:682\n227#1:683,5\n227#1:691\n129#1:679\n129#1:681\n227#1:688\n227#1:690\n129#1:680\n227#1:689\n333#1:692,2\n339#1:694,2\n438#1:696,2\n604#1:698,2\n*E\n"})
public final class FossilMultiblockStructure
implements MultiblockStructure {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final BlockPos monitorPos;
    @NotNull
    private final BlockPos analyzerPos;
    @NotNull
    private final BlockPos tankBasePos;
    @NotNull
    private final BlockPos controllerBlockPos;
    private int organicMaterialInside;
    private boolean hasCreatedPokemon;
    private int timeRemaining;
    @Nullable
    private Fossil resultingFossil;
    private long lastInteraction;
    private long machineStartTime;
    private int protectionTime;
    @Nullable
    private UUID fossilOwnerUUID;
    @NotNull
    private final FossilState fossilState;
    @NotNull
    private List<ItemStack> fossilInventory;
    @Nullable
    private Direction tankConnectorDirection;
    private int fillLevel;
    @NotNull
    private static final BlockEntityTicker<FossilMultiblockEntity> TICKER = FossilMultiblockStructure::TICKER$lambda$5;
    public static final int TICKS_PER_MINUTE = 1200;
    public static final int MATERIAL_TO_START = 128;
    public static final int TIME_TO_TAKE = 14400;
    public static final int TIME_PER_STAGE = 1800;
    public static final int PROTECTION_TIME = 6000;

    public FossilMultiblockStructure(@NotNull BlockPos monitorPos, @NotNull BlockPos analyzerPos, @NotNull BlockPos tankBasePos, int animAge, float animPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)monitorPos, (String)"monitorPos");
        Intrinsics.checkNotNullParameter((Object)analyzerPos, (String)"analyzerPos");
        Intrinsics.checkNotNullParameter((Object)tankBasePos, (String)"tankBasePos");
        this.monitorPos = monitorPos;
        this.analyzerPos = analyzerPos;
        this.tankBasePos = tankBasePos;
        this.controllerBlockPos = this.analyzerPos;
        this.timeRemaining = -1;
        this.protectionTime = -1;
        this.fossilState = new FossilState(animAge, animPartialTicks);
        this.fossilInventory = new ArrayList();
    }

    public /* synthetic */ FossilMultiblockStructure(BlockPos blockPos2, BlockPos blockPos3, BlockPos blockPos4, int n, float f, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 8) != 0) {
            n = -1;
        }
        if ((n2 & 0x10) != 0) {
            f = 0.0f;
        }
        this(blockPos2, blockPos3, blockPos4, n, f);
    }

    @NotNull
    public final BlockPos getMonitorPos() {
        return this.monitorPos;
    }

    @NotNull
    public final BlockPos getAnalyzerPos() {
        return this.analyzerPos;
    }

    @NotNull
    public final BlockPos getTankBasePos() {
        return this.tankBasePos;
    }

    @Override
    @NotNull
    public BlockPos getControllerBlockPos() {
        return this.controllerBlockPos;
    }

    public final int getOrganicMaterialInside() {
        return this.organicMaterialInside;
    }

    public final boolean getHasCreatedPokemon() {
        return this.hasCreatedPokemon;
    }

    public final int getTimeRemaining() {
        return this.timeRemaining;
    }

    @Nullable
    public final Fossil getResultingFossil() {
        return this.resultingFossil;
    }

    @NotNull
    public final FossilState getFossilState() {
        return this.fossilState;
    }

    @NotNull
    public final List<ItemStack> getFossilInventory() {
        return this.fossilInventory;
    }

    public final void setFossilInventory(@NotNull List<ItemStack> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.fossilInventory = list;
    }

    @Nullable
    public final Direction getTankConnectorDirection() {
        return this.tankConnectorDirection;
    }

    public final void setTankConnectorDirection(@Nullable Direction direction) {
        this.tankConnectorDirection = direction;
    }

    public final int getFillLevel() {
        return this.fillLevel;
    }

    public final void setFillLevel(int n) {
        this.fillLevel = n;
    }

    /*
     * WARNING - void declaration
     */
    @Override
    @NotNull
    public InteractionResult onUse(@NotNull BlockState blockState, @NotNull Level world, @NotNull BlockPos blockPos2, @NotNull Player player, @NotNull InteractionHand interactionHand, @NotNull BlockHitResult blockHitResult) {
        Intrinsics.checkNotNullParameter((Object)blockState, (String)"blockState");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        Intrinsics.checkNotNullParameter((Object)interactionHand, (String)"interactionHand");
        Intrinsics.checkNotNullParameter((Object)blockHitResult, (String)"blockHitResult");
        ItemStack stack = player.m_21120_(interactionHand);
        if (stack.m_204117_(CobblemonItemTags.POKE_BALLS) || stack.m_41720_() instanceof PokeBallItem) {
            if (!(player instanceof ServerPlayer)) {
                return InteractionResult.SUCCESS;
            }
            if (this.hasCreatedPokemon) {
                BlockState monitorState;
                Pokemon pokemon;
                Object object;
                if (this.fossilOwnerUUID != null && !Intrinsics.areEqual((Object)((ServerPlayer)player).m_20148_(), (Object)this.fossilOwnerUUID)) {
                    Object ownerName = null;
                    ownerName = "UNKNOWN_USER";
                    Object object2 = DistributionUtilsKt.server();
                    if (object2 != null && (object2 = object2.m_129927_()) != null && (object2 = object2.m_11002_(this.fossilOwnerUUID)) != null && (object2 = (GameProfile)((Optional)object2).orElse(null)) != null && (object2 = object2.getName()) != null) {
                        Object it = object2;
                        boolean bl = false;
                        ownerName = it;
                    }
                    Object[] objectArray = new Object[]{ownerName};
                    player.m_5661_((Component)LocalizationUtilsKt.lang("fossilmachine.protected", objectArray), true);
                    return InteractionResult.FAIL;
                }
                Item item = stack.m_41720_();
                Intrinsics.checkNotNull((Object)item, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.PokeBallItem");
                PokeBall ballType = ((PokeBallItem)item).getPokeBall();
                if (!((ServerPlayer)player).m_7500_()) {
                    ItemStack itemStack = stack;
                    if (itemStack != null) {
                        itemStack.m_41774_(1);
                    }
                }
                Pokemon pokemon2 = (object = this.resultingFossil) != null && (object = ((Fossil)object).getResult()) != null ? ((PokemonProperties)object).create() : (pokemon = null);
                if (pokemon != null) {
                    void $this$iv;
                    pokemon.setCaughtBall(ballType);
                    PlayerExtensionsKt.party((ServerPlayer)player).add(pokemon);
                    this.fossilState.setGrowthState("Taken");
                    player.m_6330_(CobblemonSounds.FOSSIL_MACHINE_RETRIEVE_POKEMON, SoundSource.BLOCKS, 1.0f, 1.0f);
                    EventObservable<FossilRevivedEvent> eventObservable = CobblemonEvents.FOSSIL_REVIVED;
                    FossilRevivedEvent[] fossilRevivedEventArray = new FossilRevivedEvent[]{new FossilRevivedEvent(pokemon, (ServerPlayer)player)};
                    FossilRevivedEvent[] events$iv = fossilRevivedEventArray;
                    boolean $i$f$post = false;
                    $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
                    FossilRevivedEvent[] $this$forEach$iv$iv = events$iv;
                    boolean $i$f$forEach = false;
                    int n = $this$forEach$iv$iv.length;
                    for (int i = 0; i < n; ++i) {
                        FossilRevivedEvent element$iv$iv;
                        FossilRevivedEvent fossilRevivedEvent = element$iv$iv = $this$forEach$iv$iv[i];
                        boolean bl = false;
                        FossilRevivedEvent it = fossilRevivedEvent;
                    }
                }
                if ((monitorState = world.m_8055_(this.monitorPos)).m_61138_((Property)MonitorBlock.Companion.getSCREEN()) && !monitorState.equals((Object)MonitorBlock.MonitorScreen.OFF)) {
                    world.m_46597_(this.monitorPos, (BlockState)monitorState.m_61124_((Property)MonitorBlock.Companion.getSCREEN(), (Comparable)((Object)MonitorBlock.MonitorScreen.OFF)));
                }
                this.hasCreatedPokemon = false;
                this.fossilOwnerUUID = null;
                this.protectionTime = -1;
                this.updateFossilType(world);
                this.syncToClient(world);
                this.markDirty(world);
                return InteractionResult.SUCCESS;
            }
        }
        if (player.m_21120_(interactionHand).m_41619_()) {
            if (!this.isRunning() && !this.hasCreatedPokemon) {
                if (this.fossilInventory.isEmpty()) {
                    return InteractionResult.CONSUME;
                }
                if (player instanceof ServerPlayer) {
                    player.m_21008_(interactionHand, (ItemStack)CollectionsKt.last(this.fossilInventory));
                    this.fossilInventory.remove(this.fossilInventory.size() - 1);
                    world.m_247517_(null, this.analyzerPos, CobblemonSounds.FOSSIL_MACHINE_RETRIEVE_FOSSIL, SoundSource.BLOCKS);
                    this.updateFossilType(world);
                    this.syncToClient(world);
                    this.markDirty(world);
                }
            }
            return InteractionResult.CONSUME;
        }
        Intrinsics.checkNotNullExpressionValue((Object)stack, (String)"stack");
        if (Fossils.INSTANCE.isFossilIngredient(stack)) {
            if (!this.isRunning() && !this.hasCreatedPokemon) {
                if (this.fossilInventory.size() > Cobblemon.INSTANCE.getConfig().getMaxInsertedFossilItems()) {
                    return InteractionResult.FAIL;
                }
                if (player instanceof ServerPlayer) {
                    ItemStack copyFossilStack = stack.m_255036_(1);
                    if (!((ServerPlayer)player).m_7500_()) {
                        stack.m_41774_(1);
                    }
                    this.fossilOwnerUUID = ((ServerPlayer)player).m_20148_();
                    Intrinsics.checkNotNullExpressionValue((Object)copyFossilStack, (String)"copyFossilStack");
                    this.fossilInventory.add(copyFossilStack);
                    this.updateFossilType(world);
                    world.m_247517_(null, this.analyzerPos, CobblemonSounds.FOSSIL_MACHINE_INSERT_FOSSIL, SoundSource.BLOCKS);
                    this.syncToClient(world);
                    this.markDirty(world);
                }
            }
            return InteractionResult.SUCCESS;
        }
        if (NaturalMaterials.INSTANCE.isNaturalMaterial(stack)) {
            if (player instanceof ServerPlayer && !this.isRunning() && !this.hasCreatedPokemon && this.organicMaterialInside < 128 && this.insertOrganicMaterial(new ItemStack((ItemLike)stack.m_41720_(), 1), world)) {
                this.lastInteraction = world.m_46467_();
                if (!((ServerPlayer)player).m_7500_()) {
                    ResourceLocation returnItem = NaturalMaterials.INSTANCE.getReturnItem(stack);
                    stack.m_41774_(1);
                    PlayerExtensionsKt.giveOrDropItemStack(player, new ItemStack((ItemLike)BuiltInRegistries.f_257033_.m_7745_(returnItem)), false);
                }
            }
            InteractionResult interactionResult = InteractionResult.m_19078_((boolean)world.f_46443_);
            Intrinsics.checkNotNullExpressionValue((Object)interactionResult, (String)"success(world.isClient)");
            return interactionResult;
        }
        if (stack.m_204117_(CobblemonItemTags.FOSSILS)) {
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }

    /*
     * WARNING - void declaration
     */
    public final boolean spawn(@NotNull Level world, @NotNull BlockPos pos, @NotNull Direction directionToBehind, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)directionToBehind, (String)"directionToBehind");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        PokemonEntity entity2 = new PokemonEntity(world, pokemon, null, 4, null);
        entity2.m_6210_();
        double width = entity2.m_20191_().m_82362_();
        BlockPos idealPlace = pos.m_121955_(directionToBehind.m_122436_().m_142393_((int)Math.ceil(width / 2.0) + 1));
        AABB box = entity2.m_6972_(Pose.STANDING).m_20393_(idealPlace.m_252807_().m_82492_(0.0, 0.5, 0.0));
        for (int i = 0; i < 6; ++i) {
            box = box.m_82386_((double)directionToBehind.m_122436_().m_123341_(), 0.0, (double)directionToBehind.m_122436_().m_123343_());
            BlockPos blockPos2 = idealPlace.m_121955_(directionToBehind.m_122436_());
            Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"idealPlace.add(directionToBehind.vector)");
            AABB aABB = box;
            Intrinsics.checkNotNullExpressionValue((Object)aABB, (String)"box");
            BlockPos fixedPosition = this.makeSuitableY(world, blockPos2, entity2, aABB);
            if (fixedPosition == null) continue;
            entity2.m_146884_(fixedPosition.m_252807_().m_82492_(0.0, 0.5, 0.0));
            if (world.m_7967_((Entity)entity2)) {
                void $this$iv;
                EventObservable<FossilRevivedEvent> eventObservable = CobblemonEvents.FOSSIL_REVIVED;
                FossilRevivedEvent[] fossilRevivedEventArray = new FossilRevivedEvent[]{new FossilRevivedEvent(pokemon, null)};
                FossilRevivedEvent[] events$iv = fossilRevivedEventArray;
                boolean $i$f$post = false;
                $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
                FossilRevivedEvent[] $this$forEach$iv$iv = events$iv;
                boolean $i$f$forEach = false;
                int n = $this$forEach$iv$iv.length;
                for (int j = 0; j < n; ++j) {
                    FossilRevivedEvent element$iv$iv;
                    FossilRevivedEvent fossilRevivedEvent = element$iv$iv = $this$forEach$iv$iv[j];
                    boolean bl = false;
                    FossilRevivedEvent it = fossilRevivedEvent;
                }
                return true;
            }
            Cobblemon.INSTANCE.getLOGGER().warn("Couldn't spawn resurrected Pok\u00e9mon for some reason");
            break;
        }
        return false;
    }

    public final boolean isSafeFloor(@NotNull Level world, @NotNull BlockPos pos, @NotNull PokemonEntity entity2) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        BlockState state = world.m_8055_(pos);
        return state.m_60795_() ? false : (state.m_60634_((BlockGetter)world, pos, (Entity)entity2) || state.m_60638_((BlockGetter)world, pos, (Entity)entity2, Direction.DOWN) ? true : ((entity2.getBehaviour().getMoving().getSwim().getCanWalkOnWater() || entity2.getBehaviour().getMoving().getSwim().getCanSwimInWater()) && state.m_60819_().m_205070_(FluidTags.f_13131_) ? true : (entity2.getBehaviour().getMoving().getSwim().getCanWalkOnLava() || entity2.getBehaviour().getMoving().getSwim().getCanSwimInLava()) && state.m_60819_().m_205070_(FluidTags.f_13132_)));
    }

    @Nullable
    public final BlockPos makeSuitableY(@NotNull Level world, @NotNull BlockPos pos, @NotNull PokemonEntity entity2, @NotNull AABB box) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        Intrinsics.checkNotNullParameter((Object)box, (String)"box");
        if (world.m_186437_((Entity)entity2, box)) {
            for (int i = 1; i < 16; ++i) {
                AABB newBox = box.m_82386_(0.5, (double)i, 0.5);
                if (world.m_186437_((Entity)entity2, newBox)) continue;
                BlockPos blockPos2 = pos.m_7918_(0, i - 1, 0);
                Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"pos.add(0, i - 1, 0)");
                if (!this.isSafeFloor(world, blockPos2, entity2)) continue;
                return pos.m_7918_(0, i, 0);
            }
        } else {
            for (int i = 1; i < 16; ++i) {
                AABB newBox = box.m_82386_(0.5, -((double)i), 0.5);
                if (!world.m_186437_((Entity)entity2, newBox)) continue;
                BlockPos blockPos3 = pos.m_7918_(0, -i, 0);
                Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"pos.add(0, -i, 0)");
                if (!this.isSafeFloor(world, blockPos3, entity2)) continue;
                return pos.m_7918_(0, -i + 1, 0);
            }
        }
        return null;
    }

    @Override
    @Deprecated(message="Deprecated in Java")
    public int getComparatorOutput(@NotNull BlockState state, @Nullable Level world, @Nullable BlockPos pos) {
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        if (world == null || pos == null) {
            return 0;
        }
        if (Intrinsics.areEqual((Object)this.monitorPos, (Object)pos)) {
            if (this.hasCreatedPokemon) {
                return 15;
            }
            if (!this.isRunning()) {
                return 0;
            }
            return Math.max(15 - this.timeRemaining * 15 / 14400, 1);
        }
        if (Intrinsics.areEqual((Object)this.tankBasePos, (Object)pos) || Intrinsics.areEqual((Object)this.tankBasePos.m_7494_(), (Object)pos)) {
            return this.organicMaterialInside * 15 / 128;
        }
        return 0;
    }

    @Override
    public void onTriggerEvent(@Nullable BlockState state, @Nullable ServerLevel world, @Nullable BlockPos pos, @Nullable RandomSource random) {
        if (this.protectionTime <= 0) {
            boolean success;
            Direction direction;
            Object object;
            if (this.hasCreatedPokemon) {
                object = this.resultingFossil;
                if (object == null || (object = ((Fossil)object).getResult()) == null || (object = ((PokemonProperties)object).create()) == null) {
                    return;
                }
            } else {
                return;
            }
            Object wildPokemon = object;
            BlockState blockState = state;
            Direction direction2 = blockState != null && (blockState = (Direction)blockState.m_61143_((Property)HorizontalDirectionalBlock.f_54117_)) != null ? blockState.m_122424_() : (direction = null);
            if (pos != null && direction != null && world != null && (success = this.spawn((Level)world, pos, direction, (Pokemon)wildPokemon))) {
                this.fossilState.setGrowthState("Taken");
                this.hasCreatedPokemon = false;
                this.fossilOwnerUUID = null;
                this.protectionTime = -1;
                world.m_247517_(null, this.tankBasePos, CobblemonSounds.FOSSIL_MACHINE_RETRIEVE_POKEMON, SoundSource.BLOCKS);
                this.updateFossilType((Level)world);
                this.syncToClient((Level)world);
                this.markDirty((Level)world);
            }
        }
    }

    @Override
    public void onBreak(@NotNull Level world, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable Player player) {
        ItemStack it;
        boolean $i$f$forEach;
        Iterable $this$forEach$iv;
        Object object;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
        Intrinsics.checkNotNullParameter((Object)state, (String)"state");
        BlockEntity blockEntity = world.m_7702_(this.monitorPos);
        MultiblockEntity monitorEntity = blockEntity instanceof MultiblockEntity ? (MultiblockEntity)blockEntity : null;
        BlockEntity blockEntity2 = world.m_7702_(this.analyzerPos);
        MultiblockEntity analyzerEntity = blockEntity2 instanceof MultiblockEntity ? (MultiblockEntity)blockEntity2 : null;
        BlockEntity blockEntity3 = world.m_7702_(this.tankBasePos);
        MultiblockEntity tankBaseEntity = blockEntity3 instanceof MultiblockEntity ? (MultiblockEntity)blockEntity3 : null;
        BlockEntity blockEntity4 = world.m_7702_(this.tankBasePos.m_7494_());
        MultiblockEntity tankTopEntity = blockEntity4 instanceof MultiblockEntity ? (MultiblockEntity)blockEntity4 : null;
        MultiblockEntity multiblockEntity = tankBaseEntity;
        BlockState tankBaseBlockState = world.m_8055_((BlockPos)(multiblockEntity != null ? multiblockEntity.m_58899_() : null));
        Direction direction = ((Direction)tankBaseBlockState.m_61143_((Property)HorizontalDirectionalBlock.f_54117_)).m_122424_();
        Pokemon wildPokemon = this.hasCreatedPokemon ? ((object = this.resultingFossil) != null && (object = ((Fossil)object).getResult()) != null ? ((PokemonProperties)object).create() : null) : null;
        MultiblockEntity multiblockEntity2 = monitorEntity;
        if (multiblockEntity2 != null) {
            multiblockEntity2.setMultiblockStructure(null);
        }
        MultiblockEntity multiblockEntity3 = analyzerEntity;
        if (multiblockEntity3 != null) {
            multiblockEntity3.setMultiblockStructure(null);
        }
        MultiblockEntity multiblockEntity4 = tankBaseEntity;
        if (multiblockEntity4 != null) {
            multiblockEntity4.setMultiblockStructure(null);
        }
        MultiblockEntity multiblockEntity5 = tankTopEntity;
        if (multiblockEntity5 != null) {
            multiblockEntity5.setMultiblockStructure(null);
        }
        MultiblockEntity multiblockEntity6 = monitorEntity;
        if (multiblockEntity6 != null) {
            multiblockEntity6.setMasterBlockPos(null);
        }
        MultiblockEntity multiblockEntity7 = analyzerEntity;
        if (multiblockEntity7 != null) {
            multiblockEntity7.setMasterBlockPos(null);
        }
        MultiblockEntity multiblockEntity8 = tankBaseEntity;
        if (multiblockEntity8 != null) {
            multiblockEntity8.setMasterBlockPos(null);
        }
        MultiblockEntity multiblockEntity9 = tankTopEntity;
        if (multiblockEntity9 != null) {
            multiblockEntity9.setMasterBlockPos(null);
        }
        if (this.timeRemaining == -1 || this.timeRemaining >= 20) {
            $this$forEach$iv = this.fossilInventory;
            $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                it = (ItemStack)element$iv;
                boolean bl = false;
                ItemStack stack = new ItemStack((ItemLike)it.m_41720_(), 1);
                Containers.m_18992_((Level)world, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), (ItemStack)stack);
            }
        }
        if (tankBaseEntity instanceof RestorationTankBlockEntity) {
            List list = ((RestorationTankBlockEntity)tankBaseEntity).getInv().m_19195_();
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"tankBaseEntity.inv.clearToList()");
            $this$forEach$iv = list;
            $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                it = (ItemStack)element$iv;
                boolean bl = false;
                Containers.m_18992_((Level)world, (double)pos.m_123341_(), (double)pos.m_123342_(), (double)pos.m_123343_(), (ItemStack)it);
            }
        }
        if (wildPokemon != null) {
            Intrinsics.checkNotNullExpressionValue((Object)direction, (String)"direction");
            this.spawn(world, pos, direction, wildPokemon);
        }
        this.protectionTime = -1;
        this.updateFossilType(world);
        this.stopMachine(world);
        this.syncToClient(world);
        this.markDirty(world);
    }

    @Override
    public void markRemoved(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        if (world.f_46443_) {
            ResourceLocation resourceLocation = CobblemonSounds.FOSSIL_MACHINE_ACTIVE_LOOP.m_11660_();
            Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"FOSSIL_MACHINE_ACTIVE_LOOP.id");
            CancellableSoundController.INSTANCE.stopSound(this.tankBasePos, resourceLocation);
        }
    }

    @Override
    public void tick(@NotNull Level world) {
        int n;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        if (this.protectionTime > 0) {
            n = this.protectionTime;
            this.protectionTime = n + -1;
        }
        if (this.protectionTime == 0) {
            this.protectionTime = -1;
            this.fossilOwnerUUID = null;
            this.updateProgress(world);
            this.syncToClient(world);
            this.markDirty(world);
            world.m_247517_(null, this.tankBasePos, CobblemonSounds.FOSSIL_MACHINE_UNPROTECTED, SoundSource.BLOCKS);
        }
        if (this.hasCreatedPokemon) {
            return;
        }
        if (world.f_46443_ && this.isRunning() && (world.m_46467_() - this.machineStartTime) % 160L == 0L && world.f_46443_) {
            CancellableSoundController.INSTANCE.playSound(new CancellableSoundInstance(CobblemonSounds.FOSSIL_MACHINE_ACTIVE_LOOP, this.tankBasePos, true, 1.0f, 1.0f));
        }
        if (this.timeRemaining == -1 && this.organicMaterialInside >= 128 && this.resultingFossil != null) {
            this.startMachine(world);
            return;
        }
        if (this.timeRemaining >= 0) {
            n = this.timeRemaining;
            this.timeRemaining = n + -1;
        }
        if (this.timeRemaining % 1800 == 0) {
            this.updateProgress(world);
            this.syncToClient(world);
            this.markDirty(world);
        }
        if (this.timeRemaining == 0) {
            world.m_247517_(null, this.tankBasePos, CobblemonSounds.FOSSIL_MACHINE_FINISHED, SoundSource.BLOCKS);
            this.fossilInventory.clear();
            this.hasCreatedPokemon = true;
            if (this.fossilOwnerUUID != null) {
                this.protectionTime = 6000;
            }
            this.stopMachine(world);
        }
    }

    @Override
    public void syncToClient(@NotNull Level world) {
        MultiblockEntity monitorEntity;
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        BlockEntity blockEntity = world.m_7702_(this.tankBasePos);
        MultiblockEntity tankBaseEntity = blockEntity instanceof MultiblockEntity ? (MultiblockEntity)blockEntity : null;
        BlockEntity blockEntity2 = world.m_7702_(this.getControllerBlockPos());
        MultiblockEntity analyzerEntity = blockEntity2 instanceof MultiblockEntity ? (MultiblockEntity)blockEntity2 : null;
        BlockEntity blockEntity3 = world.m_7702_(this.monitorPos);
        MultiblockEntity multiblockEntity = monitorEntity = blockEntity3 instanceof MultiblockEntity ? (MultiblockEntity)blockEntity3 : null;
        if (tankBaseEntity != null) {
            world.m_7260_(this.tankBasePos, tankBaseEntity.m_58900_(), tankBaseEntity.m_58900_(), 2);
        }
        if (analyzerEntity != null) {
            world.m_7260_(this.analyzerPos, analyzerEntity.m_58900_(), analyzerEntity.m_58900_(), 2);
        }
        if (monitorEntity != null) {
            world.m_7260_(this.monitorPos, monitorEntity.m_58900_(), monitorEntity.m_58900_(), 2);
        }
    }

    @Override
    public void markDirty(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Object[] objectArray = new BlockEntity[]{world.m_7702_(this.analyzerPos), world.m_7702_(this.tankBasePos), world.m_7702_(this.tankBasePos.m_7494_()), world.m_7702_(this.monitorPos)};
        List entities2 = CollectionsKt.listOf((Object[])objectArray);
        Iterable $this$forEach$iv = entities2;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            BlockEntity it = (BlockEntity)element$iv;
            boolean bl = false;
            BlockEntity blockEntity = it;
            if (blockEntity == null) continue;
            blockEntity.m_6596_();
        }
    }

    public final void startMachine(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        this.timeRemaining = 14400;
        this.machineStartTime = world.m_46467_();
        world.m_247517_(null, this.tankBasePos, CobblemonSounds.FOSSIL_MACHINE_ACTIVATE, SoundSource.BLOCKS);
        if (world.f_46443_) {
            CancellableSoundController.INSTANCE.playSound(new CancellableSoundInstance(CobblemonSounds.FOSSIL_MACHINE_ACTIVE_LOOP, this.tankBasePos, true, 1.0f, 1.0f));
        }
        this.updateOnStatus(world);
        this.updateProgress(world);
        this.syncToClient(world);
        this.markDirty(world);
    }

    public final void stopMachine(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        this.fossilState.setGrowthState("Fully Grown");
        this.timeRemaining = -1;
        this.organicMaterialInside = 0;
        this.fossilInventory.clear();
        if (world.f_46443_) {
            ResourceLocation resourceLocation = CobblemonSounds.FOSSIL_MACHINE_ACTIVE_LOOP.m_11660_();
            Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"FOSSIL_MACHINE_ACTIVE_LOOP.id");
            CancellableSoundController.INSTANCE.stopSound(this.tankBasePos, resourceLocation);
        }
        this.updateOnStatus(world);
        this.updateProgress(world);
        this.syncToClient(world);
        this.markDirty(world);
    }

    public final void updateOnStatus(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        BlockPos upperTankPos = this.tankBasePos.m_7494_();
        BlockState analyzerState = world.m_8055_(this.analyzerPos);
        BlockState tankState = world.m_8055_(this.tankBasePos.m_7494_());
        if (analyzerState.m_61138_((Property)FossilAnalyzerBlock.Companion.getON())) {
            world.m_46597_(this.analyzerPos, (BlockState)analyzerState.m_61124_((Property)FossilAnalyzerBlock.Companion.getON(), (Comparable)Boolean.valueOf(this.timeRemaining >= 0)));
        }
        if (tankState.m_61138_((Property)RestorationTankBlock.Companion.getON())) {
            world.m_46597_(upperTankPos, (BlockState)tankState.m_61124_((Property)RestorationTankBlock.Companion.getON(), (Comparable)Boolean.valueOf(this.timeRemaining >= 0)));
        }
    }

    public final void updateProgress(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        BlockState monitorState = world.m_8055_(this.monitorPos);
        if (monitorState.m_61138_((Property)MonitorBlock.Companion.getSCREEN())) {
            MonitorBlock.MonitorScreen screenID = (float)this.protectionTime > 0.0f ? MonitorBlock.MonitorScreen.GREEN_PROGRESS_9 : (this.timeRemaining <= 0 ? MonitorBlock.MonitorScreen.OFF : this.getProgressScreen((14400 - this.timeRemaining) / 1800));
            world.m_46597_(this.monitorPos, (BlockState)monitorState.m_61124_((Property)MonitorBlock.Companion.getSCREEN(), (Comparable)((Object)screenID)));
        }
    }

    @NotNull
    public final MonitorBlock.MonitorScreen getProgressScreen(int progress2) {
        return switch (progress2) {
            case 0 -> MonitorBlock.MonitorScreen.BLUE_PROGRESS_1;
            case 1 -> MonitorBlock.MonitorScreen.BLUE_PROGRESS_2;
            case 2 -> MonitorBlock.MonitorScreen.BLUE_PROGRESS_3;
            case 3 -> MonitorBlock.MonitorScreen.BLUE_PROGRESS_4;
            case 4 -> MonitorBlock.MonitorScreen.BLUE_PROGRESS_5;
            case 5 -> MonitorBlock.MonitorScreen.BLUE_PROGRESS_6;
            case 6 -> MonitorBlock.MonitorScreen.BLUE_PROGRESS_7;
            case 7 -> MonitorBlock.MonitorScreen.BLUE_PROGRESS_8;
            case 8 -> MonitorBlock.MonitorScreen.BLUE_PROGRESS_9;
            default -> MonitorBlock.MonitorScreen.OFF;
        };
    }

    public final void updateFossilType(@NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        if (this.fossilInventory.isEmpty()) {
            if (this.resultingFossil == null) {
                return;
            }
            this.resultingFossil = null;
        } else {
            this.resultingFossil = Fossils.INSTANCE.getFossilByItemStacks(this.fossilInventory);
        }
    }

    public final boolean isRunning() {
        return this.timeRemaining > 0;
    }

    public final boolean insertOrganicMaterial(@NotNull ItemStack stack, @NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        Integer natureValue = NaturalMaterials.INSTANCE.getContent(stack);
        if (this.timeRemaining > 0 || this.organicMaterialInside >= 128 || natureValue == null) {
            return false;
        }
        if ((natureValue = Integer.valueOf(natureValue * stack.m_41613_())) <= 0 && this.organicMaterialInside == 0) {
            return false;
        }
        int oldFillStage = this.organicMaterialInside * 8 / 128;
        this.organicMaterialInside = this.organicMaterialInside + natureValue > 128 ? 128 : (this.organicMaterialInside + natureValue < 0 ? 0 : (this.organicMaterialInside += natureValue.intValue()));
        if (this.organicMaterialInside >= 128) {
            world.m_5594_(null, this.tankBasePos, CobblemonSounds.FOSSIL_MACHINE_DNA_FULL, SoundSource.BLOCKS, 1.0f, 1.0f);
        } else if (world.m_46467_() - this.lastInteraction < 10L) {
            world.m_5594_(null, this.tankBasePos, CobblemonSounds.FOSSIL_MACHINE_INSERT_DNA_SMALL, SoundSource.BLOCKS, 1.0f, 1.0f);
        } else {
            world.m_5594_(null, this.tankBasePos, CobblemonSounds.FOSSIL_MACHINE_INSERT_DNA, SoundSource.BLOCKS, 1.0f, 1.0f);
        }
        this.markDirty(world);
        if (oldFillStage != this.organicMaterialInside * 8 / 128) {
            this.syncToClient(world);
        }
        return true;
    }

    public final boolean insertFossil(@NotNull ItemStack stack, @NotNull Level world) {
        Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
        Intrinsics.checkNotNullParameter((Object)world, (String)"world");
        if (this.timeRemaining > 0 || this.fossilInventory.size() == 3) {
            return false;
        }
        int oldFillStage = this.fossilInventory.size();
        this.fossilInventory.add(stack);
        world.m_247517_(null, this.analyzerPos, CobblemonSounds.FOSSIL_MACHINE_INSERT_FOSSIL, SoundSource.BLOCKS);
        this.updateFossilType(world);
        this.markDirty(world);
        if (oldFillStage != this.fossilInventory.size()) {
            this.syncToClient(world);
        }
        return true;
    }

    @Override
    @NotNull
    public CompoundTag writeToNbt() {
        CompoundTag result = new CompoundTag();
        result.m_128365_("MonitorPos", (Tag)NbtUtils.m_129224_((BlockPos)this.monitorPos));
        result.m_128365_("AnalyzerPos", (Tag)NbtUtils.m_129224_((BlockPos)this.analyzerPos));
        result.m_128365_("TankBasePos", (Tag)NbtUtils.m_129224_((BlockPos)this.tankBasePos));
        result.m_128405_("TimeLeft", this.timeRemaining);
        result.m_128405_("ProtectedTimeLeft", this.protectionTime);
        if (this.fossilOwnerUUID != null) {
            result.m_128362_("FossilOwner", this.fossilOwnerUUID);
        }
        result.m_128405_("OrganicContent", this.organicMaterialInside);
        ListTag fossilInv = new ListTag();
        Iterable $this$forEach$iv = this.fossilInventory;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            ItemStack item = (ItemStack)element$iv;
            boolean bl = false;
            fossilInv.add((Object)item.m_41739_(new CompoundTag()));
        }
        result.m_128365_("InsertedFossilStacks", (Tag)fossilInv);
        Direction direction = this.tankConnectorDirection;
        result.m_128359_("ConnectorDirection", direction != null ? direction.toString() : null);
        if (this.resultingFossil != null) {
            Fossil fossil = this.resultingFossil;
            Intrinsics.checkNotNull((Object)fossil);
            result.m_128359_("InsertedFossil", fossil.m_7912_());
        }
        result.m_128379_("HasCreatedPokemon", this.hasCreatedPokemon);
        return result;
    }

    private static final void TICKER$lambda$5(Level world, BlockPos blockPos2, BlockState blockState, FossilMultiblockEntity blockEntity) {
        if (blockEntity.getMultiblockStructure() != null) {
            MultiblockStructure multiblockStructure = blockEntity.getMultiblockStructure();
            Intrinsics.checkNotNull((Object)multiblockStructure);
            Intrinsics.checkNotNullExpressionValue((Object)world, (String)"world");
            multiblockStructure.tick(world);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018J)\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0005\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00048\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u00048\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\r\u0010\fR\u001d\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00048\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\fR\u0014\u0010\u0015\u001a\u00020\u00048\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0015\u0010\fR\u0014\u0010\u0016\u001a\u00020\u00048\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\f\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/block/multiblock/FossilMultiblockStructure$Companion;", "", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "", "animAge", "", "partialTicks", "Lcom/cobblemon/mod/common/block/multiblock/FossilMultiblockStructure;", "fromNbt", "(Lnet/minecraft/nbt/CompoundTag;IF)Lcom/cobblemon/mod/common/block/multiblock/FossilMultiblockStructure;", "MATERIAL_TO_START", "I", "PROTECTION_TIME", "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "Lcom/cobblemon/mod/common/block/entity/FossilMultiblockEntity;", "TICKER", "Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "getTICKER", "()Lnet/minecraft/world/level/block/entity/BlockEntityTicker;", "TICKS_PER_MINUTE", "TIME_PER_STAGE", "TIME_TO_TAKE", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nFossilMultiblockStructure.kt\nKotlin\n*S Kotlin\n*F\n+ 1 FossilMultiblockStructure.kt\ncom/cobblemon/mod/common/block/multiblock/FossilMultiblockStructure$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,673:1\n1855#2,2:674\n*S KotlinDebug\n*F\n+ 1 FossilMultiblockStructure.kt\ncom/cobblemon/mod/common/block/multiblock/FossilMultiblockStructure$Companion\n*L\n645#1:674,2\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final BlockEntityTicker<FossilMultiblockEntity> getTICKER() {
            return TICKER;
        }

        @NotNull
        public final FossilMultiblockStructure fromNbt(@NotNull CompoundTag nbt, int animAge, float partialTicks) {
            Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
            BlockPos monitorPos = NbtUtils.m_129239_((CompoundTag)nbt.m_128469_("MonitorPos"));
            BlockPos compartmentPos = NbtUtils.m_129239_((CompoundTag)nbt.m_128469_("AnalyzerPos"));
            BlockPos tankBasePos = NbtUtils.m_129239_((CompoundTag)nbt.m_128469_("TankBasePos"));
            Intrinsics.checkNotNullExpressionValue((Object)monitorPos, (String)"monitorPos");
            Intrinsics.checkNotNullExpressionValue((Object)compartmentPos, (String)"compartmentPos");
            Intrinsics.checkNotNullExpressionValue((Object)tankBasePos, (String)"tankBasePos");
            FossilMultiblockStructure result = new FossilMultiblockStructure(monitorPos, compartmentPos, tankBasePos, animAge, partialTicks);
            result.organicMaterialInside = nbt.m_128451_("OrganicContent");
            result.timeRemaining = nbt.m_128451_("TimeLeft");
            result.protectionTime = nbt.m_128441_("ProtectedTimeLeft") ? nbt.m_128451_("ProtectedTimeLeft") : -1;
            result.fossilOwnerUUID = nbt.m_128441_("FossilOwner") ? nbt.m_128342_("FossilOwner") : null;
            Tag tag = nbt.m_128423_("InsertedFossilStacks");
            Intrinsics.checkNotNull((Object)tag, (String)"null cannot be cast to non-null type net.minecraft.nbt.NbtList");
            ListTag fossilInv = (ListTag)tag;
            List actualFossilList = new ArrayList();
            Iterable $this$forEach$iv = (Iterable)fossilInv;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Tag it = (Tag)element$iv;
                boolean bl = false;
                Intrinsics.checkNotNull((Object)it, (String)"null cannot be cast to non-null type net.minecraft.nbt.NbtCompound");
                ItemStack itemStack = ItemStack.m_41712_((CompoundTag)((CompoundTag)it));
                Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"fromNbt(it as NbtCompound)");
                actualFossilList.add(itemStack);
            }
            result.setFossilInventory(actualFossilList);
            result.setTankConnectorDirection(Direction.m_122402_((String)nbt.m_128461_("ConnectorDirection")));
            if (nbt.m_128441_("InsertedFossil")) {
                ResourceLocation id = new ResourceLocation(nbt.m_128461_("InsertedFossil"));
                Fossil fossil = Fossils.INSTANCE.getByIdentifier(id);
                if (fossil != null) {
                    result.resultingFossil = fossil;
                } else {
                    Cobblemon.INSTANCE.getLOGGER().error("Loaded fossil structure with invalid fossil type: {}", (Object)id);
                }
            }
            if (nbt.m_128441_("CreatedPokemon")) {
                result.hasCreatedPokemon = true;
            } else if (nbt.m_128441_("HasCreatedPokemon")) {
                result.hasCreatedPokemon = nbt.m_128471_("HasCreatedPokemon");
            }
            result.setFillLevel(result.getOrganicMaterialInside() * 8 / 128);
            return result;
        }

        public static /* synthetic */ FossilMultiblockStructure fromNbt$default(Companion companion, CompoundTag compoundTag, int n, float f, int n2, Object object) {
            if ((n2 & 2) != 0) {
                n = -1;
            }
            if ((n2 & 4) != 0) {
                f = 0.0f;
            }
            return companion.fromNbt(compoundTag, n, f);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

