/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.jvm.internal.Intrinsics
 *  kotlin.jvm.internal.SourceDebugExtension
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.battles.model.actor.BattleActor;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.battles.BattleStartError;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\u00002*\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u00030\u0001j\u0014\u0012\u0004\u0012\u00020\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0003`\u0005B\u0007\u00a2\u0006\u0004\b\t\u0010\nJ\u001e\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0006\u0010\u0006\u001a\u00020\u0002H\u0096\u0002\u00a2\u0006\u0004\b\u0007\u0010\b\u00a8\u0006\u000b"}, d2={"Lcom/cobblemon/mod/common/battles/BattleActorErrors;", "Ljava/util/HashMap;", "Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;", "", "Lcom/cobblemon/mod/common/battles/BattleStartError;", "Lkotlin/collections/HashMap;", "key", "get", "(Lcom/cobblemon/mod/common/api/battles/model/actor/BattleActor;)Ljava/util/Set;", "<init>", "()V", "common"})
@SourceDebugExtension(value={"SMAP\nBattleBuilder.kt\nKotlin\n*S Kotlin\n*F\n+ 1 BattleBuilder.kt\ncom/cobblemon/mod/common/battles/BattleActorErrors\n+ 2 fake.kt\nkotlin/jvm/internal/FakeKt\n*L\n1#1,285:1\n1#2:286\n*E\n"})
public class BattleActorErrors
extends HashMap<BattleActor, Set<BattleStartError>> {
    @NotNull
    public Set<BattleStartError> get(@NotNull BattleActor key) {
        Set set2;
        Intrinsics.checkNotNullParameter((Object)key, (String)"key");
        Set set3 = (Set)super.get(key);
        if (set3 == null) {
            Set set4;
            Set it = set4 = (Set)new LinkedHashSet();
            boolean bl = false;
            ((Map)this).put(key, it);
            set2 = set4;
        } else {
            set2 = set3;
        }
        return set2;
    }
}

