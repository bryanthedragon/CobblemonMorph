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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.interpreter.BattleContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.battles.BattleEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u000b\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\b\u00a2\u0006\u0004\b!\u0010\"J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ.\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\bH\u00c6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001a\u0010\u0013\u001a\u00020\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0016\u001a\u00020\u0015H\u00d6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0019\u001a\u00020\u0018H\u00d6\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aR\u001a\u0010\u000b\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000b\u0010\u001b\u001a\u0004\b\u001c\u0010\u0004R\u0017\u0010\r\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u001d\u001a\u0004\b\u001e\u0010\nR\u0017\u0010\f\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\u001f\u001a\u0004\b \u0010\u0007\u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/api/events/battles/BattleFaintedEvent;", "Lcom/cobblemon/mod/common/api/events/battles/BattleEvent;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "component1", "()Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "component2", "()Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext;", "component3", "()Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext;", "battle", "killed", "context", "copy", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext;)Lcom/cobblemon/mod/common/api/events/battles/BattleFaintedEvent;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "getBattle", "Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext;", "getContext", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "getKilled", "<init>", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/api/battles/interpreter/BattleContext;)V", "common"})
public final class BattleFaintedEvent
implements BattleEvent {
    @NotNull
    private final PokemonBattle battle;
    @NotNull
    private final BattlePokemon killed;
    @NotNull
    private final BattleContext context;

    public BattleFaintedEvent(@NotNull PokemonBattle battle2, @NotNull BattlePokemon killed, @NotNull BattleContext context) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)killed, (String)"killed");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        this.battle = battle2;
        this.killed = killed;
        this.context = context;
    }

    @Override
    @NotNull
    public PokemonBattle getBattle() {
        return this.battle;
    }

    @NotNull
    public final BattlePokemon getKilled() {
        return this.killed;
    }

    @NotNull
    public final BattleContext getContext() {
        return this.context;
    }

    @NotNull
    public final PokemonBattle component1() {
        return this.battle;
    }

    @NotNull
    public final BattlePokemon component2() {
        return this.killed;
    }

    @NotNull
    public final BattleContext component3() {
        return this.context;
    }

    @NotNull
    public final BattleFaintedEvent copy(@NotNull PokemonBattle battle2, @NotNull BattlePokemon killed, @NotNull BattleContext context) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        Intrinsics.checkNotNullParameter((Object)killed, (String)"killed");
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        return new BattleFaintedEvent(battle2, killed, context);
    }

    public static /* synthetic */ BattleFaintedEvent copy$default(BattleFaintedEvent battleFaintedEvent, PokemonBattle pokemonBattle, BattlePokemon battlePokemon, BattleContext battleContext, int n, Object object) {
        if ((n & 1) != 0) {
            pokemonBattle = battleFaintedEvent.battle;
        }
        if ((n & 2) != 0) {
            battlePokemon = battleFaintedEvent.killed;
        }
        if ((n & 4) != 0) {
            battleContext = battleFaintedEvent.context;
        }
        return battleFaintedEvent.copy(pokemonBattle, battlePokemon, battleContext);
    }

    @NotNull
    public String toString() {
        return "BattleFaintedEvent(battle=" + this.battle + ", killed=" + this.killed + ", context=" + this.context + ")";
    }

    public int hashCode() {
        int result = this.battle.hashCode();
        result = result * 31 + this.killed.hashCode();
        result = result * 31 + this.context.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BattleFaintedEvent)) {
            return false;
        }
        BattleFaintedEvent battleFaintedEvent = (BattleFaintedEvent)other;
        if (!Intrinsics.areEqual((Object)this.battle, (Object)battleFaintedEvent.battle)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.killed, (Object)battleFaintedEvent.killed)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.context, (Object)battleFaintedEvent.context);
    }
}

