package net.sammmmy1628.yokairealm.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.sammmmy1628.yokairealm.datagen.loot.YBlockLootTables;

import java.util.List;
import java.util.Set;


public class YLootTableProvider {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(YBlockLootTables::new, LootContextParamSets.BLOCK)
        ));
    }
}
