package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.sound.battle

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.SoundExtensionsKt
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.client.sounds.SoundManager
import net.minecraft.sounds.SoundEvent
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource

@SourceDebugExtension(["SMAP\nBattleMusicController.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleMusicController.kt\ncom/cobblemon/mod/common/client/sound/battle/BattleMusicController\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,61:1\n1855#2,2:62\n*S KotlinDebug\n*F\n+ 1 BattleMusicController.kt\ncom/cobblemon/mod/common/client/sound/battle/BattleMusicController\n*L\n45#1:62,2\n*E\n"])
public object BattleMusicController {
   public final val filteredCategories: List<SoundSource> =
      CollectionsKt.listOf(new SoundSource[]{SoundSource.AMBIENT, SoundSource.MUSIC, SoundSource.RECORDS})
      private final val manager: SoundManager = Minecraft.m_91087_().m_91106_()

   public final var music: BattleMusicInstance
      private set

   public fun initializeMusic(newMusic: BattleMusicInstance) {
      music = newMusic;
      manager.m_120367_(music as SoundInstance);
      if (manager.m_120403_(music as SoundInstance)) {
         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$iv : $this$forEach$iv) {
            val it: SoundSource = `element$iv` as SoundSource;
            val var10000: SoundManager = manager;
            SoundExtensionsKt.pauseSounds(var10000, null, it);
         }

         val var8: SoundManager = manager;
         SoundExtensionsKt.resumeSounds(var8, music.m_7904_(), SoundSource.MUSIC);
      }
   }

   public fun switchMusic(newMusic: BattleMusicInstance) {
      manager.m_120399_(music as SoundInstance);
      music = newMusic;
      manager.m_120367_(music as SoundInstance);
   }

   public fun endMusic() {
      music.setFade();
   }

   @JvmStatic
   fun {
      val var10002: SoundEvent = SoundEvents.f_271165_;
      music = new BattleMusicInstance(var10002, 0.0F, 0.0F);
   }
}
