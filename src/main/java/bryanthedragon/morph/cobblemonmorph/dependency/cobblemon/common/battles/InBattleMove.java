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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ActiveBattlePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleGimmickMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveTarget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.Targetable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 :2\u00020\u0001:\u0001:B\u0007\u00a2\u0006\u0004\b8\u00109J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u001d\u0010\t\u001a\n\u0012\u0004\u0012\u00020\b\u0018\u00010\u00072\u0006\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\u0004\b\t\u0010\nJ\r\u0010\u000b\u001a\u00020\u0002\u00a2\u0006\u0004\b\u000b\u0010\u0004J\u0015\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\u000f\u0010\u0010R\"\u0010\u0011\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0004\"\u0004\b\u0014\u0010\u0015R$\u0010\u0017\u001a\u0004\u0018\u00010\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\"\u0010\u001e\u001a\u00020\u001d8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\"\u0010%\u001a\u00020$8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010&\u001a\u0004\b'\u0010(\"\u0004\b)\u0010*R\"\u0010+\u001a\u00020\u001d8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b+\u0010\u001f\u001a\u0004\b,\u0010!\"\u0004\b-\u0010#R\"\u0010.\u001a\u00020$8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b.\u0010&\u001a\u0004\b/\u0010(\"\u0004\b0\u0010*R\"\u00102\u001a\u0002018\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b2\u00103\u001a\u0004\b4\u00105\"\u0004\b6\u00107\u00a8\u0006;"}, d2={"Lcom/cobblemon/mod/common/battles/InBattleMove;", "", "", "canBeUsed", "()Z", "Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;", "user", "", "Lcom/cobblemon/mod/common/battles/Targetable;", "getTargets", "(Lcom/cobblemon/mod/common/battles/ActiveBattlePokemon;)Ljava/util/List;", "mustBeUsed", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "disabled", "Z", "getDisabled", "setDisabled", "(Z)V", "Lcom/cobblemon/mod/common/battles/InBattleGimmickMove;", "gimmickMove", "Lcom/cobblemon/mod/common/battles/InBattleGimmickMove;", "getGimmickMove", "()Lcom/cobblemon/mod/common/battles/InBattleGimmickMove;", "setGimmickMove", "(Lcom/cobblemon/mod/common/battles/InBattleGimmickMove;)V", "", "id", "Ljava/lang/String;", "getId", "()Ljava/lang/String;", "setId", "(Ljava/lang/String;)V", "", "maxpp", "I", "getMaxpp", "()I", "setMaxpp", "(I)V", "move", "getMove", "setMove", "pp", "getPp", "setPp", "Lcom/cobblemon/mod/common/battles/MoveTarget;", "target", "Lcom/cobblemon/mod/common/battles/MoveTarget;", "getTarget", "()Lcom/cobblemon/mod/common/battles/MoveTarget;", "setTarget", "(Lcom/cobblemon/mod/common/battles/MoveTarget;)V", "<init>", "()V", "Companion", "common"})
public final class InBattleMove {
    @NotNull
    public static final Companion Companion = new Companion(null);
    public String id;
    public String move;
    private int pp = 100;
    private int maxpp = 100;
    @NotNull
    private MoveTarget target = MoveTarget.self;
    private boolean disabled;
    @Nullable
    private InBattleGimmickMove gimmickMove;

    @NotNull
    public final String getId() {
        String string = this.id;
        if (string != null) {
            return string;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"id");
        return null;
    }

    public final void setId(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.id = string;
    }

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

    public final int getPp() {
        return this.pp;
    }

    public final void setPp(int n) {
        this.pp = n;
    }

    public final int getMaxpp() {
        return this.maxpp;
    }

    public final void setMaxpp(int n) {
        this.maxpp = n;
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

    @Nullable
    public final InBattleGimmickMove getGimmickMove() {
        return this.gimmickMove;
    }

    public final void setGimmickMove(@Nullable InBattleGimmickMove inBattleGimmickMove) {
        this.gimmickMove = inBattleGimmickMove;
    }

    @Nullable
    public final List<Targetable> getTargets(@NotNull ActiveBattlePokemon user) {
        Intrinsics.checkNotNullParameter((Object)user, (String)"user");
        return (List)this.target.getTargetList().invoke((Object)user);
    }

    public final boolean canBeUsed() {
        return this.pp > 0 && !this.disabled || this.mustBeUsed();
    }

    public final boolean mustBeUsed() {
        return this.maxpp == 100 && this.pp == 100 && this.target == MoveTarget.self;
    }

    public final void saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(this.getId());
        buffer.m_130070_(this.getMove());
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.pp);
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.maxpp);
        buffer.m_130068_((Enum)this.target);
        buffer.writeBoolean(this.disabled);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/battles/InBattleMove$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/battles/InBattleMove;", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/battles/InBattleMove;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final InBattleMove loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
            InBattleMove inBattleMove;
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            InBattleMove $this$loadFromBuffer_u24lambda_u240 = inBattleMove = new InBattleMove();
            boolean bl = false;
            String string = buffer.m_130277_();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
            $this$loadFromBuffer_u24lambda_u240.setId(string);
            String string2 = buffer.m_130277_();
            Intrinsics.checkNotNullExpressionValue((Object)string2, (String)"buffer.readString()");
            $this$loadFromBuffer_u24lambda_u240.setMove(string2);
            $this$loadFromBuffer_u24lambda_u240.setPp(NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE));
            $this$loadFromBuffer_u24lambda_u240.setMaxpp(NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE));
            Enum enum_ = buffer.m_130066_(MoveTarget.class);
            Intrinsics.checkNotNullExpressionValue((Object)enum_, (String)"buffer.readEnumConstant(MoveTarget::class.java)");
            $this$loadFromBuffer_u24lambda_u240.setTarget((MoveTarget)enum_);
            $this$loadFromBuffer_u24lambda_u240.setDisabled(buffer.readBoolean());
            return inBattleMove;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

