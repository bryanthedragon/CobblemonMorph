/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.JsonDeserializationContext
 *  com.google.gson.JsonDeserializer
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.reflect.TypeToken
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.Reflection
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.TypeIntrinsics
 *  kotlin.reflect.KCallable
 *  kotlin.reflect.KClass
 *  kotlin.reflect.KMutableProperty
 *  kotlin.reflect.KProperty1
 *  kotlin.reflect.KType
 *  kotlin.reflect.TypesJVMKt
 *  kotlin.reflect.full.KClasses
 *  kotlin.reflect.jvm.KCallablesJvm
 *  net.minecraft.resources.ResourceLocation
 *  net.minecraft.server.level.ServerPlayer
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.resources.ResourceManager
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.JsonDataRegistry;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.Species;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.ResourceLocationExtensionsKt;
import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.reflect.KCallable;
import kotlin.reflect.KClass;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KProperty1;
import kotlin.reflect.KType;
import kotlin.reflect.TypesJVMKt;
import kotlin.reflect.full.KClasses;
import kotlin.reflect.jvm.KCallablesJvm;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\b\u00c0\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0003,-.B\t\b\u0002\u00a2\u0006\u0004\b*\u0010+J#\u0010\u0007\u001a\u00020\u00062\u0012\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00020\u0003H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0017\u0010\u000b\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\tH\u0016\u00a2\u0006\u0004\b\u000b\u0010\fR\u001a\u0010\u000e\u001a\u00020\r8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u00048\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0014\u0010\u0015R \u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00000\u00168\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001c\u001a\u00020\u001b8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b\u001c\u0010\u001d\u001a\u0004\b\u001e\u0010\u001fR\u001a\u0010!\u001a\u00020 8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$R \u0010&\u001a\b\u0012\u0004\u0012\u00020\u00020%8\u0016X\u0096\u0004\u00a2\u0006\f\n\u0004\b&\u0010'\u001a\u0004\b(\u0010)\u00a8\u0006/"}, d2={"Lcom/cobblemon/mod/common/pokemon/SpeciesAdditions;", "Lcom/cobblemon/mod/common/api/data/JsonDataRegistry;", "Lcom/cobblemon/mod/common/pokemon/SpeciesAdditions$AdditionParameter;", "", "Lnet/minecraft/resources/ResourceLocation;", "data", "", "reload", "(Ljava/util/Map;)V", "Lnet/minecraft/server/level/ServerPlayer;", "player", "sync", "(Lnet/minecraft/server/level/ServerPlayer;)V", "Lcom/google/gson/Gson;", "gson", "Lcom/google/gson/Gson;", "getGson", "()Lcom/google/gson/Gson;", "id", "Lnet/minecraft/resources/ResourceLocation;", "getId", "()Lnet/minecraft/resources/ResourceLocation;", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "", "resourcePath", "Ljava/lang/String;", "getResourcePath", "()Ljava/lang/String;", "Lnet/minecraft/server/packs/PackType;", "type", "Lnet/minecraft/server/packs/PackType;", "getType", "()Lnet/minecraft/server/packs/PackType;", "Lcom/google/gson/reflect/TypeToken;", "typeToken", "Lcom/google/gson/reflect/TypeToken;", "getTypeToken", "()Lcom/google/gson/reflect/TypeToken;", "<init>", "()V", "Addition", "AdditionParameter", "AdditionParameterAdapter", "common"})
@SourceDebugExtension(value={"SMAP\nSpeciesAdditions.kt\nKotlin\n*S Kotlin\n*F\n+ 1 SpeciesAdditions.kt\ncom/cobblemon/mod/common/pokemon/SpeciesAdditions\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,113:1\n1855#2,2:114\n*S KotlinDebug\n*F\n+ 1 SpeciesAdditions.kt\ncom/cobblemon/mod/common/pokemon/SpeciesAdditions\n*L\n44#1:114,2\n*E\n"})
public final class SpeciesAdditions
implements JsonDataRegistry<AdditionParameter> {
    @NotNull
    public static final SpeciesAdditions INSTANCE = new SpeciesAdditions();
    @NotNull
    private static final ResourceLocation id = MiscUtilsKt.cobblemonResource("species_additions");
    @NotNull
    private static final PackType type = PackType.SERVER_DATA;
    @NotNull
    private static final SimpleObservable<SpeciesAdditions> observable = new SimpleObservable();
    @NotNull
    private static final Gson gson;
    @NotNull
    private static final TypeToken<AdditionParameter> typeToken;
    @NotNull
    private static final String resourcePath;

    private SpeciesAdditions() {
    }

    @Override
    @NotNull
    public ResourceLocation getId() {
        return id;
    }

    @Override
    @NotNull
    public PackType getType() {
        return type;
    }

    @NotNull
    public SimpleObservable<SpeciesAdditions> getObservable() {
        return observable;
    }

    @Override
    @NotNull
    public Gson getGson() {
        return gson;
    }

    @Override
    @NotNull
    public TypeToken<AdditionParameter> getTypeToken() {
        return typeToken;
    }

    @Override
    @NotNull
    public String getResourcePath() {
        return resourcePath;
    }

    @Override
    public void reload(@NotNull Map<ResourceLocation, AdditionParameter> data) {
        Intrinsics.checkNotNullParameter(data, (String)"data");
        for (Map.Entry<ResourceLocation, AdditionParameter> entry : data.entrySet()) {
            ResourceLocation identifier = entry.getKey();
            AdditionParameter parameter = entry.getValue();
            Species species = PokemonSpecies.INSTANCE.getByIdentifier(parameter.getTargetIdentifier());
            if (species == null) {
                Cobblemon.INSTANCE.getLOGGER().warn("Cannot find species {} for addition {}, skipping", (Object)parameter.getTargetIdentifier().toString(), (Object)identifier.toString());
                continue;
            }
            Iterable $this$forEach$iv = parameter.getAdditions();
            boolean $i$f$forEach = false;
            for (Object element$iv : $this$forEach$iv) {
                Addition addition = (Addition)element$iv;
                boolean bl = false;
                try {
                    Object value2 = addition.getValue();
                    if (TypeIntrinsics.isMutableCollection((Object)value2)) {
                        var14_15 = new Object[]{species};
                        Object object = addition.getProperty().getGetter().call(var14_15);
                        Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type kotlin.collections.MutableCollection<kotlin.Any>");
                        existing = TypeIntrinsics.asMutableCollection((Object)object);
                        existing.addAll(CollectionsKt.filterNotNull((Iterable)((Iterable)value2)));
                        value2 = existing;
                    } else if (TypeIntrinsics.isMutableMap((Object)value2)) {
                        var14_15 = new Object[]{species};
                        Object object = addition.getProperty().getGetter().call(var14_15);
                        Intrinsics.checkNotNull((Object)object, (String)"null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.Any, kotlin.Any>");
                        existing = TypeIntrinsics.asMutableMap((Object)object);
                        Object object2 = value2;
                        Intrinsics.checkNotNull((Object)object2, (String)"null cannot be cast to non-null type kotlin.collections.MutableMap<kotlin.Any, kotlin.Any>");
                        existing.putAll(TypeIntrinsics.asMutableMap((Object)object2));
                        value2 = existing;
                    }
                    Object[] objectArray = new Object[]{species, value2};
                    addition.getProperty().getSetter().call(objectArray);
                }
                catch (Exception e) {
                    Cobblemon.INSTANCE.getLOGGER().error("Caught exception applying addition {} to {}", (Object)identifier.toString(), (Object)parameter.getTargetIdentifier().toString(), (Object)e);
                }
            }
        }
        Cobblemon.INSTANCE.getLOGGER().info("Finished additions");
        SpeciesAdditions[] speciesAdditionsArray = new SpeciesAdditions[]{this};
        this.getObservable().emit((SpeciesAdditions[])speciesAdditionsArray);
    }

    @Override
    public void sync(@NotNull ServerPlayer player) {
        Intrinsics.checkNotNullParameter((Object)player, (String)"player");
    }

    @Override
    public void reload(@NotNull ResourceManager manager) {
        JsonDataRegistry.DefaultImpls.reload(this, manager);
    }

    static {
        Gson gson2 = PokemonSpecies.INSTANCE.getGson().newBuilder().registerTypeAdapter((Type)((Object)AdditionParameter.class), (Object)AdditionParameterAdapter.INSTANCE).create();
        Intrinsics.checkNotNullExpressionValue((Object)gson2, (String)"PokemonSpecies.gson.newB\u2026arameterAdapter).create()");
        gson = gson2;
        TypeToken typeToken = TypeToken.get(AdditionParameter.class);
        Intrinsics.checkNotNullExpressionValue((Object)typeToken, (String)"get(AdditionParameter::class.java)");
        SpeciesAdditions.typeToken = typeToken;
        String string = INSTANCE.getId().m_135815_();
        Intrinsics.checkNotNullExpressionValue((Object)string, (String)"this.id.path");
        resourcePath = string;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u0002\u0012\u0006\u0010\b\u001a\u00020\u0001\u00a2\u0006\u0004\b\u0019\u0010\u001aJ\u0014\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u0001H\u00c6\u0003\u00a2\u0006\u0004\b\u0005\u0010\u0006J(\u0010\t\u001a\u00020\u00002\f\b\u0002\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00022\b\b\u0002\u0010\b\u001a\u00020\u0001H\u00c6\u0001\u00a2\u0006\u0004\b\t\u0010\nJ\u001a\u0010\r\u001a\u00020\f2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0010\u0010\u0010\u001a\u00020\u000fH\u00d6\u0001\u00a2\u0006\u0004\b\u0010\u0010\u0011J\u0010\u0010\u0013\u001a\u00020\u0012H\u00d6\u0001\u00a2\u0006\u0004\b\u0013\u0010\u0014R\u001b\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0015\u001a\u0004\b\u0016\u0010\u0004R\u0017\u0010\b\u001a\u00020\u00018\u0006\u00a2\u0006\f\n\u0004\b\b\u0010\u0017\u001a\u0004\b\u0018\u0010\u0006\u00a8\u0006\u001b"}, d2={"Lcom/cobblemon/mod/common/pokemon/SpeciesAdditions$Addition;", "", "Lkotlin/reflect/KMutableProperty;", "component1", "()Lkotlin/reflect/KMutableProperty;", "component2", "()Ljava/lang/Object;", "property", "value", "copy", "(Lkotlin/reflect/KMutableProperty;Ljava/lang/Object;)Lcom/cobblemon/mod/common/pokemon/SpeciesAdditions$Addition;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Lkotlin/reflect/KMutableProperty;", "getProperty", "Ljava/lang/Object;", "getValue", "<init>", "(Lkotlin/reflect/KMutableProperty;Ljava/lang/Object;)V", "common"})
    public static final class Addition {
        @NotNull
        private final KMutableProperty<?> property;
        @NotNull
        private final Object value;

        public Addition(@NotNull KMutableProperty<?> property, @NotNull Object value2) {
            Intrinsics.checkNotNullParameter(property, (String)"property");
            Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
            this.property = property;
            this.value = value2;
        }

        @NotNull
        public final KMutableProperty<?> getProperty() {
            return this.property;
        }

        @NotNull
        public final Object getValue() {
            return this.value;
        }

        @NotNull
        public final KMutableProperty<?> component1() {
            return this.property;
        }

        @NotNull
        public final Object component2() {
            return this.value;
        }

        @NotNull
        public final Addition copy(@NotNull KMutableProperty<?> property, @NotNull Object value2) {
            Intrinsics.checkNotNullParameter(property, (String)"property");
            Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
            return new Addition(property, value2);
        }

        public static /* synthetic */ Addition copy$default(Addition addition, KMutableProperty kMutableProperty, Object object, int n, Object object2) {
            if ((n & 1) != 0) {
                kMutableProperty = addition.property;
            }
            if ((n & 2) != 0) {
                object = addition.value;
            }
            return addition.copy(kMutableProperty, object);
        }

        @NotNull
        public String toString() {
            return "Addition(property=" + this.property + ", value=" + this.value + ")";
        }

        public int hashCode() {
            int result = this.property.hashCode();
            result = result * 31 + this.value.hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Addition)) {
                return false;
            }
            Addition addition = (Addition)other;
            if (!Intrinsics.areEqual(this.property, addition.property)) {
                return false;
            }
            return Intrinsics.areEqual((Object)this.value, (Object)addition.value);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\t\u001a\u00020\u0002\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\u0004\b\u001b\u0010\u001cJ\u0010\u0010\u0003\u001a\u00020\u0002H\u00c6\u0003\u00a2\u0006\u0004\b\u0003\u0010\u0004J\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003\u00a2\u0006\u0004\b\u0007\u0010\bJ*\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\t\u001a\u00020\u00022\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0001\u00a2\u0006\u0004\b\u000b\u0010\fJ\u001a\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003\u00a2\u0006\u0004\b\u000f\u0010\u0010J\u0010\u0010\u0012\u001a\u00020\u0011H\u00d6\u0001\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0010\u0010\u0015\u001a\u00020\u0014H\u00d6\u0001\u00a2\u0006\u0004\b\u0015\u0010\u0016R\u001d\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u0017\u001a\u0004\b\u0018\u0010\bR\u0017\u0010\t\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\u0019\u001a\u0004\b\u001a\u0010\u0004\u00a8\u0006\u001d"}, d2={"Lcom/cobblemon/mod/common/pokemon/SpeciesAdditions$AdditionParameter;", "", "Lnet/minecraft/resources/ResourceLocation;", "component1", "()Lnet/minecraft/resources/ResourceLocation;", "", "Lcom/cobblemon/mod/common/pokemon/SpeciesAdditions$Addition;", "component2", "()Ljava/util/Collection;", "targetIdentifier", "additions", "copy", "(Lnet/minecraft/resources/ResourceLocation;Ljava/util/Collection;)Lcom/cobblemon/mod/common/pokemon/SpeciesAdditions$AdditionParameter;", "other", "", "equals", "(Ljava/lang/Object;)Z", "", "hashCode", "()I", "", "toString", "()Ljava/lang/String;", "Ljava/util/Collection;", "getAdditions", "Lnet/minecraft/resources/ResourceLocation;", "getTargetIdentifier", "<init>", "(Lnet/minecraft/resources/ResourceLocation;Ljava/util/Collection;)V", "common"})
    public static final class AdditionParameter {
        @NotNull
        private final ResourceLocation targetIdentifier;
        @NotNull
        private final Collection<Addition> additions;

        public AdditionParameter(@NotNull ResourceLocation targetIdentifier, @NotNull Collection<Addition> additions) {
            Intrinsics.checkNotNullParameter((Object)targetIdentifier, (String)"targetIdentifier");
            Intrinsics.checkNotNullParameter(additions, (String)"additions");
            this.targetIdentifier = targetIdentifier;
            this.additions = additions;
        }

        @NotNull
        public final ResourceLocation getTargetIdentifier() {
            return this.targetIdentifier;
        }

        @NotNull
        public final Collection<Addition> getAdditions() {
            return this.additions;
        }

        @NotNull
        public final ResourceLocation component1() {
            return this.targetIdentifier;
        }

        @NotNull
        public final Collection<Addition> component2() {
            return this.additions;
        }

        @NotNull
        public final AdditionParameter copy(@NotNull ResourceLocation targetIdentifier, @NotNull Collection<Addition> additions) {
            Intrinsics.checkNotNullParameter((Object)targetIdentifier, (String)"targetIdentifier");
            Intrinsics.checkNotNullParameter(additions, (String)"additions");
            return new AdditionParameter(targetIdentifier, additions);
        }

        public static /* synthetic */ AdditionParameter copy$default(AdditionParameter additionParameter, ResourceLocation resourceLocation, Collection collection, int n, Object object) {
            if ((n & 1) != 0) {
                resourceLocation = additionParameter.targetIdentifier;
            }
            if ((n & 2) != 0) {
                collection = additionParameter.additions;
            }
            return additionParameter.copy(resourceLocation, collection);
        }

        @NotNull
        public String toString() {
            return "AdditionParameter(targetIdentifier=" + this.targetIdentifier + ", additions=" + this.additions + ")";
        }

        public int hashCode() {
            int result = this.targetIdentifier.hashCode();
            result = result * 31 + ((Object)this.additions).hashCode();
            return result;
        }

        public boolean equals(@Nullable Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof AdditionParameter)) {
                return false;
            }
            AdditionParameter additionParameter = (AdditionParameter)other;
            if (!Intrinsics.areEqual((Object)this.targetIdentifier, (Object)additionParameter.targetIdentifier)) {
                return false;
            }
            return Intrinsics.areEqual(this.additions, additionParameter.additions);
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u00c6\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014J'\u0010\t\u001a\u00020\u00022\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u00052\u0006\u0010\b\u001a\u00020\u0007H\u0016\u00a2\u0006\u0004\b\t\u0010\nR\u0014\u0010\f\u001a\u00020\u000b8\u0002X\u0082T\u00a2\u0006\u0006\n\u0004\b\f\u0010\rR8\u0010\u0011\u001a&\u0012\u0004\u0012\u00020\u000b\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000f0\u000ej\u0012\u0012\u0004\u0012\u00020\u000b\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u000f`\u00108\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/pokemon/SpeciesAdditions$AdditionParameterAdapter;", "Lcom/google/gson/JsonDeserializer;", "Lcom/cobblemon/mod/common/pokemon/SpeciesAdditions$AdditionParameter;", "Lcom/google/gson/JsonElement;", "element", "Ljava/lang/reflect/Type;", "type", "Lcom/google/gson/JsonDeserializationContext;", "context", "deserialize", "(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lcom/cobblemon/mod/common/pokemon/SpeciesAdditions$AdditionParameter;", "", "TARGET", "Ljava/lang/String;", "Ljava/util/HashMap;", "Lkotlin/reflect/KMutableProperty;", "Lkotlin/collections/HashMap;", "properties", "Ljava/util/HashMap;", "<init>", "()V", "common"})
    public static final class AdditionParameterAdapter
    implements JsonDeserializer<AdditionParameter> {
        @NotNull
        public static final AdditionParameterAdapter INSTANCE = new AdditionParameterAdapter();
        @NotNull
        private static final String TARGET = "target";
        @NotNull
        private static final HashMap<String, KMutableProperty<?>> properties = new HashMap();

        private AdditionParameterAdapter() {
        }

        @NotNull
        public AdditionParameter deserialize(@NotNull JsonElement element, @NotNull Type type, @NotNull JsonDeserializationContext context) {
            Intrinsics.checkNotNullParameter((Object)element, (String)"element");
            Intrinsics.checkNotNullParameter((Object)type, (String)"type");
            Intrinsics.checkNotNullParameter((Object)context, (String)"context");
            JsonObject jObject = element.getAsJsonObject();
            String string = jObject.get(TARGET).getAsString();
            Intrinsics.checkNotNullExpressionValue((Object)string, (String)"jObject.get(TARGET).asString");
            ResourceLocation target = ResourceLocationExtensionsKt.asIdentifierDefaultingNamespace$default(string, null, 1, null);
            ArrayList additions = new ArrayList();
            for (Map.Entry entry : jObject.entrySet()) {
                KMutableProperty<?> property;
                Intrinsics.checkNotNullExpressionValue((Object)entry, (String)"jObject.entrySet()");
                String key = (String)entry.getKey();
                JsonElement jElement = (JsonElement)entry.getValue();
                if (Intrinsics.areEqual((Object)key, (Object)TARGET) || !properties.containsKey(key)) continue;
                Intrinsics.checkNotNull(properties.get(key));
                Object value2 = context.deserialize(jElement, TypesJVMKt.getJavaType((KType)property.getReturnType()));
                Collection collection = additions;
                Intrinsics.checkNotNullExpressionValue((Object)value2, (String)"value");
                collection.add(new Addition(property, value2));
            }
            return new AdditionParameter(target, additions);
        }

        static {
            for (KProperty1 property : KClasses.getDeclaredMemberProperties((KClass)Reflection.getOrCreateKotlinClass(Species.class))) {
                if (property.isLateinit() || !(property instanceof KMutableProperty)) continue;
                if (!KCallablesJvm.isAccessible((KCallable)((KCallable)property))) {
                    KCallablesJvm.setAccessible((KCallable)((KCallable)property), (boolean)true);
                }
                ((Map)properties).put(property.getName(), property);
            }
        }
    }
}

