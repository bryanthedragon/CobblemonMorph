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
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.actor.PlayerBattleActor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\b\u001a\u00020\u0002\u0012\u0006\u0010\t\u001a\u00020\u0005\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J$\u0010\n\u001a\u00020\u00002\b\b\u0002\u0010\b\u001a\u00020\u00022\b\b\u0002\u0010\t\u001a\u00020\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001a\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\fH\u00d6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0015\u001a\u00020\u0014H\u00d6\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u001a\u0010\b\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\u0017\u001a\u0004\b\u0018\u0010\u0004R\u0017\u0010\t\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0019\u001a\u0004\b\u001a\u0010\u0007\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/api/events/battles/BattleFledEvent;", "Lcom/cobblemon/mod/common/api/events/battles/BattleEvent;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "component1", "()Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "Lcom/cobblemon/mod/common/battles/actor/PlayerBattleActor;", "component2", "()Lcom/cobblemon/mod/common/battles/actor/PlayerBattleActor;", "battle", "player", "copy", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/battles/actor/PlayerBattleActor;)Lcom/cobblemon/mod/common/api/events/battles/BattleFledEvent;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "getBattle", "Lcom/cobblemon/mod/common/battles/actor/PlayerBattleActor;", "getPlayer", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/battles/actor/PlayerBattleActor;)V", "common"})
public final class BattleFledEvent
implements BattleEvent {
    @NotNull
    private final PokemonBattle battle;
    @NotNull
    private final PlayerBattleActor player;

    public BattleFledEvent(@NotNull PokemonBattle battle2, @NotNull PlayerBattleActor player) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        this.battle = battle2;
        this.player = player;
    }

    @Override
    @NotNull
    public PokemonBattle getBattle() {
        return this.battle;
    }

    @NotNull
    public final PlayerBattleActor getPlayer() {
        return this.player;
    }

    @NotNull
    public final PokemonBattle component1() {
        return this.battle;
    }

    @NotNull
    public final PlayerBattleActor component2() {
        return this.player;
    }

    @NotNull
    public final BattleFledEvent copy(@NotNull PokemonBattle battle2, @NotNull PlayerBattleActor player) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
        return new BattleFledEvent(battle2, player);
    }

    public static /* synthetic */ BattleFledEvent copy$default(BattleFledEvent battleFledEvent, PokemonBattle pokemonBattle, PlayerBattleActor playerBattleActor, int n, Object object) {
        if ((n & 1) != 0) {
            pokemonBattle = battleFledEvent.battle;
        }
        if ((n & 2) != 0) {
            playerBattleActor = battleFledEvent.player;
        }
        return battleFledEvent.copy(pokemonBattle, playerBattleActor);
    }

    @NotNull
    public String toString() {
        return "BattleFledEvent(battle=" + this.battle + ", player=" + this.player + ")";
    }

    public int hashCode() {
        int result = this.battle.hashCode();
        result = result * 31 + this.player.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BattleFledEvent)) {
            return false;
        }
        BattleFledEvent battleFledEvent = (BattleFledEvent)other;
        if (!Intrinsics.areEqual((Object)this.battle, (Object)battleFledEvent.battle)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.player, (Object)battleFledEvent.player);
    }
}

