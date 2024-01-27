package fr.nono74210.worlddetection.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DetectionTab implements TabCompleter {

    List<String> arguments = new ArrayList<>();
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(arguments.isEmpty()){
            arguments.add("reload");
        }

        List<String> result = new ArrayList<>();
        if(args.length == 1) {
            for (String a : arguments) {
                if (a.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(a);
                }
                return result;
            }
        }
        return null;
    }
}

//Possibilité d'ajouter des commandes par la suite (infos config ou autres)