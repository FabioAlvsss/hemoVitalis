package com.HemoVitalis.HemoVitalis.DTO;

import java.io.Serializable;

public class PercentualObesidadeDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private double percentualHomens;

    private Long totalDehomens;
    private double percentualMulheres;

    private Long totalDeMulheres;

    public PercentualObesidadeDTO(double percentualHomens, double percentualMulheres,long totalDehomens,long totalDeMulheres) {
        this.percentualHomens = percentualHomens;
        this.percentualMulheres = percentualMulheres;
        this.totalDehomens = totalDehomens;
        this.totalDeMulheres = totalDeMulheres;
    }

    public double getPercentualHomens() {
        return percentualHomens;
    }

    public void setPercentualHomens(double percentualHomens) {
        this.percentualHomens = percentualHomens;
    }

    public double getPercentualMulheres() {
        return percentualMulheres;
    }

    public void setPercentualMulheres(double percentualMulheres) {
        this.percentualMulheres = percentualMulheres;
    }

    public Long getTotalDehomens() {
        return totalDehomens;
    }

    public void setTotalDehomens(Long totalDehomens) {
        this.totalDehomens = totalDehomens;
    }

    public Long getTotalDeMulheres() {
        return totalDeMulheres;
    }

    public void setTotalDeMulheres(Long totalDeMulheres) {
        this.totalDeMulheres = totalDeMulheres;
    }

}

