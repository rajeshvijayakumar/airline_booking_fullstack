package com.vitara.payment.service.impl;

import com.razorpay.RazorpayException;
import com.vitara.common.dto.PaymentDto;
import com.vitara.common.dto.UserDto;
import com.vitara.common.enums.PaymentGateway;
import com.vitara.common.enums.PaymentStatus;
import com.vitara.common.request.PaymentInitiateRequest;
import com.vitara.common.request.PaymentVerifyRequest;
import com.vitara.common.response.PaymentInitiateResponse;
import com.vitara.common.response.PaymentLinkResponse;
import com.vitara.payment.client.UserClient;
import com.vitara.payment.entities.Payment;
import com.vitara.payment.mapper.PaymentMapper;
import com.vitara.payment.repository.PaymentRepository;
import com.vitara.payment.service.PaymentService;
import com.vitara.payment.service.gateway.RazorpayService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {


    private final PaymentRepository paymentRepository;
    private final RazorpayService razorpayService;
    private final UserClient userClient;

    @Override
    public PaymentInitiateResponse initiatePayment(PaymentInitiateRequest request) throws RazorpayException {
        paymentRepository.findByBookingId(request.getBookingId())
                .ifPresent(payment -> {
                    if(payment.getStatus() == PaymentStatus.SUCCESS){
                            throw new RuntimeException("payment already completed for this booking");
                    }
                });

        Payment payment=Payment.builder()
                .userId(request.getUserId())
                .bookingId(request.getBookingId())
                .amount(request.getAmount())
                .provider(request.getGateway())
                .status(PaymentStatus.PENDING)
                .transactionId(generateTransactionId())
                .build();

        payment=paymentRepository.save(payment);

        PaymentInitiateResponse response=PaymentInitiateResponse.builder()
                .paymentId(payment.getId())
                .gateway(request.getGateway())
                .transactionId(payment.getTransactionId())
                .amount(request.getAmount())
                .description(request.getDescription())
                .success(true)
                .message("payment initiated successfully")
                .build();

        if(request.getGateway()== PaymentGateway.RAZORPAY){
//            fetch user details using feign client
            UserDto userDTO=userClient.getUserById(request.getUserId());

//          create razorpay payment link using razorpay service
            PaymentLinkResponse paymentLinkResponse=razorpayService.createPaymentLink(
                    userDTO, payment
            );
            // set payment link to payment initiate response
            response.setRazorpayOrderId(paymentLinkResponse.getPayment_link_id());
            response.setCheckoutUrl(paymentLinkResponse.getPayment_link_url());


        }
        return response;
    }

    private String generateTransactionId() {
        return "TXN_"+System.currentTimeMillis()+"_"+
                UUID.randomUUID().toString().substring(0,8).toUpperCase();
    }

    @Override
    public PaymentDto verifyPayment(PaymentVerifyRequest request) throws Exception {

        JSONObject paymentDetails=razorpayService.fetchPaymentDetails(
                request.getRazorpayPaymentId()
        );

        String status=paymentDetails.optString("status");

        JSONObject notes=paymentDetails.getJSONObject("notes");
        Long paymentId=Long.parseLong(notes.optString("payment_id"));

        Payment payment=paymentRepository.findById(paymentId).orElseThrow(
                () -> new Exception("payment not found")
        );

        boolean isValid="captured".equalsIgnoreCase(status);
        if(isValid){
            if(payment.getProvider()==PaymentGateway.RAZORPAY){
                payment.setProviderPaymentId(request.getRazorpayPaymentId());
            }
            payment.setStatus(PaymentStatus.SUCCESS);
            payment.setPaidAt(LocalDateTime.now());
            paymentRepository.save(payment);


//          publish payment success kafka event

//            paymentEventProducer.sendPaymentCompleted(payment);

        }else{
            payment.setStatus(PaymentStatus.FAILED);
            payment.setFailureReason("Payment verification failed");
            paymentRepository.save(payment);

//          publish payment failed kafka event
//            paymentEventProducer.sendPaymentFailed(payment);
        }
        return PaymentMapper.toDTO(payment);
    }

    @Override
    public Page<PaymentDto> getAllPayments(Pageable pageable) {
        return paymentRepository.findAll(pageable).map(
                PaymentMapper::toDTO
        );
    }

    @Override
    public Map<Long, PaymentDto> getPaymentsByBookingIds(List<Long> bookingIds) {
        return paymentRepository.findByBookingIdIn(bookingIds)
                .stream().collect(Collectors.toMap(Payment::getId, PaymentMapper::toDTO));
    }
}
