package org.example.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@EqualsAndHashCode(callSuper = true)
public class MealTo extends NamedTo {
    LocalDate date;

    public MealTo(Integer id, String name, LocalDate date) {
        super(id, name);
        this.date = date;
    }
}
