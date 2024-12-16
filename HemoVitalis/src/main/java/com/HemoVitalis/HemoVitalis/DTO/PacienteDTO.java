package com.HemoVitalis.HemoVitalis.DTO;

import com.HemoVitalis.HemoVitalis.entities.Paciente;

import java.io.Serializable;

public class PacienteDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;
    private String cpf;
    private String rg;
    private String data_nasc;
    private String sexo;
    private String mae;
    private String pai;
    private String email;
    private String cep;
    private String endereco;
    private Long numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String telefone_fixo;
    private String celular;
    private double altura;
    private double peso;
    private String tipo_sanguineo;
    private double imc;

    private int idade;

    public PacienteDTO() {
    }

    public PacienteDTO(Long id, String nome, String cpf, String rg, String data_nasc, String sexo, String mae, String pai, String email, String cep, String endereco, Long numero, String bairro, String cidade, String estado, String telefone_fixo, String celular, double altura, double peso, String tipo_sanguineo, double imc) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.data_nasc = data_nasc;
        this.sexo = sexo;
        this.mae = mae;
        this.pai = pai;
        this.email = email;
        this.cep = cep;
        this.endereco = endereco;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
        this.telefone_fixo = telefone_fixo;
        this.celular = celular;
        this.altura = altura;
        this.peso = peso;
        this.tipo_sanguineo = tipo_sanguineo;
        this.imc = imc;

    }
    public PacienteDTO(Paciente paciente) {
        this.id = paciente.getId();
        this.nome = paciente.getNome();
        this.cpf = paciente.getCpf();
        this.rg = paciente.getRg();
        this.data_nasc = paciente.getDataNascimento().toString();
        this.sexo = paciente.getSexo();
        this.mae = paciente.getNomeDaMae();
        this.pai = paciente.getNomedoPai();
        this.email = paciente.getEmail();
        this.cep = paciente.getCep();
        this.endereco = paciente.getEndereco();
        this.numero = paciente.getNumero();
        this.bairro = paciente.getBairro();
        this.cidade = paciente.getCidade();
        this.estado = paciente.getEstado();
        this.telefone_fixo = paciente.getTelefoneFixo();
        this.celular = paciente.getCelular();
        this.altura = paciente.getAltura();
        this.peso = paciente.getPeso();
        this.tipo_sanguineo = paciente.getTipoSanguineo();
        this.imc = paciente.getImc();
    }

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getData_nasc() {
        return data_nasc;
    }

    public void setData_nasc(String data_nasc) {
        this.data_nasc = data_nasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getMae() {
        return mae;
    }

    public void setMae(String mae) {
        this.mae = mae;
    }

    public String getPai() {
        return pai;
    }

    public void setPai(String pai) {
        this.pai = pai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTelefone_fixo() {
        return telefone_fixo;
    }

    public void setTelefone_fixo(String telefone_fixo) {
        this.telefone_fixo = telefone_fixo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getTipo_sanguineo() {
        return tipo_sanguineo;
    }

    public void setTipo_sanguineo(String tipo_sanguineo) {
        this.tipo_sanguineo = tipo_sanguineo;
    }

    public double getImc() {
        return imc;
    }

    public void setImc(double imc) {
        this.imc = imc;
    }


}
