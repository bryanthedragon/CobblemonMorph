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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import java.util.EnumSet;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000  2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0002 !B-\u0012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\u0006\u0010\u001c\u001a\u00020\u0017\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00120\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0018\u001a\u00020\u00178\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001c\u001a\u00020\u00178\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u0019\u001a\u0004\b\u001d\u0010\u001b\u00a8\u0006\""}, d2={"Lcom/cobblemon/mod/common/net/messages/client/PlayerInteractOptionsPacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "numericTargetId", "I", "getNumericTargetId", "()I", "Ljava/util/EnumSet;", "Lcom/cobblemon/mod/common/net/messages/client/PlayerInteractOptionsPacket$Options;", "options", "Ljava/util/EnumSet;", "getOptions", "()Ljava/util/EnumSet;", "Ljava/util/UUID;", "selectedPokemonId", "Ljava/util/UUID;", "getSelectedPokemonId", "()Ljava/util/UUID;", "targetId", "getTargetId", "<init>", "(Ljava/util/EnumSet;Ljava/util/UUID;ILjava/util/UUID;)V", "Companion", "Options", "common"})
public final class PlayerInteractOptionsPacket
implements NetworkPacket<PlayerInteractOptionsPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final EnumSet<Options> options;
    @NotNull
    private final UUID targetId;
    private final int numericTargetId;
    @NotNull
    private final UUID selectedPokemonId;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtils.cobblemonResource("player_interactions");

    public PlayerInteractOptionsPacket(@NotNull EnumSet<Options> options, @NotNull UUID targetId, int numericTargetId, @NotNull UUID selectedPokemonId) {
        Intrinsics.checkNotNullParameter(options, (String)"options");
        Intrinsics.checkNotNullParameter((Object)targetId, (String)"targetId");
        Intrinsics.checkNotNullParameter((Object)selectedPokemonId, (String)"selectedPokemonId");
        this.options = options;
        this.targetId = targetId;
        this.numericTargetId = numericTargetId;
        this.selectedPokemonId = selectedPokemonId;
        this.id = ID;
    }

    @NotNull
    public final EnumSet<Options> getOptions() {
        return this.options;
    }

    @NotNull
    public final UUID getTargetId() {
        return this.targetId;
    }

    public final int getNumericTargetId() {
        return this.numericTargetId;
    }

    @NotNull
    public final UUID getSelectedPokemonId() {
        return this.selectedPokemonId;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_245616_(this.options, Options.class);
        buffer.m_130077_(this.targetId);
        buffer.writeInt(this.numericTargetId);
        buffer.m_130077_(this.selectedPokemonId);
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

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/PlayerInteractOptionsPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/PlayerInteractOptionsPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/PlayerInteractOptionsPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final PlayerInteractOptionsPacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            EnumSet enumSet = buffer.m_247336_(Options.class);
            Intrinsics.checkNotNullExpressionValue((Object)enumSet, (String)"buffer.readEnumSet(Options::class.java)");
            UUID uUID = buffer.m_130259_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"buffer.readUuid()");
            int n = buffer.readInt();
            UUID uUID2 = buffer.m_130259_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID2, (String)"buffer.readUuid()");
            return new PlayerInteractOptionsPacket(enumSet, uUID, n, uUID2);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/PlayerInteractOptionsPacket$Options;", "", "<init>", "(Ljava/lang/String;I)V", "BATTLE", "SPECTATE_BATTLE", "TRADE", "common"})
    public static final class Options
    extends Enum<Options> {
        public static final /* enum */ Options BATTLE = new Options();
        public static final /* enum */ Options SPECTATE_BATTLE = new Options();
        public static final /* enum */ Options TRADE = new Options();
        private static final /* synthetic */ Options[] $VALUES;

        public static Options[] values() {
            return (Options[])$VALUES.clone();
        }

        public static Options valueOf(String value2) {
            return Enum.valueOf(Options.class, value2);
        }

        static {
            $VALUES = optionsArray = new Options[]{Options.BATTLE, Options.SPECTATE_BATTLE, Options.TRADE};
        }
    }
}

