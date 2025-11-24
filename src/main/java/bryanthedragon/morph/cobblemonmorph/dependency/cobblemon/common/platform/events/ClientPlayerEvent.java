/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.player.LocalPlayer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001:\u0002\u0006\u0007R\u0014\u0010\u0005\u001a\u00020\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/platform/events/ClientPlayerEvent;", "", "Lnet/minecraft/client/player/LocalPlayer;", "getPlayer", "()Lnet/minecraft/client/player/LocalPlayer;", "player", "Login", "Logout", "common"})
public interface ClientPlayerEvent {
    @NotNull
    public LocalPlayer getPlayer();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0013\u001a\u0004\b\u0014\u0010\u0004\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/platform/events/ClientPlayerEvent$Login;", "Lcom/cobblemon/mod/common/platform/events/ClientPlayerEvent;", "Lnet/minecraft/client/player/LocalPlayer;", "component1", "()Lnet/minecraft/client/player/LocalPlayer;", "player", "copy", "(Lnet/minecraft/client/player/LocalPlayer;)Lcom/cobblemon/mod/common/platform/events/ClientPlayerEvent$Login;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/client/player/LocalPlayer;", "getPlayer", "<init>", "(Lnet/minecraft/client/player/LocalPlayer;)V", "common"})
    public static final class Login
    implements ClientPlayerEvent {
        @NotNull
        private final LocalPlayer player;

        public Login(@NotNull LocalPlayer player) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            this.player = player;
        }

        @Override
        @NotNull
        public LocalPlayer getPlayer() {
            return this.player;
        }

        @NotNull
        public final LocalPlayer component1() {
            return this.player;
        }

        @NotNull
        public final Login copy(@NotNull LocalPlayer player) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            return new Login(player);
        }

        public static /* synthetic */ Login copy$default(Login login, LocalPlayer localPlayer, int n, Object object) {
            if ((n & 1) != 0) {
                localPlayer = login.player;
            }
            return login.copy(localPlayer);
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

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0013\u001a\u0004\b\u0014\u0010\u0004\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/platform/events/ClientPlayerEvent$Logout;", "Lcom/cobblemon/mod/common/platform/events/ClientPlayerEvent;", "Lnet/minecraft/client/player/LocalPlayer;", "component1", "()Lnet/minecraft/client/player/LocalPlayer;", "player", "copy", "(Lnet/minecraft/client/player/LocalPlayer;)Lcom/cobblemon/mod/common/platform/events/ClientPlayerEvent$Logout;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/client/player/LocalPlayer;", "getPlayer", "<init>", "(Lnet/minecraft/client/player/LocalPlayer;)V", "common"})
    public static final class Logout
    implements ClientPlayerEvent {
        @NotNull
        private final LocalPlayer player;

        public Logout(@NotNull LocalPlayer player) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            this.player = player;
        }

        @Override
        @NotNull
        public LocalPlayer getPlayer() {
            return this.player;
        }

        @NotNull
        public final LocalPlayer component1() {
            return this.player;
        }

        @NotNull
        public final Logout copy(@NotNull LocalPlayer player) {
            Intrinsics.checkNotNullParameter((Object)player, (String)"player");
            return new Logout(player);
        }

        public static /* synthetic */ Logout copy$default(Logout logout, LocalPlayer localPlayer, int n, Object object) {
            if ((n & 1) != 0) {
                localPlayer = logout.player;
            }
            return logout.copy(localPlayer);
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
}

