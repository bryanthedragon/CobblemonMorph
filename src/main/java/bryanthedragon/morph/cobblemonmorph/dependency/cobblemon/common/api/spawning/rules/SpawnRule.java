package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.rules.component.SpawnRuleComponent
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import java.util.ArrayList;
import net.minecraft.network.chat.Component
import net.minecraft.resources.ResourceLocation

public class SpawnRule {
   public final val components: MutableList<SpawnRuleComponent> = (new ArrayList()) as java.util.List
   public final val displayName: Component = TextKt.text("Spawn Rule") as Component
   public final var enabled: Boolean = true
   public final lateinit var id: ResourceLocation
}
