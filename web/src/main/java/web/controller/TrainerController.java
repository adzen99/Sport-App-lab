package web.controller;

import core.service.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.TrainerConverter;
import web.dto.TrainerDto;
import web.dto.TrainersDto;

import java.util.List;
import java.util.Set;

@RestController
public class TrainerController {
    public static final Logger log = LoggerFactory.getLogger(TrainerController.class);

    @Autowired
    private TrainerService trainerService;

    @Autowired
    private TrainerConverter trainerConverter;


    @RequestMapping(value = "/trainers", method = RequestMethod.GET)
    List<TrainerDto> getTrainers() {
        log.trace("TrainerController - getTrainers - enter method");
        List<TrainerDto> cpy = new TrainersDto(trainerConverter
                .convertModelsToDtos(trainerService.getAllTrainers())).getTrainers();
        log.trace("end get trainers={}",cpy);
        return cpy;
    }

    @RequestMapping(value = "/trainers", method = RequestMethod.POST)
    TrainerDto saveTrainer(@RequestBody TrainerDto trainerDto) {
        log.trace("TrainerController - saveTrainer - enter method");
        return trainerConverter.convertModelToDto(trainerService.saveTrainer(
                trainerConverter.convertDtoToModel(trainerDto)
        ));
    }

    @RequestMapping(value = "/trainers/{id}", method = RequestMethod.PUT)
    TrainerDto updateTrainer(@PathVariable Long id,
                         @RequestBody TrainerDto trainerDto) {
        log.trace("TrainerController - updateTrainer - enter method");
        return trainerConverter.convertModelToDto( trainerService.updateTrainer(id,
                trainerConverter.convertDtoToModel(trainerDto)));
    }

    @RequestMapping(value = "/trainers/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteTrainer(@PathVariable Long id){
        log.trace("TrainerController - deleteTrainer - enter method");
        trainerService.deleteTrainerById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/trainers/filterFirstName/{firstName}", method = RequestMethod.GET)
    List<TrainerDto> getTrainersByFirstName(@PathVariable String firstName) {
        log.trace("begin filter trainers name={}",firstName);
        List<TrainerDto> cpy = new TrainersDto(trainerConverter
                .convertModelsToDtos(trainerService.filterTrainersByName(firstName))).getTrainers();
        log.trace("end filter trainers={}",cpy);
        return cpy;
    }

    @RequestMapping(value = "/trainers/sort", method = RequestMethod.GET)
    List<TrainerDto> getTrainersSorted() {
        log.trace("begin sort trainers");
        List<TrainerDto> cpy = new TrainersDto(trainerConverter
                .convertModelsToDtos(trainerService.sortTrainersAscendingByFirstName())).getTrainers();
        log.trace("end sort trainers={}",cpy);
        return cpy;
    }

}
