/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.util.StringRepresentable
 *  net.minecraft.util.StringRepresentable$EnumCodec
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.command.argument;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import java.util.Collection;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0086\u0001\u0018\u0000 \u00102\b\u0012\u0004\u0012\u00020\u00000\u00012\u00020\u0002:\u0001\u0010B#\b\u0002\u0012\u0018\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u0006\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005R)\u0010\n\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\b0\u00068\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rj\u0002\b\u0011j\u0002\b\u0012j\u0002\b\u0013\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/command/argument/StoreType;", "", "Lnet/minecraft/util/StringRepresentable;", "", "asString", "()Ljava/lang/String;", "Lkotlin/Function1;", "Lnet/minecraft/server/level/ServerPlayer;", "", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "storeFetcher", "Lkotlin/jvm/functions/Function1;", "getStoreFetcher", "()Lkotlin/jvm/functions/Function1;", "<init>", "(Ljava/lang/String;ILkotlin/jvm/functions/Function1;)V", "Companion", "PARTY", "PC", "ALL", "common"})
public final class StoreType
extends Enum<StoreType>
implements StringRepresentable {
    @NotNull
    public static final Companion Companion;
    @NotNull
    private final Function1<ServerPlayer, Collection<Pokemon>> storeFetcher;
    @NotNull
    private static final StringRepresentable.EnumCodec<StoreType> CODEC;
    public static final /* enum */ StoreType PARTY;
    public static final /* enum */ StoreType PC;
    public static final /* enum */ StoreType ALL;
    private static final /* synthetic */ StoreType[] $VALUES;

    private StoreType(Function1<? super ServerPlayer, ? extends Collection<? extends Pokemon>> storeFetcher) {
        this.storeFetcher = storeFetcher;
    }

    @NotNull
    public final Function1<ServerPlayer, Collection<Pokemon>> getStoreFetcher() {
        return this.storeFetcher;
    }

    @NotNull
    public String m_7912_() {
        String string = this.name().toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this as java.lang.String).toLowerCase(Locale.ROOT)");
        return string;
    }

    public static StoreType[] values() {
        return (StoreType[])$VALUES.clone();
    }

    public static StoreType valueOf(String value2) {
        return Enum.valueOf(StoreType.class, value2);
    }

    static {
        PARTY = new StoreType((Function1<? super ServerPlayer, ? extends Collection<? extends Pokemon>>)((Function1)1.INSTANCE));
        PC = new StoreType((Function1<? super ServerPlayer, ? extends Collection<? extends Pokemon>>)((Function1)2.INSTANCE));
        ALL = new StoreType((Function1<? super ServerPlayer, ? extends Collection<? extends Pokemon>>)((Function1)3.INSTANCE));
        $VALUES = storeTypeArray = new StoreType[]{StoreType.PARTY, StoreType.PC, StoreType.ALL};
        Companion = new Companion(null);
        StringRepresentable.EnumCodec enumCodec = StringRepresentable.m_216439_(StoreType::values);
        Intrinsics.checkNotNullExpressionValue((Object)enumCodec, (String)"createCodec(::values)");
        CODEC = enumCodec;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\b\u0010\tR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/command/argument/StoreType$Companion;", "", "Lnet/minecraft/util/StringIdentifiable$Codec;", "Lcom/cobblemon/mod/common/command/argument/StoreType;", "CODEC", "Lnet/minecraft/util/StringRepresentable$EnumCodec;", "getCODEC", "()Lnet/minecraft/util/StringRepresentable$EnumCodec;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final StringRepresentable.EnumCodec<StoreType> getCODEC() {
            return CODEC;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

