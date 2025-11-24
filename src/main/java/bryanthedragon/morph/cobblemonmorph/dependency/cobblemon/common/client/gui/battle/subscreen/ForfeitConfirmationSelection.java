/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.ForfeitActionResponse;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.battle.SingleActionRequest;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.BattleGUI;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleActionSelection;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.subscreen.BattleBackButton;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.battle.widgets.BattleOptionTile;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt;
import com.mojang.blaze3d.vertex.PoseStack;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\u0012\u0006\u0010\u001c\u001a\u00020\u001b\u0012\u0006\u0010\u001e\u001a\u00020\u001d\u00a2\u0006\u0004\b\u001f\u0010 J'\u0010\b\u001a\u00020\u00072\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\b\u0010\tJ/\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\u000b\u001a\u00020\n2\u0006\u0010\u0003\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\fH\u0014\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u0017\u0010\u0012\u001a\u00020\u00118\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0017\u001a\u00020\u00168\u0006\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\u00a8\u0006!"}, d2={"Lcom/cobblemon/mod/common/client/gui/battle/subscreen/ForfeitConfirmationSelection;", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleActionSelection;", "", "mouseX", "mouseY", "", "button", "", "mouseClicked", "(DDI)Z", "Lnet/minecraft/client/gui/GuiGraphics;", "context", "", "delta", "", "renderButton", "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleBackButton;", "backButton", "Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleBackButton;", "getBackButton", "()Lcom/cobblemon/mod/common/client/gui/battle/subscreen/BattleBackButton;", "Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleOptionTile;", "forfeitButton", "Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleOptionTile;", "getForfeitButton", "()Lcom/cobblemon/mod/common/client/gui/battle/widgets/BattleOptionTile;", "Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;", "battleGUI", "Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;", "request", "<init>", "(Lcom/cobblemon/mod/common/client/gui/battle/BattleGUI;Lcom/cobblemon/mod/common/client/battle/SingleActionRequest;)V", "common"})
public final class ForfeitConfirmationSelection
extends BattleActionSelection {
    @NotNull
    private final BattleOptionTile forfeitButton;
    @NotNull
    private final BattleBackButton backButton;

    public ForfeitConfirmationSelection(final @NotNull BattleGUI battleGUI, final @NotNull SingleActionRequest request) {
        Intrinsics.checkNotNullParameter((Object)((Object)battleGUI), (String)"battleGUI");
        Intrinsics.checkNotNullParameter((Object)request, (String)"request");
        MutableComponent mutableComponent = LocalizationUtilsKt.battleLang("ui.forfeit_confirmation", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent, (String)"battleLang(\"ui.forfeit_confirmation\")");
        super(battleGUI, request, 12, 12, 250, 100, mutableComponent);
        this.backButton = new BattleBackButton((float)this.m_252754_() - 3.0f, (float)Minecraft.m_91087_().m_91268_().m_85446_() - 22.0f);
        int x = Minecraft.m_91087_().m_91268_().m_85445_() / 2 - 45;
        int y = Minecraft.m_91087_().m_91268_().m_85446_() / 2 - 13;
        ResourceLocation resourceLocation = BattleGUI.Companion.getRunResource();
        MutableComponent mutableComponent2 = LocalizationUtilsKt.battleLang("ui.forfeit", new Object[0]);
        Intrinsics.checkNotNullExpressionValue((Object)mutableComponent2, (String)"battleLang(\"ui.forfeit\")");
        this.forfeitButton = new BattleOptionTile(battleGUI, x, y, resourceLocation, mutableComponent2, (Function0<Unit>)((Function0)new Function0<Unit>(){

            public final void invoke() {
                battleGUI.selectAction(request, new ForfeitActionResponse());
                this.m_7435_(Minecraft.m_91087_().m_91106_());
            }
        }));
    }

    @NotNull
    public final BattleOptionTile getForfeitButton() {
        return this.forfeitButton;
    }

    @NotNull
    public final BattleBackButton getBackButton() {
        return this.backButton;
    }

    protected void m_87963_(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        Intrinsics.checkNotNullParameter((Object)context, (String)"context");
        if (this.getOpacity() <= 0.05f) {
            return;
        }
        this.forfeitButton.m_88315_(context, mouseX, mouseY, delta);
        PoseStack poseStack = context.m_280168_();
        Intrinsics.checkNotNullExpressionValue((Object)poseStack, (String)"context.matrices");
        this.backButton.render(poseStack, mouseX, mouseY, delta);
    }

    @Override
    public boolean m_6375_(double mouseX, double mouseY, int button) {
        if (this.backButton.isHovered(mouseX, mouseY)) {
            this.getBattleGUI().changeActionSelection(null);
            this.m_7435_(Minecraft.m_91087_().m_91106_());
            return true;
        }
        return this.forfeitButton.m_6375_(mouseX, mouseY, button);
    }
}

