package net.sammmmy1628.yokairealm.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.sammmmy1628.yokairealm.YokaiRealm;

import javax.annotation.Nullable;
import java.util.concurrent.CompletableFuture;

public class YBlockTagGenerator extends BlockTagsProvider {
    public YBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, YokaiRealm.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

    }
}
