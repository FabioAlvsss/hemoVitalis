package com.HemoVitalis.HemoVitalis.DTO;

import com.HemoVitalis.HemoVitalis.entities.ListagemPaciente;
import com.HemoVitalis.HemoVitalis.entities.Paciente;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ListagemPacienteDTO implements Serializable {

    private static final long serialVersionUID = 1L;



    private Long id;
    private String nome;
    private LocalDate dataInclusao;

    private LocalDateTime dataHoraProcessamento;

    private List<PacienteDTO> pacienteDTOList =  new ArrayList<>();

    private int quantidadePacienteCadastrado;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(LocalDate dataInclusao) {
        this.dataInclusao = dataInclusao;
    }


    public LocalDateTime getDataHoraProcessamento() {
        return dataHoraProcessamento;
    }

    public void setDataHoraProcessamento(LocalDateTime dataHoraProcessamento) {
        this.dataHoraProcessamento = dataHoraProcessamento;
    }

    public List<PacienteDTO> getPacienteDTOList() {
        return pacienteDTOList;
    }

    public void setPacienteDTOList(List<PacienteDTO> pacienteDTOList) {
        this.pacienteDTOList = pacienteDTOList;
    }

    public int getQuantidadePacienteCadastrado() {
        return quantidadePacienteCadastrado;
    }

    public void setQuantidadePacienteCadastrado(int quantidadePacienteCadastrado) {
        this.quantidadePacienteCadastrado = quantidadePacienteCadastrado;
    }

    public ListagemPacienteDTO() {
    }

    public ListagemPacienteDTO(Long id, String nome, LocalDate dataInclusao, String erro, LocalDateTime dataHoraProcessamento, int quantidadePacienteCadastrado) {
        this.id = id;
        this.nome = nome;
        this.dataInclusao = dataInclusao;
        this.dataHoraProcessamento = dataHoraProcessamento;
        this.quantidadePacienteCadastrado = quantidadePacienteCadastrado;
    }

    public ListagemPacienteDTO(ListagemPaciente listagemPaciente){
        this.id = listagemPaciente.getId();
        this.nome = listagemPaciente.getNome();
        this.dataInclusao = listagemPaciente.getDataInclusao();

        this.dataHoraProcessamento = listagemPaciente.getDataHoraProcessamento();
        this.quantidadePacienteCadastrado = listagemPaciente.getPacientes().size();
    }

    public ListagemPacienteDTO(ListagemPaciente listagemPaciente, List<Paciente> pacienteSet){
        this(listagemPaciente);
        pacienteSet.forEach( paciente -> this.pacienteDTOList.add(new PacienteDTO(paciente)));
    }
}
