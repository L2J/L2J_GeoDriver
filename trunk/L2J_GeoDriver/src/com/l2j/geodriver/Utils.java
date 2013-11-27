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
public final class Utils
{
	private static boolean _nsweContains(byte nswe, byte nsweFlags)
	{
		return (nswe & nsweFlags) == nsweFlags;
	}
	
	private static byte _getNsweFlagsFromDirection(Direction dir)
	{
		switch (dir)
		{
			case NORTH_WEST:
				return Cell.FLAG_NSWE_NORTH_WEST;
			case NORTH_EAST:
				return Cell.FLAG_NSWE_NORTH_EAST;
			case SOUTH_WEST:
				return Cell.FLAG_NSWE_SOUTH_WEST;
			case SOUTH_EAST:
				return Cell.FLAG_NSWE_SOUTH_EAST;
			case NORTH:
				return Cell.FLAG_NSWE_NORTH;
			case EAST:
				return Cell.FLAG_NSWE_EAST;
			case SOUTH:
				return Cell.FLAG_NSWE_SOUTH;
			case WEST:
				return Cell.FLAG_NSWE_WEST;
			default:
				throw new IllegalStateException("This can't happen we have exacly the number of fields in the enum!");
		}
	}
	
	public static boolean canMoveIntoDirections(byte nswe, Direction first, Direction... more)
	{
		if (!_nsweContains(nswe, _getNsweFlagsFromDirection(first)))
		{
			return false;
		}
		
		if (more != null)
		{
			for (Direction dir : more)
			{
				if (!_nsweContains(nswe, _getNsweFlagsFromDirection(dir)))
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	private Utils()
	{
	}
}
