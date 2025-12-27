package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.client.spawn

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.entity.EntitySideDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.entity.GenericBedrockClientDelegate
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.generic.GenericBedrockEntity
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt
import io.netty.buffer.ByteBuf
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.entity.Entity

public class SpawnGenericBedrockPacket(category: ResourceLocation,
   aspects: Set<String>,
   poseType: PoseType,
   scale: Float,
   width: Float,
   height: Float,
   startAge: Int,
   vanillaSpawnPacket: ClientboundAddEntityPacket
) : SpawnExtraDataEntityPacket(vanillaSpawnPacket) {
   public final val aspects: Set<String>
   public final val category: ResourceLocation
   public final val height: Float
   public open val id: ResourceLocation
   public final val poseType: PoseType
   public final val scale: Float
   public final val startAge: Int
   public final val width: Float

   init {
      this.category = category;
      this.aspects = aspects;
      this.poseType = poseType;
      this.scale = scale;
      this.width = width;
      this.height = height;
      this.startAge = startAge;
      this.id = ID;
   }

   public override fun encodeEntityData(buffer: FriendlyByteBuf) {
      buffer.m_130085_(this.category);
      buffer.m_236828_(this.aspects, SpawnGenericBedrockPacket::encodeEntityData$lambda$0);
      NetExtensionsKt.writeSizedInt(buffer as ByteBuf, IntSize.U_BYTE, this.poseType.ordinal());
      buffer.writeFloat(this.scale);
      buffer.writeFloat(this.width);
      buffer.writeFloat(this.height);
      buffer.writeInt(this.startAge);
   }

   public open fun applyData(entity: GenericBedrockEntity) {
      entity.setCategory(this.category);
      entity.setAspects(this.aspects);
      entity.m_20088_().m_135381_(GenericBedrockEntity.Companion.getPOSE_TYPE(), this.poseType);
      entity.setScale(this.scale);
      entity.setColliderWidth(this.width);
      entity.setColliderHeight(this.height);
      entity.getDelegate().initialize(entity);
      entity.f_19797_ = this.startAge;
      val var10000: EntitySideDelegate = entity.getDelegate();
      (var10000 as GenericBedrockClientDelegate).updateAge(this.startAge);
   }

   public override fun checkType(entity: Entity): Boolean {
      return entity is GenericBedrockEntity;
   }

   @JvmStatic
   fun `encodeEntityData$lambda$0`(`$buffer`: FriendlyByteBuf, var1: FriendlyByteBuf, aspect: java.lang.String) {
      `$buffer`.m_130070_(aspect);
   }

   public companion object {
      public final val ID: ResourceLocation

      public fun decode(buffer: FriendlyByteBuf): SpawnGenericBedrockPacket {
         val category: ResourceLocation = buffer.m_130281_();
         val var10000: java.util.List = buffer.m_236845_(SpawnGenericBedrockPacket.Companion::decode$lambda$0);
         val aspects: java.util.Set = CollectionsKt.toSet(var10000);
         val poseType: PoseType = buffer.m_130066_(PoseType.class) as PoseType;
         val scale: Float = buffer.readFloat();
         val width: Float = buffer.readFloat();
         val height: Float = buffer.readFloat();
         val startAge: Int = buffer.readInt();
         val vanillaPacket: ClientboundAddEntityPacket = SpawnExtraDataEntityPacket.Companion.decodeVanillaPacket(buffer);
         return new SpawnGenericBedrockPacket(category, aspects, poseType, scale, width, height, startAge, vanillaPacket);
      }

      @JvmStatic
      fun `decode$lambda$0`(it: FriendlyByteBuf): java.lang.String {
         return it.m_130277_();
      }
   }
}
