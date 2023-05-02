package com.heretic.main.model.ticket;

import com.heretic.main.model.user.User;
import com.heretic.main.model.session.Session;
import com.heretic.main.util.Values;

import java.util.Objects;

public class Ticket {

    private Long id;
    private final Session session;
    private User buyer;
    private final int seat;

    @Override
    public String toString() {
        String date = session.getTime().toLocalDate().toString();
        String hours = session.getTime().toLocalTime().toString();
        return String.format(Values.SAMPLE_TICKET, date, hours, session.getMovie().getName(), seat, showOwner(buyer), id);
    }

    private String showOwner(User owner) {
        if (owner == null) {
            return Values.FREE;
        } else return owner.getUsername();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Ticket(Session session, User buyer, int position) {
        this.session = session;
        this.buyer = buyer;
        this.seat = position;
    }

    public Ticket(Long id, Session session, User buyer, int position) {
        this.id = id;
        this.session = session;
        this.buyer = buyer;
        this.seat = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public int getSeat() {
        return seat;
    }

}
