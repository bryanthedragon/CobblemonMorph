/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.Stat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.stats.StatTypeAdapter;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.EVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.FormData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.IVs;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import java.util.Collection;
import kotlin.Metadata;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000r\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u00002\u00020\u0001J\u0015\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H&\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u000f\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\f\u001a\u00020\u000b2\u0006\u0010\n\u001a\u00020\tH&\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u0010\u001a\u00020\u00032\u0006\u0010\u000f\u001a\u00020\u000eH&\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001f\u0010\u0014\u001a\u00020\u00132\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0003H&\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0019\u0010\u0018\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0017\u001a\u00020\u0016H&\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u001a\u001a\u00020\u00032\u0006\u0010\u0017\u001a\u00020\u0016H&\u00a2\u0006\u0004\b\u001a\u0010\u0019J\u001f\u0010\u001d\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00020\u0003H&\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u001d\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010 \u001a\u00020\u001fH&\u00a2\u0006\u0004\b!\u0010\"J\u0017\u0010%\u001a\u00020\u00132\u0006\u0010$\u001a\u00020#H&\u00a2\u0006\u0004\b%\u0010&J\u0017\u0010%\u001a\u00020\u00132\u0006\u0010(\u001a\u00020'H&\u00a2\u0006\u0004\b%\u0010)J!\u0010+\u001a\u00020*2\u0006\u0010(\u001a\u00020'2\b\u0010$\u001a\u0004\u0018\u00010#H&\u00a2\u0006\u0004\b+\u0010,R\u0014\u00100\u001a\u00020-8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b.\u0010/\u00a8\u00061"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/stats/StatProvider;", "", "", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "all", "()Ljava/util/Collection;", "Lcom/cobblemon/mod/common/pokemon/EVs;", "createEmptyEVs", "()Lcom/cobblemon/mod/common/pokemon/EVs;", "", "minPerfectIVs", "Lcom/cobblemon/mod/common/pokemon/IVs;", "createEmptyIVs", "(I)Lcom/cobblemon/mod/common/pokemon/IVs;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "stat", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;)V", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "fromIdentifier", "(Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;", "fromIdentifierOrThrow", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "getStatForPokemon", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/stats/Stat;)I", "Lcom/cobblemon/mod/common/api/pokemon/stats/Stat$Type;", "type", "ofType", "(Lcom/cobblemon/mod/common/api/pokemon/stats/Stat$Type;)Ljava/util/Collection;", "Lcom/cobblemon/mod/common/pokemon/FormData;", "form", "provide", "(Lcom/cobblemon/mod/common/pokemon/FormData;)V", "Lcom/cobblemon/mod/common/pokemon/Species;", "species", "(Lcom/cobblemon/mod/common/pokemon/Species;)V", "", "toShowdown", "(Lcom/cobblemon/mod/common/pokemon/Species;Lcom/cobblemon/mod/common/pokemon/FormData;)Ljava/lang/String;", "Lcom/cobblemon/mod/common/api/pokemon/stats/StatTypeAdapter;", "getTypeAdapter", "()Lcom/cobblemon/mod/common/api/pokemon/stats/StatTypeAdapter;", "typeAdapter", "common"})
public interface StatProvider {
    @NotNull
    public StatTypeAdapter getTypeAdapter();

    @NotNull
    public Collection<Stat> all();

    @NotNull
    public Collection<Stat> ofType(@NotNull Stat.Type var1);

    public void provide(@NotNull Species var1);

    public void provide(@NotNull FormData var1);

    @NotNull
    public EVs createEmptyEVs();

    @NotNull
    public IVs createEmptyIVs(int var1);

    @NotNull
    public String toShowdown(@NotNull Species var1, @Nullable FormData var2);

    public int getStatForPokemon(@NotNull Pokemon var1, @NotNull Stat var2);

    @Nullable
    public Stat fromIdentifier(@NotNull ResourceLocation var1);

    @NotNull
    public Stat fromIdentifierOrThrow(@NotNull ResourceLocation var1);

    @NotNull
    public Stat decode(@NotNull FriendlyByteBuf var1);

    public void encode(@NotNull FriendlyByteBuf var1, @NotNull Stat var2);
}

