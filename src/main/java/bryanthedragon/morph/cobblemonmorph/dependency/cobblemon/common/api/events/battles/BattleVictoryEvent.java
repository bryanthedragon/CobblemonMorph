/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleEvent;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\b\u0018\u00002\u00020\u0001B3\u0012\u0006\u0010\r\u001a\u00020\u0002\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0010\u001a\u00020\n\u00a2\u0006\u0004\b$\u0010%J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\bJ\u0010\u0010\u000b\u001a\u00020\nH\u00c6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJD\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\r\u001a\u00020\u00022\u000e\b\u0002\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\u0010\u001a\u00020\nH\u00c6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u001a\u0010\u0015\u001a\u00020\n2\b\u0010\u0014\u001a\u0004\u0018\u00010\u0013H\u00d6\u0003\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0018\u001a\u00020\u0017H\u00d6\u0001\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0010\u0010\u001b\u001a\u00020\u001aH\u00d6\u0001\u00a2\u0006\u0004\b\u001b\u0010\u001cR\u001a\u0010\r\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\r\u0010\u001d\u001a\u0004\b\u001e\u0010\u0004R\u001d\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u001f\u001a\u0004\b \u0010\bR\u0017\u0010\u0010\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010!\u001a\u0004\b\"\u0010\fR\u001d\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u001f\u001a\u0004\b#\u0010\b\u00a8\u0006&"}, d2={"Lcom/cobblemon/mod/common/api/events/battles/BattleVictoryEvent;", "Lcom/cobblemon/mod/common/api/events/battles/BattleEvent;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "component1", "()Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "component2", "()Ljava/util/List;", "component3", "", "component4", "()Z", "battle", "winners", "losers", "wasWildCapture", "copy", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Ljava/util/List;Ljava/util/List;Z)Lcom/cobblemon/mod/common/api/events/battles/BattleVictoryEvent;", "", "other", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "getBattle", "Ljava/util/List;", "getLosers", "Z", "getWasWildCapture", "getWinners", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Ljava/util/List;Ljava/util/List;Z)V", "common"})
public final class BattleVictoryEvent
implements BattleEvent {
    @NotNull
    private final PokemonBattle battle;
    @NotNull
    private final List<BattleActor> winners;
    @NotNull
    private final List<BattleActor> losers;
    private final boolean wasWildCapture;

    public BattleVictoryEvent(@NotNull PokemonBattle battle2, @NotNull List<? extends BattleActor> winners, @NotNull List<? extends BattleActor> losers, boolean wasWildCapture) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter(winners, (String)"winners");
        Intrinsics.checkNotNullParameter(losers, (String)"losers");
        this.battle = battle2;
        this.winners = winners;
        this.losers = losers;
        this.wasWildCapture = wasWildCapture;
    }

    @Override
    @NotNull
    public PokemonBattle getBattle() {
        return this.battle;
    }

    @NotNull
    public final List<BattleActor> getWinners() {
        return this.winners;
    }

    @NotNull
    public final List<BattleActor> getLosers() {
        return this.losers;
    }

    public final boolean getWasWildCapture() {
        return this.wasWildCapture;
    }

    @NotNull
    public final PokemonBattle component1() {
        return this.battle;
    }

    @NotNull
    public final List<BattleActor> component2() {
        return this.winners;
    }

    @NotNull
    public final List<BattleActor> component3() {
        return this.losers;
    }

    public final boolean component4() {
        return this.wasWildCapture;
    }

    @NotNull
    public final BattleVictoryEvent copy(@NotNull PokemonBattle battle2, @NotNull List<? extends BattleActor> winners, @NotNull List<? extends BattleActor> losers, boolean wasWildCapture) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter(winners, (String)"winners");
        Intrinsics.checkNotNullParameter(losers, (String)"losers");
        return new BattleVictoryEvent(battle2, winners, losers, wasWildCapture);
    }

    public static /* synthetic */ BattleVictoryEvent copy$default(BattleVictoryEvent battleVictoryEvent, PokemonBattle pokemonBattle, List list, List list2, boolean bl, int n, Object object) {
        if ((n & 1) != 0) {
            pokemonBattle = battleVictoryEvent.battle;
        }
        if ((n & 2) != 0) {
            list = battleVictoryEvent.winners;
        }
        if ((n & 4) != 0) {
            list2 = battleVictoryEvent.losers;
        }
        if ((n & 8) != 0) {
            bl = battleVictoryEvent.wasWildCapture;
        }
        return battleVictoryEvent.copy(pokemonBattle, list, list2, bl);
    }

    @NotNull
    public String toString() {
        return "BattleVictoryEvent(battle=" + this.battle + ", winners=" + this.winners + ", losers=" + this.losers + ", wasWildCapture=" + this.wasWildCapture + ")";
    }

    public int hashCode() {
        int result = this.battle.hashCode();
        result = result * 31 + ((Object)this.winners).hashCode();
        result = result * 31 + ((Object)this.losers).hashCode();
        int n = this.wasWildCapture ? 1 : 0;
        if (n != 0) {
            n = 1;
        }
        result = result * 31 + n;
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BattleVictoryEvent)) {
            return false;
        }
        BattleVictoryEvent battleVictoryEvent = (BattleVictoryEvent)other;
        if (!Intrinsics.areEqual((Object)this.battle, (Object)battleVictoryEvent.battle)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.winners, battleVictoryEvent.winners)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.losers, battleVictoryEvent.losers)) {
            return false;
        }
        return this.wasWildCapture == battleVictoryEvent.wasWildCapture;
    }
}

