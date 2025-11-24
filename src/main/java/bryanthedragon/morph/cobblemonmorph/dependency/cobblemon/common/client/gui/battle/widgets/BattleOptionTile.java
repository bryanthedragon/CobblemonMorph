/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Renderable
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.narration.NarratableEntry
 *  net.minecraft.client.gui.narration.NarratableEntry$NarrationPriority
 *  net.minecraft.client.gui.narration.NarratedElementType
 *  net.minecraft.client.gui.narration.NarrationElementOutput
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.widgets;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 >2\u00020\u00012\u00020\u00022\u00020\u0003:\u0001>B=\u0012\u0006\u0010\"\u001a\u00020!\u0012\u0006\u00106\u001a\u00020\u0014\u0012\u0006\u0010:\u001a\u00020\u0014\u0012\u0006\u0010-\u001a\u00020,\u0012\u0006\u00102\u001a\u000201\u0012\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00060'\u00a2\u0006\u0004\b<\u0010=J\u0017\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u001d\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f\u00a2\u0006\u0004\b\u0012\u0010\u0013J'\u0010\u0016\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u0014H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J/\u0010\u001c\u001a\u00020\u00062\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\u0010\u001a\u00020\u00142\u0006\u0010\u0011\u001a\u00020\u00142\u0006\u0010\u001b\u001a\u00020\u001aH\u0016\u00a2\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010\u001f\u001a\u00020\u00062\u0006\u0010\u001e\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\u001f\u0010 R\u0017\u0010\"\u001a\u00020!8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%R\u0016\u0010\u001e\u001a\u00020\f8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b\u001e\u0010&R\u001d\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00060'8\u0006\u00a2\u0006\f\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+R\u0017\u0010-\u001a\u00020,8\u0006\u00a2\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b/\u00100R\u0017\u00102\u001a\u0002018\u0006\u00a2\u0006\f\n\u0004\b2\u00103\u001a\u0004\b4\u00105R\u0017\u00106\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b6\u00107\u001a\u0004\b8\u00109R\u0017\u0010:\u001a\u00020\u00148\u0006\u00a2\u0006\f\n\u0004\b:\u00107\u001a\u0004\b;\u00109\u00a8\u0006?"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleOptionTile;", "Lnet/minecraft/client/gui/components/Renderable;", "Lnet/minecraft/client/gui/components/events/GuiEventListener;", "Lnet/minecraft/client/gui/narration/NarratableEntry;", "Lnet/minecraft/client/gui/narration/NarrationElementOutput;", "builder", "", "appendNarrations", "(Lnet/minecraft/client/gui/narration/NarrationElementOutput;)V", "Lnet/minecraft/client/gui/Selectable$SelectionType;", "getType", "()Lnet/minecraft/client/gui/narration/NarratableEntry$NarrationPriority;", "", "isFocused", "()Z", "", "mouseX", "mouseY", "isHovered", "(DD)Z", "", "button", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "focused", "setFocused", "(Z)V", "Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;", "battleGUI", "Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;", "getBattleGUI", "()Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;", "Z", "Lkotlin/Function0;", "onClick", "Lkotlin/jvm/functions/Function0;", "getOnClick", "()Lkotlin/jvm/functions/Function0;", "Lnet/minecraft/resources/ResourceLocation;", "resource", "Lnet/minecraft/resources/ResourceLocation;", "getResource", "()Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/network/chat/MutableComponent;", "text", "Lnet/minecraft/network/chat/MutableComponent;", "getText", "()Lnet/minecraft/network/chat/MutableComponent;", "x", "I", "getX", "()I", "y", "getY", "<init>", "(Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;IILnet/minecraft/resources/ResourceLocation;Lnet/minecraft/network/chat/MutableComponent;Lkotlin/jvm/functions/Function0;)V", "Companion", "common"})
public final class BattleOptionTile
implements Renderable,
GuiEventListener,
NarratableEntry {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final BattleGUI battleGUI;
    private final int x;
    private final int y;
    @NotNull
    private final ResourceLocation resource;
    @NotNull
    private final MutableComponent text;
    @NotNull
    private final Function0<Unit> onClick;
    private boolean focused;
    public static final int OPTION_WIDTH = 90;
    public static final int OPTION_HEIGHT = 26;

    public BattleOptionTile(@NotNull BattleGUI battleGUI, int x, int y, @NotNull ResourceLocation resource, @NotNull MutableComponent text, @NotNull Function0<Unit> onClick) {
        Intrinsics.checkNotNullParameter((Object)((Object)battleGUI), (String)"battleGUI");
        Intrinsics.checkNotNullParameter((Object)resource, (String)"resource");
        Intrinsics.checkNotNullParameter((Object)text, (String)"text");
        Intrinsics.checkNotNullParameter(onClick, (String)"onClick");
        this.battleGUI = battleGUI;
        this.x = x;
        this.y = y;
        this.resource = resource;
        this.text = text;
        this.onClick = onClick;
    }

    @NotNull
    public final BattleGUI getBattleGUI() {
        return this.battleGUI;
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    @NotNull
    public final ResourceLocation getResource() {
        return this.resource;
    }

    @NotNull
    public final MutableComponent getText() {
        return this.text;
    }

    @NotNull
    public final Function0<Unit> getOnClick() {
        return this.onClick;
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        double opacity = CobblemonClient.INSTANCE.getBattleOverlay().getOpacityRatio();
        if (opacity < 0.1) {
            return;
        }
        PoseStack poseStack = context.m_280168_();
        ResourceLocation resourceLocation = this.resource;
        int n = this.x;
        int n2 = this.y;
        int n3 = this.isHovered(mouseX, mouseY) ? 26 : 0;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, n, n2, 26, 90, null, n3, null, 52, null, null, null, null, opacity, false, 0.0f, 113984, null);
        float scale = 1.0f;
        resourceLocation = this.text;
        n = this.x + 6;
        n2 = this.y + 8;
        RenderHelperKt.drawScaledText$default(context, null, (MutableComponent)resourceLocation, n, n2, scale, opacity, 0, 0, false, true, null, null, 7042, null);
    }

    public boolean m_6375_(double mouseX, double mouseY, int button) {
        if (mouseX < (double)this.x || mouseY < (double)this.y || mouseX > (double)(this.x + 90) || mouseY > (double)(this.y + 26)) {
            return false;
        }
        this.onClick.invoke();
        return true;
    }

    public void m_93692_(boolean focused) {
        this.focused = focused;
    }

    public boolean m_93696_() {
        return this.focused;
    }

    public final boolean isHovered(double mouseX, double mouseY) {
        return mouseX > (double)this.x && mouseY > (double)this.y && mouseX < (double)(this.x + 90) && mouseY < (double)(this.y + 26);
    }

    public void m_142291_(@NotNull NarrationElementOutput builder) {
        Intrinsics.checkNotNullParameter((Object)builder, (String)"builder");
        builder.m_169146_(NarratedElementType.TITLE, (Component)this.text);
    }

    @NotNull
    public NarratableEntry.NarrationPriority m_142684_() {
        return NarratableEntry.NarrationPriority.HOVERED;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleOptionTile$Companion;", "", "", "OPTION_HEIGHT", "I", "OPTION_WIDTH", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

