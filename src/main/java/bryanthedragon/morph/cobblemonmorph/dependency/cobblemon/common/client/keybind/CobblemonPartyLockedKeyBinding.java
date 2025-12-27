package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.text.TextKt
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds.SummaryBinding
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.messages.server.starter.RequestStarterScreenPacket
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.LocalizationUtilsKt
import com.mojang.blaze3d.platform.InputConstants.Type
import kotlin.jvm.internal.SourceDebugExtension
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent

@SourceDebugExtension(["SMAP\nCobblemonPartyLockedKeyBinding.kt\nKotlin\n*S Kotlin\n*F\n+ 1 CobblemonPartyLockedKeyBinding.kt\ncom/cobblemon/mod/common/client/keybind/CobblemonPartyLockedKeyBinding\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,71:1\n1747#2,3:72\n*S KotlinDebug\n*F\n+ 1 CobblemonPartyLockedKeyBinding.kt\ncom/cobblemon/mod/common/client/keybind/CobblemonPartyLockedKeyBinding\n*L\n45#1:72,3\n*E\n"])
public abstract class CobblemonPartyLockedKeyBinding : CobblemonKeyBinding {
   private final var skippedStarterSelectionMessageShown: Boolean

   open fun CobblemonPartyLockedKeyBinding(name: java.lang.String, type: Type, key: Int, category: java.lang.String) {
      super(name, type, key, category);
   }

   public override fun onTick() {
      if (this.m_90859_() && this.hasPartyMembers()) {
         this.onPress();
      }
   }

   private fun hasPartyMembers(): Boolean {
      val starterSelected: java.lang.Iterable = CobblemonClient.INSTANCE.getStorage().getMyParty().getSlots();
      var var10000: Boolean;
      if (starterSelected is java.util.Collection && (starterSelected as java.util.Collection).isEmpty()) {
         var10000 = false;
      } else {
         label66: {
            for (Object element$iv : $this$any$iv) {
               if (`element$iv` as Pokemon != null) {
                  var10000 = true;
                  break label66;
               }
            }

            var10000 = false;
         }
      }

      val var8: Boolean = CobblemonClient.INSTANCE.getClientPlayerData().getStarterSelected();
      val var9: Boolean = CobblemonClient.INSTANCE.getClientPlayerData().getStarterLocked();
      if (!var8 && !var10000) {
         if (var9) {
            val var13: LocalPlayer = Minecraft.m_91087_().f_91074_;
            if (var13 != null) {
               val var10001: MutableComponent = LocalizationUtilsKt.lang("ui.starter.cannotchoose");
               var13.m_5661_(TextKt.red(var10001) as Component, false);
            }
         } else {
            new RequestStarterScreenPacket().sendToServer();
         }

         return false;
      } else if (!var9 && !var8 && var10000) {
         if (!this.skippedStarterSelectionMessageShown) {
            val var12: LocalPlayer = Minecraft.m_91087_().f_91074_;
            if (var12 != null) {
               val var11: Array<Any> = new Object[1];
               val var10004: Component = CurrentKeyAccessorKt.boundKey(SummaryBinding.INSTANCE).m_84875_();
               var11[0] = var10004;
               val var10: MutableComponent = LocalizationUtilsKt.lang("ui.starter.skippedchoosing", var11);
               var12.m_5661_(TextKt.yellow(var10) as Component, false);
            }

            this.skippedStarterSelectionMessageShown = true;
         }

         return true;
      } else {
         return true;
      }
   }
}
