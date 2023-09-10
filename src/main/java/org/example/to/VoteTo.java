package org.example.to;

import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
public class VoteTo extends BaseTo {
    private LocalDate date;

    public VoteTo(Integer id, LocalDate date) {
        super(id);
        this.date = date;
    }
}
