package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client

import com.bedrockk.molang.runtime.MoLangEnvironment
import com.bedrockk.molang.runtime.MoLangRuntime
import com.bedrockk.molang.runtime.MoParams
import com.bedrockk.molang.runtime.value.StringValue
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.MoLangFunctions
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt
import java.util.HashMap
import java.util.function.Function
import net.minecraft.client.Minecraft
import net.minecraft.client.multiplayer.ClientLevel
import net.minecraft.client.player.LocalPlayer
import net.minecraft.client.resources.sounds.SimpleSoundInstance
import net.minecraft.client.resources.sounds.SoundInstance
import net.minecraft.network.chat.Component
import net.minecraft.sounds.SoundEvent

public object ClientMoLangFunctions {
   public final val clientFunctions: HashMap<String, Function<MoParams, Any>> =
      MapsKt.hashMapOf(
         new Pair[]{
            TuplesKt.to("sound", ClientMoLangFunctions::clientFunctions$lambda$0),
            TuplesKt.to("is_time", ClientMoLangFunctions::clientFunctions$lambda$1),
            TuplesKt.to("say", ClientMoLangFunctions::clientFunctions$lambda$2)
         }
      )

   public fun MoLangRuntime.setupClient(): MoLangRuntime {
      val var10000: MoLangFunctions = MoLangFunctions.INSTANCE;
      val var10001: MoLangFunctions = MoLangFunctions.INSTANCE;
      val var10002: MoLangEnvironment = `$this$setupClient`.getEnvironment();
      var10000.addFunctions(MoLangFunctions.getQueryStruct$default(var10001, var10002, null, 1, null), clientFunctions);
      return `$this$setupClient`;
   }

   @JvmStatic
   fun `clientFunctions$lambda$0`(params: MoParams): Unit {
      if (params.get(0) !is StringValue) {
         return Unit.INSTANCE;
      } else {
         val var10000: java.lang.String = params.getString(0);
         val soundEvent: SoundEvent = SoundEvent.m_262824_(ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(var10000, null, 1, null));
         if (soundEvent != null) {
            Minecraft.m_91087_()
               .m_91106_()
               .m_120367_(SimpleSoundInstance.m_119752_(soundEvent, if (params.contains(2)) (float)params.getDouble(2) else 1.0F) as SoundInstance);
         }

         return Unit.INSTANCE;
      }
   }

   @JvmStatic
   fun `clientFunctions$lambda$1`(params: MoParams): Any {
      val var10000: ClientLevel = Minecraft.m_91087_().f_91073_;
      val time: Long = (if (var10000 != null) var10000.m_46468_() else 0L) % 24000;
      return (long)params.getInt(0) <= time && time <= (long)params.getInt(1);
   }

   @JvmStatic
   fun `clientFunctions$lambda$2`(params: MoParams): Unit {
      val var10000: LocalPlayer = Minecraft.m_91087_().f_91074_;
      val var1: Unit;
      if (var10000 != null) {
         val var10001: java.lang.String = params.getString(0);
         var10000.m_213846_(TextKt.text(var10001) as Component);
         var1 = Unit.INSTANCE;
      } else {
         var1 = null;
      }

      if (var1 == null) {
      }

      return Unit.INSTANCE;
   }
}
