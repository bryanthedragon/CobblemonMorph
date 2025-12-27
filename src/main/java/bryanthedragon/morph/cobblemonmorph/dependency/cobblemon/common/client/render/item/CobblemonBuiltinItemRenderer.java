package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.item

import com.mojang.blaze3d.vertex.PoseStack
import net.minecraft.client.renderer.MultiBufferSource
import net.minecraft.world.item.ItemDisplayContext
import net.minecraft.world.item.ItemStack

public interface CobblemonBuiltinItemRenderer {
   public abstract fun render(stack: ItemStack, mode: ItemDisplayContext, matrices: PoseStack, vertexConsumers: MultiBufferSource, light: Int, overlay: Int) {
   }
}
