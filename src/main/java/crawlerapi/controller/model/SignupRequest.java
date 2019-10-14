package crawlerapi.controller.model;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotEmpty
    @Length(max = 16)
    private String username;

    @NotEmpty
    @Length(max = 16)
    private String password;
}
