package net.sammmmy1628.yokairealm.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sammmmy1628.yokairealm.YokaiRealm;
import net.sammmmy1628.yokairealm.entity.client.KappaEntity;

import static software.bernie.example.registry.EntityRegistry.ENTITIES;

public class YokaiEntities {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, YokaiRealm.MOD_ID);

    public static final RegistryObject<EntityType<KappaEntity>> KAPPA =
            ENTITIES.register("kappa", () ->
                    EntityType.Builder.<KappaEntity>of(KappaEntity::new, MobCategory.MONSTER)
                            .sized(0.6f, 1.8f) // set size accordingly
                            .build("kappa"));



    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
