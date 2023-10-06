package me.lotiny.libs.serialization;

import lombok.experimental.UtilityClass;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collection;

@UtilityClass
public class EffectSerialization {

    /**
     * Serialize a collection of `PotionEffect` objects to a string.
     *
     * @param effects The collection of `PotionEffect` objects to serialize.
     * @return A serialized string representation of the effects.
     */
    public String serilizeEffects(Collection<PotionEffect> effects) {
        StringBuilder builder = new StringBuilder();
        for (PotionEffect potionEffect : effects) {
            builder.append(serilizePotionEffect(potionEffect));
            builder.append(";");
        }
        return builder.toString();
    }

    /**
     * Deserialize a serialized string representation of effects back to a collection of `PotionEffect` objects.
     *
     * @param source The serialized string containing effect information.
     * @return A collection of `PotionEffect` objects, or `null` if the source is invalid.
     */
    public Collection<PotionEffect> deserilizeEffects(String source) {
        if (!source.contains(":")) {
            return null;
        }
        Collection<PotionEffect> effects = new ArrayList<>();
        String[] split = source.split(";");

        for (String piece : split) {
            effects.add(deserilizePotionEffect(piece));
        }

        return effects;
    }

    /**
     * Serialize a `PotionEffect` object to a string.
     *
     * @param potionEffect The `PotionEffect` object to serialize.
     * @return A serialized string representation of the `PotionEffect`.
     */
    public String serilizePotionEffect(PotionEffect potionEffect) {
        StringBuilder builder = new StringBuilder();

        if (potionEffect == null) {
            return "null";
        }
        String name = potionEffect.getType().getName();
        builder.append("n@").append(name);

        String duration = String.valueOf(potionEffect.getDuration());
        builder.append(":d@").append(duration);

        String amplifier = String.valueOf(potionEffect.getAmplifier());
        builder.append(":a@").append(amplifier);

        return builder.toString();
    }

    /**
     * Deserialize a serialized string representation of a `PotionEffect` back to a `PotionEffect` object.
     *
     * @param source The serialized string containing effect information.
     * @return A `PotionEffect` object, or `null` if the source is invalid.
     */
    public PotionEffect deserilizePotionEffect(String source) {
        String name = "";
        String duration = "";
        String amplifier = "";

        if (source.equals("null")) {
            return null;
        }
        String[] split = source.split(":");

        for (String effectInfo : split) {
            String[] itemAttribute = effectInfo.split("@");
            String s2 = itemAttribute[0];

            if (s2.equalsIgnoreCase("n")) {
                name = itemAttribute[1];
            }
            if (s2.equalsIgnoreCase("d")) {
                duration = itemAttribute[1];
            }
            if (s2.equalsIgnoreCase("a")) {
                amplifier = itemAttribute[1];
            }
        }
        return new PotionEffect(PotionEffectType.getByName(name), Integer.parseInt(duration), Integer.parseInt(amplifier));
    }
}
