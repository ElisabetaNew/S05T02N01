package cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cat.itacademy.barcelonactiva.MarcualMora.Elisenda.s05.t02.n01.model.domain.Partida;

@Repository
public interface PartidaRepository extends JpaRepository <Partida, Integer> {
		 

}
