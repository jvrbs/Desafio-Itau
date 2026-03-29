package br.com.ribas.desafioitau.transacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoRequest {
    @Schema(description = "Valor da transação em reais. Não pode ser negativo.", example = "150.75")
    private BigDecimal valor;

    @Schema(description = "Data e hora em que a transação ocorreu, com fuso horário. Não pode ser no futuro.", example = "2024-03-15T10:30:00-03:00")
    private OffsetDateTime dataHora;

}
