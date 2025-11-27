/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PasturePCGUIConfiguration;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.PasturePokemonScrollList;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pasture.RecallButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.pc.StorageWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.pasture.UnpastureAllPokemonPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 )2\u00020\u0001:\u0001)B'\u0012\u0006\u0010!\u001a\u00020 \u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010%\u001a\u00020\u0005\u0012\u0006\u0010&\u001a\u00020\u0005\u00a2\u0006\u0004\b'\u0010(J'\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ/\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u000eH\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0019\u001a\u00020\u00188\u0006\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001e\u001a\u00020\u001d8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u001e\u0010\u001fR\u0017\u0010!\u001a\u00020 8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/client/gui/pasture/PastureWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/SoundlessWidget;", "", "pMouseX", "pMouseY", "", "pButton", "", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "mouseX", "mouseY", "", "delta", "", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/client/gui/pasture/PasturePCGUIConfiguration;", "pasturePCGUIConfiguration", "Lcom/cobblemon/mod/common/client/gui/pasture/PasturePCGUIConfiguration;", "getPasturePCGUIConfiguration", "()Lcom/cobblemon/mod/common/client/gui/pasture/PasturePCGUIConfiguration;", "Lcom/cobblemon/mod/common/client/gui/pasture/PasturePokemonScrollList;", "pastureScrollList", "Lcom/cobblemon/mod/common/client/gui/pasture/PasturePokemonScrollList;", "getPastureScrollList", "()Lcom/cobblemon/mod/common/client/gui/pasture/PasturePokemonScrollList;", "Lcom/cobblemon/mod/common/client/gui/pasture/RecallButton;", "recallButton", "Lcom/cobblemon/mod/common/client/gui/pasture/RecallButton;", "Lcom/cobblemon/mod/common/client/gui/pc/StorageWidget;", "storageWidget", "Lcom/cobblemon/mod/common/client/gui/pc/StorageWidget;", "getStorageWidget", "()Lcom/cobblemon/mod/common/client/gui/pc/StorageWidget;", "x", "y", "<init>", "(Lcom/cobblemon/mod/common/client/gui/pc/StorageWidget;Lcom/cobblemon/mod/common/client/gui/pasture/PasturePCGUIConfiguration;II)V", "Companion", "common"})
public final class PastureWidget
extends SoundlessWidget {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final StorageWidget storageWidget;
    @NotNull
    private final PasturePCGUIConfiguration pasturePCGUIConfiguration;
    @NotNull
    private final RecallButton recallButton;
    @NotNull
    private final PasturePokemonScrollList pastureScrollList;
    @NotNull
    private static final ResourceLocation baseResource = MiscUtils.cobblemonResource("textures/gui/pasture/pasture_panel.png");

    public PastureWidget(@NotNull StorageWidget storageWidget, @NotNull PasturePCGUIConfiguration pasturePCGUIConfiguration, int x, int y) {
        Intrinsics.checkNotNullParameter((Object)((Object)storageWidget), (String)"storageWidget");
        Intrinsics.checkNotNullParameter((Object)pasturePCGUIConfiguration, (String)"pasturePCGUIConfiguration");
        MutableComponent mutableComponent = Component.m_237113_((String)"PastureWidget");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"PastureWidget\")");
        super(x, y, 82, 169, (Component)mutableComponent);
        this.storageWidget = storageWidget;
        this.pasturePCGUIConfiguration = pasturePCGUIConfiguration;
        this.recallButton = new RecallButton(x + 6, y + 153, arg_0 -> PastureWidget.recallButton$lambda$0(this, arg_0));
        this.pastureScrollList = new PasturePokemonScrollList(x + 6, y + 31, this);
    }

    @NotNull
    public final StorageWidget getStorageWidget() {
        return this.storageWidget;
    }

    @NotNull
    public final PasturePCGUIConfiguration getPasturePCGUIConfiguration() {
        return this.pasturePCGUIConfiguration;
    }

    @NotNull
    public final PasturePokemonScrollList getPastureScrollList() {
        return this.pastureScrollList;
    }

    public void m_87963_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack poseStack = context.m_280168_();
        ResourceLocation resourceLocation = baseResource;
        int n = this.m_252754_();
        int n2 = this.m_252907_();
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, n, n2, 169, 82, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        ResourceLocation resourceLocation2 = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
        MutableComponent mutableComponent = LocalizationUtilsKt.lang("ui.pasture", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"lang(\"ui.pasture\")");
        RenderHelperKt.drawScaledText$default(context, resourceLocation2, TextKt.bold(mutableComponent), (double)this.m_252754_() + 31.5, (double)this.m_252907_() + 3.5, 0.0f, null, 0, 0, true, false, null, null, 7648, null);
        this.pastureScrollList.m_88315_(context, mouseX, mouseY, delta);
        this.recallButton.m_88315_(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean m_6375_(double pMouseX, double pMouseY, int pButton) {
        if (this.recallButton.isHovered(pMouseX, pMouseY)) {
            this.recallButton.m_6375_(pMouseX, pMouseY, pButton);
        }
        if (this.pastureScrollList.isHovered(pMouseX, pMouseY)) {
            this.pastureScrollList.m_6375_(pMouseX, pMouseY, pButton);
        }
        return super.m_6375_(pMouseX, pMouseY, pButton);
    }

    private static final void recallButton$lambda$0(PastureWidget this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        this$0.storageWidget.getPcGui().playSound(CobblemonSounds.PC_CLICK);
        new UnpastureAllPokemonPacket(this$0.pasturePCGUIConfiguration.getPastureId()).sendToServer();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/client/gui/pasture/PastureWidget$Companion;", "", "Lnet/minecraft/resources/ResourceLocation;", "baseResource", "Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

