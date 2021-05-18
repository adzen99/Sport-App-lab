package core.service;


import core.model.Trainer;

import java.util.List;

public interface TrainerService {
    Trainer saveTrainer(Trainer trainer);

    Trainer updateTrainer(Long id, Trainer trainer);

    void deleteTrainerById(Long id);

    List<Trainer> getAllTrainers();

    List<Trainer> filterTrainersByName(String name);

    List<Trainer> sortTrainersAscendingByFirstName();
}
