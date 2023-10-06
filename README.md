<h1>HanaLib</h1>

<div>

[![](https://jitpack.io/v/Lotiny/HanaLib.svg)](https://jitpack.io/#Lotiny/HanaLib)
[![Discord](https://img.shields.io/discord/1061534844494028830.svg?color=lime&label=Discord)](https://discord.gg/qBqQYgRHaF)
[![Donation](https://img.shields.io/badge/Donation-PayPal-blue)](https://www.paypal.com/paypalme/Lotiny2825)

</div>

---

### Built-in features

- ItemBuilder
- Cooldown
- Menu
- Command
- ConfigFile
- Scoreboard (Credit go to Assemble https://github.com/ThatKawaiiSam/Assemble)
- and more!

---

### Maven dependency

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>
```
```xml
<dependency>
    <groupId>com.github.Lotiny</groupId>
    <artifactId>HanaLib</artifactId>
    <version>{version}</version>
    <scope>compile</scope>
</dependency>
```

### Gradle dependency
```
repositories {
    maven { url 'https://jitpack.io' }
}
```
```
dependencies {
    implementation 'com.github.Lotiny:HanaLib:{version}'
}
```

---

### Example usage

#### Setup HanaLib

```java
import me.lotiny.libs.HanaLib;

import java.util.Arrays;

public class ExampleClass extends JavaPlugin {

    @Override
    public void onEnable() {
        HanaLib hanaLib = new HanaLib(this);
        // Register command handler.
        hanaLib.registerCommandHandler();
        // Register menu handler.
        hanaLib.registerMenuHandler();

        // Register commands.
        registerCommands(hanaLib);
    }

    private void registerCommands(HanaLib hanaLib) {
        Arrays.asList(
                new ExampleCommand1(),
                new ExampleCommand2(),
                new ExampleCommand3(),
                new ExampleCommand4(),
                // ...
                new ExampleCommand99()
        ).forEach(command -> {
            hanaLib.getCommandHandler().register(command);
        });
    }
}
```

#### Menu

```java
import me.lotiny.libs.chat.CC;
import me.lotiny.libs.general.ItemBuilder;
import me.lotiny.libs.menu.menu.Menu;
import me.lotiny.libs.menu.slots.Slot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ExampleMenu extends Menu {

    @Override
    public String getName(Player player) {
        return "Example Menu";
    }

    @Override
    public List<Slot> getSlots(Player player) {
        List<Slot> slots = new ArrayList<>();

        slots.add(new Slot() {
            @Override
            public ItemStack getItem(Player player) {
                return new ItemBuilder(Material.CARROT)
                        .setName("TEST ITEM")
                        .toItemStack();
            }

            @Override
            public int getSlot() {
                return 13;
            }

            @Override
            public void onClick(Player player, int slot, ClickType clickType) {
                player.sendMessage(CC.GREEN + "CLICKED TEST ITEM!");
            }
        });

        return slots;
    }
}
```

#### Command

```java
import me.lotiny.libs.command.Command;
import me.lotiny.libs.command.CommandArgs;
import me.lotiny.libs.command.HanaCommand;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ExampleCommand extends HanaCommand {

    @Override
    @Command(name = "example", aliases = {"ex1", "ex2"}, usage = "/example",
            permission = "cmd.example", inGameOnly = false)
    public void execute(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        // Open ExampleMenu
        new ExampleMenu().open(player);
        player.playSound(player.getLocation(), Sound.CHEST_OPEN, 1, 1);
    }
}
```