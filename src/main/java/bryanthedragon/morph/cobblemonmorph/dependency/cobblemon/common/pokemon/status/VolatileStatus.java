package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.status.Status
import net.minecraft.resources.ResourceLocation

public open class VolatileStatus(name: ResourceLocation, showdownName: String, applyMessage: String, removeMessage: String) : Status(
      name, showdownName, applyMessage, removeMessage
   )
