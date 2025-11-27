/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.pasture;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pasture.PasturePermissions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 %2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0002%&B5\u0012\u0006\u0010\u0016\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0006\u0010\r\u001a\u00020\f\u0012\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d\u0012\u0006\u0010\u0019\u001a\u00020\u0018\u00a2\u0006\u0004\b#\u0010$J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0016\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0013\u001a\u0004\b\u0017\u0010\u0015R\u0017\u0010\u0019\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u001d\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u001e0\u001d8\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"\u00a8\u0006'"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "limit", "I", "getLimit", "()I", "Ljava/util/UUID;", "pastureId", "Ljava/util/UUID;", "getPastureId", "()Ljava/util/UUID;", "pcId", "getPcId", "Lcom/cobblemon/mod/common/api/pasture/PasturePermissions;", "permissions", "Lcom/cobblemon/mod/common/api/pasture/PasturePermissions;", "getPermissions", "()Lcom/cobblemon/mod/common/api/pasture/PasturePermissions;", "", "Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket$PasturePokemonDataDTO;", "tetheredPokemon", "Ljava/util/List;", "getTetheredPokemon", "()Ljava/util/List;", "<init>", "(Ljava/util/UUID;Ljava/util/UUID;ILjava/util/List;Lcom/cobblemon/mod/common/api/pasture/PasturePermissions;)V", "Companion", "PasturePokemonDataDTO", "common"})
public final class OpenPasturePacket
implements NetworkPacket<OpenPasturePacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final UUID pcId;
    @NotNull
    private final UUID pastureId;
    private final int limit;
    @NotNull
    private final List<PasturePokemonDataDTO> tetheredPokemon;
    @NotNull
    private final PasturePermissions permissions;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtils.cobblemonResource("open_pasture");

    public OpenPasturePacket(@NotNull UUID pcId, @NotNull UUID pastureId, int limit, @NotNull List<PasturePokemonDataDTO> tetheredPokemon, @NotNull PasturePermissions permissions) {
        Intrinsics.checkNotNullParameter((Object)pcId, (String)"pcId");
        Intrinsics.checkNotNullParameter((Object)pastureId, (String)"pastureId");
        Intrinsics.checkNotNullParameter(tetheredPokemon, (String)"tetheredPokemon");
        Intrinsics.checkNotNullParameter((Object)permissions, (String)"permissions");
        this.pcId = pcId;
        this.pastureId = pastureId;
        this.limit = limit;
        this.tetheredPokemon = tetheredPokemon;
        this.permissions = permissions;
        this.id = ID;
    }

    @NotNull
    public final UUID getPcId() {
        return this.pcId;
    }

    @NotNull
    public final UUID getPastureId() {
        return this.pastureId;
    }

    public final int getLimit() {
        return this.limit;
    }

    @NotNull
    public final List<PasturePokemonDataDTO> getTetheredPokemon() {
        return this.tetheredPokemon;
    }

    @NotNull
    public final PasturePermissions getPermissions() {
        return this.permissions;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130077_(this.pcId);
        buffer.m_130077_(this.pastureId);
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.limit);
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.tetheredPokemon.size());
        for (PasturePokemonDataDTO tethered : this.tetheredPokemon) {
            tethered.encode(buffer);
        }
        this.permissions.encode(buffer);
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

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final OpenPasturePacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            UUID pcId = buffer.m_130259_();
            UUID pastureId = buffer.m_130259_();
            int limit = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
            List dtos = new ArrayList();
            int n = buffer.readUnsignedByte();
            int n2 = 0;
            while (n2 < n) {
                int it = n2++;
                boolean bl = false;
                dtos.add(PasturePokemonDataDTO.Companion.decode(buffer));
            }
            PasturePermissions permissions = PasturePermissions.Companion.decode(buffer);
            Intrinsics.checkNotNullExpressionValue((Object)pcId, (String)"pcId");
            Intrinsics.checkNotNullExpressionValue((Object)pastureId, (String)"pastureId");
            return new OpenPasturePacket(pcId, pastureId, limit, dtos, permissions);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 /2\u00020\u0001:\u0001/BM\u0012\u0006\u0010&\u001a\u00020!\u0012\u0006\u0010\"\u001a\u00020!\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010)\u001a\u00020(\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u0012\u0006\u0010\u001d\u001a\u00020\u001c\u0012\u0006\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\u0004\b-\u0010.J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001d\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u000e\u001a\u00020\r8\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0013\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0018\u001a\u00020\u00178\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001d\u001a\u00020\u001c8\u0006\u00a2\u0006\f\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 R\u0017\u0010\"\u001a\u00020!8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u0017\u0010&\u001a\u00020!8\u0006\u00a2\u0006\f\n\u0004\b&\u0010#\u001a\u0004\b'\u0010%R\u0017\u0010)\u001a\u00020(8\u0006\u00a2\u0006\f\n\u0004\b)\u0010*\u001a\u0004\b+\u0010,\u00a8\u00060"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket$PasturePokemonDataDTO;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "", "aspects", "Ljava/util/Set;", "getAspects", "()Ljava/util/Set;", "Lnet/minecraft/network/chat/Component;", "displayName", "Lnet/minecraft/network/chat/Component;", "getDisplayName", "()Lnet/minecraft/network/chat/Component;", "", "entityKnown", "Z", "getEntityKnown", "()Z", "Lnet/minecraft/world/item/ItemStack;", "heldItem", "Lnet/minecraft/world/item/ItemStack;", "getHeldItem", "()Lnet/minecraft/world/item/ItemStack;", "", "level", "I", "getLevel", "()I", "Ljava/util/UUID;", "playerId", "Ljava/util/UUID;", "getPlayerId", "()Ljava/util/UUID;", "pokemonId", "getPokemonId", "Lnet/minecraft/resources/ResourceLocation;", "species", "Lnet/minecraft/resources/ResourceLocation;", "getSpecies", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "(Ljava/util/UUID;Ljava/util/UUID;Lnet/minecraft/network/chat/Component;Lnet/minecraft/resources/ResourceLocation;Ljava/util/Set;Lnet/minecraft/world/item/ItemStack;IZ)V", "Companion", "common"})
    public static final class PasturePokemonDataDTO {
        @NotNull
        public static final Companion Companion = new Companion(null);
        @NotNull
        private final UUID pokemonId;
        @NotNull
        private final UUID playerId;
        @NotNull
        private final Component displayName;
        @NotNull
        private final ResourceLocation species;
        @NotNull
        private final Set<String> aspects;
        @NotNull
        private final ItemStack heldItem;
        private final int level;
        private final boolean entityKnown;

        public PasturePokemonDataDTO(@NotNull UUID pokemonId, @NotNull UUID playerId, @NotNull Component displayName, @NotNull ResourceLocation species, @NotNull Set<String> aspects, @NotNull ItemStack heldItem2, int level, boolean entityKnown) {
            Intrinsics.checkNotNullParameter((Object)pokemonId, (String)"pokemonId");
            Intrinsics.checkNotNullParameter((Object)playerId, (String)"playerId");
            Intrinsics.checkNotNullParameter((Object)displayName, (String)"displayName");
            Intrinsics.checkNotNullParameter((Object)species, (String)"species");
            Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
            Intrinsics.checkNotNullParameter((Object)heldItem2, (String)"heldItem");
            this.pokemonId = pokemonId;
            this.playerId = playerId;
            this.displayName = displayName;
            this.species = species;
            this.aspects = aspects;
            this.heldItem = heldItem2;
            this.level = level;
            this.entityKnown = entityKnown;
        }

        @NotNull
        public final UUID getPokemonId() {
            return this.pokemonId;
        }

        @NotNull
        public final UUID getPlayerId() {
            return this.playerId;
        }

        @NotNull
        public final Component getDisplayName() {
            return this.displayName;
        }

        @NotNull
        public final ResourceLocation getSpecies() {
            return this.species;
        }

        @NotNull
        public final Set<String> getAspects() {
            return this.aspects;
        }

        @NotNull
        public final ItemStack getHeldItem() {
            return this.heldItem;
        }

        public final int getLevel() {
            return this.level;
        }

        public final boolean getEntityKnown() {
            return this.entityKnown;
        }

        public final void encode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            buffer.m_130077_(this.pokemonId);
            buffer.m_130077_(this.playerId);
            buffer.m_130083_(this.displayName);
            buffer.m_130085_(this.species);
            buffer.m_236828_((Collection)this.aspects, (arg_0, arg_1) -> PasturePokemonDataDTO.encode$lambda$0(buffer, arg_0, arg_1));
            buffer.m_130055_(this.heldItem);
            NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_SHORT, this.level);
            buffer.writeBoolean(this.entityKnown);
        }

        private static final void encode$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String v) {
            Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
            $buffer.m_130070_(v);
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket$PasturePokemonDataDTO$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket$PasturePokemonDataDTO;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/pasture/OpenPasturePacket$PasturePokemonDataDTO;", "<init>", "()V", "common"})
        public static final class Companion {
            private Companion() {
            }

            @NotNull
            public final PasturePokemonDataDTO decode(@NotNull FriendlyByteBuf buffer) {
                Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
                UUID pokemonId = buffer.m_130259_();
                UUID playerId = buffer.m_130259_();
                Component displayName = buffer.m_130238_();
                ResourceLocation species = buffer.m_130281_();
                List list = buffer.m_236845_(Companion::decode$lambda$0);
                Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { it.readString() }");
                Set aspects = CollectionsKt.toSet((Iterable)list);
                ItemStack heldItem2 = buffer.m_130267_();
                int level = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_SHORT);
                boolean entityKnown = buffer.readBoolean();
                Intrinsics.checkNotNullExpressionValue((Object)pokemonId, (String)"pokemonId");
                Intrinsics.checkNotNullExpressionValue((Object)playerId, (String)"playerId");
                Intrinsics.checkNotNullExpressionValue((Object)displayName, (String)"displayName");
                Intrinsics.checkNotNullExpressionValue((Object)species, (String)"species");
                Intrinsics.checkNotNullExpressionValue((Object)heldItem2, (String)"heldItem");
                return new PasturePokemonDataDTO(pokemonId, playerId, displayName, species, aspects, heldItem2, level, entityKnown);
            }

            private static final String decode$lambda$0(FriendlyByteBuf it) {
                return it.m_130277_();
            }

            public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }
    }
}

