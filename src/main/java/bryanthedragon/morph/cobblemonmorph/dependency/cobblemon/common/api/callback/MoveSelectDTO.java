/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Move;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001b\b\u0017\u0012\u0006\u0010\u001b\u001a\u00020\u001a\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u001c\u0010\u001dB\u001b\b\u0017\u0012\u0006\u0010\u001b\u001a\u00020\u001e\u0012\b\b\u0002\u0010\b\u001a\u00020\u0007\u00a2\u0006\u0004\b\u001c\u0010\u001fB\u0011\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u001c\u0010\u0006B+\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u0012\u0006\u0010\b\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0014\u001a\u00020\u0013\u0012\b\b\u0002\u0010\u0018\u001a\u00020\u0013\u00a2\u0006\u0004\b\u001c\u0010 J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\"\u0010\b\u001a\u00020\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u0017\u0010\u000f\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0015\u001a\u0004\b\u0019\u0010\u0017\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/api/callback/MoveSelectDTO;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "writeToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "enabled", "Z", "getEnabled", "()Z", "setEnabled", "(Z)V", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "moveTemplate", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getMoveTemplate", "()Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "", "pp", "I", "getPp", "()I", "ppMax", "getPpMax", "Lcom/cobblemon/mod/common/api/moves/Move;", "move", "<init>", "(Lcom/cobblemon/mod/common/api/moves/Move;Z)V", "Lcom/cobblemon/mod/common/battles/InBattleMove;", "(Lcom/cobblemon/mod/common/battles/InBattleMove;Z)V", "(Lcom/cobblemon/mod/common/api/moves/MoveTemplate;ZII)V", "common"})
public final class MoveSelectDTO {
    @NotNull
    private final MoveTemplate moveTemplate;
    private boolean enabled;
    private final int pp;
    private final int ppMax;

    public MoveSelectDTO(@NotNull MoveTemplate moveTemplate, boolean enabled, int pp, int ppMax) {
        Intrinsics.checkNotNullParameter((Object)moveTemplate, (String)"moveTemplate");
        this.moveTemplate = moveTemplate;
        this.enabled = enabled;
        this.pp = pp;
        this.ppMax = ppMax;
    }

    public /* synthetic */ MoveSelectDTO(MoveTemplate moveTemplate, boolean bl, int n, int n2, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 4) != 0) {
            n = -1;
        }
        if ((n3 & 8) != 0) {
            n2 = -1;
        }
        this(moveTemplate, bl, n, n2);
    }

    @NotNull
    public final MoveTemplate getMoveTemplate() {
        return this.moveTemplate;
    }

    public final boolean getEnabled() {
        return this.enabled;
    }

    public final void setEnabled(boolean bl) {
        this.enabled = bl;
    }

    public final int getPp() {
        return this.pp;
    }

    public final int getPpMax() {
        return this.ppMax;
    }

    @JvmOverloads
    public MoveSelectDTO(@NotNull Move move, boolean enabled) {
        Intrinsics.checkNotNullParameter((Object)move, (String)"move");
        this(move.getTemplate(), enabled, move.getCurrentPp(), move.getMaxPp());
    }

    public /* synthetic */ MoveSelectDTO(Move move, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            bl = true;
        }
        this(move, bl);
    }

    @JvmOverloads
    public MoveSelectDTO(@NotNull InBattleMove move, boolean enabled) {
        Intrinsics.checkNotNullParameter((Object)move, (String)"move");
        this(Moves.INSTANCE.getByNameOrDummy(move.getMove()), enabled, move.getPp(), move.getMaxpp());
    }

    public /* synthetic */ MoveSelectDTO(InBattleMove inBattleMove, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            bl = true;
        }
        this(inBattleMove, bl);
    }

    public MoveSelectDTO(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        String string = buffer.m_130277_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
        this(Moves.INSTANCE.getByNameOrDummy(string), buffer.readBoolean(), NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.BYTE), NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.BYTE));
    }

    public final void writeToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(this.moveTemplate.getName());
        buffer.writeBoolean(this.enabled);
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.BYTE, this.pp);
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.BYTE, this.ppMax);
    }

    @JvmOverloads
    public MoveSelectDTO(@NotNull Move move) {
        Intrinsics.checkNotNullParameter((Object)move, (String)"move");
        this(move, false, 2, null);
    }

    @JvmOverloads
    public MoveSelectDTO(@NotNull InBattleMove move) {
        Intrinsics.checkNotNullParameter((Object)move, (String)"move");
        this(move, false, 2, null);
    }
}

