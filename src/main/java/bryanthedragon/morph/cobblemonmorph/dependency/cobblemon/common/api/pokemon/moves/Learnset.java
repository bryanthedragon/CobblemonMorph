/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  io.netty.buffer.ByteBuf
 *  kotlin.Metadata
 *  kotlin.collections.CollectionsKt
 *  kotlin.comparisons.ComparisonsKt
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.functions.Function2
 *  kotlin.jvm.internal.DefaultConstructorMarker
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.text.StringsKt
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.data.ClientDataSynchronizer;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.Moves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.pokemon.moves.Learnset;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.net.IntSize;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.NetExtensionsKt;
import com.google.gson.JsonElement;
import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0010#\n\u0002\b\u0006\n\u0002\u0010%\n\u0002\b\r\b\u0016\u0018\u0000 )2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0002)*B\u0007\u00a2\u0006\u0004\b'\u0010(J\u0017\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u0017\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002H\u0016\u00a2\u0006\u0004\b\u0007\u0010\u0006J\u001b\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\t\u001a\u00020\b\u00a2\u0006\u0004\b\f\u0010\rJ\u0017\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u000e\u001a\u00020\u0000H\u0016\u00a2\u0006\u0004\b\u0010\u0010\u0011R\u001d\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00128\u0006\u00a2\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u001d\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00178\u0006\u00a2\u0006\f\n\u0004\b\u0018\u0010\u0019\u001a\u0004\b\u001a\u0010\u001bR\u001d\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00128\u0006\u00a2\u0006\f\n\u0004\b\u001c\u0010\u0014\u001a\u0004\b\u001d\u0010\u0016R)\u0010\u001f\u001a\u0014\u0012\u0004\u0012\u00020\b\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\u00120\u001e8\u0006\u00a2\u0006\f\n\u0004\b\u001f\u0010 \u001a\u0004\b!\u0010\"R\u001d\u0010#\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00128\u0006\u00a2\u0006\f\n\u0004\b#\u0010\u0014\u001a\u0004\b$\u0010\u0016R\u001d\u0010%\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00128\u0006\u00a2\u0006\f\n\u0004\b%\u0010\u0014\u001a\u0004\b&\u0010\u0016\u00a8\u0006+"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset;", "Lcom/cobblemon/mod/common/api/data/ClientDataSynchronizer;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "", "decode", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "encode", "", "level", "", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "getLevelUpMovesUpTo", "(I)Ljava/util/Set;", "other", "", "shouldSynchronize", "(Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset;)Z", "", "eggMoves", "Ljava/util/List;", "getEggMoves", "()Ljava/util/List;", "", "evolutionMoves", "Ljava/util/Set;", "getEvolutionMoves", "()Ljava/util/Set;", "formChangeMoves", "getFormChangeMoves", "", "levelUpMoves", "Ljava/util/Map;", "getLevelUpMoves", "()Ljava/util/Map;", "tmMoves", "getTmMoves", "tutorMoves", "getTutorMoves", "<init>", "()V", "Companion", "Interpreter", "common"})
@SourceDebugExtension(value={"SMAP\nLearnset.kt\nKotlin\n*S Kotlin\n*F\n+ 1 Learnset.kt\ncom/cobblemon/mod/common/api/pokemon/moves/Learnset\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n+ 3 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,121:1\n766#2:122\n857#2,2:123\n1045#2:125\n1360#2:126\n1446#2,5:127\n1#3:132\n*S KotlinDebug\n*F\n+ 1 Learnset.kt\ncom/cobblemon/mod/common/api/pokemon/moves/Learnset\n*L\n91#1:122\n91#1:123,2\n92#1:125\n93#1:126\n93#1:127,5\n*E\n"})
public class Learnset
implements ClientDataSynchronizer<Learnset> {
    @NotNull
    public static final Companion Companion = new Companion(null);
    @NotNull
    private final Map<Integer, List<MoveTemplate>> levelUpMoves = new LinkedHashMap();
    @NotNull
    private final List<MoveTemplate> eggMoves = new ArrayList();
    @NotNull
    private final List<MoveTemplate> tutorMoves = new ArrayList();
    @NotNull
    private final List<MoveTemplate> tmMoves = new ArrayList();
    @NotNull
    private final Set<MoveTemplate> evolutionMoves = new LinkedHashSet();
    @NotNull
    private final List<MoveTemplate> formChangeMoves = new ArrayList();
    @NotNull
    private static final Interpreter tmInterpreter = Interpreter.Companion.parseFromPrefixIntoList("tm", (Function1<? super Learnset, ? extends List<MoveTemplate>>)((Function1)Companion.tmInterpreter.1.INSTANCE));
    @NotNull
    private static final Interpreter eggInterpreter = Interpreter.Companion.parseFromPrefixIntoList("egg", (Function1<? super Learnset, ? extends List<MoveTemplate>>)((Function1)Companion.eggInterpreter.1.INSTANCE));
    @NotNull
    private static final Interpreter tutorInterpreter = Interpreter.Companion.parseFromPrefixIntoList("tutor", (Function1<? super Learnset, ? extends List<MoveTemplate>>)((Function1)Companion.tutorInterpreter.1.INSTANCE));
    @NotNull
    private static final Interpreter formChangeInterpreter = Interpreter.Companion.parseFromPrefixIntoList("form_change", (Function1<? super Learnset, ? extends List<MoveTemplate>>)((Function1)Companion.formChangeInterpreter.1.INSTANCE));
    @NotNull
    private static final Interpreter levelUpInterpreter = new Interpreter((Function2<? super JsonElement, ? super Learnset, Boolean>)((Function2)Companion.levelUpInterpreter.1.INSTANCE));
    @NotNull
    private static final List<Interpreter> interpreters;

    @NotNull
    public final Map<Integer, List<MoveTemplate>> getLevelUpMoves() {
        return this.levelUpMoves;
    }

    @NotNull
    public final List<MoveTemplate> getEggMoves() {
        return this.eggMoves;
    }

    @NotNull
    public final List<MoveTemplate> getTutorMoves() {
        return this.tutorMoves;
    }

    @NotNull
    public final List<MoveTemplate> getTmMoves() {
        return this.tmMoves;
    }

    @NotNull
    public final Set<MoveTemplate> getEvolutionMoves() {
        return this.evolutionMoves;
    }

    @NotNull
    public final List<MoveTemplate> getFormChangeMoves() {
        return this.formChangeMoves;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final Set<MoveTemplate> getLevelUpMovesUpTo(int level) {
        void $this$flatMapTo$iv$iv;
        Map.Entry it;
        Iterable $this$filterTo$iv$iv;
        Iterable $this$filter$iv = this.levelUpMoves.entrySet();
        boolean $i$f$filter = false;
        Iterable iterable = $this$filter$iv;
        Collection destination$iv$iv = new ArrayList();
        boolean $i$f$filterTo = false;
        for (Object element$iv$iv : $this$filterTo$iv$iv) {
            it = (Map.Entry)element$iv$iv;
            boolean bl = false;
            if (!(((Number)it.getKey()).intValue() <= level)) continue;
            destination$iv$iv.add(element$iv$iv);
        }
        Iterable $this$sortedBy$iv = (List)destination$iv$iv;
        boolean $i$f$sortedBy = false;
        Iterable $this$flatMap$iv = CollectionsKt.sortedWith((Iterable)$this$sortedBy$iv, (Comparator)new Comparator(){

            public final int compare(T a, T b) {
                Map.Entry it = (Map.Entry)a;
                boolean bl = false;
                Comparable comparable = (Integer)it.getKey();
                it = (Map.Entry)b;
                Comparable comparable2 = comparable;
                bl = false;
                return ComparisonsKt.compareValues((Comparable)comparable2, (Comparable)((Integer)it.getKey()));
            }
        });
        boolean $i$f$flatMap = false;
        $this$filterTo$iv$iv = $this$flatMap$iv;
        destination$iv$iv = new ArrayList();
        boolean $i$f$flatMapTo = false;
        for (Object element$iv$iv : $this$flatMapTo$iv$iv) {
            it = (Map.Entry)element$iv$iv;
            boolean bl = false;
            Iterable list$iv$iv = (List)it.getValue();
            CollectionsKt.addAll((Collection)destination$iv$iv, (Iterable)list$iv$iv);
        }
        return CollectionsKt.toSet((Iterable)((List)destination$iv$iv));
    }

    @Override
    public boolean shouldSynchronize(@NotNull Learnset other) {
        Intrinsics.checkNotNullParameter((Object)other, (String)"other");
        return !Intrinsics.areEqual(other.levelUpMoves, this.levelUpMoves);
    }

    @Override
    public void decode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.levelUpMoves.clear();
        int n = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_BYTE);
        for (int i = 0; i < n; ++i) {
            int it = i;
            boolean bl = false;
            int level = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_SHORT);
            List moves = new ArrayList();
            int n2 = NetExtensionsKt.readSizedInt((ByteBuf)buffer, IntSize.U_SHORT);
            for (int j = 0; j < n2; ++j) {
                MoveTemplate p0;
                int it2 = j;
                boolean bl2 = false;
                if (Moves.INSTANCE.getByNumericalId(buffer.readInt()) == null) continue;
                boolean bl3 = false;
                moves.add(p0);
            }
            Integer n3 = level;
            this.levelUpMoves.put(n3, moves);
        }
    }

    @Override
    public void encode(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_BYTE, this.levelUpMoves.size());
        for (Map.Entry<Integer, List<MoveTemplate>> entry : this.levelUpMoves.entrySet()) {
            int level = ((Number)entry.getKey()).intValue();
            List<MoveTemplate> moves = entry.getValue();
            NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_SHORT, level);
            NetExtensionsKt.writeSizedInt((ByteBuf)buffer, IntSize.U_SHORT, moves.size());
            for (MoveTemplate move : moves) {
                buffer.writeInt(move.getNum());
            }
        }
    }

    static {
        Object[] objectArray = new Interpreter[]{tmInterpreter, eggInterpreter, tutorInterpreter, levelUpInterpreter, formChangeInterpreter};
        interpreters = CollectionsKt.mutableListOf((Object[])objectArray);
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010!\n\u0002\b\r\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0003\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0003\u0010\u0004\u001a\u0004\b\u0005\u0010\u0006R\u0017\u0010\u0007\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0007\u0010\u0004\u001a\u0004\b\b\u0010\u0006R\u001d\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\t8\u0006\u00a2\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0017\u0010\u000e\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u000e\u0010\u0004\u001a\u0004\b\u000f\u0010\u0006R\u0017\u0010\u0010\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0010\u0010\u0004\u001a\u0004\b\u0011\u0010\u0006R\u0017\u0010\u0012\u001a\u00020\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0012\u0010\u0004\u001a\u0004\b\u0013\u0010\u0006\u00a8\u0006\u0016"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset$Companion;", "", "Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset$Interpreter;", "eggInterpreter", "Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset$Interpreter;", "getEggInterpreter", "()Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset$Interpreter;", "formChangeInterpreter", "getFormChangeInterpreter", "", "interpreters", "Ljava/util/List;", "getInterpreters", "()Ljava/util/List;", "levelUpInterpreter", "getLevelUpInterpreter", "tmInterpreter", "getTmInterpreter", "tutorInterpreter", "getTutorInterpreter", "<init>", "()V", "common"})
    public static final class Companion {
        private Companion() {
        }

        @NotNull
        public final Interpreter getTmInterpreter() {
            return tmInterpreter;
        }

        @NotNull
        public final Interpreter getEggInterpreter() {
            return eggInterpreter;
        }

        @NotNull
        public final Interpreter getTutorInterpreter() {
            return tutorInterpreter;
        }

        @NotNull
        public final Interpreter getFormChangeInterpreter() {
            return formChangeInterpreter;
        }

        @NotNull
        public final Interpreter getLevelUpInterpreter() {
            return levelUpInterpreter;
        }

        @NotNull
        public final List<Interpreter> getInterpreters() {
            return interpreters;
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\b\u0018\u0000 \f2\u00020\u0001:\u0001\fB!\u0012\u0018\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0002\u00a2\u0006\u0004\b\n\u0010\u000bR)\u0010\u0006\u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\b\u0010\t\u00a8\u0006\r"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset$Interpreter;", "", "Lkotlin/Function2;", "Lcom/google/gson/JsonElement;", "Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset;", "", "loadMove", "Lkotlin/jvm/functions/Function2;", "getLoadMove", "()Lkotlin/jvm/functions/Function2;", "<init>", "(Lkotlin/jvm/functions/Function2;)V", "Companion", "common"})
    public static final class Interpreter {
        @NotNull
        public static final Companion Companion = new Companion(null);
        @NotNull
        private final Function2<JsonElement, Learnset, Boolean> loadMove;

        public Interpreter(@NotNull Function2<? super JsonElement, ? super Learnset, Boolean> loadMove) {
            Intrinsics.checkNotNullParameter(loadMove, (String)"loadMove");
            this.loadMove = loadMove;
        }

        @NotNull
        public final Function2<JsonElement, Learnset, Boolean> getLoadMove() {
            return this.loadMove;
        }

        @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\t\b\u0002\u00a2\u0006\u0004\b\f\u0010\rJ/\u0010\n\u001a\u00020\t2\u0006\u0010\u0003\u001a\u00020\u00022\u0018\u0010\b\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00060\u0004\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset$Interpreter$Companion;", "", "", "prefix", "Lkotlin/Function1;", "Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset;", "", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "list", "Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset$Interpreter;", "parseFromPrefixIntoList", "(Ljava/lang/String;Lkotlin/jvm/functions/Function1;)Lcom/cobblemon/mod/common/api/pokemon/moves/Learnset$Interpreter;", "<init>", "()V", "common"})
        public static final class Companion {
            private Companion() {
            }

            @NotNull
            public final Interpreter parseFromPrefixIntoList(@NotNull String prefix, @NotNull Function1<? super Learnset, ? extends List<MoveTemplate>> list) {
                Intrinsics.checkNotNullParameter((Object)prefix, (String)"prefix");
                Intrinsics.checkNotNullParameter(list, (String)"list");
                return new Interpreter((Function2<? super JsonElement, ? super Learnset, Boolean>)((Function2)new Function2<JsonElement, Learnset, Boolean>(prefix, list){
                    final /* synthetic */ String $prefix;
                    final /* synthetic */ Function1<Learnset, List<MoveTemplate>> $list;
                    {
                        this.$prefix = $prefix;
                        this.$list = $list;
                        super(2);
                    }

                    @NotNull
                    public final Boolean invoke(@NotNull JsonElement element, @NotNull Learnset learnset) {
                        MoveTemplate moveTemplate;
                        Function1<Learnset, List<MoveTemplate>> function1;
                        Intrinsics.checkNotNullParameter((Object)element, (String)"element");
                        Intrinsics.checkNotNullParameter((Object)learnset, (String)"learnset");
                        Object it = function1 = element;
                        boolean bl = false;
                        Object object = it.isJsonPrimitive() ? function1 : null;
                        String string = object != null ? object.getAsString() : null;
                        if (string == null) {
                            return false;
                        }
                        String str = string;
                        if (StringsKt.startsWith$default((String)str, (String)this.$prefix, (boolean)false, (int)2, null) && (moveTemplate = Moves.INSTANCE.getByName(StringsKt.substringAfter$default((String)str, (String)":", null, (int)2, null))) != null) {
                            MoveTemplate moveTemplate2 = moveTemplate;
                            function1 = this.$list;
                            it = moveTemplate2;
                            boolean bl2 = false;
                            ((List)function1.invoke((Object)learnset)).add(it);
                            return true;
                        }
                        return false;
                    }
                }));
            }

            public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
                this();
            }
        }
    }
}

