package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.gui.dialogue

import java.util.UUID
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.GuiGraphics
import net.minecraft.client.gui.screens.inventory.InventoryScreen
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.entity.player.Player
import org.joml.Quaternionf
import org.joml.Quaternionfc

public class PlayerRenderableFace(playerId: UUID) : RenderableFace {
   public final val playerId: UUID

   init {
      this.playerId = playerId;
   }

   public override fun render(drawContext: GuiGraphics, partialTicks: Float) {
      val var10000: ClientLevel = Minecraft.m_91087_().f_91073_;
      if (var10000 != null) {
         val var16: Player = var10000.m_46003_(this.playerId);
         if (var16 != null) {
            val f: Float = (float)Math.atan(-0.5);
            val g: Float = (float)Math.atan(0.125);
            val quaternionf: Quaternionf = new Quaternionf().rotateZ((float) Math.PI);
            val quaternionf2: Quaternionf = new Quaternionf().rotateX(g * 20.0F * (float) (Math.PI / 180.0));
            quaternionf.mul(quaternionf2 as Quaternionfc);
            val oldBodyYaw: Float = var16.f_20883_;
            val oldEntityYaw: Float = var16.m_146908_();
            val oldPitch: Float = var16.m_146909_();
            val oldPrevHeadYaw: Float = var16.f_20886_;
            val oldHeadYaw: Float = var16.f_20885_;
            var16.f_20883_ = 180.0F + f * 20.0F;
            var16.m_146922_(180.0F + f * 40.0F);
            var16.m_146926_(-g * 20.0F);
            var16.f_20885_ = var16.m_146908_();
            var16.f_20886_ = var16.m_146908_();
            InventoryScreen.m_280432_(drawContext, 0, 75, 37, quaternionf, quaternionf2, var16 as LivingEntity);
            var16.f_20883_ = oldBodyYaw;
            var16.m_146922_(oldEntityYaw);
            var16.m_146926_(oldPitch);
            var16.f_20886_ = oldPrevHeadYaw;
            var16.f_20885_ = oldHeadYaw;
            return;
         }
      }
   }
}
