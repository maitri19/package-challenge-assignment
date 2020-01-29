package com.mobiquityinc.util;

import java.util.List;

import com.mobiquityinc.common.CommonConstants;
import com.mobiquityinc.dto.ItemDto;
import com.mobiquityinc.dto.PackageDto;
import com.mobiquityinc.exception.APIException;

/**
 * The Class ValidationUtil.
 */
public class ValidationUtil {

	/**
	 * Restrict instantiates a new validation util.
	 */
	private ValidationUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * validate right number of items be available in a package.
	 *
	 * @param items the items
	 * @throws APIException the API exception
	 */
	public static void validateItemListSize(List<String> items) throws APIException {
		if (items.size() > CommonConstants.MAX_ITEM_SIZE)
			throw new APIException(
					String.format("Invalid package : Max items possible is %d", CommonConstants.MAX_ITEM_SIZE));

	}

	/**
	 * Validate all items weight values to be in valid range.
	 *
	 * @param packageDto the package dto
	 * @throws APIException the API exception
	 */
	public static void validateItemsWeights(PackageDto packageDto) throws APIException {
		boolean anyInvalidWeight = packageDto.getItemList().stream().map(ItemDto::getWeight)
				.anyMatch(weight -> weight > CommonConstants.MAX_WEIGHT || weight <= 0.0f);

		if (anyInvalidWeight)
			throw new APIException(
					String.format("Invalid package : Max item weight possible is %d", CommonConstants.MAX_WEIGHT));

	}

	/**
	 * Validate all items cost to be within allowed range.
	 *
	 * @param packageDto the package dto
	 * @throws APIException the API exception
	 */
	public static void validateItemsCosts(PackageDto packageDto) throws APIException {
		boolean anyInvalidCost = packageDto.getItemList().stream().map(ItemDto::getCost)
				.anyMatch(cost -> cost > CommonConstants.MAX_COST || cost <= 0);

		if (anyInvalidCost)
			throw new APIException(
					String.format("Invalid package : Max item cost possible is %d", CommonConstants.MAX_COST));
	}
}
