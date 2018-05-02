package org.ajar.age.display;
/*
 * This file is part of Ajar Game Engine
 * Copyright (C) May 2, 2018 Matthew Stockbridge
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
 * org.ajar.age.display
 * AbstractDisplayVisitor.java
 */
import org.ajar.age.AbstractVisitor;

/**
 * An abstract class that provides some binding of culling surface to <code>Displayable</code>
 * as a starting place to produce a display visitor.
 * <p/>
 * Because the actual process of display can vary pretty wildly between implementations this
 * class doesn't provide any utility in the realm of actually displaying. Instead, it leaves the
 * methods that would be required for drawing abstract, but provides some of the "accounting" logic
 * that it needs to be a visitor (i.e. it implements the code to perform the checks to see if
 * a <code>Displayable</code> is within the culling surface and should be displayed).
 * @param <S> the class being used as a culling surface
 * @param <D> the <code>Displayable</code> class that can use that culling surface to determine
 *           if it should be displayed.
 * @see Displayable
 * @author revms42
 */
public abstract class AbstractDisplayVisitor<S,D extends Displayable<S>> extends AbstractVisitor<D> {

    private S cullingSurface;

    /**
     * Creates a new AbstractDisplayVisitor using the specified Displayable class
     * @param decoratorClass the class that this visitor is designed to display.
     */
    public AbstractDisplayVisitor(Class<? extends D> decoratorClass) {
        super(decoratorClass);
    }

    /**
     * Retrieves the culling surface being used to determine whether or not an object is in view.
     * @return the culling surface.
     * @see Displayable#isInView(Object)
     */
    public S getCulingSurface() {
        return cullingSurface;
    }

    /**
     * Sets the culling surface to use to determine whether something is in view.
     * @param cullingSurface the new culling surface to use.
     * @see Displayable#isInView(Object)
     */
    public void setCullingSurface(S cullingSurface) {
        this.cullingSurface = cullingSurface;
    }

    @Override
    protected boolean shouldVisit(D decorator) {
        final S cullingSurface = getCulingSurface();
        if(cullingSurface != null) {
            return decorator != null && decorator.isInView(cullingSurface);
        } else {
            return false;
        }
    }
}
