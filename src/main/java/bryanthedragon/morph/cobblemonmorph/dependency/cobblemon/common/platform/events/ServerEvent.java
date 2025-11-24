/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.MinecraftServer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\b\bf\u0018\u00002\u00020\u0001:\u0004\u0006\u0007\b\tR\u0014\u0010\u0005\u001a\u00020\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/platform/events/ServerEvent;", "", "Lnet/minecraft/server/MinecraftServer;", "getServer", "()Lnet/minecraft/server/MinecraftServer;", "server", "Started", "Starting", "Stopped", "Stopping", "common"})
public interface ServerEvent {
    @NotNull
    public MinecraftServer getServer();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0013\u001a\u0004\b\u0014\u0010\u0004\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/platform/events/ServerEvent$Started;", "Lcom/cobblemon/mod/common/platform/events/ServerEvent;", "Lnet/minecraft/server/MinecraftServer;", "component1", "()Lnet/minecraft/server/MinecraftServer;", "server", "copy", "(Lnet/minecraft/server/MinecraftServer;)Lcom/cobblemon/mod/common/platform/events/ServerEvent$Started;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/server/MinecraftServer;", "getServer", "<init>", "(Lnet/minecraft/server/MinecraftServer;)V", "common"})
    public static final class Started
    implements ServerEvent {
        @NotNull
        private final MinecraftServer server;

        public Started(@NotNull MinecraftServer server) {
            Intrinsics.checkNotNullParameter((Object)server, (String)"server");
            this.server = server;
        }

        @Override
        @NotNull
        public MinecraftServer getServer() {
            return this.server;
        }

        @NotNull
        public final MinecraftServer component1() {
            return this.server;
        }

        @NotNull
        public final Started copy(@NotNull MinecraftServer server) {
            Intrinsics.checkNotNullParameter((Object)server, (String)"server");
            return new Started(server);
        }

        public static /* synthetic */ Started copy$default(Started started, MinecraftServer minecraftServer, int n, Object object) {
            if ((n & 1) != 0) {
                minecraftServer = started.server;
            }
            return started.copy(minecraftServer);
        }

        @NotNull
        public String toString() {
            return "Started(server=" + this.server + ")";
        }

        public int hashCode() {
            return this.server.hashCode();
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Started)) {
                return false;
            }
            Started started = (Started)other;
            return Intrinsics.areEqual((Object)this.server, (Object)started.server);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0013\u001a\u0004\b\u0014\u0010\u0004\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/platform/events/ServerEvent$Starting;", "Lcom/cobblemon/mod/common/platform/events/ServerEvent;", "Lnet/minecraft/server/MinecraftServer;", "component1", "()Lnet/minecraft/server/MinecraftServer;", "server", "copy", "(Lnet/minecraft/server/MinecraftServer;)Lcom/cobblemon/mod/common/platform/events/ServerEvent$Starting;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/server/MinecraftServer;", "getServer", "<init>", "(Lnet/minecraft/server/MinecraftServer;)V", "common"})
    public static final class Starting
    implements ServerEvent {
        @NotNull
        private final MinecraftServer server;

        public Starting(@NotNull MinecraftServer server) {
            Intrinsics.checkNotNullParameter((Object)server, (String)"server");
            this.server = server;
        }

        @Override
        @NotNull
        public MinecraftServer getServer() {
            return this.server;
        }

        @NotNull
        public final MinecraftServer component1() {
            return this.server;
        }

        @NotNull
        public final Starting copy(@NotNull MinecraftServer server) {
            Intrinsics.checkNotNullParameter((Object)server, (String)"server");
            return new Starting(server);
        }

        public static /* synthetic */ Starting copy$default(Starting starting, MinecraftServer minecraftServer, int n, Object object) {
            if ((n & 1) != 0) {
                minecraftServer = starting.server;
            }
            return starting.copy(minecraftServer);
        }

        @NotNull
        public String toString() {
            return "Starting(server=" + this.server + ")";
        }

        public int hashCode() {
            return this.server.hashCode();
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Starting)) {
                return false;
            }
            Starting starting = (Starting)other;
            return Intrinsics.areEqual((Object)this.server, (Object)starting.server);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0013\u001a\u0004\b\u0014\u0010\u0004\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/platform/events/ServerEvent$Stopped;", "Lcom/cobblemon/mod/common/platform/events/ServerEvent;", "Lnet/minecraft/server/MinecraftServer;", "component1", "()Lnet/minecraft/server/MinecraftServer;", "server", "copy", "(Lnet/minecraft/server/MinecraftServer;)Lcom/cobblemon/mod/common/platform/events/ServerEvent$Stopped;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/server/MinecraftServer;", "getServer", "<init>", "(Lnet/minecraft/server/MinecraftServer;)V", "common"})
    public static final class Stopped
    implements ServerEvent {
        @NotNull
        private final MinecraftServer server;

        public Stopped(@NotNull MinecraftServer server) {
            Intrinsics.checkNotNullParameter((Object)server, (String)"server");
            this.server = server;
        }

        @Override
        @NotNull
        public MinecraftServer getServer() {
            return this.server;
        }

        @NotNull
        public final MinecraftServer component1() {
            return this.server;
        }

        @NotNull
        public final Stopped copy(@NotNull MinecraftServer server) {
            Intrinsics.checkNotNullParameter((Object)server, (String)"server");
            return new Stopped(server);
        }

        public static /* synthetic */ Stopped copy$default(Stopped stopped, MinecraftServer minecraftServer, int n, Object object) {
            if ((n & 1) != 0) {
                minecraftServer = stopped.server;
            }
            return stopped.copy(minecraftServer);
        }

        @NotNull
        public String toString() {
            return "Stopped(server=" + this.server + ")";
        }

        public int hashCode() {
            return this.server.hashCode();
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Stopped)) {
                return false;
            }
            Stopped stopped = (Stopped)other;
            return Intrinsics.areEqual((Object)this.server, (Object)stopped.server);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0013\u001a\u0004\b\u0014\u0010\u0004\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/platform/events/ServerEvent$Stopping;", "Lcom/cobblemon/mod/common/platform/events/ServerEvent;", "Lnet/minecraft/server/MinecraftServer;", "component1", "()Lnet/minecraft/server/MinecraftServer;", "server", "copy", "(Lnet/minecraft/server/MinecraftServer;)Lcom/cobblemon/mod/common/platform/events/ServerEvent$Stopping;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/server/MinecraftServer;", "getServer", "<init>", "(Lnet/minecraft/server/MinecraftServer;)V", "common"})
    public static final class Stopping
    implements ServerEvent {
        @NotNull
        private final MinecraftServer server;

        public Stopping(@NotNull MinecraftServer server) {
            Intrinsics.checkNotNullParameter((Object)server, (String)"server");
            this.server = server;
        }

        @Override
        @NotNull
        public MinecraftServer getServer() {
            return this.server;
        }

        @NotNull
        public final MinecraftServer component1() {
            return this.server;
        }

        @NotNull
        public final Stopping copy(@NotNull MinecraftServer server) {
            Intrinsics.checkNotNullParameter((Object)server, (String)"server");
            return new Stopping(server);
        }

        public static /* synthetic */ Stopping copy$default(Stopping stopping, MinecraftServer minecraftServer, int n, Object object) {
            if ((n & 1) != 0) {
                minecraftServer = stopping.server;
            }
            return stopping.copy(minecraftServer);
        }

        @NotNull
        public String toString() {
            return "Stopping(server=" + this.server + ")";
        }

        public int hashCode() {
            return this.server.hashCode();
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Stopping)) {
                return false;
            }
            Stopping stopping = (Stopping)other;
            return Intrinsics.areEqual((Object)this.server, (Object)stopping.server);
        }
    }
}

