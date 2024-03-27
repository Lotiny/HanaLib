<h1>HanaLib</h1>

<div>

[![](https://jitpack.io/v/Lotiny/HanaLib.svg)](https://jitpack.io/#Lotiny/HanaLib)
[![Donation](https://img.shields.io/badge/Donation-PayPal-blue)](https://www.paypal.com/paypalme/Lotiny2825)

</div>

---

### Built-in features

- ItemBuilder
- Cooldown
- Menu
- Command
- ConfigFile
- MongoDB
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

public class MainClass extends JavaPlugin {

    @Override
    public void onEnable() {
        // Init HanaLib
        HanaLib hanaLib = new HanaLib(this);

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
        ).forEach(command -> hanaLib.getCommandHandler().register(command));
    }
}
```

#### Menu

```java
import me.lotiny.libs.chat.CC;
import me.lotiny.libs.utils.ItemBuilder;
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
                
                // Play Sound.CLICK to player.
                playClickSound(player);
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

#### MongoDB

```java
import com.mongodb.client.MongoCollection;
import lombok.Getter;
import me.lotiny.libs.database.Mongo;
import me.lotiny.libs.database.MongoCredentials;
import me.lotiny.libs.utils.ServerUtil;
import me.lotiny.uhc.HanaUHC;
import org.bson.Document;
import org.bukkit.Bukkit;

@Getter
public class ExampleMongoDB extends Mongo {

    private final MongoCollection<Document> collection;

    public MongoManager() {
        super(MongoCredentials.of(
                "mongodb://localhost:27017",
                "database_name"
        ));

        // Connect to database.
        connect();

        // Set the collection.
        this.collection = this.getDatabase().getCollection("collection_name");
    }
}
```