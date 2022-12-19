package com.amazon.ata;

import com.amazon.ata.cost.CostStrategy;
import com.amazon.ata.cost.MonetaryCostStrategy;
import com.amazon.ata.dao.PackagingDAO;
import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.service.ShipmentService;

public class App {

    /* don't instantiate me */
    private App() {}

    private static PackagingDatastore getPackagingDatastore() {
        return new PackagingDatastore();
    }
    private static MonetaryCostStrategy getMonetaryCostStrategy() {
        return new MonetaryCostStrategy();
    }
    private static CarbonCostStrategy getcarbonCostStrategy() {
        return new CarbonCostStrategy();
    }

    private static PackagingDAO getPackagingDAO() {
        return new PackagingDAO(getPackagingDatastore());
    }

    private static CostStrategy getCostStrategy() {
        return new WeightedCostStrategy(getMonetaryCostStrategy(),getcarbonCostStrategy());
    }

    public static ShipmentService getShipmentService() {
        return new ShipmentService(getPackagingDAO(), getCostStrategy());
    }
}
