package vendasproject02.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vendasproject02.domain.entity.Cliente;
import vendasproject02.domain.entity.Produto;

import java.util.List;


public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
