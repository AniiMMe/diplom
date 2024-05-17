package com.example.diplom.dto;

import com.example.diplom.entity.Client;
import com.example.diplom.entity.Orders;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO {

    private int clientId;
    @NotEmpty
    @NonNull
    @Pattern(regexp = "^[А-ЯЁа-яё\s]+$")
    private String clientName;
    @NotEmpty
    @NonNull
    private String clientAddress;
    @NotEmpty
    @NonNull
    @Pattern(regexp = "^\\+(375|80)(29|33|25|44|17)\\d{7}$")
    private String clientTel;
    @NotEmpty
    @NonNull
    @Pattern(regexp = "^[A-z0-9_.-]{1,}@[A-z0-9_.-]{1,}\\.[A-z]{2,8}$")
    private String clientEmail;

    private List<Orders> orders;

    public Client build() {
        return Client.builder()
                .clientName(clientName)
                .clientAddress(clientAddress)
                .clientEmail(clientEmail)
                .clientTel(clientTel)
                .build();
    }
}
