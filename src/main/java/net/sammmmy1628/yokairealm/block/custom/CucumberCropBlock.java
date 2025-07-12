package net.sammmmy1628.yokairealm.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.sammmmy1628.yokairealm.item.YokaiItems;

public class CucumberCropBlock extends CropBlock {
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 5);

    //split stages 0-1 lower, 2-5 lower+upper
    private static final int FIRST_STAGE_MAX_AGE = 1; // ages 0,1 lower stage
    private static final int MAX_AGE = 5;

    public CucumberCropBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) return;

        if (!level.isAreaLoaded(pos, 1)) return;
        if (level.getRawBrightness(pos, 0) >= 9) {
            int age = state.getValue(AGE);
            if (age < MAX_AGE && net.minecraftforge.common.ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(5) == 0)) {
                grow(level, pos, state);
                net.minecraftforge.common.ForgeHooks.onCropsGrowPost(level, pos, state);
            }
        }
    }

    private void grow(ServerLevel level, BlockPos pos, BlockState state) {
        int currentAge = state.getValue(AGE);
        int newAge = Math.min(currentAge + 1, MAX_AGE);

        BlockPos above = pos.above();

        BlockState newLower = this.defaultBlockState()
                .setValue(AGE, newAge)
                .setValue(HALF, DoubleBlockHalf.LOWER);
        level.setBlock(pos, newLower, 3);

        if (newAge > FIRST_STAGE_MAX_AGE) {
            BlockState newUpper = this.defaultBlockState()
                    .setValue(AGE, newAge)
                    .setValue(HALF, DoubleBlockHalf.UPPER);

            if (!level.getBlockState(above).is(this)) {
                level.setBlock(above, newUpper, 3);
            } else {
                level.setBlock(above, newUpper, 3);
            }
        } else {
            if (level.getBlockState(above).getBlock() == this) {
                level.setBlock(above, Blocks.AIR.defaultBlockState(), 3);
            }
        }
    }

    @Override
    public void growCrops(Level level, BlockPos pos, BlockState state) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            pos = pos.below();
            state = level.getBlockState(pos);
            if (!state.is(this)) return;
        }

        if (state.getValue(AGE) < MAX_AGE) {
            grow((ServerLevel)level, pos, state);
        }
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return YokaiItems.CUCUMBER.get();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, HALF);
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            BlockState below = level.getBlockState(pos.below());
            return below.is(this) && below.getValue(HALF) == DoubleBlockHalf.LOWER;
        } else {
            return super.canSurvive(state, level, pos);
        }
    }

    @Override
    public void playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        DoubleBlockHalf half = state.getValue(HALF);
        BlockPos otherPos = half == DoubleBlockHalf.LOWER ? pos.above() : pos.below();

        BlockState otherState = level.getBlockState(otherPos);
        if (otherState.is(this) && otherState.getValue(HALF) != half) {
            level.destroyBlock(otherPos, !player.isCreative());
        }

        super.playerWillDestroy(level, pos, state, player);
    }
}
