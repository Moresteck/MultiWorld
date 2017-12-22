package pl.DaAmazingShit.MultiWorld.util;

import org.bukkit.Server;

public final class BukkitCore {
    private static Server server;
    
    private BukkitCore() {}
    
    public static Server a() {
        return server;
    }
    
    public static void a(Server server) {
        if (BukkitCore.server != null) {
            throw new UnsupportedOperationException("Cannot redefine singleton Server");
        }

        BukkitCore.server = server;
    }
}
