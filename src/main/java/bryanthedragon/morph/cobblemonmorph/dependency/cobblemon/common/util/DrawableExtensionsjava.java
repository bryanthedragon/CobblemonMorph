package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util

import net.minecraft.client.Minecraft
import net.minecraft.client.gui.components.Renderable

public fun Renderable.scaleIt(value: Number): Int {
   return (int)(Minecraft.m_91087_().m_91268_().m_85449_() * value.floatValue());
}
