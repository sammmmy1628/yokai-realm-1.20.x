package net.sammmmy1628.yokairealm.entity.client;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.sammmmy1628.yokairealm.entity.YokaiEntities;
import net.sammmmy1628.yokairealm.item.YokaiItems;
import software.bernie.geckolib.util.GeckoLibUtil;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.GeoEntity;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.network.PlayMessages;
import net.minecraftforge.network.NetworkHooks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

import java.util.UUID;

public class KappaEntity extends PathfinderMob implements GeoEntity {
    public static final EntityDataAccessor<Boolean> SHOOT = SynchedEntityData.defineId(KappaEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<String> ANIMATION = SynchedEntityData.defineId(KappaEntity.class, EntityDataSerializers.STRING);
    public static final EntityDataAccessor<String> TEXTURE = SynchedEntityData.defineId(KappaEntity.class, EntityDataSerializers.STRING);

    // Attribute‑modifier UUIDs
    private static final UUID WATER_SPEED_MOD = UUID.fromString("9dc106aa-26bb-4a21-8b85-1209e6e6a901");
    private static final UUID WATER_DAMAGE_MOD = UUID.fromString("fd7e62c4-e09a-437e-bb51-a913d8d7f1d5");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    private boolean swinging;
    private long lastSwing;
    public String animationprocedure = "empty";
    private String prevAnim = "empty";

    private int tradeCooldown = 0;
    private ItemStack pendingTrade = ItemStack.EMPTY;


    public KappaEntity(PlayMessages.SpawnEntity packet, Level world) {
        this(YokaiEntities.KAPPA.get(), world);
    }

    public KappaEntity(EntityType<KappaEntity> type, Level world) {
        super(type, world);
        xpReward = 5;
        setNoAi(false);
        setMaxUpStep(0.6f);
    }

    /* --------------------------- Synced data --------------------------- */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(SHOOT, false);
        this.entityData.define(ANIMATION, "undefined");
        this.entityData.define(TEXTURE, "kappa_reimagined2");
    }

    public void setTexture(String texture) {
        this.entityData.set(TEXTURE, texture);
    }

    public String getTexture() {
        return this.entityData.get(TEXTURE);
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    /* --------------------------- AI Goals --------------------------- */
    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.2, true) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this).setAlertOthers());
        this.goalSelector.addGoal(3, new TryFindWaterGoal(this));
        this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1, 40));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new FloatGoal(this));
        this.goalSelector.addGoal(8, new TemptGoal(this, 1.5, Ingredient.of(YokaiItems.CUCUMBER.get()), false));
        // Day‑neutral / Night‑aggressive

        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false,
                player -> !this.level().isDay()));


    }

    /* --------------------------- Sounds --------------------------- */
    @Override
    public MobType getMobType() {
        return MobType.WATER;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.hurt"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.generic.death"));
    }

    /* --------------------------- Persistence --------------------------- */
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("Texture", this.getTexture());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("Texture"))
            this.setTexture(compound.getString("Texture"));
    }

    /* --------------------------- Spawning rules --------------------------- */
    public static void init() {
        SpawnPlacements.register(YokaiEntities.KAPPA.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                (type, level, reason, pos, random) -> {
                    // biome whitelist – adjust if needed
                    boolean rightBiome = level.getBiome(pos).is(Biomes.RIVER);
                    if (!rightBiome) return false;

                    // within 20 blocks of water
                    boolean nearWater = BlockPos.betweenClosedStream(pos.offset(-20, -4, -20), pos.offset(20, 4, 20))
                            .anyMatch(p -> level.getFluidState(p).is(Fluids.WATER));
                    return nearWater && level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON);
                });
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.2)
                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.ARMOR, 3)
                .add(Attributes.ATTACK_DAMAGE, 5)
                .add(Attributes.FOLLOW_RANGE, 16);
    }

    /* --------------------------- Animations --------------------------- */
    private PlayState movementPredicate(AnimationState event) {
        if (this.animationprocedure.equals("empty")) {
            if ((event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) && !this.isAggressive()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            }
            if (this.isDeadOrDying()) {
                return event.setAndContinue(RawAnimation.begin().thenPlay("death"));
            }
            if (this.isInWaterOrBubble()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("float"));
            }
            if (this.isAggressive() && event.isMoving()) {
                return event.setAndContinue(RawAnimation.begin().thenLoop("agression"));
            }
            return event.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }
        return PlayState.STOP;
    }

    private PlayState attackingPredicate(AnimationState event) {
        if (getAttackAnim(event.getPartialTick()) > 0f && !this.swinging) {
            this.swinging = true;
            this.lastSwing = level().getGameTime();
        }
        if (this.swinging && this.lastSwing + 7L <= level().getGameTime()) {
            this.swinging = false;
        }
        if (this.swinging && event.getController().getAnimationState() == AnimationController.State.STOPPED) {
            event.getController().forceAnimationReset();
            return event.setAndContinue(RawAnimation.begin().thenPlay("attack"));
        }
        return PlayState.CONTINUE;
    }

    private PlayState procedurePredicate(AnimationState event) {
        if ((!animationprocedure.equals("empty") && event.getController().getAnimationState() == AnimationController.State.STOPPED) || (!this.animationprocedure.equals(prevAnim) && !this.animationprocedure.equals("empty"))) {
            if (!this.animationprocedure.equals(prevAnim))
                event.getController().forceAnimationReset();
            event.getController().setAnimation(RawAnimation.begin().thenPlay(this.animationprocedure));
            if (event.getController().getAnimationState() == AnimationController.State.STOPPED) {
                this.animationprocedure = "empty";
                event.getController().forceAnimationReset();
            }
        } else if (animationprocedure.equals("empty")) {
            prevAnim = "empty";
            return PlayState.STOP;
        }
        prevAnim = this.animationprocedure;
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        data.add(new AnimationController<>(this, "movement", 3, this::movementPredicate));
        data.add(new AnimationController<>(this, "attacking", 3, this::attackingPredicate));
        data.add(new AnimationController<>(this, "procedure", 3, this::procedurePredicate));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    /* --------------------------- Tick logic --------------------------- */
    @Override
    public void aiStep() {
        super.aiStep();
        this.updateSwingTime();
        // Detect item entities nearby
        if (tradeCooldown <= 0 && pendingTrade.isEmpty() && !this.level().isClientSide) {
            this.level().getEntitiesOfClass(ItemEntity.class, this.getBoundingBox().inflate(1.5), item ->
                            item.getItem().is(YokaiItems.CUCUMBER.get()) && item.tickCount > 1) // NEW condition
                    .stream().findFirst().ifPresent(itemEntity -> {
                        // Consume the cucumber
                        pendingTrade = itemEntity.getItem().copy();
                        itemEntity.getItem().shrink(1);
                        if (itemEntity.getItem().isEmpty()) itemEntity.discard();

                        // Begin trade animation
                        this.animationprocedure = "trade";
                        this.tradeCooldown = 40;
                        this.setXRot(-60);
                    });

        }

// Handle trading delay and reward drop
        if (tradeCooldown > 0) {
            this.tradeCooldown--;

            this.getNavigation().stop();
            this.setDeltaMovement(0, getDeltaMovement().y, 0);

            if (tradeCooldown == 1 && !this.level().isClientSide && !pendingTrade.isEmpty()) {
                // Drop reward
                ItemStack reward = switch (this.random.nextInt(5)) {
                    case 0 -> new ItemStack(Items.LILY_PAD);
                    case 1 -> new ItemStack(Items.COD);
                    case 2 -> new ItemStack(Items.SALMON);
                    case 3 -> new ItemStack(Items.PRISMARINE_CRYSTALS);
                    default -> new ItemStack(Items.INK_SAC);
                };
                this.spawnAtLocation(reward);
                pendingTrade = ItemStack.EMPTY;
                this.setXRot(0);
            }
            this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX(), this.getY() + 1, this.getZ(), 0.0, 0.2, 0.0);
            this.level().playSound(null, this.blockPosition(), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.villager.yes")),
                    this.getSoundSource(), 1.0f, 1.0f);

        }

    }

    /* --------------------------- Hurt / particles --------------------------- */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        boolean res = super.hurt(source, amount);
        if (res && !this.level().isClientSide) {
            RandomSource r = this.getRandom();
            for (int i = 0; i < 6; ++i) {
                this.level().addParticle(ParticleTypes.SPLASH,
                        this.getX() + (r.nextDouble() - 0.5) * 0.6,
                        this.getY() + 1.2,
                        this.getZ() + (r.nextDouble() - 0.5) * 0.6,
                        0.0, 0.1 + r.nextDouble() * 0.1, 0.0);
            }
        }
        return res;
    }

    /* --------------------------- Head lock during trade --------------------------- */
    @Override
    public void travel(net.minecraft.world.phys.Vec3 travelVector) {
        if ("trade".equals(this.animationprocedure)) {
            this.setYHeadRot(this.yBodyRot);
            this.setXRot(0);
        }
        super.travel(travelVector);
    }

    /* --------------------------- Death handling --------------------------- */
    @Override
    protected void tickDeath() {
        ++this.deathTime;
        if (this.deathTime >= 50) {
            this.remove(RemovalReason.KILLED);
            this.dropExperience();
        }
    }

    /* --------------------------- Animation sync helpers --------------------------- */
    public String getSyncedAnimation() {
        return this.entityData.get(ANIMATION);
    }

    public void setAnimation(String animation) {
        this.entityData.set(ANIMATION, animation);
    }
}