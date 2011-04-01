package com.herocraftonline.dev.heroes.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.herocraftonline.dev.heroes.command.BaseCommand;

public class AbiltiesManager {

    protected List<BaseCommand> commands;

    public AbiltiesManager() {
        commands = new ArrayList<BaseCommand>();
    }

    public boolean dispatch(CommandSender sender, Command command, String label, String[] args) {
        String input = label + " ";
        for (String s : args) {
            input += s + " ";
        }

        BaseCommand match = null;
        String[] trimmedArgs = null;
        StringBuilder identifier = new StringBuilder();

        for (BaseCommand cmd : commands) {
            StringBuilder tmpIdentifier = new StringBuilder();
            String[] tmpArgs = cmd.validate(input, tmpIdentifier);
            if (tmpIdentifier.length() > identifier.length()) {
                identifier = tmpIdentifier;
                match = cmd;
                trimmedArgs = tmpArgs;
            }
        }

        if (match != null) {
            if (trimmedArgs != null) {
                match.execute(sender, trimmedArgs);
                return true;
            } else {
                sender.sendMessage("§cCommand: " + match.getName());
                sender.sendMessage("§cDescription: " + match.getDescription());
                sender.sendMessage("§cUsage: " + match.getUsage());
            }
        }

        return true;
    }

    public void addCommand(BaseCommand command) {
        commands.add(command);
    }

    public void removeCommand(BaseCommand command) {
        commands.remove(command);
    }
}
