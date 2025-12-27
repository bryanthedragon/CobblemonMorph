package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.PoseType
import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.syncher.EntityDataSerializer

public object PoseTypeDataSerializer : EntityDataSerializer<PoseType> {
   public open fun read(buf: FriendlyByteBuf): PoseType {
      return PoseType.values()[buf.readInt()];
   }

   public open fun copy(value: PoseType): PoseType {
      return value;
   }

   public open fun write(buf: FriendlyByteBuf, value: PoseType) {
      buf.writeInt(value.ordinal());
   }
}
