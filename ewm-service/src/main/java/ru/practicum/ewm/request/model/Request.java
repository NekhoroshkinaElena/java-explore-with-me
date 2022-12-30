package ru.practicum.ewm.request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "REQUESTS")
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "created")
    private String created;
    @ManyToOne
    private Event event;
    @ManyToOne
    private User requester;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
}
