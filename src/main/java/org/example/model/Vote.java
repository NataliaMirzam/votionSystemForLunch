package org.example.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "votes")
public class Vote extends AbstractBaseEntity {
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name = "date", nullable = false, columnDefinition = "date default CAST( now() AS Date )", updatable = false)
    @NotNull
    private LocalDate date = LocalDateTime.now().toLocalDate();

    @Column(name = "time", nullable = false, columnDefinition = "time default CAST( now() AS Time )", updatable = false)
    @NotNull
    private LocalTime time = LocalDateTime.now().toLocalTime();

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Vote() {
    }

    public Vote(User user, LocalDate date, LocalTime time, Restaurant restaurant) {
        super(null);
        this.user = user;
        this.date = date;
        this.time = time;
        this.restaurant = restaurant;
    }

    public Vote(Integer id, User user, LocalDate date, LocalTime time, Restaurant restaurant) {
        super(id);
        this.user = user;
        this.date = date;
        this.time = time;
        this.restaurant = restaurant;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public String toString() {
        return "Vote{" +
                "user=" + user +
                ", date=" + date +
                ", time=" + time +
                ", restaurant=" + restaurant +
                ", id=" + id +
                '}';
    }
}