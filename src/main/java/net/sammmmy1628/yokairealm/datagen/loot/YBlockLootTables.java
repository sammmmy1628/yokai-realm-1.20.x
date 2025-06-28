package net.sammmmy1628.yokairealm.datagen.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.RegistryObject;
import net.sammmmy1628.yokairealm.block.YokaiBlocks;
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
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(EggplantCropBlock.AGE, 5));

        this.add(YokaiBlocks.EGGPLANT_CROP.get(), createCropDrops(YokaiBlocks.EGGPLANT_CROP.get(), YokaiItems.EGGPLANT.get(),
                YokaiItems.EGGPLANT_SEED.get(), lootitemcondition$builder));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return YokaiBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

}
