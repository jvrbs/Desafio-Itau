package br.com.ribas.desafioitau.estatistica;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.DoubleSummaryStatistics;

@Getter
@Schema(description = "Estatísticas calculadas sobre as transações dentro do intervalo de tempo solicitado")
public class EstatisticaDTO {

    @Schema(description = "Número total de transações no intervalo", example = "10")
    private final long count;

    @Schema(description = "Valor médio das transações no intervalo", example = "150.0")
    private final Double avg;

    @Schema(description = "Menor valor entre as transações no intervalo. Retorna 0 se não houver transações.", example = "50.0")
    private final Double min;

    @Schema(description = "Maior valor entre as transações no intervalo. Retorna 0 se não houver transações.", example = "300.0")
    private final Double max;

    public EstatisticaDTO() {
        this(new DoubleSummaryStatistics());
    }

    public EstatisticaDTO(final DoubleSummaryStatistics doubleSummaryStatistics) {
        this.count = doubleSummaryStatistics.getCount();
        this.avg = doubleSummaryStatistics.getAverage();
        this.min = doubleSummaryStatistics.getMin() == Double.POSITIVE_INFINITY ? 0 : doubleSummaryStatistics.getMin();
        this.max = doubleSummaryStatistics.getMax() == Double.NEGATIVE_INFINITY ? 0 : doubleSummaryStatistics.getMax();
    }
}
