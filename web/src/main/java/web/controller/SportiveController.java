package web.controller;


import core.service.SportiveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.converter.SportiveConverter;
import web.dto.SportiveDto;
import web.dto.SportivesDto;

import java.util.List;
import java.util.Set;

@RestController
public class SportiveController {
    public static final Logger log = LoggerFactory.getLogger(SportiveController.class);

    @Autowired
    private SportiveService sportiveService;

    @Autowired
    private SportiveConverter sportiveConverter;


    @RequestMapping(value = "/sportives", method = RequestMethod.GET)
    List<SportiveDto> getSportives() {
        log.trace("SportiveController - getSportive - enter method");
        List<SportiveDto> cpy = new SportivesDto(sportiveConverter
                .convertModelsToDtos(sportiveService.getAllSportives())).getSportives();
        log.trace("end get sportives={}",cpy);
        return cpy;
    }


    @RequestMapping(value = "/sportives", method = RequestMethod.POST)
    SportiveDto saveTeam(@RequestBody SportiveDto teamDto) {
        log.trace("TeamController - saveTeam - enter method");
        return sportiveConverter.convertModelToDto(sportiveService.saveSportive(
                sportiveConverter.convertDtoToModel(teamDto)
        ));
    }

    @RequestMapping(value = "/sportives/{id}", method = RequestMethod.PUT)
    SportiveDto updateClient(@PathVariable Long id,
                         @RequestBody SportiveDto teamDto) {
        log.trace("TeamController - updateTeam - enter method");
        return sportiveConverter.convertModelToDto( sportiveService.updateSportive(id,
                sportiveConverter.convertDtoToModel(teamDto)));
    }

    @RequestMapping(value = "/sportives/{id}", method = RequestMethod.DELETE)
    ResponseEntity<?> deleteClient(@PathVariable Long id){
        log.trace("TeamController - deleteTeam - enter method");

        sportiveService.deleteSportive(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/sportives/filterAge/{age}", method = RequestMethod.GET)
    List<SportiveDto> getSportivesByAge(@PathVariable int age) {
        log.trace("begin filter sportives name={}",age);
        List<SportiveDto> cpy = new SportivesDto(sportiveConverter
                .convertModelsToDtos(sportiveService.filterSportivesByAge(age))).getSportives();
        log.trace("end filter sportives={}",cpy);
        return cpy;
    }

    @RequestMapping(value = "/sportives/filterFirstName/{firstName}", method = RequestMethod.GET)
    List<SportiveDto> getSportivesByFirstName(@PathVariable String firstName) {
        log.trace("begin filter sportives name={}",firstName);
        List<SportiveDto> cpy = new SportivesDto(sportiveConverter
                .convertModelsToDtos(sportiveService.filterSportivesByFirstName(firstName))).getSportives();
        log.trace("end filter sportives={}",cpy);
        return cpy;
    }

    @RequestMapping(value = "/sportives/filterTeamId/{teamId}", method = RequestMethod.GET)
    List<SportiveDto> getSportivesByTeamId(@PathVariable int teamId) {
        log.trace("begin filter sportives name={}",teamId);
        List<SportiveDto> cpy = new SportivesDto(sportiveConverter
                .convertModelsToDtos(sportiveService.filterSportivesByTeamId(teamId))).getSportives();
        log.trace("end filter sportives={}",cpy);
        return cpy;
    }

    @RequestMapping(value = "/sportives/sort", method = RequestMethod.GET)
    List<SportiveDto> getSportivesSorted() {
        log.trace("begin sort sportives");
        List<SportiveDto> cpy = new SportivesDto(sportiveConverter
                .convertModelsToDtos(sportiveService.sortSportivesAscendingByFirstName())).getSportives();
        log.trace("end sort sportives={}",cpy);
        return cpy;
    }
}
