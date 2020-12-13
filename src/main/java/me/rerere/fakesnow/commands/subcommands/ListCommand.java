package me.rerere.fakesnow.commands.subcommands;

import me.rerere.fakesnow.FakeSnow;
import me.rerere.fakesnow.commands.SubCommand;
import me.rerere.fakesnow.manager.Arena;
import me.rerere.fakesnow.util.VecUtil;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ListCommand extends SubCommand {
    public ListCommand() {
        super("list", "", "Get the snow area that has been added");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Set<Arena> arenas = FakeSnow.getInstance().getSnowController().getArenas();
        sender.sendMessage("§6▶ §aSnow Worlds:");
        List<String> worlds = arenas.stream().filter(Arena::isWholeWorld).map(Arena::getWorld).collect(Collectors.toList());
        if (!worlds.isEmpty()) {
            worlds.forEach(world -> sender.sendMessage("§7- §b" + world));
        }else {
            sender.sendMessage("§7- None");
        }
        sender.sendMessage("§6▶ §aSnow Regions:");
        List<Arena> rs = arenas.stream().filter(Arena::isRegion).collect(Collectors.toList());
        if(!rs.isEmpty()) {
            rs.forEach(arena -> {
                sender.sendMessage("§7- §bName: §f" + arena.getName() + " §bWorld: §f" + arena.getWorld());
                sender.sendMessage("    §bPointA: §f" + VecUtil.asString(arena.getPointOne()) + " §bPointB: §f" + VecUtil.asString(arena.getPointTwo()));
            });
        }else {
            sender.sendMessage("§7- None");
        }
    }
}
