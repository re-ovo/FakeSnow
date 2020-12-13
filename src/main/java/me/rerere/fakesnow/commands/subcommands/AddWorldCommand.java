package me.rerere.fakesnow.commands.subcommands;

import me.rerere.fakesnow.FakeSnow;
import me.rerere.fakesnow.commands.SubCommand;
import me.rerere.fakesnow.commands.TabHelper;
import me.rerere.fakesnow.manager.Arena;
import me.rerere.fakesnow.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.stream.Collectors;

public class AddWorldCommand extends SubCommand implements TabHelper {
    public AddWorldCommand() {
        super("addworld", "<World>", "Make a world snow");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0){
            return;
        }
        String world = args[0];
        if(Bukkit.getWorld(world) == null){
            sender.sendMessage(Lang.PREFIX + "§cWorld \""+world+"\" not exists!");
            return;
        }
        if(FakeSnow.getInstance().getSnowController().getArenas().stream().filter(Arena::isWholeWorld).anyMatch(arena -> arena.getWorld().equalsIgnoreCase(world))){
            sender.sendMessage(Lang.PREFIX + "§cThe world is already snowing!");
            return;
        }
        FakeSnow.getInstance().getSnowController().addWorld(world);
        sender.sendMessage(Lang.PREFIX + "§aAdded \""+world+"\" to the snow worlds~");
    }

    @Override
    public List<String> getSuggestions() {
        return Bukkit.getWorlds().stream().filter(world -> world.getEnvironment() == World.Environment.NORMAL).map(World::getName).collect(Collectors.toList());
    }
}
