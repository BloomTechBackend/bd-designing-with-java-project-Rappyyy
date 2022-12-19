package com.amazon.ata;

import com.amazon.ata.cost.CostStrategy;
import com.amazon.ata.cost.MonetaryCostStrategy;
import com.amazon.ata.types.Material;
import com.amazon.ata.types.Packaging;
import com.amazon.ata.types.ShipmentCost;
import com.amazon.ata.types.ShipmentOption;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class WeightedCostStrategy implements CostStrategy {

    private static final BigDecimal LABOR_COST = BigDecimal.valueOf(0.33);
    private final Map<Material, BigDecimal> weightedCostPerGram;

    /**
     *
     * @param monetaryWrapper for the monetary wrapper.
     * @param carbonWrapper for the carbon wrapper.
     */
    public WeightedCostStrategy(MonetaryCostStrategy monetaryWrapper, CarbonCostStrategy carbonWrapper) {
        weightedCostPerGram = new HashMap<>();

        BigDecimal carbonCostPerGram = carbonWrapper.getMaterialCostPerGram(Material.CORRUGATE);
        BigDecimal carbonCostWeighted = carbonCostPerGram.multiply(BigDecimal.valueOf(0.20));
        BigDecimal monetaryCostPerGram = monetaryWrapper.getMonetaryCostPerGram(Material.CORRUGATE);
        BigDecimal monetaryCostWeighted = monetaryCostPerGram.multiply(BigDecimal.valueOf(0.80));
        BigDecimal weightedCostBox = carbonCostWeighted.add(monetaryCostWeighted);

        weightedCostPerGram.put(Material.CORRUGATE, weightedCostBox);


        BigDecimal weightedCostPolyBag = ((carbonWrapper.getMaterialCostPerGram(Material.LAMINATED_PLASTIC))
                .multiply(BigDecimal.valueOf(0.20)))
                .add((monetaryWrapper.getMonetaryCostPerGram(Material.LAMINATED_PLASTIC))
                        .multiply(BigDecimal.valueOf(0.80)));
        weightedCostPerGram.put(Material.LAMINATED_PLASTIC, weightedCostPolyBag);
    }



    @Override
    public ShipmentCost getCost(ShipmentOption shipmentOption) {
        Packaging packaging = shipmentOption.getPackaging();
        BigDecimal weightedCostPerGram = this.weightedCostPerGram.get(packaging.getMaterial());

        BigDecimal weightedCost = packaging.getMass().multiply(weightedCostPerGram).add(LABOR_COST);

        return new ShipmentCost(shipmentOption, weightedCost);
    }
}
