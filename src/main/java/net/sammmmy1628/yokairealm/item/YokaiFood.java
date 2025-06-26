package net.sammmmy1628.yokairealm.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class YokaiFood {

    public static final FoodProperties EGGPLANT = builder(3, 2.5F).build();
    public static final FoodProperties CUCUMBER = builder(4, 1.2F).build();


    private static FoodProperties.Builder builder(int nutrition, float saturation) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturation / nutrition);

    }

}
