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
package com.l2j.geodriver;

import com.l2jserver.gameserver.geoengine.Direction;

/**
 * @author FBIagent
 */
public interface IRegion
{
	/**
	 * Region types.
	 * @author FBIagent
	 */
	public enum Type
	{
		/** Null region type */
		NULL,
		/** Non null region type */
		NON_NULL
	}
	
	/** Blocks in a region on the x axis */
	public static final int REGION_BLOCKS_X = 256;
	/** Blocks in a region on the y axis */
	public static final int REGION_BLOCKS_Y = 256;
	/** Blocks in a region */
	public static final int REGION_BLOCKS = REGION_BLOCKS_X * REGION_BLOCKS_Y;
	
	/** Cells in a region on the x axis */
	public static final int REGION_CELLS_X = REGION_BLOCKS_X * IBlock.BLOCK_CELLS_X;
	/** Cells in a regioin on the y axis */
	public static final int REGION_CELLS_Y = REGION_BLOCKS_Y * IBlock.BLOCK_CELLS_Y;
	/** Cells in a region */
	public static final int REGION_CELLS = REGION_CELLS_X * REGION_CELLS_Y;
	
	boolean hasGeoPos(int geoX, int geoY);
	
	int getNearestZ(int geoX, int geoY, int worldZ);
	
	int getNextLowerZ(int geoX, int geoY, int worldZ);
	
	int getNextHigherZ(int geoX, int geoY, int worldZ);
	
	boolean canMoveIntoDirections(int geoX, int geoY, int worldZ, Direction first, Direction... more);
	
	boolean canMoveIntoAllDirections(int geoX, int geoY, int worldZ);
}
