package org.ajar.age;

public class DefaultDecoratorTest extends DecoratorTest {

    @Override
    public Decorator decorate(Node node) {
        return new DefaultDecorator(node);
    }
}
