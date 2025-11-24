/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.MinecraftServer
 *  net.minecraft.server.level.ServerPlayer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.serverhandling.trade;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.ServerNetworkPacketHandler;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PlayerPartyStore;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.CancelTradePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.trade.UpdateTradeOfferPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.ActiveTrade;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeParticipant;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fJ'\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016\u00a2\u0006\u0004\b\t\u0010\n\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/net/serverhandling/trade/UpdateTradeOfferHandler;", "Lcom/cobblemon/mod/common/api/net/ServerNetworkPacketHandler;", "Lcom/cobblemon/mod/common/net/messages/server/trade/UpdateTradeOfferPacket;", "packet", "Lnet/minecraft/server/MinecraftServer;", "server", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "handle", "(Lcom/cobblemon/mod/common/net/messages/server/trade/UpdateTradeOfferPacket;Lnet/minecraft/server/MinecraftServer;Lnet/minecraft/server/level/ServerPlayer;)V", "<init>", "()V", "common"})
public final class UpdateTradeOfferHandler
implements ServerNetworkPacketHandler<UpdateTradeOfferPacket> {
    @NotNull
    public static final UpdateTradeOfferHandler INSTANCE = new UpdateTradeOfferHandler();

    private UpdateTradeOfferHandler() {
    }

    @Override
    public void handle(@NotNull UpdateTradeOfferPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)packet, (String)"packet");
        Intrinsics.checkNotNullParameter((Object)server, (String)"server");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        UUID uUID = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player.uuid");
        ActiveTrade activeTrade = TradeManager.INSTANCE.getActiveTrade(uUID);
        if (activeTrade == null) {
            CobblemonNetwork.INSTANCE.sendPacket(player, new CancelTradePacket());
            return;
        }
        ActiveTrade trade2 = activeTrade;
        UUID uUID2 = player.m_20148_();
        Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"player.uuid");
        TradeParticipant tradeParticipant = trade2.getTradeParticipant(uUID2);
        Pair<UUID, PartyPosition> newOffer = packet.getNewOffer();
        if (newOffer == null) {
            trade2.updateOffer(tradeParticipant, null);
        } else {
            UUID pokemonId = (UUID)newOffer.component1();
            PartyPosition partyPosition = (PartyPosition)newOffer.component2();
            PlayerPartyStore party = PlayerExtensionsKt.party(player);
            Pokemon pokemon = party.get(partyPosition);
            if (pokemon == null || !Intrinsics.areEqual((Object)pokemon.getUuid(), (Object)pokemonId)) {
                return;
            }
            if (!pokemon.getTradeable()) {
                return;
            }
            trade2.updateOffer(tradeParticipant, pokemon);
        }
    }

    @Override
    public void handleOnNettyThread(@NotNull UpdateTradeOfferPacket packet, @NotNull MinecraftServer server, @NotNull ServerPlayer player) {
        ServerNetworkPacketHandler.DefaultImpls.handleOnNettyThread(this, (NetworkPacket)packet, server, player);
    }
}

