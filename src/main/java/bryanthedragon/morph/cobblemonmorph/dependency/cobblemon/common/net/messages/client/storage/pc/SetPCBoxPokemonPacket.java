/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.storage.pc;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc.PCBox;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.PokemonDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 !2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001!B\u0011\b\u0016\u0012\u0006\u0010\u001d\u001a\u00020\u001c\u00a2\u0006\u0004\b\u001e\u0010\u001fB-\b\u0000\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00120\u0011\u00a2\u0006\u0004\b\u001e\u0010 J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\r\u001a\u00020\f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R#\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00120\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0018\u001a\u00020\u00178\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/net/messages/client/storage/pc/SetPCBoxPokemonPacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "boxNumber", "I", "getBoxNumber", "()I", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "Lcom/cobblemon/mod/common/net/messages/PokemonDTO;", "pokemon", "Ljava/util/Map;", "getPokemon", "()Ljava/util/Map;", "Ljava/util/UUID;", "storeID", "Ljava/util/UUID;", "getStoreID", "()Ljava/util/UUID;", "Lcom/cobblemon/mod/common/api/storage/pc/PCBox;", "box", "<init>", "(Lcom/cobblemon/mod/common/api/storage/pc/PCBox;)V", "(Ljava/util/UUID;ILjava/util/Map;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nSetPCBoxPokemonPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SetPCBoxPokemonPacket.kt\ncom/cobblemon/mod/common/net/messages/client/storage/pc/SetPCBoxPokemonPacket\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n*L\n1#1,58:1\n125#2:59\n152#2,3:60\n*S KotlinDebug\n*F\n+ 1 SetPCBoxPokemonPacket.kt\ncom/cobblemon/mod/common/net/messages/client/storage/pc/SetPCBoxPokemonPacket\n*L\n37#1:59\n37#1:60,3\n*E\n"})
public final class SetPCBoxPokemonPacket
implements NetworkPacket<SetPCBoxPokemonPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final UUID storeID;
    private final int boxNumber;
    @NotNull
    private final Map<Integer, PokemonDTO> pokemon;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtils.cobblemonResource("set_pc_box");

    public SetPCBoxPokemonPacket(@NotNull UUID storeID, int boxNumber, @NotNull Map<Integer, PokemonDTO> pokemon) {
        Intrinsics.checkNotNullParameter((Object)storeID, (String)"storeID");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        this.storeID = storeID;
        this.boxNumber = boxNumber;
        this.pokemon = pokemon;
        this.id = ID;
    }

    @NotNull
    public final UUID getStoreID() {
        return this.storeID;
    }

    public final int getBoxNumber() {
        return this.boxNumber;
    }

    @NotNull
    public final Map<Integer, PokemonDTO> getPokemon() {
        return this.pokemon;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    /*
     * WARNING - void declaration
     */
    public SetPCBoxPokemonPacket(@NotNull PCBox box) {
        Collection<Pair> collection;
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter((Object)box, (String)"box");
        Map<Integer, Pokemon> map = box.getNonEmptySlots();
        int n = box.getBoxNumber();
        UUID uUID = box.getPc().getUuid();
        SetPCBoxPokemonPacket setPCBoxPokemonPacket = this;
        boolean $i$f$map = false;
        void var4_7 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList($this$map$iv.size());
        boolean $i$f$mapTo = false;
        Iterator iterator = $this$mapTo$iv$iv.entrySet().iterator();
        while (iterator.hasNext()) {
            void it;
            Map.Entry item$iv$iv;
            Map.Entry entry = item$iv$iv = iterator.next();
            collection = destination$iv$iv;
            boolean bl = false;
            collection.add(TuplesKt.to(it.getKey(), (Object)new PokemonDTO((Pokemon)it.getValue(), true)));
        }
        collection = (List)destination$iv$iv;
        setPCBoxPokemonPacket(uUID, n, MapsKt.toMap((Iterable)collection));
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130077_(this.storeID);
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.boxNumber);
        NetExtensionsKt.writeMapK$default((ByteBuf)buffer, null, this.pokemon, (Function1)new Function1<Map.Entry<? extends Integer, ? extends PokemonDTO>, Unit>(buffer){
            final /* synthetic */ FriendlyByteBuf $buffer;
            {
                this.$buffer = $buffer;
                super(1);
            }

            public final void invoke(@NotNull Map.Entry<Integer, PokemonDTO> entry) {
                Intrinsics.checkNotNullParameter(entry, (String)"<name for destructuring parameter 0>");
                int slot = ((Number)entry.getKey()).intValue();
                PokemonDTO pokemon = entry.getValue();
                NetExtensionsKt.writeSizedInt((ByteBuf)this.$buffer, IntSize.U_BYTE, slot);
                pokemon.encode(this.$buffer);
            }
        }, 1, null);
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

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/storage/pc/SetPCBoxPokemonPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/storage/pc/SetPCBoxPokemonPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/storage/pc/SetPCBoxPokemonPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final SetPCBoxPokemonPacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            UUID storeID = buffer.m_130259_();
            int boxNumber = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
            Map pokemonMap = new LinkedHashMap();
            NetExtensionsKt.readMapK$default((ByteBuf)buffer, null, pokemonMap, (Function0)new Function0<Pair<? extends Integer, ? extends PokemonDTO>>(buffer){
                final /* synthetic */ FriendlyByteBuf $buffer;
                {
                    this.$buffer = $buffer;
                    super(0);
                }

                /*
                 * WARNING - void declaration
                 */
                @NotNull
                public final Pair<Integer, PokemonDTO> invoke() {
                    void it;
                    PokemonDTO pokemonDTO = new PokemonDTO();
                    FriendlyByteBuf friendlyByteBuf = this.$buffer;
                    PokemonDTO pokemonDTO2 = pokemonDTO;
                    Integer n = NetExtensionsKt.readSizedInt((ByteBuf)this.$buffer, IntSize.U_BYTE);
                    boolean bl = false;
                    it.decode(friendlyByteBuf);
                    return TuplesKt.to((Object)n, (Object)pokemonDTO);
                }
            }, 1, null);
            Intrinsics.checkNotNullExpressionValue((Object)storeID, (String)"storeID");
            return new SetPCBoxPokemonPacket(storeID, boxNumber, pokemonMap);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

