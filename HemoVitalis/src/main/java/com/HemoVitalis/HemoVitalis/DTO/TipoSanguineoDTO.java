package com.HemoVitalis.HemoVitalis.DTO;

import java.io.Serializable;

public class TipoSanguineoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tipoSanguineo;
    private Long doadores;
    private Long receptores;
    private int totalDaAmostra;

    public TipoSanguineoDTO() {
    }

    public TipoSanguineoDTO(String tipoSanguineo, Long doadores, Long receptores, int totalDaAmostra) {
        this.tipoSanguineo = tipoSanguineo;
        this.doadores = doadores;
        this.receptores = receptores;
        this.totalDaAmostra = totalDaAmostra;
    }

    public TipoSanguineoDTO(String tipoSanguineo, Long doadores, Long receptores) {
        this.tipoSanguineo = tipoSanguineo;
        this.doadores = doadores;
        this.receptores = receptores;
    }

    public String getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(String tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    public Long getDoadores() {
        return doadores;
    }

    public void setDoadores(Long doadores) {
        this.doadores = doadores;
    }

    public Long getReceptores() {
        return receptores;
    }

    public void setReceptores(Long receptores) {
        this.receptores = receptores;
    }

    public int getTotalDaAmostra() {
        return totalDaAmostra;
    }

    public void setTotalDaAmostra(int totalDaAmostra) {
        this.totalDaAmostra = totalDaAmostra;
    }
}
