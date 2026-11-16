package com.vitara.payment.service;

import com.razorpay.RazorpayException;
import com.vitara.common.dto.PaymentDto;
import com.vitara.common.request.PaymentInitiateRequest;
import com.vitara.common.request.PaymentVerifyRequest;
import com.vitara.common.response.PaymentInitiateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PaymentService {

    PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws RazorpayException;

    PaymentDto verifyPayment(PaymentVerifyRequest request) throws Exception;

    Page<PaymentDto> getAllPayments(Pageable pageable);

    Map<Long, PaymentDto> getPaymentsByBookingIds(List<Long> bookingIds);

}
