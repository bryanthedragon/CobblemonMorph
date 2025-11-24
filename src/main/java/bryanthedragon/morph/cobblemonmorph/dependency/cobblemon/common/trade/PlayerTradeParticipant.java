/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeAcceptanceChangedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeCancelledPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeCompletedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade.TradeUpdatedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.ActiveTrade;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeParticipant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\"\u001a\u00020!\u00a2\u0006\u0004\b*\u0010+J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J'\u0010\u000b\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u000e\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J)\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00012\b\u0010\u0013\u001a\u0004\u0018\u00010\u0012H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015R\"\u0010\u0018\u001a\n \u0017*\u0004\u0018\u00010\u00160\u00168\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u001a\u0010\u001d\u001a\u00020\u001c8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u0017\u0010\"\u001a\u00020!8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\"\u0010&\u001a\n \u0017*\u0004\u0018\u00010\u00070\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\u00a8\u0006,"}, d2={"Lcom/cobblemon/mod/common/trade/PlayerTradeParticipant;", "Lcom/cobblemon/mod/common/trade/TradeParticipant;", "Lcom/cobblemon/mod/common/trade/ActiveTrade;", "trade", "", "cancelTrade", "(Lcom/cobblemon/mod/common/trade/ActiveTrade;)V", "Ljava/util/UUID;", "pokemonId", "", "acceptance", "changeTradeAcceptance", "(Lcom/cobblemon/mod/common/trade/ActiveTrade;Ljava/util/UUID;Z)V", "pokemonId1", "pokemonId2", "completeTrade", "(Lcom/cobblemon/mod/common/trade/ActiveTrade;Ljava/util/UUID;Ljava/util/UUID;)V", "tradeParticipant", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "updateOffer", "(Lcom/cobblemon/mod/common/trade/ActiveTrade;Lcom/cobblemon/mod/common/trade/TradeParticipant;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "Lnet/minecraft/network/chat/Component;", "kotlin.jvm.PlatformType", "name", "Lnet/minecraft/network/chat/Component;", "getName", "()Lnet/minecraft/network/chat/Component;", "Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "party", "Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "getParty", "()Lcom/cobblemon/mod/common/api/storage/party/PlayerPartyStore;", "Lnet/minecraft/server/level/ServerPlayer;", "player", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "()Lnet/minecraft/server/level/ServerPlayer;", "uuid", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;)V", "common"})
public final class PlayerTradeParticipant
implements TradeParticipant {
    @NotNull
    private final ServerPlayer player;
    private final Component name;
    private final UUID uuid;
    @NotNull
    private final PlayerPartyStore party;

    public PlayerTradeParticipant(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        this.player = player;
        this.name = this.player.m_7755_();
        this.uuid = this.player.m_20148_();
        this.party = PlayerExtensionsKt.party(this.player);
    }

    @NotNull
    public final ServerPlayer getPlayer() {
        return this.player;
    }

    @Override
    public Component getName() {
        return this.name;
    }

    @Override
    public UUID getUuid() {
        return this.uuid;
    }

    @Override
    @NotNull
    public PlayerPartyStore getParty() {
        return this.party;
    }

    @Override
    public void updateOffer(@NotNull ActiveTrade trade2, @NotNull TradeParticipant tradeParticipant, @Nullable Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)trade2, (String)"trade");
        Intrinsics.checkNotNullParameter((Object)tradeParticipant, (String)"tradeParticipant");
        CobblemonNetwork.INSTANCE.sendPacket(this.player, new TradeUpdatedPacket(tradeParticipant.getUuid(), pokemon));
    }

    @Override
    public void changeTradeAcceptance(@NotNull ActiveTrade trade2, @NotNull UUID pokemonId, boolean acceptance) {
        Intrinsics.checkNotNullParameter((Object)trade2, (String)"trade");
        Intrinsics.checkNotNullParameter((Object)pokemonId, (String)"pokemonId");
        CobblemonNetwork.INSTANCE.sendPacket(this.player, new TradeAcceptanceChangedPacket(pokemonId, acceptance));
    }

    @Override
    public void cancelTrade(@NotNull ActiveTrade trade2) {
        Intrinsics.checkNotNullParameter((Object)trade2, (String)"trade");
        CobblemonNetwork.INSTANCE.sendPacket(this.player, new TradeCancelledPacket());
    }

    @Override
    public void completeTrade(@NotNull ActiveTrade trade2, @NotNull UUID pokemonId1, @NotNull UUID pokemonId2) {
        Intrinsics.checkNotNullParameter((Object)trade2, (String)"trade");
        Intrinsics.checkNotNullParameter((Object)pokemonId1, (String)"pokemonId1");
        Intrinsics.checkNotNullParameter((Object)pokemonId2, (String)"pokemonId2");
        CobblemonNetwork.INSTANCE.sendPacket(this.player, new TradeCompletedPacket(pokemonId1, pokemonId2));
    }
}

