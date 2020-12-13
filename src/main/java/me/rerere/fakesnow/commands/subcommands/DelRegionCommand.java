package me.rerere.fakesnow.commands.subcommands;

import me.rerere.fakesnow.FakeSnow;
import me.rerere.fakesnow.commands.SubCommand;
import me.rerere.fakesnow.commands.TabHelper;
import me.rerere.fakesnow.manager.Arena;
import me.rerere.fakesnow.manager.SnowController;
import me.rerere.fakesnow.util.Lang;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class DelRegionCommand extends SubCommand implements TabHelper {
    public DelRegionCommand() {
        super("delregion", "<Region>", "Delete a region");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        SnowController snowController = FakeSnow.getInstance().getSnowController();
        if(args.length == 0){
            sender.sendMessage(Lang.PREFIX + "§cPlease input the region name!");
            return;
        }
        if(snowController.getArenas().stream().filter(Arena::isRegion).anyMatch(arena -> arena.getName().equalsIgnoreCase(args[0]))){
            snowController.getArenas().remove(snowController.getArenas().stream().filter(Arena::isRegion).filter(r -> r.getName().equalsIgnoreCase(args[0])).findAny().get());
            snowController.save();
            sender.sendMessage(Lang.PREFIX + "§aRemoved region: "+args[0]);
        }else {
            sender.sendMessage(Lang.PREFIX + "§cThe region "+args[0]+" is not exists!");
        }
    }

    @Override
    public List<String> getSuggestions() {
        SnowController snowController = FakeSnow.getInstance().getSnowController();
        return snowController.getArenas().stream().filter(Arena::isRegion).map(Arena::getName).collect(Collectors.toList());
    }
}
