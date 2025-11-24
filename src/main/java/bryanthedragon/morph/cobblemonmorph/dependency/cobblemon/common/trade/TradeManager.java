/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.CobblemonEvents;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.TradeCompletedEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.EventObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.scheduling.SchedulingFunctionsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeOfferExpiredPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeOfferNotificationPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeStartedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.evolution.variants.TradeEvolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.ActiveTrade;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.PlayerTradeParticipant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeParticipant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\b\n\b\u00c6\u0002\u0018\u00002\u00020\u0001:\u0001&B\t\b\u0002\u00a2\u0006\u0004\b$\u0010%J\u001d\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u000b\u001a\u0004\u0018\u00010\n2\u0006\u0010\t\u001a\u00020\u0004\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000e\u001a\u0004\u0018\u00010\r2\u0006\u0010\t\u001a\u00020\u0004\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001d\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0015\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014J-\u0010\u001b\u001a\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0019\u001a\u00020\u00152\u0006\u0010\u001a\u001a\u00020\u0017\u00a2\u0006\u0004\b\u001b\u0010\u001cR\u001d\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\n0\u001d8\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!R\u001d\u0010\"\u001a\b\u0012\u0004\u0012\u00020\r0\u001d8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010\u001f\u001a\u0004\b#\u0010!\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/trade/TradeManager;", "", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Ljava/util/UUID;", "tradeOfferId", "", "acceptTradeRequest", "(Lnet/minecraft/server/level/ServerPlayer;Ljava/util/UUID;)V", "playerId", "Lcom/cobblemon/mod/common/trade/ActiveTrade;", "getActiveTrade", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/trade/ActiveTrade;", "Lcom/cobblemon/mod/common/trade/TradeManager$TradeRequest;", "getExistingRequest", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/trade/TradeManager$TradeRequest;", "otherPlayerEntity", "offerTrade", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/server/level/ServerPlayer;)V", "onLogoff", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Lcom/cobblemon/mod/common/trade/TradeParticipant;", "player1", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon1", "player2", "pokemon2", "performTrade", "(Lcom/cobblemon/mod/common/trade/TradeParticipant;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/trade/TradeParticipant;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "", "activeTrades", "Ljava/util/List;", "getActiveTrades", "()Ljava/util/List;", "requests", "getRequests", "<init>", "()V", "TradeRequest", "common"})
@SourceDebugExtension(value={"SMAP\nTradeManager.kt\nKotlin\n*S Kotlin\n*F\n+ 1 TradeManager.kt\ncom/cobblemon/mod/common/trade/TradeManager\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 4 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable\n+ 5 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n+ 6 EventObservables.kt\ncom/cobblemon/mod/common/api/reactive/EventObservable$post$1\n*L\n1#1,110:1\n1#2:111\n800#3,11:112\n288#3,2:123\n800#3,11:125\n288#3,2:136\n14#4,5:138\n19#4:146\n13579#5:143\n13580#5:145\n14#6:144\n*S KotlinDebug\n*F\n+ 1 TradeManager.kt\ncom/cobblemon/mod/common/trade/TradeManager\n*L\n101#1:112,11\n101#1:123,2\n105#1:125,11\n105#1:136,2\n108#1:138,5\n108#1:146\n108#1:143\n108#1:145\n108#1:144\n*E\n"})
public final class TradeManager {
    @NotNull
    public static final TradeManager INSTANCE = new TradeManager();
    @NotNull
    private static final List<TradeRequest> requests = new ArrayList();
    @NotNull
    private static final List<ActiveTrade> activeTrades = new ArrayList();

    private TradeManager() {
    }

    @NotNull
    public final List<TradeRequest> getRequests() {
        return requests;
    }

    @NotNull
    public final List<ActiveTrade> getActiveTrades() {
        return activeTrades;
    }

    @Nullable
    public final TradeRequest getExistingRequest(@NotNull UUID playerId) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)playerId, (String)"playerId");
            Iterable iterable = requests;
            for (Object t : iterable) {
                TradeRequest it = (TradeRequest)t;
                boolean bl = false;
                if (!(Intrinsics.areEqual((Object)it.getSenderId(), (Object)playerId) || Intrinsics.areEqual((Object)it.getReceiverId(), (Object)playerId))) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    @Nullable
    public final ActiveTrade getActiveTrade(@NotNull UUID playerId) {
        Object v0;
        block1: {
            Intrinsics.checkNotNullParameter((Object)playerId, (String)"playerId");
            Iterable iterable = activeTrades;
            for (Object t : iterable) {
                ActiveTrade it = (ActiveTrade)t;
                boolean bl = false;
                if (!(Intrinsics.areEqual((Object)it.getPlayer1().getUuid(), (Object)playerId) || Intrinsics.areEqual((Object)it.getPlayer2().getUuid(), (Object)playerId))) continue;
                v0 = t;
                break block1;
            }
            v0 = null;
        }
        return v0;
    }

    public final void offerTrade(@NotNull ServerPlayer player, @NotNull ServerPlayer otherPlayerEntity) {
        Object v0;
        Object[] objectArray;
        block5: {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)otherPlayerEntity, (String)"otherPlayerEntity");
            objectArray = (Object[])requests;
            for (Object t : objectArray) {
                TradeRequest it = (TradeRequest)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getSenderId(), (Object)player.m_20148_())) continue;
                v0 = t;
                break block5;
            }
            v0 = null;
        }
        TradeRequest existingFromPlayer = v0;
        if (existingFromPlayer != null) {
            ServerPlayer serverPlayer = PlayerExtensionsKt.getPlayer(existingFromPlayer.getReceiverId());
            if (serverPlayer != null) {
                CobblemonNetwork.INSTANCE.sendPacket(serverPlayer, new TradeOfferExpiredPacket(existingFromPlayer.getTradeOfferId()));
            }
        }
        UUID uUID = otherPlayerEntity.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"otherPlayerEntity.uuid");
        if (this.getActiveTrade(uUID) != null) {
            Object[] objectArray2 = new Object[1];
            Intrinsics.checkNotNullExpressionValue((Object)otherPlayerEntity.m_7755_(), (String)"otherPlayerEntity.name");
            player.m_5661_((Component)LocalizationUtilsKt.lang("trade.occupied", objectArray2), true);
        } else {
            UUID uUID2 = UUID.randomUUID();
            Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"randomUUID()");
            UUID uUID3 = player.m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID3, (String)"player.uuid");
            UUID uUID4 = otherPlayerEntity.m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID4, (String)"otherPlayerEntity.uuid");
            TradeRequest request = new TradeRequest(uUID2, uUID3, uUID4);
            requests.add(request);
            SchedulingFunctionsKt.afterOnServer$default(0, 60.0f, (Function0)new Function0<Unit>(request, player, otherPlayerEntity){
                final /* synthetic */ TradeRequest $request;
                final /* synthetic */ ServerPlayer $player;
                final /* synthetic */ ServerPlayer $otherPlayerEntity;
                {
                    this.$request = $request;
                    this.$player = $player;
                    this.$otherPlayerEntity = $otherPlayerEntity;
                    super(0);
                }

                public final void invoke() {
                    if (TradeManager.INSTANCE.getRequests().remove(this.$request)) {
                        Object[] objectArray = new Object[1];
                        Intrinsics.checkNotNullExpressionValue((Object)this.$otherPlayerEntity.m_7755_(), (String)"otherPlayerEntity.name");
                        this.$player.m_5661_((Component)LocalizationUtilsKt.lang("trade.request_expired", objectArray), true);
                    }
                }
            }, 1, null);
            UUID uUID5 = request.getTradeOfferId();
            UUID uUID6 = player.m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID6, (String)"player.uuid");
            MutableComponent mutableComponent = player.m_7755_().m_6881_();
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"player.name.copy()");
            CobblemonNetwork.INSTANCE.sendPacket(otherPlayerEntity, new TradeOfferNotificationPacket(uUID5, uUID6, mutableComponent));
            objectArray = new Object[1];
            Intrinsics.checkNotNullExpressionValue((Object)otherPlayerEntity.m_7755_(), (String)"otherPlayerEntity.name");
            player.m_5661_((Component)LocalizationUtilsKt.lang("trade.request_sent", objectArray), true);
        }
    }

    public final void acceptTradeRequest(@NotNull ServerPlayer player, @NotNull UUID tradeOfferId) {
        Object v0;
        block4: {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)tradeOfferId, (String)"tradeOfferId");
            Iterable iterable = requests;
            for (Object t : iterable) {
                TradeRequest it = (TradeRequest)t;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getTradeOfferId(), (Object)tradeOfferId)) continue;
                v0 = t;
                break block4;
            }
            v0 = null;
        }
        TradeRequest request = v0;
        if (request == null) {
            player.m_5661_((Component)LocalizationUtilsKt.lang("trade.request_already_expired", new Object[0]), true);
        } else {
            requests.remove(request);
            ServerPlayer serverPlayer = PlayerExtensionsKt.getPlayer(request.getSenderId());
            if (serverPlayer == null) {
                return;
            }
            ServerPlayer otherPlayer = serverPlayer;
            ActiveTrade trade2 = new ActiveTrade(new PlayerTradeParticipant(player), new PlayerTradeParticipant(otherPlayer));
            activeTrades.add(trade2);
            UUID uUID = otherPlayer.m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"otherPlayer.uuid");
            MutableComponent mutableComponent = otherPlayer.m_7755_().m_6881_();
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"otherPlayer.name.copy()");
            CobblemonNetwork.INSTANCE.sendPacket(player, new TradeStartedPacket(uUID, mutableComponent, trade2.getPlayer2().getParty().mapNullPreserving(acceptTradeRequest.1.INSTANCE)));
            UUID uUID2 = player.m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"player.uuid");
            MutableComponent mutableComponent2 = player.m_7755_().m_6881_();
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"player.name.copy()");
            CobblemonNetwork.INSTANCE.sendPacket(otherPlayer, new TradeStartedPacket(uUID2, mutableComponent2, trade2.getPlayer1().getParty().mapNullPreserving(acceptTradeRequest.2.INSTANCE)));
        }
    }

    public final void onLogoff(@NotNull ServerPlayer player) {
        Object v0;
        block4: {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Iterable iterable = requests;
            for (Object t : iterable) {
                TradeRequest it = (TradeRequest)t;
                boolean bl = false;
                if (!(Intrinsics.areEqual((Object)it.getSenderId(), (Object)player.m_20148_()) || Intrinsics.areEqual((Object)it.getReceiverId(), (Object)player.m_20148_()))) continue;
                v0 = t;
                break block4;
            }
            v0 = null;
        }
        TradeRequest request = v0;
        if (request != null) {
            ServerPlayer otherPlayer;
            ServerPlayer serverPlayer = otherPlayer = Intrinsics.areEqual((Object)request.getReceiverId(), (Object)player.m_20148_()) ? PlayerExtensionsKt.getPlayer(request.getSenderId()) : PlayerExtensionsKt.getPlayer(request.getReceiverId());
            if (serverPlayer != null) {
                CobblemonNetwork.INSTANCE.sendPacket(serverPlayer, new TradeOfferExpiredPacket(request.getTradeOfferId()));
            }
            requests.remove(request);
        }
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        ActiveTrade trade2 = this.getActiveTrade(uUID);
        if (trade2 != null) {
            UUID uUID2 = player.m_20148_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"player.uuid");
            TradeParticipant tradeParticipant = trade2.getTradeParticipant(uUID2);
            TradeParticipant oppositeParticipant = trade2.getOppositePlayer(tradeParticipant);
            oppositeParticipant.cancelTrade(trade2);
            activeTrades.remove(trade2);
        }
    }

    /*
     * WARNING - void declaration
     */
    public final void performTrade(@NotNull TradeParticipant player1, @NotNull Pokemon pokemon1, @NotNull TradeParticipant player2, @NotNull Pokemon pokemon2) {
        void $this$iv;
        Object element$iv;
        EventObservable<TradeCompletedEvent> $this$firstOrNull$iv;
        Object element$iv$iv;
        Object $this$filterIsInstanceTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)player1, (String)"player1");
        Intrinsics.checkNotNullParameter((Object)pokemon1, (String)"pokemon1");
        Intrinsics.checkNotNullParameter((Object)player2, (String)"player2");
        Intrinsics.checkNotNullParameter((Object)pokemon2, (String)"pokemon2");
        PartyStore party1 = player1.getParty();
        PartyStore party2 = player2.getParty();
        party1.remove(pokemon1);
        party2.remove(pokemon2);
        Pokemon.setFriendship$default(pokemon1, 0, false, 2, null);
        Pokemon.setFriendship$default(pokemon2, 0, false, 2, null);
        party2.add(pokemon1);
        party1.add(pokemon2);
        Iterable $this$filterIsInstance$iv = pokemon1.getLockedEvolutions();
        boolean $i$f$filterIsInstance = false;
        Object object = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        Iterator iterator = $this$filterIsInstanceTo$iv$iv.iterator();
        while (iterator.hasNext()) {
            element$iv$iv = iterator.next();
            if (!(element$iv$iv instanceof TradeEvolution)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filterIsInstance$iv = (List)destination$iv$iv;
        boolean $i$f$firstOrNull = false;
        $this$filterIsInstanceTo$iv$iv = $this$firstOrNull$iv.iterator();
        while ($this$filterIsInstanceTo$iv$iv.hasNext()) {
            element$iv = $this$filterIsInstanceTo$iv$iv.next();
            TradeEvolution it = (TradeEvolution)element$iv;
            boolean bl = false;
            if (!it.attemptEvolution(pokemon1, (Object)pokemon2)) continue;
            break;
        }
        $this$filterIsInstance$iv = pokemon2.getLockedEvolutions();
        $i$f$filterIsInstance = false;
        $this$filterIsInstanceTo$iv$iv = $this$filterIsInstance$iv;
        destination$iv$iv = new ArrayList();
        $i$f$filterIsInstanceTo = false;
        Iterator bl = $this$filterIsInstanceTo$iv$iv.iterator();
        while (bl.hasNext()) {
            element$iv$iv = bl.next();
            if (!(element$iv$iv instanceof TradeEvolution)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filterIsInstance$iv = (List)destination$iv$iv;
        $i$f$firstOrNull = false;
        object = $this$firstOrNull$iv.iterator();
        while (object.hasNext()) {
            element$iv = object.next();
            TradeEvolution it = (TradeEvolution)element$iv;
            boolean bl2 = false;
            if (!it.attemptEvolution(pokemon2, (Object)pokemon1)) continue;
            break;
        }
        $this$firstOrNull$iv = CobblemonEvents.TRADE_COMPLETED;
        TradeCompletedEvent[] $i$f$firstOrNull2 = new TradeCompletedEvent[]{new TradeCompletedEvent(player1, pokemon2, player2, pokemon1)};
        TradeCompletedEvent[] events$iv = $i$f$firstOrNull2;
        boolean $i$f$post = false;
        $this$iv.emit(Arrays.copyOf(events$iv, events$iv.length));
        TradeCompletedEvent[] $this$forEach$iv$iv = events$iv;
        boolean $i$f$forEach = false;
        int n = $this$forEach$iv$iv.length;
        for (int i = 0; i < n; ++i) {
            TradeCompletedEvent element$iv$iv2;
            TradeCompletedEvent tradeCompletedEvent = element$iv$iv2 = $this$forEach$iv$iv[i];
            boolean bl3 = false;
            TradeCompletedEvent it = tradeCompletedEvent;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u000b\u0010\fR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0004\u001a\u0004\b\n\u0010\u0006\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/trade/TradeManager$TradeRequest;", "", "Ljava/util/UUID;", "receiverId", "Ljava/util/UUID;", "getReceiverId", "()Ljava/util/UUID;", "senderId", "getSenderId", "tradeOfferId", "getTradeOfferId", "<init>", "(Ljava/util/UUID;Ljava/util/UUID;Ljava/util/UUID;)V", "common"})
    public static final class TradeRequest {
        @NotNull
        private final UUID tradeOfferId;
        @NotNull
        private final UUID senderId;
        @NotNull
        private final UUID receiverId;

        public TradeRequest(@NotNull UUID tradeOfferId, @NotNull UUID senderId, @NotNull UUID receiverId) {
            Intrinsics.checkNotNullParameter((Object)tradeOfferId, (String)"tradeOfferId");
            Intrinsics.checkNotNullParameter((Object)senderId, (String)"senderId");
            Intrinsics.checkNotNullParameter((Object)receiverId, (String)"receiverId");
            this.tradeOfferId = tradeOfferId;
            this.senderId = senderId;
            this.receiverId = receiverId;
        }

        @NotNull
        public final UUID getTradeOfferId() {
            return this.tradeOfferId;
        }

        @NotNull
        public final UUID getSenderId() {
            return this.senderId;
        }

        @NotNull
        public final UUID getReceiverId() {
            return this.receiverId;
        }
    }
}

