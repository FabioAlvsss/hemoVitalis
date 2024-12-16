package com.HemoVitalis.HemoVitalis.repository;

import com.HemoVitalis.HemoVitalis.entities.ListagemPaciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListagemPacienteRepository extends JpaRepository<ListagemPaciente,Long> {

}
