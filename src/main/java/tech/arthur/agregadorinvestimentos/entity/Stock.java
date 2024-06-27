package tech.arthur.agregadorinvestimentos.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "stock")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "description")
    private String description;

    @Column(name = "ticker")
    private BigDecimal ticker;

    public Stock(String id, String description, BigDecimal ticker) {
        this.id = id;
        this.description = description;
        this.ticker = ticker;
    }

    public Stock() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTicker() {
        return ticker;
    }

    public void setTicker(BigDecimal ticker) {
        this.ticker = ticker;
    }
}
