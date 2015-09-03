/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Sep 3, 2015 Matthew Stockbridge
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * (but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * AGE
 * org.ajar.age.logic.multi
 * CompoundRandom.java
 * 
 * For more information see: https://sourceforge.net/projects/macchiatodoppio/
 * 
 * This file is part of the LWJGL effort in reorganizing Macchiato Doppio, 
 * and is therefore *non-final* and *not* intended for public use. This code
 * is strictly experimental.
 */
package org.ajar.age.logic.multi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

/**
 * This is a special version of Random to help ensure that everyone is using the same Random hash without
 * anyone cheating. It allows all the players to input a hash code prior to sharing seeds, then checks
 * that the seeds match the hash codes. In this way, all the players commit to a seed value before they
 * every exchange seed information. Once all the hashes have been shared, the seeds can safely be shared
 * with the full knowledge that, regardless of the order the seeds are shared, no one can attempt to manipulate
 * the final seed by modifying their response prior to sharing (since the hash would be different and 
 * errors would be thrown by every other player's instance of this class).
 * <p>
 * Additionally, attempting to change the values of the seeds in memory would result in errors being thrown
 * on initialization. Resulting in a null seed for the person who modified the map.
 * <p>
 * Finally, since the hash code of this class is a delegate of the hash code of the actual seed, anybody should
 * be able to check any other person's hash code directly.
 * <p>
 * Note that any attempt to use any of the "next" methods in this class prior to exchanging all the seeds will
 * result in null pointer exceptions.
 * <p>
 * <hr>
 * <b>Example Use</b>
 * <p>
 * <ol>
 * <li>Step 1: Each player initializes an instance of CompoundRandom</li>
 * <li>Step 2: Each player exchanges their initial hash with each other player</li>
 * <li>Step 3: Once each player has a hash key for each other player, the players exchange seeds.
 * <p>
 * During this process, if any seed doesn't match the pre-supplied key an error will be thrown.
 * </li>
 * <li>Step 4: Each player calls initialize on their own CompoundRandom instance. Since all the seeds are the same
 * all the players will end up with an identical instance independently.</li>
 * <li>Step 5 (optional): If there are still concerns about cheating, all the players can be re-queried for their
 * seeds, initial hashes, and final hashes independently. These values can be compared to determine if someone is
 * attempting to manipulate the value.</li>
 * 
 * 
 * @author mstockbr
 *
 */
public final class CompoundRandom extends Random {
	private static final long serialVersionUID = 5152415374507810115L;

	private final Long initialSeed;
	private final int numberOfPlayers;
	
	private HashMap<Integer,Long> coordinatingHash;
	
	private Random seed;
	
	public CompoundRandom(int players, long initialSeed) {
		this.initialSeed = initialSeed;
		//One less, because this seed represents a player.
		this.numberOfPlayers = players - 1;
		coordinatingHash = new HashMap<Integer,Long>();
	}
	
	public CompoundRandom(int players){
		this(players,System.currentTimeMillis());
	}
	
	public int initialHash(){
		return initialSeed.hashCode();
	}
	
	public long initialSeed(){
		return initialSeed;
	}

	public void setCoordinatingHash(int coordHash){
		coordinatingHash.put(coordHash, null);
	}
	
	public boolean setPlayerSeed(int coordHash, long playerSeed){
		if(coordinatingHash.keySet().size() == numberOfPlayers){
			if(coordinatingHash.containsKey(coordHash)){
				int seedHash = (new Long(playerSeed)).hashCode();
				if(coordHash == seedHash){
					coordinatingHash.put(coordHash, playerSeed);
					return true;
				}else{
					throw new NumberFormatException("The supplied seed does not match the target hash!");
				}
			}else{
				throw new NumberFormatException("The supplied hash was not previously set!");
			}
		}else{
			return false;
		}
	}
	
	public boolean isInitialized(){
		return seed != null;
	}
	
	public boolean initialize(){
		if(coordinatingHash.keySet().size() == numberOfPlayers){
			if(!coordinatingHash.values().contains(null)){
				coordinatingHash.put(initialHash(), initialSeed);
				
				Vector<Integer> keys = new Vector<Integer>();
				keys.addAll(coordinatingHash.keySet());
				Collections.sort(keys);
				
				long seed = 0L;
				for(int i = 0; i < keys.size(); i++){
					Integer nextKey = keys.get(i);
					Long nextSeed = coordinatingHash.get(nextKey);
					
					if(nextSeed.hashCode() == nextKey){
						if(i % 2 == 0){
							seed = seed | nextSeed.longValue();
						}else{
							seed = seed & nextSeed.longValue();
						}
					}else{
						throw new NumberFormatException("Contents of the seed array were modified post-sharing!");
					}
				}
				this.seed = new Random(seed);
				
				return true;
			}else{
				throw new NumberFormatException("Could not initialize! Not all seeds have been provided!");
			}
		}else{
			return false;
		}
	}
	
	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return seed.hashCode();
	}

	/**
	 * @param bytes
	 * @see java.util.Random#nextBytes(byte[])
	 */
	public void nextBytes(byte[] bytes) {
		seed.nextBytes(bytes);
	}

	/**
	 * @return
	 * @see java.util.Random#nextInt()
	 */
	public int nextInt() {
		return seed.nextInt();
	}

	/**
	 * @param n
	 * @return
	 * @see java.util.Random#nextInt(int)
	 */
	public int nextInt(int n) {
		return seed.nextInt(n);
	}

	/**
	 * @return
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return seed.toString();
	}

	/**
	 * @return
	 * @see java.util.Random#nextLong()
	 */
	public long nextLong() {
		return seed.nextLong();
	}

	/**
	 * @return
	 * @see java.util.Random#nextBoolean()
	 */
	public boolean nextBoolean() {
		return seed.nextBoolean();
	}

	/**
	 * @return
	 * @see java.util.Random#nextFloat()
	 */
	public float nextFloat() {
		return seed.nextFloat();
	}

	/**
	 * @return
	 * @see java.util.Random#nextDouble()
	 */
	public double nextDouble() {
		return seed.nextDouble();
	}

	/**
	 * @return
	 * @see java.util.Random#nextGaussian()
	 */
	public double nextGaussian() {
		return seed.nextGaussian();
	}

}
