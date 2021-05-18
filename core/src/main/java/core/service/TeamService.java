package core.service;


import core.model.Team;

import java.util.List;

public interface TeamService {
    List<Team> getAllTeams();

    Team saveTeam(Team team);

    Team updateTeam(Long id, Team team);

    void deleteTeamById(Long id);

    List<Team> filterTeamsByTeamName(String name);

    List<Team> sortTeamsAscendingByName();
}
