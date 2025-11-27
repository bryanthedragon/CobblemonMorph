/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.ranges.IntRange
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.statuses.persistent;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.pokemon.status.PersistentStatus;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util.MiscUtils;
import kotlin.Metadata;
import kotlin.ranges.IntRange;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\u0002\u0010\u0003\u00a8\u0006\u0004"}, d2={"Lcom/cobblemon/mod/common/pokemon/status/statuses/persistent/ParalysisStatus;", "Lcom/cobblemon/mod/common/pokemon/status/PersistentStatus;", "<init>", "()V", "common"})
public final class ParalysisStatus
extends PersistentStatus {
    public ParalysisStatus() {
        super(MiscUtils.cobblemonResource("paralysis"), "par", "cobblemon.status.paralysis.apply", "cobblemon.status.paralysis.cure", new IntRange(180, 300));
    }
}

