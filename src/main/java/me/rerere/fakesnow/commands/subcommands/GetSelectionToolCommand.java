package me.rerere.fakesnow.commands.subcommands;

import me.rerere.fakesnow.commands.SubCommand;
import me.rerere.fakesnow.util.ItemBuilder;
import me.rerere.fakesnow.util.Lang;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;

public class GetSelectionToolCommand extends SubCommand {
    public GetSelectionToolCommand() {
        super("gettool", "", "Get the region selection tool");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            player.getInventory().addItem(
                    ItemBuilder
                            .typeOf(Material.STICK)
                            .name("&aSnow Region Selection Tool")
                            .lore(
                                    "&aUse the tool to select a snow region",
                                    "",
                                    "&eLeft Click Block",
                                    "&7Select a point A",
                                    "&eRight Click Block",
                                    "&7Select a point B",
                                    "",
                                    "&aAnd then, use command",
                                    "&b/fakesnow addregion <name>",
                                    "&ato save the region"
                            )
                            .enchant(Enchantment.DURABILITY,1)
                            .flag(ItemFlag.HIDE_ENCHANTS)
                            .build()
            );
            sender.sendMessage(Lang.PREFIX + "§aGave you a selection tool~");
        }else {
            sender.sendMessage(Lang.PREFIX + "§cThe command only can executed by a player!");
        }
    }
}
