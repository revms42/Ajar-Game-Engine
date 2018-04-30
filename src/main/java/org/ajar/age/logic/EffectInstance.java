package org.ajar.age.logic;

/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) April 29, 2018 Matthew Stockbridge
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
 * EffectInstance.java
 */

/**
 * EffectInstance is a class that encapsulates an <code>Effect</code>
 * and pairs it with a resultant <code>State</code>.
 * <p>
 * This class is effectively the backbone of the state engine, driving
 * game logic and state progression.
 * @see State
 * @author revms42
 */
public class EffectInstance {

    private final Effect effect;
    private final State result;

    /**
     * Creates a new Effect instance with the specified effect
     * and resultant state.
     * @param effect the effect to apply.
     * @param result the state of the subject entity after the application of the effect.
     */
    public EffectInstance(Effect effect, State result){
        this.effect = effect;
        this.result = result;
    }

    /**
     * Returns the resultant state specified at construction.
     * <p>
     * Note: While the parameters in the base implementation are superfluous they are present
     * due to the fact that sub-classes may need the information when making determinations
     * about what state the subject should end up in.
     * @param subject the subject of the effect.
     * @param initiator the initiator of the effect.
     * @return the resultant <code>State</code> for the subject.
     */
    public State getResult(Entity subject, Entity initiator) {
        return result;
    }

    /**
     * Performs the effect specified in construction on the subject using the subject and
     * initiator objects as arguments, then returns the <code>State</code> specified by
     * {@link EffectInstance#getResult(Entity, Entity)}.
     * @param subject the subject of the effect.
     * @param initiator the <code>Entity</code> that initiated the effect.
     * @return the resultant<code>State</code> of the subject.
     */
    public State perform(Entity subject, Entity initiator) {
        effect.perform(subject, initiator);
        return getResult(subject, initiator);
    }
}
