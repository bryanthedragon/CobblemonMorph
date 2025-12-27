package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;
import java.util.function.Supplier

import net.minecraft.client.color.block.BlockColor
import net.minecraft.client.color.item.ItemColor
import net.minecraft.client.model.geom.ModelLayerLocation
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.particle.ParticleProvider
import net.minecraft.client.particle.SpriteSet
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.core.particles.ParticleOptions
import net.minecraft.core.particles.ParticleType
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType

public interface CobblemonClientImplementation {
   public abstract fun registerLayer(modelLayer: ModelLayerLocation, supplier: Supplier<LayerDefinition>) {
   }

   public abstract fun <T : ParticleOptions> registerParticleFactory(type: ParticleType<Any>, factory: (SpriteSet) -> ParticleProvider<Any>) {
   }

   public abstract fun registerBlockRenderType(layer: RenderType, vararg blocks: Block) {
   }

   public abstract fun registerItemColors(provider: ItemColor, vararg items: Item) {
   }

   public abstract fun registerBlockColors(provider: BlockColor, vararg blocks: Block) {
   }

   public abstract fun <T : BlockEntity> registerBlockEntityRenderer(type: BlockEntityType<out Any>, factory: BlockEntityRendererProvider<Any>) {
   }

   public abstract fun <T : Entity> registerEntityRenderer(type: EntityType<out Any>, factory: EntityRendererProvider<Any>) {
   }
}
