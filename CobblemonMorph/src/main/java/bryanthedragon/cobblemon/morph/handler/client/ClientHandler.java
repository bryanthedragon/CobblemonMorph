package bryanthedragon.morph.cobblemonmorph.handler.client;

import bryanthedragon.morph.cobblemonmorph.handler.Handler;
import bryanthedragon.morph.cobblemonmorph.handler.client.shutdown.ClientShutdownHandler;

public class ClientHandler extends Handler {
    ClientShutdownHandler ShutdownHandlerInstance = new ClientShutdownHandler();

    public void init() {
        System.out.println("Initializing client handler");
    }

    public ClientShutdownHandler getShutdownHandler() {
        return ShutdownHandlerInstance;
    }
}
