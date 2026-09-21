package com.ritesh.notificationservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEvent {

    private Long employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String designation;
}