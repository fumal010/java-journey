package com.learn.finance.app;

import com.learn.finance.service.FinanceTracker;
import com.learn.finance.ui.AppMenu;

public class Main {
    public static void main(String[] args) {
        FinanceTracker tracker = new FinanceTracker();
        AppMenu menu = new AppMenu(tracker);
        menu.run();
    }
}
