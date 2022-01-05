package com.dss.jms.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SimpleMessage implements Serializable {

    private static final long serialVersionUID = 6308726243345213758L;

    private UUID id;
    private String message;
}
