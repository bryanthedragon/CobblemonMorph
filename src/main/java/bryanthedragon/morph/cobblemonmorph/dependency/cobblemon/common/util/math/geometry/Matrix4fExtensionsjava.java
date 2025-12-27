package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry

import net.minecraft.world.phys.Vec3
import org.joml.Matrix4f
import org.joml.Vector4f

public fun Matrix4f.getOrigin(): Vec3 {
   val var10001: Vec3 = Vec3.f_82478_;
   return transformPosition(`$this$getOrigin`, var10001);
}

public fun Matrix4f.transformPosition(pos: Vec3): Vec3 {
   val vector: Vector4f = new Vector4f((float)pos.f_82479_, (float)pos.f_82480_, (float)pos.f_82481_, 1.0F);
   `$this$transformPosition`.transform(vector);
   vector.mul((float)1 / vector.w);
   return new Vec3(vector.x, vector.y, vector.z);
}

public fun Matrix4f.transformDirection(direction: Vec3): Vec3 {
   val origin: Vector4f = new Vector4f(0.0F, 0.0F, 0.0F, 1.0F);
   `$this$transformDirection`.transform(origin);
   origin.mul((float)1 / origin.w);
   val originVec: Vec3 = new Vec3(origin.x, origin.y, origin.z);
   val magnitude: Double = direction.m_82553_();
   val vector: Vector4f = new Vector4f((float)direction.f_82479_, (float)direction.f_82480_, (float)direction.f_82481_, 1.0F);
   `$this$transformDirection`.transform(vector);
   vector.mul((float)1 / vector.w);
   val var10000: Vec3 = new Vec3((double)vector.x, (double)vector.y, (double)vector.z).m_82546_(originVec).m_82541_().m_82490_(magnitude);
   return var10000;
}
