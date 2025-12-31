package bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.common.entity.trace;

public class EntityTraceResult<T : Entity>(
    val location: Vec3,
    val entities: Iterable<T>
)
