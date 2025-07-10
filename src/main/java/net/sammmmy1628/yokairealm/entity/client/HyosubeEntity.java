package net.sammmmy1628.yokairealm.entity.client;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.level.Level;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class HyosubeEntity extends PathfinderMob implements GeoEntity {

    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public HyosubeEntity(EntityType<? extends HyosubeEntity> type, Level level) {
        super(type, level);
    }

    @Override
    public void registerControllers(final AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geoCache;
    }
}