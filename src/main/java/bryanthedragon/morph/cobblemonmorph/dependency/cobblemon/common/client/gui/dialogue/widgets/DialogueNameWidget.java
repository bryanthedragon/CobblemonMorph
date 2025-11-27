/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Renderable
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.util.FormattedCharSequence
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.widgets;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u0000 $2\u00020\u00012\u00020\u0002:\u0001$B1\u0012\u0006\u0010\u001e\u001a\u00020\b\u0012\u0006\u0010 \u001a\u00020\b\u0012\u0006\u0010\u001c\u001a\u00020\b\u0012\u0006\u0010\u0013\u001a\u00020\b\u0012\b\u0010\u0018\u001a\u0004\u0018\u00010\u0017\u00a2\u0006\u0004\b\"\u0010#J\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J/\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0013\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u0019\u0010\u0018\u001a\u0004\u0018\u00010\u00178\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001c\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u0014\u001a\u0004\b\u001d\u0010\u0016R\u0017\u0010\u001e\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u0014\u001a\u0004\b\u001f\u0010\u0016R\u0017\u0010 \u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b \u0010\u0014\u001a\u0004\b!\u0010\u0016\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueNameWidget;", "Lnet/minecraft/client/gui/components/Renderable;", "Lnet/minecraft/client/gui/components/events/GuiEventListener;", "", "isFocused", "()Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "mouseX", "mouseY", "", "delta", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "focused", "setFocused", "(Z)V", "height", "I", "getHeight", "()I", "Lnet/minecraft/network/chat/MutableComponent;", "text", "Lnet/minecraft/network/chat/MutableComponent;", "getText", "()Lnet/minecraft/network/chat/MutableComponent;", "width", "getWidth", "x", "getX", "y", "getY", "<init>", "(IIIILnet/minecraft/network/chat/MutableComponent;)V", "Companion", "common"})
public final class DialogueNameWidget
implements Renderable,
GuiEventListener {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    @Nullable
    private final MutableComponent text;
    @NotNull
    private static final ResourceLocation nameResource = MiscUtils.cobblemonResource("textures/gui/dialogue/dialogue_name.png");

    public DialogueNameWidget(int x, int y, int width, int height, @Nullable MutableComponent text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    public final int getX() {
        return this.x;
    }

    public final int getY() {
        return this.y;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    @Nullable
    public final MutableComponent getText() {
        return this.text;
    }

    public boolean m_93696_() {
        return false;
    }

    public void m_93692_(boolean focused) {
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        block3: {
            block2: {
                Intrinsics.checkNotNullParameter((Object)context, (String)"context");
                if (this.text == null) break block2;
                String string = this.text.getString();
                Intrinsics.checkNotNullExpressionValue((Object)string, (String)"text.string");
                if (!(((CharSequence)string).length() == 0)) break block3;
            }
            return;
        }
        ResourceLocation resourceLocation = nameResource;
        PoseStack poseStack = context.m_280168_();
        int n = this.x;
        int n2 = this.y;
        int n3 = this.width;
        int n4 = this.height;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, n, n2, n4, n3, 0, 0, null, null, null, null, null, null, Float.valueOf(1.0f), false, 1.0f, 16128, null);
        FormattedCharSequence formattedCharSequence = this.text.m_7532_();
        Intrinsics.checkNotNullExpressionValue((Object)formattedCharSequence, (String)"text.asOrderedText()");
        RenderHelperKt.drawScaledText$default(context, formattedCharSequence, this.x + 6, this.y + 4, 0.0f, 0.0f, null, 0, false, false, 496, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueNameWidget$Companion;", "", "Lnet/minecraft/resources/ResourceLocation;", "nameResource", "Lnet/minecraft/resources/ResourceLocation;", "getNameResource", "()Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getNameResource() {
            return nameResource;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

