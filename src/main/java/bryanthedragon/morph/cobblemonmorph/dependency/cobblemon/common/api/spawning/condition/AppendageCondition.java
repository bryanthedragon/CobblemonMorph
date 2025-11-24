/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.SpawningCondition;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.context.SpawningContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\bf\u0018\u0000 \u00072\u00020\u0001:\u0002\u0007\bJ\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H&\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\t"}, d2={"Lcom/cobblemon/mod/common/api/spawning/condition/AppendageCondition;", "", "Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;", "ctx", "", "fits", "(Lcom/cobblemon/mod/common/api/spawning/context/SpawningContext;)Z", "Companion", "RegisteredAppendageCondition", "common"})
public interface AppendageCondition {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.spawning.condition.AppendageCondition$Companion.$$INSTANCE;

    public boolean fits(@NotNull SpawningContext var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0016\u0010\u0017J'\u0010\u0007\u001a\u0010\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00050\u00042\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0002\u00a2\u0006\u0004\b\u0007\u0010\bJ5\u0010\u000e\u001a\u00020\r2\u000e\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u00052\u0016\u0010\f\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0002\u0012\u0004\u0012\u00020\u000b0\n\u00a2\u0006\u0004\b\u000e\u0010\u000fJ1\u0010\u000e\u001a\u00020\r2\u0012\u0010\u0010\u001a\u000e\u0012\n\b\u0001\u0012\u0006\u0012\u0002\b\u00030\u00020\u00052\u000e\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u0005\u00a2\u0006\u0004\b\u000e\u0010\u0011R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00130\u00128\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0014\u0010\u0015\u00a8\u0006\u0018"}, d2={"Lcom/cobblemon/mod/common/api/spawning/condition/AppendageCondition$Companion;", "", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;", "spawningCondition", "", "Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/spawning/condition/AppendageCondition;", "getAppendages", "(Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;)Ljava/util/List;", "appendageClass", "Lkotlin/Function1;", "", "spawningConditionFits", "", "registerAppendage", "(Ljava/lang/Class;Lkotlin/jvm/functions/Function1;)V", "conditionClass", "(Ljava/lang/Class;Ljava/lang/Class;)V", "", "Lcom/cobblemon/mod/common/api/spawning/condition/AppendageCondition$RegisteredAppendageCondition;", "appendages", "Ljava/util/List;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nAppendageCondition.kt\nKotlin\n*S Kotlin\n*F\n+ 1 AppendageCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/AppendageCondition$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,59:1\n766#2:60\n857#2,2:61\n1549#2:63\n1620#2,3:64\n*S KotlinDebug\n*F\n+ 1 AppendageCondition.kt\ncom/cobblemon/mod/common/api/spawning/condition/AppendageCondition$Companion\n*L\n56#1:60\n56#1:61,2\n56#1:63\n56#1:64,3\n*E\n"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final List<RegisteredAppendageCondition> appendages;

        private Companion() {
        }

        public final void registerAppendage(@NotNull Class<? extends SpawningCondition<?>> conditionClass, @NotNull Class<? extends AppendageCondition> appendageClass) {
            Intrinsics.checkNotNullParameter(conditionClass, (String)"conditionClass");
            Intrinsics.checkNotNullParameter(appendageClass, (String)"appendageClass");
            this.registerAppendage(appendageClass, (Function1)new Function1<Object, Boolean>(conditionClass){

                @NotNull
                public final Boolean invoke(Object p0) {
                    return ((Class)this.receiver).isInstance(p0);
                }
            });
        }

        public final void registerAppendage(@NotNull Class<? extends AppendageCondition> appendageClass, @NotNull Function1<? super SpawningCondition<?>, Boolean> spawningConditionFits) {
            Intrinsics.checkNotNullParameter(appendageClass, (String)"appendageClass");
            Intrinsics.checkNotNullParameter(spawningConditionFits, (String)"spawningConditionFits");
            appendages.add(new RegisteredAppendageCondition(appendageClass, spawningConditionFits));
        }

        /*
         * WARNING - void declaration
         */
        @NotNull
        public final List<Class<? extends AppendageCondition>> getAppendages(@NotNull SpawningCondition<?> spawningCondition) {
            void $this$mapTo$iv$iv;
            RegisteredAppendageCondition it;
            Iterable $this$filterTo$iv$iv;
            Intrinsics.checkNotNullParameter(spawningCondition, (String)"spawningCondition");
            Iterable $this$filter$iv = appendages;
            boolean $i$f$filter = false;
            Iterable iterable = $this$filter$iv;
            Collection destination$iv$iv = new ArrayList();
            boolean $i$f$filterTo = false;
            for (Object element$iv$iv : $this$filterTo$iv$iv) {
                it = (RegisteredAppendageCondition)element$iv$iv;
                boolean bl = false;
                if (!((Boolean)it.getSpawningConditionFits().invoke(spawningCondition)).booleanValue()) continue;
                destination$iv$iv.add(element$iv$iv);
            }
            Iterable $this$map$iv = (List)destination$iv$iv;
            boolean $i$f$map = false;
            $this$filterTo$iv$iv = $this$map$iv;
            destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                it = (RegisteredAppendageCondition)item$iv$iv;
                Collection collection = destination$iv$iv;
                boolean bl = false;
                collection.add(it.getClazz());
            }
            return (List)destination$iv$iv;
        }

        static {
            $$INSTANCE = new Companion();
            appendages = new ArrayList();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\b\u0002\u0018\u00002\u00020\u0001B/\u0012\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002\u0012\u0016\u0010\u000b\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t\u0012\u0004\u0012\u00020\n0\b\u00a2\u0006\u0004\b\u000f\u0010\u0010R\u001f\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R'\u0010\u000b\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t\u0012\u0004\u0012\u00020\n0\b8\u0006\u00a2\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/spawning/condition/AppendageCondition$RegisteredAppendageCondition;", "", "Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/spawning/condition/AppendageCondition;", "clazz", "Ljava/lang/Class;", "getClazz", "()Ljava/lang/Class;", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/api/spawning/condition/SpawningCondition;", "", "spawningConditionFits", "Lkotlin/jvm/functions/Function1;", "getSpawningConditionFits", "()Lkotlin/jvm/functions/Function1;", "<init>", "(Ljava/lang/Class;Lkotlin/jvm/functions/Function1;)V", "common"})
    private static final class RegisteredAppendageCondition {
        @NotNull
        private final Class<? extends AppendageCondition> clazz;
        @NotNull
        private final Function1<SpawningCondition<?>, Boolean> spawningConditionFits;

        public RegisteredAppendageCondition(@NotNull Class<? extends AppendageCondition> clazz, @NotNull Function1<? super SpawningCondition<?>, Boolean> spawningConditionFits) {
            Intrinsics.checkNotNullParameter(clazz, (String)"clazz");
            Intrinsics.checkNotNullParameter(spawningConditionFits, (String)"spawningConditionFits");
            this.clazz = clazz;
            this.spawningConditionFits = spawningConditionFits;
        }

        @NotNull
        public final Class<? extends AppendageCondition> getClazz() {
            return this.clazz;
        }

        @NotNull
        public final Function1<SpawningCondition<?>, Boolean> getSpawningConditionFits() {
            return this.spawningConditionFits;
        }
    }
}

