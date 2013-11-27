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

import com.l2j.geodriver.IRegion;
import com.l2jserver.gameserver.geoengine.Direction;

/**
 * @author FBIagent
 */
public final class NullRegion implements IRegion
{
	/**
	 * The only required instance of this class.
	 */
	public static final NullRegion INSTANCE = new NullRegion();
	
	@Override
	public boolean hasGeoPos(int geoX, int geoY)
	{
		return false;
	}
	
	@Override
	public int getNearestZ(int geoX, int geoY, int worldZ)
	{
		return worldZ;
	}
	
	@Override
	public int getNextLowerZ(int geoX, int geoY, int worldZ)
	{
		return worldZ;
	}
	
	@Override
	public int getNextHigherZ(int geoX, int geoY, int worldZ)
	{
		return worldZ;
	}
	
	@Override
	public boolean canMoveIntoDirections(int geoX, int geoY, int worldZ, Direction first, Direction... more)
	{
		return true;
	}
	
	@Override
	public boolean canMoveIntoAllDirections(int geoX, int geoY, int worldZ)
	{
		return true;
	}
}
