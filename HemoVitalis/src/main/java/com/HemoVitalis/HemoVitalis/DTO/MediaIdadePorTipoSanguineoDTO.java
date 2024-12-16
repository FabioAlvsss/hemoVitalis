package com.HemoVitalis.HemoVitalis.DTO;

import java.io.Serializable;

public class MediaIdadePorTipoSanguineoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tipoSanguineo;
    private Double mediaIdade;

    public MediaIdadePorTipoSanguineoDTO(String tipoSanguineo, Double mediaIdade) {
        this.tipoSanguineo = tipoSanguineo;
        this.mediaIdade = mediaIdade;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public Double getMediaIdade() {
        return mediaIdade;
    }

    public void setMediaIdade(Double mediaIdade) {
        this.mediaIdade = mediaIdade;
    }

}

