/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.IntRange
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.Renderable
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.network.chat.Component
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.IntRange;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\f\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0004\n\u0002\b\t\n\u0002\u0010\u0006\n\u0002\b\u0011\n\u0002\u0010!\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u00012\u00020\u0002B/\u0012\u0006\u00104\u001a\u00020\u0010\u0012\u0006\u00105\u001a\u00020\u0010\u0012\u0006\u00106\u001a\u00020\u0010\u0012\u0006\u00107\u001a\u00020\u0010\u0012\u0006\u00109\u001a\u000208\u00a2\u0006\u0004\b:\u0010;J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0004\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u0017\u0010\n\u001a\u00020\u00052\u0006\u0010\t\u001a\u00020\bH\u0014\u00a2\u0006\u0004\b\n\u0010\u000bJ\u0017\u0010\r\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\bH\u0014\u00a2\u0006\u0004\b\r\u0010\u000bJ\u001f\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u0013\u0010\u0014J\u001d\u0010\u0018\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0017\u001a\u00020\u0015\u00a2\u0006\u0004\b\u0018\u0010\u0019J'\u0010\u001c\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ'\u0010\u001e\u001a\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u00102\u0006\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001dJ'\u0010#\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b#\u0010$J7\u0010(\u001a\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u001f2\u0006\u0010\u0017\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020\u00102\u0006\u0010&\u001a\u00020\u001f2\u0006\u0010'\u001a\u00020\u001fH\u0016\u00a2\u0006\u0004\b(\u0010)J\u001f\u0010*\u001a\u00020\u00052\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001fH\u0016\u00a2\u0006\u0004\b*\u0010+J'\u0010,\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020\u0010H\u0016\u00a2\u0006\u0004\b,\u0010$J'\u0010.\u001a\u00020\u00122\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001f2\u0006\u0010-\u001a\u00020\u001fH\u0016\u00a2\u0006\u0004\b.\u0010/J\u0017\u00100\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0003H\u0004\u00a2\u0006\u0004\b0\u0010\u0007R\u001a\u00102\u001a\b\u0012\u0004\u0012\u00020\u0003018\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b2\u00103\u00a8\u0006<"}, d2={"Lcom/cobblemon/mod/common/api/gui/ParentWidget;", "Lnet/minecraft/client/gui/components/Renderable;", "Lnet/minecraft/client/gui/components/AbstractWidget;", "Lnet/minecraft/client/gui/components/events/GuiEventListener;", "widget", "", "addWidget", "(Lnet/minecraft/client/gui/components/events/GuiEventListener;)V", "Lnet/minecraft/client/gui/narration/NarrationElementOutput;", "builder", "appendClickableNarrations", "(Lnet/minecraft/client/gui/narration/NarrationElementOutput;)V", "pNarrationElementOutput", "appendDefaultNarrations", "", "pCodePoint", "", "pModifiers", "", "charTyped", "(CI)Z", "", "mouseX", "mouseY", "ishHovered", "(Ljava/lang/Number;Ljava/lang/Number;)Z", "pKeyCode", "pScanCode", "keyPressed", "(III)Z", "keyReleased", "", "pMouseX", "pMouseY", "pButton", "mouseClicked", "(DDI)Z", "button", "f", "g", "mouseDragged", "(DDIDD)Z", "mouseMoved", "(DD)V", "mouseReleased", "pDelta", "mouseScrolled", "(DDD)Z", "removeWidget", "", "children", "Ljava/util/List;", "pX", "pY", "pWidth", "pHeight", "Lnet/minecraft/network/chat/Component;", "component", "<init>", "(IIIILnet/minecraft/network/chat/Component;)V", "common"})
@SourceDebugExtension(value={"SMAP\nParentWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ParentWidget.kt\ncom/cobblemon/mod/common/api/gui/ParentWidget\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,111:1\n1855#2,2:112\n1747#2,3:114\n1747#2,3:117\n1747#2,3:120\n1747#2,3:123\n1747#2,3:126\n1855#2,2:129\n1855#2,2:131\n*S KotlinDebug\n*F\n+ 1 ParentWidget.kt\ncom/cobblemon/mod/common/api/gui/ParentWidget\n*L\n44#1:112,2\n51#1:114,3\n57#1:117,3\n63#1:120,3\n69#1:123,3\n75#1:126,3\n81#1:129,2\n88#1:131,2\n*E\n"})
public abstract class ParentWidget
extends AbstractWidget
implements Renderable {
    @NotNull
    private final List<GuiEventListener> children;

    public ParentWidget(int pX, int pY, int pWidth, int pHeight, @NotNull Component component) {
        Intrinsics.checkNotNullParameter((Object)component, (String)"component");
        super(pX, pY, pWidth, pHeight, component);
        this.children = new ArrayList();
    }

    protected final void addWidget(@NotNull GuiEventListener widget) {
        Intrinsics.checkNotNullParameter((Object)widget, (String)"widget");
        this.children.add(widget);
    }

    protected final void removeWidget(@NotNull GuiEventListener widget) {
        Intrinsics.checkNotNullParameter((Object)widget, (String)"widget");
        this.children.remove(widget);
    }

    public void m_94757_(double pMouseX, double pMouseY) {
        Iterable $this$forEach$iv = this.children;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            GuiEventListener it = (GuiEventListener)element$iv;
            boolean bl = false;
            it.m_94757_(pMouseX, pMouseY);
        }
        super.m_94757_(pMouseX, pMouseY);
    }

    public boolean m_6050_(double pMouseX, double pMouseY, double pDelta) {
        boolean bl;
        block3: {
            Iterable $this$any$iv = this.children;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    GuiEventListener it = (GuiEventListener)element$iv;
                    boolean bl2 = false;
                    if (!it.m_6050_(pMouseX, pMouseY, pDelta)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl || super.m_6050_(pMouseX, pMouseY, pDelta);
    }

    public boolean m_6375_(double pMouseX, double pMouseY, int pButton) {
        boolean bl;
        block3: {
            Iterable $this$any$iv = this.children;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    GuiEventListener it = (GuiEventListener)element$iv;
                    boolean bl2 = false;
                    if (!it.m_6375_(pMouseX, pMouseY, pButton)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    public boolean m_6348_(double pMouseX, double pMouseY, int pButton) {
        boolean bl;
        block3: {
            Iterable $this$any$iv = this.children;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    GuiEventListener it = (GuiEventListener)element$iv;
                    boolean bl2 = false;
                    if (!it.m_6348_(pMouseX, pMouseY, pButton)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl || super.m_6348_(pMouseX, pMouseY, pButton);
    }

    public boolean m_7979_(double mouseX, double mouseY, int button, double f, double g) {
        boolean bl;
        block3: {
            Iterable $this$any$iv = this.children;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    GuiEventListener it = (GuiEventListener)element$iv;
                    boolean bl2 = false;
                    if (!it.m_7979_(mouseX, mouseY, button, f, g)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl;
    }

    public boolean m_7933_(int pKeyCode, int pScanCode, int pModifiers) {
        boolean bl;
        block3: {
            Iterable $this$any$iv = this.children;
            boolean $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                bl = false;
            } else {
                for (Object element$iv : $this$any$iv) {
                    GuiEventListener it = (GuiEventListener)element$iv;
                    boolean bl2 = false;
                    if (!it.m_7933_(pKeyCode, pScanCode, pModifiers)) continue;
                    bl = true;
                    break block3;
                }
                bl = false;
            }
        }
        return bl || super.m_7933_(pKeyCode, pScanCode, pModifiers);
    }

    public boolean m_7920_(int pKeyCode, int pScanCode, int pModifiers) {
        Iterable $this$forEach$iv = this.children;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            GuiEventListener it = (GuiEventListener)element$iv;
            boolean bl = false;
            it.m_7920_(pKeyCode, pScanCode, pModifiers);
        }
        return super.m_7920_(pKeyCode, pScanCode, pModifiers);
    }

    public boolean m_5534_(char pCodePoint, int pModifiers) {
        Iterable $this$forEach$iv = this.children;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            GuiEventListener it = (GuiEventListener)element$iv;
            boolean bl = false;
            it.m_5534_(pCodePoint, pModifiers);
        }
        return super.m_5534_(pCodePoint, pModifiers);
    }

    protected void m_168802_(@NotNull NarrationElementOutput pNarrationElementOutput) {
        Intrinsics.checkNotNullParameter((Object)pNarrationElementOutput, (String)"pNarrationElementOutput");
    }

    public final boolean ishHovered(@NotNull Number mouseX, @NotNull Number mouseY) {
        Intrinsics.checkNotNullParameter((Object)mouseX, (String)"mouseX");
        Intrinsics.checkNotNullParameter((Object)mouseY, (String)"mouseY");
        return CollectionsKt.contains((Iterable)((Iterable)new IntRange(this.m_252754_(), this.m_252754_() + this.f_93618_)), (Object)mouseX) && CollectionsKt.contains((Iterable)((Iterable)new IntRange(this.m_252907_(), this.m_252907_() + this.f_93619_)), (Object)mouseY);
    }

    protected void m_168797_(@NotNull NarrationElementOutput builder) {
        Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
    }
}

