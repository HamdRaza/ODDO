package com.tom.chef.ui.comman.adopterLoader;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.tom.chef.ui.comman.faq.FAQItemAdapter;
import com.tom.chef.ui.comman.financial.FinancialItemAdapter;
import com.tom.chef.ui.comman.orderItem.OrderItemAdopter;
import com.tom.chef.ui.comman.orders.OrderAdopter;

public class DataBindingAdaptersJava {
    @BindingAdapter({"orderAdopter"})
    public static void orderAdopter(@NonNull RecyclerView recyclerView, OrderAdopter recyclerViewAdapter) {
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @BindingAdapter({"orderItemAdopter"})
    public static void orderItemAdopter(@NonNull RecyclerView recyclerView, OrderItemAdopter orderItemAdopter) {
        recyclerView.setAdapter(orderItemAdopter);
    }
    @BindingAdapter({"financialItemAdopter"})
    public static void financialItemAdopter(@NonNull RecyclerView recyclerView, FinancialItemAdapter financialItemAdapter) {
        recyclerView.setAdapter(financialItemAdapter);
    }
    @BindingAdapter({"faqItemAdopter"})
    public static void faqItemAdopter(@NonNull RecyclerView recyclerView, FAQItemAdapter faqItemAdapter) {
        recyclerView.setAdapter(faqItemAdapter);
    }
}
