/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  kotlin.Metadata
 *  kotlin.Unit
 *  kotlin.collections.CollectionsKt
 *  kotlin.jvm.functions.Function0
 *  kotlin.jvm.functions.Function1
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  kotlin.jvm.internal.markers.KMappedMarker
 *  net.minecraft.nbt.CompoundTag
 *  net.minecraft.nbt.ListTag
 *  net.minecraft.nbt.Tag
 *  net.minecraft.network.FriendlyByteBuf
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.BenchedMove;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.BenchedMoves;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.moves.MoveTemplate;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.reactive.SimpleObservable;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.jvm.internal.markers.KMappedMarker;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\\\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010)\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010!\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\u00a2\u0006\u0004\b5\u0010\u000bJ\u0015\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b\u0005\u0010\u0006J\u001b\u0010\b\u001a\u00020\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001\u00a2\u0006\u0004\b\b\u0010\tJ\r\u0010\n\u001a\u00020\u0004\u00a2\u0006\u0004\b\n\u0010\u000bJ\u001b\u0010\u000e\u001a\u00020\u00042\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\f\u00a2\u0006\u0004\b\u000e\u0010\u000fJ\u001b\u0010\u0010\u001a\u00020\u00042\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00040\f\u00a2\u0006\u0004\b\u0010\u0010\u000fJ\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00020\u0011H\u0096\u0002\u00a2\u0006\u0004\b\u0012\u0010\u0013J\u0015\u0010\u0016\u001a\u00020\u00002\u0006\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b\u0016\u0010\u0017J\u0015\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u0019\u001a\u00020\u0018\u00a2\u0006\u0004\b\u001a\u0010\u001bJ\u0015\u0010\u001e\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u001c\u00a2\u0006\u0004\b\u001e\u0010\u001fJ\u0015\u0010 \u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0002\u00a2\u0006\u0004\b \u0010\u0006J\u0015\u0010 \u001a\u00020\u00042\u0006\u0010\"\u001a\u00020!\u00a2\u0006\u0004\b \u0010#J\u0015\u0010$\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u0014\u00a2\u0006\u0004\b$\u0010%J\u0015\u0010&\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u0018\u00a2\u0006\u0004\b&\u0010'J\u0015\u0010(\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c\u00a2\u0006\u0004\b(\u0010)J\r\u0010*\u001a\u00020\u0004\u00a2\u0006\u0004\b*\u0010\u000bR\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00020+8\u0002X\u0082\u0004\u00a2\u0006\u0006\n\u0004\b\u0007\u0010,R\u0016\u0010.\u001a\u00020-8\u0002@\u0002X\u0082\u000e\u00a2\u0006\u0006\n\u0004\b.\u0010/R\u001d\u00101\u001a\b\u0012\u0004\u0012\u00020\u0000008\u0006\u00a2\u0006\f\n\u0004\b1\u00102\u001a\u0004\b3\u00104\u00a8\u00066"}, d2={"Lcom/cobblemon/mod/common/api/moves/BenchedMoves;", "", "Lcom/cobblemon/mod/common/api/moves/BenchedMove;", "benchedMove", "", "add", "(Lcom/cobblemon/mod/common/api/moves/BenchedMove;)V", "benchedMoves", "addAll", "(Ljava/lang/Iterable;)V", "clear", "()V", "Lkotlin/Function0;", "action", "doThenEmit", "(Lkotlin/jvm/functions/Function0;)V", "doWithoutEmitting", "", "iterator", "()Ljava/util/Iterator;", "Lnet/minecraft/network/FriendlyByteBuf;", "buffer", "loadFromBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)Lcom/cobblemon/mod/common/api/moves/BenchedMoves;", "Lcom/google/gson/JsonArray;", "json", "loadFromJSON", "(Lcom/google/gson/JsonArray;)Lcom/cobblemon/mod/common/api/moves/BenchedMoves;", "Lnet/minecraft/nbt/ListTag;", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/ListTag;)Lcom/cobblemon/mod/common/api/moves/BenchedMoves;", "remove", "Lcom/cobblemon/mod/common/api/moves/MoveTemplate;", "moveTemplate", "(Lcom/cobblemon/mod/common/api/moves/MoveTemplate;)V", "saveToBuffer", "(Lnet/minecraft/network/FriendlyByteBuf;)V", "saveToJSON", "(Lcom/google/gson/JsonArray;)Lcom/google/gson/JsonArray;", "saveToNBT", "(Lnet/minecraft/nbt/ListTag;)Lnet/minecraft/nbt/ListTag;", "update", "", "Ljava/util/List;", "", "emit", "Z", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "observable", "Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "getObservable", "()Lcom/cobblemon/mod/common/api/reactive/SimpleObservable;", "<init>", "common"})
@SourceDebugExtension(value={"SMAP\nBenchedMove.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BenchedMove.kt\ncom/cobblemon/mod/common/api/moves/BenchedMoves\n+ 2 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,138:1\n1549#2:139\n1620#2,3:140\n1549#2:143\n1620#2,3:144\n1855#2,2:147\n1855#2,2:149\n*S KotlinDebug\n*F\n+ 1 BenchedMove.kt\ncom/cobblemon/mod/common/api/moves/BenchedMoves\n*L\n52#1:139\n52#1:140,3\n57#1:143\n57#1:144,3\n58#1:147,2\n64#1:149,2\n*E\n"})
public final class BenchedMoves
implements Iterable<BenchedMove>,
KMappedMarker {
    @NotNull
    private final SimpleObservable<BenchedMoves> observable = new SimpleObservable();
    private boolean emit = true;
    @NotNull
    private final List<BenchedMove> benchedMoves = new ArrayList();

    @NotNull
    public final SimpleObservable<BenchedMoves> getObservable() {
        return this.observable;
    }

    public final void doWithoutEmitting(@NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        boolean previousEmit = this.emit;
        this.emit = false;
        action2.invoke();
        this.emit = previousEmit;
    }

    public final void doThenEmit(@NotNull Function0<Unit> action2) {
        Intrinsics.checkNotNullParameter(action2, (String)"action");
        this.doWithoutEmitting(action2);
        this.update();
    }

    public final void update() {
        if (this.emit) {
            BenchedMoves[] benchedMovesArray = new BenchedMoves[]{this};
            this.observable.emit((BenchedMoves[])benchedMovesArray);
        }
    }

    public final void add(@NotNull BenchedMove benchedMove) {
        Intrinsics.checkNotNullParameter((Object)benchedMove, (String)"benchedMove");
        this.doThenEmit((Function0<Unit>)((Function0)new Function0<Unit>(this, benchedMove){
            final /* synthetic */ BenchedMoves this$0;
            final /* synthetic */ BenchedMove $benchedMove;
            {
                this.this$0 = $receiver;
                this.$benchedMove = $benchedMove;
                super(0);
            }

            public final void invoke() {
                BenchedMoves.access$getBenchedMoves$p(this.this$0).add(this.$benchedMove);
            }
        }));
    }

    public final void addAll(@NotNull Iterable<BenchedMove> benchedMoves) {
        Intrinsics.checkNotNullParameter(benchedMoves, (String)"benchedMoves");
        this.doThenEmit((Function0<Unit>)((Function0)new Function0<Unit>(this, benchedMoves){
            final /* synthetic */ BenchedMoves this$0;
            final /* synthetic */ Iterable<BenchedMove> $benchedMoves;
            {
                this.this$0 = $receiver;
                this.$benchedMoves = $benchedMoves;
                super(0);
            }

            public final void invoke() {
                CollectionsKt.addAll((Collection)BenchedMoves.access$getBenchedMoves$p(this.this$0), this.$benchedMoves);
            }
        }));
    }

    public final void clear() {
        this.doThenEmit((Function0<Unit>)((Function0)new Function0<Unit>(this){
            final /* synthetic */ BenchedMoves this$0;
            {
                this.this$0 = $receiver;
                super(0);
            }

            public final void invoke() {
                BenchedMoves.access$getBenchedMoves$p(this.this$0).clear();
            }
        }));
    }

    public final void remove(@NotNull BenchedMove benchedMove) {
        Intrinsics.checkNotNullParameter((Object)benchedMove, (String)"benchedMove");
        this.doThenEmit((Function0<Unit>)((Function0)new Function0<Unit>(this, benchedMove){
            final /* synthetic */ BenchedMoves this$0;
            final /* synthetic */ BenchedMove $benchedMove;
            {
                this.this$0 = $receiver;
                this.$benchedMove = $benchedMove;
                super(0);
            }

            public final void invoke() {
                BenchedMoves.access$getBenchedMoves$p(this.this$0).remove(this.$benchedMove);
            }
        }));
    }

    public final void remove(@NotNull MoveTemplate moveTemplate) {
        Intrinsics.checkNotNullParameter((Object)moveTemplate, (String)"moveTemplate");
        this.doThenEmit((Function0<Unit>)((Function0)new Function0<Unit>(this, moveTemplate){
            final /* synthetic */ BenchedMoves this$0;
            final /* synthetic */ MoveTemplate $moveTemplate;
            {
                this.this$0 = $receiver;
                this.$moveTemplate = $moveTemplate;
                super(0);
            }

            public final void invoke() {
                BenchedMoves.access$getBenchedMoves$p(this.this$0).removeIf(arg_0 -> remove.2.invoke$lambda$0((Function1)new Function1<BenchedMove, Boolean>(this.$moveTemplate){
                    final /* synthetic */ MoveTemplate $moveTemplate;
                    {
                        this.$moveTemplate = $moveTemplate;
                        super(1);
                    }

                    @NotNull
                    public final Boolean invoke(@NotNull BenchedMove it) {
                        Intrinsics.checkNotNullParameter((Object)it, (String)"it");
                        return Intrinsics.areEqual((Object)it.getMoveTemplate(), (Object)this.$moveTemplate);
                    }
                }, arg_0));
            }

            private static final boolean invoke$lambda$0(Function1 $tmp0, Object p0) {
                Intrinsics.checkNotNullParameter((Object)$tmp0, (String)"$tmp0");
                return (Boolean)$tmp0.invoke(p0);
            }
        }));
    }

    @Override
    @NotNull
    public Iterator<BenchedMove> iterator() {
        return this.benchedMoves.iterator();
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final ListTag saveToNBT(@NotNull ListTag nbt) {
        void $this$mapTo$iv$iv;
        void $this$map$iv;
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        Iterable iterable = this.benchedMoves;
        ListTag listTag = nbt;
        boolean $i$f$map = false;
        void var4_5 = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            BenchedMove benchedMove = (BenchedMove)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.saveToNBT(new CompoundTag()));
        }
        listTag.addAll((Collection)((List)destination$iv$iv));
        return nbt;
    }

    /*
     * WARNING - void declaration
     */
    @NotNull
    public final JsonArray saveToJSON(@NotNull JsonArray json) {
        void $this$mapTo$iv$iv;
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        Iterable $this$map$iv = this.benchedMoves;
        boolean $i$f$map = false;
        Iterable iterable = $this$map$iv;
        Collection destination$iv$iv = new ArrayList(CollectionsKt.collectionSizeOrDefault((Iterable)$this$map$iv, (int)10));
        boolean $i$f$mapTo = false;
        for (Object item$iv$iv : $this$mapTo$iv$iv) {
            void it;
            BenchedMove benchedMove = (BenchedMove)item$iv$iv;
            Collection collection = destination$iv$iv;
            boolean bl = false;
            collection.add(it.saveToJSON(new JsonObject()));
        }
        List jsons = (List)destination$iv$iv;
        Iterable $this$forEach$iv = jsons;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            JsonObject it = (JsonObject)element$iv;
            boolean bl = false;
            json.add((JsonElement)it);
        }
        return json;
    }

    public final void saveToBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        buffer.writeShort(this.benchedMoves.size());
        Iterable $this$forEach$iv = this.benchedMoves;
        boolean $i$f$forEach = false;
        for (Object element$iv : $this$forEach$iv) {
            BenchedMove it = (BenchedMove)element$iv;
            boolean bl = false;
            it.saveToBuffer(buffer);
        }
    }

    @NotNull
    public final BenchedMoves loadFromNBT(@NotNull ListTag nbt) {
        Intrinsics.checkNotNullParameter((Object)nbt, (String)"nbt");
        this.doThenEmit((Function0<Unit>)((Function0)new Function0<Unit>(this, nbt){
            final /* synthetic */ BenchedMoves this$0;
            final /* synthetic */ ListTag $nbt;
            {
                this.this$0 = $receiver;
                this.$nbt = $nbt;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            public final void invoke() {
                void $this$forEach$iv;
                this.this$0.clear();
                Iterable iterable = (Iterable)this.$nbt;
                BenchedMoves benchedMoves = this.this$0;
                boolean $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    Tag it = (Tag)element$iv;
                    boolean bl = false;
                    List list = BenchedMoves.access$getBenchedMoves$p(benchedMoves);
                    Intrinsics.checkNotNull((Object)it, (String)"null cannot be cast to non-null type net.minecraft.nbt.NbtCompound");
                    list.add(BenchedMove.Companion.loadFromNBT((CompoundTag)it));
                }
            }
        }));
        return this;
    }

    @NotNull
    public final BenchedMoves loadFromJSON(@NotNull JsonArray json) {
        Intrinsics.checkNotNullParameter((Object)json, (String)"json");
        this.doThenEmit((Function0<Unit>)((Function0)new Function0<Unit>(this, json){
            final /* synthetic */ BenchedMoves this$0;
            final /* synthetic */ JsonArray $json;
            {
                this.this$0 = $receiver;
                this.$json = $json;
                super(0);
            }

            /*
             * WARNING - void declaration
             */
            public final void invoke() {
                void $this$forEach$iv;
                this.this$0.clear();
                Iterable iterable = (Iterable)this.$json;
                BenchedMoves benchedMoves = this.this$0;
                boolean $i$f$forEach = false;
                for (T element$iv : $this$forEach$iv) {
                    JsonElement it = (JsonElement)element$iv;
                    boolean bl = false;
                    List list = BenchedMoves.access$getBenchedMoves$p(benchedMoves);
                    JsonObject jsonObject = it.getAsJsonObject();
                    Intrinsics.checkNotNullExpressionValue((Object)jsonObject, (String)"it.asJsonObject");
                    list.add(BenchedMove.Companion.loadFromJSON(jsonObject));
                }
            }
        }));
        return this;
    }

    @NotNull
    public final BenchedMoves loadFromBuffer(@NotNull FriendlyByteBuf buffer) {
        Intrinsics.checkNotNullParameter((Object)buffer, (String)"buffer");
        this.doThenEmit((Function0<Unit>)((Function0)new Function0<Unit>(this, buffer){
            final /* synthetic */ BenchedMoves this$0;
            final /* synthetic */ FriendlyByteBuf $buffer;
            {
                this.this$0 = $receiver;
                this.$buffer = $buffer;
                super(0);
            }

            public final void invoke() {
                this.this$0.clear();
                int n = this.$buffer.readShort();
                BenchedMoves benchedMoves = this.this$0;
                FriendlyByteBuf friendlyByteBuf = this.$buffer;
                int n2 = 0;
                while (n2 < n) {
                    int it = n2++;
                    boolean bl = false;
                    BenchedMoves.access$getBenchedMoves$p(benchedMoves).add(BenchedMove.Companion.loadFromBuffer(friendlyByteBuf));
                }
            }
        }));
        return this;
    }

    public static final /* synthetic */ List access$getBenchedMoves$p(BenchedMoves $this) {
        return $this.benchedMoves;
    }
}

