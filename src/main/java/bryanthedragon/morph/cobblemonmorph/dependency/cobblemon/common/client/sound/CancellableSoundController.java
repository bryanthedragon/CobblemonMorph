package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound

import net.minecraft.client.Minecraft
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.client.sounds.SoundManager
import net.minecraft.core.BlockPos
import net.minecraft.resources.ResourceLocation

public object CancellableSoundController {
   private final val manager: SoundManager = Minecraft.m_91087_().m_91106_()
   private final val playingSounds: MutableMap<BlockPos, MutableMap<ResourceLocation, SoundInstance>> = MapsKt.toMutableMap(MapsKt.emptyMap())

   public fun playSound(newSound: CancellableSoundInstance) {
      manager.m_120367_(newSound as SoundInstance);
      var idMap: java.util.Map = playingSounds.get(newSound.getPos());
      var soundInstance: SoundInstance = null;
      if (idMap == null) {
         idMap = MapsKt.toMutableMap(MapsKt.emptyMap());
      } else {
         soundInstance = idMap.get(newSound.m_7904_()) as SoundInstance;
      }

      if (soundInstance != null) {
         manager.m_120399_(soundInstance);
      }

      val var10000: ResourceLocation = newSound.m_7904_();
      idMap.put(var10000, newSound);
      playingSounds.put(newSound.getPos(), idMap);
   }

   public fun stopSound(soundInstance: CancellableSoundInstance) {
      val var10001: BlockPos = soundInstance.getPos();
      val var10002: ResourceLocation = soundInstance.m_5891_().m_119787_();
      this.stopSound(var10001, var10002);
   }

   public fun stopSound(blockPos: BlockPos, identifier: ResourceLocation) {
      val idMap: java.util.Map = playingSounds.get(blockPos);
      if (idMap != null) {
         val soundInstance: SoundInstance = idMap.get(identifier) as SoundInstance;
         if (soundInstance != null) {
            manager.m_120399_(soundInstance);
            idMap.remove(identifier);
            if (idMap.keySet().size() == 0) {
               playingSounds.remove(blockPos);
            }
         }
      }
   }
}
