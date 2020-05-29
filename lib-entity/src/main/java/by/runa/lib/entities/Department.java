package by.runa.lib.entities;

import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(fluent = false, chain = true)
@Table(name = "department")
public class Department extends AEntity {

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "departments", cascade = CascadeType.ALL)
    private Set<Book> books;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<User> users;

}
