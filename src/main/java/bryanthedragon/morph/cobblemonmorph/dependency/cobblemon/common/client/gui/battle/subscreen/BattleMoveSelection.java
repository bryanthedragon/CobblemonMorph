/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Triple
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.client.sounds.SoundManager
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.sounds.SoundEvent
 *  net.minecraft.util.Mth
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.CobblemonSounds;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.gui.GuiUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.InBattleMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.MoveActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ShowdownMoveset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.Targetable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonResources;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.SingleActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.MoveCategoryIcon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.TypeIcon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleActionSelection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleBackButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleGimmickButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.DynamaxButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.RenderHelperKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.SimpleMathExtensionsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Triple;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000l\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 22\u00020\u0001:\u000223B\u0017\u0012\u0006\u0010-\u001a\u00020,\u0012\u0006\u0010/\u001a\u00020.\u00a2\u0006\u0004\b0\u00101J'\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\r\u001a\u00020\f2\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ/\u0010\u0013\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u0011H\u0014\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u0017\u0010\u0016\u001a\u00020\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019R\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a8\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u001d\u0010!\u001a\b\u0012\u0004\u0012\u00020 0\u001a8\u0006\u00a2\u0006\f\n\u0004\b!\u0010\u001d\u001a\u0004\b\"\u0010\u001fR\u0017\u0010$\u001a\u00020#8\u0006\u00a2\u0006\f\n\u0004\b$\u0010%\u001a\u0004\b&\u0010'R(\u0010(\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010\u001d\u001a\u0004\b)\u0010\u001f\"\u0004\b*\u0010+\u00a8\u00064"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection;", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleActionSelection;", "", "mouseX", "mouseY", "", "button", "", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/sounds/SoundManager;", "soundManager", "", "playDownSound", "(Lnet/minecraft/client/sounds/SoundManager;)V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleBackButton;", "backButton", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleBackButton;", "getBackButton", "()Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleBackButton;", "", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection$MoveTile;", "baseTiles", "Ljava/util/List;", "getBaseTiles", "()Ljava/util/List;", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleGimmickButton;", "gimmickButtons", "getGimmickButtons", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "moveSet", "Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "getMoveSet", "()Lcom/cobblemon/mod/common/battles/ShowdownMoveset;", "moveTiles", "getMoveTiles", "setMoveTiles", "(Ljava/util/List;)V", "Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;", "battleGUI", "Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;", "request", "<init>", "(Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;)V", "Companion", "MoveTile", "common"})
@SourceDebugExtension(value={"SMAP\nBattleMoveSelection.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleMoveSelection.kt\ncom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,214:1\n1559#2:215\n1590#2,4:216\n1559#2:220\n1590#2,4:221\n1855#2,2:225\n1855#2,2:227\n766#2:230\n857#2,2:231\n1855#2,2:233\n1#3:229\n*S KotlinDebug\n*F\n+ 1 BattleMoveSelection.kt\ncom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection\n*L\n60#1:215\n60#1:216,4\n73#1:220\n73#1:221,4\n186#1:225,2\n190#1:227,2\n205#1:230\n205#1:231,2\n205#1:233,2\n*E\n"})
public final class BattleMoveSelection
extends BattleActionSelection {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final ShowdownMoveset moveSet;
    @NotNull
    private final List<MoveTile> baseTiles;
    @NotNull
    private List<? extends MoveTile> moveTiles;
    @NotNull
    private final BattleBackButton backButton;
    @NotNull
    private final List<BattleGimmickButton> gimmickButtons;
    public static final int MOVE_WIDTH = 92;
    public static final int MOVE_HEIGHT = 24;
    public static final float MOVE_VERTICAL_SPACING = 5.0f;
    public static final float MOVE_HORIZONTAL_SPACING = 13.0f;
    @NotNull
    private static final ResourceLocation moveTexture = MiscUtils.cobblemonResource("textures/gui/battle/battle_move.png");
    @NotNull
    private static final ResourceLocation moveOverlayTexture = MiscUtils.cobblemonResource("textures/gui/battle/battle_move_overlay.png");

    /*
     * WARNING - void declaration
     */
    public BattleMoveSelection(@NotNull BattleGUI battleGUI, @NotNull SingleActionRequest request) {
        ShowdownMoveset.Gimmick inBattleMove;
        int index;
        Collection collection;
        int n;
        Iterable $this$mapIndexedTo$iv$iv;
        Iterable $this$mapIndexed$iv;
        Intrinsics.checkNotNullParameter((Object)((Object)battleGUI), (String)"battleGUI");
        Intrinsics.checkNotNullParameter((Object)request, (String)"request");
        int n2 = Minecraft.m_91087_().m_91268_().m_85446_() - 84;
        MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("ui.select_move", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"ui.select_move\")");
        super(battleGUI, request, 20, n2, 100, 100, mutableComponent);
        ShowdownMoveset showdownMoveset = request.getMoveSet();
        Intrinsics.checkNotNull((Object)showdownMoveset);
        this.moveSet = showdownMoveset;
        Iterable iterable = this.moveSet.getMoves();
        BattleMoveSelection battleMoveSelection = this;
        boolean $i$f$mapIndexed = false;
        void var5_6 = $this$mapIndexed$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$mapIndexed$iv, (int)10));
        boolean $i$f$mapIndexedTo = false;
        int index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexedTo$iv$iv) {
            float y;
            if ((n = index$iv$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            InBattleMove inBattleMove2 = (InBattleMove)item$iv$iv;
            int n3 = n;
            collection = destination$iv$iv;
            boolean bl = false;
            boolean isEven = index % 2 == false;
            float x = isEven ? (float)this.m_252754_() : (float)this.m_252754_() + 13.0f + (float)92;
            float f = y = index > 1 ? (float)(this.m_252907_() + 24) + 5.0f : (float)this.m_252907_();
            collection.add(this.moveSet.hasActiveGimmick() ? (MoveTile)new DynamaxButton.DynamaxTile(this, (InBattleMove)((Object)inBattleMove), x, y) : new MoveTile(this, (InBattleMove)((Object)inBattleMove), x, y));
        }
        battleMoveSelection.baseTiles = (List)destination$iv$iv;
        this.moveTiles = this.baseTiles;
        this.backButton = new BattleBackButton((float)this.m_252754_() - 3.0f, (float)Minecraft.m_91087_().m_91268_().m_85446_() - 22.0f);
        $this$mapIndexed$iv = this.moveSet.getGimmicks();
        battleMoveSelection = this;
        $i$f$mapIndexed = false;
        $this$mapIndexedTo$iv$iv = $this$mapIndexed$iv;
        destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$mapIndexed$iv, (int)10));
        $i$f$mapIndexedTo = false;
        index$iv$iv = 0;
        for (Object item$iv$iv : $this$mapIndexedTo$iv$iv) {
            void gimmick;
            if ((n = index$iv$iv++) < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            inBattleMove = (ShowdownMoveset.Gimmick)((Object)item$iv$iv);
            index = n;
            collection = destination$iv$iv;
            boolean bl = false;
            float initOff = 37.699997f;
            float xOff = initOff + (float)(26 * index);
            collection.add(BattleGimmickButton.Companion.create((ShowdownMoveset.Gimmick)gimmick, this, this.backButton.getX() + xOff, this.backButton.getY()));
        }
        battleMoveSelection.gimmickButtons = (List)destination$iv$iv;
    }

    @NotNull
    public final ShowdownMoveset getMoveSet() {
        return this.moveSet;
    }

    @NotNull
    public final List<MoveTile> getBaseTiles() {
        return this.baseTiles;
    }

    @NotNull
    public final List<MoveTile> getMoveTiles() {
        return this.moveTiles;
    }

    public final void setMoveTiles(@NotNull List<? extends MoveTile> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.moveTiles = list;
    }

    @NotNull
    public final BattleBackButton getBackButton() {
        return this.backButton;
    }

    @NotNull
    public final List<BattleGimmickButton> getGimmickButtons() {
        return this.gimmickButtons;
    }

    protected void m_87963_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Object it;
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        Iterable $this$forEach$iv = this.moveTiles;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            it = (MoveTile)element$iv;
            boolean bl = false;
            ((MoveTile)it).render(context, mouseX, mouseY, delta);
        }
        PoseStack poseStack = context.m_280168_();
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"context.matrices");
        this.backButton.render(poseStack, mouseX, mouseY, delta);
        $this$forEach$iv = this.gimmickButtons;
        $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            it = (BattleGimmickButton)element$iv;
            boolean bl = false;
            PoseStack poseStack2 = context.m_280168_();
            Intrinsics.checkNotNullExpressionValue((Object)poseStack2, (String)"context.matrices");
            ((BattleGimmickButton)it).render(poseStack2, mouseX, mouseY, delta);
        }
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public boolean m_6375_(double mouseX, double mouseY, int button) {
        MoveTile moveTile;
        MoveTile move;
        Object object3;
        block9: {
            Object object2;
            MoveTile it;
            block8: {
                Iterable iterable = this.moveTiles;
                for (Object object3 : iterable) {
                    it = (MoveTile)object3;
                    boolean bl = false;
                    if (!it.isHovered(mouseX, mouseY)) continue;
                    object2 = object3;
                    break block8;
                }
                object2 = null;
            }
            move = (MoveTile)object2;
            Iterable iterable = this.gimmickButtons;
            object3 = iterable.iterator();
            while (object3.hasNext()) {
                it = object3.next();
                BattleGimmickButton it2 = (BattleGimmickButton)((Object)it);
                boolean bl = false;
                if (!it2.isHovered(mouseX, mouseY)) continue;
                moveTile = it;
                break block9;
            }
            moveTile = null;
        }
        BattleGimmickButton gimmick = (BattleGimmickButton)((Object)moveTile);
        if (move != null) {
            move.onClick();
            return true;
        }
        if (this.backButton.isHovered(mouseX, mouseY)) {
            SoundManager soundManager = Minecraft.m_91087_().m_91106_();
            Intrinsics.checkNotNullExpressionValue((Object)soundManager, (String)"getInstance().soundManager");
            this.m_7435_(soundManager);
            this.getBattleGUI().changeActionSelection(null);
        } else if (gimmick != null) {
            void $this$forEach$iv;
            void $this$filterTo$iv$iv;
            Iterable $this$filter$iv = this.gimmickButtons;
            boolean $i$f$filter = false;
            object3 = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                BattleGimmickButton it = (BattleGimmickButton)element$iv$iv;
                boolean bl = false;
                if (!(!Intrinsics.areEqual((Object)it, (Object)gimmick))) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            $this$filter$iv = (List)destination$iv$iv;
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                BattleGimmickButton it = (BattleGimmickButton)element$iv;
                boolean bl = false;
                it.setToggled(false);
            }
            this.moveTiles = gimmick.toggle() ? gimmick.getTiles() : this.baseTiles;
        }
        return false;
    }

    public void m_7435_(@NotNull SoundManager soundManager) {
        Intrinsics.checkNotNullParameter((Object)soundManager, (String)"soundManager");
        soundManager.m_120367_((SoundInstance)SimpleSoundInstance.m_119752_((SoundEvent)CobblemonSounds.GUI_CLICK, (float)1.0f));
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004R\u0014\u0010\u0006\u001a\u00020\u00058\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\u00058\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\b\u0010\u0007R\u0014\u0010\t\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\t\u0010\u0004R\u0017\u0010\u000b\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u000f\u001a\u00020\n8\u0006\u00a2\u0006\f\n\u0004\b\u000f\u0010\f\u001a\u0004\b\u0010\u0010\u000e\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection$Companion;", "", "", "MOVE_HEIGHT", "I", "", "MOVE_HORIZONTAL_SPACING", "F", "MOVE_VERTICAL_SPACING", "MOVE_WIDTH", "Lnet/minecraft/resources/ResourceLocation;", "moveOverlayTexture", "Lnet/minecraft/resources/ResourceLocation;", "getMoveOverlayTexture", "()Lnet/minecraft/resources/ResourceLocation;", "moveTexture", "getMoveTexture", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final ResourceLocation getMoveTexture() {
            return moveTexture;
        }

        @NotNull
        public final ResourceLocation getMoveOverlayTexture() {
            return moveOverlayTexture;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000n\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\f\b\u0016\u0018\u00002\u00020\u0001B'\u0012\u0006\u0010\u0018\u001a\u00020\u0017\u0012\u0006\u0010\u0013\u001a\u00020\u0012\u0012\u0006\u0010:\u001a\u00020\u000e\u0012\u0006\u0010>\u001a\u00020\u000e\u00a2\u0006\u0004\b@\u0010AJ\u001d\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\r\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\t\u0010\nJ-\u0010\u0010\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0003\u001a\u00020\r2\u0006\u0010\u0004\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u000e\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u0017\u0010\u0013\u001a\u00020\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u0017\u0010\u0018\u001a\u00020\u00178\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\"\u0010\u001d\u001a\u00020\u001c8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u001e\u001a\u0004\b\u001f\u0010 \"\u0004\b!\u0010\"R\u0014\u0010&\u001a\u00020#8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b$\u0010%R4\u0010(\u001a\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020'8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010)\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u0014\u00100\u001a\u00020\u00058VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b.\u0010/R\u001c\u00105\u001a\n\u0012\u0004\u0012\u000202\u0018\u0001018VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b3\u00104R\u0013\u00109\u001a\u0004\u0018\u0001068F\u00a2\u0006\u0006\u001a\u0004\b7\u00108R\u0017\u0010:\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b:\u0010;\u001a\u0004\b<\u0010=R\u0017\u0010>\u001a\u00020\u000e8\u0006\u00a2\u0006\f\n\u0004\b>\u0010;\u001a\u0004\b?\u0010=\u00a8\u0006B"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection$MoveTile;", "", "", "mouseX", "mouseY", "", "isHovered", "(DD)Z", "", "onClick", "()V", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "", "delta", "render", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/battles/InBattleMove;", "move", "Lcom/cobblemon/mod/common/battles/InBattleMove;", "getMove", "()Lcom/cobblemon/mod/common/battles/InBattleMove;", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection;", "moveSelection", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection;", "getMoveSelection", "()Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection;", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "moveTemplate", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getMoveTemplate", "()Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "setMoveTemplate", "(Lcom/cobblemon/mod/common/api/moves/MoveTemplate;)V", "Lcom/cobblemon/mod/common/battles/MoveActionResponse;", "getResponse", "()Lcom/cobblemon/mod/common/battles/MoveActionResponse;", "response", "Lkotlin/Triple;", "rgb", "Lkotlin/Triple;", "getRgb", "()Lkotlin/Triple;", "setRgb", "(Lkotlin/Triple;)V", "getSelectable", "()Z", "selectable", "", "Lcom/cobblemon/mod/common/battles/Targetable;", "getTargetList", "()Ljava/util/List;", "targetList", "", "getTargetPnx", "()Ljava/lang/String;", "targetPnx", "x", "F", "getX", "()F", "y", "getY", "<init>", "(Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleMoveSelection;Lcom/cobblemon/mod/common/battles/InBattleMove;FF)V", "common"})
    public static class MoveTile {
        @NotNull
        private final BattleMoveSelection moveSelection;
        @NotNull
        private final InBattleMove move;
        private final float x;
        private final float y;
        @NotNull
        private MoveTemplate moveTemplate;
        @NotNull
        private Triple<Double, Double, Double> rgb;

        public MoveTile(@NotNull BattleMoveSelection moveSelection, @NotNull InBattleMove move, float x, float y) {
            Intrinsics.checkNotNullParameter((Object)((Object)moveSelection), (String)"moveSelection");
            Intrinsics.checkNotNullParameter((Object)move, (String)"move");
            this.moveSelection = moveSelection;
            this.move = move;
            this.x = x;
            this.y = y;
            this.moveTemplate = Moves.INSTANCE.getByNameOrDummy(this.move.getId());
            this.rgb = SimpleMathExtensionsKt.toRGB(this.moveTemplate.getElementalType().getHue());
        }

        @NotNull
        public final BattleMoveSelection getMoveSelection() {
            return this.moveSelection;
        }

        @NotNull
        public final InBattleMove getMove() {
            return this.move;
        }

        public final float getX() {
            return this.x;
        }

        public final float getY() {
            return this.y;
        }

        @NotNull
        public final MoveTemplate getMoveTemplate() {
            return this.moveTemplate;
        }

        public final void setMoveTemplate(@NotNull MoveTemplate moveTemplate) {
            Intrinsics.checkNotNullParameter((Object)moveTemplate, (String)"<set-?>");
            this.moveTemplate = moveTemplate;
        }

        @NotNull
        public final Triple<Double, Double, Double> getRgb() {
            return this.rgb;
        }

        public final void setRgb(@NotNull Triple<Double, Double, Double> triple) {
            Intrinsics.checkNotNullParameter(triple, (String)"<set-?>");
            this.rgb = triple;
        }

        @Nullable
        public List<Targetable> getTargetList() {
            return (List)this.move.getTarget().getTargetList().invoke((Object)this.moveSelection.getRequest().getActivePokemon());
        }

        @NotNull
        public MoveActionResponse getResponse() {
            return new MoveActionResponse(this.move.getId(), this.getTargetPnx(), null, 4, null);
        }

        public boolean getSelectable() {
            return !this.move.getDisabled();
        }

        @Nullable
        public final String getTargetPnx() {
            String string;
            List<Targetable> list = this.getTargetList();
            if (list != null) {
                List<Targetable> targets = list;
                boolean bl = false;
                string = targets.isEmpty() ? null : (targets.size() == 1 ? targets.get(0).getPNX() : null);
            } else {
                string = null;
            }
            return string;
        }

        public final void render(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            float selectConditionOpacity = this.moveSelection.getOpacity() * (!this.getSelectable() ? 0.5f : 1.0f);
            PoseStack poseStack = context.m_280168_();
            ResourceLocation resourceLocation = Companion.getMoveTexture();
            float f = this.x;
            float f2 = this.y;
            int n = this.getSelectable() && this.isHovered(mouseX, mouseY) ? 24 : 0;
            double d = ((Number)this.rgb.getFirst()).doubleValue();
            double d2 = ((Number)this.rgb.getSecond()).doubleValue();
            double d3 = ((Number)this.rgb.getThird()).doubleValue();
            Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
            GuiUtilsKt.blitk$default(poseStack, resourceLocation, Float.valueOf(f), Float.valueOf(f2), 24, 92, null, n, null, 48, null, d, d2, d3, Float.valueOf(selectConditionOpacity), false, 0.0f, 99648, null);
            poseStack = context.m_280168_();
            resourceLocation = Companion.getMoveOverlayTexture();
            f = this.x;
            f2 = this.y;
            float f3 = this.moveSelection.getOpacity();
            Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"matrices");
            GuiUtilsKt.blitk$default(poseStack, resourceLocation, Float.valueOf(f), Float.valueOf(f2), 24, 92, null, null, null, null, null, null, null, null, Float.valueOf(f3), false, 0.0f, 114624, null);
            new TypeIcon(Float.valueOf(this.x - (float)9), Float.valueOf(this.y + (float)2), this.moveTemplate.getElementalType(), null, false, false, 0.0f, 0.0f, this.moveSelection.getOpacity(), 248, null).render(context);
            new MoveCategoryIcon(Float.valueOf(this.x + (float)48), (double)this.y + 14.5, this.moveTemplate.getDamageCategory(), this.moveSelection.getOpacity()).render(context);
            RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), TextKt.bold(this.moveTemplate.getDisplayName()), Float.valueOf(this.x + (float)17), Float.valueOf(this.y + (float)2), 0.0f, Float.valueOf(selectConditionOpacity), 0, 0, false, true, null, null, 7072, null);
            MutableComponent mutableComponent = Component.m_237113_((String)(this.move.getPp() + "/" + this.move.getMaxpp()));
            Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"literal(\"${move.pp}/${move.maxpp}\")");
            MutableComponent movePPText = TextKt.bold(mutableComponent);
            if (this.move.getPp() <= Mth.m_14143_((float)((float)this.move.getMaxpp() / 2.0f))) {
                MutableComponent mutableComponent2 = movePPText = this.move.getPp() == 0 ? TextKt.red(movePPText) : TextKt.gold(movePPText);
            }
            if (this.move.getPp() == 100 && this.move.getMaxpp() == 100) {
                movePPText = TextKt.bold(TextKt.text("\u2014/\u2014"));
            }
            RenderHelperKt.drawScaledText$default(context, CobblemonResources.INSTANCE.getDEFAULT_LARGE(), movePPText, Float.valueOf(this.x + (float)75), Float.valueOf(this.y + (float)14), 0.0f, Float.valueOf(this.moveSelection.getOpacity()), 0, 0, true, false, null, null, 7584, null);
        }

        public final boolean isHovered(double mouseX, double mouseY) {
            return mouseX >= (double)this.x && mouseX <= (double)(this.x + (float)92) && mouseY >= (double)this.y && mouseY <= (double)(this.y + (float)24);
        }

        public final void onClick() {
            if (!this.getSelectable()) {
                return;
            }
            SoundManager soundManager = Minecraft.m_91087_().m_91106_();
            Intrinsics.checkNotNullExpressionValue((Object)soundManager, (String)"getInstance().soundManager");
            this.moveSelection.m_7435_(soundManager);
            this.moveSelection.getBattleGUI().selectAction(this.moveSelection.getRequest(), this.getResponse());
        }
    }
}

