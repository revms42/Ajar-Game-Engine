package org.ajar.age.logic;

/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) Dec 1, 2010 Matthew Stockbridge
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
 * age
 * org.ajar.age.logic
 * Effect.java
 */
public interface Effect {
    /**
     * Classes that implement this interface change the attributes of <code>Entities</code>.
     * @author revms42
     * @since 0.0.0.153
     * @see EffectInstance
     */

    /**
     * Updates the attributes of the provided subject <code>Entity</code>, using values derived
     * from the initiator <code>Entity</code> argument.
     * @param subject the entity whose attributes will be modified.
     * @param initiator the <code>Entity</code> to use to derive the magnitude of the effect.
     * @since 0.0.0.207
     */
    public void perform(Entity subject, Entity initiator);
}
