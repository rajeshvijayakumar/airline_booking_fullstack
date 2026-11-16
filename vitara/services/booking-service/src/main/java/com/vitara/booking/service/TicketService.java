package com.vitara.booking.service;


import com.vitara.booking.entities.Booking;
import com.vitara.booking.entities.Ticket;
import java.util.List;

public interface TicketService {

    List<Ticket> generateTicketsForBooking(Booking booking);
}
