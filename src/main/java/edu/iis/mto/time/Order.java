package edu.iis.mto.time;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private static final long VALID_PERIOD_HOURS = 24;
    private State orderState;
    private List<OrderItem> items = new ArrayList<>();
    private LocalDateTime subbmitionDate;
    private FakeSystemClock clock;

    public Order() {
        orderState = State.CREATED;
        clock = new FakeSystemClock();
    }

    public Order(FakeSystemClock clock){
        orderState = State.CREATED;
        this.clock = clock;
    }

    public void addItem(OrderItem item) {
        requireState(State.CREATED, State.SUBMITTED);

        items.add(item);
        orderState = State.CREATED;

    }

    public void submit() {
        requireState(State.CREATED);

        orderState = State.SUBMITTED;
        subbmitionDate = clock.now();

    }

    public void confirm() {
        requireState(State.SUBMITTED);
        long hoursElapsedAfterSubmittion = subbmitionDate.until(clock.now(), ChronoUnit.HOURS);
        if (hoursElapsedAfterSubmittion > VALID_PERIOD_HOURS) {
            orderState = State.CANCELLED;
            throw new OrderExpiredException();
        }
        orderState = State.CONFIRMED;
    }

    public void realize() {
        requireState(State.CONFIRMED);
        orderState = State.REALIZED;
    }

    State getOrderState() {
        return orderState;
    }

    public long getValidPeriodHours(){
        return VALID_PERIOD_HOURS;
    }

    private void requireState(State... allowedStates) {
        for (State allowedState : allowedStates) {
            if (orderState == allowedState) {
                return;
            }
        }

        throw new OrderStateException("order should be in state "
                                      + allowedStates
                                      + " to perform required  operation, but is in "
                                      + orderState);

    }

    public enum State {
        CREATED,
        SUBMITTED,
        CONFIRMED,
        REALIZED,
        CANCELLED
    }
}
