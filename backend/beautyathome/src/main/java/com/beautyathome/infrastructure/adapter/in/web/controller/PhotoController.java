package api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.dto.ConsentRequest;
import api.dto.PhotoUploadRequest;
import application.facade.BeautyAtHomeFacade;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {

    private final BeautyAtHomeFacade facade;

    public PhotoController(BeautyAtHomeFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    public void uploadPhoto(@RequestBody PhotoUploadRequest request) {
        facade.uploadPhoto(request.getBookingId(), request.getUrl(), request.isPublic());
    }

    @PostMapping("/consent")
    public void grantConsent(@RequestBody ConsentRequest request) {
        facade.grantPhotoConsent(request.getBookingId());
    }
}
