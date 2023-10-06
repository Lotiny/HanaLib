package me.lotiny.libs.command;

import me.lotiny.libs.HanaLib;
import me.lotiny.libs.chat.CC;
import me.lotiny.libs.utils.ServerUtil;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private final JavaPlugin plugin = HanaLib.getInstance();

    public static String NO_PERM = CC.RED + "You don't have permission to execute this command.";
    public static String ONLY_PLAYER = CC.RED + "Only player can execute this command.";

    private final Map<String, HanaCommand> commands = new HashMap<>();

    protected CommandMap commandMap;

    public CommandHandler() {
        if (plugin.getServer().getPluginManager() instanceof SimplePluginManager) {
            final SimplePluginManager manager = (SimplePluginManager) plugin.getServer().getPluginManager();

            try {
                final Field field = SimplePluginManager.class.getDeclaredField("commandMap");
                field.setAccessible(true);

                commandMap = (CommandMap) field.get(manager);
            } catch (IllegalArgumentException | SecurityException | IllegalAccessException | NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getDefaultTabComplete(CommandArgs commandArgs) {
        List<String> toReturn = ServerUtil.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
        String[] args = commandArgs.getArgs();

        if (args.length == 0) {
            return new ArrayList<>();
        }

        int lastIndex = args.length - 1;
        if (!args[lastIndex].isEmpty()) {
            toReturn.clear();

            ServerUtil.getOnlinePlayers().forEach(player -> {
                if (player.getName().toLowerCase().startsWith(args[lastIndex].toLowerCase())) {
                    toReturn.add(player.getName());
                }
            });
        }

        return toReturn;
    }

    public void register(HanaCommand command) {
        String name = command.getCommand().name();

        commands.putIfAbsent(name, command);

        try {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);

            PluginCommand pluginCommand = constructor.newInstance(name, plugin);
            pluginCommand.setTabCompleter(this);
            pluginCommand.setExecutor(this);
            pluginCommand.setUsage(command.getCommand().usage());
            pluginCommand.setPermission(command.getCommand().permission());
            pluginCommand.setAliases(Arrays.asList(command.getCommand().aliases()));

            commandMap.register(name, pluginCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        HanaCommand cmd = commands.get(command.getName());

        if (cmd.getCommand().inGameOnly() && commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage(ONLY_PLAYER);
            return true;
        }

        if (cmd.getCommand().permission() != null && !cmd.getCommand().permission().isEmpty() && !commandSender.hasPermission(cmd.getCommand().permission())) {
            commandSender.sendMessage(NO_PERM);
            return true;
        }

        cmd.execute(new CommandArgs(commandSender, s, strings));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        HanaCommand cmd = commands.get(command.getName());
        List<String> toReturn = cmd.onTabComplete(new CommandArgs(commandSender, null, strings));

        if (toReturn.isEmpty()) {
            toReturn.addAll(getDefaultTabComplete(new CommandArgs(commandSender, null, strings)));
        }

        return toReturn;
    }
}