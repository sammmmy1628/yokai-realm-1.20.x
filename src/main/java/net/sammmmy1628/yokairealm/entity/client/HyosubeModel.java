package net.sammmmy1628.yokairealm.entity.client;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class HyosubeModel extends GeoModel<HyosubeEntity> {
    @Override
    public ResourceLocation getAnimationResource(HyosubeEntity entity) {
        return new ResourceLocation("yokairealm", "animations/hyosube_animations.geo.json");
    }

    @Override
    public ResourceLocation getModelResource(HyosubeEntity entity) {
        return new ResourceLocation("yokairealm", "geo/hyosube.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HyosubeEntity entity) {
        return new ResourceLocation("yokairealm", "textures/entity/hyosube.png");
    }

    @Override
    public void setCustomAnimations(HyosubeEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

    }
}
