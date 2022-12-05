package com.amazon.ata.types;

import java.math.BigDecimal;

public class PolyBag extends Packaging{
    private BigDecimal volume;
    /**
     * Instantiates a new Packaging object.
     *
     * @param material - the Material of the package
     * @param length   - the length of the package
     * @param width    - the width of the package
     * @param height   - the height of the package
     */
    public PolyBag(Material material, BigDecimal length, BigDecimal width, BigDecimal height) {
        super(material);
    }

    @Override
    // Look for formula in directions for mass
    public BigDecimal getMass() {

        return super.getMass();
    }

    @Override
    public boolean canFitItem(Item item) {
        return super.canFitItem(item);
    }
}
