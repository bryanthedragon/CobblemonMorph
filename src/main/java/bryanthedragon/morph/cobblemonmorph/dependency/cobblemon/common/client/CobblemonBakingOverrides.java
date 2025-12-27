package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.BakingOverride
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt
import java.util.ArrayList;
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.resources.ResourceLocation

public object CobblemonBakingOverrides {
   public final val RESTORATION_TANK_CONNECTOR: BakingOverride =
      INSTANCE.registerOverride(
         MiscUtilsKt.cobblemonResource("block/restoration_tank_connector"), MiscUtilsKt.cobblemonModel("restoration_tank_connector", "none")
      )
      public final val RESTORATION_TANK_FLUID_BUBBLING: BakingOverride =
      INSTANCE.registerOverride(
         MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_bubbling"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_bubbling", "none")
      )
      public final val RESTORATION_TANK_FLUID_CHUNKED_1: BakingOverride =
      INSTANCE.registerOverride(
         MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_1"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "1")
      )
      public final val RESTORATION_TANK_FLUID_CHUNKED_2: BakingOverride =
      INSTANCE.registerOverride(
         MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_2"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "2")
      )
      public final val RESTORATION_TANK_FLUID_CHUNKED_3: BakingOverride =
      INSTANCE.registerOverride(
         MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_3"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "3")
      )
      public final val RESTORATION_TANK_FLUID_CHUNKED_4: BakingOverride =
      INSTANCE.registerOverride(
         MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_4"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "4")
      )
      public final val RESTORATION_TANK_FLUID_CHUNKED_5: BakingOverride =
      INSTANCE.registerOverride(
         MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_5"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "5")
      )
      public final val RESTORATION_TANK_FLUID_CHUNKED_6: BakingOverride =
      INSTANCE.registerOverride(
         MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_6"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "6")
      )
      public final val RESTORATION_TANK_FLUID_CHUNKED_7: BakingOverride =
      INSTANCE.registerOverride(
         MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_7"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "7")
      )
      public final val RESTORATION_TANK_FLUID_CHUNKED_8: BakingOverride =
      INSTANCE.registerOverride(
         MiscUtilsKt.cobblemonResource("block/restoration_tank_fluid_chunked_8"), MiscUtilsKt.cobblemonModel("restoration_tank_fluid_chunked", "8")
      )
      public final val models: MutableList<BakingOverride> = (new ArrayList()) as java.util.List

   public fun registerOverride(modelLocation: ResourceLocation, modelIdentifier: ModelResourceLocation): BakingOverride {
      val result: BakingOverride = new BakingOverride(modelLocation, modelIdentifier);
      models.add(result);
      return result;
   }
}
