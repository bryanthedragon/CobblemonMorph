/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function2
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.tags;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonPropertyType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.StringProperty;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.properties.tags.PokemonFlagProperty;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function2;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0015\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001b\u0010\b\u001a\u0004\u0018\u00010\u00022\b\u0010\u0007\u001a\u0004\u0018\u00010\u0004H\u0016\u00a2\u0006\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00048\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\n\u0010\u000bR \u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u0006R\u001a\u0010\u0010\u001a\u00020\u000f8\u0016X\u0096D\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/pokemon/properties/tags/PokemonFlagProperty;", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonPropertyType;", "Lcom/cobblemon/mod/common/pokemon/properties/StringProperty;", "", "", "examples", "()Ljava/util/Set;", "value", "fromString", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/pokemon/properties/StringProperty;", "KEY", "Ljava/lang/String;", "keys", "Ljava/util/Set;", "getKeys", "", "needsKey", "Z", "getNeedsKey", "()Z", "<init>", "()V", "common"})
public final class PokemonFlagProperty
implements CustomPokemonPropertyType<StringProperty> {
    @NotNull
    public static final PokemonFlagProperty INSTANCE = new PokemonFlagProperty();
    @NotNull
    private static final String KEY = "tag";
    @NotNull
    private static final Set<String> keys = SetsKt.setOf((Object)"tag");
    private static final boolean needsKey = true;

    private PokemonFlagProperty() {
    }

    @NotNull
    public Set<String> getKeys() {
        return keys;
    }

    @Override
    public boolean getNeedsKey() {
        return needsKey;
    }

    @Override
    @Nullable
    public StringProperty fromString(@Nullable String value2) {
        return value2 == null ? null : new StringProperty(KEY, value2, (Function2<? super Pokemon, ? super String, Unit>)((Function2)fromString.1.INSTANCE), (Function2<? super Pokemon, ? super String, Boolean>)((Function2)fromString.2.INSTANCE));
    }

    @NotNull
    public Set<String> examples() {
        return SetsKt.emptySet();
    }
}

