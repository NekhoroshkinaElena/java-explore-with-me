package ru.practicum.ewm.event.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "EVENTS")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "annotation", nullable = false)
    @Length(min = 20, max = 2000)
    private String annotation;
    @ManyToOne
    private Category category;
    @Column(name = "confirmedRequest")
    private int confirmedRequest;
    @Column(name = "createdOn")
    private LocalDateTime createdOn;
    @Column(name = "description")
    @Length(min = 20, max = 7000)
    private String description;
    @Column(name = "eventDate")
    private LocalDateTime eventDate;
    @ManyToOne
    private User initiator;
    @Column(name = "lat")
    private Double lat;
    @Column(name = "lon")
    private Double lon;
    @Column(name = "paid")
    private Boolean paid;
    @Column(name = "participantLimit")
    private int participantLimit;
    @Column(name = "publishedOn")
    private LocalDateTime publishedOn;
    @Column(name = "requestModeration")
    private Boolean requestModeration;
    @Enumerated(EnumType.STRING)
    private StateEvent state;
    @Column(name = "title")
    @Length(min = 3, max = 120)
    private String title;
    @Column(name = "views")
    private int views;
}
