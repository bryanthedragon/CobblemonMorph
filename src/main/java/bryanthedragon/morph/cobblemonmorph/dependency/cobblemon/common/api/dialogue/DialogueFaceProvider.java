/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.MapsKt
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ArtificialDialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ExpressionLikeDialogueFaceProvider;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.PlayerDialogueFaceProvider;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import org.jetbrains.annotations.NotNull;

/*
 * Uses 'sealed' constructs - enablewith --sealed true
 */
@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u0000 \u00022\u00020\u0001:\u0001\u0002\u0082\u0001\u0004\u0003\u0004\u0005\u0006\u00a8\u0006\u0007"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider;", "", "Companion", "Lcom/cobblemon/mod/common/api/dialogue/ArtificialDialogueFaceProvider;", "Lcom/cobblemon/mod/common/api/dialogue/ExpressionLikeDialogueFaceProvider;", "Lcom/cobblemon/mod/common/api/dialogue/PlayerDialogueFaceProvider;", "Lcom/cobblemon/mod/common/api/dialogue/ReferenceDialogueFaceProvider;", "common"})
public interface DialogueFaceProvider {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueFaceProvider$Companion.$$INSTANCE;

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR+\u0010\u0006\u001a\u0016\u0012\u0004\u0012\u00020\u0003\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00040\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider$Companion;", "", "", "", "Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueFaceProvider;", "types", "Ljava/util/Map;", "getTypes", "()Ljava/util/Map;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final Map<String, Class<? extends DialogueFaceProvider>> types;

        private Companion() {
        }

        @NotNull
        public final Map<String, Class<? extends DialogueFaceProvider>> getTypes() {
            return types;
        }

        static {
            $$INSTANCE = new Companion();
            Pair[] pairArray = new Pair[]{TuplesKt.to((Object)"player", PlayerDialogueFaceProvider.class), TuplesKt.to((Object)"standard", ArtificialDialogueFaceProvider.class), TuplesKt.to((Object)"expression", ExpressionLikeDialogueFaceProvider.class)};
            types = MapsKt.mutableMapOf((Pair[])pairArray);
        }
    }
}

