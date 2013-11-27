/*
 * Copyright (C) 2004-2013 L2J Server
 * 
 * This file is part of L2J Server.
 * 
 * L2J Server is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2j.geodriver.regions;

import java.nio.ByteBuffer;

import com.l2j.geodriver.IBlock;
import com.l2j.geodriver.IRegion;
import com.l2j.geodriver.blocks.ComplexBlock;
import com.l2j.geodriver.blocks.FlatBlock;
import com.l2j.geodriver.blocks.MultilayerBlock;
import com.l2jserver.gameserver.geoengine.Direction;

/**
 * @author FBIagent
 */
public final class NonNullRegion implements IRegion
{
	private final IBlock[] _blocks = new IBlock[IRegion.REGION_BLOCKS];
	
	/**
	 * Initializes a new instance of this region reading from the specified<br>
	 * buffer.
	 * @param bb the buffer
	 */
	public NonNullRegion(ByteBuffer bb)
	{
		int blockType;
		for (int regionBlockOffset = 0; regionBlockOffset < IRegion.REGION_BLOCKS; ++regionBlockOffset)
		{
			blockType = bb.get();
			if (blockType == IBlock.Type.FLAT.ordinal())
			{
				_blocks[regionBlockOffset] = new FlatBlock(bb);
			}
			else if (blockType == IBlock.Type.COMPLEX.ordinal())
			{
				_blocks[regionBlockOffset] = new ComplexBlock(bb);
			}
			else if (blockType == IBlock.Type.MULTILAYER.ordinal())
			{
				_blocks[regionBlockOffset] = new MultilayerBlock(bb);
			}
			else
			{
				throw new RuntimeException("L2JGeoDriver: Invalid block type!");
			}
		}
	}
	
	private IBlock _getBlock(int geoX, int geoY)
	{
		return _blocks[(((geoX / IBlock.BLOCK_CELLS_X) % IRegion.REGION_BLOCKS_X) * IRegion.REGION_BLOCKS_Y) + ((geoY / IBlock.BLOCK_CELLS_Y) % IRegion.REGION_BLOCKS_Y)];
	}
	
	@Override
	public boolean hasGeoPos(int geoX, int geoY)
	{
		return _getBlock(geoX, geoY).hasGeoPos(geoX, geoY);
	}
	
	@Override
	public int getNearestZ(int geoX, int geoY, int worldZ)
	{
		return _getBlock(geoX, geoY).getNearestZ(geoX, geoY, worldZ);
	}
	
	@Override
	public int getNextLowerZ(int geoX, int geoY, int worldZ)
	{
		return _getBlock(geoX, geoY).getNextLowerZ(geoX, geoY, worldZ);
	}
	
	@Override
	public int getNextHigherZ(int geoX, int geoY, int worldZ)
	{
		return _getBlock(geoX, geoY).getNextHigherZ(geoX, geoY, worldZ);
	}
	
	@Override
	public boolean canMoveIntoDirections(int geoX, int geoY, int worldZ, Direction first, Direction... more)
	{
		return _getBlock(geoX, geoY).canMoveIntoDirections(geoX, geoY, worldZ, first, more);
	}
	
	@Override
	public boolean canMoveIntoAllDirections(int geoX, int geoY, int worldZ)
	{
		return _getBlock(geoX, geoY).canMoveIntoAllDirections(geoX, geoY, worldZ);
	}
}
