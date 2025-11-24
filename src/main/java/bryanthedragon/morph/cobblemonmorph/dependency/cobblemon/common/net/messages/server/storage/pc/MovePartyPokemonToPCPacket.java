/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.storage.pc;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party.PartyPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCPosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u001d2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001dB!\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\u0006\u0010\r\u001a\u00020\f\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0012\u001a\u0004\u0018\u00010\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001e"}, d2={"Lcom/cobblemon/mod/common/net/messages/server/storage/pc/MovePartyPokemonToPCPacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "partyPosition", "Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "getPartyPosition", "()Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;", "pcPosition", "Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;", "getPcPosition", "()Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;", "Ljava/util/UUID;", "pokemonID", "Ljava/util/UUID;", "getPokemonID", "()Ljava/util/UUID;", "<init>", "(Ljava/util/UUID;Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;)V", "Companion", "common"})
public final class MovePartyPokemonToPCPacket
implements NetworkPacket<MovePartyPokemonToPCPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final UUID pokemonID;
    @NotNull
    private final PartyPosition partyPosition;
    @Nullable
    private final PCPosition pcPosition;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("move_party_pokemon_to_pc");

    public MovePartyPokemonToPCPacket(@NotNull UUID pokemonID, @NotNull PartyPosition partyPosition, @Nullable PCPosition pcPosition) {
        Intrinsics.checkNotNullParameter((Object)pokemonID, (String)"pokemonID");
        Intrinsics.checkNotNullParameter((Object)partyPosition, (String)"partyPosition");
        this.pokemonID = pokemonID;
        this.partyPosition = partyPosition;
        this.pcPosition = pcPosition;
        this.id = ID;
    }

    @NotNull
    public final UUID getPokemonID() {
        return this.pokemonID;
    }

    @NotNull
    public final PartyPosition getPartyPosition() {
        return this.partyPosition;
    }

    @Nullable
    public final PCPosition getPcPosition() {
        return this.pcPosition;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130077_(this.pokemonID);
        PartyPosition.Companion.writePartyPosition(buffer, this.partyPosition);
        buffer.m_236821_((Object)this.pcPosition, MovePartyPokemonToPCPacket::encode$lambda$0);
    }

    @Override
    public void sendToPlayer(@NotNull ServerPlayer player) {
        NetworkPacket.DefaultImpls.sendToPlayer(this, player);
    }

    @Override
    public void sendToPlayers(@NotNull Iterable<? extends ServerPlayer> players2) {
        NetworkPacket.DefaultImpls.sendToPlayers(this, players2);
    }

    @Override
    public void sendToAllPlayers() {
        NetworkPacket.DefaultImpls.sendToAllPlayers(this);
    }

    @Override
    public void sendToServer() {
        NetworkPacket.DefaultImpls.sendToServer(this);
    }

    @Override
    public void sendToPlayersAround(double x, double y, double z, double distance, @NotNull ResourceKey<Level> worldKey, @NotNull Function1<? super ServerPlayer, Boolean> exclusionCondition) {
        NetworkPacket.DefaultImpls.sendToPlayersAround(this, x, y, z, distance, worldKey, exclusionCondition);
    }

    @Override
    @NotNull
    public FriendlyByteBuf toBuffer() {
        return NetworkPacket.DefaultImpls.toBuffer(this);
    }

    private static final void encode$lambda$0(FriendlyByteBuf pb, PCPosition value2) {
        Intrinsics.checkNotNullExpressionValue((Object)pb, (String)"pb");
        Intrinsics.checkNotNullExpressionValue((Object)value2, (String)"value");
        PCPosition.Companion.writePCPosition(pb, value2);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/server/storage/pc/MovePartyPokemonToPCPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/server/storage/pc/MovePartyPokemonToPCPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/server/storage/pc/MovePartyPokemonToPCPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final MovePartyPokemonToPCPacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            UUID uUID = buffer.m_130259_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"buffer.readUuid()");
            return new MovePartyPokemonToPCPacket(uUID, PartyPosition.Companion.readPartyPosition(buffer), (PCPosition)buffer.m_236868_(Companion::decode$lambda$0));
        }

        private static final PCPosition decode$lambda$0(FriendlyByteBuf it) {
            Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
            return PCPosition.Companion.readPCPosition(it);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

