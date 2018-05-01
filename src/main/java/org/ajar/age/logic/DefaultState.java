package org.ajar.age.logic;
/*
 * This file is part of Ajar Game Engine.
 * Copyright (C) May 1st, 2018 Matthew Stockbridge
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
 * DefaultState.java
 */

import java.util.HashMap;
import java.util.Map;

/**
 * Default State is a basic implementation of the <code>State</code> interface backed by
 * a <code>Map</code>.
 * <p>
 * The enclosed class <code>DefaultState.Builder</code> provides a convenient way to construct
 * a DefaultState by supplying <code>Action</code>s, <code>Effect</code>s, and the resulting
 * <code>State</code>s.
 * @see State
 * @author revms42
 */
public class DefaultState implements State {

    private Map<Action,EffectInstance> effectMap;

    /**
     * Create a new DefaultState with the given <code>Action</code> to <code>EffectInstance</code>
     * mapping.
     * @param effectMap the mapping of Action to EffectInstance representing the transitions of
     *                  this state.
     * @see EffectInstance
     */
    protected DefaultState(Map<Action,EffectInstance> effectMap) {
        this.effectMap = effectMap;
    }

    /**
     * Returns the effect map currently being used by this DefaultState.
     * @returnm the <code>Map</code> representing the transitions of this state.
     */
    protected Map<Action,EffectInstance> getEffectMap() {
        return effectMap;
    }

    /**
     * Replaces the existing effect map with a new one.
     * @param effectMap the new <code>Map</code> representing this state's transitions.
     */
    protected void setEffectMap(Map<Action,EffectInstance> effectMap) {
        this.effectMap = effectMap;
    }

    @Override
    public State perform(Entity subject, Event e) {
        State result = null;
        Action a = e.getAction();

        if(effectMap.containsKey(a)){
            result = effectMap.get(a).perform(subject, e.getInitiator());
        }

        return result == null ? this : result;
    }

    /**
     * A basic builder class to help with the construction of a DefaultState.
     */
    public class Builder {
        private final Map<Action, EffectInstance> effectMap;

        /**
         * Creates a new builder mapping <code>Action</code>s to <code>EffectInstance</code>s.
         */
        public Builder() {
            effectMap = new HashMap<>();
        }

        /**
         * Constructs a new transition using the stated <code>Effect</code> and result
         * <code>State</code> and maps it to the specified <code>Action</code>.
         * @param action the action that triggers this transition.
         * @param effect the effect that this transition has.
         * @param result the state that the <code>Entity</code> will be in after the transition.
         */
        public void addTransition(Action action, Effect effect, State result) {
            effectMap.put(action, new EffectInstance(effect, result));
        }

        /**
         * Constructs a new transition using the stated <code>Effect</code> mapped to the specified
         * <code>Action</code> that doesn't result in a change in state.
         * @param action the action that triggers this transition.
         * @param effect the effect that the transition has.
         */
        public void addTransition(Action action, Effect effect) {
            this.addTransition(action, effect, null);
        }

        /**
         * Constructs a new transition mapping the specified <code>Action</code> to a resultant
         * <code>State</code> without any on-entry <code>Effect</code>.
         * @param action the action that triggers this transition.
         * @param result the resultant state.
         */
        public void addTransition(Action action, State result) {
            this.addTransition(action, null, result);
        }

        /**
         * Constructs a new <code>State</code> based on the underlying effect map.
         * @return constructed <code>State</code> with specified transitions.
         */
        public State build() {
            return new DefaultState(effectMap);
        }
    }
}
