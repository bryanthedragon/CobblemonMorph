/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.adapters.PokemonPropertiesAdapter;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\b\n\u0002\u0018\u0002\n\u0002\b\u0007\"\u0017\u0010\u0001\u001a\u00020\u00008\u0006\u00a2\u0006\f\n\u0004\b\u0001\u0010\u0002\u001a\u0004\b\u0003\u0010\u0004\"\u0017\u0010\u0005\u001a\u00020\u00008\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0002\u001a\u0004\b\u0006\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/util/adapters/PokemonPropertiesAdapter;", "pokemonPropertiesLongAdapter", "Lcom/cobblemon/mod/common/util/adapters/PokemonPropertiesAdapter;", "getPokemonPropertiesLongAdapter", "()Lcom/cobblemon/mod/common/util/adapters/PokemonPropertiesAdapter;", "pokemonPropertiesShortAdapter", "getPokemonPropertiesShortAdapter", "common"})
public final class PokemonPropertiesAdapterKt {
    @NotNull
    private static final PokemonPropertiesAdapter pokemonPropertiesLongAdapter = new PokemonPropertiesAdapter(true);
    @NotNull
    private static final PokemonPropertiesAdapter pokemonPropertiesShortAdapter = new PokemonPropertiesAdapter(false);

    @NotNull
    public static final PokemonPropertiesAdapter getPokemonPropertiesLongAdapter() {
        return pokemonPropertiesLongAdapter;
    }

    @NotNull
    public static final PokemonPropertiesAdapter getPokemonPropertiesShortAdapter() {
        return pokemonPropertiesShortAdapter;
    }
}

