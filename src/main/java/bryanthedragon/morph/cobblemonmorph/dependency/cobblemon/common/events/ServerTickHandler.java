package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.events

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.SpawnerManager
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleRegistry
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.PlayerExtensionsKt
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.server.MinecraftServer
import net.minecraft.server.level.ServerPlayer

@SourceDebugExtension(["SMAP\nServerTickHandler.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ServerTickHandler.kt\ncom/cobblemon/mod/common/events/ServerTickHandler\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,34:1\n1855#2,2:35\n*S KotlinDebug\n*F\n+ 1 ServerTickHandler.kt\ncom/cobblemon/mod/common/events/ServerTickHandler\n*L\n20#1:35,2\n*E\n"])
public object ServerTickHandler {
   private final var secondsTick: Int

   public fun onTick(server: MinecraftServer) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as SpawnerManager).onServerTick();
      }

      BattleRegistry.INSTANCE.tick();
      val var8: Int = secondsTick++;
      if (secondsTick == 20) {
         secondsTick = 0;

         for (ServerPlayer player : server.m_6846_().m_11314_()) {
            PlayerExtensionsKt.party(var10).onSecondPassed(var10);
         }
      }
   }
}
