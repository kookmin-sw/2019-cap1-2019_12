package com.capstone.server.controller;

import com.capstone.server.dbtest.DbTest;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
public class Controller {

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Principal home(Principal principal) {
        return principal;
    }

    private DbTest dbTest;

    @GetMapping("/dbtest")
    public void dbtest() throws Exception {
        dbTest.main();

    }


}
