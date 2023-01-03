package ru.practicum.explorewithme.compilation.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import ru.practicum.explorewithme.event.model.Event;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Table(name = "compilations", schema = "public")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Compilation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean pinned;

    @Column
    private String title;

    @ManyToMany
    private List<Event> events;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Compilation compilation = (Compilation) o;
        return id != null && Objects.equals(id, compilation.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}