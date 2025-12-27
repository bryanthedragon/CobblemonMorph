package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import net.minecraft.resources.ResourceLocation

public open class AspectCriterionTrigger(identifier: ResourceLocation, criterionClass: Class<AspectCriterionCondition>) : SimpleCriterionTrigger(
      identifier, criterionClass
   )
