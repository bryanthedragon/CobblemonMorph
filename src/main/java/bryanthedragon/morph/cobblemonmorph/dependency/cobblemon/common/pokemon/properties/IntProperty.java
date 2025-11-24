/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.pokemon.PokemonEntity;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u000f\b\u0016\u0018\u00002\u00020\u0001B\u00f7\u0001\u0012\u0006\u0010\u0019\u001a\u00020\n\u0012\u0006\u0010\u0015\u001a\u00020\u0014\u00126\u0010\u001c\u001a2\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\u0014\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u00040\u0011\u00126\u0010\u0016\u001a2\u0012\u0013\u0012\u00110\u0002\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0003\u0012\u0013\u0012\u00110\u0014\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u00040\u0011\u00126\u0010\u001d\u001a2\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\u0014\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\r0\u0011\u00126\u0010\u0018\u001a2\u0012\u0013\u0012\u00110\u0002\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0003\u0012\u0013\u0012\u00110\u0014\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\r0\u0011\u00a2\u0006\u0004\b!\u0010\"J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u0005\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u000e\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u0010RD\u0010\u0016\u001a2\u0012\u0013\u0012\u00110\u0002\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0003\u0012\u0013\u0012\u00110\u0014\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u00040\u00118\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0016\u0010\u0017RD\u0010\u0018\u001a2\u0012\u0013\u0012\u00110\u0002\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0003\u0012\u0013\u0012\u00110\u0014\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\r0\u00118\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0018\u0010\u0017R\u0017\u0010\u0019\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\fRD\u0010\u001c\u001a2\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\u0014\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\u00040\u00118\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001c\u0010\u0017RD\u0010\u001d\u001a2\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\b\u0012\u0013\u0012\u00110\u0014\u00a2\u0006\f\b\u0012\u0012\b\b\u0013\u0012\u0004\b\b(\u0015\u0012\u0004\u0012\u00020\r0\u00118\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001d\u0010\u0017R\u0017\u0010\u0015\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u001e\u001a\u0004\b\u001f\u0010 \u00a8\u0006#"}, d2={"Lcom/cobblemon/mod/common/pokemon/properties/IntProperty;", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonProperty;", "Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;", "pokemonEntity", "", "apply", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "", "asString", "()Ljava/lang/String;", "", "matches", "(Lcom/cobblemon/mod/common/entity/pokemon/PokemonEntity;)Z", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "", "value", "entityApplicator", "Lkotlin/jvm/functions/Function2;", "entityMatcher", "key", "Ljava/lang/String;", "getKey", "pokemonApplicator", "pokemonMatcher", "I", "getValue", "()I", "<init>", "(Ljava/lang/String;ILkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;Lkotlin/jvm/functions/Function2;)V", "common"})
public class IntProperty
implements CustomPokemonProperty {
    @NotNull
    private final String key;
    private final int value;
    @NotNull
    private final Function2<Pokemon, Integer, Unit> pokemonApplicator;
    @NotNull
    private final Function2<PokemonEntity, Integer, Unit> entityApplicator;
    @NotNull
    private final Function2<Pokemon, Integer, Boolean> pokemonMatcher;
    @NotNull
    private final Function2<PokemonEntity, Integer, Boolean> entityMatcher;

    public IntProperty(@NotNull String key, int value2, @NotNull Function2<? super Pokemon, ? super Integer, Unit> pokemonApplicator, @NotNull Function2<? super PokemonEntity, ? super Integer, Unit> entityApplicator, @NotNull Function2<? super Pokemon, ? super Integer, Boolean> pokemonMatcher, @NotNull Function2<? super PokemonEntity, ? super Integer, Boolean> entityMatcher) {
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Intrinsics.checkNotNullParameter(pokemonApplicator, (String)"pokemonApplicator");
        Intrinsics.checkNotNullParameter(entityApplicator, (String)"entityApplicator");
        Intrinsics.checkNotNullParameter(pokemonMatcher, (String)"pokemonMatcher");
        Intrinsics.checkNotNullParameter(entityMatcher, (String)"entityMatcher");
        this.key = key;
        this.value = value2;
        this.pokemonApplicator = pokemonApplicator;
        this.entityApplicator = entityApplicator;
        this.pokemonMatcher = pokemonMatcher;
        this.entityMatcher = entityMatcher;
    }

    @NotNull
    public final String getKey() {
        return this.key;
    }

    public final int getValue() {
        return this.value;
    }

    @Override
    @NotNull
    public String asString() {
        return this.key + "=" + this.value;
    }

    @Override
    public void apply(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        this.pokemonApplicator.invoke((Object)pokemon, (Object)this.value);
    }

    @Override
    public void apply(@NotNull PokemonEntity pokemonEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        this.entityApplicator.invoke((Object)pokemonEntity, (Object)this.value);
    }

    @Override
    public boolean matches(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        return (Boolean)this.pokemonMatcher.invoke((Object)pokemon, (Object)this.value);
    }

    @Override
    public boolean matches(@NotNull PokemonEntity pokemonEntity) {
        Intrinsics.checkNotNullParameter((Object)pokemonEntity, (String)"pokemonEntity");
        return (Boolean)this.entityMatcher.invoke((Object)pokemonEntity, (Object)this.value);
    }
}

