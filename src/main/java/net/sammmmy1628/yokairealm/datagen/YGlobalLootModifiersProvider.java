package net.sammmmy1628.yokairealm.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.sammmmy1628.yokairealm.YokaiRealm;

public class YGlobalLootModifiersProvider extends GlobalLootModifierProvider {
    public YGlobalLootModifiersProvider(PackOutput output) {
        super(output, YokaiRealm.MOD_ID);
    }

    @Override
    protected void start() {

    }
}
