/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u001d\b\u0002\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002\u00a2\u0006\u0004\b\t\u0010\nR#\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bj\u0002\b\u000bj\u0002\b\fj\u0002\b\rj\u0002\b\u000ej\u0002\b\u000fj\u0002\b\u0010j\u0002\b\u0011\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/battles/ShowdownActionResponseType;", "", "Lkotlin/Function1;", "Lnet/minecraft/network/FriendlyByteBuf;", "Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "loader", "Lkotlin/jvm/functions/Function1;", "getLoader", "()Lkotlin/jvm/functions/Function1;", "<init>", "(Ljava/lang/String;ILkotlin/jvm/functions/Function1;)V", "SWITCH", "MOVE", "DEFAULT", "FORCE_PASS", "PASS", "HEAL_ITEM", "FORFEIT", "common"})
public final class ShowdownActionResponseType
extends Enum<ShowdownActionResponseType> {
    @NotNull
    private final Function1<FriendlyByteBuf, ShowdownActionResponse> loader;
    public static final /* enum */ ShowdownActionResponseType SWITCH = new ShowdownActionResponseType((Function1<? super FriendlyByteBuf, ? extends ShowdownActionResponse>)((Function1)1.INSTANCE));
    public static final /* enum */ ShowdownActionResponseType MOVE = new ShowdownActionResponseType((Function1<? super FriendlyByteBuf, ? extends ShowdownActionResponse>)((Function1)2.INSTANCE));
    public static final /* enum */ ShowdownActionResponseType DEFAULT = new ShowdownActionResponseType((Function1<? super FriendlyByteBuf, ? extends ShowdownActionResponse>)((Function1)3.INSTANCE));
    public static final /* enum */ ShowdownActionResponseType FORCE_PASS = new ShowdownActionResponseType((Function1<? super FriendlyByteBuf, ? extends ShowdownActionResponse>)((Function1)4.INSTANCE));
    public static final /* enum */ ShowdownActionResponseType PASS = new ShowdownActionResponseType((Function1<? super FriendlyByteBuf, ? extends ShowdownActionResponse>)((Function1)5.INSTANCE));
    public static final /* enum */ ShowdownActionResponseType HEAL_ITEM = new ShowdownActionResponseType((Function1<? super FriendlyByteBuf, ? extends ShowdownActionResponse>)((Function1)6.INSTANCE));
    public static final /* enum */ ShowdownActionResponseType FORFEIT = new ShowdownActionResponseType((Function1<? super FriendlyByteBuf, ? extends ShowdownActionResponse>)((Function1)7.INSTANCE));
    private static final /* synthetic */ ShowdownActionResponseType[] $VALUES;

    private ShowdownActionResponseType(Function1<? super FriendlyByteBuf, ? extends ShowdownActionResponse> loader) {
        this.loader = loader;
    }

    @NotNull
    public final Function1<FriendlyByteBuf, ShowdownActionResponse> getLoader() {
        return this.loader;
    }

    public static ShowdownActionResponseType[] values() {
        return (ShowdownActionResponseType[])$VALUES.clone();
    }

    public static ShowdownActionResponseType valueOf(String value2) {
        return Enum.valueOf(ShowdownActionResponseType.class, value2);
    }

    static {
        $VALUES = showdownActionResponseTypeArray = new ShowdownActionResponseType[]{ShowdownActionResponseType.SWITCH, ShowdownActionResponseType.MOVE, ShowdownActionResponseType.DEFAULT, ShowdownActionResponseType.FORCE_PASS, ShowdownActionResponseType.PASS, ShowdownActionResponseType.HEAL_ITEM, ShowdownActionResponseType.FORFEIT};
    }
}

