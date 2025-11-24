/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.datafixers.util.Pair
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.random.Random
 *  kotlin.text.Regex
 *  kotlin.text.StringsKt
 *  net.minecraft.client.resources.model.ModelResourceLocation
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.phys.shapes.VoxelShape
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000~\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0010\u0006\n\u0002\b\u0004\n\u0002\u0010\u0007\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u001d\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0001\u001a\u00020\u00002\u0006\u0010\u0002\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0004\u0010\u0005\u001a\u0015\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0001\u001a\u00020\u0000\u00a2\u0006\u0004\b\u0007\u0010\b\u001a\u0015\u0010\u000b\u001a\u00020\n2\u0006\u0010\t\u001a\u00020\u0000\u00a2\u0006\u0004\b\u000b\u0010\f\u001a\u0011\u0010\r\u001a\u00020\u0006*\u00020\u0000\u00a2\u0006\u0004\b\r\u0010\b\u001a\u0019\u0010\u0010\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000e*\u00020\u0000\u00a2\u0006\u0004\b\u0010\u0010\u0011\u001a-\u0010\u0010\u001a\n \u000f*\u0004\u0018\u00010\u000e0\u000e*\u00020\u00002\u0012\u0010\u0014\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00130\u0012\"\u00020\u0013\u00a2\u0006\u0004\b\u0010\u0010\u0015\u001a\u0017\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00180\u0017*\u00020\u0016\u00a2\u0006\u0004\b\u0019\u0010\u001a\u001a\u0019\u0010\u001c\u001a\u00020\n*\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u0000\u00a2\u0006\u0004\b\u001c\u0010\u001d\u001a\u0011\u0010\u001e\u001a\u00020\n*\u00020\u0000\u00a2\u0006\u0004\b\u001e\u0010\f\u001a!\u0010#\u001a\u00020 *\u00020\u001f2\u0006\u0010!\u001a\u00020 2\u0006\u0010\"\u001a\u00020 \u00a2\u0006\u0004\b#\u0010$\u001a!\u0010#\u001a\u00020%*\u00020\u001f2\u0006\u0010!\u001a\u00020%2\u0006\u0010\"\u001a\u00020%\u00a2\u0006\u0004\b#\u0010&\u001a!\u0010#\u001a\u00020'*\u00020\u001f2\u0006\u0010!\u001a\u00020'2\u0006\u0010\"\u001a\u00020'\u00a2\u0006\u0004\b#\u0010(\u001a:\u0010.\u001a\b\u0012\u0004\u0012\u00028\u00000*\"\u0004\b\u0000\u0010)*\b\u0012\u0004\u0012\u00028\u00000*2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020,0+H\u0086\u0002\u00a2\u0006\u0004\b.\u0010/\u001a#\u00102\u001a\u00020\u0000*\u00020\u00002\u0006\u00100\u001a\u00020\u00002\b\u00101\u001a\u0004\u0018\u00010\u0013\u00a2\u0006\u0004\b2\u00103\u001a4\u00108\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u000107\"\u0004\b\u0000\u00104\"\u0004\b\u0001\u00105*\u00028\u00002\u0006\u00106\u001a\u00028\u0001H\u0086\u0004\u00a2\u0006\u0004\b8\u00109\"!\u0010=\u001a\u00020\n*\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\n0:8F\u00a2\u0006\u0006\u001a\u0004\b;\u0010<\u00a8\u0006>"}, d2={"", "path", "variant", "Lnet/minecraft/client/resources/model/ModelResourceLocation;", "cobblemonModel", "(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/client/resources/model/ModelResourceLocation;", "Lnet/minecraft/resources/ResourceLocation;", "cobblemonResource", "(Ljava/lang/String;)Lnet/minecraft/resources/ResourceLocation;", "string", "", "isUuid", "(Ljava/lang/String;)Z", "asResource", "Lnet/minecraft/network/chat/MutableComponent;", "kotlin.jvm.PlatformType", "asTranslated", "(Ljava/lang/String;)Lnet/minecraft/network/chat/MutableComponent;", "", "", "data", "(Ljava/lang/String;[Ljava/lang/Object;)Lnet/minecraft/network/chat/MutableComponent;", "Lnet/minecraft/world/phys/shapes/VoxelShape;", "", "Lnet/minecraft/core/BlockPos;", "blockPositionsAsList", "(Lnet/minecraft/world/phys/shapes/VoxelShape;)Ljava/util/List;", "other", "isHigherVersion", "(Ljava/lang/String;Ljava/lang/String;)Z", "isInt", "Lkotlin/random/Random;", "", "min", "max", "nextBetween", "(Lkotlin/random/Random;DD)D", "", "(Lkotlin/random/Random;FF)F", "", "(Lkotlin/random/Random;II)I", "T", "Ljava/util/function/Consumer;", "Lkotlin/Function1;", "", "action", "plus", "(Ljava/util/function/Consumer;Lkotlin/jvm/functions/Function1;)Ljava/util/function/Consumer;", "placeholder", "value", "substitute", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;", "A", "B", "b", "Lcom/mojang/datafixers/util/Pair;", "toDF", "(Ljava/lang/Object;Ljava/lang/Object;)Lcom/mojang/datafixers/util/Pair;", "Lkotlin/Pair;", "getEither", "(Lkotlin/Pair;)Z", "either", "common"})
public final class MiscUtilsKt {
    @NotNull
    public static final ResourceLocation cobblemonResource(@NotNull String path) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        return new ResourceLocation("cobblemon", path);
    }

    @NotNull
    public static final ModelResourceLocation cobblemonModel(@NotNull String path, @NotNull String variant) {
        Intrinsics.checkNotNullParameter((Object)path, (String)"path");
        Intrinsics.checkNotNullParameter((Object)variant, (String)"variant");
        return new ModelResourceLocation("cobblemon", path, variant);
    }

    public static final MutableComponent asTranslated(@NotNull String $this$asTranslated) {
        Intrinsics.checkNotNullParameter((Object)$this$asTranslated, (String)"<this>");
        return Component.m_237115_((String)$this$asTranslated);
    }

    @NotNull
    public static final ResourceLocation asResource(@NotNull String $this$asResource) {
        Intrinsics.checkNotNullParameter((Object)$this$asResource, (String)"<this>");
        return new ResourceLocation($this$asResource);
    }

    public static final MutableComponent asTranslated(@NotNull String $this$asTranslated, Object ... data) {
        Intrinsics.checkNotNullParameter((Object)$this$asTranslated, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)data, (String)"data");
        return Component.m_237110_((String)$this$asTranslated, (Object[])Arrays.copyOf(data, data.length));
    }

    public static final boolean isInt(@NotNull String $this$isInt) {
        Intrinsics.checkNotNullParameter((Object)$this$isInt, (String)"<this>");
        return StringsKt.toIntOrNull((String)$this$isInt) != null;
    }

    public static final boolean isHigherVersion(@NotNull String $this$isHigherVersion, @NotNull String other) {
        Intrinsics.checkNotNullParameter((Object)$this$isHigherVersion, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
        String[] stringArray = new String[]{"."};
        List thisSplits = StringsKt.split$default((CharSequence)$this$isHigherVersion, (String[])stringArray, (boolean)false, (int)0, (int)6, null);
        String[] stringArray2 = new String[]{"."};
        List thatSplits = StringsKt.split$default((CharSequence)other, (String[])stringArray2, (boolean)false, (int)0, (int)6, null);
        int thisCount = thisSplits.size();
        int thatCount = thatSplits.size();
        int min2 = Math.min(thisCount, thatCount);
        for (int i = 0; i < min2; ++i) {
            Integer thisDigit = StringsKt.toIntOrNull((String)((String)thisSplits.get(i)));
            Integer thatDigit = StringsKt.toIntOrNull((String)((String)thatSplits.get(i)));
            if (thisDigit == null || thatDigit == null) {
                return false;
            }
            if (thisDigit > thatDigit) {
                return true;
            }
            if (thisDigit >= thatDigit) continue;
            return false;
        }
        return thisCount > thatCount;
    }

    @NotNull
    public static final String substitute(@NotNull String $this$substitute, @NotNull String placeholder, @Nullable Object value2) {
        Intrinsics.checkNotNullParameter((Object)$this$substitute, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)placeholder, (String)"placeholder");
        String string = "{{" + placeholder + "}}";
        Object object = value2;
        if (object == null || (object = object.toString()) == null) {
            object = "";
        }
        return StringsKt.replace$default((String)$this$substitute, (String)string, (String)object, (boolean)false, (int)4, null);
    }

    public static final boolean getEither(@NotNull kotlin.Pair<Boolean, Boolean> $this$either) {
        Intrinsics.checkNotNullParameter($this$either, (String)"<this>");
        return (Boolean)$this$either.getFirst() != false || (Boolean)$this$either.getSecond() != false;
    }

    public static final float nextBetween(@NotNull Random $this$nextBetween, float min2, float max2) {
        Intrinsics.checkNotNullParameter((Object)$this$nextBetween, (String)"<this>");
        return $this$nextBetween.nextFloat() * (max2 - min2) + min2;
    }

    public static final double nextBetween(@NotNull Random $this$nextBetween, double min2, double max2) {
        Intrinsics.checkNotNullParameter((Object)$this$nextBetween, (String)"<this>");
        return $this$nextBetween.nextDouble() * (max2 - min2) + min2;
    }

    public static final int nextBetween(@NotNull Random $this$nextBetween, int min2, int max2) {
        Intrinsics.checkNotNullParameter((Object)$this$nextBetween, (String)"<this>");
        return $this$nextBetween.nextInt(max2 - min2 + 1) + min2;
    }

    @NotNull
    public static final <A, B> Pair<A, B> toDF(A $this$toDF, B b) {
        return new Pair($this$toDF, b);
    }

    public static final boolean isUuid(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"string");
        return new Regex("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$").matches((CharSequence)string);
    }

    @NotNull
    public static final List<BlockPos> blockPositionsAsList(@NotNull VoxelShape $this$blockPositionsAsList) {
        Intrinsics.checkNotNullParameter((Object)$this$blockPositionsAsList, (String)"<this>");
        List result = new ArrayList();
        $this$blockPositionsAsList.m_83286_((arg_0, arg_1, arg_2, arg_3, arg_4, arg_5) -> MiscUtilsKt.blockPositionsAsList$lambda$0(result, arg_0, arg_1, arg_2, arg_3, arg_4, arg_5));
        return result;
    }

    @NotNull
    public static final <T> Consumer<T> plus(@NotNull Consumer<T> $this$plus, @NotNull Function1<? super T, Unit> action2) {
        Intrinsics.checkNotNullParameter($this$plus, (String)"<this>");
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        Consumer<Object> consumer = $this$plus.andThen(arg_0 -> MiscUtilsKt.plus$lambda$1(action2, arg_0));
        Intrinsics.checkNotNullExpressionValue(consumer, (String)"andThen(action)");
        return consumer;
    }

    private static final void blockPositionsAsList$lambda$0(List $result, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        Intrinsics.checkNotNullParameter((Object)$result, (String)"$result");
        int n = (int)maxX;
        for (int x = (int)minX; x < n; ++x) {
            int n2 = (int)maxY;
            for (int y = (int)minY; y < n2; ++y) {
                int n3 = (int)maxZ;
                for (int z = (int)minZ; z < n3; ++z) {
                    $result.add(new BlockPos(x, y, z));
                }
            }
        }
    }

    private static final void plus$lambda$1(Function1 $tmp0, Object p0) {
        Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
        $tmp0.invoke(p0);
    }
}

