package me.rerere.fakesnow.commands;

import me.rerere.fakesnow.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeSnowCommand implements CommandExecutor, TabCompleter {
    private static final List<SubCommand> subCommands = Arrays.asList(
            new AddWorldCommand(),
            new DelWorldCommand(),
            new GetSelectionToolCommand(),
            new AddRegionCommand(),
            new DelRegionCommand(),
            new ListCommand()
    );

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!sender.hasPermission("fakesnow.use")){
            sender.sendMessage("§cYou don't have permission to use the command!");
            return true;
        }
        if(args.length == 0){
            sender.sendMessage("§a========== §bFakeSnow §a=========");
            for (SubCommand subCommand : subCommands) {
                sender.sendMessage(String.format("§b/%s §3%s §2%s §7- §a%s", label.toLowerCase(), subCommand.getName(), subCommand.getParams(), subCommand.getDescription()));
            }
        }else {
            Optional<SubCommand> subCommand = subCommands.stream().filter(c -> c.getName().equalsIgnoreCase(args[0])).findFirst();
            if(subCommand.isPresent()){
                subCommand.get().execute(sender, Arrays.copyOfRange(args, 1, args.length));
            }else {
                sender.sendMessage("§cSorry, the sub-command "+args[0]+" is not exists!");
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(!sender.hasPermission("fakesnow.use")){
            return null;
        }
        // commands
        if (args.length == 0) {
            return subCommands.stream().map(SubCommand::getName).collect(Collectors.toList());
        }
        if (args.length == 1) {
            for (SubCommand subCommand : subCommands) {
                if (subCommand.getName().startsWith(args[0]) && !subCommand.getName().equalsIgnoreCase(args[0])) {
                    return Arrays.asList(subCommand.getName());
                }
            }
            return subCommands.stream().map(SubCommand::getName).collect(Collectors.toList());
        }
        if (args.length == 2) {
            for (SubCommand subCommand : subCommands) {
                if (subCommand.getName().equalsIgnoreCase(args[0]) && subCommand instanceof TabHelper) {
                    return ((TabHelper)subCommand).getSuggestions();
                }
            }
        }
        return null;
    }
}
