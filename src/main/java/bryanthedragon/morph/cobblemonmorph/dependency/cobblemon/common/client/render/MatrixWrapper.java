package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.math.geometry.Matrix4fExtensionsKt
import net.minecraft.world.phys.Vec3
import org.joml.Matrix4f
import org.joml.Matrix4fc

public class MatrixWrapper {
   public final var matrix: Matrix4f
   public final var position: Vec3

   public fun updateMatrix(rotationMatrix: Matrix4f): MatrixWrapper {
      this.matrix = new Matrix4f(rotationMatrix as Matrix4fc);
      return this;
   }

   public fun updatePosition(position: Vec3): MatrixWrapper {
      this.position = position;
      return this;
   }

   public fun getOrigin(): Vec3 {
      return this.position.m_82549_(Matrix4fExtensionsKt.getOrigin(this.matrix));
   }

   public fun transformPosition(position: Vec3): Vec3 {
      return this.position.m_82549_(Matrix4fExtensionsKt.transformPosition(this.matrix, position));
   }
}
