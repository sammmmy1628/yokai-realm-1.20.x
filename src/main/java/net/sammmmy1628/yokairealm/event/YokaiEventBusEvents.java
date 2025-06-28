package net.sammmmy1628.yokairealm.event;

import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sammmmy1628.yokairealm.YokaiRealm;
import net.sammmmy1628.yokairealm.entity.YokaiEntities;
import net.sammmmy1628.yokairealm.entity.client.KappaEntity;

@Mod.EventBusSubscriber(modid = YokaiRealm.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class YokaiEventBusEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(YokaiEntities.KAPPA.get(), KappaEntity.createAttributes().build());
    }
}
