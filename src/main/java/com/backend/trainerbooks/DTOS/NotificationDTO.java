package com.backend.trainerbooks.DTOS;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.ZonedDateTime;

@Builder
@AllArgsConstructor
public class NotificationDTO {

    private Object notificationMessage;
    private ZonedDateTime date;

    public Object getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(Object notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

}
