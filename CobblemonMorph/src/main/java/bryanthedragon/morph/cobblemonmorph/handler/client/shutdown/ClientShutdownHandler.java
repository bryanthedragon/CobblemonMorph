package bryanthedragon.morph.cobblemonmorph.handler.client.shutdown;

import bryanthedragon.morph.cobblemonmorph.handler.client.ClientHandler;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ClientShutdownHandler extends ClientHandler {

    private static boolean wasRunning = true;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!net.minecraft.client.Minecraft.getInstance().isRunning() && wasRunning) {
                wasRunning = false;
                System.out.println("Client shutting down");
            }
        }
    }
}