package org.study.bootcamp.task_16.instagram_filters.v1.api.cli;

import org.study.bootcamp.task_16.instagram_filters.v1.application.service.FilterProcessor;
import org.study.bootcamp.task_16.instagram_filters.v1.domain.model.Image;

import java.util.function.*;

public class Demo {
    public static void main(String[] args) {

        Image originalImage = new Image("original.jpg", "Original image");

        FilterProcessor processor = new FilterProcessor();

        Function<Image, Image> grayscaleFilter = createGrayscaleFilter();
        Function<Image, Image> sepiaFilter = createSepiaFilter();
        Function<Image, Image> vignetteFilter = createVignetteFilter();

        Image grayscaleImage = processor.applyFilter(originalImage, grayscaleFilter);
        System.out.println(grayscaleImage.description());

        Image sepiaImage = processor.applyFilter(grayscaleImage, sepiaFilter);
        System.out.println(sepiaImage.description());

        Function<Image, Image> combinedFilter = processor.combineFilters(grayscaleFilter, vignetteFilter);
        Image combinedImage = processor.applyFilter(originalImage, combinedFilter);
        System.out.println(combinedImage.description());
    }

    private static Function<Image, Image> createGrayscaleFilter() {
        return image -> image.withDescription(image.description() + " | Filter: Grayscale");
    }

    private static Function<Image, Image> createSepiaFilter() {
        return image -> image.withDescription(image.description() + " | Filter: Sepia");
    }

    private static Function<Image, Image> createVignetteFilter() {
        return image -> image.withDescription(image.description() + " | Filter: Vignette");
    }
}
