package com.backend.trainerbooks.DTOS;

import com.backend.trainerbooks.enums.LikeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LikeDTO {

    private Long id;
    private LikeEnum likeEnum;
    private UserDTO byUser;
}
