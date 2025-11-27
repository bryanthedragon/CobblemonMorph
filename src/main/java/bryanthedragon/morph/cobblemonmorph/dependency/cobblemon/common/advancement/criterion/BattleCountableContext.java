/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.PokemonBattle;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;

public class BattleCountableContext extends CountableContext {
    @NotNull
    private PokemonBattle battle;

    public BattleCountableContext(int times2, @NotNull PokemonBattle battle2) {
        super(times2);
        Intrinsics.checkNotNullParameter((Object)battle2, (String)"battle");
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

