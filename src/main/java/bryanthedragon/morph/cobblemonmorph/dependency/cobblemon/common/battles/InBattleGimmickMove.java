/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveTarget;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 \u001e2\u00020\u0001:\u0001\u001eB\u0007\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\"\u0010\b\u001a\u00020\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\b\u0010\t\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\"\u0010\u000f\u001a\u00020\u000e8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\"\u0010\u0016\u001a\u00020\u00158\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001b\u00a8\u0006\u001f"}, d2={"Lcom/cobblemon/mod/common/battles/InBattleGimmickMove;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "", "disabled", "Z", "getDisabled", "()Z", "setDisabled", "(Z)V", "", "move", "Ljava/lang/String;", "getMove", "()Ljava/lang/String;", "setMove", "(Ljava/lang/String;)V", "Lcom/cobblemon/mod/common/battles/MoveTarget;", "target", "Lcom/cobblemon/mod/common/battles/MoveTarget;", "getTarget", "()Lcom/cobblemon/mod/common/battles/MoveTarget;", "setTarget", "(Lcom/cobblemon/mod/common/battles/MoveTarget;)V", "<init>", "()V", "Companion", "common"})
public final class InBattleGimmickMove {
    @NotNull
    public static final Companion Companion = new Companion(null);
    public String move;
    @NotNull
    private MoveTarget target = MoveTarget.self;
    private boolean disabled;

    @NotNull
    public final String getMove() {
        String string = this.move;
        if (string != null) {
            return string;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"move");
        return null;
    }

    public final void setMove(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.move = string;
    }

    @NotNull
    public final MoveTarget getTarget() {
        return this.target;
    }

    public final void setTarget(@NotNull MoveTarget moveTarget) {
        Intrinsics.checkNotNullParameter((Object)((Object)moveTarget), (String)"<set-?>");
        this.target = moveTarget;
    }

    public final boolean getDisabled() {
        return this.disabled;
    }

    public final void setDisabled(boolean bl) {
        this.disabled = bl;
    }

    public final void saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(this.getMove());
        buffer.m_130068_((Enum)this.target);
        buffer.writeBoolean(this.disabled);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/battles/InBattleGimmickMove$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/battles/InBattleGimmickMove;", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/battles/InBattleGimmickMove;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final InBattleGimmickMove loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
            InBattleGimmickMove inBattleGimmickMove;
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            InBattleGimmickMove $this$loadFromBuffer_u24lambda_u240 = inBattleGimmickMove = new InBattleGimmickMove();
            boolean bl = false;
            String string = buffer.m_130277_();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
            $this$loadFromBuffer_u24lambda_u240.setMove(string);
            Enum enum_ = buffer.m_130066_(MoveTarget.class);
            Intrinsics.checkNotNullExpressionValue((Object)enum_, (String)"buffer.readEnumConstant(MoveTarget::class.java)");
            $this$loadFromBuffer_u24lambda_u240.setTarget((MoveTarget)enum_);
            $this$loadFromBuffer_u24lambda_u240.setDisabled(buffer.readBoolean());
            return inBattleGimmickMove;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

