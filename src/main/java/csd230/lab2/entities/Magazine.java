package csd230.lab2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Entity
public class Magazine extends Publication {

    @Column(name = "order_qty", nullable = true)
    private int orderQty;

    @Column(name = "curr_issue")
    @DateTimeFormat(pattern = "yyyy-MM-dd")  // This tells Spring to expect dates in yyyy-MM-dd format
    private Date currIssue;

    // Add these two fields
    @Column(name = "isbn")
    private String isbn;

    @Column(name = "publisher")
    private String publisher;

    public Magazine() {}

    public Magazine(double price, int quantity, String description, String title, int copies, int orderQty, Date currIssue) {
        super(price, quantity, description, title, copies);
        this.orderQty = orderQty;
        this.currIssue = currIssue;
    }

    // getters and setters
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
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
