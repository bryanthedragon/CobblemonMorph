/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeManager;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeOffer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeParticipant;
import java.util.Collection;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u001b\u001a\u00020\u0006\u0012\u0006\u0010#\u001a\u00020\u0006\u00a2\u0006\u0004\b'\u0010(J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\r\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u0015\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\t\u0010\nJ\u0015\u0010\u000b\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\u000b\u0010\nJ\u0015\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0010\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0010\u0010\u0011J\r\u0010\u0012\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0004J\u001d\u0010\u0015\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0014\u001a\u00020\u0013\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u001f\u0010\u0019\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00062\b\u0010\u0018\u001a\u0004\u0018\u00010\u0017\u00a2\u0006\u0004\b\u0019\u0010\u001aR\u0017\u0010\u001b\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u0017\u0010\u001f\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R\u0017\u0010#\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b#\u0010\u001c\u001a\u0004\b$\u0010\u001eR\u0017\u0010%\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b%\u0010 \u001a\u0004\b&\u0010\"\u00a8\u0006)"}, d2={"Lcom/cobblemon/mod/common/trade/ActiveTrade;", "", "", "cancelTrade", "()V", "completeTrade", "Lcom/cobblemon/mod/common/trade/TradeParticipant;", "tradeParticipant", "Lcom/cobblemon/mod/common/trade/TradeOffer;", "getOffer", "(Lcom/cobblemon/mod/common/trade/TradeParticipant;)Lcom/cobblemon/mod/common/trade/TradeOffer;", "getOpposingOffer", "getOppositePlayer", "(Lcom/cobblemon/mod/common/trade/TradeParticipant;)Lcom/cobblemon/mod/common/trade/TradeParticipant;", "Ljava/util/UUID;", "uuid", "getTradeParticipant", "(Ljava/util/UUID;)Lcom/cobblemon/mod/common/trade/TradeParticipant;", "performTrade", "", "acceptance", "updateAcceptance", "(Lcom/cobblemon/mod/common/trade/TradeParticipant;Z)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "updateOffer", "(Lcom/cobblemon/mod/common/trade/TradeParticipant;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "player1", "Lcom/cobblemon/mod/common/trade/TradeParticipant;", "getPlayer1", "()Lcom/cobblemon/mod/common/trade/TradeParticipant;", "player1Offer", "Lcom/cobblemon/mod/common/trade/TradeOffer;", "getPlayer1Offer", "()Lcom/cobblemon/mod/common/trade/TradeOffer;", "player2", "getPlayer2", "player2Offer", "getPlayer2Offer", "<init>", "(Lcom/cobblemon/mod/common/trade/TradeParticipant;Lcom/cobblemon/mod/common/trade/TradeParticipant;)V", "common"})
public final class ActiveTrade {
    @NotNull
    private final TradeParticipant player1;
    @NotNull
    private final TradeParticipant player2;
    @NotNull
    private final TradeOffer player1Offer;
    @NotNull
    private final TradeOffer player2Offer;

    public ActiveTrade(@NotNull TradeParticipant player1, @NotNull TradeParticipant player2) {
        Intrinsics.checkNotNullParameter((Object)player1, (String)"player1");
        Intrinsics.checkNotNullParameter((Object)player2, (String)"player2");
        this.player1 = player1;
        this.player2 = player2;
        this.player1Offer = new TradeOffer();
        this.player2Offer = new TradeOffer();
    }

    @NotNull
    public final TradeParticipant getPlayer1() {
        return this.player1;
    }

    @NotNull
    public final TradeParticipant getPlayer2() {
        return this.player2;
    }

    @NotNull
    public final TradeOffer getPlayer1Offer() {
        return this.player1Offer;
    }

    @NotNull
    public final TradeOffer getPlayer2Offer() {
        return this.player2Offer;
    }

    @NotNull
    public final TradeParticipant getTradeParticipant(@NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        return Intrinsics.areEqual((Object)this.player1.getUuid(), (Object)uuid2) ? this.player1 : this.player2;
    }

    @NotNull
    public final TradeOffer getOffer(@NotNull TradeParticipant tradeParticipant) {
        Intrinsics.checkNotNullParameter((Object)tradeParticipant, (String)"tradeParticipant");
        return Intrinsics.areEqual((Object)tradeParticipant, (Object)this.player1) ? this.player1Offer : this.player2Offer;
    }

    @NotNull
    public final TradeOffer getOpposingOffer(@NotNull TradeParticipant tradeParticipant) {
        Intrinsics.checkNotNullParameter((Object)tradeParticipant, (String)"tradeParticipant");
        return Intrinsics.areEqual((Object)tradeParticipant, (Object)this.player1) ? this.player2Offer : this.player1Offer;
    }

    public final void updateOffer(@NotNull TradeParticipant tradeParticipant, @Nullable Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)tradeParticipant, (String)"tradeParticipant");
        this.getOffer(tradeParticipant).updateOffer(pokemon);
        this.getOffer(this.getOppositePlayer(tradeParticipant)).setAccepted(false);
        this.player1.updateOffer(this, tradeParticipant, pokemon);
        this.player2.updateOffer(this, tradeParticipant, pokemon);
    }

    public final void updateAcceptance(@NotNull TradeParticipant tradeParticipant, boolean acceptance) {
        Intrinsics.checkNotNullParameter((Object)tradeParticipant, (String)"tradeParticipant");
        TradeOffer offer = this.getOpposingOffer(tradeParticipant);
        if (offer.getAccepted() != acceptance) {
            offer.setAccepted(acceptance);
            TradeParticipant tradeParticipant2 = this.getOppositePlayer(tradeParticipant);
            Pokemon pokemon = offer.getPokemon();
            Intrinsics.checkNotNull((Object)pokemon);
            UUID uUID = pokemon.getUuid();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"offer.pokemon!!.uuid");
            tradeParticipant2.changeTradeAcceptance(this, uUID, acceptance);
            Pokemon pokemon2 = offer.getPokemon();
            Intrinsics.checkNotNull((Object)pokemon2);
            UUID uUID2 = pokemon2.getUuid();
            Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"offer.pokemon!!.uuid");
            tradeParticipant.changeTradeAcceptance(this, uUID2, acceptance);
        }
        if (offer.getAccepted() && this.getOffer(tradeParticipant).getAccepted()) {
            this.performTrade();
        }
    }

    @NotNull
    public final TradeParticipant getOppositePlayer(@NotNull TradeParticipant tradeParticipant) {
        Intrinsics.checkNotNullParameter((Object)tradeParticipant, (String)"tradeParticipant");
        return Intrinsics.areEqual((Object)tradeParticipant, (Object)this.player1) ? this.player2 : this.player1;
    }

    public final void performTrade() {
        TradeManager tradeManager = TradeManager.INSTANCE;
        TradeParticipant tradeParticipant = this.player1;
        TradeParticipant tradeParticipant2 = this.player2;
        Pokemon pokemon = this.player1Offer.getPokemon();
        Intrinsics.checkNotNull((Object)pokemon);
        Pokemon pokemon2 = pokemon;
        Pokemon pokemon3 = this.player2Offer.getPokemon();
        Intrinsics.checkNotNull((Object)pokemon3);
        Pokemon pokemon4 = pokemon3;
        tradeManager.performTrade(tradeParticipant, pokemon2, tradeParticipant2, pokemon4);
        this.completeTrade();
    }

    public final void cancelTrade() {
        this.player1.cancelTrade(this);
        this.player2.cancelTrade(this);
        ((Collection)TradeManager.INSTANCE.getActiveTrades()).remove(this);
    }

    public final void completeTrade() {
        Pokemon pokemon = this.player1Offer.getPokemon();
        Intrinsics.checkNotNull((Object)pokemon);
        UUID uUID = pokemon.getUuid();
        Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"player1Offer.pokemon!!.uuid");
        Pokemon pokemon2 = this.player2Offer.getPokemon();
        Intrinsics.checkNotNull((Object)pokemon2);
        UUID uUID2 = pokemon2.getUuid();
        Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"player2Offer.pokemon!!.uuid");
        this.player1.completeTrade(this, uUID, uUID2);
        Pokemon pokemon3 = this.player2Offer.getPokemon();
        Intrinsics.checkNotNull((Object)pokemon3);
        UUID uUID3 = pokemon3.getUuid();
        Intrinsics.checkNotNullExpressionValue((Object)uUID3, (String)"player2Offer.pokemon!!.uuid");
        Pokemon pokemon4 = this.player1Offer.getPokemon();
        Intrinsics.checkNotNull((Object)pokemon4);
        UUID uUID4 = pokemon4.getUuid();
        Intrinsics.checkNotNullExpressionValue((Object)uUID4, (String)"player1Offer.pokemon!!.uuid");
        this.player2.completeTrade(this, uUID3, uUID4);
        this.player1Offer.setPokemon(null);
        this.player1Offer.setAccepted(false);
        this.player2Offer.setPokemon(null);
        this.player2Offer.setAccepted(false);
    }
}

