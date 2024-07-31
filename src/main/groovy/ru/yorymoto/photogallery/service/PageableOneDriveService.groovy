package ru.yorymoto.photogallery.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import ru.yorymoto.photogallery.model.ImageForPage

@Service
public class PageableOneDriveService {
    OneDriveIntegration oneDriveIntegration

    @Autowired
    PageableOneDriveService(OneDriveIntegration oneDriveIntegration) {
        this.oneDriveIntegration = oneDriveIntegration
    }

    def findPaginated(Pageable pageable) {
        def pageSize = pageable.getPageSize()
        def currentPage = pageable.getPageNumber()
        def startItem = currentPage * pageSize
        List<ImageForPage> list
        def images = oneDriveIntegration.getListOfMainImages()

        if (images.size() < startItem) {
            list = Collections.emptyList()
        } else {
            def toIndex = Math.min(startItem + pageSize, images.size())
            list = images.subList(startItem, toIndex)
        }

        new PageImpl<ImageForPage>(list, PageRequest.of(currentPage, pageSize), images.size())
    }
}
