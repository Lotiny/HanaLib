package me.lotiny.libs.command;

import lombok.Getter;
import me.lotiny.libs.chat.CC;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class HanaCommand {

    private final Command command;

    public HanaCommand() {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            Command annotation = method.getAnnotation(Command.class);
            if (annotation != null) {
                this.command = annotation;
                return;
            }
        }
        this.command = null;
    }

    public abstract void execute(CommandArgs commandArgs);

    public List<String> onTabComplete(CommandArgs commandArgs) {
        return new ArrayList<>();
    }

    public void sendUsage(CommandSender to) {
        if (command.usage() == null || command.usage().isEmpty()) {
            throw new NullPointerException("Cannot find usage for command `" + command.name() + "`");
        }

        to.sendMessage(CC.RED + "Usage: " + command.name());
    }

    public Player getTargetPlayer(CommandSender sender, String targetName) {
        Player target = Bukkit.getPlayer(targetName);

        if (target == null) {
            sender.sendMessage(CC.RED + "The player you specified is not online.");
            return null;
        } else {
            return target;
        }
    }
}
