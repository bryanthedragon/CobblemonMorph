/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.QueryStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.Cobblemon;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.ActiveInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueOption;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueTimeout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\n\u0018\u00002\u00020\u0001B\t\b\u0016\u00a2\u0006\u0004\b'\u0010(B+\u0012\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u0012\u0012\n\b\u0002\u0010\u001a\u001a\u0004\u0018\u00010\u0019\u0012\b\b\u0002\u0010!\u001a\u00020 \u00a2\u0006\u0004\b'\u0010)J\u001b\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0006\u0010\u0007J\u001f\u0010\r\u001a\u00020\f2\u0006\u0010\t\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\nH\u0016\u00a2\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\t\u001a\u00020\bH\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011R(\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u00128\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R$\u0010\u001a\u001a\u0004\u0018\u00010\u00198\u0016@\u0016X\u0096\u000e\u00a2\u0006\u0012\n\u0004\b\u001a\u0010\u001b\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR\"\u0010!\u001a\u00020 8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b!\u0010\"\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&\u00a8\u0006*"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/input/DialogueOptionSetInput;", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "activeDialogue", "", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueOption;", "getVisibleOptions", "(Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;)Ljava/util/List;", "Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;", "activeInput", "", "value", "", "handle", "(Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;Ljava/lang/String;)V", "Lcom/bedrockk/molang/runtime/struct/QueryStruct;", "toMoLangStruct", "(Lcom/cobblemon/mod/common/api/dialogue/input/ActiveInput;)Lcom/bedrockk/molang/runtime/struct/QueryStruct;", "", "options", "Ljava/util/List;", "getOptions", "()Ljava/util/List;", "setOptions", "(Ljava/util/List;)V", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;", "timeout", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;", "getTimeout", "()Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;", "setTimeout", "(Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;)V", "", "vertical", "Z", "getVertical", "()Z", "setVertical", "(Z)V", "<init>", "()V", "(Ljava/util/List;Lcom/cobblemon/mod/common/api/dialogue/input/DialogueTimeout;Z)V", "common"})
@SourceDebugExtension(value={"SMAP\nDialogueOptionSetInput.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialogueOptionSetInput.kt\ncom/cobblemon/mod/common/api/dialogue/input/DialogueOptionSetInput\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,49:1\n766#2:50\n857#2,2:51\n288#2,2:53\n*S KotlinDebug\n*F\n+ 1 DialogueOptionSetInput.kt\ncom/cobblemon/mod/common/api/dialogue/input/DialogueOptionSetInput\n*L\n31#1:50\n31#1:51,2\n34#1:53,2\n*E\n"})
public final class DialogueOptionSetInput
implements DialogueInput {
    @NotNull
    private List<DialogueOption> options;
    @Nullable
    private DialogueTimeout timeout;
    private boolean vertical;

    public DialogueOptionSetInput(@NotNull List<DialogueOption> options, @Nullable DialogueTimeout timeout, boolean vertical) {
        Intrinsics.checkNotNullParameter(options, (String)"options");
        this.options = options;
        this.timeout = timeout;
        this.vertical = vertical;
    }

    public /* synthetic */ DialogueOptionSetInput(List list, DialogueTimeout dialogueTimeout, boolean bl, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 2) != 0) {
            dialogueTimeout = null;
        }
        if ((n & 4) != 0) {
            bl = false;
        }
        this(list, dialogueTimeout, bl);
    }

    @NotNull
    public final List<DialogueOption> getOptions() {
        return this.options;
    }

    public final void setOptions(@NotNull List<DialogueOption> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.options = list;
    }

    @Override
    @Nullable
    public DialogueTimeout getTimeout() {
        return this.timeout;
    }

    @Override
    public void setTimeout(@Nullable DialogueTimeout dialogueTimeout) {
        this.timeout = dialogueTimeout;
    }

    public final boolean getVertical() {
        return this.vertical;
    }

    public final void setVertical(boolean bl) {
        this.vertical = bl;
    }

    public DialogueOptionSetInput() {
        this(new ArrayList(), null, false);
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final List<DialogueOption> getVisibleOptions(@NotNull ActiveDialogue activeDialogue) {
        void $this$filterTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)activeDialogue, (String)"activeDialogue");
        Iterable $this$filter$iv = this.options;
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            DialogueOption it = (DialogueOption)element$iv$iv;
            boolean bl = false;
            if (!it.isVisible().invoke(activeDialogue)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        return (List)destination$iv$iv;
    }

    @Override
    @NotNull
    public QueryStruct toMoLangStruct(@NotNull ActiveInput activeInput) {
        Intrinsics.checkNotNullParameter((Object)activeInput, (String)"activeInput");
        return new QueryStruct(new HashMap<String, Function<MoParams, Object>>());
    }

    @Override
    public void handle(@NotNull ActiveInput activeInput, @NotNull String value2) {
        Object v0;
        block6: {
            Intrinsics.checkNotNullParameter((Object)activeInput, (String)"activeInput");
            Intrinsics.checkNotNullParameter((Object)value2, (String)"value");
            Iterable $this$firstOrNull$iv = this.options;
            boolean $i$f$firstOrNull = false;
            for (Object element$iv : $this$firstOrNull$iv) {
                DialogueOption it = (DialogueOption)element$iv;
                boolean bl = false;
                if (!Intrinsics.areEqual((Object)it.getValue(), (Object)value2)) continue;
                v0 = element$iv;
                break block6;
            }
            v0 = null;
        }
        DialogueOption option = v0;
        if (option != null) {
            if (!option.isSelectable().invoke(activeInput.getActiveDialogue())) {
                Cobblemon.INSTANCE.getLOGGER().warn("Dialogue option " + value2 + " is not selectable but " + activeInput.getActiveDialogue().getPlayerEntity().m_36316_().getName() + " selected it anyway");
                activeInput.getActiveDialogue().close();
            } else if (!option.isVisible().invoke(activeInput.getActiveDialogue())) {
                Cobblemon.INSTANCE.getLOGGER().warn("Dialogue option " + value2 + " is not visible but " + activeInput.getActiveDialogue().getPlayerEntity().m_36316_().getName() + " selected it anyway");
                activeInput.getActiveDialogue().close();
            }
            option.getAction().invoke(activeInput.getActiveDialogue(), value2);
        } else {
            Cobblemon.INSTANCE.getLOGGER().warn("No option with value " + value2 + " found in dialogue option set");
        }
    }
}

