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
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonProperties;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.pokemon.BattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle.BattleInitializePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 !2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001!B!\b\u0016\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0016\u001a\u00020\u001a\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u001b\u0010\u001cB)\b\u0016\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0016\u001a\u00020\u001a\u0012\u0006\u0010\u001e\u001a\u00020\u001d\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u001b\u0010\u001fB\u001f\u0012\u0006\u0010\u0011\u001a\u00020\u0010\u0012\u0006\u0010\u0016\u001a\u00020\u0015\u0012\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u001b\u0010 J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\r\u0010\u000fR\u0017\u0010\u0011\u001a\u00020\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0016\u001a\u00020\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/net/messages/client/battle/BattleTransformPokemonPacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "isAlly", "Z", "()Z", "", "pnx", "Ljava/lang/String;", "getPnx", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO;", "updatedPokemon", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO;", "getUpdatedPokemon", "()Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO;", "Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;", "<init>", "(Ljava/lang/String;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Z)V", "Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;", "mock", "(Ljava/lang/String;Lcom/cobblemon/mod/common/battles/pokemon/BattlePokemon;Lcom/cobblemon/mod/common/api/pokemon/PokemonProperties;Z)V", "(Ljava/lang/String;Lcom/cobblemon/mod/common/net/messages/client/battle/BattleInitializePacket$ActiveBattlePokemonDTO;Z)V", "Companion", "common"})
public final class BattleTransformPokemonPacket
implements NetworkPacket<BattleTransformPokemonPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final String pnx;
    @NotNull
    private final BattleInitializePacket.ActiveBattlePokemonDTO updatedPokemon;
    private final boolean isAlly;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("battle_transform_pokemon");

    public BattleTransformPokemonPacket(@NotNull String pnx, @NotNull BattleInitializePacket.ActiveBattlePokemonDTO updatedPokemon, boolean isAlly) {
        Intrinsics.checkNotNullParameter((Object)pnx, (String)"pnx");
        Intrinsics.checkNotNullParameter((Object)updatedPokemon, (String)"updatedPokemon");
        this.pnx = pnx;
        this.updatedPokemon = updatedPokemon;
        this.isAlly = isAlly;
        this.id = ID;
    }

    @NotNull
    public final String getPnx() {
        return this.pnx;
    }

    @NotNull
    public final BattleInitializePacket.ActiveBattlePokemonDTO getUpdatedPokemon() {
        return this.updatedPokemon;
    }

    public final boolean isAlly() {
        return this.isAlly;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    public BattleTransformPokemonPacket(@NotNull String pnx, @NotNull BattlePokemon updatedPokemon, boolean isAlly) {
        Intrinsics.checkNotNullParameter((Object)pnx, (String)"pnx");
        Intrinsics.checkNotNullParameter((Object)updatedPokemon, (String)"updatedPokemon");
        this(pnx, BattleInitializePacket.ActiveBattlePokemonDTO.Companion.fromPokemon$default(BattleInitializePacket.ActiveBattlePokemonDTO.Companion, updatedPokemon, isAlly, null, 4, null), isAlly);
    }

    public BattleTransformPokemonPacket(@NotNull String pnx, @NotNull BattlePokemon updatedPokemon, @NotNull PokemonProperties mock, boolean isAlly) {
        Intrinsics.checkNotNullParameter((Object)pnx, (String)"pnx");
        Intrinsics.checkNotNullParameter((Object)updatedPokemon, (String)"updatedPokemon");
        Intrinsics.checkNotNullParameter((Object)mock, (String)"mock");
        this(pnx, BattleInitializePacket.ActiveBattlePokemonDTO.Companion.fromMock(updatedPokemon, isAlly, mock), isAlly);
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(this.pnx);
        this.updatedPokemon.saveToBuffer(buffer);
        buffer.writeBoolean(this.isAlly);
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

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/battle/BattleTransformPokemonPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleTransformPokemonPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleTransformPokemonPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final BattleTransformPokemonPacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            String string = buffer.m_130277_();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
            return new BattleTransformPokemonPacket(string, BattleInitializePacket.ActiveBattlePokemonDTO.Companion.loadFromBuffer(buffer), buffer.readBoolean());
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

