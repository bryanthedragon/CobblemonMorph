/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.Button
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.moveselect;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonNetwork;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.callback.MoveSelectDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.ExitButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.moveselect.MoveSelectConfiguration;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.interact.moveselect.MoveSlotButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.move.MoveSelectCancelledPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.callback.move.MoveSelectedPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 /2\u00020\u0001:\u0001/B'\b\u0016\u0012\u0006\u0010'\u001a\u00020&\u0012\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00070(\u0012\u0006\u0010+\u001a\u00020*\u00a2\u0006\u0004\b,\u0010-B\u000f\u0012\u0006\u0010\"\u001a\u00020!\u00a2\u0006\u0004\b,\u0010.J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\r\u0010\u0005\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u0006\u0010\u0004J\u0017\u0010\t\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\u0007H\u0002\u00a2\u0006\u0004\b\t\u0010\nJ\u0015\u0010\r\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\r\u0010\u000eJ/\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u0014H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u000f\u0010\u0019\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u000f\u0010\u001b\u001a\u00020\u0018H\u0016\u00a2\u0006\u0004\b\u001b\u0010\u001aR\"\u0010\u001c\u001a\u00020\u00188\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001a\"\u0004\b\u001f\u0010 R\u0017\u0010\"\u001a\u00020!8\u0006\u00a2\u0006\f\n\u0004\b\"\u0010#\u001a\u0004\b$\u0010%\u00a8\u00060"}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/moveselect/MoveSelectGUI;", "Lnet/minecraft/client/gui/screens/Screen;", "", "close", "()V", "closeProperly", "init", "Lcom/cobblemon/mod/common/api/callback/MoveSelectDTO;", "move", "onPress", "(Lcom/cobblemon/mod/common/api/callback/MoveSelectDTO;)V", "Lnet/minecraft/sounds/SoundEvent;", "soundEvent", "playSound", "(Lnet/minecraft/sounds/SoundEvent;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "mouseX", "mouseY", "", "partialTicks", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "", "shouldCloseOnEsc", "()Z", "shouldPause", "closed", "Z", "getClosed", "setClosed", "(Z)V", "Lcom/cobblemon/mod/common/client/gui/interact/moveselect/MoveSelectConfiguration;", "config", "Lcom/cobblemon/mod/common/client/gui/interact/moveselect/MoveSelectConfiguration;", "getConfig", "()Lcom/cobblemon/mod/common/client/gui/interact/moveselect/MoveSelectConfiguration;", "Lnet/minecraft/network/chat/MutableComponent;", "title", "", "moves", "Ljava/util/UUID;", "uuid", "<init>", "(Lnet/minecraft/network/chat/MutableComponent;Ljava/util/List;Ljava/util/UUID;)V", "(Lcom/cobblemon/mod/common/client/gui/interact/moveselect/MoveSelectConfiguration;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nMoveSelectGUI.kt\nKotlin\n*S Kotlin\n*F\n+ 1 MoveSelectGUI.kt\ncom/cobblemon/mod/common/client/gui/interact/moveselect/MoveSelectGUI\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,139:1\n1864#2,3:140\n*S KotlinDebug\n*F\n+ 1 MoveSelectGUI.kt\ncom/cobblemon/mod/common/client/gui/interact/moveselect/MoveSelectGUI\n*L\n74#1:140,3\n*E\n"})
public final class MoveSelectGUI
extends Screen {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final MoveSelectConfiguration config;
    private boolean closed;
    public static final int WIDTH = 122;
    public static final int HEIGHT = 133;
    @NotNull
    private static final ResourceLocation baseBackgroundResource = MiscUtilsKt.cobblemonResource("textures/gui/interact/move_select.png");

    public MoveSelectGUI(@NotNull MoveSelectConfiguration config) {
        Intrinsics.checkNotNullParameter((Object)config, (String)"config");
        super((Component)Component.m_237115_((String)"cobblemon.ui.interact.moveselect"));
        this.config = config;
    }

    @NotNull
    public final MoveSelectConfiguration getConfig() {
        return this.config;
    }

    public final boolean getClosed() {
        return this.closed;
    }

    public final void setClosed(boolean bl) {
        this.closed = bl;
    }

    public MoveSelectGUI(@NotNull MutableComponent title, final @NotNull List<MoveSelectDTO> moves, final @NotNull UUID uuid2) {
        Intrinsics.checkNotNullParameter((Object)title, (String)"title");
        Intrinsics.checkNotNullParameter(moves, (String)"moves");
        Intrinsics.checkNotNullParameter((Object)uuid2, (String)"uuid");
        this(new MoveSelectConfiguration(title, moves, (Function1<? super MoveSelectGUI, Unit>)((Function1)new Function1<MoveSelectGUI, Unit>(){

            public final void invoke(@NotNull MoveSelectGUI it) {
                Intrinsics.checkNotNullParameter((Object)((Object)it), (String)"it");
                CobblemonNetwork.INSTANCE.sendToServer(new MoveSelectCancelledPacket(uuid2));
            }
        }), (Function1<? super MoveSelectGUI, Unit>)((Function1)2.INSTANCE), (Function2<? super MoveSelectGUI, ? super MoveSelectDTO, Unit>)((Function2)new Function2<MoveSelectGUI, MoveSelectDTO, Unit>(){

            public final void invoke(@NotNull MoveSelectGUI gui, @NotNull MoveSelectDTO it) {
                Intrinsics.checkNotNullParameter((Object)((Object)gui), (String)"gui");
                Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                CobblemonNetwork.INSTANCE.sendToServer(new MoveSelectedPacket(uuid2, moves.indexOf(it)));
                gui.closeProperly();
            }
        })));
    }

    public final void closeProperly() {
        this.closed = true;
        this.m_7379_();
    }

    /*
     * WARNING - void declaration
     */
    protected void m_7856_() {
        int x = (this.f_96543_ - 122) / 2;
        int y = (this.f_96544_ - 133) / 2;
        Iterable $this$forEachIndexed$iv = this.config.getMoves();
        boolean $i$f$forEachIndexed = false;
        int index$iv = 0;
        for (Object item$iv : $this$forEachIndexed$iv) {
            void move;
            int n;
            if ((n = index$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            MoveSelectDTO moveSelectDTO = (MoveSelectDTO)item$iv;
            int index = n;
            boolean bl = false;
            this.m_142416_((GuiEventListener)new MoveSlotButton(x + 7, y + 7 + 25 * index, move.getMoveTemplate(), move.getPp(), move.getPpMax(), move.getEnabled(), arg_0 -> MoveSelectGUI.init$lambda$1$lambda$0(this, (MoveSelectDTO)move, arg_0)));
        }
        this.m_142416_((GuiEventListener)new ExitButton(x + 92, y + 115, arg_0 -> MoveSelectGUI.init$lambda$2(this, arg_0)));
        super.m_7856_();
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float partialTicks) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        int x = (this.f_96543_ - 122) / 2;
        int y = (this.f_96544_ - 133) / 2;
        PoseStack poseStack = context.m_280168_();
        ResourceLocation resourceLocation = baseBackgroundResource;
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
        GuiUtilsKt.blitk$default(poseStack, resourceLocation, x, y, 133, 122, null, null, null, null, null, null, null, null, null, false, 0.0f, 131008, null);
        super.m_88315_(context, mouseX, mouseY, partialTicks);
    }

    private final void onPress(MoveSelectDTO move) {
        if (!move.getEnabled()) {
            return;
        }
        this.playSound(CobblemonSounds.GUI_CLICK);
        this.config.getOnSelect().invoke((Object)this, (Object)move);
    }

    public void m_7379_() {
        if (!this.closed) {
            this.config.getOnCancel().invoke((Object)this);
        }
        super.m_7379_();
    }

    public boolean m_6913_() {
        return true;
    }

    public boolean m_7043_() {
        return false;
    }

    public final void playSound(@NotNull SoundEvent soundEvent) {
        Intrinsics.checkNotNullParameter((Object)soundEvent, (String)"soundEvent");
        Minecraft.m_91087_().m_91106_().m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)soundEvent, (float)1.0f));
    }

    private static final void init$lambda$1$lambda$0(MoveSelectGUI this$0, MoveSelectDTO $move, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)$move, (String)"$move");
        this$0.onPress($move);
    }

    private static final void init$lambda$2(MoveSelectGUI this$0, Button it) {
        Intrinsics.checkNotNullParameter((Object)((Object)this$0), (String)"this$0");
        this$0.playSound(CobblemonSounds.GUI_CLICK);
        this$0.config.getOnBack().invoke((Object)this$0);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\t\u0010\nR\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00068\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/gui/interact/moveselect/MoveSelectGUI$Companion;", "", "", "HEIGHT", "I", "WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "baseBackgroundResource", "Lnet/minecraft/resources/ResourceLocation;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

