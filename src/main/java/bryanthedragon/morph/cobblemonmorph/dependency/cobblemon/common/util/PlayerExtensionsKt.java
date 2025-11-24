/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.ClosedFloatingPointRange
 *  kotlin.ranges.RangesKt
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.core.NonNullList
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.sounds.SoundEvents
 *  net.minecraft.sounds.SoundSource
 *  net.minecraft.util.Mth
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.item.ItemEntity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.Dialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.PokemonStoreManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerDataExtension;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.ServerPlayerEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.BlockPosExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.DistributionUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.EntityTraceResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.TraceResult;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.Vec3ExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.WorldExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.ClosedFloatingPointRange;
import kotlin.ranges.RangesKt;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u00ec\u0001\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\u001a%\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u00002\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001a\u0011\u0010\n\u001a\u00020\t*\u00020\b\u00a2\u0006\u0004\b\n\u0010\u000b\u001a\u0011\u0010\r\u001a\u00020\f*\u00020\b\u00a2\u0006\u0004\b\r\u0010\u000e\u001a\u001b\u0010\u0012\u001a\u0004\u0018\u00010\u0011*\u00020\b2\u0006\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\u0004\b\u0012\u0010\u0013\u001a\u001f\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u0016\u0018\u00010\u0014*\u00020\b\u00a2\u0006\u0004\b\u0017\u0010\u0018\u001a\u0011\u0010\u001a\u001a\u00020\u0019*\u00020\b\u00a2\u0006\u0004\b\u001a\u0010\u001b\u001a\u0013\u0010\u001d\u001a\u0004\u0018\u00010\b*\u00020\u001c\u00a2\u0006\u0004\b\u001d\u0010\u001e\u001a#\u0010#\u001a\u00020\t*\u00020\u001f2\u0006\u0010!\u001a\u00020 2\b\b\u0002\u0010\"\u001a\u00020\f\u00a2\u0006\u0004\b#\u0010$\u001a\u0019\u0010&\u001a\u00020\f*\u00020\b2\u0006\u0010\u0010\u001a\u00020%\u00a2\u0006\u0004\b&\u0010'\u001a\u0011\u0010(\u001a\u00020\f*\u00020\b\u00a2\u0006\u0004\b(\u0010\u000e\u001a-\u0010.\u001a\u00020\f*\u00020)2\u0006\u0010*\u001a\u00020)2\b\b\u0002\u0010,\u001a\u00020+2\b\b\u0002\u0010-\u001a\u00020+\u00a2\u0006\u0004\b.\u0010/\u001a\u001f\u00102\u001a\u00020\t*\u00020\b2\f\u00101\u001a\b\u0012\u0004\u0012\u00020\t00\u00a2\u0006\u0004\b2\u00103\u001a\u0019\u00106\u001a\u00020\t*\u00020\b2\u0006\u00105\u001a\u000204\u00a2\u0006\u0004\b6\u00107\u001a\u0011\u00109\u001a\u000208*\u00020\b\u00a2\u0006\u0004\b9\u0010:\u001a\u0011\u0010<\u001a\u00020;*\u00020\b\u00a2\u0006\u0004\b<\u0010=\u001a#\u0010A\u001a\u00020@*\u00020\b2\u0006\u0010,\u001a\u00020+2\b\u0010?\u001a\u0004\u0018\u00010>\u00a2\u0006\u0004\bA\u0010B\u001a5\u0010G\u001a\u0004\u0018\u00010\u0000*\u00020\b2\u0006\u0010D\u001a\u00020C2\u0006\u0010,\u001a\u00020E2\u0006\u0010F\u001a\u00020E2\b\u0010?\u001a\u0004\u0018\u00010>\u00a2\u0006\u0004\bG\u0010H\u001a=\u0010M\u001a\u0004\u0018\u00010L*\u00020\u001f2\b\b\u0002\u0010,\u001a\u00020+2\b\b\u0002\u0010-\u001a\u00020+2\u0014\b\u0002\u0010K\u001a\u000e\u0012\u0004\u0012\u00020J\u0012\u0004\u0012\u00020\f0I\u00a2\u0006\u0004\bM\u0010N\u001aQ\u0010T\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010S\"\b\b\u0000\u0010O*\u00020)*\u00020\u001f2\b\b\u0002\u0010,\u001a\u00020+2\b\b\u0002\u0010-\u001a\u00020+2\f\u0010Q\u001a\b\u0012\u0004\u0012\u00028\u00000P2\n\b\u0002\u0010R\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0004\bT\u0010U\u001aK\u0010V\u001a\u0004\u0018\u00018\u0000\"\b\b\u0000\u0010O*\u00020)*\u00020\u001f2\b\b\u0002\u0010,\u001a\u00020+2\b\b\u0002\u0010-\u001a\u00020+2\f\u0010Q\u001a\b\u0012\u0004\u0012\u00028\u00000P2\n\b\u0002\u0010R\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0004\bV\u0010W\u001a\u001f\u0010[\u001a\u0010\u0012\f\u0012\n Z*\u0004\u0018\u00010 0 0Y*\u00020X\u00a2\u0006\u0004\b[\u0010\\\"\u0017\u0010`\u001a\u0004\u0018\u00010]*\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\b^\u0010_\"\u0015\u0010a\u001a\u00020\f*\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\ba\u0010\u000e\u00a8\u0006b"}, d2={"Lnet/minecraft/world/phys/Vec3;", "p0", "p1", "Lnet/minecraft/core/BlockPos;", "blockPos", "Lnet/minecraft/core/Direction;", "findDirectionForIntercept", "(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/core/Direction;", "Lnet/minecraft/server/level/ServerPlayer;", "", "closeDialogue", "(Lnet/minecraft/server/level/ServerPlayer;)V", "", "didSleep", "(Lnet/minecraft/server/level/ServerPlayer;)Z", "", "key", "Lcom/cobblemon/mod/common/api/storage/player/PlayerDataExtension;", "extraData", "(Lnet/minecraft/server/level/ServerPlayer;Ljava/lang/String;)Lcom/cobblemon/mod/common/api/storage/player/PlayerDataExtension;", "Lkotlin/Pair;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "getBattleState", "(Lnet/minecraft/server/level/ServerPlayer;)Lkotlin/Pair;", "Lnet/minecraft/sounds/SoundEvent;", "getBattleTheme", "(Lnet/minecraft/server/level/ServerPlayer;)Lnet/minecraft/sounds/SoundEvent;", "Ljava/util/UUID;", "getPlayer", "(Ljava/util/UUID;)Lnet/minecraft/server/level/ServerPlayer;", "Lnet/minecraft/world/entity/player/Player;", "Lnet/minecraft/world/item/ItemStack;", "stack", "playSound", "giveOrDropItemStack", "(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/item/ItemStack;Z)V", "Lnet/minecraft/resources/ResourceLocation;", "hasKeyItem", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/resources/ResourceLocation;)Z", "isInBattle", "Lnet/minecraft/world/entity/Entity;", "other", "", "maxDistance", "stepDistance", "isLookingAt", "(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/entity/Entity;FF)Z", "Lkotlin/Function0;", "handler", "onLogout", "(Lnet/minecraft/server/level/ServerPlayer;Lkotlin/jvm/functions/Function0;)V", "Lcom/cobblemon/mod/common/api/dialogue/Dialogue;", "dialogue", "openDialogue", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/api/dialogue/Dialogue;)V", "Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "party", "(Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "pc", "(Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "Lnet/minecraft/world/RaycastContext$FluidHandling;", "fluidHandling", "Lnet/minecraft/world/phys/BlockHitResult;", "raycast", "(Lnet/minecraft/server/level/ServerPlayer;FLnet/minecraft/world/level/ClipContext$Fluid;)Lnet/minecraft/world/phys/BlockHitResult;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "dropHeight", "raycastSafeSendout", "(Lnet/minecraft/server/level/ServerPlayer;Lcom/cobblemon/mod/common/pokemon/Pokemon;DDLnet/minecraft/world/level/ClipContext$Fluid;)Lnet/minecraft/world/phys/Vec3;", "Lkotlin/Function1;", "Lnet/minecraft/world/level/block/state/BlockState;", "blockFilter", "Lcom/cobblemon/mod/common/util/TraceResult;", "traceBlockCollision", "(Lnet/minecraft/world/entity/player/Player;FFLkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/util/TraceResult;", "T", "Ljava/lang/Class;", "entityClass", "ignoreEntity", "Lcom/cobblemon/mod/common/util/EntityTraceResult;", "traceEntityCollision", "(Lnet/minecraft/world/entity/player/Player;FFLjava/lang/Class;Lnet/minecraft/world/entity/Entity;)Lcom/cobblemon/mod/common/util/EntityTraceResult;", "traceFirstEntityCollision", "(Lnet/minecraft/world/entity/player/Player;FFLjava/lang/Class;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/entity/Entity;", "Lnet/minecraft/world/entity/player/Inventory;", "", "kotlin.jvm.PlatformType", "usableItems", "(Lnet/minecraft/world/entity/player/Inventory;)Ljava/util/List;", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "getActiveDialogue", "(Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "activeDialogue", "isInDialogue", "common"})
@SourceDebugExtension(value={"SMAP\nPlayerExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PlayerExtensions.kt\ncom/cobblemon/mod/common/util/PlayerExtensionsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,370:1\n1#2:371\n2333#3,14:372\n766#3:386\n857#3,2:387\n*S KotlinDebug\n*F\n+ 1 PlayerExtensions.kt\ncom/cobblemon/mod/common/util/PlayerExtensionsKt\n*L\n124#1:372,14\n148#1:386\n148#1:387,2\n*E\n"})
public final class PlayerExtensionsKt {
    @NotNull
    public static final PlayerPartyStore party(@NotNull ServerPlayer $this$party) {
        Intrinsics.checkNotNullParameter((Object)$this$party, (String)"<this>");
        return Cobblemon.INSTANCE.getStorage().getParty($this$party);
    }

    @NotNull
    public static final PCStore pc(@NotNull ServerPlayer $this$pc) {
        Intrinsics.checkNotNullParameter((Object)$this$pc, (String)"<this>");
        PokemonStoreManager pokemonStoreManager = Cobblemon.INSTANCE.getStorage();
        UUID uUID = $this$pc.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"this.uuid");
        return pokemonStoreManager.getPC(uUID);
    }

    @Nullable
    public static final ActiveDialogue getActiveDialogue(@NotNull ServerPlayer $this$activeDialogue) {
        Intrinsics.checkNotNullParameter((Object)$this$activeDialogue, (String)"<this>");
        return DialogueManager.INSTANCE.getActiveDialogues().get($this$activeDialogue.m_20148_());
    }

    public static final boolean isInDialogue(@NotNull ServerPlayer $this$isInDialogue) {
        Intrinsics.checkNotNullParameter((Object)$this$isInDialogue, (String)"<this>");
        return DialogueManager.INSTANCE.getActiveDialogues().containsKey($this$isInDialogue.m_20148_());
    }

    public static final void closeDialogue(@NotNull ServerPlayer $this$closeDialogue) {
        Intrinsics.checkNotNullParameter((Object)$this$closeDialogue, (String)"<this>");
        DialogueManager.INSTANCE.stopDialogue($this$closeDialogue);
    }

    public static final void openDialogue(@NotNull ServerPlayer $this$openDialogue, @NotNull Dialogue dialogue2) {
        Intrinsics.checkNotNullParameter((Object)$this$openDialogue, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)dialogue2, (String)"dialogue");
        DialogueManager.INSTANCE.startDialogue($this$openDialogue, dialogue2);
    }

    @Nullable
    public static final PlayerDataExtension extraData(@NotNull ServerPlayer $this$extraData, @NotNull String key) {
        Intrinsics.checkNotNullParameter((Object)$this$extraData, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        return Cobblemon.INSTANCE.getPlayerData().get((Player)$this$extraData).getExtraData().get(key);
    }

    public static final boolean hasKeyItem(@NotNull ServerPlayer $this$hasKeyItem, @NotNull ResourceLocation key) {
        Intrinsics.checkNotNullParameter((Object)$this$hasKeyItem, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        return Cobblemon.INSTANCE.getPlayerData().get((Player)$this$hasKeyItem).getKeyItems().contains(key);
    }

    @Nullable
    public static final ServerPlayer getPlayer(@NotNull UUID $this$getPlayer) {
        Intrinsics.checkNotNullParameter((Object)$this$getPlayer, (String)"<this>");
        MinecraftServer minecraftServer = DistributionUtilsKt.server();
        return minecraftServer != null && (minecraftServer = minecraftServer.m_6846_()) != null ? minecraftServer.m_11259_($this$getPlayer) : null;
    }

    public static final void onLogout(@NotNull ServerPlayer $this$onLogout, @NotNull Function0<Unit> handler) {
        Intrinsics.checkNotNullParameter((Object)$this$onLogout, (String)"<this>");
        Intrinsics.checkNotNullParameter(handler, (String)"handler");
        Observable.DefaultImpls.subscribe$default(PlatformEvents.SERVER_PLAYER_LOGOUT.pipe(Observable.Companion.filter((Function1)new Function1<ServerPlayerEvent.Logout, Boolean>($this$onLogout){
            final /* synthetic */ ServerPlayer $this_onLogout;
            {
                this.$this_onLogout = $receiver;
                super(1);
            }

            @NotNull
            public final Boolean invoke(@NotNull ServerPlayerEvent.Logout it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                return Intrinsics.areEqual((Object)it.getPlayer().m_20148_(), (Object)this.$this_onLogout.m_20148_());
            }
        }), Observable.Companion.takeFirst$default(Observable.Companion, 0, 1, null)), null, (Function1)new Function1<ServerPlayerEvent.Logout, Unit>(handler){
            final /* synthetic */ Function0<Unit> $handler;
            {
                this.$handler = $handler;
                super(1);
            }

            public final void invoke(@NotNull ServerPlayerEvent.Logout it) {
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                this.$handler.invoke();
            }
        }, 1, null);
    }

    public static final boolean didSleep(@NotNull ServerPlayer $this$didSleep) {
        Intrinsics.checkNotNullParameter((Object)$this$didSleep, (String)"<this>");
        if ($this$didSleep.m_36318_() != 100 || (int)$this$didSleep.m_9236_().m_46468_() % 24000 != 0 || PlayerExtensionsKt.isInBattle($this$didSleep)) {
            return false;
        }
        PlayerExtensionsKt.party($this$didSleep).didSleep();
        return true;
    }

    public static final boolean isInBattle(@NotNull ServerPlayer $this$isInBattle) {
        Intrinsics.checkNotNullParameter((Object)$this$isInBattle, (String)"<this>");
        return BattleRegistry.INSTANCE.getBattleByParticipatingPlayer($this$isInBattle) != null;
    }

    @Nullable
    public static final Pair<PokemonBattle, BattleActor> getBattleState(@NotNull ServerPlayer $this$getBattleState) {
        BattleActor actor;
        Intrinsics.checkNotNullParameter((Object)$this$getBattleState, (String)"<this>");
        PokemonBattle battle2 = BattleRegistry.INSTANCE.getBattleByParticipatingPlayer($this$getBattleState);
        if (battle2 != null && (actor = battle2.getActor($this$getBattleState)) != null) {
            return TuplesKt.to((Object)battle2, (Object)actor);
        }
        return null;
    }

    public static final boolean isLookingAt(@NotNull Entity $this$isLookingAt, @NotNull Entity other, float maxDistance, float stepDistance) {
        Intrinsics.checkNotNullParameter((Object)$this$isLookingAt, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
        Vec3 startPos = $this$isLookingAt.m_146892_();
        Vec3 direction = $this$isLookingAt.m_20154_();
        for (float step = stepDistance; step <= maxDistance; step += stepDistance) {
            Vec3 location = startPos.m_82549_(direction.m_82490_((double)step));
            if (!other.m_20191_().m_82390_(location)) continue;
            return true;
        }
        return false;
    }

    public static /* synthetic */ boolean isLookingAt$default(Entity entity2, Entity entity3, float f, float f2, int n, Object object) {
        if ((n & 2) != 0) {
            f = 10.0f;
        }
        if ((n & 4) != 0) {
            f2 = 0.01f;
        }
        return PlayerExtensionsKt.isLookingAt(entity2, entity3, f, f2);
    }

    @Nullable
    public static final <T extends Entity> T traceFirstEntityCollision(@NotNull Player $this$traceFirstEntityCollision, float maxDistance, float stepDistance, @NotNull Class<T> entityClass, @Nullable T ignoreEntity) {
        Entity entity2;
        Intrinsics.checkNotNullParameter((Object)$this$traceFirstEntityCollision, (String)"<this>");
        Intrinsics.checkNotNullParameter(entityClass, (String)"entityClass");
        EntityTraceResult<T> entityTraceResult = PlayerExtensionsKt.traceEntityCollision($this$traceFirstEntityCollision, maxDistance, stepDistance, entityClass, ignoreEntity);
        if (entityTraceResult != null) {
            Object v0;
            EntityTraceResult<T> it = entityTraceResult;
            boolean bl = false;
            Iterable<T> $this$minByOrNull$iv = it.getEntities();
            boolean $i$f$minByOrNull = false;
            Iterator<T> iterator$iv = $this$minByOrNull$iv.iterator();
            if (!iterator$iv.hasNext()) {
                v0 = null;
            } else {
                T minElem$iv = iterator$iv.next();
                if (!iterator$iv.hasNext()) {
                    v0 = minElem$iv;
                } else {
                    Entity it2 = (Entity)minElem$iv;
                    boolean bl2 = false;
                    float minValue$iv = it2.m_20270_((Entity)$this$traceFirstEntityCollision);
                    do {
                        T e$iv = iterator$iv.next();
                        Entity it3 = (Entity)e$iv;
                        $i$a$-minByOrNull-PlayerExtensionsKt$traceFirstEntityCollision$1$1 = false;
                        float v$iv = it3.m_20270_((Entity)$this$traceFirstEntityCollision);
                        if (Float.compare(minValue$iv, v$iv) <= 0) continue;
                        minElem$iv = e$iv;
                        minValue$iv = v$iv;
                    } while (iterator$iv.hasNext());
                    v0 = minElem$iv;
                }
            }
            entity2 = v0;
        } else {
            entity2 = null;
        }
        return (T)entity2;
    }

    public static /* synthetic */ Entity traceFirstEntityCollision$default(Player player, float f, float f2, Class clazz, Entity entity2, int n, Object object) {
        if ((n & 1) != 0) {
            f = 10.0f;
        }
        if ((n & 2) != 0) {
            f2 = 0.05f;
        }
        if ((n & 8) != 0) {
            entity2 = null;
        }
        return PlayerExtensionsKt.traceFirstEntityCollision(player, f, f2, clazz, entity2);
    }

    @Nullable
    public static final <T extends Entity> EntityTraceResult<T> traceEntityCollision(@NotNull Player $this$traceEntityCollision, float maxDistance, float stepDistance, @NotNull Class<T> entityClass, @Nullable T ignoreEntity) {
        Intrinsics.checkNotNullParameter((Object)$this$traceEntityCollision, (String)"<this>");
        Intrinsics.checkNotNullParameter(entityClass, (String)"entityClass");
        float step = stepDistance;
        Vec3 startPos = $this$traceEntityCollision.m_146892_();
        Vec3 direction = $this$traceEntityCollision.m_20154_();
        Vec3 maxDistanceVector = new Vec3(1.0, 1.0, 1.0).m_82490_((double)maxDistance);
        List entities2 = $this$traceEntityCollision.m_9236_().m_6249_(null, new AABB(startPos.m_82546_(maxDistanceVector), startPos.m_82549_(maxDistanceVector)), arg_0 -> PlayerExtensionsKt.traceEntityCollision$lambda$2((Function1)new Function1<Entity, Boolean>(entityClass){
            final /* synthetic */ Class<T> $entityClass;
            {
                this.$entityClass = $entityClass;
                super(1);
            }

            @NotNull
            public final Boolean invoke(Entity it) {
                return this.$entityClass.isInstance(it);
            }
        }, arg_0));
        while (step <= maxDistance) {
            Entity it;
            Iterable $this$filterTo$iv$iv;
            Vec3 location = startPos.m_82549_(direction.m_82490_((double)step));
            step += stepDistance;
            Intrinsics.checkNotNullExpressionValue((Object)entities2, (String)"entities");
            Iterable $this$filter$iv = entities2;
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                it = (Entity)element$iv$iv;
                boolean bl = false;
                if (!(!Intrinsics.areEqual(ignoreEntity, (Object)it) && it.m_20191_().m_82390_(location))) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filter$iv = (List)destination$iv$iv;
            $i$f$filter = false;
            $this$filterTo$iv$iv = $this$filter$iv;
            destination$iv$iv = new ArrayList();
            $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                it = (Entity)element$iv$iv;
                boolean bl = false;
                if (!entityClass.isInstance(it)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            List collided = (List)destination$iv$iv;
            if (!(!((Collection)collided).isEmpty())) continue;
            Intrinsics.checkNotNullExpressionValue((Object)location, (String)"location");
            return new EntityTraceResult(location, CollectionsKt.filterIsInstance((Iterable)collided, entityClass));
        }
        return null;
    }

    public static /* synthetic */ EntityTraceResult traceEntityCollision$default(Player player, float f, float f2, Class clazz, Entity entity2, int n, Object object) {
        if ((n & 1) != 0) {
            f = 10.0f;
        }
        if ((n & 2) != 0) {
            f2 = 0.05f;
        }
        if ((n & 8) != 0) {
            entity2 = null;
        }
        return PlayerExtensionsKt.traceEntityCollision(player, f, f2, clazz, entity2);
    }

    @Nullable
    public static final TraceResult traceBlockCollision(@NotNull Player $this$traceBlockCollision, float maxDistance, float stepDistance, @NotNull Function1<? super BlockState, Boolean> blockFilter) {
        Intrinsics.checkNotNullParameter((Object)$this$traceBlockCollision, (String)"<this>");
        Intrinsics.checkNotNullParameter(blockFilter, (String)"blockFilter");
        Vec3 startPos = $this$traceBlockCollision.m_146892_();
        Vec3 direction = $this$traceBlockCollision.m_20154_();
        Intrinsics.checkNotNullExpressionValue((Object)startPos, (String)"startPos");
        BlockPos lastBlockPos = Vec3ExtensionsKt.toBlockPos(startPos);
        for (float step = stepDistance; step <= maxDistance; step += stepDistance) {
            Vec3 location = startPos.m_82549_(direction.m_82490_((double)step));
            Intrinsics.checkNotNullExpressionValue((Object)location, (String)"location");
            BlockPos blockPos2 = Vec3ExtensionsKt.toBlockPos(location);
            if (Intrinsics.areEqual((Object)blockPos2, (Object)lastBlockPos)) continue;
            lastBlockPos = blockPos2;
            BlockState block = $this$traceBlockCollision.m_9236_().m_8055_(blockPos2);
            Intrinsics.checkNotNullExpressionValue((Object)block, (String)"block");
            if (!((Boolean)blockFilter.invoke((Object)block)).booleanValue()) continue;
            Direction dir = PlayerExtensionsKt.findDirectionForIntercept(startPos, location, blockPos2);
            return new TraceResult(location, blockPos2, dir);
        }
        return null;
    }

    public static /* synthetic */ TraceResult traceBlockCollision$default(Player player, float f, float f2, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            f = 10.0f;
        }
        if ((n & 2) != 0) {
            f2 = 0.05f;
        }
        if ((n & 4) != 0) {
            function1 = traceBlockCollision.1.INSTANCE;
        }
        return PlayerExtensionsKt.traceBlockCollision(player, f, f2, (Function1<? super BlockState, Boolean>)function1);
    }

    @NotNull
    public static final Direction findDirectionForIntercept(@NotNull Vec3 p0, @NotNull Vec3 p1, @NotNull BlockPos blockPos2) {
        Intrinsics.checkNotNullParameter((Object)p0, (String)"p0");
        Intrinsics.checkNotNullParameter((Object)p1, (String)"p1");
        Intrinsics.checkNotNullParameter((Object)blockPos2, (String)"blockPos");
        Function1 xFunc2 = (Function1)new Function1<Double, Double>(p0, p1){
            final /* synthetic */ Vec3 $p0;
            final /* synthetic */ Vec3 $p1;
            {
                this.$p0 = $p0;
                this.$p1 = $p1;
                super(1);
            }

            @NotNull
            public final Double invoke(double it) {
                return this.$p0.f_82479_ + (this.$p1.f_82479_ - this.$p0.f_82479_) * it;
            }
        };
        Function1 yFunc2 = (Function1)new Function1<Double, Double>(p0, p1){
            final /* synthetic */ Vec3 $p0;
            final /* synthetic */ Vec3 $p1;
            {
                this.$p0 = $p0;
                this.$p1 = $p1;
                super(1);
            }

            @NotNull
            public final Double invoke(double it) {
                return this.$p0.f_82480_ + (this.$p1.f_82480_ - this.$p0.f_82480_) * it;
            }
        };
        Function1 zFunc2 = (Function1)new Function1<Double, Double>(p0, p1){
            final /* synthetic */ Vec3 $p0;
            final /* synthetic */ Vec3 $p1;
            {
                this.$p0 = $p0;
                this.$p1 = $p1;
                super(1);
            }

            @NotNull
            public final Double invoke(double it) {
                return this.$p0.f_82481_ + (this.$p1.f_82481_ - this.$p0.f_82481_) * it;
            }
        };
        Function1 tForX2 = (Function1)new Function1<Double, Double>(p0, p1){
            final /* synthetic */ Vec3 $p0;
            final /* synthetic */ Vec3 $p1;
            {
                this.$p0 = $p0;
                this.$p1 = $p1;
                super(1);
            }

            @NotNull
            public final Double invoke(double it) {
                return !(this.$p0.f_82479_ == this.$p1.f_82479_) ? (it - this.$p0.f_82479_) / (this.$p1.f_82479_ - this.$p0.f_82479_) : this.$p0.f_82479_;
            }
        };
        Function1 tForY2 = (Function1)new Function1<Double, Double>(p0, p1){
            final /* synthetic */ Vec3 $p0;
            final /* synthetic */ Vec3 $p1;
            {
                this.$p0 = $p0;
                this.$p1 = $p1;
                super(1);
            }

            @NotNull
            public final Double invoke(double it) {
                return !(this.$p0.f_82480_ == this.$p1.f_82480_) ? (it - this.$p0.f_82480_) / (this.$p1.f_82480_ - this.$p0.f_82480_) : this.$p0.f_82480_;
            }
        };
        Function1 tForZ2 = (Function1)new Function1<Double, Double>(p0, p1){
            final /* synthetic */ Vec3 $p0;
            final /* synthetic */ Vec3 $p1;
            {
                this.$p0 = $p0;
                this.$p1 = $p1;
                super(1);
            }

            @NotNull
            public final Double invoke(double it) {
                return !(this.$p0.f_82481_ == this.$p1.f_82481_) ? (it - this.$p0.f_82481_) / (this.$p1.f_82481_ - this.$p0.f_82481_) : this.$p0.f_82481_;
            }
        };
        ClosedFloatingPointRange xRange = RangesKt.rangeTo((double)blockPos2.m_123341_(), (double)((double)blockPos2.m_123341_() + 1.0));
        ClosedFloatingPointRange yRange = RangesKt.rangeTo((double)blockPos2.m_123342_(), (double)((double)blockPos2.m_123342_() + 1.0));
        ClosedFloatingPointRange zRange = RangesKt.rangeTo((double)blockPos2.m_123343_(), (double)((double)blockPos2.m_123343_() + 1.0));
        double tAtNorth = ((Number)tForZ2.invoke((Object)blockPos2.m_123343_())).doubleValue();
        double tAtSouth = ((Number)tForZ2.invoke((Object)((double)blockPos2.m_123343_() + 1.0))).doubleValue();
        double tAtEast = ((Number)tForX2.invoke((Object)((double)blockPos2.m_123341_() + 1.0))).doubleValue();
        double tAtWest = ((Number)tForX2.invoke((Object)blockPos2.m_123341_())).doubleValue();
        double tAtUp = ((Number)tForY2.invoke((Object)((double)blockPos2.m_123342_() + 1.0))).doubleValue();
        double tAtDown = ((Number)tForY2.invoke((Object)blockPos2.m_123342_())).doubleValue();
        boolean northCollision = yRange.contains((Comparable)yFunc2.invoke((Object)tAtNorth)) && xRange.contains((Comparable)xFunc2.invoke((Object)tAtNorth));
        boolean southCollision = yRange.contains((Comparable)yFunc2.invoke((Object)tAtSouth)) && xRange.contains((Comparable)xFunc2.invoke((Object)tAtSouth));
        boolean eastCollision = yRange.contains((Comparable)yFunc2.invoke((Object)tAtEast)) && zRange.contains((Comparable)zFunc2.invoke((Object)tAtEast));
        boolean westCollision = yRange.contains((Comparable)yFunc2.invoke((Object)tAtWest)) && zRange.contains((Comparable)zFunc2.invoke((Object)tAtWest));
        boolean upCollision = zRange.contains((Comparable)zFunc2.invoke((Object)tAtUp)) && xRange.contains((Comparable)xFunc2.invoke((Object)tAtUp));
        boolean downCollision = zRange.contains((Comparable)zFunc2.invoke((Object)tAtDown)) && xRange.contains((Comparable)xFunc2.invoke((Object)tAtDown));
        Direction minDirection = Direction.UP;
        double minTime = Double.MAX_VALUE;
        if (northCollision && tAtNorth < minTime) {
            minDirection = Direction.NORTH;
            minTime = tAtNorth;
        }
        if (southCollision && tAtSouth < minTime) {
            minDirection = Direction.SOUTH;
            minTime = tAtSouth;
        }
        if (eastCollision && tAtEast < minTime) {
            minDirection = Direction.EAST;
            minTime = tAtEast;
        }
        if (westCollision && tAtWest < minTime) {
            minDirection = Direction.WEST;
            minTime = tAtWest;
        }
        if (upCollision && tAtUp < minTime) {
            minDirection = Direction.UP;
            minTime = tAtUp;
        }
        if (downCollision && tAtDown < minTime) {
            return Direction.DOWN;
        }
        return minDirection;
    }

    @NotNull
    public static final BlockHitResult raycast(@NotNull ServerPlayer $this$raycast, float maxDistance, @Nullable ClipContext.Fluid fluidHandling) {
        Intrinsics.checkNotNullParameter((Object)$this$raycast, (String)"<this>");
        float f = $this$raycast.m_146909_();
        float g = $this$raycast.m_146908_();
        Vec3 vec3d = $this$raycast.m_146892_();
        float h = Mth.m_14089_((float)(-g * ((float)Math.PI / 180) - (float)Math.PI));
        float i = Mth.m_14031_((float)(-g * ((float)Math.PI / 180) - (float)Math.PI));
        float j = -Mth.m_14089_((float)(-f * ((float)Math.PI / 180)));
        float k = Mth.m_14031_((float)(-f * ((float)Math.PI / 180)));
        float l = i * j;
        float n = h * j;
        Vec3 vec3d2 = vec3d.m_82520_((double)l * (double)maxDistance, (double)k * (double)maxDistance, (double)n * (double)maxDistance);
        BlockHitResult blockHitResult = $this$raycast.m_9236_().m_45547_(new ClipContext(vec3d, vec3d2, ClipContext.Block.OUTLINE, fluidHandling, (Entity)$this$raycast));
        Intrinsics.checkNotNullExpressionValue((Object)blockHitResult, (String)"world.raycast(RaycastCon\u2026NE, fluidHandling, this))");
        return blockHitResult;
    }

    @Nullable
    public static final Vec3 raycastSafeSendout(@NotNull ServerPlayer $this$raycastSafeSendout, @NotNull Pokemon pokemon, double maxDistance, double dropHeight, @Nullable ClipContext.Fluid fluidHandling) {
        Intrinsics.checkNotNullParameter((Object)$this$raycastSafeSendout, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        float f = $this$raycastSafeSendout.m_146909_();
        float g = $this$raycastSafeSendout.m_146908_();
        Vec3 vec3d = $this$raycastSafeSendout.m_146892_();
        float h = Mth.m_14089_((float)(-g * ((float)Math.PI / 180) - (float)Math.PI));
        float i = Mth.m_14031_((float)(-g * ((float)Math.PI / 180) - (float)Math.PI));
        float j = -Mth.m_14089_((float)(-f * ((float)Math.PI / 180)));
        float k = Mth.m_14031_((float)(-f * ((float)Math.PI / 180)));
        float l = i * j;
        float n = h * j;
        Vec3 vec3d2 = vec3d.m_82520_((double)l * maxDistance, (double)k * maxDistance, (double)n * maxDistance);
        BlockHitResult result = $this$raycastSafeSendout.m_9236_().m_45547_(new ClipContext(vec3d, vec3d2, ClipContext.Block.OUTLINE, fluidHandling, (Entity)$this$raycastSafeSendout));
        if ($this$raycastSafeSendout.m_9236_().m_8055_(result.m_82425_()).m_60795_()) {
            TraceResult traceDown = null;
            double minDrop = Math.min(2.5, maxDistance);
            double stepDistance = 0.05;
            double stepDrop = minDrop;
            Vec3 stepPos = null;
            double traceHeight = 0.0;
            double smallestHeight = dropHeight;
            TraceResult fallLoc = null;
            for (double step = minDrop; step <= maxDistance; step += stepDistance) {
                Intrinsics.checkNotNullExpressionValue((Object)vec3d.m_82520_((double)l * step, (double)k * step, (double)n * step), (String)"vec3d.add(l.toDouble() *\u2026tep, n.toDouble() * step)");
                if (!(minDrop == maxDistance)) {
                    stepDrop = (step - minDrop) / (maxDistance - minDrop) * dropHeight;
                }
                Level level = $this$raycastSafeSendout.m_9236_();
                Intrinsics.checkNotNullExpressionValue((Object)level, (String)"this.world");
                traceDown = WorldExtensionsKt.traceDownwards$default(stepPos, level, (float)stepDrop, 0.0f, 4, null);
                if (traceDown == null) continue;
                Level level2 = $this$raycastSafeSendout.m_9236_();
                Intrinsics.checkNotNullExpressionValue((Object)level2, (String)"world");
                if (!pokemon.isPositionSafe(level2, traceDown.getBlockPos()) || !((traceHeight = stepPos.f_82480_ - traceDown.getLocation().f_82480_) < smallestHeight)) continue;
                smallestHeight = traceHeight;
                fallLoc = traceDown;
            }
            TraceResult traceResult = fallLoc;
            return traceResult != null && (traceResult = traceResult.getBlockPos()) != null && (traceResult = traceResult.m_7494_()) != null ? traceResult.m_252807_() : null;
        }
        if (result.m_82434_() != Direction.UP) {
            double offset = result.m_82434_() == Direction.DOWN ? 0.125 + (double)(pokemon.getForm().getHitbox().f_20378_ * pokemon.getForm().getBaseScale()) * 0.5 : 0.125 + (double)(pokemon.getForm().getHitbox().f_20377_ * pokemon.getForm().getBaseScale()) * 0.5;
            Vec3 posOffset = result.m_82450_().m_231075_(result.m_82434_(), offset);
            Intrinsics.checkNotNullExpressionValue((Object)posOffset, (String)"posOffset");
            Level level = $this$raycastSafeSendout.m_9236_();
            Intrinsics.checkNotNullExpressionValue((Object)level, (String)"this.world");
            TraceResult traceDown = WorldExtensionsKt.traceDownwards$default(posOffset, level, (float)dropHeight, 0.0f, 4, null);
            if (traceDown != null) {
                Level level3 = $this$raycastSafeSendout.m_9236_();
                Intrinsics.checkNotNullExpressionValue((Object)level3, (String)"world");
                if (pokemon.isPositionSafe(level3, traceDown.getBlockPos())) {
                    double d = traceDown.getLocation().f_82479_;
                    BlockPos blockPos2 = traceDown.getBlockPos().m_7494_();
                    Intrinsics.checkNotNullExpressionValue((Object)blockPos2, (String)"traceDown.blockPos.up()");
                    return new Vec3(d, BlockPosExtensionsKt.toVec3d((BlockPos)blockPos2).f_82480_, traceDown.getLocation().f_82481_);
                }
            }
            return null;
        }
        if (!$this$raycastSafeSendout.m_9236_().m_8055_(result.m_82425_().m_7494_()).m_280296_()) {
            Level level = $this$raycastSafeSendout.m_9236_();
            Intrinsics.checkNotNullExpressionValue((Object)level, (String)"world");
            BlockPos blockPos3 = result.m_82425_();
            Intrinsics.checkNotNullExpressionValue((Object)blockPos3, (String)"result.blockPos");
            if (pokemon.isPositionSafe(level, blockPos3)) {
                double d = result.m_82450_().f_82479_;
                BlockPos blockPos4 = result.m_82425_().m_7494_();
                Intrinsics.checkNotNullExpressionValue((Object)blockPos4, (String)"result.blockPos.up()");
                return new Vec3(d, BlockPosExtensionsKt.toVec3d((BlockPos)blockPos4).f_82480_, result.m_82450_().f_82481_);
            }
        }
        return null;
    }

    @NotNull
    public static final List<ItemStack> usableItems(@NotNull Inventory $this$usableItems) {
        Intrinsics.checkNotNullParameter((Object)$this$usableItems, (String)"<this>");
        NonNullList nonNullList = $this$usableItems.f_35976_;
        Intrinsics.checkNotNullExpressionValue((Object)nonNullList, (String)"offHand");
        Collection collection = (Collection)nonNullList;
        NonNullList nonNullList2 = $this$usableItems.f_35974_;
        Intrinsics.checkNotNullExpressionValue((Object)nonNullList2, (String)"main");
        return CollectionsKt.plus((Collection)collection, (Iterable)((Iterable)nonNullList2));
    }

    public static final void giveOrDropItemStack(@NotNull Player $this$giveOrDropItemStack, @NotNull ItemStack stack, boolean playSound) {
        block3: {
            block2: {
                Intrinsics.checkNotNullParameter((Object)$this$giveOrDropItemStack, (String)"<this>");
                Intrinsics.checkNotNullParameter((Object)stack, (String)"stack");
                boolean inserted = $this$giveOrDropItemStack.m_150109_().m_36054_(stack);
                if (!inserted || !stack.m_41619_()) break block2;
                stack.m_41764_(1);
                ItemEntity itemEntity = $this$giveOrDropItemStack.m_36176_(stack, false);
                if (itemEntity != null) {
                    itemEntity.m_32065_();
                }
                if (playSound) {
                    $this$giveOrDropItemStack.m_9236_().m_6263_(null, $this$giveOrDropItemStack.m_20185_(), $this$giveOrDropItemStack.m_20186_(), $this$giveOrDropItemStack.m_20189_(), SoundEvents.f_12019_, SoundSource.PLAYERS, 0.2f, (($this$giveOrDropItemStack.m_217043_().m_188501_() - $this$giveOrDropItemStack.m_217043_().m_188501_()) * 0.7f + 1.0f) * 2.0f);
                }
                $this$giveOrDropItemStack.f_36096_.m_38946_();
                break block3;
            }
            ItemEntity itemEntity = $this$giveOrDropItemStack.m_36176_(stack, false);
            if (itemEntity == null) break block3;
            ItemEntity itemEntity2 = itemEntity;
            boolean bl = false;
            itemEntity2.m_32061_();
            itemEntity2.m_266426_($this$giveOrDropItemStack.m_20148_());
        }
    }

    public static /* synthetic */ void giveOrDropItemStack$default(Player player, ItemStack itemStack, boolean bl, int n, Object object) {
        if ((n & 2) != 0) {
            bl = true;
        }
        PlayerExtensionsKt.giveOrDropItemStack(player, itemStack, bl);
    }

    @NotNull
    public static final SoundEvent getBattleTheme(@NotNull ServerPlayer $this$getBattleTheme) {
        ResourceLocation resourceLocation;
        block3: {
            block2: {
                Intrinsics.checkNotNullParameter((Object)$this$getBattleTheme, (String)"<this>");
                resourceLocation = Cobblemon.INSTANCE.getPlayerData().get((Player)$this$getBattleTheme).getBattleTheme();
                if (resourceLocation == null) break block2;
                ResourceLocation it = resourceLocation;
                boolean bl = false;
                SoundEvent soundEvent = (SoundEvent)BuiltInRegistries.f_256894_.m_7745_(it);
                resourceLocation = soundEvent;
                if (soundEvent != null) break block3;
            }
            resourceLocation = CobblemonSounds.PVP_BATTLE;
        }
        Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"Cobblemon.playerData.get\u2026obblemonSounds.PVP_BATTLE");
        return resourceLocation;
    }

    private static final boolean traceEntityCollision$lambda$2(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        return (Boolean)$tmp0.invoke(p0);
    }
}

