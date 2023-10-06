package me.lotiny.libs.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class CommandArgs {

    private final CommandSender sender;
    private final String label;
    private final String[] args;

    public Player getPlayer() {
        if (!(sender instanceof Player)) {
            return null;
        }

        return (Player) sender;
    }
}
