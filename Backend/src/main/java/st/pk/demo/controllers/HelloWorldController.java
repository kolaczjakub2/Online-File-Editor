package st.pk.demo.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @CrossOrigin
    @GetMapping("helloWorld/{name}")
    public String helloWorld(@PathVariable String name) {
        return "hello " + name;
    }
}
