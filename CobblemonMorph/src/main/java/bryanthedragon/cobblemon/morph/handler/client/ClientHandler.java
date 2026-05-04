package bryanthedragon.cobblemon.morph.handler.client;

import bryanthedragon.cobblemon.morph.handler.Handlers;

public class ClientHandler extends Handlers {

    private final HandlerType type = HandlerType.CLIENT;

    protected enum ClientType {
        SHUTDOWN
    }

    protected HandlerType getType() {
        return type;
    }

    protected void printType() {
        System.err.println(this.type);
    }
}
