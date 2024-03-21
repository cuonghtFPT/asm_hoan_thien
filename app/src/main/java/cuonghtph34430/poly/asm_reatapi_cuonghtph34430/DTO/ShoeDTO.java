package cuonghtph34430.poly.asm_reatapi_cuonghtph34430.DTO;

public class ShoeDTO {

    private String name;
    private String description; // Thay đổi từ brand thành description
    private long price;
    private String url;
    private String _id;

    public ShoeDTO(String name, String description, long price, String url) {
        this.name = name;
        this.description = description; // Thay đổi từ brand thành description
        this.price = price;
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
                ", url='" + url + '\'' +
                ", id='" + _id + '\'' +
                '}';
    }
}
