package net.sammmmy1628.yokairealm.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.sammmmy1628.yokairealm.YokaiRealm;

public class YokaiCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, YokaiRealm.MOD_ID);

    public static final RegistryObject<CreativeModeTab> YOKAI_TAB = CREATIVE_MODE_TABS.register("yokai_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(YokaiItems.EGGPLANT.get()))
                    .title(Component.translatable("creativetab.yokai_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(YokaiItems.EGGPLANT.get());
                        output.accept(YokaiItems.CUCUMBER.get());
                        output.accept(YokaiItems.KAPPA_SCUTE.get());
                        output.accept(YokaiItems.MIND_LIQUID.get());
                        output.accept(YokaiItems.EGGPLANT_SEED.get());

                        output.accept(YokaiItems.KAPPA_SPAWN_EGG.get());
                        output.accept(YokaiItems.HYOSUBE_SPAWN_EGG.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
