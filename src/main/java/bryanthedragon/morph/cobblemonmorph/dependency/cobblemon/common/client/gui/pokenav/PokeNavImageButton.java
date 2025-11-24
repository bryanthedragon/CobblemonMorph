/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button$OnPress
 *  net.minecraft.client.gui.components.ImageButton
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pokenav;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u00002\u00020\u0001B\u0087\u0001\u0012\u0006\u0010\u001b\u001a\u00020\u0004\u0012\u0006\u0010\u001f\u001a\u00020\u0004\u0012\u0006\u0010'\u001a\u00020\u0004\u0012\u0006\u0010(\u001a\u00020\u0004\u0012\u0006\u0010)\u001a\u00020\u0004\u0012\u0006\u0010*\u001a\u00020\u0004\u0012\u0006\u0010+\u001a\u00020\u0004\u0012\u0006\u0010,\u001a\u00020\u0004\u0012\u0006\u0010-\u001a\u00020\u0004\u0012\u0006\u0010\"\u001a\u00020!\u0012\u0006\u0010.\u001a\u00020\u0004\u0012\u0006\u0010/\u001a\u00020\u0004\u0012\u0006\u0010\u000f\u001a\u000200\u0012\u0006\u0010%\u001a\u00020$\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u0019\u00a2\u0006\u0004\b1\u00102J/\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0014\u00a2\u0006\u0004\b\n\u0010\u000bJ\r\u0010\r\u001a\u00020\f\u00a2\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u000f\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0013\u001a\u00020\t2\u0006\u0010\u0012\u001a\u00020\u0011H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J/\u0010\u0017\u001a\u00020\t2\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018R\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\f0\u00198\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u001aR\u0017\u0010\u001b\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001eR\u0017\u0010\u001f\u001a\u00020\u00048\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010\u001c\u001a\u0004\b \u0010\u001eR\u0014\u0010\"\u001a\u00020!8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\"\u0010#R\u0014\u0010%\u001a\u00020$8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b%\u0010&\u00a8\u00063"}, d2={"Lcom/cobblemon/mod/common/client/gui/pokenav/PokeNavImageButton;", "Lnet/minecraft/client/gui/components/ImageButton;", "Lcom/mojang/blaze3d/vertex/PoseStack;", "pMatrixStack", "", "pMouseX", "pMouseY", "", "pPartialTicks", "", "applyBlitk", "(Lcom/mojang/blaze3d/vertex/PoseStack;IIF)V", "", "canClick", "()Z", "onPress", "()V", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lkotlin/Function0;", "Lkotlin/jvm/functions/Function0;", "posX", "I", "getPosX", "()I", "posY", "getPosY", "Lnet/minecraft/resources/ResourceLocation;", "resourceLocation", "Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/network/chat/MutableComponent;", "text", "Lnet/minecraft/network/chat/MutableComponent;", "pX", "pY", "pWidth", "pHeight", "pXTexStart", "pYTexStart", "pYDiffText", "pTextureWidth", "pTextureHeight", "Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;", "<init>", "(IIIIIIIIILnet/minecraft/resources/ResourceLocation;IILnet/minecraft/client/gui/components/Button$OnPress;Lnet/minecraft/network/chat/MutableComponent;Lkotlin/jvm/functions/Function0;)V", "common"})
public class PokeNavImageButton
extends ImageButton {
    private final int posX;
    private final int posY;
    @NotNull
    private final ResourceLocation resourceLocation;
    @NotNull
    private final MutableComponent text;
    @NotNull
    private final Function0<Boolean> canClick;

    public PokeNavImageButton(int posX, int posY, int pX, int pY, int pWidth, int pHeight, int pXTexStart, int pYTexStart, int pYDiffText, @NotNull ResourceLocation resourceLocation, int pTextureWidth, int pTextureHeight, @NotNull Button.OnPress onPress, @NotNull MutableComponent text, @NotNull Function0<Boolean> canClick) {
        Intrinsics.checkNotNullParameter((Object)resourceLocation, (String)"resourceLocation");
        Intrinsics.checkNotNullParameter((Object)onPress, (String)"onPress");
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Intrinsics.checkNotNullParameter(canClick, (String)"canClick");
        super(pX, pY, pWidth, pHeight, pXTexStart, pYTexStart, pYDiffText, resourceLocation, pTextureWidth, pTextureHeight, onPress);
        this.posX = posX;
        this.posY = posY;
        this.resourceLocation = resourceLocation;
        this.text = text;
        this.canClick = canClick;
    }

    public /* synthetic */ PokeNavImageButton(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, ResourceLocation resourceLocation, int n10, int n11, Button.OnPress onPress, MutableComponent mutableComponent, Function0 function0, int n12, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n12 & 0x4000) != 0) {
            function0 = 1.INSTANCE;
        }
        this(n, n2, n3, n4, n5, n6, n7, n8, n9, resourceLocation, n10, n11, onPress, mutableComponent, (Function0<Boolean>)function0);
    }

    public final int getPosX() {
        return this.posX;
    }

    public final int getPosY() {
        return this.posY;
    }

    public void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack poseStack = context.m_280168_();
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"context.matrices");
        this.applyBlitk(poseStack, pMouseX, pMouseY, pPartialTicks);
        ResourceLocation resourceLocation = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
        MutableComponent mutableComponent = TextKt.bold(this.text);
        int n = this.m_252754_() + this.f_93618_ / 2;
        int n2 = this.m_252907_() + this.f_93619_ + 3;
        RenderHelperKt.drawScaledText$default(context, resourceLocation, mutableComponent, n, n2, 0.0f, null, 0, 0xFFFFFF, true, false, null, null, 6368, null);
    }

    public final boolean canClick() {
        return (Boolean)this.canClick.invoke();
    }

    public void m_5691_() {
        if (this.canClick()) {
            super.m_5691_();
        }
    }

    public void m_7435_(@NotNull SoundManager soundManager) {
        Intrinsics.checkNotNullParameter((Object)soundManager, (String)"soundManager");
        if (this.canClick()) {
            super.m_7435_(soundManager);
        }
    }

    protected void applyBlitk(@NotNull PoseStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)pMatrixStack, (String)"pMatrixStack");
        ResourceLocation resourceLocation = this.resourceLocation;
        int n = this.m_252754_();
        double d = (double)this.m_252907_() + 0.25;
        int n2 = this.f_93618_;
        int n3 = this.f_93619_;
        GuiUtilsKt.blitk$default(pMatrixStack, resourceLocation, n, d, n3, n2, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
    }
}

