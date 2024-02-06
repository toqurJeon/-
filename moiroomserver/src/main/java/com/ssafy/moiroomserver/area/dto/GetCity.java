package com.ssafy.moiroomserver.area.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCity {
    private Long cityId;
    private Long metropolitanId;
    private String cityName;
}
