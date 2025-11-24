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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.party;

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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u000f\u0012\u0006\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001a\u0010\u0006\u001a\u00020\u00002\b\b\u0002\u0010\u0005\u001a\u00020\u0002H\u00c6\u0001\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001a\u0010\u000b\u001a\u00020\n2\b\u0010\t\u001a\u0004\u0018\u00010\bH\u00d6\u0003\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0010\u0010\r\u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b\r\u0010\u0004J\u0010\u0010\u000f\u001a\u00020\u000eH\u00d6\u0001\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0005\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0011\u001a\u0004\b\u0012\u0010\u0004\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "", "component1", "()I", "slot", "copy", "(I)Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "", "other", "", "equals", "(Ljava/lang/Object;)Z", "hashCode", "", "toString", "()Ljava/lang/String;", "I", "getSlot", "<init>", "(I)V", "Companion", "common"})
public final class PartyPosition
implements StorePosition {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int slot;

    public PartyPosition(int slot) {
        this.slot = slot;
    }

    public final int getSlot() {
        return this.slot;
    }

    public final int component1() {
        return this.slot;
    }

    @NotNull
    public final PartyPosition copy(int slot) {
        return new PartyPosition(slot);
    }

    public static /* synthetic */ PartyPosition copy$default(PartyPosition partyPosition, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            n = partyPosition.slot;
        }
        return partyPosition.copy(n);
    }

    @NotNull
    public String toString() {
        return "PartyPosition(slot=" + this.slot + ")";
    }

    public int hashCode() {
        return Integer.hashCode(this.slot);
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PartyPosition)) {
            return false;
        }
        PartyPosition partyPosition = (PartyPosition)other;
        return this.slot == partyPosition.slot;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0011\u0010\u0004\u001a\u00020\u0003*\u00020\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0019\u0010\b\u001a\u00020\u0007*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/storage/party/PartyPosition$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "readPartyPosition", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;", "position", "", "writePartyPosition", "(Lnet/minecraft/network/FriendlyByteBuf;Lcom/cobblemon/mod/common/api/storage/party/PartyPosition;)V", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final void writePartyPosition(@NotNull FriendlyByteBuf $this$writePartyPosition, @NotNull PartyPosition position) {
            Intrinsics.checkNotNullParameter((Object)$this$writePartyPosition, (String)"<this>");
            Intrinsics.checkNotNullParameter((Object)position, (String)"position");
            NetExtensionsKt.writeSizedInt((ByteBuf)$this$writePartyPosition, IntSize.U_BYTE, position.getSlot());
        }

        @NotNull
        public final PartyPosition readPartyPosition(@NotNull FriendlyByteBuf $this$readPartyPosition) {
            Intrinsics.checkNotNullParameter((Object)$this$readPartyPosition, (String)"<this>");
            return new PartyPosition(NetExtensionsKt.readSizedInt((ByteBuf)$this$readPartyPosition, IntSize.U_BYTE));
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

