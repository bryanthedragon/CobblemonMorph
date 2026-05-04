package bryanthedragon.cobblemon.morph.helper.registries.mod;

import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import com.cobblemon.mod.common.api.pokemon.PokemonSpecies;
import com.cobblemon.mod.common.pokemon.Species;

import bryanthedragon.cobblemon.morph.helper.registries.CobblemonMorphModRegistry;

public final class CobblemonMorphCobblemonRegistry extends CobblemonMorphModRegistry {
    
    @SuppressWarnings("null")
    @SubscribeEvent
    public static void onCommandRegistration(final RegisterCommandsEvent event) {
        event.getDispatcher().register(Commands.literal("test").executes(context -> {Species species = PokemonSpecies.INSTANCE.getByIdentifier(ResourceLocation.of("cobblemon", ':')); context.getSource().sendSystemMessage(Component.literal("Got species: ").withStyle(Style.EMPTY.withColor(0x03e3fc)).append(species.getTranslatedName())); return 0;}));
    }
}