/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.client.gui.GuiGraphics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue;

import kotlin.Metadata;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H&\u00a2\u0006\u0004\b\u0007\u0010\b\u0082\u0001\u0003\t\n\u000b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/RenderableFace;", "", "Lnet/minecraft/client/gui/GuiGraphics;", "drawContext", "", "partialTicks", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;F)V", "Lcom/cobblemon/mod/common/client/gui/dialogue/ArtificialRenderableFace;", "Lcom/cobblemon/mod/common/client/gui/dialogue/PlayerRenderableFace;", "Lcom/cobblemon/mod/common/client/gui/dialogue/ReferenceRenderableFace;", "common"})
public interface RenderableFace {
    public void render(@NotNull GuiGraphics var1, float var2);
}

