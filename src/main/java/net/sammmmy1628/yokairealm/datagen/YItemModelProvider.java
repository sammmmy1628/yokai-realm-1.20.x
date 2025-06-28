package net.sammmmy1628.yokairealm.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.sammmmy1628.yokairealm.YokaiRealm;
import net.sammmmy1628.yokairealm.item.YokaiItems;

public class YItemModelProvider extends ItemModelProvider {

    public YItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, YokaiRealm.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(YokaiItems.CUCUMBER);
        simpleItem(YokaiItems.EGGPLANT);
        simpleItem(YokaiItems.KAPPA_SCUTE);
        simpleItem(YokaiItems.MIND_LIQUID);
        simpleItem(YokaiItems.EGGPLANT_SEED);


        withExistingParent(YokaiItems.KAPPA_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(YokaiRealm.MOD_ID,"item/" + item.getId().getPath()));
    }
}
