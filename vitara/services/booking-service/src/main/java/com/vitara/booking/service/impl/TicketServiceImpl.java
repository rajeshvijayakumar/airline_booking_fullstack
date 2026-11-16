package com.vitara.booking.service.impl;

import com.vitara.booking.entities.Booking;
import com.vitara.booking.entities.Passenger;
import com.vitara.booking.entities.Ticket;
import com.vitara.booking.repository.TicketRepository;
import com.vitara.booking.service.TicketService;
import com.vitara.common.enums.TicketStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    @Override
    public List<Ticket> generateTicketsForBooking(Booking booking) {
        List<Ticket> tickets = new ArrayList<Ticket>();

        for(Passenger passenger : booking.getPassengers()) {
            String ticketNumber=generateUniqueTicketNumber();

            Ticket ticket=Ticket.builder()
                    .ticketNumber(ticketNumber)
                    .status(TicketStatus.BOOKED)
                    .issuedAt(LocalDateTime.now())
                    .booking(booking)
                    .passenger(passenger)
                    .build();

            Ticket savedTicket=ticketRepository.save(ticket);
            tickets.add(savedTicket);
        }
        return tickets;
    }

    private String generateUniqueTicketNumber() {
        String ticketNumber;

        do{
            String datePart=LocalDateTime.now().toString().substring(0,10);
            String randomPart= UUID.randomUUID().toString().substring(0,8);
            ticketNumber=String.format("TKT-%s-%s",datePart,randomPart);
        }while (ticketRepository.existsByTicketNumber(ticketNumber));

        return ticketNumber;
    }
}
