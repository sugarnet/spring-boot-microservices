package com.dss.ssm.services;

import com.dss.ssm.domain.Payment;
import com.dss.ssm.domain.PaymentEvent;
import com.dss.ssm.domain.PaymentState;
import com.dss.ssm.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class PaymentStateChangeInterceptor extends StateMachineInterceptorAdapter<PaymentState, PaymentEvent> {
    private final PaymentRepository paymentRepository;

    @Override
    public void preStateChange(State<PaymentState, PaymentEvent> state, Message<PaymentEvent> message, Transition<PaymentState, PaymentEvent> transition, StateMachine<PaymentState, PaymentEvent> stateMachine, StateMachine<PaymentState, PaymentEvent> rootStateMachine) {
        Optional.ofNullable(message).ifPresent(msg -> {
            Optional.ofNullable(Long.class.cast(message.getHeaders().getOrDefault(PaymentServiceImpl.PAYMENT_ID_HEADER, -1l)))
                    .ifPresent(paymentId -> {
                        Payment payment = paymentRepository.getById(paymentId);
                        payment.setPaymentState(state.getId());
                        paymentRepository.save(payment);
                    });
        });
    }
}
