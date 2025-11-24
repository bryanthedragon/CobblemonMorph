/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000p\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0004\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 T2\u00020\u0001:\u0001TB\u00c9\u0001\u0012\u0006\u00102\u001a\u00020\u001e\u0012\u0006\u00108\u001a\u00020\u001e\u0012\u0006\u00100\u001a\u00020+\u0012\u0006\u0010,\u001a\u00020+\u0012\u0006\u0010<\u001a\u00020;\u0012\b\b\u0002\u0010O\u001a\u00020N\u0012\u0006\u0010L\u001a\u00020&\u0012\n\b\u0002\u0010'\u001a\u0004\u0018\u00010&\u0012#\b\u0002\u0010K\u001a\u001d\u0012\u0013\u0012\u00110\u0000\u00a2\u0006\f\bA\u0012\b\bB\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f0@\u0012#\b\u0002\u0010C\u001a\u001d\u0012\u0013\u0012\u00110\u0000\u00a2\u0006\f\bA\u0012\b\bB\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f0@\u0012\b\b\u0002\u0010E\u001a\u00020\f\u0012\b\b\u0002\u0010M\u001a\u00020\f\u0012\b\b\u0002\u0010)\u001a\u00020\f\u0012\b\b\u0002\u0010J\u001a\u00020\f\u0012\b\b\u0002\u0010Q\u001a\u00020\u001e\u00a2\u0006\u0004\bR\u0010SJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u0005\u0010\u0006J'\u0010\r\u001a\u00020\f2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\t\u001a\u00020\u00072\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ7\u0010\u0014\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\u00072\u0006\u0010\u0010\u001a\u00020\u00072\u0006\u0010\u0011\u001a\u00020\n2\u0006\u0010\u0012\u001a\u00020\u00072\u0006\u0010\u0013\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0018\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u0016H\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019J/\u0010 \u001a\u00020\u00042\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\n2\u0006\u0010\u001d\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020\u001eH\u0014\u00a2\u0006\u0004\b \u0010!J\u001d\u0010$\u001a\u00020\u00042\u0006\u0010\"\u001a\u00020\u001e2\u0006\u0010#\u001a\u00020\u001e\u00a2\u0006\u0004\b$\u0010%R\u0016\u0010'\u001a\u0004\u0018\u00010&8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b'\u0010(R\u0014\u0010)\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b)\u0010*R\u0017\u0010,\u001a\u00020+8\u0006\u00a2\u0006\f\n\u0004\b,\u0010-\u001a\u0004\b.\u0010/R\u0017\u00100\u001a\u00020+8\u0006\u00a2\u0006\f\n\u0004\b0\u0010-\u001a\u0004\b1\u0010/R\"\u00102\u001a\u00020\u001e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b2\u00103\u001a\u0004\b4\u00105\"\u0004\b6\u00107R\"\u00108\u001a\u00020\u001e8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b8\u00103\u001a\u0004\b9\u00105\"\u0004\b:\u00107R\u0017\u0010<\u001a\u00020;8\u0006\u00a2\u0006\f\n\u0004\b<\u0010=\u001a\u0004\b>\u0010?R/\u0010C\u001a\u001d\u0012\u0013\u0012\u00110\u0000\u00a2\u0006\f\bA\u0012\b\bB\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f0@8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bC\u0010DR\u0014\u0010E\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bE\u0010*R\"\u0010F\u001a\u00020\f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bF\u0010*\u001a\u0004\bF\u0010G\"\u0004\bH\u0010IR\u0014\u0010J\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bJ\u0010*R/\u0010K\u001a\u001d\u0012\u0013\u0012\u00110\u0000\u00a2\u0006\f\bA\u0012\b\bB\u0012\u0004\b\b(\u000b\u0012\u0004\u0012\u00020\f0@8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bK\u0010DR\u0014\u0010L\u001a\u00020&8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bL\u0010(R\u0014\u0010M\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bM\u0010*R\u0014\u0010O\u001a\u00020N8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bO\u0010PR\u0014\u0010Q\u001a\u00020\u001e8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\bQ\u00103\u00a8\u0006U"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/SummaryButton;", "Lnet/minecraft/client/gui/components/Button;", "Lnet/minecraft/client/gui/narration/NarrationElementOutput;", "builder", "", "appendDefaultNarrations", "(Lnet/minecraft/client/gui/narration/NarrationElementOutput;)V", "", "mouseX", "mouseY", "", "button", "", "mouseClicked", "(DDI)Z", "d", "e", "i", "f", "g", "mouseDragged", "(DDIDD)Z", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "pMouseX", "pMouseY", "", "pPartialTicks", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "x", "y", "setPosFloat", "(FF)V", "Lnet/minecraft/resources/ResourceLocation;", "activeResource", "Lnet/minecraft/resources/ResourceLocation;", "boldText", "Z", "", "buttonHeight", "Ljava/lang/Number;", "getButtonHeight", "()Ljava/lang/Number;", "buttonWidth", "getButtonWidth", "buttonX", "F", "getButtonX", "()F", "setButtonX", "(F)V", "buttonY", "getButtonY", "setButtonY", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "clickAction", "Lnet/minecraft/client/gui/components/Button$OnPress;", "getClickAction", "()Lnet/minecraft/client/gui/components/Button$OnPress;", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "clickRequirement", "Lkotlin/jvm/functions/Function1;", "hoverTexture", "isActive", "()Z", "setActive", "(Z)V", "largeText", "renderRequirement", "resource", "silent", "Lnet/minecraft/network/chat/MutableComponent;", "text", "Lnet/minecraft/network/chat/MutableComponent;", "textScale", "<init>", "(FFLjava/lang/Number;Ljava/lang/Number;Lnet/minecraft/client/gui/components/Button$OnPress;Lnet/minecraft/network/chat/MutableComponent;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;ZZZZF)V", "Companion", "common"})
public final class SummaryButton
extends Button {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private float buttonX;
    private float buttonY;
    @NotNull
    private final Number buttonWidth;
    @NotNull
    private final Number buttonHeight;
    @NotNull
    private final Button.OnPress clickAction;
    @NotNull
    private final MutableComponent text;
    @NotNull
    private final ResourceLocation resource;
    @Nullable
    private final ResourceLocation activeResource;
    @NotNull
    private final Function1<SummaryButton, Boolean> renderRequirement;
    @NotNull
    private final Function1<SummaryButton, Boolean> clickRequirement;
    private final boolean hoverTexture;
    private final boolean silent;
    private final boolean boldText;
    private final boolean largeText;
    private final float textScale;
    private boolean isActive;
    public static final int TEXT_HEIGHT = 9;

    public SummaryButton(float buttonX, float buttonY, @NotNull Number buttonWidth, @NotNull Number buttonHeight, @NotNull Button.OnPress clickAction, @NotNull MutableComponent text, @NotNull ResourceLocation resource, @Nullable ResourceLocation activeResource, @NotNull Function1<? super SummaryButton, Boolean> renderRequirement, @NotNull Function1<? super SummaryButton, Boolean> clickRequirement, boolean hoverTexture, boolean silent, boolean boldText, boolean largeText, float textScale) {
        Intrinsics.checkNotNullParameter((Object)buttonWidth, (String)"buttonWidth");
        Intrinsics.checkNotNullParameter((Object)buttonHeight, (String)"buttonHeight");
        Intrinsics.checkNotNullParameter((Object)clickAction, (String)"clickAction");
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Intrinsics.checkNotNullParameter((Object)resource, (String)"resource");
        Intrinsics.checkNotNullParameter(renderRequirement, (String)"renderRequirement");
        Intrinsics.checkNotNullParameter(clickRequirement, (String)"clickRequirement");
        super((int)buttonX, (int)buttonY, buttonWidth.intValue(), buttonHeight.intValue(), (Component)text, clickAction, Button.f_252438_);
        this.buttonX = buttonX;
        this.buttonY = buttonY;
        this.buttonWidth = buttonWidth;
        this.buttonHeight = buttonHeight;
        this.clickAction = clickAction;
        this.text = text;
        this.resource = resource;
        this.activeResource = activeResource;
        this.renderRequirement = renderRequirement;
        this.clickRequirement = clickRequirement;
        this.hoverTexture = hoverTexture;
        this.silent = silent;
        this.boldText = boldText;
        this.largeText = largeText;
        this.textScale = textScale;
    }

    public /* synthetic */ SummaryButton(float f, float f2, Number number, Number number2, Button.OnPress onPress, MutableComponent mutableComponent, ResourceLocation resourceLocation, ResourceLocation resourceLocation2, Function1 function1, Function1 function12, boolean bl, boolean bl2, boolean bl3, boolean bl4, float f3, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 0x20) != 0) {
            MutableComponent mutableComponent2 = Component.m_237119_();
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"empty()");
            mutableComponent = mutableComponent2;
        }
        if ((n & 0x80) != 0) {
            resourceLocation2 = null;
        }
        if ((n & 0x100) != 0) {
            function1 = 1.INSTANCE;
        }
        if ((n & 0x200) != 0) {
            function12 = 2.INSTANCE;
        }
        if ((n & 0x400) != 0) {
            bl = true;
        }
        if ((n & 0x800) != 0) {
            bl2 = false;
        }
        if ((n & 0x1000) != 0) {
            bl3 = true;
        }
        if ((n & 0x2000) != 0) {
            bl4 = true;
        }
        if ((n & 0x4000) != 0) {
            f3 = 1.0f;
        }
        this(f, f2, number, number2, onPress, mutableComponent, resourceLocation, resourceLocation2, (Function1<? super SummaryButton, Boolean>)function1, (Function1<? super SummaryButton, Boolean>)function12, bl, bl2, bl3, bl4, f3);
    }

    public final float getButtonX() {
        return this.buttonX;
    }

    public final void setButtonX(float f) {
        this.buttonX = f;
    }

    public final float getButtonY() {
        return this.buttonY;
    }

    public final void setButtonY(float f) {
        this.buttonY = f;
    }

    @NotNull
    public final Number getButtonWidth() {
        return this.buttonWidth;
    }

    @NotNull
    public final Number getButtonHeight() {
        return this.buttonHeight;
    }

    @NotNull
    public final Button.OnPress getClickAction() {
        return this.clickAction;
    }

    public final boolean isActive() {
        return this.isActive;
    }

    public final void setActive(boolean bl) {
        this.isActive = bl;
    }

    public boolean m_7979_(double d, double e, int i, double f, double g) {
        return false;
    }

    protected void m_168802_(@NotNull NarrationElementOutput builder) {
        Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
    }

    protected void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (!((Boolean)this.renderRequirement.invoke((Object)this)).booleanValue()) {
            return;
        }
        PoseStack matrices = context.m_280168_();
        ResourceLocation resourceLocation = this.isActive && this.activeResource != null ? this.activeResource : this.resource;
        float f = this.buttonX;
        float f2 = this.buttonY;
        Number number = this.buttonWidth;
        Number number2 = this.buttonHeight;
        Number number3 = this.hoverTexture && this.m_274382_() ? (Number)this.buttonHeight : (Number)0;
        Number number4 = this.hoverTexture ? (Number)Float.valueOf(this.buttonHeight.floatValue() * (float)2) : (Number)this.buttonHeight;
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, resourceLocation, Float.valueOf(f), Float.valueOf(f2), number2, number, null, number3, null, number4, null, null, null, null, null, false, 0.0f, 130368, null);
        RenderHelperKt.drawScaledText$default(context, this.largeText ? CobblemonResources.INSTANCE.getDEFAULT_LARGE() : null, this.boldText ? TextKt.bold(this.text) : this.text, Float.valueOf(this.buttonX + this.buttonWidth.floatValue() / (float)2), Float.valueOf(this.buttonY + this.buttonHeight.floatValue() / (float)2 - (float)4 * this.textScale), this.textScale, null, 0, 0, true, true, null, null, 6592, null);
    }

    public boolean m_6375_(double mouseX, double mouseY, int button) {
        if (((Boolean)this.clickRequirement.invoke((Object)this)).booleanValue()) {
            super.m_6375_(mouseX, mouseY, button);
        }
        return false;
    }

    public void m_7435_(@NotNull SoundManager soundManager) {
        Intrinsics.checkNotNullParameter((Object)soundManager, (String)"soundManager");
        if (!this.silent) {
            soundManager.m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)CobblemonSounds.GUI_CLICK, (float)1.0f));
        }
    }

    public final void setPosFloat(float x, float y) {
        this.m_252865_((int)x);
        this.m_253211_((int)y);
        this.buttonX = x;
        this.buttonY = y;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/SummaryButton$Companion;", "", "", "TEXT_HEIGHT", "I", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

