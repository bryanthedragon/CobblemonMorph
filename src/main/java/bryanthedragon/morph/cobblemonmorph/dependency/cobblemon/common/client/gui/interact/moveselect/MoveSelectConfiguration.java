/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.moveselect;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.moveselect.MoveSelectGUI;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B_\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\u0012\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b\u0012\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b\u0012\u0018\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\n0\u0011\u00a2\u0006\u0004\b\u001b\u0010\u001cR\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R#\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR#\u0010\u000f\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\b8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\f\u001a\u0004\b\u0010\u0010\u000eR)\u0010\u0012\u001a\u0014\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\n0\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/moveselect/MoveSelectConfiguration;", "", "", "Lcom/cobblemon/mod/common/api/callback/MoveSelectDTO;", "moves", "Ljava/util/List;", "getMoves", "()Ljava/util/List;", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/gui/interact/moveselect/MoveSelectGUI;", "", "onBack", "Lkotlin/jvm/functions/Function1;", "getOnBack", "()Lkotlin/jvm/functions/Function1;", "onCancel", "getOnCancel", "Lkotlin/Function2;", "onSelect", "Lkotlin/jvm/functions/Function2;", "getOnSelect", "()Lkotlin/jvm/functions/Function2;", "Lnet/minecraft/network/chat/MutableComponent;", "title", "Lnet/minecraft/network/chat/MutableComponent;", "getTitle", "()Lnet/minecraft/network/chat/MutableComponent;", "<init>", "(Lnet/minecraft/network/chat/MutableComponent;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)V", "common"})
public final class MoveSelectConfiguration {
    @NotNull
    private final MutableComponent title;
    @NotNull
    private final List<MoveSelectDTO> moves;
    @NotNull
    private final Function1<MoveSelectGUI, Unit> onCancel;
    @NotNull
    private final Function1<MoveSelectGUI, Unit> onBack;
    @NotNull
    private final Function2<MoveSelectGUI, MoveSelectDTO, Unit> onSelect;

    public MoveSelectConfiguration(@NotNull MutableComponent title, @NotNull List<MoveSelectDTO> moves, @NotNull Function1<? super MoveSelectGUI, Unit> onCancel, @NotNull Function1<? super MoveSelectGUI, Unit> onBack, @NotNull Function2<? super MoveSelectGUI, ? super MoveSelectDTO, Unit> onSelect) {
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter(moves, (String)"moves");
        Intrinsics.checkNotNullParameter(onCancel, (String)"onCancel");
        Intrinsics.checkNotNullParameter(onBack, (String)"onBack");
        Intrinsics.checkNotNullParameter(onSelect, (String)"onSelect");
        this.title = title;
        this.moves = moves;
        this.onCancel = onCancel;
        this.onBack = onBack;
        this.onSelect = onSelect;
    }

    @NotNull
    public final MutableComponent getTitle() {
        return this.title;
    }

    @NotNull
    public final List<MoveSelectDTO> getMoves() {
        return this.moves;
    }

    @NotNull
    public final Function1<MoveSelectGUI, Unit> getOnCancel() {
        return this.onCancel;
    }

    @NotNull
    public final Function1<MoveSelectGUI, Unit> getOnBack() {
        return this.onBack;
    }

    @NotNull
    public final Function2<MoveSelectGUI, MoveSelectDTO, Unit> getOnSelect() {
        return this.onSelect;
    }
}

