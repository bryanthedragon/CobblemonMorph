/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon;

import kotlin.Metadata;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0010\b\n\u0002\b\n\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\tj\u0002\b\nj\u0002\b\u000b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/entity/pokemon/PokemonBehaviourFlag;", "", "", "bit", "I", "getBit", "()I", "<init>", "(Ljava/lang/String;I)V", "LOOKING", "EXCITED", "FLYING", "common"})
public final class PokemonBehaviourFlag
extends Enum<PokemonBehaviourFlag> {
    private final int bit = this.ordinal() + 1;
    public static final /* enum */ PokemonBehaviourFlag LOOKING = new PokemonBehaviourFlag();
    public static final /* enum */ PokemonBehaviourFlag EXCITED = new PokemonBehaviourFlag();
    public static final /* enum */ PokemonBehaviourFlag FLYING = new PokemonBehaviourFlag();
    private static final /* synthetic */ PokemonBehaviourFlag[] $VALUES;

    public final int getBit() {
        return this.bit;
    }

    public static PokemonBehaviourFlag[] values() {
        return (PokemonBehaviourFlag[])$VALUES.clone();
    }

    public static PokemonBehaviourFlag valueOf(String value2) {
        return Enum.valueOf(PokemonBehaviourFlag.class, value2);
    }

    static {
        $VALUES = pokemonBehaviourFlagArray = new PokemonBehaviourFlag[]{PokemonBehaviourFlag.LOOKING, PokemonBehaviourFlag.EXCITED, PokemonBehaviourFlag.FLYING};
    }
}

