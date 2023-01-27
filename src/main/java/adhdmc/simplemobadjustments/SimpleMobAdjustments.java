package adhdmc.simplemobadjustments;

import adhdmc.simplemobadjustments.listener.enderman.EndermanShearing;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleMobAdjustments extends JavaPlugin {

    private static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        // TODO: Dynamic registering and unregistering of events on plugin reload.
        Bukkit.getPluginManager().registerEvents(new EndermanShearing(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Plugin getPlugin() { return plugin; }
}
