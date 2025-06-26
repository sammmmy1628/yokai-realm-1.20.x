package net.sammmmy1628.yokairealm.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sammmmy1628.yokairealm.YokaiRealm;

public class YokaiItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, YokaiRealm.MOD_ID);

    public static final RegistryObject<Item> EGGPLANT = ITEMS.register("eggplant",
            () -> new Item(new Item.Properties().food(YokaiFood.EGGPLANT)));
    public static final RegistryObject<Item> CUCUMBER = ITEMS.register("cucumber",
            () -> new Item(new Item.Properties().food(YokaiFood.CUCUMBER)));

    public static final RegistryObject<Item> KAPPA_SCUTE = ITEMS.register("kappa_scute",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MIND_LIQUID = ITEMS.register("mind_liquid",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}