/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  kotlin.Lazy
 *  kotlin.LazyKt
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.JvmClassMappingKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.internal.CollectionToArray
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.markers.KMutableSet
 *  kotlin.reflect.KClass
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.collections;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.PokemonSpecies;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.CollectionToArray;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMutableSet;
import kotlin.reflect.KClass;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010#\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010)\n\u0002\b\t\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\b\u0012\u0004\u0012\u00028\u00000\u0003B\u001d\u0012\f\u0010'\u001a\b\u0012\u0004\u0012\u00028\u00000&\u0012\u0006\u0010*\u001a\u00020)\u00a2\u0006\u0004\b+\u0010,J\u0017\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001d\u0010\n\u001a\u00020\u00052\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016\u00a2\u0006\u0004\b\n\u0010\u000bJ\u000f\u0010\r\u001a\u00020\fH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0018\u0010\u000f\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00028\u0000H\u0096\u0002\u00a2\u0006\u0004\b\u000f\u0010\u0007J\u001d\u0010\u0010\u001a\u00020\u00052\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u000bJ\u000f\u0010\u0011\u001a\u00020\u0005H\u0016\u00a2\u0006\u0004\b\u0011\u0010\u0012J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u0013H\u0096\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015J\u0017\u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00028\u0000H\u0016\u00a2\u0006\u0004\b\u0016\u0010\u0007J\u001d\u0010\u0017\u001a\u00020\u00052\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016\u00a2\u0006\u0004\b\u0017\u0010\u000bJ\u001d\u0010\u0018\u001a\u00020\u00052\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\bH\u0016\u00a2\u0006\u0004\b\u0018\u0010\u000bR!\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\u00038BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\"\u0010 \u001a\u0010\u0012\f\u0012\n \u001f*\u0004\u0018\u00010\u001e0\u001e0\u001d8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b \u0010!R\u0014\u0010%\u001a\u00020\"8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b#\u0010$R\u001a\u0010'\u001a\b\u0012\u0004\u0012\u00028\u00000&8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b'\u0010(\u00a8\u0006-"}, d2={"Lcom/cobblemon/mod/common/util/collections/LazySet;", "", "T", "", "element", "", "add", "(Ljava/lang/Object;)Z", "", "elements", "addAll", "(Ljava/util/Collection;)Z", "", "clear", "()V", "contains", "containsAll", "isEmpty", "()Z", "", "iterator", "()Ljava/util/Iterator;", "remove", "removeAll", "retainAll", "elements$delegate", "Lkotlin/Lazy;", "getElements", "()Ljava/util/Set;", "", "Lcom/google/gson/JsonElement;", "kotlin.jvm.PlatformType", "json", "Ljava/util/Set;", "", "getSize", "()I", "size", "Lkotlin/reflect/KClass;", "type", "Lkotlin/reflect/KClass;", "Lcom/google/gson/JsonArray;", "values", "<init>", "(Lkotlin/reflect/KClass;Lcom/google/gson/JsonArray;)V", "common"})
public final class LazySet<T>
implements Set<T>,
KMutableSet {
    @NotNull
    private final KClass<T> type;
    @NotNull
    private final Set<JsonElement> json;
    @NotNull
    private final Lazy elements$delegate;

    public LazySet(@NotNull KClass<T> type, @NotNull JsonArray values) {
        Intrinsics.checkNotNullParameter(type, (String)"type");
        Intrinsics.checkNotNullParameter((Object)values, (String)"values");
        this.type = type;
        this.json = CollectionsKt.toSet((Iterable)((Iterable)values));
        this.elements$delegate = LazyKt.lazy((Function0)new Function0<Set<T>>(this){
            final /* synthetic */ LazySet<T> this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            @NotNull
            public final Set<T> invoke() {
                void $this$mapTo$iv$iv;
                void $this$map$iv;
                Iterable iterable = LazySet.access$getJson$p(this.this$0);
                LazySet<T> lazySet = this.this$0;
                boolean $i$f$map = false;
                void var4_4 = $this$map$iv;
                Collection destination$iv$iv = new ArrayList<E>(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
                boolean $i$f$mapTo = false;
                for (T item$iv$iv : $this$mapTo$iv$iv) {
                    void jsonElement;
                    JsonElement jsonElement2 = (JsonElement)item$iv$iv;
                    Collection collection = destination$iv$iv;
                    boolean bl = false;
                    collection.add(PokemonSpecies.INSTANCE.getGson().fromJson((JsonElement)jsonElement, JvmClassMappingKt.getJavaClass((KClass)LazySet.access$getType$p(lazySet))));
                }
                return CollectionsKt.toMutableSet((Iterable)((List)destination$iv$iv));
            }
        });
    }

    private final Set<T> getElements() {
        Lazy lazy = this.elements$delegate;
        return (Set)lazy.getValue();
    }

    @Override
    public boolean add(@NotNull T element) {
        Intrinsics.checkNotNullParameter(element, (String)"element");
        return this.getElements().add(element);
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> elements2) {
        Intrinsics.checkNotNullParameter(elements2, (String)"elements");
        return this.getElements().addAll(elements2);
    }

    @Override
    public void clear() {
        this.getElements().clear();
    }

    @Override
    @NotNull
    public Iterator<T> iterator() {
        return this.getElements().iterator();
    }

    @Override
    public boolean remove(@Nullable Object element) {
        if (element == null) {
            return false;
        }
        return this.getElements().remove(element);
    }

    @Override
    public boolean removeAll(@NotNull Collection<? extends Object> elements2) {
        Intrinsics.checkNotNullParameter(elements2, (String)"elements");
        return this.getElements().removeAll(CollectionsKt.toSet((Iterable)elements2));
    }

    @Override
    public boolean retainAll(@NotNull Collection<? extends Object> elements2) {
        Intrinsics.checkNotNullParameter(elements2, (String)"elements");
        return this.getElements().retainAll(CollectionsKt.toSet((Iterable)elements2));
    }

    public int getSize() {
        return this.getElements().size();
    }

    @Override
    public boolean contains(@Nullable Object element) {
        if (element == null) {
            return false;
        }
        return this.getElements().contains(element);
    }

    @Override
    public boolean containsAll(@NotNull Collection<? extends Object> elements2) {
        Intrinsics.checkNotNullParameter(elements2, (String)"elements");
        return this.getElements().containsAll(elements2);
    }

    @Override
    public boolean isEmpty() {
        return this.getElements().isEmpty();
    }

    public static final /* synthetic */ Set access$getJson$p(LazySet $this) {
        return $this.json;
    }

    public static final /* synthetic */ KClass access$getType$p(LazySet $this) {
        return $this.type;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        Intrinsics.checkNotNullParameter(array, (String)"array");
        return CollectionToArray.toArray((Collection)this, (Object[])array);
    }

    @Override
    public Object[] toArray() {
        return CollectionToArray.toArray((Collection)this);
    }
}

