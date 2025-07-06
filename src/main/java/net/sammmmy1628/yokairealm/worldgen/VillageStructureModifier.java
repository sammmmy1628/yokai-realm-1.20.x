package net.sammmmy1628.yokairealm.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.data.worldgen.VillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.core.registries.BuiltInRegistries;
import java.util.List;

public class VillageStructureModifier extends PlainVillagePools {
/*
    public static final ResourceLocation VILLAGE_PLAINS_HOUSES = new ResourceLocation("minecraft:village/plains/houses");
    public static final ResourceLocation EGGPLANT_FARM = new ResourceLocation("yokairealm:village/plains/eggplantfarm");

    public static void bootstrap(BootstapContext<StructureTemplatePool> context) {
        Registry<StructureTemplatePool> registry = context.lookup(Registry.STRUCTURE_TEMPLATE_POOL_REGISTRY).orElseThrow();

        // Get the existing village houses pool
        Holder<StructureTemplatePool> housesPool = registry.getHolderOrThrow(VILLAGE_PLAINS_HOUSES);

        // Create new element for your eggplant farm structure
        StructurePoolElement eggplantElement = StructurePoolElement.single(EGGPLANT_FARM.toString(), Projection.RIGID).build();

        // Clone the original pool and add your structure to it
        StructureTemplatePool modifiedPool = new StructureTemplatePool(
                housesPool.value().name(),
                housesPool.value().getFallback(),
                List.copyOf(housesPool.value().templates())
        );

        // Add your structure with weight (e.g., 1)
        modifiedPool.templates().add(Pair.of(eggplantElement, 1));

        // Register the new pool with the same name (overwrite)
        context.register(VILLAGE_PLAINS_HOUSES, modifiedPool);


    }
     */
}