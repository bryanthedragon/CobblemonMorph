/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.toast;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.UUID;
import kotlin.Metadata;
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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u0000 02\b\u0012\u0004\u0012\u00020\u00000\u0001:\u000210BG\u0012\u0006\u0010'\u001a\u00020\f\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\u0006\u0010\u0012\u001a\u00020\u0011\u0012\u0006\u0010\u001e\u001a\u00020\u001d\u0012\u0006\u0010#\u001a\u00020\"\u0012\u0006\u0010*\u001a\u00020)\u0012\u0006\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b.\u0010/J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u00118\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001b\u0010\u0013\u001a\u0004\b\u001c\u0010\u0015R\u0017\u0010\u001e\u001a\u00020\u001d8\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!R\u0017\u0010#\u001a\u00020\"8\u0006\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&R\u0017\u0010'\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b'\u0010\u000e\u001a\u0004\b(\u0010\u0010R\u0017\u0010*\u001a\u00020)8\u0006\u00a2\u0006\f\n\u0004\b*\u0010+\u001a\u0004\b,\u0010-\u00a8\u00062"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket$Behaviour;", "behaviour", "Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket$Behaviour;", "getBehaviour", "()Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket$Behaviour;", "Lnet/minecraft/network/chat/Component;", "description", "Lnet/minecraft/network/chat/Component;", "getDescription", "()Lnet/minecraft/network/chat/Component;", "Lnet/minecraft/resources/ResourceLocation;", "frameTexture", "Lnet/minecraft/resources/ResourceLocation;", "getFrameTexture", "()Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/world/item/ItemStack;", "icon", "Lnet/minecraft/world/item/ItemStack;", "getIcon", "()Lnet/minecraft/world/item/ItemStack;", "id", "getId", "", "progress", "F", "getProgress", "()F", "", "progressColor", "I", "getProgressColor", "()I", "title", "getTitle", "Ljava/util/UUID;", "uuid", "Ljava/util/UUID;", "getUuid", "()Ljava/util/UUID;", "<init>", "(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/Component;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/resources/ResourceLocation;FILjava/util/UUID;Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket$Behaviour;)V", "Companion", "Behaviour", "common"})
public final class ToastPacket
implements NetworkPacket<ToastPacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Component title;
    @NotNull
    private final Component description;
    @NotNull
    private final ItemStack icon;
    @NotNull
    private final ResourceLocation frameTexture;
    private final float progress;
    private final int progressColor;
    @NotNull
    private final UUID uuid;
    @NotNull
    private final Behaviour behaviour;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("toast");

    public ToastPacket(@NotNull Component title, @NotNull Component description, @NotNull ItemStack icon, @NotNull ResourceLocation frameTexture, float progress2, int progressColor, @NotNull UUID uuid2, @NotNull Behaviour behaviour) {
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter((Object)description, (String)"description");
        Intrinsics.checkNotNullParameter((Object)icon, (String)"icon");
        Intrinsics.checkNotNullParameter((Object)frameTexture, (String)"frameTexture");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        Intrinsics.checkNotNullParameter((Object)((Object)behaviour), (String)"behaviour");
        this.title = title;
        this.description = description;
        this.icon = icon;
        this.frameTexture = frameTexture;
        this.progress = progress2;
        this.progressColor = progressColor;
        this.uuid = uuid2;
        this.behaviour = behaviour;
        this.id = ID;
    }

    @NotNull
    public final Component getTitle() {
        return this.title;
    }

    @NotNull
    public final Component getDescription() {
        return this.description;
    }

    @NotNull
    public final ItemStack getIcon() {
        return this.icon;
    }

    @NotNull
    public final ResourceLocation getFrameTexture() {
        return this.frameTexture;
    }

    public final float getProgress() {
        return this.progress;
    }

    public final int getProgressColor() {
        return this.progressColor;
    }

    @NotNull
    public final UUID getUuid() {
        return this.uuid;
    }

    @NotNull
    public final Behaviour getBehaviour() {
        return this.behaviour;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130083_(this.title);
        buffer.m_130083_(this.description);
        buffer.m_130055_(this.icon);
        buffer.m_130085_(this.frameTexture);
        buffer.writeFloat(this.progress);
        buffer.writeInt(this.progressColor);
        buffer.m_130077_(this.uuid);
        buffer.m_130068_((Enum)this.behaviour);
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

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0002\u0010\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket$Behaviour;", "", "<init>", "(Ljava/lang/String;I)V", "SHOW_OR_UPDATE", "HIDE", "common"})
    public static final class Behaviour
    extends Enum<Behaviour> {
        public static final /* enum */ Behaviour SHOW_OR_UPDATE = new Behaviour();
        public static final /* enum */ Behaviour HIDE = new Behaviour();
        private static final /* synthetic */ Behaviour[] $VALUES;

        public static Behaviour[] values() {
            return (Behaviour[])$VALUES.clone();
        }

        public static Behaviour valueOf(String value2) {
            return Enum.valueOf(Behaviour.class, value2);
        }

        static {
            $VALUES = behaviourArray = new Behaviour[]{Behaviour.SHOW_OR_UPDATE, Behaviour.HIDE};
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/toast/ToastPacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final ToastPacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            Component component = buffer.m_130238_();
            Intrinsics.checkNotNullExpressionValue((Object)component, (String)"buffer.readText()");
            Component component2 = buffer.m_130238_();
            Intrinsics.checkNotNullExpressionValue((Object)component2, (String)"buffer.readText()");
            ItemStack itemStack = buffer.m_130267_();
            Intrinsics.checkNotNullExpressionValue((Object)itemStack, (String)"buffer.readItemStack()");
            ResourceLocation resourceLocation = buffer.m_130281_();
            Intrinsics.checkNotNullExpressionValue((Object)resourceLocation, (String)"buffer.readIdentifier()");
            float f = buffer.readFloat();
            int n = buffer.readInt();
            UUID uUID = buffer.m_130259_();
            Intrinsics.checkNotNullExpressionValue((Object)uUID, (String)"buffer.readUuid()");
            Enum enum_ = buffer.m_130066_(Behaviour.class);
            Intrinsics.checkNotNullExpressionValue((Object)enum_, (String)"buffer.readEnumConstant(Behaviour::class.java)");
            return new ToastPacket(component, component2, itemStack, resourceLocation, f, n, uUID, (Behaviour)enum_);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

