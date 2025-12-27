package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.util

import kotlin.jvm.functions.Function0
import net.minecraft.client.Minecraft
import net.minecraft.resources.ResourceLocation

public fun ResourceLocation.exists(): Boolean {
   return Minecraft.m_91087_().m_91098_().m_213713_(`$this$exists`).isPresent();
}

public fun runOnRender(action: () -> Unit) {
   Minecraft.m_91087_().execute(ClientDistributionUtilsKt::runOnRender$lambda$0);
}

fun `runOnRender$lambda$0`(`$tmp0`: Function0) {
   `$tmp0`.invoke();
}
