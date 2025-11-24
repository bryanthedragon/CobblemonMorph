/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion.CountableContext;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;

import kotlin.Metadata;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0004\b\u0016\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\n\u001a\u00020\t\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u000b\u0010\fR\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/advancement/criterion/BattleCountableContext;", "Lcom/cobblemon/mod/common/advancement/criterion/CountableContext;", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "battle", "Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "getBattle", "()Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;", "setBattle", "(Lcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "", "times", "<init>", "(ILcom/cobblemon/mod/common/api/battles/model/PokemonBattle;)V", "common"})
public class BattleCountableContext
extends CountableContext {
    @NotNull
    private PokemonBattle battle;

    public BattleCountableContext(int times2, @NotNull PokemonBattle battle2) {
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
        super(times2);
        this.battle = battle2;
    }

    @NotNull
    public final PokemonBattle getBattle() {
        return this.battle;
    }

    public final void setBattle(@NotNull PokemonBattle pokemonBattle) {
        Intrinsics.checkNotNullParameter((Object)pokemonBattle, (String)"<set-?>");
        this.battle = pokemonBattle;
    }
}

