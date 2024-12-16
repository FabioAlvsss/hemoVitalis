package com.HemoVitalis.HemoVitalis.resources;

import com.HemoVitalis.HemoVitalis.DTO.*;
import com.HemoVitalis.HemoVitalis.service.ListagemPacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/listagemPaciente")
@CrossOrigin(origins = "http://localhost:4200")
public class ListagemPacienteResource {

    @Autowired
    private ListagemPacienteService service;

    @PostMapping
    public ResponseEntity<ListagemPacienteDTO> save(@RequestBody ListagemPacienteDTO listagemPacienteDTO) {
        listagemPacienteDTO = service.save(listagemPacienteDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(listagemPacienteDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(listagemPacienteDTO);
    }

    @PostMapping("/savelistaListagemPaciente")
    public ResponseEntity<List<ListagemPacienteDTO>> saveLista(@RequestBody List<ListagemPacienteDTO> listagemPacienteDTOList) {
        List<ListagemPacienteDTO> listagemPacienteDTOS = new ArrayList<>();
        for (ListagemPacienteDTO listagemPacienteDTO : listagemPacienteDTOList) {
            listagemPacienteDTO = service.save(listagemPacienteDTO);
            listagemPacienteDTOS.add(listagemPacienteDTO);
        }
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand().toUri();
        return ResponseEntity.created(uri).body(listagemPacienteDTOS);
    }

    @GetMapping
    public ResponseEntity<List<ListagemPacienteDTO>> findAll() {
        List<ListagemPacienteDTO> listCategories = service.findAll();
        return ResponseEntity.ok().body(listCategories);
    }

    @GetMapping("/pageado")
    public ResponseEntity<Page<ListagemPacienteDTO>> findAllPageable(@RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
                                                                     @RequestParam(value = "linesPerPage", defaultValue = "5", required = false) Integer linesPerPage,
                                                                     @RequestParam(value = "direction", defaultValue = "ASC", required = false) String direction,
                                                                     @RequestParam(value = "orderBy", defaultValue = "id", required = false) String order) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), order);
        Page<ListagemPacienteDTO> listPaciente = service.findAllPage(pageRequest);
        return ResponseEntity.ok().body(listPaciente);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ListagemPacienteDTO> findById(@PathVariable Long id) {
        ListagemPacienteDTO listagemPacienteDTO = service.findById(id);
        return ResponseEntity.ok().body(listagemPacienteDTO);
    }

    @GetMapping("/listaDePacientes/{id}")
    public ResponseEntity<List<PacienteDTO>>findyListadePacientes(@PathVariable Long id) {
        List<PacienteDTO> pacienteDTOS = service.findListaPaciente(id);
        return ResponseEntity.ok().body(pacienteDTOS);
    }

    @GetMapping("estado/{id}")
    public ResponseEntity<List<EstadoDTO>> findPopulacaoCadaEstado(@PathVariable Long id) {
        List<EstadoDTO> estadoDTOS = service.qtdPorEstado(id);
        return ResponseEntity.ok().body(estadoDTOS);
    }

    @GetMapping("/{id}/{estado}")
    public ResponseEntity<EstadoDTO> findPopulacaoPorEstado(@PathVariable Long id,@PathVariable String estado) {
        EstadoDTO estadoDTO = service.qtdPorCadaEstado(id,estado);
        return ResponseEntity.ok().body(estadoDTO);
    }

    @GetMapping("/imc/{id}")
    public ResponseEntity<List<IMCDTO>> findImcPorFaixaEtaria(@PathVariable Long id) {
        List<IMCDTO> imcdtos = service.imcFaixaEtaria(id);
        return ResponseEntity.ok().body(imcdtos);
    }

    @GetMapping("/percentualObesidade/{id}")
    public ResponseEntity<PercentualObesidadeDTO> findPercentualObesidadePorSexo(@PathVariable Long id) {
        PercentualObesidadeDTO percentualObesidadeDTO = service.calculoPercentualPorSexo(id);
        return ResponseEntity.ok().body(percentualObesidadeDTO);
    }

    @GetMapping("/tipoSanguineoPorIdade/{id}")
    public ResponseEntity<List<MediaIdadePorTipoSanguineoDTO>> findMediaIdadePorTipoSanguineo(@PathVariable Long id) {
        List<MediaIdadePorTipoSanguineoDTO> mediaIdadePorTipoSanguineoDTOS = service.tipoSanguineoPorIdade(id);
        return ResponseEntity.ok().body(mediaIdadePorTipoSanguineoDTOS);
    }

    @GetMapping("/receptorEdoadores/{id}")
    public ResponseEntity<List<TipoSanguineoDTO>> findReceptoresEDoadores(@PathVariable Long id) {
        List<TipoSanguineoDTO> tipoSanguineoDTOS= service.findDoadoresEReceptores(id);
        return ResponseEntity.ok().body(tipoSanguineoDTOS);
    }

    @GetMapping("/receptorEdoadores/{id}/{tipoSanguineo}")
    public ResponseEntity<TipoSanguineoDTO> findReceptoresEDoadoresPorTipoSanguineo(@PathVariable Long id,@PathVariable String tipoSanguineo) {
        TipoSanguineoDTO tipoSanguineoDTO= service.findDoadoresEReceptoresPorTipoSanguineo(id,tipoSanguineo);
        return ResponseEntity.ok().body(tipoSanguineoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListagemPacienteDTO> update(@PathVariable Long id, @RequestBody ListagemPacienteDTO listagemPacienteDTO) {
        ListagemPacienteDTO ProductDTO = service.update(id, listagemPacienteDTO);

        return ResponseEntity.ok().body(ProductDTO);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


}
