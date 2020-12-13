package me.rerere.fakesnow.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.CommandSender;

@AllArgsConstructor
@Getter
public abstract class SubCommand {
    private String name;
    private String params;
    private String description;

    public abstract void execute(CommandSender sender, String[] args);
}
