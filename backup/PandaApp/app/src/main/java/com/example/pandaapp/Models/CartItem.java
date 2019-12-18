package com.example.pandaapp.Models;

public class CartItem {
    private Product product;
    private int Mount;

    public CartItem(Product product, int mount) {
        this.product = product;
        Mount = mount;
    }
    public Double getTotal()
    {
        return (product.getPrice()*(double)Mount);
    }


    public CartItem() {
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getMount() {
        return Mount;
    }

    public void setMount(int mount) {
        Mount = mount;
    }
}
