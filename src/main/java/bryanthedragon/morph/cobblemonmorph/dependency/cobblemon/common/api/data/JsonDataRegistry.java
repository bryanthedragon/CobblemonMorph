/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.DataRegistry;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u0000 \u0018*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001\u0018J#\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00028\u00000\u0003H&\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u0007\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u0007\u0010\u000bR\u0014\u0010\u000f\u001a\u00020\f8&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0013\u001a\u00020\u00108&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\u00148&X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u0019"}, d2={"Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "T", "Lcom/cobblemon/mod/common/api/data/DataRegistry;", "", "Lnet/minecraft/resources/ResourceLocation;", "data", "", "reload", "(Ljava/util/Map;)V", "Lnet/minecraft/server/packs/resources/ResourceManager;", "manager", "(Lnet/minecraft/server/packs/resources/ResourceManager;)V", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "gson", "", "getResourcePath", "()Ljava/lang/String;", "resourcePath", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "typeToken", "Companion", "common"})
public interface JsonDataRegistry<T>
extends DataRegistry {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry$Companion.$$INSTANCE;
    @NotNull
    public static final String JSON_EXTENSION = ".json";

    @NotNull
    public Gson getGson();

    @NotNull
    public TypeToken<T> getTypeToken();

    @NotNull
    public String getResourcePath();

    @Override
    public void reload(@NotNull ResourceManager var1);

    public void reload(@NotNull Map<ResourceLocation, ? extends T> var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0003\u001a\u00020\u00028\u0006X\u0086T\u00a2\u0006\u0006\n\u0004\b\u0003\u0010\u0004\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/api/data/JsonDataRegistry$Companion;", "", "", "JSON_EXTENSION", "Ljava/lang/String;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        public static final String JSON_EXTENSION = ".json";

        private Companion() {
        }

        static {
            $$INSTANCE = new Companion();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static <T> void reload(@NotNull JsonDataRegistry<T> $this, @NotNull ResourceManager manager) {
            Intrinsics.checkNotNullParameter((Object)manager, (String)"manager");
            $this.reload((Map)Cobblemon.INSTANCE.getImplementation().reloadJsonRegistry($this, manager));
        }
    }
}

