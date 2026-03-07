package ru.ashemchuk.OrderQueue.Order;

public class Order {
    private int id;
    private OrderState state;

    public Order(int id, OrderState state) {
        this.id = id;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

}
