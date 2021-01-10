package org.drew.carcenter.data.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.drew.carcenter.data.models.User;

@Data
public class UserDTO {
    @JsonProperty("id")
    private Long id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("username")
    private String username;
    @JsonProperty("phone")
    private String phone;

    public static UserDTO fromEntity(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhone(user.getPhone());
        dto.setUsername(user.getUsername());

        return dto;
    }
}
