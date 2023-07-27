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
- Menu Framework
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
public class ExampleClass extends JavaPlugin {

    @Override
    public void onEnable() {
        // Initialize the HanaLib
        HanaLib.init(this);

        // Then if you want to use MenuFramework
        new MenuFramework();
    }
}
```

#### Menu

```java
public class ExampleMenu extends Menu implements Listener {

    @Override
    public String getName(Player player) {
        return "Example Menu";
    }

    @Override
    public List<Slot> getSlots() {
        List<Slot> slots = new ArrayList<>();

        // Added Get Diamond slot.
        slots.add(new Slot() {
            @Override
            public ItemStack getItem(Player player) {
                return new ItemBuilder(Material.DIAMOND).setName("Get Diamond").toItemStack();
            }

            @Override
            public int getSlot() {
                return 13;
            }

            @Override
            public void onClick(Player player, int slot, ClickType clickType) {
                player.getInventory().addItem(new ItemStack(Material.DIAMOND));
            }
        });

        // Fill menu with Glass.
        Slot.fillGlass(slots, 27);

        return slots;
    }
}
```