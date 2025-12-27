package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.block

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.entity.BerryBlockEntity
import com.mojang.blaze3d.vertex.VertexBuffer
import com.mojang.blaze3d.vertex.VertexBuffer.Usage

public class BerryBlockEntityRenderState : BerryBlockEntity.RenderState {
   public final val lastRenderFrame: Int = -1
   public open var needsRebuild: Boolean = true
   public final val vbo: VertexBuffer = new VertexBuffer(Usage.STATIC)
   public final var vboLightLevel: Int

   public override fun close() {
      this.vbo.close();
   }
}
