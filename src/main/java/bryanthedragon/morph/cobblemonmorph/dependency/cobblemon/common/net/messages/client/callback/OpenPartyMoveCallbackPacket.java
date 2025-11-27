/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.callback;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectPokemonDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000  2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001 B7\u0012\u0006\u0010\u001a\u001a\u00020\u0019\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u001e\u0010\u0015\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00110\u00120\u0011\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R/\u0010\u0015\u001a\u001a\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0013\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\u00110\u00120\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u0017\u0010\u001a\u001a\u00020\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/callback/OpenPartyMoveCallbackPacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/network/chat/MutableComponent;", "partyTitle", "Lnet/minecraft/network/chat/MutableComponent;", "getPartyTitle", "()Lnet/minecraft/network/chat/MutableComponent;", "", "Lkotlin/Pair;", "Lcom/cobblemon/mod/common/api/callback/PartySelectPokemonDTO;", "Lcom/cobblemon/mod/common/api/callback/MoveSelectDTO;", "pokemonList", "Ljava/util/List;", "getPokemonList", "()Ljava/util/List;", "Ljava/util/UUID;", "uuid", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "<init>", "(Ljava/util/UUID;Lnet/minecraft/network/chat/MutableComponent;Ljava/util/List;)V", "Companion", "common"})
public final class OpenPartyMoveCallbackPacket
implements NetworkPacket<OpenPartyMoveCallbackPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final UUID uuid;
    @NotNull
    private final MutableComponent partyTitle;
    @NotNull
    private final List<Pair<PartySelectPokemonDTO, List<MoveSelectDTO>>> pokemonList;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtils.cobblemonResource("open_party_move_callback");

    public OpenPartyMoveCallbackPacket(@NotNull UUID uuid2, @NotNull MutableComponent partyTitle, @NotNull List<? extends Pair<? extends PartySelectPokemonDTO, ? extends List<MoveSelectDTO>>> pokemonList) {
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter((Object)partyTitle, (String)"partyTitle");
        Intrinsics.checkNotNullParameter(pokemonList, (String)"pokemonList");
        this.uuid = uuid2;
        this.partyTitle = partyTitle;
        this.pokemonList = pokemonList;
        this.id = ID;
    }

    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }

    @NotNull
    public final MutableComponent getPartyTitle() {
        return this.partyTitle;
    }

    @NotNull
    public final List<Pair<PartySelectPokemonDTO, List<MoveSelectDTO>>> getPokemonList() {
        return this.pokemonList;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130077_(this.uuid);
        buffer.m_130083_((Component)this.partyTitle);
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.pokemonList.size());
        for (Pair<PartySelectPokemonDTO, List<MoveSelectDTO>> pair : this.pokemonList) {
            PartySelectPokemonDTO pkDTO = (PartySelectPokemonDTO)pair.component1();
            List mvDTOs = (List)pair.component2();
            pkDTO.writeToBuffer(buffer);
            NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, mvDTOs.size());
            for (MoveSelectDTO mvDTO : mvDTOs) {
                mvDTO.writeToBuffer(buffer);
            }
        }
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

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/callback/OpenPartyMoveCallbackPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/callback/OpenPartyMoveCallbackPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/callback/OpenPartyMoveCallbackPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final OpenPartyMoveCallbackPacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            UUID uuid2 = buffer.m_130259_();
            MutableComponent partyTitle = buffer.m_130238_().m_6881_();
            List pokemonList = new ArrayList();
            int n = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
            for (int i = 0; i < n; ++i) {
                int it = i;
                boolean bl = false;
                PartySelectPokemonDTO pkDTO = new PartySelectPokemonDTO(buffer);
                List mvDTOs = new ArrayList();
                int n2 = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
                int n3 = 0;
                while (n3 < n2) {
                    int it2 = n3++;
                    boolean bl2 = false;
                    mvDTOs.add(new MoveSelectDTO(buffer));
                }
                pokemonList.add(TuplesKt.to((Object)pkDTO, (Object)mvDTOs));
            }
            Intrinsics.checkNotNullExpressionValue((Object)uuid2, (String)"uuid");
            Intrinsics.checkNotNullExpressionValue((Object)partyTitle, (String)"partyTitle");
            return new OpenPartyMoveCallbackPacket(uuid2, partyTitle, pokemonList);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

