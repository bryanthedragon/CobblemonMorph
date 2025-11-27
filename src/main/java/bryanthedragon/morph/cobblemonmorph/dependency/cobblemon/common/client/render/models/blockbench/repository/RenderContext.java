/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.world.entity.Entity
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.repository;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import com.google.gson.reflect.TypeToken;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010%\n\u0002\b\u0007\u0018\u0000 \u00132\u00020\u0001:\u0003\u0013\u0014\u0015B\u0007\u00a2\u0006\u0004\b\u0012\u0010\u0004J\r\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0003\u0010\u0004J%\u0010\u0003\u001a\u00020\u0002\"\b\b\u0000\u0010\u0005*\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u00a2\u0006\u0004\b\u0003\u0010\bJ/\u0010\n\u001a\u00020\u0002\"\b\b\u0000\u0010\u0005*\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u00062\b\u0010\t\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0004\b\n\u0010\u000bJ'\u0010\f\u001a\u0004\u0018\u00018\u0000\"\b\b\u0000\u0010\u0005*\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u00a2\u0006\u0004\b\f\u0010\rJ%\u0010\u000e\u001a\u00028\u0000\"\b\b\u0000\u0010\u0005*\u00020\u00012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u00a2\u0006\u0004\b\u000e\u0010\rR&\u0010\u0010\u001a\u0014\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00010\u000f8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0010\u0010\u0011\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext;", "", "", "pop", "()V", "T", "Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$Key;", "key", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$Key;)V", "value", "put", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$Key;Ljava/lang/Object;)V", "request", "(Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$Key;)Ljava/lang/Object;", "requires", "", "context", "Ljava/util/Map;", "<init>", "Companion", "Key", "RenderState", "common"})
@SourceDebugExtension(value={"SMAP\nRenderContext.kt\nKotlin\n*S Kotlin\n*F\n+ 1 RenderContext.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext\n+ 2 RenderContext.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$Companion\n*L\n1#1,159:1\n155#2:160\n155#2:161\n155#2:162\n155#2:163\n155#2:164\n155#2:165\n*S KotlinDebug\n*F\n+ 1 RenderContext.kt\ncom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext\n*L\n108#1:160\n113#1:161\n118#1:162\n123#1:163\n128#1:164\n133#1:165\n*E\n"})
public final class RenderContext {
    @NotNull
    public static final Companion Companion;
    @NotNull
    private final Map<Key<?>, Object> context = new LinkedHashMap();
    @NotNull
    private static final Key<Entity> ENTITY;
    @NotNull
    private static final Key<ResourceLocation> TEXTURE;
    @NotNull
    private static final Key<Float> SCALE;
    @NotNull
    private static final Key<ResourceLocation> SPECIES;
    @NotNull
    private static final Key<Set<String>> ASPECTS;
    @NotNull
    private static final Key<RenderState> RENDER_STATE;

    @Nullable
    public final <T> T request(@NotNull Key<T> key) {
        Intrinsics.checkNotNullParameter(key, (String)"key");
        return (T)this.context.get(key);
    }

    @NotNull
    public final <T> T requires(@NotNull Key<T> key) {
        Intrinsics.checkNotNullParameter(key, (String)"key");
        T t = this.request(key);
        if (t == null) {
            throw new NullPointerException("Required value not found in context for key: " + key);
        }
        return t;
    }

    public final <T> void put(@NotNull Key<T> key, @Nullable T value2) {
        Intrinsics.checkNotNullParameter(key, (String)"key");
        this.context.put(key, value2);
    }

    public final void pop() {
        this.context.clear();
    }

    public final <T> void pop(@NotNull Key<T> key) {
        Intrinsics.checkNotNullParameter(key, (String)"key");
        this.context.remove(key);
    }

    static {
        Companion this_$iv;
        Companion companion = Companion = new Companion(null);
        ResourceLocation id$iv = MiscUtils.asResource("entity");
        boolean $i$f$key = false;
        TypeToken typeToken = TypeToken.get(Entity.class);
        Intrinsics.checkNotNullExpressionValue((Object)typeToken, (String)"get(T::class.java)");
        ENTITY = this_$iv.key(id$iv, typeToken);
        this_$iv = Companion;
        id$iv = MiscUtils.asResource("texture");
        $i$f$key = false;
        TypeToken typeToken2 = TypeToken.get(ResourceLocation.class);
        Intrinsics.checkNotNullExpressionValue((Object)typeToken2, (String)"get(T::class.java)");
        TEXTURE = this_$iv.key(id$iv, typeToken2);
        this_$iv = Companion;
        id$iv = MiscUtils.asResource("scale");
        $i$f$key = false;
        TypeToken typeToken3 = TypeToken.get(Float.class);
        Intrinsics.checkNotNullExpressionValue((Object)typeToken3, (String)"get(T::class.java)");
        SCALE = this_$iv.key(id$iv, typeToken3);
        this_$iv = Companion;
        id$iv = MiscUtils.asResource("species");
        $i$f$key = false;
        TypeToken typeToken4 = TypeToken.get(ResourceLocation.class);
        Intrinsics.checkNotNullExpressionValue((Object)typeToken4, (String)"get(T::class.java)");
        SPECIES = this_$iv.key(id$iv, typeToken4);
        this_$iv = Companion;
        id$iv = MiscUtils.asResource("species");
        $i$f$key = false;
        TypeToken typeToken5 = TypeToken.get(Set.class);
        Intrinsics.checkNotNullExpressionValue((Object)typeToken5, (String)"get(T::class.java)");
        ASPECTS = this_$iv.key(id$iv, typeToken5);
        this_$iv = Companion;
        id$iv = MiscUtils.asResource("state");
        $i$f$key = false;
        TypeToken typeToken6 = TypeToken.get(RenderState.class);
        Intrinsics.checkNotNullExpressionValue((Object)typeToken6, (String)"get(T::class.java)");
        RENDER_STATE = this_$iv.key(id$iv, typeToken6);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\t\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u001e\u0010\u001fJ*\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0003H\u0086\b\u00a2\u0006\u0004\b\u0006\u0010\u0007J3\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005\"\b\b\u0000\u0010\u0002*\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\b\u00a2\u0006\u0004\b\u0006\u0010\nR#\u0010\r\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b0\u00058\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010R\u001d\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00110\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u000e\u001a\u0004\b\u0013\u0010\u0010R\u001d\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00140\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0015\u0010\u000e\u001a\u0004\b\u0016\u0010\u0010R\u001d\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00170\u00058\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u000e\u001a\u0004\b\u0019\u0010\u0010R\u001d\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00030\u00058\u0006\u00a2\u0006\f\n\u0004\b\u001a\u0010\u000e\u001a\u0004\b\u001b\u0010\u0010R\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00030\u00058\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u000e\u001a\u0004\b\u001d\u0010\u0010\u00a8\u0006 "}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$Companion;", "", "T", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$Key;", "key", "(Lnet/minecraft/resources/ResourceLocation;)Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$Key;", "Lcom/google/gson/reflect/TypeToken;", "token", "(Lnet/minecraft/resources/ResourceLocation;Lcom/google/gson/reflect/TypeToken;)Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$Key;", "", "", "ASPECTS", "Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$Key;", "getASPECTS", "()Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$Key;", "Lnet/minecraft/world/entity/Entity;", "ENTITY", "getENTITY", "Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$RenderState;", "RENDER_STATE", "getRENDER_STATE", "", "SCALE", "getSCALE", "SPECIES", "getSPECIES", "TEXTURE", "getTEXTURE", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Key<Entity> getENTITY() {
            return ENTITY;
        }

        @NotNull
        public final Key<ResourceLocation> getTEXTURE() {
            return TEXTURE;
        }

        @NotNull
        public final Key<Float> getSCALE() {
            return SCALE;
        }

        @NotNull
        public final Key<ResourceLocation> getSPECIES() {
            return SPECIES;
        }

        @NotNull
        public final Key<Set<String>> getASPECTS() {
            return ASPECTS;
        }

        @NotNull
        public final Key<RenderState> getRENDER_STATE() {
            return RENDER_STATE;
        }

        @NotNull
        public final <T> Key<T> key(@NotNull ResourceLocation id, @NotNull TypeToken<T> token) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            Intrinsics.checkNotNullParameter(token, (String)"token");
            return new Key<T>(id, token);
        }

        public final /* synthetic */ <T> Key<T> key(ResourceLocation id) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            boolean $i$f$key = false;
            Intrinsics.reifiedOperationMarker((int)4, (String)"T");
            TypeToken typeToken = TypeToken.get(Object.class);
            Intrinsics.checkNotNullExpressionValue((Object)typeToken, (String)"get(T::class.java)");
            return this.key(id, typeToken);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0001B\u001d\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0010\u0010\u0004\u001a\u00020\u0003H\u00c6\u0003\u00a2\u0006\u0004\b\u0004\u0010\u0005J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006H\u00c6\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ0\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\t\u001a\u00020\u00032\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006H\u00c6\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001a\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0015\u001a\u00020\u0014H\u00d6\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u0017\u0010\t\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0017\u001a\u0004\b\u0018\u0010\u0005R\u001d\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u00068\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u0019\u001a\u0004\b\u001a\u0010\b\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$Key;", "", "T", "Lnet/minecraft/resources/ResourceLocation;", "component1", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/google/gson/reflect/TypeToken;", "component2", "()Lcom/google/gson/reflect/TypeToken;", "key", "token", "copy", "(Lnet/minecraft/resources/ResourceLocation;Lcom/google/gson/reflect/TypeToken;)Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$Key;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lnet/minecraft/resources/ResourceLocation;", "getKey", "Lcom/google/gson/reflect/TypeToken;", "getToken", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Lcom/google/gson/reflect/TypeToken;)V", "common"})
    public static final class Key<T> {
        @NotNull
        private final ResourceLocation key;
        @NotNull
        private final TypeToken<T> token;

        public Key(@NotNull ResourceLocation key, @NotNull TypeToken<T> token) {
            Intrinsics.checkNotNullParameter((Object)key, (String)"key");
            Intrinsics.checkNotNullParameter(token, (String)"token");
            this.key = key;
            this.token = token;
        }

        @NotNull
        public final ResourceLocation getKey() {
            return this.key;
        }

        @NotNull
        public final TypeToken<T> getToken() {
            return this.token;
        }

        @NotNull
        public final ResourceLocation component1() {
            return this.key;
        }

        @NotNull
        public final TypeToken<T> component2() {
            return this.token;
        }

        @NotNull
        public final Key<T> copy(@NotNull ResourceLocation key, @NotNull TypeToken<T> token) {
            Intrinsics.checkNotNullParameter((Object)key, (String)"key");
            Intrinsics.checkNotNullParameter(token, (String)"token");
            return new Key<T>(key, token);
        }

        public static /* synthetic */ Key copy$default(Key key, ResourceLocation resourceLocation, TypeToken typeToken, int n, Object object) {
            if ((n & 1) != 0) {
                resourceLocation = key.key;
            }
            if ((n & 2) != 0) {
                typeToken = key.token;
            }
            return key.copy(resourceLocation, typeToken);
        }

        @NotNull
        public String toString() {
            return "Key(key=" + this.key + ", token=" + this.token + ")";
        }

        public int hashCode() {
            int result = this.key.hashCode();
            result = result * 31 + this.token.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Key)) {
                return false;
            }
            Key key = (Key)other;
            if (!Intrinsics.areEqual((Object)this.key, (Object)key.key)) {
                return false;
            }
            return Intrinsics.areEqual(this.token, key.token);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\u0010\u000b\n\u0002\b\t\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0003\u0010\u0005j\u0002\b\bj\u0002\b\tj\u0002\b\n\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/repository/RenderContext$RenderState;", "", "", "isGuiBased", "Z", "()Z", "<init>", "(Ljava/lang/String;IZ)V", "WORLD", "PORTRAIT", "PROFILE", "common"})
    public static final class RenderState
    extends Enum<RenderState> {
        private final boolean isGuiBased;
        public static final /* enum */ RenderState WORLD = new RenderState(false);
        public static final /* enum */ RenderState PORTRAIT = new RenderState(true);
        public static final /* enum */ RenderState PROFILE = new RenderState(true);
        private static final /* synthetic */ RenderState[] $VALUES;

        private RenderState(boolean isGuiBased) {
            this.isGuiBased = isGuiBased;
        }

        public final boolean isGuiBased() {
            return this.isGuiBased;
        }

        public static RenderState[] values() {
            return (RenderState[])$VALUES.clone();
        }

        public static RenderState valueOf(String value2) {
            return Enum.valueOf(RenderState.class, value2);
        }

        static {
            $VALUES = renderStateArray = new RenderState[]{RenderState.WORLD, RenderState.PORTRAIT, RenderState.PROFILE};
        }
    }
}

