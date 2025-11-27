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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.starter;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.player.PlayerData;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 #2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001#B\u001d\b\u0016\u0012\u0006\u0010\u001f\u001a\u00020\u001e\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0004\b \u0010!B3\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u0015\u001a\u00020\f\u0012\u0006\u0010\u0017\u001a\u00020\f\u0012\b\u0010\u001a\u001a\u0004\u0018\u00010\u0019\u0012\b\u0010\u0011\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0004\b \u0010\"J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0011\u001a\u0004\u0018\u00010\f8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0015\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u000e\u001a\u0004\b\u0016\u0010\u0010R\u0017\u0010\u0017\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u000e\u001a\u0004\b\u0018\u0010\u0010R\u0019\u0010\u001a\u001a\u0004\u0018\u00010\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\u00a8\u0006$"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/starter/SetClientPlayerDataPacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "promptStarter", "Z", "getPromptStarter", "()Z", "resetStarterPrompt", "Ljava/lang/Boolean;", "getResetStarterPrompt", "()Ljava/lang/Boolean;", "starterLocked", "getStarterLocked", "starterSelected", "getStarterSelected", "Ljava/util/UUID;", "starterUUID", "Ljava/util/UUID;", "getStarterUUID", "()Ljava/util/UUID;", "Lcom/cobblemon/mod/common/api/storage/player/PlayerData;", "playerData", "<init>", "(Lcom/cobblemon/mod/common/api/storage/player/PlayerData;Ljava/lang/Boolean;)V", "(ZZZLjava/util/UUID;Ljava/lang/Boolean;)V", "Companion", "common"})
public final class SetClientPlayerDataPacket
implements NetworkPacket<SetClientPlayerDataPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final boolean promptStarter;
    private final boolean starterLocked;
    private final boolean starterSelected;
    @Nullable
    private final UUID starterUUID;
    @Nullable
    private final Boolean resetStarterPrompt;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtils.cobblemonResource("set_client_playerdata");

    public SetClientPlayerDataPacket(boolean promptStarter, boolean starterLocked, boolean starterSelected, @Nullable UUID starterUUID, @Nullable Boolean resetStarterPrompt) {
        this.promptStarter = promptStarter;
        this.starterLocked = starterLocked;
        this.starterSelected = starterSelected;
        this.starterUUID = starterUUID;
        this.resetStarterPrompt = resetStarterPrompt;
        this.id = ID;
    }

    public final boolean getPromptStarter() {
        return this.promptStarter;
    }

    public final boolean getStarterLocked() {
        return this.starterLocked;
    }

    public final boolean getStarterSelected() {
        return this.starterSelected;
    }

    @Nullable
    public final UUID getStarterUUID() {
        return this.starterUUID;
    }

    @Nullable
    public final Boolean getResetStarterPrompt() {
        return this.resetStarterPrompt;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    public SetClientPlayerDataPacket(@NotNull PlayerData playerData, @Nullable Boolean resetStarterPrompt) {
        Intrinsics.checkNotNullParameter((Object)playerData, (String)"playerData");
        this(!playerData.getStarterPrompted() || !Cobblemon.INSTANCE.getStarterConfig().getPromptStarterOnceOnly(), playerData.getStarterLocked(), playerData.getStarterSelected(), playerData.getStarterUUID(), resetStarterPrompt);
    }

    public /* synthetic */ SetClientPlayerDataPacket(PlayerData playerData, Boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            bl = null;
        }
        this(playerData, bl);
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.writeBoolean(this.promptStarter);
        buffer.writeBoolean(this.starterLocked);
        buffer.writeBoolean(this.starterSelected);
        UUID starterUUID = this.starterUUID;
        buffer.m_236821_((Object)starterUUID, SetClientPlayerDataPacket::encode$lambda$0);
        Boolean resetStarterPrompt = this.resetStarterPrompt;
        buffer.m_236821_((Object)resetStarterPrompt, SetClientPlayerDataPacket::encode$lambda$1);
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

    private static final void encode$lambda$0(FriendlyByteBuf pb, UUID value2) {
        pb.m_130077_(value2);
    }

    private static final void encode$lambda$1(FriendlyByteBuf pb, Boolean value2) {
        Intrinsics.checkNotNullExpressionValue((Object)value2, (String)"value");
        pb.writeBoolean(value2.booleanValue());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/starter/SetClientPlayerDataPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/starter/SetClientPlayerDataPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/starter/SetClientPlayerDataPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final SetClientPlayerDataPacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            boolean promptStarter = buffer.readBoolean();
            boolean starterLocked = buffer.readBoolean();
            boolean starterSelected = buffer.readBoolean();
            UUID starterUUID = (UUID)buffer.m_236868_(Companion::decode$lambda$0);
            Boolean resetStarterPrompt = (Boolean)buffer.m_236868_(Companion::decode$lambda$1);
            return new SetClientPlayerDataPacket(promptStarter, starterLocked, starterSelected, starterUUID, resetStarterPrompt);
        }

        private static final UUID decode$lambda$0(FriendlyByteBuf it) {
            return it.m_130259_();
        }

        private static final Boolean decode$lambda$1(FriendlyByteBuf it) {
            return it.readBoolean();
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

