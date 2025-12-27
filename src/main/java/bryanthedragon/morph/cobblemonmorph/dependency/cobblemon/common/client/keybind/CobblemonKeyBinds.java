package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.Observable
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.DownShiftPartyBinding
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.HidePartyBinding
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.PartySendBinding
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.SummaryBinding
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.UpShiftPartyBinding
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.platform.events.PlatformEvents
import java.util.ArrayList;
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.KeyMapping

@SourceDebugExtension(["SMAP\nCobblemonKeyBinds.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonKeyBinds.kt\ncom/cobblemon/mod/common/client/keybind/CobblemonKeyBinds\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,62:1\n1855#2,2:63\n1855#2,2:65\n1855#2,2:67\n*S KotlinDebug\n*F\n+ 1 CobblemonKeyBinds.kt\ncom/cobblemon/mod/common/client/keybind/CobblemonKeyBinds\n*L\n49#1:63,2\n54#1:65,2\n34#1:67,2\n*E\n"])
public object CobblemonKeyBinds {
   public final val HIDE_PARTY: KeyMapping
   public final val PARTY_OVERLAY_DOWN: KeyMapping
   public final val PARTY_OVERLAY_UP: KeyMapping
   public final val SEND_OUT_POKEMON: KeyMapping
   public final val SUMMARY: KeyMapping
   private final val keyBinds: ArrayList<CobblemonKeyBinding> = new ArrayList()

   public fun register(registrar: (KeyMapping) -> Unit) {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         registrar.invoke(`element$iv` as KeyMapping);
      }
   }

   private fun onTick() {
      val `$this$forEach$iv`: java.lang.Iterable;
      for (Object element$iv : $this$forEach$iv) {
         (`element$iv` as CobblemonKeyBinding).onTick();
      }
   }

   private fun queue(keyBinding: CobblemonKeyBinding): KeyMapping {
      keyBinds.add(keyBinding);
      return keyBinding;
   }

   @JvmStatic
   fun {
      Observable.DefaultImpls.subscribe$default(PlatformEvents.CLIENT_TICK_POST, null, <unrepresentable>.INSTANCE, 1, null);
      if (Cobblemon.INSTANCE.getConfig().getEnableDebugKeys()) {
         val `$this$forEach$iv`: java.lang.Iterable;
         for (Object element$iv : $this$forEach$iv) {
            INSTANCE.queue(`element$iv` as CobblemonKeyBinding);
         }
      }

      HIDE_PARTY = INSTANCE.queue(HidePartyBinding.INSTANCE);
      SUMMARY = INSTANCE.queue(SummaryBinding.INSTANCE);
      PARTY_OVERLAY_DOWN = INSTANCE.queue(DownShiftPartyBinding.INSTANCE);
      PARTY_OVERLAY_UP = INSTANCE.queue(UpShiftPartyBinding.INSTANCE);
      SEND_OUT_POKEMON = INSTANCE.queue(PartySendBinding.INSTANCE);
   }
}
