package ro.hackitall.encode.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ro.hackitall.encode.service.EncodeService;

/**
 * Created by Andrei-Daniel Ene (andreidaniel.ene@bcr.ro) on 3/22/2018.
 */
@Controller
public class EncodeController {
    @Autowired
    EncodeService encodeService;
}
