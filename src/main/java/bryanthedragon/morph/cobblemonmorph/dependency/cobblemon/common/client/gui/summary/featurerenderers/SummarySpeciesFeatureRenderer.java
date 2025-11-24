/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0003J/\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ7\u0010\f\u001a\u00020\u000f2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000e\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\f\u0010\u0010R\u0014\u0010\u0014\u001a\u00020\u00118&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/featurerenderers/SummarySpeciesFeatureRenderer;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeature;", "T", "", "Lnet/minecraft/client/gui/GuiGraphics;", "drawContext", "", "x", "y", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;FFLcom/cobblemon/mod/common/pokemon/Pokemon;)Z", "feature", "", "(Lnet/minecraft/client/gui/GuiGraphics;FFLcom/cobblemon/mod/common/pokemon/Pokemon;Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeature;)V", "", "getName", "()Ljava/lang/String;", "name", "common"})
public interface SummarySpeciesFeatureRenderer<T extends SynchronizedSpeciesFeature> {
    @NotNull
    public String getName();

    public void render(@NotNull GuiGraphics var1, float var2, float var3, @NotNull Pokemon var4, @NotNull T var5);

    public boolean render(@NotNull GuiGraphics var1, float var2, float var3, @NotNull Pokemon var4);

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static <T extends SynchronizedSpeciesFeature> boolean render(@NotNull SummarySpeciesFeatureRenderer<T> $this, @NotNull GuiGraphics drawContext, float x, float y, @NotNull Pokemon pokemon) {
            Intrinsics.checkNotNullParameter((Object)drawContext, (String)"drawContext");
            Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
            SynchronizedSpeciesFeature synchronizedSpeciesFeature = (SynchronizedSpeciesFeature)pokemon.getFeature($this.getName());
            if (synchronizedSpeciesFeature == null) {
                return false;
            }
            SynchronizedSpeciesFeature feature = synchronizedSpeciesFeature;
            $this.render(drawContext, x, y, pokemon, feature);
            return true;
        }
    }
}

