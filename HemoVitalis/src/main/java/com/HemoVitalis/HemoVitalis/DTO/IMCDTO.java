package com.HemoVitalis.HemoVitalis.DTO;

import java.io.Serializable;

public class IMCDTO implements Serializable {

    private static final Long serialVersionUID = 1L;

    private String faixaEtaria;
    private double imc;

    public IMCDTO() {
    }

    public IMCDTO(String faixaEtaria, double imc) {
        this.faixaEtaria = faixaEtaria;
        this.imc = imc;
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setFaixaEtaria(String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }

}
