/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Decodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.Encodable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SpeciesFeatureProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.feature.SynchronizedSpeciesFeature;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.featurerenderers.SummarySpeciesFeatureRenderer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import kotlin.Metadata;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u00032\u00020\u00042\u00020\u0005J\u0019\u0010\b\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\b\u0010\tJ\u001f\u0010\u000b\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\n2\u0006\u0010\u0007\u001a\u00020\u0006H&\u00a2\u0006\u0004\b\u000b\u0010\fJ\"\u0010\u0011\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u000fH\u00a6\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0018\u001a\u00020\u00138&@&X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeatureProvider;", "Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeature;", "T", "Lcom/cobblemon/mod/common/api/pokemon/feature/SpeciesFeatureProvider;", "Lcom/cobblemon/mod/common/api/net/Encodable;", "Lcom/cobblemon/mod/common/api/net/Decodable;", "Lcom/cobblemon/mod/common/pokemon/Pokemon;", "pokemon", "get", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeature;", "Lcom/cobblemon/mod/common/client/gui/summary/featurerenderers/SummarySpeciesFeatureRenderer;", "getRenderer", "(Lcom/cobblemon/mod/common/pokemon/Pokemon;)Lcom/cobblemon/mod/common/client/gui/summary/featurerenderers/SummarySpeciesFeatureRenderer;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "name", "invoke", "(Lnet/minecraft/network/FriendlyByteBuf;Ljava/lang/String;)Lcom/cobblemon/mod/common/api/pokemon/feature/SynchronizedSpeciesFeature;", "", "getVisible", "()Z", "setVisible", "(Z)V", "visible", "common"})
public interface SynchronizedSpeciesFeatureProvider<T extends SynchronizedSpeciesFeature>
extends SpeciesFeatureProvider<T>,
Encodable,
Decodable {
    public boolean getVisible();

    public void setVisible(boolean var1);

    @Nullable
    public T invoke(@NotNull FriendlyByteBuf var1, @NotNull String var2);

    @Nullable
    public T get(@NotNull Pokemon var1);

    @Nullable
    public SummarySpeciesFeatureRenderer<T> getRenderer(@NotNull Pokemon var1);
}

