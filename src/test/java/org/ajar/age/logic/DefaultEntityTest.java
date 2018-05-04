package org.ajar.age.logic;

import org.ajar.age.Node;

public class DefaultEntityTest extends EntityTest {

    @Override
    public Entity decorate(Node node) {
        return new DefaultEntity(node);
    }
}
