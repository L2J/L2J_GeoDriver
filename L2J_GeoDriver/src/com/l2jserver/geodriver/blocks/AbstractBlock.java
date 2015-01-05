/*
 * Copyright (C) 2004-2015 L2J Server
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
package com.l2jserver.geodriver.blocks;

import com.l2jserver.geodriver.IBlock;

/**
 * @author HorridoJoho
 */
public abstract class AbstractBlock implements IBlock
{
	private int limitZ(int z, int newZ, int deltaLimit)
	{
		if (Math.abs(z - newZ) > deltaLimit)
		{
			return z;
		}
		return newZ;
	}
	
	@Override
	public int getNearestZ(int geoX, int geoY, int worldZ, int zDeltaLimit)
	{
		return limitZ(worldZ, getNearestZ(geoX, geoY, worldZ), zDeltaLimit);
	}
	
	@Override
	public int getNextLowerZ(int geoX, int geoY, int worldZ, int zDeltaLimit)
	{
		return limitZ(worldZ, getNextLowerZ(geoX, geoY, worldZ), zDeltaLimit);
	}
	
	@Override
	public int getNextHigherZ(int geoX, int geoY, int worldZ, int zDeltaLimit)
	{
		return limitZ(worldZ, getNextHigherZ(geoX, geoY, worldZ), zDeltaLimit);
	}
}
