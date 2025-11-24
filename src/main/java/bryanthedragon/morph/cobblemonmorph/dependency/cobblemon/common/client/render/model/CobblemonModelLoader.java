/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  kotlin.Metadata
 *  kotlin.NotImplementedError
 *  net.minecraft.client.resources.model.BakedModel
 *  net.minecraft.client.resources.model.ModelBaker
 *  net.minecraft.client.resources.model.ModelState
 *  net.minecraft.client.resources.model.UnbakedModel
 *  net.minecraft.resources.ResourceLocation
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.client.render.model;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001:\u0001\u000bB\u0007\u00a2\u0006\u0004\b\t\u0010\nR#\u0010\u0005\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00028\u0006\u00a2\u0006\f\n\u0004\b\u0005\u0010\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\f"}, d2={"Lcom/cobblemon/mod/common/client/render/model/CobblemonModelLoader;", "", "", "Lnet/minecraft/resources/ResourceLocation;", "Lnet/minecraft/client/resources/model/BakedModel;", "bakedModelMap", "Ljava/util/Map;", "getBakedModelMap", "()Ljava/util/Map;", "<init>", "()V", "CobblemonBaker", "common"})
public final class CobblemonModelLoader {
    @NotNull
    private final Map<ResourceLocation, BakedModel> bakedModelMap = new LinkedHashMap();

    @NotNull
    public final Map<ResourceLocation, BakedModel> getBakedModelMap() {
        return this.bakedModelMap;
    }

    @Metadata(mv={1, 8, 0}, k=1, xi=48, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0007\u00a2\u0006\u0004\b\f\u0010\rJ%\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\u0010\u0003\u001a\u0004\u0018\u00010\u00022\b\u0010\u0005\u001a\u0004\u0018\u00010\u0004H\u0016\u00a2\u0006\u0004\b\u0007\u0010\bJ\u0019\u0010\n\u001a\u00020\t2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0002H\u0016\u00a2\u0006\u0004\b\n\u0010\u000b\u00a8\u0006\u000e"}, d2={"Lcom/cobblemon/mod/common/client/render/model/CobblemonModelLoader$CobblemonBaker;", "Lnet/minecraft/client/resources/model/ModelBaker;", "Lnet/minecraft/resources/ResourceLocation;", "id", "Lnet/minecraft/client/resources/model/ModelState;", "settings", "Lnet/minecraft/client/resources/model/BakedModel;", "bake", "(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/client/resources/model/ModelState;)Lnet/minecraft/client/resources/model/BakedModel;", "Lnet/minecraft/client/resources/model/UnbakedModel;", "getOrLoadModel", "(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/resources/model/UnbakedModel;", "<init>", "()V", "common"})
    public static final class CobblemonBaker
    implements ModelBaker {
        @NotNull
        public UnbakedModel m_245361_(@Nullable ResourceLocation id) {
            String string = "Not yet implemented";
            throw new NotImplementedError("An operation is not implemented: " + string);
        }

        @Nullable
        public BakedModel m_245240_(@Nullable ResourceLocation id, @Nullable ModelState settings) {
            UnbakedModel unbakedModel = this.m_245361_(id);
            BakedModel bakedModel = unbakedModel.m_7611_((ModelBaker)this, null, settings, id);
            String string = "Not yet implemented";
            throw new NotImplementedError("An operation is not implemented: " + string);
        }
    }
}

