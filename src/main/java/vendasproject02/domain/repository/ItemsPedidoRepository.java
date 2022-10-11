package vendasproject02.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vendasproject02.domain.entity.ItensPedido;

public interface ItemsPedidoRepository extends JpaRepository<ItensPedido, Integer> {

}
