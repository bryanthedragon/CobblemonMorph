/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Quaternionf
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.startselection.widgets.preview;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.PokemonGuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.RenderablePokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.QuaternionUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 ,2\u00020\u0001:\u0001,Bq\u0012\u0006\u0010&\u001a\u00020\n\u0012\u0006\u0010'\u001a\u00020\n\u0012\u0006\u0010(\u001a\u00020\n\u0012\u0006\u0010)\u001a\u00020\n\u0012\u0006\u0010\u001d\u001a\u00020\u001c\u00128\b\u0002\u0010\u001a\u001a2\u0012\u0013\u0012\u00110\u0002\u00a2\u0006\f\b\u0018\u0012\b\b\u0019\u0012\u0004\b\b(\u0003\u0012\u0013\u0012\u00110\u0002\u00a2\u0006\f\b\u0018\u0012\b\b\u0019\u0012\u0004\b\b(\u0004\u0012\u0004\u0012\u00020\u000e0\u0017\u0012\u0006\u0010$\u001a\u00020#\u00a2\u0006\u0004\b*\u0010+J\u001f\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u0006\u0010\u0007J'\u0010\f\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u00022\u0006\u0010\t\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u001f\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J/\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0003\u001a\u00020\n2\u0006\u0010\u0004\u001a\u00020\n2\u0006\u0010\u0014\u001a\u00020\u0013H\u0014\u00a2\u0006\u0004\b\u0015\u0010\u0016RD\u0010\u001a\u001a2\u0012\u0013\u0012\u00110\u0002\u00a2\u0006\f\b\u0018\u0012\b\b\u0019\u0012\u0004\b\b(\u0003\u0012\u0013\u0012\u00110\u0002\u00a2\u0006\f\b\u0018\u0012\b\b\u0019\u0012\u0004\b\b(\u0004\u0012\u0004\u0012\u00020\u000e0\u00178\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001a\u0010\u001bR\"\u0010\u001d\u001a\u00020\u001c8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0014\u0010$\u001a\u00020#8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b$\u0010%\u00a8\u0006-"}, d2={"Lcom/cobblemon/mod/common/client/gui/startselection/widgets/preview/StarterRoundabout;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/SoundlessWidget;", "", "mouseX", "mouseY", "", "clicked", "(DD)Z", "pMouseX", "pMouseY", "", "pButton", "mouseClicked", "(DDI)Z", "", "onClick", "(DD)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "clickAction", "Lkotlin/jvm/functions/Function2;", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "pokemon", "Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "getPokemon", "()Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;", "setPokemon", "(Lcom/cobblemon/mod/common/pokemon/RenderablePokemon;)V", "Lorg/joml/Vector3f;", "rotationVector", "Lorg/joml/Vector3f;", "pX", "pY", "pWidth", "pHeight", "<init>", "(IIIILcom/cobblemon/mod/common/pokemon/RenderablePokemon;Lkotlin/jvm/functions/Function2;Lorg/joml/Vector3f;)V", "Companion", "common"})
public final class StarterRoundabout
extends SoundlessWidget {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private RenderablePokemon pokemon;
    @NotNull
    private final Function2<Double, Double, Unit> clickAction;
    @NotNull
    private final Vector3f rotationVector;
    public static final int MODEL_WIDTH = 30;
    public static final int MODEL_HEIGHT = 30;

    public StarterRoundabout(int pX, int pY, int pWidth, int pHeight, @NotNull RenderablePokemon pokemon, @NotNull Function2<? super Double, ? super Double, Unit> clickAction, @NotNull Vector3f rotationVector) {
        Intrinsics.checkNotNullParameter((Object)pokemon, (String)"pokemon");
        Intrinsics.checkNotNullParameter(clickAction, (String)"clickAction");
        Intrinsics.checkNotNullParameter((Object)rotationVector, (String)"rotationVector");
        MutableComponent mutableComponent = Component.m_237113_((String)"StarterRoundabout");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"StarterRoundabout\")");
        super(pX, pY, pWidth, pHeight, (Component)mutableComponent);
        this.pokemon = pokemon;
        this.clickAction = clickAction;
        this.rotationVector = rotationVector;
    }

    public /* synthetic */ StarterRoundabout(int n, int n2, int n3, int n4, RenderablePokemon renderablePokemon, Function2 function2, Vector3f vector3f, int n5, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n5 & 0x20) != 0) {
            function2 = 1.INSTANCE;
        }
        this(n, n2, n3, n4, renderablePokemon, (Function2<? super Double, ? super Double, Unit>)function2, vector3f);
    }

    @NotNull
    public final RenderablePokemon getPokemon() {
        return this.pokemon;
    }

    public final void setPokemon(@NotNull RenderablePokemon renderablePokemon) {
        Intrinsics.checkNotNullParameter((Object)renderablePokemon, (String)"<set-?>");
        this.pokemon = renderablePokemon;
    }

    protected void m_87963_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (!this.f_93624_) {
            return;
        }
        PoseStack matrices = context.m_280168_();
        this.f_93622_ = mouseX >= this.m_252754_() && mouseX < this.m_252754_() + this.f_93618_ && mouseY >= this.m_252907_() - 30 && mouseY < this.m_252907_();
        matrices.m_85836_();
        double correctionTerm = -3.0;
        matrices.m_85837_((double)this.m_252754_() + 15.0, (double)this.m_252907_() - 30.0 + correctionTerm, 0.0);
        context.m_280588_(this.m_252754_(), this.m_252907_() - 30, this.m_252754_() + 30, this.m_252907_());
        RenderablePokemon renderablePokemon = this.pokemon;
        Quaternionf quaternionf = QuaternionUtilsKt.fromEulerXYZDegrees(new Quaternionf(), this.rotationVector);
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        PokemonGuiUtilsKt.drawProfilePokemon(renderablePokemon, matrices, quaternionf, null, delta, 18.0f);
        context.m_280618_();
        matrices.m_85849_();
    }

    @Override
    public boolean m_6375_(double pMouseX, double pMouseY, int pButton) {
        if (this.m_93680_(pMouseX, pMouseY) && this.m_7972_(pButton)) {
            this.m_5716_(pMouseX, pMouseY);
        }
        return super.m_6375_(pMouseX, pMouseY, pButton);
    }

    protected boolean m_93680_(double mouseX, double mouseY) {
        return this.f_93623_ && this.f_93624_ && this.f_93622_;
    }

    public void m_5716_(double mouseX, double mouseY) {
        this.clickAction.invoke((Object)mouseX, (Object)mouseY);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/client/gui/startselection/widgets/preview/StarterRoundabout$Companion;", "", "", "MODEL_HEIGHT", "I", "MODEL_WIDTH", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

