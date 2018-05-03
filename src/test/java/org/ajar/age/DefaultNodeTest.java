package org.ajar.age;

import org.junit.Before;

public class DefaultNodeTest extends NodeTest {

    @Before
    @Override
    public void setup() {
        this.node = new DefaultNode();
    }
}
