/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.JvmStatic
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bf\u0018\u0000 \t2\u00020\u0001:\u0001\tJ$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0003\u001a\u00020\u00022\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u00a6\u0002\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\n"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "dialogue", "", "input", "", "invoke", "(Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;Ljava/lang/String;)V", "Companion", "common"})
public interface DialogueAction {
    @NotNull
    public static final Companion Companion = bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction$Companion.$$INSTANCE;

    public void invoke(@NotNull ActiveDialogue var1, @Nullable String var2);

    @NotNull
    public static Map<String, Class<? extends DialogueAction>> getTypes() {
        return Companion.getTypes();
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010%\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\u000bR4\u0010\u0006\u001a\u0016\u0012\u0004\u0012\u00020\u0003\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u00040\u00028\u0006X\u0087\u0004\u00a2\u0006\u0012\n\u0004\b\u0006\u0010\u0007\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/DialogueAction$Companion;", "", "", "", "Ljava/lang/Class;", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "types", "Ljava/util/Map;", "getTypes", "()Ljava/util/Map;", "getTypes$annotations", "()V", "<init>", "common"})
    public static final class Companion {
        static final /* synthetic */ Companion $$INSTANCE;
        @NotNull
        private static final Map<String, Class<? extends DialogueAction>> types;

        private Companion() {
        }

        @NotNull
        public final Map<String, Class<? extends DialogueAction>> getTypes() {
            return types;
        }

        @JvmStatic
        public static /* synthetic */ void getTypes$annotations() {
        }

        static {
            $$INSTANCE = new Companion();
            types = new LinkedHashMap();
        }
    }

    @Metadata(mv={1, 8, 0}, k=3, xi=48)
    public static final class DefaultImpls {
        public static /* synthetic */ void invoke$default(DialogueAction dialogueAction, ActiveDialogue activeDialogue, String string, int n, Object object) {
            if (object != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: invoke");
            }
            if ((n & 2) != 0) {
                string = null;
            }
            dialogueAction.invoke(activeDialogue, string);
        }
    }
}

