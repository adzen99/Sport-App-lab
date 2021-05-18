package web.converter;


import core.model.SportiveTrainer;
import org.springframework.stereotype.Component;
import web.dto.SportiveTrainerDto;

@Component
public class SportiveTrainerConverter extends BaseConverter<SportiveTrainer, SportiveTrainerDto>{

    @Override
    public SportiveTrainer convertDtoToModel(SportiveTrainerDto dto) {
        SportiveTrainer st = SportiveTrainer.builder()
                .sportiveID(dto.getSportiveID())
                .trainerID(dto.getTrainerID())
                .trainingType(dto.getTrainingType())
                .cost(dto.getCost())
                .build();
        st.setId(dto.getId());
        return st;
    }

    @Override
    public SportiveTrainerDto convertModelToDto(SportiveTrainer st) {
         SportiveTrainerDto dto = SportiveTrainerDto.builder()
                 .sportiveID(st.getSportiveID())
                 .trainerID(st.getTrainerID())
                 .trainingType(st.getTrainingType())
                 .cost(st.getCost())
                 .build();
        dto.setId(st.getId());
        return dto;
    }
}
