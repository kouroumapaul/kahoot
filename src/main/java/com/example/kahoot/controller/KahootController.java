package com.example.kahoot.controller;

import com.example.kahoot.dto.kahoot.KahootCreateDto;
import com.example.kahoot.dto.kahoot.KahootDto;
import com.example.kahoot.dto.kahoot.KahootSummaryDto;
import com.example.kahoot.service.kahoot.KahootService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kahoots")
public class KahootController {
    private final KahootService kahootService;

    @Autowired
    public KahootController(KahootService kahootService) {
        this.kahootService = kahootService;
    }

    @PostMapping
    public ResponseEntity<KahootDto> createKahoot(@Valid @RequestBody KahootCreateDto kahootCreateDto) {
        KahootDto createdKahoot = kahootService.createKahoot(kahootCreateDto);
        return new ResponseEntity<>(createdKahoot, HttpStatus.CREATED);
    }

    @GetMapping("/{kahootId}")
    public ResponseEntity<KahootDto> getKahoot(@PathVariable Long kahootId) {
        KahootDto kahoot = kahootService.getKahoot(kahootId);
        return new ResponseEntity<>(kahoot, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Iterable<KahootSummaryDto>> getKahoots() {
        Iterable<KahootSummaryDto> kahoots = kahootService.findAllKahoots();
        return new ResponseEntity<>(kahoots, HttpStatus.OK);
    }
}
