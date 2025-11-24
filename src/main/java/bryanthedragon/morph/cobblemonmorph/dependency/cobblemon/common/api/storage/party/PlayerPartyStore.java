/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.random.Random
 *  kotlin.random.Random$Default
 *  kotlin.ranges.RangesKt
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.CobblemonCriteria;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PartyCheckContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.PartyCheckCriterion;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.SimpleCriterionTrigger;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.PassiveEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.OriginalTrainerType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.PokemonState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.ShoulderedState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.LevelUpEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatusContainer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.CompoundTagExtensionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.random.Random;
import kotlin.ranges.RangesKt;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\u0011\b\u0016\u0012\u0006\u0010\u001f\u001a\u00020\u001e\u00a2\u0006\u0004\b&\u0010'B\u0019\u0012\u0006\u0010\u001f\u001a\u00020\u001e\u0012\b\b\u0002\u0010(\u001a\u00020\u001e\u00a2\u0006\u0004\b&\u0010)J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0011\u0010\b\u001a\u0004\u0018\u00010\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\u000f\u001a\u00020\n2\u0006\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u000f\u0010\u0010J \u0010\u0013\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u001f\u0010\u0017\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u001d\u0010\u001c\u001a\u00020\u00042\u0006\u0010\u001a\u001a\u00020\u00192\u0006\u0010\u001b\u001a\u00020\u0004\u00a2\u0006\u0004\b\u001c\u0010\u001dR\u0017\u0010\u001f\u001a\u00020\u001e8\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R\u0016\u0010$\u001a\u00020#8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b$\u0010%\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "Lcom/cobblemon/mod/common/api/storage/party/PartyStore;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "add", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "getOverflowPC", "()Lcom/cobblemon/mod/common/api/storage/pc/PCStore;", "", "initialize", "()V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "onSecondPassed", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "position", "set", "(Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "position1", "position2", "swap", "(Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;)V", "Lnet/minecraft/nbt/CompoundTag;", "shoulderEntity", "isLeft", "validateShoulder", "(Lnet/minecraft/nbt/CompoundTag;Z)Z", "Ljava/util/UUID;", "playerUUID", "Ljava/util/UUID;", "getPlayerUUID", "()Ljava/util/UUID;", "", "secondsSinceFriendshipUpdate", "I", "<init>", "(Ljava/util/UUID;)V", "storageUUID", "(Ljava/util/UUID;Ljava/util/UUID;)V", "common"})
@SourceDebugExtension(value={"SMAP\nPlayerPartyStore.kt\nKotlin\n*S Kotlin\n*F\n+ 1 PlayerPartyStore.kt\ncom/cobblemon/mod/common/api/storage/party/PlayerPartyStore\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,199:1\n1#2:200\n800#3,11:201\n1855#3,2:212\n1855#3,2:214\n1855#3,2:216\n1855#3,2:218\n1855#3,2:220\n*S KotlinDebug\n*F\n+ 1 PlayerPartyStore.kt\ncom/cobblemon/mod/common/api/storage/party/PlayerPartyStore\n*L\n127#1:201,11\n127#1:212,2\n129#1:214,2\n133#1:216,2\n139#1:218,2\n157#1:220,2\n*E\n"})
public class PlayerPartyStore
extends PartyStore {
    @NotNull
    private final UUID playerUUID;
    private int secondsSinceFriendshipUpdate;

    public PlayerPartyStore(@NotNull UUID playerUUID, @NotNull UUID storageUUID) {
        Intrinsics.checkNotNullParameter((Object)playerUUID, (String)"playerUUID");
        Intrinsics.checkNotNullParameter((Object)storageUUID, (String)"storageUUID");
        super(storageUUID);
        this.playerUUID = playerUUID;
    }

    public /* synthetic */ PlayerPartyStore(UUID uUID, UUID uUID2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            uUID2 = uUID;
        }
        this(uUID, uUID2);
    }

    @NotNull
    public final UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public PlayerPartyStore(@NotNull UUID playerUUID) {
        Intrinsics.checkNotNullParameter((Object)playerUUID, (String)"playerUUID");
        this(playerUUID, playerUUID);
    }

    @Override
    public void initialize() {
        super.initialize();
        this.getObserverUUIDs().add(this.playerUUID);
    }

    @Nullable
    public PCStore getOverflowPC() {
        return Cobblemon.INSTANCE.getStorage().getPC(this.playerUUID);
    }

    @Override
    public boolean add(@NotNull Pokemon pokemon) {
        boolean bl;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        if (pokemon.getOriginalTrainerType() == OriginalTrainerType.NONE) {
            pokemon.setOriginalTrainer(this.playerUUID);
        }
        pokemon.refreshOriginalTrainer();
        if (super.add(pokemon)) {
            ServerPlayer serverPlayer = pokemon.getOwnerPlayer();
            if (serverPlayer != null) {
                ServerPlayer it = serverPlayer;
                boolean bl2 = false;
                CobblemonCriteria.INSTANCE.getPARTY_CHECK().trigger(it, new PartyCheckContext(this));
            }
            bl = true;
        } else {
            ServerPlayer player = PlayerExtensionsKt.getPlayer(this.playerUUID);
            PCStore pc = this.getOverflowPC();
            if (pc == null || !pc.add(pokemon)) {
                if (pc == null) {
                    ServerPlayer serverPlayer = player;
                    if (serverPlayer != null) {
                        serverPlayer.m_213846_((Component)LocalizationUtilsKt.lang("overflow_no_pc", new Object[0]));
                    }
                } else {
                    ServerPlayer serverPlayer = player;
                    if (serverPlayer != null) {
                        Object[] objectArray = new Object[]{pc.getName()};
                        serverPlayer.m_213846_((Component)LocalizationUtilsKt.lang("overflow_no_space", objectArray));
                    }
                }
                bl = false;
            } else {
                ServerPlayer serverPlayer = player;
                if (serverPlayer != null) {
                    Object[] objectArray = new Object[]{pokemon.getSpecies().getTranslatedName(), pc.getName()};
                    serverPlayer.m_213846_((Component)LocalizationUtilsKt.lang("overflow_to_pc", objectArray));
                }
                bl = true;
            }
        }
        return bl;
    }

    /*
     * WARNING - void declaration
     */
    public final void onSecondPassed(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        if (BattleRegistry.INSTANCE.getBattleByParticipatingPlayer(player) == null) {
            Random.Default random = Random.Default;
            for (Pokemon pokemon : this) {
                Evolution it;
                void $this$forEach$iv;
                Iterator $this$filterIsInstanceTo$iv$iv;
                Object status;
                if (pokemon.isFainted()) {
                    pokemon.setFaintedTimer(pokemon.getFaintedTimer() - 1);
                    if (pokemon.getFaintedTimer() <= -1) {
                        float php = (float)Math.ceil((float)pokemon.getHp() * Cobblemon.INSTANCE.getConfig().getFaintAwakenHealthPercent());
                        pokemon.setCurrentHealth((int)php);
                        Object[] objectArray = new Object[]{pokemon.getDisplayName()};
                        player.m_213846_((Component)Component.m_237110_((String)"cobblemon.party.faintRecover", (Object[])objectArray));
                    }
                } else if (pokemon.getCurrentHealth() < pokemon.getHp()) {
                    int php = pokemon.getHealTimer();
                    pokemon.setHealTimer(php + -1);
                    if (pokemon.getHealTimer() <= -1) {
                        pokemon.setHealTimer(Cobblemon.INSTANCE.getConfig().getHealTimer());
                        double healAmount = RangesKt.coerceAtLeast((double)1.0, (double)((double)pokemon.getHp() * Cobblemon.INSTANCE.getConfig().getHealPercent()));
                        pokemon.setCurrentHealth(pokemon.getCurrentHealth() + (int)Math.rint(healAmount));
                    }
                }
                if ((status = pokemon.getStatus()) != null && !player.m_5803_()) {
                    if (((PersistentStatusContainer)status).isExpired()) {
                        ((PersistentStatusContainer)status).getStatus().onStatusExpire(player, pokemon, (Random)random);
                        pokemon.setStatus(null);
                    } else {
                        ((PersistentStatusContainer)status).getStatus().onSecondPassed(player, pokemon, (Random)random);
                        ((PersistentStatusContainer)status).tickTimer();
                    }
                }
                Iterable $this$filterIsInstance$iv = pokemon.getLockedEvolutions();
                boolean $i$f$filterIsInstance = false;
                Iterable iterable = $this$filterIsInstance$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$filterIsInstanceTo = false;
                Iterator iterator = $this$filterIsInstanceTo$iv$iv.iterator();
                while (iterator.hasNext()) {
                    Object element$iv$iv = iterator.next();
                    if (!(element$iv$iv instanceof PassiveEvolution)) continue;
                    destination$iv$iv.add(element$iv$iv);
                }
                $this$filterIsInstance$iv = (List)destination$iv$iv;
                boolean $i$f$forEach = false;
                $this$filterIsInstanceTo$iv$iv = $this$forEach$iv.iterator();
                while ($this$filterIsInstanceTo$iv$iv.hasNext()) {
                    Object element$iv = $this$filterIsInstanceTo$iv$iv.next();
                    PassiveEvolution it2 = (PassiveEvolution)element$iv;
                    boolean bl = false;
                    it2.attemptEvolution(pokemon);
                }
                List removeList = new ArrayList();
                Iterable $this$forEach$iv2 = pokemon.getEvolutionProxy().server();
                boolean $i$f$forEach2 = false;
                for (Object element$iv : $this$forEach$iv2) {
                    it = (Evolution)element$iv;
                    boolean bl = false;
                    if (it.test(pokemon) || !(it instanceof LevelUpEvolution) || ((LevelUpEvolution)it).getPermanent()) continue;
                    removeList.add(it);
                }
                $this$forEach$iv2 = removeList;
                $i$f$forEach2 = false;
                for (Object element$iv : $this$forEach$iv2) {
                    it = (Evolution)element$iv;
                    boolean bl = false;
                    pokemon.getEvolutionProxy().server().remove(it);
                }
            }
            ++this.secondsSinceFriendshipUpdate;
            if (this.secondsSinceFriendshipUpdate == 120) {
                this.secondsSinceFriendshipUpdate = 0;
                Iterable $this$forEach$iv = this;
                boolean $i$f$forEach = false;
                for (Object element$iv : $this$forEach$iv) {
                    Pokemon pokemon = (Pokemon)element$iv;
                    boolean bl = false;
                    if (pokemon.getFriendship() >= 160 || pokemon.getEntity() == null && !(pokemon.getState() instanceof ShoulderedState)) continue;
                    Pokemon.incrementFriendship$default(pokemon, 1, false, 2, null);
                }
            }
        }
        CompoundTag compoundTag = player.m_36331_();
        Intrinsics.checkNotNullExpressionValue((Object)compoundTag, (String)"player.shoulderEntityLeft");
        if (CompoundTagExtensionsKt.isPokemonEntity(compoundTag)) {
            CompoundTag compoundTag2 = player.m_36331_();
            Intrinsics.checkNotNullExpressionValue((Object)compoundTag2, (String)"player.shoulderEntityLeft");
            if (!this.validateShoulder(compoundTag2, true)) {
                player.m_36370_(player.m_36331_());
            }
        }
        CompoundTag compoundTag3 = player.m_36332_();
        Intrinsics.checkNotNullExpressionValue((Object)compoundTag3, (String)"player.shoulderEntityRight");
        if (CompoundTagExtensionsKt.isPokemonEntity(compoundTag3)) {
            CompoundTag compoundTag4 = player.m_36332_();
            Intrinsics.checkNotNullExpressionValue((Object)compoundTag4, (String)"player.shoulderEntityRight");
            if (!this.validateShoulder(compoundTag4, false)) {
                player.m_36370_(player.m_36332_());
            }
        }
        Iterable $this$forEach$iv = this;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Pokemon it = (Pokemon)element$iv;
            boolean bl = false;
            PokemonState state = it.getState();
            if (!(state instanceof ShoulderedState) || ((ShoulderedState)state).isStillShouldered(player)) continue;
            it.recall();
        }
    }

    public final boolean validateShoulder(@NotNull CompoundTag shoulderEntity, boolean isLeft) {
        block6: {
            block5: {
                Object v0;
                Object object;
                block4: {
                    Intrinsics.checkNotNullParameter((Object)shoulderEntity, (String)"shoulderEntity");
                    object = this;
                    Iterator iterator = object.iterator();
                    while (iterator.hasNext()) {
                        Object t = iterator.next();
                        Pokemon it = (Pokemon)t;
                        boolean bl = false;
                        if (!Intrinsics.areEqual((Object)it.getUuid(), (Object)shoulderEntity.m_128469_("Pokemon").m_128342_("UUID"))) continue;
                        v0 = t;
                        break block4;
                    }
                    v0 = null;
                }
                Pokemon pokemon = v0;
                if (pokemon == null) break block5;
                object = pokemon.getState();
                ShoulderedState shoulderedState = object instanceof ShoulderedState ? (ShoulderedState)object : null;
                if (shoulderedState != null ? shoulderedState.isLeftShoulder() == isLeft : false) break block6;
            }
            return false;
        }
        return true;
    }

    @Override
    public void swap(@NotNull PartyPosition position1, @NotNull PartyPosition position2) {
        Intrinsics.checkNotNullParameter((Object)position1, (String)"position1");
        Intrinsics.checkNotNullParameter((Object)position2, (String)"position2");
        super.swap(position1, position2);
        Pokemon pokemon1 = this.get(position1);
        Pokemon pokemon2 = this.get(position2);
        if (pokemon1 != null && pokemon2 != null) {
            ServerPlayer player = pokemon1.getOwnerPlayer();
            if (player != null) {
                CobblemonCriteria.INSTANCE.getPARTY_CHECK().trigger(player, new PartyCheckContext(this));
            }
        } else if (pokemon1 != null || pokemon2 != null) {
            ServerPlayer player;
            Pokemon pokemon = pokemon1;
            Object object = player = pokemon != null ? pokemon.getOwnerPlayer() : null;
            if (player != null) {
                CobblemonCriteria.INSTANCE.getPARTY_CHECK().trigger(player, new PartyCheckContext(this));
            } else {
                Pokemon pokemon3 = pokemon2;
                Intrinsics.checkNotNull((Object)pokemon3);
                player = pokemon3.getOwnerPlayer();
                SimpleCriterionTrigger<PartyCheckContext, PartyCheckCriterion> simpleCriterionTrigger = CobblemonCriteria.INSTANCE.getPARTY_CHECK();
                ServerPlayer serverPlayer = player;
                Intrinsics.checkNotNull((Object)serverPlayer);
                simpleCriterionTrigger.trigger(serverPlayer, new PartyCheckContext(this));
            }
        }
    }

    @Override
    public void set(@NotNull PartyPosition position, @NotNull Pokemon pokemon) {
        block0: {
            Intrinsics.checkNotNullParameter((Object)position, (String)"position");
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            super.set(position, pokemon);
            ServerPlayer serverPlayer = pokemon.getOwnerPlayer();
            if (serverPlayer == null) break block0;
            ServerPlayer it = serverPlayer;
            boolean bl = false;
            CobblemonCriteria.INSTANCE.getPARTY_CHECK().trigger(it, new PartyCheckContext(this));
        }
    }
}

