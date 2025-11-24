/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.trade;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.PokemonGuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonFloatingState;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.QuaternionUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u0001BM\u0012\u0006\u0010,\u001a\u00020\u0004\u0012\u0006\u0010-\u001a\u00020\u0004\u0012\u0006\u0010.\u001a\u00020\u0004\u0012\u0006\u0010/\u001a\u00020\u0004\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u0012\b\b\u0002\u0010\f\u001a\u00020\u0007\u0012\b\b\u0002\u0010!\u001a\u00020\u0007\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u0010\u00a2\u0006\u0004\b0\u00101J/\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0014\u00a2\u0006\u0004\b\n\u0010\u000bR\u0017\u0010\f\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\u000e\u0010\u000fR\"\u0010\u0011\u001a\u00020\u00108\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\"\u0010\u0018\u001a\u00020\u00178\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u0016\u0010\u001f\u001a\u00020\u001e8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u001f\u0010 R\"\u0010!\u001a\u00020\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b!\u0010\r\u001a\u0004\b\"\u0010\u000f\"\u0004\b#\u0010$R\"\u0010&\u001a\u00020%8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+\u00a8\u00062"}, d2={"Lcom/cobblemon/mod/common/client/gui/trade/ModelWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/SoundlessWidget;", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "mouseX", "mouseY", "", "delta", "", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "baseScale", "F", "getBaseScale", "()F", "", "offsetY", "D", "getOffsetY", "()D", "setOffsetY", "(D)V", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "setPokemon", "(Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;)V", "Lorg/joml/Vector3f;", "rotVec", "Lorg/joml/Vector3f;", "rotationY", "getRotationY", "setRotationY", "(F)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonFloatingState;", "state", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonFloatingState;", "getState", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonFloatingState;", "setState", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonFloatingState;)V", "pX", "pY", "pWidth", "pHeight", "<init>", "(IIIILcom/cobblemon/mod/common/pokemon/RenderablePokemon;FFD)V", "common"})
public final class ModelWidget
extends SoundlessWidget {
    @NotNull
    private RenderablePokemon pokemon;
    private final float baseScale;
    private float rotationY;
    private double offsetY;
    @NotNull
    private PokemonFloatingState state;
    @NotNull
    private Vector3f rotVec;

    public ModelWidget(int pX, int pY, int pWidth, int pHeight, @NotNull RenderablePokemon pokemon, float baseScale, float rotationY, double offsetY) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        MutableComponent mutableComponent = Component.m_237113_((String)"Trade - ModelWidget");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"Trade - ModelWidget\")");
        super(pX, pY, pWidth, pHeight, (Component)mutableComponent);
        this.pokemon = pokemon;
        this.baseScale = baseScale;
        this.rotationY = rotationY;
        this.offsetY = offsetY;
        this.state = new PokemonFloatingState();
        this.rotVec = new Vector3f(13.0f, this.rotationY, 0.0f);
    }

    public /* synthetic */ ModelWidget(int n, int n2, int n3, int n4, RenderablePokemon renderablePokemon, float f, float f2, double d, int n5, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n5 & 0x20) != 0) {
            f = 2.7f;
        }
        if ((n5 & 0x40) != 0) {
            f2 = 35.0f;
        }
        if ((n5 & 0x80) != 0) {
            d = 0.0;
        }
        this(n, n2, n3, n4, renderablePokemon, f, f2, d);
    }

    @NotNull
    public final RenderablePokemon getPokemon() {
        return this.pokemon;
    }

    public final void setPokemon(@NotNull RenderablePokemon renderablePokemon) {
        Intrinsics.checkNotNullParameter((Object)renderablePokemon, (String)"<set-?>");
        this.pokemon = renderablePokemon;
    }

    public final float getBaseScale() {
        return this.baseScale;
    }

    public final float getRotationY() {
        return this.rotationY;
    }

    public final void setRotationY(float f) {
        this.rotationY = f;
    }

    public final double getOffsetY() {
        return this.offsetY;
    }

    public final void setOffsetY(double d) {
        this.offsetY = d;
    }

    @NotNull
    public final PokemonFloatingState getState() {
        return this.state;
    }

    public final void setState(@NotNull PokemonFloatingState pokemonFloatingState) {
        Intrinsics.checkNotNullParameter((Object)pokemonFloatingState, (String)"<set-?>");
        this.state = pokemonFloatingState;
    }

    protected void m_87963_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack matrices = context.m_280168_();
        matrices.m_85836_();
        matrices.m_85837_((double)this.m_252754_() + (double)this.f_93618_ * 0.5, (double)this.m_252907_() + this.offsetY, 0.0);
        matrices.m_85841_(this.baseScale, this.baseScale, this.baseScale);
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        PokemonGuiUtilsKt.drawProfilePokemon$default(this.pokemon, matrices, QuaternionUtilsKt.fromEulerXYZDegrees(new Quaternionf(), this.rotVec), this.state, delta, 0.0f, 32, null);
        matrices.m_85849_();
    }
}

