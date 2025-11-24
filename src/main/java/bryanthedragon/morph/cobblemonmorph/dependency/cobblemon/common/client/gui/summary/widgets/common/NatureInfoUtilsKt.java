/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonItems;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.item.interactive.MintItem;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Nature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0015\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0001\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0005"}, d2={"Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "Lnet/minecraft/network/chat/MutableComponent;", "reformatNatureTextIfMinted", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lnet/minecraft/network/chat/MutableComponent;", "common"})
public final class NatureInfoUtilsKt {
    @NotNull
    public static final MutableComponent reformatNatureTextIfMinted(@NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        MutableComponent natureText = null;
        natureText = MiscUtilsKt.asTranslated(pokemon.getNature().getDisplayName());
        if (pokemon.getMintedNature() != null) {
            Map<String, MintItem> map = CobblemonItems.INSTANCE.getMints();
            Nature nature = pokemon.getMintedNature();
            Intrinsics.checkNotNull((Object)nature);
            MintItem mintItem = map.get(nature.getDisplayName());
            if (mintItem != null) {
                MintItem mint = mintItem;
                boolean bl = false;
                MutableComponent mutableComponent = natureText;
                Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"natureText");
                MutableComponent mutableComponent2 = TextKt.italicise(mutableComponent);
                Component component = mint.m_41466_();
                Intrinsics.checkNotNullExpressionValue((Object)component, (String)"mint.name");
                natureText = TextKt.onHover(mutableComponent2, component);
            }
        }
        MutableComponent mutableComponent = natureText;
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"natureText");
        return mutableComponent;
    }
}

