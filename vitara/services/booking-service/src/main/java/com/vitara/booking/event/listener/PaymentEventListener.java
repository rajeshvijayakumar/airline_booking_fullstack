package com.vitara.booking.event.listener;


import com.vitara.booking.client.FlightClient;
import com.vitara.booking.client.PricingClient;
import com.vitara.booking.client.UserClient;
import com.vitara.booking.entities.Booking;
import com.vitara.booking.event.publisher.BookingEventProducer;
import com.vitara.booking.repository.BookingRepository;
import com.vitara.common.dto.UserDto;
import com.vitara.common.enums.BookingStatus;
import com.vitara.common.events.PaymentCompletedEvent;
import com.vitara.common.events.PaymentFailedEvent;
import com.vitara.common.response.FareResponse;
import com.vitara.common.response.FlightInstanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentEventListener {

    private final BookingRepository bookingRepository;
    private final FlightClient flightClient;
    private final PricingClient pricingClient;
    private final UserClient userClient;
    private final BookingEventProducer bookingEventProducer;

    @KafkaListener(topics = "payment.completed", groupId = "booking-service-group")
    @Transactional
    public void handlePaymentCompleted(PaymentCompletedEvent event) throws Exception {

        System.out.println("Received PaymentCompletedEvent "
                + event.getBookingId()+"-"
                + event.getPaymentId());

        Booking booking = bookingRepository.findById(event.getBookingId())
                .orElse(null);

        if(booking == null) {return;}

        booking.setStatus(BookingStatus.CONFIRMED);
        bookingRepository.save(booking);

        FlightInstanceResponse flightInstanceResponse=flightClient
                .getFlightInstanceById(booking.getFlightInstanceId());
        FareResponse fareResponse=pricingClient.getFareById(booking.getFareId());
        UserDto userDTO=userClient.getUserById(booking.getUserId());


        bookingEventProducer.sendBookingConfirmed(booking,event,flightInstanceResponse,
                fareResponse,userDTO);

    }

    @KafkaListener(topics = "payment.failed",groupId = "booking-service-group")
    public void handlePaymentFailed(PaymentFailedEvent event) throws Exception {
        Booking booking = bookingRepository.findById(event.getBookingId())
                .orElse(null);

        if(booking == null) {return;}

        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}
