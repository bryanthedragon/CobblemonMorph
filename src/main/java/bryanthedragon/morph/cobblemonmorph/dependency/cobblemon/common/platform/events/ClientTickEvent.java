/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\bf\u0018\u00002\u00020\u0001:\u0002\u0006\u0007R\u0014\u0010\u0005\u001a\u00020\u00028&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0003\u0010\u0004\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/platform/events/ClientTickEvent;", "", "Lnet/minecraft/client/Minecraft;", "getClient", "()Lnet/minecraft/client/Minecraft;", "client", "Post", "Pre", "common"})
public interface ClientTickEvent {
    @NotNull
    public Minecraft getClient();

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0013\u001a\u0004\b\u0014\u0010\u0004\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/platform/events/ClientTickEvent$Post;", "Lcom/cobblemon/mod/common/platform/events/ClientTickEvent;", "Lnet/minecraft/client/Minecraft;", "component1", "()Lnet/minecraft/client/Minecraft;", "client", "copy", "(Lnet/minecraft/client/Minecraft;)Lcom/cobblemon/mod/common/platform/events/ClientTickEvent$Post;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/client/Minecraft;", "getClient", "<init>", "(Lnet/minecraft/client/Minecraft;)V", "common"})
    public static final class Post
    implements ClientTickEvent {
        @NotNull
        private final Minecraft client;

        public Post(@NotNull Minecraft client) {
            Intrinsics.checkNotNullParameter((Object)client, (String)"client");
            this.client = client;
        }

        @Override
        @NotNull
        public Minecraft getClient() {
            return this.client;
        }

        @NotNull
        public final Minecraft component1() {
            return this.client;
        }

        @NotNull
        public final Post copy(@NotNull Minecraft client) {
            Intrinsics.checkNotNullParameter((Object)client, (String)"client");
            return new Post(client);
        }

        public static /* synthetic */ Post copy$default(Post post2, Minecraft minecraft, int n, Object object) {
            if ((n & 1) != 0) {
                minecraft = post2.client;
            }
            return post2.copy(minecraft);
        }

        @NotNull
        public String toString() {
            return "Post(client=" + this.client + ")";
        }

        public int hashCode() {
            return this.client.hashCode();
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Post)) {
                return false;
            }
            Post post2 = (Post)other;
            return Intrinsics.areEqual((Object)this.client, (Object)post2.client);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0007\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0015\u0010\u0016J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\u000e\u001a\u00020\rH\u00d6\u0001\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0005\u001a\u00020\u00028\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0013\u001a\u0004\b\u0014\u0010\u0004\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/platform/events/ClientTickEvent$Pre;", "Lcom/cobblemon/mod/common/platform/events/ClientTickEvent;", "Lnet/minecraft/client/Minecraft;", "component1", "()Lnet/minecraft/client/Minecraft;", "client", "copy", "(Lnet/minecraft/client/Minecraft;)Lcom/cobblemon/mod/common/platform/events/ClientTickEvent$Pre;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/client/Minecraft;", "getClient", "<init>", "(Lnet/minecraft/client/Minecraft;)V", "common"})
    public static final class Pre
    implements ClientTickEvent {
        @NotNull
        private final Minecraft client;

        public Pre(@NotNull Minecraft client) {
            Intrinsics.checkNotNullParameter((Object)client, (String)"client");
            this.client = client;
        }

        @Override
        @NotNull
        public Minecraft getClient() {
            return this.client;
        }

        @NotNull
        public final Minecraft component1() {
            return this.client;
        }

        @NotNull
        public final Pre copy(@NotNull Minecraft client) {
            Intrinsics.checkNotNullParameter((Object)client, (String)"client");
            return new Pre(client);
        }

        public static /* synthetic */ Pre copy$default(Pre pre, Minecraft minecraft, int n, Object object) {
            if ((n & 1) != 0) {
                minecraft = pre.client;
            }
            return pre.copy(minecraft);
        }

        @NotNull
        public String toString() {
            return "Pre(client=" + this.client + ")";
        }

        public int hashCode() {
            return this.client.hashCode();
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Pre)) {
                return false;
            }
            Pre pre = (Pre)other;
            return Intrinsics.areEqual((Object)this.client, (Object)pre.client);
        }
    }
}

