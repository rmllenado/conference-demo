package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {
    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> list(){
        return speakerRepository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker get(@PathVariable Long id){
        Speaker speaker = speakerRepository.findById(id).orElse(null);
        if (speaker == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Speaker not found"
            );
        }
        return speaker;
    }

    @PostMapping
    public Speaker create(@RequestBody Speaker speaker){
        return speakerRepository.saveAndFlush(speaker);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id){
        if (!speakerRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Speaker not found");
        }
        speakerRepository.deleteById(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Speaker update(@PathVariable Long id, @RequestBody Speaker updateSpeaker){
        Speaker speaker = speakerRepository.findById(id).orElse(null);
        if (speaker == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Speaker not found");
        }
        BeanUtils.copyProperties(updateSpeaker, speaker, "speaker_id");
        return speakerRepository.saveAndFlush(speaker);
    }
}
