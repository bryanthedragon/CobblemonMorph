/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function3
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.storage.StorePosition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.PCGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\b\u0016\u0018\u00002\u00020\u0001Bc\u0012\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u0002\u0012$\b\u0002\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\n\u0018\u00010\r\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0004\u0012\u0014\b\u0002\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002\u00a2\u0006\u0004\b\u0017\u0010\u0018R#\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bR#\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\u0006\u001a\u0004\b\f\u0010\bR3\u0010\u000f\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000e\u0012\u0006\u0012\u0004\u0018\u00010\u0003\u0012\u0004\u0012\u00020\n\u0018\u00010\r8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/client/gui/pc/PCGUIConfiguration;", "", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "canSelect", "Lkotlin/jvm/functions/Function1;", "getCanSelect", "()Lkotlin/jvm/functions/Function1;", "Lcom/cobblemon/mod/common/client/gui/pc/PCGUI;", "", "exitFunction", "getExitFunction", "Lkotlin/Function3;", "Lcom/cobblemon/mod/common/api/storage/StorePosition;", "selectOverride", "Lkotlin/jvm/functions/Function3;", "getSelectOverride", "()Lkotlin/jvm/functions/Function3;", "showParty", "Z", "getShowParty", "()Z", "<init>", "(Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function3;ZLkotlin/jvm/functions/Function1;)V", "common"})
public class PCGUIConfiguration {
    @NotNull
    private final Function1<PCGUI, Unit> exitFunction;
    @Nullable
    private final Function3<PCGUI, StorePosition, Pokemon, Unit> selectOverride;
    private final boolean showParty;
    @NotNull
    private final Function1<Pokemon, Boolean> canSelect;

    public PCGUIConfiguration(@NotNull Function1<? super PCGUI, Unit> exitFunction, @Nullable Function3<? super PCGUI, ? super StorePosition, ? super Pokemon, Unit> selectOverride, boolean showParty, @NotNull Function1<? super Pokemon, Boolean> canSelect) {
        Intrinsics.checkNotNullParameter(exitFunction, (String)"exitFunction");
        Intrinsics.checkNotNullParameter(canSelect, (String)"canSelect");
        this.exitFunction = exitFunction;
        this.selectOverride = selectOverride;
        this.showParty = showParty;
        this.canSelect = canSelect;
    }

    public /* synthetic */ PCGUIConfiguration(Function1 function1, Function3 function3, boolean bl, Function1 function12, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            function1 = 1.INSTANCE;
        }
        if ((n & 2) != 0) {
            function3 = null;
        }
        if ((n & 4) != 0) {
            bl = true;
        }
        if ((n & 8) != 0) {
            function12 = 2.INSTANCE;
        }
        this((Function1<? super PCGUI, Unit>)function1, (Function3<? super PCGUI, ? super StorePosition, ? super Pokemon, Unit>)function3, bl, (Function1<? super Pokemon, Boolean>)function12);
    }

    @NotNull
    public final Function1<PCGUI, Unit> getExitFunction() {
        return this.exitFunction;
    }

    @Nullable
    public final Function3<PCGUI, StorePosition, Pokemon, Unit> getSelectOverride() {
        return this.selectOverride;
    }

    public final boolean getShowParty() {
        return this.showParty;
    }

    @NotNull
    public final Function1<Pokemon, Boolean> getCanSelect() {
        return this.canSelect;
    }

    public PCGUIConfiguration() {
        this(null, null, false, null, 15, null);
    }
}

