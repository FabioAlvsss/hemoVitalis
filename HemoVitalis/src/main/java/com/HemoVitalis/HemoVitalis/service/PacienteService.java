package com.HemoVitalis.HemoVitalis.service;

import com.HemoVitalis.HemoVitalis.DTO.PacienteDTO;
import com.HemoVitalis.HemoVitalis.entities.Paciente;
import com.HemoVitalis.HemoVitalis.repository.PacienteRepository;
import com.HemoVitalis.HemoVitalis.service.exceptions.DataBaseException;
import com.HemoVitalis.HemoVitalis.service.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    @Transactional(readOnly = true)
    public List<PacienteDTO> findAll() {
        List<Paciente> list = pacienteRepository.findAll();
        return list.stream().map(PacienteDTO::new).collect(Collectors.toList());
    }

    public PacienteDTO save(PacienteDTO pacienteDTO) {
        Paciente paciente = new Paciente();
        copyDTOtoEntity(pacienteDTO, paciente);
        paciente = pacienteRepository.save(paciente);
        return new PacienteDTO(paciente);
    }

    public void delete(Long id) {
        try {
            pacienteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id não encontrado" + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integridade Violada");
        }

    }

    @Transactional(readOnly = true)
    public PacienteDTO findById(Long id) {
        Optional<Paciente> paciente = pacienteRepository.findById(id);
        Paciente entity = paciente.orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada"));
        return new PacienteDTO(entity);
    }

    @Transactional
    public PacienteDTO update(Long id, PacienteDTO pacienteDTO) {
        try {
            Paciente entity = pacienteRepository.getOne(id);
            copyDTOtoEntity(pacienteDTO,entity);
            pacienteRepository.save(entity);
            return new PacienteDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("id Not Found" + id);
        }
    }

    @Transactional(readOnly = true)
    public Page<PacienteDTO> findAllPage(PageRequest pageable) {
        Page<Paciente> list = pacienteRepository.findAll(pageable);
        return list.map(PacienteDTO::new);
    }

    public void copyDTOtoEntity(PacienteDTO pacienteDTO, Paciente paciente) {
        paciente.setNome(pacienteDTO.getNome());
        paciente.setCpf(pacienteDTO.getCpf());
        paciente.setRg(pacienteDTO.getRg());
        paciente.setDataNascimento(pacienteDTO.getData_nasc());
        paciente.setSexo(pacienteDTO.getSexo());
        paciente.setNomeDaMae(pacienteDTO.getMae());
        paciente.setNomedoPai(pacienteDTO.getPai());
        paciente.setEmail(pacienteDTO.getEmail());
        paciente.setCep(pacienteDTO.getCep());
        paciente.setEndereco(pacienteDTO.getEndereco());
        paciente.setNumero(pacienteDTO.getNumero());
        paciente.setBairro(pacienteDTO.getBairro());
        paciente.setCidade(pacienteDTO.getCidade());
        paciente.setEstado(pacienteDTO.getEstado());
        paciente.setTelefoneFixo(pacienteDTO.getTelefone_fixo());
        paciente.setCelular(pacienteDTO.getCelular());
        paciente.setAltura(pacienteDTO.getAltura());
        paciente.setPeso(pacienteDTO.getPeso());
        paciente.setTipoSanguineo(pacienteDTO.getTipo_sanguineo());
        paciente.setImc(calculoImc(pacienteDTO));

    }

    private double calculoImc(PacienteDTO pacienteDTO) {
        double imc = 0;
        if (Objects.nonNull(pacienteDTO.getPeso()) && Objects.nonNull(pacienteDTO.getAltura()) && pacienteDTO.getAltura() > 0) {
            imc = pacienteDTO.getPeso() / Math.pow(pacienteDTO.getAltura(), 2);
        }
        return imc;
    }




}
