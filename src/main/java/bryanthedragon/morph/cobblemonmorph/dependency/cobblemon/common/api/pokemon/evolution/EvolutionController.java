/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.markers.KMutableSet
 *  net.minecraft.nbt.Tag
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.progress.EvolutionProgress;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.BufferSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.DataSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.google.gson.JsonElement;
import java.util.Collection;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.markers.KMutableSet;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u00032\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00042\u00020\u0007J\u0019\u0010\n\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\bH&\u00a2\u0006\u0004\b\n\u0010\u000bJR\u0010\u0014\u001a\u00028\u0001\"\f\b\u0001\u0010\f*\u0006\u0012\u0002\b\u00030\t2%\u0010\u0011\u001a!\u0012\u0017\u0012\u0015\u0012\u0002\b\u00030\t\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\n\u0012\u0004\u0012\u00020\u00100\r2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00010\u0012H&\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0018\u001a\u00020\u00172\u0006\u0010\u0016\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\u0018\u0010\u0019J%\u0010\u001a\u001a\u00028\u0001\"\f\b\u0001\u0010\f*\u0006\u0012\u0002\b\u00030\t2\u0006\u0010\n\u001a\u00028\u0001H&\u00a2\u0006\u0004\b\u001a\u0010\u001bR\u0014\u0010\u001f\u001a\u00020\u001c8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionController;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionLike;", "T", "", "Lcom/cobblemon/mod/common/api/serialization/DataSerializer;", "Lnet/minecraft/nbt/Tag;", "Lcom/google/gson/JsonElement;", "Lcom/cobblemon/mod/common/api/serialization/BufferSerializer;", "", "Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;", "progress", "()Ljava/util/Collection;", "P", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "", "predicate", "Lkotlin/Function0;", "progressFactory", "progressFirstOrCreate", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function0;)Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;", "evolution", "", "start", "(Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionLike;)V", "trackProgress", "(Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;)Lcom/cobblemon/mod/common/api/pokemon/evolution/progress/EvolutionProgress;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "common"})
public interface EvolutionController<T extends EvolutionLike>
extends Set<T>,
DataSerializer<Tag, JsonElement>,
BufferSerializer,
KMutableSet {
    @NotNull
    public Pokemon getPokemon();

    public void start(@NotNull T var1);

    @NotNull
    public Collection<EvolutionProgress<?>> progress();

    @NotNull
    public <P extends EvolutionProgress<?>> P trackProgress(@NotNull P var1);

    @NotNull
    public <P extends EvolutionProgress<?>> P progressFirstOrCreate(@NotNull Function1<? super EvolutionProgress<?>, Boolean> var1, @NotNull Function0<? extends P> var2);
}

