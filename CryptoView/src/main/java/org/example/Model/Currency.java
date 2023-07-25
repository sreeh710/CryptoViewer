package org.example.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
@AllArgsConstructor
public class Currency {
    public String code;
    public String symbol;
}
