/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  kotlin.Metadata
 *  kotlin.Pair
 *  kotlin.TuplesKt
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.collections.MapsKt
 *  kotlin.jvm.JvmOverloads
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  net.minecraft.network.chat.MutableComponent
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.Expression;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.MoParams;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.MoStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.struct.QueryStruct;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.MoValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.bedrock.molang.runtime.value.StringValue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.ActiveDialogue;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialogueText;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.FunctionDialogueAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.WrappedDialogueText;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueInput;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.input.DialogueNoInput;
import com.google.gson.JsonArray;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import net.minecraft.network.chat.MutableComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u0000 -2\u00020\u0001:\u0001-BS\u0012\b\b\u0002\u0010\u0017\u001a\u00020\u0016\u0012\n\b\u0002\u0010(\u001a\u0004\u0018\u00010\u0016\u0012\u000e\b\u0002\u0010%\u001a\b\u0012\u0004\u0012\u00020$0\u0007\u0012\b\b\u0002\u0010\u001e\u001a\u00020\u001d\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u0007\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u000f\u00a2\u0006\u0004\b+\u0010,J\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006R(\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR$\u0010\u0010\u001a\u0004\u0018\u00010\u000f8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0010\u0010\u0011\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\"\u0010\u0017\u001a\u00020\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u0017\u0010\u0018\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\"\u0010\u001e\u001a\u00020\u001d8\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b\u001e\u0010\u001f\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R(\u0010%\u001a\b\u0012\u0004\u0012\u00020$0\u00078\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b%\u0010\n\u001a\u0004\b&\u0010\f\"\u0004\b'\u0010\u000eR$\u0010(\u001a\u0004\u0018\u00010\u00168\u0006@\u0006X\u0086\u000e\u00a2\u0006\u0012\n\u0004\b(\u0010\u0018\u001a\u0004\b)\u0010\u001a\"\u0004\b*\u0010\u001c\u00a8\u0006."}, d2={"Lcom/cobblemon/mod/common/api/dialogue/DialoguePage;", "", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "activeDialogue", "Lcom/bedrockk/molang/runtime/struct/MoStruct;", "toMoLangStruct", "(Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;)Lcom/bedrockk/molang/runtime/struct/MoStruct;", "", "Lcom/bedrockk/molang/Expression;", "clientActions", "Ljava/util/List;", "getClientActions", "()Ljava/util/List;", "setClientActions", "(Ljava/util/List;)V", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "escapeAction", "Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "getEscapeAction", "()Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;", "setEscapeAction", "(Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;)V", "", "id", "Ljava/lang/String;", "getId", "()Ljava/lang/String;", "setId", "(Ljava/lang/String;)V", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;", "input", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;", "getInput", "()Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;", "setInput", "(Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;)V", "Lcom/cobblemon/mod/common/api/dialogue/DialogueText;", "lines", "getLines", "setLines", "speaker", "getSpeaker", "setSpeaker", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/util/List;Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;Ljava/util/List;Lcom/cobblemon/mod/common/api/dialogue/DialogueAction;)V", "Companion", "common"})
@SourceDebugExtension(value={"SMAP\nDialoguePage.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialoguePage.kt\ncom/cobblemon/mod/common/api/dialogue/DialoguePage\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,71:1\n1855#2,2:72\n*S KotlinDebug\n*F\n+ 1 DialoguePage.kt\ncom/cobblemon/mod/common/api/dialogue/DialoguePage\n*L\n65#1:72,2\n*E\n"})
public final class DialoguePage {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private String id;
    @Nullable
    private String speaker;
    @NotNull
    private List<DialogueText> lines;
    @NotNull
    private DialogueInput input;
    @NotNull
    private List<Expression> clientActions;
    @Nullable
    private DialogueAction escapeAction;

    public DialoguePage(@NotNull String id, @Nullable String speaker, @NotNull List<DialogueText> lines, @NotNull DialogueInput input, @NotNull List<Expression> clientActions, @Nullable DialogueAction escapeAction) {
        Intrinsics.checkNotNullParameter((Object)id, (String)"id");
        Intrinsics.checkNotNullParameter(lines, (String)"lines");
        Intrinsics.checkNotNullParameter((Object)input, (String)"input");
        Intrinsics.checkNotNullParameter(clientActions, (String)"clientActions");
        this.id = id;
        this.speaker = speaker;
        this.lines = lines;
        this.input = input;
        this.clientActions = clientActions;
        this.escapeAction = escapeAction;
    }

    public /* synthetic */ DialoguePage(String string, String string2, List list, DialogueInput dialogueInput, List list2, DialogueAction dialogueAction, int n, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n & 1) != 0) {
            string = "";
        }
        if ((n & 2) != 0) {
            string2 = null;
        }
        if ((n & 4) != 0) {
            list = new ArrayList();
        }
        if ((n & 8) != 0) {
            dialogueInput = new DialogueNoInput(null, 1, null);
        }
        if ((n & 0x10) != 0) {
            list2 = new ArrayList();
        }
        if ((n & 0x20) != 0) {
            dialogueAction = null;
        }
        this(string, string2, list, dialogueInput, list2, dialogueAction);
    }

    @NotNull
    public final String getId() {
        return this.id;
    }

    public final void setId(@NotNull String string) {
        Intrinsics.checkNotNullParameter((Object)string, (String)"<set-?>");
        this.id = string;
    }

    @Nullable
    public final String getSpeaker() {
        return this.speaker;
    }

    public final void setSpeaker(@Nullable String string) {
        this.speaker = string;
    }

    @NotNull
    public final List<DialogueText> getLines() {
        return this.lines;
    }

    public final void setLines(@NotNull List<DialogueText> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.lines = list;
    }

    @NotNull
    public final DialogueInput getInput() {
        return this.input;
    }

    public final void setInput(@NotNull DialogueInput dialogueInput) {
        Intrinsics.checkNotNullParameter((Object)dialogueInput, (String)"<set-?>");
        this.input = dialogueInput;
    }

    @NotNull
    public final List<Expression> getClientActions() {
        return this.clientActions;
    }

    public final void setClientActions(@NotNull List<Expression> list) {
        Intrinsics.checkNotNullParameter(list, (String)"<set-?>");
        this.clientActions = list;
    }

    @Nullable
    public final DialogueAction getEscapeAction() {
        return this.escapeAction;
    }

    public final void setEscapeAction(@Nullable DialogueAction dialogueAction) {
        this.escapeAction = dialogueAction;
    }

    @NotNull
    public final MoStruct toMoLangStruct(@NotNull ActiveDialogue activeDialogue) {
        Intrinsics.checkNotNullParameter((Object)activeDialogue, (String)"activeDialogue");
        Pair[] pairArray = new Pair[]{TuplesKt.to((Object)"id", arg_0 -> DialoguePage.toMoLangStruct$lambda$0(this, arg_0)), TuplesKt.to((Object)"input", arg_0 -> DialoguePage.toMoLangStruct$lambda$1(activeDialogue, arg_0)), TuplesKt.to((Object)"lines", arg_0 -> DialoguePage.toMoLangStruct$lambda$3(this, activeDialogue, arg_0))};
        return new QueryStruct(MapsKt.hashMapOf((Pair[])pairArray));
    }

    private static final Object toMoLangStruct$lambda$0(DialoguePage this$0, MoParams moParams) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        return new StringValue(this$0.id);
    }

    private static final Object toMoLangStruct$lambda$1(ActiveDialogue $activeDialogue, MoParams moParams) {
        Intrinsics.checkNotNullParameter((Object)$activeDialogue, (String)"$activeDialogue");
        return $activeDialogue.getActiveInput().getStruct();
    }

    private static final Object toMoLangStruct$lambda$3(DialoguePage this$0, ActiveDialogue $activeDialogue, MoParams moParams) {
        Intrinsics.checkNotNullParameter((Object)this$0, (String)"this$0");
        Intrinsics.checkNotNullParameter((Object)$activeDialogue, (String)"$activeDialogue");
        JsonArray array = new JsonArray();
        Iterable $this$forEach$iv = this$0.lines;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            DialogueText it = (DialogueText)element$iv;
            boolean bl = false;
            array.add(it.invoke($activeDialogue).getString());
        }
        return MoValue.of(array);
    }

    public DialoguePage() {
        this(null, null, null, null, null, null, 63, null);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0013\u0010\u0014Je\u0010\u0011\u001a\u00020\u00102\b\b\u0002\u0010\u0003\u001a\u00020\u00022\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00022\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\b\b\u0002\u0010\t\u001a\u00020\b2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\n0\u00052\u0016\b\u0002\u0010\u000f\u001a\u0010\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u000e\u0018\u00010\fH\u0007\u00a2\u0006\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0015"}, d2={"Lcom/cobblemon/mod/common/api/dialogue/DialoguePage$Companion;", "", "", "id", "speaker", "", "Lnet/minecraft/network/chat/MutableComponent;", "lines", "Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;", "input", "Lcom/bedrockk/molang/Expression;", "clientActions", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/api/dialogue/ActiveDialogue;", "", "escapeAction", "Lcom/cobblemon/mod/common/api/dialogue/DialoguePage;", "of", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Iterable;Lcom/cobblemon/mod/common/api/dialogue/input/DialogueInput;Ljava/lang/Iterable;Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/dialogue/DialoguePage;", "<init>", "()V", "common"})
    @SourceDebugExtension(value={"SMAP\nDialoguePage.kt\nKotlin\n*S Kotlin\n*F\n+ 1 DialoguePage.kt\ncom/cobblemon/mod/common/api/dialogue/DialoguePage$Companion\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,71:1\n1549#2:72\n1620#2,3:73\n1#3:76\n*S KotlinDebug\n*F\n+ 1 DialoguePage.kt\ncom/cobblemon/mod/common/api/dialogue/DialoguePage$Companion\n*L\n50#1:72\n50#1:73,3\n*E\n"})
    public static final class Companion {
        private Companion() {
        }

        /*
         * WARNING - void declaration
         */
        @JvmOverloads
        @NotNull
        public final DialoguePage of(@NotNull String id, @Nullable String speaker, @NotNull Iterable<? extends MutableComponent> lines, @NotNull DialogueInput input, @NotNull Iterable<? extends Expression> clientActions, @Nullable Function1<? super ActiveDialogue, Unit> escapeAction) {
            FunctionDialogueAction functionDialogueAction;
            Collection<WrappedDialogueText> collection;
            void $this$mapTo$iv$iv;
            void $this$map$iv;
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            Intrinsics.checkNotNullParameter(lines, (String)"lines");
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            Intrinsics.checkNotNullParameter(clientActions, (String)"clientActions");
            Iterable<? extends MutableComponent> iterable = lines;
            String string = speaker;
            String string2 = id;
            boolean $i$f$map2 = false;
            void var9_12 = $this$map$iv;
            Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
            boolean $i$f$mapTo = false;
            for (Object item$iv$iv : $this$mapTo$iv$iv) {
                void it;
                MutableComponent mutableComponent = (MutableComponent)item$iv$iv;
                collection = destination$iv$iv;
                boolean bl = false;
                collection.add(new WrappedDialogueText((MutableComponent)it));
            }
            collection = (List)destination$iv$iv;
            String string3 = string2;
            String string4 = string;
            Collection collection2 = CollectionsKt.toMutableList((Collection)collection);
            DialogueInput dialogueInput = input;
            List list = CollectionsKt.toMutableList(clientActions);
            Function1<? super ActiveDialogue, Unit> function1 = escapeAction;
            if (function1 != null) {
                void func;
                Function1<? super ActiveDialogue, Unit> $i$f$map2 = function1;
                List list2 = list;
                DialogueInput dialogueInput2 = dialogueInput;
                collection = collection2;
                string = string4;
                string2 = string3;
                boolean bl = false;
                FunctionDialogueAction functionDialogueAction2 = new FunctionDialogueAction((Function2<? super ActiveDialogue, ? super String, Unit>)((Function2)new Function2<ActiveDialogue, String, Unit>((Function1<? super ActiveDialogue, Unit>)func){
                    final /* synthetic */ Function1<ActiveDialogue, Unit> $func;
                    {
                        this.$func = $func;
                        super(2);
                    }

                    public final void invoke(@NotNull ActiveDialogue activeDialogue, @Nullable String string) {
                        Intrinsics.checkNotNullParameter((Object)activeDialogue, (String)"activeDialogue");
                        this.$func.invoke((Object)activeDialogue);
                    }
                }));
                string3 = string2;
                string4 = string;
                collection2 = collection;
                dialogueInput = dialogueInput2;
                list = list2;
                functionDialogueAction = functionDialogueAction2;
            } else {
                functionDialogueAction = null;
            }
            DialogueAction dialogueAction = functionDialogueAction;
            List list3 = list;
            DialogueInput dialogueInput3 = dialogueInput;
            List list4 = collection2;
            String string5 = string4;
            String string6 = string3;
            return new DialoguePage(string6, string5, list4, dialogueInput3, list3, dialogueAction);
        }

        public static /* synthetic */ DialoguePage of$default(Companion companion, String string, String string2, Iterable iterable, DialogueInput dialogueInput, Iterable iterable2, Function1 function1, int n, Object object) {
            if ((n & 1) != 0) {
                string = "";
            }
            if ((n & 2) != 0) {
                string2 = null;
            }
            if ((n & 8) != 0) {
                dialogueInput = new DialogueNoInput(null, 1, null);
            }
            if ((n & 0x10) != 0) {
                iterable2 = CollectionsKt.emptyList();
            }
            if ((n & 0x20) != 0) {
                function1 = null;
            }
            return companion.of(string, string2, iterable, dialogueInput, iterable2, (Function1<? super ActiveDialogue, Unit>)function1);
        }

        @JvmOverloads
        @NotNull
        public final DialoguePage of(@NotNull String id, @Nullable String speaker, @NotNull Iterable<? extends MutableComponent> lines, @NotNull DialogueInput input, @NotNull Iterable<? extends Expression> clientActions) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            Intrinsics.checkNotNullParameter(lines, (String)"lines");
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            Intrinsics.checkNotNullParameter(clientActions, (String)"clientActions");
            return bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialoguePage$Companion.of$default(this, id, speaker, lines, input, clientActions, null, 32, null);
        }

        @JvmOverloads
        @NotNull
        public final DialoguePage of(@NotNull String id, @Nullable String speaker, @NotNull Iterable<? extends MutableComponent> lines, @NotNull DialogueInput input) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            Intrinsics.checkNotNullParameter(lines, (String)"lines");
            Intrinsics.checkNotNullParameter((Object)input, (String)"input");
            return bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialoguePage$Companion.of$default(this, id, speaker, lines, input, null, null, 48, null);
        }

        @JvmOverloads
        @NotNull
        public final DialoguePage of(@NotNull String id, @Nullable String speaker, @NotNull Iterable<? extends MutableComponent> lines) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            Intrinsics.checkNotNullParameter(lines, (String)"lines");
            return bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialoguePage$Companion.of$default(this, id, speaker, lines, null, null, null, 56, null);
        }

        @JvmOverloads
        @NotNull
        public final DialoguePage of(@NotNull String id, @NotNull Iterable<? extends MutableComponent> lines) {
            Intrinsics.checkNotNullParameter((Object)id, (String)"id");
            Intrinsics.checkNotNullParameter(lines, (String)"lines");
            return bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialoguePage$Companion.of$default(this, id, null, lines, null, null, null, 58, null);
        }

        @JvmOverloads
        @NotNull
        public final DialoguePage of(@NotNull Iterable<? extends MutableComponent> lines) {
            Intrinsics.checkNotNullParameter(lines, (String)"lines");
            return bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.dialogue.DialoguePage$Companion.of$default(this, null, null, lines, null, null, null, 59, null);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}

