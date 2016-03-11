package com.example.project3vice.vice_classes;

/**
 * Created by gregorydaly on 3/9/16.
 */

public class Media{
    private Video video;
    private GalleryItem[] gallery;

    public Media(){}

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public GalleryItem[] getGallery() {
        return gallery;
    }

    public void setGallery(GalleryItem[] gallery) {
        this.gallery = gallery;
    }
}
