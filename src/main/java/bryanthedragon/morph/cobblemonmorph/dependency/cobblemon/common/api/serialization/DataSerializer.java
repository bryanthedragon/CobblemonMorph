/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  kotlin.Metadata
 *  net.minecraft.nbt.Tag
 *  org.jetbrains.annotations.NotNull
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.api.serialization;

import com.google.gson.JsonElement;
import kotlin.Metadata;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\n\bf\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u0001*\b\b\u0001\u0010\u0004*\u00020\u00032\u00020\u0005J\u0017\u0010\b\u001a\u00020\u00072\u0006\u0010\u0006\u001a\u00028\u0001H&\u00a2\u0006\u0004\b\b\u0010\tJ\u0017\u0010\u000b\u001a\u00020\u00072\u0006\u0010\n\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\u000b\u0010\fJ\u000f\u0010\r\u001a\u00028\u0001H&\u00a2\u0006\u0004\b\r\u0010\u000eJ\u000f\u0010\u000f\u001a\u00028\u0000H&\u00a2\u0006\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0011"}, d2={"Lcom/cobblemon/mod/common/api/serialization/DataSerializer;", "Lnet/minecraft/nbt/Tag;", "N", "Lcom/google/gson/JsonElement;", "J", "", "json", "", "loadFromJson", "(Lcom/google/gson/JsonElement;)V", "nbt", "loadFromNBT", "(Lnet/minecraft/nbt/Tag;)V", "saveToJson", "()Lcom/google/gson/JsonElement;", "saveToNBT", "()Lnet/minecraft/nbt/Tag;", "common"})
public interface DataSerializer<N extends Tag, J extends JsonElement> {
    public void loadFromNBT(@NotNull N var1);

    @NotNull
    public N saveToNBT();

    public void loadFromJson(@NotNull J var1);

    @NotNull
    public J saveToJson();
}

