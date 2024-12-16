package com.HemoVitalis.HemoVitalis.service;

import com.HemoVitalis.HemoVitalis.DTO.*;
import com.HemoVitalis.HemoVitalis.entities.ListagemPaciente;
import com.HemoVitalis.HemoVitalis.entities.Paciente;
import com.HemoVitalis.HemoVitalis.repository.ListagemPacienteRepository;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ListagemPacienteService {
    @Autowired
    private ListagemPacienteRepository listagemPacienteRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PacienteService pacienteService;

    public static final String SEXO_MASCULINO = "Masculino";
    public static final String SEXO_FEMININO = "Feminino";
    public static final String TIPO_SANGUINEO_A_POSITIVO = "A+";
    public static final String TIPO_SANGUINEO_A_NEGATIVO = "A-";
    public static final String TIPO_SANGUINEO_B_POSITIVO = "B+";
    public static final String TIPO_SANGUINEO_B_NEGATIVO = "B-";
    public static final String TIPO_SANGUINEO_AB_POSITIVO = "AB+";
    public static final String TIPO_SANGUINEO_AB_NEGATIVO = "AB-";
    public static final String TIPO_SANGUINEO_O_POSITIVO = "O+";
    public static final String TIPO_SANGUINEO_O_NEGATIVO = "O-";

    @Transactional(readOnly = true)
    public List<ListagemPacienteDTO> findAll() {
        List<ListagemPaciente> list = listagemPacienteRepository.findAll();
        return list.stream().map(ListagemPacienteDTO::new).collect(Collectors.toList());
    }


    @Transactional
    public ListagemPacienteDTO save(ListagemPacienteDTO listagemPacienteDTO) {
        ListagemPaciente listagemPaciente = new ListagemPaciente();
        copyDTOtoEntity(listagemPacienteDTO, listagemPaciente);
        for (Paciente paciente : listagemPaciente.getPacientes()) {
            paciente.setHistoricoPaciente(listagemPaciente);
        }
        listagemPaciente.setDataHoraProcessamento(LocalDateTime.now());
        listagemPaciente = listagemPacienteRepository.save(listagemPaciente);
        return new ListagemPacienteDTO(listagemPaciente, listagemPaciente.getPacientes());
    }

    public void delete(Long id) {
        try {
            listagemPacienteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id não encontrado" + id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integridade Violada");
        }

    }

    @Transactional(readOnly = true)
    public ListagemPacienteDTO findById(Long id) {
        Optional<ListagemPaciente> historicoPaciente = listagemPacienteRepository.findById(id);
        ListagemPaciente entity = historicoPaciente.orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));
        return new ListagemPacienteDTO(entity, entity.getPacientes());
    }

    @Transactional
    public ListagemPacienteDTO update(Long id, ListagemPacienteDTO listagemPacienteDTO) {
        try {
            ListagemPaciente entity = listagemPacienteRepository.getOne(id);
            copyDTOtoEntity(listagemPacienteDTO, entity);
            listagemPacienteRepository.save(entity);
            return new ListagemPacienteDTO(entity, entity.getPacientes());
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("id não encontrado" + id);
        }
    }

    @Transactional(readOnly = true)
    public List<PacienteDTO> findListaPaciente(Long id) {
        Optional<ListagemPaciente> historicoPaciente = listagemPacienteRepository.findById(id);
        ListagemPaciente entity = historicoPaciente.orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));
        List<PacienteDTO> pacienteDTOS = entity.getPacientes().stream().map(
                PacienteDTO::new
        ).collect(Collectors.toList());
        return pacienteDTOS;
    }

    @Transactional(readOnly = true)
    public Page<ListagemPacienteDTO> findAllPage(PageRequest pageable) {
        Page<ListagemPaciente> list = listagemPacienteRepository.findAll(pageable);
        return list.map(ListagemPacienteDTO::new);
    }

    @Transactional(readOnly = true)
    public List<EstadoDTO> qtdPorEstado(Long id) {
        Optional<ListagemPaciente> historicoPaciente = listagemPacienteRepository.findById(id);
        ListagemPaciente entity = historicoPaciente.orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));
        Map<String, Long> quantidadePorEstado = entity.getPacientes().stream()
                .collect(Collectors.groupingBy(paciente -> {
                    return (paciente.getEstado() == null || paciente.getEstado().isEmpty())
                            ? "Desconhecido"
                            : paciente.getEstado();
                }, Collectors.counting()));
        List<EstadoDTO> dtos = quantidadePorEstado.entrySet().stream()
                .map(entry -> new EstadoDTO(entry.getKey(), entry.getValue(),entity.getPacientes().size()))
                .collect(Collectors.toList());
        return dtos;
    }

    @Transactional(readOnly = true)
    public EstadoDTO qtdPorCadaEstado(Long id, String estado) {
        Optional<ListagemPaciente> historicoPaciente = listagemPacienteRepository.findById(id);
        ListagemPaciente entity = historicoPaciente.orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));
        EstadoDTO estadoDTO = new EstadoDTO();
        estadoDTO.setEstado(estado);
        estadoDTO.setQuantidade(entity.getPacientes().stream().filter(paciente -> paciente.getEstado().equals(estado)).count());
        estadoDTO.setTotalDaAmostra(entity.getPacientes().size());
        return estadoDTO;
    }

    @Transactional(readOnly = true)
    public List<IMCDTO> imcFaixaEtaria(Long id) {
        Optional<ListagemPaciente> historicoPaciente = listagemPacienteRepository.findById(id);
        ListagemPaciente entity = historicoPaciente.orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));
        Map<String, List<Paciente>> pacientesPorFaixaEtaria = entity.getPacientes().stream()
                .collect(Collectors.groupingBy(paciente -> {
                    int idade = getIdade(paciente.getDataNascimento());
                    if (idade >= 0 && idade <= 10) return "0-10";
                    if (idade >= 11 && idade <= 20) return "11-20";
                    if (idade >= 21 && idade <= 30) return "21-30";
                    if (idade >= 31 && idade <= 40) return "31-40";
                    if (idade >= 41 && idade <= 50) return "41-50";
                    if (idade >= 51 && idade <= 60) return "51-60";
                    if (idade >= 61 && idade <= 70) return "61-70";
                    if (idade >= 71 && idade <= 80) return "71-80";
                    if (idade >= 81 && idade <= 90) return "81-60";
                    if (idade >= 91 && idade <= 100) return "91-70";
                    return "100+";
                }));
        Map<String, Double> imcMedioPorFaixaEtaria = new HashMap<>();
        List<IMCDTO> resultado = new ArrayList<>();

        for (Map.Entry<String, List<Paciente>> entry : pacientesPorFaixaEtaria.entrySet()) {
            String faixaEtaria = entry.getKey();
            List<Paciente> pacientesNaFaixa = entry.getValue();

            double imcTotal = pacientesNaFaixa.stream()
                    .mapToDouble(Paciente::getImc)
                    .sum();

            double imcMedio = imcTotal / pacientesNaFaixa.size();
            imcMedioPorFaixaEtaria.put(faixaEtaria, imcMedio);

            IMCDTO dto = new IMCDTO(faixaEtaria, imcMedio);
            resultado.add(dto);
        }
        return resultado;
    }

    @Transactional(readOnly = true)
    public PercentualObesidadeDTO calculoPercentualPorSexo(Long id) {
        Optional<ListagemPaciente> historicoPaciente = listagemPacienteRepository.findById(id);
        ListagemPaciente entity = historicoPaciente.orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));
        long totalHomens = entity.getPacientes().stream().filter(p -> p.getSexo().equalsIgnoreCase(SEXO_MASCULINO)).count();
        long obesosHomens = entity.getPacientes().stream()
                .filter(p -> p.getSexo().equalsIgnoreCase(SEXO_MASCULINO) && p.getImc() > 30)
                .count();
        double percentualHomens = (double) obesosHomens / totalHomens * 100;

        long totalMulheres = entity.getPacientes().stream().filter(p -> p.getSexo().equalsIgnoreCase(SEXO_FEMININO)).count();
        long obesosMulheres = entity.getPacientes().stream()
                .filter(p -> p.getSexo().equalsIgnoreCase(SEXO_FEMININO) && p.getImc() > 30)
                .count();
        double percentualMulheres = (double) obesosMulheres / totalMulheres * 100;

        PercentualObesidadeDTO percentualObesidadeDTO = new PercentualObesidadeDTO(percentualHomens, percentualMulheres,totalHomens,totalMulheres);
        return percentualObesidadeDTO;
    }

    @Transactional(readOnly = true)
    public List<MediaIdadePorTipoSanguineoDTO> tipoSanguineoPorIdade(Long id) {
        Optional<ListagemPaciente> historicoPaciente = listagemPacienteRepository.findById(id);
        ListagemPaciente entity = historicoPaciente.orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));

        Map<String, List<Paciente>> pacientesPorTipoSanguineo = entity.getPacientes().stream()
                .collect(Collectors.groupingBy(Paciente::getTipoSanguineo));

        List<MediaIdadePorTipoSanguineoDTO> tipoSanguineoDTOs = pacientesPorTipoSanguineo.entrySet().stream()
                .map(entry -> {
                    String tipoSanguineo = entry.getKey();
                    List<Paciente> pacientesNoTipo = entry.getValue();

                    double mediaIdade = pacientesNoTipo.stream()
                            .mapToInt(paciente -> getIdade(paciente.getDataNascimento()))
                            .average()
                            .orElse(0);

                    BigDecimal mediaArrendondada = new BigDecimal(mediaIdade)
                            .setScale(0, RoundingMode.HALF_UP);
                    return new MediaIdadePorTipoSanguineoDTO(tipoSanguineo, mediaArrendondada.doubleValue());
                })
                .collect(Collectors.toList());

        return tipoSanguineoDTOs;
    }

    @Transactional(readOnly = true)
    public List<TipoSanguineoDTO> findDoadoresEReceptores(Long id){

        Optional<ListagemPaciente> historicoPaciente = listagemPacienteRepository.findById(id);
        ListagemPaciente entity = historicoPaciente.orElseThrow(() -> new ResourceNotFoundException("Lista não encontrada"));
        List<Paciente> pacienteList = entity.getPacientes().stream().filter(paciente -> paciente.getPeso()>50.0).collect(Collectors.toList());
        List<Paciente> pacientesComIdadeEntre16e69 = pacienteList.stream()
                .filter(paciente -> {
                    int idade = getIdade(paciente.getDataNascimento());
                    return idade >= 16 && idade <= 69;
                })
                .collect(Collectors.toList());
        List<TipoSanguineoDTO> tipoSanguineoDTOs = calculoDoadoresEreceptores(pacientesComIdadeEntre16e69);


        return tipoSanguineoDTOs;
    }

    private static List<TipoSanguineoDTO> calculoDoadoresEreceptores(List<Paciente> pacientesComIdadeEntre16e69) {
        Map<String, Long> doadoresParaTipo = contarDoadoresParaCadaTipo(pacientesComIdadeEntre16e69);


        Map<String, Long> receptoresParaTipo = contarReceptoresParaCadaTipo(pacientesComIdadeEntre16e69);


        List<TipoSanguineoDTO> tipoSanguineoDTOs = doadoresParaTipo.keySet().stream()
                .map(tipo -> new TipoSanguineoDTO(
                        tipo,
                        doadoresParaTipo.get(tipo),
                        receptoresParaTipo.getOrDefault(tipo, 0L),
                        pacientesComIdadeEntre16e69.size()
                ))
                .collect(Collectors.toList());
        return tipoSanguineoDTOs;
    }

    @Transactional(readOnly = true)
    public TipoSanguineoDTO findDoadoresEReceptoresPorTipoSanguineo(Long id,String tipoSanguineo){
        List<TipoSanguineoDTO> tipoSanguineoDTOList = findDoadoresEReceptores(id);
        Optional<TipoSanguineoDTO> tipoSanguineoDTOOptional = tipoSanguineoDTOList.stream().filter(tipoSanguineoDTO -> tipoSanguineoDTO.getTipoSanguineo().equalsIgnoreCase(tipoSanguineo)).findFirst();
        if(tipoSanguineoDTOOptional.isPresent()){
        return tipoSanguineoDTOOptional.get();}
        else {
            return null;
        }
    }

    public static Map<String, Long> contarDoadoresParaCadaTipo(List<Paciente> pacientes) {
        Map<String, Set<String>> doadoresCompativeis = new HashMap<>();
        doadoresCompativeis.put(TIPO_SANGUINEO_A_POSITIVO, Set.of(TIPO_SANGUINEO_A_POSITIVO, TIPO_SANGUINEO_AB_POSITIVO));
        doadoresCompativeis.put(TIPO_SANGUINEO_A_NEGATIVO, Set.of(TIPO_SANGUINEO_A_POSITIVO, TIPO_SANGUINEO_A_NEGATIVO,TIPO_SANGUINEO_AB_POSITIVO,TIPO_SANGUINEO_AB_NEGATIVO));
        doadoresCompativeis.put(TIPO_SANGUINEO_B_POSITIVO, Set.of(TIPO_SANGUINEO_B_POSITIVO,TIPO_SANGUINEO_AB_POSITIVO));
        doadoresCompativeis.put(TIPO_SANGUINEO_B_NEGATIVO, Set.of(TIPO_SANGUINEO_B_POSITIVO, TIPO_SANGUINEO_B_NEGATIVO, TIPO_SANGUINEO_AB_POSITIVO, TIPO_SANGUINEO_AB_NEGATIVO));
        doadoresCompativeis.put(TIPO_SANGUINEO_AB_POSITIVO, Set.of(TIPO_SANGUINEO_AB_POSITIVO));
        doadoresCompativeis.put(TIPO_SANGUINEO_AB_NEGATIVO, Set.of(TIPO_SANGUINEO_AB_POSITIVO,TIPO_SANGUINEO_AB_NEGATIVO));
        doadoresCompativeis.put(TIPO_SANGUINEO_O_POSITIVO, Set.of(TIPO_SANGUINEO_A_POSITIVO, TIPO_SANGUINEO_B_POSITIVO, TIPO_SANGUINEO_O_POSITIVO, TIPO_SANGUINEO_AB_POSITIVO));
        doadoresCompativeis.put(TIPO_SANGUINEO_O_NEGATIVO, Set.of(TIPO_SANGUINEO_A_POSITIVO,TIPO_SANGUINEO_B_POSITIVO, TIPO_SANGUINEO_O_POSITIVO,TIPO_SANGUINEO_AB_POSITIVO,TIPO_SANGUINEO_A_NEGATIVO,TIPO_SANGUINEO_B_NEGATIVO,TIPO_SANGUINEO_O_NEGATIVO,TIPO_SANGUINEO_AB_NEGATIVO));


        return pacientes.stream()
                .flatMap(paciente -> doadoresCompativeis.entrySet().stream()
                        .filter(entry -> entry.getValue().contains(paciente.getTipoSanguineo()))
                        .map(entry -> entry.getKey())).collect(Collectors.groupingBy(tipo -> tipo, Collectors.counting()));
    }

    public static Map<String, Long> contarReceptoresParaCadaTipo(List<Paciente> pacientes) {
        Map<String, Set<String>> receptoresCompatibilidade = new HashMap<>();
        receptoresCompatibilidade.put(TIPO_SANGUINEO_A_POSITIVO, Set.of(TIPO_SANGUINEO_A_POSITIVO,TIPO_SANGUINEO_A_NEGATIVO,TIPO_SANGUINEO_O_POSITIVO,TIPO_SANGUINEO_O_NEGATIVO));
        receptoresCompatibilidade.put(TIPO_SANGUINEO_A_NEGATIVO, Set.of(TIPO_SANGUINEO_A_NEGATIVO, TIPO_SANGUINEO_O_NEGATIVO));
        receptoresCompatibilidade.put(TIPO_SANGUINEO_B_POSITIVO, Set.of(TIPO_SANGUINEO_B_POSITIVO, TIPO_SANGUINEO_B_NEGATIVO, TIPO_SANGUINEO_O_POSITIVO, TIPO_SANGUINEO_O_NEGATIVO));
        receptoresCompatibilidade.put(TIPO_SANGUINEO_B_NEGATIVO, Set.of(TIPO_SANGUINEO_B_NEGATIVO,TIPO_SANGUINEO_O_NEGATIVO));
        receptoresCompatibilidade.put(TIPO_SANGUINEO_AB_POSITIVO, Set.of(TIPO_SANGUINEO_A_POSITIVO,TIPO_SANGUINEO_B_POSITIVO,TIPO_SANGUINEO_O_POSITIVO,TIPO_SANGUINEO_AB_POSITIVO,TIPO_SANGUINEO_A_NEGATIVO,TIPO_SANGUINEO_B_NEGATIVO,TIPO_SANGUINEO_O_NEGATIVO,TIPO_SANGUINEO_AB_NEGATIVO));
        receptoresCompatibilidade.put(TIPO_SANGUINEO_AB_NEGATIVO, Set.of(TIPO_SANGUINEO_A_NEGATIVO, TIPO_SANGUINEO_B_NEGATIVO,TIPO_SANGUINEO_O_NEGATIVO,TIPO_SANGUINEO_AB_NEGATIVO));
        receptoresCompatibilidade.put(TIPO_SANGUINEO_O_POSITIVO, Set.of(TIPO_SANGUINEO_O_POSITIVO, TIPO_SANGUINEO_O_NEGATIVO));
        receptoresCompatibilidade.put(TIPO_SANGUINEO_O_NEGATIVO, Set.of(TIPO_SANGUINEO_O_NEGATIVO));


        return pacientes.stream()
                .flatMap(paciente -> receptoresCompatibilidade.entrySet().stream()
                        .filter(entry -> entry.getValue().contains(paciente.getTipoSanguineo()))
                        .map(entry -> entry.getKey()))
                .collect(Collectors.groupingBy(tipo -> tipo, Collectors.counting()));
    }

    private void copyDTOtoEntity(ListagemPacienteDTO dto, ListagemPaciente listagemPaciente) {
        listagemPaciente.setNome(dto.getNome());
        if (dto.getDataInclusao() != null) {
            listagemPaciente.setDataInclusao(dto.getDataInclusao());
        } else {
            listagemPaciente.setDataInclusao(LocalDate.now());
        }
        listagemPaciente.getPacientes().clear();
        for (PacienteDTO pacienteDTO : dto.getPacienteDTOList()) {
            Paciente paciente = new Paciente();
            pacienteService.copyDTOtoEntity(pacienteDTO, paciente);
            listagemPaciente.getPacientes().add(paciente);
        }

    }

    public int getIdade(String dataNascimento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data = LocalDate.parse(dataNascimento, formatter);
        LocalDate hoje = LocalDate.now();
        return Period.between(data, hoje).getYears();
    }
}
