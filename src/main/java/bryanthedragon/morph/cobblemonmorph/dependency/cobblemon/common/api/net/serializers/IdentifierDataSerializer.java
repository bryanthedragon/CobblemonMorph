package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers

import net.minecraft.network.FriendlyByteBuf
import net.minecraft.network.syncher.EntityDataSerializer
import net.minecraft.resources.ResourceLocation

public object IdentifierDataSerializer : EntityDataSerializer<ResourceLocation> {
   public open fun copy(value: ResourceLocation): ResourceLocation {
      return new ResourceLocation(value.m_135827_(), value.m_135815_());
   }

   public open fun read(buf: FriendlyByteBuf): ResourceLocation {
      return new ResourceLocation(buf.m_130277_(), buf.m_130277_());
   }

   public open fun write(buf: FriendlyByteBuf, value: ResourceLocation) {
      buf.m_130070_(value.m_135827_());
      buf.m_130070_(value.m_135815_());
   }
}
