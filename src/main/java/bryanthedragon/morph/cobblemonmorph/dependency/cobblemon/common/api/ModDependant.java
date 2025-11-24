/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\t\bf\u0018\u00002\u00020\u0001J\u000f\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0003\u0010\u0004R\"\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058&@&X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\"\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\u00058&@&X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\f\u0010\b\"\u0004\b\r\u0010\n\u00a8\u0006\u000f"}, d2={"Lcom/cobblemon/mod/common/api/ModDependant;", "", "", "isModDependencySatisfied", "()Z", "", "", "getNeededInstalledMods", "()Ljava/util/List;", "setNeededInstalledMods", "(Ljava/util/List;)V", "neededInstalledMods", "getNeededUninstalledMods", "setNeededUninstalledMods", "neededUninstalledMods", "common"})
public interface ModDependant {
    @NotNull
    public List<String> getNeededInstalledMods();

    public void setNeededInstalledMods(@NotNull List<String> var1);

    @NotNull
    public List<String> getNeededUninstalledMods();

    public void setNeededUninstalledMods(@NotNull List<String> var1);

    public boolean isModDependencySatisfied();

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    @SourceDebugExtension(value={"SMAP\nModDependant.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ModDependant.kt\ncom/cobblemon/mod/common/api/ModDependant$DefaultImpls\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,32:1\n1747#2,3:33\n1747#2,3:36\n*S KotlinDebug\n*F\n+ 1 ModDependant.kt\ncom/cobblemon/mod/common/api/ModDependant$DefaultImpls\n*L\n24#1:33,3\n26#1:36,3\n*E\n"})
    public static final class DefaultImpls {
        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public static boolean isModDependencySatisfied(@NotNull ModDependant $this) {
            String it;
            boolean $i$f$any;
            Iterable $this$any$iv;
            if (!((Collection)$this.getNeededInstalledMods()).isEmpty()) {
                boolean bl;
                $this$any$iv = $this.getNeededInstalledMods();
                $i$f$any = false;
                if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                    bl = false;
                } else {
                    for (Object element$iv : $this$any$iv) {
                        it = (String)element$iv;
                        boolean bl2 = false;
                        if (!Cobblemon.INSTANCE.getImplementation().isModInstalled(it)) {
                            return false;
                        }
                        boolean bl3 = false;
                        if (!bl3) continue;
                        return false;
                    }
                    bl = false;
                }
                if (bl) {
                    return false;
                }
            }
            if (((Collection)$this.getNeededUninstalledMods()).isEmpty()) return true;
            boolean bl = true;
            if (!bl) return true;
            $this$any$iv = $this.getNeededUninstalledMods();
            $i$f$any = false;
            if ($this$any$iv instanceof Collection && ((Collection)$this$any$iv).isEmpty()) {
                return true;
            }
            Iterator iterator = $this$any$iv.iterator();
            do {
                Object element$iv;
                if (!iterator.hasNext()) return true;
                element$iv = iterator.next();
                it = (String)element$iv;
                boolean bl4 = false;
            } while (!Cobblemon.INSTANCE.getImplementation().isModInstalled(it));
            return false;
        }
    }
}

