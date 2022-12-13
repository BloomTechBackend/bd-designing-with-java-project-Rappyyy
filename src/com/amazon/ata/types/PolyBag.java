package com.amazon.ata.types;

import java.math.BigDecimal;
public class PolyBag extends Packaging {
    private double mass;
    private BigDecimal volume;
    /**
     * Instantiates a new Packaging object.
     *
     * @param material - the Material of the package
     * @param volume   - the Volume of the package
     */
    public PolyBag(Material material, BigDecimal volume) {
        super(material);
        this.volume = volume;
    }
    public BigDecimal getVolume() {
     return this.volume;
    }

    @Override
    public boolean canFitItem(Item item) {
        BigDecimal itemHeight = item.getHeight();
        BigDecimal itemLength = item.getLength();
        BigDecimal itemWidth = item.getWidth();
        BigDecimal itemVolume = itemHeight.multiply(itemLength).multiply(itemWidth);
        return volume.compareTo(itemVolume) < 0;
    }
    @Override
    public BigDecimal getMass() {
        double v = 0.6;
        double vVolume = volume.doubleValue();
        mass = Math.ceil(Math.sqrt(vVolume) * v);
        return BigDecimal.valueOf(mass);
    }
}
