package ru.yorymoto.photogallery.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import ru.yorymoto.photogallery.service.OneDriveIntegration

@Controller
class IndexController {

    OneDriveIntegration oneDriveIntegration

    @Autowired
    IndexController(OneDriveIntegration oneDriveIntegration) {
        this.oneDriveIntegration = oneDriveIntegration
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    index(Model model) {
        model.addAttribute("listImages", oneDriveIntegration.getListOfImages())
        return "gallery"
    }

}
