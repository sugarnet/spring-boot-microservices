package com.dss.ssm.services;

import com.dss.ssm.domain.Payment;
import com.dss.ssm.domain.PaymentEvent;
import com.dss.ssm.domain.PaymentState;
import com.dss.ssm.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;

import javax.transaction.Transactional;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PaymentServiceImplTest {

    @Autowired
    PaymentService paymentService;

    @Autowired
    PaymentRepository paymentRepository;

    Payment payment;

    @BeforeEach
    void setUp() {
        payment = Payment.builder().amount(new BigDecimal("12.99")).build();
    }

    @Transactional
    @Test
    void preAuth() {
        Payment savedPayment = paymentService.newPayment(payment);

        System.out.println("Should be NEW");
        System.out.println(savedPayment.getPaymentState());

        StateMachine<PaymentState, PaymentEvent> stateMachine = paymentService.preAuth(savedPayment.getId());

        Payment preAuthPayment = paymentRepository.getOne(savedPayment.getId());

        System.out.println(stateMachine.getState().getId());

        System.out.println("Should be PRE_AUTH or PRE_AUTH_ERROR");
        System.out.println(preAuthPayment.getPaymentState());
    }

    @Transactional
    @RepeatedTest(10)
    @Test
    void authorizePayment() {
        Payment savedPayment = paymentService.newPayment(payment);
        StateMachine<PaymentState, PaymentEvent> preAuthStateMachine = paymentService.preAuth(savedPayment.getId());

        if (preAuthStateMachine.getState().getId() == PaymentState.PRE_AUTH) {
            System.out.println("Payment is Pre Authorized");

            StateMachine<PaymentState, PaymentEvent> authStateMachine = paymentService.preAuth(savedPayment.getId());

            System.out.println("Result of Auth: " + authStateMachine.getState().getId());
        } else {
            System.out.println("Payment failed Pre-Auth...");
        }
    }
}