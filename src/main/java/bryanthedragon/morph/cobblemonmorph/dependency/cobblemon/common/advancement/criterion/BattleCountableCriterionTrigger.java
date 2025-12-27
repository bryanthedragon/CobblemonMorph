package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.advancement.criterion

import net.minecraft.resources.ResourceLocation

public open class BattleCountableCriterionTrigger(identifier: ResourceLocation, criterionClass: Class<BattleCountableCriterionCondition>) : SimpleCriterionTrigger(
      identifier, criterionClass
   )
