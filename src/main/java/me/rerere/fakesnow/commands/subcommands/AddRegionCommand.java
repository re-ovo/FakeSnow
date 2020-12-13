package me.rerere.fakesnow.commands.subcommands;

import me.rerere.fakesnow.FakeSnow;
import me.rerere.fakesnow.commands.SubCommand;
import me.rerere.fakesnow.manager.Arena;
import me.rerere.fakesnow.manager.SnowController;
import me.rerere.fakesnow.util.Lang;
import org.bukkit.command.CommandSender;

public class AddRegionCommand extends SubCommand {
    public AddRegionCommand() {
        super("addregion", "<Name>", "Add a snow region");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        SnowController snowController = FakeSnow.getInstance().getSnowController();
        if(args.length == 0){
            sender.sendMessage(Lang.PREFIX + "§cPlease input the region name!");
            return;
        }
        if(snowController.getPointA() == null || snowController.getPointB() == null || snowController.getWorld() == null){
            sender.sendMessage(Lang.PREFIX + "§cYou haven't chosen a snowy area");
            return;
        }
        if(snowController.getArenas().stream().filter(Arena::isRegion).anyMatch(arena -> arena.getName().equalsIgnoreCase(args[0]))){
            sender.sendMessage(Lang.PREFIX + "§cAlready exists this arena: "+args[0]);
            return;
        }
        if(snowController.isInSnowWorld(snowController.getWorld())){
            sender.sendMessage(Lang.PREFIX + "§cThe world is already a snowy world, why you want to add a snowy region in it?");
            return;
        }
        Arena arena = new Arena(args[0], snowController.getWorld(), snowController.getPointA(), snowController.getPointB());
        snowController.getArenas().add(arena);
        snowController.save();
        sender.sendMessage(Lang.PREFIX +"§aAdded snowy region: "+args[0]);
        snowController.setPointA(null);
        snowController.setPointB(null);
        snowController.setWorld(null);
    }
}
