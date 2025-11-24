/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.collections.MapsKt
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ExpressionLikeDialogueText;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\bf\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007J\u0018\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u00a6\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006\u00a8\u0006\b"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/DialogueText;", "", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "activeDialogue", "Lnet/minecraft/network/chat/MutableComponent;", "invoke", "(Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;)Lnet/minecraft/network/chat/MutableComponent;", "Companion", "common"})
public interface DialogueText {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueText$Companion.$$INSTANCE;

    @NotNull
    public MutableComponent invoke(@NotNull ActiveDialogue var1);

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR+\u0010\u0006\u001a\u0016\u0012\u0004\u0012\u00020\u0003\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00040\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/DialogueText$Companion;", "", "", "", "Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueText;", "types", "Ljava/util/Map;", "getTypes", "()Ljava/util/Map;", "<init>", "()V", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final Map<String, Class<? extends DialogueText>> types;

        private Companion() {
        }

        @NotNull
        public final Map<String, Class<? extends DialogueText>> getTypes() {
            return types;
        }

        static {
            $$INSTANCE = new Companion();
            Pair[] pairArray = new Pair[]{TuplesKt.to((Object)"expression", ExpressionLikeDialogueText.class)};
            types = MapsKt.mutableMapOf((Pair[])pairArray);
        }
    }
}

