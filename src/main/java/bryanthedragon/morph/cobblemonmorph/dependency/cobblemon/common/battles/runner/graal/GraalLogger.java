/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.runner.graal;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u0004J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0005\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0004J\u0019\u0010\b\u001a\u00020\u00022\b\u0010\u0007\u001a\u0004\u0018\u00010\u0006H\u0016\u00a2\u0006\u0004\b\b\u0010\t\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/battles/runner/graal/GraalLogger;", "Ljava/util/logging/Handler;", "", "close", "()V", "flush", "Ljava/util/logging/LogRecord;", "record", "publish", "(Ljava/util/logging/LogRecord;)V", "<init>", "common"})
public final class GraalLogger
extends Handler {
    @NotNull
    public static final GraalLogger INSTANCE = new GraalLogger();

    private GraalLogger() {
    }

    @Override
    public void publish(@Nullable LogRecord record) {
        if (record == null) {
            return;
        }
        Level level = record.getLevel();
        if (Intrinsics.areEqual((Object)level, (Object)Level.INFO)) {
            Cobblemon.INSTANCE.getLOGGER().info(record.getMessage());
        } else if (Intrinsics.areEqual((Object)level, (Object)Level.WARNING)) {
            Cobblemon.INSTANCE.getLOGGER().warn(record.getMessage());
        } else if (Intrinsics.areEqual((Object)level, (Object)Level.SEVERE)) {
            Cobblemon.INSTANCE.getLOGGER().error(record.getMessage());
        } else {
            Cobblemon.INSTANCE.getLOGGER().debug(record.getMessage());
        }
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() {
    }
}

