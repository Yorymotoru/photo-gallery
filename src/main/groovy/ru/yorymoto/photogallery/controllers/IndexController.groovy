package ru.yorymoto.photogallery.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.thymeleaf.context.LazyContextVariable
import ru.yorymoto.photogallery.model.OneDriveResponse
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
        model.addAttribute("listImages", new LazyContextVariable<List<OneDriveResponse>>() {
            @Override
            protected List<Object> loadValue() {
                return oneDriveIntegration.getListOfMainImages()
            }
        })

//        model.addAttribute("listImages", oneDriveIntegration.getListOfMainImages())
        return "gallery_css"
    }

    @RequestMapping(value = "/wallpapers", method = RequestMethod.GET)
    wallpaper(Model model) {
        model.addAttribute("listImages", oneDriveIntegration.getListOfWallpapers())
        return "gallery"
    }

    @RequestMapping(value = "/old", method = RequestMethod.GET)
    indexOld(Model model) {
        model.addAttribute("listImages", oneDriveIntegration.getListOfMainImages())
        return "gallery"
    }

}
