/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.components.Tooltip
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.joml.Vector3f
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.wheel;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0004\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 -2\u00020\u0001:\u0001-BS\u0012\b\u0010!\u001a\u0004\u0018\u00010\u001a\u0012\u0006\u0010\u001b\u001a\u00020\u001a\u0012\b\u0010%\u001a\u0004\u0018\u00010$\u0012\u0006\u0010'\u001a\u00020\u0016\u0012\u0006\u0010(\u001a\u00020\u0016\u0012\u0006\u0010\"\u001a\u00020\f\u0012\u000e\u0010\u001f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001e0\u001d\u0012\u0006\u0010*\u001a\u00020)\u00a2\u0006\u0004\b+\u0010,J\u001b\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u0002H\u0002\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0011\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u001f\u0010\r\u001a\u00020\f2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\tH\u0002\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0019\u0010\u0012\u001a\u00020\u00112\b\u0010\u0010\u001a\u0004\u0018\u00010\u000fH\u0016\u00a2\u0006\u0004\b\u0012\u0010\u0013J/\u0010\u0018\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\n\u001a\u00020\u00162\u0006\u0010\u000b\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001b\u001a\u00020\u001a8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001b\u0010\u001cR\u001c\u0010\u001f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u001e0\u001d8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001f\u0010 R\u0016\u0010!\u001a\u0004\u0018\u00010\u001a8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b!\u0010\u001cR\u0014\u0010\"\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\"\u0010#R\u0016\u0010%\u001a\u0004\u0018\u00010$8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b%\u0010&\u00a8\u0006."}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelButton;", "Lnet/minecraft/client/gui/components/Button;", "Lkotlin/Pair;", "", "getIconPosition", "()Lkotlin/Pair;", "Lnet/minecraft/client/gui/components/Tooltip;", "getTooltip", "()Lnet/minecraft/client/gui/components/Tooltip;", "", "mouseX", "mouseY", "", "isHovered", "(FF)Z", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lnet/minecraft/resources/ResourceLocation;", "buttonResource", "Lnet/minecraft/resources/ResourceLocation;", "Lkotlin/Function0;", "Lorg/joml/Vector3f;", "colour", "Lkotlin/jvm/functions/Function0;", "iconResource", "isEnabled", "Z", "", "tooltipText", "Ljava/lang/String;", "x", "y", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "onPress", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Ljava/lang/String;IIZLkotlin/jvm/functions/Function0;Lnet/minecraft/client/gui/components/Button$OnPress;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nInteractWheelButton.kt\nKotlin\n*S Kotlin\n*F\n+ 1 InteractWheelButton.kt\ncom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelButton\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,102:1\n1#2:103\n*E\n"})
public final class InteractWheelButton
extends Button {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @Nullable
    private final ResourceLocation iconResource;
    @NotNull
    private final ResourceLocation buttonResource;
    @Nullable
    private final String tooltipText;
    private final boolean isEnabled;
    @NotNull
    private final Function0<Vector3f> colour;
    public static final int BUTTON_SIZE = 69;
    public static final int TEXTURE_HEIGHT = 138;
    public static final int ICON_SIZE = 32;
    public static final float ICON_SCALE = 0.5f;
    public static final double ICON_OFFSET = 26.5;

    public InteractWheelButton(@Nullable ResourceLocation iconResource, @NotNull ResourceLocation buttonResource, @Nullable String tooltipText, int x, int y, boolean isEnabled, @NotNull Function0<? extends Vector3f> colour, @NotNull Button.OnPress onPress) {
        Intrinsics.checkNotNullParameter((Object)buttonResource, (String)"buttonResource");
        Intrinsics.checkNotNullParameter(colour, (String)"colour");
        Intrinsics.checkNotNullParameter((Object)onPress, (String)"onPress");
        super(x, y, 69, 69, (Component)Component.m_237113_((String)"Interact"), onPress, Button.f_252438_);
        this.iconResource = iconResource;
        this.buttonResource = buttonResource;
        this.tooltipText = tooltipText;
        this.isEnabled = isEnabled;
        this.colour = colour;
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack matrices = context.m_280168_();
        Pair<Number, Number> pair = this.buttonResource;
        int n = this.m_252754_();
        int n2 = this.m_252907_();
        int n3 = this.isHovered(mouseX, mouseY) && this.isEnabled ? 69 : 0;
        float f = this.isEnabled ? 1.0f : 0.4f;
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, (ResourceLocation)pair, n, n2, 69, 69, null, n3, null, 138, null, null, null, null, Float.valueOf(f), false, 0.0f, 113984, null);
        if (this.isHovered(mouseX, mouseY)) {
            String string = this.tooltipText;
            if (string != null) {
                String it = string;
                boolean bl = false;
                context.m_280557_(Minecraft.m_91087_().f_91062_, (Component)Component.m_237115_((String)it), mouseX, mouseY);
            }
        }
        if (this.iconResource != null) {
            pair = this.getIconPosition();
            Number iconX = (Number)pair.component1();
            Number iconY = (Number)pair.component2();
            Vector3f vector3f = (Vector3f)this.colour.invoke();
            if (vector3f == null) {
                vector3f = new Vector3f(1.0f, 1.0f, 1.0f);
            }
            Vector3f colour = vector3f;
            ResourceLocation resourceLocation = this.iconResource;
            float f2 = this.isEnabled ? 1.0f : 0.4f;
            float f3 = colour.x;
            float f4 = colour.y;
            float f5 = colour.z;
            GuiUtilsKt.blitk$default(matrices, resourceLocation, iconX, iconY, 32, 32, null, null, null, null, null, Float.valueOf(f3), Float.valueOf(f4), Float.valueOf(f5), Float.valueOf(f2), false, 0.5f, 34752, null);
        }
    }

    private final Pair<Number, Number> getIconPosition() {
        return new Pair((Object)(((double)this.m_252754_() + 26.5) / (double)0.5f), (Object)(((double)this.m_252907_() + 26.5) / (double)0.5f));
    }

    public void m_7435_(@Nullable SoundManager soundManager) {
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private final boolean isHovered(float mouseX, float mouseY) {
        float xMin = this.m_252754_();
        float xMax = xMin + (float)69;
        float yMin = this.m_252907_();
        float yMax = yMin + (float)69;
        if (!(xMin <= mouseX)) return false;
        if (!(mouseX <= xMax)) return false;
        boolean bl = true;
        if (!bl) return false;
        if (!(yMin <= mouseY)) return false;
        if (!(mouseY <= yMax)) return false;
        return true;
    }

    @Nullable
    public Tooltip m_278622_() {
        String string = this.tooltipText;
        if (string != null) {
            String it = string;
            boolean bl = false;
            return Tooltip.m_257550_((Component)((Component)Component.m_237115_((String)it)));
        }
        return super.m_278622_();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\r\u0010\u000eR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\t\u001a\u00020\b8\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\u0004R\u0014\u0010\f\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\f\u0010\u0004\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/wheel/InteractWheelButton$Companion;", "", "", "BUTTON_SIZE", "I", "", "ICON_OFFSET", "D", "", "ICON_SCALE", "F", "ICON_SIZE", "TEXTURE_HEIGHT", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

