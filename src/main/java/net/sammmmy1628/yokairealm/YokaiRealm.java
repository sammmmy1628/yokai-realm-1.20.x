package net.sammmmy1628.yokairealm;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.sammmmy1628.yokairealm.entity.YokaiEntities;
import net.sammmmy1628.yokairealm.item.YokaiCreativeModeTabs;
import net.sammmmy1628.yokairealm.item.YokaiItems;
import org.slf4j.Logger;
import software.bernie.geckolib.GeckoLib;

@Mod(YokaiRealm.MOD_ID)
public class YokaiRealm
{
    public static final String MOD_ID = "yokairealm";
    private static final Logger LOGGER = LogUtils.getLogger();


    public YokaiRealm(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        YokaiCreativeModeTabs.register(modEventBus);

        YokaiItems.register(modEventBus);
        YokaiEntities.register(modEventBus);


        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);

        GeckoLib.initialize();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ComposterBlock.COMPOSTABLES.put(YokaiItems.EGGPLANT.get(), 0.65F);
            ComposterBlock.COMPOSTABLES.put(YokaiItems.CUCUMBER.get(), 0.65F);
        });

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
