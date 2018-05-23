package org.ajar.age;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

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

    @Test
    public void testGameLoopHitsAllVisitorsDuringFullUpdate() {
        GameLoop.GameLoopBuilder builder = new GameLoop.GameLoopBuilder();

        Visitor culling = mock(Visitor.class, "Culling");
        Visitor pausable = mock(Visitor.class, "Pausable");
        Visitor skipable = mock(Visitor.class, "Skipable");
        Visitor both = mock(Visitor.class, "Both");

        DefaultNode node = new DefaultNode();

        when(culling.shouldVisit(node)).thenReturn(true);
        when(pausable.shouldVisit(node)).thenReturn(true);
        when(skipable.shouldVisit(node)).thenReturn(true);
        when(both.shouldVisit(node)).thenReturn(true);

        builder.add(culling, GameLoop.UpdateMode.NODE_CULLING)
                .add(pausable, GameLoop.UpdateMode.PAUSABLE)
                .add(skipable, GameLoop.UpdateMode.SKIPPABLE)
                .add(both, GameLoop.UpdateMode.SKIPPABLE, GameLoop.UpdateMode.PAUSABLE);

        loop = builder.build(node);

        InOrder full = Mockito.inOrder(culling, pausable, skipable, both);

        loop.update(node);

        full.verify(culling, times(2)).shouldVisit(node);
        full.verify(culling).scheduleVisit(node);
        full.verify(pausable).shouldVisit(node);
        full.verify(pausable).scheduleVisit(node);
        full.verify(skipable).shouldVisit(node);
        full.verify(skipable).scheduleVisit(node);
        full.verify(both).shouldVisit(node);
        full.verify(both).scheduleVisit(node);

        full.verify(culling).visit();
        full.verify(pausable).visit();
        full.verify(skipable).visit();
        full.verify(both).visit();
        full.verifyNoMoreInteractions();
    }

    @Test
    public void testGameLoopOnlyNonPauseVisitorsDuringPausedUpdate() {
        GameLoop.GameLoopBuilder builder = new GameLoop.GameLoopBuilder();

        Visitor culling = mock(Visitor.class, "Culling");
        Visitor pausable = mock(Visitor.class, "Pausable");
        Visitor skipable = mock(Visitor.class, "Skipable");
        Visitor both = mock(Visitor.class, "Both");

        DefaultNode node = new DefaultNode();

        when(culling.shouldVisit(node)).thenReturn(true);
        when(pausable.shouldVisit(node)).thenReturn(true);
        when(skipable.shouldVisit(node)).thenReturn(true);
        when(both.shouldVisit(node)).thenReturn(true);

        builder.add(culling, GameLoop.UpdateMode.NODE_CULLING)
                .add(pausable, GameLoop.UpdateMode.PAUSABLE)
                .add(skipable, GameLoop.UpdateMode.SKIPPABLE)
                .add(both, GameLoop.UpdateMode.SKIPPABLE, GameLoop.UpdateMode.PAUSABLE);

        loop = builder.build(node);
        loop.pause();

        InOrder pause = Mockito.inOrder(culling, pausable, skipable, both);

        loop.update(node);

        pause.verify(culling, times(2)).shouldVisit(node);
        pause.verify(culling).scheduleVisit(node);
        pause.verify(skipable).shouldVisit(node);
        pause.verify(skipable).scheduleVisit(node);

        pause.verify(culling).visit();
        pause.verify(skipable).visit();
        pause.verifyNoMoreInteractions();
    }

    @Test
    public void testGameLoopOnlyNonSkippableVisitorsDuringFrameskipUpdate() {
        GameLoop.GameLoopBuilder builder = new GameLoop.GameLoopBuilder();

        Visitor culling = mock(Visitor.class, "Culling");
        Visitor pausable = mock(Visitor.class, "Pausable");
        Visitor skipable = mock(Visitor.class, "Skipable");
        Visitor both = mock(Visitor.class, "Both");

        DefaultNode node = new DefaultNode();

        when(culling.shouldVisit(node)).thenReturn(true);
        when(pausable.shouldVisit(node)).thenReturn(true);
        when(skipable.shouldVisit(node)).thenReturn(true);
        when(both.shouldVisit(node)).thenReturn(true);

        builder.add(culling, GameLoop.UpdateMode.NODE_CULLING)
                .add(pausable, GameLoop.UpdateMode.PAUSABLE)
                .add(skipable, GameLoop.UpdateMode.SKIPPABLE)
                .add(both, GameLoop.UpdateMode.SKIPPABLE, GameLoop.UpdateMode.PAUSABLE);

        loop = builder.build(node);

        InOrder frameSkip = Mockito.inOrder(culling, pausable, skipable, both);

        loop.frameSkipUpdate(node);

        frameSkip.verify(culling, times(2)).shouldVisit(node);
        frameSkip.verify(culling).scheduleVisit(node);
        frameSkip.verify(pausable).shouldVisit(node);
        frameSkip.verify(pausable).scheduleVisit(node);

        frameSkip.verify(culling).visit();
        frameSkip.verify(pausable).visit();
        frameSkip.verifyNoMoreInteractions();
    }

    @Test
    public void testGameLoopHitsOnlyCullingAndNoFlagsDuringFrameskipPauseUpdate() {
        GameLoop.GameLoopBuilder builder = new GameLoop.GameLoopBuilder();

        Visitor culling = mock(Visitor.class, "Culling");
        Visitor pausable = mock(Visitor.class, "Pausable");
        Visitor skipable = mock(Visitor.class, "Skipable");
        Visitor both = mock(Visitor.class, "Both");
        Visitor none = mock(Visitor.class, "None");

        DefaultNode node = new DefaultNode();

        when(culling.shouldVisit(node)).thenReturn(true);
        when(pausable.shouldVisit(node)).thenReturn(true);
        when(skipable.shouldVisit(node)).thenReturn(true);
        when(both.shouldVisit(node)).thenReturn(true);
        when(none.shouldVisit(node)).thenReturn(true);

        builder.add(culling, GameLoop.UpdateMode.NODE_CULLING)
                .add(pausable, GameLoop.UpdateMode.PAUSABLE)
                .add(skipable, GameLoop.UpdateMode.SKIPPABLE)
                .add(both, GameLoop.UpdateMode.SKIPPABLE, GameLoop.UpdateMode.PAUSABLE)
                .add(none);

        loop = builder.build(node);
        loop.pause();

        InOrder full = Mockito.inOrder(culling, pausable, skipable, both, none);

        loop.frameSkipUpdate(node);

        full.verify(culling, times(2)).shouldVisit(node);
        full.verify(culling).scheduleVisit(node);
        full.verify(none).shouldVisit(node);
        full.verify(none).scheduleVisit(node);

        full.verify(culling).visit();
        full.verify(none).visit();
        full.verifyNoMoreInteractions();
    }
}
