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
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.Level
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.trade;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Gender;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
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
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 \u001e2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0002\u001e\u001fB'\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u000e\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u001f\u0010\u0018\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00170\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Ljava/util/UUID;", "traderId", "Ljava/util/UUID;", "getTraderId", "()Ljava/util/UUID;", "Lnet/minecraft/network/chat/MutableComponent;", "traderName", "Lnet/minecraft/network/chat/MutableComponent;", "getTraderName", "()Lnet/minecraft/network/chat/MutableComponent;", "", "Lcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket$TradeablePokemon;", "traderParty", "Ljava/util/List;", "getTraderParty", "()Ljava/util/List;", "<init>", "(Ljava/util/UUID;Lnet/minecraft/network/chat/MutableComponent;Ljava/util/List;)V", "Companion", "TradeablePokemon", "common"})
public final class TradeStartedPacket
implements NetworkPacket<TradeStartedPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final UUID traderId;
    @NotNull
    private final MutableComponent traderName;
    @NotNull
    private final List<TradeablePokemon> traderParty;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("trade_started");

    public TradeStartedPacket(@NotNull UUID traderId, @NotNull MutableComponent traderName, @NotNull List<TradeablePokemon> traderParty) {
        Intrinsics.checkNotNullParameter((Object)traderId, (String)"traderId");
        Intrinsics.checkNotNullParameter((Object)traderName, (String)"traderName");
        Intrinsics.checkNotNullParameter(traderParty, (String)"traderParty");
        this.traderId = traderId;
        this.traderName = traderName;
        this.traderParty = traderParty;
        this.id = ID;
    }

    @NotNull
    public final UUID getTraderId() {
        return this.traderId;
    }

    @NotNull
    public final MutableComponent getTraderName() {
        return this.traderName;
    }

    @NotNull
    public final List<TradeablePokemon> getTraderParty() {
        return this.traderParty;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130077_(this.traderId);
        buffer.m_130083_((Component)this.traderName);
        buffer.m_236828_((Collection)this.traderParty, (arg_0, arg_1) -> TradeStartedPacket.encode$lambda$1(buffer, arg_0, arg_1));
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

    private static final void encode$lambda$1$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, TradeablePokemon v2) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        v2.encode($buffer);
    }

    private static final void encode$lambda$1(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, TradeablePokemon v) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_236821_((Object)v, (arg_0, arg_1) -> TradeStartedPacket.encode$lambda$1$lambda$0($buffer, arg_0, arg_1));
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final TradeStartedPacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            UUID uUID = buffer.m_130259_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"buffer.readUuid()");
            MutableComponent mutableComponent = buffer.m_130238_().m_6881_();
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"buffer.readText().copy()");
            List list = buffer.m_236845_(arg_0 -> Companion.decode$lambda$1(buffer, arg_0));
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { buffer\u2026okemon.decode(buffer) } }");
            return new TradeStartedPacket(uUID, mutableComponent, list);
        }

        private static final TradeablePokemon decode$lambda$1$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
            Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
            return TradeablePokemon.Companion.decode($buffer);
        }

        private static final TradeablePokemon decode$lambda$1(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
            Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
            return (TradeablePokemon)$buffer.m_236868_(arg_0 -> Companion.decode$lambda$1$lambda$0($buffer, arg_0));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000b\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 32\u00020\u0001:\u00013B\u0011\b\u0016\u0012\u0006\u0010/\u001a\u00020.\u00a2\u0006\u0004\b0\u00101BE\u0012\u0006\u0010 \u001a\u00020\u001f\u0012\u0006\u0010%\u001a\u00020$\u0012\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n\u0012\u0006\u0010\u001b\u001a\u00020\u001a\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0016\u001a\u00020\u0015\u0012\u0006\u0010*\u001a\u00020)\u00a2\u0006\u0004\b0\u00102J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0015\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n8\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\u0017\u0010\u0011\u001a\u00020\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0016\u001a\u00020\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001b\u001a\u00020\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u0017\u0010 \u001a\u00020\u001f8\u0006\u00a2\u0006\f\n\u0004\b \u0010!\u001a\u0004\b\"\u0010#R\u0017\u0010%\u001a\u00020$8\u0006\u00a2\u0006\f\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(R\u0017\u0010*\u001a\u00020)8\u0006\u00a2\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-\u00a8\u00064"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket$TradeablePokemon;", "", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "asRenderablePokemon", "()Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "", "aspects", "Ljava/util/Set;", "getAspects", "()Ljava/util/Set;", "Lcom/cobblemon/mod/common/pokemon/Gender;", "gender", "Lcom/cobblemon/mod/common/pokemon/Gender;", "getGender", "()Lcom/cobblemon/mod/common/pokemon/Gender;", "Lnet/minecraft/world/item/ItemStack;", "heldItem", "Lnet/minecraft/world/item/ItemStack;", "getHeldItem", "()Lnet/minecraft/world/item/ItemStack;", "", "level", "I", "getLevel", "()I", "Ljava/util/UUID;", "pokemonId", "Ljava/util/UUID;", "getPokemonId", "()Ljava/util/UUID;", "Lnet/minecraft/resources/ResourceLocation;", "species", "Lnet/minecraft/resources/ResourceLocation;", "getSpecies", "()Lnet/minecraft/resources/ResourceLocation;", "", "tradeable", "Z", "getTradeable", "()Z", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "<init>", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "(Ljava/util/UUID;Lnet/minecraft/resources/ResourceLocation;Ljava/util/Set;ILcom/cobblemon/mod/common/pokemon/Gender;Lnet/minecraft/world/item/ItemStack;Z)V", "Companion", "common"})
    public static final class TradeablePokemon {
        @NotNull
        public static final Companion Companion = new Companion(null);
        @NotNull
        private final UUID pokemonId;
        @NotNull
        private final ResourceLocation species;
        @NotNull
        private final Set<String> aspects;
        private final int level;
        @NotNull
        private final Gender gender;
        @NotNull
        private final ItemStack heldItem;
        private final boolean tradeable;

        public TradeablePokemon(@NotNull UUID pokemonId, @NotNull ResourceLocation species, @NotNull Set<String> aspects, int level, @NotNull Gender gender, @NotNull ItemStack heldItem2, boolean tradeable) {
            Intrinsics.checkNotNullParameter((Object)pokemonId, (String)"pokemonId");
            Intrinsics.checkNotNullParameter((Object)species, (String)"species");
            Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
            Intrinsics.checkNotNullParameter((Object)((Object)gender), (String)"gender");
            Intrinsics.checkNotNullParameter((Object)heldItem2, (String)"heldItem");
            this.pokemonId = pokemonId;
            this.species = species;
            this.aspects = aspects;
            this.level = level;
            this.gender = gender;
            this.heldItem = heldItem2;
            this.tradeable = tradeable;
        }

        @NotNull
        public final UUID getPokemonId() {
            return this.pokemonId;
        }

        @NotNull
        public final ResourceLocation getSpecies() {
            return this.species;
        }

        @NotNull
        public final Set<String> getAspects() {
            return this.aspects;
        }

        public final int getLevel() {
            return this.level;
        }

        @NotNull
        public final Gender getGender() {
            return this.gender;
        }

        @NotNull
        public final ItemStack getHeldItem() {
            return this.heldItem;
        }

        public final boolean getTradeable() {
            return this.tradeable;
        }

        public TradeablePokemon(@NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            UUID uUID = pokemon.getUuid();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"pokemon.uuid");
            ResourceLocation resourceLocation = pokemon.getSpecies().getResourceIdentifier();
            Set<String> set2 = pokemon.getAspects();
            int n = pokemon.getLevel();
            Gender gender = pokemon.getGender();
            ItemStack itemStack = pokemon.heldItem().m_41777_();
            Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"pokemon.heldItem().copy()");
            this(uUID, resourceLocation, set2, n, gender, itemStack, pokemon.getTradeable());
        }

        public final void encode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            buffer.m_130077_(this.pokemonId);
            buffer.m_130085_(this.species);
            buffer.m_236828_((Collection)this.aspects, (arg_0, arg_1) -> TradeablePokemon.encode$lambda$0(buffer, arg_0, arg_1));
            NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_SHORT, this.level);
            NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.gender.ordinal());
            buffer.m_130055_(this.heldItem);
            buffer.writeBoolean(this.tradeable);
        }

        @NotNull
        public final RenderablePokemon asRenderablePokemon() {
            Species species = PokemonSpecies.INSTANCE.getByIdentifier(this.species);
            Intrinsics.checkNotNull((Object)species);
            return new RenderablePokemon(species, this.aspects);
        }

        private static final void encode$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String v) {
            Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
            $buffer.m_130070_(v);
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket$TradeablePokemon$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket$TradeablePokemon;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/trade/TradeStartedPacket$TradeablePokemon;", "<init>", "()V", "common"})
        public static final class Companion {
            private Companion() {
            }

            @NotNull
            public final TradeablePokemon decode(@NotNull FriendlyByteBuf buffer) {
                Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
                UUID uUID = buffer.m_130259_();
                Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"buffer.readUuid()");
                ResourceLocation resourceLocation = buffer.m_130281_();
                Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"buffer.readIdentifier()");
                List list = buffer.m_236845_(Companion::decode$lambda$0);
                Intrinsics.checkNotNullExpressionValue((Object)list, (String)"buffer.readList { it.readString() }");
                Set set2 = CollectionsKt.toSet((Iterable)list);
                int n = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_SHORT);
                Gender gender = Gender.values()[NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE)];
                ItemStack itemStack = buffer.m_130267_();
                Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"buffer.readItemStack()");
                return new TradeablePokemon(uUID, resourceLocation, set2, n, gender, itemStack, buffer.readBoolean());
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

