package me.lotiny.libs.serialization;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

@UtilityClass
public class LocationSerialization {

    /**
     * Serialize a `Location` object to a string.
     *
     * @param location The `Location` object to serialize.
     * @return A serialized string representation of the `Location`.
     */
    public String serialize(Location location) {
        return location.getWorld().getName() + ":" + location.getX() + ":" + location.getY() + ":" +
                location.getZ() + ":" + location.getYaw() + ":" + location.getPitch();
    }

    /**
     * Deserialize a serialized string representation of a `Location` back to a `Location` object.
     *
     * @param source The serialized string containing location information.
     * @return A `Location` object, or `null` if the source is invalid.
     */
    public Location deserialize(String source) {
        if (source == null) {
            return null;
        }

        String[] split = source.split(":");

        if (split.length != 6) {
            return null; // Invalid format, return null
        }

        World world = Bukkit.getServer().getWorld(split[0]);

        if (world == null) {
            return null; // Unknown world, return null
        }

        try {
            double x = Double.parseDouble(split[1]);
            double y = Double.parseDouble(split[2]);
            double z = Double.parseDouble(split[3]);
            float yaw = Float.parseFloat(split[4]);
            float pitch = Float.parseFloat(split[5]);

            return new Location(world, x, y, z, yaw, pitch);
        } catch (NumberFormatException e) {
            return null; // Invalid number format, return null
        }
    }
}
