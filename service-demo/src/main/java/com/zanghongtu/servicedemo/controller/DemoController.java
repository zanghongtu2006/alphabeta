package com.zanghongtu.servicedemo.controller;

import com.zanghongtu.servicedemo.dto.DemoDTO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("demo")
public class DemoController {
    @GetMapping("/")
    public DemoDTO get() {
        return new DemoDTO("Hello World");
    }

    @PostMapping("/")
    public DemoDTO init(DemoDTO demoDTO) {
        return demoDTO;
    }

    @PutMapping("/")
    public DemoDTO update(DemoDTO demoDTO) {
        demoDTO.setResultStr(demoDTO.getResultStr() + " updated");
        return demoDTO;
    }

    @DeleteMapping("")
    public DemoDTO delete(DemoDTO demoDTO) {
        demoDTO.setResultStr(demoDTO.getResultStr() + " deleted");
        return demoDTO;
    }
}
