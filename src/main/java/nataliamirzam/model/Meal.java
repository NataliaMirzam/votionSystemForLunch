package nataliamirzam.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import nataliamirzam.HasId;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "meal", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "dt"}, name = "meal_unique_name_dt_idx")})
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true, exclude = {"restaurant"})
public class Meal extends NamedEntity implements HasId {
    @Column(name = "dt", nullable = false, columnDefinition = "date default CAST( now() AS Date )", updatable = false)
    @NotNull
    private LocalDate date = LocalDateTime.now().toLocalDate();

    @Column(name = "price", nullable = false)
    @NotNull
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public Meal(Integer id, String name, @NotNull LocalDate date, Integer price) {
        super(id, name);
        this.date = date;
        this.price = price;
    }
}
