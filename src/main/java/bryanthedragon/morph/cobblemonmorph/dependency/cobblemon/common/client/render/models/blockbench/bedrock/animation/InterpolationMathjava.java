package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.bedrock.animation

import com.bedrockk.molang.runtime.MoLangRuntime
import java.util.ArrayList;
import net.minecraft.world.phys.Vec3
import org.joml.Vector3d

public fun catmullromLerp(
   frameA: BedrockAnimationKeyFrame?,
   frameB: BedrockAnimationKeyFrame,
   frameC: BedrockAnimationKeyFrame,
   frameD: BedrockAnimationKeyFrame?,
   time: Double,
   runtime: MoLangRuntime
): Vec3 {
   return new Vec3(
      catmullromLerp(frameA, frameB, frameC, frameD, 0, time, runtime),
      catmullromLerp(frameA, frameB, frameC, frameD, 1, time, runtime),
      catmullromLerp(frameA, frameB, frameC, frameD, 2, time, runtime)
   );
}

public fun linearLerpAlpha(before: Double, after: Double, value: Double): Double {
   return (value - before) / (after - before);
}

public fun catmullromLerp(
   frameA: BedrockAnimationKeyFrame?,
   frameB: BedrockAnimationKeyFrame,
   frameC: BedrockAnimationKeyFrame,
   frameD: BedrockAnimationKeyFrame?,
   axis: Int,
   time: Double,
   runtime: MoLangRuntime
): Double {
   var vectors: java.util.List;
   var var15: Vec3;
   label34: {
      vectors = new ArrayList();
      if (frameA != null) {
         val var10000: MolangBoneValue = frameA.getPost();
         if (var10000 != null) {
            var15 = var10000.resolve(time, runtime);
            break label34;
         }
      }

      var15 = null;
   }

   var frameBData: Vec3;
   var frameCData: Vec3;
   label29: {
      frameBData = frameB.getPost().resolve(time, runtime);
      frameCData = frameC.getPre().resolve(time, runtime);
      if (frameD != null) {
         val var16: MolangBoneValue = frameD.getPre();
         if (var16 != null) {
            var15 = var16.resolve(time, runtime);
            break label29;
         }
      }

      var15 = null;
   }

   if (var15 != null) {
      vectors.add(new Vector2d(frameA.getTime(), get(var15, axis)));
   }

   vectors.add(new Vector2d(frameB.getTime(), get(frameBData, axis)));
   vectors.add(new Vector2d(frameC.getTime(), get(frameCData, axis)));
   if (var15 != null) {
      vectors.add(new Vector2d(frameD.getTime(), get(var15, axis)));
   }

   return getPointOnSpline(
         vectors, (linearLerpAlpha(frameB.getTime(), frameC.getTime(), time) + (double)(if (frameA != null) 1 else 0)) / (double)(vectors.size() - 1)
      )
      .getB();
}

public fun Vec3.get(axis: Int): Double {
   var var10000: Double;
   switch (axis) {
      case 0:
         var10000 = `$this$get`.f_82479_;
         break;
      case 1:
         var10000 = `$this$get`.f_82480_;
         break;
      default:
         var10000 = `$this$get`.f_82481_;
   }

   return var10000;
}

private fun getPointOnSpline(points: List<Vector2d>, time: Double): Vector2d {
   val p: Double = (points.size() - 1) * time;
   val intPoint: Int = (int)Math.floor(p);
   val weight: Double = p - intPoint;
   val p0Index: Int = if (intPoint == 0) intPoint else intPoint - 1;
   val p2Index: Int = if (intPoint > points.size() - 2) points.size() - 1 else intPoint + 1;
   val p3Index: Int = if (intPoint > points.size() - 3) points.size() - 1 else intPoint + 2;
   val p0: Vector2d = points.get(p0Index) as Vector2d;
   val p1: Vector2d = points.get(intPoint) as Vector2d;
   val p2: Vector2d = points.get(p2Index) as Vector2d;
   val p3: Vector2d = points.get(p3Index) as Vector2d;
   return new Vector2d(catmullrom(weight, p0.getA(), p1.getA(), p2.getA(), p3.getA()), catmullrom(weight, p0.getB(), p1.getB(), p2.getB(), p3.getB()));
}

private fun catmullrom(t: Double, p0: Double, p1: Double, p2: Double, p3: Double): Double {
   return (2 * p1 - 2 * p2 + (p2 - p0) * 0.5 + (p3 - p1) * 0.5) * (t * (t * t))
      + (-3 * p1 + 3 * p2 - 2 * ((p2 - p0) * 0.5) - (p3 - p1) * 0.5) * (t * t)
      + (p2 - p0) * 0.5 * t
      + p1;
}

private fun Vector3d.get(axis: Int): Double {
   var var10000: Double;
   switch (axis) {
      case 0:
         var10000 = `$this$get`.x;
         break;
      case 1:
         var10000 = `$this$get`.y;
         break;
      case 2:
         var10000 = `$this$get`.z;
         break;
      default:
         throw new IllegalStateException();
   }

   return var10000;
}
