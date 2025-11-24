/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  net.minecraft.nbt.CompoundTag
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003J\u001a\u0010\u0006\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0005\u001a\u00020\u0004H\u00a6\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u0006\u001a\u0004\u0018\u00018\u00002\u0006\u0010\t\u001a\u00020\bH\u00a6\u0002\u00a2\u0006\u0004\b\u0006\u0010\nJ\u001a\u0010\u0006\u001a\u0004\u0018\u00018\u00002\u0006\u0010\f\u001a\u00020\u000bH\u00a6\u0002\u00a2\u0006\u0004\b\u0006\u0010\r\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "T", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "invoke", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "Lcom/google/gson/JsonObject;", "json", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "Lnet/minecraft/nbt/CompoundTag;", "nbt", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "common"})
public interface SpeciesFeatureProvider<T extends SpeciesFeature> {
    @Nullable
    public T invoke(@NotNull Pokemon var1);

    @Nullable
    public T invoke(@NotNull CompoundTag var1);

    @Nullable
    public T invoke(@NotNull JsonObject var1);
}

