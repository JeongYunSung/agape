package com.yunseong.core.payment.service;

import com.yunseong.core.common.exception.EntityNotFoundException;
import com.yunseong.core.common.exception.NotConnectException;
import com.yunseong.core.order.domain.Order;
import com.yunseong.core.payment.*;
import com.yunseong.core.payment.config.PaymentConfiguration;
import com.yunseong.core.payment.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@Transactional
@RequiredArgsConstructor
public class KakaoPayService implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentConfiguration paymentConfiguration;
    private final RestTemplate restTemplate;

    @Override
    public KakaoPayReadyVO ready(Order order) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("cid", "TC0ONETIME");
        params.add("partner_order_id", String.valueOf(order.getId()));
        params.add("partner_user_id", order.getOrderer().getEmail());
        params.add("item_name", order.getOrders().get(0).getFood().getName() + "외 " + (order.getOrders().size()-1) + "개");
        params.add("quantity", String.valueOf(order.getTotalCount()));
        params.add("total_amount", String.valueOf(order.getTotalAmount()));
        params.add("tax_free_amount", String.valueOf(order.getTotalAmount()/10));
        params.add("approval_url", this.paymentConfiguration.getKakaopay().getSuccessUri() + "/" + order.getId());
        params.add("cancel_url", this.paymentConfiguration.getKakaopay().getCancelUri() + "/" + order.getId());
        params.add("fail_url", this.paymentConfiguration.getKakaopay().getSuccessFailUri() + "/" + order.getId());

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, getHeaders());

        KakaoPayReadyVO kakaoPayReadyVO;
        try {
            kakaoPayReadyVO = this.restTemplate.postForObject(new URI(this.paymentConfiguration.getKakaopay().getHost() + "/v1/payment/ready"), body, KakaoPayReadyVO.class);
        } catch (URISyntaxException e) {
            throw new NotConnectException("카카오페이 서버에 연결할 수 없습니다.");
        }
        KakaoPayPayment kakaopay = new KakaoPayPayment(kakaoPayReadyVO.getTid(), PaymentMethod.KAKAO_PAY);
        kakaopay.setOrder(order);
        this.paymentRepository.save(kakaopay);
        return kakaoPayReadyVO;
    }

    public void approve(String email, long id, String pg_token) {
        Payment payment = getPayment(id);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("cid", "TC0ONETIME");
        params.add("tid", payment.getTid());
        params.add("partner_order_id", String.valueOf(payment.getId()));
        params.add("partner_user_id", email);
        params.add("pg_token", pg_token);

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, getHeaders());

        try {
            this.restTemplate.postForObject(new URI(this.paymentConfiguration.getKakaopay().getHost() + "/v1/payment/approve"), body, KakaoPayApproveVO.class);
            payment.getOrder().order();
        } catch (URISyntaxException e) {
            throw new NotConnectException("카카오페이 서버에 연결할 수 없습니다.");
        }
    }

    public void cancel(long id) {
        Payment payment = this.getPayment(id);
        payment.cancel();
        payment.getOrder().cancel();
    }

    public void refund(long id) {
        this.refund(this.paymentRepository.findFetchById(id).orElseThrow(() -> new EntityNotFoundException(Payment.class, String.valueOf(id))));
    }

    @Override
    public void refund(Payment payment) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("cid", "TC0ONETIME");
        params.add("tid", payment.getTid());
        params.add("cancel_amount", String.valueOf(payment.getOrder().getTotalAmount()));
        params.add("cancel_tax_free_amount", String.valueOf(payment.getOrder().getTotalAmount()/10));

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, getHeaders());

        try {
            this.restTemplate.postForObject(new URI(this.paymentConfiguration.getKakaopay().getHost() + "/v1/payment/cancel"), body, KakaoPayCancelVO.class);
            payment.refund();
        } catch (URISyntaxException e) {
            throw new NotConnectException("카카오페이 서버에 연결할 수 없습니다.");
        }
    }

    public KakaoPayOrderVO order(long id) {
        Payment payment = getPayment(id);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        params.add("cid", "TC0ONETIME");
        params.add("tid", payment.getTid());

        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<>(params, getHeaders());

        try {
            return this.restTemplate.postForObject(new URI(this.paymentConfiguration.getKakaopay().getHost() + "/v1/payment/order"), body, KakaoPayOrderVO.class);
        } catch (URISyntaxException e) {
            throw new NotConnectException("카카오페이 서버에 연결할 수 없습니다.");
        }
    }


    private Payment getPayment(long id) {
        return this.paymentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Payment.class, String.valueOf(id)));
    }

    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add(HttpHeaders.AUTHORIZATION, "KakaoAK ");
        httpHeaders.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        return httpHeaders;
    }
}
