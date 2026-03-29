package br.com.ribas.desafioitau.estatistica;

import br.com.ribas.desafioitau.transacao.TransacaoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;

@Slf4j
@RestController
@RequestMapping(value = "/estatistica", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Estatísticas", description = "Consulta de estatísticas calculadas sobre as transações registradas")
public class EstatisticaController {

    private TransacaoRepository transacaoRepository;

    public EstatisticaController(TransacaoRepository transacaoRepository) {
        this.transacaoRepository = transacaoRepository;
    }

    @Operation(
            summary = "Retorna estatísticas das transações",
            description = "Calcula e retorna count, média, mínimo e máximo dos valores das transações registradas dentro da janela de tempo informada."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estatísticas calculadas com sucesso")
    })

    @GetMapping
    public ResponseEntity estatistica(@RequestParam(defaultValue = "60") Integer intervaloEmSegundos) {
        log.info("Calcular as estastisticas");

        final var horaInicial = OffsetDateTime.now().minusSeconds(intervaloEmSegundos);
        return ResponseEntity.ok(transacaoRepository.estatistica(horaInicial));
    }


}
