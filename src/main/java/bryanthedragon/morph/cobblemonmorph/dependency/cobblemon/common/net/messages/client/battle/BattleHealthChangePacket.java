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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
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
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u0000 \u001c2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u001cB#\u0012\u0006\u0010\u0016\u001a\u00020\u0015\u0012\u0006\u0010\r\u001a\u00020\f\u0012\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u001a\u0010\b\u001a\u00020\u00078\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000bR\u0017\u0010\r\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u0019\u0010\u0011\u001a\u0004\u0018\u00010\f8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0016\u001a\u00020\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/battle/BattleHealthChangePacket;", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "encode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "", "newHealth", "F", "getNewHealth", "()F", "newMaxHealth", "Ljava/lang/Float;", "getNewMaxHealth", "()Ljava/lang/Float;", "", "pnx", "Ljava/lang/String;", "getPnx", "()Ljava/lang/String;", "<init>", "(Ljava/lang/String;FLjava/lang/Float;)V", "Companion", "common"})
public final class BattleHealthChangePacket
implements NetworkPacket<BattleHealthChangePacket> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final String pnx;
    private final float newHealth;
    @Nullable
    private final Float newMaxHealth;
    @NotNull
    private final ResourceLocation id;
    @NotNull
    private static final ResourceLocation ID = MiscUtilsKt.cobblemonResource("battle_health_change");

    public BattleHealthChangePacket(@NotNull String pnx, float newHealth, @Nullable Float newMaxHealth) {
        Intrinsics.checkNotNullParameter((Object)pnx, (String)"pnx");
        this.pnx = pnx;
        this.newHealth = newHealth;
        this.newMaxHealth = newMaxHealth;
        this.id = ID;
    }

    public /* synthetic */ BattleHealthChangePacket(String string, float f, Float f2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 4) != 0) {
            f2 = null;
        }
        this(string, f, f2);
    }

    @NotNull
    public final String getPnx() {
        return this.pnx;
    }

    public final float getNewHealth() {
        return this.newHealth;
    }

    @Nullable
    public final Float getNewMaxHealth() {
        return this.newMaxHealth;
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(this.pnx);
        buffer.writeFloat(this.newHealth);
        buffer.m_236821_((Object)this.newMaxHealth, (arg_0, arg_1) -> BattleHealthChangePacket.encode$lambda$0(buffer, arg_0, arg_1));
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

    private static final void encode$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, Float newMaxHealth) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        Intrinsics.checkNotNullExpressionValue((Object)newMaxHealth, (String)"newMaxHealth");
        $buffer.writeFloat(newMaxHealth.floatValue());
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\b\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/net/messages/client/battle/BattleHealthChangePacket$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/net/messages/client/battle/BattleHealthChangePacket;", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/net/messages/client/battle/BattleHealthChangePacket;", "Lnet/minecraft/resources/ResourceLocation;", "ID", "Lnet/minecraft/resources/ResourceLocation;", "getID", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getID() {
            return ID;
        }

        @NotNull
        public final BattleHealthChangePacket decode(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            String string = buffer.m_130277_();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
            return new BattleHealthChangePacket(string, buffer.readFloat(), (Float)buffer.m_236868_(arg_0 -> Companion.decode$lambda$0(buffer, arg_0)));
        }

        private static final Float decode$lambda$0(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
            Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
            return Float.valueOf($buffer.readFloat());
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

