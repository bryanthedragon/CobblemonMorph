/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.PokemonClientDelegate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.RenderableFace;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u001b\u0010\n\u001a\u0006\u0012\u0002\b\u00030\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0012"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/ReferenceRenderableFace;", "Lcom/cobblemon/mod/common/client/gui/dialogue/RenderableFace;", "Lnet/minecraft/client/gui/GuiGraphics;", "drawContext", "", "partialTicks", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;F)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "getState", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "Lcom/cobblemon/mod/common/entity/Poseable;", "entity", "<init>", "(Lcom/cobblemon/mod/common/entity/Poseable;)V", "common"})
public final class ReferenceRenderableFace
implements RenderableFace {
    @NotNull
    private final PoseableEntityState<?> state;

    public ReferenceRenderableFace(@NotNull Poseable entity2) {
        Intrinsics.checkNotNullParameter((Object)entity2, (String)"entity");
        EntitySideDelegate<?> entitySideDelegate = entity2.getDelegate();
        Intrinsics.checkNotNull(entitySideDelegate, (String)"null cannot be cast to non-null type bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState<*>");
        this.state = (PoseableEntityState)((Object)entitySideDelegate);
    }

    @NotNull
    public final PoseableEntityState<?> getState() {
        return this.state;
    }

    @Override
    public void render(@NotNull GuiGraphics drawContext, float partialTicks) {
        Intrinsics.checkNotNullParameter((Object)drawContext, (String)"drawContext");
        PoseableEntityState<?> state = this.state;
        if (state instanceof PokemonClientDelegate) {
            Species species = ((PokemonClientDelegate)state).getCurrentEntity().getPokemon().getSpecies();
            Set<String> set2 = ((PokemonClientDelegate)state).getCurrentEntity().getPokemon().getAspects();
            PoseStack poseStack = drawContext.m_280168_();
            Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"drawContext.matrices");
            GuiUtilsKt.drawPortraitPokemon$default(species, set2, poseStack, 0.0f, false, state, 0.0f, 24, null);
        }
    }
}

