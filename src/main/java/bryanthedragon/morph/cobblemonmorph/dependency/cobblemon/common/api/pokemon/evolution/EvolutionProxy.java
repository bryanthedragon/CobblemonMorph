/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  net.minecraft.nbt.Tag
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionController;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.evolution.EvolutionLike;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.BufferSerializer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization.DataSerializer;
import com.google.gson.JsonElement;
import kotlin.Metadata;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u0001*\b\b\u0001\u0010\u0003*\u00020\u00012\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u00042\u00020\u0007J\u0015\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\bH&\u00a2\u0006\u0004\b\t\u0010\nJ\u0017\u0010\u000b\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\bH&\u00a2\u0006\u0004\b\u000b\u0010\nJ\u000f\u0010\r\u001a\u00020\fH&\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0015\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00010\bH&\u00a2\u0006\u0004\b\u000f\u0010\n\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionProxy;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionLike;", "C", "S", "Lcom/cobblemon/mod/common/api/serialization/DataSerializer;", "Lnet/minecraft/nbt/Tag;", "Lcom/google/gson/JsonElement;", "Lcom/cobblemon/mod/common/api/serialization/BufferSerializer;", "Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionController;", "client", "()Lcom/cobblemon/mod/common/api/pokemon/evolution/EvolutionController;", "current", "", "isClient", "()Z", "server", "common"})
public interface EvolutionProxy<C extends EvolutionLike, S extends EvolutionLike>
extends DataSerializer<Tag, JsonElement>,
BufferSerializer {
    public boolean isClient();

    @NotNull
    public EvolutionController<? extends EvolutionLike> current();

    @NotNull
    public EvolutionController<C> client();

    @NotNull
    public EvolutionController<S> server();
}

