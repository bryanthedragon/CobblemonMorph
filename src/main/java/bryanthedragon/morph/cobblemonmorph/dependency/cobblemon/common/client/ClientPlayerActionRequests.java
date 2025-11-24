/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleChallenge;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.trade.ClientTradeOffer;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u000b\u0010\fR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u001d\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0005\u001a\u0004\b\n\u0010\u0007\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/ClientPlayerActionRequests;", "", "", "Lcom/cobblemon/mod/common/client/battle/ClientBattleChallenge;", "battleChallenges", "Ljava/util/List;", "getBattleChallenges", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/client/trade/ClientTradeOffer;", "tradeOffers", "getTradeOffers", "<init>", "()V", "common"})
public final class ClientPlayerActionRequests {
    @NotNull
    private final List<ClientBattleChallenge> battleChallenges = new ArrayList();
    @NotNull
    private final List<ClientTradeOffer> tradeOffers = new ArrayList();

    @NotNull
    public final List<ClientBattleChallenge> getBattleChallenges() {
        return this.battleChallenges;
    }

    @NotNull
    public final List<ClientTradeOffer> getTradeOffers() {
        return this.tradeOffers;
    }
}

