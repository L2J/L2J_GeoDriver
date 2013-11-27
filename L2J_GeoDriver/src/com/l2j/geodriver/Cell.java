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

/**
 * @author FBIagent
 */
public final class Cell
{
	/** East NSWE flag */
	public static final byte FLAG_NSWE_EAST = 1 << 0;
	/** West NSWE flag */
	public static final byte FLAG_NSWE_WEST = 1 << 1;
	/** South NSWE flag */
	public static final byte FLAG_NSWE_SOUTH = 1 << 2;
	/** North NSWE flag */
	public static final byte FLAG_NSWE_NORTH = 1 << 3;
	
	/** North-East NSWE flags */
	public static final byte FLAG_NSWE_NORTH_EAST = FLAG_NSWE_NORTH | FLAG_NSWE_EAST;
	/** North-West NSWE flags */
	public static final byte FLAG_NSWE_NORTH_WEST = FLAG_NSWE_NORTH | FLAG_NSWE_WEST;
	/** South-East NSWE flags */
	public static final byte FLAG_NSWE_SOUTH_EAST = FLAG_NSWE_SOUTH | FLAG_NSWE_EAST;
	/** South-West NSWE flags */
	public static final byte FLAG_NSWE_SOUTH_WEST = FLAG_NSWE_SOUTH | FLAG_NSWE_WEST;
	
	/** All directions NSWE flags */
	public static final byte FLAG_NSWE_ALL = FLAG_NSWE_EAST | FLAG_NSWE_WEST | FLAG_NSWE_SOUTH | FLAG_NSWE_NORTH;
	
	private Cell()
	{
	}
}
