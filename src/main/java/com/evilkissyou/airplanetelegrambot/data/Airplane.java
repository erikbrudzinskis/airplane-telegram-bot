package com.evilkissyou.airplanetelegrambot.data;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "airplanes")
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer airplaneId;
    String name;
    String designator;
    String wakeTurbulence;
    String configuration;
    String cruiseLevel;
    String trueAirSpeed;
    String rateOfClimb;
    String rateOfDescent;
    String category;
    String dimensions;
    String notes;
    String imageUrl;

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
}
