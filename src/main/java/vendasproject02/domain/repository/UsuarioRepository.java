package vendasproject02.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vendasproject02.domain.entity.Cliente;
import vendasproject02.domain.entity.Usuario;

import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);
}
