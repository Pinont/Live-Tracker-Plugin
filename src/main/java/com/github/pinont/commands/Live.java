package com.github.pinont.commands;

import com.github.pinont.Core;
import com.github.pinont.singularitylib.api.annotation.AutoRegister;
import com.github.pinont.singularitylib.api.command.SimpleCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;

@AutoRegister
public class Live implements SimpleCommand {
    @Override
    public String getName() {
        return "live:livetracker:lt";
    }

    @Override
    public String usage(Boolean bool) {
        return SimpleCommand.super.usage(bool);
    }

    @Override
    public String description() {
        return "Live Tracker main command.";
    }

    @Override
    public void execute(CommandSourceStack commandSourceStack, String[] strings) {
        if (strings.length == 0) return;
        if (strings[0].equalsIgnoreCase("reload")) {
            new Core().reload(commandSourceStack.getSender());
        }
    }

    @Override
    public Collection<String> suggest(CommandSourceStack commandSourceStack, String[] args) {
        return Collections.singletonList("reload");
    }

    @Override
    public @Nullable String permission() {
        return "livetracker.commands.admin";
    }
}
