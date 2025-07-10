package net.sammmmy1628.yokairealm.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sammmmy1628.yokairealm.YokaiRealm;
import net.sammmmy1628.yokairealm.block.YokaiBlocks;
import net.sammmmy1628.yokairealm.entity.YokaiEntities;

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

    public static final RegistryObject<Item> EGGPLANT_SEED = ITEMS.register("eggplant_seed",
            () -> new ItemNameBlockItem(YokaiBlocks.EGGPLANT_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> KAPPA_SPAWN_EGG = ITEMS.register("kappa_spawn_egg",
            () -> new ForgeSpawnEggItem(YokaiEntities.KAPPA, 0x003300, 0x003399, new Item.Properties()));

    public static final RegistryObject<Item> HYOSUBE_SPAWN_EGG = ITEMS.register("hyosube_spawn_egg",
            () -> new ForgeSpawnEggItem(YokaiEntities.HYOSUBE, 0xfad1aa, 0x000000, new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}