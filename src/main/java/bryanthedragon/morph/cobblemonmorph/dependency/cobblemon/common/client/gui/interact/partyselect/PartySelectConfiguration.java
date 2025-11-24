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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.PartySelectPokemonDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.partyselect.PartySelectGUI;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B_\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u0012\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\f0\u0011\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002\u0012\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0002\u0012\u0018\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00040\u000b\u00a2\u0006\u0004\b\u001b\u0010\u001cR#\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\bR#\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0006\u001a\u0004\b\n\u0010\bR)\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00040\u000b8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\f0\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/partyselect/PartySelectConfiguration;", "", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/gui/interact/partyselect/PartySelectGUI;", "", "onBack", "Lkotlin/jvm/functions/Function1;", "getOnBack", "()Lkotlin/jvm/functions/Function1;", "onCancel", "getOnCancel", "Lkotlin/Function2;", "Lcom/cobblemon/mod/common/api/callback/PartySelectPokemonDTO;", "onSelect", "Lkotlin/jvm/functions/Function2;", "getOnSelect", "()Lkotlin/jvm/functions/Function2;", "", "pokemon", "Ljava/util/List;", "getPokemon", "()Ljava/util/List;", "Lnet/minecraft/network/chat/MutableComponent;", "title", "Lnet/minecraft/network/chat/MutableComponent;", "getTitle", "()Lnet/minecraft/network/chat/MutableComponent;", "<init>", "(Lnet/minecraft/network/chat/MutableComponent;Ljava/util/List;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function2;)V", "common"})
public final class PartySelectConfiguration {
    @NotNull
    private final MutableComponent title;
    @NotNull
    private final List<PartySelectPokemonDTO> pokemon;
    @NotNull
    private final Function1<PartySelectGUI, Unit> onCancel;
    @NotNull
    private final Function1<PartySelectGUI, Unit> onBack;
    @NotNull
    private final Function2<PartySelectGUI, PartySelectPokemonDTO, Unit> onSelect;

    public PartySelectConfiguration(@NotNull MutableComponent title, @NotNull List<? extends PartySelectPokemonDTO> pokemon, @NotNull Function1<? super PartySelectGUI, Unit> onCancel, @NotNull Function1<? super PartySelectGUI, Unit> onBack, @NotNull Function2<? super PartySelectGUI, ? super PartySelectPokemonDTO, Unit> onSelect) {
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter(pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(onCancel, (String)"onCancel");
        Intrinsics.checkNotNullParameter(onBack, (String)"onBack");
        Intrinsics.checkNotNullParameter(onSelect, (String)"onSelect");
        this.title = title;
        this.pokemon = pokemon;
        this.onCancel = onCancel;
        this.onBack = onBack;
        this.onSelect = onSelect;
    }

    @NotNull
    public final MutableComponent getTitle() {
        return this.title;
    }

    @NotNull
    public final List<PartySelectPokemonDTO> getPokemon() {
        return this.pokemon;
    }

    @NotNull
    public final Function1<PartySelectGUI, Unit> getOnCancel() {
        return this.onCancel;
    }

    @NotNull
    public final Function1<PartySelectGUI, Unit> getOnBack() {
        return this.onBack;
    }

    @NotNull
    public final Function2<PartySelectGUI, PartySelectPokemonDTO, Unit> getOnSelect() {
        return this.onSelect;
    }
}

