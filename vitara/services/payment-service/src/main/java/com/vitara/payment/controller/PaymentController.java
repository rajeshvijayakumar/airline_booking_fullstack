package com.vitara.payment.controller;

import com.razorpay.RazorpayException;
import com.vitara.common.dto.PaymentDto;
import com.vitara.common.request.PaymentInitiateRequest;
import com.vitara.common.request.PaymentVerifyRequest;
import com.vitara.common.response.PaymentInitiateResponse;
import com.vitara.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<PaymentInitiateResponse> initiatePayment(
            @Valid @RequestBody PaymentInitiateRequest paymentInitiateRequest
    ) throws RazorpayException {
        PaymentInitiateResponse response=paymentService.initiatePayment(
                paymentInitiateRequest
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyPayment(
            @Valid @RequestBody PaymentVerifyRequest request)
            throws Exception {
        PaymentDto payment = paymentService.verifyPayment(request);
        return ResponseEntity.ok(payment);

    }

    @PostMapping("/batch/bookings")
    public ResponseEntity<Map<Long, PaymentDto>> getPaymentsByBookingIds(@RequestBody List<Long> bookingIds) {
        return ResponseEntity.ok(paymentService
                .getPaymentsByBookingIds(bookingIds));
    }

    @GetMapping
    public ResponseEntity<Page<PaymentDto>> getAllPayments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        Sort.Direction direction = sortDirection.equalsIgnoreCase("ASC") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<PaymentDto> payments = paymentService.getAllPayments(pageable);
        return ResponseEntity.ok(payments);
    }
}
