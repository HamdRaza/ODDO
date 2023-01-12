package com.tom.chef.ui.comman.adopterLoader;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.tom.chef.ui.comman.faq.FAQItemAdapter;
import com.tom.chef.ui.comman.financial.FinancialItemAdapter;
import com.tom.chef.ui.comman.menuItems.MenuItemAdopter;
import com.tom.chef.ui.comman.menuItems.files.FileNamesAdopter;
import com.tom.chef.ui.comman.menuItems.packageMenu.PackageAdopter;
import com.tom.chef.ui.comman.menuItems.veriants.VariantItemAdopter;
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
    @BindingAdapter({"menuItemAdopter"})
    public static void menuItemAdopter(@NonNull RecyclerView recyclerView, MenuItemAdopter menuItemAdopter) {
        recyclerView.setAdapter(menuItemAdopter);
    }
    @BindingAdapter({"veriantsNamesAdopter"})
    public static void veriantsNamesAdopter(@NonNull RecyclerView recyclerView, VariantItemAdopter variantItemAdopter) {
        recyclerView.setAdapter(variantItemAdopter);
    }
    @BindingAdapter({"menuPackageAdopter"})
    public static void menuPackageAdopter(@NonNull RecyclerView recyclerView, PackageAdopter packageAdopter) {
        recyclerView.setAdapter(packageAdopter);
    }
    @BindingAdapter({"fileNamesAdopter"})
    public static void fileNamesAdopter(@NonNull RecyclerView recyclerView, FileNamesAdopter fileNamesAdopter) {
        recyclerView.setAdapter(fileNamesAdopter);
    }


}
