package com.evilkissyou.airplanetelegrambot.data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private Integer chatId;

    @ManyToMany (fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_airplanes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "airplane_id")
    )
    private List<Airplane> airplanes;

    public User() {
    }

    public User(Integer chatId) {
        this.chatId = chatId;
    }

    public Integer getChatId() {
        return chatId;
    }

    public List<Airplane> getAirplanes() {
        return airplanes;
    }

    public void setAirplanes(List<Airplane> airplanes) {
        this.airplanes = airplanes;
    }

    public List<Integer> getCompletedAirplanesIds() {
        List<Integer> completedIds = new ArrayList<>();
        for (Airplane airplane : airplanes) {
            completedIds.add(airplane.getAirplaneId());
        }
        return completedIds;
    }
}
