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
public final class ComplexBlock implements IBlock
{
	private final int _bbPos;
	private final ByteBuffer _bb;
	
	/**
	 * Initializes a new instance of this block reading the specified buffer.
	 * @param bb the buffer
	 */
	public ComplexBlock(ByteBuffer bb)
	{
		_bbPos = bb.position();
		_bb = bb;
		_bb.position(_bbPos + (IBlock.BLOCK_CELLS * 2));
	}
	
	private short _getCellData(int geoX, int geoY)
	{
		return _bb.getShort(_bbPos + ((((geoX % IBlock.BLOCK_CELLS_X) * IBlock.BLOCK_CELLS_Y) + (geoY % IBlock.BLOCK_CELLS_Y)) * 2));
	}
	
	private byte _getCellNSWE(int geoX, int geoY)
	{
		return (byte) (_getCellData(geoX, geoY) & 0x000F);
	}
	
	private int _getCellHeight(int geoX, int geoY)
	{
		short height = (short) (_getCellData(geoX, geoY) & 0x0fff0);
		return height >> 1;
	}
	
	@Override
	public boolean hasGeoPos(int geoX, int geoY)
	{
		return true;
	}
	
	@Override
	public int getNearestZ(int geoX, int geoY, int worldZ)
	{
		return _getCellHeight(geoX, geoY);
	}
	
	@Override
	public int getNextLowerZ(int geoX, int geoY, int worldZ)
	{
		int cellHeight = _getCellHeight(geoX, geoY);
		return cellHeight <= worldZ ? cellHeight : worldZ;
	}
	
	@Override
	public int getNextHigherZ(int geoX, int geoY, int worldZ)
	{
		int cellHeight = _getCellHeight(geoX, geoY);
		return cellHeight >= worldZ ? cellHeight : worldZ;
	}
	
	@Override
	public boolean canMoveIntoDirections(int geoX, int geoY, int worldZ, Direction first, Direction... more)
	{
		return Utils.canMoveIntoDirections(_getCellNSWE(geoX, geoY), first, more);
	}
	
	@Override
	public boolean canMoveIntoAllDirections(int geoX, int geoY, int worldZ)
	{
		return _getCellNSWE(geoX, geoY) == Cell.FLAG_NSWE_ALL;
	}
}
