package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.AngleExtensionsKt
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.model.geom.ModelPart

@SourceDebugExtension(["SMAP\nModelPartTransformation.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ModelPartTransformation.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/pose/ModelPartTransformation\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,165:1\n1#2:166\n*E\n"])
public class ModelPartTransformation(modelPart: ModelPart) {
   public final val modelPart: ModelPart

   public final var pitch: Float
      public final get() {
         return this.rotation[0];
      }

      public final set(value) {
         this.rotation[0] = value;
      }


   public final var position: FloatArray

   public final var roll: Float
      public final get() {
         return this.rotation[2];
      }

      public final set(value) {
         this.rotation[2] = value;
      }


   public final var rotation: FloatArray
   public final val scale: FloatArray
   public final var visibility: Boolean?

   public final var xPos: Float
      public final get() {
         return this.position[0];
      }

      public final set(value) {
         this.position[0] = value;
      }


   public final var yPos: Float
      public final get() {
         return this.position[1];
      }

      public final set(value) {
         this.position[1] = value;
      }


   public final var yaw: Float
      public final get() {
         return this.rotation[1];
      }

      public final set(value) {
         this.rotation[1] = value;
      }


   public final var zPos: Float
      public final get() {
         return this.position[2];
      }

      public final set(value) {
         this.position[2] = value;
      }


   init {
      this.modelPart = modelPart;
      this.position = new float[]{0.0F, 0.0F, 0.0F};
      this.rotation = new float[]{0.0F, 0.0F, 0.0F};
      this.scale = new float[]{1.0F, 1.0F, 1.0F};
   }

   public fun apply(intensity: Float) {
      this.modelPart.f_104200_ = this.modelPart.f_104200_ + this.position[0] * intensity;
      this.modelPart.f_104201_ = this.modelPart.f_104201_ + this.position[1] * intensity;
      this.modelPart.f_104202_ = this.modelPart.f_104202_ + this.position[2] * intensity;
      this.modelPart.f_104203_ = this.modelPart.f_104203_ + this.rotation[0] * intensity;
      this.modelPart.f_104204_ = this.modelPart.f_104204_ + this.rotation[1] * intensity;
      this.modelPart.f_104205_ = this.modelPart.f_104205_ + this.rotation[2] * intensity;
      this.modelPart.f_233553_ = this.modelPart.f_233553_ * ((1 - this.scale[0]) * intensity + 1);
      this.modelPart.f_233554_ = this.modelPart.f_233554_ * ((1 - this.scale[1]) * intensity + 1);
      this.modelPart.f_233555_ = this.modelPart.f_233555_ * ((1 - this.scale[2]) * intensity + 1);
      if (this.visibility != null) {
         this.modelPart.f_104207_ = this.visibility;
      }
   }

   public fun set() {
      this.modelPart.f_104200_ = this.position[0];
      this.modelPart.f_104201_ = this.position[1];
      this.modelPart.f_104202_ = this.position[2];
      this.modelPart.f_104203_ = this.rotation[0];
      this.modelPart.f_104204_ = this.rotation[1];
      this.modelPart.f_104205_ = this.rotation[2];
      this.modelPart.f_233553_ = this.scale[0];
      this.modelPart.f_233554_ = this.scale[1];
      this.modelPart.f_233555_ = this.scale[2];
      if (this.visibility != null) {
         this.modelPart.f_104207_ = this.visibility;
      }
   }

   public fun withVisibility(visibility: Boolean): ModelPartTransformation {
      this.visibility = visibility;
      return this;
   }

   public fun withPosition(axis: Int, position: Number): ModelPartTransformation {
      this.position[axis] = position.floatValue();
      return this;
   }

   public fun withPosition(xPos: Number, yPos: Number, zPos: Number): ModelPartTransformation {
      return this.withPosition(0, xPos).withPosition(1, yPos).withPosition(2, zPos);
   }

   public fun withRotation(axis: Int, angleRadians: Number): ModelPartTransformation {
      this.rotation[axis] = angleRadians.floatValue();
      return this;
   }

   public fun withRotation(pitch: Number, yaw: Number, roll: Number): ModelPartTransformation {
      return this.withRotation(0, pitch).withRotation(1, yaw).withRotation(2, roll);
   }

   public fun addPosition(axis: Int, distance: Number): ModelPartTransformation {
      return this.withPosition(axis, this.position[axis] + distance.floatValue());
   }

   public fun addPosition(xDist: Number, yDist: Number, zDist: Number): ModelPartTransformation {
      return this.addPosition(0, xDist).addPosition(1, yDist).addPosition(2, zDist);
   }

   public fun addRotation(axis: Int, angleRadians: Number): ModelPartTransformation {
      return this.withRotation(axis, this.rotation[axis] + angleRadians.floatValue());
   }

   public fun addRotation(pitchRadians: Number, yawRadians: Number, rollRadians: Number): ModelPartTransformation {
      return this.addRotation(0, pitchRadians).addRotation(1, yawRadians).addRotation(2, rollRadians);
   }

   public fun addRotationDegrees(pitch: Number, yaw: Number, roll: Number): ModelPartTransformation {
      return this.addRotation(0, AngleExtensionsKt.toRadians(pitch.floatValue()))
         .addRotation(1, AngleExtensionsKt.toRadians(yaw.floatValue()))
         .addRotation(2, AngleExtensionsKt.toRadians(roll.floatValue()));
   }

   public fun multiplyScale(axis: Int, scale: Number): ModelPartTransformation {
      return this.withScale(axis, scale.floatValue() * this.scale[axis]);
   }

   public fun multiplyScale(scaleX: Number, scaleY: Number, scaleZ: Number): ModelPartTransformation {
      return this.multiplyScale(0, scaleX).multiplyScale(1, scaleY).multiplyScale(2, scaleZ);
   }

   public fun withRotationDegrees(pitch: Number, yaw: Number, roll: Number): ModelPartTransformation {
      return this.withRotation(
         AngleExtensionsKt.toRadians(pitch.floatValue()), AngleExtensionsKt.toRadians(yaw.floatValue()), AngleExtensionsKt.toRadians(roll.floatValue())
      );
   }

   public fun addRotationDegrees(axis: Int, angle: Number): ModelPartTransformation {
      return this.addRotation(axis, this.rotation[axis] + AngleExtensionsKt.toRadians(angle.floatValue()));
   }

   public fun withScale(axis: Int, scale: Number): ModelPartTransformation {
      this.scale[axis] = scale.floatValue();
      return this;
   }

   public fun withScale(scaleX: Number, scaleY: Number, scaleZ: Number): ModelPartTransformation {
      return this.withScale(0, scaleX).withScale(1, scaleY).withScale(2, scaleZ);
   }

   public companion object {
      public const val X_AXIS: Int
      public const val Y_AXIS: Int
      public const val Z_AXIS: Int

      public fun derive(modelPart: ModelPart): ModelPartTransformation {
         return new ModelPartTransformation(modelPart)
            .withPosition(modelPart.f_104200_, modelPart.f_104201_, modelPart.f_104202_)
            .withRotation(modelPart.f_104203_, modelPart.f_104204_, modelPart.f_104205_)
            .withScale(modelPart.f_233553_, modelPart.f_233554_, modelPart.f_233555_)
            .withVisibility(modelPart.f_104207_);
      }
   }
}
