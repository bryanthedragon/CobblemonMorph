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
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets;

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

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0007\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 82\u00020\u0001:\u00018BM\u0012\u0006\u00102\u001a\u00020\n\u0012\u0006\u00103\u001a\u00020\n\u0012\u0006\u00104\u001a\u00020\n\u0012\u0006\u00105\u001a\u00020\n\u0012\u0006\u0010\u001c\u001a\u00020\u001b\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u000b\u0012\b\b\u0002\u0010'\u001a\u00020\u000b\u0012\b\b\u0002\u0010\u0015\u001a\u00020\u0002\u00a2\u0006\u0004\b6\u00107J\u001f\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J/\u0010\r\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u000bH\u0014\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001f\u0010\u000f\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000bH\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0011\u001a\u00020\u000b8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\u0012\u001a\u0004\b\u0013\u0010\u0014R\"\u0010\u0015\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0015\u0010\u0016\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\"\u0010\u001c\u001a\u00020\u001b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0017\u0010#\u001a\u00020\"8\u0006\u00a2\u0006\f\n\u0004\b#\u0010$\u001a\u0004\b%\u0010&R\"\u0010'\u001a\u00020\u000b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b'\u0010\u0012\u001a\u0004\b(\u0010\u0014\"\u0004\b)\u0010*R\"\u0010,\u001a\u00020+8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/\"\u0004\b0\u00101\u00a8\u00069"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/ModelWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/SoundlessWidget;", "", "pMouseX", "pMouseY", "", "onClick", "(DD)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "", "partialTicks", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "renderPKM", "(Lnet/minecraft/client/gui/GuiGraphics;F)V", "baseScale", "F", "getBaseScale", "()F", "offsetY", "D", "getOffsetY", "()D", "setOffsetY", "(D)V", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "setPokemon", "(Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;)V", "Lorg/joml/Vector3f;", "rotVec", "Lorg/joml/Vector3f;", "getRotVec", "()Lorg/joml/Vector3f;", "rotationY", "getRotationY", "setRotationY", "(F)V", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonFloatingState;", "state", "Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonFloatingState;", "getState", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonFloatingState;", "setState", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/pokemon/PokemonFloatingState;)V", "pX", "pY", "pWidth", "pHeight", "<init>", "(IIIILcom/cobblemon/mod/common/pokemon/RenderablePokemon;FFD)V", "Companion", "common"})
public final class ModelWidget
extends SoundlessWidget {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private RenderablePokemon pokemon;
    private final float baseScale;
    private float rotationY;
    private double offsetY;
    @NotNull
    private PokemonFloatingState state;
    @NotNull
    private final Vector3f rotVec;
    private static boolean render = true;

    public ModelWidget(int pX, int pY, int pWidth, int pHeight, @NotNull RenderablePokemon pokemon, float baseScale, float rotationY, double offsetY) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        MutableComponent mutableComponent = Component.m_237113_((String)"Summary - ModelWidget");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"Summary - ModelWidget\")");
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

    @NotNull
    public final Vector3f getRotVec() {
        return this.rotVec;
    }

    protected void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float partialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (!render) {
            return;
        }
        this.f_93622_ = pMouseX >= this.m_252754_() && pMouseY >= this.m_252907_() && pMouseX < this.m_252754_() + this.f_93618_ && pMouseY < this.m_252907_() + this.f_93619_;
        this.renderPKM(context, partialTicks);
    }

    private final void renderPKM(GuiGraphics context, float partialTicks) {
        PoseStack matrices = context.m_280168_();
        matrices.m_85836_();
        context.m_280588_(this.m_252754_(), this.m_252907_(), this.m_252754_() + this.f_93618_, this.m_252907_() + this.f_93619_);
        matrices.m_85837_((double)this.m_252754_() + (double)this.f_93618_ * 0.5, (double)this.m_252907_() + this.offsetY, 0.0);
        matrices.m_85841_(this.baseScale, this.baseScale, this.baseScale);
        matrices.m_85836_();
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        PokemonGuiUtilsKt.drawProfilePokemon$default(this.pokemon, matrices, QuaternionUtilsKt.fromEulerXYZDegrees(new Quaternionf(), this.rotVec), this.state, partialTicks, 0.0f, 32, null);
        matrices.m_85849_();
        context.m_280618_();
        matrices.m_85849_();
    }

    public void m_5716_(double pMouseX, double pMouseY) {
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR\"\u0010\u0003\u001a\u00020\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/ModelWidget$Companion;", "", "", "render", "Z", "getRender", "()Z", "setRender", "(Z)V", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public final boolean getRender() {
            return render;
        }

        public final void setRender(boolean bl) {
            render = bl;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

