package csd230.lab2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.util.Date;

@Entity
public class DiscMag extends Publication {

    @Column(name = "order_qty", nullable = true)
    private int orderQty;

    @Column(name = "curr_issue")
    private Date currIssue;

    @Column(name = "has_disc", nullable = true)
    private boolean hasDisc;

    // Default constructor required by JPA
    public DiscMag() {
        // You can initialize default values if needed
    }

    // Constructor with all properties
    public DiscMag(double price, int quantity, String description, String title, int copies,
                   int orderQty, Date currIssue, boolean hasDisc) {
        // Call Publication's constructor to set common fields
        super(price, quantity, description, title, copies);
        this.orderQty = orderQty;
        this.currIssue = currIssue;
        this.hasDisc = hasDisc;
    }

    // Getters and setters
    public int getOrderQty() {
        return orderQty;
    }

    public void setOrderQty(int orderQty) {
        this.orderQty = orderQty;
    }

    public Date getCurrIssue() {
        return currIssue;
    }

    public void setCurrIssue(Date currIssue) {
        this.currIssue = currIssue;
    }

    public boolean isHasDisc() {
        return hasDisc;
    }

    public void setHasDisc(boolean hasDisc) {
        this.hasDisc = hasDisc;
    }

    @Override
    public String toString() {
        return "DiscMag{" +
                "id=" + getId() +
                ", title=" + getTitle() +
                ", price=" + getPrice() +
                ", quantity=" + getQuantity() +
                ", copies=" + getCopies() +
                ", orderQty=" + orderQty +
                ", currIssue=" + currIssue +
                ", hasDisc=" + hasDisc +
                '}';
    }
}
