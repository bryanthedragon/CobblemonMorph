package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public class BakingOverride {
   public final ResourceLocation modelLocation;
   public final ModelResourceLocation modelIdentifier;

   public BakingOverride(ResourceLocation modelLocation, BlockState modelIdentifier) {
      this.modelLocation = modelLocation;
      this.modelIdentifier = modelIdentifier;
   }

   @SuppressWarnings({ "deprecation", "null" })
   public BakedModel getModel() {
      return (BakedModel) Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getParticleIcon(this.modelIdentifier);
   }

   public ResourceLocation component1() {
      return this.modelLocation;
   }

   public ModelResourceLocation component2() {
      return this.modelIdentifier;
   }

   public BakingOverride copy(ResourceLocation modelLocation, ModelResourceLocation modelIdentifier) {
      return new BakingOverride(modelLocation, modelIdentifier);
   }

   @Override
   public String toString() {
      return "BakingOverride(modelLocation=" + this.modelLocation + ", modelIdentifier=" + this.modelIdentifier + ")";
   }

   @Override
   public int hashCode() {
      return this.modelLocation.hashCode() * 31 + this.modelIdentifier.hashCode();
   }

   @Override
   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } 
      else if (!(other instanceof BakingOverride)) {
         return false;
      } 
      else {
         BakingOverride var2 = (BakingOverride) other;
         if (!(this.modelLocation.equals(((BakingOverride) other).modelLocation))) {
            return false;
         } 
         else {
            return this.modelIdentifier.equals(var2.modelIdentifier);
         }
      }
   }
}
