/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.pokemon.evolution.EvolutionEvent;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.Evolution;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionDisplay;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u000b\u001a\u00020\u0002\u0012\u0006\u0010\f\u001a\u00020\u0005\u0012\u0006\u0010\r\u001a\u00020\b\u00a2\u0006\u0004\b#\u0010$J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0010\u0010\t\u001a\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ.\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u000b\u001a\u00020\u00022\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\bH\u00c6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001a\u0010\u0013\u001a\u00020\u00122\b\u0010\u0011\u001a\u0004\u0018\u00010\u0010H\u00d6\u0003\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0016\u001a\u00020\u0015H\u00d6\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0019\u001a\u00020\u0018H\u00d6\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aR\"\u0010\f\u001a\u00020\u00058\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\f\u0010\u001b\u001a\u0004\b\u001c\u0010\u0007\"\u0004\b\u001d\u0010\u001eR\u001a\u0010\r\u001a\u00020\b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\r\u0010\u001f\u001a\u0004\b \u0010\nR\u001a\u0010\u000b\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000b\u0010!\u001a\u0004\b\"\u0010\u0004\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/api/events/pokemon/evolution/EvolutionDisplayEvent;", "Lcom/cobblemon/mod/common/api/events/pokemon/evolution/EvolutionEvent;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "component1", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;", "component2", "()Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "component3", "()Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "pokemon", "display", "evolution", "copy", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;)Lcom/cobblemon/mod/common/api/events/pokemon/evolution/EvolutionDisplayEvent;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;", "getDisplay", "setDisplay", "(Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;)V", "Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;", "getEvolution", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionDisplay;Lcom/cobblemon/mod/common/api/pokemon/evolution/Evolution;)V", "common"})
public final class EvolutionDisplayEvent
implements EvolutionEvent {
    @NotNull
    private final Pokemon pokemon;
    @NotNull
    private EvolutionDisplay display;
    @NotNull
    private final Evolution evolution;

    public EvolutionDisplayEvent(@NotNull Pokemon pokemon, @NotNull EvolutionDisplay display, @NotNull Evolution evolution) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)display, (String)"display");
        Intrinsics.checkNotNullParameter((Object)evolution, (String)"evolution");
        this.pokemon = pokemon;
        this.display = display;
        this.evolution = evolution;
    }

    @Override
    @NotNull
    public Pokemon getPokemon() {
        return this.pokemon;
    }

    @NotNull
    public final EvolutionDisplay getDisplay() {
        return this.display;
    }

    public final void setDisplay(@NotNull EvolutionDisplay evolutionDisplay) {
        Intrinsics.checkNotNullParameter((Object)evolutionDisplay, (String)"<set-?>");
        this.display = evolutionDisplay;
    }

    @Override
    @NotNull
    public Evolution getEvolution() {
        return this.evolution;
    }

    @NotNull
    public final Pokemon component1() {
        return this.pokemon;
    }

    @NotNull
    public final EvolutionDisplay component2() {
        return this.display;
    }

    @NotNull
    public final Evolution component3() {
        return this.evolution;
    }

    @NotNull
    public final EvolutionDisplayEvent copy(@NotNull Pokemon pokemon, @NotNull EvolutionDisplay display, @NotNull Evolution evolution) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter((Object)display, (String)"display");
        Intrinsics.checkNotNullParameter((Object)evolution, (String)"evolution");
        return new EvolutionDisplayEvent(pokemon, display, evolution);
    }

    public static /* synthetic */ EvolutionDisplayEvent copy$default(EvolutionDisplayEvent evolutionDisplayEvent, Pokemon pokemon, EvolutionDisplay evolutionDisplay, Evolution evolution, int n, Object object) {
        if ((n & 1) != 0) {
            pokemon = evolutionDisplayEvent.pokemon;
        }
        if ((n & 2) != 0) {
            evolutionDisplay = evolutionDisplayEvent.display;
        }
        if ((n & 4) != 0) {
            evolution = evolutionDisplayEvent.evolution;
        }
        return evolutionDisplayEvent.copy(pokemon, evolutionDisplay, evolution);
    }

    @NotNull
    public String toString() {
        return "EvolutionDisplayEvent(pokemon=" + this.pokemon + ", display=" + this.display + ", evolution=" + this.evolution + ")";
    }

    public int hashCode() {
        int result = this.pokemon.hashCode();
        result = result * 31 + this.display.hashCode();
        result = result * 31 + this.evolution.hashCode();
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof EvolutionDisplayEvent)) {
            return false;
        }
        EvolutionDisplayEvent evolutionDisplayEvent = (EvolutionDisplayEvent)other;
        if (!Intrinsics.areEqual((Object)this.pokemon, (Object)evolutionDisplayEvent.pokemon)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.display, (Object)evolutionDisplayEvent.display)) {
            return false;
        }
        return Intrinsics.areEqual((Object)this.evolution, (Object)evolutionDisplayEvent.evolution);
    }
}

