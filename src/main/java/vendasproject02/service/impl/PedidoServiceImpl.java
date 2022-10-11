package vendasproject02.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vendasproject02.domain.entity.Cliente;
import vendasproject02.domain.entity.ItensPedido;
import vendasproject02.domain.entity.Pedido;
import vendasproject02.domain.entity.Produto;
import vendasproject02.domain.enums.StatusPedido;
import vendasproject02.domain.repository.ClienteRepository;
import vendasproject02.domain.repository.ItemsPedidoRepository;
import vendasproject02.domain.repository.PedidoRepository;
import vendasproject02.domain.repository.ProdutoRepository;
import vendasproject02.exception.PedidoNaoEncontradoException;
import vendasproject02.exception.RegraNegocioException;
import vendasproject02.rest.dto.ItemPedidoDTO;
import vendasproject02.rest.dto.PedidoDTO;
import vendasproject02.service.PedidoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final ProdutoRepository produtoRepository;
    private final ItemsPedidoRepository itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar(PedidoDTO pedidoDTO) {
        Integer idCliente = pedidoDTO.getCliente();
        Cliente cliente= clienteRepository
                .findById(idCliente)
                .orElseThrow(()-> new RegraNegocioException("Código de cliente inválido"));

        Pedido pedido = new Pedido();
        pedido.setTotal(pedidoDTO.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);
        pedido.setStatus(StatusPedido.REALIZADO);

        List<ItensPedido> itensPedido= converterItens(pedido, pedidoDTO.getItens());
        pedidoRepository.save(pedido);
        itemsPedidoRepository.saveAll(itensPedido);
        pedido.setItens(itensPedido);
        return pedido;
    }


    private List<ItensPedido> converterItens(Pedido pedido, List<ItemPedidoDTO> itens){
        if(itens.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem itens.");
         }

        return itens.stream().map(dto-> {
            Integer idProduto = dto.getProduto();
           Produto produto= produtoRepository.findById(idProduto)
                    .orElseThrow(()-> new RegraNegocioException("Código de produto inválido." + idProduto));

            ItensPedido itensPedido = new ItensPedido();
            itensPedido.setQuantidade(dto.getQuantidade());
            itensPedido.setPedido(pedido);
            itensPedido.setProduto(produto);
            return itensPedido;

        }).collect(Collectors.toList());
    }

    @Override
    public Optional<Pedido> obterPedidoCompleto(Integer id) {

        return pedidoRepository.findByIdFecthItens(id);
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusPedido statusPedido) {
        pedidoRepository
                .findById(id)
                .map(pedido -> {
                    pedido.setStatus(statusPedido);
                    return pedidoRepository.save(pedido);
                }).orElseThrow(()->new PedidoNaoEncontradoException());
    }

}
