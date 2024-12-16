package com.HemoVitalis.HemoVitalis.resources;

import com.HemoVitalis.HemoVitalis.DTO.PacienteDTO;
import com.HemoVitalis.HemoVitalis.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/pacientes")
@CrossOrigin(origins = "http://localhost:4200")
public class PacienteResource {

    @Autowired
    private PacienteService service;

    @GetMapping
    public ResponseEntity<List<PacienteDTO>> findAll() {
        List<PacienteDTO> listPacientes = service.findAll();
        return ResponseEntity.ok().body(listPacientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity <PacienteDTO>findById(@PathVariable Long id) {
        PacienteDTO pacienteDTO = service.findById(id);
        return  ResponseEntity.ok().body(pacienteDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteDTO> update(@PathVariable Long id,@RequestBody PacienteDTO pacienteDTO) {
        PacienteDTO pacienteDTO1DTO = service.update(id,pacienteDTO);
        return ResponseEntity.ok().body(pacienteDTO1DTO);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return  ResponseEntity.noContent().build();
    }


}
