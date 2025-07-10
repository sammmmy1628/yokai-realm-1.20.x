package net.sammmmy1628.yokairealm;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
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
import net.sammmmy1628.yokairealm.block.YokaiBlocks;
import net.sammmmy1628.yokairealm.entity.YokaiEntities;
import net.sammmmy1628.yokairealm.entity.custom.HyosubeRenderer;
import net.sammmmy1628.yokairealm.entity.custom.KappaRenderer;
import net.sammmmy1628.yokairealm.item.YokaiCreativeModeTabs;
import net.sammmmy1628.yokairealm.item.YokaiItems;
import net.sammmmy1628.yokairealm.loot.YokaiLootModifiers;
import org.slf4j.Logger;

@Mod(YokaiRealm.MOD_ID)
public class YokaiRealm
{
    public static final String MOD_ID = "yokairealm";
    public static final Logger LOGGER = LogUtils.getLogger();


    public YokaiRealm() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        YokaiCreativeModeTabs.register(modEventBus);

        YokaiItems.register(modEventBus);
        YokaiBlocks.register(modEventBus);

        YokaiEntities.register(modEventBus);
        YokaiLootModifiers.register(modEventBus);


        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
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
            EntityRenderers.register(YokaiEntities.KAPPA.get(), KappaRenderer::new);
            EntityRenderers.register(YokaiEntities.HYOSUBE.get(), HyosubeRenderer::new);

            ItemBlockRenderTypes.setRenderLayer(YokaiBlocks.EGGPLANT_CROP.get(), RenderType.cutout());
        }
    }
}
