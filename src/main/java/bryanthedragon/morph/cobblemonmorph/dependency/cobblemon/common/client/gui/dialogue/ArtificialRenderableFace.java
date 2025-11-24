/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.RenderableFace;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.PoseableEntityState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonFloatingState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u001e\u001a\u00020\n\u0012\u0006\u0010\u0010\u001a\u00020\u000f\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t\u00a2\u0006\u0004\b\u001f\u0010 J\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u001d\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\t8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0010\u001a\u00020\u000f8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013R\u0017\u0010\u0015\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018R\u001b\u0010\u001a\u001a\u0006\u0012\u0002\b\u00030\u00198\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/ArtificialRenderableFace;", "Lcom/cobblemon/mod/common/client/gui/dialogue/RenderableFace;", "Lnet/minecraft/client/gui/GuiGraphics;", "drawContext", "", "partialTicks", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;F)V", "", "", "aspects", "Ljava/util/Set;", "getAspects", "()Ljava/util/Set;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "Lnet/minecraft/resources/ResourceLocation;", "getIdentifier", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/pokemon/Species;", "species", "Lcom/cobblemon/mod/common/pokemon/Species;", "getSpecies", "()Lcom/cobblemon/mod/common/pokemon/Species;", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "state", "Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "getState", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/PoseableEntityState;", "modelType", "<init>", "(Ljava/lang/String;Lnet/minecraft/resources/ResourceLocation;Ljava/util/Set;)V", "common"})
public final class ArtificialRenderableFace
implements RenderableFace {
    @NotNull
    private final ResourceLocation identifier;
    @NotNull
    private final Set<String> aspects;
    @NotNull
    private final Species species;
    @NotNull
    private final PoseableEntityState<?> state;

    /*
     * WARNING - void declaration
     */
    public ArtificialRenderableFace(@NotNull String modelType, @NotNull ResourceLocation identifier, @NotNull Set<String> aspects) {
        Intrinsics.checkNotNullParameter((Object)modelType, (String)"modelType");
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        this.identifier = identifier;
        this.aspects = aspects;
        ArtificialRenderableFace artificialRenderableFace = this;
        Species species = PokemonSpecies.INSTANCE.getByIdentifier(this.identifier);
        if (species == null) {
            void $this$species_u24lambda_u240;
            ArtificialRenderableFace artificialRenderableFace2 = this;
            ArtificialRenderableFace artificialRenderableFace3 = artificialRenderableFace;
            boolean bl = false;
            Cobblemon.INSTANCE.getLOGGER().error("Unable to find species for " + $this$species_u24lambda_u240.identifier + " for a dialogue face. Defaulting to first species.");
            species = (Species)CollectionsKt.first((Iterable)PokemonSpecies.INSTANCE.getSpecies());
            artificialRenderableFace = artificialRenderableFace3;
        }
        artificialRenderableFace.species = species;
        if (!Intrinsics.areEqual((Object)modelType, (Object)"pokemon")) {
            throw new IllegalArgumentException("Unknown model type: " + modelType);
        }
        this.state = new PokemonFloatingState();
    }

    @NotNull
    public final ResourceLocation getIdentifier() {
        return this.identifier;
    }

    @NotNull
    public final Set<String> getAspects() {
        return this.aspects;
    }

    @NotNull
    public final Species getSpecies() {
        return this.species;
    }

    @NotNull
    public final PoseableEntityState<?> getState() {
        return this.state;
    }

    @Override
    public void render(@NotNull GuiGraphics drawContext, float partialTicks) {
        Intrinsics.checkNotNullParameter((Object)drawContext, (String)"drawContext");
        PoseableEntityState<?> state = this.state;
        if (state instanceof PokemonFloatingState) {
            PoseStack poseStack = drawContext.m_280168_();
            Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"drawContext.matrices");
            GuiUtilsKt.drawPortraitPokemon$default(this.species, this.aspects, poseStack, 0.0f, false, state, partialTicks, 24, null);
        }
    }
}

