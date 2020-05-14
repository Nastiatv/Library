package by.runa.lib.entities;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = false, chain = true)
@Table(name = "order_book")
public class Order extends AEntity {

    @Column(name = "order_date")
    private LocalDate orderDate;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "is_finished")
    private boolean isFinished;

    @Column(name = "is_prolonged")
    private boolean isProlonged;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
