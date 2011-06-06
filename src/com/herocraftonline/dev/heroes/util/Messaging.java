package com.herocraftonline.dev.heroes.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.herocraftonline.dev.heroes.Heroes;

public final class Messaging {

    public static void send(CommandSender player, String msg, String... params) {
        player.sendMessage(parameterizeMessage(msg, params));
    }

    public static void broadcast(Heroes plugin, String msg, String... params) {
        plugin.getServer().broadcastMessage(parameterizeMessage(msg, params));
    }

    private static String parameterizeMessage(String msg, String... params) {
        msg = ChatColor.BLUE + "Heroes: " + ChatColor.RED + msg;
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                msg = msg.replace("$" + (i + 1), ChatColor.WHITE + params[i] + ChatColor.RED);
            }
        }
        return msg;
    }

    public static String createManaBar(int mana) {
        String manaBar = ChatColor.RED + "[" + ChatColor.BLUE;
        int progress = (int) (mana / 100.0 * 50);
        for (int i = 0; i < progress; i++) {
            manaBar += "|";
        }
        manaBar += ChatColor.DARK_RED;
        for (int i = 0; i < 50 - progress; i++) {
            manaBar += "|";
        }
        manaBar += ChatColor.RED + "]";
        return manaBar + " - " + ChatColor.BLUE + mana + "%";
    }

}
