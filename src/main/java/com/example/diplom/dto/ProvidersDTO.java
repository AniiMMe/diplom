package com.example.diplom.dto;

import com.example.diplom.entity.Supply;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProvidersDTO {

    private int providerId;
    @NotEmpty
    @NonNull
    @Pattern(regexp = "^[А-ЯЁа-яё\s]+$")
    private String providerName;
    @NotEmpty
    @NonNull
    private String providerAddress;
    @NotEmpty
    @NonNull
    @Pattern(regexp = "^\\+(375|80)(29|33|25|44|17)\\d{7}$")
    private String providerTel;
    @NotEmpty
    @NonNull
    @Pattern(regexp = "^[A-z0-9_.-]{1,}@[A-z0-9_.-]{1,}\\.[A-z]{2,8}$")
    private String providerEmail;


    private List<Supply> supplies;

}
