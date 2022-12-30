package ru.practicum.explorewithme.event.model;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class Location implements Serializable {
    private float lat;
    private float lon;
}