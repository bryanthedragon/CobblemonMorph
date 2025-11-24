/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.trade.TradeParticipant;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0006\u0010\f\u001a\u00020\u0002\u0012\u0006\u0010\u000e\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u0004\u001a\u0004\b\r\u0010\u0006R\u0017\u0010\u000e\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\t\u001a\u0004\b\u000f\u0010\u000b\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/TradeCompletedEvent;", "", "Lcom/cobblemon/mod/common/trade/TradeParticipant;", "tradeParticipant1", "Lcom/cobblemon/mod/common/trade/TradeParticipant;", "getTradeParticipant1", "()Lcom/cobblemon/mod/common/trade/TradeParticipant;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "tradeParticipant1Pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getTradeParticipant1Pokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "tradeParticipant2", "getTradeParticipant2", "tradeParticipant2Pokemon", "getTradeParticipant2Pokemon", "<init>", "(Lcom/cobblemon/mod/common/trade/TradeParticipant;Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/trade/TradeParticipant;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "common"})
public final class TradeCompletedEvent {
    @NotNull
    private final TradeParticipant tradeParticipant1;
    @NotNull
    private final Pokemon tradeParticipant1Pokemon;
    @NotNull
    private final TradeParticipant tradeParticipant2;
    @NotNull
    private final Pokemon tradeParticipant2Pokemon;

    public TradeCompletedEvent(@NotNull TradeParticipant tradeParticipant1, @NotNull Pokemon tradeParticipant1Pokemon, @NotNull TradeParticipant tradeParticipant2, @NotNull Pokemon tradeParticipant2Pokemon) {
        Intrinsics.checkNotNullParameter((Object)tradeParticipant1, (String)"tradeParticipant1");
        Intrinsics.checkNotNullParameter((Object)tradeParticipant1Pokemon, (String)"tradeParticipant1Pokemon");
        Intrinsics.checkNotNullParameter((Object)tradeParticipant2, (String)"tradeParticipant2");
        Intrinsics.checkNotNullParameter((Object)tradeParticipant2Pokemon, (String)"tradeParticipant2Pokemon");
        this.tradeParticipant1 = tradeParticipant1;
        this.tradeParticipant1Pokemon = tradeParticipant1Pokemon;
        this.tradeParticipant2 = tradeParticipant2;
        this.tradeParticipant2Pokemon = tradeParticipant2Pokemon;
    }

    @NotNull
    public final TradeParticipant getTradeParticipant1() {
        return this.tradeParticipant1;
    }

    @NotNull
    public final Pokemon getTradeParticipant1Pokemon() {
        return this.tradeParticipant1Pokemon;
    }

    @NotNull
    public final TradeParticipant getTradeParticipant2() {
        return this.tradeParticipant2;
    }

    @NotNull
    public final Pokemon getTradeParticipant2Pokemon() {
        return this.tradeParticipant2Pokemon;
    }
}

