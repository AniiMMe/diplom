package com.example.diplom.dto;

import com.example.diplom.entity.User;
import com.example.diplom.entity.Workers;
import lombok.*;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class WorkersDTO extends User {
    @NotEmpty
    @NonNull
    @Pattern(regexp = "^[А-ЯЁа-яё\s]+$")
    private String workerName;
    @NotEmpty
    @NonNull
    @Pattern(regexp = "^[А-ЯЁа-яё\s]+$")
    private String workerSurname;
    @NotEmpty
    @NonNull
    @Pattern(regexp = "^[A-z0-9_.-]{1,}@[A-z0-9_.-]{1,}\\.[A-z]{2,8}$")
    private String workerEmail;
    @NotEmpty
    @NonNull
    @Pattern(regexp = "^\\+(375|80)(29|33|25|44|17)\\d{7}$")
    private String workerTel;

    public Workers build() {
        return new Workers(workerName, workerSurname, workerEmail, workerTel);
    }
}
