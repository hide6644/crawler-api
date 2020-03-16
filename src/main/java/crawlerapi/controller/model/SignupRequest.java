package crawlerapi.controller.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignupRequest {

    @NotEmpty
    @Length(max = 16)
    private String username;

    @NotEmpty
    @Length(max = 16)
    private String password;

    @NotEmpty
    @Email
    @Length(max = 64)
    private String email;
}
