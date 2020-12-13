package me.rerere.fakesnow.listener;

import me.rerere.fakesnow.FakeSnow;
import me.rerere.fakesnow.manager.SnowController;
import me.rerere.fakesnow.util.Lang;
import me.rerere.fakesnow.util.VecUtil;
import me.rerere.fakesnow.util.VersionUtil;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EventListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        SnowController snowController = FakeSnow.getInstance().getSnowController();
        if(snowController.shouldForceSnow(event.getPlayer())){
            event.getPlayer().setPlayerWeather(WeatherType.DOWNFALL);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Location from = event.getFrom();
        Location to = event.getTo();
        SnowController snowController = FakeSnow.getInstance().getSnowController();
        boolean fromShould = snowController.shouldForceSnow(player, from);
        boolean toShould = snowController.shouldForceSnow(player, to);
        if(fromShould && !toShould){
            player.setPlayerWeather(WeatherType.CLEAR);
        }
        if(!fromShould && toShould){
            player.setPlayerWeather(WeatherType.DOWNFALL);
        }
    }

    @EventHandler
    public void onWorldChange(PlayerChangedWorldEvent event){
        SnowController snowController = FakeSnow.getInstance().getSnowController();
        if(snowController.shouldForceSnow(event.getPlayer())){
            event.getPlayer().setPlayerWeather(WeatherType.DOWNFALL);
        }
    }

    @EventHandler
    public void onInteractBlock(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(event.getClickedBlock() != null){
                if(VersionUtil.getVersion() > 9){
                    if(event.getHand() == EquipmentSlot.HAND && this.isSelectionTool(player.getInventory().getItemInMainHand())){
                        this.handleSelection(player, event.getClickedBlock(), event.getAction());
                        event.setCancelled(true);
                    }
                }else {
                    if(this.isSelectionTool(player.getItemInHand())){
                        this.handleSelection(player, event.getClickedBlock(), event.getAction());
                        event.setCancelled(true);
                    }
                }
            }
        }
    }

    private boolean isSelectionTool(ItemStack itemStack){
        if(itemStack.getType() != Material.STICK){
            return false;
        }
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null || !meta.getDisplayName().equalsIgnoreCase("§aSnow Region Selection Tool")){
            return false;
        }
        return true;
    }

    private void handleSelection(Player player, Block block, Action action){
        if(!player.hasPermission("fakesnow.use")){
            player.sendMessage("§cYou don't have permission to use the selection tool!");
            return;
        }
        if(player.getWorld().getEnvironment() != World.Environment.NORMAL){
            player.sendMessage("§cYou can't select a region in the nether/end world");
            return;
        }
        SnowController snowController = FakeSnow.getInstance().getSnowController();
        if(action == Action.LEFT_CLICK_BLOCK){
            snowController.setPointA(block.getLocation().toVector());
            snowController.setWorld(player.getWorld().getName());
            player.sendMessage(Lang.PREFIX + "§aSelected Point A: §b"+ VecUtil.asString(block.getLocation().toVector()));
        }
        if(action == Action.RIGHT_CLICK_BLOCK){
            snowController.setPointB(block.getLocation().toVector());
            snowController.setWorld(player.getWorld().getName());
            player.sendMessage(Lang.PREFIX + "§aSelected Point B: §b"+ VecUtil.asString(block.getLocation().toVector()));
        }
    }
}
