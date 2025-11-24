/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Direction
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.item.ItemStack
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.events.Cancelable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\t\bf\u0018\u00002\u00020\u0001:\u0005\u0006\u0007\b\t\nR\u0014\u0010\u0005\u001a\u00020\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent;", "", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "()Lnet/minecraft/server/level/ServerPlayer;", "player", "Death", "Login", "Logout", "RightClickBlock", "RightClickEntity", "common"})
public interface ServerPlayerEvent {
    @NotNull
    public ServerPlayer getPlayer();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0004\u001a\u00020\u0003H\u00c6\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u001a\u0010\u0007\u001a\u00020\u00002\b\b\u0002\u0010\u0006\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001a\u0010\f\u001a\u00020\u000b2\b\u0010\n\u001a\u0004\u0018\u00010\tH\u00d6\u0003\u00a2\u0006\u0004\b\f\u0010\rJ\u0010\u0010\u000f\u001a\u00020\u000eH\u00d6\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0006\u001a\u00020\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0014\u001a\u0004\b\u0015\u0010\u0005\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$Death;", "Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lnet/minecraft/server/level/ServerPlayer;", "component1", "()Lnet/minecraft/server/level/ServerPlayer;", "player", "copy", "(Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$Death;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;)V", "common"})
    public static final class Death
    extends Cancelable
    implements ServerPlayerEvent {
        @NotNull
        private final ServerPlayer player;

        public Death(@NotNull ServerPlayer player) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            this.player = player;
        }

        @Override
        @NotNull
        public ServerPlayer getPlayer() {
            return this.player;
        }

        @NotNull
        public final ServerPlayer component1() {
            return this.player;
        }

        @NotNull
        public final Death copy(@NotNull ServerPlayer player) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            return new Death(player);
        }

        public static /* synthetic */ Death copy$default(Death death, ServerPlayer serverPlayer, int n, Object object) {
            if ((n & 1) != 0) {
                serverPlayer = death.player;
            }
            return death.copy(serverPlayer);
        }

        @NotNull
        public String toString() {
            return "Death(player=" + this.player + ")";
        }

        public int hashCode() {
            return this.player.hashCode();
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Death)) {
                return false;
            }
            Death death = (Death)other;
            return Intrinsics.areEqual((Object)this.player, (Object)death.player);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0013\u001a\u0004\b\u0014\u0010\u0004\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$Login;", "Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent;", "Lnet/minecraft/server/level/ServerPlayer;", "component1", "()Lnet/minecraft/server/level/ServerPlayer;", "player", "copy", "(Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$Login;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;)V", "common"})
    public static final class Login
    implements ServerPlayerEvent {
        @NotNull
        private final ServerPlayer player;

        public Login(@NotNull ServerPlayer player) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            this.player = player;
        }

        @Override
        @NotNull
        public ServerPlayer getPlayer() {
            return this.player;
        }

        @NotNull
        public final ServerPlayer component1() {
            return this.player;
        }

        @NotNull
        public final Login copy(@NotNull ServerPlayer player) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            return new Login(player);
        }

        public static /* synthetic */ Login copy$default(Login login, ServerPlayer serverPlayer, int n, Object object) {
            if ((n & 1) != 0) {
                serverPlayer = login.player;
            }
            return login.copy(serverPlayer);
        }

        @NotNull
        public String toString() {
            return "Login(player=" + this.player + ")";
        }

        public int hashCode() {
            return this.player.hashCode();
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Login)) {
                return false;
            }
            Login login = (Login)other;
            return Intrinsics.areEqual((Object)this.player, (Object)login.player);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0013\u001a\u0004\b\u0014\u0010\u0004\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$Logout;", "Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent;", "Lnet/minecraft/server/level/ServerPlayer;", "component1", "()Lnet/minecraft/server/level/ServerPlayer;", "player", "copy", "(Lnet/minecraft/server/level/ServerPlayer;)Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$Logout;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;)V", "common"})
    public static final class Logout
    implements ServerPlayerEvent {
        @NotNull
        private final ServerPlayer player;

        public Logout(@NotNull ServerPlayer player) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            this.player = player;
        }

        @Override
        @NotNull
        public ServerPlayer getPlayer() {
            return this.player;
        }

        @NotNull
        public final ServerPlayer component1() {
            return this.player;
        }

        @NotNull
        public final Logout copy(@NotNull ServerPlayer player) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            return new Logout(player);
        }

        public static /* synthetic */ Logout copy$default(Logout logout, ServerPlayer serverPlayer, int n, Object object) {
            if ((n & 1) != 0) {
                serverPlayer = logout.player;
            }
            return logout.copy(serverPlayer);
        }

        @NotNull
        public String toString() {
            return "Logout(player=" + this.player + ")";
        }

        public int hashCode() {
            return this.player.hashCode();
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Logout)) {
                return false;
            }
            Logout logout = (Logout)other;
            return Intrinsics.areEqual((Object)this.player, (Object)logout.player);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B)\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\u0006\u0010\u0010\u001a\u00020\u0006\u0012\u0006\u0010\u0011\u001a\u00020\t\u0012\b\u0010\u0012\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0004\b(\u0010)J\u0010\u0010\u0004\u001a\u00020\u0003H\u00c6\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0007\u001a\u00020\u0006H\u00c6\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010\n\u001a\u00020\tH\u00c6\u0003\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0012\u0010\r\u001a\u0004\u0018\u00010\fH\u00c6\u0003\u00a2\u0006\u0004\b\r\u0010\u000eJ:\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00062\b\b\u0002\u0010\u0011\u001a\u00020\t2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\fH\u00c6\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u001a\u0010\u0018\u001a\u00020\u00172\b\u0010\u0016\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0010\u0010\u001b\u001a\u00020\u001aH\u00d6\u0001\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0010\u0010\u001e\u001a\u00020\u001dH\u00d6\u0001\u00a2\u0006\u0004\b\u001e\u0010\u001fR\u0019\u0010\u0012\u001a\u0004\u0018\u00010\f8\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010 \u001a\u0004\b!\u0010\u000eR\u0017\u0010\u0011\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\"\u001a\u0004\b#\u0010\u000bR\u001a\u0010\u000f\u001a\u00020\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000f\u0010$\u001a\u0004\b%\u0010\u0005R\u0017\u0010\u0010\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010&\u001a\u0004\b'\u0010\b\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$RightClickBlock;", "Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lnet/minecraft/server/level/ServerPlayer;", "component1", "()Lnet/minecraft/server/level/ServerPlayer;", "Lnet/minecraft/core/BlockPos;", "component2", "()Lnet/minecraft/core/BlockPos;", "Lnet/minecraft/world/InteractionHand;", "component3", "()Lnet/minecraft/world/InteractionHand;", "Lnet/minecraft/core/Direction;", "component4", "()Lnet/minecraft/core/Direction;", "player", "pos", "hand", "face", "copy", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/core/Direction;)Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$RightClickBlock;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/core/Direction;", "getFace", "Lnet/minecraft/world/InteractionHand;", "getHand", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "Lnet/minecraft/core/BlockPos;", "getPos", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/core/Direction;)V", "common"})
    public static final class RightClickBlock
    extends Cancelable
    implements ServerPlayerEvent {
        @NotNull
        private final ServerPlayer player;
        @NotNull
        private final BlockPos pos;
        @NotNull
        private final InteractionHand hand;
        @Nullable
        private final Direction face;

        public RightClickBlock(@NotNull ServerPlayer player, @NotNull BlockPos pos, @NotNull InteractionHand hand, @Nullable Direction face) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
            Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
            this.player = player;
            this.pos = pos;
            this.hand = hand;
            this.face = face;
        }

        @Override
        @NotNull
        public ServerPlayer getPlayer() {
            return this.player;
        }

        @NotNull
        public final BlockPos getPos() {
            return this.pos;
        }

        @NotNull
        public final InteractionHand getHand() {
            return this.hand;
        }

        @Nullable
        public final Direction getFace() {
            return this.face;
        }

        @NotNull
        public final ServerPlayer component1() {
            return this.player;
        }

        @NotNull
        public final BlockPos component2() {
            return this.pos;
        }

        @NotNull
        public final InteractionHand component3() {
            return this.hand;
        }

        @Nullable
        public final Direction component4() {
            return this.face;
        }

        @NotNull
        public final RightClickBlock copy(@NotNull ServerPlayer player, @NotNull BlockPos pos, @NotNull InteractionHand hand, @Nullable Direction face) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)pos, (String)"pos");
            Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
            return new RightClickBlock(player, pos, hand, face);
        }

        public static /* synthetic */ RightClickBlock copy$default(RightClickBlock rightClickBlock, ServerPlayer serverPlayer, BlockPos blockPos2, InteractionHand interactionHand, Direction direction, int n, Object object) {
            if ((n & 1) != 0) {
                serverPlayer = rightClickBlock.player;
            }
            if ((n & 2) != 0) {
                blockPos2 = rightClickBlock.pos;
            }
            if ((n & 4) != 0) {
                interactionHand = rightClickBlock.hand;
            }
            if ((n & 8) != 0) {
                direction = rightClickBlock.face;
            }
            return rightClickBlock.copy(serverPlayer, blockPos2, interactionHand, direction);
        }

        @NotNull
        public String toString() {
            return "RightClickBlock(player=" + this.player + ", pos=" + this.pos + ", hand=" + this.hand + ", face=" + this.face + ")";
        }

        public int hashCode() {
            int result = this.player.hashCode();
            result = result * 31 + this.pos.hashCode();
            result = result * 31 + this.hand.hashCode();
            result = result * 31 + (this.face == null ? 0 : this.face.hashCode());
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof RightClickBlock)) {
                return false;
            }
            RightClickBlock rightClickBlock = (RightClickBlock)other;
            if (!Intrinsics.areEqual((Object)this.player, (Object)rightClickBlock.player)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.pos, (Object)rightClickBlock.pos)) {
                return false;
            }
            if (this.hand != rightClickBlock.hand) {
                return false;
            }
            return this.face == rightClickBlock.face;
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B'\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\u0006\u0010\u0010\u001a\u00020\u0006\u0012\u0006\u0010\u0011\u001a\u00020\t\u0012\u0006\u0010\u0012\u001a\u00020\f\u00a2\u0006\u0004\b(\u0010)J\u0010\u0010\u0004\u001a\u00020\u0003H\u00c6\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0010\u0010\u0007\u001a\u00020\u0006H\u00c6\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0010\u0010\n\u001a\u00020\tH\u00c6\u0003\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0010\u0010\r\u001a\u00020\fH\u00c6\u0003\u00a2\u0006\u0004\b\r\u0010\u000eJ8\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u000f\u001a\u00020\u00032\b\b\u0002\u0010\u0010\u001a\u00020\u00062\b\b\u0002\u0010\u0011\u001a\u00020\t2\b\b\u0002\u0010\u0012\u001a\u00020\fH\u00c6\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u001a\u0010\u0018\u001a\u00020\u00172\b\u0010\u0016\u001a\u0004\u0018\u00010\u0015H\u00d6\u0003\u00a2\u0006\u0004\b\u0018\u0010\u0019J\u0010\u0010\u001b\u001a\u00020\u001aH\u00d6\u0001\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0010\u0010\u001e\u001a\u00020\u001dH\u00d6\u0001\u00a2\u0006\u0004\b\u001e\u0010\u001fR\u0017\u0010\u0012\u001a\u00020\f8\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010 \u001a\u0004\b!\u0010\u000eR\u0017\u0010\u0011\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\"\u001a\u0004\b#\u0010\u000bR\u0017\u0010\u0010\u001a\u00020\u00068\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010$\u001a\u0004\b%\u0010\bR\u001a\u0010\u000f\u001a\u00020\u00038\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000f\u0010&\u001a\u0004\b'\u0010\u0005\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$RightClickEntity;", "Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent;", "Lcom/cobblemon/mod/common/api/events/Cancelable;", "Lnet/minecraft/server/level/ServerPlayer;", "component1", "()Lnet/minecraft/server/level/ServerPlayer;", "Lnet/minecraft/world/item/ItemStack;", "component2", "()Lnet/minecraft/world/item/ItemStack;", "Lnet/minecraft/world/InteractionHand;", "component3", "()Lnet/minecraft/world/InteractionHand;", "Lnet/minecraft/world/entity/Entity;", "component4", "()Lnet/minecraft/world/entity/Entity;", "player", "item", "hand", "entity", "copy", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/entity/Entity;)Lcom/cobblemon/mod/common/platform/events/ServerPlayerEvent$RightClickEntity;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/world/entity/Entity;", "getEntity", "Lnet/minecraft/world/InteractionHand;", "getHand", "Lnet/minecraft/world/item/ItemStack;", "getItem", "Lnet/minecraft/server/level/ServerPlayer;", "getPlayer", "<init>", "(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/entity/Entity;)V", "common"})
    public static final class RightClickEntity
    extends Cancelable
    implements ServerPlayerEvent {
        @NotNull
        private final ServerPlayer player;
        @NotNull
        private final ItemStack item;
        @NotNull
        private final InteractionHand hand;
        @NotNull
        private final Entity entity;

        public RightClickEntity(@NotNull ServerPlayer player, @NotNull ItemStack item, @NotNull InteractionHand hand, @NotNull Entity entity2) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)item, (String)"item");
            Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
            Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
            this.player = player;
            this.item = item;
            this.hand = hand;
            this.entity = entity2;
        }

        @Override
        @NotNull
        public ServerPlayer getPlayer() {
            return this.player;
        }

        @NotNull
        public final ItemStack getItem() {
            return this.item;
        }

        @NotNull
        public final InteractionHand getHand() {
            return this.hand;
        }

        @NotNull
        public final Entity getEntity() {
            return this.entity;
        }

        @NotNull
        public final ServerPlayer component1() {
            return this.player;
        }

        @NotNull
        public final ItemStack component2() {
            return this.item;
        }

        @NotNull
        public final InteractionHand component3() {
            return this.hand;
        }

        @NotNull
        public final Entity component4() {
            return this.entity;
        }

        @NotNull
        public final RightClickEntity copy(@NotNull ServerPlayer player, @NotNull ItemStack item, @NotNull InteractionHand hand, @NotNull Entity entity2) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            Intrinsics.checkNotNullParameter((Object)item, (String)"item");
            Intrinsics.checkNotNullParameter((Object)hand, (String)"hand");
            Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
            return new RightClickEntity(player, item, hand, entity2);
        }

        public static /* synthetic */ RightClickEntity copy$default(RightClickEntity rightClickEntity, ServerPlayer serverPlayer, ItemStack itemStack, InteractionHand interactionHand, Entity entity2, int n, Object object) {
            if ((n & 1) != 0) {
                serverPlayer = rightClickEntity.player;
            }
            if ((n & 2) != 0) {
                itemStack = rightClickEntity.item;
            }
            if ((n & 4) != 0) {
                interactionHand = rightClickEntity.hand;
            }
            if ((n & 8) != 0) {
                entity2 = rightClickEntity.entity;
            }
            return rightClickEntity.copy(serverPlayer, itemStack, interactionHand, entity2);
        }

        @NotNull
        public String toString() {
            return "RightClickEntity(player=" + this.player + ", item=" + this.item + ", hand=" + this.hand + ", entity=" + this.entity + ")";
        }

        public int hashCode() {
            int result = this.player.hashCode();
            result = result * 31 + this.item.hashCode();
            result = result * 31 + this.hand.hashCode();
            result = result * 31 + this.entity.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof RightClickEntity)) {
                return false;
            }
            RightClickEntity rightClickEntity = (RightClickEntity)other;
            if (!Intrinsics.areEqual((Object)this.player, (Object)rightClickEntity.player)) {
                return false;
            }
            if (!Intrinsics.areEqual((Object)this.item, (Object)rightClickEntity.item)) {
                return false;
            }
            if (this.hand != rightClickEntity.hand) {
                return false;
            }
            return Intrinsics.areEqual((Object)this.entity, (Object)rightClickEntity.entity);
        }
    }
}

