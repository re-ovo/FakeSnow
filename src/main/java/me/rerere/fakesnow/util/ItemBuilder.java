package me.rerere.fakesnow.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemBuilder {
    private final ItemStack itemStack;

    // hide constructor
    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public static ItemBuilder typeOf(Material material){
        return new ItemBuilder(material);
    }

    public ItemStack build(){
        return this.itemStack;
    }

    /// APIs BLOW
    public ItemBuilder name(String name){
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder enchant(Enchantment enchantment, int level){
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder amount(int amount){
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder flag(ItemFlag itemFlag){
        ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(itemFlag);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(String... lore){
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(Stream.of(lore).map(line -> ChatColor.translateAlternateColorCodes('&', line)).collect(Collectors.toList()));
        this.itemStack.setItemMeta(meta);
        return this;
    }
}
