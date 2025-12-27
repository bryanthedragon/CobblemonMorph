package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.snowstorm

import com.bedrockk.molang.runtime.MoLangRuntime
import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.DataResult
import com.mojang.serialization.DynamicOps
import com.mojang.serialization.codecs.PrimitiveCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import kotlin.random.Random
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.world.entity.Entity
import net.minecraft.world.phys.AABB
import net.minecraft.world.phys.Vec3

public class EntityBoundingBoxParticleEmitterShape(surfaceOnly: Boolean = true) : ParticleEmitterShape {
   public final var surfaceOnly: Boolean
   public open val type: ParticleEmitterShapeType

   init {
      this.surfaceOnly = surfaceOnly;
      this.type = ParticleEmitterShapeType.ENTITY_BOUNDING_BOX;
   }

   public override fun getNewParticlePosition(runtime: MoLangRuntime, entity: Entity?): Vec3 {
      val box: AABB = this.getBox(entity);
      val center: Vec3 = this.getCenter(runtime, entity);
      val sizes: Vec3 = new Vec3(box.f_82291_ - box.f_82288_, box.f_82292_ - box.f_82289_, box.f_82293_ - box.f_82290_);
      var var10000: Vec3;
      if (this.surfaceOnly) {
         switch (Random.Default.nextInt(6)) {
            case 0:
               var10000 = new Vec3(
                  -0.5 * sizes.f_82479_,
                  Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0,
                  Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0
               );
               break;
            case 1:
               var10000 = new Vec3(
                  0.5 * sizes.f_82479_,
                  Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0,
                  Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0
               );
               break;
            case 2:
               var10000 = new Vec3(
                  Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0,
                  -0.5 * sizes.f_82480_,
                  Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0
               );
               break;
            case 3:
               var10000 = new Vec3(
                  Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0,
                  0.5 * sizes.f_82480_,
                  Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2.0
               );
               break;
            case 4:
               var10000 = new Vec3(
                  Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0,
                  Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0,
                  -0.5 * sizes.f_82481_
               );
               break;
            default:
               var10000 = new Vec3(
                  Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2.0,
                  Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2.0,
                  0.5 * sizes.f_82481_
               );
         }
      } else {
         var10000 = new Vec3(
            Random.Default.nextDouble(sizes.f_82479_) - sizes.f_82479_ / 2,
            Random.Default.nextDouble(sizes.f_82480_) - sizes.f_82480_ / 2,
            Random.Default.nextDouble(sizes.f_82481_) - sizes.f_82481_ / 2
         );
      }

      var10000 = center.m_82549_(var10000);
      return var10000;
   }

   public override fun getCenter(runtime: MoLangRuntime, entity: Entity?): Vec3 {
      return this.getBox(entity).m_82399_();
   }

   public override fun <T> encode(ops: DynamicOps<Any>): DataResult<Any> {
      return CODEC.encodeStart(ops, this);
   }

   public fun getBox(entity: Entity?): AABB {
      if (entity != null) {
         val var10000: AABB = entity.m_20191_();
         if (var10000 != null) {
            return var10000;
         }
      }

      return AABB.m_165882_(new Vec3(0.0, 0.0, 0.0), 1.0, 2.0, 1.0);
   }

   public override fun readFromBuffer(buffer: FriendlyByteBuf) {
      this.surfaceOnly = buffer.readBoolean();
   }

   public override fun writeToBuffer(buffer: FriendlyByteBuf) {
      buffer.writeBoolean(this.surfaceOnly);
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$0`(it: EntityBoundingBoxParticleEmitterShape): java.lang.String {
      return it.getType().name();
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$1`(it: EntityBoundingBoxParticleEmitterShape): java.lang.Boolean {
      return it.surfaceOnly;
   }

   @JvmStatic
   fun `CODEC$lambda$3$lambda$2`(var0: java.lang.String, surfaceOnly: java.lang.Boolean): EntityBoundingBoxParticleEmitterShape {
      return new EntityBoundingBoxParticleEmitterShape(surfaceOnly);
   }

   @JvmStatic
   fun `CODEC$lambda$3`(instance: Instance): App {
      return instance.group(
            PrimitiveCodec.STRING.fieldOf("type").forGetter(EntityBoundingBoxParticleEmitterShape::CODEC$lambda$3$lambda$0) as App,
            PrimitiveCodec.BOOL.fieldOf("surfaceOnly").forGetter(EntityBoundingBoxParticleEmitterShape::CODEC$lambda$3$lambda$1) as App
         )
         .apply(instance as Applicative, EntityBoundingBoxParticleEmitterShape::CODEC$lambda$3$lambda$2);
   }

   fun EntityBoundingBoxParticleEmitterShape() {
      this(false, 1, null);
   }

   @JvmStatic
   fun {
      val var10000: Codec = RecordCodecBuilder.create(EntityBoundingBoxParticleEmitterShape::CODEC$lambda$3);
      CODEC = var10000;
   }

   public companion object {
      public final val CODEC: Codec<EntityBoundingBoxParticleEmitterShape>
   }
}
