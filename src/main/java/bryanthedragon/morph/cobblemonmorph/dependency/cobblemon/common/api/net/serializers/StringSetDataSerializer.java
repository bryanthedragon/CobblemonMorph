/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.FriendlyByteBuf
 *  net.minecraft.network.syncher.EntityDataSerializer
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.net.serializers;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import io.netty.buffer.ByteBuf;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u000e\u0010\u000fJ#\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001d\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\nJ%\u0010\f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\u00072\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002H\u0016\u00a2\u0006\u0004\b\f\u0010\r\u00a8\u0006\u0010"}, d2={"Lcom/cobblemon/mod/common/api/net/serializers/StringSetDataSerializer;", "Lnet/minecraft/network/syncher/EntityDataSerializer;", "", "", "set", "copy", "(Ljava/util/Set;)Ljava/util/Set;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "read", "(Lnet/minecraft/network/FriendlyByteBuf;)Ljava/util/Set;", "", "write", "(Lnet/minecraft/network/FriendlyByteBuf;Ljava/util/Set;)V", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nStringSetDataSerializer.kt\nKotlin\n*S Kotlin\n*F\n+ 1 StringSetDataSerializer.kt\ncom/cobblemon/mod/common/api/net/serializers/StringSetDataSerializer\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,33:1\n1855#2,2:34\n*S KotlinDebug\n*F\n+ 1 StringSetDataSerializer.kt\ncom/cobblemon/mod/common/api/net/serializers/StringSetDataSerializer\n*L\n20#1:34,2\n*E\n"})
public final class StringSetDataSerializer
implements EntityDataSerializer<Set<? extends String>> {
    @NotNull
    public static final StringSetDataSerializer INSTANCE = new StringSetDataSerializer();

    private StringSetDataSerializer() {
    }

    public void write(@NotNull FriendlyByteBuf buffer, @NotNull Set<String> set2) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Intrinsics.checkNotNullParameter(set2, (String)"set");
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, set2.size());
        Iterable $this$forEach$iv = set2;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            String p0 = (String)element$iv;
            boolean bl = false;
            buffer.m_130070_(p0);
        }
    }

    @NotNull
    public Set<String> read(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        Set set2 = new LinkedHashSet();
        int n = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
        int n2 = 0;
        while (n2 < n) {
            int it = n2++;
            boolean bl = false;
            String string = buffer.m_130277_();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"buffer.readString()");
            set2.add(string);
        }
        return set2;
    }

    @NotNull
    public Set<String> copy(@NotNull Set<String> set2) {
        Intrinsics.checkNotNullParameter(set2, (String)"set");
        return CollectionsKt.toSet((Iterable)set2);
    }
}

