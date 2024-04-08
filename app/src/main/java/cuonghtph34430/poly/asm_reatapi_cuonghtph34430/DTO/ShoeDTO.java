package cuonghtph34430.poly.asm_reatapi_cuonghtph34430.DTO;

public class ShoeDTO {

    private String name;
    private String description; // Thay đổi từ brand thành description
    private long price;
    private String img;
    private String url;
    private String _id;

    public ShoeDTO(String name, String description, long price,String img) {
        this.name = name;
        this.description = description; // Thay đổi từ brand thành description
        this.price = price;
        this.img=img;
        this.url = url;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ShoeDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' + // Thay đổi từ brand thành description
                ", price=" + price +
                ", img="+ img +
                ", url='" + url + '\'' +
                ", id='" + _id + '\'' +
                '}';
    }
}
