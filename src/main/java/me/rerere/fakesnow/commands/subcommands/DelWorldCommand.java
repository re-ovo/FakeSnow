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

public class DelWorldCommand extends SubCommand implements TabHelper {
    public DelWorldCommand() {
        super("delworld", "<World>", "Remove a world from the snow worlds");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0){
            sender.sendMessage(Lang.PREFIX + "§cPlease input the world name!");
            return;
        }
        String worldName = args[0];
        SnowController snowController = FakeSnow.getInstance().getSnowController();
        if(snowController.getArenas().stream().filter(Arena::isWholeWorld).anyMatch(w -> w.getWorld().equalsIgnoreCase(worldName))){
            snowController.getArenas().remove(snowController.getArenas().stream().filter(Arena::isWholeWorld).filter(w -> w.getWorld().equalsIgnoreCase(worldName)).findAny().get());
            snowController.save();
            sender.sendMessage(Lang.PREFIX +"§aRemoved "+worldName+" from the snow worlds!");
        }else {
            sender.sendMessage(Lang.PREFIX + "§cThe world is not a snow world!");
        }
    }

    @Override
    public List<String> getSuggestions() {
        SnowController snowController = FakeSnow.getInstance().getSnowController();
        return snowController.getArenas().stream().filter(Arena::isWholeWorld).map(Arena::getName).collect(Collectors.toList());
    }
}
