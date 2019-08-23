package com.dataPoa.dataPoaRestAPI.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "linhasOnibus")
public class LinhaOnibus {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    private String codigo;
    private String nome;
    @DBRef
    private List<Itinerario> itinerarios;
}
