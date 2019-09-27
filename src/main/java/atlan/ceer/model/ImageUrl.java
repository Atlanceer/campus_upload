package atlan.ceer.model;

/**
 * 图片路径模型
 */
public class ImageUrl {
    private String image;
    private String imageCut;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageCut() {
        return imageCut;
    }

    public void setImageCut(String imageCut) {
        this.imageCut = imageCut;
    }

    @Override
    public String toString() {
        return "ImageUrl{" +
                "image='" + image + '\'' +
                ", imageCut='" + imageCut + '\'' +
                '}';
    }
}
