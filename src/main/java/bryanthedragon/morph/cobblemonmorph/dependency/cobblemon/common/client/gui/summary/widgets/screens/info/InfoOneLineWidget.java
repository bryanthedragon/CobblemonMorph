/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.info;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.SoundlessWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.screens.info.InfoBlockWidget;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B9\u0012\u0006\u0010\u0010\u001a\u00020\u0004\u0012\u0006\u0010\u0011\u001a\u00020\u0004\u0012\u0006\u0010\u0012\u001a\u00020\u0004\u0012\b\b\u0002\u0010\u0013\u001a\u00020\u0004\u0012\u0006\u0010\r\u001a\u00020\f\u0012\u0006\u0010\u000f\u001a\u00020\f\u00a2\u0006\u0004\b\u0014\u0010\u0015J/\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0007H\u0014\u00a2\u0006\u0004\b\n\u0010\u000bR\u0014\u0010\r\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u000e\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/info/InfoOneLineWidget;", "Lcom/cobblemon/mod/common/client/gui/summary/widgets/SoundlessWidget;", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "pMouseX", "pMouseY", "", "pPartialTicks", "", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lnet/minecraft/network/chat/MutableComponent;", "label", "Lnet/minecraft/network/chat/MutableComponent;", "value", "pX", "pY", "width", "height", "<init>", "(IIIILnet/minecraft/network/chat/MutableComponent;Lnet/minecraft/network/chat/MutableComponent;)V", "Companion", "common"})
public final class InfoOneLineWidget
extends SoundlessWidget {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final MutableComponent label;
    @NotNull
    private final MutableComponent value;
    @NotNull
    private static final ResourceLocation FONT = CobblemonResources.INSTANCE.getDEFAULT_LARGE();
    private static final int ROW_HEIGHT = 15;
    private static final int WITHIN_ROW_VERTICAL_OFFSET = 6;
    private static final int LABEL_HORIZONTAL_OFFSET = 8;
    private static final int VALUE_HORIZONTAL_OFFSET = 53;

    public InfoOneLineWidget(int pX, int pY, int width, int height, @NotNull MutableComponent label, @NotNull MutableComponent value2) {
        Intrinsics.checkNotNullParameter((Object)label, (String)"label");
        Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
        MutableComponent mutableComponent = Component.m_237113_((String)"InfoOneLineWidget");
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"InfoOneLineWidget\")");
        super(pX, pY, width, height, (Component)mutableComponent);
        this.label = label;
        this.value = value2;
    }

    public /* synthetic */ InfoOneLineWidget(int n, int n2, int n3, int n4, MutableComponent mutableComponent, MutableComponent mutableComponent2, int n5, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n5 & 8) != 0) {
            n4 = 15;
        }
        this(n, n2, n3, n4, mutableComponent, mutableComponent2);
    }

    protected void m_87963_(@NotNull GuiGraphics context, int pMouseX, int pMouseY, float pPartialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        int n = this.m_252754_() + 8;
        int n2 = this.m_252907_();
        int n3 = Minecraft.m_91087_().f_91062_.m_92852_((FormattedText)this.label);
        int n4 = this.f_93619_;
        MutableComponent mutableComponent = this.label;
        ResourceLocation resourceLocation = FONT;
        InfoBlockWidget label = new InfoBlockWidget(n, n2, n3, n4, mutableComponent, 6, resourceLocation);
        label.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
        n2 = this.m_252754_() + 53;
        n3 = this.m_252907_();
        n4 = Minecraft.m_91087_().f_91062_.m_92852_((FormattedText)this.value);
        int n5 = this.f_93619_;
        resourceLocation = this.value;
        ResourceLocation resourceLocation2 = FONT;
        InfoBlockWidget value2 = new InfoBlockWidget(n2, n3, n4, n5, (MutableComponent)resourceLocation, 6, resourceLocation2);
        value2.m_88315_(context, pMouseX, pMouseY, pPartialTicks);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fR\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00058\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0007R\u0014\u0010\t\u001a\u00020\u00058\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0007R\u0014\u0010\n\u001a\u00020\u00058\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\n\u0010\u0007\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/screens/info/InfoOneLineWidget$Companion;", "", "Lnet/minecraft/resources/ResourceLocation;", "FONT", "Lnet/minecraft/resources/ResourceLocation;", "", "LABEL_HORIZONTAL_OFFSET", "I", "ROW_HEIGHT", "VALUE_HORIZONTAL_OFFSET", "WITHIN_ROW_VERTICAL_OFFSET", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

