/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.NoWhenBranchMatchedException
 *  kotlin.Pair
 *  kotlin.Unit
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import io.netty.buffer.ByteBuf;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=2, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010&\n\u0002\b\n\u001a\u0011\u0010\u0002\u001a\u00020\u0001*\u00020\u0000\u00a2\u0006\u0004\b\u0002\u0010\u0003\u001a\u001f\u0010\u0007\u001a\u00020\u0005*\u00020\u00002\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\u0004\b\u0007\u0010\b\u001aU\u0010\u0011\u001a\u00020\u0005\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\n*\u00020\u00002\b\b\u0002\u0010\f\u001a\u00020\u000b2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\r2\u0018\u0010\u0010\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u000f0\u0004\u00a2\u0006\u0004\b\u0011\u0010\u0012\u001a\u0019\u0010\u0014\u001a\u00020\u0013*\u00020\u00002\u0006\u0010\f\u001a\u00020\u000b\u00a2\u0006\u0004\b\u0014\u0010\u0015\u001a)\u0010\u0017\u001a\u00020\u0005*\u00020\u00002\b\b\u0002\u0010\f\u001a\u00020\u000b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\u0004\b\u0017\u0010\u0018\u001a\u0011\u0010\u001a\u001a\u00020\u0019*\u00020\u0000\u00a2\u0006\u0004\b\u001a\u0010\u001b\u001a\u0019\u0010\u001d\u001a\u00020\u0005*\u00020\u00002\u0006\u0010\u001c\u001a\u00020\u0001\u00a2\u0006\u0004\b\u001d\u0010\u001e\u001a-\u0010\"\u001a\u00020\u0005*\u00020\u00002\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u00042\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\u0004\b\"\u0010#\u001a'\u0010\"\u001a\u00020\u0005*\u00020\u00002\u0006\u0010$\u001a\u00020\u001f2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\u0004\b\"\u0010%\u001a[\u0010*\u001a\u00020\u0005\"\u0004\b\u0000\u0010\t\"\u0004\b\u0001\u0010\n*\u00020\u00002\b\b\u0002\u0010\f\u001a\u00020\u000b2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010&2\u001e\u0010)\u001a\u001a\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010(\u0012\u0004\u0012\u00020\u00050'\u00a2\u0006\u0004\b*\u0010+\u001a!\u0010-\u001a\u00020\u0005*\u00020\u00002\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010,\u001a\u00020\u0013\u00a2\u0006\u0004\b-\u0010.\u001a\u0019\u00100\u001a\u00020\u0005*\u00020\u00002\u0006\u0010/\u001a\u00020\u0019\u00a2\u0006\u0004\b0\u00101\u00a8\u00062"}, d2={"Lio/netty/buffer/ByteBuf;", "Lnet/minecraft/world/phys/AABB;", "readBox", "(Lio/netty/buffer/ByteBuf;)Lnet/minecraft/world/phys/AABB;", "Lkotlin/Function0;", "", "reader", "readConditional", "(Lio/netty/buffer/ByteBuf;Lkotlin/jvm/functions/Function0;)V", "K", "V", "Lcom/cobblemon/mod/common/net/IntSize;", "size", "", "map", "Lkotlin/Pair;", "entryReader", "readMapK", "(Lio/netty/buffer/ByteBuf;Lcom/cobblemon/mod/common/net/IntSize;Ljava/util/Map;Lkotlin/jvm/functions/Function0;)V", "", "readSizedInt", "(Lio/netty/buffer/ByteBuf;Lcom/cobblemon/mod/common/net/IntSize;)I", "readEntry", "readTimes", "(Lio/netty/buffer/ByteBuf;Lcom/cobblemon/mod/common/net/IntSize;Lkotlin/jvm/functions/Function0;)V", "Lnet/minecraft/world/phys/Vec3;", "readVec3d", "(Lio/netty/buffer/ByteBuf;)Lnet/minecraft/world/phys/Vec3;", "box", "writeBox", "(Lio/netty/buffer/ByteBuf;Lnet/minecraft/world/phys/AABB;)V", "", "condition", "writer", "writeConditional", "(Lio/netty/buffer/ByteBuf;Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "shouldWrite", "(Lio/netty/buffer/ByteBuf;ZLkotlin/jvm/functions/Function0;)V", "", "Lkotlin/Function1;", "", "entryWriter", "writeMapK", "(Lio/netty/buffer/ByteBuf;Lcom/cobblemon/mod/common/net/IntSize;Ljava/util/Map;Lkotlin/jvm/functions/Function1;)V", "value", "writeSizedInt", "(Lio/netty/buffer/ByteBuf;Lcom/cobblemon/mod/common/net/IntSize;I)V", "vec3d", "writeVec3d", "(Lio/netty/buffer/ByteBuf;Lnet/minecraft/world/phys/Vec3;)V", "common"})
@SourceDebugExtension(value={"SMAP\nNetExtensions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 NetExtensions.kt\ncom/cobblemon/mod/common/util/NetExtensionsKt\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,87:1\n1#2:88\n1855#3,2:89\n*S KotlinDebug\n*F\n+ 1 NetExtensions.kt\ncom/cobblemon/mod/common/util/NetExtensionsKt\n*L\n70#1:89,2\n*E\n"})
public final class NetExtensionsKt {
    public static final void writeConditional(@NotNull ByteBuf $this$writeConditional, @NotNull Function0<Boolean> condition2, @NotNull Function0<Unit> writer) {
        Intrinsics.checkNotNullParameter((Object)$this$writeConditional, (String)"<this>");
        Intrinsics.checkNotNullParameter(condition2, (String)"condition");
        Intrinsics.checkNotNullParameter(writer, (String)"writer");
        NetExtensionsKt.writeConditional($this$writeConditional, (Boolean)condition2.invoke(), writer);
    }

    public static final void writeConditional(@NotNull ByteBuf $this$writeConditional, boolean shouldWrite, @NotNull Function0<Unit> writer) {
        Intrinsics.checkNotNullParameter((Object)$this$writeConditional, (String)"<this>");
        Intrinsics.checkNotNullParameter(writer, (String)"writer");
        $this$writeConditional.writeBoolean(shouldWrite);
        if (shouldWrite) {
            writer.invoke();
        }
    }

    public static final void writeSizedInt(@NotNull ByteBuf $this$writeSizedInt, @NotNull IntSize size, int value2) {
        Intrinsics.checkNotNullParameter((Object)$this$writeSizedInt, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)((Object)size), (String)"size");
        switch (WhenMappings.$EnumSwitchMapping$0[size.ordinal()]) {
            case 1: {
                $this$writeSizedInt.writeInt(value2);
                break;
            }
            case 2: 
            case 3: {
                $this$writeSizedInt.writeShort(value2);
                break;
            }
            case 4: 
            case 5: {
                $this$writeSizedInt.writeByte(value2);
            }
        }
    }

    public static final void readConditional(@NotNull ByteBuf $this$readConditional, @NotNull Function0<Unit> reader) {
        Intrinsics.checkNotNullParameter((Object)$this$readConditional, (String)"<this>");
        Intrinsics.checkNotNullParameter(reader, (String)"reader");
        boolean shouldRead = $this$readConditional.readBoolean();
        if (shouldRead) {
            reader.invoke();
        }
    }

    public static final int readSizedInt(@NotNull ByteBuf $this$readSizedInt, @NotNull IntSize size) {
        Intrinsics.checkNotNullParameter((Object)$this$readSizedInt, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)((Object)size), (String)"size");
        return switch (WhenMappings.$EnumSwitchMapping$0[size.ordinal()]) {
            case 1 -> $this$readSizedInt.readInt();
            case 2 -> $this$readSizedInt.readShort();
            case 3 -> $this$readSizedInt.readUnsignedShort();
            case 4 -> $this$readSizedInt.readByte();
            case 5 -> $this$readSizedInt.readUnsignedByte();
            default -> throw new NoWhenBranchMatchedException();
        };
    }

    public static final void readTimes(@NotNull ByteBuf $this$readTimes, @NotNull IntSize size, @NotNull Function0<Unit> readEntry) {
        Intrinsics.checkNotNullParameter((Object)$this$readTimes, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)((Object)size), (String)"size");
        Intrinsics.checkNotNullParameter(readEntry, (String)"readEntry");
        int times2 = NetExtensionsKt.readSizedInt($this$readTimes, size);
        int n = 0;
        while (n < times2) {
            int it = n++;
            boolean bl = false;
            readEntry.invoke();
        }
    }

    public static /* synthetic */ void readTimes$default(ByteBuf byteBuf, IntSize intSize, Function0 function0, int n, Object object) {
        if ((n & 1) != 0) {
            intSize = IntSize.U_BYTE;
        }
        NetExtensionsKt.readTimes(byteBuf, intSize, (Function0<Unit>)function0);
    }

    public static final void writeBox(@NotNull ByteBuf $this$writeBox, @NotNull AABB box) {
        Intrinsics.checkNotNullParameter((Object)$this$writeBox, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)box, (String)"box");
        $this$writeBox.writeDouble(box.f_82288_);
        $this$writeBox.writeDouble(box.f_82289_);
        $this$writeBox.writeDouble(box.f_82290_);
        $this$writeBox.writeDouble(box.f_82291_);
        $this$writeBox.writeDouble(box.f_82292_);
        $this$writeBox.writeDouble(box.f_82293_);
    }

    @NotNull
    public static final AABB readBox(@NotNull ByteBuf $this$readBox) {
        Intrinsics.checkNotNullParameter((Object)$this$readBox, (String)"<this>");
        return new AABB($this$readBox.readDouble(), $this$readBox.readDouble(), $this$readBox.readDouble(), $this$readBox.readDouble(), $this$readBox.readDouble(), $this$readBox.readDouble());
    }

    public static final <K, V> void writeMapK(@NotNull ByteBuf $this$writeMapK, @NotNull IntSize size, @NotNull Map<K, ? extends V> map, @NotNull Function1<? super Map.Entry<? extends K, ? extends V>, Unit> entryWriter) {
        Intrinsics.checkNotNullParameter((Object)$this$writeMapK, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)((Object)size), (String)"size");
        Intrinsics.checkNotNullParameter(map, (String)"map");
        Intrinsics.checkNotNullParameter(entryWriter, (String)"entryWriter");
        NetExtensionsKt.writeSizedInt($this$writeMapK, size, map.size());
        Iterable $this$forEach$iv = map.entrySet();
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            entryWriter.invoke(element$iv);
        }
    }

    public static /* synthetic */ void writeMapK$default(ByteBuf byteBuf, IntSize intSize, Map map, Function1 function1, int n, Object object) {
        if ((n & 1) != 0) {
            intSize = IntSize.U_BYTE;
        }
        NetExtensionsKt.writeMapK(byteBuf, intSize, map, function1);
    }

    public static final <K, V> void readMapK(@NotNull ByteBuf $this$readMapK, @NotNull IntSize size, @NotNull Map<K, V> map, @NotNull Function0<? extends Pair<? extends K, ? extends V>> entryReader) {
        Intrinsics.checkNotNullParameter((Object)$this$readMapK, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)((Object)size), (String)"size");
        Intrinsics.checkNotNullParameter(map, (String)"map");
        Intrinsics.checkNotNullParameter(entryReader, (String)"entryReader");
        int times2 = NetExtensionsKt.readSizedInt($this$readMapK, size);
        int n = 0;
        while (n < times2) {
            int it = n++;
            boolean bl = false;
            Pair pair = (Pair)entryReader.invoke();
            Object key = pair.component1();
            Object value2 = pair.component2();
            map.put(key, value2);
        }
    }

    public static /* synthetic */ void readMapK$default(ByteBuf byteBuf, IntSize intSize, Map map, Function0 function0, int n, Object object) {
        if ((n & 1) != 0) {
            intSize = IntSize.U_BYTE;
        }
        NetExtensionsKt.readMapK(byteBuf, intSize, map, function0);
    }

    public static final void writeVec3d(@NotNull ByteBuf $this$writeVec3d, @NotNull Vec3 vec3d) {
        Intrinsics.checkNotNullParameter((Object)$this$writeVec3d, (String)"<this>");
        Intrinsics.checkNotNullParameter((Object)vec3d, (String)"vec3d");
        $this$writeVec3d.writeDouble(vec3d.f_82479_);
        $this$writeVec3d.writeDouble(vec3d.f_82480_);
        $this$writeVec3d.writeDouble(vec3d.f_82481_);
    }

    @NotNull
    public static final Vec3 readVec3d(@NotNull ByteBuf $this$readVec3d) {
        Intrinsics.checkNotNullParameter((Object)$this$readVec3d, (String)"<this>");
        return new Vec3($this$readVec3d.readDouble(), $this$readVec3d.readDouble(), $this$readVec3d.readDouble());
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public final class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] nArray = new int[IntSize.values().length];
            try {
                nArray[IntSize.INT.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IntSize.SHORT.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IntSize.U_SHORT.ordinal()] = 3;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IntSize.BYTE.ordinal()] = 4;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            try {
                nArray[IntSize.U_BYTE.ordinal()] = 5;
            }
            catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
            $EnumSwitchMapping$0 = nArray;
        }
    }
}

