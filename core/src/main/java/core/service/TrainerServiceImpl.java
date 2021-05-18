package core.service;

import core.model.Trainer;
import core.repository.TrainerRepository;
import core.validators.TrainerValidator;
import core.validators.ValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TrainerServiceImpl implements TrainerService{
    private static final Logger log = LoggerFactory.getLogger(TrainerServiceImpl.class);

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private TrainerValidator trainerValidator;


    @Override
    public List<Trainer> getAllTrainers() {
        return trainerRepository.findAll();
    }

    @Override
    public List<Trainer> filterTrainersByName(String name) {
        log.trace("filterTrainersByName - method entered age={}",name);
        return trainerRepository.findAll()
                .stream()
                .filter(trainer -> trainer.getFirstName().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public Trainer saveTrainer(Trainer trainer) {
        log.trace("TrainerServiceImpl - saveTrainer - method entered: trainer={}", trainer);
        trainerValidator.validate(trainer);
        return trainerRepository.save(trainer);
    }

    @Override
    @Transactional
    public Trainer updateTrainer(Long id, Trainer trainer) {
        Optional<Trainer> findTrainer = trainerRepository.findById(trainer.getId());
        if (findTrainer.isEmpty())
            throw new ValidatorException("Cannot find id");

        try {
            log.trace("TrainerServiceImpl - updateTrainer - method entered: trainer={}", trainer);

            Trainer update = trainerRepository.findById(id).orElse(trainer);
            update.setAge(trainer.getAge());
            update.setFirstName(trainer.getFirstName());
            update.setLastName(trainer.getLastName());
            return update;
        } catch (ValidatorException e) {
            throw new ValidatorException(e.getMessage());
        }
    }

    @Override
    public void deleteTrainerById(Long id) {
        Optional<Trainer> findTrainer = trainerRepository.findById(id);
        if (findTrainer.isEmpty()) {
            throw new ValidatorException("Cannot find id");
        }

        log.trace("TrainerServiceImpl - deleteTrainer - method entered: id={}", id);
        trainerRepository.deleteById(id);
        log.trace("TrainerServiceImpl - deleteTrainer - method finished");
    }

    @Override
    public List<Trainer> sortTrainersAscendingByFirstName() {
        log.trace("sortTrainersAscendingByName - method entered");
        Iterable<Trainer> trainers = trainerRepository.findAll(Sort.by("firstName").ascending());
        return StreamSupport.stream(trainers.spliterator(),false)
                .collect(Collectors.toList());
    }
}
