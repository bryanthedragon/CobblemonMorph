/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.properties.CustomPokemonProperty;
import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010\u001e\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u001c\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003J\u0015\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H&\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001b\u0010\t\u001a\u0004\u0018\u00018\u00002\b\u0010\b\u001a\u0004\u0018\u00010\u0005H&\u00a2\u0006\u0004\b\t\u0010\nR\u001a\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u000b8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u0012\u001a\u00020\u000f8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/properties/CustomPokemonPropertyType;", "Lcom/cobblemon/mod/common/api/properties/CustomPokemonProperty;", "T", "", "", "", "examples", "()Ljava/util/Collection;", "value", "fromString", "(Ljava/lang/String;)Lcom/cobblemon/mod/common/api/properties/CustomPokemonProperty;", "", "getKeys", "()Ljava/lang/Iterable;", "keys", "", "getNeedsKey", "()Z", "needsKey", "common"})
public interface CustomPokemonPropertyType<T extends CustomPokemonProperty> {
    @NotNull
    public Iterable<String> getKeys();

    public boolean getNeedsKey();

    @Nullable
    public T fromString(@Nullable String var1);

    @NotNull
    public Collection<String> examples();
}

