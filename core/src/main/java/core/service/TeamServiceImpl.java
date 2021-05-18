package core.service;


import core.model.Team;
import core.repository.TeamRepository;
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
public class TeamServiceImpl implements TeamService {
    private static final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Team saveTeam(Team team) {
        log.trace("TeamServiceImpl - saveTeam - method entered: team={}", team);
        return teamRepository.save(team);
    }

    @Override
    @Transactional
    public Team updateTeam(Long id, Team team) {
        Optional<Team> findTeam = teamRepository.findById(team.getId());
        if (findTeam.isEmpty()) {
            throw new ValidatorException("Cannot find id");
        }

        try {
            log.trace("TeamServiceImpl - updateTeam - method entered: team={}", team);

            Team update = teamRepository.findById(id).orElse(team);
            update.setTeamName(team.getTeamName());
            return update;
        } catch (ValidatorException e) {
            throw new ValidatorException(e.getMessage());
        }
    }

    @Override
    public void deleteTeamById(Long id) {
        Optional<Team> findTeam = teamRepository.findById(id);
        if (findTeam.isEmpty()) {
            throw new ValidatorException("Cannot find id");
        }

        log.trace("TeamServiceImpl - deleteTeam - method entered: id={}", id);
        teamRepository.deleteById(id);
        log.trace("TeamServiceImpl - deleteTeam - method finished");
    }

    @Override
    public List<Team> filterTeamsByTeamName(String teamName) {
        log.trace("filterTeamsByTeamName - method entered teamName={}",teamName);
        return teamRepository.findAll()
                .stream()
                .filter(team -> team.getTeamName().contains(teamName))
                .collect(Collectors.toList());
    }

    @Override
    public List<Team> sortTeamsAscendingByName() {
        log.trace("sortTeamsAscendingByName - method entered");
        Iterable<Team> teams = teamRepository.findAll(Sort.by("teamName").ascending());
        return StreamSupport.stream(teams.spliterator(),false)
                .collect(Collectors.toList());
    }
}
