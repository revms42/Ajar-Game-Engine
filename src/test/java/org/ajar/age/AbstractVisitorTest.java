package org.ajar.age;

import org.junit.Before;

public class AbstractVisitorTest extends VisitorTest<Decorator> {

    protected boolean shouldVisit;

    @Before
    public void setup() {
        visitor = new AbstractVisitor<Decorator>(Decorator.class) {

            @Override
            protected boolean shouldVisit(Decorator decorator) {
                return shouldVisit;
            }

            @Override
            public void visit(Decorator decorator) {}

            @Override
            protected void startProcess() {}

            @Override
            protected void finishProcess() {}
        };
        decoratorClass = Decorator.class;
    }

    @Override
    public void setupShouldVisit(Decorator decoratorMock) {
        shouldVisit = true;
    }

    @Override
    public void setupShouldNotVisit(Decorator decoratorMock) {
        shouldVisit = false;
    }
}
