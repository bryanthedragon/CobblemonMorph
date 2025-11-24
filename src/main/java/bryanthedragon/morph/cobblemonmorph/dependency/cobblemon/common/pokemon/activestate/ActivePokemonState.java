/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.activestate.PokemonState;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import org.jetbrains.annotations.Nullable;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001B\t\b\u0004\u00a2\u0006\u0004\b\t\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0003\u0010\u0004R\u0016\u0010\b\u001a\u0004\u0018\u00010\u00058&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007\u0082\u0001\u0002\n\u000b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/pokemon/activestate/ActivePokemonState;", "Lcom/cobblemon/mod/common/pokemon/activestate/PokemonState;", "", "recall", "()V", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "getEntity", "()Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "entity", "<init>", "Lcom/cobblemon/mod/common/pokemon/activestate/SentOutState;", "Lcom/cobblemon/mod/common/pokemon/activestate/ShoulderedState;", "common"})
public abstract class ActivePokemonState
extends PokemonState {
    private ActivePokemonState() {
        super(null);
    }

    @Nullable
    public abstract PokemonEntity getEntity();

    public abstract void recall();

    public /* synthetic */ ActivePokemonState(DefaultConstructorMarker $constructor_marker) {
        this();
    }
}

