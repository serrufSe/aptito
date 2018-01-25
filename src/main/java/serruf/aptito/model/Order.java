package serruf.aptito.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "aptito_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int amount;

    /* заказ считается завершенным если он оплачен */
    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    @JsonIgnore
    private Boolean completed = false;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<Transaction> transactions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getComplete() { return completed; }

    public void setCompleted(Boolean completed) { this.completed = completed; }

    @Override
    public String toString() {
        return String.format("Order[amount=%d, name=%s]", amount, name);
    }
}
