package vendasproject02.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class PedidoDTO {

    @NotNull(message = "Informe o código do Cliente.")
    private Integer cliente;

    @NotNull(message = "Campo Total do pedido é obrigatório,")
    private BigDecimal total;

    private List<ItemPedidoDTO> itens;

}
