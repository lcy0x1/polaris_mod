package dev.lcy0x1.base.block.impl;

import com.tterrag.registrate.util.entry.TileEntityEntry;
import dev.lcy0x1.base.block.mult.OnClickBlockMethod;
import dev.lcy0x1.base.block.one.TitleEntityBlockMethod;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class TitleEntityBlockMethodImpl implements TitleEntityBlockMethod, OnClickBlockMethod {

	private final Supplier<TileEntityEntry<?>> f;

	public TitleEntityBlockMethodImpl(Supplier<TileEntityEntry<?>> f) {
		this.f = f;
	}

	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return f.get().create();
	}

	@Override
	public ActionResultType onClick(BlockState bs, World w, BlockPos pos, PlayerEntity pl, Hand h, BlockRayTraceResult r) {
		TileEntity te = w.getBlockEntity(pos);
		if (w.isClientSide())
			return te instanceof INamedContainerProvider ? ActionResultType.SUCCESS : ActionResultType.PASS;
		if (te instanceof INamedContainerProvider) {
			pl.openMenu((INamedContainerProvider) te);
			return ActionResultType.SUCCESS;
		}
		return ActionResultType.PASS;
	}

}
