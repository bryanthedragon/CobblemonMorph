/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.Natures;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001d2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001dB'\u0012\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00190\u0018\u0012\b\u0010\u0014\u001a\u0004\u0018\u00010\u0013\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0017\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u001a\u0010\n\u001a\u00020\t8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000f\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0019\u0010\u0014\u001a\u0004\u0018\u00010\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/NatureUpdatePacket;", "Lcom/cobblemon/mod/common/net/messages/client/PokemonUpdatePacket;", "", "applyToPokemon", "()V", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "encodeDetails", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "minted", "Z", "getMinted", "()Z", "Lcom/cobblemon/mod/common/pokemon/Nature;", "nature", "Lcom/cobblemon/mod/common/pokemon/Nature;", "getNature", "()Lcom/cobblemon/mod/common/pokemon/Nature;", "Lkotlin/Function0;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "<init>", "(Lkotlin/jvm/functions/Function0;Lcom/cobblemon/mod/common/pokemon/Nature;Z)V", "Companion", "common"})
public final class NatureUpdatePacket
extends PokemonUpdatePacket<NatureUpdatePacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private final Nature nature;
    private final boolean minted;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("nature_update");

    public NatureUpdatePacket(@NotNull Function0<? extends Pokemon> pokemon, @Nullable Nature nature, boolean minted) {
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        super(pokemon);
        this.nature = nature;
        this.minted = minted;
        this.id = ID;
    }

    @Nullable
    public final Nature getNature() {
        return this.nature;
    }

    public final boolean getMinted() {
        return this.minted;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encodeDetails(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_236821_((Object)this.nature, (arg_0, arg_1) -> NatureUpdatePacket.encodeDetails$lambda$0(buffer, arg_0, arg_1));
        buffer.writeBoolean(this.minted);
    }

    @Override
    public void applyToPokemon() {
        if (this.minted && this.nature == null) {
            ((Pokemon)this.getPokemon().invoke()).setMintedNature(null);
            return;
        }
        if (this.nature == null) {
            Cobblemon.INSTANCE.getLOGGER().warn("A null nature was attempted to be put onto: '" + this.getPokemon() + "'");
            return;
        }
        if (!this.minted) {
            ((Pokemon)this.getPokemon().invoke()).setNature(this.nature);
        } else {
            ((Pokemon)this.getPokemon().invoke()).setMintedNature(this.nature);
        }
    }

    private static final void encodeDetails$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, Nature v) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130085_(v.getName());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/NatureUpdatePacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/NatureUpdatePacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/NatureUpdatePacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final NatureUpdatePacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            return new NatureUpdatePacket(PokemonUpdatePacket.Companion.decodePokemon(buffer), (Nature)buffer.m_236868_(arg_0 -> Companion.decode$lambda$0(buffer, arg_0)), buffer.readBoolean());
        }

        private static final Nature decode$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
            Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
            ResourceLocation resourceLocation = $buffer.m_130281_();
            Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"buffer.readIdentifier()");
            return Natures.INSTANCE.getNature(resourceLocation);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

