package net.sammmmy1628.yokairealm.datagen.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import net.sammmmy1628.yokairealm.block.YokaiBlocks;
import net.sammmmy1628.yokairealm.block.custom.CucumberCropBlock;
import net.sammmmy1628.yokairealm.block.custom.EggplantCropBlock;
import net.sammmmy1628.yokairealm.item.YokaiItems;

import java.util.Set;

public class YBlockLootTables extends BlockLootSubProvider {
    public YBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        LootItemCondition.Builder lootitemcondition$builder = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(YokaiBlocks.EGGPLANT_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(EggplantCropBlock.AGE, 5)
                        .hasProperty(EggplantCropBlock.HALF, DoubleBlockHalf.LOWER));

        this.add(YokaiBlocks.EGGPLANT_CROP.get(), createCropDrops(YokaiBlocks.EGGPLANT_CROP.get(), YokaiItems.EGGPLANT.get(),
                YokaiItems.EGGPLANT_SEED.get(), lootitemcondition$builder));

        LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition
                .hasBlockStateProperties(YokaiBlocks.CUCUMBER_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties()
                        .hasProperty(CucumberCropBlock.AGE, 5)
                        .hasProperty(EggplantCropBlock.HALF, DoubleBlockHalf.LOWER));

        this.add(YokaiBlocks.CUCUMBER_CROP.get(), createCropDrops(YokaiBlocks.CUCUMBER_CROP.get(), YokaiItems.CUCUMBER.get(),
                YokaiItems.CUCUMBER.get(), lootitemcondition$builder2));//cucumber_seed
    }

    @Override
    protected LootTable.Builder createCropDrops(Block crop, Item drop, Item seeds, LootItemCondition.Builder condition) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(drop)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 2.0F))))
                        .when(condition))
                .withPool(LootPool.lootPool()
                        .add(LootItem.lootTableItem(seeds)
                                .apply(SetItemCountFunction.setCount(ConstantValue.exactly(2))))
                        .when(condition)
                );
    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return YokaiBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
