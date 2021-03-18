package MinecraftDSL

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.GameMode
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.event.world.PortalCreateEvent
import org.bukkit.event.weather.WeatherChangeEvent
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object DSLListener : Listener {

    @EventHandler
    public fun onJoin(event: PlayerJoinEvent) {
        event.setJoinMessage(MinecraftDSLPlugin.instance!!.joinMessage.replace("@user", event.player.name))

        MinecraftDSLPlugin.instance!!.joinItems.forEach {
            event.player.inventory.addItem(ItemStack(findItem(it), 1))
        }
    }

    @EventHandler
    public fun onLeave(event: PlayerQuitEvent) {
        event.setQuitMessage(MinecraftDSLPlugin.instance!!.joinMessage.replace("@user", event.player.name))
    }

    fun findItem(name : String) : Material {
        return Material.valueOf(name.toUpperCase())
    }

}