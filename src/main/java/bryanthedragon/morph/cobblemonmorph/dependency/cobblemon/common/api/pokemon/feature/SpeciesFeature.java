/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  net.minecraft.nbt.CompoundTag
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature;

import com.google.gson.JsonObject;
import kotlin.Metadata;
import net.minecraft.nbt.CompoundTag;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0017\u0010\u0004\u001a\u00020\u00002\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0017\u0010\b\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\n\u001a\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\f\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\f\u0010\rR\u0014\u0010\u0011\u001a\u00020\u000e8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "", "Lcom/google/gson/JsonObject;", "pokemonJSON", "loadFromJSON", "(Lcom/google/gson/JsonObject;)Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "Lnet/minecraft/nbt/CompoundTag;", "pokemonNBT", "loadFromNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeature;", "saveToJSON", "(Lcom/google/gson/JsonObject;)Lcom/google/gson/JsonObject;", "saveToNBT", "(Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraft/nbt/CompoundTag;", "", "getName", "()Ljava/lang/String;", "name", "common"})
public interface SpeciesFeature {
    @NotNull
    public String getName();

    @NotNull
    public CompoundTag saveToNBT(@NotNull CompoundTag var1);

    @NotNull
    public SpeciesFeature loadFromNBT(@NotNull CompoundTag var1);

    @NotNull
    public JsonObject saveToJSON(@NotNull JsonObject var1);

    @NotNull
    public SpeciesFeature loadFromJSON(@NotNull JsonObject var1);
}

