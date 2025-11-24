/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.pc;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B\u0017\u0012\u0006\u0010\u0006\u001a\u00020\u0002\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0004J$\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0006\u001a\u00020\u00022\b\b\u0002\u0010\u0007\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\b\u0010\tJ\u001a\u0010\r\u001a\u00020\f2\b\u0010\u000b\u001a\u0004\u0018\u00010\nH\u00d6\u0003\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0010\u0010\u000f\u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0004J\u0010\u0010\u0011\u001a\u00020\u0010H\u00d6\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0006\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0013\u001a\u0004\b\u0014\u0010\u0004R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0013\u001a\u0004\b\u0015\u0010\u0004\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "", "component1", "()I", "component2", "box", "slot", "copy", "(II)Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "hashCode", "", "toString", "()Ljava/lang/String;", "I", "getBox", "getSlot", "<init>", "(II)V", "Companion", "common"})
public final class PCPosition
implements StorePosition {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int box;
    private final int slot;

    public PCPosition(int box, int slot) {
        this.box = box;
        this.slot = slot;
    }

    public final int getBox() {
        return this.box;
    }

    public final int getSlot() {
        return this.slot;
    }

    public final int component1() {
        return this.box;
    }

    public final int component2() {
        return this.slot;
    }

    @NotNull
    public final PCPosition copy(int box, int slot) {
        return new PCPosition(box, slot);
    }

    public static /* synthetic */ PCPosition copy$default(PCPosition pCPosition, int n, int n2, int n3, Object object) {
        if ((n3 & 1) != 0) {
            n = pCPosition.box;
        }
        if ((n3 & 2) != 0) {
            n2 = pCPosition.slot;
        }
        return pCPosition.copy(n, n2);
    }

    @NotNull
    public String toString() {
        return "PCPosition(box=" + this.box + ", slot=" + this.slot + ")";
    }

    public int hashCode() {
        int result = Integer.hashCode(this.box);
        result = result * 31 + Integer.hashCode(this.slot);
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PCPosition)) {
            return false;
        }
        PCPosition pCPosition = (PCPosition)other;
        if (this.box != pCPosition.box) {
            return false;
        }
        return this.slot == pCPosition.slot;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0011\u0010\u0004\u001a\u00020\u0003*\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0019\u0010\b\u001a\u00020\u0007*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/storage/pc/PCPosition$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;", "readPCPosition", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;", "position", "", "writePCPosition", "(Lnet/minecraft/network/FriendlyByteBuf;Lcom/cobblemon/mod/common/api/storage/pc/PCPosition;)V", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final void writePCPosition(@NotNull FriendlyByteBuf $this$writePCPosition, @NotNull PCPosition position) {
            Intrinsics.checkNotNullParameter((Object)$this$writePCPosition, (String)"<this>");
            Intrinsics.checkNotNullParameter((Object)position, (String)"position");
            NetExtensionsKt.writeSizedInt((ByteBuf)$this$writePCPosition, IntSize.U_BYTE, position.getBox());
            NetExtensionsKt.writeSizedInt((ByteBuf)$this$writePCPosition, IntSize.U_BYTE, position.getSlot());
        }

        @NotNull
        public final PCPosition readPCPosition(@NotNull FriendlyByteBuf $this$readPCPosition) {
            Intrinsics.checkNotNullParameter((Object)$this$readPCPosition, (String)"<this>");
            return new PCPosition(NetExtensionsKt.readSizedInt((ByteBuf)$this$readPCPosition, IntSize.U_BYTE), NetExtensionsKt.readSizedInt((ByteBuf)$this$readPCPosition, IntSize.U_BYTE));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

