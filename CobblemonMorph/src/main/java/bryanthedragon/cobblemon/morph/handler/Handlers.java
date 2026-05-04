package bryanthedragon.cobblemon.morph.handler;

import java.util.Arrays;

import bryanthedragon.cobblemon.morph.handler.client.ClientHandler;
import bryanthedragon.cobblemon.morph.handler.utils.UtilHandler;

public class Handlers {

    private final ClientHandler clientHandler = new ClientHandler();
    private final UtilHandler utilHandler = new UtilHandler();

    public enum HandlerType {
        CLIENT,
        UTIL
    }

    public String[] printHandlers() {
        return Arrays.stream(HandlerType.values()).map(HandlerType::name).toArray(String[]::new);
    }

    public ClientHandler getClientHandler() {
        return clientHandler;
    }

    public UtilHandler getUtilHandler() {
        return utilHandler;
    }
}