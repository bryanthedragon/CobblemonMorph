/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u0000*\u0004\b\u0000\u0010\u0001*\u000e\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u00028\u00010\u00022\b\u0012\u0004\u0012\u00028\u00010\u0004B\u001d\u0012\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\r0\u0015\u0012\u0006\u0010\u000f\u001a\u00028\u0000\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\n\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\f\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\bH&\u00a2\u0006\u0004\b\f\u0010\u000bJ\u001f\u0010\u0010\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u0017\u0010\u000f\u001a\u00028\u00008\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/SingleUpdatePacket;", "T", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "N", "Lcom/cobblemon/mod/common/net/messages/client/PokemonUpdatePacket;", "", "applyToPokemon", "()V", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "encodeDetails", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encodeValue", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "value", "set", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Ljava/lang/Object;)V", "Ljava/lang/Object;", "getValue", "()Ljava/lang/Object;", "Lkotlin/Function0;", "<init>", "(Lkotlin/jvm/functions/Function0;Ljava/lang/Object;)V", "common"})
public abstract class SingleUpdatePacket<T, N extends NetworkPacket<N>>
extends PokemonUpdatePacket<N> {
    private final T value;

    public SingleUpdatePacket(@NotNull Function0<? extends Pokemon> pokemon, T value2) {
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        super(pokemon);
        this.value = value2;
    }

    public final T getValue() {
        return this.value;
    }

    @Override
    public void encodeDetails(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.encodeValue(buffer);
    }

    @Override
    public void applyToPokemon() {
        this.set((Pokemon)this.getPokemon().invoke(), this.value);
    }

    public abstract void encodeValue(@NotNull FriendlyByteBuf var1);

    public abstract void set(@NotNull Pokemon var1, T var2);
}

