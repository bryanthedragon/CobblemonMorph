/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.StorageSlot;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.StorageWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0015\u001a\u00020\u000b\u0012\u0006\u0010\u0016\u001a\u00020\u000b\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u0012\u0006\u0010\u0013\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u000f\u0010\b\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\u0007J/\u0010\u0011\u001a\u00020\u00102\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0013\u0010\u0014\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/client/gui/pc/GrabbedStorageSlot;", "Lcom/cobblemon/mod/common/client/gui/pc/StorageSlot;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/Pokemon;", "", "isSelected", "()Z", "isStationary", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "mouseX", "mouseY", "", "delta", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "pokemon", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "x", "y", "Lcom/cobblemon/mod/common/client/gui/pc/StorageWidget;", "parent", "<init>", "(IILcom/cobblemon/mod/common/client/gui/pc/StorageWidget;Lcom/cobblemon/mod/common/pokemon/Pokemon;)V", "common"})
public final class GrabbedStorageSlot
extends StorageSlot {
    @NotNull
    private final Pokemon pokemon;

    public GrabbedStorageSlot(int x, int y, @NotNull StorageWidget parent, @NotNull Pokemon pokemon) {
        Intrinsics.checkNotNullParameter((Object)((Object)parent), (String)"parent");
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        super(x, y, parent, GrabbedStorageSlot::_init_$lambda$0);
        this.pokemon = pokemon;
    }

    @Override
    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        this.renderSlot(context, mouseX - this.f_93618_ / 2, mouseY - this.f_93619_ / 2, delta);
    }

    @Override
    public boolean isStationary() {
        return false;
    }

    @Override
    @NotNull
    public Pokemon getPokemon() {
        return this.pokemon;
    }

    @Override
    public boolean m_198029_() {
        return true;
    }

    private static final void _init_$lambda$0(Button it) {
    }
}

