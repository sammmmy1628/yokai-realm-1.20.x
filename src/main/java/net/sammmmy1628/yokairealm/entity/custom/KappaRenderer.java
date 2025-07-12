package net.sammmmy1628.yokairealm.entity.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.sammmmy1628.yokairealm.YokaiRealm;
import net.sammmmy1628.yokairealm.entity.client.KappaEntity;
import net.sammmmy1628.yokairealm.entity.client.KappaModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class KappaRenderer extends GeoEntityRenderer<KappaEntity> {
    public KappaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new KappaModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public ResourceLocation getTextureLocation(KappaEntity animatable) {
        return new ResourceLocation(YokaiRealm.MOD_ID, "textures/entity/" + animatable.getTexture() + ".png");
    }


    @Override
    public void render(KappaEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        if (pEntity.isBaby()) {
            pMatrixStack.scale(0.4f,0.4f,0.4f);
        }
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}