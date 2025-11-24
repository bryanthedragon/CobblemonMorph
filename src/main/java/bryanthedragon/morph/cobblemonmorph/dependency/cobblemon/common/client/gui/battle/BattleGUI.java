/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.TypeIntrinsics
 *  kotlin.ranges.RangesKt
 *  kotlin.text.StringsKt
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattle;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.ClientBattleSide;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.SingleActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleActionSelection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleBackButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleGeneralActionSelection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleSwitchPokemonSelection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.ForfeitConfirmationSelection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.widgets.BattleMessagePane;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CurrentKeyAccessorKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.PartySendBinding;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.battle.RemoveSpectatorPacket;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\f\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 K2\u00020\u0001:\u0001KB\u0007\u00a2\u0006\u0004\bJ\u0010\u000fJ\u0017\u0010\u0005\u001a\u00020\u00042\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001f\u0010\f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\f\u0010\rJ\u000f\u0010\u000e\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001d\u0010\u0014\u001a\u00020\u00022\u0006\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u0012\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0014\u0010\u0017\u001a\t\u0018\u00010\u0002\u00a2\u0006\u0002\b\u0016\u00a2\u0006\u0004\b\u0017\u0010\u0018J\u000f\u0010\u0019\u001a\u00020\u0004H\u0014\u00a2\u0006\u0004\b\u0019\u0010\u000fJ'\u0010\u001e\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u001e\u0010\u001fJ7\u0010\"\u001a\u00020\u000b2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\u001c\u001a\u00020\u001a2\u0006\u0010\u001d\u001a\u00020\t2\u0006\u0010 \u001a\u00020\u001a2\u0006\u0010!\u001a\u00020\u001aH\u0016\u00a2\u0006\u0004\b\"\u0010#J\r\u0010$\u001a\u00020\u0004\u00a2\u0006\u0004\b$\u0010\u000fJ/\u0010)\u001a\u00020\u00042\u0006\u0010&\u001a\u00020%2\u0006\u0010\u001b\u001a\u00020\t2\u0006\u0010\u001c\u001a\u00020\t2\u0006\u0010(\u001a\u00020'H\u0016\u00a2\u0006\u0004\b)\u0010*J\u001d\u0010-\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010,\u001a\u00020+\u00a2\u0006\u0004\b-\u0010.J\u000f\u0010/\u001a\u00020\u000bH\u0016\u00a2\u0006\u0004\b/\u00100R\u0019\u0010\u0011\u001a\u0004\u0018\u00010\u00108\u0006\u00a2\u0006\f\n\u0004\b\u0011\u00101\u001a\u0004\b2\u00103R\u0016\u00105\u001a\u0002048\u0002@\u0002X\u0082.\u00a2\u0006\u0006\n\u0004\b5\u00106R\"\u00107\u001a\u00020'8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b7\u00108\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R.\u0010?\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040>0=8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b?\u0010@\u001a\u0004\bA\u0010B\"\u0004\bC\u0010DR\u0017\u0010F\u001a\u00020E8\u0006\u00a2\u0006\f\n\u0004\bF\u0010G\u001a\u0004\bH\u0010I\u00a8\u0006L"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;", "Lnet/minecraft/client/gui/screens/Screen;", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleActionSelection;", "newSelection", "", "changeActionSelection", "(Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleActionSelection;)V", "", "chr", "", "modifiers", "", "charTyped", "(CI)Z", "close", "()V", "Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "actor", "Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;", "request", "deriveRootActionSelection", "(Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;)Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleActionSelection;", "Lkotlin/internal/NoInfer;", "getCurrentActionSelection", "()Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleActionSelection;", "init", "", "mouseX", "mouseY", "button", "mouseClicked", "(DDI)Z", "deltaX", "deltaY", "mouseDragged", "(DDIDD)Z", "removeInvalidBattleActionSelection", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;", "response", "selectAction", "(Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;Lcom/cobblemon/mod/common/battles/ShowdownActionResponse;)V", "shouldPause", "()Z", "Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "getActor", "()Lcom/cobblemon/mod/common/client/battle/ClientBattleActor;", "Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane;", "messagePane", "Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleMessagePane;", "opacity", "F", "getOpacity", "()F", "setOpacity", "(F)V", "", "Lkotlin/Function0;", "queuedActions", "Ljava/util/List;", "getQueuedActions", "()Ljava/util/List;", "setQueuedActions", "(Ljava/util/List;)V", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleBackButton;", "specBackButton", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleBackButton;", "getSpecBackButton", "()Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleBackButton;", "<init>", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nBattleGUI.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleGUI.kt\ncom/cobblemon/mod/common/client/gui/battle/BattleGUI\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,182:1\n1#2:183\n800#3,11:184\n800#3,11:195\n800#3,11:206\n1855#3,2:217\n1855#3,2:219\n1855#3,2:221\n*S KotlinDebug\n*F\n+ 1 BattleGUI.kt\ncom/cobblemon/mod/common/client/gui/battle/BattleGUI\n*L\n67#1:184,11\n70#1:195,11\n86#1:206,11\n86#1:217,2\n88#1:219,2\n139#1:221,2\n*E\n"})
public final class BattleGUI
extends Screen {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private BattleMessagePane messagePane;
    private float opacity;
    @Nullable
    private final ClientBattleActor actor;
    @NotNull
    private final BattleBackButton specBackButton;
    @NotNull
    private List<Function0<Unit>> queuedActions;
    public static final int OPTION_VERTICAL_SPACING = 3;
    public static final int OPTION_HORIZONTAL_SPACING = 3;
    public static final int OPTION_ROOT_X = 12;
    public static final int OPTION_VERTICAL_OFFSET = 85;
    @NotNull
    private static final ResourceLocation fightResource = MiscUtilsKt.cobblemonResource("textures/gui/battle/battle_menu_fight.png");
    @NotNull
    private static final ResourceLocation bagResource = MiscUtilsKt.cobblemonResource("textures/gui/battle/battle_menu_bag.png");
    @NotNull
    private static final ResourceLocation switchResource = MiscUtilsKt.cobblemonResource("textures/gui/battle/battle_menu_switch.png");
    @NotNull
    private static final ResourceLocation runResource = MiscUtilsKt.cobblemonResource("textures/gui/battle/battle_menu_run.png");

    public BattleGUI() {
        super((Component)LocalizationUtilsKt.battleLang("gui.title", new Object[0]));
        ClientBattleActor clientBattleActor;
        BattleGUI battleGUI = this;
        Object object = CobblemonClient.INSTANCE.getBattle();
        if (object != null && (object = ((ClientBattle)object).getSide1()) != null && (object = ((ClientBattleSide)object).getActors()) != null) {
            Object v3;
            BattleGUI battleGUI2;
            block3: {
                Iterable iterable = (Iterable)object;
                battleGUI2 = battleGUI;
                Iterable iterable2 = iterable;
                for (Object t : iterable2) {
                    ClientBattleActor it = (ClientBattleActor)t;
                    boolean bl = false;
                    LocalPlayer localPlayer = Minecraft.m_91087_().f_91074_;
                    if (!Intrinsics.areEqual((Object)it.getUuid(), (Object)(localPlayer != null ? localPlayer.m_20148_() : null))) continue;
                    v3 = t;
                    break block3;
                }
                v3 = null;
            }
            battleGUI = battleGUI2;
            clientBattleActor = v3;
        } else {
            clientBattleActor = null;
        }
        battleGUI.actor = clientBattleActor;
        this.specBackButton = new BattleBackButton(12.0f, (float)Minecraft.m_91087_().m_91268_().m_85446_() - 32.0f);
        this.queuedActions = new ArrayList();
    }

    public final float getOpacity() {
        return this.opacity;
    }

    public final void setOpacity(float f) {
        this.opacity = f;
    }

    @Nullable
    public final ClientBattleActor getActor() {
        return this.actor;
    }

    @NotNull
    public final BattleBackButton getSpecBackButton() {
        return this.specBackButton;
    }

    @NotNull
    public final List<Function0<Unit>> getQueuedActions() {
        return this.queuedActions;
    }

    public final void setQueuedActions(@NotNull List<Function0<Unit>> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.queuedActions = list;
    }

    protected void m_7856_() {
        super.m_7856_();
        ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
        Intrinsics.checkNotNull((Object)clientBattle);
        this.messagePane = new BattleMessagePane(clientBattle.getMessages());
        BattleMessagePane battleMessagePane = this.messagePane;
        if (battleMessagePane == null) {
            Intrinsics.throwUninitializedPropertyAccessException((String)"messagePane");
            battleMessagePane = null;
        }
        this.m_142416_((GuiEventListener)battleMessagePane);
    }

    public final void changeActionSelection(@Nullable BattleActionSelection newSelection) {
        Object v1;
        block1: {
            List list = this.m_6702_();
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"children()");
            Iterable iterable = list;
            for (Object t : iterable) {
                GuiEventListener it = (GuiEventListener)t;
                boolean bl = false;
                if (!(it instanceof BattleActionSelection)) continue;
                v1 = t;
                break block1;
            }
            v1 = null;
        }
        GuiEventListener current = v1;
        this.queuedActions.add(new Function0<Unit>(current, this, newSelection){
            final /* synthetic */ GuiEventListener $current;
            final /* synthetic */ BattleGUI this$0;
            final /* synthetic */ BattleActionSelection $newSelection;
            {
                this.$current = $current;
                this.this$0 = $receiver;
                this.$newSelection = $newSelection;
                super(0);
            }

            public final void invoke() {
                GuiEventListener guiEventListener = this.$current;
                if (guiEventListener != null) {
                    GuiEventListener guiEventListener2 = guiEventListener;
                    BattleGUI battleGUI = this.this$0;
                    GuiEventListener p0 = guiEventListener2;
                    boolean bl = false;
                    BattleGUI.access$remove(battleGUI, p0);
                }
                if (this.$newSelection != null) {
                    BattleGUI.access$addDrawableChild(this.this$0, (GuiEventListener)this.$newSelection);
                }
            }
        });
    }

    /*
     * WARNING - void declaration
     */
    @Nullable
    public final BattleActionSelection getCurrentActionSelection() {
        void $this$filterIsInstanceTo$iv$iv;
        List list = this.m_6702_();
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"children()");
        Iterable $this$filterIsInstance$iv = list;
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
            if (!(element$iv$iv instanceof BattleActionSelection)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (BattleActionSelection)((Object)CollectionsKt.firstOrNull((List)((List)destination$iv$iv)));
    }

    /*
     * WARNING - void declaration
     */
    public final void removeInvalidBattleActionSelection() {
        block1: {
            void $this$filterIsInstanceTo$iv$iv;
            List list = this.m_6702_();
            Intrinsics.checkNotNullExpressionValue((Object)list, (String)"children()");
            Iterable $this$filterIsInstance$iv = list;
            boolean $i$f$filterIsInstance = false;
            Iterable iterable = $this$filterIsInstance$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterIsInstanceTo = false;
            for (Object element$iv$iv : $this$filterIsInstanceTo$iv$iv) {
                if (!(element$iv$iv instanceof BattleActionSelection)) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            BattleActionSelection battleActionSelection = (BattleActionSelection)((Object)CollectionsKt.firstOrNull((List)((List)destination$iv$iv)));
            if (battleActionSelection == null) break block1;
            BattleActionSelection it = battleActionSelection;
            boolean bl = false;
            List list2 = this.m_6702_();
            Intrinsics.checkNotNullExpressionValue((Object)list2, (String)"children()");
            TypeIntrinsics.asMutableCollection((Object)list2).remove((Object)it);
        }
    }

    public final void selectAction(@NotNull SingleActionRequest request, @NotNull ShowdownActionResponse response) {
        Intrinsics.checkNotNullParameter((Object)request, (String)"request");
        Intrinsics.checkNotNullParameter((Object)response, (String)"response");
        ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
        if (clientBattle == null) {
            return;
        }
        ClientBattle battle2 = clientBattle;
        if (request.getResponse() == null) {
            request.setResponse(response);
            this.changeActionSelection(null);
            battle2.checkForFinishedChoosing();
        }
    }

    public void m_88315_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        BattleActionSelection currentSelection;
        Iterable $this$forEach$iv;
        Iterator $this$filterIsInstanceTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        this.opacity = (float)CobblemonClient.INSTANCE.getBattleOverlay().getOpacityRatio();
        List list = this.m_6702_();
        Intrinsics.checkNotNullExpressionValue((Object)list, (String)"children()");
        Iterable $this$filterIsInstance$iv = list;
        boolean $i$f$filterIsInstance = false;
        Iterable iterable = $this$filterIsInstance$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterIsInstanceTo = false;
        Iterator iterator = $this$filterIsInstanceTo$iv$iv.iterator();
        while (iterator.hasNext()) {
            Object element$iv$iv = iterator.next();
            if (!(element$iv$iv instanceof BattleMessagePane)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        $this$filterIsInstance$iv = (List)destination$iv$iv;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            BattleMessagePane it = (BattleMessagePane)((Object)element$iv);
            boolean bl = false;
            it.setOpacity(RangesKt.coerceAtLeast((float)this.opacity, (float)0.3f));
        }
        $this$forEach$iv = this.queuedActions;
        $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            Function0 it = (Function0)element$iv;
            boolean bl = false;
            it.invoke();
        }
        this.queuedActions.clear();
        super.m_88315_(context, mouseX, mouseY, delta);
        ClientBattle battle2 = CobblemonClient.INSTANCE.getBattle();
        if (battle2 == null) {
            this.m_7379_();
            return;
        }
        if (CobblemonClient.INSTANCE.getBattleOverlay().getOpacityRatio() <= 0.1) {
            ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
            boolean bl = clientBattle != null ? clientBattle.getMinimised() : false;
            if (bl) {
                this.m_7379_();
                return;
            }
        }
        if (this.actor != null) {
            if (battle2.getMustChoose()) {
                SingleActionRequest unanswered;
                if (this.getCurrentActionSelection() == null && (unanswered = battle2.getFirstUnansweredRequest()) != null) {
                    this.changeActionSelection(this.deriveRootActionSelection(this.actor, unanswered));
                }
            } else if (this.getCurrentActionSelection() != null) {
                this.changeActionSelection(null);
            }
        }
        if (battle2.getSpectating()) {
            PoseStack poseStack = context.m_280168_();
            Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"context.matrices");
            this.specBackButton.render(poseStack, mouseX, mouseY, delta);
        }
        if ((currentSelection = this.getCurrentActionSelection()) == null || currentSelection instanceof BattleGeneralActionSelection) {
            element$iv = new Object[1];
            Intrinsics.checkNotNullExpressionValue((Object)CurrentKeyAccessorKt.boundKey(PartySendBinding.INSTANCE).m_84875_(), (String)"PartySendBinding.boundKey().localizedText");
            MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("ui.hide_label", element$iv);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"ui.hide_labe\u2026boundKey().localizedText)");
            RenderHelperKt.drawScaledText$default(context, null, mutableComponent, Minecraft.m_91087_().m_91268_().m_85445_() / 2, Minecraft.m_91087_().m_91268_().m_85446_() / 5, 0.0f, Float.valueOf(0.75f * this.opacity), 0, 0, true, false, null, null, 7586, null);
        } else if (currentSelection instanceof ForfeitConfirmationSelection) {
            element$iv = new Object[1];
            Intrinsics.checkNotNullExpressionValue((Object)CurrentKeyAccessorKt.boundKey(PartySendBinding.INSTANCE).m_84875_(), (String)"PartySendBinding.boundKey().localizedText");
            MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("ui.forfeit_confirmation", element$iv);
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"ui.forfeit_c\u2026boundKey().localizedText)");
            RenderHelperKt.drawScaledText$default(context, null, mutableComponent, Minecraft.m_91087_().m_91268_().m_85445_() / 2, Minecraft.m_91087_().m_91268_().m_85446_() / 5, 0.0f, Float.valueOf(0.75f * this.opacity), 0, 0, true, false, null, null, 7586, null);
        }
        Iterable $this$forEach$iv2 = this.queuedActions;
        boolean $i$f$forEach2 = false;
        for (Object element$iv : $this$forEach$iv2) {
            Function0 it = (Function0)element$iv;
            boolean bl = false;
            it.invoke();
        }
        this.queuedActions.clear();
    }

    @NotNull
    public final BattleActionSelection deriveRootActionSelection(@NotNull ClientBattleActor actor, @NotNull SingleActionRequest request) {
        Intrinsics.checkNotNullParameter((Object)actor, (String)"actor");
        Intrinsics.checkNotNullParameter((Object)request, (String)"request");
        return request.getForceSwitch() ? (BattleActionSelection)new BattleSwitchPokemonSelection(this, request) : (BattleActionSelection)new BattleGeneralActionSelection(this, request);
    }

    public boolean m_7043_() {
        return false;
    }

    public void m_7379_() {
        super.m_7379_();
        ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
        if (clientBattle != null) {
            clientBattle.setMinimised(true);
        }
        PartySendBinding.INSTANCE.setCanApplyChange(false);
        PartySendBinding.INSTANCE.setWasDown(true);
    }

    public boolean m_7979_(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (this.messagePane != null) {
            BattleMessagePane battleMessagePane = this.messagePane;
            if (battleMessagePane == null) {
                Intrinsics.throwUninitializedPropertyAccessException((String)"messagePane");
                battleMessagePane = null;
            }
            battleMessagePane.m_7979_(mouseX, mouseY, button, deltaX, deltaY);
        }
        return super.m_7979_(mouseX, mouseY, button, deltaX, deltaY);
    }

    public boolean m_5534_(char chr, int modifiers) {
        if (StringsKt.equals((String)String.valueOf(chr), (String)CurrentKeyAccessorKt.boundKey(PartySendBinding.INSTANCE).m_84875_().getString(), (boolean)true) && CobblemonClient.INSTANCE.getBattleOverlay().getOpacity() == 1.0 && PartySendBinding.INSTANCE.canAction()) {
            ClientBattle battle2;
            ClientBattle clientBattle = CobblemonClient.INSTANCE.getBattle();
            if (clientBattle == null) {
                return false;
            }
            battle2.setMinimised(!(battle2 = clientBattle).getMinimised());
            PartySendBinding.INSTANCE.actioned();
            return true;
        }
        return super.m_5534_(chr, modifiers);
    }

    public boolean m_6375_(double mouseX, double mouseY, int button) {
        ClientBattle battle2;
        ClientBattle clientBattle = battle2 = CobblemonClient.INSTANCE.getBattle();
        boolean bl = clientBattle != null ? clientBattle.getSpectating() : false;
        if (bl && this.specBackButton.isHovered(mouseX, mouseY)) {
            new RemoveSpectatorPacket(battle2.getBattleId()).sendToServer();
            CobblemonClient.INSTANCE.endBattle();
        }
        return super.m_6375_(mouseX, mouseY, button);
    }

    public static final /* synthetic */ GuiEventListener access$addDrawableChild(BattleGUI $this, GuiEventListener drawableElement) {
        return $this.m_142416_(drawableElement);
    }

    public static final /* synthetic */ void access$remove(BattleGUI $this, GuiEventListener child) {
        $this.m_169411_(child);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0005\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0004R\u0014\u0010\u0007\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0007\u0010\u0004R\u0017\u0010\t\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\n\u001a\u0004\b\u000e\u0010\fR\u0017\u0010\u000f\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\n\u001a\u0004\b\u0010\u0010\fR\u0017\u0010\u0011\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\u0011\u0010\n\u001a\u0004\b\u0012\u0010\f\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI$Companion;", "", "", "OPTION_HORIZONTAL_SPACING", "I", "OPTION_ROOT_X", "OPTION_VERTICAL_OFFSET", "OPTION_VERTICAL_SPACING", "Lnet/minecraft/resources/ResourceLocation;", "bagResource", "Lnet/minecraft/resources/ResourceLocation;", "getBagResource", "()Lnet/minecraft/resources/ResourceLocation;", "fightResource", "getFightResource", "runResource", "getRunResource", "switchResource", "getSwitchResource", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getFightResource() {
            return fightResource;
        }

        @NotNull
        public final ResourceLocation getBagResource() {
            return bagResource;
        }

        @NotNull
        public final ResourceLocation getSwitchResource() {
            return switchResource;
        }

        @NotNull
        public final ResourceLocation getRunResource() {
            return runResource;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

