/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleType;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleTypes;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0011\b\u0086\b\u0018\u0000 )2\u00020\u0001:\u0001)B5\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u0002\u0012\b\b\u0002\u0010\u000f\u001a\u00020\u0005\u0012\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\b\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u000b\u00a2\u0006\u0004\b'\u0010(J\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0006\u001a\u00020\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00020\bH\u00c6\u0003\u00a2\u0006\u0004\b\t\u0010\nJ\u0010\u0010\f\u001a\u00020\u000bH\u00c6\u0003\u00a2\u0006\u0004\b\f\u0010\rJ>\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u000e\u001a\u00020\u00022\b\b\u0002\u0010\u000f\u001a\u00020\u00052\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\b2\b\b\u0002\u0010\u0011\u001a\u00020\u000bH\u00c6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u001a\u0010\u0016\u001a\u00020\u00152\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0010\u0010\u0018\u001a\u00020\u000bH\u00d6\u0001\u00a2\u0006\u0004\b\u0018\u0010\rJ\u0015\u0010\u001b\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0019\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\r\u0010\u001d\u001a\u00020\u0002\u00a2\u0006\u0004\b\u001d\u0010\u0004J\u0010\u0010\u001e\u001a\u00020\u0002H\u00d6\u0001\u00a2\u0006\u0004\b\u001e\u0010\u0004R\u0017\u0010\u000f\u001a\u00020\u00058\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u001f\u001a\u0004\b \u0010\u0007R\u0017\u0010\u0011\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010!\u001a\u0004\b\"\u0010\rR\u0017\u0010\u000e\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010#\u001a\u0004\b$\u0010\u0004R\u001d\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010%\u001a\u0004\b&\u0010\n\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/battles/BattleFormat;", "", "", "component1", "()Ljava/lang/String;", "Lcom/cobblemon/mod/common/battles/BattleType;", "component2", "()Lcom/cobblemon/mod/common/battles/BattleType;", "", "component3", "()Ljava/util/Set;", "", "component4", "()I", "mod", "battleType", "ruleSet", "gen", "copy", "(Ljava/lang/String;Lcom/cobblemon/mod/common/battles/BattleType;Ljava/util/Set;I)Lcom/cobblemon/mod/common/battles/BattleFormat;", "other", "", "equals", "(Ljava/lang/Object;)Z", "hashCode", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lnet/minecraft/network/FriendlyByteBuf;", "toFormatJSON", "toString", "Lcom/cobblemon/mod/common/battles/BattleType;", "getBattleType", "I", "getGen", "Ljava/lang/String;", "getMod", "Ljava/util/Set;", "getRuleSet", "<init>", "(Ljava/lang/String;Lcom/cobblemon/mod/common/battles/BattleType;Ljava/util/Set;I)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nBattleFormat.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleFormat.kt\ncom/cobblemon/mod/common/battles/BattleFormat\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,76:1\n1855#2,2:77\n*S KotlinDebug\n*F\n+ 1 BattleFormat.kt\ncom/cobblemon/mod/common/battles/BattleFormat\n*L\n61#1:77,2\n*E\n"})
public final class BattleFormat {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final String mod;
    @NotNull
    private final BattleType battleType;
    @NotNull
    private final Set<String> ruleSet;
    private final int gen;
    @NotNull
    private static final BattleFormat GEN_9_SINGLES;
    @NotNull
    private static final BattleFormat GEN_9_DOUBLES;
    @NotNull
    private static final BattleFormat GEN_9_MULTI;

    public BattleFormat(@NotNull String mod, @NotNull BattleType battleType, @NotNull Set<String> ruleSet, int gen) {
        Intrinsics.checkNotNullParameter((Object)mod, (String)"mod");
        Intrinsics.checkNotNullParameter((Object)battleType, (String)"battleType");
        Intrinsics.checkNotNullParameter(ruleSet, (String)"ruleSet");
        this.mod = mod;
        this.battleType = battleType;
        this.ruleSet = ruleSet;
        this.gen = gen;
    }

    public /* synthetic */ BattleFormat(String string, BattleType battleType, Set set2, int n, int n2, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n2 & 1) != 0) {
            string = "cobblemon";
        }
        if ((n2 & 2) != 0) {
            battleType = BattleTypes.INSTANCE.getSINGLES();
        }
        if ((n2 & 4) != 0) {
            set2 = SetsKt.emptySet();
        }
        if ((n2 & 8) != 0) {
            n = 9;
        }
        this(string, battleType, set2, n);
    }

    @NotNull
    public final String getMod() {
        return this.mod;
    }

    @NotNull
    public final BattleType getBattleType() {
        return this.battleType;
    }

    @NotNull
    public final Set<String> getRuleSet() {
        return this.ruleSet;
    }

    public final int getGen() {
        return this.gen;
    }

    @NotNull
    public final FriendlyByteBuf saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.m_130070_(this.mod);
        this.battleType.saveToBuffer(buffer);
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.ruleSet.size());
        Iterable $this$forEach$iv = this.ruleSet;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String p0 = (String)element$iv;
            boolean bl = false;
            buffer.m_130070_(p0);
        }
        return buffer;
    }

    @NotNull
    public final String toFormatJSON() {
        return StringsKt.replace$default((String)StringsKt.trimIndent((String)("\n            {\n                \"mod\": \"" + this.mod + "\",\n                \"gameType\": \"" + this.battleType.getName() + "\",\n                \"gen\": " + this.gen + ",\n                \"ruleset\": [" + CollectionsKt.joinToString$default((Iterable)this.ruleSet, null, null, null, (int)0, null, (Function1)toFormatJSON.1.INSTANCE, (int)31, null) + "],\n                \"effectType\": \"Format\"\n            }\n        ")), (String)"\n", (String)"", (boolean)false, (int)4, null);
    }

    @NotNull
    public final String component1() {
        return this.mod;
    }

    @NotNull
    public final BattleType component2() {
        return this.battleType;
    }

    @NotNull
    public final Set<String> component3() {
        return this.ruleSet;
    }

    public final int component4() {
        return this.gen;
    }

    @NotNull
    public final BattleFormat copy(@NotNull String mod, @NotNull BattleType battleType, @NotNull Set<String> ruleSet, int gen) {
        Intrinsics.checkNotNullParameter((Object)mod, (String)"mod");
        Intrinsics.checkNotNullParameter((Object)battleType, (String)"battleType");
        Intrinsics.checkNotNullParameter(ruleSet, (String)"ruleSet");
        return new BattleFormat(mod, battleType, ruleSet, gen);
    }

    public static /* synthetic */ BattleFormat copy$default(BattleFormat battleFormat, String string, BattleType battleType, Set set2, int n, int n2, Object object) {
        if ((n2 & 1) != 0) {
            string = battleFormat.mod;
        }
        if ((n2 & 2) != 0) {
            battleType = battleFormat.battleType;
        }
        if ((n2 & 4) != 0) {
            set2 = battleFormat.ruleSet;
        }
        if ((n2 & 8) != 0) {
            n = battleFormat.gen;
        }
        return battleFormat.copy(string, battleType, set2, n);
    }

    @NotNull
    public String toString() {
        return "BattleFormat(mod=" + this.mod + ", battleType=" + this.battleType + ", ruleSet=" + this.ruleSet + ", gen=" + this.gen + ")";
    }

    public int hashCode() {
        int result = this.mod.hashCode();
        result = result * 31 + this.battleType.hashCode();
        result = result * 31 + ((Object)this.ruleSet).hashCode();
        result = result * 31 + Integer.hashCode(this.gen);
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof BattleFormat)) {
            return false;
        }
        BattleFormat battleFormat = (BattleFormat)other;
        if (!Intrinsics.areEqual((Object)this.mod, (Object)battleFormat.mod)) {
            return false;
        }
        if (!Intrinsics.areEqual((Object)this.battleType, (Object)battleFormat.battleType)) {
            return false;
        }
        if (!Intrinsics.areEqual(this.ruleSet, battleFormat.ruleSet)) {
            return false;
        }
        return this.gen == battleFormat.gen;
    }

    public BattleFormat() {
        this(null, null, null, 0, 15, null);
    }

    static {
        Object[] objectArray = new String[]{"Obtainable", "+Past", "+Unobtainable"};
        GEN_9_SINGLES = new BattleFormat(null, BattleTypes.INSTANCE.getSINGLES(), SetsKt.setOf((Object[])objectArray), 0, 9, null);
        GEN_9_DOUBLES = new BattleFormat(null, BattleTypes.INSTANCE.getDOUBLES(), SetsKt.setOf((Object)"Obtainable"), 0, 9, null);
        GEN_9_MULTI = new BattleFormat(null, BattleTypes.INSTANCE.getMULTI(), SetsKt.setOf((Object)"Obtainable"), 0, 9, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\t\u0010\nR\u0017\u0010\u000b\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\f\u0010\nR\u0017\u0010\r\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000e\u0010\n\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/battles/BattleFormat$Companion;", "", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "Lcom/cobblemon/mod/common/battles/BattleFormat;", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/battles/BattleFormat;", "GEN_9_DOUBLES", "Lcom/cobblemon/mod/common/battles/BattleFormat;", "getGEN_9_DOUBLES", "()Lcom/cobblemon/mod/common/battles/BattleFormat;", "GEN_9_MULTI", "getGEN_9_MULTI", "GEN_9_SINGLES", "getGEN_9_SINGLES", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nBattleFormat.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleFormat.kt\ncom/cobblemon/mod/common/battles/BattleFormat$Companion\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,76:1\n1#2:77\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final BattleFormat getGEN_9_SINGLES() {
            return GEN_9_SINGLES;
        }

        @NotNull
        public final BattleFormat getGEN_9_DOUBLES() {
            return GEN_9_DOUBLES;
        }

        @NotNull
        public final BattleFormat getGEN_9_MULTI() {
            return GEN_9_MULTI;
        }

        @NotNull
        public final BattleFormat loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
            Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
            String mod = buffer.m_130277_();
            BattleType battleType = BattleType.Companion.loadFromBuffer(buffer);
            Set ruleSet = new LinkedHashSet();
            int n = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
            int n2 = 0;
            while (n2 < n) {
                int it = n2++;
                boolean bl = false;
                String string = buffer.m_130277_();
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
                ruleSet.add(string);
            }
            Intrinsics.checkNotNullExpressionValue((Object)mod, (String)"mod");
            return new BattleFormat(mod, battleType, ruleSet, 0, 8, null);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

