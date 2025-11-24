/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Renderable
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.widgets;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.DialogueRenderableSpeaker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.DialogueScreen;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\u0018\u0000 $2\u00020\u00012\u00020\u0002:\u0001$B/\u0012\u0006\u0010\u0014\u001a\u00020\u0013\u0012\u0006\u0010\u001e\u001a\u00020\b\u0012\u0006\u0010 \u001a\u00020\b\u0012\u0006\u0010\u001c\u001a\u00020\b\u0012\u0006\u0010\u0018\u001a\u00020\b\u00a2\u0006\u0004\b\"\u0010#J\u000f\u0010\u0004\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0004\u0010\u0005J/\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0017\u0010\u0014\u001a\u00020\u00138\u0006\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0016\u0010\u0017R\u0017\u0010\u0018\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u0017\u0010\u001c\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u0019\u001a\u0004\b\u001d\u0010\u001bR\u0017\u0010\u001e\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u001e\u0010\u0019\u001a\u0004\b\u001f\u0010\u001bR\u0017\u0010 \u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b \u0010\u0019\u001a\u0004\b!\u0010\u001b\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueFaceWidget;", "Lnet/minecraft/client/gui/components/Renderable;", "Lnet/minecraft/client/gui/components/events/GuiEventListener;", "", "isFocused", "()Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "mouseX", "mouseY", "", "delta", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "focused", "setFocused", "(Z)V", "Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "dialogueScreen", "Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "getDialogueScreen", "()Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "height", "I", "getHeight", "()I", "width", "getWidth", "x", "getX", "y", "getY", "<init>", "(Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;IIII)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nDialogueFaceWidget.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueFaceWidget.kt\ncom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueFaceWidget\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,67:1\n1#2:68\n*E\n"})
public final class DialogueFaceWidget
implements Renderable,
GuiEventListener {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final DialogueScreen dialogueScreen;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    @NotNull
    private static final ResourceLocation frameResource = MiscUtilsKt.cobblemonResource("textures/gui/dialogue/dialogue_face.png");
    @NotNull
    private static final ResourceLocation frameBackground = MiscUtilsKt.cobblemonResource("textures/gui/dialogue/dialogue_face_background.png");

    public DialogueFaceWidget(@NotNull DialogueScreen dialogueScreen, int x, int y, int width, int height) {
        Intrinsics.checkNotNullParameter((Object)((Object)dialogueScreen), (String)"dialogueScreen");
        this.dialogueScreen = dialogueScreen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @NotNull
    public final DialogueScreen getDialogueScreen() {
        return this.dialogueScreen;
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

    public void m_93692_(boolean focused) {
    }

    public boolean m_93696_() {
        return false;
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Object object;
        block3: {
            block2: {
                Intrinsics.checkNotNullParameter((Object)context, (String)"context");
                object = this.dialogueScreen.getDialogueDTO().getCurrentPageDTO().getSpeaker();
                if (object == null) break block2;
                String it = object;
                boolean bl = false;
                DialogueRenderableSpeaker dialogueRenderableSpeaker = this.dialogueScreen.getSpeakers().get(it);
                object = dialogueRenderableSpeaker;
                if (dialogueRenderableSpeaker != null && (object = ((DialogueRenderableSpeaker)object).getFace()) != null) break block3;
            }
            return;
        }
        Object face = object;
        ResourceLocation resourceLocation = frameBackground;
        PoseStack poseStack = context.m_280168_();
        int n = this.x;
        int n2 = this.y;
        int n3 = this.width;
        int n4 = this.height;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, n, n2, n4, n3, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        context.m_280588_(this.x + 2, this.y + 2, this.x + 2 + this.width - 4, this.y + 2 + this.height - 4);
        context.m_280168_().m_85836_();
        context.m_280168_().m_85837_((double)this.x + (double)(this.width / 2), (double)this.y, 0.0);
        face.render(context, delta);
        context.m_280618_();
        context.m_280168_().m_85849_();
        context.m_280168_().m_85836_();
        context.m_280168_().m_252880_(0.0f, 0.0f, 100.0f);
        resourceLocation = frameResource;
        poseStack = context.m_280168_();
        n = this.x;
        n2 = this.y;
        n3 = this.width;
        n4 = this.height;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, n, n2, n4, n3, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        context.m_280168_().m_85849_();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueFaceWidget$Companion;", "", "Lnet/minecraft/resources/ResourceLocation;", "frameBackground", "Lnet/minecraft/resources/ResourceLocation;", "getFrameBackground", "()Lnet/minecraft/resources/ResourceLocation;", "frameResource", "getFrameResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getFrameResource() {
            return frameResource;
        }

        @NotNull
        public final ResourceLocation getFrameBackground() {
            return frameBackground;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

