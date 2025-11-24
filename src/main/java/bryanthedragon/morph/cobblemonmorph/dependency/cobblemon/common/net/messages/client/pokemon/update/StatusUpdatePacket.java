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

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Statuses;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.PokemonUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pokemon.update.SingleUpdatePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00152\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0015B\u001f\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0012\u0012\b\u0010\n\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J!\u0010\u000b\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\b2\b\u0010\n\u001a\u0004\u0018\u00010\u0002H\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/StatusUpdatePacket;", "Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/SingleUpdatePacket;", "Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encodeValue", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "value", "set", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lkotlin/Function0;", "<init>", "(Lkotlin/jvm/functions/Function0;Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;)V", "Companion", "common"})
public final class StatusUpdatePacket
extends SingleUpdatePacket<PersistentStatus, StatusUpdatePacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("status_update");

    public StatusUpdatePacket(@NotNull Function0<? extends Pokemon> pokemon, @Nullable PersistentStatus value2) {
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        super(pokemon, value2);
        this.id = ID;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encodeValue(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_236821_(this.getValue(), StatusUpdatePacket::encodeValue$lambda$0);
    }

    @Override
    public void set(@NotNull Pokemon pokemon, @Nullable PersistentStatus value2) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        if (value2 == null) {
            pokemon.setStatus(null);
            return;
        }
        pokemon.applyStatus(value2);
    }

    private static final void encodeValue$lambda$0(FriendlyByteBuf pb, PersistentStatus value2) {
        pb.m_130085_(value2.getName());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/StatusUpdatePacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/StatusUpdatePacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pokemon/update/StatusUpdatePacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final StatusUpdatePacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            Function0<Pokemon> pokemon = PokemonUpdatePacket.Companion.decodePokemon(buffer);
            ResourceLocation resourceLocation = (ResourceLocation)buffer.m_236868_(FriendlyByteBuf::m_130281_);
            if (resourceLocation == null) {
                return new StatusUpdatePacket(pokemon, null);
            }
            ResourceLocation identifier = resourceLocation;
            Status status = Statuses.INSTANCE.getStatus(identifier);
            PersistentStatus status2 = status instanceof PersistentStatus ? (PersistentStatus)status : null;
            return new StatusUpdatePacket(pokemon, status2);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

