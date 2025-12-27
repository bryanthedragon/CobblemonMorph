package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.codec

import com.mojang.datafixers.kinds.App
import com.mojang.datafixers.kinds.Applicative
import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder.Instance
import org.joml.Vector3f

public final val VECTOR3F_CODEC: Codec<Vector3f>

fun `VECTOR3F_CODEC$lambda$4$lambda$0`(it: Vector3f): java.lang.Float {
   return it.x;
}

fun `VECTOR3F_CODEC$lambda$4$lambda$1`(it: Vector3f): java.lang.Float {
   return it.y;
}

fun `VECTOR3F_CODEC$lambda$4$lambda$2`(it: Vector3f): java.lang.Float {
   return it.z;
}

fun `VECTOR3F_CODEC$lambda$4$lambda$3`(x: java.lang.Float, y: java.lang.Float, z: java.lang.Float): Vector3f {
   val var10002: Float = x;
   val var10003: Float = y;
   return new Vector3f(var10002, var10003, z);
}

fun `VECTOR3F_CODEC$lambda$4`(instance: Instance): App {
   return instance.group(
         Codec.FLOAT.fieldOf("x").forGetter(VecCodecsKt::VECTOR3F_CODEC$lambda$4$lambda$0) as App,
         Codec.FLOAT.fieldOf("y").forGetter(VecCodecsKt::VECTOR3F_CODEC$lambda$4$lambda$1) as App,
         Codec.FLOAT.fieldOf("z").forGetter(VecCodecsKt::VECTOR3F_CODEC$lambda$4$lambda$2) as App
      )
      .apply(instance as Applicative, VecCodecsKt::VECTOR3F_CODEC$lambda$4$lambda$3);
}
