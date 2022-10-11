package vendasproject02.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vendasproject02.domain.entity.Cliente;
import vendasproject02.domain.entity.Pedido;
import vendasproject02.domain.entity.Produto;

import java.util.List;
import java.util.Optional;


public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

    List<Pedido> findByCliente (Cliente cliente);

    @Query("select p from Pedido p left join fetch p.itens where p.id = :id")
    Optional<Pedido> findByIdFecthItens(@Param("id") Integer id);
}
