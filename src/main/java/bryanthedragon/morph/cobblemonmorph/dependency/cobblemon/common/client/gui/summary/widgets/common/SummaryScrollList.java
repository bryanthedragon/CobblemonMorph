/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.ObjectSelectionList
 *  net.minecraft.client.gui.components.ObjectSelectionList$Entry
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.summary.widgets.common;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0010\b&\u0018\u0000 0*\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u00028\u00000\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003:\u00010B'\u0012\u0006\u0010(\u001a\u00020\u0007\u0012\u0006\u0010+\u001a\u00020\u0007\u0012\u0006\u0010\"\u001a\u00020!\u0012\u0006\u0010-\u001a\u00020\u0007\u00a2\u0006\u0004\b.\u0010/J\u000f\u0010\u0005\u001a\u00020\u0004H\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u000f\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u000f\u0010\n\u001a\u00020\u0007H\u0014\u00a2\u0006\u0004\b\n\u0010\tJ'\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011J7\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u000b2\u0006\u0010\u0013\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J/\u0010\u001a\u001a\u00020\u00042\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u00072\u0006\u0010\u0019\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001c\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\u001d\u0010\u001eJ\u001f\u0010\u001f\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bH\u0002\u00a2\u0006\u0004\b\u001f\u0010 R\u0017\u0010\"\u001a\u00020!8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u0016\u0010&\u001a\u00020\u000f8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b&\u0010'R\u0017\u0010(\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b(\u0010)\u001a\u0004\b*\u0010\tR\u0017\u0010+\u001a\u00020\u00078\u0006\u00a2\u0006\f\n\u0004\b+\u0010)\u001a\u0004\b,\u0010\t\u00a8\u00061"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/common/SummaryScrollList;", "Lnet/minecraft/client/gui/widget/AlwaysSelectedEntryListWidget$Entry;", "T", "Lnet/minecraft/client/gui/components/ObjectSelectionList;", "", "correctSize", "()V", "", "getRowWidth", "()I", "getScrollbarPositionX", "", "mouseX", "mouseY", "button", "", "mouseClicked", "(DDI)Z", "deltaX", "deltaY", "mouseDragged", "(DDIDD)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "partialTicks", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "i", "scaleIt", "(I)I", "updateScrollingState", "(DD)V", "Lnet/minecraft/network/chat/MutableComponent;", "label", "Lnet/minecraft/network/chat/MutableComponent;", "getLabel", "()Lnet/minecraft/network/chat/MutableComponent;", "scrolling", "Z", "x", "I", "getX", "y", "getY", "slotHeight", "<init>", "(IILnet/minecraft/network/chat/MutableComponent;I)V", "Companion", "common"})
public abstract class SummaryScrollList<T extends ObjectSelectionList.Entry<T>>
extends ObjectSelectionList<T> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int x;
    private final int y;
    @NotNull
    private final MutableComponent label;
    private boolean scrolling;
    public static final int WIDTH = 108;
    public static final int HEIGHT = 114;
    public static final int SLOT_WIDTH = 91;
    @NotNull
    private static final ResourceLocation backgroundResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_scroll_background.png");
    @NotNull
    private static final ResourceLocation scrollOverlayResource = MiscUtilsKt.cobblemonResource("textures/gui/summary/summary_scroll_overlay.png");

    public SummaryScrollList(int x, int y, @NotNull MutableComponent label, int slotHeight) {
        Intrinsics.checkNotNullParameter((Object)label, (String)"label");
        super(Minecraft.m_91087_(), 108, 114, 0, 114, slotHeight);
        this.x = x;
        this.y = y;
        this.label = label;
        this.correctSize();
        this.m_93496_(false);
        this.m_93488_(false);
        this.m_93471_(false);
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    @NotNull
    public final MutableComponent getLabel() {
        return this.label;
    }

    public int m_5759_() {
        return 91;
    }

    protected int m_5756_() {
        return this.f_93393_ + this.f_93388_ - 3;
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float partialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        PoseStack matrices = context.m_280168_();
        this.correctSize();
        Intrinsics.checkNotNullExpressionValue((Object)matrices, (String)"matrices");
        GuiUtilsKt.blitk$default(matrices, backgroundResource, this.f_93393_, this.f_93390_, 114, 108, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        context.m_280588_(this.f_93393_, this.f_93390_ + 1, this.f_93393_ + this.f_93388_, this.f_93390_ + 1 + this.f_93389_);
        super.m_88315_(context, mouseX, mouseY, partialTicks);
        context.m_280618_();
        int scrollOverlayOffset = 4;
        GuiUtilsKt.blitk$default(matrices, scrollOverlayResource, this.f_93393_, this.f_93390_ - scrollOverlayOffset / 2, 114 + scrollOverlayOffset, 108, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(this.label), (double)this.f_93393_ + 32.5, (double)this.f_93390_ - 13.5, 0.0f, null, 0, 0, true, true, null, null, 6624, null);
    }

    public boolean m_6375_(double mouseX, double mouseY, int button) {
        this.updateScrollingState(mouseX, mouseY);
        if (this.scrolling) {
            this.m_7522_((GuiEventListener)this.m_93412_(mouseX, mouseY));
            this.m_7897_(true);
        }
        return super.m_6375_(mouseX, mouseY, button);
    }

    public boolean m_7979_(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.scrolling) {
            if (mouseY < (double)this.f_93390_) {
                this.m_93410_(0.0);
            } else if (mouseY > (double)this.f_93391_) {
                this.m_93410_(this.m_93518_());
            } else {
                this.m_93410_(this.m_93517_() + deltaY);
            }
        }
        return super.m_7979_(mouseX, mouseY, button, deltaX, deltaY);
    }

    private final void updateScrollingState(double mouseX, double mouseY) {
        this.scrolling = mouseX >= (double)this.m_5756_() && mouseX < (double)(this.m_5756_() + 3) && mouseY >= (double)this.f_93390_ && mouseY < (double)this.f_93391_;
    }

    private final void correctSize() {
        this.m_93437_(108, 114, this.y + 1, this.y + 1 + 112);
        this.m_93507_(this.x);
    }

    private final int scaleIt(int i) {
        return (int)(this.f_93386_.m_91268_().m_85449_() * (double)i);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000b\u0010\fR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u00078\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\n\u0010\t\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/client/gui/summary/widgets/common/SummaryScrollList$Companion;", "", "", "HEIGHT", "I", "SLOT_WIDTH", "WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "backgroundResource", "Lnet/minecraft/resources/ResourceLocation;", "scrollOverlayResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

