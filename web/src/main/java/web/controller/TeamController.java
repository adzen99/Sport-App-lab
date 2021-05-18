package web.controller;

import core.service.TeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.TeamConverter;
import web.dto.TeamDto;
import web.dto.TeamsDto;

import java.util.List;
import java.util.Set;

@RestController
public class TeamController {
    public static final Logger log = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private TeamService teamService;

    @Autowired
    private TeamConverter teamConverter;


    @RequestMapping(value = "/teams", method = RequestMethod.GET)
    List<TeamDto> getTeams() {
        log.trace("TeamController - getTeams - enter method");
        List<TeamDto> cpy = new TeamsDto(teamConverter
                .convertModelsToDtos(teamService.getAllTeams())).getTeams();
        log.trace("end get teams={}",cpy);
        return cpy;
    }

    @RequestMapping(value = "/teams", method = RequestMethod.POST)
    TeamDto saveTeam(@RequestBody TeamDto teamDto) {
        log.trace("TeamController - saveTeam - enter method");
        return teamConverter.convertModelToDto(teamService.saveTeam(
                teamConverter.convertDtoToModel(teamDto)
        ));
    }

    @RequestMapping(value = "/teams/{id}", method = RequestMethod.PUT)
    TeamDto updateTeam(@PathVariable Long id,
                           @RequestBody TeamDto teamDto) {
        log.trace("TeamController - updateTeam - enter method");
        return teamConverter.convertModelToDto( teamService.updateTeam(id,
                teamConverter.convertDtoToModel(teamDto)));
    }

    @RequestMapping(value = "/teams/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteTeam(@PathVariable Long id){
        log.trace("TeamController - deleteTeam - enter method");

        teamService.deleteTeamById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/teams/filterByName/{teamName}", method = RequestMethod.GET)
    List<TeamDto> getTeamsByFirstName(@PathVariable String teamName) {
        log.trace("begin filter teams name={}",teamName);
        List<TeamDto> cpy = new TeamsDto(teamConverter
                .convertModelsToDtos(teamService.filterTeamsByTeamName(teamName))).getTeams();
        log.trace("end filter teams={}",cpy);
        return cpy;
    }

    @RequestMapping(value = "/teams/sort", method = RequestMethod.GET)
    List<TeamDto> getTeamsSorted() {
        log.trace("begin sort teams");
        List<TeamDto> cpy = new TeamsDto(teamConverter
                .convertModelsToDtos(teamService.sortTeamsAscendingByName())).getTeams();
        log.trace("end sort teams={}",cpy);
        return cpy;
    }
}
