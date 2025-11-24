/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleGimmickMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001:\u00019B\u0007\u00a2\u0006\u0004\b7\u00108J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0013\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00020\u0007\u00a2\u0006\u0004\b\b\u0010\tJ\r\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\u000b\u0010\fJ\u0015\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0015\u0010\u0011\u001a\u00020\u00042\u0006\u0010\u000e\u001a\u00020\r\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0013\u001a\u0004\u0018\u00010\u0004\u00a2\u0006\u0004\b\u0013\u0010\u0014R\"\u0010\u0015\u001a\u00020\n8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\f\"\u0004\b\u0018\u0010\u0019R\"\u0010\u001a\u001a\u00020\n8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u0016\u001a\u0004\b\u001b\u0010\f\"\u0004\b\u001c\u0010\u0019R$\u0010\u001e\u001a\u0004\u0018\u00010\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\"\u0010$\u001a\u00020\n8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b$\u0010\u0016\u001a\u0004\b%\u0010\f\"\u0004\b&\u0010\u0019R,\u0010(\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010'\u0018\u00010\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010)\u001a\u0004\b*\u0010\t\"\u0004\b+\u0010,R,\u0010-\u001a\f\u0012\u0006\u0012\u0004\u0018\u00010'\u0018\u00010\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b-\u0010)\u001a\u0004\b.\u0010\t\"\u0004\b/\u0010,R(\u00101\u001a\b\u0012\u0004\u0012\u0002000\u00078\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b1\u0010)\u001a\u0004\b2\u0010\t\"\u0004\b3\u0010,R\"\u00104\u001a\u00020\n8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b4\u0010\u0016\u001a\u0004\b5\u0010\f\"\u0004\b6\u0010\u0019\u00a8\u0006:"}, d2={"Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset$Gimmick;", "gimmick", "", "blockGimmick", "(Lcom/cobblemon/mod/common/battles/ShowdownMoveset$Gimmick;)V", "", "getGimmicks", "()Ljava/util/List;", "", "hasActiveGimmick", "()Z", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "setGimmickMapping", "()Lkotlin/Unit;", "canDynamax", "Z", "getCanDynamax", "setCanDynamax", "(Z)V", "canMegaEvo", "getCanMegaEvo", "setCanMegaEvo", "", "canTerastallize", "Ljava/lang/String;", "getCanTerastallize", "()Ljava/lang/String;", "setCanTerastallize", "(Ljava/lang/String;)V", "canUltraBurst", "getCanUltraBurst", "setCanUltraBurst", "Lcom/cobblemon/mod/common/battles/InBattleGimmickMove;", "canZMove", "Ljava/util/List;", "getCanZMove", "setCanZMove", "(Ljava/util/List;)V", "maxMoves", "getMaxMoves", "setMaxMoves", "Lcom/cobblemon/mod/common/battles/InBattleMove;", "moves", "getMoves", "setMoves", "trapped", "getTrapped", "setTrapped", "<init>", "()V", "Gimmick", "common"})
@SourceDebugExtension(value={"SMAP\nShowdownActionRequest.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownMoveset\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,452:1\n1855#2,2:453\n1864#2,3:455\n1855#2,2:458\n1855#2,2:460\n1#3:462\n*S KotlinDebug\n*F\n+ 1 ShowdownActionRequest.kt\ncom/cobblemon/mod/common/battles/ShowdownMoveset\n*L\n286#1:453,2\n352#1:455,3\n291#1:458,2\n297#1:460,2\n*E\n"})
public final class ShowdownMoveset {
    public List<InBattleMove> moves;
    private boolean trapped;
    private boolean canMegaEvo;
    private boolean canUltraBurst;
    @Nullable
    private List<InBattleGimmickMove> canZMove;
    private boolean canDynamax;
    @Nullable
    private List<InBattleGimmickMove> maxMoves;
    @Nullable
    private String canTerastallize;

    @NotNull
    public final List<InBattleMove> getMoves() {
        List<InBattleMove> list = this.moves;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"moves");
        return null;
    }

    public final void setMoves(@NotNull List<InBattleMove> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.moves = list;
    }

    public final boolean getTrapped() {
        return this.trapped;
    }

    public final void setTrapped(boolean bl) {
        this.trapped = bl;
    }

    public final boolean getCanMegaEvo() {
        return this.canMegaEvo;
    }

    public final void setCanMegaEvo(boolean bl) {
        this.canMegaEvo = bl;
    }

    public final boolean getCanUltraBurst() {
        return this.canUltraBurst;
    }

    public final void setCanUltraBurst(boolean bl) {
        this.canUltraBurst = bl;
    }

    @Nullable
    public final List<InBattleGimmickMove> getCanZMove() {
        return this.canZMove;
    }

    public final void setCanZMove(@Nullable List<InBattleGimmickMove> list) {
        this.canZMove = list;
    }

    public final boolean getCanDynamax() {
        return this.canDynamax;
    }

    public final void setCanDynamax(boolean bl) {
        this.canDynamax = bl;
    }

    @Nullable
    public final List<InBattleGimmickMove> getMaxMoves() {
        return this.maxMoves;
    }

    public final void setMaxMoves(@Nullable List<InBattleGimmickMove> list) {
        this.maxMoves = list;
    }

    @Nullable
    public final String getCanTerastallize() {
        return this.canTerastallize;
    }

    public final void setCanTerastallize(@Nullable String string) {
        this.canTerastallize = string;
    }

    public final void saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.getMoves().size());
        Iterable $this$forEach$iv = this.getMoves();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            InBattleMove it = (InBattleMove)element$iv;
            boolean bl = false;
            it.saveToBuffer(buffer);
        }
        buffer.writeBoolean(this.trapped);
        buffer.writeBoolean(this.canMegaEvo);
        buffer.writeBoolean(this.canUltraBurst);
        buffer.m_236821_(this.canZMove, (arg_0, arg_1) -> ShowdownMoveset.saveToBuffer$lambda$3(buffer, arg_0, arg_1));
        buffer.writeBoolean(this.canDynamax);
        buffer.m_236821_(this.maxMoves, (arg_0, arg_1) -> ShowdownMoveset.saveToBuffer$lambda$6(buffer, arg_0, arg_1));
        buffer.m_236821_((Object)this.canTerastallize, (arg_0, arg_1) -> ShowdownMoveset.saveToBuffer$lambda$7(buffer, arg_0, arg_1));
    }

    @NotNull
    public final ShowdownMoveset loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        List moves = new ArrayList();
        int n = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
        int n2 = 0;
        while (n2 < n) {
            int it = n2++;
            boolean bl = false;
            moves.add(InBattleMove.Companion.loadFromBuffer(buffer));
        }
        this.setMoves(moves);
        this.trapped = buffer.readBoolean();
        this.canMegaEvo = buffer.readBoolean();
        this.canUltraBurst = buffer.readBoolean();
        this.canZMove = (List)buffer.m_236868_(arg_0 -> ShowdownMoveset.loadFromBuffer$lambda$11(moves, buffer, arg_0));
        this.canDynamax = buffer.readBoolean();
        this.maxMoves = (List)buffer.m_236868_(arg_0 -> ShowdownMoveset.loadFromBuffer$lambda$14(moves, buffer, arg_0));
        this.canTerastallize = (String)buffer.m_236868_(arg_0 -> ShowdownMoveset.loadFromBuffer$lambda$15(buffer, arg_0));
        this.setGimmickMapping();
        return this;
    }

    public final boolean hasActiveGimmick() {
        return !this.canDynamax && this.maxMoves != null;
    }

    @NotNull
    public final List<Gimmick> getGimmicks() {
        List list;
        if (!this.hasActiveGimmick()) {
            List list2;
            List $this$getGimmicks_u24lambda_u2416 = list2 = CollectionsKt.createListBuilder();
            boolean bl = false;
            if (this.canMegaEvo) {
                $this$getGimmicks_u24lambda_u2416.add(Gimmick.MEGA_EVOLUTION);
            }
            if (this.canUltraBurst) {
                $this$getGimmicks_u24lambda_u2416.add(Gimmick.ULTRA_BURST);
            }
            if (this.canZMove != null) {
                $this$getGimmicks_u24lambda_u2416.add(Gimmick.Z_POWER);
            }
            if (this.canDynamax) {
                $this$getGimmicks_u24lambda_u2416.add(Gimmick.DYNAMAX);
            }
            if (this.canTerastallize != null) {
                $this$getGimmicks_u24lambda_u2416.add(Gimmick.TERASTALLIZATION);
            }
            list = CollectionsKt.toList((Iterable)CollectionsKt.build((List)list2));
        } else {
            list = CollectionsKt.emptyList();
        }
        return list;
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public final Unit setGimmickMapping() {
        Unit unit;
        List<InBattleGimmickMove> list = this.canZMove;
        if (list == null) {
            list = this.maxMoves;
        }
        if (list != null) {
            List<InBattleGimmickMove> gimmickMoves = list;
            boolean bl = false;
            Iterable $this$forEachIndexed$iv = this.getMoves();
            boolean $i$f$forEachIndexed = false;
            int index$iv = 0;
            for (Object item$iv : $this$forEachIndexed$iv) {
                void move;
                int n;
                if ((n = index$iv++) < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                InBattleMove inBattleMove = (InBattleMove)item$iv;
                int index = n;
                boolean bl2 = false;
                move.setGimmickMove(gimmickMoves.get(index));
            }
            unit = Unit.INSTANCE;
        } else {
            unit = null;
        }
        return unit;
    }

    public final void blockGimmick(@NotNull Gimmick gimmick) {
        Intrinsics.checkNotNullParameter((Object)((Object)gimmick), (String)"gimmick");
        switch (WhenMappings.$EnumSwitchMapping$0[gimmick.ordinal()]) {
            case 1: {
                this.canMegaEvo = false;
                break;
            }
            case 2: {
                this.canDynamax = false;
                break;
            }
            case 3: {
                this.canUltraBurst = false;
                break;
            }
            case 4: {
                this.canZMove = null;
                break;
            }
            default: {
                this.canTerastallize = null;
            }
        }
    }

    private static final void saveToBuffer$lambda$3$lambda$2$lambda$1(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, InBattleGimmickMove zmove) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        zmove.saveToBuffer($buffer);
    }

    private static final void saveToBuffer$lambda$3(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, List canZMove) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        Intrinsics.checkNotNullExpressionValue((Object)canZMove, (String)"canZMove");
        Iterable $this$forEach$iv = canZMove;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            InBattleGimmickMove it = (InBattleGimmickMove)element$iv;
            boolean bl = false;
            $buffer.m_236821_((Object)it, (arg_0, arg_1) -> ShowdownMoveset.saveToBuffer$lambda$3$lambda$2$lambda$1($buffer, arg_0, arg_1));
        }
    }

    private static final void saveToBuffer$lambda$6$lambda$5$lambda$4(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, InBattleGimmickMove maxMove) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        maxMove.saveToBuffer($buffer);
    }

    private static final void saveToBuffer$lambda$6(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, List maxMoves) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        Intrinsics.checkNotNullExpressionValue((Object)maxMoves, (String)"maxMoves");
        Iterable $this$forEach$iv = maxMoves;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            InBattleGimmickMove it = (InBattleGimmickMove)element$iv;
            boolean bl = false;
            $buffer.m_236821_((Object)it, (arg_0, arg_1) -> ShowdownMoveset.saveToBuffer$lambda$6$lambda$5$lambda$4($buffer, arg_0, arg_1));
        }
    }

    private static final void saveToBuffer$lambda$7(FriendlyByteBuf $buffer, FriendlyByteBuf friendlyByteBuf, String teraType) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        $buffer.m_130070_(teraType);
    }

    private static final InBattleGimmickMove loadFromBuffer$lambda$11$lambda$10$lambda$9(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return InBattleGimmickMove.Companion.loadFromBuffer($buffer);
    }

    private static final List loadFromBuffer$lambda$11(List $moves, FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$moves, (String)"$moves");
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        List zMoves = new ArrayList();
        int n = $moves.size();
        int n2 = 0;
        while (n2 < n) {
            int it2 = n2++;
            boolean bl = false;
            zMoves.add($buffer.m_236868_(arg_0 -> ShowdownMoveset.loadFromBuffer$lambda$11$lambda$10$lambda$9($buffer, arg_0)));
        }
        return zMoves;
    }

    private static final InBattleGimmickMove loadFromBuffer$lambda$14$lambda$13$lambda$12(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return InBattleGimmickMove.Companion.loadFromBuffer($buffer);
    }

    private static final List loadFromBuffer$lambda$14(List $moves, FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$moves, (String)"$moves");
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        List maxMoves = new ArrayList();
        int n = $moves.size();
        int n2 = 0;
        while (n2 < n) {
            int it2 = n2++;
            boolean bl = false;
            maxMoves.add($buffer.m_236868_(arg_0 -> ShowdownMoveset.loadFromBuffer$lambda$14$lambda$13$lambda$12($buffer, arg_0)));
        }
        return maxMoves;
    }

    private static final String loadFromBuffer$lambda$15(FriendlyByteBuf $buffer, FriendlyByteBuf it) {
        Intrinsics.checkNotNullParameter((Object)$buffer, (String)"$buffer");
        return $buffer.m_130277_();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0010\u000e\n\u0002\b\f\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\tj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/battles/ShowdownMoveset$Gimmick;", "", "", "id", "Ljava/lang/String;", "getId", "()Ljava/lang/String;", "<init>", "(Ljava/lang/String;ILjava/lang/String;)V", "MEGA_EVOLUTION", "ULTRA_BURST", "Z_POWER", "DYNAMAX", "TERASTALLIZATION", "common"})
    public static final class Gimmick
    extends Enum<Gimmick> {
        @NotNull
        private final String id;
        public static final /* enum */ Gimmick MEGA_EVOLUTION = new Gimmick("mega");
        public static final /* enum */ Gimmick ULTRA_BURST = new Gimmick("ultra");
        public static final /* enum */ Gimmick Z_POWER = new Gimmick("zmove");
        public static final /* enum */ Gimmick DYNAMAX = new Gimmick("max");
        public static final /* enum */ Gimmick TERASTALLIZATION = new Gimmick("terastal");
        private static final /* synthetic */ Gimmick[] $VALUES;

        private Gimmick(String id) {
            this.id = id;
        }

        @NotNull
        public final String getId() {
            return this.id;
        }

        public static Gimmick[] values() {
            return (Gimmick[])$VALUES.clone();
        }

        public static Gimmick valueOf(String value2) {
            return Enum.valueOf(Gimmick.class, value2);
        }

        static {
            $VALUES = gimmickArray = new Gimmick[]{Gimmick.MEGA_EVOLUTION, Gimmick.ULTRA_BURST, Gimmick.Z_POWER, Gimmick.DYNAMAX, Gimmick.TERASTALLIZATION};
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[Gimmick.values().length];
            try {
                nArray[Gimmick.MEGA_EVOLUTION.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Gimmick.DYNAMAX.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Gimmick.ULTRA_BURST.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[Gimmick.Z_POWER.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

