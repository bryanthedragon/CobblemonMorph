package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import com.mojang.blaze3d.vertex.VertexFormat
import net.minecraft.resources.ResourceLocation
import net.minecraft.server.packs.resources.ResourceProvider

public data ShaderRegistryData(resourceFactory: ResourceProvider, shaderName: ResourceLocation, vertexFormat: VertexFormat) {
   public final val resourceFactory: ResourceProvider
   public final val shaderName: ResourceLocation
   public final val vertexFormat: VertexFormat

   init {
      this.resourceFactory = resourceFactory;
      this.shaderName = shaderName;
      this.vertexFormat = vertexFormat;
   }

   public operator fun component1(): ResourceProvider {
      return this.resourceFactory;
   }

   public operator fun component2(): ResourceLocation {
      return this.shaderName;
   }

   public operator fun component3(): VertexFormat {
      return this.vertexFormat;
   }

   public fun copy(
      resourceFactory: ResourceProvider = this.resourceFactory,
      shaderName: ResourceLocation = this.shaderName,
      vertexFormat: VertexFormat = this.vertexFormat
   ): ShaderRegistryData {
      return new ShaderRegistryData(resourceFactory, shaderName, vertexFormat);
   }

   public override fun toString(): String {
      return "ShaderRegistryData(resourceFactory=${this.resourceFactory}, shaderName=${this.shaderName}, vertexFormat=${this.vertexFormat})";
   }

   public override fun hashCode(): Int {
      return (this.resourceFactory.hashCode() * 31 + this.shaderName.hashCode()) * 31 + this.vertexFormat.hashCode();
   }

   public override operator fun equals(other: Any?): Boolean {
      if (this === other) {
         return true;
      } else if (other !is ShaderRegistryData) {
         return false;
      } else {
         val var2: ShaderRegistryData = other as ShaderRegistryData;
         if (!(this.resourceFactory == (other as ShaderRegistryData).resourceFactory)) {
            return false;
         } else if (!(this.shaderName == var2.shaderName)) {
            return false;
         } else {
            return this.vertexFormat == var2.vertexFormat;
         }
      }
   }
}
