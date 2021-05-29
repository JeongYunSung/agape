package com.yunseong.core.notification.service;

import com.yunseong.core.common.exception.DifferentOwnerException;
import com.yunseong.core.common.exception.EntityNotFoundException;
import com.yunseong.core.member.service.MemberDetailsService;
import com.yunseong.core.notification.controller.CreateNotificationRequest;
import com.yunseong.core.notification.controller.CreateNotificationResponse;
import com.yunseong.core.notification.controller.FindNotificationResponse;
import com.yunseong.core.notification.domain.Notification;
import com.yunseong.core.notification.domain.NotificationRepository;
import com.yunseong.core.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;

    private final MemberDetailsService memberDetailsService;

    public CreateNotificationResponse sendNotification(String email, CreateNotificationRequest request) {
        Notification notification = this.notificationRepository.save(new Notification(this.memberDetailsService.findMember(email), request.getTitle(), request.getDescription()));
        return new CreateNotificationResponse(notification);
    }

    @Transactional(readOnly = true)
    public Page<FindNotificationResponse> findNotifications(String email, Pageable pageable) {
        Page<Notification> page = this.notificationRepository.findAllByEmail(email, pageable);
        return page.map(FindNotificationResponse::new);
    }

    @Transactional(readOnly = true)
    public FindNotificationResponse findNotification(String email, long id) {
        Notification notification = findNotification(id);
        if(!notification.getReceiver().getEmail().equals(email)) {
            throw new DifferentOwnerException(Order.class, email);
        }
        return new FindNotificationResponse(notification);
    }

    private Notification findNotification(long id) {
        return this.notificationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Notification.class, String.valueOf(id)));
    }
}
