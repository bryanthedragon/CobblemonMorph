/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialoguePage;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueSpeaker;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ExpressionLikeDialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.FunctionDialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.molang.ExpressionLike;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 \u00162\u00020\u0001:\u0001\u0016B7\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u0012\u0014\b\u0002\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u001d\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006\u00a2\u0006\f\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\fR#\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000f0\r8\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\u00a8\u0006\u0017"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/Dialogue;", "", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "escapeAction", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "getEscapeAction", "()Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "", "Lcom/cobblemon/mod/common/api/dialogue/DialoguePage;", "pages", "Ljava/util/List;", "getPages", "()Ljava/util/List;", "", "", "Lcom/cobblemon/mod/common/api/dialogue/DialogueSpeaker;", "speakers", "Ljava/util/Map;", "getSpeakers", "()Ljava/util/Map;", "<init>", "(Ljava/util/List;Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;Ljava/util/Map;)V", "Companion", "common"})
public final class Dialogue {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final List<DialoguePage> pages;
    @NotNull
    private final DialogueAction escapeAction;
    @NotNull
    private final Map<String, DialogueSpeaker> speakers;

    public Dialogue(@NotNull List<DialoguePage> pages, @NotNull DialogueAction escapeAction, @NotNull Map<String, DialogueSpeaker> speakers) {
        Intrinsics.checkNotNullParameter(pages, (String)"pages");
        Intrinsics.checkNotNullParameter((Object)escapeAction, (String)"escapeAction");
        Intrinsics.checkNotNullParameter(speakers, (String)"speakers");
        this.pages = pages;
        this.escapeAction = escapeAction;
        this.speakers = speakers;
    }

    public /* synthetic */ Dialogue(List list, DialogueAction dialogueAction, Map map, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            list = new ArrayList();
        }
        if ((n & 2) != 0) {
            dialogueAction = new FunctionDialogueAction((Function2<? super ActiveDialogue, ? super String, Unit>)((Function2)1.INSTANCE));
        }
        if ((n & 4) != 0) {
            map = MapsKt.emptyMap();
        }
        this(list, dialogueAction, map);
    }

    @NotNull
    public final List<DialoguePage> getPages() {
        return this.pages;
    }

    @NotNull
    public final DialogueAction getEscapeAction() {
        return this.escapeAction;
    }

    @NotNull
    public final Map<String, DialogueSpeaker> getSpeakers() {
        return this.speakers;
    }

    public Dialogue() {
        this(null, null, null, 7, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013JC\u0010\u000e\u001a\u00020\r2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00070\u00052\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t\u00a2\u0006\u0004\b\u000e\u0010\u000fJ7\u0010\u000e\u001a\u00020\r2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\u0006\u0010\b\u001a\u00020\u00102\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t\u00a2\u0006\u0004\b\u000e\u0010\u0011\u00a8\u0006\u0014"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/Dialogue$Companion;", "", "", "Lcom/cobblemon/mod/common/api/dialogue/DialoguePage;", "pages", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "", "escapeAction", "", "", "Lcom/cobblemon/mod/common/api/dialogue/DialogueSpeaker;", "speakers", "Lcom/cobblemon/mod/common/api/dialogue/Dialogue;", "of", "(Ljava/lang/Iterable;Lkotlin/jvm/functions/Function1;Ljava/util/Map;)Lcom/cobblemon/mod/common/api/dialogue/Dialogue;", "Lcom/cobblemon/mod/common/api/molang/ExpressionLike;", "(Ljava/lang/Iterable;Lcom/cobblemon/mod/common/api/molang/ExpressionLike;Ljava/util/Map;)Lcom/cobblemon/mod/common/api/dialogue/Dialogue;", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Dialogue of(@NotNull Iterable<DialoguePage> pages, @NotNull ExpressionLike escapeAction, @NotNull Map<String, DialogueSpeaker> speakers) {
            Intrinsics.checkNotNullParameter(pages, (String)"pages");
            Intrinsics.checkNotNullParameter((Object)escapeAction, (String)"escapeAction");
            Intrinsics.checkNotNullParameter(speakers, (String)"speakers");
            return new Dialogue(CollectionsKt.toList(pages), new ExpressionLikeDialogueAction(escapeAction), speakers);
        }

        @NotNull
        public final Dialogue of(@NotNull Iterable<DialoguePage> pages, @NotNull Function1<? super ActiveDialogue, Unit> escapeAction, @NotNull Map<String, DialogueSpeaker> speakers) {
            Intrinsics.checkNotNullParameter(pages, (String)"pages");
            Intrinsics.checkNotNullParameter(escapeAction, (String)"escapeAction");
            Intrinsics.checkNotNullParameter(speakers, (String)"speakers");
            Dialogue dialogue2 = new Dialogue(CollectionsKt.toList(pages), new FunctionDialogueAction((Function2<? super ActiveDialogue, ? super String, Unit>)((Function2)new Function2<ActiveDialogue, String, Unit>(escapeAction){
                final /* synthetic */ Function1<ActiveDialogue, Unit> $escapeAction;
                {
                    this.$escapeAction = $escapeAction;
                    super(2);
                }

                public final void invoke(@NotNull ActiveDialogue activeDialogue, @Nullable String string) {
                    Intrinsics.checkNotNullParameter((Object)activeDialogue, (String)"activeDialogue");
                    this.$escapeAction.invoke((Object)activeDialogue);
                }
            })), speakers);
            return dialogue2;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

