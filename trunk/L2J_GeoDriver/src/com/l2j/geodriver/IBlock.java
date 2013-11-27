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
public interface IBlock
{
	/**
	 * Region type.
	 * @author FBIagent
	 */
	public enum Type
	{
		/** Flat block type */
		FLAT,
		/** Complex block type */
		COMPLEX,
		/** Multilayer block type */
		MULTILAYER
	}
	
	/** Cells in a block on the x axis */
	public static final int BLOCK_CELLS_X = 8;
	/** Cells in a block on the y axis */
	public static final int BLOCK_CELLS_Y = 8;
	/** Cells in a block */
	public static final int BLOCK_CELLS = BLOCK_CELLS_X * BLOCK_CELLS_Y;
	
	boolean hasGeoPos(int geoX, int geoY);
	
	int getNearestZ(int geoX, int geoY, int worldZ);
	
	int getNextLowerZ(int geoX, int geoY, int worldZ);
	
	int getNextHigherZ(int geoX, int geoY, int worldZ);
	
	boolean canMoveIntoDirections(int geoX, int geoY, int worldZ, Direction first, Direction... more);
	
	boolean canMoveIntoAllDirections(int geoX, int geoY, int worldZ);
}
