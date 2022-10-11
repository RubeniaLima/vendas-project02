package vendasproject02.service;

import vendasproject02.domain.entity.Pedido;
import vendasproject02.domain.enums.StatusPedido;
import vendasproject02.rest.dto.PedidoDTO;

import java.util.Optional;

public interface PedidoService {

    Pedido salvar(PedidoDTO pedidoDTO);

    Optional<Pedido>  obterPedidoCompleto(Integer id);
    void atualizaStatus(Integer id, StatusPedido statusPedido);

}
