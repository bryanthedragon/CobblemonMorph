/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.server.level.ServerLevel
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.world.entity.LivingEntity
 *  net.minecraft.world.phys.Vec3
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0007\bf\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017J3\u0010\u000b\u001a\u00020\n2\b\u0010\u0003\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00062\b\u0010\t\u001a\u0004\u0018\u00010\bH&\u00a2\u0006\u0004\b\u000b\u0010\fR\u0014\u0010\u0010\u001a\u00020\r8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0014\u001a\u00020\u00118&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u0014\u0010\u0016\u001a\u00020\r8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u000f\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/api/drop/DropEntry;", "", "Lnet/minecraft/world/entity/LivingEntity;", "entity", "Lnet/minecraft/server/level/ServerLevel;", "world", "Lnet/minecraft/world/phys/Vec3;", "pos", "Lnet/minecraft/server/level/ServerPlayer;", "player", "", "drop", "(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/server/level/ServerPlayer;)V", "", "getMaxSelectableTimes", "()I", "maxSelectableTimes", "", "getPercentage", "()F", "percentage", "getQuantity", "quantity", "Companion", "common"})
public interface DropEntry {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.drop.DropEntry$Companion.$$INSTANCE;

    public float getPercentage();

    public int getQuantity();

    public int getMaxSelectableTimes();

    public void drop(@Nullable LivingEntity var1, @NotNull ServerLevel var2, @NotNull Vec3 var3, @Nullable ServerPlayer var4);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0010%\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u001f\u0010\u0006\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0005\u0018\u00010\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J7\u0010\r\u001a\u00020\f\"\b\b\u0000\u0010\b*\u00020\u00052\u0006\u0010\u0003\u001a\u00020\u00022\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u00042\b\b\u0002\u0010\u000b\u001a\u00020\n\u00a2\u0006\u0004\b\r\u0010\u000eR,\u0010\u000f\u001a\f\u0012\u0006\b\u0001\u0012\u00020\u0005\u0018\u00010\u00048\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u000f\u0010\u0010\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R+\u0010\u0016\u001a\u0016\u0012\u0004\u0012\u00020\u0002\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00040\u00158\u0006\u00a2\u0006\f\n\u0004\b\u0016\u0010\u0017\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001c"}, d2={"Lcom/cobblemon/mod/common/api/drop/DropEntry$Companion;", "", "", "name", "Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/drop/DropEntry;", "getByName", "(Ljava/lang/String;)Ljava/lang/Class;", "T", "clazz", "", "isDefault", "", "register", "(Ljava/lang/String;Ljava/lang/Class;Z)V", "defaultType", "Ljava/lang/Class;", "getDefaultType", "()Ljava/lang/Class;", "setDefaultType", "(Ljava/lang/Class;)V", "", "entryTypes", "Ljava/util/Map;", "getEntryTypes", "()Ljava/util/Map;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final Map<String, Class<? extends DropEntry>> entryTypes;
        @Nullable
        private static Class<? extends DropEntry> defaultType;

        private Companion() {
        }

        @NotNull
        public final Map<String, Class<? extends DropEntry>> getEntryTypes() {
            return entryTypes;
        }

        @Nullable
        public final Class<? extends DropEntry> getDefaultType() {
            return defaultType;
        }

        public final void setDefaultType(@Nullable Class<? extends DropEntry> clazz) {
            defaultType = clazz;
        }

        @Nullable
        public final Class<? extends DropEntry> getByName(@NotNull String name) {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            return entryTypes.get(name);
        }

        public final <T extends DropEntry> void register(@NotNull String name, @NotNull Class<T> clazz, boolean isDefault) {
            Intrinsics.checkNotNullParameter((Object)name, (String)"name");
            Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
            entryTypes.put(name, clazz);
            if (isDefault) {
                defaultType = clazz;
            }
        }

        public static /* synthetic */ void register$default(Companion companion, String string, Class clazz, boolean bl, int n, Object object) {
            if ((n & 4) != 0) {
                bl = false;
            }
            companion.register(string, clazz, bl);
        }

        static {
            $$INSTANCE = new Companion();
            entryTypes = new LinkedHashMap();
        }
    }
}

