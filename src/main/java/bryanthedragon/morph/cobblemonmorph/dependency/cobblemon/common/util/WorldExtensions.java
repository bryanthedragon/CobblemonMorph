    package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BlockAction;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.block.BlockEntityPos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class WorldExtensions {

    private WorldExtensions() {}

    /* ---------------- SOUND & PARTICLES ---------------- */

    @SuppressWarnings("null")
    public static void playSoundServer(
            Level level,
            Vec3 position,
            SoundEvent sound,
            SoundSource category,
            float volume,
            float pitch
    ) {
        if (level instanceof ServerLevel server) {
            server.playSound(
                    null,
                    position.x,
                    position.y,
                    position.z,
                    sound,
                    category,
                    volume,
                    pitch
            );
        }
    }

    @SuppressWarnings("null")
    public static <T extends ParticleOptions> void sendParticlesServer(
            Level level,
            T particle,
            Vec3 position,
            int count,
            Vec3 offset,
            double speed
    ) {
        if (level instanceof ServerLevel server) {
            server.sendParticles(
                    particle,
                    position.x,
                    position.y,
                    position.z,
                    count,
                    offset.x,
                    offset.y,
                    offset.z,
                    speed
            );
        }
    }

    public static BlockPos squeezeWithinBounds(Level level, BlockPos pos) {
        var border = level.getWorldBorder();

        int x = Mth.clamp(pos.getX(), (int) border.getMinX(), (int) border.getMaxX());
        int y = Mth.clamp(pos.getY(), level.getMinBuildHeight(), level.getMaxBuildHeight());
        int z = Mth.clamp(pos.getZ(), (int) border.getMinZ(), (int) border.getMaxZ());

        return new BlockPos(x, y, z);
    }

    public static boolean isBoxLoaded(ServerLevel level, AABB box) {
        int startX = SectionPos.posToSectionCoord(box.minX);
        int startZ = SectionPos.posToSectionCoord(box.minZ);
        int endX   = SectionPos.posToSectionCoord(box.maxX);
        int endZ   = SectionPos.posToSectionCoord(box.maxZ);

        for (int x = startX; x <= endX; x++) {
            for (int z = startZ; z <= endZ; z++) {
                if (!level.areEntitiesLoaded(ChunkPos.asLong(x, z))) {
                    return false;
                }
            }
        }
        return true;
    }


    public static void forAllBlocksIn(BlockGetter world, AABB box, BlockAction action) {
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

        int minX = Mth.floor(box.minX);
        int minY = Mth.floor(box.minY);
        int minZ = Mth.floor(box.minZ);
        int maxX = Mth.floor(box.maxX);
        int maxY = Mth.floor(box.maxY);
        int maxZ = Mth.floor(box.maxZ);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {
                    pos.set(x, y, z);
                    action.accept(world.getBlockState(pos), pos);
                }
            }
        }
    }

    public static <T extends BlockEntity> List<BlockEntityPos<T>> getNearbyBlockEntities(BlockGetter world, AABB box, BlockEntityType<T> type) {
        List<BlockEntityPos<T>> result = new ArrayList<>();

        forAllBlocksIn(world, box, (state, p) -> {
            @SuppressWarnings("null")
            Optional<T> be = world.getBlockEntity(p, type);
            be.ifPresent(entity -> result.add(new BlockEntityPos<>(p.immutable(), entity)));
        });
        return result;
    }

    @SuppressWarnings("null")
    public static boolean[] getWaterAndLavaIn(BlockGetter world, AABB box) {
        boolean[] result = new boolean[2];

        forAllBlocksIn(world, box, (state, pos) -> {
            if (!result[0] && state.getFluidState().is(FluidTags.WATER)) result[0] = true;
            if (!result[1] && state.getFluidState().is(FluidTags.LAVA)) result[1] = true;
        });

        return result;
    }

    @SuppressWarnings("null")
    public static boolean canFit(Entity entity, Vec3 position) {
        AABB box = entity.getBoundingBox().move(position.subtract(entity.position()));
        return entity.level().noCollision(box);
    }

    @SuppressWarnings("null")
    public static Registry<Item> itemRegistry(Level level) {
        return level.registryAccess().registryOrThrow(Registries.ITEM);
    }

    @SuppressWarnings("null")
    public static Registry<Block> blockRegistry(Level level) {
        return level.registryAccess().registryOrThrow(Registries.BLOCK);
    }

    @SuppressWarnings("null")
    public static Registry<Fluid> fluidRegistry(Level level) {
        return level.registryAccess().registryOrThrow(Registries.FLUID);
    }
}
