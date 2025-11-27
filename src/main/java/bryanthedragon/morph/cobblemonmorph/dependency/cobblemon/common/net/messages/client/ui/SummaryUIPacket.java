/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.ui;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.PokemonDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u001c2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001cB'\b\u0016\u0012\u0012\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00180\u0017\"\u00020\u0018\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0019\u0010\u001aB\u001f\b\u0000\u0012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u0019\u0010\u001bJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\r\u001a\u00020\f8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/ui/SummaryUIPacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "editable", "Z", "getEditable", "()Z", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "Lcom/cobblemon/mod/common/net/messages/PokemonDTO;", "pokemon", "Ljava/util/List;", "getPokemon", "()Ljava/util/List;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "<init>", "([Lcom/cobblemon/mod/common/pokemon/Pokemon;Z)V", "(Ljava/util/List;Z)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nSummaryUIPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SummaryUIPacket.kt\ncom/cobblemon/mod/common/net/messages/client/ui/SummaryUIPacket\n+ 2 _Arrays.kt\nkotlin/collections/ArraysKt___ArraysKt\n*L\n1#1,32:1\n11335#2:33\n11670#2,3:34\n*S KotlinDebug\n*F\n+ 1 SummaryUIPacket.kt\ncom/cobblemon/mod/common/net/messages/client/ui/SummaryUIPacket\n*L\n21#1:33\n21#1:34,3\n*E\n"})
public final class SummaryUIPacket
implements NetworkPacket<SummaryUIPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final List<PokemonDTO> pokemon;
    private final boolean editable;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtils.cobblemonResource("summary_ui");

    public SummaryUIPacket(@NotNull List<PokemonDTO> pokemon, boolean editable) {
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        this.pokemon = pokemon;
        this.editable = editable;
        this.id = ID;
    }

    @NotNull
    public final List<PokemonDTO> getPokemon() {
        return this.pokemon;
    }

    public final boolean getEditable() {
        return this.editable;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    /*
     * WARNING - void declaration
     */
    public SummaryUIPacket(@NotNull Pokemon[] pokemon, boolean editable) {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Pokemon[] pokemonArray = pokemon;
        SummaryUIPacket summaryUIPacket = this;
        boolean $i$f$map = false;
        void var5_6 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(((void)$this$map$iv).length);
        boolean $i$f$mapTo = false;
        int n = ((void)$this$mapTo$iv$iv).length;
        for (int i = 0; i < n; ++i) {
            void it;
            void item$iv$iv;
            void var11_12 = item$iv$iv = $this$mapTo$iv$iv[i];
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(new PokemonDTO((Pokemon)it, true));
        }
        summaryUIPacket((List)destination$iv$iv, editable);
    }

    public /* synthetic */ SummaryUIPacket(Pokemon[] pokemonArray, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            bl = true;
        }
        this(pokemonArray, bl);
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.writeBoolean(this.editable);
        buffer.m_236828_((Collection)this.pokemon, SummaryUIPacket::encode$lambda$1);
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

    private static final void encode$lambda$1(FriendlyByteBuf pb, PokemonDTO value2) {
        Intrinsics.checkNotNullExpressionValue((Object)pb, (String)"pb");
        value2.encode(pb);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/ui/SummaryUIPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/ui/SummaryUIPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/ui/SummaryUIPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nSummaryUIPacket.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SummaryUIPacket.kt\ncom/cobblemon/mod/common/net/messages/client/ui/SummaryUIPacket$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,32:1\n1#2:33\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final SummaryUIPacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            List list = buffer.m_236845_(Companion::decode$lambda$1);
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { Pokemo\u2026().apply { decode(it) } }");
            return new SummaryUIPacket(list, buffer.readBoolean());
        }

        private static final PokemonDTO decode$lambda$1(FriendlyByteBuf it) {
            PokemonDTO pokemonDTO;
            PokemonDTO $this$decode_u24lambda_u241_u24lambda_u240 = pokemonDTO = new PokemonDTO();
            boolean bl = false;
            Intrinsics.checkNotNullExpressionValue((Object)it, (String)"it");
            $this$decode_u24lambda_u241_u24lambda_u240.decode(it);
            return pokemonDTO;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

