package com.heretic.main.service.ticket;

import com.heretic.main.model.session.Session;
import com.heretic.main.model.ticket.Ticket;
import com.heretic.main.model.user.User;
import com.heretic.main.repository.ticket.TicketRepository;
import com.heretic.main.util.Values;
import lombok.Builder;

import java.util.List;

@Builder
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public boolean create(Ticket ticket) {
        return ticketRepository.create(ticket);
    }

    @Override
    public List<Ticket> getAll() {
        return ticketRepository.getAll();
    }

    @Override
    public List<Ticket> get(Session session) {
        return ticketRepository.get(session);
    }

    @Override
    public List<Ticket> get(User user) {
        return ticketRepository.get(user);
    }

    @Override
    public Ticket get(Long id) {
        return ticketRepository.get(id);
    }

    @Override
    public Ticket update(Ticket ticket, Long id) {
        return ticketRepository.update(ticket, id);
    }

    @Override
    public Ticket delete(Long id) {
        return ticketRepository.delete(id);
    }

    @Override
    public boolean returnTicket(Ticket ticket) {
        return ticketRepository.create(ticketRepository.delete(ticket.getId()));
    }

    @Override
    public void createTickets(Session session) {
        for (int i = Values.ZERO; i < Values.COUNT_TICKETS; i++) {
            Ticket ticket = new Ticket(session, null, (i + Values.ONE));
            ticketRepository.create(ticket);
        }
    }

    @Override
    public void removeTickets(Session session) {
        for (Ticket ticket : get(session)) {
            ticketRepository.delete(ticket.getId());
        }
    }

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

}
