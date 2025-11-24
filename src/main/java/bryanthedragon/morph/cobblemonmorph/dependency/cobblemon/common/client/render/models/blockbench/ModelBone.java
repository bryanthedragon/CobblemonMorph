/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.Cube;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.models.blockbench.LocatorBone;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0007\n\u0002\b\t\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b#\u0010$R*\u0010\u0004\u001a\n\u0012\u0004\u0012\u00020\u0003\u0018\u00010\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR0\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f\u0018\u00010\n8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\"\u0010\u0013\u001a\u00020\u000b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R$\u0010\u0019\u001a\u0004\u0018\u00010\u000b8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0019\u0010\u0014\u001a\u0004\b\u001a\u0010\u0016\"\u0004\b\u001b\u0010\u0018R(\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001c0\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001d\u0010\u0005\u001a\u0004\b\u001e\u0010\u0007\"\u0004\b\u001f\u0010\tR*\u0010 \u001a\n\u0012\u0004\u0012\u00020\u001c\u0018\u00010\u00028\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b \u0010\u0005\u001a\u0004\b!\u0010\u0007\"\u0004\b\"\u0010\t\u00a8\u0006%"}, d2={"Lcom/cobblemon/mod/common/client/render/models/blockbench/ModelBone;", "", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/Cube;", "cubes", "Ljava/util/List;", "getCubes", "()Ljava/util/List;", "setCubes", "(Ljava/util/List;)V", "", "", "Lcom/cobblemon/mod/common/client/render/models/blockbench/LocatorBone;", "locators", "Ljava/util/Map;", "getLocators", "()Ljava/util/Map;", "setLocators", "(Ljava/util/Map;)V", "name", "Ljava/lang/String;", "getName", "()Ljava/lang/String;", "setName", "(Ljava/lang/String;)V", "parent", "getParent", "setParent", "", "pivot", "getPivot", "setPivot", "rotation", "getRotation", "setRotation", "<init>", "()V", "common"})
public final class ModelBone {
    @NotNull
    private String name = "";
    @Nullable
    private String parent;
    @NotNull
    private List<Float> pivot = CollectionsKt.emptyList();
    @Nullable
    private List<Float> rotation;
    @Nullable
    private List<Cube> cubes;
    @Nullable
    private Map<String, LocatorBone> locators;

    @NotNull
    public final String getName() {
        return this.name;
    }

    public final void setName(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.name = string;
    }

    @Nullable
    public final String getParent() {
        return this.parent;
    }

    public final void setParent(@Nullable String string) {
        this.parent = string;
    }

    @NotNull
    public final List<Float> getPivot() {
        return this.pivot;
    }

    public final void setPivot(@NotNull List<Float> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.pivot = list;
    }

    @Nullable
    public final List<Float> getRotation() {
        return this.rotation;
    }

    public final void setRotation(@Nullable List<Float> list) {
        this.rotation = list;
    }

    @Nullable
    public final List<Cube> getCubes() {
        return this.cubes;
    }

    public final void setCubes(@Nullable List<Cube> list) {
        this.cubes = list;
    }

    @Nullable
    public final Map<String, LocatorBone> getLocators() {
        return this.locators;
    }

    public final void setLocators(@Nullable Map<String, LocatorBone> map) {
        this.locators = map;
    }
}

