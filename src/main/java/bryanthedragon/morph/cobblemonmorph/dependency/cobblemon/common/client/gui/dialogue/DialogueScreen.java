/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.ranges.RangesKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangEnvironment;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoLangRuntime;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ArtificialDialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.PlayerDialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ReferenceDialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.NetworkPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.ClientMoLangFunctions;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.ArtificialRenderableFace;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.DialogueRenderableSpeaker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.DialogueScreen;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.PlayerRenderableFace;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.ReferenceRenderableFace;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.RenderableFace;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.widgets.DialogueBox;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.widgets.DialogueFaceWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.widgets.DialogueNameWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.widgets.DialogueOptionWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.widgets.DialogueTextInputWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.widgets.DialogueTimerWidget;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.Poseable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto.DialogueDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto.DialogueInputDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto.DialogueOptionDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.dialogue.dto.DialogueSpeakerDTO;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.dialogue.EscapeDialoguePacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MoLangExtensionsKt;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\f\u0018\u0000 k2\u00020\u0001:\u0001kB\u000f\u0012\u0006\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\bj\u0010\u0019J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u00020\u0002H\u0014\u00a2\u0006\u0004\b\u0005\u0010\u0004J/\u0010\r\u001a\u00020\u00022\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0019\u0010\u0011\u001a\u00020\u00022\n\u0010\u0010\u001a\u0006\u0012\u0002\b\u00030\u000f\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u000f\u0010\u0014\u001a\u00020\u0013H\u0016\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0015\u0010\u0018\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u0016\u00a2\u0006\u0004\b\u0018\u0010\u0019R\"\u0010\u001b\u001a\u00020\u001a8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\"\u0010\u0017\u001a\u00020\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0017\u0010!\u001a\u0004\b\"\u0010#\"\u0004\b$\u0010\u0019R\"\u0010&\u001a\u00020%8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u0017\u0010-\u001a\u00020,8\u0006\u00a2\u0006\f\n\u0004\b-\u0010.\u001a\u0004\b/\u00100R\"\u00102\u001a\u0002018\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b2\u00103\u001a\u0004\b4\u00105\"\u0004\b6\u00107R(\u0010:\u001a\b\u0012\u0004\u0012\u000209088\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\b:\u0010;\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\"\u0010A\u001a\u00020@8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bA\u0010B\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\"\u0010H\u001a\u00020G8\u0006@\u0006X\u0086.\u00a2\u0006\u0012\n\u0004\bH\u0010I\u001a\u0004\bJ\u0010K\"\u0004\bL\u0010MR\"\u0010N\u001a\u00020\u000b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\bN\u0010O\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR\u0017\u0010U\u001a\u00020T8\u0006\u00a2\u0006\f\n\u0004\bU\u0010V\u001a\u0004\bW\u0010XR\u0011\u0010[\u001a\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\bY\u0010ZR\u0011\u0010]\u001a\u00020\b8F\u00a2\u0006\u0006\u001a\u0004\b\\\u0010ZR#\u0010a\u001a\u000e\u0012\u0004\u0012\u00020_\u0012\u0004\u0012\u00020`0^8\u0006\u00a2\u0006\f\n\u0004\ba\u0010b\u001a\u0004\bc\u0010dR\"\u0010e\u001a\u00020\u00138\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\be\u0010f\u001a\u0004\bg\u0010\u0015\"\u0004\bh\u0010i\u00a8\u0006l"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "Lnet/minecraft/client/gui/screens/Screen;", "", "close", "()V", "init", "Lnet/minecraft/client/gui/GuiGraphics;", "drawContext", "", "mouseX", "mouseY", "", "delta", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/api/net/NetworkPacket;", "packet", "sendToServer", "(Lcom/cobblemon/mod/common/api/net/NetworkPacket;)V", "", "shouldPause", "()Z", "Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueDTO;", "dialogueDTO", "update", "(Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueDTO;)V", "Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueBox;", "dialogueBox", "Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueBox;", "getDialogueBox", "()Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueBox;", "setDialogueBox", "(Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueBox;)V", "Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueDTO;", "getDialogueDTO", "()Lcom/cobblemon/mod/common/net/messages/client/dialogue/dto/DialogueDTO;", "setDialogueDTO", "Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueFaceWidget;", "dialogueFaceWidget", "Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueFaceWidget;", "getDialogueFaceWidget", "()Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueFaceWidget;", "setDialogueFaceWidget", "(Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueFaceWidget;)V", "Ljava/util/UUID;", "dialogueId", "Ljava/util/UUID;", "getDialogueId", "()Ljava/util/UUID;", "Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueNameWidget;", "dialogueNameWidget", "Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueNameWidget;", "getDialogueNameWidget", "()Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueNameWidget;", "setDialogueNameWidget", "(Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueNameWidget;)V", "", "Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueOptionWidget;", "dialogueOptionWidgets", "Ljava/util/List;", "getDialogueOptionWidgets", "()Ljava/util/List;", "setDialogueOptionWidgets", "(Ljava/util/List;)V", "Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueTextInputWidget;", "dialogueTextInputWidget", "Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueTextInputWidget;", "getDialogueTextInputWidget", "()Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueTextInputWidget;", "setDialogueTextInputWidget", "(Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueTextInputWidget;)V", "Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueTimerWidget;", "dialogueTimerWidget", "Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueTimerWidget;", "getDialogueTimerWidget", "()Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueTimerWidget;", "setDialogueTimerWidget", "(Lcom/cobblemon/mod/common/client/gui/dialogue/widgets/DialogueTimerWidget;)V", "remainingSeconds", "F", "getRemainingSeconds", "()F", "setRemainingSeconds", "(F)V", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "runtime", "Lcom/bedrockk/molang/runtime/MoLangRuntime;", "getRuntime", "()Lcom/bedrockk/molang/runtime/MoLangRuntime;", "getScaledHeight", "()I", "scaledHeight", "getScaledWidth", "scaledWidth", "", "", "Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueRenderableSpeaker;", "speakers", "Ljava/util/Map;", "getSpeakers", "()Ljava/util/Map;", "waitingForServerUpdate", "Z", "getWaitingForServerUpdate", "setWaitingForServerUpdate", "(Z)V", "<init>", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nDialogueScreen.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueScreen.kt\ncom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen\n+ 2 _Maps.kt\nkotlin/collections/MapsKt___MapsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 4 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,232:1\n135#2,9:233\n215#2:242\n216#2:244\n144#2:245\n1#3:243\n1360#4:246\n1446#4,5:247\n1179#4,2:252\n1253#4,4:254\n1855#4,2:258\n1559#4:260\n1590#4,4:261\n1855#4,2:265\n1360#4:267\n1446#4,5:268\n*S KotlinDebug\n*F\n+ 1 DialogueScreen.kt\ncom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen\n*L\n42#1:233,9\n42#1:242\n42#1:244\n42#1:245\n42#1:243\n115#1:246\n115#1:247,5\n116#1:252,2\n116#1:254,4\n119#1:258,2\n169#1:260\n169#1:261,4\n198#1:265,2\n206#1:267\n206#1:268,5\n*E\n"})
public final class DialogueScreen
extends Screen {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private DialogueDTO dialogueDTO;
    @NotNull
    private final Map<String, DialogueRenderableSpeaker> speakers;
    @NotNull
    private final MoLangRuntime runtime;
    private boolean waitingForServerUpdate;
    @NotNull
    private final UUID dialogueId;
    private float remainingSeconds;
    public DialogueTimerWidget dialogueTimerWidget;
    public DialogueTextInputWidget dialogueTextInputWidget;
    public DialogueBox dialogueBox;
    public List<DialogueOptionWidget> dialogueOptionWidgets;
    public DialogueNameWidget dialogueNameWidget;
    public DialogueFaceWidget dialogueFaceWidget;
    private static final int BOX_WIDTH = 169;
    private static final int BOX_HEIGHT = 47;
    private static final int BAR_WIDTH = 169;
    private static final int BAR_HEIGHT = 13;
    private static final int OPTION_HEIGHT = 24;
    private static final int OPTION_WIDTH_NARROW = 92;
    private static final int OPTION_WIDTH_WIDE = 162;
    private static final int NAME_WIDTH = 120;
    private static final int NAME_HEIGHT = 15;
    private static final int TEXT_INPUT_WIDTH = 160;
    private static final int TEXT_INPUT_HEIGHT = 16;
    private static final int OPTION_HORIZONTAL_SPACING = 12;
    private static final int OPTION_VERTICAL_SPACING = 1;
    private static final int FACE_WIDTH = 38;
    private static final int FACE_HEIGHT = 36;
    @NotNull
    private static final List<Function1<DialogueScreen, HashMap<String, Function<MoParams, Object>>>> dialogueMolangFunctions;

    /*
     * WARNING - void declaration
     */
    public DialogueScreen(@NotNull DialogueDTO dialogueDTO) {
        Map map;
        block13: {
            block12: {
                void $this$mapNotNullTo$iv$iv;
                void $this$mapNotNull$iv;
                Intrinsics.checkNotNullParameter((Object)dialogueDTO, (String)"dialogueDTO");
                super((Component)MiscUtilsKt.asTranslated("gui.dialogue"));
                this.dialogueDTO = dialogueDTO;
                DialogueScreen dialogueScreen = this;
                map = this.dialogueDTO.getSpeakers();
                if (map == null) break block12;
                Map map2 = map;
                DialogueScreen dialogueScreen2 = dialogueScreen;
                boolean $i$f$mapNotNull = false;
                void var4_5 = $this$mapNotNull$iv;
                Collection destination$iv$iv = new ArrayList();
                boolean $i$f$mapNotNullTo = false;
                void $this$forEach$iv$iv$iv = $this$mapNotNullTo$iv$iv;
                boolean $i$f$forEach = false;
                Iterator iterator = $this$forEach$iv$iv$iv.entrySet().iterator();
                while (iterator.hasNext()) {
                    Pair pair;
                    Map.Entry element$iv$iv$iv;
                    Map.Entry element$iv$iv = element$iv$iv$iv = iterator.next();
                    boolean bl = false;
                    Map.Entry entry = element$iv$iv;
                    boolean bl2 = false;
                    String key = (String)entry.getKey();
                    DialogueSpeakerDTO value2 = (DialogueSpeakerDTO)entry.getValue();
                    MutableComponent name = value2.getName();
                    DialogueFaceProvider face = value2.getFace();
                    if (face instanceof ArtificialDialogueFaceProvider) {
                        pair = TuplesKt.to((Object)key, (Object)new DialogueRenderableSpeaker(name, new ArtificialRenderableFace(((ArtificialDialogueFaceProvider)face).getModelType(), ((ArtificialDialogueFaceProvider)face).getIdentifier(), ((ArtificialDialogueFaceProvider)face).getAspects())));
                    } else if (face instanceof PlayerDialogueFaceProvider) {
                        pair = TuplesKt.to((Object)key, (Object)new DialogueRenderableSpeaker(name, new PlayerRenderableFace(((PlayerDialogueFaceProvider)face).getPlayerId())));
                    } else if (face instanceof ReferenceDialogueFaceProvider) {
                        ClientLevel clientLevel = Minecraft.m_91087_().f_91073_;
                        Entity entity2 = clientLevel != null ? clientLevel.m_6815_(((ReferenceDialogueFaceProvider)face).getEntityId()) : null;
                        if ((entity2 instanceof Poseable ? (Poseable)entity2 : null) == null) {
                            Object var20_21 = null;
                            pair = var20_21;
                        } else {
                            MutableComponent mutableComponent;
                            Poseable poseable;
                            poseable = poseable;
                            RenderableFace renderableFace = new ReferenceRenderableFace(poseable);
                            pair = TuplesKt.to((Object)key, (Object)new DialogueRenderableSpeaker(mutableComponent, renderableFace));
                        }
                    } else {
                        pair = TuplesKt.to((Object)key, (Object)new DialogueRenderableSpeaker(name, null));
                    }
                    if (pair == null) continue;
                    Pair it$iv$iv = pair;
                    boolean bl3 = false;
                    destination$iv$iv.add(it$iv$iv);
                }
                dialogueScreen = dialogueScreen2;
                map = MapsKt.toMap((Iterable)((List)destination$iv$iv));
                if (map != null) break block13;
            }
            map = MapsKt.emptyMap();
        }
        dialogueScreen.speakers = map;
        this.runtime = ClientMoLangFunctions.INSTANCE.setupClient(MoLangFunctions.INSTANCE.setup(new MoLangRuntime()));
        this.dialogueId = this.dialogueDTO.getDialogueId();
        this.remainingSeconds = this.dialogueDTO.getDialogueInput().getDeadline();
    }

    @NotNull
    public final DialogueDTO getDialogueDTO() {
        return this.dialogueDTO;
    }

    public final void setDialogueDTO(@NotNull DialogueDTO dialogueDTO) {
        Intrinsics.checkNotNullParameter((Object)dialogueDTO, (String)"<set-?>");
        this.dialogueDTO = dialogueDTO;
    }

    @NotNull
    public final Map<String, DialogueRenderableSpeaker> getSpeakers() {
        return this.speakers;
    }

    @NotNull
    public final MoLangRuntime getRuntime() {
        return this.runtime;
    }

    public final boolean getWaitingForServerUpdate() {
        return this.waitingForServerUpdate;
    }

    public final void setWaitingForServerUpdate(boolean bl) {
        this.waitingForServerUpdate = bl;
    }

    @NotNull
    public final UUID getDialogueId() {
        return this.dialogueId;
    }

    public final float getRemainingSeconds() {
        return this.remainingSeconds;
    }

    public final void setRemainingSeconds(float f) {
        this.remainingSeconds = f;
    }

    @NotNull
    public final DialogueTimerWidget getDialogueTimerWidget() {
        DialogueTimerWidget dialogueTimerWidget = this.dialogueTimerWidget;
        if (dialogueTimerWidget != null) {
            return dialogueTimerWidget;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"dialogueTimerWidget");
        return null;
    }

    public final void setDialogueTimerWidget(@NotNull DialogueTimerWidget dialogueTimerWidget) {
        Intrinsics.checkNotNullParameter((Object)dialogueTimerWidget, (String)"<set-?>");
        this.dialogueTimerWidget = dialogueTimerWidget;
    }

    @NotNull
    public final DialogueTextInputWidget getDialogueTextInputWidget() {
        DialogueTextInputWidget dialogueTextInputWidget = this.dialogueTextInputWidget;
        if (dialogueTextInputWidget != null) {
            return dialogueTextInputWidget;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"dialogueTextInputWidget");
        return null;
    }

    public final void setDialogueTextInputWidget(@NotNull DialogueTextInputWidget dialogueTextInputWidget) {
        Intrinsics.checkNotNullParameter((Object)((Object)dialogueTextInputWidget), (String)"<set-?>");
        this.dialogueTextInputWidget = dialogueTextInputWidget;
    }

    @NotNull
    public final DialogueBox getDialogueBox() {
        DialogueBox dialogueBox = this.dialogueBox;
        if (dialogueBox != null) {
            return dialogueBox;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"dialogueBox");
        return null;
    }

    public final void setDialogueBox(@NotNull DialogueBox dialogueBox) {
        Intrinsics.checkNotNullParameter((Object)((Object)dialogueBox), (String)"<set-?>");
        this.dialogueBox = dialogueBox;
    }

    @NotNull
    public final List<DialogueOptionWidget> getDialogueOptionWidgets() {
        List<DialogueOptionWidget> list = this.dialogueOptionWidgets;
        if (list != null) {
            return list;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"dialogueOptionWidgets");
        return null;
    }

    public final void setDialogueOptionWidgets(@NotNull List<DialogueOptionWidget> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.dialogueOptionWidgets = list;
    }

    @NotNull
    public final DialogueNameWidget getDialogueNameWidget() {
        DialogueNameWidget dialogueNameWidget = this.dialogueNameWidget;
        if (dialogueNameWidget != null) {
            return dialogueNameWidget;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"dialogueNameWidget");
        return null;
    }

    public final void setDialogueNameWidget(@NotNull DialogueNameWidget dialogueNameWidget) {
        Intrinsics.checkNotNullParameter((Object)dialogueNameWidget, (String)"<set-?>");
        this.dialogueNameWidget = dialogueNameWidget;
    }

    @NotNull
    public final DialogueFaceWidget getDialogueFaceWidget() {
        DialogueFaceWidget dialogueFaceWidget = this.dialogueFaceWidget;
        if (dialogueFaceWidget != null) {
            return dialogueFaceWidget;
        }
        Intrinsics.throwUninitializedPropertyAccessException((String)"dialogueFaceWidget");
        return null;
    }

    public final void setDialogueFaceWidget(@NotNull DialogueFaceWidget dialogueFaceWidget) {
        Intrinsics.checkNotNullParameter((Object)dialogueFaceWidget, (String)"<set-?>");
        this.dialogueFaceWidget = dialogueFaceWidget;
    }

    public final int getScaledWidth() {
        Minecraft minecraft = this.f_96541_;
        Intrinsics.checkNotNull((Object)minecraft);
        return minecraft.m_91268_().m_85445_();
    }

    public final int getScaledHeight() {
        Minecraft minecraft = this.f_96541_;
        Intrinsics.checkNotNull((Object)minecraft);
        return minecraft.m_91268_().m_85446_();
    }

    /*
     * WARNING - void declaration
     */
    protected void m_7856_() {
        void $this$flatMapTo$iv$iv;
        Iterable $this$mapIndexedTo$iv$iv;
        void $this$mapIndexed$iv;
        int n;
        Object element$iv2;
        void $this$associateTo$iv$iv;
        void $this$associate$iv;
        void $this$flatMapTo$iv$iv2;
        Iterable $this$flatMap$iv;
        super.m_7856_();
        MoLangEnvironment moLangEnvironment = this.runtime.getEnvironment();
        Intrinsics.checkNotNullExpressionValue((Object)moLangEnvironment, (String)"runtime.environment");
        Iterable iterable = dialogueMolangFunctions;
        Object object = MoLangFunctions.getQueryStruct$default(MoLangFunctions.INSTANCE, moLangEnvironment, null, 1, null);
        Object object2 = MoLangFunctions.INSTANCE;
        boolean $i$f$flatMap = false;
        void var3_7 = $this$flatMap$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv2) {
            Function1 it = (Function1)element$iv$iv;
            boolean bl = false;
            Set set2 = ((HashMap)it.invoke((Object)this)).entrySet();
            Intrinsics.checkNotNullExpressionValue(set2, (String)"it(this@DialogueScreen).entries");
            Iterable list$iv$iv = set2;
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        Object object3 = (List)destination$iv$iv;
        $this$flatMap$iv = (Iterable)object3;
        boolean $i$f$associate = false;
        int capacity$iv = RangesKt.coerceAtLeast((int)MapsKt.mapCapacity((int)CollectionsKt.collectionSizeOrDefault((Iterable)$this$associate$iv, (int)10)), (int)16);
        destination$iv$iv = $this$associate$iv;
        Map destination$iv$iv2 = new LinkedHashMap(capacity$iv);
        boolean $i$f$associateTo = false;
        for (Object element$iv$iv : $this$associateTo$iv$iv) {
            Map bl = destination$iv$iv2;
            Map.Entry it = (Map.Entry)element$iv$iv;
            boolean bl2 = false;
            it = TuplesKt.to(it.getKey(), it.getValue());
            bl.put(it.getFirst(), it.getSecond());
        }
        object3 = destination$iv$iv2;
        ((MoLangFunctions)object2).addFunctions(object, (Map<String, ? extends Function<MoParams, Object>>)object3);
        Iterable $this$forEach$iv = this.dialogueDTO.getCurrentPageDTO().getClientActions();
        boolean $i$f$forEach = false;
        for (Object element$iv2 : $this$forEach$iv) {
            String it = (String)element$iv2;
            n = 0;
            Expression expression = MoLangExtensionsKt.asExpression(it);
            Intrinsics.checkNotNullExpressionValue((Object)expression, (String)"it.asExpression()");
            MoLangExtensionsKt.resolve(this.runtime, expression);
        }
        float centerX = (float)this.getScaledWidth() / 2.0f;
        float boxMinY = (float)this.getScaledHeight() / 2.0f - 23.5f - (float)10;
        float boxMaxY = boxMinY + (float)47;
        this.setDialogueTimerWidget(new DialogueTimerWidget(this, (int)(centerX - 84.5f), (int)(boxMaxY + (float)4), 169, 13));
        this.setDialogueTextInputWidget(new DialogueTextInputWidget(this, (int)(centerX - 80.0f), (int)(boxMaxY + (float)16), 160, 16, 0, 32, null));
        element$iv2 = this.dialogueDTO.getCurrentPageDTO().getLines();
        int it = (int)(centerX - 84.5f);
        n = (int)boxMinY;
        this.setDialogueBox(new DialogueBox(this, it, n, 169, 47, (List<? extends MutableComponent>)element$iv2));
        DialogueRenderableSpeaker dialogueRenderableSpeaker = this.speakers.get(this.dialogueDTO.getCurrentPageDTO().getSpeaker());
        MutableComponent name = dialogueRenderableSpeaker != null ? dialogueRenderableSpeaker.getName() : null;
        this.setDialogueNameWidget(new DialogueNameWidget((int)(centerX - 84.5f), (int)(boxMinY - (float)15), 120, 15, name));
        this.setDialogueFaceWidget(new DialogueFaceWidget(this, (int)(centerX - 84.5f - (float)38), (int)boxMinY, 38, 36));
        int optionCount = this.dialogueDTO.getDialogueInput().getOptions().size();
        float optionStartY = boxMaxY + (float)18;
        boolean vertical = this.dialogueDTO.getDialogueInput().getVertical();
        int horizontalSpacing = vertical ? 0 : 93;
        int verticalSpacing = vertical ? 25 : 0;
        int totalWidth = (optionCount - 1) * horizontalSpacing;
        float optionStartX = centerX - (float)totalWidth / 2.0f;
        Iterable iterable2 = this.dialogueDTO.getDialogueInput().getOptions();
        object2 = this;
        boolean $i$f$mapIndexed = false;
        void var14_32 = $this$mapIndexed$iv;
        Collection destination$iv$iv3 = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$mapIndexed$iv, (int)10));
        boolean $i$f$mapIndexedTo = false;
        int index$iv$iv = 0;
        Iterator iterator = $this$mapIndexedTo$iv$iv.iterator();
        while (iterator.hasNext()) {
            void option;
            void index;
            int n2;
            Object item$iv$iv = iterator.next();
            if ((n2 = index$iv$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            DialogueOptionDTO dialogueOptionDTO = (DialogueOptionDTO)item$iv$iv;
            int n3 = n2;
            object = destination$iv$iv3;
            boolean bl = false;
            float x = optionStartX + (float)(index * horizontalSpacing);
            float y = optionStartY + (float)(index * verticalSpacing);
            object.add(new DialogueOptionWidget(this, option.getText(), option.getValue(), option.getSelectable(), (int)x - (vertical ? 81 : 46), (int)y, vertical ? 162 : 92, 24, vertical ? MiscUtilsKt.cobblemonResource("textures/gui/dialogue/dialogue_button_wide.png") : MiscUtilsKt.cobblemonResource("textures/gui/dialogue/dialogue_button_narrow.png"), vertical ? MiscUtilsKt.cobblemonResource("textures/gui/dialogue/dialogue_button_wide_overlay.png") : MiscUtilsKt.cobblemonResource("textures/gui/dialogue/dialogue_button_narrow_overlay.png")));
        }
        ((DialogueScreen)((Object)object2)).setDialogueOptionWidgets((List)destination$iv$iv3);
        this.m_169394_(this.getDialogueTimerWidget());
        this.m_142416_((GuiEventListener)this.getDialogueTextInputWidget());
        this.m_142416_((GuiEventListener)this.getDialogueBox());
        Iterable $this$forEach$iv2 = this.getDialogueOptionWidgets();
        boolean $i$f$forEach2 = false;
        for (Object element$iv3 : $this$forEach$iv2) {
            DialogueOptionWidget it2 = (DialogueOptionWidget)((Object)element$iv3);
            boolean bl = false;
            this.m_142416_((GuiEventListener)it2);
        }
        this.m_169394_(this.getDialogueNameWidget());
        this.m_169394_(this.getDialogueFaceWidget());
        if (this.dialogueDTO.getDialogueInput().getInputType() == DialogueInputDTO.InputType.TEXT) {
            this.m_94725_((GuiEventListener)this.getDialogueTextInputWidget());
        }
        Iterable $this$flatMap$iv2 = this.dialogueDTO.getCurrentPageDTO().getClientActions();
        boolean $i$f$flatMap2 = false;
        $this$mapIndexedTo$iv$iv = $this$flatMap$iv2;
        destination$iv$iv3 = new ArrayList();
        boolean $i$f$flatMapTo2 = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            String p0 = (String)element$iv$iv;
            boolean bl = false;
            Iterable list$iv$iv = MoLangExtensionsKt.asExpressions(p0);
            CollectionsKt.addAll((Collection)destination$iv$iv3, (Iterable)list$iv$iv);
        }
        MoLangExtensionsKt.resolve((List)destination$iv$iv3, this.runtime);
    }

    public void m_88315_(@NotNull GuiGraphics drawContext, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)drawContext, (String)"drawContext");
        this.remainingSeconds -= delta / 20.0f;
        this.getDialogueTimerWidget().setRatio(this.remainingSeconds <= 0.0f ? -1.0f : this.remainingSeconds / this.dialogueDTO.getDialogueInput().getDeadline());
        super.m_88315_(drawContext, mouseX, mouseY, delta);
    }

    public boolean m_7043_() {
        return false;
    }

    public final void update(@NotNull DialogueDTO dialogueDTO) {
        Intrinsics.checkNotNullParameter((Object)dialogueDTO, (String)"dialogueDTO");
        this.dialogueDTO = dialogueDTO;
        this.remainingSeconds = dialogueDTO.getDialogueInput().getDeadline();
        this.waitingForServerUpdate = false;
        this.m_232761_();
    }

    public final void sendToServer(@NotNull NetworkPacket<?> packet) {
        Intrinsics.checkNotNullParameter(packet, (String)"packet");
        packet.sendToServer();
        this.waitingForServerUpdate = true;
    }

    public void m_7379_() {
        new EscapeDialoguePacket().sendToServer();
    }

    static {
        Object[] objectArray = new Function1[]{Companion.dialogueMolangFunctions.1.INSTANCE};
        dialogueMolangFunctions = CollectionsKt.mutableListOf((Object[])objectArray);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0010\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001f\u0010 R\u0014\u0010\u0003\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0004R\u0014\u0010\b\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0004R\u0014\u0010\t\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0004R\u0014\u0010\n\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\n\u0010\u0004R\u0014\u0010\u000b\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u000b\u0010\u0004R\u0014\u0010\f\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\f\u0010\u0004R\u0014\u0010\r\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\r\u0010\u0004R\u0014\u0010\u000e\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u000e\u0010\u0004R\u0014\u0010\u000f\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u000f\u0010\u0004R\u0014\u0010\u0010\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0004R\u0014\u0010\u0011\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0004R\u0014\u0010\u0012\u001a\u00020\u00028\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\u0012\u0010\u0004R]\u0010\u001b\u001aH\u0012D\u0012B\u0012\u0004\u0012\u00020\u0015\u00128\u00126\u0012\u0004\u0012\u00020\u0017\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00010\u00180\u0016j\u001a\u0012\u0004\u0012\u00020\u0017\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00010\u0018`\u001a0\u00140\u00138\u0006\u00a2\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001d\u0010\u001e\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen$Companion;", "", "", "BAR_HEIGHT", "I", "BAR_WIDTH", "BOX_HEIGHT", "BOX_WIDTH", "FACE_HEIGHT", "FACE_WIDTH", "NAME_HEIGHT", "NAME_WIDTH", "OPTION_HEIGHT", "OPTION_HORIZONTAL_SPACING", "OPTION_VERTICAL_SPACING", "OPTION_WIDTH_NARROW", "OPTION_WIDTH_WIDE", "TEXT_INPUT_HEIGHT", "TEXT_INPUT_WIDTH", "", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/client/gui/dialogue/DialogueScreen;", "Ljava/util/HashMap;", "", "Ljava/util/function/Function;", "Lcom/bedrockk/molang/runtime/MoParams;", "Lkotlin/collections/HashMap;", "dialogueMolangFunctions", "Ljava/util/List;", "getDialogueMolangFunctions", "()Ljava/util/List;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final List<Function1<DialogueScreen, HashMap<String, Function<MoParams, Object>>>> getDialogueMolangFunctions() {
            return dialogueMolangFunctions;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

