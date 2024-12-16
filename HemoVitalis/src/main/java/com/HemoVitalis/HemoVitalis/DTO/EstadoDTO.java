package com.HemoVitalis.HemoVitalis.DTO;

import java.io.Serializable;

public class EstadoDTO implements Serializable {

    private static final Long serialVersionUID = 1L;

    private String estado;
    private long quantidade;

    private int totalDaAmostra;


    public EstadoDTO() {
    }

    public EstadoDTO(String estado, long quantidade,int totalDaAmostra) {
        this.estado = estado;
        this.quantidade = quantidade;
        this.totalDaAmostra = totalDaAmostra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(long quantidade) {
        this.quantidade = quantidade;
    }

    public int getTotalDaAmostra() {
        return totalDaAmostra;
    }

    public void setTotalDaAmostra(int totalDaAmostra) {
        this.totalDaAmostra = totalDaAmostra;
    }

}
