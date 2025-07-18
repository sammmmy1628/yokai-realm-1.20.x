package net.sammmmy1628.yokairealm.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sammmmy1628.yokairealm.YokaiRealm;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = YokaiRealm.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new YRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), YLootTableProvider.create(packOutput));

        generator.addProvider(event.includeClient(), new YBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new YItemModelProvider(packOutput, existingFileHelper));

        YBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new YBlockTagGenerator(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new YItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));

        generator.addProvider(event.includeServer(), new YGlobalLootModifiersProvider(packOutput));
    }
}