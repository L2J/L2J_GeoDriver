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
package com.l2j.geodriver.blocks;

import java.nio.ByteBuffer;

import com.l2j.geodriver.Cell;
import com.l2j.geodriver.IBlock;
import com.l2j.geodriver.Utils;
import com.l2jserver.gameserver.geoengine.Direction;

/**
 * @author FBIagent
 */
public class MultilayerBlock implements IBlock
{
	private final int _bbPos;
	private final ByteBuffer _bb;
	
	/**
	 * Initializes a new instance of this block reading the specified buffer.
	 * @param bb the buffer
	 */
	public MultilayerBlock(ByteBuffer bb)
	{
		_bbPos = bb.position();
		_bb = bb;
		
		byte numLayers;
		for (int blockCellOffset = 0; blockCellOffset < IBlock.BLOCK_CELLS; ++blockCellOffset)
		{
			numLayers = _bb.get();
			if ((numLayers <= 0) || (numLayers > 125))
			{
				throw new RuntimeException("L2JGeoDriver: Geo file corrupted! Invalid layers count!");
			}
			
			_bb.position(_bb.position() + (numLayers * 2));
		}
	}
	
	private short _getNearestLayer(int geoX, int geoY, int worldZ)
	{
		int index = _getCellIndex(geoX, geoY);
		byte numLayers = _bb.get(index);
		++index;
		
		// 1 layer at least was required on loading so this is set at least once on the loop below
		int nearestDZ = 0;
		short nearestData = 0;
		for (int i = 0; i < numLayers; ++i)
		{
			short layerData = _bb.getShort(index);
			index += 2;
			
			int layerZ = _extractLayerHeight(layerData);
			if (layerZ == worldZ)
			{
				// exact z
				return layerData;
			}
			
			int layerDZ = Math.abs(layerZ - worldZ);
			if ((i == 0) || (layerDZ < nearestDZ))
			{
				nearestDZ = layerDZ;
				nearestData = layerData;
			}
		}
		
		return nearestData;
	}
	
	private int _getCellIndex(int geoX, int geoY)
	{
		int cellOffset = ((geoX % IBlock.BLOCK_CELLS_X) * IBlock.BLOCK_CELLS_Y) + (geoY % IBlock.BLOCK_CELLS_Y);
		int index = _bbPos;
		// move index to cell, we need to parse on each request, OR we parse on creation and save indexes
		for (int i = 0; i < cellOffset; ++i)
		{
			index += 1 + (_bb.get(index) * 2);
		}
		// now the index points to the cell we need
		
		return index;
	}
	
	private byte _getNearestNSWE(int geoX, int geoY, int worldZ)
	{
		return (byte) (_getNearestLayer(geoX, geoY, worldZ) & 0x000F);
	}
	
	private int _extractLayerHeight(short layer)
	{
		layer = (short) (layer & 0x0fff0);
		return layer >> 1;
	}
	
	@Override
	public boolean hasGeoPos(int geoX, int geoY)
	{
		return true;
	}
	
	@Override
	public int getNearestZ(int geoX, int geoY, int worldZ)
	{
		return _extractLayerHeight(_getNearestLayer(geoX, geoY, worldZ));
	}
	
	@Override
	public int getNextLowerZ(int geoX, int geoY, int worldZ)
	{
		int index = _getCellIndex(geoX, geoY);
		byte numLayers = _bb.get(index);
		++index;
		
		int lowerZ = Integer.MIN_VALUE;
		for (int i = 0; i < numLayers; ++i)
		{
			short layerData = _bb.getShort(index);
			index += 2;
			
			int layerZ = _extractLayerHeight(layerData);
			if (layerZ == worldZ)
			{
				// exact z
				return layerZ;
			}
			
			if ((layerZ < worldZ) && (layerZ > lowerZ))
			{
				lowerZ = layerZ;
			}
		}
		
		return lowerZ == Integer.MIN_VALUE ? worldZ : lowerZ;
	}
	
	@Override
	public int getNextHigherZ(int geoX, int geoY, int worldZ)
	{
		int index = _getCellIndex(geoX, geoY);
		byte numLayers = _bb.get(index);
		++index;
		
		int higherZ = Integer.MAX_VALUE;
		for (int i = 0; i < numLayers; ++i)
		{
			short layerData = _bb.getShort(index);
			index += 2;
			
			int layerZ = _extractLayerHeight(layerData);
			if (layerZ == worldZ)
			{
				// exact z
				return layerZ;
			}
			
			if ((layerZ > worldZ) && (layerZ < higherZ))
			{
				higherZ = layerZ;
			}
		}
		
		return higherZ == Integer.MAX_VALUE ? worldZ : higherZ;
	}
	
	@Override
	public boolean canMoveIntoDirections(int geoX, int geoY, int worldZ, Direction first, Direction... more)
	{
		return Utils.canMoveIntoDirections(_getNearestNSWE(geoX, geoY, worldZ), first, more);
	}
	
	@Override
	public boolean canMoveIntoAllDirections(int geoX, int geoY, int worldZ)
	{
		return _getNearestNSWE(geoX, geoY, worldZ) == Cell.FLAG_NSWE_ALL;
	}
}
