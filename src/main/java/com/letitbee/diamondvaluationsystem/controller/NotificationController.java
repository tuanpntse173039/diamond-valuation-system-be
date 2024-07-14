package com.letitbee.diamondvaluationsystem.controller;

import com.letitbee.diamondvaluationsystem.payload.NotificationDTO;
import com.letitbee.diamondvaluationsystem.service.NotificationService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotificationByAccount(@RequestParam(value = "accountId") Long id) {
        return new ResponseEntity<>(notificationService.getAllNotificationByAccount(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NotificationDTO> updateNotification(
            @RequestBody NotificationDTO notificationDTO,
            @PathVariable long id) {
        return new ResponseEntity<>(notificationService.updateNotification(notificationDTO, id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(
            @RequestBody NotificationDTO notificationDTO) {
        return new ResponseEntity<>(notificationService.createNotification(notificationDTO), HttpStatus.CREATED);
    }
}
