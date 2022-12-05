package helper;

import models.screenDom.ButtonDom;
import models.screenDom.DDom;

public class ButtonsFactory {

    public ButtonDom getNewOfferButton() {
        return createButton("Новый заказ", "Заказать книгу", "Заказать тетрадь");
    }

    public ButtonDom getReturnProductButton() {
        return createButton("Возврат", "Оформить возврат");
    }

    public ButtonDom getHistoryButton() {
        return createButton("История покупок", "За весь период", "За последний месяц", " За последний год");
    }

    private ButtonDom createButton(String t, String ... d) {
        ButtonDom button = new ButtonDom();
       button.setT(t);
        for(String value : d) {
            button.getDList().add(new DDom().withD(value));
        }
        return button;
    }
}
