package web.converter;


import core.model.Trainer;
import org.springframework.stereotype.Component;
import web.dto.TrainerDto;

@Component
public class TrainerConverter extends BaseConverter<Trainer, TrainerDto>{
    @Override
    public Trainer convertDtoToModel(TrainerDto dto) {
        Trainer trainer = Trainer.builder()
                .age(dto.getAge())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
        trainer.setId(dto.getId());
        return trainer;
    }

    @Override
    public TrainerDto convertModelToDto(Trainer trainer) {
        TrainerDto dto = TrainerDto.builder()
                .age(trainer.getAge())
                .firstName(trainer.getFirstName())
                .lastName(trainer.getLastName())
                .build();
        dto.setId(trainer.getId());
        return dto;
    }
}
