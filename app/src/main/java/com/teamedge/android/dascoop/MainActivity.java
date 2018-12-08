package com.teamedge.android.dascoop;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * This app displays an order form to order ice cream.
 */
public class MainActivity extends Activity {

    int numberOfScoops = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        CheckBox sprinklesCheckBox = (CheckBox) findViewById(R.id.sprinkles);
        boolean hasSprinkles = sprinklesCheckBox.isChecked();
        CheckBox gummybearsCheckBox = (CheckBox) findViewById(R.id.gummybears);
        boolean hasGummybears = gummybearsCheckBox.isChecked();
        EditText nameEditText = (EditText) findViewById(R.id.name);

        displayQuantity(numberOfScoops);
        int price = calculatePrice(hasSprinkles, hasGummybears);
        displayPrice(price);

        String orderMessage = "Name: " + nameEditText.getText().toString() + "\n" +
                "Add sprinkles? " + hasSprinkles + "\n" +
                "Add gummy bears? " + hasGummybears + "\n" +
                "Number of Scoops: " + numberOfScoops + "\n" +
                "Total: $" + price;


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "DaScoop Order");
        intent.putExtra(Intent.EXTRA_TEXT), orderMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText("$" + number);
        priceTextView.setTextColor(Color.parseColor("#4AF6B3"));
    }

    public void increase(View view) {
        numberOfScoops = numberOfScoops + 1;
        displayQuantity(numberOfScoops);
    }

    public void decrease(View view) {
        numberOfScoops = numberOfScoops - 1;
        displayQuantity(numberOfScoops);
    }

    /**
     * Calculates the price of the order based on the current number of scoops.
     *
     * @return the price
     */
    private int calculatePrice(boolean addSprinkles, boolean addGummybears) {
        int price = numberOfScoops * 2;

        if (addSprinkles == true) {
            price++;
        }

        if (addGummybears == true) {
            price++;
        }
        return price;
    }

}
