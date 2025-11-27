package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import com.mojang.datafixers.util.Pair;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.*;
import java.util.function.Consumer;

public final class MiscUtils {

    @SuppressWarnings("removal")
    public static ResourceLocation cobblemonResource(String path) {
        Objects.requireNonNull(path, "path");
        return new ResourceLocation("cobblemon", path);
    }

    public static ModelResourceLocation cobblemonModel(String path, String variant) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(variant);
        return new ModelResourceLocation("cobblemon", path, variant);
    }

    public static Component asTranslated(String key) {
        Objects.requireNonNull(key);
        return Component.translatable(key);
    }

    @SuppressWarnings("null")
    public static Component asTranslated(String key, Object... args) {
        Objects.requireNonNull(key);
        return Component.translatable(key, args);
    }

    @SuppressWarnings("removal")
    public static ResourceLocation asResource(String path) {
        Objects.requireNonNull(path);
        return new ResourceLocation(path);
    }

    public static boolean isInt(String value) {
        Objects.requireNonNull(value);
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException ignored) {
            return false;
        }
    }

    public static boolean isUuid(String value) {
        Objects.requireNonNull(value);
        return value.matches("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");
    }

    public static boolean isHigherVersion(String versionA, String versionB) {
        Objects.requireNonNull(versionA);
        Objects.requireNonNull(versionB);

        String[] a = versionA.split("\\.");
        String[] b = versionB.split("\\.");

        int length = Math.min(a.length, b.length);

        for (int i = 0; i < length; i++) {
            int ai = Integer.parseInt(a[i]);
            int bi = Integer.parseInt(b[i]);

            if (ai > bi) return true;
            if (ai < bi) return false;
        }

        return a.length > b.length;
    }

    public static String substitute(String text, String placeholder, Object value) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(placeholder);
        return text.replace("{{" + placeholder + "}}", value == null ? "" : value.toString());
    }

    public static boolean getEither(Pair<Boolean, Boolean> pair) {
        Objects.requireNonNull(pair);
        return pair.getFirst() || pair.getSecond();
    }

    public static float nextBetween(Random random, float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }

    public static double nextBetween(Random random, double min, double max) {
        return random.nextDouble() * (max - min) + min;
    }

    public static int nextBetween(Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static <A, B> Pair<A, B> toDF(A a, B b) {
        return new Pair<>(a, b);
    }

    public static List<BlockPos> blockPositionsAsList(VoxelShape shape) {
        Objects.requireNonNull(shape);
        List<BlockPos> list = new ArrayList<>();

        shape.forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> {
            for (int x = (int)Math.floor(minX); x <= Math.floor(maxX); x++) {
                for (int y = (int)Math.floor(minY); y <= Math.floor(maxY); y++) {
                    for (int z = (int)Math.floor(minZ); z <= Math.floor(maxZ); z++) {
                        list.add(new BlockPos(x, y, z));
                    }
                }
            }
        });

        return list;
    }

    public static <T> Consumer<T> plus(Consumer<T> a, Consumer<T> b) {
        Objects.requireNonNull(a);
        Objects.requireNonNull(b);
        return a.andThen(b);
    }
}
