package PetRegistrationSystem.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do pet é obrigatório")
    private String nome;

    @NotNull(message = "O tipo do pet é obrigatório")
    @Enumerated(EnumType.STRING)
    private TipoPet tipo;

    @NotNull(message = "O sexo do pet é obrigatório")
    @Enumerated(EnumType.STRING)
    private SexoPet sexo;

    @Embedded
    @Valid
    private Endereco endereco;

    @Min(value = 0, message = "Idade não pode ser negativa")
    private Integer idade;

    @Min(value = 0, message = "Peso não pode ser negativo")
    private Integer peso;

    @NotBlank(message = "A raça do pet é obrigatória")
    private String raca;

    public enum TipoPet {CACHORRO, GATO}
    public enum SexoPet {MACHO, FEMEA}
}

