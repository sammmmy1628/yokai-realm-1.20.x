package net.sammmmy1628.yokairealm.entity.client;

import software.bernie.geckolib.model.data.EntityModelData;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.constant.DataTickets;

import net.minecraft.util.Mth;
import net.minecraft.resources.ResourceLocation;

public class KappaModel extends GeoModel<KappaEntity> {
    @Override
    public ResourceLocation getAnimationResource(KappaEntity entity) {
        return new ResourceLocation("yokairealm", "animations/kappa_animations.geo.json");
    }

    @Override
    public ResourceLocation getModelResource(KappaEntity entity) {
        return new ResourceLocation("yokairealm", "geo/kappa.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(KappaEntity entity) {
        return new ResourceLocation("yokairealm", "textures/entity/kappa/" + entity.getTexture() + ".png");
    }

    @Override
    public void setCustomAnimations(KappaEntity animatable, long instanceId, AnimationState animationState) {
        CoreGeoBone head = getAnimationProcessor().getBone("head");
        if (head != null) {
            EntityModelData entityData = (EntityModelData) animationState.getData(DataTickets.ENTITY_MODEL_DATA);
            head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
            head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
        }

    }
}