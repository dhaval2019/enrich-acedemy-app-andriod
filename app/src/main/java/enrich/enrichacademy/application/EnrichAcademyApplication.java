package enrich.enrichacademy.application;

import android.app.Application;

import java.util.ArrayList;
import java.util.Iterator;

import enrich.enrichacademy.model.ServicesModel;
import enrich.enrichacademy.utils.EnrichUtils;

/**
 * Created by Admin on 03-Mar-17.
 */

public class EnrichAcademyApplication extends Application {

    ArrayList<ServicesModel> cartList;

    int maximumQuantity = 3;

    @Override
    public void onCreate() {
        super.onCreate();
        cartList = new ArrayList<>();
    }

    public void addToCart(ServicesModel servicesModel) {
        if (isCartFull())
            return;

        if (alreadyExist(servicesModel)) {
            EnrichUtils.showMessage(this, servicesModel.name + " already in cart");
            return;
        }

        cartList.add(servicesModel);

        EnrichUtils.log("" + cartList.size());
        EnrichUtils.showMessage(this, servicesModel.name + " added to cart");
    }

    public boolean isCartFull() {
        if (cartList.size() == maximumQuantity)
            return true;
        return false;
    }

    public boolean isCartEmpty() {
        if (cartList.size() == 0)
            return true;
        return false;
    }

    public boolean alreadyExist(ServicesModel servicesModel) {
        for (int i = 0; i < cartList.size(); i++) {
            if (servicesModel.Id == cartList.get(i).Id) {
                return true;
            }
        }
        return false;
    }

    public void removeFromCart(ServicesModel servicesModel) {
        Iterator<ServicesModel> itemIterator = cartList.iterator();
        while (itemIterator.hasNext()) {
            ServicesModel servicesModelTemp = itemIterator.next();
            if (servicesModelTemp.Id == servicesModel.Id) {
                itemIterator.remove();
            }
        }
    }

    public void updateCartItem(ServicesModel servicesModel) {
        removeFromCart(servicesModel);
        addToCart(servicesModel);
    }

    public ServicesModel getCartItem(int position) {
        for (int i = 0; i < cartList.size(); i++) {
            return cartList.get(i);
        }
        return null;
    }

    public int getCartItemCount() {
        return cartList.size();
    }

    public double getTotalCartPrice() {
        double totalPrice = 0;
        for (int i = 0; i < cartList.size(); i++)
            if (cartList.get(i).DiscountPrice != 0)
                totalPrice += cartList.get(i).DiscountPrice;
        return totalPrice;
    }

    public void clearCart() {
        cartList.clear();
    }

    public ArrayList<ServicesModel> getCart() {
        return cartList;
    }
}
