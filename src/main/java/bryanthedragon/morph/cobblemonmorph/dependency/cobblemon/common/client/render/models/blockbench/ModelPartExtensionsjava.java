package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.Bone
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pose.ModelPartTransformation
import java.util.Arrays
import net.minecraft.client.model.geom.ModelPart

public fun ModelPart.createTransformation(): ModelPartTransformation {
   return new ModelPartTransformation(`$this$createTransformation`);
}

public fun ModelPart.getPosition(axis: Int): Float {
   var var10000: Float;
   switch (axis) {
      case 0:
         var10000 = `$this$getPosition`.f_104200_;
         break;
      case 1:
         var10000 = `$this$getPosition`.f_104201_;
         break;
      default:
         var10000 = `$this$getPosition`.f_104202_;
   }

   return var10000;
}

public fun Bone.getRotation(axis: Int): Float {
   var var10000: Float;
   if (`$this$getRotation` is ModelPart) {
      switch (axis) {
         case 0:
            var10000 = (`$this$getRotation` as ModelPart).f_104203_;
            break;
         case 1:
            var10000 = (`$this$getRotation` as ModelPart).f_104204_;
            break;
         default:
            var10000 = (`$this$getRotation` as ModelPart).f_104205_;
      }
   } else {
      var10000 = 0.0F;
   }

   return var10000;
}

public fun Bone.setRotation(axis: Int, angleInRadians: Float): Bone {
   if (`$this$setRotation` is ModelPart) {
      switch (axis) {
         case 0:
            (`$this$setRotation` as ModelPart).f_104203_ = angleInRadians;
            break;
         case 1:
            (`$this$setRotation` as ModelPart).f_104204_ = angleInRadians;
            break;
         default:
            (`$this$setRotation` as ModelPart).f_104205_ = angleInRadians;
      }
   }

   return `$this$setRotation`;
}

public fun ModelPart.setPosition(axis: Int, position: Float): ModelPart {
   switch (axis) {
      case 0:
         `$this$setPosition`.f_104200_ = position;
         break;
      case 1:
         `$this$setPosition`.f_104201_ = position;
         break;
      default:
         `$this$setPosition`.f_104202_ = position;
   }

   return `$this$setPosition`;
}

public fun Bone.addRotation(axis: Int, differenceInRadians: Float): Bone {
   return setRotation(`$this$addRotation`, axis, getRotation(`$this$addRotation`, axis) + differenceInRadians);
}

public fun ModelPart.addPosition(axis: Int, difference: Float): ModelPart {
   return setPosition(`$this$addPosition`, axis, getPosition(`$this$addPosition`, axis) + difference);
}

public fun ModelPart.getChildOf(vararg path: String): ModelPart {
   var part: ModelPart = `$this$getChildOf`;

   for (java.lang.String piece : path) {
      val var10000: ModelPart = part.m_171324_(piece);
      part = var10000;
   }

   return part;
}

public fun ModelPart.childNamed(vararg path: String): Pair<String, ModelPart> {
   return TuplesKt.to(ArraysKt.last(path) as java.lang.String, getChildOf(`$this$childNamed`, Arrays.copyOf(path, path.length)));
}
