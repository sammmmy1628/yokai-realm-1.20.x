package net.sammmmy1628.yokairealm.entity.custom;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class KappaRenderer extends GeoEntityRenderer<KappaEntity> {
    public KappaRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new KappaModel());
        this.shadowRadius = 0.5f;
    }
}