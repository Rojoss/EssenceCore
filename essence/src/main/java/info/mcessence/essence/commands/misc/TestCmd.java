/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Essence <http://www.mc-essence.info>
 * Copyright (c) 2015 contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package info.mcessence.essence.commands.misc;

import info.mcessence.essence.Essence;
import info.mcessence.essence.cmd_arguments.ListArgument;
import info.mcessence.essence.cmd_arguments.internal.ArgumentParseResults;
import info.mcessence.essence.cmd_arguments.internal.ArgumentRequirement;
import info.mcessence.essence.cmd_arguments.internal.CmdArgument;
import info.mcessence.essence.commands.EssenceCommand;
import info.mcessence.essence.entity.EItem;
import info.mcessence.essence.util.InvUtil;
import info.mcessence.essence.util.ItemParser;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.List;

public class TestCmd extends EssenceCommand {

    public TestCmd(Essence ess, String command, String description, String permission, List<String> aliases) {
        super(ess, command, description, permission, aliases);

        List<String> testArgs = Arrays.asList("items", "itemstring");

        cmdArgs = new CmdArgument[] {
                new ListArgument("type", ArgumentRequirement.REQUIRED, "", testArgs)
        };

        register();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        ArgumentParseResults result = parseArgs(this, sender, args);
        if (!result.success) {
            return true;
        }
        args = result.getArgs();

        //Test methods without player.

        if (!(sender instanceof Player)) {
            sender.sendMessage("This can't be tested from the console!");
            return true;
        }
        Player player = (Player)sender;

        //Test methods with player
        if (args[0].equalsIgnoreCase("items")) {
            itemTest(player);
        }
        if (args[0].equalsIgnoreCase("itemstring")) {
            itemString(player);
        }

        return true;
    }


    public void itemTest(Player player) {
        ItemParser parser = new ItemParser("playerskull 1 texture:cbb311f3ba1c07c3d1147cd210d81fe11fd8ae9e3db212a0fa748946c3633", false);
        if (!parser.isValid()) {
            player.sendMessage(parser.getError().msg().getMsg(true));
        } else {
            InvUtil.addItems(player.getInventory(), parser.getItem());
        }
        parser = new ItemParser("leatherhelmet:10 1 name:&6Golden_helmet protection:1 protection:2 color:#CC9900", false);
        if (!parser.isValid()) {
            player.sendMessage(parser.getError().msg().getMsg(true));
        } else {
            InvUtil.addItems(player.getInventory(), parser.getItem());
        }
        parser = new ItemParser("diamond 1 name:&bDiamond sharpness:1 lore:&3Shiny_diamond!|&7With_lore!", false);
        if (!parser.isValid()) {
            player.sendMessage(parser.getError().msg().getMsg(true));
        } else {
            InvUtil.addItems(player.getInventory(), parser.getItem());
        }
        InvUtil.addItems(player.getInventory(), new EItem(Material.SKULL_ITEM).setSkull("Worstboy"));
        InvUtil.addItems(player.getInventory(), new EItem(Material.SKULL_ITEM).setTexture("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTU1ZDYxMWE4NzhlODIxMjMxNzQ5YjI5NjU3MDhjYWQ5NDI2NTA2NzJkYjA5ZTI2ODQ3YTg4ZTJmYWMyOTQ2In19fQ=="));
        InvUtil.addItems(player.getInventory(), new EItem(Material.SKULL_ITEM).setTexture("http://textures.minecraft.net/texture/82d8ccac4d982bf3199761c1c74b9aa18e312ff5ca0a6e51b77a87abad610b"));
        InvUtil.addItems(player.getInventory(), new EItem(Material.SKULL_ITEM).setTexture("cbb311f3ba1c07c3d1147cd210d81fe11fd8ae9e3db212a0fa748946c3633"));
        InvUtil.addItems(player.getInventory(), new EItem(Material.DIAMOND_SWORD).setName("&aName").setLore("lore0", "&alore1").addEnchant(Enchantment.ARROW_FIRE, 1));
        InvUtil.addItems(player.getInventory(), new EItem(Material.LEATHER_CHESTPLATE).setColor("#191919"));
        InvUtil.addItems(player.getInventory(), new EItem(Material.LEATHER_CHESTPLATE).setColor("191919"));
        InvUtil.addItems(player.getInventory(), new EItem(Material.LEATHER_CHESTPLATE).setColor("20,200,255"));
        InvUtil.addItems(player.getInventory(), new EItem(Material.LEATHER_CHESTPLATE).setColor("500,500,500"));
        InvUtil.addItems(player.getInventory(), new EItem(Material.LEATHER_CHESTPLATE).setColor("asdas"));
        InvUtil.addItems(player.getInventory(), new EItem(Material.PAPER).setLore("lore0", "lore1", "lore2", "lore3").clearLore(2).setLore(0, "0"));
        InvUtil.addItems(player.getInventory(), new EItem(Material.PAPER).setLore(4, "lore4").clearLore(6).addEffect(new PotionEffect(PotionEffectType.SPEED, 20, 20), true));
        InvUtil.addItems(player.getInventory(), new EItem(Material.POTION, 1, (short)8196).addEffect(new PotionEffect(PotionEffectType.SPEED, 100, 3), true).addEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 3), true).setMainEffect(PotionEffectType.REGENERATION));
    }

    public void itemString(Player player) {
        ItemParser parser = new ItemParser(player.getItemInHand());
        if (!parser.isValid()) {
            player.sendMessage(parser.getError().msg().getMsg(true));
        } else {
            player.sendMessage(parser.getString());
        }
    }
}
