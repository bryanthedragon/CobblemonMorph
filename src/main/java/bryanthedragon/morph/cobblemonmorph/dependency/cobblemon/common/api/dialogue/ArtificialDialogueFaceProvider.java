/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.collections.SetsKt
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtilsKt;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\u0018\u00002\u00020\u0001B+\u0012\b\b\u0002\u0010\r\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002\u00a2\u0006\u0004\b\u0011\u0010\u0012R\u001d\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0004\u0010\u0005\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\t\u001a\u00020\b8\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\r\u001a\u00020\u00038\u0006\u00a2\u0006\f\n\u0004\b\r\u0010\u000e\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0013"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/ArtificialDialogueFaceProvider;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider;", "", "", "aspects", "Ljava/util/Set;", "getAspects", "()Ljava/util/Set;", "Lnet/minecraft/resources/ResourceLocation;", "identifier", "Lnet/minecraft/resources/ResourceLocation;", "getIdentifier", "()Lnet/minecraft/resources/ResourceLocation;", "modelType", "Ljava/lang/String;", "getModelType", "()Ljava/lang/String;", "<init>", "(Ljava/lang/String;Lnet/minecraft/resources/ResourceLocation;Ljava/util/Set;)V", "common"})
public final class ArtificialDialogueFaceProvider
implements DialogueFaceProvider {
    @NotNull
    private final String modelType;
    @NotNull
    private final ResourceLocation identifier;
    @NotNull
    private final Set<String> aspects;

    public ArtificialDialogueFaceProvider(@NotNull String modelType, @NotNull ResourceLocation identifier, @NotNull Set<String> aspects) {
        Intrinsics.checkNotNullParameter((Object)modelType, (String)"modelType");
        Intrinsics.checkNotNullParameter((Object)identifier, (String)"identifier");
        Intrinsics.checkNotNullParameter(aspects, (String)"aspects");
        this.modelType = modelType;
        this.identifier = identifier;
        this.aspects = aspects;
    }

    public /* synthetic */ ArtificialDialogueFaceProvider(String string, ResourceLocation resourceLocation, Set set2, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            string = "";
        }
        if ((n & 2) != 0) {
            resourceLocation = MiscUtilsKt.cobblemonResource("bulbasaur");
        }
        if ((n & 4) != 0) {
            set2 = SetsKt.emptySet();
        }
        this(string, resourceLocation, set2);
    }

    @NotNull
    public final String getModelType() {
        return this.modelType;
    }

    @NotNull
    public final ResourceLocation getIdentifier() {
        return this.identifier;
    }

    @NotNull
    public final Set<String> getAspects() {
        return this.aspects;
    }

    public ArtificialDialogueFaceProvider() {
        this(null, null, null, 7, null);
    }
}

