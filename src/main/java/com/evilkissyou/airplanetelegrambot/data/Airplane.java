package com.evilkissyou.airplanetelegrambot.data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "airplanes")
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer airplaneId;
    private String name;
    private String designator;
    private String wakeTurbulence;
    private String configuration;
    private String cruiseLevel;
    private String trueAirSpeed;
    private String rateOfClimb;
    private String rateOfDescent;
    private String category;
    private String dimensions;
    private String notes;
    private String imageUrl;

    @ManyToMany
    @JoinTable(
            name = "users_airplanes",
            joinColumns = @JoinColumn(name = "airplane_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;

    public Airplane() {
    }

    public Airplane(String name, String designator, String wakeTurbulence, String configuration, String cruiseLevel, String trueAirSpeed, String rateOfClimb, String rateOfDescent, String category, String dimensions, String notes, String imageUrl) {
        this.name = name;
        this.designator = designator;
        this.wakeTurbulence = wakeTurbulence;
        this.configuration = configuration;
        this.cruiseLevel = cruiseLevel;
        this.trueAirSpeed = trueAirSpeed;
        this.rateOfClimb = rateOfClimb;
        this.rateOfDescent = rateOfDescent;
        this.category = category;
        this.dimensions = dimensions;
        this.notes = notes;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "<strong>" + name + "</strong>" + "\n" +
                "ICAO Designator = <i>" + designator + "</i>" + "\n" +
                "ICAO Wake Turbulence = <i>" + wakeTurbulence + "</i>" + "\n" +
                "Configuration = <i>" + configuration + "</i>" + "\n" +
                "Cruise Level = <i>" + cruiseLevel + "</i>" + "\n" +
                "True Air Speed = <i>" + trueAirSpeed + "</i>" + "\n" +
                "Rate Of Climb = <i>" + rateOfClimb + "</i>" + "\n" +
                "Rate Of Descent = <i>" + rateOfDescent + "</i>" + "\n" +
                "Category = <i>" + category + "</i>" + "\n" +
                "Dimensions = <i>" + dimensions + "</i>" + "\n" +
                "Notes = <i>" + notes + "</i>" + "\n" +
                "<a href=\"" + imageUrl + "\">Image</a>";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Airplane airplane = (Airplane) o;
        return Objects.equals(airplaneId, airplane.airplaneId) &&
                Objects.equals(name, airplane.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airplaneId, name);
    }
}
