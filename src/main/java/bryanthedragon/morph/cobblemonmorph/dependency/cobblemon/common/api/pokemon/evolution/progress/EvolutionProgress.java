/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.DataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonObject;
import kotlin.Metadata;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002J\u000f\u0010\u0005\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u00020\u0007H&\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH&\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\rH&\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0017\u0010\u0013\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;", "T", "Lcom/cobblemon/mod/common/api/serialization/DataSerializer;", "Lnet/minecraft/nbt/CompoundTag;", "Lcom/google/gson/JsonObject;", "currentProgress", "()Ljava/lang/Object;", "Lnet/minecraft/resources/ResourceLocation;", "id", "()Lnet/minecraft/resources/ResourceLocation;", "", "reset", "()V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "shouldKeep", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "progress", "updateProgress", "(Ljava/lang/Object;)V", "common"})
public interface EvolutionProgress<T>
extends DataSerializer<CompoundTag, JsonObject> {
    @NotNull
    public ResourceLocation id();

    public T currentProgress();

    public void updateProgress(T var1);

    public void reset();

    public boolean shouldKeep(@NotNull Pokemon var1);
}

