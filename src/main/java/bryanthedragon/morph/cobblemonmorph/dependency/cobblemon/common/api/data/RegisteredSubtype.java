/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.Codec
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data;

import com.mojang.serialization.Codec;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002B'\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u0003\u0012\u000e\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\b\u00a2\u0006\u0004\b\r\u0010\u000eR\u001f\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\u00038\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u001f\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00028\u00000\b8\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/data/RegisteredSubtype;", "T", "", "Ljava/lang/Class;", "clazz", "Ljava/lang/Class;", "getClazz", "()Ljava/lang/Class;", "Lcom/mojang/serialization/Codec;", "codec", "Lcom/mojang/serialization/Codec;", "getCodec", "()Lcom/mojang/serialization/Codec;", "<init>", "(Ljava/lang/Class;Lcom/mojang/serialization/Codec;)V", "common"})
public final class RegisteredSubtype<T> {
    @NotNull
    private final Class<? extends T> clazz;
    @NotNull
    private final Codec<? extends T> codec;

    public RegisteredSubtype(@NotNull Class<? extends T> clazz, @NotNull Codec<? extends T> codec2) {
        Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
        Intrinsics.checkNotNullParameter(codec2, (String)"codec");
        this.clazz = clazz;
        this.codec = codec2;
    }

    @NotNull
    public final Class<? extends T> getClazz() {
        return this.clazz;
    }

    @NotNull
    public final Codec<? extends T> getCodec() {
        return this.codec;
    }
}

