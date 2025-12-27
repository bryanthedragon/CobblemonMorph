package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.keybinds

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.CobblemonClient
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.keybind.CobblemonKeyBinding
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.pokemon.PokemonPoseableModel
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository.PokemonModelRepository
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Pokemon;
import com.mojang.blaze3d.platform.InputConstants.Type
import net.minecraft.client.Minecraft
import net.minecraft.client.player.LocalPlayer
import net.minecraft.network.chat.Component
import net.minecraft.world.phys.Vec3

public object DebugKeybindings {
   public final val keybindings: List<CobblemonKeyBinding> =
      CollectionsKt.listOf(
         new CobblemonKeyBinding[]{
            new DebugKeybindings.ScaleUpKeybinding(),
            new DebugKeybindings.ScaleDownKeybinding(),
            new DebugKeybindings.TranslateLeftKeybinding(),
            new DebugKeybindings.TranslateRightKeybinding(),
            new DebugKeybindings.TranslateUpKeybinding(),
            new DebugKeybindings.TranslateDownKeybinding(),
            new DebugKeybindings.PrintModelSettingsKeybinding()
         }
      )

   public class PrintModelSettingsKeybinding : CobblemonKeyBinding(
         "key.cobblemon.printmodelsettings", Type.KEYSYM, 46, "key.cobblemon.categories.cobblemon.debug"
      ) {
      public override fun onPress() {
         val currentlySelectedPokemon: Pokemon = CobblemonClient.INSTANCE
            .getStorage()
            .getMyParty()
            .get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
         if (currentlySelectedPokemon != null) {
            val model: PokemonPoseableModel = PokemonModelRepository.INSTANCE
               .getPoser(currentlySelectedPokemon.getSpecies().getResourceIdentifier(), currentlySelectedPokemon.getAspects());
            var var10000: LocalPlayer = Minecraft.m_91087_().f_91074_;
            if (var10000 != null) {
               var10000.m_213846_(Component.m_130674_("Portrait Translation: ${model.getPortraitTranslation()}"));
            }

            var10000 = Minecraft.m_91087_().f_91074_;
            if (var10000 != null) {
               var10000.m_213846_(Component.m_130674_("Portrait Scale: ${model.getPortraitScale()}"));
            }

            Cobblemon.INSTANCE
               .getLOGGER()
               .info(
                  "override var portraitTranslation = Vec3d(${model.getPortraitTranslation().f_82479_}, ${model.getPortraitTranslation().f_82480_}, ${model.getPortraitTranslation()
                     .f_82481_})"
               );
            Cobblemon.INSTANCE.getLOGGER().info("override var portraitScale = ${model.getPortraitScale()}F");
         }
      }
   }

   public class ScaleDownKeybinding : CobblemonKeyBinding("key.cobblemon.scaleportraitdown", Type.KEYSYM, 45, "key.cobblemon.categories.cobblemon.debug") {
      public override fun onPress() {
         val currentlySelectedPokemon: Pokemon = CobblemonClient.INSTANCE
            .getStorage()
            .getMyParty()
            .get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
         if (currentlySelectedPokemon != null) {
            val model: PokemonPoseableModel = PokemonModelRepository.INSTANCE
               .getPoser(currentlySelectedPokemon.getSpecies().getResourceIdentifier(), currentlySelectedPokemon.getAspects());
            model.setPortraitScale(model.getPortraitScale() - 0.01F);
         }
      }
   }

   public class ScaleUpKeybinding : CobblemonKeyBinding("key.cobblemon.scaleportraitup", Type.KEYSYM, 61, "key.cobblemon.categories.cobblemon.debug") {
      public override fun onPress() {
         val currentlySelectedPokemon: Pokemon = CobblemonClient.INSTANCE
            .getStorage()
            .getMyParty()
            .get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
         if (currentlySelectedPokemon != null) {
            val model: PokemonPoseableModel = PokemonModelRepository.INSTANCE
               .getPoser(currentlySelectedPokemon.getSpecies().getResourceIdentifier(), currentlySelectedPokemon.getAspects());
            model.setPortraitScale(model.getPortraitScale() + 0.01F);
         }
      }
   }

   public class TranslateDownKeybinding : CobblemonKeyBinding(
         "key.cobblemon.translateportraitdown", Type.KEYSYM, 75, "key.cobblemon.categories.cobblemon.debug"
      ) {
      public override fun onPress() {
         val currentlySelectedPokemon: Pokemon = CobblemonClient.INSTANCE
            .getStorage()
            .getMyParty()
            .get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
         if (currentlySelectedPokemon != null) {
            val model: PokemonPoseableModel = PokemonModelRepository.INSTANCE
               .getPoser(currentlySelectedPokemon.getSpecies().getResourceIdentifier(), currentlySelectedPokemon.getAspects());
            val var10001: Vec3 = model.getPortraitTranslation().m_82520_(0.0, 0.01, 0.0);
            model.setPortraitTranslation(var10001);
         }
      }
   }

   public class TranslateLeftKeybinding : CobblemonKeyBinding(
         "key.cobblemon.translateportraitleft", Type.KEYSYM, 74, "key.cobblemon.categories.cobblemon.debug"
      ) {
      public override fun onPress() {
         val currentlySelectedPokemon: Pokemon = CobblemonClient.INSTANCE
            .getStorage()
            .getMyParty()
            .get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
         if (currentlySelectedPokemon != null) {
            val model: PokemonPoseableModel = PokemonModelRepository.INSTANCE
               .getPoser(currentlySelectedPokemon.getSpecies().getResourceIdentifier(), currentlySelectedPokemon.getAspects());
            val var10001: Vec3 = model.getPortraitTranslation().m_82520_(-0.01, 0.0, 0.0);
            model.setPortraitTranslation(var10001);
         }
      }
   }

   public class TranslateRightKeybinding : CobblemonKeyBinding(
         "key.cobblemon.translateportraitright", Type.KEYSYM, 76, "key.cobblemon.categories.cobblemon.debug"
      ) {
      public override fun onPress() {
         val currentlySelectedPokemon: Pokemon = CobblemonClient.INSTANCE
            .getStorage()
            .getMyParty()
            .get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
         if (currentlySelectedPokemon != null) {
            val model: PokemonPoseableModel = PokemonModelRepository.INSTANCE
               .getPoser(currentlySelectedPokemon.getSpecies().getResourceIdentifier(), currentlySelectedPokemon.getAspects());
            val var10001: Vec3 = model.getPortraitTranslation().m_82520_(0.01, 0.0, 0.0);
            model.setPortraitTranslation(var10001);
         }
      }
   }

   public class TranslateUpKeybinding : CobblemonKeyBinding("key.cobblemon.translateportraitup", Type.KEYSYM, 73, "key.cobblemon.categories.cobblemon.debug") {
      public override fun onPress() {
         val currentlySelectedPokemon: Pokemon = CobblemonClient.INSTANCE
            .getStorage()
            .getMyParty()
            .get(CobblemonClient.INSTANCE.getStorage().getSelectedSlot());
         if (currentlySelectedPokemon != null) {
            val model: PokemonPoseableModel = PokemonModelRepository.INSTANCE
               .getPoser(currentlySelectedPokemon.getSpecies().getResourceIdentifier(), currentlySelectedPokemon.getAspects());
            val var10001: Vec3 = model.getPortraitTranslation().m_82520_(0.0, -0.01, 0.0);
            model.setPortraitTranslation(var10001);
         }
      }
   }
}
