package ru.axol.authservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDto {
    @NotNull(message = "Тэг не может быть пустым")
    @Length(min = 3, max = 12, message = "Тэг должен быть от 3 до 12 символов в длину")
    private String userTag;

    @NotNull(message = "Почта не может быть пустой")
    @Email(message = "Указан неизвестный формат почты")
    private String email;

    @NotNull(message = "Пароль не может быть пустым")
    @Length(min = 6, message = "Пароль должен быть не короче 6 символов")
    private String password;
}