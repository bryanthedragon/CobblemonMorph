package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.syncher.EntityDataSerializer
import net.minecraft.world.phys.Vec3

public object Vec3DataSerializer : EntityDataSerializer<Vec3> {
   public open fun write(buffer: FriendlyByteBuf, vec: Vec3) {
      buffer.writeDouble(vec.f_82479_);
      buffer.writeDouble(vec.f_82480_);
      buffer.writeDouble(vec.f_82481_);
   }

   public open fun read(buffer: FriendlyByteBuf): Vec3 {
      return new Vec3(buffer.readDouble(), buffer.readDouble(), buffer.readDouble());
   }

   public open fun copy(vec: Vec3): Vec3 {
      return new Vec3(vec.f_82479_, vec.f_82480_, vec.f_82481_);
   }
}
