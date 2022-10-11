package vendasproject02.rest.controller;

import static org.springframework.http.HttpStatus.*;

import jdk.jshell.Snippet;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import vendasproject02.domain.entity.ItensPedido;
import vendasproject02.domain.entity.Pedido;
import vendasproject02.domain.enums.StatusPedido;
import vendasproject02.rest.dto.AtualizacaoStatusPedidoDTO;
import vendasproject02.rest.dto.InformacaoItemPedidoDTO;
import vendasproject02.rest.dto.InformacoesPedidoDTO;
import vendasproject02.rest.dto.PedidoDTO;
import vendasproject02.service.PedidoService;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save(@RequestBody PedidoDTO pedidoDTO){
        Pedido pedido = pedidoService.salvar(pedidoDTO);
        return pedido.getId();
    }

    @GetMapping("{id}")
    public InformacoesPedidoDTO getById(@PathVariable Integer id){
        return pedidoService.
                obterPedidoCompleto(id)
                .map(p->converter(p))
                .orElseThrow(()->new ResponseStatusException(NOT_FOUND, "Pedido não encontrado"));

    }

    @PatchMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void updateStatus(@PathVariable Integer id,
                             @RequestBody AtualizacaoStatusPedidoDTO dto) {
        String novoStatus = dto.getNovoStatus();
        pedidoService.atualizaStatus( id, StatusPedido.valueOf(novoStatus));

    }


    @Builder
    private InformacoesPedidoDTO converter(Pedido pedido){
        return InformacoesPedidoDTO
                .builder()
                .codigo(pedido.getId())
                .dataPedido(pedido.getDataPedido().format(DateTimeFormatter.ofPattern("dd/MM/YY")))
                .cpf(pedido.getCliente().getCpf())
                .nomeCliente(pedido.getCliente().getNome())
                .total(pedido.getTotal())
                .items(converter(pedido.getItens()))
                .status(pedido.getStatus().name())
                .build();

    }

    private List<InformacaoItemPedidoDTO> converter(List<ItensPedido> itens){

        if(CollectionUtils.isEmpty(itens)){
            return Collections.emptyList();
        }
        return itens.stream().map(
                item -> InformacaoItemPedidoDTO
                        .builder().descricaoProduto(item.getProduto().getDescricao())
                        .precoUnitario(item.getProduto().getPreco())
                        .quantidade(item.getQuantidade())
                        .build()
        ).collect(Collectors.toList());
    }
}
