package com.amazon.ata.dao;

import com.amazon.ata.datastore.PackagingDatastore;
import com.amazon.ata.exceptions.NoPackagingFitsItemException;
import com.amazon.ata.exceptions.UnknownFulfillmentCenterException;
import com.amazon.ata.types.*;

import java.util.*;

public class PackagingDAO {

    private Map<FulfillmentCenter, Set<FcPackagingOption>> fcPackagingOptions;


    /**
     * Instantiates a PackagingDAO object.
     *
     * @param datastore Where to pull the data from for fulfillment center/packaging available mappings.
     */


    /*
     * This constructor will instantiate a hashmap containing info from PackagingDataStore
     * but more neat and organized by fulfillmentcenter id.
     * @param datastore- takes an instance of datastore which is the class that contains all fulfillment Center */
    public PackagingDAO(PackagingDatastore datastore) {
        // KEY :    Objects Fulfillment Centers
        // VALUE : HashSet <FCPackagingOption> fcPackagingOptionsSet

        Set<FcPackagingOption> tempPackaging = new HashSet<>(datastore.getFcPackagingOptions());

        this.fcPackagingOptions = new HashMap<>();

        for (FcPackagingOption e : datastore.getFcPackagingOptions()) {

            fcPackagingOptions.put(e.getFulfillmentCenter(), tempPackaging);

        }


    }


    /**
     * Returns the packaging options available for a given item at the specified fulfillment center. The API
     * used to call this method handles null inputs, so we don't have to.
     *
     * @param item              the item to pack
     * @param fulfillmentCenter fulfillment center to fulfill the order from
     * @return the shipping options available for that item; this can never be empty, because if there is no
     * acceptable option an exception will be thrown
     * @throws UnknownFulfillmentCenterException if the fulfillmentCenter is not in the fcPackagingOptions list
     * @throws NoPackagingFitsItemException      if the item doesn't fit in any packaging at the FC
     */

    public List<ShipmentOption> findShipmentOptions(Item item, FulfillmentCenter fulfillmentCenter)
            throws UnknownFulfillmentCenterException, NoPackagingFitsItemException {

        boolean fcFound = false;
        List<ShipmentOption> result = new ArrayList<>();
        boolean doesContain = fcPackagingOptions.containsKey(fulfillmentCenter);

        // TODO Ensure No Repeats - if fcCode is null and not contained in hashmap (merged both together)
        // remove else if and run the tct-
        if (fcPackagingOptions.get(fulfillmentCenter) == null) {
            System.out.println("HashMap cannot call a 'null' fulfillmentcenter!");
            throw new UnknownFulfillmentCenterException();
        }

        for (FcPackagingOption fcPackagingOption : fcPackagingOptions.get(fulfillmentCenter)) {

            Packaging thisPackaging = fcPackagingOption.getPackaging();
            System.out.println(String.format(" This Packaging Option's fulfilmentCenter : %s ", fcPackagingOption
                    .getFulfillmentCenter().getFcCode()));
            String thisFcCode = fulfillmentCenter.getFcCode();

            // if this fccode is a valid existing fulfilmentcenter code, then add to hashmap at end...
            if (thisFcCode.equals(fcPackagingOption.getFulfillmentCenter().getFcCode())) {
                fcFound = true;
                System.out.println(" FCCode Match for %s  found" + thisFcCode);

                // if this item fits in this packaging
                if (thisPackaging.canFitItem(item)) {
                    ShipmentOption thisShipmentOption = ShipmentOption.builder().withPackaging(thisPackaging)
                            .withItem(item).withFulfillmentCenter(fulfillmentCenter).build();

                    result.add(thisShipmentOption);
                }


            }

        }

        if (!fcFound) {
            throw new UnknownFulfillmentCenterException(
                    String.format("Unknown FC: %s!", fulfillmentCenter.getFcCode()));
        }


        if (result.isEmpty()) {
            throw new NoPackagingFitsItemException(
                    String.format("No packaging at %s fits %s!", fulfillmentCenter.getFcCode(), item));
        }

        return result;
    }

    /*
     * duplicateCheck Method checks for duplicate fcPackagingOptions */
    public List<ShipmentOption> duplicateCheck(List<ShipmentOption> fcPackagingOptions) {
        if (fcPackagingOptions != null) {
            for (int i = 0; i < fcPackagingOptions.size() - 1; i++) {
                String fcCode1 = fcPackagingOptions.get(i).getFulfillmentCenter().getFcCode();
                String fcCode2 = fcPackagingOptions.get(i + 1).getFulfillmentCenter().getFcCode();
                if (fcCode1.equals(fcCode2)) {
                    fcPackagingOptions.remove(i);
                }
            }
        }
        return fcPackagingOptions;
    }
}
