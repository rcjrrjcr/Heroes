package com.herocraftonline.dev.heroes.util;

import org.bukkit.command.CommandSender;

import com.herocraftonline.dev.heroes.Heroes;

public class Messaging {

    public void send(CommandSender player, String msg, String... params) {
        player.sendMessage(parameterizeMessage(msg, params));
    }

    public void broadcast(Heroes plugin, String msg, String... params) {
        plugin.getServer().broadcastMessage(parameterizeMessage(msg, params));
    }

    private String parameterizeMessage(String msg, String... params) {
        msg = "§9Heroes:§c " + msg;
        if (params != null) {
            for (int i = 0; i < params.length; i++) {
                msg = msg.replace("$" + (i + 1), "§f" + params[i] + "§c");
            }
        }
        return msg;
    }

    public static String createManaBar(int mana) {
        String manaBar = "§c[§9";
        int progress = (int) (mana / 100.0 * 92);
        for (int i = 0; i < progress; i++) {
            manaBar += "|";
        }
        manaBar += "§4";
        for (int i = 0; i < 92 - progress; i++) {
            manaBar += "|";
        }
        manaBar += "§c]";
        return manaBar;
    }

}
