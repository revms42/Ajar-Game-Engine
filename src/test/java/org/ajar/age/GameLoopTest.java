package org.ajar.age;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class GameLoopTest {

    private GameLoop loop;

    @Test
    public void testBuildGameLoopPreservesOrder() {
        GameLoop.GameLoopBuilder builder = new GameLoop.GameLoopBuilder();

        Visitor first = mock(Visitor.class, "First");
        Visitor second = mock(Visitor.class, "Second");
        Visitor third = mock(Visitor.class, "Third");

        DefaultNode node = new DefaultNode();

        when(first.shouldVisit(node)).thenReturn(true);
        when(second.shouldVisit(node)).thenReturn(false);
        when(third.shouldVisit(node)).thenReturn(false);

        builder.add(first, GameLoop.UpdateMode.NODE_CULLING).add(second).add(third);

        loop = builder.build(node);

        InOrder inOrder = Mockito.inOrder(first, second, third);

        loop.update(node);

        inOrder.verify(first, times(2)).shouldVisit(node);
        inOrder.verify(first).scheduleVisit(node);
        inOrder.verify(second).shouldVisit(node);
        inOrder.verify(third).shouldVisit(node);
        inOrder.verify(first).visit();
        inOrder.verifyNoMoreInteractions();
    }
}
