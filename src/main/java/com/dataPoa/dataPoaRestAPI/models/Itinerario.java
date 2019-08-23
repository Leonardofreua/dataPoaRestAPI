package com.dataPoa.dataPoaRestAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "itinerarios")
public class Itinerario {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String idLinha;
    private Integer token;
    private String lat;
    private String lng;
}
