package net.sammmmy1628.yokairealm.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sammmmy1628.yokairealm.YokaiRealm;
import net.sammmmy1628.yokairealm.block.YokaiBlocks;
import net.sammmmy1628.yokairealm.block.custom.EggplantCropBlock;

import java.util.function.Function;

public class YBlockStateProvider extends BlockStateProvider {
    public YBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, YokaiRealm.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        makeEggplantCrop(((CropBlock) YokaiBlocks.EGGPLANT_CROP.get()), "eggplant_stage_", 7);
    }

    public void makeEggplantCrop(CropBlock block, String stageName, int maxAge) {
        for (int age = 0; age < maxAge; age++) {
            String textureName = "block/" + stageName + age;
            models().cross(stageName + age, modLoc(textureName))
                    .renderType("cutout");
        }

        getVariantBuilder(block).forAllStates(state -> {
            int age = state.getValue(EggplantCropBlock.AGE);
            return ConfiguredModel.builder()
                    .modelFile(models().getExistingFile(modLoc("block/" + stageName + age)))
                    .build();
        });
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), new ResourceLocation("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(YokaiRealm.MOD_ID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}