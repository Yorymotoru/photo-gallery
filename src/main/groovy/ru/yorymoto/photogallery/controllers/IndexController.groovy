package ru.yorymoto.photogallery.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PathVariable
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
        def currentPage = 1
        def pageSize = 6

        Page<String> galleryPage = oneDriveIntegration.findPaginated(PageRequest.of(currentPage - 1, pageSize))

        model.addAttribute("listImages", galleryPage)

        return "gallery_infinity_load"
    }

    @RequestMapping(value = "/css", method = RequestMethod.GET)
    indexCss(Model model) {
        model.addAttribute("listImages", new LazyContextVariable<List<OneDriveResponse>>() {
            @Override
            protected List<Object> loadValue() {
                return oneDriveIntegration.getListOfMainImages()
            }
        })

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

    @RequestMapping(value = "/{page}", method = RequestMethod.GET)
    indexPaginated(
            Model model,
            @PathVariable("page") Optional<Integer> page
    ) {
        def currentPage = page.orElse(1)
        def pageSize = 6

        Page<String> galleryPage = oneDriveIntegration.findPaginated(PageRequest.of(currentPage - 1, pageSize))

        model.addAttribute("listImages", galleryPage)

        return "gallery_infinity_load"
    }

}
