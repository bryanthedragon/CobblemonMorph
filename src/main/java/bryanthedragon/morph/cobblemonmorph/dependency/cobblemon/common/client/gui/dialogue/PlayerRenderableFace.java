/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphics
 *  net.minecraft.client.gui.screens.inventory.InventoryScreen
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.world.entity.LivingEntity
 *  org.jetbrains.annotations.NotNull
 *  org.joml.Quaternionf
 *  org.joml.Quaternionfc
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue.RenderableFace;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u000f\u0012\u0006\u0010\n\u001a\u00020\t\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001f\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bR\u0017\u0010\n\u001a\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/client/gui/dialogue/PlayerRenderableFace;", "Lcom/cobblemon/mod/common/client/gui/dialogue/RenderableFace;", "Lnet/minecraft/client/gui/GuiGraphics;", "drawContext", "", "partialTicks", "", "render", "(Lnet/minecraft/client/gui/GuiGraphics;F)V", "Ljava/util/UUID;", "playerId", "Ljava/util/UUID;", "getPlayerId", "()Ljava/util/UUID;", "<init>", "(Ljava/util/UUID;)V", "common"})
public final class PlayerRenderableFace
implements RenderableFace {
    @NotNull
    private final UUID playerId;

    public PlayerRenderableFace(@NotNull UUID playerId) {
        Intrinsics.checkNotNullParameter((Object)playerId, (String)"playerId");
        this.playerId = playerId;
    }

    @NotNull
    public final UUID getPlayerId() {
        return this.playerId;
    }

    @Override
    public void render(@NotNull GuiGraphics drawContext, float partialTicks) {
        Intrinsics.checkNotNullParameter((Object)drawContext, (String)"drawContext");
        ClientLevel clientLevel = Minecraft.m_91087_().f_91073_;
        if (clientLevel == null || (clientLevel = clientLevel.m_46003_(this.playerId)) == null) {
            return;
        }
        ClientLevel entity2 = clientLevel;
        float f = (float)Math.atan(-0.5);
        float g = (float)Math.atan(0.125);
        Quaternionf quaternionf = new Quaternionf().rotateZ((float)Math.PI);
        Quaternionf quaternionf2 = new Quaternionf().rotateX(g * 20.0f * ((float)Math.PI / 180));
        quaternionf.mul((Quaternionfc)quaternionf2);
        float oldBodyYaw = entity2.f_20883_;
        float oldEntityYaw = entity2.m_146908_();
        float oldPitch = entity2.m_146909_();
        float oldPrevHeadYaw = entity2.f_20886_;
        float oldHeadYaw = entity2.f_20885_;
        entity2.f_20883_ = 180.0f + f * 20.0f;
        entity2.m_146922_(180.0f + f * 40.0f);
        entity2.m_146926_(-g * 20.0f);
        entity2.f_20885_ = entity2.m_146908_();
        entity2.f_20886_ = entity2.m_146908_();
        int size = 37;
        int xOffset = 0;
        int yOffset = 75;
        InventoryScreen.m_280432_((GuiGraphics)drawContext, (int)xOffset, (int)yOffset, (int)size, (Quaternionf)quaternionf, (Quaternionf)quaternionf2, (LivingEntity)((LivingEntity)entity2));
        entity2.f_20883_ = oldBodyYaw;
        entity2.m_146922_(oldEntityYaw);
        entity2.m_146926_(oldPitch);
        entity2.f_20886_ = oldPrevHeadYaw;
        entity2.f_20885_ = oldHeadYaw;
    }
}

