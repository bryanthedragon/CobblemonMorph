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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001:\u0002\u0006\u0007R\u0014\u0010\u0005\u001a\u00020\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/platform/events/ServerTickEvent;", "", "Lnet/minecraft/server/MinecraftServer;", "getServer", "()Lnet/minecraft/server/MinecraftServer;", "server", "Post", "Pre", "common"})
public interface ServerTickEvent {
    @NotNull
    public MinecraftServer getServer();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0013\u001a\u0004\b\u0014\u0010\u0004\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/platform/events/ServerTickEvent$Post;", "Lcom/cobblemon/mod/common/platform/events/ServerTickEvent;", "Lnet/minecraft/server/MinecraftServer;", "component1", "()Lnet/minecraft/server/MinecraftServer;", "server", "copy", "(Lnet/minecraft/server/MinecraftServer;)Lcom/cobblemon/mod/common/platform/events/ServerTickEvent$Post;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/server/MinecraftServer;", "getServer", "<init>", "(Lnet/minecraft/server/MinecraftServer;)V", "common"})
    public static final class Post
    implements ServerTickEvent {
        @NotNull
        private final MinecraftServer server;

        public Post(@NotNull MinecraftServer server) {
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
        public final Post copy(@NotNull MinecraftServer server) {
            Intrinsics.checkNotNullParameter((Object)server, (String)"server");
            return new Post(server);
        }

        public static /* synthetic */ Post copy$default(Post post2, MinecraftServer minecraftServer, int n, Object object) {
            if ((n & 1) != 0) {
                minecraftServer = post2.server;
            }
            return post2.copy(minecraftServer);
        }

        @NotNull
        public String toString() {
            return "Post(server=" + this.server + ")";
        }

        public int hashCode() {
            return this.server.hashCode();
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Post)) {
                return false;
            }
            Post post2 = (Post)other;
            return Intrinsics.areEqual((Object)this.server, (Object)post2.server);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0013\u001a\u0004\b\u0014\u0010\u0004\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/platform/events/ServerTickEvent$Pre;", "Lcom/cobblemon/mod/common/platform/events/ServerTickEvent;", "Lnet/minecraft/server/MinecraftServer;", "component1", "()Lnet/minecraft/server/MinecraftServer;", "server", "copy", "(Lnet/minecraft/server/MinecraftServer;)Lcom/cobblemon/mod/common/platform/events/ServerTickEvent$Pre;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/server/MinecraftServer;", "getServer", "<init>", "(Lnet/minecraft/server/MinecraftServer;)V", "common"})
    public static final class Pre
    implements ServerTickEvent {
        @NotNull
        private final MinecraftServer server;

        public Pre(@NotNull MinecraftServer server) {
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
        public final Pre copy(@NotNull MinecraftServer server) {
            Intrinsics.checkNotNullParameter((Object)server, (String)"server");
            return new Pre(server);
        }

        public static /* synthetic */ Pre copy$default(Pre pre, MinecraftServer minecraftServer, int n, Object object) {
            if ((n & 1) != 0) {
                minecraftServer = pre.server;
            }
            return pre.copy(minecraftServer);
        }

        @NotNull
        public String toString() {
            return "Pre(server=" + this.server + ")";
        }

        public int hashCode() {
            return this.server.hashCode();
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Pre)) {
                return false;
            }
            Pre pre = (Pre)other;
            return Intrinsics.areEqual((Object)this.server, (Object)pre.server);
        }
    }
}

