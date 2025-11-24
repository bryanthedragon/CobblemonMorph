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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleEvent;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0013\u001a\u0004\b\u0014\u0010\u0004\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/api/events/battles/BattleStartedPostEvent;", "Lcom/cobblemon/mod/common/api/events/battles/BattleEvent;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "component1", "()Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "copy", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)Lcom/cobblemon/mod/common/api/events/battles/BattleStartedPostEvent;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "getBattle", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "common"})
public final class BattleStartedPostEvent
implements BattleEvent {
    @NotNull
    private final PokemonBattle battle;

    public BattleStartedPostEvent(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        this.battle = battle2;
    }

    @Override
    @NotNull
    public PokemonBattle getBattle() {
        return this.battle;
    }

    @NotNull
    public final PokemonBattle component1() {
        return this.battle;
    }

    @NotNull
    public final BattleStartedPostEvent copy(@NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        return new BattleStartedPostEvent(battle2);
    }

    public static /* synthetic */ BattleStartedPostEvent copy$default(BattleStartedPostEvent battleStartedPostEvent, PokemonBattle pokemonBattle, int n, Object object) {
        if ((n & 1) != 0) {
            pokemonBattle = battleStartedPostEvent.battle;
        }
        return battleStartedPostEvent.copy(pokemonBattle);
    }

    @NotNull
    public String toString() {
        return "BattleStartedPostEvent(battle=" + this.battle + ")";
    }

    public int hashCode() {
        return this.battle.hashCode();
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BattleStartedPostEvent)) {
            return false;
        }
        BattleStartedPostEvent battleStartedPostEvent = (BattleStartedPostEvent)other;
        return Intrinsics.areEqual((Object)this.battle, (Object)battleStartedPostEvent.battle);
    }
}

