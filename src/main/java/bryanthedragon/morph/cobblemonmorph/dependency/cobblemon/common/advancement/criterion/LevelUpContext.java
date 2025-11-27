/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

public class LevelUpContext {
    private int level;
    @NotNull
    private Pokemon pokemon;

    public LevelUpContext(int level, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.level = level;
        this.pokemon = pokemon;
    }

    public final int getLevel() {
        return this.level;
    }

    public final void setLevel(int n) {
        this.level = n;
    }

    @NotNull
    public final Pokemon getPokemon() {
        return this.pokemon;
    }

    public final void setPokemon(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"<set-?>");
        this.pokemon = pokemon;
    }
}

